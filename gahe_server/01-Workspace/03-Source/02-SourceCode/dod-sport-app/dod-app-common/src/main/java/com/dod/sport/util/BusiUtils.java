package com.dod.sport.util;

import com.dod.sport.constant.SysConfigConstants;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/9/4.
 */
public class BusiUtils {

    /**
     * 获取datetime的numMinu之前的时间
     * @param datetime
     * @param numMinu
     * @return
     */
    public final static Date getDateTobefore(Date datetime,int numMinu) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(datetime);
        cal.add(cal.MINUTE,-numMinu);
        return cal.getTime();
    }

    /**
     * 获取datetime的numMinu之后的时间
     * @param datetime
     * @param numMinu
     * @return
     */
    public final static Date getDateToafter(Date datetime,int numMinu) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(datetime);
        cal.add(cal.MINUTE,numMinu);
        return cal.getTime();
    }

    /**
     * 获取某个月的天数
     * @param year
     * @param month
     * @return
     */
    public final static int getDaysByYearMonth(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }
    /**
     * 获取指定日期未周几
     * @param dateString
     * @return
     */
    public static int getWeek(String  dateString) throws ParseException {
        int[] weeks = {1,2,3,4,5,6,7};
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(dateString));
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if(week_index<0){
            week_index = 0;
        }
        return weeks[week_index];
    }

    /**
     * 获取指定日期date months个月后的日期
     * @param date
     * @param months 几个月
     * @param format 日期格式
     * @return
     */
    public static String getNextDate(Date date, int months,String format){
        SimpleDateFormat df=new SimpleDateFormat(format); //制定日期格式
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, months); //将当前日期加一个月
        return df.format(calendar.getTime());  //返回String型的时间
    }

    public static String getEmpWorkAge(String entryDate){
        //工龄计算
        Date date = new Date();
        Date beginDate = DateUtil.parse(entryDate, SysConfigConstants.DATE_FORMAT_FORDATE);
        Integer month = DateUtil.getMonth(beginDate,date);
        double years = Math.floor(month/12);
        return Double.toString(years);
    }

    /**
     * 取得两个时间段的时间间隔
     * return t2 与t1的间隔天数
     * throws ParseException 如果输入的日期格式不是0000-00-00 格式抛出异常
     */
    public static int getDays(String begin,String end) throws ParseException {
        DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate=df.parse(begin);
        Date endDate=df.parse(end);
        long  days = (endDate.getTime()-beginDate.getTime())/(60*60*1000*24);

       return (int)days;
    }

    /**
     * 两个时间相隔多少分钟
     * @param begin
     * @param end
     * @return
     * @throws ParseException
     */
    public static int getMinutes(String begin,String end) throws ParseException {
        DateFormat df=new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        Date beginDate=df.parse(begin);
        Date endDate=df.parse(end);
        long  minutes = (endDate.getTime()-beginDate.getTime())/(60*1000);

        return (int)minutes;
    }

    /**
     * 进行md5加密
     * @param message
     * @return
     */
    public static String md5(String message) throws UnsupportedEncodingException {
        try {
            MessageDigest md;
            md = MessageDigest.getInstance("md5");
            byte m5[] = md.digest(message.getBytes("utf-8"));
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(m5);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取星期几
     * @param time
     * @return
     */
    public static String getWeekNumber (String time){
        String Week = null;
        try {

            int  date = getWeek(time);
            switch (date) {
                case 1:
                    Week =  "天";
                case 2:
                    Week =  "一";
                case 3:
                    Week =  "二";
                case 4:
                    Week =  "三";
                case 5:
                    Week =  "四";
                case 6:
                    Week =  "五";
                case 7:
                    Week =  "六";
                default:

                    break;
            }
        }catch (Exception e){

        }

        return Week;
    }
}
