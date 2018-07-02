package com.dodsport.request;

/**
 * Created by Administrator on 2017/7/22.
 *
 * 网络请求接口管理类
 */

public class UrlInterfaceManager {


    /*开发接口*/
    public static String HOST = "http://192.168.1.194:8080/api/common/v1/";

//    测试
//    public static String HOSTs = "http://192.168.1.186:8080/api/AdminManage/v1/listVacationBill";//api/AdminManage/v1/listVacationBill


//    徐利
    public static String HOSTS = "http://192.168.1.195:8080/api/userManager/v1/";
    //徐利 提交数据接口
    public static String XLHOST = "http://192.168.1.194:8080/api/AdminManage/v1/";

    //会员卡接口
    public static String XLHUIYUANKA = "http://192.168.1.194:8080/api/memberCard/v1/";
    //会员接口
    public static String XLHUIYUAN = "http://192.168.1.194:8080/api/member/v1/";
    //用户管理
    public static String XLHOSTS = "http://192.168.1.194:8080/api/userManager/v1/";
    //课程接口
    public static String XLGCOURSEHOST = "http://192.168.1.194:8080/api/Course/v1/";
    //会员管理
    public static String XLGCARDRELATIONHOST = "http://192.168.1.194:8080/api/cardRelation/v1/";
    //会员卡阶梯
    public static String XLHUIYUANKAJIAGE = "http://192.168.1.194:8080/api/rechargegrad/v1/";

    //获取商家数据(人事管理)
    public static String XLBUSINESE = "http://192.168.1.194:8080/api/Business/v1/";


    public static String XLCOURSE = "http://192.168.1.194:8080/api/Course/v1/";




    //老大 开发接口
    public static String TESTHOST = "http://192.168.1.186:8080/api/Course/v1/";



    //化成
    //登录 注册接口
    public static String ZHANGHOST = "http://192.168.1.150:8080/api/common/v1/";

//    public static String ZHANGHUACHENG = "http://192.168.1.150:8080/api/AdminManage/v1/";
//    //会员卡接口
//    public static String ZHANGHUACHENG2 = "http://192.168.1.150:8080/api/memberCard/v1/";
//    //用户管理
//    public static String ZHANGHOSTS = "http://192.168.1.150:8080/api/userManager/v1/";
//    //课程接口
//    public static String ZHANGCOURSEHOST = "http://192.168.1.150:8080/api/Course/v1/";
//    //会员管理
//    public static String ZHANGCARDRELATIONHOST = "http://192.168.1.150:8080/api/cardRelation/v1/";



    public static String[] type = {"","事假","病假","丧假","婚假","产假","护理假","休假","其他"};
    public static String[] billType = {"","请假单","报销单","离职单","调岗单","转正单"};


    /**
     * 获取用户Token
     * */
    public static String TOKEN = HOST+"getToken";


    /*==================================================登录注册===================================================*/
    /**
     * 获取验证码
     * */
    public static String UserCode = HOST + "register/getIdentcode";


    /**注册*/
    public static String REGISTER = XLHOSTS + "register/registerEmployee";


    /**获取用户信息    用户注册   */
    public static String USERINFO = XLHOSTS + "getEmployeeInfoByPhoneNum";


    /**用户登录*/
    public static String USERLOGIN = XLHOSTS + "doLogin";


    /**忘记密码---->验证码验证*/
    public static String VERIFYIDENTCODE = XLHOSTS + "verifyIdentCode";

    /**忘记密码*/
    public static String USERFORGETPASSWOED = XLHOSTS + "getEmployeePassword";

    /**获取用户信息(用户切换门店)*/
    public static String GETEMPLOYEE = XLHOSTS + "getEmployeeByPhoneNumAndBusinessIdAndStoreId";




    /*==================================================行政管理===================================================*/
    /**用户签到*/
    public static String PUNCHTHECLOCK = XLHOST + "sign/business_user";


    /**获取员工商家列表*/
    public static String COMPANYLIST = XLHOSTS + "getBusinessListByPhoneNum";


    /**请假单 休假单提交*/
    public static String UPLOADASKFORLEAVE = XLHOST + "addVacationBill";


    /**请假单 休假单历史记录 管理人员查看接口*/
    public static String LISTBILLINFO = XLHOST + "listBillInfo";


    /**请假单 休假单历史记录 普通员工人员查看接口*/
    public static String GETUSERLISTBILLINFO = XLHOST + "getUserBillInfoList";


    /**请假单 休假单详情*/
    public static String GETBILLFATEIL = XLHOST + "getVacationBillDetail";



    /*==================================================人事管理===================================================*/
    /**新增员工信息*/
    public static String ADDEMPLOYEEINFO = XLHOSTS + "addEmployeeInfo";

    /**获取员工信息*/
    public static String GETEMPLOYEEINFO = XLHOST + "getEmployeeInfoByEmployeePhoneNum";

    /**提交转正单据*/
    public static String ADDREGULAREMINFO = XLHOST + "addRegularEmpInfo";

    /**获取转正单详情*/
    public static String GETREGULARBILLDETAIL = XLHOST + "getRegularBillDetail";

    /**提交离职单据*/
    public static String ADDLEAVEEMPINFO = XLHOST + "addLeaveEmpInfo";

    /**获取离职单据详情*/
    public static String GETLEAVEBILLDETAIL = XLHOST + "getLeaveBillDetail";

    /**提交调岗单据详情*/
    public static String ADDTRANSFEREMPINFO = XLHOST + "addTransferEmpInfo";

    /**获取调岗单据详情*/
    public static String GETTRANSFEREMPINFO = XLHOST + "getTransferBillDetail";

    /**入职单中获取商家部门列表*/
    public static String LISTBUSIDEPARTMENTINFO = XLBUSINESE + "listBusiDepartmentInfo";

    /**入职单页面 获取商家职位列表*/
    public static String LISTBUSIPOSITIONINFO = XLHOST + "listBusiPositionInfo";


    /*==================================================会员卡管理===================================================*/
    /**添加会员卡类型*/
    public static String ADDMEMBERCARDTYPE = XLHUIYUANKA + "addMemberCardType";

    /**获取会员卡类型列表*/
    public static String LISTMEMBERCARDTYPE = XLHUIYUANKA + "listMemberCardType";

    /**获取会员卡类型数据*/
    public static String GETMEMBERCARDTYPE = XLHUIYUANKA + "getMemberCardType";

    /**新添加会员卡数据*/
    public static String ADDMEMBERCARD = XLHUIYUANKA + "addMemberCard";

    /**获取会员卡数据列表*/
    public static String LISTMEMBERCARD = XLHUIYUANKA + "listMemberCard";

    /**获取会员卡数据*/
    public static String GETLISTMEMBERCARD = XLHUIYUANKA + "getMemberCard";

    /**根据id删除会员卡*/
    public static String DELMEMBERCARDBYID = XLHUIYUANKA + "delMembercardById";

    /**会员卡启用*/
    public static String ADDCOURSEANDCARD = TESTHOST +"addCourseAndCard";

    /**会员卡编辑*/
    public static String EDITCOURSEANDCARD = TESTHOST +"editCourseAndCard";

    /**编辑会员卡信息*/
    public static String UPDATEMEMBERCARD = XLHUIYUANKA +"updateMembercard";

    /**添加会员卡的价格阶梯*/
    public static String ADDRECHARGEGRADLIST = XLHUIYUANKAJIAGE +"addRechargegrad";

    /**获取会员卡的价格阶梯*/
    public static String GETRECHARGEGRADLIST = XLHUIYUANKAJIAGE +"getRechargegradList";

    /**获取商家门店信息以及会员卡关联门店信息列表*/
    public static String QUERYSTOREINFOLIST = XLHUIYUANKA +"queryStoreInfoList";

    /**保存 会员卡关联门店*/
    public static String ADDSTORERELATIONLIST = XLHUIYUANKA +"addMembercardStoreRelationList";




    /*==================================================会员管理===================================================*/

    /**获取门店中会员列表*/
    public static String QUERYMEMBERLISTBYSTOREID = XLHUIYUAN +"queryMemberListByStoreId";

    /**不存在的会员开卡*/
    public static String OPENMEMBERCARD = XLGCARDRELATIONHOST +"openMemberCard";

    /**查询门店中开卡信息(模糊查询 门店会员卡列表)*/
    public static String QUERYOPENCARDINFO = XLGCARDRELATIONHOST +"queryOpencardInfo";



     /*==================================================课程管理===================================================*/
    /**获取课程数据列表*/
    public static String LISTCOURSEINFO = XLGCOURSEHOST + "listCourseInfo";

    /**获取课程数据*/
    public static String GETCOURSEINFO = XLGCOURSEHOST + "getCourseInfo";

    /**添加课程数据*/
    public static String ADDCOURSE = XLGCOURSEHOST + "addCourse";

    /**获取课程类型列表*/
    public static String LISTCOURSETYPE = XLGCOURSEHOST + "listCourseType";

    /**获取课程类型*/
    public static String GETCOURSETYPE = XLGCOURSEHOST + "getCourseType";

    /**添加课程类型*/
    public static String ADDCOURSETYPE = XLGCOURSEHOST + "addCourseType";

    /**获取课程数据列表*/
    public static String QUERMENBERCARDRELATION = XLGCARDRELATIONHOST + "queryMembercardrelationListByMemberId";




    /*==================================================财务管理===================================================*/
    /**提交报销单*/
    public static String ADDECPENSEACCOUNTBILL = XLHOST +"addExpenseAccountBill";


    /*==================================================商家管理===================================================*/
    /**添加相册*/
    public static String ADDALBUMINFO = XLHOST +"addAlbumInfo";

    /**获取相册信息*/
    public static String GETALBUMLIST = XLHOST +"getAlbumList";

    /**修改相册信息*/
    public static String UPDATEALBUMBYID = XLHOST +"updateAlbumById";

    /**删除相册信息*/
    public static String DELETEALBUMBYID = XLHOST +"deleteAlbumById";

    /**添加相片*/
    public static String ADDPHOTOINFO = XLHOST +"addPhotoInfo";

    /**删除相片*/
    public static String DELETEPHOTOBYID = XLHOST +"deletePhotoById";
}

