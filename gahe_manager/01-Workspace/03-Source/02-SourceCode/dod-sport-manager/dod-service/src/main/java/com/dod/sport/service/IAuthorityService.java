package com.dod.sport.service;

import com.dod.sport.domain.po.System.*;
import java.util.List;

/**
 * Created by defi on 2017/9/14.
 */
public interface IAuthorityService {

    public void init();

    public void addUserRole(UserRole userRole);

    public List<UserRole> listUserRoleByPage(String platform, String businessId, Integer page);

    public List<SystemModel> listSystemModelByPage(String platform, Integer page);

    public List<SystemFunction> listSystemFunctionByPage(String platform, String modelSerialId, Integer page);

    public List<UserRole> listUserRole(String platform, String businessId);

    public List<SystemModel> listSystemModel(String platform);

    public List<SystemFunction> listSystemFunction(String platform, String modelSerialId);

    public void addSystemModel(SystemModel systemModel);

    public void addSystemFunction(SystemFunction systemFunction);

    public List<SystemModel> listAllModelByPlatform(String platform);

    public List<SystemFunction> listAllFunctionByModelId(String platform, String modelId);

    public List<SystemModel> listSelectedModelByPlatform(String roleCode, String platform);

    public List<SystemFunction> listSelectedFunctionByModelId(String roleCode, String platform, String modelId);

    public void addFunctionDetail(FunctionDetail functionDetail);

    public void delUserRole(String id,String platform);

    public boolean existsUserUrl(String roleId,String url);

    public UserRole getUserRoleById(String id);

    public List<PlatformMenus> listPlatformMenus(String platform);

    public List<PlatformMenus> getRoleMenus(List<PlatformMenus> platformMenusList, UserRole roleInfo);

    public List<FunctionDetail> listFunctionDetailByPage(String platform, String modelSerialId, String functionSerialId,
                                                         Integer page, String isPublic);

    public List<FunctionDetail> listFunctionDetail(String platform, String modelSerialId, String functionSerialId,
                                                   String isPublic);

    /**
     * 获取公共接口
     * @param platform
     * @return
     */
    public List<FunctionDetail> listFunctionDetailForPublic(String platform, String modelSerialId);

    /**
     * 删除模块
     * @param modelSerialId
     */
    public void delSystemModel(String modelSerialId,String platform);

    /**
     * 删除系统功能
     * @param functionSerialId
     */
    public void delSystemFunction(String modelSerialId,String functionSerialId,String platform);

    /**
     * 删除功能细节项
     * @param detailSerialId
     */
    public void delFunctionDetail(String detailSerialId,String platform,String isPublic,String url);

    /**
     * 更新用户角色信息
     * @param userRole
     */
    public void updateUserRole(UserRole userRole);

    /**
     * 更新功能信息
     * @param systemModel
     */
    public void updateSystemModel(SystemModel systemModel);

    /**
     * 更新功能信息
     * @param systemFunction
     */
    public void updateSystemFunction(SystemFunction systemFunction);

    /**
     * 更新功能细节
     * @param functionDetail
     */
    public void updateFunctionDetail(FunctionDetail functionDetail);

    /**
     * 获取用户菜单项
     * @param userRoleId
     * @return
     */
    public List<PlatformMenus> getUserMenus(String userRoleId);

    /**
     * 初始化角色菜单项
     * @param userRoleList
     */
    public void initUserMenu(List<UserRole> userRoleList);
}
