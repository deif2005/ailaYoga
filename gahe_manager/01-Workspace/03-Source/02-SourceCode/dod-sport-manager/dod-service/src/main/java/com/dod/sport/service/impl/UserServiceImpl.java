package com.dod.sport.service.impl;

import com.dod.sport.constant.RedisConstants;
import com.dod.sport.dao.IUserDao;
import com.dod.sport.domain.common.BatchDataPage;
import com.dod.sport.domain.common.BusiException;
import com.dod.sport.domain.po.Base.BusinessMember;
import com.dod.sport.domain.po.Base.ClientUser;
import com.dod.sport.domain.po.Base.ManagerUser;
import com.dod.sport.service.IUserService;
import com.dod.sport.util.CommonEnum;
import com.dod.sport.util.RedisCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * UserService
 * 用户信息相关serviceImpl
 * Created by defi on 2017/7/22.
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    IUserDao userDao;

    /**
     * 获取管理端员工列表
     * @param page
     * @return
     */
    public List<ManagerUser> listManagerUser(Integer page){
        BatchDataPage batchDataPage = new BatchDataPage();
        batchDataPage.setPage(page);
        List<ManagerUser> managerUserList = userDao.listManagerUser(batchDataPage.getOffset(),batchDataPage.getPagesize());
        return managerUserList;
    }

    /**
     * 新增管理端用户
     * @param user
     */
    public void addManagerUser(ManagerUser user){
        ManagerUser managerUser = userDao.getUserInfoByPhoneNum(user);
        if (managerUser != null){
            throw new BusiException(CommonEnum.ReturnCode.ManagerCode.manager_err_registered.getValue());
        }
        user.setId(UUID.randomUUID().toString());
        String maxId = RedisCommon.getMaxManagerUseSerialId();
        if ("".equals(maxId)){
            String nowId = userDao.getMaxManagerUserSerialId();
            maxId = RedisCommon.setAndReturnMaxManagerUserSerialId(nowId);
        }
        user.setEmployeeSerialId(maxId);
        userDao.addManagerUser(user);
    }

    /**
     * 获取手机号对应用户
     * @param user
     * @return
     */
    public ManagerUser getUserInfoByPhoneNum(ManagerUser user){
        return userDao.getUserInfoByPhoneNum(user);
    }

    /**
     * 注册平台用户
     * @param businessMember
     */
    @Override
    public void addClientUser(BusinessMember businessMember) {
        if (businessMember.getId()==null||businessMember.getId()== ""){
            businessMember.setId( UUID.randomUUID().toString());
        }
        String userSerialId = RedisCommon.getMaxClientUserId();
        if(userSerialId == ""||userSerialId==null){
            String maxClientUserId = userDao.getMaxClientUserId();
            userSerialId = RedisCommon.setAndReturnMaxClientUserId(maxClientUserId);
        }
        businessMember.setUserSerialId(userSerialId);
        userDao.addClientUser(businessMember);
    }
}
