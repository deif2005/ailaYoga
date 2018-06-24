package com.dod.sport.service.impl;

import com.dod.sport.config.Configuration;
import com.dod.sport.constant.SysConfigConstants;
import com.dod.sport.dao.ISystemBaseDao;
import com.dod.sport.dao.IUserDao;
import com.dod.sport.domain.common.BatchDataPage;
import com.dod.sport.domain.common.BusiException;
import com.dod.sport.domain.po.Base.*;
import com.dod.sport.domain.po.Member.HistoryCard;
import com.dod.sport.domain.po.Member.MembercardRelation;
import com.dod.sport.domain.po.Member.QueryMember;
import com.dod.sport.domain.po.Response.ResponseMember;
import com.dod.sport.service.ISystemBaseService;
import com.dod.sport.util.BusiUtils;
import com.dod.sport.util.CommonEnum;
import com.dod.sport.util.RedisCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by defi on 2017-09-21.
 */
@Service
public class SystemBaseServiceImpl implements ISystemBaseService {

    @Autowired
    ISystemBaseDao systemBaseDao;

    @Autowired
    IUserDao userDao;

    /**
     * 获取职位信息列表
     * @return
     */
    @Override
    public List<Position> listPositionInfo(Integer page){
        BatchDataPage batchDataPage = new BatchDataPage();
        batchDataPage.setPage(page);
        List<Position> positionList = systemBaseDao.listPositionInfo(batchDataPage.getOffset(),batchDataPage.getPagesize());
        return positionList;
    }

    /**
     * 获取职位信息
     * @return
     */
    @Override
    public Position getPositionInfo(String id){
        Position position = systemBaseDao.getPositionInfo(id);
        return position;
    }

    /**
     * 添加职位信息
     * @param position
     */
    @Override
    public void addPosition(Position position){
        String maxId = RedisCommon.getMaxPositionId();
        if ("".equals(maxId)){
            maxId = RedisCommon.setAndReturnMaxPositionId(systemBaseDao.getMaxPositionId());
        }
        position.setId(UUID.randomUUID().toString());
        position.setPositionSerialId(maxId);
        systemBaseDao.addPosition(position);
    }

    /**
     * 获取商家职位信息列表
     * @return
     */
    @Override
    public List<Position> listBusiPositionInfo(String businessId, Integer page){
        BatchDataPage batchDataPage = new BatchDataPage();
        batchDataPage.setPage(page);
        List<Position> positionList = systemBaseDao.listBusiPositionInfo(businessId,batchDataPage.getOffset(),
                batchDataPage.getPagesize());
        return positionList;
    }

    /**
     * 获取商家职位信息
     * @return
     */
    @Override
    public Position getBusiPositionInfo(String id){
        Position position = systemBaseDao.getBusiPositionInfo(id);
        return position;
    }

    /**
     * 添加商家职位信息
     * @param position
     */
    @Override
    public void addBusiPosition(Position position){
        String maxId = RedisCommon.getMaxPositionId();
        if ("".equals(maxId)){
            maxId = RedisCommon.setAndReturnMaxPositionId(systemBaseDao.getMaxPositionId());
        }
        position.setId(UUID.randomUUID().toString());
        position.setPositionSerialId(maxId);
        List<Position> positionList = new ArrayList<>();
        positionList.add(position);
        systemBaseDao.addBusiPosition(positionList);
    }

    /**
     * 添加职位信息
     * @param businessId
     * @param positionIdStr
     * @param positionNameStr
     * @param creator
     */
    @Override
    public void addBatchBusiPosition(String businessId, String positionIdStr, String positionNameStr, String creator){
        String[] aPositionId = positionIdStr.split(",");
        String[] aPositionName = positionNameStr.split(",");
        List<String> positions = Arrays.asList(aPositionId);
        List<Position> positionList = new ArrayList<>();
        int i = 0;
        for (String psId:positions){
            Position position2 = systemBaseDao.getBusinessPositionByPlatformPositionId(businessId,psId);
            if (position2!=null){
                position2.setId(null);
                position2.setPlatformPositionId(psId);
                position2.setStatus(CommonEnum.DBData.DataStatus.normal.getValue());
                systemBaseDao.updateBusiPositionStatus(position2);
                i++;
                continue;
            }
            String maxId = RedisCommon.getMaxPositionId();
            if ("".equals(maxId)){
                maxId = RedisCommon.setAndReturnMaxPositionId(systemBaseDao.getMaxPositionId());
            }
            Position position = new Position();
            position.setId(UUID.randomUUID().toString());
            position.setBusinessId(businessId);
            position.setPositionSerialId(maxId);
            position.setPlatformPositionId(psId);
            position.setPositionName(aPositionName[i]);
            position.setCreator(creator);
            positionList.add(position);
            i++;
        }
        if (positionList.size()>0) {
            systemBaseDao.addBusiPosition(positionList);
        }
    }

    /**
     * 新增部门信息
     * @param department
     */
    @Override
    public void addDepartmentInfo(Department department){
        String maxId = RedisCommon.getMaxDepartmentId();
        if ("".equals(maxId)){
            maxId = RedisCommon.setAndReturnMaxDepartmentId(systemBaseDao.getMaxDepartmentId());
        }
        department.setId(UUID.randomUUID().toString());
        department.setDepSerialId(maxId);
        systemBaseDao.addDepartmentInfo(department);
    }

    /**
     * 获取部门信息列表
     * @return
     */
    @Override
    public List<Department> listDepartment(Integer page){
        BatchDataPage batchDataPage = new BatchDataPage();
        batchDataPage.setPage(page);
        List<Department> departmentList = systemBaseDao.listDepartmentInfo(batchDataPage.getOffset(),batchDataPage.getPagesize());
        return departmentList;
    }

    /**
     * 获取部门信息
     * @param id
     * @return
     */
    @Override
    public Department getDepartmentById(String id){
        Department department = systemBaseDao.getDepartmentInfoById(id);
        return department;
    }
    /**
     * 新增门店信息
     * @param storeInfo
     */
    @Override
    public void addStoreInfo(StoreInfo storeInfo){
        storeInfo.setId(UUID.randomUUID().toString());
        String maxId = RedisCommon.getMaxStoreId(storeInfo.getBusinessId());
        if ("".equals(maxId)){
            maxId = RedisCommon.setAndReturnMaxStoreId(storeInfo.getBusinessId(),
                    systemBaseDao.getMaxStoreId(storeInfo.getBusinessId()));
        }
        storeInfo.setStoreSerialId(maxId);
        systemBaseDao.addStoreInfo(storeInfo);
    }

    /**
     * 新增商家部门信息
     * @param department
     */
    @Override
    public void addBusiDepartmentInfo(Department department){
        String maxId = RedisCommon.getMaxDepartmentId();
        if ("".equals(maxId)){
            maxId = RedisCommon.setAndReturnMaxDepartmentId(systemBaseDao.getMaxBusiDepartmentId(department.getBusinessId()));
        }
        department.setId(UUID.randomUUID().toString());
        department.setDepSerialId(maxId);
        List<Department> departmentList = new ArrayList<>();
        departmentList.add(department);
        systemBaseDao.addBusiDepartmentInfo(departmentList);
    }

    /**
     * 新增商家部门信息
     * @param businessId
     * @param departmentIdStr
     * @param creator
     */
    @Override
    public void addBatchBusiDepartmentInfo(String businessId, String departmentIdStr, String departmentNameStr,
                                           String creator){
        String[] departmentId = departmentIdStr.split(",");
        String[] departmentName =departmentNameStr.split(",");
        List<String> depIdList = Arrays.asList(departmentId);
        List<Department> departmentList = new ArrayList<>();
        int i = 0;
        for (String depId:depIdList){
            Department department2 = systemBaseDao.getBusinessDepartmentByPlatformDepId(businessId,depId);
            if (department2!=null){
                department2.setId(null);
                department2.setPlatformDepId(depId);
                department2.setStatus(CommonEnum.DBData.DataStatus.normal.getValue());
                systemBaseDao.updateBusiDepartmentStatus(department2);
                i++;
                continue;
            }
            String maxId = RedisCommon.getMaxDepartmentId();
            if ("".equals(maxId)){
                maxId = RedisCommon.setAndReturnMaxDepartmentId(systemBaseDao.getMaxBusiDepartmentId(businessId));
            }
            Department department = new Department();
            department.setId(UUID.randomUUID().toString());
            department.setBusinessId(businessId);
            department.setDepSerialId(maxId);
            department.setPlatformDepId(depId);
            department.setDepName(departmentName[i]);
            department.setCreator(creator);
            departmentList.add(department);
            i++;
        }
        if (departmentList.size()>0) {
            systemBaseDao.addBusiDepartmentInfo(departmentList);
        }
    }

    /**
     * 获取商家部门信息列表
     * @return
     */
    @Override
    public List<Department> listBusiDepartment(String businessId, Integer page){
        BatchDataPage batchDataPage = new BatchDataPage();
        batchDataPage.setPage(page);
        List<Department> departmentList = systemBaseDao.listBusiDepartmentInfo(businessId,batchDataPage.getOffset(),
                batchDataPage.getPagesize());
        return departmentList;
    }

    /**
     * 获取商家部门信息
     * @param id
     * @return
     */
    @Override
    public Department getBusiDepartmentById(String id){
        Department department = systemBaseDao.getBusiDepartmentInfoById(id);
        return department;
    }

    /**
     * 获取商家信息列表
     * @param businessType
     * @param page
     * @return
     */
    @Override
    public List<BusinessInfo> listBusinessInfo(Integer businessType, Integer page){
        String iconPath = "";
        String picturePath = "";
        String idcardPictureA = "";
        String idcardPictureB = "";
        BatchDataPage batchDataPage = new BatchDataPage();
        batchDataPage.setPage(page);

        List<BusinessInfo> businessInfoList = systemBaseDao.listBusinessInfo(businessType,batchDataPage.getOffset(),
                batchDataPage.getPagesize());
        //拼接商家logo和背景图地址
        for (BusinessInfo businessInfo:businessInfoList){
            iconPath = ("".equals(businessInfo.getLogo()) || businessInfo.getLogo() == null) ? "" :
                    Configuration.getStaticShowPath() + businessInfo.getLogo();
            picturePath = ("".equals(businessInfo.getBusinessPicture()) || businessInfo.getBusinessPicture() == null) ? "" :
                    Configuration.getStaticShowPath() + businessInfo.getBusinessPicture();
            idcardPictureA = ("".equals(businessInfo.getIdcardPictureA()) || businessInfo.getIdcardPictureA() == null) ? "" :
                    Configuration.getStaticShowPath() + businessInfo.getIdcardPictureA();
            idcardPictureB = ("".equals(businessInfo.getIdcardPictureB()) || businessInfo.getIdcardPictureB() == null) ? "" :
                    Configuration.getStaticShowPath() + businessInfo.getIdcardPictureB();
            businessInfo.setLogo(iconPath);
            businessInfo.setBusinessPicture(picturePath);
            businessInfo.setIdcardPictureA(idcardPictureA);
            businessInfo.setIdcardPictureB(idcardPictureB);
        }
        return businessInfoList;
    }

    /**
     * 获取商家门店列表
     * @param businessId
     * @param page
     * @return
     */
    @Override
    public List<StoreInfo> listStoreInfo(String businessId, Integer page){
        StringBuffer doorPicture = new StringBuffer();
        StringBuffer classroomPicture = new StringBuffer();
        StringBuffer receptionPicture = new StringBuffer();

        BatchDataPage batchDataPage = new BatchDataPage();
        batchDataPage.setPage(page);

        List<StoreInfo> storeInfoList = systemBaseDao.listStoreInfo(businessId, batchDataPage.getOffset(),
                batchDataPage.getPagesize());

        for (StoreInfo storeInfo:storeInfoList){
            if (storeInfo.getDoorPicture() != null && !"".equals(storeInfo.getDoorPicture())){
                String[] doorArray = storeInfo.getDoorPicture().split(";");
                for (int i=0;i<doorArray.length;i++){
                    doorPicture.append(";").append(Configuration.getStaticShowPath() + doorArray[i]);
                }
                storeInfo.setDoorPicture(doorPicture.toString().substring(1));
            }
            if (storeInfo.getClassroomPicture() != null && !"".equals(storeInfo.getClassroomPicture())){
                String[] classRoomArray = storeInfo.getClassroomPicture().split(";");
                for (int i=0;i<classRoomArray.length;i++){
                    classroomPicture.append(";").append(Configuration.getStaticShowPath() + classRoomArray[i]);
                }
                storeInfo.setClassroomPicture(classroomPicture.toString().substring(1));
            }
            if (storeInfo.getReceptionPicture() != null && !"".equals(storeInfo.getReceptionPicture())){
                String[] receptionArray = storeInfo.getReceptionPicture().split(";");
                for (int i=0;i<receptionArray.length;i++){
                    receptionPicture.append(";").append(Configuration.getStaticShowPath() + receptionArray[i]);
                }
                storeInfo.setReceptionPicture(receptionPicture.toString().substring(1));
            }
            if (storeInfo.getLicensePicture() != null && !"".equals(storeInfo.getLicensePicture())){
                storeInfo.setLicensePicture(Configuration.getStaticShowPath()+storeInfo.getLicensePicture());
            }
        }
        return storeInfoList;
    }

    /**
     * 获取商家信息
     * @param id
     * @return
     */
    public BusinessInfo getBusinessInfoById(String id){
        String iconPath = "";
        String picturePath = "";
        BusinessInfo businessInfo = systemBaseDao.getBusinessInfoById(id);
        //拼接商家logo和背景图地址
        iconPath = ("".equals(businessInfo.getLogo()) || businessInfo.getLogo() == null) ? "" :
                Configuration.getStaticShowPath() + businessInfo.getLogo();
        picturePath = ("".equals(businessInfo.getBusinessPicture()) || businessInfo.getBusinessPicture() == null) ? "" :
                Configuration.getStaticShowPath() + businessInfo.getBusinessPicture();
        businessInfo.setLogo(iconPath);
        businessInfo.setBusinessPicture(picturePath);
        return businessInfo;
    }

    /**
     * 新增商家信息
     * @param businessInfo
     */
    public void addBusinessInfo(BusinessInfo businessInfo){
        String maxId = RedisCommon.getMaxBusinessId(businessInfo.getBusinessType());
        if ("".equals(maxId)){
            maxId = RedisCommon.setAndReturnMaxBusinessId(businessInfo.getBusinessType(),
                    systemBaseDao.getMaxBusinessId());
        }
        businessInfo.setBusinessSerialId(maxId);
        systemBaseDao.addBusinessInfo(businessInfo);
    }

    /**
     * 新增教室
     * @param classroom
     */
    @Override
    public void addClassroom(Classroom classroom) {
        String maxId = RedisCommon.getMaxClassroomId();
        if ("".equals(maxId)){
            maxId = RedisCommon.setAndReturnMaxClassroomId(systemBaseDao.getMaxClassroomId(classroom.getStoreId()));
        }
        classroom.setId(UUID.randomUUID().toString());
        classroom.setClassroomSerialId(maxId);
        systemBaseDao.addClassroom(classroom);
    }

    /**
     **
     * 新增门店照片
     * @param
     * @param
     */
    @Override
    public String addStorePhotoInfo(StorePhoto storePhoto){
        storePhoto.setId(UUID.randomUUID().toString());
        systemBaseDao.addStorePhotoInfo(storePhoto);
        return storePhoto.getId();
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
                    systemBaseDao.getMaxCourseTypeId(courseType.getBusinessId()));
        }
        courseType.setId(UUID.randomUUID().toString());
        courseType.setCoursetypeSerialId(maxId);
        systemBaseDao.addCourseType(courseType);
    }

    /**
     * 获取课程类型列表
     * @param businessId
     * @param page
     * @return
     */
    public List<CourseType> listCourseType(String businessId,Integer page){
        BatchDataPage batchDataPage = new BatchDataPage();
        batchDataPage.setPage(page);
        List<CourseType> courseTypeList = systemBaseDao.listCourseType(businessId,batchDataPage.getOffset(),
                batchDataPage.getPagesize());
        return courseTypeList;
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
        return systemBaseDao.listClassroomByStoreId(storeId, batchDataPage.getOffset(),
                batchDataPage.getPagesize());
    }

    /**
     * 新增会员信息
     * @param businessMember
     */
    @Override
    @Transactional
    public void addMemberInfoAndCardInfo(BusinessMember businessMember,List<HistoryCard> historyCardList){
        String userId =null;
        ResponseMember resp = systemBaseDao.queryMemberInfo(businessMember);
        if (resp!=null){ //提示该会员已存在
            throw new BusiException(CommonEnum.ReturnCode.ManagerCode.manager_err_memberexists.getValue());
        }
        //新增平台用户信息
        ClientUser queryUser = userDao.getClientUserInfo(businessMember);
        if (queryUser ==null){//如果平台中不存在该用户
            ClientUser clientUser = new ClientUser();
            userId = UUID.randomUUID().toString();
            clientUser.setId(userId);
            String userSerialId = RedisCommon.getMaxClientUserId();
            if(userSerialId == ""){
                String maxClientUserId = userDao.getMaxClientUserId();
                userSerialId = RedisCommon.setAndReturnMaxClientUserId(maxClientUserId);
            }
            clientUser.setUserSerialId(userSerialId);
            clientUser.setUserName(businessMember.getUserName());
            clientUser.setNickName(businessMember.getNickName());
            clientUser.setBirthday(businessMember.getBirthday());
            clientUser.setSex(businessMember.getSex());
            clientUser.setPhoneNum(businessMember.getPhoneNum());
            userDao.addClientUser(clientUser);
        }else {
            userId = queryUser.getId();
        }
        //新增商家会员信息
        String memberId = UUID.randomUUID().toString();
        businessMember.setMemberId(memberId);
        businessMember.setUserId(userId);
        String maxId = RedisCommon.getMaxMemberCardId(businessMember.getBusinessId());
        if ("".equals(maxId)){
            maxId = RedisCommon.setAndReturnMaxMemberId(businessMember.getBusinessId(),
                    systemBaseDao.getMaxMemberId(businessMember.getBusinessId()));
        }
        businessMember.setMemberSerialId(maxId);
        systemBaseDao.addMemberInfo(businessMember);
        //关联会员卡
        if (historyCardList.size() > 0){
            addHistoryMemberCardRelation(historyCardList,businessMember);
        }
    }

    /**
     * 编辑会员信息
     * @param businessMember
     * @param updateHistoryCardList
     * @param delCardList
     */
    @Override
    @Transactional
    public void updateMemberInfoAndCardInfo(BusinessMember businessMember, List<HistoryCard> addHistoryCardList,
                                            List<HistoryCard> updateHistoryCardList, List<String> delCardList){
        ClientUser clientUser = new ClientUser();
        clientUser.setId(businessMember.getUserId());
        clientUser.setUserName(businessMember.getUserName());
        clientUser.setNickName(businessMember.getNickName());
        clientUser.setBirthday(businessMember.getBirthday());
        clientUser.setSex(businessMember.getSex());
        clientUser.setPhoneNum(businessMember.getPhoneNum());
        userDao.updateClientUser(clientUser);
        systemBaseDao.updateBusinessMeberInfo(businessMember);
        if (addHistoryCardList.size() > 0){
            addHistoryMemberCardRelation(addHistoryCardList,businessMember);
        }
        if (updateHistoryCardList.size() > 0){
            updateHistoryMemberCard(updateHistoryCardList);
        }
        if (delCardList.size() > 0){
            delHistoryMemberCard(delCardList);
        }
    }

    /**
     * 新增历史会员卡信息
     * @param historyCardList
     */
    @Override
    public void addHistoryMemberCardRelation(List<HistoryCard> historyCardList,BusinessMember businessMember){
        List<MembercardRelation> membercardRelationList = new ArrayList<>();
        for (HistoryCard historyCard:historyCardList){
            MembercardRelation membercardRelation = new MembercardRelation();
            String id = UUID.randomUUID().toString();
            membercardRelation.setId(id);
            membercardRelation.setMemberId(businessMember.getMemberId());
            membercardRelation.setStoreId(businessMember.getStoreId());
            membercardRelation.setCreator(businessMember.getCreator());
            //卡流水号设置
            String maxId = RedisCommon.getMaxMemberCardNum(historyCard.getMembcardId());
            if ("".equals(maxId)){
                maxId = RedisCommon.setAndReturnMaxMemberCardNum(historyCard.getMembcardId(),
                        systemBaseDao.getMaxMembercardOpencardSerialId(historyCard.getMembcardId()));
            }
            membercardRelation.setOpencardSerialId(maxId);
            membercardRelation.setCardStatus(historyCard.getCardStatus());
            membercardRelation.setMembcardId(historyCard.getMembcardId());
            if (historyCard.getTimes() != null){
                membercardRelation.setTimes(historyCard.getTimes());
            }
            if (historyCard.getValidityType() != null){
                membercardRelation.setValidityType(historyCard.getValidityType());
            }
            if (historyCard.getValidityTime() != null){
                membercardRelation.setValidityTime(historyCard.getValidityTime());
            }
            membercardRelationList.add(membercardRelation);
        }
        systemBaseDao.addMemberCardRelation(membercardRelationList);
    }

    /**
     * 更新历史卡信息
     * @param historyCardList
     */
    @Override
    public void updateHistoryMemberCard(List<HistoryCard> historyCardList){
        for (HistoryCard historyCard:historyCardList){
            MembercardRelation membercardRelation = new MembercardRelation();
            membercardRelation.setId(historyCard.getId());
            membercardRelation.setCardStatus(historyCard.getCardStatus());
            membercardRelation.setTimes(historyCard.getTimes());
            membercardRelation.setValidityType(historyCard.getValidityType());
            membercardRelation.setValidityTime(historyCard.getValidityTime());
            systemBaseDao.updateMemberCardRelation(membercardRelation);
        }
    }

    /**
     * 删除历史卡信息
     * @param delCardList
     */
    @Override
    public void delHistoryMemberCard(List<String> delCardList){
        for (String cardId:delCardList){
            systemBaseDao.deleteMemberCardRelation(cardId);
        }
    }

    /**
     * 获取门店会员信息列表
     * @param storeId
     * @param page
     */
    public List<ResponseMember> listMemberByStoreId(String storeId,Integer page){
        BatchDataPage batchDataPage = new BatchDataPage();
        batchDataPage.setPage(page);
        return systemBaseDao.queryMemberListByStoreId(storeId, batchDataPage.getOffset(),
                batchDataPage.getPagesize());
    }

    /**
     * 删除商家会员信息
     * @param id
     */
    @Override
    @Transactional
    public void delBusinessMemberInfo(String id, String businessId){
        List<MembercardRelation> membercardRelationList = new ArrayList<>();
        systemBaseDao.deleteBusinessMemberInfo(id);
        membercardRelationList = systemBaseDao.listMemberCardByMemberId(id, businessId);
        for (MembercardRelation membercardRelation: membercardRelationList){
            systemBaseDao.deleteMemberCardRelation(membercardRelation.getId());
        }
    }

    /**
     * 获取客户端用户列表
     * @param page
     * @return
     */
    @Override
    public List<ClientUser> listClientUser(Integer page){
        BatchDataPage batchDataPage = new BatchDataPage();
        batchDataPage.setPage(page);
        List<ClientUser> clientUserList = userDao.listClientUser(batchDataPage.getOffset(),
                batchDataPage.getPagesize());
        return clientUserList;
    }

}
