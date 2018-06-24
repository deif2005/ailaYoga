package com.dod.sport.dao;

import com.dod.sport.domain.po.Base.BusinessMember;
import com.dod.sport.domain.po.BussinessMemberLeave;
import com.dod.sport.domain.po.Member.MemberEvaluate;
import com.dod.sport.domain.po.Member.MemberLeave;
import com.dod.sport.domain.po.ResponseMember;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/8/28.
 */
@Repository
public interface IMemberDao {

    /**
     * 获取会员信息
     * @param businessMember
     * @return
     */
    public ResponseMember queryMemberInfo(BusinessMember businessMember);

    /**
     * 根据用户id获取信息
     * @param businessMember
     * @return
     */
    public ResponseMember getBuseinessMemberByUserId(BusinessMember businessMember);

    /**
     * 获取会员关系列表
     * @param userId
     * @return
     */
    public List<ResponseMember> listMemberInfoByUserId(String userId);

    /**
     * 新增会员信息
     * @param businessMember
     */
    public void addMemberInfo(BusinessMember businessMember);

    /**
     * 获取会员编号
     * @return
     */
    public String getMaxMemberId(String businessId);

    /**
     * 获取门店中所有会员信息
     * @param storeId
     * @return
     */
    public List<ResponseMember> queryMemberListByStoreId(@Param("storeId")String storeId,
                                                         @Param("startPage")Integer startPage,
                                                         @Param("endPage")Integer endPage);

    /**
     *
     * @param storeId
     * @param requeryParam
     * @return
     */
    public List<ResponseMember> queryMemberListByStoreIdAndName(@Param("storeId")String storeId,
                                                                @Param("requeryParam")String requeryParam,
                                                                @Param("startPage")Integer startPage,
                                                                @Param("endPage")Integer endPage);

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
     * 会员请假
     * @param memberLeave
     */
    public void addMemberLeaveInfo(MemberLeave memberLeave);

    /**
     * 获取会员请假
     * @param memberLeave
     */
    public MemberLeave getMemberLeaveById(MemberLeave memberLeave);

    /**
     * 审核会员请假
     * @param memberLeave
     */
    public void memberApprove(MemberLeave memberLeave);

    /**
     * 删除会员请假
     * @param memberLeave
     */
    public void deleteMemberApprove(MemberLeave memberLeave);

    /**
     * 获取商家会员请假基础数据
     * @param bussinessMemberLeave
     */
    public BussinessMemberLeave getBusinessMemberLeaveInfo(BussinessMemberLeave bussinessMemberLeave);

    /**
     * 新增会员评价
     */
    public void addMemberEvaluate(MemberEvaluate memberEvaluate);

    /**
     * 获取会员评价最大编号
     * @param empRelationId
     * @return
     */
    public String getMemberEvaluateIdMax(String empRelationId);

}
