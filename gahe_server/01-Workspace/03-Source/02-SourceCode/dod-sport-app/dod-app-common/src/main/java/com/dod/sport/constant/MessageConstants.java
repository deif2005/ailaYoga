package com.dod.sport.constant;

/**
 * Created by defi on 2017-10-12.
 * 消息内容常量
 */
public class MessageConstants {
    //您预约的xx瑜伽馆的xx老师xx(时间)的课程:xx，因预约人数不足，课程已取消
    public static final String COURSE_ORDER_CANCELED="您预约的%s的%s老师%s的课程:%s,因预约人数" +
            "不足,课程已取消,给您带来的不便敬请谅解";

    //您预约的xx瑜伽馆的xx老师xx(时间)的课程:xx,马上就要开课了
    public static final String COURSE_START_ATTENTION="您预约的%s瑜伽馆的%s老师%s的课程:%s,马上就要开课了";

    //课程评价
    public static final String COURSE_COMMENT="请您为最近所上的课程留下宝贵的意见和建议";

    //刷卡内容拼接
    //开卡
    public static final String OPEN_CARD_RECORD = "【%s】开卡成功";
    //次卡充值
    public static final String RECHARGE_CARD_RECORD_TIMES = "【%s】充值【%s】次";
    //期限卡充值
    public static final String RECHARGE_CARD_RECORD_DAYS = "【%s】充值【%s】月";
    //换卡
    public static final String CHANGE_CARD_RECORD ="【%s】换卡为【%s】";
    //次卡校准
    public static final String CALIBRATION_CARD_RECORD_TIMES = "【%s】由【%s】次校准为【%s】次";
    //期限卡校准
    public static final String CALIBRATION_CARD_RECORD_DAYS = "【%s】由【%s】时间校准有效期时间为【%s】";
    //次卡赠送
    public static final String GIVE_CARD_RECORD_TIMES = "赠送【%s】【%s】次";
    //期限卡赠送
    public static final String GIVE_CARD_RECORD_DAYS = "赠送【%s】【%s】天";
    //停卡
    public static final String STOP_CARD_RECORD ="【%s】已停卡";
    //启用
    public static final String START_CARD_RECORD = "【%s】已启用";
    //过户
    public static final String TRANSFER_CARD_RECORD = "【%s】已过户给【%s】";
    //请假
    public static final String LEAVE_CARD_RECORD = "从【%s】开始请假,请假时长为:【%s】个月";
    //已预约
    public static final String RESERVED_COUSE_RECORD ="预约【%s】老师的【%s】";
    //签到
    public static final String SIGNED_COUSE_RECORD = "签到【%s】老师的【%s】";
    //爽约
    public static final String BREAKORDER_COUSE_RECORD = "爽约【%s】老师的【%s】";
    //取消
    public static final String CANCEL_COUSE_RECORD = "取消【%s】老师的【%s】";

    //短信推送内容
    //新增用户
    public static final String ADD_USER_MODE_ID = "146215";
    //员工注册
    public static final String REGISTER_EMPLOYEE_MODE_ID = "146211";
    //修改密码
    public static final String UPDATE_EMPLOYEE_PASSWORD_MODE_ID = "146143";

    //订单推送内容
    //开卡提交订单推送
    public static final String opencard_submit_order_content = "";

    public static final String membercard_recharge_submit_order_content = "";

    public static final String membercard_change_submit_order_content ="";

}
