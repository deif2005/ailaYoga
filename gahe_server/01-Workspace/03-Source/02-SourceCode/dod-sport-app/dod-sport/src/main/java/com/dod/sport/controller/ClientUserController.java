package com.dod.sport.controller;

import com.dod.sport.config.Configuration;
import com.dod.sport.constant.SysConfigConstants;
import com.dod.sport.domain.common.BusiException;
import com.dod.sport.domain.common.UploadFileResponse;
import com.dod.sport.domain.po.Base.BaseBusinessInfo;
import com.dod.sport.domain.po.Base.ClientUser;
import com.dod.sport.domain.po.Member.MembercardRelation;
import com.dod.sport.domain.po.ResponseMember;
import com.dod.sport.service.IClientUserService;
import com.dod.sport.util.CommonEnum;
import com.dod.sport.util.RedisCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/9.
 */

@Controller
@RequestMapping(value = "api/ClientUser/v1")
public class ClientUserController extends BaseController {

    @Autowired
    IClientUserService clientUserService;

    /**
     * 注册平台用户
     * @param password
     * @param repassword
     * @param phoneNum
     * @return
     */
    @RequestMapping(value = "/registerClientUser",method = RequestMethod.POST)
    @ResponseBody
    public String registerClientUser( @RequestParam(value = "password") final String password,
                                      @RequestParam(value = "repassword") final String repassword,
                                      @RequestParam(value = "phoneNum") final String phoneNum){

        if("".equals(password)||password==null||repassword==null||"".equals(repassword)){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_passworderror.getValue());
        }
        if(!repassword.equals(password)){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_passwordnotmatch.getValue());
        }
        ClientUser clientUser = new ClientUser();
        clientUser.setPhoneNum(phoneNum);
        ClientUser getclientUser  = clientUserService.getClientUserInfo(clientUser);
        if (getclientUser !=null){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_registered.getValue());
        }
        clientUser.setPassword(password);
        clientUserService.addClientUser(clientUser);
        return "";
    }

    /**
     * 平台用户登陆
     * @param phoneNum
     * @param password
     * @return
     */
    @RequestMapping(value = "/loginClientUserInfo",method = RequestMethod.POST)
    @ResponseBody
    public Map<String ,Object> loginClientUserInfo( @RequestParam(value = "phoneNum") final String phoneNum,
                                                    @RequestParam(value = "password") final String password){
        ClientUser clientUser = new ClientUser();
        Map<String ,Object> map = new HashMap<>();
        if (phoneNum!=null &&!"".equals(phoneNum)){
            clientUser.setPhoneNum(phoneNum);
            ClientUser getclientUser  = clientUserService.getClientUserInfo(clientUser);
            if (getclientUser == null){
                throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_employeenotexists.getValue());
            }
        }
        if (password!=null && !"".equals(password)){
            clientUser.setPassword(password);
            ResponseMember loginClientUserInfo  = clientUserService.loginClientUserInfo(clientUser);
            map.put("loginClientUserInfo",loginClientUserInfo);
        }
        return map;
    }

    /**
     * 获取平台用户详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getClientUserInfo",method = RequestMethod.POST)
    @ResponseBody
    public Map<String ,Object> getClientUserInfo( @RequestParam(value = "id",required = false) final String id) {
        ClientUser clientUser = new ClientUser();
        Map<String, Object> map = new HashMap<>();
        clientUser.setId(id);
        ClientUser getclientUser  = clientUserService.getClientUserInfo(clientUser);
        map.put("clientUser",getclientUser);
        return map;
    }

    /**
     * 获取平台用户列表
     * @param sex
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/listClientUserInfo",method = RequestMethod.POST)
    @ResponseBody
    public Map<String ,Object> listClientUserInfo( @RequestParam(value = "sex",required = false) String sex,
                                                   @RequestParam(value = "page") Integer page,
                                                   @RequestParam(value = "pageSize",required = false) Integer pageSize){
        Map<String ,Object> map = new HashMap<>();
        List<ClientUser> list = clientUserService.listClientUserInfo(sex, page, pageSize);
        if (list ==null){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_nodata.getValue());
        }
        map.put("clientUserList",list);
        return map;
    }

    /**
     * 更新平台用户信息
     * @return
     */
    @RequestMapping(value = "/updateClientUser",method = RequestMethod.POST)
    @ResponseBody
    public List<String> updateClientUser(@RequestParam(value = "height",required = false) final String height,
                                   @RequestParam(value = "weight",required = false) final String weight,
                                   @RequestParam(value = "birthday",required = false) final String birthday,
                                   @RequestParam(value = "nickName",required = false) final String nickName,
                                   @RequestParam(value = "hobby",required = false) final String hobby,
                                   @RequestParam(value = "email",required = false) final String email,
                                   @RequestParam(value = "sex",required = false) final String sex,
                                   @RequestParam(value = "id") final String id,
                                   @RequestParam(value = "phoneNum",required = false) final String phoneNum){

        ClientUser clientUser = new ClientUser();
        clientUser.setId(id);
        clientUser.setNickName(nickName);
        clientUser.setHeight(height);
        clientUser.setWeight(weight);
        clientUser.setBirthday(birthday);
        clientUser.setHobby(hobby);
        clientUser.setSex(sex);
        clientUser.setEmail(email);
        clientUser.setPhoneNum(phoneNum);
        List<String> showPathList = new ArrayList<>();
        try {
            //文件上传相对目录
            String relaPath = Configuration.getUploadUserHeadPath(clientUser.getId());
            //文件展示地址
            String baseShowPath = Configuration.getStaticShowPath();
            //上传文件
            UploadFileResponse uploadFileResponse = super.uploadFile(request,relaPath);
            String uploadPath = uploadFileResponse.getFileList().toString();
            StringBuffer stringBuffer = new StringBuffer();
            if (!"".equals(uploadPath)){
                for (String path : uploadFileResponse.getFileList()){
                    stringBuffer.append(";").append(path);
//                    showPathList.add(baseShowPath + path);
                }
                clientUser.setHeadPortrait(stringBuffer.toString().substring(1));
            }
        }catch (BusiException e){
            //没传图片继续执行
        }catch (IOException e){
            logger.error("ClientUserController-updateClientUser:", e);
            throw new BusiException(CommonEnum.ReturnCode.SystemCode.BusinessCode.busi_err_uploadfileerror.getValue());
        }
        clientUserService.updateClientUser(clientUser);
        return showPathList;
    }

    /**
     * 客户端验证码验证
     * @param phoneNum
     * @param identifyingCode
     * @param flag 1:注册;2:忘记密码
     * @param sideType 1:客户端;2:商家端;3:服务端
     */
 /*   @RequestMapping(value = "/ClientUserVerifyIdentCode",method = RequestMethod.POST )
    @ResponseBody
    public String verifyIdentCode(  @RequestParam(value = "phoneNum") final String phoneNum,
                                    @RequestParam(value = "sideType") final String sideType,
                                    @RequestParam(value = "flag") final String flag,
                                    @RequestParam(value = "identifyingCode") final String identifyingCode){
        //验证码校验
        if (!RedisCommon.verifyIdentCode(phoneNum, identifyingCode)){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_identifyingcode.getValue());
        }
        ClientUser clientUser = clientUserService.getClientUserByPhone(phoneNum);
        if ("1".equals(flag)){//注册
            if (clientUser!=null){
                throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_employeeexists.getValue());
            }
        }else if ("2".equals(flag)){//找回密码
            if (clientUser==null){
                throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_membernotexists.getValue());
            }
        }else{
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_parametererror.getValue());
        }

        return "";
    }*/

    /**
     * 获取平台用户密码
     * @param phoneNum
     * @param password
     * @param repassword
     * @return
     */
    @RequestMapping(value = "/updateClientUserPassword",method = RequestMethod.POST)
    @ResponseBody
    public String updateClientUserPassword(@RequestParam(value = "phoneNum") final String phoneNum,
                                           @RequestParam(value = "password") final String password,
                                           @RequestParam(value = "repassword") final String repassword){
        if("".equals(password)||"".equals(repassword)){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_passworderror.getValue());
        }
        if(!repassword.equals(password)){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_passwordnotmatch.getValue());
        }
        if("".equals(phoneNum)||phoneNum.length()!= SysConfigConstants.EMPLOYEE_PHONENUM_LENGTH){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_phonenumerror.getValue());
        }
        ClientUser clientUser = new ClientUser();
        clientUser.setPhoneNum(phoneNum);
        ClientUser getClientUser = clientUserService.getClientUserInfo(clientUser);
        if (getClientUser == null){//该用户不存在
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_employeenotexists.getValue());
        }
        ClientUser updateClientUser = new ClientUser();
        updateClientUser.setId(getClientUser.getId());
        updateClientUser.setPassword(password);
        clientUserService.updateClientUser(updateClientUser);
        return "";
    }

    /**
     * 根据电话获取平台用户信息
     * @param phoneNum
     * @return
     */
    @RequestMapping(value = "/getClientUserByPhone",method = RequestMethod.POST)
    @ResponseBody
    public  Map<String,Object>getClientUserByPhone(@RequestParam(value = "phoneNum")String phoneNum){
        Map<String,Object>map = new HashMap<>();
        ClientUser clientUser = clientUserService.getClientUserByPhone(phoneNum);
        map.put("clientUser",clientUser);
        return map;
    }

    /**
     * 获取平台用户商家列表
     * @param phoneNum
     * @return
     */
    @RequestMapping(value = "/listClientUserBusiness",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> listClientUserBusiness(@RequestParam(value = "phoneNum")String phoneNum) {
        Map<String,Object>map = new HashMap<>();
        List<BaseBusinessInfo> list = clientUserService.listClientUserBusiness(phoneNum);
        map.put("list",list);
        return map;
    }

    /**
     * 获取用户的会员卡列表
     * @param userId
     * @param businessId
     * @return
     */
    @RequestMapping(value = "/listUserMembercard",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> listUserMembercard(@RequestParam(value = "userId")String userId,
                                                 @RequestParam(value = "businessId")String businessId){
        Map<String,Object>map = new HashMap<>();
        List<MembercardRelation> list = null;
        try {
            list = clientUserService.listUserMembercard(userId,  businessId);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        map.put("list",list);
        return map;
    }


}
