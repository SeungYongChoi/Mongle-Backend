package com.rtsj.return_to_soju.model.entity;

import com.rtsj.return_to_soju.model.enums.Role;
import com.rtsj.return_to_soju.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserTest {

    @Autowired
    private UserRepository userRepository;
//
//    @Test
//    public void saveOrMergeCheckTest() {
//        User user = new User(111111L, "yong", "yongyong", "qrjasviojklqwejf", "qwuerijfolaskzjvxc", Role.ROLE_USER);
//        userRepository.save(user);
//    }



}