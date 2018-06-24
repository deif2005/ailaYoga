package com.dod.sport.domain.po.Base;

import java.io.Serializable;

/**
 * 门店信息
 * Created by Administrator on 2017/8/19.
 */
public class StoreInfo implements Serializable {
    private static final long serialVersionUID = -4037794717962721663L;

    private String id;
    private String storeSerialId;        //门店编号
    private String businessId;           //商家编号
    private String storeName;            //门店名称
    private String managerName;          //负责人姓名
    private String phoneNum;             //手机
    private String phoneNum2;            //座机
    private String email;                //负责人email
    private String contactWay;           //其它联系方式
    private String area;                 //地区
    private String address;              //地址
    private String doorPicture;          //门头照
    private String classroomPicture;     //教室照
    private String receptionPicture;     //前台照
    private String licensePicture;       //营业执照
    private String startTime;            //开始营业时间
    private String endTime;              //营业结束时间
    private String serviceIntroduction;  //服务介绍
    private String status;               //状态:1正常，2异常，3停用
    private String creator;
    private String createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStoreSerialId() {
        return storeSerialId;
    }

    public void setStoreSerialId(String storeSerialId) {
        this.storeSerialId = storeSerialId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPhoneNum2() {
        return phoneNum2;
    }

    public void setPhoneNum2(String phoneNum2) {
        this.phoneNum2 = phoneNum2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactWay() {
        return contactWay;
    }

    public void setContactWay(String contactWay) {
        this.contactWay = contactWay;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDoorPicture() {
        return doorPicture;
    }

    public void setDoorPicture(String doorPicture) {
        this.doorPicture = doorPicture;
    }

    public String getClassroomPicture() {
        return classroomPicture;
    }

    public void setClassroomPicture(String classroomPicture) {
        this.classroomPicture = classroomPicture;
    }

    public String getReceptionPicture() {
        return receptionPicture;
    }

    public void setReceptionPicture(String receptionPicture) {
        this.receptionPicture = receptionPicture;
    }

    public String getLicensePicture() {
        return licensePicture;
    }

    public void setLicensePicture(String licensePicture) {
        this.licensePicture = licensePicture;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getServiceIntroduction() {
        return serviceIntroduction;
    }

    public void setServiceIntroduction(String serviceIntroduction) {
        this.serviceIntroduction = serviceIntroduction;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
