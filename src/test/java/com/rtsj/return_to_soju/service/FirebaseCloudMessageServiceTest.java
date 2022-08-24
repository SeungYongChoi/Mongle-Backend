package com.rtsj.return_to_soju.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FirebaseCloudMessageServiceTest {

    @Autowired
    private FirebaseCloudMessageService firebaseCloudMessageService;

    @Test
    void test(){
        firebaseCloudMessageService.init();
    }
}