package com.dod.sport.dao;

import com.dod.sport.domain.po.System.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by defi on 2017-09-13.
 * 系统权限dao
 */
@Repository
public interface ISystemAuthDao {

    public void addSystemModel(SystemModel systemModel);

    public void addUserRole(UserRole userRole);

    public void addSystemFunction(SystemFunction systemFunction);

    public List<SystemModel> listSystemModel(@Param("platform") String platform,
                                             @Param("startPage") Integer startPage,
                                             @Param("endPage") Integer endPage);

    public List<UserRole> listUserRole(@Param("platform") String platform,
                                       @Param("businessId") String businessId,
                                       @Param("startPage") Integer startPage,
                                       @Param("endPage") Integer endPage);

    public List<SystemFunction> listSystemFunction(@Param("platform") String platform,
                                                   @Param("modelSerialId") String modelSerialId,
                                                   @Param("startPage") Integer startPage,
                                                   @Param("endPage") Integer endPage);

    public String getMaxModelId(String platform);

    public String getMaxFunctionId(String platform);

    public String getMaxDetailSerialId(String platform);

    public SystemModel getSystemModel(String id);

    public SystemFunction getSystemFunction(String id);

    public void updateUserRole(UserRole UserRole);

    public void updateSystemModel(SystemModel systemModel);

    public void updateSystemFunction(SystemFunction systemFunction);

    public void addFunctionDetail(FunctionDetail functionDetail);

    public FunctionDetail getFunctionDetail(String id);

    public void updateFunctionDetail(FunctionDetail functionDetail);

    public void deleteUserRole(String id);

    /**
     * 获取平台菜单项
     * @param platform
     * @return
     */
    public List<PlatformMenus> listMenusByPlatform(String platform);

    /**
     * 获取平台uri资源
     * @param platform
     * @return
     */
    public List<PlatformMenus> getUriByPlatform(String platform);

    /**
     * 获取角色菜单及权限信息
     * @param id
     * @return
     */
    public UserRole getUserRoleById(String id);

    /**
     * 根据流水号获取功能细节
     * @param detailSerialId
     * @param platform
     * @return
     */
    public FunctionDetail getDetailUrlBySerialId(@Param("detailSerialId") String detailSerialId,
                                                 @Param("platform") String platform);

    /**
     * 获取功能细节列表
     * @param modelSerialId
     * @param functionSerialId
     * @param platform
     * @param isPublic
     * @param startPage
     * @param endPage
     * @return
     */
    public List<FunctionDetail> listFunctionDetail(@Param("modelSerialId") String modelSerialId,
                                                   @Param("functionSerialId") String functionSerialId,
                                                   @Param("platform") String platform,
                                                   @Param("isPublic") String isPublic,
                                                   @Param("startPage") Integer startPage,
                                                   @Param("endPage") Integer endPage);

    /**
     * 获取角色记录笔数
     * @param platform
     * @param businessId
     * @return
     */
    public String getUserRoleCount(@Param("platform") String platform,
                                   @Param("businessId") String businessId);

    /**
     * 获取模块记录笔数
     * @param platform
     * @return
     */
    public String getSystemModelCount(@Param("platform") String platform);

    /**
     * 获取功能记录笔数
     * @param modelSerialId
     * @param platform
     * @return
     */
    public String getSystemFunctionCount(@Param("modelSerialId") String modelSerialId,
                                         @Param("platform") String platform);

    /**
     * 获取功能细节记录笔数
     * @param modelSerialId
     * @param functionSerialId
     * @param platform
     * @param isPublic
     * @return
     */
    public String getFunctionDetailCount(@Param("modelSerialId") String modelSerialId,
                                         @Param("functionSerialId") String functionSerialId,
                                         @Param("platform") String platform,
                                         @Param("isPublic") String isPublic);

    /**
     * 是否存在关联的用户角色
     * @param roleId
     * @return
     */
    public String existsUserByUserRoleId(String roleId);

    /**
     * 删除功能模块
     * @param modelSerialId
     */
    public void deleteSystemModel(@Param("modelSerialId") String modelSerialId,
                                  @Param("platform") String platform);

    /**
     * 模块是否在使用
     * @param modelSerialId
     */
    public String existsSystemModelInUsed(@Param("modelSerialId") String modelSerialId,
                                          @Param("platform") String platform);

    /**
     * 功能是否在使用
     * @param functionSerialId
     * @return
     */
    public String existsSystemFunctionInUsed(@Param("functionSerialId") String functionSerialId,
                                             @Param("platform") String platform);

    /**
     * 删除功能项
     * @param functionSerialId
     */
    public void deleteSystemFunction(@Param("functionSerialId") String functionSerialId,
                                     @Param("platform") String platform);

    /**
     * 删除功能细节项
     * @param detailSerialId
     */
    public void deleteFunctionDetail(@Param("detailSerialId") String detailSerialId,
                                     @Param("platform") String platform);

    /**
     * 系统模块是否有角色使用
     * @param modelSerialId
     * @return
     */
    public String IsSystemModelInUserRole(@Param("modelSerialId") String modelSerialId,
                                          @Param("platform") String platform);

    /**
     * 系统功能是否有角色使用
     * @param functionSerialId
     * @return
     */
    public String IsSystemFunctionInUserRole(@Param("functionSerialId") String functionSerialId,
                                             @Param("platform") String platform);

    /**
     * 功能细节是否有角色使用
     * @param detailSerialId
     * @return
     */
    public String IsFunctionDetailInUserRole(@Param("detailSerialId") String detailSerialId,
                                             @Param("platform") String platform);

    /**
     * 获取平台编号
     * @return
     */
    public List<String> getAllPlatform();
}
