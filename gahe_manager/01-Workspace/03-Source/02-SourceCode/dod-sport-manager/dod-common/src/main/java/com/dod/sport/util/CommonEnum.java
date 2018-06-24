package com.dod.sport.util;


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

        enum AuthorityType implements DBData{
            AuthPrivate("1"),AuthPublic("2"){
                @Override
                public boolean isRest() {
                    return true;
                }
            };
            private String value;

            private AuthorityType(String value) {
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

    public interface Employee{
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
            //教练职称:1:初级教练，2:中级教练，3,高级教练
            job_primary("1"),job_intermediate("2"), job_expert("3"){
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
            //雇佣状态,1:签到成功，2:签到失败
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
        enum SignStatus implements Administeration {
            //雇佣状态,1:按时上班，2:迟到;3:早退
            work_ontime("1"), work_last("2"),work_leave ("3") {
                @Override
                public boolean isRest() {
                    return true;
                }
            };
            private String value;

            private SignStatus(String value) {
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
    public interface Membecardmodify {
        enum modifyType implements Administeration {
           // 1.过户,2.停卡,3.换卡,4,赠送,5校准,6.启用,7.开卡
            modify_transfer("1"),
            modify_stop("2"),
            modify_change("3"),
            modify_give("4"),
            modify_cali("5"),
            modify_start("6"),
            modify_open("7"){
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
    }

    //系统业务枚举
    public interface SystemOperation{
        //验证码类别1，注册；2，修改密码；3，修改绑定手机号
        enum verifyIdent implements SystemOperation{
            verifyRegister("1"),verifyPassword("2"),modifyPhoneNum("3"){
                @Override
                public boolean isRest(){return true;
                }
            };
            private String value;

            private verifyIdent(String value) {this.value = value;}

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
            //4004,连接超时
            sys_ok("0"),
            sys_err_noauth("4001"),
            sys_err_paramerror("4002"),
            sys_err_exception("4003"),
            sys_err_sessioninvalid("4004"){
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
        enum ManagerCode implements ReturnCode {
            //消息代码说明:
            //5001,验证码错误;
            //5002,未查到数据;
            //5003,密码不匹配;
            //5004,密码错误;
            //5005,该用户已存在;
            //5006,电话号码输入错误;
            //5008,用户不存在
            //5009,用户已注册
            //5010,查询数据出错
            //5011，上传文件为空
            //5012，文件上传失败
            manager_err_identifyingcode("5001"),
            manager_err_nodata("5002"),
            manager_err_passwordnotmatch("5003"),
            manager_err_passworderror("5004"),
            manager_err_employeeexists("5005"),
            manager_err_phonenumerror("5006"),
            manager_err_employeenotexists("5008"),
            manager_err_registered("5009"),
            manager_err_getdataerror("5010"),
            manager_err_nouploadfile("5011"),
            manager_err_uploadfileerror("5012"),
            manager_err_userroleinused("5013"),
            manager_err_functioninused("5014"),
            manager_err_idorpwdisblank("5015"),
            manager_err_memberexists("5016"),
            manager_err_businessuserphonenexists("5017"),
            manager_err_memberphoneexists("5018"),
            manager_err_manageruserphoneexits("5019"){
                @Override
                public boolean isRest(){return true;
                }
            };
            private String value;

            private ManagerCode(String value) {this.value = value;}

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
            sys_err_exception("操作失败"),
            sys_err_sessioninvalid("您太久没有操作了，请重新登陆！"){
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
        enum ManagerMessage implements ReturnMessage{
            manager_err_identifyingcode("验证码错误"),
            manager_err_nodata("未查到数据"),
            manager_err_passwordnotmatch("账号或密码错误"),
            manager_err_passworderror("密码错误"),
            manager_err_getdataerror("查询数据出错"),
            manager_err_employeeexists("员工已存在"),
            manager_err_phonenumerror("电话号码出错"),
            manager_err_employeenotexists("员工不存在"),
            manager_err_registered("已注册"),
            manager_err_nouploadfile("上传文件为空"),
            manager_err_uploadfileerror("文件上传失败"),
            manager_err_userroleinused("角色已使用"),
            manager_err_functioninused("该功能存在关联项"),
            manager_err_idorpwdisblank("用户id或密码不能为空"),
            manager_err_memberexists("会员已存在"),
            manager_err_businessuserphonenexists("该员工手机号码已存在"),
            manager_err_memberphoneexists("该会员手机号码已存在"),
            manager_err_manageruserphoneexits("该用户手机号已存在"){
                @Override
                public boolean isRest(){return true;
                }
            };
            private String value;

            private ManagerMessage(String value) {this.value = value;}

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
}
