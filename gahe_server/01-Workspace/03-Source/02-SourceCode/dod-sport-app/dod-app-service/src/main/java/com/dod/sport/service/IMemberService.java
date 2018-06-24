package com.dod.sport.service;

import com.dod.sport.domain.po.Base.BusinessMember;
import com.dod.sport.domain.po.BussinessMemberLeave;
import com.dod.sport.domain.po.Member.MemberEvaluate;
import com.dod.sport.domain.po.Member.MemberLeave;
import com.dod.sport.domain.po.ResponseMember;

import java.util.List;

/**
 *
 * Created by Administrator on 2017/8/25.
 */

public interface IMemberService {
    /**
     * 获取会员信息
     * @param responseMember
     * @return
     */
    public ResponseMember queryMemberInfo(BusinessMember responseMember);

    /**
     * 获取会员关系列表
     * @param userId
     * @return
     */
    public List<ResponseMember> listMemberInfoByUserId(String userId);

    /**
     * 注册会员
     * @param queryMember
     */
    public void registerMemberInfo(BusinessMember  queryMember);

    /**
     * 新增会员信息
     * @param
     */
    public void addMemberInfo(BusinessMember businessMember);

    /**
     * 获取门店中所有会员信息
     * @param StoreId
     * @return
     */
    public List<ResponseMember> queryMemberListByStoreId(String StoreId,Integer page,Integer pageSize);

    /**
     * 根据会员名称获取会员信息
     * @param storeId
     * @param requeryParam
     * @return
     */
    public List<ResponseMember> queryMemberListByStoreIdAndName(String storeId,String requeryParam,Integer page,Integer pageSize);

    /**
     * 设置会员标签
     * @param businessMember
     */
    public void updatememberTags(BusinessMember businessMember);

    /**
     * 编辑会员信息
     * @param businessMember
     */
    public void updateMeberInfo(BusinessMember businessMember);

    /**
     * 新增会员请假
     * @param memberLeave
     */
    public void addMemberLeaveInfo(MemberLeave memberLeave);

    /**
     * 获取会员请假信息
     * @param memberLeave
     */
    public MemberLeave getMemberLeaveById(MemberLeave memberLeave);

    /**
     * 审核会员请假信息
     * @param memberLeave
     */
    public void memberApprove(MemberLeave memberLeave);

   /**
     * 删除会员请假信息
     * @param memberLeave
     */
    public void deleteMemberApprove(MemberLeave memberLeave);

   /**
     * 获取商家会员请假信息
     * @param bussinessMemberLeave
     */
    public BussinessMemberLeave getBusinessMemberLeaveInfo( BussinessMemberLeave  bussinessMemberLeave);

    /**
     * 新增会员评价
     */
    public void  addMemberEvaluate(MemberEvaluate memberEvaluate);
}
