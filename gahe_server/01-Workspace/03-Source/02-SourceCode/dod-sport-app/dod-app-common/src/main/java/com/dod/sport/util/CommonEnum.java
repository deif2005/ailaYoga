package com.dod.sport.util;


import org.apache.tools.ant.types.resources.comparators.Exists;

/**
 * Created by defi on 2016/11/15.
 */
public enum CommonEnum {
    test(DBData.DataStatus.class);

    private DBData[] values;

    private CommonEnum(Class<? extends DBData> kind) {
        values = kind.getEnumConstants();
    }

    public interface DBData {//数据相关状态
        enum DataStatus implements DBData {
            normal("1"), delete("2"), other("3"){
                @Override
                public boolean isRest() {
                    return true;
                }
            };
            private String value;

            private DataStatus(String value) {
                this.value = value;
            }

            public String getValue() {
                return value;
            }

            public boolean isRest() {
                return false;
            }
        }
    }

    /***********************业务逻辑枚举类型定义***************/
    public interface Billinfo{
        enum billType implements Billinfo{
            //单据类型:1.请假单,2.入职单,3.离职单,4调岗单,5.转正单,6.报销单;
            vacationBill("1"),entryBill("6"),leaveBill("3"),transferBill("4"),regularBill("5"),reimbursementBill("6"){
                @Override
                public boolean isRest(){return true;}
            };

            private String value;

            private billType(String value) {
                this.value = value;
            }

            public String getValue() {
                return value;
            }

            public Integer getIntegerVal(){
                return Integer.parseInt(value);
            }

            public boolean isRest() {
                return false;
            }
        }
    }

    public interface MemberCard{
        enum membcardType implements MemberCard{
            //会员卡类型1;次卡;2.期限卡
            type_times("1"),type_days("2"){
                @Override
                public boolean isRest(){return true;}
            };

            private String value;

            private membcardType(String value) {
                this.value = value;
            }

            public String getValue() {
                return value;
            }

            public Integer getIntegerVal(){
                return Integer.parseInt(value);
            }

            public boolean isRest() {
                return false;
            }
        }
    }
    public interface Membercardrelation{
        enum flageType implements Membercardrelation{
            //会员卡类型1;未换卡;2.已换卡
            notchang("1"),changed("2"){
                @Override
                public boolean isRest(){return true;}
            };

            private String value;

            private flageType(String value) {
                this.value = value;
            }

            public String getValue() {
                return value;
            }

            public Integer getIntegerVal(){
                return Integer.parseInt(value);
            }

            public boolean isRest() {
                return false;
            }
        }
        enum cardStatus implements Membercardrelation {
            //会员卡状态状态:1.正常,2.停卡,3.删除,4.请假,5.已过期,6.未激活
            cardStatus_normal("1"), cardStatus_stop("2"), cardStatus_del("3"), cardStatus_leave("4"),
            cardStatus_outofdate("5"),cardStatus_nonactivated("6"){
                @Override
                public boolean isRest() {
                    return true;
                }
            };

            private String value;

            private cardStatus(String value) {
                this.value = value;
            }

            public String getValue() {
                return value;
            }

            public Integer getIntegerVal() {
                return Integer.parseInt(value);
            }

            public boolean isRest() {
                return false;
            }
        }
        enum validityType implements Membercardrelation {
            //次卡是否开启有效期1;是;2.否,
            validityType_yes("1"), validityType_no("2"){
                @Override
                public boolean isRest() {
                    return true;
                }
            };

            private String value;

            private validityType(String value) {
                this.value = value;
            }

            public String getValue() {
                return value;
            }

            public Integer getIntegerVal() {
                return Integer.parseInt(value);
            }

            public boolean isRest() {
                return false;
            }
        }
    }

    public interface EmployeeSignSet{
        enum schedulingType implements EmployeeSignSet{
            //排班类型1:早班;2:晚班
            morning_shift("1"),night_shift("2"){
                @Override
                public boolean isRest(){return true;}
            };

            private String value;

            private schedulingType(String value) {
                this.value = value;
            }

            public String getValue() {
                return value;
            }

            public Integer getIntegerVal(){
                return Integer.parseInt(value);
            }

            public boolean isRest() {
                return false;
            }
        }
    }
    public interface Employee{
        enum registerType implements Employee{
            //雇佣状态,1未注册，2.已注册
            noregister("1"), registered("2"){
                @Override
                public boolean isRest(){return true;}
            };

            private String value;

            private registerType(String value) {
                this.value = value;
            }

            public String getValue() {
                return value;
            }

            public Integer getIntegerVal(){
                return Integer.parseInt(value);
            }

            public boolean isRest() {
                return false;
            }
        }

        enum empType implements Employee{
            //雇佣状态,1正式员工，2非正式员工,3解聘员工,4其它
            normal("1"), informal("2"), fired("3"), other("4"){
                @Override
                public boolean isRest(){return true;}
            };

            private String value;

            private empType(String value) {
                this.value = value;
            }

            public String getValue() {
                return value;
            }

            public Integer getIntegerVal(){
                return Integer.parseInt(value);
            }

            public boolean isRest() {
                return false;
            }
        }

        enum empRela implements Employee{
            //聘用关系：1全职，2兼职
            qz("1"), jz("2"){
                @Override
                public boolean isRest(){return true;}
            };
            private String value;
            private empRela(String value) {
                this.value = value;
            }
            public String getValue() {
                return value;
            }
            public Integer getIntegerVal(){
                return Integer.parseInt(value);
            }
            public boolean isRest() {
                return false;
            }
        }
        enum empStatus implements Employee{
            //员工状态:1:未激活，2:已激活，3,停用或其它
            noact("1"),act("2"), other("3"){
                @Override
                public boolean isRest(){return true;}
            };

            private String value;

            private empStatus(String value) {
                this.value = value;
            }

            public String getValue() {
                return value;
            }

            public Integer getIntegerVal(){
                return Integer.parseInt(value);
            }

            public boolean isRest() {
                return false;
            }
        }
        enum jobTitle implements Employee{
            //教练职称:1:初级教练，2:中级教练，3,高级教练,9.无职称
            job_primary("1"),job_intermediate("2"), job_expert("3"),job_no("9"){
                @Override
                public boolean isRest(){return true;}
            };

            private String value;

            private jobTitle(String value) {
                this.value = value;
            }

            public String getValue() {
                return value;
            }

            public Integer getIntegerVal(){
                return Integer.parseInt(value);
            }

            public boolean isRest() {
                return false;
            }
        }

    }
    public interface Administeration {
        enum EmpSign implements Administeration {
            //签到状态,1:签到成功，2:签到失败
            sign_ok("1"), sign_false("2"),other("4") {
                @Override
                public boolean isRest() {
                    return true;
                }
            };
            private String value;

            private EmpSign(String value) {
                this.value = value;
            }

            public String getValue() {
                return value;
            }

            public Integer getIntegerVal() {
                return Integer.parseInt(value);
            }

            public boolean isRest() {
                return false;
            }
        }
        enum Sign_status implements Administeration {
            //雇佣状态,1:按时上班，2:迟到;3:早退
            work_ontime("1"), work_last("2"),work_leave ("3") {
                @Override
                public boolean isRest() {
                    return true;
                }
            };
            private String value;

            private Sign_status(String value) {
                this.value = value;
            }

            public String getValue() {
                return value;
            }

            public Integer getIntegerVal() {
                return Integer.parseInt(value);
            }

            public boolean isRest() {
                return false;
            }
        }
    }
    public interface MembercardRecharge {
        enum status implements MembercardRecharge {
            //1:未完成;2:已完成:3:删除/其他
            status_unfinished("1"),
            status_finished("2"),
            status_del("3"){
                @Override
                public boolean isRest() {
                    return true;
                }
            };
            private String value;

            private status(String value) {
                this.value = value;
            }

            public String getValue() {
                return value;
            }

            public Integer getIntegerVal() {
                return Integer.parseInt(value);
            }

            public boolean isRest() {
                return false;
            }
        }
    }
    public interface Common {
        enum platformId implements Common {
            //1后台管理端，2商家端，3客户端
            service_sid("1"),
            business_side("2"),
            client_side("3"){
                @Override
                public boolean isRest() {
                    return true;
                }
            };
            private String value;

            private platformId(String value) {
                this.value = value;
            }

            public String getValue() {
                return value;
            }

            public Integer getIntegerVal() {
                return Integer.parseInt(value);
            }

            public boolean isRest() {
                return false;
            }
        }
        enum operationId implements Common {
            // businessType 1:注册;2:忘记密码;3:新增平台用户
            register("1"),
            lost_password("2"),
            add_user("3"){
                @Override
                public boolean isRest() {
                    return true;
                }
            };
            private String value;

            private operationId(String value) {
                this.value = value;
            }

            public String getValue() {
                return value;
            }

            public Integer getIntegerVal() {
                return Integer.parseInt(value);
            }

            public boolean isRest() {
                return false;
            }
        }
    }
        public interface Order {
        enum orderStatus implements Order {
            //订单状态:1未付款,2已付款,3取消交易
            orderStatus_unpaid("1"),
            orderStatus_prepaid("2"),
            orderStatus_cancel("3"){
                @Override
                public boolean isRest() {
                    return true;
                }
            };
            private String value;

            private orderStatus(String value) {
                this.value = value;
            }

            public String getValue() {
                return value;
            }

            public Integer getIntegerVal() {
                return Integer.parseInt(value);
            }

            public boolean isRest() {
                return false;
            }
        }
    }
    public interface SaleDetail {
        enum saleType implements SaleDetail {
            //销售类型：1:商品;2:店内服务;3:增值服务,9:其它
            sale_product("1"),
            sale_store_service("2"),
            sale_added_service("3"),
            sale_other("4"){
                @Override
                public boolean isRest() {
                    return true;
                }
            };
            private String value;

            private saleType(String value) {
                this.value = value;
            }

            public String getValue() {
                return value;
            }

            public Integer getIntegerVal() {
                return Integer.parseInt(value);
            }

            public boolean isRest() {
                return false;
            }
        }
    }

    public interface Membercardmodify {
        enum modifyType implements Membercardmodify {
           // 1.过户,2.停卡,3.换卡,4,赠送,5校准,6.启用,7.开卡,8删除,9请假,10充值
            modify_transfer("1"),
            modify_stop("2"),
            modify_change("3"),
            modify_give("4"),
            modify_cali("5"),
            modify_start("6"),
            modify_open("7"),
            modify_del("8"),
            modify_leave("9"),
            modify_recharge("10"){
                @Override
                public boolean isRest() {
                    return true;
                }
            };
            private String value;

            private modifyType(String value) {
                this.value = value;
            }

            public String getValue() {
                return value;
            }

            public Integer getIntegerVal() {
                return Integer.parseInt(value);
            }

            public boolean isRest() {
                return false;
            }
        }
        enum status implements Membercardmodify {
            //1:未完成;2:已完成:3:删除/其他
            status_unfinished("1"),
            status_finished("2"),
            status_del("3"){
                @Override
                public boolean isRest() {
                    return true;
                }
            };
            private String value;

            private status(String value) {
                this.value = value;
            }

            public String getValue() {
                return value;
            }

            public Integer getIntegerVal() {
                return Integer.parseInt(value);
            }

            public boolean isRest() {
                return false;
            }
        }
    }

    //课程相关枚举类型
    public interface Course {
        enum ClassStatus implements Course {
            //课程状态：1未开课，2已开课，3已取消
            classnostarted("1"),classstarted("2"),classcancel("3"){
                @Override
                public boolean isRest(){return true;
                }
            };
            private String value;

            private ClassStatus(String value) {this.value = value;}

            public String getValue() {return value;}

            public Integer getIntegerVal(){return Integer.parseInt(value);}

            public boolean isRest(){return false;}
        }
        //签到状态：1未签到，2店长签到，3老师签到
        enum SignStatus implements Course {
            classnosign("1"),classmanagersign("2"),classteachersign("3"){
                @Override
                public boolean isRest(){return true;
                }
            };
            private String value;

            private SignStatus(String value) {this.value = value;}

            public String getValue() {return value;}

            public Integer getIntegerVal(){return Integer.parseInt(value);}

            public boolean isRest(){return false;}
        }
        enum OrderStatus implements Course {
            reserved("1"),signed("2"),breakorder("3"),cancel("4"){
                @Override
                public boolean isRest(){return true;
                }
            };
            private String value;

            private OrderStatus(String value) {this.value = value;}

            public String getValue() {return value;}

            public Integer getIntegerVal(){return Integer.parseInt(value);}

            public boolean isRest(){return false;}
        }
        enum CourseMeans implements Course {
            personal("1"),common("2"),mix("3"){
                @Override
                public boolean isRest(){return true;
                }
            };
            private String value;

            private CourseMeans(String value) {this.value = value;}

            public String getValue() {return value;}

            public Integer getIntegerVal(){return Integer.parseInt(value);}

            public boolean isRest(){return false;}
        }
    }


    public interface ReturnCode{
        /***********************系统级code枚举类型定义****************/
        enum SystemCode implements ReturnCode {
            //消息代码说明:
            //0,操作成功;
            //4001,您没有该项操作的权利;
            //4002,操作参数错误，请更正后重试;
            //4003,操作失败，请重试
            sys_ok("0"),
            sys_err_noauth("4001"),
            sys_err_paramerror("4002"),
            sys_err_exception("4003"){
                @Override
                public boolean isRest(){return true;
                }
            };
            private String value;

            private SystemCode(String value) {this.value = value;}

            public String getValue() {return value;}

            public Integer getIntegerVal(){return Integer.parseInt(value);}

            public boolean isRest(){return false;}
        }

        /***********************业务code枚举类型定义*****************/
        enum BusinessCode implements ReturnCode {
            //消息代码说明:
            //5001,验证码错误;
            //5002,未查到数据;
            //5003,密码不匹配;
            //5004,密码错误;
            //5005,该用户已存在;
            //5006,电话号码输入错误;
            //5007,商家编号错误
            //5008,用户不存在
            //5009,用户已注册
            //5010,查询数据出错
            //5011，上传文件为空
            //5012，文件上传失败
            //5013，不在签到范围
            //5014，不在打卡时间范围
            //5015,教室被占用
            //5016,老师该时段已排课
            //5017,预约已满
            //5022,会员卡不存在
            //5023,时间格式错误
            //5024,会员已存在
            //5025,充值阶梯已存在
            //5026,wifi不存在
            //5040,该员工已经是全职
            //5041,会员不存在
            //5045,
            busi_err_identifyingcode("5001"),
            busi_err_nodata("5002"),
            busi_err_passwordnotmatch("5003"),
            busi_err_passworderror("5004"),
            busi_err_employeeexists("5005"),
            busi_err_phonenumerror("5006"),
            busi_err_businessiderror("5007"),
            busi_err_employeenotexists("5008"),
            busi_err_registered("5009"),
            busi_err_getdataerror("5010"),
            busi_err_nouploadfile("5011"),
            busi_err_uploadfileerror("5012"),
            busi_err_notinsignscope("5013"),
            busi_err_notimetosign("5014"),
            busi_err_classroombusy("5015"),
            busi_err_employeebusy("5016"),
            busi_err_noremainorder("5017"),
            busi_err_cannotorder("5018"),
            busi_err_cardisover("5019"),
            busi_err_cardisnotvalid("5020"),
            busi_err_courseexistsorder("5021"),
            busi_err_membercardnotexists("5022"),
            busi_err_datefomaterror("5023"),
            busi_err_memberexists("5024"),
            busi_err_rechargegradexists("5025"),
            busi_err_storewifinotexists("5026"),
            busi_err_notmanagerauth("5027"),
            busi_err_teachernotmatch("5028"),
            busi_err_signcompleted("5029"),
            busi_err_existsprivatecourseplan("5030"),
            busi_err_coursecanceled("5031"),
            busi_err_coursenotenable("5032"),
            busi_err_cardtimesisinvalid("5033"),
            busi_err_cardstatusisstop("5034"),
            busi_err_cannotorderexperiencecourse("5035"),
            busi_err_privatecoursecancle("5036"),
            busi_err_employeenotregister("5037"),
            busi_err_employeesigned("5038"),
            busi_err_employeesignedquit("5039"),
            busi_err_employeeqzexists("5040"),
            busi_err_membernotexists("5041"),
            busi_err_notcancelcourseorder("5042"),
            busi_err_membercourseorderexists("5043"),
            busi_err_parametererror("5044"),
            busi_err_clientuserxists("5045"),
            busi_err_clientusernotxists("5046"),
            busi_err_bill("5047"){
                @Override
                public boolean isRest(){return true;
                }
            };
            private String value;

            private BusinessCode(String value) {this.value = value;}

            public String getValue() {return value;}

            public Integer getIntegerVal(){return Integer.parseInt(value);}

            public boolean isRest(){return false;}
        }

    }

    public interface ReturnMessage {
        /***********************系统msg枚举类型定义*****************/
        enum SystemMessage implements ReturnMessage{
            sys_ok("操作成功"),
            sys_err_noauth("您没有该项操作的权利"),
            sys_err_paramerror("操作参数错误，请更正后重试"),
            sys_err_exception("操作失败"){
                @Override
                public boolean isRest(){return true;
                }
            };
            private String value;

            private SystemMessage(String value) {this.value = value;}

            public String getValue() {return value;}

            public boolean isRest(){return false;}
        }

        /***********************业务msg枚举类型定义*****************/
        enum BusiMessage implements ReturnMessage{
            /**
             *
             busi_err_phonenumerror("5006"),
             busi_err_businessiderror("5007"),
             busi_err_noemployee("5008"),
             busi_err_registered("5009"),
             */
            busi_err_identifyingcode("验证码错误"),
            busi_err_nodata("未查到数据"),
            busi_err_passwordnotmatch("密码不匹配"),
            busi_err_passworderror("密码错误"),
            busi_err_getdataerror("查询数据出错"),
            busi_err_employeeexists("员工已存在"),
            busi_err_phonenumerror("电话号码出错"),
            busi_err_businessiderror("商家id错误"),
            busi_err_employeenotexists("员工不存在"),
            busi_err_registered("已注册"),
            busi_err_nouploadfile("上传文件为空"),
            busi_err_uploadfileerror("文件上传失败"),
            busi_err_notinsignscope("不在签到范围"),
            busi_err_notimetosign("不在打卡时间范围"),
            busi_err_classroombusy("教室被占用"),
            busi_err_employeebusy("该教师该时段已排课"),
            busi_err_noremainorder("该课程预约已满"),
            busi_err_cannotorder("该课程已过预约时间"),
            busi_err_coursecanceled("该课程已取消"),
            busi_err_coursenotenable("该课程已禁用"),
            busi_err_cardisover("卡已过有效期"),
            busi_err_cardisnotvalid("卡未激活"),
            busi_err_courseexistsorder("课程已有人预约"),
            busi_err_membercardnotexists("会员卡不存在"),
            busi_err_datefomaterror("时间格式错误"),
            busi_err_memberexists("会员已存在"),
            busi_err_rechargegradexists("充值阶梯已存在"),
            busi_err_storewifinotexists("wifi不存在"),
            busi_err_notmanagerauth("需店长及以上权限"),
            busi_err_teachernotmatch("需当前课程老师"),
            busi_err_signcompleted("签到已完成"),
            busi_err_existsprivatecourseplan("已存在当天的私教排课"),
            busi_err_cardtimesisinvalid("该卡已无剩余次数"),
            busi_err_cardstatusisstop("该卡已经停卡"),
            busi_err_cannotorderexperiencecourse("已申请过体验课，不能再申请"),
            busi_err_privatecoursecancle("该私教时间已取消"),
            busi_err_employeenotregister("该员工未注册"),
            busi_err_employeesigned("已签到"),
            busi_err_employeesignedquit("已签退"),
            busi_err_employeeqzexists("该员工已经是全职状态"),
            busi_err_membernotexists("会员不存在"),
            busi_err_notcancelcourseorder("不允许取消课程预约"),
            busi_err_membercourseorderexists("该会员已预约该课程,不能重复预约"),
            busi_err_parametererror("参数错误"),
            busi_err_clientuserxists("用户已存在"),
            busi_err_clientusernotxists("用户不存在"){
                @Override
                public boolean isRest(){return true;
                }
            };
            private String value;

            private BusiMessage(String value) {this.value = value;}

            public String getValue() {return value;}

            public boolean isRest(){return false;}
        }
    }

    public interface Notice{
        enum noticeType implements Notice{
            //单据类型:1系统消息，2,联盟消息，3,内部消息
            systemNotice("1"), leagueNotice("2"), innerNotice("3"){
                @Override
                public boolean isRest(){return true;}
            };
            private String value;

            private noticeType(String value) {
                this.value = value;
            }

            public String getValue() {
                return value;
            }

            public Integer getIntegerVal(){
                return Integer.parseInt(value);
            }

            public boolean isRest() {
                return false;
            }
        }
    }

    public interface Authority{
        enum RoleCode implements Authority{
            roleBoss("ROLE_BOSS"),
            roleManager("ROLE_MANAGER"),
            roleEmployee("ROLE_EMPLOYEE"),
            roleTeacher("ROLE_TEACHER"){
                @Override
                public boolean isRest(){return true;}
            };
            private String value;

            private RoleCode(String value) {
                this.value = value;
            }

            public String getValue() {
                return value;
            }

            public Integer getIntegerVal(){
                return Integer.parseInt(value);
            }

            public boolean isRest() {
                return false;
            }
        }
    }
}
