package com.dod.sport.controller;

import com.dod.sport.dao.IMembercardRelationDao;
import com.dod.sport.domain.common.BusiException;
import com.dod.sport.domain.po.Base.BusinessMember;
import com.dod.sport.domain.po.Base.ClientUser;
import com.dod.sport.domain.po.BussinessMemberLeave;
import com.dod.sport.domain.po.Member.MemberEvaluate;
import com.dod.sport.domain.po.Member.MemberLeave;
import com.dod.sport.domain.po.Member.MembercardRelation;
import com.dod.sport.domain.po.ResponseMember;
import com.dod.sport.service.IClientUserService;
import com.dod.sport.service.IMemberService;
import com.dod.sport.service.IMembercardRelationService;
import com.dod.sport.util.CommonEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 会员Controller
 * Created by Administrator on 2017/8/25.
 */
@Controller
@RequestMapping("api/member/v1")
public class MemberController {

    @Autowired
    private IMemberService memberService;

    @Autowired
    private IMembercardRelationService membercardRelationService;

    @Autowired
    private IMembercardRelationDao iMembercardRelationDao;

    @Autowired
    private IClientUserService clientUserService;

    /**
     * 获取会员信息
     * @param phoneNum
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryMemberinfoByPhoneNum", method = RequestMethod.POST)
    public Map<String,Object> queryMemberinfoByPhoneNum(@RequestParam(value = "phoneNum") final String phoneNum,
                                                        @RequestParam(value = "businessId") final String businessId){
        Map<String,Object>map =  new HashMap<>();
        BusinessMember queryMember = new BusinessMember();
        queryMember.setPhoneNum(phoneNum);
        queryMember.setBusinessId(businessId);
        ResponseMember resultresponseMember = memberService.queryMemberInfo(queryMember);
        map.put("baseMember",resultresponseMember);
        return map;
    }

    /**
     * 添加会员信息
     * @param businessId
     * @param storeId
     * @param nickName
     * @param sex
     * @param birthday
     * @param phoneNum
     * @param creator
     * @param remark
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addMemberInfo", method = RequestMethod.POST)
    public String addMemberInfo(@RequestParam(value = "businessId") final String businessId,
                                @RequestParam(value = "storeId") final String storeId,
                                @RequestParam(value = "nickName") final String nickName,
                                @RequestParam(value = "sex") final String sex,
                                @RequestParam(value = "birthday", required = false) final String birthday,
                                @RequestParam(value = "phoneNum") final String phoneNum,
                                @RequestParam(value = "memberTags") final String memberTags,
                                @RequestParam(value = "creator") final String creator,
                                @RequestParam(value = "remark", required = false) final String remark){
        BusinessMember queryMember = new BusinessMember();
        queryMember.setMemberTags(memberTags);
        queryMember.setBusinessId(businessId);
        queryMember.setStoreId(storeId);
        queryMember.setCreator(creator);
        queryMember.setPhoneNum(phoneNum);
        queryMember.setBirthday(birthday);
        queryMember.setSex(sex);
        queryMember.setNickName(nickName);
        queryMember.setRemark(remark);
        memberService.registerMemberInfo(queryMember);
        return "";
    }

    /**
     * 获取门店中所有会员信息
     * @param storeId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryMemberListByStoreId", method = RequestMethod.POST)
    public Map<String,Object> queryMemberListByStoreId(@RequestParam(value = "storeId") final String storeId,
                                                       @RequestParam(value = "page") Integer page,
                                                       @RequestParam(value = "pageSize",required = false) Integer pageSize){
        Map<String,Object> map = new HashMap<>();
        List<ResponseMember> responseMemberList = memberService.queryMemberListByStoreId(storeId,page,pageSize);

        map.put("responseMemberList",responseMemberList);
        return map;
    }
    /**
     * 根据会员名称/电话获取会员信息
     * @param storeId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryMemberListByStoreIdAndName", method = RequestMethod.POST)
    public Map<String,Object> queryMemberListByStoreIdAndName(@RequestParam(value = "storeId") final String storeId,
                                                              @RequestParam(value = "requeryParam", required = false) final String requeryParam,
                                                              @RequestParam(value = "page") Integer page,
                                                              @RequestParam(value = "pageSize",required = false) Integer pageSize){
        Map<String,Object> map = new HashMap<>();
        List<ResponseMember> responseMemberList = memberService.queryMemberListByStoreIdAndName(storeId,requeryParam,page,pageSize);
        map.put("responseMemberList",responseMemberList);
        return map;
    }

    /**
     * 设置会员标签
     * @param memberTags
     * @param memberId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updatememberTags", method = RequestMethod.POST)
    public String updatememberTags( @RequestParam(value = "memberTags") final String memberTags,
                                   @RequestParam(value = "memberId") final String memberId){
        BusinessMember businessMember = new BusinessMember();
        businessMember.setMemberTags(memberTags);
        businessMember.setId(memberId);
        memberService.updatememberTags(businessMember);
        return "";
    }

    /**
     * 编辑会员信息
     * @param nickName  用户名称
     * @param sex 性别:1男;2:女;3:未知
     * @param birthday 生日
     * @param phoneNum 电话
     * @param remark 备注
     * @param memberId 会员id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateMemberInfo", method = RequestMethod.POST)
    public String updateMemberInfo(@RequestParam(value = "nickName", required = false) final String nickName,
                                  @RequestParam(value = "sex", required = false) final String sex,
                                  @RequestParam(value = "birthday", required = false) final String birthday,
                                  @RequestParam(value = "phoneNum", required = false) final String phoneNum,
                                  @RequestParam(value = "remark", required = false) final String remark,
                                  @RequestParam(value = "memberTags", required = false) final String memberTags,
                                  @RequestParam(value = "memberId") final String memberId,
                                  @RequestParam(value = "userId") final String userId){
        ClientUser clientUser = new ClientUser();
        clientUser.setNickName(nickName);
        clientUser.setSex(sex);
        clientUser.setBirthday(birthday);
        clientUser.setPhoneNum(phoneNum);
        clientUser.setId(userId);
        clientUserService.updateClientUser(clientUser);

        BusinessMember businessMember = new BusinessMember();
        businessMember.setMemberTags(memberTags);
        businessMember.setRemark(remark);
        businessMember.setId(memberId);
        memberService.updateMeberInfo(businessMember);
        return "";
    }

    /**会员请假信息
     * @param memberId 会员id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addMemberLeaveInfo", method = RequestMethod.POST)
    public String addMmberLeaveInfo(@RequestParam(value = "memberId") final String memberId,
                                  @RequestParam(value = "startTime") final String startTime,
                                  @RequestParam(value = "duration") final String duration,
                                  @RequestParam(value = "endTime") final String endTime,
                                  @RequestParam(value = "fee" ) final String fee,
                                  @RequestParam(value = "creator" ) final String creator,
                                  @RequestParam(value = "remark") final String remark){

        MemberLeave  memberLeave  = new MemberLeave();
        memberLeave.setMemberId(memberId);
        memberLeave.setStartTime(startTime);
        memberLeave.setDuration(duration);
        memberLeave.setEndTime(endTime);
        memberLeave.setFee(fee);
        memberLeave.setRemark(remark);
        memberLeave.setCreator(creator);
        memberService.addMemberLeaveInfo(memberLeave);
        return "";
    }

    /**获取会员请假信息
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getMemberLeaveById", method = RequestMethod.POST)
    public Map<String,Object>getMemberLeaveById(@RequestParam(value = "memberId") final String memberId,
                                    @RequestParam(value = "approveStatus") final String approveStatus ){
        MemberLeave  memberLeave  = new MemberLeave();
        memberLeave.setMemberId(memberId);
        memberLeave.setApproveStatus(approveStatus);
        Map<String,Object> map = new HashMap<>();
        MemberLeave memberLeaves = memberService.getMemberLeaveById(memberLeave);
        if (memberLeaves ==null ){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_nodata.getValue());
        }
        map.put("memberLeaveList",memberLeaves);
        return map;
    }

    /**会员审核信息
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/memberApprove", method = RequestMethod.POST)
    public String memberApprove (@RequestParam(value = "memberId") final String memberId,
                                 @RequestParam(value = "id") final String id,
                                 @RequestParam(value = "approver") final String approver,
                                 @RequestParam(value = "approveStatus") final String approveStatus ){

        MemberLeave  memberLeave  = new MemberLeave();
        memberLeave.setId(id);
        memberLeave.setApproveStatus(approveStatus);
        memberLeave.setApprover(approver);
        if(memberLeave.equals("2")){
            List<MembercardRelation> membercardRelation = iMembercardRelationDao.queryMembercardrelationListByMemberId(memberId);
            for (int i = 0; i <membercardRelation.size() ; i++) {
                membercardRelationService.memberCardStopOrStart(membercardRelation.get(i).getId(),"2",approver);
            }
        }
        memberService.memberApprove(memberLeave);
        return null;
    }

    /**删除会员请假信息
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleleMemberApprove", method = RequestMethod.POST)
    public String deleteMemberApprove (@RequestParam(value = "id") final String id ){

        MemberLeave  memberLeave  = new MemberLeave();
        memberLeave.setId(id);
        memberService.deleteMemberApprove(memberLeave);
        return null;
    }


    /**获取商家会员基础数据
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getBusinessMemberLeaveInfo", method = RequestMethod.POST)
    public Map<String,Object> getBusinessMemberLeaveInfo (@RequestParam(value = "bussinessId") final String bussinessId  ){

        BussinessMemberLeave bussinessMemberLeave = new BussinessMemberLeave();
        bussinessMemberLeave.setBussinessId(bussinessId);
        BussinessMemberLeave memberLeaves =memberService.getBusinessMemberLeaveInfo(bussinessMemberLeave);
        Map<String,Object> map = new HashMap<>();
        if (memberLeaves ==null ){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_nodata.getValue());
        }
        map.put("bussinessMemberLeave",memberLeaves);
        return map;
    }
    /**
     *新增会员评价
     * @param memberId
     * @param empId
     * @param courseId
     * @param teachLevel
     * @param serviceLevel
     * @param environmentLevel
     * @param evaluateStr
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addMemberEvaluate", method = RequestMethod.POST)
    public String addMemberEvaluate(@RequestParam(value = "memberId") final String memberId,
                                  @RequestParam(value = "empId") final String empId,
                                  @RequestParam(value = "courseId") final String courseId,
                                  @RequestParam(value = "teachLevel") final String teachLevel,
                                  @RequestParam(value = "serviceLevel") final String serviceLevel,
                                  @RequestParam(value = "environmentLevel") final String environmentLevel,
                                  @RequestParam(value = "serviceLevel") final String evaluateStr){
        MemberEvaluate memberEvaluate = new  MemberEvaluate();
        memberEvaluate.setMemberId(memberId);
        memberEvaluate.setEmpId(empId);
        memberEvaluate.setCourseId(courseId);
        memberEvaluate.setTeachLevel(teachLevel);
        memberEvaluate.setServiceLevel(serviceLevel);
        memberEvaluate.setEnvironmentLevel(environmentLevel);
        memberEvaluate.setServiceLevel(serviceLevel);
        memberEvaluate.setEvaluateStr(evaluateStr);
        memberService.addMemberEvaluate(memberEvaluate);
        return "";
    }


}
