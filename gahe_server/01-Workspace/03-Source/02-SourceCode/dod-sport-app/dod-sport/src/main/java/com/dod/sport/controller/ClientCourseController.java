package com.dod.sport.controller;

import com.dod.sport.constant.SysConfigConstants;
import com.dod.sport.dao.ICourseDao;
import com.dod.sport.dao.IEmployeeDao;
import com.dod.sport.domain.common.BusiException;
import com.dod.sport.domain.po.BackCourseOrderInfo;
import com.dod.sport.domain.po.Base.CoachInfo;
import com.dod.sport.domain.po.Course.*;
import com.dod.sport.domain.po.Member.MembercardRelation;
import com.dod.sport.domain.po.PrivateOrderDateTime;
import com.dod.sport.domain.po.QueryClass;
import com.dod.sport.domain.po.ResponseCourseOrder;
import com.dod.sport.service.ICourseService;
import com.dod.sport.util.CommonEnum;
import com.dod.sport.util.DateUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.*;

/**
 * Created by defi on 2017-10-14.
 * 客户端课程功能controller
 */

@Controller
@RequestMapping(value = "api/clientCourse/v1")
public class ClientCourseController extends BaseController  {

    @Autowired
    private ICourseService courseService;

    @Autowired
    private ICourseDao courseDao;

    @Autowired
    private IEmployeeDao employeeDao;

    /**
     * 客户端团课约课课程列表
     * @param storeId
     * @param memberId
     * @param isExperience
     * @param classDate
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listClientCourseOrder", method = RequestMethod.POST)
    public Map<String,Object> listClientCourseOrder(@RequestParam("storeId") String storeId,
                                                    @RequestParam("memberId") String memberId,
                                                    @RequestParam(value = "isExperience",required = false) String isExperience,
                                                    @RequestParam("classDate") String classDate,
                                                    @RequestParam("page") String page){
        Map<String,Object> map = new HashMap<>();
        Date date = DateUtil.parse(classDate,"yyyy-MM-dd HH:mm:ss");
        classDate = DateUtil.formatDate(SysConfigConstants.DATE_FORMAT_FORDATETIME,date);
        List<ClientCourseOrder> clientCourseOrderList = courseService.listClientCourseOrder(storeId,"",memberId,
                isExperience,classDate,"1",Integer.valueOf(page));
        map.put("clientCourseOrderList", clientCourseOrderList);
        return map;
    }

    /**
     * 客户端根据老师获取约课列表
     * @param storeId
     * @param memberId
     * @param employeeId
     * @param isExperience
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listCourseOrderByTeacher", method = RequestMethod.POST)
    public Map<String,Object> listCourseOrderByTeacher(@RequestParam("storeId") String storeId,
                                                       @RequestParam("memberId") String memberId,
                                                       @RequestParam("employeeId") String employeeId,
                                                       @RequestParam(value = "isExperience", required = false) String isExperience,
                                                       @RequestParam("page") String page){
        Map<String,Object> map = new HashMap<>();
        List<ClientCourseOrder> clientCourseOrderList = courseService.listClientCourseOrder(storeId,employeeId,memberId,
                isExperience,"","2",Integer.valueOf(page));
        map.put("clientCourseOrderList",clientCourseOrderList);
        return map;
    }

    /**
     * 获取可约课团课老师列表
     * @param storeId
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listCourseOrderTeacher", method = RequestMethod.POST)
    public Map<String,Object> listCourseOrderTeacher(@RequestParam("storeId") String storeId,
                                                     @RequestParam("page") String page){
        Map<String,Object> map = new HashMap<>();
        List<CoachInfo> coachInfoList = courseService.listClientCourseOrderTeacher(storeId, Integer.valueOf(page));
        map.put("coachInfoList",coachInfoList);
        return map;
    }

    /**
     * 获取可预约私教的教练列表
     * @param storeId
     * @param page
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listPrevateCoursePlanTeacher", method = RequestMethod.POST)
    public Map<String,Object>listPrevateCoursePlanTeacher(@RequestParam("storeId") String storeId,
                                                          @RequestParam("page") Integer page,
                                                          @RequestParam("pageSize")Integer pageSize){
        Map<String,Object> map = new HashMap<>();
        List<CoachInfo>coachInfoList = courseService.listPrevateCoursePlanTeacher(storeId, page, pageSize);
        map.put("coachInfoList",coachInfoList);
        return map;
    }

    /**
     * 获取老师私教课程列表
     * @param storeId
     * @param id 员工id
     * @param page
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listPrevateCourse", method = RequestMethod.POST)
    public Map<String,Object>listPrevateCourse(@RequestParam("storeId") String storeId,
                                               @RequestParam("id") String id,
                                               @RequestParam("page") Integer page,
                                               @RequestParam("pageSize") Integer pageSize){
        Map<String,Object> map = new HashMap<>();
        List<Course>courseList  = courseService.listPrevateCourse(storeId, id, page, pageSize);
        map.put("courseList",courseList);
        return map;
    }

    /**
     * 获取老师私教的可预约时间
     * @param storeId
     * @param id
     * @param page
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listDateTimeByEmpId", method = RequestMethod.POST)
    public Map<String,Object> listDateTimeByEmpId(@RequestParam("storeId") String storeId,
                                                  @RequestParam("id") String id,
                                                  @RequestParam("page") Integer page,
                                                  @RequestParam("pageSize") Integer pageSize){
        Map<String,Object> map = new HashMap<>();
        List<PrivateOrderDateTime> privateCoursePlans = courseService.listDateTimeByEmpId(storeId, id);
        map.put("privateCoursePlans",privateCoursePlans);
        return map;
    }

    /**
     * 获取已预约该教练的私教排课时间
     * @param id
     * @param storeId
     * @param page
     * @param pageSize
     * @return
     */
   /* @ResponseBody
    @RequestMapping(value = "/listExistsPrivateCoursePlanDateTimeByEmpId", method = RequestMethod.POST)
    public Map<String,Object> listExistsPrivateCoursePlanDateTimeByEmpId(@RequestParam("id") String id,
                                                                         @RequestParam("storeId") String storeId,
                                                                         @RequestParam("page") Integer page,
                                                                         @RequestParam("pageSize") Integer pageSize){

        Map<String,Object> map = new HashMap<>();
        List<PrivateOrderDateTime>privateOrderDateTimes = courseService.listexistsDateTimeByEmpId(id, storeId);
        map.put("privateOrderDateTimes",privateOrderDateTimes);
        return map;
    }*/

    /**
     * 获取私教预约时间
     * @param storeId
     * @param employeeId
     * @param page
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listPrivateOrderDateTime", method = RequestMethod.POST)
    public Map<String,Object> listPrivateOrderDateTime( @RequestParam("storeId")String storeId,
                                                        @RequestParam("employeeId") String employeeId,
                                                        @RequestParam("page") Integer page,
                                                        @RequestParam("pageSize") Integer pageSize){
        Map<String,Object> map = new HashMap<>();
        List<PrivateOrderDateTime>PrivateOrderDateTimes = courseService.listPrivateOrderDateTime(storeId, employeeId);
        map.put("PrivateOrderDateTimes",PrivateOrderDateTimes);
        return map;
    }
    /**
     * 获取可以预约该私教课程的会员卡
     * @param courseId
     * @param page
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listPrevateCourseMembercard", method = RequestMethod.POST)
    public Map<String,Object> listPrevateCourseMembercard(@RequestParam("courseId") String courseId,
                                                          @RequestParam("memberId") String memberId,
                                                          @RequestParam("page") Integer page,
                                                          @RequestParam("pageSize") Integer pageSize){
        Map<String,Object> map = new HashMap<>();
        List<MembercardRelation> membercardrelations = courseService.listPrevateCourseMembercard(courseId, memberId, page, pageSize);
        if (membercardrelations ==null ||membercardrelations.size()<1){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_nodata.getValue());
        }
        map.put("membercardrelations",membercardrelations);
        return map;
    }

    /**
     * 获取预约课程详细信息
     * @param courseId
     * @param memberId
     * @param businessId
     * @param id  签到表id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getCourseOrderInfo", method = RequestMethod.POST)
    public Map<String,Object> getCourseOrderInfo(@RequestParam(value = "courseId") String courseId,
                                                 @RequestParam(value = "memberId") String memberId,
                                                 @RequestParam(value = "businessId") String businessId,
                                                 @RequestParam(value = "id") String id){
        Map<String,Object> map = new HashMap<>();
        CourseOrderInfo courseOrderInfo = courseService.getCourseOrderInfo(courseId, memberId, businessId, id);
        map.put("courseOrderInfo",courseOrderInfo);
        return map;
    }

    /**
     * 团课约课
     * @param id  coursesignid
     * @param memberId
     * @param cardrelationId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/OrderPublicCourse", method = RequestMethod.POST)
    public String OrderPublicCourse(@RequestParam("id") String id,
                                    @RequestParam("coursePlanId") String coursePlanId,
                                    @RequestParam("memberId") String memberId,
                                    @RequestParam("cardrelationId") String cardrelationId){
        try {
            courseService.addCourseOrder(memberId, coursePlanId, id, cardrelationId);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 私教约课
     * @param queryClass json 格式字符串
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/OrderPrevateCourse", method = RequestMethod.POST)
    public String OrderPrevateCourse(@RequestBody String queryClass){
        try {
            String queryClassStr =  java.net.URLDecoder.decode(queryClass, "utf-8");
            System.out.print(queryClassStr);
            String[]strArr = queryClassStr.split("&");
            System.out.print((strArr[0]).split("=")[1]);
            Map classMap = new HashMap();
            classMap.put("privateOrderDateTimes",PrivateOrderDateTime.class);
            String newJson = StringEscapeUtils.unescapeHtml((strArr[0]).split("=")[1]);
            JSONObject jsonobject = JSONObject.fromObject(newJson);
            QueryClass queryClass1 = (QueryClass)JSONObject.toBean(jsonobject,QueryClass.class,classMap);
            try {
                courseService.OrderPrevateCourse(queryClass1.getEmployeeId(),queryClass1.getMemberId(), queryClass1.getCourseId(), queryClass1.getCardrelationId(), queryClass1.getRemark(), queryClass1.getPrivateOrderDateTimes());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }catch (UnsupportedEncodingException u){
            u.printStackTrace();
        }
       return "";
    }

    /**
     * 获取会员已预约私教信息
     * @param memberId
     * @param storeId
     * @param page
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listPrivateOrderRecordById", method = RequestMethod.POST)
    public Map<String,Object> listPrivateOrderRecordById(@RequestParam(value = "memberId") String memberId,
                                                         @RequestParam(value = "storeId") String storeId,
                                                         @RequestParam(value = "page") Integer page,
                                                         @RequestParam(value = "pageSize") Integer pageSize){
        Map<String,Object>map = new HashMap<>();
        List<PrivateOrderRecord> privateOrderRecords = courseService.listPrivateOrderRecordById(memberId, storeId, page,pageSize);
        if (privateOrderRecords==null||privateOrderRecords.size()<1){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_nodata.getValue());
        }
        map.put("privateOrderRecords",privateOrderRecords);
        return map;
    }

    /**
     * 获取体验课详细信息
     * @param businessId
     * @param memberId
     * @param coursesignId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getExperienceCourseOrderInfo", method = RequestMethod.POST)
    public Map<String,Object> getExperienceCourseOrderInfo(@RequestParam("businessId") String businessId,
                                                           @RequestParam("memberId") String memberId,
                                                           @RequestParam("coursesignId") String coursesignId) {
        CourseOrderInfo courseOrderInfo = courseService.getExperienceCourseOrderInfo(memberId, businessId, coursesignId);
        Map<String,Object> map = new HashMap<>();
        map.put("courseOrderInfo",courseOrderInfo);
        return map;
    }

    /**
     * 预约体验课
     * @param id
     * @param courseplanId
     * @param memberId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/orderExperienceCourse", method = RequestMethod.POST)
    public String orderExperienceCourse(@RequestParam("id") String id,
                                        @RequestParam("courseplanId") String courseplanId,
                                        @RequestParam("courseplanId") String remark,
                                        @RequestParam("memberId") String memberId) throws ParseException {
        courseService.addExperienceCourseOrder(memberId, courseplanId, id,remark);
        return "";
    }

    /**
     * 取消预约
     * @param id
     */
    @ResponseBody
    @RequestMapping(value = "/cancelCourseOrder", method = RequestMethod.POST)
    public String cancelCourseOrder(@RequestParam("id")String id) throws ParseException {
        courseService.cancelCourseOrder(id);
        return "";
    }

    /**
     * 获取平台用户所有预约记录
     * @param phoneNum
     * @param businessId
     * @param page
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getMemberAllOrderByPhoneNum", method = RequestMethod.POST)
    public Map<String,Object> getMemberAllOrderByPhoneNum(@RequestParam("phoneNum")String phoneNum,
                                                          @RequestParam(value = "businessId",required = false) String businessId ,
                                                          @RequestParam("page") Integer page ,
                                                          @RequestParam("pageSize")Integer pageSize){
        ResponseCourseOrder responseCourseOrder = courseService.getMemberAllOrderByPhoneNum(phoneNum,businessId,page,pageSize);
        Map<String,Object> map = new HashMap<>();
        map.put("responseCourseOrder",responseCourseOrder);
        return map;
    }

    /**
     * 根据预约状态来查询记录
     * @param phoneNum 会员电话
     * @param businessId 商家id
     * @param page 当前页
     * @param pageSize 每页显示大小
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getMemberAllOrderByOrderStatus", method = RequestMethod.POST)
    public Map<String,Object> getMemberAllOrderByOrderStatus(@RequestParam("phoneNum")String phoneNum,
                                                             @RequestParam("businessId") String businessId ,
                                                             @RequestParam("orderStatus")String orderStatus,
                                                             @RequestParam("page") Integer page ,
                                                             @RequestParam("pageSize")Integer pageSize){
        List<BackCourseOrderInfo> backCourseOrderInfos = courseService.getMemberAllOrderByOrderStatus(phoneNum, businessId,orderStatus, page, pageSize);
        Map<String,Object> map = new HashMap<>();
        map.put("backCourseOrderInfos",backCourseOrderInfos);
        return map;
    }

}
