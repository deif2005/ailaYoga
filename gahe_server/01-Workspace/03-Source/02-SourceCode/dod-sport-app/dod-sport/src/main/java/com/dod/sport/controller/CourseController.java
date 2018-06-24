package com.dod.sport.controller;

import com.dod.sport.config.Configuration;
import com.dod.sport.constant.SysConfigConstants;
import com.dod.sport.constant.WebConstants;
import com.dod.sport.dao.ICourseDao;
import com.dod.sport.dao.IEmployeeDao;
import com.dod.sport.domain.common.BusiException;
import com.dod.sport.domain.common.UploadFileResponse;
import com.dod.sport.domain.po.*;
import com.dod.sport.domain.po.Base.Classroom;
import com.dod.sport.domain.po.Course.*;
import com.dod.sport.jgpush.Jgpushtool;
import com.dod.sport.redis.RedisData;
import com.dod.sport.service.ICourseService;
import com.dod.sport.service.IMessageService;
import com.dod.sport.Scheduler.SchedulerUtil;
import com.dod.sport.Scheduler.Job.SendCourseCommentMessageJob;
import com.dod.sport.util.CommonEnum;
import com.dod.sport.util.DateUtil;
import com.dod.sport.util.MD5Utils;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import org.quartz.*;


/**
 * Created by defi on 2017-08-23.
 * 课程管理controller
 */
@Controller
@RequestMapping(value = "api/Course/v1")
public class CourseController extends BaseController {

    @Autowired
    private ICourseService courseService;

    @Autowired
    private ICourseDao courseDao;

    @Autowired
    private IEmployeeDao employeeDao;

    @Autowired
    private IMessageService messageService;

    /**
     * 获取课程信息列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listCourseInfo", method = RequestMethod.POST)
    public Map listCourseInfo(@RequestParam("businessId") final String businessId){
        Map map = new HashMap<>();
        List<Course> courseList = courseService.listCourseInfo(businessId);
        map.put("courseList",courseList);
        return map;
    }

    /**
     * 根据课程名称模糊查询课程
     * @param businessId
     * @param courseName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listCourseByName", method = RequestMethod.POST)
    public Map<String ,Object>listCourseByName(String businessId, String courseName){
        Map map = new HashMap<>();
        List<Course> courseList = courseService.listCourseByName(businessId, courseName);
        map.put("courseList",courseList);
        return map;
    }

    /**
     * 获取课程信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getCourseInfo", method = RequestMethod.POST)
    public Map getCourseInfo(@RequestParam("id") final String id){
        Map map = new HashMap<>();
        Course course = courseService.getCourseInfo(id);
        map.put("course",course);
        return map;
    }

    /**
     * 新增课程信息
     * @param courseName
     * @param duration
     * @param courseTypeId
     * @param courseMeans
     * @param courseStatus
     * @param isExperience
     * @param remark
     * @param creator
     */
    @ResponseBody
    @RequestMapping(value = "/addCourse", method = RequestMethod.POST)
    public String addCourse(HttpServletRequest request,
                        @RequestParam("businessId") final String businessId,
                        @RequestParam("courseName") final String courseName,
                        @RequestParam("duration") final String duration,
                        @RequestParam("courseTypeId") final String courseTypeId,
                        @RequestParam("courseMeans") final String courseMeans,
                        @RequestParam("courseStatus") final String courseStatus,
                        @RequestParam("isExperience") final String isExperience,
                        @RequestParam("remark") final String remark,
                        @RequestParam("creator") final String creator){
        Course course = new Course();
        course.setBusinessId(businessId);
        course.setCourseName(courseName);
        course.setDuration(duration);
        course.setCourseTypeId(courseTypeId);
        course.setCourseMeans(courseMeans);
        course.setCourseStatus(courseStatus);
        course.setIsExperience(isExperience);
        course.setRemark(remark);
        course.setCreator(creator);
        try {
            //文件上传相对目录
            String relaPath = Configuration.getUploadCoursePicturePath(businessId);
            //上传文件
            UploadFileResponse uploadFileResponse = super.uploadFile(request,relaPath);
            String uploadPath = uploadFileResponse.getFileList().toString();
            if (!"".equals(uploadPath)){
                for (String path : uploadFileResponse.getFileList()){
                    course.setIconPath(path);
                }
            }
        }catch (BusiException e){
            //没传图片继续执行
        }catch (IOException e){
            logger.error("CourseController-addCourse:", e);
            throw new BusiException(CommonEnum.ReturnCode.SystemCode.BusinessCode.busi_err_uploadfileerror.getValue());
        }
        courseService.addCourse(course);
        return "";
    }

    /**
     * 更新课程信息
     * @param request
     * @param id
     * @param courseName
     * @param duration
     * @param courseTypeId
     * @param courseMeans
     * @param courseStatus
     * @param isExperience
     * @param remark
     * @param isUpdateIcon
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/editCourseInfo", method = RequestMethod.POST)
    public String editCourseInfo(HttpServletRequest request,
                              @RequestParam(value = "id") final String id,
                              @RequestParam(value = "businessId") final String businessId,
                              @RequestParam(value = "courseName") final String courseName,
                              @RequestParam(value = "duration") final String duration,
                              @RequestParam(value = "courseTypeId") final String courseTypeId,
                              @RequestParam(value = "courseMeans") final String courseMeans,
                              @RequestParam(value = "courseStatus") final String courseStatus,
                              @RequestParam(value = "isExperience") final String isExperience,
                              @RequestParam(value = "remark") final String remark,
                              @RequestParam(value = "isUpdateIcon") final String isUpdateIcon){
        Course course = new Course();
        course.setId(id);
        course.setCourseName(courseName);
        course.setDuration(duration);
        course.setCourseTypeId(courseTypeId);
        course.setCourseMeans(courseMeans);
        course.setCourseStatus(courseStatus);
        course.setIsExperience(isExperience);
        course.setRemark(remark);
        if ("0".equals(isUpdateIcon)){
            courseService.updateCourseInfo(course,false);
        }else {
            try {
                //文件上传相对目录
                String relaPath = Configuration.getUploadCoursePicturePath(businessId);
                //上传文件
                UploadFileResponse uploadFileResponse = super.uploadFile(request,relaPath);
                String uploadPath = uploadFileResponse.getFileList().toString();
                if (!"".equals(uploadPath)){
                    for (String path : uploadFileResponse.getFileList()){
                        course.setIconPath(path);
                    }
                }
            }catch (BusiException e){
                //没传图片继续执行
            }catch (IOException e){
                logger.error("CourseController-addCourse:", e);
                throw new BusiException(CommonEnum.ReturnCode.SystemCode.BusinessCode.busi_err_uploadfileerror.getValue());
            }
            courseService.updateCourseInfo(course, true);
        }
        return "";
    }

    /**
     * 获取课程类型信息列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listCourseType", method = RequestMethod.POST)
    public Map listCourseType(@RequestParam(value = "businessId") final String businessId){
        Map map = new HashMap<>();
        List<CourseType> courseTypeList = courseDao.listCourseType(businessId);
        map.put("courseTypeList",courseTypeList);
        return map;
    }

    /**
     * 获取课程类型信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getCourseType", method = RequestMethod.POST)
    public Map getCourseType(@RequestParam("id") final String id){
        Map map = new HashMap<>();
        CourseType courseType = courseDao.getCourseType(id);
        map.put("courseType",courseType);
        return map;
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
        courseService.addCourseType(courseType);
        return "";
    }


    /**
     * 获取课程-卡信息
     * @param businessId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listCourseAndCard", method = RequestMethod.POST)
    public Map listCourseAndCard(@RequestParam("businessId") final String businessId,
                                 @RequestParam("courseId") final String courseId){
        Map<String,Object> map = new HashMap<>();
        List<CourseAndCard> courseAndCardList = courseDao.listCourseAndCard(businessId,courseId);
        map.put("courseAndCardList",courseAndCardList);
        return map;
    }

    /**
     * 获取老师-课程关联信息
     * @param businessId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listTeacherAndCourse", method = RequestMethod.POST)
    public Map listTeacherAndCourse(@RequestParam("businessId") final String businessId,
                                    @RequestParam("employeeId") final String employeeId){
        Map<String,Object> map = new HashMap<>();
        List<TeacherAndCourse> teacherAndCourseList = courseDao.listTeacherAndCourse(businessId,employeeId);
        map.put("teacherAndCourseList",teacherAndCourseList);
        return map;
    }


    /**
     * 新增课程卡关系数据
     * @param courseId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addCourseAndCard", method = RequestMethod.POST)
    public String addCourseAndCard(@RequestParam(value = "courseId") final String courseId,
                                   @RequestParam(value = "cardIdStr") final String cardIdStr){
//        net.sf.json.JSONArray jsonArray1 = net.sf.json.JSONArray.fromObject(dataJson);
//        List<CourseAndCard> courseAndCardList =
//                (List)net.sf.json.JSONArray.toCollection(jsonArray1,CourseAndCard.class);

        courseService.addCourseAndCard(courseId,cardIdStr);
        return "";
    }

    /**
     * 新增老师-课程关联数据
     * @param employeeId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addTeacherAndCourse", method = RequestMethod.POST)
    public String addTeacherAndCourse(@RequestParam(value = "employeeId") final String employeeId,
                                      @RequestParam(value = "courseIdStr") final String courseIdStr){
        courseService.addTeacherAndCourse(employeeId,courseIdStr);
        return "";
    }

    /**
     * 修改课程卡关系数据
     * @param dataJson
     * @return
     */
//    @ResponseBody
//    @RequestMapping(value = "/editCourseAndCard", method = RequestMethod.POST)
//    public String editCourseAndCard(@RequestParam(value = "dataJson") final String dataJson){
//        net.sf.json.JSONArray jsonArray1 = net.sf.json.JSONArray.fromObject(dataJson);
//        List<CourseAndCard> courseAndCardList =
//                (List)net.sf.json.JSONArray.toCollection(jsonArray1,CourseAndCard.class);
//        courseService.updateCourseAndCard(courseAndCardList);
//        return "";
//    }

    /**
     * 获取团课周排课计划
     * @param storeId
     * @param year
     * @param weekIndex
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listCoursePlanByWeek", method = RequestMethod.POST)
    public Map listCoursePlanByWeek(@RequestParam("storeId") String storeId,
                                    @RequestParam("year") String year,
                                    @RequestParam("weekIndex") String weekIndex){
        Map<String,Object> map = new HashMap<>();
        List<CoursePlan> coursePlanList = courseService.listCoursePlanByWeek(storeId, "", year, weekIndex);
        map.put("coursePlanList",coursePlanList);
        return map;
    }

    /**
     * 按日期获取某教师的排课计划
     * @param storeId
     * @param employeeId
     * @param classDate
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listCoursePlanByEmployeeId", method = RequestMethod.POST)
    public Map listCoursePlanByEmployeeId(@RequestParam("storeId") String storeId,
                                    @RequestParam("employeeId") String employeeId,
                                    @RequestParam("classDate") String classDate){
        Map<String,Object> map = new HashMap<>();
        Date date = DateUtil.parse(classDate,"yyyy-MM-dd");
        classDate = DateUtil.formatDate(SysConfigConstants.DATE_FORMAT_FORDATE,date);
        List<CoursePlan> coursePlanList = courseDao.listCoursePlanByEmployeeId(storeId, employeeId, classDate);
        map.put("coursePlanList",coursePlanList);
        return map;
    }

    /**
     * 新增团课排课计划
     * @param storeId
     * @param courseId
     * @param employeeId
     * @param lowPersons
     * @param upperPersons
     * @param classDatetime
     * @param enable
     * @param isExperience
     * @param remark
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addCoursePlan", method = RequestMethod.POST)
    public String addCoursePlan(@RequestParam("storeId") String storeId,
                                @RequestParam("courseId") String courseId,
                                @RequestParam("employeeId") String employeeId,
                                @RequestParam("classroomId") String classroomId,
                                @RequestParam("lowPersons") String lowPersons,
                                @RequestParam("upperPersons") String upperPersons,
                                @RequestParam("classDatetime") String classDatetime,
                                @RequestParam("duration") String duration,
                                @RequestParam("enable") String enable,
                                @RequestParam("isExperience") String isExperience,
                                @RequestParam("remark") String remark,
                                @RequestParam("creator") String creator){
        CoursePlan coursePlan = new CoursePlan();
        coursePlan.setStoreId(storeId);
        coursePlan.setCourseId(courseId);
        coursePlan.setEmployeeId(employeeId);
        coursePlan.setClassroomId(classroomId);
        coursePlan.setLowPersons(lowPersons);
        coursePlan.setUpperPersons(upperPersons);
        coursePlan.setClassDatetime(classDatetime);
        coursePlan.setDuration(duration);
        coursePlan.setEnable(enable);
        coursePlan.setIsExperience(isExperience);
        coursePlan.setRemark(remark);
        coursePlan.setCreator(creator);
        courseService.addCoursePlan(coursePlan);
        return "";
    }

    /**
     * 编辑团课排课计划
     * @param id
     * @param courseId
     * @param employeeId
     * @param lowPersons
     * @param upperPersons
     * @param classDatetime
     * @param duration
     * @param enable
     * @param isExperience
     * @param remark
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/editCoursePlan", method = RequestMethod.POST)
    public String editCoursePlan(@RequestParam("id") String id,
                                 @RequestParam("courseId") String courseId,
                                 @RequestParam("employeeId") String employeeId,
                                 @RequestParam("classroomId") String classroomId,
                                 @RequestParam("lowPersons") String lowPersons,
                                 @RequestParam("upperPersons") String upperPersons,
                                 @RequestParam("classDatetime") String classDatetime,
                                 @RequestParam("duration") String duration,
                                 @RequestParam("enable") String enable,
                                 @RequestParam("isExperience") String isExperience,
                                 @RequestParam(value = "remark",required = false) String remark){
        CoursePlan coursePlan = new CoursePlan();
        coursePlan.setId(id);
        coursePlan.setCourseId(courseId);
        coursePlan.setEmployeeId(employeeId);
        coursePlan.setClassroomId(classroomId);
        coursePlan.setLowPersons(lowPersons);
        coursePlan.setUpperPersons(upperPersons);
        coursePlan.setClassDatetime(classDatetime);
        coursePlan.setDuration(duration);
        coursePlan.setEnable(enable);
        coursePlan.setIsExperience(isExperience);
        coursePlan.setRemark(remark);
        courseService.updateCoursePlan(coursePlan);
        return "";
    }

    /**
     * 删除团课排课计划
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteCoursePlan", method = RequestMethod.POST)
    public String deleteCoursePlan(@RequestParam("id") String id){
        courseDao.deleteCoursePlan(id);
        return "";
    }

    /**
     * 排课签到列表
     * @param storeId
     * @param employeeId
     * @param classDate
     * @param courseMeans 1 团课 2 私课 3 全部
     * @param dataType 1签到页面数据，2签到历史数据
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listCoursePlanSign", method = RequestMethod.POST)
    public Map<String,Object> listCoursePlanSign(@RequestParam("storeId") final String storeId,
                                                 @RequestParam("employeeId") String employeeId,
                                                 @RequestParam("classDate") final String classDate,
                                                 @RequestParam("courseMeans") final String courseMeans,
                                                 @RequestParam("dataType") final String dataType,
                                                 @RequestParam("page") String page){
        Map<String,Object> map = new HashMap<>();
        Date date = DateUtil.parse(classDate,SysConfigConstants.DATE_FORMAT_FORDATE);
        List<CoursePlanSign> listCoursePlanSign = new ArrayList<>();
//        根据employeeId的角色判断是否要传入该值
        //暂时从库里取值
        String roleId = employeeDao.getEmployeeRole(employeeId);
        UserRole userRole = RedisData.getRedisRoleInfo(roleId);
        if (userRole !=null){
            if (userRole.equals(CommonEnum.Authority.RoleCode.roleBoss.getValue()) ||
                    userRole.equals(CommonEnum.Authority.RoleCode.roleManager.getValue())){
                employeeId = "";
            }
        }
        if ("1".equals(courseMeans)){
            //签到页面显示还未结束的课程
            if ("1".equals(dataType)){
                listCoursePlanSign = courseService.listCoursePlanSignInfo(storeId, employeeId, 1, Integer.valueOf(page),
                        DateUtil.formatDate(SysConfigConstants.DATE_FORMAT_FORDATE,date));
            }else if ("2".equals(dataType)){ //历史记录显示已结束的课程
                listCoursePlanSign = courseService.listCoursePlanSignInfo(storeId, employeeId, 2, Integer.valueOf(page),
                        DateUtil.formatDate(SysConfigConstants.DATE_FORMAT_FORDATE,date));
            }
        }else if ("2".equals(courseMeans)){
            //签到页面显示还未结束的课程
            if ("1".equals(dataType)){
                listCoursePlanSign = courseService.listPrivateCoursePlanSignInfo(storeId, employeeId, 1, Integer.valueOf(page),
                        DateUtil.formatDate(SysConfigConstants.DATE_FORMAT_FORDATE, date));
            }else if ("2".equals(dataType)){ //历史记录显示已结束的课程
                listCoursePlanSign = courseService.listPrivateCoursePlanSignInfo(storeId, employeeId, 2, Integer.valueOf(page),
                        DateUtil.formatDate(SysConfigConstants.DATE_FORMAT_FORDATE, date));
            }
        }
        map.put("listCoursePlanSignInfo", listCoursePlanSign);
        return map;
    }

    /**
     * 获取约课人员列表
     * @param coursesignId
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listCourseOrder", method = RequestMethod.POST)
    public Map<String,Object> listCourseOrder(@RequestParam("coursesignId") String coursesignId,
                                              @RequestParam("page") String page){
        Map<String,Object> map = new HashMap<>();
        List<CourseOrder> courseOrderList = courseService.listCourseOrder(coursesignId, Integer.valueOf(page));
        map.put("courseOrderList",courseOrderList);
        return map;
    }

    /**
     * 课程签到 店长签到，教师签到
     * @param id
     * @param employeeId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/courseSign", method = RequestMethod.POST)
    public String courseSign(@RequestParam("id") String id,
                             @RequestParam("employeeId") String employeeId){
        //正式登录时启用
//        BusiEmployee busiEmployee = (BusiEmployee)request.getAttribute(WebConstants.USER_INFO);
        //暂时从库里取值
        String roleId = employeeDao.getEmployeeRole(employeeId);
        UserRole userRole = RedisData.getRedisRoleInfo(roleId); //busiEmployee.getUserRoleId()
        CourseSign courseSign = courseDao.getCourseSignInfoById(id);
        //未签到 则判断权限
        if ("1".equals(courseSign.getSignStatus())){
            //判断是否是店长及以上权限
            if (userRole !=null ||!userRole.getRoleCode().contains(CommonEnum.Authority.RoleCode.roleBoss.getValue())
                    && !userRole.getRoleCode().contains(CommonEnum.Authority.RoleCode.roleManager.getValue())){
                throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_notmanagerauth.getValue());
            }
            //店长签到
            courseService.managerCourseSign(id);
        }else if ("2".equals(courseSign.getSignStatus())){ //店长已签到
            //是当前课程老师
            if (courseSign.getEmployeeId().equals(employeeId)){
                //老师签到
                courseDao.updateCourseSignStatus(CommonEnum.Course.SignStatus.classteachersign.getValue(),
                        null,DateUtil.format(new Date(), SysConfigConstants.DATE_FORMAT_FORDATETIME), id);

                CoursePlan coursePlan = courseDao.getCoursePlanInfoBySignId(id);
                Date date = DateUtil.parse(DateUtil.subMinute(coursePlan.getClassDatetime(),
                        Integer.valueOf(coursePlan.getDuration())));
                List<String> courseOrderMemberIdList = courseDao.listCourseOrderMember(id,
                        CommonEnum.Course.OrderStatus.signed.getIntegerVal());

                //课程结束时发送课程评论消息
                SchedulerUtil schedulerUtil = new SchedulerUtil();
                try {
                    JobDataMap jobDataMap = new JobDataMap();
                    jobDataMap.put("memberIdList",courseOrderMemberIdList);
                    schedulerUtil.setJobDataMap(jobDataMap);
                    schedulerUtil.setJobClass(SendCourseCommentMessageJob.class);
                    schedulerUtil.executeJobWithSimpleTrigger(date);
                }catch (Exception e){
                    logger.error("任务调度异常",e);
                    throw new BusiException(CommonEnum.ReturnCode.SystemCode.sys_err_exception.getValue());
                }

            }else { //其它人员不允许签到
                throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_teachernotmatch.getValue());
            }
        }else if ("3".equals(courseSign.getSignStatus())){ //都已签到 前端应在界面做控制
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_signcompleted.getValue());
        }
        return "";
    }

    /**
     * 取消课程
     * @param employeeId
     * @param courseplanId
     * @param coursesignId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/cancelCourse", method = RequestMethod.POST)
    public String courseCancel(@RequestParam("employeeId") String employeeId,
                               @RequestParam("courseplanId") String courseplanId,
                               @RequestParam("coursesignId") String coursesignId){
        ResponseEmployee remp = (ResponseEmployee)request.getAttribute(WebConstants.USER_INFO);
        //UserRole userRole = RedisData.getRedisRoleInfo(busiEmployee.getUserRoleId());
        //从库里取出 defi 0925
        String roleId = employeeDao.getEmployeeRole(employeeId);
        UserRole userRole = RedisData.getRedisRoleInfo(roleId);

        //判断employeeId的执行权限
        if (!userRole.getRoleCode().contains(CommonEnum.Authority.RoleCode.roleBoss.getValue())
                && !userRole.getRoleCode().contains(CommonEnum.Authority.RoleCode.roleManager.getValue())){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_notmanagerauth.getValue());
        }
        courseService.courseCancelByCourse(courseplanId,coursesignId);
        //发送消息至每个预约的会员
        List<String> courseOrderMemberIdList = courseDao.listCourseOrderMember(coursesignId,null);
        String[] memberIdArray = courseOrderMemberIdList.toArray(new String[courseOrderMemberIdList.size()]);
        String messageContent = messageService.getCourseOrderCanceledMessageContent(courseplanId);
        HashMap<String,String> extrasMap = new HashMap<>();
        extrasMap.put("url","message");
        messageService.sendMessageToAliasMsg("取消课程通知", messageContent, messageContent, extrasMap, memberIdArray);
        return "";
    }

    /**
     * 更新会员预约状态
     * @param employeeId
     * @param courseOrderId
     * @param orderStatus 预约状态:1已预约，2已签到，3爽约，4取消
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/editMemberSignStatus", method = RequestMethod.POST)
    public String editMemberSignStatus(@RequestParam("employeeId") String employeeId,
                                       @RequestParam("courseplanId") String courseplanId,
                                       @RequestParam("courseOrderId") String courseOrderId,
                                       @RequestParam("orderStatus") String orderStatus){
        //判断employeeId是否有修改权限
//        CourseSign courseSign = courseDao.getCourseSignInfoById(courseplanId);
        //这里可以把employeeId传到前端 有前端判断是否有权限

        //是当前课程老师
//        if (courseSign.getEmployeeId().equals(employeeId)){
            courseDao.updateCourseOrderStatusById(courseOrderId, Integer.valueOf(orderStatus));
//        }else{
//            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_teachernotmatch.getValue());
//        }
        return "";
    }

    /**
     * 获取私教排课列表
     * @param storeId
     * @param classDate
     * @param employeeName
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listPrivateCoursePlan", method = RequestMethod.POST)
    public Map<String,Object> listPrivateCoursePlan(@RequestParam(value = "storeId") String storeId,
                                                    @RequestParam(value = "classDate") String classDate,
                                                    @RequestParam(value = "employeeName", required = false) String employeeName,
                                                    @RequestParam(value = "page") String page){
        Map<String,Object> map = new HashMap<String,Object>();
        List<PrivateCoursePlan> privateCoursePlanList = new ArrayList();
        privateCoursePlanList = courseService.listPrivateCoursePlan(storeId,classDate,employeeName,Integer.valueOf(page));
        map.put("privateCoursePlanList",privateCoursePlanList);
        return map;
    }

    /**
     * 获取私教排课信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPrivateCoursePlanInfo", method = RequestMethod.POST)
    public Map<String,Object> getPrivateCoursePlanInfo(@RequestParam("id") String id){
        Map<String,Object> map = new HashMap<>();
        PrivateCoursePlan privateCoursePlan = courseDao.getPrivateCoursePlanInfoById(id);
        String[] timeStr = privateCoursePlan.getClassDatetime().split(",");
        List<String> timeList = Arrays.asList(timeStr);
        privateCoursePlan.setTimeList(timeList);
        map.put("privateCoursePlan",privateCoursePlan);
        return map;
    }

    /**
     * 新增私教排期信息
     * @param storeId
     * @param classroomId
     * @param employeeId
     * @param classDate
     * @param classDatetime
     * @param remark
     * @param creator
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addPrivateCoursePlan", method = RequestMethod.POST)
    public String addPrivateCoursePlan(@RequestParam("storeId") String storeId,
                                       @RequestParam("classroomId") String classroomId,
                                       @RequestParam("employeeId") String employeeId,
                                       @RequestParam("classDate") String classDate,
                                       @RequestParam("classDatetime") String classDatetime,
                                       @RequestParam(value = "remark", required = false) String remark,
                                       @RequestParam("creator") String creator){
        PrivateCoursePlan privateCoursePlan = new PrivateCoursePlan();
        privateCoursePlan.setStoreId(storeId);
        privateCoursePlan.setClassroomId(classroomId);
        privateCoursePlan.setEmployeeId(employeeId);
        privateCoursePlan.setClassDate(classDate);
        privateCoursePlan.setClassDatetime(classDatetime);
        privateCoursePlan.setRemark(remark);
        privateCoursePlan.setCreator(creator);
        courseService.addPrivateCoursePlan(privateCoursePlan);
        return "";
    }

    /**
     * 更新私教排期信息
     * @param id
     * @param classroomId
     * @param employeeId
     * @param classDate
     * @param classDatetime
     * @param remark
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/editPrivateCoursePlan", method = RequestMethod.POST)
    public String editPrivateCoursePlan(@RequestParam("id") String id,
                                        @RequestParam("classroomId") String classroomId,
                                        @RequestParam("employeeId") String employeeId,
                                        @RequestParam("classDate") String classDate,
                                        @RequestParam("classDatetime") String classDatetime,
                                        @RequestParam(value = "remark", required = false) String remark){
        PrivateCoursePlan privateCoursePlan = new PrivateCoursePlan();
        privateCoursePlan.setId(id);
        privateCoursePlan.setClassroomId(classroomId);
        privateCoursePlan.setEmployeeId(employeeId);
        privateCoursePlan.setClassDate(classDate);
        privateCoursePlan.setClassDatetime(classDatetime);
        privateCoursePlan.setRemark(remark);
        courseDao.updatePrivateCoursePlan(privateCoursePlan);
        return "";
    }

    /**
     * 删除私教排课信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delPrivateCoursePlan", method = RequestMethod.POST)
    public String delPrivateCoursePlan(@RequestParam("id") String id){
        courseService.delPrivateCoursePlan(id);
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
        courseService.addClassroom(classroom);
        return "";
    }

    /**
     * 获取教室详情
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getClassroomInfoById", method = RequestMethod.POST)
    public Map<String,Object> getClassroomInfoById( @RequestParam("id")  String id){
        Map<String,Object> map = new HashMap<>();
        Classroom classroom = courseService.getClassroomInfoById(id);
        map.put("classroom", classroom);
        return map;
    }

    /**
     *查询门店中所有教室
     * @param storeId
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listClassroomByStoreId", method = RequestMethod.POST)
    public  Map<String,Object> listClassroomByStoreId( @RequestParam("storeId") String storeId,
                                                       @RequestParam("page") String page ){
        Map<String,Object> map = new HashMap<>();
        List<Classroom> classroomList = courseService.listClassroomByStoreId(storeId, Integer.valueOf(page));
        map.put("classroomList",classroomList);
        return map;
    }

    /**
     * 编辑教室信息
     * @param classroomName
     * @param classroomId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateClassroom", method = RequestMethod.POST)
    public String updateClassroom( @RequestParam(value ="classroomName",required = false) String classroomName,
                                   @RequestParam("classroomId")String classroomId){
        courseService.updateClassroom(classroomName, classroomId);
        return "";
    }

    /**
     * 发送上课通知
     * @param memberId
     * @param courseplanId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/sendCourseStartAttention", method = RequestMethod.POST)
    public String sendCourseStartAttention(@RequestParam(value = "memberId") String memberId,
                                           @RequestParam(value = "courseplanId") String courseplanId,
                                           @RequestParam(value = "type") String type){
       //String messageContent = messageService.getCourseOrderAttentionMessageContent(courseplanId);
      /*  String  id = memberId.replaceAll("-", "");
        System.out.print("别名:"+memberId.replaceAll("-",""));
        String messageContent = "您预约的%s瑜伽馆的%s老师%s的课程:%s,马上就要开课了";
        HashMap<String,String> extrasMap = new HashMap<>();
        extrasMap.put("url","message");
        extrasMap.put("memberId",memberId);
        extrasMap.put("courseplanId",courseplanId);
        messageService.sendMessageToAlias("多点运动平台", "上课通知", messageContent, extrasMap,"72ceea163c684f8db4ea696563f22026");*/

        HashMap<String,String> extrasMap = new HashMap<>();
        extrasMap.put("title", SysConfigConstants.MEMBERCARD_CALIBRATIONSUCCEED_ALERT);
        extrasMap.put("memberId",memberId);
        extrasMap.put("courseplanId",courseplanId);
        extrasMap.put("content","尊敬的用户您好,你的会员卡已经校准成功");
        JSONArray json = JSONArray.fromObject(extrasMap);

        Jgpushtool.setAlert("尊敬的用户您好,你的会员卡已经校准成功");
        Jgpushtool.setTitle("多点运动助手");
        Jgpushtool.setExtrasMap(extrasMap);
        Jgpushtool.setMsgContent(json.toString());
        System.out.print("别名:"+memberId.replace("-",""));
        Jgpushtool.setAliases(memberId.replace("-",""));
       // Jgpushtool.setType(Integer.valueOf(type));
        Jgpushtool.setPushPayload(Jgpushtool.buildPushObject_Android_alias_alert());
        Jgpushtool.push();

        //Jgpushtool.buildPushObject_android_alertWithTitle()
        //Jgpushtool.buildPushObject_Android_alias_alert()
        /*Jgpushtool.setPushPayload(Jgpushtool.buildPushObject_Ios_alias_alertAndMessage());
        Jgpushtool.push();*/
        return "";
    }

    public static void main(String[] args) {
       /* System.out.println(String.valueOf(((int)(Math.random() * 9 + 1) * 100000)));*/

        System.out.print("密码加密"+MD5Utils.getMD5("900000" + "18666824647"));
    }

}
