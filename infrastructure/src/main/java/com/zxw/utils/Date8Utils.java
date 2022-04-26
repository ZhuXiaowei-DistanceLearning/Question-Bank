package com.zxw.utils;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        System.out.println(localDateTime.format(dateTimeFormatter));
//        ZonedDateTime of = ZonedDateTime.of(LocalDateTime.parse("", dateTimeFormatter), zoneSH);
//        ZoneOffset.ofHours(8);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        IntStream.range(0, 20)
//                .forEach(e -> executorService.execute(() -> IntStream.range(0, 10).forEach(e1 -> {
//                    try {
//                        System.out.println(simpleDateFormat.parse("2020-01-01 11:12:13"));
//                    } catch (ParseException ex) {
//                        ex.printStackTrace();
//                    }
//                })));
        // 使用TemporalAdjusters.firstDayOfMonth得到当前月的第一天；
        // 使用TemporalAdjusters.firstDayOfYear()得到当前年的第一天；
        // 使用TemporalAdjusters.previous(DayOfWeek.SATURDAY)得到上一个周六；
        // 使用TemporalAdjusters.lastInMonth(DayOfWeek.FRIDAY)得到本月最后一个周五。
        System.out.println(LocalDate.now().minus(Period.ofDays(1)).plus(1, ChronoUnit.DAYS).minusMonths(1).plus(Period.ofMonths(1)));
        System.out.println(ZonedDateTime.now());
        System.out.println(LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()));
        Duration duration = Duration.ofDays(1);
        System.out.println(duration.getSeconds());
        Instant now = Instant.now();
        System.out.println(now.toEpochMilli());
        Period period = Period.ofDays(1);
        System.out.println(period.getYears());
        Period between = Period.between(LocalDate.now(), LocalDate.now());
        System.out.println(between.getDays());
    }
}
