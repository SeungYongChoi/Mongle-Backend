package com.rtsj.return_to_soju.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import com.rtsj.return_to_soju.model.dto.dto.FcmMessage;
import com.rtsj.return_to_soju.model.dto.dto.FcmTypeAndData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FirebaseCloudMessageService{
    @Value("${fcm.key.name}")
    public String keyName;
    @Value("${fcm.key.scope}")
    public String fcmScope;

    @Value("${fcm.api.url}")
    public String fcmUrl;

    private final ObjectMapper objectMapper;

    private final UserService userService;
    public void sendMessageListWithToken(List<String> fcmTokenList, String title, String body){
        List<Message> messages = fcmTokenList.stream().map(
                token -> Message.builder()
                        .putData("type","gift")
                        .putData("date", userService.getUserMemoryDateByFcmToken(token))
                        .setNotification(new Notification(title, body))
                        .setToken(token)
                        .build())
                .collect(Collectors.toList());
        BatchResponse response;
        try{
            response = FirebaseMessaging.getInstance().sendAll(messages);
            log.info("단체 알림 전송");
            log.info(response.toString());
            if(response.getFailureCount()>0){
                List<SendResponse> responses = response.getResponses();
                List<String> failedTokens = new ArrayList<>();
                for(int i=0; i<responses.size(); i++){
                    if(!responses.get(i).isSuccessful()){
                        failedTokens.add(fcmTokenList.get(i));
                    }
                }
                log.error("List of tokens are not valid FCM token : " + failedTokens);
            }
        }catch (FirebaseMessagingException e){
            log.error("Cannot send to memberList push message. error info : {}", e.getMessage());
        }
    }

    public void sendMessageTo(String targetToken, String title, String body) throws IOException{
        OkHttpClient client = new OkHttpClient();
        log.info("알림을 전송합니다.");
        String message = makeMessage(targetToken, title, body);
        sendMessage(client, message);
    }

    public void sendErrorMessageTo(String targetToken, String title, String body) throws IOException{
        OkHttpClient client = new OkHttpClient();
        log.info("에러 알림을 전송합니다.");
        String message = makeErrorMessage(targetToken, title, body);
        sendMessage(client, message);
    }


    public void sendAnalyzeMessageTo(String targetToken, String title, String body, String date) throws IOException{
        OkHttpClient client = new OkHttpClient();
        log.info("분석 알림을 발송합니다.");
        String message = makeAnalyzeMessage(targetToken, title, body, date);
        sendMessage(client, message);
    }

    private void sendMessage(OkHttpClient client, String message) throws IOException {
        RequestBody requestBody = RequestBody.create(message,
                MediaType.get("application/json;charset=utf-8"));
        Request request = new Request.Builder()
                .url(fcmUrl)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION,"Bearer "+getAccessToken())
                .build();
        Response response = client.newCall(request)
                .execute();
        log.info(response.body().string());
    }
    private String makeMessage(String targetToken, String title, String body) throws JsonProcessingException {
        FcmMessage fcmMessage = FcmMessage.builder()
                .message(FcmMessage.Message.builder()
                        .token(targetToken)
                        .data(new FcmTypeAndData(title, body))
                        .build()
                )
                .validate_only(false)
                .build();
        return objectMapper.writeValueAsString(fcmMessage);
    }
    private String makeErrorMessage(String targetToken, String title, String body) throws JsonProcessingException {
        FcmMessage fcmMessage = FcmMessage.builder()
                .message(FcmMessage.Message.builder()
                        .token(targetToken)
                        .data(new FcmTypeAndData(title, body, "error", "전송 실패"))
                        .build()
                )
                .validate_only(false)
                .build();
        return objectMapper.writeValueAsString(fcmMessage);
    }
    private String makeAnalyzeMessage(String targetToken, String title, String body, String date) throws JsonProcessingException {
        FcmMessage fcmMessage = FcmMessage.builder()
                .message(FcmMessage.Message.builder()
                        .token(targetToken)
                        .data(new FcmTypeAndData(title, body, "analyze", date))
                        .build()
                )
                .validate_only(false)
                .build();
        return objectMapper.writeValueAsString(fcmMessage);
    }



    private String getAccessToken() throws IOException{
        String firebaseConfigPath = "firebase/" +keyName;
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath)
                        .getInputStream())
                .createScoped(List.of(fcmScope));
        googleCredentials.refreshIfExpired(); //access를 생성하기 위함.
        return googleCredentials.getAccessToken().getTokenValue();
    }

    @PostConstruct
    public void init(){
        String firebaseConfigPath = "firebase/" +keyName;
        try{
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(
                            GoogleCredentials
                                    .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                                    .createScoped(List.of(fcmScope))
                    )
                    .build();
            if(FirebaseApp.getApps().isEmpty()){
                FirebaseApp.initializeApp(options);
                log.info("Firebase application has been initialized");
            }
        } catch (IOException e){
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
