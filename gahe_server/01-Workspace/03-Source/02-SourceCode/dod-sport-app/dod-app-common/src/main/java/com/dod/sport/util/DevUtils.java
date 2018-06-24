package com.dod.sport.util;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by defi on 2017-09-26.
 */
public class DevUtils {

    public static String formatOrderTime(String time){
        return " "+time.substring(0,5)+":00";
    }

    public static String formatOrderTimeToDatetime(String date, String time){
        return date + " "+time.substring(0,5)+":00";
    }


    public static String getWeekLastDate(String date) throws ParseException{
        Integer end = 7 - getWeekOfDate(date);
        String endDate = DateUtil.subDay(date, end);
        return endDate;
    }

    public static Integer getWeekOfDate(String dt) {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.setTime(DateUtil.parse(dt,"yyyy-MM-dd"));
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return w;
    }

    public static void main(String[] args) {

        try {
            System.out.println(getWeekLastDate("2017-10-10"));

        }catch (ParseException e){

        }

    }
}
