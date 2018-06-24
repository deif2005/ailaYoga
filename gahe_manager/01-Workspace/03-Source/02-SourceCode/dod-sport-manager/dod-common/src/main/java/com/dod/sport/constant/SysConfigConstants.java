package com.dod.sport.constant;

/**
 * Created by defi on 2017-08-12.
 * 系统配置,数据格式参数
 */
public class SysConfigConstants {

    //系统代号 用户平台:1后台管理端，2商家端，3客户端
    public static final String PLATFORM_ID = "1";

    /**
     * 角色信息
     */
    public static final String USER_ROLE = "USER_ROLE";

    /**
     * 用户会话信息key
     */
    public static final String USER_INFO = "USER_INFO";

    /**
     * 用户菜单信息key
     */
    public static final String USER_MENU = "USER_MENU";

    //单据流水号单据前缀-请假单
    public static final String BILL_FREFIX_VACATION = "QJB";

    //单据流水号单据前缀-调岗单
    public static final String BILL_FREFIX_TRANSFER = "DGB";

    //单据流水号单据前缀-转正单
    public static final String BILL_FREFIX_REGULAR = "ZZB";

    //单据流水号单据前缀-入职职单
    public static final String BILL_FREFIX_ENTRY = "RZB";

    //单据流水号单据前缀-离职单
    public static final String BILL_FREFIX_LEAVE = "LZB";

    //单据流水号单据前缀-报销单
    public static final String BILL_FREFIX_ACCOUNT = "BXD";

    //单据流水号日期前缀
    public static final String BILL_PREFIX_DATE = "yyMMdd";

    //单据流水号固定长度
    public static final int BILL_SERIAL_LENGTH = 6;

    //商家编号固定长度
    public static final int BUSINESS_SERIAL_LENGTH = 6;

    //门店编号固定长度
    public static final int STORE_SERIAL_LENGTH = 3;

    //部门编号固定长度
    public static final int DEPARTMENT_SERIAL_LENGTH = 3;

    //员工编号固定长度
    public static final int EMPLOYEE_SERIAL_LENGTH = 5;

    //员工手机号固定长度
    public static final int EMPLOYEE_PHONENUM_LENGTH = 11;

    //编号固定长度三位
    public static final int COMMON_SERIAL_LENGTH_3 = 3;

    //编号固定长度四位
    public static final int COMMON_SERIAL_LENGTH_4 = 4;

    //编号固定长度六位
    public static final int COMMON_SERIAL_LENGTH_6 = 6;

    //编号固定长度六位
    public static final int COMMON_SERIAL_LENGTH_9 = 9;

    //会员卡编号固定长度
    public static final int MEMBERCARD_SERIAL_LENGTH = 3;

    //会员卡关系表编号固定长度
    public static final int MEMBERCARDTYPE_SERIAL_LENGTH = 6;

    //会员卡类型编号固定长度
    public static final int MEMBER_SERIAL_LENGTH = 9;

    //课程编号固定长度
    public static final int COURSE_SERIAL_LENGTH = 3;

    //课程类型编号固定长度
    public static final int COURSETYPE_SERIAL_LENGTH = 3;

    //系统消息编号固定长度
    public static final int SYSTEMNOTICE_SERIAL_LENGTH = 6;

    //日期时间格式
    public static final String DATE_FORMAT_FORDATETIME = "yyyy-MM-dd HH:mm:ss";

    //日期格式
    public static final String DATE_FORMAT_FORDATE = "yyyy-MM-dd";

    //编号固定长度
    public static final int FUNCTION_SERIAL_LENGTH = 4;

    //会员卡卡号固定长度
    public static final int MEMBERCARD_NUM_SERIAL_LENGTH = 9;
}
