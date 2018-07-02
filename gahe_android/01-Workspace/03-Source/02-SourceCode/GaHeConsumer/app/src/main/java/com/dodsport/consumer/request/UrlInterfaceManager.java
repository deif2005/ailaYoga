package com.dodsport.consumer.request;

/**
 * url  接口管理类
 */

public class UrlInterfaceManager {

//    //上线接口
//    private static final String HOST = "http://webapi.dayanghot.com/Interface/mobile_ajax.asmx/";
    // 测试接口
//    private static final String  HOST ="http://webapi2.dayanghot.com/Interface/mobile_ajax.asmx/";

    /**徐利**/
    public static String URL = "http://192.168.1.199:8080";

    /**开发接口**/
    public static String HOST = "/api/common/v1/";

    /**获取用户信息*/
    public static String USERINFO = "/api/ClientUser/v1/";

    /**获取团课信息*/
    public static String COURSEINFO = "/api/clientCourse/v1/";

    /**获取用户订单* --微信支付*/
    public static String MODIFY = "/api/modify/v1/";



    public static String [] type ={"","私教","团课","私教&团课"};
    public static String [] status = {"","已预约","已签到","已爽约","已取消"};

    /************************************************俱乐部****************************************/


    /**
     * 用户登录
     * */
    public static String LOGIN = USERINFO+"loginClientUserInfo";

    /**
     * 获取验证码
     * */
    public static String CODE = HOST+"register/getIdentcode";

    /**
     * 校对验证码
     * */
    public static String PROOFREADCODE = USERINFO+"ClientUserVerifyIdentCode";

    /**
     * 找回密码
     * */
    public static String INTERCALATEPASSWORD = USERINFO+"updateClientUserPassword";

    /**
     * 客户端用户注册
     * */
    public static String REGISTERCLIENTUSER = USERINFO+"registerClientUser";


    /**
     * 根据电话获取平台用户信息
     * */
    public static String GETCLIENTUSERBYPHONE = USERINFO+"getClientUserByPhone";

    /**
     * 按日期获取约课课程列表
     * */
    public static String GETCOURSELIST = COURSEINFO+"listClientCourseOrder";

    /**
     * 获取可约团课老师列表
     * */
    public static String LISTCOURSEORDERTEACHER = COURSEINFO+"listCourseOrderTeacher";

    /**
     * 团课约课课程列表-按老师显示
     * */
    public static String LISTCOURSEORDERBYTEACHER = COURSEINFO+"listCourseOrderByTeacher";

    /**
     * 获取团课预约课程详细信息
     * */
    public static String GETCOURSEORDERINFO = COURSEINFO+"getCourseOrderInfo";

    /**
     * 团课约课
     * */
    public static String ORDERPUBLICCOURSE = COURSEINFO+"OrderPublicCourse";

    /**
     * 取消预约
     * */
    public static String CANCELCOURSEORDER = COURSEINFO+"cancelCourseOrder";


    /**
     * 获取会员已预约私教信息
     * */
    public static String LISTPRIVATEORDERRECORDBYID = COURSEINFO+"listPrivateOrderRecordById";

    /**
     * 获取可预约私教老师列表
     * */
    public static String LPCPTEACHER = COURSEINFO+"listPrevateCoursePlanTeacher";

    /**
     * 获取老师私教课程列表
     * */
    public static String LISTPREVATECOURSE = COURSEINFO+"listPrevateCourse";

    /**
     * 获取私教预约时间
     * */
    public static String LISTDATETIMEBYEMPID = COURSEINFO+"listPrivateOrderDateTime";

    /**
     * 获取可以预约该私教课程的会员卡列表
     * */
    public static String LISTPREVATECOURSEMEMBERCARD = COURSEINFO+"listPrevateCourseMembercard";

    /**
     * 私教约课
     * */
    public static String ORDERPREVATECOURSE = COURSEINFO+"OrderPrevateCourse";

    /**
     * 预约体验课
     * */
    public static String GETEXPERIENCECOURSEORDERINFO = COURSEINFO+"getExperienceCourseOrderInfo";

    /**
     * 获取体验课详细信息
     * */
    public static String ORDEREXPERIENCECOURSE = COURSEINFO+"orderExperienceCourse";



    /***********************************************消息模块***********************************************************/





    /***********************************************我的模块***********************************************************/

    /**
     * 获取用户详情
     * */
    public static String GETCLIENTUSERINFO = USERINFO+"getClientUserInfo";

    /**
     * 更新平台用户信息
     * */
    public static String UPDETECLIENTUSER = USERINFO+"updateClientUser";

    /**
     * 获取平台用户所有预约记录
     * */
    public static String GETMEMBERALLORDER = COURSEINFO+"getMemberAllOrderByPhoneNum";

    /**
     * 根据预约状态来查询记录
     * */
    public static String GETMEMBERALLORDERSTATUS = COURSEINFO+"getMemberAllOrderByOrderStatus";

    /**
     * 根据电话获取平台用户所有商家所有门店列表
     * */
    public static String LISTCLIENUSERBUSINESS = USERINFO+"listClientUserBusiness";

    /**
     * 获取用户订单列表
     * */
    public static String LISTMEMBERORDERINFO = MODIFY+"listMemberOrderInfo";
}
