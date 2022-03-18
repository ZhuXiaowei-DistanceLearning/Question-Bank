package com.zxw.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author zxw
 * @date 2022/3/18 10:45
 */
public class Date8Utils {
    public static void main(String[] args) {
        // 定义时区
        ZoneId zoneSH = ZoneId.of("Asia/Shanghai");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime parse = LocalDateTime.parse("2022-03-09 15:00:00", dateTimeFormatter);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault());
        System.out.println(parse);
        System.out.println(localDateTime);
//        ZonedDateTime of = ZonedDateTime.of(LocalDateTime.parse("", dateTimeFormatter), zoneSH);
//        ZoneOffset.ofHours(8);
    }
}
