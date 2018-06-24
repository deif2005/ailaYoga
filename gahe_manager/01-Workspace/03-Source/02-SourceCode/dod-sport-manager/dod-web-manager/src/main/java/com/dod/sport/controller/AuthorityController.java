package com.dod.sport.controller;

import com.dod.sport.dao.ISystemAuthDao;
import com.dod.sport.domain.po.System.FunctionDetail;
import com.dod.sport.domain.po.System.SystemFunction;
import com.dod.sport.domain.po.System.SystemModel;
import com.dod.sport.domain.po.System.UserRole;
import com.dod.sport.service.IAuthorityService;
import com.dod.sport.util.CommonEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by defi on 2017/9/14.
 */
@Controller
@RequestMapping(value = "api/Authority/v1")
public class AuthorityController {

    @Autowired
    IAuthorityService authorityService;

    @Autowired
    ISystemAuthDao systemAuthDao;

    @ResponseBody
    @RequestMapping(value = "/getUserRoleInfo", method = RequestMethod.POST)
    public Map<String,Object> getUserRoleInfo(String id){
        Map<String,Object> map = new HashMap<>();
        UserRole userRole = authorityService.getUserRoleById(id);
        map.put("userRole",userRole);
        return map;
    }

    /**
     * 新增角色信息
     * @param roleName
     * @param roleCode
     * @param businessId
     * @param modelSerialIdstr
     * @param functionSerialIdstr
     * @param detailSerialIdstr
     * @param platform
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addUserRole", method = RequestMethod.POST)
    public String addUserRole(@RequestParam("roleName") String roleName,
                              @RequestParam("roleCode") String roleCode,
                              @RequestParam(value = "businessId",required = false) String businessId,
                              @RequestParam(value = "modelSerialIdstr") String modelSerialIdstr,
                              @RequestParam(value = "functionSerialIdstr") String functionSerialIdstr,
                              @RequestParam(value = "detailSerialIdstr", required = false) String detailSerialIdstr,
                              @RequestParam("platform") String platform){
        UserRole userRole = new UserRole();
        userRole.setRoleName(roleName);
        userRole.setRoleCode(roleCode);
        userRole.setBusinessId(businessId);
        userRole.setModelSerialIdstr(modelSerialIdstr);
        userRole.setFunctionSerialIdstr(functionSerialIdstr);
        userRole.setDetailSerialIdstr(detailSerialIdstr);
        userRole.setPlatform(platform);
        authorityService.addUserRole(userRole);
        return "";
    }

    /**
     * 新增功能模块
     * @param modelName
     * @param platform
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addSystemModel", method = RequestMethod.POST)
    public String addSystemModel(@RequestParam("modelName") String modelName,
                                 @RequestParam("platform") String platform){
        SystemModel systemModel = new SystemModel();
        systemModel.setModelName(modelName);
        systemModel.setPlatform(platform);
        authorityService.addSystemModel(systemModel);
        return "";
    }

    @ResponseBody
    @RequestMapping(value = "/addSystemFunction", method = RequestMethod.POST)
    public String addSystemFunction(@RequestParam("modelSerialId") String modelSerialId,
                                    @RequestParam("functionName") String functionName,
                                    @RequestParam("platform") String platform){
        SystemFunction systemFunction = new SystemFunction();
        systemFunction.setModelSerialId(modelSerialId);
        systemFunction.setFunctionName(functionName);
        systemFunction.setPlatform(platform);
        authorityService.addSystemFunction(systemFunction);
        return "";
    }

    /**
     * 新增功能细节
     * @param modelSerialId
     * @param functionSerialId
     * @param detailName
     * @param detailUri
     * @param platform
     * @param isPublic
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addFunctionDetail", method = RequestMethod.POST)
    public String addFunctionDetail(@RequestParam("modelSerialId") String modelSerialId,
                                    @RequestParam("functionSerialId") String functionSerialId,
                                    @RequestParam("detailName") String detailName,
                                    @RequestParam("detailUri") String detailUri,
                                    @RequestParam("platform") String platform,
                                    @RequestParam("isPublic") String isPublic){
        FunctionDetail functionDetail = new FunctionDetail();
        functionDetail.setModelSerialId(modelSerialId);
        functionDetail.setFunctionSerialId(functionSerialId);
        functionDetail.setDetailName(detailName);
        functionDetail.setDetailUri(detailUri);
        functionDetail.setPlatform(platform);
        functionDetail.setIsPublic(isPublic);
        authorityService.addFunctionDetail(functionDetail);
        return "";
    }

    /**
     * 获取角色列表
     * @param platform
     * @param businessId
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listUserRole", method = RequestMethod.POST)
    public Map<String,Object> listUserRole(@RequestParam("platform") String platform,
                                           @RequestParam(value = "businessId", required = false) String businessId,
                                           @RequestParam(value = "page", required = false) String page){
        Map<String,Object> map = new HashMap<>();
        List<UserRole> userRoleList = new ArrayList<>();
        if (page == null){
            userRoleList = authorityService.listUserRole(platform,businessId);
        }else {
            userRoleList = authorityService.listUserRoleByPage(platform, businessId, Integer.valueOf(page));
        }
        String count = systemAuthDao.getUserRoleCount(platform,businessId);
        map.put("userRoleList",userRoleList);
        map.put("count",count);
        return map;
    }

    /**
     * 获取系统功能模块
     * @param platform
     * @param page
     * @return
     */
     @ResponseBody
     @RequestMapping(value = "/listSystemModel", method = RequestMethod.POST)
     public Map<String,Object> listSystemModel(@RequestParam("platform") String platform,
                                               @RequestParam(value = "page", required = false) String page){
         Map<String,Object> map = new HashMap<>();
         List<SystemModel> systemModelList = new ArrayList<>();
         String count="";
         if (page == null) {
             systemModelList = authorityService.listSystemModel(platform);
         }else {
             systemModelList = authorityService.listSystemModelByPage(platform, Integer.valueOf(page));
             count = systemAuthDao.getSystemModelCount(platform);
         }
         map.put("systemModelList",systemModelList);
         map.put("count",count);
        return map;
    }

    /**
     * 获取系统功能记录列表
     * @param platform
     * @param modelSerialId
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listSystemFunction", method = RequestMethod.POST)
    public Map<String,Object> listSystemFunction(@RequestParam("platform") String platform,
                                                 @RequestParam(value = "modelSerialId") String modelSerialId,
                                                 @RequestParam(value = "page", required = false) String page){
        Map<String,Object> map = new HashMap<>();
        List<SystemFunction> systemFunctionList = new ArrayList<>();
        String count="";
        if (page == null){
            systemFunctionList = authorityService.listSystemFunction(platform, modelSerialId);
        }else {
            systemFunctionList = authorityService.listSystemFunctionByPage(platform, modelSerialId,
                    Integer.valueOf(page));
            count= systemAuthDao.getSystemFunctionCount(modelSerialId,platform);
        }
        map.put("systemFunctionList",systemFunctionList);
        map.put("count",count);
        return map;
    }

    /**
     * 获取功能细节列表
     * @param platform
     * @param functionSerialId
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listFunctionDetail", method = RequestMethod.POST)
    public Map<String,Object> listFunctionDetail(@RequestParam("platform") String platform,
                                                 @RequestParam(value = "modelSerialId",required = false) String modelSerialId,
                                                 @RequestParam(value = "functionSerialId",required = false) String functionSerialId,
                                                 @RequestParam(value = "page",required = false) String page,
                                                 @RequestParam(value = "isPublic",required = false) String isPublic){
        Map<String,Object> map = new HashMap<>();
        List<FunctionDetail> functionDetailList = new ArrayList<>();
        String count="";
        if (page == null){
            functionDetailList = authorityService.listFunctionDetail(platform,modelSerialId,functionSerialId, isPublic);
        }else {
            functionDetailList = authorityService.listFunctionDetailByPage(platform,modelSerialId,functionSerialId,
                    Integer.valueOf(page), isPublic);
            count = systemAuthDao.getFunctionDetailCount(modelSerialId,functionSerialId,platform,isPublic);
            map.put("count",count);
        }
        map.put("functionDetailList",functionDetailList);
        return map;
    }

    /**
     * 获取角色模块
     * @param platform
     * @param roleCode
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listRoleSystemModel", method = RequestMethod.POST)
    public Map<String,Object> listRoleSystemModel(@RequestParam("platform") String platform,
                                                  @RequestParam("roleCode") String roleCode){
        Map<String,Object> map = new HashMap<>();
        List<SystemModel> systemAllModelList = authorityService.listAllModelByPlatform(platform);
        map.put("systemAllModelList",systemAllModelList);
        List<SystemModel> systemSelectedModelList = authorityService.listSelectedModelByPlatform(roleCode, platform);
        map.put("systemModelList",systemSelectedModelList);
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/listRoleSystemFunction", method = RequestMethod.POST)
    public Map<String,Object> listRoleSystemFunction(@RequestParam("platform") String platform,
                                                     @RequestParam("roleCode") String roleCode,
                                                     @RequestParam("modelId") String modelId){
        Map<String,Object> map = new HashMap<>();
        List<SystemFunction> systemAllFunctionList = authorityService.listAllFunctionByModelId(platform,modelId);
        map.put("systemAllFunctionList",systemAllFunctionList);
        List<SystemFunction> systemSelectedFunctionList = authorityService.listSelectedFunctionByModelId(roleCode, platform,
                modelId);
        map.put("systemSelectedFunctionList",systemSelectedFunctionList);
        return map;
    }

    /**
     * 编辑用户角色信息
     * @param id
     * @param roleName
     * @param roleCode
     * @param modelSerialIdstr
     * @param functionSerialIdstr
     * @param detailSerialIdstr
     * @param platform
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/editUserRole", method = RequestMethod.POST)
    public String editUserRole(@RequestParam("id") String id,
                               @RequestParam("roleName") String roleName,
                               @RequestParam(value ="roleCode",required = false) String roleCode,
                               @RequestParam(value = "modelSerialIdstr") String modelSerialIdstr,
                               @RequestParam(value = "functionSerialIdstr") String functionSerialIdstr,
                               @RequestParam(value = "detailSerialIdstr", required = false) String detailSerialIdstr,
                               @RequestParam("platform") String platform){
        UserRole userRole = new UserRole();
        userRole.setId(id);
        userRole.setRoleName(roleName);
        userRole.setRoleCode(roleCode);
        userRole.setModelSerialIdstr(modelSerialIdstr);
        userRole.setFunctionSerialIdstr(functionSerialIdstr);
        userRole.setDetailSerialIdstr(detailSerialIdstr);
        userRole.setPlatform(platform);
        authorityService.updateUserRole(userRole);
        return "";
    }

    @ResponseBody
    @RequestMapping(value = "/editSystemModel", method = RequestMethod.POST)
    public String editSystemModel(@RequestParam("id") final String id,
                                  @RequestParam("modelName") String modelName,
                                  @RequestParam("platform") String platform){
        SystemModel systemModel = new SystemModel();
        systemModel.setId(id);
        systemModel.setModelName(modelName);
        systemModel.setPlatform(platform);
        authorityService.updateSystemModel(systemModel);
        return "";
    }

    @ResponseBody
    @RequestMapping(value = "/editSystemFunction", method = RequestMethod.POST)
    public String editSystemFunction(@RequestParam("id") final String id,
                                     @RequestParam("modelSerialId") String modelSerialId,
                                     @RequestParam("functionName") String functionName,
                                     @RequestParam("platform") String platform){
        SystemFunction systemFunction = new SystemFunction();
        systemFunction.setId(id);
        systemFunction.setModelSerialId(modelSerialId);
        systemFunction.setFunctionName(functionName);
        systemFunction.setPlatform(platform);
        authorityService.updateSystemFunction(systemFunction);
        return "";
    }

    @ResponseBody
    @RequestMapping(value = "/editFunctionDetail", method = RequestMethod.POST)
    public String editFunctionDetail(@RequestParam("id") final String id,
                                     @RequestParam("modelSerialId") String modelSerialId,
                                     @RequestParam("functionSerialId") String functionSerialId,
                                     @RequestParam("detailName") String detailName,
                                     @RequestParam("detailUri") String detailUri,
                                     @RequestParam("platform") String platform,
                                     @RequestParam("isPublic") String isPublic){
        FunctionDetail functionDetail = new FunctionDetail();
        functionDetail.setId(id);
        functionDetail.setModelSerialId(modelSerialId);
        functionDetail.setFunctionSerialId(functionSerialId);
        functionDetail.setDetailName(detailName);
        functionDetail.setDetailUri(detailUri);
        functionDetail.setPlatform(platform);
        functionDetail.setIsPublic(isPublic);
        authorityService.updateFunctionDetail(functionDetail);
        return "";
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteUserRole", method = RequestMethod.POST)
    public String deleteUserRole(@RequestParam("id") final String id,
                                 @RequestParam("platform") final String platform){
        authorityService.delUserRole(id,platform);
        return "";
    }

    /**
     * 删除系统模块
     * @param modelSerialId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteSystemModel", method = RequestMethod.POST)
    public String deleteSystemModel(@RequestParam("modelSerialId") String modelSerialId,
                                    @RequestParam("platform") String platform){
        authorityService.delSystemModel(modelSerialId,platform);
        return "";
    }

    /**
     * 删除系统功能
     * @param functionSerialId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteSystemFunction", method = RequestMethod.POST)
    public String deleteSystemFunction(@RequestParam("modelSerialId") String modelSerialId,
                                       @RequestParam("functionSerialId") String functionSerialId,
                                       @RequestParam("platform") String platform){
        authorityService.delSystemFunction(modelSerialId,functionSerialId,platform);
        return "";
    }

    /**
     * 删除功能细节项
     * @param detailSerialId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteFunctionDetail", method = RequestMethod.POST)
    public String deleteFunctionDetail(@RequestParam("detailSerialId") String detailSerialId,
                                       @RequestParam("platform") String platform,
                                       @RequestParam("isPublic") String isPublic,
                                       @RequestParam("detailUri") String detailUri){
        authorityService.delFunctionDetail(detailSerialId,platform,isPublic,detailUri);
        return "";
    }
}
