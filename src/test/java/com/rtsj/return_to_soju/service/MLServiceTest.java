package com.rtsj.return_to_soju.service;

import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class MLServiceTest {

    @Test
    public void test() throws ParseException {
        LocalDate localDate = convertStringToLocalDate("2022년 1월 13일 오후 2:13");
        System.out.println("localDate = " + localDate);
    }
    private LocalDate convertStringToLocalDate(String stringDate) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy년 MM월 dd일 a HH:mm");
        Date parse = df.parse(stringDate);
        LocalDate localDate = new java.sql.Date(parse.getTime()).toLocalDate();
        return localDate;
    }

}