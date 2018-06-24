package com.dod.sport.dao;

import com.dod.sport.domain.po.Base.BaseBusinessInfo;
import com.dod.sport.domain.po.Base.BusinessInfo;
import com.dod.sport.domain.po.Base.ClientUser;
import com.dod.sport.domain.po.Member.MembercardRelation;
import com.dod.sport.domain.po.ResponseMember;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.imageio.stream.IIOByteBuffer;
import java.util.List;

/**
 * Created by Administrator on 2017/10/9.
 */
@Repository
public interface IClientUserDao {

    /**
     * 新增平台用户
     * @param clientUser
     */
    public void addClientUser(ClientUser clientUser );

    /**
     * 登陆
     * @param phoneNum
     * @param password
     * @return
     */
    public ResponseMember loginClientUserInfo(@Param("phoneNum")String phoneNum, @Param("password") String password);
    /**
     * 获取平台用户详情
     * @return
     */
    public ClientUser getClientUserInfo(ClientUser clientUser);

    /**
     * 获取平台用户列表
     * @param sex
     * @return
     */
    public List<ClientUser> listClientUserInfo( @Param("sex") String sex,
                                                @Param("startPage") Integer startPage,
                                                @Param("endPage") Integer endPage);

    /**
     * 更新平台用户信息
     * @param clientUser
     */
    public void updateClientUser(ClientUser clientUser);

    /**
     * 获取平台用户最大编号
     * @return
     */
    public String getMaxClientUserId();

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
    public List<MembercardRelation>listUserMembercard( @Param("userId")String userId,@Param("businessId")String businessId);

}
