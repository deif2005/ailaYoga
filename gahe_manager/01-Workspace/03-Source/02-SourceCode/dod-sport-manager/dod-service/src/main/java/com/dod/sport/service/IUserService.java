package com.dod.sport.service;

import com.dod.sport.domain.po.Base.BusinessMember;
import com.dod.sport.domain.po.Base.ClientUser;
import com.dod.sport.domain.po.Base.ManagerUser;

import java.util.List;

/**
 * IUserService
 * 商家端用户信息相关service
 * Created by defi on 2017/7/22.
 */
public interface IUserService {

    /**
     * 获取管理端员工列表
     * @param page
     * @return
     */
    public List<ManagerUser> listManagerUser(Integer page);

    /**
     * 获取手机号对应用户
     * @param user
     * @return
     */
    public ManagerUser getUserInfoByPhoneNum(ManagerUser user);

    /**
     * 新增管理端用户
     * @param user
     */
    public void addManagerUser(ManagerUser user);

    /**
     * 注册平台用户
     * @param businessMember
     */
    public void addClientUser(BusinessMember businessMember);



}
