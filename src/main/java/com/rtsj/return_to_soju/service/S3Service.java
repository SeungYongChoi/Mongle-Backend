package com.rtsj.return_to_soju.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.rtsj.return_to_soju.common.CalendarUtil;
import com.rtsj.return_to_soju.exception.NotFoundCalenderException;
import com.rtsj.return_to_soju.exception.NotFoundUserException;
import com.rtsj.return_to_soju.model.entity.Calender;
import com.rtsj.return_to_soju.model.entity.KakaoRoom;
import com.rtsj.return_to_soju.model.entity.KakaoText;
import com.rtsj.return_to_soju.model.entity.User;
import com.rtsj.return_to_soju.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class S3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3 amazonS3;
    private final UserRepository userRepository;
    private final KakaoTextRepository kakaoTextRepository;
    private final CalenderRepository calenderRepository;
    private final DailySentenceRepository dailySentenceRepository;
    private final DailyTopicRepository dailyTopicRepository;

    private final String target = "카카오톡 대화";
    private final CalendarUtil calendarUtil;

    private List<String> uploadFile(List<MultipartFile> files, String prefix, String dirname, String suffix, User user) {
        log.info("S3 파일올리는 로직 시작");
        List<String> fileNameList = new ArrayList<>();
        files.stream()
                .forEach(file -> {
                    String kakaoRoomName;
                    kakaoRoomName = file.getOriginalFilename();
                    String fileName = dirname + "/" + prefix + kakaoRoomName + "-" + UUID.randomUUID() + suffix;
                    try (InputStream inputStream = checkDuplicateFile(file, user)) {
                        ObjectMetadata objectMetadata = new ObjectMetadata();
                        objectMetadata.setContentType(file.getContentType());
                        objectMetadata.setContentLength(inputStream.available());
                        amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                                .withCannedAcl(CannedAccessControlList.PublicRead));
                    } catch (IOException e) {
                        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
                    }
                    fileNameList.add(fileName);
                });
        log.info("S3 파일올리는 로직 완료");
        return fileNameList;
    }

    private void deleteDailySentenceAndTopicByDateAndRoom(User user, String date, String roomName) {
        LocalDate localDate = calendarUtil.convertStringToLocalDate(date);
        Calender calender = calenderRepository.findByUserAndDate(user, localDate).orElseThrow(NotFoundCalenderException::new);
        dailyTopicRepository.deleteAllByCalenderAndRoomName(calender, roomName);
        dailySentenceRepository.deleteAllByCalenderAndRoomName(calender, roomName);
    }

    private InputStream checkDuplicateFile(MultipartFile file, User user) throws IOException {
        String roomName = file.getOriginalFilename();
        List<String> collect = user.getRoomList().stream().map(KakaoRoom::getRoomName).collect(Collectors.toList());
        // 중복 된 파일
        if(collect.contains(roomName)){
            int i = collect.indexOf(roomName);
            KakaoRoom kakaoRoom = user.getRoomList().get(i);
            String lastDateTime = calendarUtil.convertStringToLocalDateStringKorean(kakaoRoom.getLastDateTime());
            InputStream inputStream = file.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String line;
            String memory = "";
            while((line=br.readLine())!=null){
                if(line.startsWith(lastDateTime)){
                    memory += line+'\n';
                    break;
                }
            }
            while((line=br.readLine()) != null){
                memory += line+'\n';
            }
            InputStream in = new ByteArrayInputStream(memory.getBytes(StandardCharsets.UTF_8));
            br.close();

            deleteDailySentenceAndTopicByDateAndRoom(user, kakaoRoom.getLastDateTime(), kakaoRoom.getRoomName());
            return in;
        }
        return file.getInputStream();
    }

    //Todo
    // 1. 카카오서버에 요청해서 닉네임 업데이트하기
    // 2. 카카오파일 하루단위로 잘라서 따로 올리기
    @Transactional
    public void uploadKakaoFile(List<MultipartFile> files, Long userId) {
        // 이 에러가 발생할 확률이 사실상 없지만 만약 발생하게 된다면 400번으로 나가는데 이게 옳을까..?
        User user = userRepository.findById(userId).orElseThrow(NotFoundUserException::new);
        String prefix = user.getId() + "-" + user.getKakaoName() + "-";

        List<String> urls = this.uploadFile(files, prefix, "origin", ".txt", user);
        urls.stream()
                .forEach(url -> {
                    KakaoText kakaoText = new KakaoText(url, user);
                    kakaoTextRepository.save(kakaoText);
                });
        log.info(userId+ "유저가 kakao 파일을 성공적으로 올렸습니다.");
    }
}
