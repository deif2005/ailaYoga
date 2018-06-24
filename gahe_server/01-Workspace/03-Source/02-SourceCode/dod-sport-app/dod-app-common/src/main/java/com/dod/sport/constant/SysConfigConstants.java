package com.dod.sport.constant;

/**
 * Created by defi on 2017-08-12.
 * 系统配置,数据格式参数
 */
public class SysConfigConstants {

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


    //员工手机号固定长度
    public static final int EMPLOYEE_PHONENUM_LENGTH = 11;

    //编号固定长度
    public static final int COMMON_SERIAL_LENGTH = 3;

    //会员卡编号固定长度
    public static final int MEMBERCARD_SERIAL_LENGTH = 3;

    //会员卡卡号固定长度
    public static final int MEMBERCARD_NUM_SERIAL_LENGTH = 9;

    //会员卡关系表编号固定长度
    public static final int MEMBERCARD_RELATION_SERIAL_LENGTH = 3;

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

    //单据数据刷新纪录笔数
    public static final int BILL_FLUSH_COUNT = 10;

    //编号固定长度
    public static final int FUNCTION_SERIAL_LENGTH = 4;

    //提前预约分钟数
    public static int BEFORE_MINUTERS_ORDER = 30;

    //平台用户编号固定长度
    public static final int CLIENTUSER_SERIAL_LENGTH = 6;

    //可提前预约天数
    public static final int COURSE_ORDER_LATERDAY = 6;

    //编号固定长度三位
    public static final int COMMON_SERIAL_LENGTH_3 = 3;

    //编号固定长度六位
    public static final int COMMON_SERIAL_LENGTH_6 = 6;
    //编号固定长度六位
    public static final int COMMON_SERIAL_LENGTH_5 = 5;

    //用户消费记录编号固定长度
    public static final int EXPENSE_RECORD_SERIAL_LENGTH_23 = 23;

    //会员取消预约提前30分钟
    public static final int MEMBER_CANCEL_ORDER_TIME_30 = 30;

    //编号固定长度三位
    public static final int COMMON_SERIAL_LENGTH_13 = 13;

    //用户消费记录编号固定长度
    public static final int ORDER_SERIAL_LENGTH_9 = 9;

    //次卡默认给一年的有效期时间
    public static final int TIMESCARD_VALIDITYTIME_YEAR_LENGTH_1 = 1;

    //卡卷变更通知
    public static final String MEMBERCARD_MODIFY_TITLE = "卡卷变更通知";
    //会员卡开卡成功通知内容
    public static final String MEMBERCARD_OPENCARDSUCCEED_ALERT = "会员卡开卡成功通知";
    //会员卡充值成功通知
    public static final String MEMBERCARD_RECHANGESUCCEED_ALERT = "会员卡充值成功通知";
    //会员卡过户通知
    public static final String MEMBERCARD_TRANSFERSUCCEED_ALERT = "会员卡过户通知";
    //会员卡启用通知
    public static final String MEMBERCARD_STARTSUCCEED_ALERT = "会员卡启用通知";
    //会员卡停卡通知
    public static final String MEMBERCARD_STOPCARDSUCCEED_ALERT = "会员卡停卡通知";
    //会员卡删除通知
    public static final String MEMBERCARD_DELCARDSUCCEED_ALERT = "会员卡删除通知";
    //会员卡赠送通知
    public static final String MEMBERCARD_GIVESUCCEED_ALERT = "会员卡赠送通知";
    //会员卡换卡通知
    public static final String MEMBERCARD_CHANGESUCCEED_ALERT = "会员卡换卡通知";
    //会员校准通知
    public static final String MEMBERCARD_CALIBRATIONSUCCEED_ALERT = "会员校准通知";

    //会员卡开卡订单提交通知
    public static final String MEMBERCARD_SUBMITOPENCARDORDER_ALERT = "会员卡开卡订单提交通知";
    //会员卡充值订单提交通知
    public static final String MEMBERCARD_SUBMITRECHANGEORDER_ALERT = "会员卡充值订单提交通知";
    //会员卡换卡订单提交通知
    public static final String MEMBERCARD_SUBMITCHANGESUCCEEDORDER_ALERT = "会员卡换卡订单提交通知";
}
