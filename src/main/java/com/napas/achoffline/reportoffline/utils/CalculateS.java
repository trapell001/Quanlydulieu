package com.napas.achoffline.reportoffline.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CalculateS {
    public static long calculateDay(Date date1, Date date2) {
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //PLus 24h
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR, 24);
        Date date3 = null;
        Date date4 = null;
        try {
            // calculating the difference b/w startDate and endDate
            String startDate =  simpleDateFormat.format(date1);
            String endDate = simpleDateFormat.format(date2);

            date3 = simpleDateFormat.parse(startDate);
            date4 = simpleDateFormat.parse(endDate);
            long getDiff = date4.getTime() - date3.getTime();
            long getDaysDiff = TimeUnit.MILLISECONDS.toMillis(getDiff);

            return (long)(getDaysDiff / 1000);
        } catch (Exception ex) {
            log.error("Message: {}", ex.getMessage());
        }
        return 0;
    }
//
//    public static void main(String[] args) {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
//        try {
//            Date date1 = simpleDateFormat.parse("26/07/2022 12:00:00");
//            Date date2 = simpleDateFormat.parse("27/07/2022 12:00:02");
//            System.out.println(calculateDay(date1,date2));
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
}


