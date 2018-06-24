package com.dod.sport.dao;

import com.dod.sport.domain.po.Base.*;
import com.dod.sport.domain.po.Bill.EntryBill;
import com.dod.sport.domain.po.Business.BusiEmployee;
import com.dod.sport.domain.po.Member.MemberCard;
import com.dod.sport.domain.po.Member.MembercardRelation;
import com.dod.sport.domain.po.Member.QueryMember;
import com.dod.sport.domain.po.Response.ResponseEmployee;
import com.dod.sport.domain.po.Response.ResponseMember;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by defi on 2017-09-21.
 */
public interface ISystemBaseDao {

    /**
     * 获取职位信息列表
     * @return
     */
    public List<Position> listPositionInfo(@Param("startPage") Integer startPage,
                                           @Param("endPage") Integer endPage);

    /**
     * 获取平台职位记录笔数
     * @return
     */
    public String getPositionCount();

    /**
     * 获取职位信息
     * @return
     */
    public Position getPositionInfo(String id);

    /**
     * 添加职位信息
     * @param position
     */
    public void addPosition(Position position);

    /**
     * 获取商家职位信息列表
     * @return
     */
    public List<Position> listBusiPositionInfo(@Param("businessId") String businessId,
                                               @Param("startPage") Integer startPage,
                                               @Param("endPage") Integer endPage);

    /**
     * 获取商家职位记录笔数
     * @param businessId
     * @return
     */
    public String getBusiPositionCount(String businessId);

    /**
     * 获取商家职位信息
     * @return
     */
    public Position getBusiPositionInfo(String id);

    /**
     * 添加商家职位信息
     * @param position
     */
    public void addBusiPosition(List<Position> position);

    /**
     * 获取最大职位信息
     * @return
     */
    public String getMaxPositionId();

    /**
     * 获取该职位信息是否已经添加过
     * @param platformPositionId
     * @return
     */
    public Position getBusinessPositionByPlatformPositionId(@Param("businessId") String businessId,
                                                            @Param("platformPositionId") String platformPositionId);

    /**
     * 获取部门信息列表
     * @return
     */
    public List<Department> listDepartmentInfo(@Param("startPage") Integer startPage,
                                               @Param("endPage") Integer endPage);

    /**
     * 获取平台所有部门
     * @return
     */
    public List<Department> listAllDepartmentInfo();

    /**
     * 增加部门信息
     * @param department
     */
    public void addDepartmentInfo(Department department);

    /**
     * 获取部门信息
     * @param id
     * @return
     */
    public Department getDepartmentInfoById(String id);

    /**
     * 获取商家部门信息列表
     * @return
     */
    public List<Department> listBusiDepartmentInfo(@Param("businessId") String businessId,
                                                   @Param("startPage") Integer startPage,
                                                   @Param("endPage") Integer endPage);

    /**
     * 获取平台部门记录笔数
     * @return
     */
    public String getDepartmentCount();

    /**
     * 获取商家部门记录笔数
     * @return
     */
    public String getBusinessDepartmentCount(String businessId);

    /**
     * 获取商家所有部门信息
     * @param businessId
     * @return
     */
    public List<Department> listAllBusiDepartmentInfo(@Param("businessId") String businessId);

    /**
     * 增加商家部门信息
     * @param departmentList
     */
    public void addBusiDepartmentInfo(List<Department> departmentList);

    /**
     * 获取商家部门信息
     * @param id
     * @return
     */
    public Department getBusiDepartmentInfoById(String id);

    /**
     * 获取商家的部门列表
     * @param businessId
     * @return
     */
    public List<Department>getBusiDepartmentListByBusinessId(String businessId);

    /**
     * 更新平台部门信息
     * @param department
     */
    public void updateDepartment(Department department);

    /**
     * 更新平台部门数据状态
     * @param department
     */
    public void updateDepartmentStatus(Department department);

    /**
     * 更新商家部门信息
     * @param department
     */
    public void updateBusiDepartment(Department department);

    /**
     * 更新商家部门数据状态
     * @param department
     */
    public void updateBusiDepartmentStatus(Department department);

    /**
     * 根据平台部门id获取商家部门信息
     * @param platformDepId
     * @param businessId
     * @return
     */
    public Department getBusinessDepartmentByPlatformDepId(@Param("businessId") String businessId,
                                                           @Param("platformDepId") String platformDepId);

    /**
     * 获取部门最大编号
     * @return
     */
    public String getMaxDepartmentId();

    /**
     * 获取商家部门最大编号
     * @return
     */
    public String getMaxBusiDepartmentId(String businessId);

    /**
     * 获取商家信息列表
     * @return
     *
     */
    public List<BusinessInfo> listBusinessInfo(@Param("businessType") Integer businessType,
                                               @Param("startPage") Integer startPage,
                                               @Param("endPage") Integer endPage);

    /**
     * 获取商家门店列表
     * @param businessId
     * @param startPage
     * @param endPage
     * @return
     */
    public List<StoreInfo> listStoreInfo(@Param("businessId") String businessId,
                                         @Param("startPage") Integer startPage,
                                         @Param("endPage") Integer endPage);

    /**
     * 获取商家记录笔数
     * @param businessType
     * @return
     */
    public String getBusinessInfoCount(@Param("businessType") Integer businessType);

    /**
     * 获取门店记录笔数
     * @param businessId
     * @return
     */
    public String getStoreInfoCount(@Param("businessId") String businessId);

    /**
     * 增加商家信息
     * @param businessInfo
     */
    public void addBusinessInfo(BusinessInfo businessInfo);

    /**
     * 获取商家信息
     * @param id
     * @return
     */
    public BusinessInfo getBusinessInfoById(String id);

    /**
     * 获取商家最大编号
     * @return
     */
    public String getMaxBusinessId();

    /**
     * 推荐门店信息
     * @return
     */
    public void addStoreInfo(StoreInfo storeInfo);

    /**
     * 获取商家最大门店编号
     * @param businessId
     * @return
     */
    public String getMaxStoreId(String businessId);

    /**
     * 新增教室
     * @param classroom
     */
    public void addClassroom(Classroom classroom);

    /**
     * 获取门店最大教室编号
     * @param storeId
     * @return
     */
    public String getMaxClassroomId(String storeId);

    /**
     **
     * 新增门店照片
     * @param
     * @param
     */
    public void addStorePhotoInfo(StorePhoto storePhoto);

    /**
     * 新增员工
     * @param em
     */
    public void addEmployee(BusiEmployee em);

    /**
     * 获取员工最大的员工编号
     * @return
     */
    public String queryEmployeeIdMax(String businessId);

    /**
     * 获取所有商家信息
     * @param businessType
     * @return
     */
    public List<BusinessInfo> getAllBusinessInfo(@Param("businessType") Integer businessType);

    /**
     * 获取所有商家所有门店信息
     * @param businessId
     * @return
     */
    public List<StoreInfo> getAllStoreInfo(String businessId);

    /**
     * 更新平台职位数据状态
     * @param position
     */
    public void updatePositionStatus(Position position);

    /**
     * 更新商家职位数据状态
     * @param position
     */
    public void updateBusiPositionStatus(Position position);

    /**
     * 更新商家职位信息
     * @param position
     */
    public void updateBusiPositionInfo(Position position);

    /**
     * 获取商家所有职位信息
     * @param businessId
     * @return
     */
    public List<Position> getAllBusiPositionInfo(String businessId);

    /**
     * 获取平台所有职位信息
     * @return
     */
    public List<Position> getAllPositionInfo();

    /**
     * 新增课程类型信息
     * @param courseType
     */
    public void addCourseType(CourseType courseType);

    /**
     * 获取课程类型最大编号
     * @param businessId
     * @return
     */
    public String getMaxCourseTypeId(String businessId);

    /**
     * 获取课程类型列表
     * @param businessId
     * @param startPage
     * @param endPage
     * @return
     */
    public List<CourseType> listCourseType(@Param("businessId") String businessId,
                                           @Param("startPage") Integer startPage,
                                           @Param("endPage") Integer endPage);

    /**
     * 获取商家课程类型记录笔数
     * @param businessId
     * @return
     */
    public String getCourseTypeCount(String businessId);

    /**
     * 更新课程类型数据状态
     * @param courseType
     */
    public void updateCourseTypeStatus(CourseType courseType);

    /**
     * 更新课程类型数据
     * @param courseType
     */
    public void updateCourseType(CourseType courseType);

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
     * 获取门店教室信息
     * @param storeId
     * @return
     */
    public String getClassroomCount(String storeId);

    /**
     * 更新教室信息
     * @param classroom
     */
    public void updateClassroom(Classroom classroom);

    /**
     * 新增员工基础信息
     * @param employeeInfo
     */
    public void addBaseEmployeeInfo(EmployeeInfo employeeInfo);

    /**
     * 新增员工关系表数据
     * @param em
     */
    public void addEmpBusinessRelation(EmpBusinessRelation em);

    /**
     * 根据商家编号与员工的电话号码查询员工信息
     * @param em
     * @return
     */
    public ResponseEmployee queryEmployeeInfoByStoreIdAndPhoneNum(ResponseEmployee em);

    /**
     * 获取员工基础信息
     * @param phoneNum
     * @param status
     * @return
     */
    public EmployeeInfo getEmployeeByPhoneNum(@Param("phoneNum") String phoneNum,
                                              @Param("status") String status);

    /**
     * 获取员工入职单
     * @param employeeId
     * @return
     */
    public List<EntryBill> queryentryBillByEmpId(String employeeId);

    /**
     * 新增入职单
     * @param entryBill
     */
    public void addEntryBill(EntryBill entryBill);

    /**
     * 获取门店中所有员工的信息
     * @param storeId
     * @return
     */
    public List<ResponseEmployee>listEmpBusinessRelationByStoreId(@Param("storeId") String storeId,
                                                                  @Param("startPage") Integer startPage,
                                                                  @Param("endPage") Integer endPage);

    /**
     * 根据员工关系id获取员工详情
     * @param id
     * @return
     */
    public ResponseEmployee getEmpBusinessRelationById(String id);

    /**
     * 编辑员工基本信息
     * @param emp
     */
    public void updateEmployeInfo(EmployeeInfo emp);

    /**
     * 编辑员工与商家关系信息
     * @param empBusinessRelation
     */
    public void updateEmpBusinessRelation(EmpBusinessRelation empBusinessRelation);

    /**
     * 获取门店员工数
     * @param storeId
     * @return
     */
    public String getStoreEmployeeCount(String storeId);

    /**
     * 获取基础员工最大编号
     * @return
     */
    public String getMaxBaseEmployeeSerialId();

    /**
     * 更新商家信息
     * @param businessInfo
     * @return
     */
    public void updateBusinessInfo(BusinessInfo businessInfo);

    /**
     * 更新门店信息
     * @param storeInfo
     * @return
     */
    public void updateStoreInfo(StoreInfo storeInfo);

    /**
     * 获取会员信息
     * @param businessMember
     * @return
     */
    public ResponseMember queryMemberInfo(BusinessMember businessMember);

    /**
     * 获取会员编号
     * @return
     */
    public String getMaxMemberId(String businessId);

    /**
     * 新增会员信息
     * @param businessMember
     */
    public void addMemberInfo(BusinessMember businessMember);

    /**
     * 获取会员卡的最大编号
     * @param membcardId
     * @return
     */
    public String getMaxMembercardOpencardSerialId(String membcardId);

    /**
     * 开卡
     * @param membercardRelationList
     */
    public void addMemberCardRelation(List<MembercardRelation> membercardRelationList);

    /**
     * 获取会员列表
     * @return
     */
    public List<MemberCard> listMemberCardInfo(String businessId);

    /**
     * 获取门店中所有会员信息
     * @param storeId
     * @return
     */
    public List<ResponseMember> queryMemberListByStoreId(@Param("storeId")String storeId,
                                                         @Param("startPage")Integer startPage,
                                                         @Param("endPage")Integer endPage);

    /**
     * 获取门店会员记录笔数
     * @param storeId
     * @return
     */
    public String getMemberCountByStoreId(String storeId);

    /**
     * 更新商家会员信息
     * @param businessMember
     */
    public void updateBusinessMeberInfo(BusinessMember businessMember);

    /**
     * 更新会员卡关系
     * @param membercardRelation
     */
    public void updateMemberCardRelation(MembercardRelation membercardRelation);

    /**
     * 删除会员卡关系
     * @param id
     */
    public void deleteMemberCardRelation(String id);

    /**
     * 删除商家会员信息
     * @param id
     */
    public void deleteBusinessMemberInfo(String id);

    /**
     * 删除平台用户信息
     * @param id
     */
    public void deleteClientUser(String id);

    /**
     * 获取会员在某商家的会员卡信息
     * @param id
     * @return
     */
    public List<MembercardRelation> listMemberCardByMemberId(@Param("id") String id,@Param("businessId") String businessId);

    /**
     * 获取用户关联的门店信息
     * @param id
     * @return
     */
    public List<StoreInfo> listStoreInfoByClientUserId(String id);

    /**
     * 获取员工关联的门店
     * @param id
     * @return
     */
    public List<StoreInfo> listStoreInfoByEmployeeId(String id);

    /**
     * 获取门店信息
     * @param id
     * @return
     */
    public StoreInfo getStoreInfo(String id);

    /**
     * 获取商家员工基础信息列表
     * @param employeeName
     * @param startPage
     * @param endPage
     * @return
     */
    public List<EmployeeInfo> listBusinessEmployeeInfo(@Param("employeeName") String employeeName,
                                                       @Param("startPage") Integer startPage,
                                                       @Param("endPage") Integer endPage);

    /**
     * 获取商家员工信息记录笔数
     * @return
     */
    public String getBusinessEmployeeCount();

    /**
     * 是否已存在该手机号
     * @param employeeInfo
     * @return
     */
    public EmployeeInfo isExistsEmployeePhoneNum(EmployeeInfo employeeInfo);

    /**
     * 该手机号会员是否已存在
     * @param phoneNum
     * @param id
     * @return
     */
    public String isExistsMemberPhoneNum(@Param("phoneNum") String phoneNum,
                                         @Param("id") String id);
}
