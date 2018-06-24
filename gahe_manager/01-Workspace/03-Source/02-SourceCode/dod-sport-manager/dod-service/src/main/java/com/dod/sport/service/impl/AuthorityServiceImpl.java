package com.dod.sport.service.impl;

import com.dod.sport.constant.RedisConstants;
import com.dod.sport.constant.SysConfigConstants;
import com.dod.sport.dao.ISystemAuthDao;
import com.dod.sport.domain.common.BatchDataPage;
import com.dod.sport.domain.common.BusiException;
import com.dod.sport.domain.po.System.*;
import com.dod.sport.redis.IRedisRepository;
import com.dod.sport.service.IAuthorityService;
import com.dod.sport.util.CommonEnum;
import com.dod.sport.util.RedisCommon;
import com.dod.sport.zk.util.ConfigLoader;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.RoleInfo;
import java.util.*;

/**
 * Created by defi on 2017/9/14.
 */
@Service
public class AuthorityServiceImpl implements IAuthorityService {

    @Autowired
    ISystemAuthDao systemAuthDao;

    @Autowired
    IRedisRepository redisRepository;

    @Override
    public void init() {
        //初始化公共url资源权限
        initUrl();
        //查询用户权限(1，2级菜单及3级url资源)
        List<UserRole> userRoleList = systemAuthDao.listUserRole(null, null, 0, 0);
        //初始化用户菜单
        initUserMenu(userRoleList);
    }

    public void initUrl(){
        String url=null;
        Set<String> keys = redisRepository.keys("AUTH_PUBLIC_URL;*");
        //清空所有公共权限
        for(String key : keys){
            redisRepository.del(key);
        }
        Set<String> keys1 = redisRepository.keys("AUTH_USER_URL;*");
        //清空所有用户权限
        for(String key : keys1){
            redisRepository.del(key);
        }
        List<String> platformList = systemAuthDao.getAllPlatform();
        for (String platform:platformList){
            //初始缓存公有接口
            List<FunctionDetail> functionDetailList = systemAuthDao.listFunctionDetail("","",platform,"2",0,0);
            for(FunctionDetail dto : functionDetailList){
                if ("2".equals(dto.getIsPublic())){
                    redisRepository.set(String.format(RedisConstants.PUBLIC_URL_KEY,platform,dto.getDetailSerialId()),
                            dto.getDetailUri());
                }
            }
        }
        //初始缓存私有接口
        List<UserRole> userRoleList = listUserRole(SysConfigConstants.PLATFORM_ID,null);
        for (UserRole userRole:userRoleList){
            for(String detailId:userRole.getDetailList()){
                if (!redisRepository.exists(String.format(RedisConstants.SYSTEM_FUNCTION_DETAIL,1,userRole.getPlatform(),
                        detailId))){
                    FunctionDetail functionDetail = systemAuthDao.getDetailUrlBySerialId(detailId,
                            SysConfigConstants.PLATFORM_ID);
                    url = functionDetail.getDetailUri();
                }else {
                    url = (String)redisRepository.get(String.format(RedisConstants.SYSTEM_FUNCTION_DETAIL,1,
                            userRole.getPlatform(), detailId));
                }
                redisRepository.set(String.format(RedisConstants.USER_URL_KEY,userRole.getId(),userRole.getPlatform(),
                        url),url);
            }
        }
    }

    /**
     * 初始化角色菜单项
     * @param userRoleList
     */
    @Override
    public void initUserMenu(List<UserRole> userRoleList){
        List<PlatformMenus> platformMenusList = null;
        for (UserRole userRole:userRoleList){
            //缓存该角色的菜单项
            if (redisRepository.exists(String.format(RedisConstants.PLATFORM_MENUS,userRole.getPlatform()))){
                String menus = (String)redisRepository.get(String.format(RedisConstants.PLATFORM_MENUS,
                        userRole.getPlatform()));
                JSONArray jsonArray = JSONArray.fromObject(menus);
                platformMenusList = (List)jsonArray.toCollection(jsonArray,PlatformMenus.class);
            }else {
                platformMenusList = listPlatformMenus(userRole.getPlatform());
                redisRepository.set(String.format(RedisConstants.PLATFORM_MENUS,userRole.getPlatform()),
                        JSONArray.fromObject(platformMenusList).toString());
            }
            if (userRole.getModelSerialIdstr()!=null){
                String[] modelStr = userRole.getModelSerialIdstr().split(",");
                List<String> modelList = Arrays.asList(modelStr);
                userRole.setModelList(modelList);
            }
            if (userRole.getFunctionSerialIdstr()!=null){
                String[] functionStr = userRole.getFunctionSerialIdstr().split(",");
                List<String> functionList = Arrays.asList(functionStr);
                userRole.setFunctionList(functionList);
            }
            if (userRole.getDetailSerialIdstr()!=null){
                String[] detailStr = userRole.getDetailSerialIdstr().split(",");
                List<String> detailList = Arrays.asList(detailStr);
                userRole.setDetailList(detailList);
            }

            List<PlatformMenus> roleMenus = getRoleMenus(platformMenusList,userRole);
            redisRepository.set(String.format(RedisConstants.USER_MENU_KEY,userRole.getId(),userRole.getPlatform()),
                    JSONArray.fromObject(roleMenus).toString());
        }
    }

    /**
     * 新增用户角色
     * @param userRole
     */
    @Override
    public void addUserRole(UserRole userRole){
        String url;
        List<PlatformMenus> platformMenusList=null;
        userRole.setId(UUID.randomUUID().toString());
        systemAuthDao.addUserRole(userRole);
        if (userRole.getModelSerialIdstr()!=null){
            String[] modelStr = userRole.getModelSerialIdstr().split(",");
            List<String> modelList = Arrays.asList(modelStr);
            userRole.setModelList(modelList);
        }
        if (userRole.getFunctionSerialIdstr()!=null){
            String[] functionStr = userRole.getFunctionSerialIdstr().split(",");
            List<String> functionList = Arrays.asList(functionStr);
            userRole.setFunctionList(functionList);
        }
        if (userRole.getDetailSerialIdstr()!=null){
            String[] detailStr = userRole.getDetailSerialIdstr().split(",");
            List<String> detailList = Arrays.asList(detailStr);
            userRole.setDetailList(detailList);
        }

        //缓存角色权限信息
        for(String detailId:userRole.getDetailList()){
            if (!redisRepository.exists(String.format(RedisConstants.SYSTEM_FUNCTION_DETAIL,
                    CommonEnum.DBData.AuthorityType.AuthPrivate.getValue(),userRole.getPlatform(),detailId))){
                FunctionDetail functionDetail = systemAuthDao.getDetailUrlBySerialId(detailId,
                        userRole.getPlatform());
                url = functionDetail.getDetailUri();
            }else {
                url = (String)redisRepository.get(String.format(RedisConstants.SYSTEM_FUNCTION_DETAIL,
                        CommonEnum.DBData.AuthorityType.AuthPrivate.getValue(),userRole.getPlatform(),detailId));
            }
            redisRepository.set(String.format(RedisConstants.USER_URL_KEY,userRole.getId(),userRole.getPlatform(),
                            url),url);
        }
        //缓存该角色的菜单项
        if (redisRepository.exists(String.format(RedisConstants.PLATFORM_MENUS,SysConfigConstants.PLATFORM_ID))){
            String menus = (String)redisRepository.get(String.format(RedisConstants.PLATFORM_MENUS,
                    SysConfigConstants.PLATFORM_ID));
            JSONArray jsonArray = JSONArray.fromObject(menus);
            platformMenusList = (List)jsonArray.toCollection(jsonArray,PlatformMenus.class);
        }else {
            platformMenusList = listPlatformMenus(SysConfigConstants.PLATFORM_ID);
            redisRepository.set(String.format(RedisConstants.PLATFORM_MENUS,SysConfigConstants.PLATFORM_ID),
                    JSONArray.fromObject(platformMenusList).toString());
        }
        List<PlatformMenus> roleMenus = getRoleMenus(platformMenusList,userRole);
        redisRepository.set(String.format(RedisConstants.USER_MENU_KEY,userRole.getId(),userRole.getPlatform()),
                JSONArray.fromObject(roleMenus).toString());
    }

    /**
     * 获取用户菜单项
     * @param userRoleId
     * @return
     */
    @Override
    public List<PlatformMenus> getUserMenus(String userRoleId){
        List<PlatformMenus> platformMenusList = null;
        List<PlatformMenus> roleMenus = null;
        if (redisRepository.exists(String.format(RedisConstants.USER_MENU_KEY,userRoleId,
                SysConfigConstants.PLATFORM_ID))){
            platformMenusList = listPlatformMenus(SysConfigConstants.PLATFORM_ID);
            UserRole userRole = getUserRoleById(userRoleId);
            roleMenus = getRoleMenus(platformMenusList,userRole);
            redisRepository.set(String.format(RedisConstants.USER_MENU_KEY,userRoleId,SysConfigConstants.PLATFORM_ID),
                    JSONArray.fromObject(roleMenus).toString());
        }else {
            String menusStr = (String)redisRepository.get(String.format(RedisConstants.USER_MENU_KEY,userRoleId,
                    SysConfigConstants.PLATFORM_ID));
            JSONArray jsonArray = JSONArray.fromObject(menusStr);
            roleMenus = (List)jsonArray.toCollection(jsonArray,PlatformMenus.class);
        }
        return roleMenus;
    }

    /**
     * 增加系统功能模块
     * @param systemModel
     */
    @Override
    public void addSystemModel(SystemModel systemModel){
        String maxId = RedisCommon.getMaxModelId(systemModel.getPlatform());
        if ("".equals(maxId)){
            maxId = RedisCommon.setAndReturnMaxModelId(systemModel.getPlatform(),
                    systemAuthDao.getMaxModelId(systemModel.getPlatform()));
        }
        systemModel.setId(UUID.randomUUID().toString());
        systemModel.setModelSerialId(maxId);
        systemAuthDao.addSystemModel(systemModel);
        List<SystemModel> systemModelList = systemAuthDao.listSystemModel(systemModel.getPlatform(), 0, 0);
        redisRepository.del(String.format(RedisConstants.SYSTEM_MODEL,systemModel.getPlatform()));
        redisRepository.set(String.format(RedisConstants.SYSTEM_MODEL,systemModel.getPlatform()),
                JSONArray.fromObject(systemModelList).toString());
        //        更新系统最新菜单内容至缓存
        List<PlatformMenus> platformMenusList = systemAuthDao.listMenusByPlatform(SysConfigConstants.PLATFORM_ID);
        if (redisRepository.exists(String.format(RedisConstants.PLATFORM_MENUS, SysConfigConstants.PLATFORM_ID))){
            redisRepository.del(String.format(RedisConstants.PLATFORM_MENUS, SysConfigConstants.PLATFORM_ID));
        }
        redisRepository.set(String.format(RedisConstants.PLATFORM_MENUS, SysConfigConstants.PLATFORM_ID),
                JSONArray.fromObject(platformMenusList).toString());
    }

    /**
     * 新增功能
     * @param systemFunction
     */
    @Override
    public void addSystemFunction(SystemFunction systemFunction){
        String maxId = RedisCommon.getMaxFunctionId(systemFunction.getPlatform());
        if ("".equals(maxId)){
            maxId = RedisCommon.setAndReturnMaxFunctionId(systemFunction.getPlatform(),
                    systemAuthDao.getMaxFunctionId(systemFunction.getPlatform()));
        }
        systemFunction.setId(UUID.randomUUID().toString());
        systemFunction.setFunctionSerialId(maxId);
        systemAuthDao.addSystemFunction(systemFunction);

        //更新系统菜单项至缓存
        List<PlatformMenus> platformMenusList = systemAuthDao.listMenusByPlatform(SysConfigConstants.PLATFORM_ID);
        if (redisRepository.exists(String.format(RedisConstants.PLATFORM_MENUS, SysConfigConstants.PLATFORM_ID))){
            redisRepository.del(String.format(RedisConstants.PLATFORM_MENUS, SysConfigConstants.PLATFORM_ID));
        }
        redisRepository.set(String.format(RedisConstants.PLATFORM_MENUS, SysConfigConstants.PLATFORM_ID),
                JSONArray.fromObject(platformMenusList).toString());

        List<SystemFunction> systemFunctionList = systemAuthDao.listSystemFunction(systemFunction.getPlatform(),
                systemFunction.getModelSerialId(), 0, 0);
        redisRepository.del(String.format(RedisConstants.SYSTEM_FUNCTION,systemFunction.getPlatform(),
                systemFunction.getModelSerialId()));
        redisRepository.set(String.format(RedisConstants.SYSTEM_FUNCTION,systemFunction.getPlatform(),
                systemFunction.getModelSerialId()),JSONArray.fromObject(systemFunctionList).toString());
    }

    /**
     * 新增功能细节
     * @param functionDetail
     */
    @Override
    public void addFunctionDetail(FunctionDetail functionDetail){
        String maxId = RedisCommon.getMaxFunctionDetailId(functionDetail.getPlatform());
        if ("".equals(maxId)){
            maxId = RedisCommon.setAndReturnMaxFunctionDetailId(functionDetail.getPlatform(),
                    systemAuthDao.getMaxDetailSerialId(functionDetail.getPlatform()));
        }
        functionDetail.setId(UUID.randomUUID().toString());
        functionDetail.setDetailSerialId(maxId);
        systemAuthDao.addFunctionDetail(functionDetail);
        //私有功能
        if ("1".equals(functionDetail.getIsPublic())){
            redisRepository.del(String.format(RedisConstants.SYSTEM_FUNCTION_DETAIL,functionDetail.getIsPublic(),
                    functionDetail.getPlatform(),functionDetail.getDetailSerialId()));
            redisRepository.set(String.format(RedisConstants.SYSTEM_FUNCTION_DETAIL,functionDetail.getIsPublic(),
                    functionDetail.getPlatform(),functionDetail.getDetailSerialId()),functionDetail.getDetailUri());
        }else if ("2".equals(functionDetail.getIsPublic())){ //公有功能直接缓存不必区分角色判断权限
            redisRepository.del(String.format(RedisConstants.PUBLIC_URL_KEY,functionDetail.getPlatform(),
                    functionDetail.getDetailUri()));
            redisRepository.set(String.format(RedisConstants.PUBLIC_URL_KEY,functionDetail.getPlatform(),
                    functionDetail.getDetailUri()),functionDetail.getDetailUri());
        }
    }

    /**
     * 更新用户角色信息
     * @param userRole
     */
    @Override
    public void updateUserRole(UserRole userRole){
        List<PlatformMenus> platformMenusList=null;
        String url = "";
        systemAuthDao.updateUserRole(userRole);
        //缓存角色权限信息
        if (userRole.getDetailList() != null){
            for(String detailId:userRole.getDetailList()){
                if (!redisRepository.exists(String.format(RedisConstants.SYSTEM_FUNCTION_DETAIL,
                        CommonEnum.DBData.AuthorityType.AuthPrivate.getValue(),userRole.getPlatform(),detailId))){
                    FunctionDetail functionDetail = systemAuthDao.getDetailUrlBySerialId(detailId,
                            SysConfigConstants.PLATFORM_ID);
                    url = functionDetail.getDetailUri();
                }else {
                    url = (String)redisRepository.get(String.format(RedisConstants.SYSTEM_FUNCTION_DETAIL,
                            CommonEnum.DBData.AuthorityType.AuthPrivate.getValue(),userRole.getPlatform(),detailId));
                }
                redisRepository.set(String.format(RedisConstants.USER_URL_KEY,userRole.getId(),userRole.getPlatform(),
                        url),url);
            }
        }

        //缓存该角色的菜单项
        if (redisRepository.exists(String.format(RedisConstants.PLATFORM_MENUS,SysConfigConstants.PLATFORM_ID))){
            String menus = (String)redisRepository.get(String.format(RedisConstants.PLATFORM_MENUS,
                    SysConfigConstants.PLATFORM_ID));
            JSONArray jsonArray = JSONArray.fromObject(menus);
            platformMenusList = (List)jsonArray.toCollection(jsonArray,PlatformMenus.class);
        }else {
            platformMenusList = listPlatformMenus(SysConfigConstants.PLATFORM_ID);
            redisRepository.set(String.format(RedisConstants.PLATFORM_MENUS,SysConfigConstants.PLATFORM_ID),
                    JSONArray.fromObject(platformMenusList).toString());
        }
        List<PlatformMenus> roleMenus = getRoleMenus(platformMenusList,userRole);
        redisRepository.set(String.format(RedisConstants.USER_MENU_KEY,userRole.getId(),userRole.getPlatform()),
                JSONArray.fromObject(roleMenus).toString());
    }

    /**
     * 更新系统模块信息
     * @param systemModel
     */
    @Override
    public void updateSystemModel(SystemModel systemModel){
        systemAuthDao.updateSystemModel(systemModel);
        List<SystemModel> systemModelList = systemAuthDao.listSystemModel(systemModel.getPlatform(), 0, 0);
        redisRepository.del(String.format(RedisConstants.SYSTEM_MODEL,systemModel.getPlatform()));
        redisRepository.set(String.format(RedisConstants.SYSTEM_MODEL,systemModel.getPlatform()),
                JSONArray.fromObject(systemModelList).toString());
        //更新系统最新菜单内容至缓存
        List<PlatformMenus> platformMenusList = systemAuthDao.listMenusByPlatform(SysConfigConstants.PLATFORM_ID);
        if (redisRepository.exists(String.format(RedisConstants.PLATFORM_MENUS, SysConfigConstants.PLATFORM_ID))){
            redisRepository.del(String.format(RedisConstants.PLATFORM_MENUS, SysConfigConstants.PLATFORM_ID));
        }
        redisRepository.set(String.format(RedisConstants.PLATFORM_MENUS, SysConfigConstants.PLATFORM_ID),
                JSONArray.fromObject(platformMenusList).toString());
    }

    /**
     * 更新功能信息
     * @param systemFunction
     */
    @Override
    public void updateSystemFunction(SystemFunction systemFunction){
        systemAuthDao.updateSystemFunction(systemFunction);
        //更新系统菜单缓存
        List<PlatformMenus> platformMenusList = systemAuthDao.listMenusByPlatform(SysConfigConstants.PLATFORM_ID);
        if (redisRepository.exists(String.format(RedisConstants.PLATFORM_MENUS, SysConfigConstants.PLATFORM_ID))){
            redisRepository.del(String.format(RedisConstants.PLATFORM_MENUS, SysConfigConstants.PLATFORM_ID));
        }
        redisRepository.set(String.format(RedisConstants.PLATFORM_MENUS, SysConfigConstants.PLATFORM_ID),
                JSONArray.fromObject(platformMenusList).toString());

        List<SystemFunction> systemFunctionList = systemAuthDao.listSystemFunction(systemFunction.getPlatform(),
                systemFunction.getModelSerialId(), 0, 0);
        redisRepository.del(String.format(RedisConstants.SYSTEM_FUNCTION,systemFunction.getPlatform(),
                systemFunction.getModelSerialId()));
        redisRepository.set(String.format(RedisConstants.SYSTEM_FUNCTION,systemFunction.getPlatform(),
                systemFunction.getModelSerialId()),JSONArray.fromObject(systemFunctionList).toString());
    }

    /**
     * 更新功能细节
     * @param functionDetail
     */
    public void updateFunctionDetail(FunctionDetail functionDetail){
        systemAuthDao.updateFunctionDetail(functionDetail);
        //私有功能
        if ("1".equals(functionDetail.getIsPublic())){
            redisRepository.del(String.format(RedisConstants.SYSTEM_FUNCTION_DETAIL,functionDetail.getIsPublic(),
                    functionDetail.getPlatform(),functionDetail.getDetailSerialId()));
            redisRepository.set(String.format(RedisConstants.SYSTEM_FUNCTION_DETAIL,functionDetail.getIsPublic(),
                    functionDetail.getPlatform(),functionDetail.getDetailSerialId()),functionDetail.getDetailUri());
        }else if ("2".equals(functionDetail.getIsPublic())){ //公有功能直接缓存
            redisRepository.del(String.format(RedisConstants.PUBLIC_URL_KEY,SysConfigConstants.PLATFORM_ID,
                    functionDetail.getDetailUri()));
            redisRepository.set(String.format(RedisConstants.PUBLIC_URL_KEY,SysConfigConstants.PLATFORM_ID,
                    functionDetail.getDetailUri()),functionDetail.getDetailUri());
        }
    }

    @Override
    public List<UserRole> listUserRoleByPage(String platform, String businessId, Integer page){
        BatchDataPage batchDataPage = new BatchDataPage();
        batchDataPage.setPage(page);
        List<UserRole> userRoleList = systemAuthDao.listUserRole(platform, businessId, batchDataPage.getOffset(),
                batchDataPage.getPagesize());
        return userRoleList;
    }

    @Override
    public List<SystemModel> listSystemModelByPage(String platform, Integer page){
        BatchDataPage batchDataPage = new BatchDataPage();
        batchDataPage.setPage(page);
        List<SystemModel> systemModelList = systemAuthDao.listSystemModel(platform, batchDataPage.getOffset(),
                batchDataPage.getPagesize());
        return systemModelList;
    }

    @Override
    public List<SystemFunction> listSystemFunctionByPage(String platform, String modelSerialId, Integer page){
        BatchDataPage batchDataPage = new BatchDataPage();
        batchDataPage.setPage(page);
        List<SystemFunction> systemModelList = systemAuthDao.listSystemFunction(platform, modelSerialId,
                batchDataPage.getOffset(), batchDataPage.getPagesize());
        return systemModelList;
    }

    /**
     * 获取功能细节列表
     * @param platform
     * @param functionSerialId
     * @param page
     * @return
     */
    @Override
    public List<FunctionDetail> listFunctionDetailByPage(String platform, String modelSerialId, String functionSerialId,
                                                         Integer page, String isPublic){
        BatchDataPage batchDataPage = new BatchDataPage();
        batchDataPage.setPage(page);
        List<FunctionDetail> functionDetailList = systemAuthDao.listFunctionDetail(modelSerialId,functionSerialId, platform,
                isPublic,batchDataPage.getOffset(), batchDataPage.getPagesize());
        return functionDetailList;
    }

    /**
     * 获取功能细节不分页
     * @param platform
     * @param functionSerialId
     * @return
     */
    @Override
    public List<FunctionDetail> listFunctionDetail(String platform, String modelSerialId, String functionSerialId,
                                                   String isPublic){
        List<FunctionDetail> functionDetailList = systemAuthDao.listFunctionDetail(modelSerialId,functionSerialId,
                platform,isPublic,0,0);
        return functionDetailList;
    }

    /**
     * 获取公共接口
     * @param platform
     * @return
     */
    @Override
    public List<FunctionDetail> listFunctionDetailForPublic(String platform, String modelSerialId){
        List<FunctionDetail> functionDetailList = systemAuthDao.listFunctionDetail(modelSerialId,"",platform,
                CommonEnum.DBData.AuthorityType.AuthPublic.getValue(),0,0);
        return functionDetailList;
    }

    /**
     * 获取角色列表
     * @param platform
     * @param businessId
     * @return
     */
    @Override
    public List<UserRole> listUserRole(String platform, String businessId){
        List<UserRole> userRoleList = systemAuthDao.listUserRole(platform,businessId,0,0);
        for (UserRole userRole:userRoleList){
            if (userRole.getModelSerialIdstr()!=null){
                String[] modelStr = userRole.getModelSerialIdstr().split(",");
                List<String> modelList = Arrays.asList(modelStr);
                userRole.setModelList(modelList);
            }
            if (userRole.getFunctionSerialIdstr()!=null){
                String[] functionStr = userRole.getFunctionSerialIdstr().split(",");
                List<String> functionList = Arrays.asList(functionStr);
                userRole.setFunctionList(functionList);
            }
            if (userRole.getDetailSerialIdstr()!=null){
                String[] detailStr = userRole.getDetailSerialIdstr().split(",");
                List<String> detailList = Arrays.asList(detailStr);
                userRole.setDetailList(detailList);
            }
        }
        return userRoleList;
    }

    @Override
    public List<SystemModel> listSystemModel(String platform){
        List<SystemModel> systemModelList = new ArrayList<>();
        if (redisRepository.exists(String.format(RedisConstants.SYSTEM_MODEL,platform))){
            String modelStr = (String)redisRepository.get(String.format(RedisConstants.SYSTEM_MODEL, platform));
            JSONArray jsonArray = JSONArray.fromObject(modelStr);
            systemModelList = (List)jsonArray.toCollection(jsonArray,SystemModel.class);
        }else {
            systemModelList = systemAuthDao.listSystemModel(platform,0,0);
            redisRepository.set(String.format(RedisConstants.SYSTEM_MODEL, platform),
                    JSONArray.fromObject(systemModelList).toString());
        }
        return systemModelList;
    }

    /**
     * 获取系统功能列表
     * @param platform
     * @param modelSerialId
     * @return
     */
    @Override
    public List<SystemFunction> listSystemFunction(String platform, String modelSerialId){
        List<SystemFunction> systemModelList = systemAuthDao.listSystemFunction(platform, modelSerialId,0,0);
        return systemModelList;
    }



    @Override
    public List<SystemModel> listAllModelByPlatform(String platform){
        List<SystemModel> systemModelList = new ArrayList();
        if (redisRepository.exists(String.format(RedisConstants.SYSTEM_MODEL, platform))){
            JSONArray jsonArray = JSONArray.fromObject(redisRepository.get(String.format(RedisConstants.SYSTEM_MODEL,
                    platform)));
            systemModelList = (List)jsonArray.toCollection(jsonArray,SystemModel.class);
        }else {
            systemModelList = systemAuthDao.listSystemModel(platform,0,0);
            redisRepository.set(String.format(RedisConstants.SYSTEM_MODEL, platform),
                    JSONArray.fromObject(systemModelList).toString());
        }
        return systemModelList;
    }

    @Override
    public List<SystemModel> listSelectedModelByPlatform(String id, String platform){
        UserRole userRole = getUserRoleById(id);
        List<String> modelList = userRole.getModelList();
        List<SystemModel> systemModelList = listAllModelByPlatform(platform);
        List<SystemModel> systemModelList1 = new ArrayList<>();
        for (SystemModel systemModel : systemModelList){
            for (String modelId : modelList){
                if (systemModel.getModelSerialId().equals(modelId)){
                    systemModelList1.add(systemModel);
                }
            }
        }
        return systemModelList1;
    }

    public List<SystemModel> listNoSelectedModelByPlatform(String roleId, String platform){
        List<SystemModel> systemModelList = listAllModelByPlatform(platform);
        List<SystemModel> systemModelList1 = listSelectedModelByPlatform(roleId, platform);
        for (SystemModel systemModel : systemModelList){
            for (SystemModel systemModel1 : systemModelList1){
                if (systemModel.getModelSerialId().equals(systemModel1.getModelSerialId())){
                    systemModelList.remove(systemModel);
                }
            }
        }
        return systemModelList;
    }

    @Override
    public List<SystemFunction> listAllFunctionByModelId(String platform, String modelId){
        List<SystemFunction> systemFunctionList = new ArrayList<>();
        if (redisRepository.exists(String.format(RedisConstants.SYSTEM_FUNCTION, platform, modelId))){
            JSONArray jsonArray = JSONArray.fromObject(redisRepository.get(String.format(RedisConstants.SYSTEM_FUNCTION,
                    platform, modelId)));
            systemFunctionList = (List)JSONArray.toCollection(jsonArray,SystemFunction.class);
        }else {
            systemFunctionList = systemAuthDao.listSystemFunction(platform,modelId,0,0);
            redisRepository.set(String.format(RedisConstants.SYSTEM_FUNCTION,
                    platform, modelId),JSONArray.fromObject(systemFunctionList).toString());
        }
        return systemFunctionList;
    }

    /**
     * 获取平台菜单项
     * @param platform
     * @return
     */
    @Override
    public List<PlatformMenus> listPlatformMenus(String platform){
        List<PlatformMenus> platformMenusList = new ArrayList<>();
        if (redisRepository.exists(String.format(RedisConstants.PLATFORM_MENUS, platform))){
            JSONArray jsonArray = JSONArray.fromObject(redisRepository.get(String.format(
                    RedisConstants.PLATFORM_MENUS, platform)));
            platformMenusList = (List)JSONArray.toCollection(jsonArray,PlatformMenus.class);
        }else {
            platformMenusList = systemAuthDao.listMenusByPlatform(platform);
            redisRepository.set(String.format(RedisConstants.PLATFORM_MENUS, platform),
                    JSONArray.fromObject(platformMenusList).toString());
        }
        return platformMenusList;
    }

    @Override
    public List<SystemFunction> listSelectedFunctionByModelId(String id, String platform, String modelId){
        UserRole userRole = getUserRoleById(id);
        List<String> functionList = userRole.getFunctionList();
        List<SystemFunction> systemFunctionList = listAllFunctionByModelId(platform,modelId);
        List<SystemFunction> systemFunctionList1 = new ArrayList<>();
        for (SystemFunction systemFunction : systemFunctionList){
            for (String functionId : functionList){
                if (systemFunction.getFunctionSerialId().equals(functionId)){
                    systemFunctionList1.add(systemFunction);
                }
            }
        }
        return systemFunctionList1;
    }

    public List<SystemFunction> listNoSelectedFunctionByModelId(String roleId, String platform, String modelId){
        List<SystemFunction> systemFunctionList = listAllFunctionByModelId(platform,modelId);
        List<SystemFunction> systemFunctionList1 = listSelectedFunctionByModelId(roleId, platform, modelId);
        for (SystemFunction systemFunction : systemFunctionList){
            for (SystemFunction systemFunction1 : systemFunctionList1){
                if (systemFunction.getFunctionSerialId().equals(systemFunction1.getFunctionSerialId())){
                    systemFunctionList.remove(systemFunction);
                }
            }
        }
        return systemFunctionList;
    }

    /**
     * 获取角色信息
     * @param id
     * @return
     */
    @Override
    public UserRole getUserRoleById(String id){
        UserRole userRole = systemAuthDao.getUserRoleById(id);
//        if (!redisRepository.exists(String.format(RedisConstants.SYSTEM_ROLE,id))){
//            userRole = systemAuthDao.getUserRoleById(id);
//            String data = JSONObject.fromObject(userRole).toString();
//            redisRepository.set(String.format(RedisConstants.SYSTEM_ROLE,id),data);
//        }else {
//            JSONObject jsonObject = JSONObject.fromObject(redisRepository.get(String.format(RedisConstants.SYSTEM_ROLE,
//                    id)));
//            userRole = (UserRole)JSONObject.toBean(jsonObject,UserRole.class);
//        }

        if (userRole.getModelSerialIdstr()!=null){
            String[] modelStr = userRole.getModelSerialIdstr().split(",");
            List<String> modelList = Arrays.asList(modelStr);
            userRole.setModelList(modelList);
        }
        if (userRole.getFunctionSerialIdstr()!=null){
            String[] functionStr = userRole.getFunctionSerialIdstr().split(",");
            List<String> functionList = Arrays.asList(functionStr);
            userRole.setFunctionList(functionList);
        }
        if (userRole.getDetailSerialIdstr()!=null){
            String[] detailStr = userRole.getDetailSerialIdstr().split(",");
            List<String> detailList = Arrays.asList(detailStr);
            userRole.setDetailList(detailList);
        }
        return userRole;
    }

    /**
     * 获取角色菜单
     * @param platformMenusList
     * @param userRole
     * @return
     */
    @Override
    public List<PlatformMenus> getRoleMenus(List<PlatformMenus> platformMenusList, UserRole userRole){
        List<PlatformMenus> platformMenuses = new ArrayList<>();
        if(userRole.getFunctionList()!=null){
            for (String functionSerialId:userRole.getFunctionList()){
                for (PlatformMenus platformMenus:platformMenusList){
                    if (functionSerialId.equals(platformMenus.getFunctionSerialId())){
                        PlatformMenus platformMenus1 = new PlatformMenus();
                        platformMenus1.setModelSerialId(platformMenus.getModelSerialId());
                        platformMenus1.setModelName(platformMenus.getModelName());
                        platformMenus1.setFunctionSerialId(platformMenus.getFunctionSerialId());
                        platformMenus1.setFunctionName(platformMenus.getFunctionName());
                        platformMenuses.add(platformMenus1);
                    }
                }
            }
        }
        return platformMenuses;
    }

    /**
     * 删除角色
     * @param id
     */
    @Override
    public void delUserRole(String id,String platform){
        if (!"0".equals(systemAuthDao.existsUserByUserRoleId(id))){
            throw new BusiException(CommonEnum.ReturnCode.ManagerCode.manager_err_userroleinused.getValue());
        }
        systemAuthDao.deleteUserRole(id);
        //清空角色权限
        Set<String> keys = redisRepository.keys(String.format(RedisConstants.USER_URL_KEY,id,platform)+"*");
        for(String key : keys){
            redisRepository.del(key);
        }
        //清空角色菜单
        Set<String> keys1 = redisRepository.keys(String.format(RedisConstants.USER_MENU_KEY,id,platform)+"*");
        for (String key : keys1){
            redisRepository.del(key);
        }
        //删除角色缓存
//        redisRepository.del(String.format(RedisConstants.SYSTEM_ROLE,id));
    }

    /**
     * 删除系统模块
     * @param modelSerialId
     */
    @Override
    public void delSystemModel(String modelSerialId, String platform){
        if (!"0".equals(systemAuthDao.existsSystemModelInUsed(modelSerialId,platform)) ||
                !"0".equals(systemAuthDao.IsSystemModelInUserRole(modelSerialId,platform))){
            throw new BusiException(CommonEnum.ReturnCode.ManagerCode.manager_err_functioninused.getValue());
        }
        systemAuthDao.deleteSystemModel(modelSerialId,platform);
        //更新redis缓存信息
        List<SystemModel> systemModelList = systemAuthDao.listSystemModel(SysConfigConstants.PLATFORM_ID, 0, 0);
        redisRepository.del(String.format(RedisConstants.SYSTEM_MODEL,SysConfigConstants.PLATFORM_ID));
        redisRepository.set(String.format(RedisConstants.SYSTEM_MODEL,SysConfigConstants.PLATFORM_ID),
                JSONArray.fromObject(systemModelList).toString());
        //更新系统最新菜单内容至缓存
        List<PlatformMenus> platformMenusList = systemAuthDao.listMenusByPlatform(SysConfigConstants.PLATFORM_ID);
        if (redisRepository.exists(String.format(RedisConstants.PLATFORM_MENUS, SysConfigConstants.PLATFORM_ID))){
            redisRepository.del(String.format(RedisConstants.PLATFORM_MENUS, SysConfigConstants.PLATFORM_ID));
        }
        redisRepository.set(String.format(RedisConstants.PLATFORM_MENUS, SysConfigConstants.PLATFORM_ID),
                JSONArray.fromObject(platformMenusList).toString());
    }

    /**
     * 删除系统功能
     * @param functionSerialId
     */
    @Override
    public void delSystemFunction(String modelSerialId,String functionSerialId,String platform){
        if (!"0".equals(systemAuthDao.existsSystemFunctionInUsed(functionSerialId,platform)) ||
                !"0".equals(systemAuthDao.IsSystemFunctionInUserRole(functionSerialId,platform))){
            throw new BusiException(CommonEnum.ReturnCode.ManagerCode.manager_err_functioninused.getValue());
        }
        systemAuthDao.deleteSystemFunction(functionSerialId,platform);

        //更新系统菜单项至缓存
        List<PlatformMenus> platformMenusList = systemAuthDao.listMenusByPlatform(SysConfigConstants.PLATFORM_ID);
        if (redisRepository.exists(String.format(RedisConstants.PLATFORM_MENUS, SysConfigConstants.PLATFORM_ID))){
            redisRepository.del(String.format(RedisConstants.PLATFORM_MENUS, SysConfigConstants.PLATFORM_ID));
        }
        redisRepository.set(String.format(RedisConstants.PLATFORM_MENUS, SysConfigConstants.PLATFORM_ID),
                JSONArray.fromObject(platformMenusList).toString());

        //更新redis缓存信息
        List<SystemFunction> systemFunctionList = systemAuthDao.listSystemFunction(SysConfigConstants.PLATFORM_ID,
                modelSerialId, 0, 0);
        redisRepository.del(String.format(RedisConstants.SYSTEM_FUNCTION,SysConfigConstants.PLATFORM_ID,
                modelSerialId));
        redisRepository.set(String.format(RedisConstants.SYSTEM_FUNCTION,SysConfigConstants.PLATFORM_ID,
                modelSerialId), JSONArray.fromObject(systemFunctionList).toString());
    }

    /**
     * 删除功能细节项
     * @param detailSerialId
     */
    @Override
    public void delFunctionDetail(String detailSerialId,String platform,String isPublic,String url){
        if (!"0".equals(systemAuthDao.existsSystemFunctionInUsed(detailSerialId,platform)) ||
                !"0".equals(systemAuthDao.IsFunctionDetailInUserRole(detailSerialId,platform))){
            throw new BusiException(CommonEnum.ReturnCode.ManagerCode.manager_err_functioninused.getValue());
        }
        systemAuthDao.deleteFunctionDetail(detailSerialId,platform);
        //更新redis缓存信息
        //私有功能
        if ("1".equals(isPublic)){
            redisRepository.del(String.format(RedisConstants.SYSTEM_FUNCTION_DETAIL,isPublic,platform,detailSerialId));
        }else if ("2".equals(isPublic)){ //公有功能直接缓存
            redisRepository.del(String.format(RedisConstants.PUBLIC_URL_KEY,platform,url));
        }
    }

    @Override
    public boolean existsUserUrl(String roleId,String url) {
        String appCode = SysConfigConstants.PLATFORM_ID;//ConfigLoader.getInstance().getProperty("appCode");

        //判断是否为公共权限
        if(redisRepository.exists(String.format(RedisConstants.PUBLIC_URL_KEY,SysConfigConstants.PLATFORM_ID,url))){
            return true;
        }
        //判断用户是否存在此权限
        else{
            return redisRepository.exists(String.format(RedisConstants.USER_URL_KEY,roleId,appCode,url));
        }
    }

}
