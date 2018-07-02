package com.dodsport.consumer.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Administrator
 * @date 2017/11/2
 * 团课详情
 */

public class CourseOrderInfoBean implements Serializable {


    /**
     * datas : {"courseOrderInfo":{"cardrelationId":"0030ab62-e3ce-47ce-b302-a804c9f25773","classDateTime":"2017-11-06 20:00:00.0","classRoomName":"最差的教室2","courseName":"2015怎么办","courseplanId":"380d9ee2-2d6f-4ec8-8da4-04537b1b5a18","coursesignId":"1bb7c574-1bc7-4e52-ae35-14fb386db235","employeeName":"李二牛","isExperience":"","lowPersons":"5","membcardName":"天天","memberCardList":[{"actStatus":"","actTime":"","balance":"","businessId":"","cardStatus":"","cardtypeName":"","createTime":"","creator":"","days":"","flagType":"","giveTimes":"","id":"0030ab62-e3ce-47ce-b302-a804c9f25773","membcardId":"daba894b-b925-42f2-9de5-3a127d5d2922","membcardName":"天天","membcardType":"","memberId":"","opencardSerialId":"","phoneNum":"","price":"","remark":"","rownum":"","specactTime":"","storeId":"","times":"","userName":"","validityTime":"","validityType":""}],"orderPersons":"4","orderStatus":"4","remark":"这是一个不平凡的时间"}}
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

    public CourseOrderInfoBean() {
    }

    @Override
    public String toString() {
        return "CourseOrderInfoBean{" +
                "datas=" + datas +
                ", result=" + result +
                '}';
    }

    public static class DatasBean implements Serializable{
        /**
         * courseOrderInfo : {"cardrelationId":"0030ab62-e3ce-47ce-b302-a804c9f25773","classDateTime":"2017-11-06 20:00:00.0","classRoomName":"最差的教室2","courseName":"2015怎么办","courseplanId":"380d9ee2-2d6f-4ec8-8da4-04537b1b5a18","coursesignId":"1bb7c574-1bc7-4e52-ae35-14fb386db235","employeeName":"李二牛","isExperience":"","lowPersons":"5","membcardName":"天天","memberCardList":[{"actStatus":"","actTime":"","balance":"","businessId":"","cardStatus":"","cardtypeName":"","createTime":"","creator":"","days":"","flagType":"","giveTimes":"","id":"0030ab62-e3ce-47ce-b302-a804c9f25773","membcardId":"daba894b-b925-42f2-9de5-3a127d5d2922","membcardName":"天天","membcardType":"","memberId":"","opencardSerialId":"","phoneNum":"","price":"","remark":"","rownum":"","specactTime":"","storeId":"","times":"","userName":"","validityTime":"","validityType":""}],"orderPersons":"4","orderStatus":"4","remark":"这是一个不平凡的时间"}
         */

        private CourseOrderInfo courseOrderInfo;

        public CourseOrderInfo getCourseOrderInfo() {
            return courseOrderInfo;
        }

        public void setCourseOrderInfo(CourseOrderInfo courseOrderInfo) {
            this.courseOrderInfo = courseOrderInfo;
        }

        public DatasBean() {
        }

        public static class CourseOrderInfo implements Serializable{
            /**
             * cardrelationId : 0030ab62-e3ce-47ce-b302-a804c9f25773
             * classDateTime : 2017-11-06 20:00:00.0
             * classRoomName : 最差的教室2
             * courseName : 2015怎么办
             * courseplanId : 380d9ee2-2d6f-4ec8-8da4-04537b1b5a18
             * coursesignId : 1bb7c574-1bc7-4e52-ae35-14fb386db235
             * employeeName : 李二牛
             * isExperience :
             * lowPersons : 5
             * membcardName : 天天
             * memberCardList : [{"actStatus":"","actTime":"","balance":"","businessId":"","cardStatus":"","cardtypeName":"","createTime":"","creator":"","days":"","flagType":"","giveTimes":"","id":"0030ab62-e3ce-47ce-b302-a804c9f25773","membcardId":"daba894b-b925-42f2-9de5-3a127d5d2922","membcardName":"天天","membcardType":"","memberId":"","opencardSerialId":"","phoneNum":"","price":"","remark":"","rownum":"","specactTime":"","storeId":"","times":"","userName":"","validityTime":"","validityType":""}]
             * orderPersons : 4
             * orderStatus : 4
             * remark : 这是一个不平凡的时间
             */

            private String cardrelationId;
            private String classDateTime;
            private String classRoomName;
            private String courseName;
            private String courseOrderId;
            private String courseplanId;
            private String coursesignId;
            private String employeeName;
            private String isExperience;
            private String lowPersons;
            private String membcardName;
            private String orderPersons;
            private String orderStatus;
            private String remark;
            private List<MemberCardListBean> memberCardList;

            public String getCardrelationId() {
                return cardrelationId;
            }

            public void setCardrelationId(String cardrelationId) {
                this.cardrelationId = cardrelationId;
            }

            public String getClassDateTime() {
                return classDateTime;
            }

            public void setClassDateTime(String classDateTime) {
                this.classDateTime = classDateTime;
            }

            public String getClassRoomName() {
                return classRoomName;
            }

            public void setClassRoomName(String classRoomName) {
                this.classRoomName = classRoomName;
            }

            public String getCourseName() {
                return courseName;
            }

            public void setCourseName(String courseName) {
                this.courseName = courseName;
            }

            public String getCourseplanId() {
                return courseplanId;
            }

            public void setCourseplanId(String courseplanId) {
                this.courseplanId = courseplanId;
            }

            public String getCoursesignId() {
                return coursesignId;
            }

            public void setCoursesignId(String coursesignId) {
                this.coursesignId = coursesignId;
            }

            public String getEmployeeName() {
                return employeeName;
            }

            public void setEmployeeName(String employeeName) {
                this.employeeName = employeeName;
            }

            public String getIsExperience() {
                return isExperience;
            }

            public void setIsExperience(String isExperience) {
                this.isExperience = isExperience;
            }

            public String getLowPersons() {
                return lowPersons;
            }

            public void setLowPersons(String lowPersons) {
                this.lowPersons = lowPersons;
            }

            public String getMembcardName() {
                return membcardName;
            }

            public void setMembcardName(String membcardName) {
                this.membcardName = membcardName;
            }

            public String getOrderPersons() {
                return orderPersons;
            }

            public void setOrderPersons(String orderPersons) {
                this.orderPersons = orderPersons;
            }

            public String getOrderStatus() {
                return orderStatus;
            }

            public void setOrderStatus(String orderStatus) {
                this.orderStatus = orderStatus;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public List<MemberCardListBean> getMemberCardList() {
                return memberCardList;
            }

            public void setMemberCardList(List<MemberCardListBean> memberCardList) {
                this.memberCardList = memberCardList;
            }

            public String getCourseOrderId() {
                return courseOrderId;
            }

            public void setCourseOrderId(String courseOrderId) {
                this.courseOrderId = courseOrderId;
            }

            public CourseOrderInfo() {
            }


            @Override
            public String toString() {
                return "CourseOrderInfo{" +
                        "cardrelationId='" + cardrelationId + '\'' +
                        ", classDateTime='" + classDateTime + '\'' +
                        ", classRoomName='" + classRoomName + '\'' +
                        ", courseName='" + courseName + '\'' +
                        ", courseOrderId='" + courseOrderId + '\'' +
                        ", courseplanId='" + courseplanId + '\'' +
                        ", coursesignId='" + coursesignId + '\'' +
                        ", employeeName='" + employeeName + '\'' +
                        ", isExperience='" + isExperience + '\'' +
                        ", lowPersons='" + lowPersons + '\'' +
                        ", membcardName='" + membcardName + '\'' +
                        ", orderPersons='" + orderPersons + '\'' +
                        ", orderStatus='" + orderStatus + '\'' +
                        ", remark='" + remark + '\'' +
                        ", memberCardList=" + memberCardList +
                        '}';
            }

            public static class MemberCardListBean implements Serializable{
                /**
                 * actStatus :
                 * actTime :
                 * balance :
                 * businessId :
                 * cardStatus :
                 * cardtypeName :
                 * createTime :
                 * creator :
                 * days :
                 * flagType :
                 * giveTimes :
                 * id : 0030ab62-e3ce-47ce-b302-a804c9f25773
                 * membcardId : daba894b-b925-42f2-9de5-3a127d5d2922
                 * membcardName : 天天
                 * membcardType :
                 * memberId :
                 * opencardSerialId :
                 * phoneNum :
                 * price :
                 * remark :
                 * rownum :
                 * specactTime :
                 * storeId :
                 * times :
                 * userName :
                 * validityTime :
                 * validityType :
                 */

                private String actStatus;
                private String actTime;
                private String balance;
                private String businessId;
                private String cardStatus;
                private String cardtypeName;
                private String createTime;
                private String creator;
                private String days;
                private String flagType;
                private String giveTimes;
                private String id;
                private String membcardId;
                private String membcardName;
                private String membcardType;
                private String memberId;
                private String opencardSerialId;
                private String phoneNum;
                private String price;
                private String remark;
                private String rownum;
                private String specactTime;
                private String storeId;
                private String times;
                private String userName;
                private String validityTime;
                private String validityType;

                public String getActStatus() {
                    return actStatus;
                }

                public void setActStatus(String actStatus) {
                    this.actStatus = actStatus;
                }

                public String getActTime() {
                    return actTime;
                }

                public void setActTime(String actTime) {
                    this.actTime = actTime;
                }

                public String getBalance() {
                    return balance;
                }

                public void setBalance(String balance) {
                    this.balance = balance;
                }

                public String getBusinessId() {
                    return businessId;
                }

                public void setBusinessId(String businessId) {
                    this.businessId = businessId;
                }

                public String getCardStatus() {
                    return cardStatus;
                }

                public void setCardStatus(String cardStatus) {
                    this.cardStatus = cardStatus;
                }

                public String getCardtypeName() {
                    return cardtypeName;
                }

                public void setCardtypeName(String cardtypeName) {
                    this.cardtypeName = cardtypeName;
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

                public String getDays() {
                    return days;
                }

                public void setDays(String days) {
                    this.days = days;
                }

                public String getFlagType() {
                    return flagType;
                }

                public void setFlagType(String flagType) {
                    this.flagType = flagType;
                }

                public String getGiveTimes() {
                    return giveTimes;
                }

                public void setGiveTimes(String giveTimes) {
                    this.giveTimes = giveTimes;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getMembcardId() {
                    return membcardId;
                }

                public void setMembcardId(String membcardId) {
                    this.membcardId = membcardId;
                }

                public String getMembcardName() {
                    return membcardName;
                }

                public void setMembcardName(String membcardName) {
                    this.membcardName = membcardName;
                }

                public String getMembcardType() {
                    return membcardType;
                }

                public void setMembcardType(String membcardType) {
                    this.membcardType = membcardType;
                }

                public String getMemberId() {
                    return memberId;
                }

                public void setMemberId(String memberId) {
                    this.memberId = memberId;
                }

                public String getOpencardSerialId() {
                    return opencardSerialId;
                }

                public void setOpencardSerialId(String opencardSerialId) {
                    this.opencardSerialId = opencardSerialId;
                }

                public String getPhoneNum() {
                    return phoneNum;
                }

                public void setPhoneNum(String phoneNum) {
                    this.phoneNum = phoneNum;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public String getRemark() {
                    return remark;
                }

                public void setRemark(String remark) {
                    this.remark = remark;
                }

                public String getRownum() {
                    return rownum;
                }

                public void setRownum(String rownum) {
                    this.rownum = rownum;
                }

                public String getSpecactTime() {
                    return specactTime;
                }

                public void setSpecactTime(String specactTime) {
                    this.specactTime = specactTime;
                }

                public String getStoreId() {
                    return storeId;
                }

                public void setStoreId(String storeId) {
                    this.storeId = storeId;
                }

                public String getTimes() {
                    return times;
                }

                public void setTimes(String times) {
                    this.times = times;
                }

                public String getUserName() {
                    return userName;
                }

                public void setUserName(String userName) {
                    this.userName = userName;
                }

                public String getValidityTime() {
                    return validityTime;
                }

                public void setValidityTime(String validityTime) {
                    this.validityTime = validityTime;
                }

                public String getValidityType() {
                    return validityType;
                }

                public void setValidityType(String validityType) {
                    this.validityType = validityType;
                }

                public MemberCardListBean() {
                }

                @Override
                public String toString() {
                    return "MemberCardListBean{" +
                            "actStatus='" + actStatus + '\'' +
                            ", actTime='" + actTime + '\'' +
                            ", balance='" + balance + '\'' +
                            ", businessId='" + businessId + '\'' +
                            ", cardStatus='" + cardStatus + '\'' +
                            ", cardtypeName='" + cardtypeName + '\'' +
                            ", createTime='" + createTime + '\'' +
                            ", creator='" + creator + '\'' +
                            ", days='" + days + '\'' +
                            ", flagType='" + flagType + '\'' +
                            ", giveTimes='" + giveTimes + '\'' +
                            ", id='" + id + '\'' +
                            ", membcardId='" + membcardId + '\'' +
                            ", membcardName='" + membcardName + '\'' +
                            ", membcardType='" + membcardType + '\'' +
                            ", memberId='" + memberId + '\'' +
                            ", opencardSerialId='" + opencardSerialId + '\'' +
                            ", phoneNum='" + phoneNum + '\'' +
                            ", price='" + price + '\'' +
                            ", remark='" + remark + '\'' +
                            ", rownum='" + rownum + '\'' +
                            ", specactTime='" + specactTime + '\'' +
                            ", storeId='" + storeId + '\'' +
                            ", times='" + times + '\'' +
                            ", userName='" + userName + '\'' +
                            ", validityTime='" + validityTime + '\'' +
                            ", validityType='" + validityType + '\'' +
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
