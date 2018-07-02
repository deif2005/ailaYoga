package com.dodsport.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Administrator
 * @date 2017/8/25
 *
 * 工作台 获取员工商家列表
 */

public class CompanyListBean implements Serializable{

    /**
     * datas : {"businessInfoList":[{"address":"","area":"","businessName":"huz","businessPicture":"","businessSerialId":"000030","businessType":"","createTime":"","creator":"","customType":"","email":"","id":"80a2aa90-2205-4453-8760-b395fb48746a","idcardPictureA":"","idcardPictureB":"","logo":"","otherInformation":"","owner":"","phoneNum":"","phoneNum2":"","remark":"","status":"","storeInfoList":[{"address":"111111111","bossEmail":"","bossName":"","bossPhone":"","businessEndTime":"","businessId":"80a2aa90-2205-4453-8760-b395fb48746a","businessLicenseId":"","businessPicture":"","businessStartTime":"","classPhotosId":"","classroomPhotosId":"","create_time":null,"creator":"bdc13c70-de9b-47e0-b59d-ea90e8926ee9","doorPhotoId":"","email":"291346962@qq.com","id":"4340b0a8-7cdf-4634-b516-a79aef833fff","logo":"","otherWays":"","phoneNum":"15100000000","productService":"","receptionPhotosId":"","status":2,"storeName":"dods","storePicture":"","storeSerialId":"009","teacherPhotoId":""},{"address":"山东","bossEmail":"","bossName":"","bossPhone":"","businessEndTime":"","businessId":"80a2aa90-2205-4453-8760-b395fb48746a","businessLicenseId":"","businessPicture":"","businessStartTime":"","classPhotosId":"","classroomPhotosId":"","create_time":null,"creator":"bdc13c70-de9b-47e0-b59d-ea90e8926ee9","doorPhotoId":"","email":"291346962@qq.com","id":"5565f926-eb45-47c5-a297-ad2101533b19","logo":"","otherWays":"","phoneNum":"15102741252","productService":"","receptionPhotosId":"","status":1,"storeName":"最好的门店2","storePicture":"","storeSerialId":"004","teacherPhotoId":""}]}]}
     * result : {"code":"0","msg":"sys_ok"}
     */

    private DatasBean datas;
    private ResultBean result;

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public CompanyListBean() {
    }

    public static class DatasBean implements Serializable{
        private List<BusinessInfoListBean> businessInfoList;

        public List<BusinessInfoListBean> getBusinessInfoList() {
            return businessInfoList;
        }

        public void setBusinessInfoList(List<BusinessInfoListBean> businessInfoList) {
            this.businessInfoList = businessInfoList;
        }

        public DatasBean() {
        }

        public static class BusinessInfoListBean implements Serializable{
            /**
             * address :
             * area :
             * businessName : huz
             * businessPicture :
             * businessSerialId : 000030
             * businessType :
             * createTime :
             * creator :
             * customType :
             * email :
             * id : 80a2aa90-2205-4453-8760-b395fb48746a
             * idcardPictureA :
             * idcardPictureB :
             * logo :
             * otherInformation :
             * owner :
             * phoneNum :
             * phoneNum2 :
             * remark :
             * status :
             * storeInfoList : [{"address":"111111111","bossEmail":"","bossName":"","bossPhone":"","businessEndTime":"","businessId":"80a2aa90-2205-4453-8760-b395fb48746a","businessLicenseId":"","businessPicture":"","businessStartTime":"","classPhotosId":"","classroomPhotosId":"","create_time":null,"creator":"bdc13c70-de9b-47e0-b59d-ea90e8926ee9","doorPhotoId":"","email":"291346962@qq.com","id":"4340b0a8-7cdf-4634-b516-a79aef833fff","logo":"","otherWays":"","phoneNum":"15100000000","productService":"","receptionPhotosId":"","status":2,"storeName":"dods","storePicture":"","storeSerialId":"009","teacherPhotoId":""},{"address":"山东","bossEmail":"","bossName":"","bossPhone":"","businessEndTime":"","businessId":"80a2aa90-2205-4453-8760-b395fb48746a","businessLicenseId":"","businessPicture":"","businessStartTime":"","classPhotosId":"","classroomPhotosId":"","create_time":null,"creator":"bdc13c70-de9b-47e0-b59d-ea90e8926ee9","doorPhotoId":"","email":"291346962@qq.com","id":"5565f926-eb45-47c5-a297-ad2101533b19","logo":"","otherWays":"","phoneNum":"15102741252","productService":"","receptionPhotosId":"","status":1,"storeName":"最好的门店2","storePicture":"","storeSerialId":"004","teacherPhotoId":""}]
             */

            private String address;
            private String area;
            private String businessName;
            private String businessPicture;
            private String businessSerialId;
            private String businessType;
            private String createTime;
            private String creator;
            private String customType;
            private String email;
            private String id;
            private String idcardPictureA;
            private String idcardPictureB;
            private String logo;
            private String otherInformation;
            private String owner;
            private String phoneNum;
            private String phoneNum2;
            private String remark;
            private String status;
            private List<StoreInfoListBean> storeInfoList;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getBusinessName() {
                return businessName;
            }

            public void setBusinessName(String businessName) {
                this.businessName = businessName;
            }

            public String getBusinessPicture() {
                return businessPicture;
            }

            public void setBusinessPicture(String businessPicture) {
                this.businessPicture = businessPicture;
            }

            public String getBusinessSerialId() {
                return businessSerialId;
            }

            public void setBusinessSerialId(String businessSerialId) {
                this.businessSerialId = businessSerialId;
            }

            public String getBusinessType() {
                return businessType;
            }

            public void setBusinessType(String businessType) {
                this.businessType = businessType;
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

            public String getCustomType() {
                return customType;
            }

            public void setCustomType(String customType) {
                this.customType = customType;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getOtherInformation() {
                return otherInformation;
            }

            public void setOtherInformation(String otherInformation) {
                this.otherInformation = otherInformation;
            }

            public String getOwner() {
                return owner;
            }

            public void setOwner(String owner) {
                this.owner = owner;
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

            public List<StoreInfoListBean> getStoreInfoList() {
                return storeInfoList;
            }

            public void setStoreInfoList(List<StoreInfoListBean> storeInfoList) {
                this.storeInfoList = storeInfoList;
            }

            public BusinessInfoListBean() {
            }

            @Override
            public String toString() {
                return "BusinessInfoListBean{" +
                        "address='" + address + '\'' +
                        ", area='" + area + '\'' +
                        ", businessName='" + businessName + '\'' +
                        ", businessPicture='" + businessPicture + '\'' +
                        ", businessSerialId='" + businessSerialId + '\'' +
                        ", businessType='" + businessType + '\'' +
                        ", createTime='" + createTime + '\'' +
                        ", creator='" + creator + '\'' +
                        ", customType='" + customType + '\'' +
                        ", email='" + email + '\'' +
                        ", id='" + id + '\'' +
                        ", idcardPictureA='" + idcardPictureA + '\'' +
                        ", idcardPictureB='" + idcardPictureB + '\'' +
                        ", logo='" + logo + '\'' +
                        ", otherInformation='" + otherInformation + '\'' +
                        ", owner='" + owner + '\'' +
                        ", phoneNum='" + phoneNum + '\'' +
                        ", phoneNum2='" + phoneNum2 + '\'' +
                        ", remark='" + remark + '\'' +
                        ", status='" + status + '\'' +
                        ", storeInfoList=" + storeInfoList +
                        '}';
            }

            public static class StoreInfoListBean implements Serializable{
                /**
                 * address : 111111111
                 * bossEmail :
                 * bossName :
                 * bossPhone :
                 * businessEndTime :
                 * businessId : 80a2aa90-2205-4453-8760-b395fb48746a
                 * businessLicenseId :
                 * businessPicture :
                 * businessStartTime :
                 * classPhotosId :
                 * classroomPhotosId :
                 * create_time : null
                 * creator : bdc13c70-de9b-47e0-b59d-ea90e8926ee9
                 * doorPhotoId :
                 * email : 291346962@qq.com
                 * id : 4340b0a8-7cdf-4634-b516-a79aef833fff
                 * logo :
                 * otherWays :
                 * phoneNum : 15100000000
                 * productService :
                 * receptionPhotosId :
                 * status : 2
                 * storeName : dods
                 * storePicture :
                 * storeSerialId : 009
                 * teacherPhotoId :
                 */

                private String address;
                private String bossEmail;
                private String bossName;
                private String bossPhone;
                private String businessEndTime;
                private String businessId;
                private String businessLicenseId;
                private String businessPicture;
                private String businessStartTime;
                private String classPhotosId;
                private String classroomPhotosId;
                private Object create_time;
                private String creator;
                private String doorPhotoId;
                private String email;
                private String id;
                private String logo;
                private String otherWays;
                private String phoneNum;
                private String productService;
                private String receptionPhotosId;
                private int status;
                private String storeName;
                private String storePicture;
                private String storeSerialId;
                private String teacherPhotoId;

                public String getAddress() {
                    return address;
                }

                public void setAddress(String address) {
                    this.address = address;
                }

                public String getBossEmail() {
                    return bossEmail;
                }

                public void setBossEmail(String bossEmail) {
                    this.bossEmail = bossEmail;
                }

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

                public String getBusinessEndTime() {
                    return businessEndTime;
                }

                public void setBusinessEndTime(String businessEndTime) {
                    this.businessEndTime = businessEndTime;
                }

                public String getBusinessId() {
                    return businessId;
                }

                public void setBusinessId(String businessId) {
                    this.businessId = businessId;
                }

                public String getBusinessLicenseId() {
                    return businessLicenseId;
                }

                public void setBusinessLicenseId(String businessLicenseId) {
                    this.businessLicenseId = businessLicenseId;
                }

                public String getBusinessPicture() {
                    return businessPicture;
                }

                public void setBusinessPicture(String businessPicture) {
                    this.businessPicture = businessPicture;
                }

                public String getBusinessStartTime() {
                    return businessStartTime;
                }

                public void setBusinessStartTime(String businessStartTime) {
                    this.businessStartTime = businessStartTime;
                }

                public String getClassPhotosId() {
                    return classPhotosId;
                }

                public void setClassPhotosId(String classPhotosId) {
                    this.classPhotosId = classPhotosId;
                }

                public String getClassroomPhotosId() {
                    return classroomPhotosId;
                }

                public void setClassroomPhotosId(String classroomPhotosId) {
                    this.classroomPhotosId = classroomPhotosId;
                }

                public Object getCreate_time() {
                    return create_time;
                }

                public void setCreate_time(Object create_time) {
                    this.create_time = create_time;
                }

                public String getCreator() {
                    return creator;
                }

                public void setCreator(String creator) {
                    this.creator = creator;
                }

                public String getDoorPhotoId() {
                    return doorPhotoId;
                }

                public void setDoorPhotoId(String doorPhotoId) {
                    this.doorPhotoId = doorPhotoId;
                }

                public String getEmail() {
                    return email;
                }

                public void setEmail(String email) {
                    this.email = email;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getLogo() {
                    return logo;
                }

                public void setLogo(String logo) {
                    this.logo = logo;
                }

                public String getOtherWays() {
                    return otherWays;
                }

                public void setOtherWays(String otherWays) {
                    this.otherWays = otherWays;
                }

                public String getPhoneNum() {
                    return phoneNum;
                }

                public void setPhoneNum(String phoneNum) {
                    this.phoneNum = phoneNum;
                }

                public String getProductService() {
                    return productService;
                }

                public void setProductService(String productService) {
                    this.productService = productService;
                }

                public String getReceptionPhotosId() {
                    return receptionPhotosId;
                }

                public void setReceptionPhotosId(String receptionPhotosId) {
                    this.receptionPhotosId = receptionPhotosId;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public String getStoreName() {
                    return storeName;
                }

                public void setStoreName(String storeName) {
                    this.storeName = storeName;
                }

                public String getStorePicture() {
                    return storePicture;
                }

                public void setStorePicture(String storePicture) {
                    this.storePicture = storePicture;
                }

                public String getStoreSerialId() {
                    return storeSerialId;
                }

                public void setStoreSerialId(String storeSerialId) {
                    this.storeSerialId = storeSerialId;
                }

                public String getTeacherPhotoId() {
                    return teacherPhotoId;
                }

                public void setTeacherPhotoId(String teacherPhotoId) {
                    this.teacherPhotoId = teacherPhotoId;
                }

                public StoreInfoListBean() {
                }

                @Override
                public String toString() {
                    return "StoreInfoListBean{" +
                            "address='" + address + '\'' +
                            ", bossEmail='" + bossEmail + '\'' +
                            ", bossName='" + bossName + '\'' +
                            ", bossPhone='" + bossPhone + '\'' +
                            ", businessEndTime='" + businessEndTime + '\'' +
                            ", businessId='" + businessId + '\'' +
                            ", businessLicenseId='" + businessLicenseId + '\'' +
                            ", businessPicture='" + businessPicture + '\'' +
                            ", businessStartTime='" + businessStartTime + '\'' +
                            ", classPhotosId='" + classPhotosId + '\'' +
                            ", classroomPhotosId='" + classroomPhotosId + '\'' +
                            ", create_time=" + create_time +
                            ", creator='" + creator + '\'' +
                            ", doorPhotoId='" + doorPhotoId + '\'' +
                            ", email='" + email + '\'' +
                            ", id='" + id + '\'' +
                            ", logo='" + logo + '\'' +
                            ", otherWays='" + otherWays + '\'' +
                            ", phoneNum='" + phoneNum + '\'' +
                            ", productService='" + productService + '\'' +
                            ", receptionPhotosId='" + receptionPhotosId + '\'' +
                            ", status=" + status +
                            ", storeName='" + storeName + '\'' +
                            ", storePicture='" + storePicture + '\'' +
                            ", storeSerialId='" + storeSerialId + '\'' +
                            ", teacherPhotoId='" + teacherPhotoId + '\'' +
                            '}';
                }
            }
        }
    }

    public static class ResultBean implements Serializable{
        /**
         * code : 0
         * msg : sys_ok
         */

        private String code;
        private String msg;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public ResultBean() {
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "code='" + code + '\'' +
                    ", msg='" + msg + '\'' +
                    '}';
        }
    }
}
