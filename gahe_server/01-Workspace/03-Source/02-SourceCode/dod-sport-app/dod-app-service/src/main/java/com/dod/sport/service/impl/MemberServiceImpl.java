package com.dod.sport.service.impl;

import com.dod.sport.Scheduler.Job.MemberLeaveJob;
import com.dod.sport.Scheduler.Job.SendCourseCommentMessageJob;
import com.dod.sport.Scheduler.SchedulerUtil;
import com.dod.sport.config.Configuration;
import com.dod.sport.dao.IMemberDao;
import com.dod.sport.domain.common.BatchDataPage;
import com.dod.sport.domain.po.Base.BusinessMember;
import com.dod.sport.domain.po.Base.ClientUser;
import com.dod.sport.domain.po.BussinessMemberLeave;
import com.dod.sport.domain.po.Member.MemberEvaluate;
import com.dod.sport.domain.po.Member.MemberLeave;
import com.dod.sport.domain.po.ResponseMember;
import com.dod.sport.service.IClientUserService;
import com.dod.sport.service.IMemberService;
import com.dod.sport.util.DateUtil;
import com.dod.sport.util.RedisCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2017/8/25.
 */
@Service
public class MemberServiceImpl implements IMemberService {
    @Autowired
    private IMemberDao memberDao;
    @Autowired
    private IClientUserService clientUserService;
    /**
     * 会员电话获取门店会员信息
     * @param businessMember
     * @return
     */
    @Override
    public ResponseMember queryMemberInfo(BusinessMember businessMember) {
        return memberDao.queryMemberInfo(businessMember);
    }

    /**
     * 获取会员关系列表
     * @param userId
     * @return
     */
    @Override
    public List<ResponseMember> listMemberInfoByUserId(String userId) {
        List<ResponseMember> responseMembers =  memberDao.listMemberInfoByUserId(userId);
        if (responseMembers!=null &&responseMembers.size()>0){
            for (ResponseMember responseMember: responseMembers ){
                if (responseMember !=null && responseMember.getHeadPortrait()!=null && !"".equals(responseMember.getHeadPortrait())){
                    responseMember.setHeadPortrait(Configuration.getStaticShowPath()+responseMember.getHeadPortrait());
                }
            }
        }
        return responseMembers;
    }

    /**
     * 注册会员
     * @param businessMember
     */
    @Override
    @Transactional
    public void registerMemberInfo(BusinessMember businessMember) {
        ClientUser clientUser = new ClientUser();
        clientUser.setPhoneNum(businessMember.getPhoneNum());
        ClientUser queryUser = clientUserService.getClientUserInfo(clientUser);
        String userId =null;
        if (queryUser ==null){//如果平台中不存在该用户
            userId = UUID.randomUUID().toString();
            clientUser.setNickName(businessMember.getNickName());
            clientUser.setSex(businessMember.getSex());
            clientUser.setId(userId);
            clientUser.setPhoneNum(businessMember.getPhoneNum());
            clientUser.setBirthday(businessMember.getBirthday());
            clientUserService.addClientUser(clientUser);
        }else {
            userId = queryUser.getId();
        }
        businessMember.setUserId(userId);
        this.addMemberInfo(businessMember);
    }

    /**
     * 新增会员信息
     * @param
     */
    @Override
    public void addMemberInfo(BusinessMember businessMember){
        ResponseMember responseMember = memberDao.getBuseinessMemberByUserId(businessMember);
        if (responseMember!=null){
            return;
        }
        String maxId = RedisCommon.getMaxMemberId(businessMember.getBusinessId());
        if ("".equals(maxId)){
            maxId = RedisCommon.setAndReturnMaxMemberId(businessMember.getBusinessId(),memberDao.getMaxMemberId(businessMember.getBusinessId()));
        }
        businessMember.setMemberSerialId(maxId);
        if (businessMember.getId()==null||"".equals(businessMember.getId())){
            businessMember.setId(UUID.randomUUID().toString());
        }
        memberDao.addMemberInfo(businessMember);
    }
    /**
     * 获取门店中所有会员信息
     * @param StoreId
     * @return
     */
    @Override
    public List<ResponseMember> queryMemberListByStoreId(String StoreId,Integer page,Integer pageSize) {
        BatchDataPage batchDataPage = new BatchDataPage();
        batchDataPage.setPage(page);
        if (pageSize!=null&&pageSize!=0){
            batchDataPage.setPagesize(pageSize);
        }
        return memberDao.queryMemberListByStoreId(StoreId, batchDataPage.getOffset(),
                batchDataPage.getPagesize());
    }

    /**
     * 根据会员名称获取会员信息
     * @param storeId
     * @param requeryParam
     * @return
     */
    @Override
    public List<ResponseMember> queryMemberListByStoreIdAndName(String storeId,String requeryParam,Integer page,Integer pageSize) {
        BatchDataPage batchDataPage = new BatchDataPage();
        batchDataPage.setPage(page);
        if (pageSize!=null&&pageSize!=0){
            batchDataPage.setPagesize(pageSize);
        }
        return memberDao.queryMemberListByStoreIdAndName(storeId, requeryParam, batchDataPage.getOffset(),
                batchDataPage.getPagesize());
    }

    /**
     * 设置会员标签
     * @param businessMember
     */
    @Override
    public void updatememberTags(BusinessMember businessMember) {
        memberDao.updatememberTags(businessMember);
    }

    /**
     * 编辑会员信息
     * @param businessMember
     */
    @Override
    public void updateMeberInfo(BusinessMember businessMember) {
        memberDao.updateMeberInfo(businessMember);
    }

    /**
     * 会员请假
     * @param memberLeave
     */
    @Override
    public void addMemberLeaveInfo(MemberLeave memberLeave) {

        try {

            SchedulerUtil schedulerUtil = new SchedulerUtil();
            schedulerUtil.setJobClass(MemberLeaveJob.class);
            schedulerUtil.executeJobWithCronTrigger();
            memberLeave.setId(UUID.randomUUID().toString());
            memberDao.addMemberLeaveInfo(memberLeave);
        }catch (Exception e){

        }
    }

    /**
     * 获取会员请假信息
     * @param memberLeave
     */
    @Override
    public MemberLeave getMemberLeaveById(MemberLeave memberLeave) {

        return  memberDao.getMemberLeaveById(memberLeave);
    }

    /**
     * 审核会员请假信息
     * @param memberLeave
     */
    @Override
    public void  memberApprove(MemberLeave memberLeave) {
        memberDao.memberApprove(memberLeave);
    }

    /**
     * 删除会员请假信息
     * @param memberLeave
     */
    @Override
    public void  deleteMemberApprove(MemberLeave memberLeave) {
        memberDao.deleteMemberApprove(memberLeave);
    }

    /**
     * 获取商家会员请假基础信息
     * @param bussinessMemberLeave
     */
    @Override
    public BussinessMemberLeave getBusinessMemberLeaveInfo(BussinessMemberLeave bussinessMemberLeave) {
        return  memberDao.getBusinessMemberLeaveInfo(bussinessMemberLeave);
    }

    /**
     * 新增会员评价
     * @param memberEvaluate
     */
    @Override
    public void addMemberEvaluate(MemberEvaluate memberEvaluate) {
        memberEvaluate.setId(UUID.randomUUID().toString());
        String memberEvaluateId = RedisCommon.getMaxMemberEvaluateId(memberEvaluate.getEmpId());
        if("".equals(memberEvaluateId)||memberEvaluateId==null){
            String maxMemberEvaluateId = memberDao.getMemberEvaluateIdMax(memberEvaluate.getEmpId());
            memberEvaluateId = RedisCommon.setAndReturnMaxMemberEvaluateId(memberEvaluate.getEmpId() ,maxMemberEvaluateId);
        }
        memberEvaluate.setEvaluateSerialId(memberEvaluateId);
        memberDao.addMemberEvaluate(memberEvaluate);
    }
}
