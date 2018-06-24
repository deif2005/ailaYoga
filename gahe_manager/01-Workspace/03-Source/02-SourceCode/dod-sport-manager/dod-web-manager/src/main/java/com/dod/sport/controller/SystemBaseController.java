package com.dod.sport.controller;

import com.dod.sport.config.Configuration;
import com.dod.sport.constant.SysConfigConstants;
import com.dod.sport.dao.ISystemBaseDao;
import com.dod.sport.dao.IUserDao;
import com.dod.sport.domain.common.BusiException;
import com.dod.sport.domain.common.UploadFileResponse;
import com.dod.sport.domain.po.Bill.EntryBill;
import com.dod.sport.domain.po.Business.BaseEmployee;
import com.dod.sport.domain.po.Business.BusiEmployee;
import com.dod.sport.domain.po.Base.*;
import com.dod.sport.domain.po.Member.HistoryCard;
import com.dod.sport.domain.po.Member.MemberCard;
import com.dod.sport.domain.po.Member.MembercardRelation;
import com.dod.sport.domain.po.Member.QueryMember;
import com.dod.sport.domain.po.Response.ResponseEmployee;
import com.dod.sport.domain.po.Response.ResponseMember;
import com.dod.sport.service.IBusiEmployeeService;
import com.dod.sport.service.ISystemBaseService;
import com.dod.sport.util.CommonEnum;
import com.dod.sport.util.DateUtil;
import com.dod.sport.util.FileUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by defi on 2017-09-21.
 */
@Controller
@RequestMapping(value = "api/SystemBase/v1")
public class SystemBaseController extends BaseController {

    @Autowired
    ISystemBaseService systemBaseService;

    @Autowired
    IBusiEmployeeService busiEmployeeService;

    @Autowired
    ISystemBaseDao systemBaseDao;

    @Autowired
    IUserDao userDao;

    /**
     * 获取部门列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listDepartmentInfo", method = RequestMethod.POST)
    public Map listDepartmentInfo(@RequestParam("page") String page){
        Map<String,Object> map = new HashMap<>();
        List<Department> departmentList = systemBaseService.listDepartment(Integer.valueOf(page));
        String count = systemBaseDao.getDepartmentCount();
        map.put("departments",departmentList);
        map.put("count",count);
        return map;
    }

    /**
     * 新增部门信息
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addDepartmentInfo", method = RequestMethod.POST)
    public String addDepartmentInfo(@RequestParam(value = "departmentName") final String departmentName,
                                    @RequestParam(value = "creator") final String creator){
        Department department = new Department();
        department.setDepName(departmentName);
        department.setCreator(creator);
        systemBaseService.addDepartmentInfo(department);
        return "";
    }

    /**
     * 获取商家部门列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listBusiDepartmentInfo", method = RequestMethod.POST)
    public Map listBusiDepartmentInfo(@RequestParam(value = "businessId") final String businessId,
                                    @RequestParam(value = "page") final String page){
        Map<String,Object> map = new HashMap<>();
        List<Department> departmentList = systemBaseService.listBusiDepartment(businessId,Integer.valueOf(page));
        String count = systemBaseDao.getBusinessDepartmentCount(businessId);
        map.put("departments",departmentList);
        map.put("count",count);
        return map;
    }

    /**
     * 新增商家部门信息
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addBusiDepartmentInfo", method = RequestMethod.POST)
    public String addBusiDepartmentInfo(@RequestParam(value = "businessId") final String businessId,
                                        @RequestParam(value = "departmentName") final String departmentName,
                                        @RequestParam(value = "creator") final String creator){
        Department department = new Department();
        department.setBusinessId(businessId);
        department.setDepName(departmentName);
        department.setCreator(creator);
        systemBaseService.addBusiDepartmentInfo(department);
        return "";
    }

    /**
     * 批量新增商家部门信息
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addBatchBusiDepartmentInfo", method = RequestMethod.POST)
    public String addBatchBusiDepartmentInfo(@RequestParam(value = "businessId") final String businessId,
                                             @RequestParam(value = "departmentIdStr") final String departmentIdStr,
                                             @RequestParam(value = "departmentNameStr") final String departmentNameStr,
                                             @RequestParam(value = "creator") final String creator){
        systemBaseService.addBatchBusiDepartmentInfo(businessId, departmentIdStr, departmentNameStr, creator);
        return "";
    }

    /**
     * 获取平台所有的部门
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAllDepartmentInfo", method = RequestMethod.POST)
    public Map<String,Object> getAllDepartmentInfo(){
        Map<String,Object> map = new HashMap<>();
        List<Department> departmentList = systemBaseDao.listAllDepartmentInfo();
        map.put("departmentList",departmentList);
        return map;
    }

    /**
     * 获取商家所有部门信息
     * @param businessId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAllBusinessDepartmentInfo", method = RequestMethod.POST)
    public Map<String,Object> getAllBusinessDepartmentInfo(@RequestParam("businessId") String businessId){
        Map<String,Object> map = new HashMap<>();
        List<Department> departmentList = systemBaseDao.listAllBusiDepartmentInfo(businessId);
        map.put("departmentList",departmentList);
        return map;
    }

    /**
     * 编辑部门信息
     * @param id
     * @param departmentName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/editDepartmentInfo", method = RequestMethod.POST)
    public String editDepartmentInfo(@RequestParam("id") String id,
                                     @RequestParam("departmentName") String departmentName){
        Department department = new Department();
        department.setId(id);
        department.setDepName(departmentName);
        systemBaseDao.updateDepartment(department);
        return "";
    }

    /**
     * 删除平台部门数据
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteDepartment", method = RequestMethod.POST)
    public String deleteDepartment(@RequestParam("id") String id){
        Department department = new Department();
        department.setId(id);
        department.setStatus(CommonEnum.DBData.DataStatus.delete.getValue());
        systemBaseDao.updateDepartmentStatus(department);
        return "";
    }

    /**
     * 编辑商家部门信息
     * @param id
     * @param departmentName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/editBusinessDepartmentInfo", method = RequestMethod.POST)
    public String editBusinessDepartmentInfo(@RequestParam("id") String id,
                                             @RequestParam("departmentName") String departmentName){
        Department department = new Department();
        department.setId(id);
        department.setDepName(departmentName);
        systemBaseDao.updateBusiDepartment(department);
        return "";
    }

    /**
     * 删除商家部门信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteBusinessDepartment", method = RequestMethod.POST)
    public String deleteBusinessDepartment(@RequestParam("id") String id){
        Department department = new Department();
        department.setId(id);
        department.setStatus(CommonEnum.DBData.DataStatus.delete.getValue());
        systemBaseDao.updateBusiDepartmentStatus(department);
        return "";
    }

    /**
     * 新增商家信息
     * @param businessName
     * @param owner
     * @param phoneNum
     * @param phoneNum2
     * @param email
     * @param otherInformation
     * @param area
     * @param address
     * @param businessType
     * @param customType
     * @param remark
     * @param creator
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addBusinessInfo", method = RequestMethod.POST)
    public String addBusinessInfo(HttpServletRequest request,
                                  @RequestParam(value = "businessName") final String businessName,
                                  @RequestParam(value = "owner") String owner,
                                  @RequestParam(value = "phoneNum") final String phoneNum,
                                  @RequestParam(value = "phoneNum2", required = false) final String phoneNum2,
                                  @RequestParam(value = "email") final String email,
                                  @RequestParam(value = "otherInformation",required = false) String otherInformation,
                                  @RequestParam(value = "area") String area,
                                  @RequestParam(value = "address") final String address,
                                  @RequestParam(value = "businessType") final String businessType,
                                  @RequestParam(value = "customType") String customType,
                                  @RequestParam(value = "remark") String remark,
                                  @RequestParam(value = "creator") final String creator){
        BusinessInfo businessInfo = new BusinessInfo();
        businessInfo.setId(UUID.randomUUID().toString());
        businessInfo.setBusinessName(businessName);
        businessInfo.setOwner(owner);
        businessInfo.setPhoneNum(phoneNum);
        businessInfo.setPhoneNum2(phoneNum2);
        businessInfo.setEmail(email);
        businessInfo.setOtherInformation(otherInformation);
        businessInfo.setArea(area);
        businessInfo.setAddress(address);
        businessInfo.setBusinessType(businessType);
        businessInfo.setCustomType(customType);
        businessInfo.setRemark(remark);
        businessInfo.setCreator(creator);
        try {
            //文件上传相对目录
            String relaPath = Configuration.getUploadBusinessPicturePath(businessInfo.getId());
            //上传文件
            UploadFileResponse uploadFileResponse = super.uploadMultiFileBykey(request, relaPath);
            String uploadPath = uploadFileResponse.getFileList().toString();
            if (!"".equals(uploadPath)){
                Iterator iterator = uploadFileResponse.getTypeValueMap().entrySet().iterator();
                while (iterator.hasNext()){
                    Map.Entry entry = (Map.Entry) iterator.next();
                    String multiKey = String.valueOf(entry.getKey());
                    String value = String.valueOf(entry.getValue());
                    String key = multiKey.substring(0, multiKey.length() - 3);
                    if ("logo".equals(key)){
                        businessInfo.setLogo(value);
                    }else if ("businessPicture".equals(key)){
                        businessInfo.setBusinessPicture(value);
                    }else if ("idcardPictureA".equals(key)){
                        businessInfo.setIdcardPictureA(value);
                    }else if ("idcardPictureB".equals(key)){
                        businessInfo.setIdcardPictureB(value);
                    }
                }
            }
        }catch (BusiException e){
            //没传图片继续执行
        }catch (IOException e){
            logger.error("BusinessController-addPhotoInfo:", e);
            throw new BusiException(CommonEnum.ReturnCode.ManagerCode.manager_err_uploadfileerror.getValue());
        }
        systemBaseService.addBusinessInfo(businessInfo);
        return "";
    }

    /**
     * 获取商家列表
     * @param businessType
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listBusinessInfo", method = RequestMethod.POST)
    public Map<String,Object> listBusinessInfo(@RequestParam(value = "businessType") final String businessType,
                                               @RequestParam("page") String page){
        Map<String,Object> map = new HashMap<>();
        List<BusinessInfo> businessInfoList = systemBaseService.listBusinessInfo(Integer.valueOf(businessType),
                Integer.valueOf(page));
        String recordCount = systemBaseDao.getBusinessInfoCount(Integer.valueOf(businessType));
        map.put("businessInfo",businessInfoList);
        map.put("recordCount",recordCount);
        return map;
    }

    /**
     * 获取所有商家信息
     * @param businessType
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAllBusinessInfo", method = RequestMethod.POST)
    public Map<String,Object> getAllBusinessInfo(@RequestParam("businessType") String businessType){
        Map<String,Object> map = new HashMap<>();
        List<BusinessInfo> businessInfoList = systemBaseDao.getAllBusinessInfo(Integer.valueOf(businessType));
        map.put("businessInfo",businessInfoList);
        return map;
    }

    /**
     * 获取门店列表
     * @param businessId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listStoreInfo", method = RequestMethod.POST)
    public Map<String,Object> listStoreInfo(@RequestParam(value = "businessId") final String businessId,
                                            @RequestParam("page") String page){
        Map<String,Object> map = new HashMap<>();
        List<StoreInfo> storeInfoList = systemBaseService.listStoreInfo(businessId, Integer.valueOf(page));
        String recordCount = systemBaseDao.getStoreInfoCount(businessId);
        map.put("businessInfo",storeInfoList);
        map.put("recordCount",recordCount);
        return map;
    }

    /**
     * 获取商家所有门店
     * @param businessId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAllStoreInfo", method = RequestMethod.POST)
    public Map<String,Object> getAllStoreInfo(@RequestParam(value = "businessId") final String businessId){
        Map<String,Object> map = new HashMap<>();
        List<StoreInfo> storeInfoList = systemBaseDao.getAllStoreInfo(businessId);
        map.put("businessInfo",storeInfoList);
        return map;
    }

    /**
     * 编辑员工信息
     * @param employeeName 姓名
     * @param phoneNum 电话
     * @param depId 部门id
     * @param positionId 职位id
     * @param empRela 就职状态:1:全职;2:兼职
     * @param sex 性别:1男;2:女
     * @param coachExists 是否是教练:1:否;2:是
     * @param jobTitle 教练职称;1:初级教练;2:中级教练;3:高级教练;
     * @param id  员工关系id
     * @return
     */
    @RequestMapping(value = "/updateEmployeeInfo",method = RequestMethod.POST)
    @ResponseBody
    public String updateEmployeeInfo(@RequestParam(value = "employeeName" ,required = false) final String employeeName,
                                     @RequestParam(value = "phoneNum",required = false) final String phoneNum,
                                     @RequestParam(value = "depId",required = false) final String depId,
                                     @RequestParam(value = "positionId",required = false) final String positionId,
                                     @RequestParam(value = "empRela",required = false) final String empRela,
                                     @RequestParam(value = "entryDate") final String entryDate,
                                     @RequestParam(value = "sex",required = false) final String sex,
                                     @RequestParam(value = "coachExists",required = false) final String coachExists,
                                     @RequestParam(value = "jobTitle",required = false) final String jobTitle,
                                     @RequestParam(value = "creator") final String creator,
                                     @RequestParam(value = "roleId") final String roleId,
                                     @RequestParam(value = "id") final String id){
        ResponseEmployee remp =  busiEmployeeService.getEmpBusinessRelationById(id);
        //编辑员工基础信息
        EmployeeInfo emp = new EmployeeInfo();
        emp.setEmployeeName(employeeName);
        emp.setPhoneNum(phoneNum);
        emp.setSex(sex);
        systemBaseDao.updateEmployeInfo(emp);

        //编辑员工关系id
        EmpBusinessRelation empr = new EmpBusinessRelation();
        empr.setDepId(depId);
        empr.setPositionId(positionId);
        empr.setEmpRela(empRela);
        empr.setIsCoach(coachExists);
        empr.setJobTitle(jobTitle);
        empr.setId(remp.getEmpId());
        empr.setEntryDate(entryDate);
        empr.setCreator(creator);
        empr.setRoleId(roleId);
        systemBaseDao.updateEmpBusinessRelation(empr);
        return "";
    }

    /**
     * 员工图片上传
     * @param request
     * @param flag 1上传的是头像;2上传的是教学照片
     * @param employeeId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/UploadEmployeeFile",method = RequestMethod.POST)
    public String UploadEmployeePhoto(HttpServletRequest request,
                                      @RequestParam(value = "flag") final Integer flag,
                                      @RequestParam(value = "employeeId") final String employeeId){
        //文件上传相对目录
        String relaPath ="";
        List<String> showPathList = new ArrayList<>();
        String empHead = "";
        String empPic="";
        if (flag==1){//头像
            relaPath = Configuration.getUploadEmployeePicturePath(employeeId);
        }else if(flag ==2){//教学照片
            relaPath = Configuration.getUploadEmployeePicturePath(employeeId);
        }
        try {
            //文件展示地址
            String baseShowPath = Configuration.getStaticShowPath();
            //上传文件
            UploadFileResponse uploadFileResponse = super.uploadFile(request,relaPath);
            String uploadPath = uploadFileResponse.getFileList().toString();
            StringBuffer stringBuffer = new StringBuffer();
            if (!"".equals(uploadPath)){
                for (String path : uploadFileResponse.getFileList()){
                    stringBuffer.append(";").append(path);
                }
                if (flag==1){//头像
                    empHead = stringBuffer.toString().substring(1);
                }else if(flag ==2) {//教学照片
                    empPic = stringBuffer.toString().substring(1);
                }
            }
        }catch (BusiException e){
            //没传图片继续执行
        }catch (IOException e){
            logger.error("EmployeeController-empUploadFile:", e);
            throw new BusiException(CommonEnum.ReturnCode.ManagerCode.manager_err_uploadfileerror.getValue());
        }
        if (empHead!=null && !"".equals(empHead)){
            EmployeeInfo emp = new EmployeeInfo();
            emp.setEmpHead(empHead);
            systemBaseDao.updateEmployeInfo(emp);
        }
        if (empPic!=null && !"".equals(empPic)){
            EmpBusinessRelation empr = new EmpBusinessRelation();
            empr.setEmpPicture(empPic);
            systemBaseDao.updateEmpBusinessRelation(empr);
        }
        return "";
    }

    /**
     * 获取职位信息列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listPositionInfo", method = RequestMethod.POST)
    public Map listPositionInfo(@RequestParam("page") String page){
        Map<String,Object> map = new HashMap<>();
        List<Position> positionList = systemBaseService.listPositionInfo(Integer.valueOf(page));
        String count = systemBaseDao.getPositionCount();
        map.put("positionList",positionList);
        map.put("count",count);
        return map;
    }

    /**
     * 获取平台所有职位信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAllPositionInfo", method = RequestMethod.POST)
    public Map getAllPositionInfo(){
        Map<String,Object> map = new HashMap<>();
        List<Position> positionList = systemBaseDao.getAllPositionInfo();
        map.put("positionList",positionList);
        return map;
    }

    /**
     * 获取商家所有职位信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAllBusiPositionInfo", method = RequestMethod.POST)
    public Map getAllBusiPositionInfo(@RequestParam("businessId") String businessId){
        Map<String,Object> map = new HashMap<>();
        List<Position> positionList = systemBaseDao.getAllBusiPositionInfo(businessId);
        map.put("positionList",positionList);
        return map;
    }

    /**
     * 获取职位信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPositionInfo", method = RequestMethod.POST)
    public Map getPositionInfo(String id){
        Map<String,Object> map = new HashMap<>();
        Position position = systemBaseService.getPositionInfo(id);
        map.put("position",position);
        return map;
    }

    /**
     * 添加职位信息
     * @param positionName
     * @param creator
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addPosition", method = RequestMethod.POST)
    public String addPosition(@RequestParam("positionName") final String positionName,
                              @RequestParam("creator") final String creator){
        Position position = new Position();
        position.setPositionName(positionName);
        position.setCreator(creator);
        systemBaseService.addPosition(position);
        return "";
    }

    /**
     * 获取商家职位信息列表
     * @param businessId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listBusiPositionInfo", method = RequestMethod.POST)
    public Map listBusiPositionInfo(@RequestParam("businessId") final String businessId,
                                    @RequestParam("page") final String page){
        Map<String,Object> map = new HashMap<>();
        List<Position> positionList = systemBaseService.listBusiPositionInfo(businessId,Integer.valueOf(page));
        String count = systemBaseDao.getBusiPositionCount(businessId);
        map.put("positionList",positionList);
        map.put("count",count);
        return map;
    }

    /**
     * 获取职位信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getBusiPositionInfo", method = RequestMethod.POST)
    public Map getBusiPositionInfo(String id){
        Map<String,Object> map = new HashMap<>();
        Position position = systemBaseService.getBusiPositionInfo(id);
        map.put("position",position);
        return map;
    }

    /**
     * 添加商家职位信息
     * @param positionName
     * @param creator
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addBusiPosition", method = RequestMethod.POST)
    public String addBusiPosition(@RequestParam("businessId") final String businessId,
                                  @RequestParam("positionName") final String positionName,
                                  @RequestParam("creator") final String creator){
        Position position = new Position();
        position.setBusinessId(businessId);
        position.setPositionName(positionName);
        position.setCreator(creator);
        systemBaseService.addBusiPosition(position);
        return "";
    }

    /**
     * 添加商家职位信息
     * @param positionIdStr
     * @param positionNameStr
     * @param creator
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addBatchBusiPosition", method = RequestMethod.POST)
    public String addBatchBusiPosition(@RequestParam("businessId") final String businessId,
                                       @RequestParam("positionIdStr") final String positionIdStr,
                                       @RequestParam("positionNameStr") final String positionNameStr,
                                       @RequestParam("creator") final String creator){
        systemBaseService.addBatchBusiPosition(businessId, positionIdStr, positionNameStr, creator);
        return "";
    }

    /**
     * 编辑平台职位信息
     * @param id
     * @param positionName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/editPosition", method = RequestMethod.POST)
    public String editPosition(@RequestParam("id") String id,
                               @RequestParam("positionName") String positionName){
        Position position = new Position();
        position.setId(id);
        position.setPositionName(positionName);
        systemBaseDao.updateBusiPositionInfo(position);
        return "";
    }

    /**
     * 编辑商家职位信息
     * @param id
     * @param positionName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/editBusiPosition", method = RequestMethod.POST)
    public String editBusiPosition(@RequestParam("id") String id,
                                   @RequestParam("positionName") String positionName){
        Position position = new Position();
        position.setId(id);
        position.setPositionName(positionName);
        systemBaseDao.updateBusiPositionInfo(position);
        return "";
    }

    /**
     * 删除平台职位信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deletePosition", method = RequestMethod.POST)
    public String deletePosition(@RequestParam("id") String id){
        Position position = new Position();
        position.setId(id);
        position.setStatus(CommonEnum.DBData.DataStatus.delete.getValue());
        systemBaseDao.updatePositionStatus(position);
        return "";
    }

    /**
     * 删除商家职位信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteBusiPosition", method = RequestMethod.POST)
    public String deleteBusiPosition(@RequestParam("id") String id){
        Position position = new Position();
        position.setId(id);
        position.setStatus(CommonEnum.DBData.DataStatus.delete.getValue());
        systemBaseDao.updateBusiPositionStatus(position);
        return "";
    }

    /**
     * 新增教室
     * @param classroomName
     * @param storeId
     * @param creator
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addClassroom", method = RequestMethod.POST)
    public String addClassroom( @RequestParam("classroomName") String classroomName,
                                @RequestParam("storeId") String storeId,
                                @RequestParam("creator") String creator){
        Classroom classroom = new Classroom();
        classroom.setClassroomName(classroomName);
        classroom.setCreator(creator);
        classroom.setStoreId(storeId);
        systemBaseService.addClassroom(classroom);
        return "";
    }

    /**
     *查询门店中所有教室
     * @param storeId
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listClassroomByStoreId", method = RequestMethod.POST)
    public Map<String,Object> listClassroomByStoreId(@RequestParam("storeId") String storeId,
                                                     @RequestParam("page") String page){
        Map<String,Object> map = new HashMap<>();
        List<Classroom> classroomList = systemBaseService.listClassroomByStoreId(storeId, Integer.valueOf(page));
        String count = systemBaseDao.getClassroomCount(storeId);
        map.put("classroomList",classroomList);
        map.put("count",count);
        return map;
    }

    /**
     * 编辑教室信息
     * @param id
     * @param classroomName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/editClassroom", method = RequestMethod.POST)
    public String editClassroom(@RequestParam("id") String id,
                                @RequestParam("classroomName") String classroomName){
        Classroom classroom = new Classroom();
        classroom.setId(id);
        classroom.setClassroomName(classroomName);
        systemBaseDao.updateClassroom(classroom);
        return "";
    }

    /**
     * 删除教室信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteClassroom", method = RequestMethod.POST)
    public String deleteClassroom(@RequestParam("id") String id){
        Classroom classroom = new Classroom();
        classroom.setId(id);
        classroom.setStatus(CommonEnum.DBData.DataStatus.delete.getValue());
        systemBaseDao.updateClassroom(classroom);
        return "";
    }

    /**
     * 新增门店信息
     * @param businessId
     * @param storeName
     * @param address
     * @param creator
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addStoreInfo", method = RequestMethod.POST)
    public String addStoreInfo(HttpServletRequest request,
                               @RequestParam(value = "businessId") final String businessId,
                               @RequestParam(value = "storeName") final String storeName,
                               @RequestParam(value = "managerName") final String managerName,
                               @RequestParam(value = "phoneNum") final String phoneNum,
                               @RequestParam(value = "phoneNum2",required = false) final String phoneNum2,
                               @RequestParam(value = "email" ,required = false) final String email,
                               @RequestParam(value = "area") final String area,
                               @RequestParam(value = "address") final String address,
                               @RequestParam(value = "contactWay", required = false) final String contactWay,
                               @RequestParam(value = "serviceIntroduction") final String serviceIntroduction,
                               @RequestParam(value = "startTime") final String startTime,
                               @RequestParam(value = "endTime") final String endTime,
                               @RequestParam(value = "creator") final String creator){

        StoreInfo storeInfo = new StoreInfo();
        String id = UUID.randomUUID().toString();
        storeInfo.setId(id);
        storeInfo.setBusinessId(businessId);
        storeInfo.setStoreName(storeName);
        storeInfo.setManagerName(managerName);
        storeInfo.setPhoneNum(phoneNum);
        storeInfo.setPhoneNum2(phoneNum2);
        storeInfo.setEmail(email);
        storeInfo.setArea(area);
        storeInfo.setAddress(address);
        storeInfo.setCreator(creator);
        storeInfo.setContactWay(contactWay);
        storeInfo.setServiceIntroduction(serviceIntroduction);
        storeInfo.setStartTime(startTime);
        storeInfo.setEndTime(endTime);

        //文件上传相对目录
        String relaPath =Configuration.getUploadStoreInfoPicturePath(id);
        try {
            //上传文件
            UploadFileResponse uploadFileResponse = super.uploadMultiFileBykey(request, relaPath);
            String uploadPath = uploadFileResponse.getFileList().toString();
            if (!"".equals(uploadPath)){
                StringBuffer doorPicture = new StringBuffer();
                StringBuffer classroomPicture = new StringBuffer();
                StringBuffer receptionPicture = new StringBuffer();
                StringBuffer licensePicture = new StringBuffer();
                String value = "";
                Iterator iterator = uploadFileResponse.getTypeValueMap().entrySet().iterator();
                while (iterator.hasNext()){
                    Map.Entry entry = (Map.Entry) iterator.next();
                    String multiKey = String.valueOf(entry.getKey());
                    value = String.valueOf(entry.getValue());
                    String key = multiKey.substring(0, multiKey.length() - 3);
                    if ("doorPicture".equals(key)){
                        doorPicture.append(";").append(value);
                    }else if ("classroomPicture".equals(key)){
                        classroomPicture.append(";").append(value);
                    }else if ("receptionPicture".equals(key)){
                        receptionPicture.append(";").append(value);
                    }else if ("licensePicture".equals(key)){
                        licensePicture.append(";").append(value);
                    }
                }
                storeInfo.setDoorPicture(doorPicture.toString().substring(1));
                storeInfo.setClassroomPicture(classroomPicture.toString().substring(1));
                storeInfo.setReceptionPicture(receptionPicture.toString().substring(1));
                storeInfo.setLicensePicture(licensePicture.toString().substring(1));
            }
        }catch (BusiException e){
            //没传图片继续执行
        }catch (IOException e){
            logger.error("BusinessController-addStoreInfo:", e);
            throw new BusiException(CommonEnum.ReturnCode.ManagerCode.manager_err_uploadfileerror.getValue());
        }
        systemBaseService.addStoreInfo(storeInfo);
        return "";
    }

    /**
     * 新门店照片信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addStorePhotoInfo", method = RequestMethod.POST)
    public String addStorePhotoInfo(HttpServletRequest request,
                                    @RequestParam(value = "businessId") final String businessId,
                                    @RequestParam(value = "photoName") final String photoName){
        StorePhoto storePhoto = new StorePhoto();
        try {
            //文件上传相对目录
            String relaPath = Configuration.getUploadBusinessPicturePath(businessId);
            //上传文件
            UploadFileResponse uploadFileResponse = super.uploadFile(request, relaPath);
            String uploadPath = uploadFileResponse.getFileList().toString();
            StringBuffer stringBuffer = new StringBuffer();
            if (!"".equals(uploadPath)){
                for (String path : uploadFileResponse.getFileList()){
                    stringBuffer.append(";").append(path);
                }
                if("classPhotos".equals(photoName)){
                    storePhoto.setClassPhotos(stringBuffer.toString().substring(1));
                }else if("businessLicense".equals(photoName)){
                    storePhoto.setBusinessLicense(stringBuffer.toString().substring(1));
                } else if("classroomPhotos".equals(photoName)){
                    storePhoto.setClassroomPhotos(stringBuffer.toString().substring(1));
                }else if("doorPhoto".equals(photoName)){
                    storePhoto.setDoorPhoto(stringBuffer.toString().substring(1));
                } else if("receptionPhotos".equals(photoName)){
                    storePhoto.setReceptionPhotos(stringBuffer.toString().substring(1));
                } else if("teacherPhoto".equals(photoName)){
                    storePhoto.setTeacherPhoto(stringBuffer.toString().substring(1));
                }
            }
        }catch (BusiException e){
            //没传图片继续执行
        }catch (IOException e){
            logger.error("BusinessController-addPhotoInfo:", e);
            throw new BusiException(CommonEnum.ReturnCode.ManagerCode.manager_err_uploadfileerror.getValue());
        }
        return systemBaseService.addStorePhotoInfo(storePhoto);
    }

    /**
     * 新增课程类型信息
     * @param courseTypeName
     * @param creator
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addCourseType", method = RequestMethod.POST)
    public String addCourseType(@RequestParam("courseTypeName") final String courseTypeName,
                                @RequestParam("creator") final String creator,
                                @RequestParam("businessId") final String businessId){
        CourseType courseType = new CourseType();
        courseType.setCourseTypeName(courseTypeName);
        courseType.setBusinessId(businessId);
        courseType.setCreator(creator);
        systemBaseService.addCourseType(courseType);
        return "";
    }

    /**
     * 获取课程类型信息列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listCourseType", method = RequestMethod.POST)
    public Map listCourseType(@RequestParam(value = "businessId") final String businessId,
                              @RequestParam(value = "page") final String page){
        Map map = new HashMap<>();
        List<CourseType> courseTypeList = systemBaseService.listCourseType(businessId,Integer.valueOf(page));
        String count = systemBaseDao.getCourseTypeCount(businessId);
        map.put("courseTypeList",courseTypeList);
        map.put("count",count);
        return map;
    }

    /**
     * 删除课程类型数据
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteCourseType", method = RequestMethod.POST)
    public String deleteCourseType(@RequestParam("id") String id){
        CourseType courseType = new CourseType();
        courseType.setId(id);
        courseType.setStatus(CommonEnum.DBData.DataStatus.delete.getValue());
        systemBaseDao.updateCourseTypeStatus(courseType);
        return "";
    }

    /**
     * 编辑课程类型
     * @param id
     * @param courseTypeName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/editCourseType", method = RequestMethod.POST)
    public String editCourseType(@RequestParam("id") String id,
                                 @RequestParam("courseTypeName") String courseTypeName){
        CourseType courseType = new CourseType();
        courseType.setId(id);
        courseType.setCourseTypeName(courseTypeName);
        systemBaseDao.updateCourseType(courseType);
        return "";
    }

    /**
     * 员工入职
     * @param employeeName 员工姓名
     * @param phoneNum 员工电话
     * @param idCard 员工身份证
     * @param depId 部门id
     * @param positionId 职位id
     * @param empRela  聘用关系：1全职，2兼职
     * @param businessId 商家id
     * @param storeId 门店id
     * @param sex 性别:1:男;2:女;3:未知
     * @param coachExists :是否是教练:1.否;2.是
     * @param jobTitle  教练职称;1:初级教练;2:中级教练;3:高级教练
     * @param selfIntroduction 自我介绍
     * @param roleId 角色id
     * @param creator 创建人id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addEmployeeInfo",method = RequestMethod.POST)
    public String addEmployeeInfo(HttpServletRequest httpServletRequest,
                                  @RequestParam(value = "employeeName") final String employeeName,
                                  @RequestParam(value = "phoneNum") final String phoneNum,
                                  @RequestParam(value = "idCard") final String idCard,
                                  @RequestParam(value = "depId") final String depId,
                                  @RequestParam(value = "birthday") final String birthday,
                                  @RequestParam(value = "empType") final String empType,
                                  @RequestParam(value = "positionId") final String positionId,
                                  @RequestParam(value = "entryDate") final String entryDate,
                                  @RequestParam(value = "empRela") final String empRela,
                                  @RequestParam(value = "businessId") final String businessId,
                                  @RequestParam(value = "storeId") final String storeId,
                                  @RequestParam(value = "sex") final String sex,
                                  @RequestParam(value = "coachExists") final String coachExists,
                                  @RequestParam(value = "jobTitle", required = false) final String jobTitle,
                                  @RequestParam(value = "selfIntroduction", required = false) final String selfIntroduction,
                                  @RequestParam(value = "roleId") final String roleId,
                                  @RequestParam(value = "creator") final String creator){
        if (employeeName==""||roleId ==""){
            throw new BusiException(CommonEnum.ReturnCode.SystemCode.sys_err_paramerror.getValue());
        }
        if(phoneNum==""||phoneNum.length()!= SysConfigConstants.EMPLOYEE_PHONENUM_LENGTH){
            throw new BusiException(CommonEnum.ReturnCode.ManagerCode.manager_err_phonenumerror.getValue());
        }
        SimpleDateFormat sdf = new SimpleDateFormat(SysConfigConstants.DATE_FORMAT_FORDATE);
        ResponseEmployee queryremp =new ResponseEmployee();
        queryremp.setPhoneNum(phoneNum);
        queryremp.setStoreId(storeId);
        ResponseEmployee remp = busiEmployeeService.queryEmployeeInfoByStoreIdAndPhoneNum(queryremp);
        if(remp!=null){ //1.判断员工关系表中对应的商家中是否存在该员工信息,如果存在则提示该员工信息已存在
            throw new BusiException(CommonEnum.ReturnCode.ManagerCode.manager_err_employeeexists.getValue());
        }

        String empId = UUID.randomUUID().toString();
        EmployeeInfo addEmployee = new EmployeeInfo();
        addEmployee.setId(empId);
        addEmployee.setIdCard(idCard);
        addEmployee.setPhoneNum(phoneNum);
        addEmployee.setBirthday(birthday);
        addEmployee.setEmployeeName(employeeName);
        addEmployee.setSex(sex);

        //新增员工关系表信息
        EmpBusinessRelation empRelation = new EmpBusinessRelation();
        empRelation.setIsCoach(coachExists);
        if(coachExists.equals("2")){//教练
            empRelation.setJobTitle(jobTitle);
        }
        String relationId =  UUID.randomUUID().toString();
        empRelation.setId(relationId);
        empRelation.setEmpId(empId);
        empRelation.setSelfIntroduction(selfIntroduction);
        empRelation.setEmpType(CommonEnum.Employee.empType.informal.getValue());
        empRelation.setBusinessId(businessId);
        empRelation.setEmpType(empType);
        empRelation.setDepId(depId);
        empRelation.setStoreId(storeId);
        empRelation.setPositionId(positionId);
        empRelation.setEmpRela(empRela);
        empRelation.setCreator(creator);
        empRelation.setEntryDate(entryDate);
        empRelation.setIsCoach(coachExists);
        empRelation.setRoleId(roleId);

        //文件上传相对目录
        String relaPath =Configuration.getUploadStoreInfoPicturePath(relationId);
        try {
            //上传文件
            UploadFileResponse uploadFileResponse = super.uploadMultiFileBykey(httpServletRequest, relaPath);
            String uploadPath = uploadFileResponse.getFileList().toString();
            if (!"".equals(uploadPath)){
                StringBuffer empHead = new StringBuffer();
                StringBuffer empPicture = new StringBuffer();
                String value = "";
                Iterator iterator = uploadFileResponse.getTypeValueMap().entrySet().iterator();
                while (iterator.hasNext()){
                    Map.Entry entry = (Map.Entry) iterator.next();
                    String multiKey = String.valueOf(entry.getKey());
                    value = String.valueOf(entry.getValue());
                    String key = multiKey.substring(0, multiKey.length() - 3);
                    if ("empHead".equals(key)){
                        addEmployee.setEmpHead(value);
                    }else if ("empPicture".equals(key)){
                        empPicture.append(";").append(value);
                    }
                }
                empRelation.setEmpPicture(empPicture.toString().substring(1));
            }
        }catch (BusiException e){
            //没传图片继续执行
        }catch (IOException e){
            logger.error("BusinessController-addEmployeeInfo:", e);
            throw new BusiException(CommonEnum.ReturnCode.ManagerCode.manager_err_uploadfileerror.getValue());
        }
        busiEmployeeService.addBaseAndBusinessEmployeeInfo(addEmployee,empRelation);
        return "";
    }

    /**
     * 获取门店所有员工信息
     * @param storeId
     * @return
     */
    @RequestMapping(value = "/listBusiEmployeeByStoreId",method = RequestMethod.POST)
    @ResponseBody
    public Map listBusiEmployeeByStoreId(@RequestParam(value = "storeId") final String storeId,
                                         @RequestParam(value = "page") final String page){
        Map<String,Object>map = new HashMap<>();
        List<ResponseEmployee> responseEmployeeList = busiEmployeeService.listEmpBusinessRelationByStoreId(storeId,
                Integer.valueOf(page));
        String count = systemBaseDao.getStoreEmployeeCount(storeId);
        map.put("responseEmployeeList", responseEmployeeList);
        map.put("count",count);
        return map;
    }

    /**
     * 获取商家员工基础信息列表
     * @param employeeName
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listBusinessEmployee",method = RequestMethod.POST)
    public Map listBusinessEmployee(@RequestParam(value = "employeeName",required = false) final String employeeName,
                                    @RequestParam(value = "page") final String page){
        Map<String,Object>map = new HashMap<>();
        List<EmployeeInfo> employeeInfoList = busiEmployeeService.listBusinessEmployee(employeeName,
                Integer.valueOf(page));
        String count = systemBaseDao.getBusinessEmployeeCount();
        map.put("employeeInfoList", employeeInfoList);
        map.put("count",count);
        return map;
    }

    /**
     * 编辑员工基础信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/editEmployeeInfo",method = RequestMethod.POST)
    public String editEmployeeInfo(HttpServletRequest httpServletRequest,
                                   @RequestParam("id") String id,
                                   @RequestParam("employeeName") String employeeName,
                                   @RequestParam("sex") String sex,
                                   @RequestParam("birthday") String birthday,
                                   @RequestParam("phoneNum") String phoneNum,
                                   @RequestParam("idCard") String idCard,
                                   @RequestParam(value = "delEmpHead", required = false) String delEmpHead){
        EmployeeInfo employeeInfo = new EmployeeInfo();
        employeeInfo.setId(id);
        employeeInfo.setEmployeeName(employeeName);
        employeeInfo.setSex(sex);
        employeeInfo.setBirthday(birthday);
        employeeInfo.setPhoneNum(phoneNum);
        employeeInfo.setIdCard(idCard);
        EmployeeInfo employeeInfo1 = systemBaseDao.isExistsEmployeePhoneNum(employeeInfo);
        if (employeeInfo1 != null){
            throw new BusiException(CommonEnum.ReturnCode.ManagerCode.manager_err_businessuserphonenexists.getValue());
        }
        //文件上传相对目录
        String relaPath =Configuration.getUploadStoreInfoPicturePath(id);
        try {
            if (!"".equals(delEmpHead) && delEmpHead!=null){
                deleteExistsFile(relaPath,delEmpHead);
            }
            //上传文件
            UploadFileResponse uploadFileResponse = super.uploadFile(httpServletRequest, relaPath);
            String uploadPath = uploadFileResponse.getFileList().toString();
            if (!"".equals(uploadPath)) {
                employeeInfo.setEmpHead(uploadPath);
            }
        }catch (BusiException e){
            //没传图片继续执行
        }catch (IOException e){
            logger.error("BusinessController-addEmployeeInfo:", e);
            throw new BusiException(CommonEnum.ReturnCode.ManagerCode.manager_err_uploadfileerror.getValue());
        }
        systemBaseDao.updateEmployeInfo(employeeInfo);
        return "";
    }

    /**
     * 删除员工基础信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteEmployeeInfo",method = RequestMethod.POST)
    public String deleteEmployee(@RequestParam("id") String id){
        EmployeeInfo employeeInfo = new EmployeeInfo();
        employeeInfo.setId(id);
        employeeInfo.setStatus(CommonEnum.DBData.DataStatus.delete.getValue());
        systemBaseDao.updateEmployeInfo(employeeInfo);
        return "";
    }

    /**
     * 更新商家员工信息
     * @param relationId
     * @param positionId
     * @param depId
     * @param empType
     * @param empRela
     * @param isCoach
     * @param jobTitle
     * @param entryDate
     * @param selfIntroduction
     * @param roleId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/editBusiEmployeeInfo",method = RequestMethod.POST)
    public String editBusiEmployeeInfo(HttpServletRequest httpServletRequest,
                                       @RequestParam("id") String id,
                                       @RequestParam("employeeName") String employeeName,
                                       @RequestParam("sex") String sex,
                                       @RequestParam("birthday") String birthday,
                                       @RequestParam("phoneNum") String phoneNum,
                                       @RequestParam("idCard") String idCard,
                                       @RequestParam(value = "delEmpHead", required = false) String delEmpHead,
                                       @RequestParam("relationId") String relationId,
                                       @RequestParam("positionId") String positionId,
                                       @RequestParam("depId") String depId,
                                       @RequestParam("empType") String empType,
                                       @RequestParam("empRela") String empRela,
                                       @RequestParam("isCoach") String isCoach,
                                       @RequestParam(value = "jobTitle", required = false) String jobTitle,
                                       @RequestParam("entryDate") String entryDate,
                                       @RequestParam(value = "selfIntroduction", required = false) String selfIntroduction,
                                       @RequestParam("roleId") String roleId,
                                       @RequestParam(value = "delEmpPicture", required = false) String delEmpPicture
    ){
        StringBuffer pictureBuf = new StringBuffer();
        EmployeeInfo employeeInfo = new EmployeeInfo();
        employeeInfo.setId(id);
        employeeInfo.setEmployeeName(employeeName);
        employeeInfo.setSex(sex);
        employeeInfo.setBirthday(birthday);
        employeeInfo.setPhoneNum(phoneNum);
        employeeInfo.setIdCard(idCard);

        EmpBusinessRelation empBusinessRelation = new EmpBusinessRelation();
        empBusinessRelation.setId(relationId);
        empBusinessRelation.setPositionId(positionId);
        empBusinessRelation.setDepId(depId);
        empBusinessRelation.setEmpType(empType);
        empBusinessRelation.setEmpRela(empRela);
        empBusinessRelation.setIsCoach(isCoach);
        empBusinessRelation.setJobTitle(jobTitle);
        empBusinessRelation.setEntryDate(entryDate);
        empBusinessRelation.setSelfIntroduction(selfIntroduction);
        empBusinessRelation.setRoleId(roleId);
        EmployeeInfo employeeInfo1 = systemBaseDao.isExistsEmployeePhoneNum(employeeInfo);
        if (employeeInfo1 != null){
            throw new BusiException(CommonEnum.ReturnCode.ManagerCode.manager_err_businessuserphonenexists.getValue());
        }
        //文件上传相对目录
        String relaPath =Configuration.getUploadStoreInfoPicturePath(relationId);
        EmpBusinessRelation empBusinessRelation1 = systemBaseDao.getEmpBusinessRelationById(relationId);
        if (!"".equals(empBusinessRelation1.getEmpPicture()) && empBusinessRelation1.getEmpPicture()!=null){
            pictureBuf.append(";").append(empBusinessRelation1.getEmpPicture());
        }
        try {
            if (!"".equals(delEmpHead) && delEmpHead!=null){
                employeeInfo.setEmpHead("");
                deleteExistsFile(relaPath+"empHead",delEmpHead);
            }
            if (!"".equals(delEmpPicture) && delEmpPicture != null){
                //删除数据库记录
                if (empBusinessRelation1.getEmpPicture()!=null && !"".equals(empBusinessRelation1.getEmpPicture())){
                    String[] empPicture = empBusinessRelation1.getEmpPicture().split(";");
                    ArrayList<String> list = new ArrayList<String>(Arrays.asList(empPicture));
                    pictureBuf = removeItemAndAddExistsItem(list,delEmpPicture);
                    if(pictureBuf.toString().length() > 0){
                        empBusinessRelation.setEmpPicture(pictureBuf.toString().substring(1));
                    }else{
                        empBusinessRelation.setEmpPicture("");
                    }
                    deleteExistsFile(relaPath+"empPicture",delEmpPicture);
                }
            }
            //上传文件
            UploadFileResponse uploadFileResponse = super.uploadMultiFileBykey(httpServletRequest, relaPath);
            String uploadPath = uploadFileResponse.getFileList().toString();
            if (!"".equals(uploadPath)){
                String value = "";
                Iterator iterator = uploadFileResponse.getTypeValueMap().entrySet().iterator();
                while (iterator.hasNext()){
                    Map.Entry entry = (Map.Entry) iterator.next();
                    String multiKey = String.valueOf(entry.getKey());
                    value = String.valueOf(entry.getValue());
                    String key = multiKey.substring(0, multiKey.length() - 3);
                    if("empHead".equals(key)){
                        employeeInfo.setEmpHead(uploadPath);
                    }
                    if ("empPicture".equals(key)){
                        pictureBuf.append(";").append(value);
                    }
                }
            }
            if (pictureBuf.toString().length() > 0){
                empBusinessRelation.setEmpPicture(pictureBuf.toString().substring(1));
            }
        }catch (BusiException e){//没传图片继续执行

        }catch (IOException e){
            logger.error("BusinessController-addEmployeeInfo:", e);
            throw new BusiException(CommonEnum.ReturnCode.ManagerCode.manager_err_uploadfileerror.getValue());
        }
        systemBaseDao.updateEmployeInfo(employeeInfo);
        systemBaseDao.updateEmpBusinessRelation(empBusinessRelation);
        return "";
    }

    /**
     * 删除list中已存在于delEmpPicture中的数据
     * @param list
     * @param delEmpPicture
     * @return
     */
    public static StringBuffer removeItemAndAddExistsItem(List<String> list, String delEmpPicture){
        String picture = "";
        StringBuffer pictureBuf = new StringBuffer();
        if (!"".equals(delEmpPicture) && delEmpPicture != null) {
            if (delEmpPicture.indexOf(";") > 0) {  //删除多张图片
                String[] arrayFile = delEmpPicture.split(";");
                for (int i = 0; i < arrayFile.length; i++) {
                    String file = arrayFile[i].substring(arrayFile[i].lastIndexOf("/"));
                    Iterator<String> it = list.iterator();
                    while(it.hasNext()){
                        picture = it.next();
                        if (picture.indexOf(file) >= 0){
                            it.remove();
                        }
                    }
                }
                for (String pic:list){
                    pictureBuf.append(";").append(pic);
                }
            } else { //删除一张照片
                String file = delEmpPicture.substring(delEmpPicture.lastIndexOf("/"));
                Iterator<String> it = list.iterator();
                while(it.hasNext()){
                    picture = it.next();
                    if (picture.indexOf(file) >= 0){
                        it.remove();
                    }else {
                        pictureBuf.append(";").append(picture);
                    }
                }
            }
        }
        return pictureBuf;
    }

    /**
     * 删除商家信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteBusiness",method = RequestMethod.POST)
    public String deleteBusiness(@RequestParam("id") String id){
        BusinessInfo businessInfo = new BusinessInfo();
        businessInfo.setId(id);
        businessInfo.setStatus(CommonEnum.DBData.DataStatus.delete.getValue());
        systemBaseDao.updateBusinessInfo(businessInfo);
        return "";
    }

    /**
     * 删除门店信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteStore", method = RequestMethod.POST)
    public String deleteStore(@RequestParam("id") String id){
        StoreInfo storeInfo = new StoreInfo();
        storeInfo.setId(id);
        storeInfo.setStatus(CommonEnum.DBData.DataStatus.delete.getValue());
        systemBaseDao.updateStoreInfo(storeInfo);
        return "";
    }

    /**
     * 删除商家员工信息
     * @param relationId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteBusiEmployeeInfo",method = RequestMethod.POST)
    public String deleteBusiEmployeeInfo(@RequestParam("relationId") String relationId){
        EmpBusinessRelation empBusinessRelation = new EmpBusinessRelation();
        empBusinessRelation.setId(relationId);
        empBusinessRelation.setStatus(CommonEnum.DBData.DataStatus.delete.getValue());
        systemBaseDao.updateEmpBusinessRelation(empBusinessRelation);
        return "";
    }

    /**
     * 更新商家信息
     * @param id
     * @param businessName
     * @param owner
     * @param delLogo
     * @param delBusinessPicture
     * @param delIdcardPictureA
     * @param delIdcardPictureB
     * @param otherInformation
     * @param email
     * @param area
     * @param address
     * @param phoneNum
     * @param phoneNum2
     * @param businessType
     * @param customType
     * @param remark
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/editBusinessInfo", method = RequestMethod.POST)
    public String editBusinessInfo(HttpServletRequest httpServletRequest,
                                   @RequestParam("id") String id,
                                   @RequestParam(value = "businessName") String businessName,
                                   @RequestParam(value = "owner") String owner,
                                   @RequestParam(value = "delLogo", required = false) String delLogo,
                                   @RequestParam(value = "delBusinessPicture", required = false) String delBusinessPicture,
                                   @RequestParam(value = "delIdcardPictureA", required = false) String delIdcardPictureA,
                                   @RequestParam(value = "delIdcardPictureB", required = false) String delIdcardPictureB,
                                   @RequestParam("otherInformation") String otherInformation,
                                   @RequestParam("email") String email,
                                   @RequestParam("area") String area,
                                   @RequestParam("address") String address,
                                   @RequestParam("phoneNum") String phoneNum,
                                   @RequestParam("phoneNum2") String phoneNum2,
                                   @RequestParam("businessType") String businessType,
                                   @RequestParam("customType") String customType,
                                   @RequestParam("remark") String remark){
        BusinessInfo businessInfo = new BusinessInfo();
        businessInfo.setId(id);
        businessInfo.setBusinessName(businessName);
        businessInfo.setOwner(owner);
        businessInfo.setOtherInformation(otherInformation);
        businessInfo.setEmail(email);
        businessInfo.setArea(area);
        businessInfo.setAddress(address);
        businessInfo.setPhoneNum(phoneNum);
        businessInfo.setPhoneNum2(phoneNum2);
        businessInfo.setBusinessType(businessType);
        businessInfo.setCustomType(customType);
        businessInfo.setRemark(remark);
        //文件上传相对目录
        String relaPath =Configuration.getUploadStoreInfoPicturePath(id);
        try {
            if (!"".equals(delLogo) && delLogo!=null){
                businessInfo.setLogo("");
                deleteExistsFile(relaPath + "logo", delLogo);
            }

            if (!"".equals(delBusinessPicture) && delBusinessPicture != null){
                businessInfo.setBusinessPicture("");
                deleteExistsFile(relaPath + "businessPicture", delBusinessPicture);
            }

            if (!"".equals(delIdcardPictureA) && delIdcardPictureA != null){
                businessInfo.setIdcardPictureA("");
                deleteExistsFile(relaPath + "idcardPictureA", delIdcardPictureA);
            }

            if (!"".equals(delIdcardPictureB) && delIdcardPictureB != null){
                businessInfo.setIdcardPictureB("");
                deleteExistsFile(relaPath + "idcardPictureB", delIdcardPictureB);
            }

            //上传文件
            UploadFileResponse uploadFileResponse = super.uploadMultiFileBykey(httpServletRequest, relaPath);
            String uploadPath = uploadFileResponse.getFileList().toString();
            if (!"".equals(uploadPath)){
                StringBuffer pictureBuf = new StringBuffer();
                String value = "";
                Iterator iterator = uploadFileResponse.getTypeValueMap().entrySet().iterator();
                while (iterator.hasNext()){
                    Map.Entry entry = (Map.Entry) iterator.next();
                    String multiKey = String.valueOf(entry.getKey());
                    value = String.valueOf(entry.getValue());
                    String key = multiKey.substring(0, multiKey.length() - 3);
                    if("logo".equals(key)){
                        businessInfo.setLogo(value);
                    }
                    if ("businessPicture".equals(key)){
                        businessInfo.setBusinessPicture(value);
                    }
                    if ("idcardPictureA".equals(key)){
                        businessInfo.setIdcardPictureA(value);
                    }
                    if ("idcardPictureB".equals(key)){
                        businessInfo.setIdcardPictureB(value);
                    }
                }
            }
        }catch (BusiException e){
            //没传图片继续执行
        }catch (IOException e){
            logger.error("SystemBaseController-editBusinessInfo:", e);
            throw new BusiException(CommonEnum.ReturnCode.ManagerCode.manager_err_uploadfileerror.getValue());
        }
        systemBaseDao.updateBusinessInfo(businessInfo);
        return "";
    }

    /**
     * 编辑门店信息
     * @param request
     * @param id
     * @param storeName
     * @param managerName
     * @param phoneNum
     * @param phoneNum2
     * @param email
     * @param area
     * @param address
     * @param delDoorPicture
     * @param delClassroomPicture
     * @param delReceptionPicture
     * @param delLicensePicture
     * @param contactWay
     * @param serviceIntroduction
     * @param startTime
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/editStoreInfo", method = RequestMethod.POST)
    public String editStoreInfo(HttpServletRequest request,
                                @RequestParam(value = "id") final String id,
                                @RequestParam(value = "storeName") final String storeName,
                                @RequestParam(value = "managerName") final String managerName,
                                @RequestParam(value = "phoneNum") final String phoneNum,
                                @RequestParam(value = "phoneNum2",required = false) final String phoneNum2,
                                @RequestParam(value = "email" ,required = false) final String email,
                                @RequestParam(value = "area") final String area,
                                @RequestParam(value = "address") final String address,
                                @RequestParam(value = "delDoorPicture" , required = false) final String delDoorPicture,
                                @RequestParam(value = "delClassroomPicture",required = false) final String delClassroomPicture,
                                @RequestParam(value = "delReceptionPicture", required = false) final String delReceptionPicture,
                                @RequestParam(value = "delLicensePicture", required = false) final String delLicensePicture,
                                @RequestParam(value = "contactWay") final String contactWay,
                                @RequestParam(value = "serviceIntroduction") final String serviceIntroduction,
                                @RequestParam(value = "startTime") final String startTime){
        StringBuffer doorPictureBuf = new StringBuffer();
        StringBuffer classroomPictureBuf = new StringBuffer();
        StringBuffer licensePictureBuf = new StringBuffer();
        StringBuffer receptionPictureBuf = new StringBuffer();
        StoreInfo storeInfo = new StoreInfo();
        storeInfo.setId(id);
        storeInfo.setStoreName(storeName);
        storeInfo.setManagerName(managerName);
        storeInfo.setPhoneNum(phoneNum);
        storeInfo.setPhoneNum2(phoneNum2);
        storeInfo.setEmail(email);
        storeInfo.setArea(area);
        storeInfo.setAddress(address);
        storeInfo.setContactWay(contactWay);
        storeInfo.setServiceIntroduction(serviceIntroduction);
        storeInfo.setStartTime(startTime);
        StoreInfo storeInfo1 = systemBaseDao.getStoreInfo(id);
        if (storeInfo1.getDoorPicture()!=null && !"".equals(storeInfo1.getDoorPicture())){
            doorPictureBuf.append(";").append(storeInfo1.getDoorPicture());
        }
        if (storeInfo1.getClassroomPicture()!=null && !"".equals(storeInfo1.getClassroomPicture())){
            classroomPictureBuf.append(";").append(storeInfo1.getClassroomPicture());
        }
        if (storeInfo1.getLicensePicture()!=null && !"".equals(storeInfo1.getLicensePicture())){
            licensePictureBuf.append(";").append(storeInfo1.getLicensePicture());
        }
        if (storeInfo1.getReceptionPicture()!=null && !"".equals(storeInfo1.getReceptionPicture())){
            receptionPictureBuf.append(";").append(storeInfo1.getReceptionPicture());
        }
        //文件上传相对目录
        String relaPath =Configuration.getUploadStoreInfoPicturePath(id);
        try {
            if (!"".equals(delDoorPicture) && delDoorPicture!=null){
                //删除数据库记录
                if (storeInfo1.getDoorPicture()!=null && !"".equals(storeInfo1.getDoorPicture())){
                    String[] doorPicture = storeInfo1.getDoorPicture().split(";");
                    ArrayList<String> list = new ArrayList<String>(Arrays.asList(doorPicture));
                    doorPictureBuf = removeItemAndAddExistsItem(list,delDoorPicture);
                    if(doorPictureBuf.toString().length() > 0){
                        storeInfo.setDoorPicture(doorPictureBuf.toString().substring(1));
                    }else{
                        storeInfo.setDoorPicture("");
                    }
                    deleteExistsFile(relaPath+"doorPicture",delDoorPicture);
                }
            }
            if (!"".equals(delClassroomPicture) && delClassroomPicture!=null){
                if (storeInfo1.getClassroomPicture()!=null && !"".equals(storeInfo1.getClassroomPicture())){
                    String[] classroomPicture = storeInfo1.getClassroomPicture().split(";");
                    ArrayList<String> list = new ArrayList<String>(Arrays.asList(classroomPicture));
                    classroomPictureBuf = removeItemAndAddExistsItem(list,delClassroomPicture);
                    if(classroomPictureBuf.toString().length() > 0){
                        storeInfo.setClassroomPicture(classroomPictureBuf.toString().substring(1));
                    }else{
                        storeInfo.setClassroomPicture("");
                    }
                    deleteExistsFile(relaPath+"classroomPicture",delClassroomPicture);
                }
            }
            if (!"".equals(delLicensePicture) && delLicensePicture!=null){
                if (storeInfo1.getLicensePicture()!=null && !"".equals(storeInfo1.getLicensePicture())){
                    String[] licensePicture = storeInfo1.getLicensePicture().split(";");
                    ArrayList<String> list = new ArrayList<String>(Arrays.asList(licensePicture));
                    licensePictureBuf = removeItemAndAddExistsItem(list,delLicensePicture);
                    if(licensePictureBuf.toString().length() > 0){
                        storeInfo.setLicensePicture(licensePictureBuf.toString().substring(1));
                    }else{
                        storeInfo.setLicensePicture("");
                    }
                    deleteExistsFile(relaPath+"licensePicture",delLicensePicture);
                }
            }
            if (!"".equals(delReceptionPicture) && delReceptionPicture!=null){
                if (storeInfo1.getReceptionPicture()!=null && !"".equals(storeInfo1.getReceptionPicture())){
                    String[] receptionPicture = storeInfo1.getReceptionPicture().split(";");
                    ArrayList<String> list = new ArrayList<String>(Arrays.asList(receptionPicture));
                    receptionPictureBuf = removeItemAndAddExistsItem(list,delReceptionPicture);
                    if(receptionPictureBuf.toString().length() > 0){
                        storeInfo.setReceptionPicture(receptionPictureBuf.toString().substring(1));
                    }else{
                        storeInfo.setReceptionPicture("");
                    }
                    deleteExistsFile(relaPath+"receptionPicture",delReceptionPicture);
                }
            }
            //上传文件
            UploadFileResponse uploadFileResponse = super.uploadMultiFileBykey(request, relaPath);
            String uploadPath = uploadFileResponse.getFileList().toString();
            if (!"".equals(uploadPath)){
                String value = "";
                Iterator iterator = uploadFileResponse.getTypeValueMap().entrySet().iterator();
                while (iterator.hasNext()){
                    Map.Entry entry = (Map.Entry) iterator.next();
                    String multiKey = String.valueOf(entry.getKey());
                    value = String.valueOf(entry.getValue());
                    String key = multiKey.substring(0, multiKey.length() - 3);
                    if ("doorPicture".equals(key)){
                        doorPictureBuf.append(";").append(value);
                    }else if ("classroomPicture".equals(key)){
                        classroomPictureBuf.append(";").append(value);
                    }else if ("receptionPicture".equals(key)){
                        receptionPictureBuf.append(";").append(value);
                    }else if ("licensePicture".equals(key)){
                        licensePictureBuf.append(";").append(value);
                    }
                }
                if (doorPictureBuf.toString().length()>0){
                    storeInfo.setDoorPicture(doorPictureBuf.toString().substring(1));
                }
                if (classroomPictureBuf.toString().length()>0){
                    storeInfo.setClassroomPicture(classroomPictureBuf.toString().substring(1));
                }
                if (licensePictureBuf.toString().length()>0){
                    storeInfo.setLicensePicture(licensePictureBuf.toString().substring(1));
                }
                if (receptionPictureBuf.toString().length()>0){
                    storeInfo.setReceptionPicture(receptionPictureBuf.toString().substring(1));
                }
            }
        }catch (BusiException e){
            //没传图片继续执行
        }catch (IOException e){
            logger.error("SystemBaseController-addStoreInfo:", e);
            throw new BusiException(CommonEnum.ReturnCode.ManagerCode.manager_err_uploadfileerror.getValue());
        }
        systemBaseDao.updateStoreInfo(storeInfo);
        return "";
    }

    /**
     * 添加会员信息
     * @param businessId
     * @param storeId
     * @param userName
     * @param sex
     * @param birthday
     * @param phoneNum
     * @param creator
     * @param remark
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addMemberInfoAndCardInfo", method = RequestMethod.POST)
    public String addMemberInfoAndCardInfo(@RequestParam(value = "businessId") final String businessId,
                                           @RequestParam(value = "storeId") final String storeId,
                                           @RequestParam(value = "userName",required = false) final String userName,
                                           @RequestParam(value = "nickName") final String nickName,
                                           @RequestParam(value = "sex") final String sex,
                                           @RequestParam(value = "birthday", required = false) final String birthday,
                                           @RequestParam(value = "phoneNum") final String phoneNum,
                                           @RequestParam(value = "memberTags") final String memberTags,
                                           @RequestParam(value = "memberCardListStr",required = false) final String memberCardListStr,
                                           @RequestParam(value = "creator") final String creator,
                                           @RequestParam(value = "remark", required = false) final String remark){
        List<HistoryCard> historyCardList = new ArrayList<>();
        BusinessMember businessMember = new BusinessMember();
        businessMember.setBusinessId(businessId);
        businessMember.setStoreId(storeId);
        businessMember.setUserName(userName);
        businessMember.setNickName(nickName);
        businessMember.setSex(sex);
        businessMember.setBirthday(birthday);
        businessMember.setMemberTags(memberTags);
        businessMember.setCreator(creator);
        businessMember.setPhoneNum(phoneNum);
        businessMember.setRemark(remark);
        if (memberCardListStr != null){
            JSONArray jsonArray1 = JSONArray.fromObject(memberCardListStr);
            historyCardList = (List)net.sf.json.JSONArray.toCollection(jsonArray1,HistoryCard.class);
        }
        systemBaseService.addMemberInfoAndCardInfo(businessMember, historyCardList);
        return "";
    }

    /**
     * 编辑会员信息
     * @param id
     * @param businessId
     * @param storeId
     * @param userName
     * @param sex
     * @param birthday
     * @param phoneNum
     * @param memberTags
     * @param addMemberCardListStr
     * @param updateMemberCardListStr
     * @param delCardStr
     * @param remark
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/editMemberInfoAndCardInfo", method = RequestMethod.POST)
    public String editMemberInfoAndCardInfo(@RequestParam(value = "id") final String id,
                                            @RequestParam(value = "userId") final String userId,
                                            @RequestParam(value = "businessId") final String businessId,
                                            @RequestParam(value = "storeId") final String storeId,
                                            @RequestParam(value = "userName",required = false) final String userName,
                                            @RequestParam(value = "nickName") final String nickName,
                                            @RequestParam(value = "sex") final String sex,
                                            @RequestParam(value = "birthday", required = false) final String birthday,
                                            @RequestParam(value = "phoneNum") final String phoneNum,
                                            @RequestParam(value = "memberTags") final String memberTags,
                                            @RequestParam(value = "addMemberCardListStr") final String addMemberCardListStr,
                                            @RequestParam(value = "updateMemberCardListStr",required = false) final String updateMemberCardListStr,
                                            @RequestParam(value = "delCardStr", required = false) final String delCardStr,
                                            @RequestParam(value = "remark", required = false) final String remark){
        String isExists = systemBaseDao.isExistsMemberPhoneNum(phoneNum, userId);
        if (!isExists.equals("0")){
            throw new BusiException(CommonEnum.ReturnCode.ManagerCode.manager_err_memberphoneexists.getValue());
        }
        List<HistoryCard> updateHistoryCardList = new ArrayList<>();
        List<HistoryCard> addHistoreyCardList = new ArrayList<>();
        List<String> delCardList = new ArrayList<>();
        BusinessMember businessMember = new BusinessMember();
        businessMember.setMemberId(id);
        businessMember.setUserId(userId);
        businessMember.setBusinessId(businessId);
        businessMember.setStoreId(storeId);
        businessMember.setUserName(userName);
        businessMember.setNickName(nickName);
        businessMember.setSex(sex);
        businessMember.setBirthday(birthday);
        businessMember.setMemberTags(memberTags);
        businessMember.setPhoneNum(phoneNum);
        businessMember.setRemark(remark);
        if (addMemberCardListStr != null){
            JSONArray jsonArray1 = JSONArray.fromObject(addMemberCardListStr);
            addHistoreyCardList = (List)net.sf.json.JSONArray.toCollection(jsonArray1,HistoryCard.class);
        }
        if (updateMemberCardListStr != null){
            JSONArray jsonArray2 = JSONArray.fromObject(updateMemberCardListStr);
            updateHistoryCardList = (List)net.sf.json.JSONArray.toCollection(jsonArray2,HistoryCard.class);
        }
        if (delCardStr != null){
            String[] array = delCardStr.split(",");
            delCardList = Arrays.asList(array);
        }
        systemBaseService.updateMemberInfoAndCardInfo(businessMember,addHistoreyCardList,updateHistoryCardList,delCardList);
        return "";
    }

    /**
     * 删除商家会员信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteBusinessMember", method = RequestMethod.POST)
    public String deleteBusinessMember(@RequestParam("id") String id,
                                       @RequestParam("businessId") String businessId){
        systemBaseService.delBusinessMemberInfo(id,businessId);
        return "";
    }

    /**
     * 获取会员卡列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listMemberCard", method = RequestMethod.POST)
    public Map<String,Object> listMemberCard(@RequestParam("businessId") final String businessId){
        Map<String,Object> map = new HashMap<>();
        List<MemberCard> memberCardList = systemBaseDao.listMemberCardInfo(businessId);
        map.put("memberCardList",memberCardList);
        return map;
    }

    /**
     * 获取门店中所有会员信息
     * @param storeId
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listMemberInfoByStoreId", method = RequestMethod.POST)
    public Map<String,Object> listMemberInfoByStoreId(@RequestParam(value = "storeId") final String storeId,
                                                      @RequestParam(value = "page") Integer page){
        Map<String,Object> map = new HashMap<>();
        List<ResponseMember> responseMemberList = systemBaseService.listMemberByStoreId(storeId, page);
        String count = systemBaseDao.getMemberCountByStoreId(storeId);
        map.put("memberList",responseMemberList);
        map.put("count",count);
        return map;
    }

    /**
     * 获取会员在某商家的会员卡列表
     * @param id
     * @param businessId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listMemberCardByMemberId", method = RequestMethod.POST)
    public Map<String,Object> listMemberCardByMemberId(@RequestParam("id") String id,
                                                       @RequestParam("businessId") String businessId){
        Map<String,Object> map = new HashMap<>();
        List<MembercardRelation> memberCardRelationList = systemBaseDao.listMemberCardByMemberId(id, businessId);
        map.put("memberCardRelationList",memberCardRelationList);
        return map;
    }

    /**
     * 获取会员详细信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getMemberInfoById", method = RequestMethod.POST)
    public Map<String,Object> getMemberInfoById(@RequestParam("id") String id){
        Map<String,Object> map = new HashMap<>();
        BusinessMember businessMember = new BusinessMember();
        businessMember.setId(id);
        ResponseMember responseMember = systemBaseDao.queryMemberInfo(businessMember);
        map.put("responseMember",responseMember);
        return map;
    }

    /**
     * 获取客户端用户列表
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listClientUser",method = RequestMethod.POST)
    public Map<String,Object> listClientUser(@RequestParam("page") String page){
        Map<String,Object> map = new HashMap<>();
        List<ClientUser> clientUserList = systemBaseService.listClientUser(Integer.valueOf(page));
        String count = userDao.getClientUserCount();
        map.put("clientUserList",clientUserList);
        map.put("count",count);
        return map;
    }

    /**
     * 获取用户关联的门店信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listStoreInfoByClientUserId",method = RequestMethod.POST)
    public Map<String,Object> listStoreInfoByClientUserId(@RequestParam("id") String id){
        Map<String,Object> map = new HashMap<>();
        List<StoreInfo> storeInfoList = systemBaseDao.listStoreInfoByClientUserId(id);
        map.put("storeInfoList",storeInfoList);
        return map;
    }

    /**
     * 获取员工关联的门店信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listStoreInfoByEmployeeId",method = RequestMethod.POST)
    public Map<String,Object> listStoreInfoByEmployeeId(@RequestParam("id") String id){
        Map<String,Object> map = new HashMap<>();
        List<StoreInfo> storeInfoList = systemBaseDao.listStoreInfoByEmployeeId(id);
        map.put("storeInfoList",storeInfoList);
        return map;
    }

//    public static void main(String[] args) {
////        List<String> list = new ArrayList<>();
////        list.add("1");
////        list.add("2");
//        String[] strings = {"1","2","3"};
//        ArrayList<String> list = new ArrayList<String>(Arrays.asList(strings));
////        List<String> list = Arrays.asList(strings);
//        Iterator<String> it = list.iterator();
//        while(it.hasNext()){
//            it.next();
//            it.remove();
//        }
////        removeItemAndAddExistsItem(list,"/1");
//    }
}
