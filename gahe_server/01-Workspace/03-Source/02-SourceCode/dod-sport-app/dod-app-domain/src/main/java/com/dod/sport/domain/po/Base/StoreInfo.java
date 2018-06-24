package com.dod.sport.domain.po.Base;

import java.util.Date;

/**
 * 门店信息
 * Created by Administrator on 2017/8/19.
 */
public class StoreInfo {
    private String id; //not null,
    private String storeSerialId;//门店编号not null,
    private String businessId;//商家编号not null,
    private String  storeName;//门店编号not null,
    private String phoneNum;    //电话
    private String email;        //邮箱
    private String storePicture; //门店照片
    private String address;//地址not null,
    private int status;//状态:1正常，2异常，3停用,默认是1
    private String creator;//创建人
    private Date create_time;//创建时间
    private String businessPicture;     //商家照片
    private String logo;               //商家logo
    private String bossName;   //老板姓名
    private String bossPhone;  //老板手机号
    private String bossEmail;  //老板email
    private String otherWays;  //其他联系方式
    private String productService; //产品介绍
    private String doorPhotoId;           //门头照ID
    private String classroomPhotosId;     //教室照片ID
    private String receptionPhotosId;     //前台照ID
    private String businessLicenseId;     //营业执照ID
    private String teacherPhotoId;        //老师照ID
    private String classPhotosId;        //上课照片ID
    private String businessStartTime;        //营业开始时间
    private String businessEndTime;        //营业结束时间
    private String cid;                   //用于表示是否关联门店
    public String getBossName() {
        return bossName;
    }

    public void setBossName(String bossName) {
        this.bossName = bossName;
    }

    public String getBossPhone() {
        return bossPhone;
    }

    public void setBossPhone(String bossPhone) {
        this.bossPhone = bossPhone;
    }

    public String getBossEmail() {
        return bossEmail;
    }

    public void setBossEmail(String bossEmail) {
        this.bossEmail = bossEmail;
    }

    public String getOtherWays() {
        return otherWays;
    }

    public void setOtherWays(String otherWays) {
        this.otherWays = otherWays;
    }

    public String getProductService() {
        return productService;
    }

    public void setProductService(String productService) {
        this.productService = productService;
    }

    public String getDoorPhotoId() {
        return doorPhotoId;
    }

    public void setDoorPhotoId(String doorPhotoId) {
        this.doorPhotoId = doorPhotoId;
    }

    public String getClassroomPhotosId() {
        return classroomPhotosId;
    }

    public void setClassroomPhotosId(String classroomPhotosId) {
        this.classroomPhotosId = classroomPhotosId;
    }

    public String getReceptionPhotosId() {
        return receptionPhotosId;
    }

    public void setReceptionPhotosId(String receptionPhotosId) {
        this.receptionPhotosId = receptionPhotosId;
    }

    public String getBusinessLicenseId() {
        return businessLicenseId;
    }

    public void setBusinessLicenseId(String businessLicenseId) {
        this.businessLicenseId = businessLicenseId;
    }

    public String getTeacherPhotoId() {
        return teacherPhotoId;
    }

    public void setTeacherPhotoId(String teacherPhotoId) {
        this.teacherPhotoId = teacherPhotoId;
    }

    public String getClassPhotosId() {
        return classPhotosId;
    }

    public void setClassPhotosId(String classPhotosId) {
        this.classPhotosId = classPhotosId;
    }

    public String getBusinessStartTime() {
        return businessStartTime;
    }

    public void setBusinessStartTime(String businessStartTime) {
        this.businessStartTime = businessStartTime;
    }

    public String getBusinessEndTime() {
        return businessEndTime;
    }

    public void setBusinessEndTime(String businessEndTime) {
        this.businessEndTime = businessEndTime;
    }

    public String getBusinessPicture() {
        return businessPicture;
    }

    public void setBusinessPicture(String businessPicture) {
        this.businessPicture = businessPicture;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getStorePicture() {
        return storePicture;
    }

    public void setStorePicture(String storePicture) {
        this.storePicture = storePicture;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStoreSerialId() {
        return storeSerialId;
    }

    public void setStoreSerialId(String storeSerialId) {
        this.storeSerialId = storeSerialId;
    }

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getBusinessId() {return businessId;}

    public void setBusinessId(String businessId) {this.businessId = businessId;}

    public String getStoreName() {return storeName;}

    public void setStoreName(String storeName) {this.storeName = storeName;}

    public String getAddress() {return address;}

    public void setAddress(String address) {this.address = address;}

    public int getStatus() {return status;}

    public void setStatus(int status) {this.status = status;}

    public String getCreator() {return creator;}

    public void setCreator(String creator) {this.creator = creator;}

    public Date getCreate_time() {return create_time;}

    public void setCreate_time(Date create_time) {this.create_time = create_time;}

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
}
