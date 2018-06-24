package com.dod.sport.service.impl;

import com.dod.sport.config.Configuration;
import com.dod.sport.constant.SysConfigConstants;
import com.dod.sport.dao.ICourseDao;
import com.dod.sport.dao.IMembercardRelationDao;
import com.dod.sport.domain.common.BatchDataPage;
import com.dod.sport.domain.common.BusiException;
import com.dod.sport.domain.po.*;
import com.dod.sport.domain.po.Base.Classroom;
import com.dod.sport.domain.po.Base.CoachInfo;
import com.dod.sport.domain.po.Course.*;
import com.dod.sport.domain.po.Member.MembercardRelation;
import com.dod.sport.service.ICourseService;
import com.dod.sport.service.IMembercardRelationService;
import com.dod.sport.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by defi on 2017-08-21.
 * 课程管理service
 */

@Service
public class CourseServiceImpl implements ICourseService{

    @Autowired
    private ICourseDao courseDao;

    @Autowired
    private IMembercardRelationDao membercardrelationDao;

    @Autowired
    private IMembercardRelationService membercardRelationService;

    /**
     * 获取课程信息列表
     * @return
     */
    @Override
    public List<Course> listCourseInfo(String businessId){
        List<Course> courseList = courseDao.listCourseInfo(businessId);
        for (Course course:courseList){
            if (course.getIconPath()!=null && !"".equals(course.getIconPath())){
                String iconPath = Configuration.getStaticShowPath()+course.getIconPath();
                course.setIconPath(iconPath);
            }
        }
        return courseList;
    }

    /**
     * 根据课程名称模糊查询课程
     * @param businessId
     * @param courseName
     * @return
     */
    @Override
    public List<Course> listCourseByName(String businessId, String courseName) {
        List<Course> courseList = courseDao.listCourseByName(businessId, courseName);
        for (Course course :courseList ){
              if (course.getIconPath() !=  null ){
                  course.setIconPath(Configuration.getStaticShowPath()+course.getIconPath());
              }
        }
        return courseList;
    }

    /**
     * 获取课程信息
     * @param id
     * @return
     */
    @Override
    public Course getCourseInfo(String id){
        Course course = courseDao.getCourseInfo(id);
        if (course.getIconPath()!=null && !"".equals(course.getIconPath())){
            String iconPath = Configuration.getStaticShowPath()+course.getIconPath();
            course.setIconPath(iconPath);
        }
        return course;
    }

    /**
     * 新增课程信息
     * @param course
     */
    @Override
    public void addCourse(Course course){
        String maxId = RedisCommon.getMaxCourseId(course.getBusinessId());
        if ("".equals(maxId)){
            maxId = RedisCommon.setAndReturnMaxCourseId(course.getBusinessId(), courseDao.getMaxCourseId(course.getBusinessId()));
        }
        course.setId(UUID.randomUUID().toString());
        course.setCourseSerialId(maxId);
        courseDao.addCourse(course);
    }

    /**
     * 获取课程类型信息列表
     * @return
     */
    @Override
    public List<CourseType> listCourseType(String businessId){
        List<CourseType> courseTypeList = courseDao.listCourseType(businessId);
        return courseTypeList;
    }

    /**
     * 获取课程类型信息
     * @param id
     * @return
     */
    @Override
    public CourseType getCourseType(String id){
        CourseType courseType = courseDao.getCourseType(id);
        return courseType;
    }

    /**
     * 新增课程类型信息
     * @param courseType
     */
    @Override
    public void addCourseType(CourseType courseType){
        String maxId = RedisCommon.getMaxCourseTypeId(courseType.getBusinessId());
        if ("".equals(maxId)){
            maxId = RedisCommon.setAndReturnMaxCourseTypeId(courseType.getBusinessId(),
                    courseDao.getMaxCourseTypeId(courseType.getBusinessId()));
        }
        courseType.setId(UUID.randomUUID().toString());
        courseType.setCoursetypeSerialId(maxId);
        courseDao.addCourseType(courseType);
    }

    /**
     * 更新课程信息
     * @param course
     * @param updateIcon
     */
    public void updateCourseInfo(Course course, boolean updateIcon){
        if (updateIcon){
            courseDao.updateCourseInfo(course);
        }else {
            courseDao.updateCourseInfoNoIcon(course);
        }
    }

    /**
     * 新增课程-卡关系
     * @param cardIdStr
     */
    @Transactional
    public void addCourseAndCard(String courseId, String cardIdStr){
        String[] strings = cardIdStr.split(",");
        List<CourseAndCard> courseAndCardList = new ArrayList<>();
        List<String> list = Arrays.asList(strings);
        for (String cards:list){
            CourseAndCard courseAndCard = new CourseAndCard();
            courseAndCard.setId(UUID.randomUUID().toString());
            courseAndCard.setCourseId(courseId);
            courseAndCard.setMembcardId(cards);
            courseAndCardList.add(courseAndCard);
        }
        courseDao.deleteCourseAndCard(courseId);
        courseDao.addCourseAndCard(courseAndCardList);
    }

    /**
     * 新增老师-课程关系
     * @param courseIdStr
     */
    @Override
    @Transactional
    public void addTeacherAndCourse(String employeeId, String courseIdStr){
        String[] strings = courseIdStr.split(",");
        List<TeacherAndCourse> teacherAndCourseList = new ArrayList<>();
        List<String> list = Arrays.asList(strings);
        for (String courseId:list){
            TeacherAndCourse teacherAndCourse = new TeacherAndCourse();
            teacherAndCourse.setId(UUID.randomUUID().toString());
            teacherAndCourse.setEmployeeId(employeeId);
            teacherAndCourse.setCourseId(courseId);
            teacherAndCourseList.add(teacherAndCourse);
        }
        courseDao.deleteTeacherAndCourse(employeeId);
        courseDao.addTeacherAndCard(teacherAndCourseList);
    }

    /**
     * 更新课程-卡关系
     * @param courseAndCardList
     */
//    @Override
//    public void updateCourseAndCard(List<CourseAndCard> courseAndCardList){
//        for (CourseAndCard courseAndCard:courseAndCardList){
//            courseDao.updateCourseAndCard(courseAndCard);
//        }
//    }

    /**
     * 新增排课课程设置信息
     * @param coursePlan
     */
    @Override
    @Transactional
    public void addCoursePlan(CoursePlan coursePlan){
        String bg = coursePlan.getClassDatetime();
        String ed = DateUtil.subMinute(bg,Integer.valueOf(coursePlan.getDuration()));
        //判断排课教室是否空闲
        if (!isFreeClassroom(coursePlan.getStoreId(),coursePlan.getClassroomId(),bg,ed)){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_classroombusy.getValue());
        }
        //排课老师是否空闲
        if (!isFreeEmployee(coursePlan.getStoreId(),coursePlan.getEmployeeId(),bg,ed)){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_employeebusy.getValue());
        }
        coursePlan.setId(UUID.randomUUID().toString());
        List<CoursePlan> coursePlanList = new ArrayList<>();
        coursePlanList.add(coursePlan);
        courseDao.addCoursePlan(coursePlanList);
        //增加签到数据
        CourseSign courseSign = new CourseSign();
        courseSign.setId(UUID.randomUUID().toString());
        courseSign.setCourseplanId(coursePlan.getId());
        List<CourseSign> courseSignList = new ArrayList<>();
        courseSignList.add(courseSign);
        courseDao.addCourseSign(courseSignList);
    }

    /**
     * 复制一周的排课内容
     * 暂时不做defi 20170908
     * @param storeId
     * @param year
     * @param weekIndex
     *
     */
    @Override
    public void batchAddCoursePlanByWeek(String storeId, String year, String weekIndex){
        List<CoursePlan> coursePlanList = listCoursePlanByWeek(storeId,"",year,weekIndex);
        //暂时不做
    }

    /**
     * 修改排课课程设置信息
     * @param coursePlan
     */
    @Override
    public void updateCoursePlan(CoursePlan coursePlan){
        String bg = coursePlan.getClassDatetime();
        String ed = DateUtil.subMinute(bg,Integer.valueOf(coursePlan.getDuration()));
        //判断排课教室是否空闲
        if (!isFreeClassroom(coursePlan.getStoreId(),coursePlan.getClassroomId(),bg,ed)){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_classroombusy.getValue());
        }
        //排课老师是否空闲
        if (!isFreeEmployee(coursePlan.getStoreId(),coursePlan.getEmployeeId(),bg,ed)){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_employeebusy.getValue());
        }
        courseDao.updateCoursePlan(coursePlan);
    }

    /**
     * 判断教室是否空闲
     * @param storeId
     * @param classroomId
     * @param beginTime
     * @param endTime
     * @return
     */
    private boolean isFreeClassroom(String storeId, String classroomId, String beginTime, String endTime){
        Integer count = courseDao.getClassroomFreeStatus(storeId, classroomId, beginTime, endTime);
        if (count == 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 判断老师是否空闲
     * @param storeId
     * @param employeeId
     * @param beginTime
     * @param endTime
     * @return
     */
    private boolean isFreeEmployee(String storeId, String employeeId, String beginTime, String endTime){
        Integer count = courseDao.getEmployeeFreeStatus(storeId, employeeId, beginTime, endTime);
        if (count == 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 获取团课排课信息（按周展示）
     * @param storeId
     * @param year
     * @param weekIndex
     * @return
     */
    @Override
    public List<CoursePlan> listCoursePlanByWeek(String storeId, String employeeId, String year, String weekIndex){
        HashMap<String,String> hashMap =
                DateUtil.getDateByWeekIndex(Integer.valueOf(year), Integer.valueOf(weekIndex));
        String beginDate = hashMap.get("beginDate");
        String endDate = hashMap.get("endDate");
        List<CoursePlan> coursePlanList =courseDao.listCoursePlanByWeek(storeId, employeeId, beginDate, endDate);
        return coursePlanList;
    }

    /**
     * 获取约课人员信息
     * @param coursesignId
     * @param page
     * @return
     */
    @Override
    public List<CourseOrder> listCourseOrder(String coursesignId, Integer page){
        BatchDataPage batchDataPage = new BatchDataPage();
        batchDataPage.setPage(page);
        List<CourseOrder> listCourseOrder = courseDao.listCourseOrder(coursesignId, batchDataPage.getOffset(),
                batchDataPage.getPagesize());
        for (CourseOrder courseOrder:listCourseOrder){
            if (courseOrder.getHeadPortrait() !=null &&courseOrder.getHeadPortrait()!=""){
                String heanPath = Configuration.getStaticShowPath()+courseOrder.getHeadPortrait();
                courseOrder.setHeadPortrait(heanPath);
            }
        }
        batchDataPage.setRows(listCourseOrder);
        return listCourseOrder;
    }

    /**
     * 增加私教约课信息
     * @param courseOrder
     */
    @Override
    @Transactional
    public void addPrivateCourseOrder(CourseOrder courseOrder){
        MembercardRelation membercardrelation = membercardrelationDao.queryMembercardrelationById(courseOrder.getCardrelationId());
        //查看约课人员是否超员
        String remainsOrder = courseDao.getPrivateRemainsOrder(courseOrder.getCourseplanId(), courseOrder.getClassTime());
        if ("1".equals(remainsOrder)){
            throw new BusiException(CommonEnum.ReturnCode.SystemCode.BusinessCode.busi_err_noremainorder.getValue());
        }
        //根据时间判断是否可预约
        String courseDateTimeStr = DevUtils.formatOrderTimeToDatetime(courseOrder.getClassDate(),courseOrder.getClassTime());
        String orderTimeStr = DateUtil.subMinute(DateUtil.getDateTime(), SysConfigConstants.BEFORE_MINUTERS_ORDER);
        Date courseDateTime = DateUtil.parse(courseDateTimeStr);
        Date orderDateTime = DateUtil.parse(orderTimeStr);
        if (DateUtil.isAfter(orderDateTime,courseDateTime)){
            throw new BusiException(CommonEnum.ReturnCode.SystemCode.BusinessCode.busi_err_cannotorder.getValue());
        }
        //该卡是否过期
        if (Integer.parseInt(membercardrelation.getTimes()) <= 0 || DateUtil.isBeforeNow(DateUtil.parse(membercardrelation.getValidityTime()))){
            throw new BusiException(CommonEnum.ReturnCode.SystemCode.BusinessCode.busi_err_cardisover.getValue());
        }
        //未激活
        if (Integer.parseInt(membercardrelation.getCardStatus()) != 2){
            throw new BusiException(CommonEnum.ReturnCode.SystemCode.BusinessCode.busi_err_cardisnotvalid.getValue());
        }
        courseOrder.setId(UUID.randomUUID().toString());
        //私教要在新增预约记录时添加签到记录
        //生成coursesignId
        CourseSign courseSign = new CourseSign();
        String signId = UUID.randomUUID().toString();
        courseSign.setId(signId);
        courseOrder.setCoursesignId(signId);
        courseSign.setCourseplanId(courseOrder.getCourseplanId());
        List<CourseSign> courseSignList = new ArrayList<>();
        courseSignList.add(courseSign);
        courseDao.addCourseSign(courseSignList);
        courseDao.addCourseOrder(courseOrder);
    }

    /**
     * 获取课程签到关系数据列表
     * @param storeId
     * @param employeeId
     * @param dataType
     * @param page
     * @return
     */
    @Override
    public List<CoursePlanSign> listCoursePlanSignInfo(String storeId, String employeeId, Integer dataType, Integer page,
                                                       String classDate){
        BatchDataPage batchDataPage = new BatchDataPage();
        batchDataPage.setPage(page);
        List<CoursePlanSign> listCoursePlanSign = courseDao.listCoursePlanSignInfo(storeId, employeeId, dataType,
                batchDataPage.getOffset(), batchDataPage.getPagesize(), classDate);
        return listCoursePlanSign;
    }

    /**
     * 获取课程签到关系数据列表(私课)
     * @param storeId
     * @param employeeId
     * @param dataType
     * @param page
     * @param classDate
     * @return
     */
    @Override
    public List<CoursePlanSign> listPrivateCoursePlanSignInfo(String storeId, String employeeId, Integer dataType,
                                                              Integer page, String classDate){
        BatchDataPage batchDataPage = new BatchDataPage();
        batchDataPage.setPage(page);
        List<CoursePlanSign> listCoursePlanSign = courseDao.listPrivateCoursePlanSignInfo(storeId, employeeId, dataType,
                batchDataPage.getOffset(), batchDataPage.getPagesize(), classDate);
        return listCoursePlanSign;
    }

    /**
     * 获取私教排课列表
     * @param storeId
     * @param classDate
     * @param employeeName
     * @param page
     * @return
     */
    @Override
    public List<PrivateCoursePlan> listPrivateCoursePlan(String storeId,
                                                         String classDate,
                                                         String employeeName,
                                                         Integer page){
        BatchDataPage batchDataPage = new BatchDataPage();
        batchDataPage.setPage(page);
        List<PrivateCoursePlan> privateCoursePlanList = courseDao.listPrivateCoursePlan(storeId, classDate, employeeName,
                batchDataPage.getOffset(), batchDataPage.getPagesize());
        return privateCoursePlanList;
    }

    /**
     * 新增私教排课信息
     * @param privateCoursePlan
     */
    @Override
    public void addPrivateCoursePlan(PrivateCoursePlan privateCoursePlan){
        Integer count = courseDao.getPrivateCoursePlanCountByClassTime(privateCoursePlan.getClassDate(),
                privateCoursePlan.getEmployeeId());
        if (count >0 ){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_existsprivatecourseplan.getValue());
        }
        privateCoursePlan.setId(UUID.randomUUID().toString());
        courseDao.addPrivateCoursePlan(privateCoursePlan);
    }

    /**
     * 删除私教排课信息
     * @param id
     */
    public void delPrivateCoursePlan(String id){
        String  count = courseDao.getCourseOrderCount(id);
        if (Integer.parseInt(count) > 0){//如果已有预约就不能删除排课计划
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_courseexistsorder.getValue());
        }
        courseDao.deletePrivateCoursePlan(id);
    }

    /**
     * 店长签到
     * @param id
     */
    @Override
    @Transactional
    public void managerCourseSign(String id){
        courseDao.updateCourseSignStatus(CommonEnum.Course.SignStatus.classmanagersign.getValue(),
                DateUtil.format(new Date(), SysConfigConstants.DATE_FORMAT_FORDATETIME),
                null, id);
        //更新课程状态
        courseDao.updateCourseStatus(id, CommonEnum.Course.ClassStatus.classstarted.getIntegerVal());
    }

    /**
     * 取消课程
     * @param coursePlanId
     * @param courseSignId
     */
    @Override
    @Transactional
    public void courseCancelByCourse(String coursePlanId, String courseSignId){
        courseDao.updateCourseStatus(courseSignId,CommonEnum.Course.ClassStatus.classcancel.getIntegerVal());
        //更新会员约课状态，并发送信息
        CourseOrder courseOrder = new CourseOrder();
        courseOrder.setCourseplanId(coursePlanId);
        courseOrder.setCoursesignId(courseSignId);
        courseOrder.setOrderStatus(CommonEnum.Course.OrderStatus.cancel.getValue());
        courseDao.updateOrderStatusByCourser(courseOrder);
    }

    /**
     * 新增教室
     * @param classroom
     */
    @Override
    public void addClassroom(Classroom classroom) {
        classroom.setId(UUID.randomUUID().toString());
        courseDao.addClassroom(classroom);
    }

    /**
     * 获取教室详情
     * @param id
     * @return
     */
    @Override
    public Classroom getClassroomInfoById(String id) {
        return courseDao.getClassroomInfoById(id);
    }

    /**
     *查询门店中所有教室
     * @param storeId
     * @param page
     * @return
     */
    @Override
    public List<Classroom> listClassroomByStoreId(String storeId, Integer page) {
        BatchDataPage batchDataPage = new BatchDataPage();
        batchDataPage.setPage(page);
        return courseDao.listClassroomByStoreId(storeId, batchDataPage.getOffset(), batchDataPage.getPagesize());
    }

    /**
     * 编辑教室信息
     * @param classroomName
     * @param classroomId
     */
    @Override
    public void updateClassroom(String classroomName, String classroomId) {
        courseDao.updateClassroom(classroomName, classroomId);
    }

    /**
     * 获取团课约课列表
     * @param storeId
     * @param employeeId
     * @param memberId
     * @param classDate
     * @param DataType 1.按时间获取 2.按老师获取
     * @param page
     * @return
     */
    public List<ClientCourseOrder> listClientCourseOrder(String storeId, String employeeId, String memberId,String isExperience,
                                                         String classDate, String DataType, Integer page){
        List<ClientCourseOrder> clientCourseOrderList = null;
        BatchDataPage batchDataPage = new BatchDataPage();
        batchDataPage.setPage(page);
        String endDate = null;
        if ("1".equals(DataType)){
            endDate = DateUtil.subDay(DateUtil.format(DateUtil.parse(classDate),"yyyy-MM-dd"),1);
            clientCourseOrderList = courseDao.listClientCourseOrder(storeId, classDate,endDate, memberId, isExperience,
                    batchDataPage.getOffset(), batchDataPage.getPagesize());
        }else if ("2".equals(DataType)){
//            加上提前预约时间
            String beginDate = DateUtil.subMinute(DateUtil.format(new Date(), SysConfigConstants.DATE_FORMAT_FORDATETIME),
                    SysConfigConstants.BEFORE_MINUTERS_ORDER);
//            try {
//                加上一天使用"<"来判断时间
                  //固定可提前预约6天的课程
//                endDate = DateUtil.subDay(DevUtils.getWeekLastDate(DateUtil.format(new Date())),1);
                endDate = DateUtil.subDay(DateUtil.format(new Date()),SysConfigConstants.COURSE_ORDER_LATERDAY+1);
//            }catch (ParseException e){
//                throw new BusiException(CommonEnum.ReturnCode.SystemCode.sys_err_exception.getValue());
//            }
            clientCourseOrderList = courseDao.listCourseOrderByTeacher(storeId,beginDate,endDate,memberId,employeeId,isExperience,
                    batchDataPage.getOffset(),batchDataPage.getPagesize());
        }
        for (ClientCourseOrder clientCourseOrder:clientCourseOrderList){
            String iconPath = clientCourseOrder.getIconPath();
            if (iconPath != null && !"".equals(iconPath)){
                clientCourseOrder.setIconPath(Configuration.getStaticShowPath() + iconPath);
            }
        }
        return clientCourseOrderList;
    }

    /**
     * 获取团课可约课老师列表
     * @param storeId
     * @param page
     * @return
     */
    public List<CoachInfo> listClientCourseOrderTeacher(String storeId, Integer page){
        BatchDataPage batchDataPage = new BatchDataPage();
        batchDataPage.setPage(page);
        String classDate = DateUtil.subMinute(DateUtil.format(new Date()),SysConfigConstants.BEFORE_MINUTERS_ORDER);
        List<CoachInfo> coachInfoList = courseDao.listCourseOrderTeacher(storeId,classDate,batchDataPage.getOffset(),
                batchDataPage.getPagesize());
        for (CoachInfo coachInfo:coachInfoList){
            String empIcon = coachInfo.getEmpHead();
            if (empIcon != null && !"".equals(empIcon)){
                coachInfo.setEmpHead(Configuration.getStaticShowPath() + empIcon);
            }
        }
        return coachInfoList;
    }

    /**
     *获取可预约私教的教练列表
     * @param storeId
     * @param page
     * @param pageSiz
     * @return
     */
    @Override
    public List<CoachInfo> listPrevateCoursePlanTeacher(String storeId, Integer page, Integer pageSiz) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        BatchDataPage batchDataPage = new BatchDataPage();
        batchDataPage.setPage(page);
        batchDataPage.setPagesize(pageSiz);
       // String beginDate = sdf.format(new Date())+" 00:00:00";
       // String classDate = DateUtil.subMinute(DateUtil.format(new Date()),SysConfigConstants.BEFORE_MINUTERS_ORDER);
        String classDate = sdf.format(new Date())+" 00:00:00";
        List<CoachInfo> coachInfoList = courseDao.listPrevateCoursePlanTeacher(storeId, classDate, batchDataPage.getOffset(), batchDataPage.getPagesize());
        if (coachInfoList !=null && coachInfoList.size()>0){
            for (CoachInfo coachInfo :coachInfoList){
                String empHeand = coachInfo.getEmpHead();
                if (empHeand !=null && !"".equals(empHeand)){
                    coachInfo.setEmpHead(Configuration.getStaticShowPath()+empHeand);
                }
            }
        }
        return coachInfoList;
    }

    /**
     *获取老师私教课程列表
     * @param storeId
     * @param employeeId
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public List<Course> listPrevateCourse(String storeId,String employeeId, Integer page, Integer pageSize) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        BatchDataPage batchDataPage = new BatchDataPage();
        batchDataPage.setPage(page);
        batchDataPage.setPagesize(pageSize);
        String  endDate = DateUtil.subDay(DateUtil.format(new Date()),SysConfigConstants.COURSE_ORDER_LATERDAY+1);
       // String beginDate = DateUtil.subMinute(DateUtil.format(new Date()),SysConfigConstants.BEFORE_MINUTERS_ORDER);
        String beginDate =  sdf.format(new Date())+" 00:00:00";
        return courseDao.listPrevateCourse(storeId,beginDate,endDate,employeeId,batchDataPage.getOffset(), batchDataPage.getPagesize());
    }

    /**
     *获取老师私教的可预约时间
     * @param storeId
     * @param employeeId
     * @return
     */
    @Override
    public List<PrivateOrderDateTime> listDateTimeByEmpId(String storeId, String employeeId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       // String beginDate = DateUtil.subMinute(DateUtil.format(new Date()), SysConfigConstants.BEFORE_MINUTERS_ORDER);
        String beginDate = sdf.format(new Date())+" 00:00:00";
        String endDate = DateUtil.subDay(DateUtil.format(new Date()),SysConfigConstants.COURSE_ORDER_LATERDAY+1);
        return courseDao.listDateTimeByEmpId(employeeId,storeId, beginDate,endDate);
    }

    /**
     * 获取该教练的已预约时间
     * @param employeeId
     * @param storeId
     * @return
     */
    @Override
    public List<PrivateOrderDateTime> listexistsDateTimeByEmpId(String employeeId, String storeId) {
        String classDate = DateUtil.subMinute(DateUtil.format(new Date()),SysConfigConstants.BEFORE_MINUTERS_ORDER);
       // String classDate = DateUtil.subDay(nowTime,SysConfigConstants.COURSE_ORDER_LATERDAY+1);
        String endDate = DateUtil.subDay(DateUtil.format(new Date()),SysConfigConstants.COURSE_ORDER_LATERDAY+1);
        return courseDao.listexistsDateTimeByEmpId(employeeId,storeId,classDate,endDate);
    }

    /**
     * 获取私教预约时间
     * @param employeeId
     * @param storeId
     * @return
     */
    @Override
    public List<PrivateOrderDateTime> listPrivateOrderDateTime(String employeeId, String storeId) {
        //获取教练可预约时间
        List<PrivateOrderDateTime> listDateTime = this.listDateTimeByEmpId(employeeId,storeId);
        String orderType =""; //1:可预约 ;2:不可预约
        List<PrivateOrderDateTime> list =new ArrayList<>();
        if (listDateTime!=null &&listDateTime.size()>0){
            for (PrivateOrderDateTime privateOrderDateTime:listDateTime){
                PrivateOrderDateTime privateOrderDateTime1 = new PrivateOrderDateTime();
                String privatePlanId = privateOrderDateTime.getId();
                String classdate = privateOrderDateTime.getClassDate();
                privateOrderDateTime1.setId(privatePlanId);
                privateOrderDateTime1.setClassDate(classdate);
                String[] timeArr = (privateOrderDateTime.getClassDatetime()).split(",");
                List<ResponsePrivateDateTime> timeList = new ArrayList<>();
                String nowDate = DateUtil.subMinute(DateUtil.format(new Date()), SysConfigConstants.BEFORE_MINUTERS_ORDER);
                if (timeArr!=null && timeArr.length>0){
                    for (String timeStr: timeArr){ //格式:19:00-19:50
                        //判断是否已过预约时间 dateTime :2017-09-11 15:51:00
                        String dateTime = classdate+" "+((timeStr.split("-"))[0].trim()+":00");
                        ResponsePrivateDateTime responsePrivateDateTime = new ResponsePrivateDateTime();
                        responsePrivateDateTime.setClassTime(timeStr);
                        if (!DateUtil.isBefore(DateUtil.parse(nowDate), DateUtil.parse(dateTime))) {//已过预约时间
                            orderType = "2";//不可预约
                        }else {//未过开课时间
                            CourseOrder courseOrder =  courseDao.getPrivateOrderByPlan(privatePlanId,classdate,(timeStr.split("-"))[0].trim()+":00");
                            if (courseOrder!=null){
                                orderType = "2";//不可预约
                            }else {
                                orderType = "1";//可预约
                            }
                        }
                        responsePrivateDateTime.setOrderType(orderType);
                        timeList.add(responsePrivateDateTime);
                    }
                }
                privateOrderDateTime1.setResponsePrivateDateTimeList(timeList);
                list.add(privateOrderDateTime1);
            }

        }
        return list;
    }

    /**
     * 获取可以预约该私教课程的会员卡
     * @param courseId
     * @param memberId
     * @param page
     * @param pageSiz
     * @return
     */
    @Override
    public List<MembercardRelation> listPrevateCourseMembercard(String courseId,String memberId, Integer page, Integer pageSiz) {
        BatchDataPage batchDataPage = new BatchDataPage();
        batchDataPage.setPage(page);
        batchDataPage.setPagesize(pageSiz);
        return courseDao.listPrevateCourseMembercard(courseId,memberId, batchDataPage.getOffset(), batchDataPage.getPagesize());
    }

    /**
     * 获取团课预约课程详细信息
     * @param courseId
     * @param memberId
     * @param businessId
     * @param courseSignId
     * @return
     */
    @Override
    public CourseOrderInfo getCourseOrderInfo(String courseId, String memberId, String businessId, String courseSignId ){
        CourseOrderInfo courseOrderInfo = courseDao.getCourseOrderInfo(courseSignId,memberId);
        if (courseOrderInfo != null){
            List<MembercardRelation> memberCardList = courseDao.listCourseOrderCard(courseId,memberId,businessId);
            if (memberCardList != null && memberCardList.size() > 0)
                courseOrderInfo.setMemberCardList(memberCardList);
        }
        return courseOrderInfo;
    }

    /**
     * 获取体验团课预约课程详细信息
     * @param memberId
     * @param businessId
     * @param courseSignId
     * @return
     */
    @Override
    public CourseOrderInfo getExperienceCourseOrderInfo(String memberId, String businessId,
                                                        String courseSignId){
        String isExperience = courseDao.getIsOrderExperienceCourse(businessId,memberId);
//        if (!isExperience.equals("0")){
//            //已存在体验课 不允许在体验
//            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_cannotorderexperiencecourse.getValue());
//        }
        CourseOrderInfo courseOrderInfo = courseDao.getCourseOrderInfo(courseSignId,memberId);
        courseOrderInfo.setIsExperience(isExperience);
        return courseOrderInfo;
    }

    /**
     * 新增约课记录
     * @param memberId
     * @param coursesignId
     * @param cardrelationId
     */
    @Transactional
    @Override
    public void addCourseOrder(String memberId, String courseplanId, String coursesignId, String cardrelationId) throws ParseException {
        //课程是否可预约
        CourseOrderEnable courseOrderEnable = isCourseValid(coursesignId);
        String classTime = DateUtil.format(DateUtil.parse(courseOrderEnable.getClassDatetime()),"kk:mm");
        String nowDate = DateUtil.subMinute(DateUtil.format(new Date()), SysConfigConstants.BEFORE_MINUTERS_ORDER);//当前时间
        //判断会员是否请假
        //状态:1.正常,2.停卡,3.删除,4.请假,5.已过期,6.未激活
        MembercardRelation membercardRelation =  membercardRelationService.queryMembercardrelationById(cardrelationId);
        //判断是否请假中
        if (membercardRelation.getCardStatus()!=null && membercardRelation.getCardStatus().equals(CommonEnum.Membercardrelation.cardStatus.cardStatus_leave.getValue())){
            String creator = "";
            membercardRelationService.memberCardStopOrStart(cardrelationId,CommonEnum.Membercardrelation.cardStatus.cardStatus_leave.getValue(),creator);
        }
        //判断是否删除或是停卡或是已过期
        if (membercardRelation.getCardStatus().equals(CommonEnum.Membercardrelation.cardStatus.cardStatus_del.getValue())
                ||membercardRelation.getCardStatus().equals(CommonEnum.Membercardrelation.cardStatus.cardStatus_stop.getValue())
                ||membercardRelation.getCardStatus().equals(CommonEnum.Membercardrelation.cardStatus.cardStatus_outofdate.getValue())){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_cannotorder.getValue());
        }
        //判断会员卡是否未激活
        if (membercardRelation.getCardStatus().equals(CommonEnum.Membercardrelation.cardStatus.cardStatus_nonactivated.getValue())) {
            //1设置成正常
            //2设置有效期时间
            MembercardRelation updateMemebercardRelation = new MembercardRelation();
            updateMemebercardRelation.setId(cardrelationId);
            updateMemebercardRelation.setCardStatus(CommonEnum.Membercardrelation.cardStatus.cardStatus_normal.getValue());
            //计算开卡到当前时间天数
            if ( membercardRelation.getOpencardDate()!=null ){
                SimpleDateFormat sdf = new SimpleDateFormat(SysConfigConstants.DATE_FORMAT_FORDATETIME);
                long days = BusiUtils.getDays(nowDate, membercardRelation.getOpencardDate().substring(0, 19));
                Calendar cal = Calendar.getInstance();
                if (days > 0) {
                    cal.add(cal.DAY_OF_MONTH, Integer.parseInt(String.valueOf(days)));
                    updateMemebercardRelation.setValidityTime(sdf.format(cal.getTime()));
                }
            }

            membercardRelationService.updateMembercardRelation(updateMemebercardRelation);
        }

     //   String classDate = courseOrderEnable.getClassDatetime();
        //卡可否约课
       /* MembercardRelation membercardRelation = membercardRelationService.isCardValid(cardrelationId, classDate);
        if (membercardRelation.getActTime()!=null){
            membercardRelationService.activateMembercardRelation(cardrelationId);
        }*/

        //判断是否预约过并取消过该计划
        BaseCourseOrderInfo baseCourseOrderInfo =this.getPublicCourseOrderByPlanId(courseplanId,coursesignId,memberId);
        if (baseCourseOrderInfo ==null){ //如果有预约并取消过的记录就更新记录,如果没有就插入数据
            CourseOrder courseOrder = new CourseOrder();
            courseOrder.setId(UUID.randomUUID().toString());
            courseOrder.setCourseplanId(courseplanId);
            courseOrder.setCoursesignId(coursesignId);
//        courseOrder.setClassDate(classDate);
            courseOrder.setClassTime(classTime);
            courseOrder.setMemberId(memberId);
            courseOrder.setCardrelationId(cardrelationId);
            courseDao.addCourseOrder(courseOrder);
        }else {
            if (baseCourseOrderInfo.getOrderStatus().equals("1")){//已预约,不能重复预约
                throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_membercourseorderexists.getValue());
            }
            if(baseCourseOrderInfo.getOrderStatus().equals("4")){//有取消过预约,更新为预约
                DateFormat df=new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
                CourseOrder updateCourseOrder = new CourseOrder();
                updateCourseOrder.setId(baseCourseOrderInfo.getId());
                updateCourseOrder.setOrderTime(df.format(new Date()));
                updateCourseOrder.setOrderStatus("1");
                updateCourseOrder.setCardrelationId(cardrelationId);
                courseDao.updateCourseOrder(updateCourseOrder);
                //课程签到表中设置取消预约人数
                CourseSign courseSign = courseDao.getCourseSignById(coursesignId);
                //更新课程签到表中取消人数
                courseDao.updateCourseSign(coursesignId,(Integer.parseInt(courseSign.getCancelPersons())-1)+"");
            }
        }



    }

    /**
     * 预约私教
     * @param
     * @param courseId
     * @param cardrelationId
     * @param remark
     * @param privateOrderDateTimes
     */
    @Transactional
    @Override
    public void OrderPrevateCourse(String employeeId,String memberId, String courseId, String cardrelationId, String remark, List<PrivateOrderDateTime> privateOrderDateTimes) throws ParseException {
        String classDate = "";
        String courseplanId = "";
        CourseOrder courseOrder = new CourseOrder();
        CourseSign courseSign = new CourseSign();
        courseOrder.setMemberId(memberId);
        if (privateOrderDateTimes !=null && privateOrderDateTimes.size()>0) {
            for (PrivateOrderDateTime privateOrderDateTime : privateOrderDateTimes) {
                classDate = privateOrderDateTime.getClassDate();
                courseplanId = privateOrderDateTime.getId();
                String[] classDatetimeArr = privateOrderDateTime.getClassDatetime().split(",");
                if (classDatetimeArr != null && classDatetimeArr.length > 0) {
                    for (String classTime : classDatetimeArr) { //10:50 - 11:50
                        classTime = (classTime.split("-"))[0].trim()+":00";
                        String classdateTime = classDate + " " + classTime;
                        //判断是否已过预约时间
                        String nowDate = DateUtil.subMinute(DateUtil.format(new Date()), SysConfigConstants.BEFORE_MINUTERS_ORDER);
                        if (!DateUtil.isBefore(DateUtil.parse(nowDate), DateUtil.parse(classdateTime))) {
                            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_cannotorder.getValue());
                        }
                        PrivateOrderDateTime queryPrivateOrderDateTime = courseDao.getlistExistsDateTime(employeeId, courseplanId, classDate, classTime);
                        //判断该时间段的私教是否已预约
                        if (queryPrivateOrderDateTime != null) {
                            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_courseexistsorder.getValue());
                        }
                        //判断课程是否取消
                        PrivateOrderDateTime queryPrivateOrderTime = courseDao.getPrivateCourseOrderEnable(courseplanId);
                        if (queryPrivateOrderTime ==null){
                            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_coursenotenable.getValue());
                        }else {
                            String[] timeStr = (queryPrivateOrderTime.getClassDatetime()).split(",");//22:37-23:37,21:37-22:37,20:37-21:37
                            if (timeStr!=null && timeStr.length>0){
                                int count = 0;
                                for (String str: timeStr){

                                    if (classTime.equals(str.substring(0,5)+":00")){
                                        count++;
                                    }
                                }
                                if (count==0){
                                    throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_privatecoursecancle.getValue());
                                }
                            }
                        }
                        //判断会员是否请假
                        //状态:1.正常,2.停卡,3.删除,4.请假,5.已过期,6.未激活
                        MembercardRelation membercardRelation =  membercardRelationService.queryMembercardrelationById(cardrelationId);
                        //判断是否请假中
                        if (membercardRelation.getCardStatus().equals(CommonEnum.Membercardrelation.cardStatus.cardStatus_leave.getValue())){
                            String creator = "";
                            membercardRelationService.memberCardStopOrStart(cardrelationId,CommonEnum.Membercardrelation.cardStatus.cardStatus_leave.getValue(),creator);
                        }
                        //判断是否删除或是停卡或是已过期
                        if (membercardRelation.getCardStatus().equals(CommonEnum.Membercardrelation.cardStatus.cardStatus_del.getValue())
                                ||membercardRelation.getCardStatus().equals(CommonEnum.Membercardrelation.cardStatus.cardStatus_stop.getValue())
                                ||membercardRelation.getCardStatus().equals(CommonEnum.Membercardrelation.cardStatus.cardStatus_outofdate.getValue())){
                            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_cannotorder.getValue());
                        }
                        //判断会员卡是否未激活
                        if (membercardRelation.getCardStatus().equals(CommonEnum.Membercardrelation.cardStatus.cardStatus_nonactivated.getValue())){
                            //1设置成正常
                            //2设置有效期时间
                            MembercardRelation updateMemebercardRelation = new MembercardRelation();
                            updateMemebercardRelation.setId(cardrelationId);
                            updateMemebercardRelation.setCardStatus(CommonEnum.Membercardrelation.cardStatus.cardStatus_normal.getValue());
                            //计算开卡到当前时间天数
                            SimpleDateFormat sdf = new SimpleDateFormat(SysConfigConstants.DATE_FORMAT_FORDATETIME);
                            if (membercardRelation.getOpencardDate() !=null && membercardRelation.getOpencardDate()!=""){
                                long days = BusiUtils.getDays(nowDate, membercardRelation.getOpencardDate().substring(0, 19));
                                Calendar cal = Calendar.getInstance();
                                if (days > 0) {
                                    cal.add(cal.DAY_OF_MONTH,Integer.parseInt(String.valueOf(days)));
                                    updateMemebercardRelation.setValidityTime(sdf.format(cal.getTime()));
                                }
                                membercardRelationService.updateMembercardRelation(updateMemebercardRelation);
                            }

                        }

                        //判断是否有预约并且取消过的预约记录
                        BaseCourseOrderInfo baseCourseOrderInfo = this.getPrivateCourseOrderByPlanId(courseplanId, memberId, classDate, classTime);
                        if (baseCourseOrderInfo==null){ //如果没有插入数据
                            String id = UUID.randomUUID().toString();
                            courseSign.setId(id);
                            courseSign.setCourseplanId(courseplanId);
                            List<CourseSign> courseSignList = new ArrayList<>();
                            courseSignList.add(courseSign);
                            courseDao.addCourseSign(courseSignList); //创建课程签到

                            courseOrder.setId(UUID.randomUUID().toString());
                            courseOrder.setCoursesignId(id);
                            courseOrder.setCardrelationId(cardrelationId);
                            courseOrder.setClassTime(classTime);
                            courseOrder.setMemberId(memberId);
                            courseOrder.setCourseplanId(courseplanId);
                            courseOrder.setCourseId(courseId);
                            courseOrder.setCourseplanId(privateOrderDateTime.getId());
                            if (remark != null && remark != "") {
                                courseOrder.setRemark(remark);
                            }
                            courseDao.addCourseOrder(courseOrder);//创建课程预约记录
                        }else { //如果有的话更新预约记录就更新预约记录
                            if (baseCourseOrderInfo.getOrderStatus().equals("1")){//已预约,不能重复预约
                                throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_membercourseorderexists.getValue());
                            }
                            if(baseCourseOrderInfo.getOrderStatus().equals("4")){//有取消过预约,更新为预约
                                DateFormat df=new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
                                CourseOrder updateCourseOrder = new CourseOrder();
                                updateCourseOrder.setId(baseCourseOrderInfo.getId());
                                updateCourseOrder.setOrderTime(df.format(new Date()));
                                updateCourseOrder.setOrderStatus("1");
                                updateCourseOrder.setCardrelationId(cardrelationId);
                                courseDao.updateCourseOrder(updateCourseOrder);
                                //课程签到表中设置取消预约人数
                                CourseSign queryCourseSign = courseDao.getCourseSignById(baseCourseOrderInfo.getCoursesignId());
                                //更新课程签到表中取消人数
                                courseDao.updateCourseSign(baseCourseOrderInfo.getCoursesignId(),(Integer.parseInt(queryCourseSign.getCancelPersons())-1)+"");
                            }
                        }
                    }
                }
            }
        }else {
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_coursenotenable.getValue());
        }
    }

    /**
     * 获取已预约私教列表
     * @param memberdId
     * @param storeId
     * @return
     */
    @Override
    public List<PrivateOrderRecord> listPrivateOrderRecordById(String memberdId, String storeId, Integer page, Integer pageSiz) {
        BatchDataPage batchDataPage = new BatchDataPage();
        batchDataPage.setPage(page);
        batchDataPage.setPagesize(pageSiz);
        List<PrivateOrderRecord> privateOrderRecords = courseDao.listPrivateOrderRecordById(memberdId,storeId, batchDataPage.getOffset(), batchDataPage.getPagesize());
        if (privateOrderRecords!=null && privateOrderRecords.size()>0){
            for (PrivateOrderRecord privateOrderRecord:privateOrderRecords){
                if (privateOrderRecord.getIconPath()!=null&&privateOrderRecord.getIconPath()!=""){//图片路径拼接
                    privateOrderRecord.setIconPath(Configuration.getStaticResourcePath()+privateOrderRecord.getIconPath());
                }
                String classTime =privateOrderRecord.getClassTime();
                String classDateTime = privateOrderRecord.getClassDate()+" "+classTime+":00";
                privateOrderRecord.setClassDateTime(classDateTime);
                String classDateTimeStr =  DateUtil.subMinute(classDateTime, Integer.parseInt(privateOrderRecord.getDuration()));
                privateOrderRecord.setClassTime(classTime.substring(0,5)+" - "+classDateTimeStr.substring(11,16));
                if (privateOrderRecord.getEmpHead()!=null &&privateOrderRecord.getEmpHead()!=""){
                    privateOrderRecord.setEmpHead(Configuration.getStaticShowPath() + privateOrderRecord.getEmpHead());
                }
            }
        }
        return privateOrderRecords;
    }

    /**
     * 判断课程是否有效(团课)
     * @param coursesignId
     * @return
     */
    @Override
    public CourseOrderEnable isCourseValid(String coursesignId){
        CourseOrderEnable courseOrderEnable = courseDao.getCourseOrderEnable(coursesignId);
        //根据时间判断是否可预约(时间可配置 默认30分钟)
        String nowDate = DateUtil.subMinute(DateUtil.format(new Date()), SysConfigConstants.BEFORE_MINUTERS_ORDER);
        String classDate = courseOrderEnable.getClassDatetime();
        if (!DateUtil.isBefore(DateUtil.parse(nowDate),DateUtil.parse(classDate))){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_cannotorder.getValue());
        }
        //判断课程是否约满
        if (Integer.valueOf(courseOrderEnable.getPermitPersons())==0){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_noremainorder.getValue());
        }
        //判断课程是否取消
        if ("3".equals(courseOrderEnable.getClassStatus())){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_coursecanceled.getValue());
        }
        if ("2".equals(courseOrderEnable.getEnable())){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_coursenotenable.getValue());
        }
        return courseOrderEnable;
    }

    /**
     * 新增约课记录
     * @param memberId
     * @param coursesignId
     * @param courseplanId
     */
    @Override
    public void addExperienceCourseOrder(String memberId, String courseplanId, String coursesignId, String remark) throws ParseException {
        DateFormat df=new SimpleDateFormat("kk:mm");
        //课程是否可预约
        CourseOrderEnable courseOrderEnable = isCourseValid(coursesignId);
        String classTime = df.format(df.parse(courseOrderEnable.getClassDatetime().substring(11,17))) ;
        //判断是否预约过并取消过该计划
        BaseCourseOrderInfo baseCourseOrderInfo =this.getPublicCourseOrderByPlanId(courseplanId,coursesignId,memberId);
        if (baseCourseOrderInfo ==null){ //如果有预约并取消过的记录就更新记录,如果没有就插入数据
            CourseOrder courseOrder = new CourseOrder();
            courseOrder.setId(UUID.randomUUID().toString());
            courseOrder.setCourseplanId(courseplanId);
            courseOrder.setCoursesignId(coursesignId);
            courseOrder.setClassTime(classTime);
            courseOrder.setMemberId(memberId);
            courseOrder.setRemark(remark);
            courseDao.addCourseOrder(courseOrder);
        }else {
            if (baseCourseOrderInfo.getOrderStatus().equals("1")){//已预约,不能重复预约
                throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_membercourseorderexists.getValue());
            }
            if(baseCourseOrderInfo.getOrderStatus().equals("4")){//有取消过预约,更新为预约
                DateFormat dft=new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
                CourseOrder updateCourseOrder = new CourseOrder();
                updateCourseOrder.setId(baseCourseOrderInfo.getId());
                updateCourseOrder.setOrderTime(dft.format(new Date()));
                updateCourseOrder.setOrderStatus("1");
                courseDao.updateCourseOrder(updateCourseOrder);
                //课程签到表中设置取消预约人数
                CourseSign courseSign = courseDao.getCourseSignById(coursesignId);
                //更新课程签到表中取消人数
                courseDao.updateCourseSign(coursesignId,(Integer.parseInt(courseSign.getCancelPersons())-1)+"");
            }
        }



    }

    /**
     * 根据id查询预约信息
     * @param id
     * @return
     */
    @Override
    public BaseCourseOrderInfo getCourseOrderInfoById(String id) {
        return courseDao.getCourseOrderInfoById(id);
    }

    /**
     * 取消预约
     * @param id 预约id
     */
    @Override
    public void cancelCourseOrder(String id) throws ParseException {
        DateFormat df=new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        BaseCourseOrderInfo baseCourseOrderInfo = this.getCourseOrderInfoById(id);
        String classTime= baseCourseOrderInfo.getClassDate()+" "+baseCourseOrderInfo.getClassTime()+":00";
        int minutes  = BusiUtils.getMinutes(df.format(new Date()),classTime);
        if (minutes<SysConfigConstants.MEMBER_CANCEL_ORDER_TIME_30){//离开课时间<30分钟不给予取消约课
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_notcancelcourseorder.getValue());
        }
        courseDao.cancelCourseOrder(id, CommonEnum.Course.OrderStatus.cancel.getValue());
    }

    /**
     * 查询平台用户所有的预约记录详情
     * @param phoneNum
     * @return
     */
    @Override
    public ResponseCourseOrder getMemberAllOrderByPhoneNum(String phoneNum,String businessId,Integer page,Integer pageSiz ) {
        BatchDataPage batchDataPage = new BatchDataPage();
        batchDataPage.setPage(page);
        batchDataPage.setPagesize(pageSiz);
        List<BackCourseOrderInfo> orderInfoList = courseDao.listMemberAllOrderByPhoneNum(phoneNum, null,batchDataPage.getOffset(), batchDataPage.getPagesize() );
        if (orderInfoList !=null && orderInfoList.size()>0){
            for (BackCourseOrderInfo bc : orderInfoList){
                if (bc.getCourseMeans().equals("2")){
                    bc.setOrderedPerson(courseDao.getCourseOrderCount(bc.getCoursesignId()));
                }
            }
        }
        ResponseCourseOrder responseCourseOrder = new ResponseCourseOrder();
        responseCourseOrder.setBackCourseOrderInfos(orderInfoList);
        //预约状态:1已预约，2已签到，3爽约，4取消;null:所有记录
        String orderTotal = courseDao.getEmpOrderSumByEmpId(phoneNum,businessId,null);

        String orderTimes =  courseDao.getEmpOrderSumByEmpId(phoneNum,businessId,"1");
        String signTimes = courseDao.getEmpOrderSumByEmpId(phoneNum,businessId,"2");
        String breakOrderTimes = courseDao.getEmpOrderSumByEmpId(phoneNum,businessId,"3");
        responseCourseOrder.setOrderTotal(orderTotal);
        responseCourseOrder.setOrderTimes(orderTimes);
        responseCourseOrder.setSignTimes(signTimes);
        responseCourseOrder.setBreakOrderTimes(breakOrderTimes);
        return responseCourseOrder;
    }

    /**
     * 根据预约状态查询记录
     * @param phoneNum
     * @param businessId
     * @param orderStatus
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public List<BackCourseOrderInfo> getMemberAllOrderByOrderStatus(String phoneNum, String businessId, String orderStatus, Integer page,Integer pageSize) {
        BatchDataPage batchDataPage = new BatchDataPage();
        batchDataPage.setPage(page);
        batchDataPage.setPagesize(pageSize);
        List<BackCourseOrderInfo> orderInfoList = courseDao.listMemberAllOrderByPhoneNum(phoneNum, orderStatus,batchDataPage.getOffset(), batchDataPage.getPagesize() );
        if (orderInfoList !=null && orderInfoList.size()>0){
            for (BackCourseOrderInfo bc : orderInfoList){
                if (bc.getCourseMeans().equals("2")){
                    bc.setOrderedPerson(courseDao.getCourseOrderCount(bc.getCoursesignId()));
                }
            }
        }
        return orderInfoList;
    }

    /**
     * 获取会员预约团课详情
     * @param courseplanId 团课排课计划id
     * @param coursesignId 团课签到id
     * @param memberId 会员id
     * @return
     */
    @Override
    public BaseCourseOrderInfo getPublicCourseOrderByPlanId(String courseplanId, String coursesignId, String memberId) {
        return courseDao.getPublicCourseOrderByPlanId(courseplanId, coursesignId, memberId);
    }

    /**
     * 获取会员预约私教详情
     * @param courseplanId 团课排课计划id
     * @param classDate 开课日期
     * @param classTime 开课时间
     * @param memberId 会员id
     * @return
     */
    @Override
    public BaseCourseOrderInfo getPrivateCourseOrderByPlanId(String courseplanId,String memberId, String classDate, String classTime) {
        BaseCourseOrderInfo baseCourseOrderInfo = new BaseCourseOrderInfo();
        baseCourseOrderInfo.setCourseplanId(courseplanId);
        baseCourseOrderInfo.setMemberId(memberId);
        baseCourseOrderInfo.setClassDate(classDate);
        baseCourseOrderInfo.setClassTime(classTime);
        return courseDao.getPrivateCourseOrderByPlanId(baseCourseOrderInfo);
    }
}
