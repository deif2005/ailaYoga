package com.dod.sport.dao;

import com.dod.sport.domain.po.*;
import com.dod.sport.domain.po.Base.Classroom;
import com.dod.sport.domain.po.Base.CoachInfo;
import com.dod.sport.domain.po.Course.*;
import com.dod.sport.domain.po.Member.MembercardRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Created by defi on 2017-08-21.
 */
@Repository
public interface ICourseDao {

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
    public List<Course> listCourseByName(@Param("businessId")String businessId,@Param("courseName")String courseName);

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
     * 更新课程信息
     * @param course
     */
    public void updateCourseInfo(Course course);

    /**
     * 更新课程信息，不包含课程图片
     * @param course
     */
    public void updateCourseInfoNoIcon(Course course);

    /**
     * 获取课程类型信息列表
     * @return
     */
    public List<CourseType> listCourseType(String businessId);

    /**
     * 新增课程卡关系数据
     * @return
     */
    public void addCourseAndCard(List<CourseAndCard> courseAndCardList);

    /**
     *
     * @param teacherAndCourseList
     */
    public void addTeacherAndCard(List<TeacherAndCourse> teacherAndCourseList);

    /**
     * 更新课程卡关系数据
     * @param courseAndCard
     */
    public void updateCourseAndCard(CourseAndCard courseAndCard);

    /**
     * 获取课程卡关联信息
     * @param businessId
     * @return
     */
    public List<CourseAndCard> listCourseAndCard(@Param("businessId") String businessId,
                                                 @Param("courseId") String courseId);

    /**
     * 获取课程卡关联信息
     * @param businessId
     * @return
     */
    public List<TeacherAndCourse> listTeacherAndCourse(@Param("businessId") String businessId,
                                                       @Param("employeeId") String employeeId);

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
     * 获取课程类型编号
     * @return
     */
    public String getMaxCourseTypeId(String businessId);

    /**
     * 获取课程编号
     * @return
     */
    public String getMaxCourseId(String businessId);

    /**
     * 获取排课课程信息
     * @param id
     * @return
     */
    public CoursePlan getCoursePlanInfo(String id);

    /**
     * 根据签到id获取排课信息
     * @param coursePlanSignId
     * @return
     */
    public CoursePlan getCoursePlanInfoBySignId(String coursePlanSignId);

    /**
     * 根据教师获取排课信息
     * @param storeId
     * @param employeeId
     * @param classDate
     * @return
     */
    public List<CoursePlan> listCoursePlanByEmployeeId(@Param("storeId") String storeId,
                                                       @Param("employeeId") String employeeId,
                                                       @Param("classDate") String classDate);

    /**
     * 新增排课课程信息
     * @param coursePlanList
     */
    public void addCoursePlan(List<CoursePlan> coursePlanList);

    /**
     * 更新排课课程信息
     * @param coursePlan
     */
    public void updateCoursePlan(CoursePlan coursePlan);

    /**
     * 判断教室是否空闲
     * @param storeId
     * @param classroomId
     * @param beginDatetime
     * @param endDatetime
     */
    public Integer getClassroomFreeStatus(@Param("storeId") String storeId,
                                          @Param("classroomId") String classroomId,
                                          @Param("beginDatetime") String beginDatetime,
                                          @Param("endDatetime") String endDatetime);

    /**
     * 判断老师是否空闲
     * @param storeId
     * @param employeeId
     * @param beginDatetime
     * @param endDatetime
     */
    public Integer getEmployeeFreeStatus(@Param("storeId") String storeId,
                                         @Param("employeeId") String employeeId,
                                         @Param("beginDatetime") String beginDatetime,
                                         @Param("endDatetime") String endDatetime);

    /**
     * 获取课程排课列表数据
     * @param storeId
     * @param employeeId
     * @param beginDate
     * @param endDate
     * @return
     */
    public List<CoursePlan> listCoursePlanByWeek(@Param("storeId") String storeId,
                                                 @Param("employeeId") String employeeId,
                                                 @Param("beginDate") String beginDate,
                                                 @Param("endDate") String endDate);

    /**
     * 删除排课内容
     * @param id
     */
    public void deleteCoursePlan(String id);

    /**
     * 获取课程预约会员列表
     * @param courseSignId
     * @param startPage
     * @param endPage
     * @return
     */
    public List<CourseOrder> listCourseOrder(@Param("courseSignId") String courseSignId,
                                             @Param("startPage") Integer startPage,
                                             @Param("endPage") Integer endPage);

    /**
     * 增加约课信息
     * @param courseOrder
     */
    public void addCourseOrder(CourseOrder courseOrder);

    /**
     * 获取剩余预约数
     * @param courseplanId
     * @return
     */
    public String getRemainsOrder(String courseplanId);

    /**
     * 获取私课预约人数
     * @param courseplanId
     * @param classTime
     * @return
     */
    public String getPrivateRemainsOrder(String courseplanId, String classTime);

    /**
     * 开课前预约时间限制状态获取
     * @param prevMin（分钟）
     * @param courseplanId
     * @return
     */
    public String getCanOrderStatus(@Param("prevMin") Integer prevMin,
                                    @Param("courseplanId") String courseplanId);

    /**
     * 更新会员预约状态
     * @param orderStatus
     * @param id
     */
    public void updateCourseOrderStatusById(@Param("id") String id,
                                            @Param("orderStatus") Integer orderStatus);

    /**
     * 获取课程-签到关系数据列表(团课)
     * @param storeId
     * @param employeeId 老师id 非店长权限时传入
     * @param dataType 1，当前数据 2，历史数据
     * @param startPage
     * @param endPage
     * @param classDate 排课时间(日期)
     * @return
     */
    public List<CoursePlanSign> listCoursePlanSignInfo(@Param("storeId") String storeId,
                                                       @Param("employeeId") String employeeId,
                                                       @Param("dataType") Integer dataType,
                                                       @Param("startPage") Integer startPage,
                                                       @Param("endPage") Integer endPage,
                                                       @Param("classDate") String classDate);

    /**
     * 获取私教课程-签到关系数据列表(私课)
     * @param storeId
     * @param employeeId 老师id 非店长权限时传入
     * @param dataType 1，当前数据 2，历史数据
     * @param startPage
     * @param endPage
     * @param classDate  排课时间(日期)
     * @return
     */
    public List<CoursePlanSign> listPrivateCoursePlanSignInfo(@Param("storeId") String storeId,
                                                              @Param("employeeId") String employeeId,
                                                              @Param("dataType") Integer dataType,
                                                              @Param("startPage") Integer startPage,
                                                              @Param("endPage") Integer endPage,
                                                              @Param("classDate") String classDate);

    /**
     * 更新课程状态
     * @param id
     * @param classStatus
     */
    public void updateCourseStatus(@Param("id") String id,
                                   @Param("classStatus") Integer classStatus);

    /**
     * 签到状态及时间更新
     * @param signStatus
     * @param signTime1
     * @param signTime2
     * @param id
     */
    public void updateCourseSignStatus(@Param("signStatus") String signStatus,
                                       @Param("signTime1") String signTime1,
                                       @Param("signTime2") String signTime2,
                                       @Param("id") String id);

    /**
     * 根据开课时间获取课程签到列表(团课)
     * @param classDate
     * @param courseMeans
     * @return
     */
    public List<CourseSign> listCourseSignInfo(@Param("classDate") String classDate,
                                               @Param("courseMeans") String courseMeans);

    /**
     * 根据id获取课程签到信息
     * @param id
     * @return
     */
    public CourseSign getCourseSignInfoById(@Param("id") String id);

    /**
     * 获取私课排期列表
     * @param storeId
     * @param classDate
     * @param employeeName
     * @param startPage
     * @param endPage
     * @return
     */
    public List<PrivateCoursePlan> listPrivateCoursePlan(@Param("storeId") String storeId,
                                                         @Param("classDate") String classDate,
                                                         @Param("employeeName") String employeeName,
                                                         @Param("startPage") Integer startPage,
                                                         @Param("endPage") Integer endPage);

    /**
     * 获取私教排课信息
     * @param id
     * @return
     */
    public PrivateCoursePlan getPrivateCoursePlanInfoById(String id);

    /**
     * 根据排课日期获取排课信息
     * @param classDate
     * @param employeeId
     * @return
     */
    public Integer getPrivateCoursePlanCountByClassTime(@Param("classDate") String classDate,
                                                       @Param("employeeId") String employeeId);

    /**
     * 新增私教排课信息
     * @param privateCoursePlan
     */
    public void addPrivateCoursePlan(PrivateCoursePlan privateCoursePlan);

    /**
     * 更新私教排课信息
     * @param privateCoursePlan
     */
    public void updatePrivateCoursePlan(PrivateCoursePlan privateCoursePlan);

    /**
     * 删除私教排期信息
     * @param id
     */
    public void deletePrivateCoursePlan(String id);

    /**
     * 获取约课人数
     * @param coursesignId
     * @return
     */
    public String getCourseOrderCount(String coursesignId);

    /**
     * 删除课程卡关联数据
     * @param courseId
     */
    public void deleteCourseAndCard(String courseId);

    /**
     *
     * @param employeeId
     */
    public void deleteTeacherAndCourse(String employeeId);

    /**
     * 添加课程签到记录
     * @param courseSignList
     */
    public void addCourseSign(List<CourseSign> courseSignList);

    /**
     * 更新会员约课状态
     * @param courseOrder
     */
    public void updateOrderStatusByCourser(CourseOrder courseOrder);

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
     * @param startPage
     * @param endPage
     * @return
     */
    public List<Classroom> listClassroomByStoreId(@Param("storeId") String storeId,
                                                  @Param("startPage") Integer startPage,
                                                  @Param("endPage") Integer endPage);

    /**
     * 编辑教室信息
     * @param classroomName
     * @param classroomId
     */
    public void updateClassroom(@Param("classroomName") String classroomName,
                                @Param("classroomId") String classroomId);


    /**
     * 获取客户端团课约课列表
     * @param classDate
     * @param storeId
     */
    public List<ClientCourseOrder> listClientCourseOrder(@Param("storeId") String storeId,
                                                         @Param("classDate") String classDate,
                                                         @Param("endDate") String endDate,
                                                         @Param("memberId") String memberId,
                                                         @Param("isExperience") String isExperience,
                                                         @Param("startPage") Integer startPage,
                                                         @Param("endPage") Integer endPage);

    /**
     * 获取某老师的客户端团课约课列表
     * @param storeId
     * @param beginDate
     * @param endDate
     * @param employeeId
     * @return
     */
    public List<ClientCourseOrder> listCourseOrderByTeacher(@Param("storeId") String storeId,
                                                            @Param("beginDate") String beginDate,
                                                            @Param("endDate") String endDate,
                                                            @Param("memberId") String memberId,
                                                            @Param("employeeId") String employeeId,
                                                            @Param("isExperience") String isExperience,
                                                            @Param("startPage") Integer startPage,
                                                            @Param("endPage") Integer endPage);

    /**
     * 获取团课可约课老师
     * @param storeId
     * @param classDate
     * @return
     */
    public List<CoachInfo> listCourseOrderTeacher(@Param("storeId") String storeId,
                                                     @Param("classDate") String classDate,
                                                     @Param("startPage") Integer startPage,
                                                     @Param("endPage") Integer endPage);

    /**
     *获取私教可预约老师
     * @param storeId
     * @param classDate
     * @param startPage
     * @param endPage
     * @return
     */
    public List<CoachInfo> listPrevateCoursePlanTeacher(@Param("storeId") String storeId,
                                                        @Param("classDate") String classDate,
                                                        @Param("startPage") Integer startPage,
                                                        @Param("endPage") Integer endPage);

    /**
     * 获取约课课程详细信息
     * @param id 签到表id
     * @param memberId  会员id
     * @return
     */
    public CourseOrderInfo getCourseOrderInfo(@Param("id") String id,
                                              @Param("memberId") String memberId);
    /**
     * 获取老师私教课程列表
     * @param storeId
     * @param beginDate
     * @param endDate
     * @param employeeId
     * @param startPage
     * @param endPage
     * @return
     */
    public List<Course> listPrevateCourse(@Param("storeId") String storeId,
                                          @Param("beginDate") String beginDate,
                                          @Param("endDate") String endDate,
                                          @Param("employeeId") String employeeId,
                                          @Param("startPage") Integer startPage,
                                          @Param("endPage") Integer endPage);

    /**
     * 获取老师私教的可预约时间
     * @param storeId
     * @param beginDate
     * @param endDate
     * @param employeeId
     * @return
     */
    public List<PrivateOrderDateTime> listDateTimeByEmpId(@Param("employeeId") String employeeId,
                                                          @Param("storeId") String storeId,
                                                          @Param("beginDate") String beginDate,
                                                          @Param("endDate")String endDate );

    /**
     * 获取该教练的已预约时间
     * @param employeeId
     * @param storeId
     * @param beginDate
     * @param endDate
     * @return
     */
    public List<PrivateOrderDateTime>listexistsDateTimeByEmpId( @Param("employeeId") String employeeId,
                                                                @Param("storeId") String storeId,
                                                                @Param("beginDate") String beginDate,
                                                                @Param("endDate")String endDate);

    /**
     * 根据计划id查询预约记录
     * @param courseplanId
     * @param classTime
     * @param classDate
     * @return
     */
    public CourseOrder getPrivateOrderByPlan(@Param("courseplanId") String courseplanId,
                                             @Param("classTime") String classTime,
                                             @Param("classDate") String classDate);
    /**
     * 获取在某日某时间段内预约课程信息
     * @param employeeId
     * @param planId
     * @param classDate
     * @param classTime
     * @return
     */
    public PrivateOrderDateTime getlistExistsDateTime( @Param("employeeId") String employeeId,
                                                       @Param("planId") String planId,
                                                       @Param("classDate")String classDate,
                                                       @Param("classTime")String classTime);
    /**
     * 获取可以预约该私教课程的会员卡
     * @param courseId
     * @param startPage
     * @param endPage
     * @return
     */
    public List<MembercardRelation> listPrevateCourseMembercard(@Param("courseId") String courseId,
                                                                @Param("memberId") String memberId,
                                                                @Param("startPage") Integer startPage,
                                                                @Param("endPage") Integer endPage);

    /**
     * 获取该时间段私教是否取消
     * @param courseplanId
     * @return
     */
    public PrivateOrderDateTime getPrivateCourseOrderEnable( String courseplanId);

    /**
     * 获取可约课会员卡列表
     * @param courseId
     * @param memberId
     * @param businessId
     * @return
     */
    public List<MembercardRelation> listCourseOrderCard(@Param("courseId") String courseId,
                                                        @Param("memberId") String memberId,
                                                        @Param("businessId") String businessId);

    /**
     * 获取预约信息判断是否可以预约
     * @param id
     * @return
     */
    public CourseOrderEnable getCourseOrderEnable(String id);

    /**
     * 获取全部约课人员名单
     * @param courseSignId
     * @param orderStatus
     * @return
     */
    public List<String> listCourseOrderMember(@Param("courseSignId") String courseSignId,
                                              @Param("orderStatus") Integer orderStatus);

    /**
     * 获取会员的已预约列表
     * @param memberdId
     * @param storeId
     * @return
     */
    public List<PrivateOrderRecord> listPrivateOrderRecordById(@Param("memberdId")String memberdId,
                                                               @Param("storeId") String storeId ,
                                                               @Param("startPage") Integer startPage,
                                                               @Param("endPage") Integer endPage);

    /**
     * 获取是否参加过该体验课
     * @param businessId
     * @param memberId
     * @return
     */
    public String getIsOrderExperienceCourse(@Param("businessId") String businessId,
                                             @Param("memberId") String memberId);

    /**
     * 根据id查询预约信息
     * @param id
     * @return
     */
    public BaseCourseOrderInfo getCourseOrderInfoById(String id);

    /**
     * 取消私教预约
     * @param id
     * @param orderStatus
     */
    public void cancelCourseOrder(@Param("id")String id ,@Param("orderStatus")String orderStatus);

    /**
     * 查询平台用户所有的预约记录详情
     * @param phoneNum
     * @return
     */
    public List<BackCourseOrderInfo>listMemberAllOrderByPhoneNum(@Param("phoneNum")String phoneNum,
                                                                 @Param("orderStatus")String orderStatus,
                                                                 @Param("startPage") Integer startPage,
                                                                 @Param("endPage") Integer endPage);

    public String getEmpOrderSumByEmpId(@Param("phoneNum")String phoneNum ,
                                         @Param("businessId")String businessId,
                                         @Param("orderStatus")String orderStatus);

    /**
     * 获取会员预约团课详情
     * @param courseplanId 团课排课计划id
     * @param coursesignId 团课签到id
     * @param memberId 会员id
     * @return
     */
    public BaseCourseOrderInfo getPublicCourseOrderByPlanId( @Param("courseplanId")String courseplanId,
                                                             @Param("coursesignId")String coursesignId,
                                                             @Param("memberId")String memberId);

    /**
     * 获取会员预约私教详情
     * @return
     */
    public BaseCourseOrderInfo  getPrivateCourseOrderByPlanId( BaseCourseOrderInfo baseCourseOrderInfo);
    /**
     * 更新预约记录
     * @param courseOrder
     */
    public void updateCourseOrder(CourseOrder courseOrder);

    public CourseSign getCourseSignById(String id);

    /**
     * 更新课程签到表取消人数
     * @param id
     * @param cancelPersons
     */
    public void updateCourseSign(@Param("id")String id,@Param("cancelPersons")String cancelPersons);

}

