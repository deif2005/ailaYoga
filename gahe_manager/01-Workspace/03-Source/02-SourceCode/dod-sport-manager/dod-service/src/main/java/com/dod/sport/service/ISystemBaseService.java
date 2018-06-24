package com.dod.sport.service;

import com.dod.sport.domain.po.Base.*;
import com.dod.sport.domain.po.Bill.EntryBill;
import com.dod.sport.domain.po.Member.HistoryCard;
import com.dod.sport.domain.po.Member.MembercardRelation;
import com.dod.sport.domain.po.Member.QueryMember;
import com.dod.sport.domain.po.Response.ResponseMember;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by defi on 2017-09-21.
 */
public interface ISystemBaseService {

    /**
     * 获取职位信息列表
     * @return
     */
    public List<Position> listPositionInfo(Integer page);

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
     * 获取职位信息列表
     * @return
     */
    public List<Position> listBusiPositionInfo(String businessId, Integer page);

    /**
     * 获取职位信息
     * @return
     */
    public Position getBusiPositionInfo(String id);

    /**
     * 添加职位信息
     * @param position
     */
    public void addBusiPosition(Position position);

    /**
     * 添加职位信息
     * @param businessId
     * @param positionIdStr
     * @param positionNameStr
     * @param creator
     */
    public void addBatchBusiPosition(String businessId, String positionIdStr, String positionNameStr, String creator);

    /**
     * 新增部门信息
     * @param department
     */
    public void addDepartmentInfo(Department department);

    /**
     * 新增商家部门信息
     * @param businessId
     * @param departmentIdStr
     * @param creator
     */
    public void addBatchBusiDepartmentInfo(String businessId, String departmentIdStr, String departmentNameStr,
                                           String creator);

    /**
     * 获取部门信息列表
     * @return
     */
    public List<Department> listDepartment(Integer page);


    /**
     * 新增商家部门信息
     * @param department
     */
    public void addBusiDepartmentInfo(Department department);

    /**
     * 获取商家部门信息列表
     * @return
     */
    public List<Department> listBusiDepartment(String businessId, Integer page);

    /**
     * 获取商家部门信息
     * @param id
     * @return
     */
    public Department getBusiDepartmentById(String id);

    /**
     * 获取部门信息
     * @param id
     * @return
     */
    public Department getDepartmentById(String id);

    /**
     * 新增商家信息
     * @param businessInfo
     */
    public void addBusinessInfo(BusinessInfo businessInfo);

    /**
     * 获取商家信息列表
     * @return
     */
    public List<BusinessInfo> listBusinessInfo(Integer businessType, Integer page);

    /**
     * 获取商家门店列表
     * @param businessId
     * @param page
     * @return
     */
    public List<StoreInfo> listStoreInfo(String businessId, Integer page);

    /**
     * 获取商家信息
     * @param id
     * @return
     */
    public BusinessInfo getBusinessInfoById(String id);

    /**
     * 新增教室
     * @param classroom
     */
    public void addClassroom(Classroom classroom);

    /**
     * 新增门店信息
     * @param storeInfo
     */
    public void addStoreInfo(StoreInfo storeInfo);

    /**
     **
     * 新增门店照片
     * @param
     */
    public String addStorePhotoInfo(StorePhoto storePhoto);

    /**
     * 新增课程类型信息
     * @param courseType
     */
    public void addCourseType(CourseType courseType);

    /**
     * 获取课程类型列表
     * @param businessId
     * @param page
     * @return
     */
    public List<CourseType> listCourseType(String businessId,Integer page);

    /**
     *查询门店中所有教室
     * @param storeId
     * @param page
     * @return
     */
    public List<Classroom> listClassroomByStoreId(String storeId, Integer page);

    /**
     * 新增会员及会员卡信息
     * @param businessMember
     * @param historyCardList
     */
    public void addMemberInfoAndCardInfo(BusinessMember businessMember,List<HistoryCard> historyCardList);


    /**
     * 新增历史会员卡信息
     * @param historyCardList
     */
    public void addHistoryMemberCardRelation(List<HistoryCard> historyCardList,BusinessMember businessMember);

    /**
     * 获取门店会员信息列表
     * @param storeId
     * @param page
     */
    public List<ResponseMember> listMemberByStoreId(String storeId,Integer page);

    /**
     * 新增会员信息
     * @param addHistoryCardList
     * @param updateHistoryCardList
     * @param businessMember
     */
    public void updateMemberInfoAndCardInfo(BusinessMember businessMember, List<HistoryCard> addHistoryCardList,
                                            List<HistoryCard> updateHistoryCardList, List<String> delCardList);

    /**
     * 更新历史卡信息
     * @param historyCardList
     */
    public void updateHistoryMemberCard(List<HistoryCard> historyCardList);

    /**
     * 删除历史卡信息
     * @param delCardList
     */
    public void delHistoryMemberCard(List<String> delCardList);

    /**
     * 删除商家会员信息
     * @param id
     */
    public void delBusinessMemberInfo(String id,String businessId);

    /**
     * 获取客户端用户列表
     * @param page
     * @return
     */
    public List<ClientUser> listClientUser(Integer page);

}
