package com.dod.sport.service;

import com.dod.sport.domain.po.BackCourseOrderInfo;
import com.dod.sport.domain.po.Base.Classroom;
import com.dod.sport.domain.po.Base.CoachInfo;
import com.dod.sport.domain.po.Course.*;
import com.dod.sport.domain.po.Member.MembercardRelation;
import com.dod.sport.domain.po.PrivateOrderDateTime;
import com.dod.sport.domain.po.ResponseCourseOrder;

import java.text.ParseException;
import java.util.List;

/**
 * Created by defi on 2017-08-21.
 */
public interface ICourseService {

    /**
     * 获取课程信息列表
     * @return
     */
    public List<Course> listCourseInfo(String businessId);

    /**
     * 根据课程名称模糊查询课程
     * @param businessId
     * @param courseName
     * @return
     */
    public List<Course> listCourseByName(String businessId,String courseName);

    /**
     * 获取课程信息
     * @param id
     * @return
     */
    public Course getCourseInfo(String id);

    /**
     * 新增课程信息
     * @param course
     */
    public void addCourse(Course course);

    /**
     * 获取课程类型信息列表
     * @return
     */
    public List<CourseType> listCourseType(String businessId);

    /**
     * 获取课程类型信息
     * @param id
     * @return
     */
    public CourseType getCourseType(String id);

    /**
     * 新增课程类型信息
     * @param courseType
     */
    public void addCourseType(CourseType courseType);

    /**
     * 更新课程信息
     * @param course
     * @param updateIcon
     */
    public void updateCourseInfo(Course course, boolean updateIcon);

    /**
     * 新增课程-卡关系
     * @param courseId
     * @param cardIdStr
     */
    public void addCourseAndCard(String courseId, String cardIdStr);

    /**
     * 更新课程-卡关系
     * @param courseAndCardList
     */
//    public void updateCourseAndCard(List<CourseAndCard> courseAndCardList);

    /**
     * 新增排课课程设置信息
     * @param coursePlan
     */
    public void addCoursePlan(CoursePlan coursePlan);

    /**
     * 复制一周的排课内容
     * @param storeId
     * @param year
     * @param weekIndex
     */
    public void batchAddCoursePlanByWeek(String storeId, String year, String weekIndex);

    /**
     * 修改排课课程设置信息
     * @param coursePlan
     */
    public void updateCoursePlan(CoursePlan coursePlan);

    /**
     * 获取团课排课信息（按周展示）
     * @param storeId
     * @param weekIndex
     * @return
     */
    public List<CoursePlan> listCoursePlanByWeek(String storeId, String employeeId, String year, String weekIndex);

    /**
     * 获取约课人员信息
     * @param CoursePlanId
     * @param page
     * @return
     */
    public List<CourseOrder> listCourseOrder(String CoursePlanId, Integer page);

    /**
     * 增加私教约课信息
     * @param courseOrder
     */
    public void addPrivateCourseOrder(CourseOrder courseOrder);

    /**
     * 获取课程签到关系数据列表(团课)
     * @param storeId
     * @param employeeId
     * @param dataType
     * @param page
     * @return
     */
    public List<CoursePlanSign> listCoursePlanSignInfo(String storeId, String employeeId, Integer dataType,
                                                       Integer page, String classDate);

    /**
     * 获取课程签到关系数据列表(私课)
     * @param storeId
     * @param employeeId
     * @param dataType
     * @param page
     * @param classDate
     * @return
     */
    public List<CoursePlanSign> listPrivateCoursePlanSignInfo(String storeId, String employeeId, Integer dataType,
                                                              Integer page, String classDate);

    /**
     * 获取私教排课列表
     * @param storeId
     * @param classDate
     * @param employeeName
     * @param page
     * @return
     */
    public List<PrivateCoursePlan> listPrivateCoursePlan(String storeId,
                                                         String classDate,
                                                         String employeeName,
                                                         Integer page);

    /**
     * 新增私教排课信息
     * @param privateCoursePlan
     */
    public void addPrivateCoursePlan(PrivateCoursePlan privateCoursePlan);

    /**
     * 删除私教排课信息
     * @param id
     */
    public void delPrivateCoursePlan(String id);

    /**
     * 店长签到
     * @param id
     */
    public void managerCourseSign(String id);

    /**
     * 新增老师-课程关系
     * @param courseIdStr
     */
    public void addTeacherAndCourse(String employeeId, String courseIdStr);

    /**
     * 取消课程
     * @param coursePlanId
     * @param courseSignId
     */
    public void courseCancelByCourse(String coursePlanId, String courseSignId);

    /**
     * 新增教室
     * @param classroom
     */
    public void addClassroom(Classroom classroom);

    /**
     * 获取教室详情
     * @param id
     * @return
     */
    public Classroom getClassroomInfoById(String id);

    /**
     *查询门店中所有教室
     * @param storeId
     * @param page
     * @return
     */
    public List<Classroom> listClassroomByStoreId( String storeId, Integer page );

    /**
     * 编辑教室信息
     * @param classroomName
     * @param classroomId
     */
    public void updateClassroom( String classroomName,  String classroomId);

    /**
     * 获取团课约课列表
     * @param storeId
     * @param employeeId
     * @param classDate
     * @param DataType 1.按时间获取 2.按老师获取
     * @return
     */
    public List<ClientCourseOrder> listClientCourseOrder(String storeId, String employeeId, String memberId,String isExperience,
                                                         String classDate, String DataType, Integer page);

    /**
     * 获取团课可约课老师列表
     * @param storeId
     * @param page
     * @return
     */
    public List<CoachInfo> listClientCourseOrderTeacher(String storeId, Integer page);

    /**
     *获取私教可预约老师
     * @param storeId
     * @param page
     * @param pageSiz
     * @return
     */
    public List<CoachInfo> listPrevateCoursePlanTeacher(String storeId,Integer page,Integer pageSiz);

    /**
     * 获取老师私教课程列表
     * @param storeId
     * @param employeeId
     * @param page
     * @param pageSiz
     * @return
     */
    public List<Course> listPrevateCourse(String storeId ,String employeeId, Integer page,  Integer pageSiz);

    /**
     * 获取老师私教的可预约时间
     * @param storeId
     * @param employeeId
     * @return
     */
    public List<PrivateOrderDateTime> listDateTimeByEmpId( String storeId,String employeeId);

    /**
     * 获取该教练的已预约时间
     * @param employeeId
     * @param storeId
     * @return
     */
    List<PrivateOrderDateTime>listexistsDateTimeByEmpId( String employeeId, String storeId);

    List<PrivateOrderDateTime>listPrivateOrderDateTime(String employeeId, String storeId );
    /**
     * 获取可以预约该私教课程的会员卡
     * @param courseId
     * @param page
     * @param pageSiz
     * @return
     */
    public List<MembercardRelation> listPrevateCourseMembercard(String courseId,String memberId, Integer page,  Integer pageSiz);

    /**
     * 获取团课预约课程详细信息
     * @param courseId
     * @param memberId
     * @param businessId
     * @param courseSignId
     * @return
     */
    public CourseOrderInfo getCourseOrderInfo(String courseId, String memberId, String businessId, String courseSignId );

    /**
     * 新增约课记录
     * @param memberId
     * @param coursesignId
     * @param cardrelationId
     */
    public void addCourseOrder(String memberId, String courseplanId, String coursesignId,String cardrelationId) throws ParseException;

    /**
     * 预约私教课程
     * @param employeeId
     * @param memberId
     * @param courseId
     * @param cardrelationId
     * @param remark
     * @param privateOrderDateTimes
     */
    public void OrderPrevateCourse(String employeeId,String memberId,String courseId,String cardrelationId, String remark, List<PrivateOrderDateTime> privateOrderDateTimes) throws ParseException;

    /**
     * 获取已预约私教列表
     * @param memberdId
     * @param storeId
     * @return
     */
    public List<PrivateOrderRecord> listPrivateOrderRecordById(String memberdId, String storeId, Integer page, Integer pageSiz);

    /**
     * 判断课程是否有效(团课)
     * @param coursesignId
     * @return
     */
    public CourseOrderEnable isCourseValid(String coursesignId);

    /**
     * 获取体验团课预约课程详细信息
     * @param memberId
     * @param businessId
     * @param courseSignId
     * @return
     */
    public CourseOrderInfo getExperienceCourseOrderInfo(String memberId, String businessId,String courseSignId);

    /**
     * 新增约课记录
     * @param memberId
     * @param coursesignId
     */
    public void addExperienceCourseOrder(String memberId, String courseplanId, String coursesignId, String remark) throws ParseException;

    /**
     * 根据id查询预约信息
     * @param id
     * @return
     */
    public BaseCourseOrderInfo getCourseOrderInfoById(String id);

    /**
     * 取消预约
     * @param id 课程预约id
     */
    public void cancelCourseOrder(String id) throws ParseException;

    /**
     * 查询平台用户所有的预约记录详情
     * @param phoneNum
     * @return
     */
    public ResponseCourseOrder getMemberAllOrderByPhoneNum(String phoneNum,String businessId,Integer page ,Integer pageSiz);

    /**
     * 根据预约状态查询记录
     * @param phoneNum
     * @param businessId
     * @param orderStatus
     * @param page
     * @param pageSize
     * @return
     */
    public List<BackCourseOrderInfo> getMemberAllOrderByOrderStatus(String phoneNum,String businessId,String orderStatus,Integer page,Integer pageSize);


    /**
     * 获取会员预约团课详情
     * @param courseplanId 团课排课计划id
     * @param coursesignId 团课签到id
     * @param memberId 会员id
     * @return
     */
    public BaseCourseOrderInfo getPublicCourseOrderByPlanId(String courseplanId,String coursesignId,String memberId);

    /**
     * 获取会员预约私教详情
     * @param courseplanId 团课排课计划id
     * @param classDate 开课日期
     * @param classTime 开课时间
     * @param memberId 会员id
     * @return
     */
    public BaseCourseOrderInfo getPrivateCourseOrderByPlanId(String courseplanId,String memberId,String classDate,String classTime);
}
