package com.rtsj.return_to_soju.service;

import com.rtsj.return_to_soju.common.JwtProvider;
import com.rtsj.return_to_soju.exception.NotFoundUserException;
import com.rtsj.return_to_soju.model.dto.dto.KakaoTokenDto;
import com.rtsj.return_to_soju.model.dto.dto.KakaoUserInfo;
import com.rtsj.return_to_soju.model.dto.response.LoginResponseDto;
import com.rtsj.return_to_soju.model.dto.response.ReissueTokenResponseDto;
import com.rtsj.return_to_soju.model.dto.response.UserInfoResponseDto;
import com.rtsj.return_to_soju.model.entity.User;
import com.rtsj.return_to_soju.model.enums.Role;
import com.rtsj.return_to_soju.repository.CalenderRepository;
import com.rtsj.return_to_soju.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final OauthService oauthService;
    private final JwtProvider jwtProvider;

    @Transactional
    public void saveAndUpdateUserName(Long userId, String userName){
        User user = userRepository.findById(userId).orElseThrow(NotFoundUserException::new);
        user.updateMongleName(userName);
        return;
    }

    public UserInfoResponseDto getUserInfo(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(NotFoundUserException::new);
        return new UserInfoResponseDto(user);
    }

    public String getUserMemoryDateByFcmToken(String token){
        LocalDate memoryByFcmToken = userRepository.findMemoryByFcmToken(token);
        return memoryByFcmToken.toString();
    }

    @Transactional
    public LoginResponseDto loginWithKakaoToken(KakaoTokenDto kakaoTokenDto) {
        KakaoUserInfo kakaoUserInfo = oauthService.getKakaoUserInfoWithToken(kakaoTokenDto);
        Long userId = kakaoUserInfo.getId();
        String nickName = kakaoUserInfo.getNickName();

        LoginResponseDto dto = new LoginResponseDto();

        Optional<User> findUser = userRepository.findById(userId);
        if (findUser.isPresent()) {
            dto.setIsNew(false);
            User user = findUser.get();
            user.updateKakaoName(nickName);
            user.updateKakaoToken(kakaoTokenDto);
            dto.setName(user.getMongleName());
        }else{
            dto.setIsNew(true);
            User user = new User(userId, nickName, kakaoTokenDto, Role.ROLE_USER);
            userRepository.save(user);

        }
        String accessToken = jwtProvider.createAccessToken(String.valueOf(userId));
        String refreshToken = jwtProvider.createRefreshToken();
        dto.setAccessToken(accessToken);
        dto.setRefreshToken(refreshToken);
        dto.setAccessExpiredAt(jwtProvider.getExpirationDate(accessToken));
        dto.setRefreshExpiredAt(jwtProvider.getExpirationDate(refreshToken));
        dto.setTokenType("Bearer");

        return dto;
    }

    public String getUserFcmToken(Long userId){
        User user = userRepository.findById(userId).orElseThrow(NotFoundUserException::new);
        return user.getFcmToken();
    }

    @Transactional
    public void SaveUserFcmToken(Long userId, String fcmToken){
        User user = userRepository.findById(userId).orElseThrow(NotFoundUserException::new);
        user.setFcmToken(fcmToken);
    }

    public void updateUserKakaoNickName(User user){
        log.info("유저 토큰 재발급");
        KakaoTokenDto kakaoToken = user.getKakaoToken();
        ReissueTokenResponseDto reissueTokenResponseDto = oauthService.renewKakaoToken(kakaoToken.getRefreshToken());
        kakaoToken.setAccessToken(reissueTokenResponseDto.getAccessToken());
        if(reissueTokenResponseDto.getRefreshToken() != null){
            log.info("유저 리프레쉬 토큰 재발급");
            kakaoToken.setRefreshToken(reissueTokenResponseDto.getRefreshToken());
        }
        KakaoUserInfo kakaoUserInfo = oauthService.getKakaoUserInfoWithToken(kakaoToken);
        user.updateKakaoName(kakaoUserInfo.getNickName());
        user.updateKakaoToken(kakaoToken);
        log.info("유저 닉네임 업데이트");
    }
    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(NotFoundUserException::new);
        String s = oauthService.unlinkUser(user);
        userRepository.deleteById(Long.parseLong(s));
    }


}
