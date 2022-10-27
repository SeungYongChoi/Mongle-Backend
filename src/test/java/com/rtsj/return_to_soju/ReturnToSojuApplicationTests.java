//package com.rtsj.return_to_soju;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
//import org.springframework.scheduling.annotation.EnableScheduling;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Locale;
//
////@SpringBootTest
////@EnableScheduling
////@EnableJpaAuditing
//class ReturnToSojuApplicationTests {
//
//	@Test
//	void contextLoads() throws ParseException {
//        String s = convertStringToLocalDateKorean("20221005");
//        System.out.println("s = " + s);
//    }
//    private String convertStringToLocalDateKorean(String data) throws ParseException {
//        SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMdd", Locale.KOREAN);
//        SimpleDateFormat newDtFormat = new SimpleDateFormat("yyyy년 M월 d일", Locale.KOREAN);
//        Date parse = dtFormat.parse(data);
//        String format = newDtFormat.format(parse);
//        return format;
//    }
//
//}
