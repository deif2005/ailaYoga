package com.dod.sport.util;

import java.util.Calendar;

/**
 * Created by Administrator on 2017/11/2.
 */
public class IdCardUtil {
    /** 中国公民身份证号码最小长度。 */
    public  final int CHINA_ID_MIN_LENGTH = 15;

    /** 中国公民身份证号码最大长度。 */
    public  final int CHINA_ID_MAX_LENGTH = 18;

    /**
     * 根据身份编号获取年龄
     *
     * @param idCard
     *            身份编号
     * @return 年龄
     */
    public static int getAgeByIdCard(String idCard) {
        int iAge = 0;
        Calendar cal = Calendar.getInstance();
        String year = idCard.substring(6, 10);
        int iCurrYear = cal.get(Calendar.YEAR);
        iAge = iCurrYear - Integer.valueOf(year);
        return iAge;
    }

    /**
     * 根据身份编号获取生日
     *
     * @param idCard 身份编号
     * @return 生日(yyyyMMdd)
     */
    public static String getBirthByIdCard(String idCard) {
        return idCard.substring(6, 14);
    }

    /**
     * 根据身份编号获取生日年
     *
     * @param idCard 身份编号
     * @return 生日(yyyy)
     */
    public static String getYearByIdCard(String idCard) {
        String year = Short.valueOf(idCard.substring(6, 10))+"";

        return year;
    }

    /**
     * 根据身份编号获取生日月
     *
     * @param idCard
     *            身份编号
     * @return 生日(MM)
     */
    public static String getMonthByIdCard(String idCard) {
        String mon = Short.valueOf(idCard.substring(10, 12))+"";
        if (mon.length()==1){
            mon = "0"+mon;
        }
        return mon;
    }

    /**
     * 根据身份编号获取生日天
     *
     * @param idCard
     *            身份编号
     * @return 生日(dd)
     */
    public static String getDateByIdCard(String idCard) {
        String theDate = Short.valueOf(idCard.substring(12, 14))+"";
        if (theDate.length()==1){
            theDate = "0"+theDate;
        }
        return theDate;
    }

    /**
     * 根据身份编号获取性别
     *
     * @param idCard 身份编号
     * @return 性别(M-男，F-女，N-未知)
     */
    public static String getGenderByIdCard(String idCard) {
        String sGender = "未知";

        String sCardNum = idCard.substring(16, 17);
        if (Integer.parseInt(sCardNum) % 2 != 0) {
            sGender = "1";//男
        } else {
            sGender = "2";//女
        }
        return sGender;
    }

    public static boolean validate(String no)
    {
        // 对身份证号进行长度等简单判断
        if (no == null || no.length() != 18 || !no.matches("\\d{17}[0-9X]"))
        {
            return false;
        }
        // 1-17位相乘因子数组
        int[] factor = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
        // 18位随机码数组
        char[] random = "10X98765432".toCharArray();
        // 计算1-17位与相应因子乘积之和
        int total = 0;
        for (int i = 0; i < 17; i++)
        {
            total += Character.getNumericValue(no.charAt(i)) * factor[i];
        }
        // 判断随机码是否相等
        return random[total % 11] == no.charAt(17);
    }


    public static  void  main(String [] a){
        String idcard="362422198911094039";
        String sex= getGenderByIdCard(idcard);
        System.out.println("性别:" + sex);
        int age= getAgeByIdCard(idcard);
        System.out.println("年龄:" + age);
        String nian=getYearByIdCard(idcard);
        String yue=getMonthByIdCard(idcard);
        String ri=getDateByIdCard(idcard);
        System.out.println(nian + "-" + yue + "-" + ri);

        String sr=getBirthByIdCard(idcard);
        System.out.println("生日:" + sr);

        System.out.println(validate("36242219890814403X"));

    }
}
