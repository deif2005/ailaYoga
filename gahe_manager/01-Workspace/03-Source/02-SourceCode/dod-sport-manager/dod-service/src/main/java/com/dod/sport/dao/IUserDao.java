package com.dod.sport.dao;

import com.dod.sport.domain.po.Base.BusinessMember;
import com.dod.sport.domain.po.Base.ClientUser;
import com.dod.sport.domain.po.Base.ManagerUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserDao
 * 用户dao
 * Created by defi on 2017/7/22.
 */
@Repository
public interface IUserDao {

    /**
     * 获取全部用户
     * @param phoneNum
     * @param password
     * @return
     */
    public ManagerUser getUserInfo(@Param("phoneNum") String phoneNum,
                                   @Param("password") String password);

    /**
     * 获取手机号对应用户
     * @param user
     * @return
     */
    public ManagerUser getUserInfoByPhoneNum(ManagerUser user);

    /**
     * 新增管理端用户信息
     * @param user
     */
    public void addManagerUser(ManagerUser user);

    /**
     * 更新管理端用户信息
     * @param user
     */
    public void updateManagerUser(ManagerUser user);

    /**
     * 获取最大管理端用户流水号
     * @return
     */
    public String getMaxManagerUserSerialId();

    /**
     * 获取管理端用户列表
     * @return
     */
    public List<ManagerUser> listManagerUser(@Param("startPage") Integer startPage,
                                             @Param("endPage") Integer endPage);

    /**
     * 获取管理端用户记录笔数
     * @return
     */
    public String getManagerUserCount();

    /**
     * 获取平台用户详情
     * @return
     */
    public ClientUser getClientUserInfo(BusinessMember businessMember);

    /**
     * 获取平台用户最大编号
     * @return
     */
    public String getMaxClientUserId();

    /**
     * 新增平台用户
     * @param clientUser
     */
    public void addClientUser(ClientUser clientUser);

    /**
     * 更新平台用户信息
     * @param clientUser
     */
    public void updateClientUser(ClientUser clientUser);

    /**
     * 获取客户端用户列表
     * @param startPage
     * @param endPage
     * @return
     */
    public List<ClientUser> listClientUser(@Param("startPage") Integer startPage,
                                           @Param("endPage") Integer endPage);

    /**
     * 获取客户端用户记录笔数
     * @return
     */
    public String getClientUserCount();

    /**
     * 该手机号员工是否已存在
     * @param phoneNum
     * @param id
     * @return
     */
    public String isExistsUserPhoneNum(@Param("phoneNum") String phoneNum,
                                       @Param("id") String id);
}
