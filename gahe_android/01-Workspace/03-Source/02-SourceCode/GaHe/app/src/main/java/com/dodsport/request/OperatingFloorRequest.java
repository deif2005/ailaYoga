package com.dodsport.request;

import android.text.TextUtils;
import android.util.Log;

import com.dodsport.GaHeApplication;
import com.dodsport.utils.SPUtils;
import com.yanzhenjie.nohttp.BasicBinary;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/1.
 *
 * 工作台接口管理
 */

public class OperatingFloorRequest {

    private static String Token = SPUtils.getToken(GaHeApplication.getmContext());

    /**
     * 工作台 商家列表请求
     * token 用户唯一标识
     * phoneNum 用户账号
     * */
    public static void getCompanyList(String phoneNum,OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.COMPANYLIST;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);

        if (request!=null){
            request.add("token",Token);
            request.add("phoneNum",phoneNum);
            RequestMeansManager.request(0, request, onResponseListener);
        }

    }


    /**
     * 提交请假单 休假单
     * token 用户唯一标识
     * phoneNum 用户账号
     * */
    public static void uploadAskForLeave(String businessId, String storeId, Integer vacationType, String employeeId, String startTime
            ,String endTime, float duration, String description, String approver,String startDay,String endDay ,OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.UPLOADASKFORLEAVE;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        if (request!=null){
            request.add("token",Token);
            request.add("businessId",businessId);
            request.add("storeId",storeId);
            request.add("vacationType",vacationType);
            request.add("employeeId",employeeId);
            request.add("startTime",startTime);
            request.add("endTime",endTime);
            request.add("duration",duration);
            request.add("description",description);
            request.add("approver",approver);
            request.add("startDay",startDay);
            request.add("endDay",endDay);
            RequestMeansManager.request(0, request, onResponseListener);
        }
    }



    /**
     *  管理人员获取请假、休假单记录
     * token 用户唯一标识
     * storeId 商家Id
     * billType 单据类型
     * page   刷新页数
     * id  用户ID
     * */
    public static void getListBillInfo(String storeId,Integer billType,Integer page,String id,String approveStatus,OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.LISTBILLINFO;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        if (request!=null){
            request.add("token",Token);
            request.add("storeId",storeId);
            request.add("billType",billType);
            request.add("page",page);

            if (!approveStatus.equals("0")){
                request.add("approveStatus",approveStatus);
            }
//            if (!TextUtils.isEmpty(id))
            request.add("id",id);
            RequestMeansManager.request(0, request, onResponseListener);
        }
    }



    /**
     *  普通员工获取自己请假、休假单记录
     * token 用户唯一标识
     * storeId 商家Id
     * billType 单据类型
     * page   刷新页数
     * id  用户ID
     * */
    public static void getUserListBillInfo(String storeId,Integer billType,Integer page,String employeeId,String approveStatus,OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.GETUSERLISTBILLINFO;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        if (request!=null){
            request.add("token",Token);
            request.add("storeId",storeId);
            request.add("billType",billType);
            request.add("page",page);

            if (!approveStatus.equals("0")){
                request.add("approveStatus",approveStatus);
            }
//            if (!TextUtils.isEmpty(id))
            request.add("employeeId",employeeId);
            RequestMeansManager.request(0, request, onResponseListener);
        }
    }




    /**
     *  获取请假、休假单详情信息
     * token 用户唯一标识
     * id 商家Id
     * */
    public static void getBillsDetail(String id,OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.GETBILLFATEIL;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        if (request!=null){
            request.add("token",Token);
            request.add("id",id);
            RequestMeansManager.request(0, request, onResponseListener);
        }
    }



    /**
     *  新增员工信息
     * token 用户唯一标识
     * employeeName 员工姓名
     * phoneNum 员工电话
     * positionId   员工职位Id
     * empRela  员工角色(全职、兼职)
     * sex  员工性别
     * businessId   商家ID
     * storeId 门店Id
     * creator  创建人 用户ID
     * idCard 员工证件号
     * depId    是否 教练
     * selfIntroduction 个人介绍
     * */
    public static void addEmployeeInfo1(String employeeName,String phoneNum,String idCard,String positionId,Integer empRela,Integer sex,
                                       String businessId, String depId,String creator,String storeId,Integer jobTitle,Integer coachExists,
                                       String selfIntroduction,OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.ADDEMPLOYEEINFO;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);

        if (request!=null){
            request.add("token",Token);
            request.add("employeeName",employeeName);
            request.add("phoneNum",phoneNum);
            request.add("positionId",positionId);
            request.add("empRela",empRela);
            request.add("sex",sex);
            request.add("businessId",businessId);
            request.add("creator",creator);
            request.add("idCard",idCard);
            request.add("depId",depId);
            request.add("storeId",storeId);
            if (coachExists == 1){
                request.add("jobTitle",jobTitle);
            }
            if (!TextUtils.isEmpty(selfIntroduction)){
                request.add("selfIntroduction",selfIntroduction);
            }
            request.add("coachExists",coachExists);
            RequestMeansManager.request(0, request, onResponseListener);
        }

    }



    /**
     *  新增员工信息
     * token 用户唯一标识
     * employeeName 员工姓名
     * phoneNum 员工电话
     * positionId   员工职位Id
     * empRela  员工角色(全职、兼职)
     * sex  员工性别
     * businessId   商家ID
     * storeId 门店Id
     * creator  创建人 用户ID
     * idCard 员工证件号
     * depId    是否 教练
     * jobTitle 教练职称;1:初级教练;2:中级教练;3:高级教练
     * */
    public static void addEmployeeInfo(String employeeName,String phoneNum,String idCard,String positionId,Integer empRela,Integer sex,
                                       String businessId, String depId,String creator,String storeId,Integer jobTitle,ArrayList<BasicBinary> binaryList,
                                       ArrayList<BasicBinary> binaryHead,String text,OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.ADDEMPLOYEEINFO;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);

        if (request!=null){
            request.add("token",Token);
            request.add("employeeName",employeeName);
            request.add("phoneNum",phoneNum);
            request.add("positionId",positionId);
            request.add("empRela",empRela);
            request.add("sex",sex);
            request.add("businessId",businessId);
            request.add("creator",creator);
            request.add("idCard",idCard);
            request.add("depId",depId);
            request.add("storeId",storeId);
            request.add("jobTitle",jobTitle);
            if (!TextUtils.isEmpty(text)){
                request.add("selfIntroduction",text);
            }
           if (binaryHead.size()!=0){
               BasicBinary basicBinary = binaryHead.get(0);
               request.add("userHead",basicBinary);
           }

            if (binaryList.size()!=0){
                for (int i = 0; i < binaryList.size(); i++) {
                    String binary ="binary";
                    binary = binary+i+"";
                    request.add(binary,binaryList.get(i));
                }
            }
            RequestMeansManager.request(0, request, onResponseListener);
        }

    }



    /**
     *  获取员工信息
     * token 用户唯一标识
     * phoneNum 用户手机号
     * */
    public static void getEmployeeInfo(String phoneNum,OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.GETEMPLOYEEINFO;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        if (request!=null){
            request.add("token",Token);
            request.add("phoneNum",phoneNum);
            RequestMeansManager.request(0, request, onResponseListener);
        }
    }



    /**
     *  获取用户信息(用户切换门店)
     *
     * token 用户唯一标识
     * phoneNum 用户手机号
     * businessid   商家Id
     * storeid  分店Id
     * */
    public static void getEmployeeInfo2(String phoneNum,String businessid,String storeid,OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.GETEMPLOYEE;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        if (request!=null){
            request.add("token",Token);
            request.add("phoneNum",phoneNum);
            request.add("businessId",businessid);
            request.add("storeId",storeid);
            RequestMeansManager.request(0, request, onResponseListener);
        }
    }


    /**
     *  提交转正单
     *
     * token 用户唯一标识
     * businessId 商家id
     * storeId 门点Id
     * employeeId   员工Id
     * positionDesc  试用期岗位理解
     * workDesc  使用期工作总结
     * suggestion   意见或建议
     * approver  审批人
     * */
    public static void addRegularEmpInfo(String businessId,String storeId,String employeeId,String positionDesc,String workDesc,
                                       String suggestion,String approver,OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.ADDREGULAREMINFO;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);

        if (request!=null){
            request.add("token",Token);
            request.add("businessId",businessId);
            request.add("storeId",storeId);
            request.add("employeeId",employeeId);

            if (!TextUtils.isEmpty(positionDesc)){
                request.add("positionDesc",positionDesc);
            }
            if (!TextUtils.isEmpty(workDesc)){
                request.add("workDesc",workDesc);
            }
            if (!TextUtils.isEmpty(suggestion)){
                request.add("suggestion",suggestion);
            }
            request.add("approver",approver);
            RequestMeansManager.request(0, request, onResponseListener);
        }

    }

    /**
     *  获取转正单详情数据信息
     * token 用户唯一标识
     * id 单据Id
     * */
    public static void getRegularBillDetail(String id,OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.GETREGULARBILLDETAIL;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        if (request!=null){
            request.add("token",Token);
            request.add("id",id);
            RequestMeansManager.request(0, request, onResponseListener);
        }
    }



    /**
     * 提交离职单据数据
     *
     * businessId 商家ID
     * storeId 门点ID
     * employeeId 员工ID
     * leaveDate 离职日期
     * handler 交接人ID
     * leaveReason 离职原因
     * handleItem 所需交接事项
     * approver 审批人ID
     * */
    public static void addLeaveEmpInfo(String businessId, String storeId, String employeeId, String leaveDate, String handler
            ,String leaveReason,String handleItem, String approver,OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.ADDLEAVEEMPINFO;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        if (request!=null){
            request.add("token",Token);
            request.add("businessId",businessId);
            request.add("storeId",storeId);
            request.add("employeeId",employeeId);
            request.add("leaveDate",leaveDate);
            request.add("handler",handler);
            request.add("leaveReason",leaveReason);
            request.add("handleItem",handleItem);
            request.add("approver",approver);
            RequestMeansManager.request(0, request, onResponseListener);
        }
    }


    /**
     *  获取离职单详情数据信息
     * token 用户唯一标识
     * id 单据Id
     * */
    public static void getLeaveBillDetail(String id,OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.GETLEAVEBILLDETAIL;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        if (request!=null){
            request.add("token",Token);
            request.add("id",id);
            RequestMeansManager.request(0, request, onResponseListener);
        }
    }



    /**
     * 提交调岗单据数据
     *
     * businessId 商家ID
     * storeId 门点ID
     * employeeId 员工ID
     * transferReason 调岗原因
     * transferDate 调岗日期
     * transferPositionId 调岗职位
     * transferDepId 调岗部门
     * approver 审批人ID
     * */
    public static void addTransferEmpInfo(String businessId, String storeId, String employeeId, String transferReason, String transferDate
            ,String transferPositionId,String transferDepId, String approver,OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.ADDTRANSFEREMPINFO;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        if (request!=null){
            request.add("token",Token);
            request.add("businessId",businessId);
            request.add("storeId",storeId);
            request.add("employeeId",employeeId);
            request.add("transferReason",transferReason);
            request.add("transferDate",transferDate);
            request.add("transferPositionId",transferPositionId);
            request.add("transferDepId",transferDepId);
            request.add("approver",approver);
            RequestMeansManager.request(0, request, onResponseListener);
        }
    }


    /**
     *  获取调岗单详情数据信息
     * token 用户唯一标识
     * id 单据Id
     * */
    public static void getTransferBillDetail(String id,OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.GETTRANSFEREMPINFO;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        if (request!=null){
            request.add("token",Token);
            request.add("id",id);
            RequestMeansManager.request(0, request, onResponseListener);
        }
    }

    /**
     * 入职单中获取商家部门列表
     *
     * */
    public static void getListBusiDepartmentInfo(String businessId,OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.LISTBUSIDEPARTMENTINFO;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        if (request!=null){
            request.add("token",Token);
            request.add("businessId",businessId);
            RequestMeansManager.request(0, request, onResponseListener);
        }

    }


    /**
     * 入职单中获取商家职位列表
     *
     * */
    public static void getListBusiPositionInfo(String businessId,OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.LISTBUSIPOSITIONINFO;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        if (request!=null){
            request.add("token",Token);
            request.add("businessId",businessId);
            RequestMeansManager.request(0, request, onResponseListener);
        }

    }



    /*==================================================会员卡管理===================================================*/
    /**
     *  添加会员卡类型
     * membcardTypeName 会员卡类别名称
     * creator 添加者的ID
     * */
    public static void addMemberCardType(String membcardTypeName,String membcardTypeId,String creator,OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.ADDMEMBERCARDTYPE;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        if (request!=null){
            request.add("token",Token);
            request.add("membcardTypeName",membcardTypeName);
            request.add("membcardTypeId",membcardTypeId);
            request.add("creator",creator);
            RequestMeansManager.request(0, request, onResponseListener);
        }
    }


    /**
     *  获取会员卡类型列表
     * Token 用户通行口令
     * */
    public static void getListMemberCardType(OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.LISTMEMBERCARDTYPE;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        if (request!=null){
            request.add("token",Token);
            RequestMeansManager.request(0, request, onResponseListener);
        }
    }

    /**
     *  获取会员卡类型数据
     * Token 用户通行口令
     * Id 会员卡类型ID
     * */
    public static void getMemberCardType(String id,OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.GETMEMBERCARDTYPE;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        if (request!=null){
            request.add("token",Token);
            request.add("id",id);
            RequestMeansManager.request(0, request, onResponseListener);
        }
    }

    /**
     *  添加会员卡数据
     * Token 用户通行口令
     * businessId	String		是	商家id(新增)
     * membcardName	String		是	会员卡名称
     * membcardTypeId	String		是	会员卡类型id
     * remark	String		否	简介
     * creator	String		是	创建人id
     * */
    public static void addMemberCard(String businessId,String membcardName,String membcardTypeId,String remark,String creator,String membcardType,OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.ADDMEMBERCARD;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        if (request!=null){
            request.add("token",Token);
            request.add("businessId",businessId);
            request.add("membcardName",membcardName);
//          request.add("membcardTypeId","1");
            request.add("membcardType",membcardType);
            request.add("remark",remark);
            request.add("creator",creator);
            RequestMeansManager.request(0, request, onResponseListener);
        }
    }

    /**
     *  获取会员卡数据列表
     * Token 用户通行口令
     * businessId 商家Id
     * */
    public static void getListMemberCard(String businessId,OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.LISTMEMBERCARD;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        if (request!=null){
            request.add("token",Token);
            request.add("businessId",businessId);
            RequestMeansManager.request(0, request, onResponseListener);
        }
    }

    /**
     *  获取会员卡数据
     * Token 用户通行口令
     * Id 会员卡类型ID
     * */
    public static void getMemberCard(String id,OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.GETLISTMEMBERCARD;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        if (request!=null){
            request.add("token",Token);
            request.add("id",id);
            RequestMeansManager.request(0, request, onResponseListener);
        }
    }
    /**
     *  根据id删除会员卡
     * Token 用户通行口令
     * Id 会员卡类型ID
     * */
    public static void delMembercardById(String id,OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.DELMEMBERCARDBYID;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        if (request!=null){
            request.add("token",Token);
            request.add("id",id);
            RequestMeansManager.request(0, request, onResponseListener);
        }
    }


    /**
     *  获取门店中会员列表
     * Token 用户通行口令
     * storeId 门店id
     * */
    public static void queryMemberListByStoreId(String storeId,OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.QUERYMEMBERLISTBYSTOREID;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        if (request!=null){
            request.add("token",Token);
            request.add("storeId",storeId);
            RequestMeansManager.request(0, request, onResponseListener);
        }
    }


    /**
     *  编辑会员卡信息
     * Token 用户通行口令
     * membcardName	String			会员卡名称
     * membcardType	Integer			会员卡类型:1次卡;2:期限卡
     * remark	String			备注
     * id	String		是	会员卡id
     * */
    public static void updateMembercard(String membcardName,Integer membcardType,String remark,String id,OnResponseListener<String> onResponseListener){

        String url = UrlInterfaceManager.UPDATEMEMBERCARD;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        if (request!=null){
            request.add("token",Token);
            request.add("membcardName",membcardName);
            request.add("membcardType",membcardType);
            request.add("remark",remark);
            request.add("id",id);
            RequestMeansManager.request(0, request, onResponseListener);
        }
    }


    /**
     *  添加会员卡的价格阶梯
     *token	string		是	口令
     *membcardId	String		是	会员卡Id
     *times	integer			充值次数(次卡充值)
     *months	integer			充值月数(期限卡充值)
     *nominalAmount	String		是	面值金额
     *creator	String		提交人ID
     * */
    public static void addRechargegrad(String membcardId,Integer times,Integer months,String nominalAmount,String creator,OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.ADDRECHARGEGRADLIST;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        if (request!=null){
            request.add("token",Token);
            request.add("membcardId",membcardId);
            if (times != 0){
                request.add("times",times);
            }
            if (months !=0){
                request.add("months",months);
            }
            request.add("nominalAmount",nominalAmount);
            request.add("creator",creator);
            RequestMeansManager.request(0, request, onResponseListener);
        }
    }



    /**
     *  获取会员卡的价格阶梯
     *
     * Token 用户通行口令
     * membcardId 会员卡id
     * */
    public static void getRechargegradList(String membcardId,OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.GETRECHARGEGRADLIST;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        if (request!=null){
            request.add("token",Token);
            request.add("membcardId",membcardId);
            RequestMeansManager.request(0, request, onResponseListener);
        }
    }

    /**
     *  获取商家门店信息以及会员卡关联门店信息列表
     *
     * Ttoken	string		是	口令
     * businessId	String		是	商家id
     * membcardId	String		是	会员卡id
     * */
    public static void queryStoreInfoList(String businessId,String membcardId,OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.QUERYSTOREINFOLIST;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        if (request!=null){
            request.add("token",Token);
            request.add("businessId",businessId);
            request.add("membercardId",membcardId);
            RequestMeansManager.request(0, request, onResponseListener);
        }
    }

    /**
     *  保存 会员卡关联门店
     *
     * Ttoken	string		是	口令
     * storeIdStr	String		是	门店id拼接字符串(以逗号隔开)
     * membercardId	String		是	会员卡id
     * */
    public static void addMembercardStoreRelationList(String storeIdStr,String membercardId,OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.ADDSTORERELATIONLIST;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        if (request!=null){
            request.add("token",Token);
            request.add("storeIdStr",storeIdStr);
            request.add("membercardId",membercardId);
            RequestMeansManager.request(0, request, onResponseListener);
        }
    }


/*==================================================课程管理===================================================*/


    /**
     *  查询门店中开卡信息
     *
     * token	string		是	口令
     * storeId	String		是	门店id
     * page	Integer		是	当前页
     * queryParameter	String			请求参数
     * flagtimes	String			按剩余次数查询
     * flagdays	String			按剩余天数查询
     * membcardId	Integer		是	会员卡id

     * */
    public static void queryOpencardInfo(String storeId,String queryParameter,Integer page,Integer flagtimes

            ,Integer flagdays,String membcardId,OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.QUERYOPENCARDINFO;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        if (request!=null){
            request.add("token",Token);
            request.add("storeId",storeId);
            if (!TextUtils.isEmpty(queryParameter)){
                request.add("queryParameter",queryParameter);
            }
            request.add("page",page);
            if (flagtimes !=0){
                request.add("flagtimes",flagtimes);
            }
            if (flagdays != 0){
                request.add("flagdays",flagdays);
            }
            if (!TextUtils.isEmpty(membcardId)){
                request.add("membcardId",membcardId);
            }
            RequestMeansManager.request(0, request, onResponseListener);
        }
    }



     /*==================================================课程管理===================================================*/

    /**
     * 获取课程数据列表
     * Token 用户通行口令
     * */
    public static void getListCourseInfo(OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.LISTCOURSEINFO;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        if (request!=null){
            request.add("token",Token);
            RequestMeansManager.request(0, request, onResponseListener);
        }
    }

    /**
     *  获取课程数据
     * Token 用户通行口令
     * Id 会员卡类型ID
     * */
    public static void getCourseInfo(String id,OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.GETCOURSEINFO;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        if (request!=null){
            request.add("token",Token);
            request.add("id",id);
            RequestMeansManager.request(0, request, onResponseListener);
        }
    }

    /*==================================================财务管理===================================================*/

    /**提交报销单
     *token		用户通行口令
     *businessId	商家id
     *storeId	    门店id
     *employeeId	员工id
     *description	款项用途
     *account		金额（保留一位小数）
     *approver		审批人id
     *binaryList   单据图片
     * */
    public static void addExpenseAccountBill(String businessId, String storeId, String employeeId, String description
            ,String account, String approver, ArrayList< BasicBinary> binaryList,OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.ADDECPENSEACCOUNTBILL;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        if (request!=null){
            request.add("token",Token);
            request.add("businessId",businessId);
            request.add("storeId",storeId);
            request.add("employeeId",employeeId);
            request.add("description",description);
            request.add("account",account);
            request.add("approver",approver);

            //图片可传可不传
            if (binaryList.size()!= 0){
                for (int i = 0; i < binaryList.size(); i++) {
                    String binary = "binary";
                    binary = binary+i;
                    BasicBinary basicBinary = binaryList.get(i);
                    request.add(binary+"",basicBinary);
                    Log.i("*****", "图片---->"+binary+"");
                }
            }

            RequestMeansManager.request(0, request, onResponseListener);
        }
    }



    /**
     *  获取课程数据
     * Token 用户通行口令
     * Id 会员卡类型ID
     * */
    public static void addS(String ids,OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.ADDCOURSEANDCARD;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        String enable = "";


        List<String> data = new ArrayList<>();
        if (request!=null){
            request.add("token",Token);

            for (int i = 0; i < data.size(); i++) {
//                request.add("id",);
                request.add("enable",enable);
            }

            RequestMeansManager.request(0, request, onResponseListener);
        }
    }

     /*==================================================商家管理===================================================*/
    /**
     *添加相册
     *Token 用户通行口令
     *storeId	string		是	店铺Id
     *describes	string		是	描叙
     *albumName	string		是	相册名称
     *creator	string		是	创建人
     * */
    public static void addAlbumInfo(String storeId,String describes,String albumName,String creator,OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.ADDALBUMINFO;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        if (request!=null){
            request.add("token",Token);
            request.add("storeId",storeId);
            request.add("describes",describes);
            request.add("albumName",albumName);
            request.add("creator",creator);
            RequestMeansManager.request(0, request, onResponseListener);
        }
    }


    /**
     *  获取相册信息
     * Token 用户通行口令
     * storeId 门店ID
     * */
    public static void getAlbumList(String storeId,OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.GETALBUMLIST;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        if (request!=null){
            request.add("token",Token);
            request.add("storeId",storeId);
            RequestMeansManager.request(0, request, onResponseListener);
        }
    }

    /**
     *修改相册信息
     *token	string		是	用户通行口令
     *Id	String		是	相册Id
     *storeId	string		是	店铺Id
     *describes	string		是	描叙
     *albumName	string		是	相册名称
     *creator	string		是	创建人
     * */
    public static void updateAlbumById(String id,String storeId,String describes,String albumName,String creator,
                                       OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.UPDATEALBUMBYID;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        if (request!=null){
            request.add("token",Token);
            request.add("Id",id);
            request.add("storeId",storeId);
            request.add("describes",describes);
            request.add("albumName",albumName);
            request.add("creator",creator);

            RequestMeansManager.request(0, request, onResponseListener);
        }
    }


    /**
     *  删除相册
     * Token 用户通行口令
     * Id 相册ID
     * */
    public static void deleteAlbumById(String Id,OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.DELETEALBUMBYID;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        if (request!=null){
            request.add("token",Token);
            request.add("storeId",Id);
            RequestMeansManager.request(0, request, onResponseListener);
        }
    }


    /**
     *添加相片
     token	string		是	用户通行口令
     albumId	string		是	相册Id
     photoName	string		是	照片名称
     describes	string		是	描述
     url	string		是	照片URL
     creator	string		是	创建人

     * */
    public static void addPhotoInfo(String albumId,String photoName,String describes,String urls,String creator,
                                       OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.ADDPHOTOINFO;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        if (request!=null){
            request.add("token",Token);
            request.add("albumId",albumId);
            request.add("photoName",photoName);
            request.add("describes",describes);
            request.add("urls",urls);
            request.add("creator",creator);

            RequestMeansManager.request(0, request, onResponseListener);
        }
    }

    /**
     *  删除相片
     * Token 用户通行口令
     * Id 相片ID
     * */
    public static void deletePhotoById(String Id,OnResponseListener<String> onResponseListener){
        String url = UrlInterfaceManager.DELETEPHOTOBYID;
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        if (request!=null){
            request.add("token",Token);
            request.add("storeId",Id);
            RequestMeansManager.request(0, request, onResponseListener);
        }
    }

}
