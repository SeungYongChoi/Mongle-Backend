package com.rtsj.return_to_soju.service;

import com.rtsj.return_to_soju.exception.NotFoundUserException;
import com.rtsj.return_to_soju.model.entity.User;
import com.rtsj.return_to_soju.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void saveAndUpdateUserName(Long userId, String userName){
        User user = userRepository.findById(userId).orElseThrow(NotFoundUserException::new);
        user.updateUserName(userName);
        return;
    }
}
