package com.dod.sport.service;

import com.dod.sport.domain.po.Base.BaseBusinessInfo;
import com.dod.sport.domain.po.Base.BusinessInfo;
import com.dod.sport.domain.po.Base.ClientUser;
import com.dod.sport.domain.po.Member.MembercardRelation;
import com.dod.sport.domain.po.ResponseMember;

import java.text.ParseException;
import java.util.List;

/**
 * Created by Administrator on 2017/10/13.
 */
public interface IClientUserService {
    /**
     * 新增平台用户
     * @param clientUser
     */
    public void addClientUser(ClientUser clientUser );

    /**
     * 平台用户登陆
     * @return
     */
    public ResponseMember loginClientUserInfo(ClientUser clientUser);

    /**
     * 获取平台用户详情
     * @return
     */
    public ClientUser  getClientUserInfo(ClientUser clientUser);

    /**
     * 获取用户的商家列表
     * @param phoneNum
     * @return
     */
    public List<BusinessInfo> listBusinessByPhoneNum(String phoneNum);
    /**
     * 获取平台用户列表
     * @param sex
     * @return
     */
    public List<ClientUser> listClientUserInfo(  String sex,Integer startPage, Integer endPage);

    /**
     * 更新平台用户信息
     * @param clientUser
     */
    public void updateClientUser(ClientUser clientUser);

    /**
     * 根据电话获取平台用户信息
     * @param phoneNum
     * @return
     */
    public ClientUser  getClientUserByPhone(String phoneNum);

    /**
     * 获取平台用户商家列表
     * @param phoneNum
     * @return
     */
    public List<BaseBusinessInfo>listClientUserBusiness(String phoneNum);

    /**
     * 获取用户的会员卡列表
     * @param userId
     * @param businessId
     * @return
     */
    public List<MembercardRelation>listUserMembercard(String userId,String businessId) throws ParseException;
}
