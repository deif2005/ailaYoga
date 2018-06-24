package com.dod.sport.domain.po.Base;

import java.io.Serializable;

/**
 * Created by defi on 2017-08-14.
 * 商家信息pojo
 */
public class BusinessInfo extends BaseBusinessInfo implements Serializable{

    private static final long serialVersionUID = 6920100337220911511L;

    private String id;
    private String businessSerialId;    //商家编号
    private String businessName;        //商家名称
    private String owner;               //所有者
    private String logo;                //商家logo
    private String businessPicture;     //营业执照
    private String idcardPictureA;      //身份证正面照
    private String idcardPictureB;      //身份证反面照
    private String otherInformation;    //其它联系方式
    private String email;               //商家邮箱
    private String area;                //地区
    private String address;             //地址
    private String phoneNum;            //手机号
    private String phoneNum2;           //座机
    private String businessType;        //商家类别:1瑜伽类
    private String customType;          //客户类别:1签约客户，2待开发客户，3体验客户
    private String remark;              //商家简介
    private String status;              //状态：1正常，2异常，3停用
    private String createTime;
    private String creator;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getBusinessSerialId() {
        return businessSerialId;
    }

    @Override
    public void setBusinessSerialId(String businessSerialId) {
        this.businessSerialId = businessSerialId;
    }

    @Override
    public String getBusinessName() {
        return businessName;
    }

    @Override
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBusinessPicture() {
        return businessPicture;
    }

    public void setBusinessPicture(String businessPicture) {
        this.businessPicture = businessPicture;
    }

    public String getIdcardPictureA() {
        return idcardPictureA;
    }

    public void setIdcardPictureA(String idcardPictureA) {
        this.idcardPictureA = idcardPictureA;
    }

    public String getIdcardPictureB() {
        return idcardPictureB;
    }

    public void setIdcardPictureB(String idcardPictureB) {
        this.idcardPictureB = idcardPictureB;
    }

    public String getOtherInformation() {
        return otherInformation;
    }

    public void setOtherInformation(String otherInformation) {
        this.otherInformation = otherInformation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getCustomType() {
        return customType;
    }

    public void setCustomType(String customType) {
        this.customType = customType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
