package com.dodsport.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/8/17.
 */

public class EmployeeBean implements Serializable{

    /**
     * businessId : 000007
     * businessList : [{"businessId":"000007","businessName":"零度瑜伽馆宝安分馆"},{"businessId":"000006","businessName":"零度瑜伽馆南山分馆"},{"businessId":"000008","businessName":"零度瑜伽馆（南山2分馆）"}]
     * employeeId :
     * employeeName : 张三
     * id :
     * idCard :
     * phoneNum : 15102741250
     * sex : 1
     */

    private String businessId;
    private String employeeId;
    private String employeeName;
    private String id;
    private String idCard;
    private String phoneNum;
    private int sex;
    private List<BusinessListBean> businessList;

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public List<BusinessListBean> getBusinessList() {
        return businessList;
    }

    public void setBusinessList(List<BusinessListBean> businessList) {
        this.businessList = businessList;
    }


    public EmployeeBean() {
    }

    @Override
    public String toString() {
        return "CompanyNameBean{" +
                "businessId='" + businessId + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", id='" + id + '\'' +
                ", idCard='" + idCard + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", sex=" + sex +
                ", businessList=" + businessList +
                '}';
    }

    public static class BusinessListBean implements Serializable{
        /**
         * businessId : 000007
         * businessName : 零度瑜伽馆宝安分馆
         */

        private String businessId;
        private String businessName;

        public String getBusinessId() {
            return businessId;
        }

        public void setBusinessId(String businessId) {
            this.businessId = businessId;
        }

        public String getBusinessName() {
            return businessName;
        }

        public void setBusinessName(String businessName) {
            this.businessName = businessName;
        }

        public BusinessListBean() {
        }

        @Override
        public String toString() {
            return "BusinessListBean{" +
                    "businessId='" + businessId + '\'' +
                    ", businessName='" + businessName + '\'' +
                    '}';
        }
    }


}
