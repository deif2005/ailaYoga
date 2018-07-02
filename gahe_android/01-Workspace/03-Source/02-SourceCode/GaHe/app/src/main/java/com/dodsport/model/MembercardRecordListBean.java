package com.dodsport.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/9/25.
 */

public class MembercardRecordListBean implements Serializable {


    /**
     * datas : {"membercardRecordList":[{"createrTime":"2017-09-22 17:51:06.0","empName":"张三","giveTimes":"","memberName":"了了","modifyType":"1","months":"","newCard":"","nominalAmount":"","oldCard":"","opencardSerialId":"000000007","orderStatus":"","tableType":"","tarmemberName":"","times":"0","validityTime":""},{"createrTime":"2017-09-22 17:22:27.0","empName":"里有","giveTimes":"","memberName":"了了","modifyType":"5","months":"","newCard":"","nominalAmount":"","oldCard":"","opencardSerialId":"000000007","orderStatus":"","tableType":"","tarmemberName":"","times":"0","validityTime":""},{"createrTime":"2017-09-22 17:20:26.0","empName":"里有","giveTimes":"","memberName":"了了","modifyType":"4","months":"","newCard":"","nominalAmount":"","oldCard":"","opencardSerialId":"000000007","orderStatus":"","tableType":"","tarmemberName":"","times":"23","validityTime":""},{"createrTime":"2017-09-22 17:12:11.0","empName":"里有","giveTimes":"","memberName":"了了","modifyType":"","months":"","newCard":"","nominalAmount":"688.00","oldCard":"","opencardSerialId":"000000007","orderStatus":"","tableType":"2","tarmemberName":"","times":"","validityTime":""},{"createrTime":"2017-09-22 16:44:03.0","empName":"里有","giveTimes":"","memberName":"了了","modifyType":"","months":"","newCard":"","nominalAmount":"488.00","oldCard":"","opencardSerialId":"000000007","orderStatus":"","tableType":"2","tarmemberName":"","times":"1","validityTime":""}]}
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

    public static class DatasBean {
        private List<MembercardRecordList> membercardRecordList;

        public List<MembercardRecordList> getMembercardRecordList() {
            return membercardRecordList;
        }

        public void setMembercardRecordList(List<MembercardRecordList> membercardRecordList) {
            this.membercardRecordList = membercardRecordList;
        }

        public DatasBean() {
        }

        @Override
        public String toString() {
            return "DatasBean{" +
                    "membercardRecordList=" + membercardRecordList +
                    '}';
        }

        public static class MembercardRecordList implements Serializable{


            /**
             * contentStr : 充值1次488.00元
             * courseName :
             * createrTime : 2017-09-25 20:32:55.0
             * empName : 张三
             * giveTimes : 0
             * membcardType : 1
             * memberName : 了了
             * modifyType : 0
             * months : 0
             * newCard :
             * nominalAmount : 488.00
             * oldCard :
             * opencardSerialId : 000000010
             * orderStatus : 0
             * tableType : 2
             * tarmemberName :
             * times : 1
             * validityTime :
             */

            private String contentStr;
            private String courseName;
            private String createrTime;
            private String empName;
            private int giveTimes;
            private int membcardType;
            private String memberName;
            private int modifyType;
            private int months;
            private String newCard;
            private String nominalAmount;
            private String oldCard;
            private String opencardSerialId;
            private int orderStatus;
            private String tableType;
            private String tarmemberName;
            private int times;
            private String validityTime;

            public String getContentStr() {
                return contentStr;
            }

            public void setContentStr(String contentStr) {
                this.contentStr = contentStr;
            }

            public String getCourseName() {
                return courseName;
            }

            public void setCourseName(String courseName) {
                this.courseName = courseName;
            }

            public String getCreaterTime() {
                return createrTime;
            }

            public void setCreaterTime(String createrTime) {
                this.createrTime = createrTime;
            }

            public String getEmpName() {
                return empName;
            }

            public void setEmpName(String empName) {
                this.empName = empName;
            }

            public int getGiveTimes() {
                return giveTimes;
            }

            public void setGiveTimes(int giveTimes) {
                this.giveTimes = giveTimes;
            }

            public int getMembcardType() {
                return membcardType;
            }

            public void setMembcardType(int membcardType) {
                this.membcardType = membcardType;
            }

            public String getMemberName() {
                return memberName;
            }

            public void setMemberName(String memberName) {
                this.memberName = memberName;
            }

            public int getModifyType() {
                return modifyType;
            }

            public void setModifyType(int modifyType) {
                this.modifyType = modifyType;
            }

            public int getMonths() {
                return months;
            }

            public void setMonths(int months) {
                this.months = months;
            }

            public String getNewCard() {
                return newCard;
            }

            public void setNewCard(String newCard) {
                this.newCard = newCard;
            }

            public String getNominalAmount() {
                return nominalAmount;
            }

            public void setNominalAmount(String nominalAmount) {
                this.nominalAmount = nominalAmount;
            }

            public String getOldCard() {
                return oldCard;
            }

            public void setOldCard(String oldCard) {
                this.oldCard = oldCard;
            }

            public String getOpencardSerialId() {
                return opencardSerialId;
            }

            public void setOpencardSerialId(String opencardSerialId) {
                this.opencardSerialId = opencardSerialId;
            }

            public int getOrderStatus() {
                return orderStatus;
            }

            public void setOrderStatus(int orderStatus) {
                this.orderStatus = orderStatus;
            }

            public String getTableType() {
                return tableType;
            }

            public void setTableType(String tableType) {
                this.tableType = tableType;
            }

            public String getTarmemberName() {
                return tarmemberName;
            }

            public void setTarmemberName(String tarmemberName) {
                this.tarmemberName = tarmemberName;
            }

            public int getTimes() {
                return times;
            }

            public void setTimes(int times) {
                this.times = times;
            }

            public String getValidityTime() {
                return validityTime;
            }

            public void setValidityTime(String validityTime) {
                this.validityTime = validityTime;
            }
            public MembercardRecordList() {
            }

            @Override
            public String toString() {
                return "MembercardRecordList{" +
                        "createrTime='" + createrTime + '\'' +
                        ", empName='" + empName + '\'' +
                        ", giveTimes='" + giveTimes + '\'' +
                        ", memberName='" + memberName + '\'' +
                        ", modifyType='" + modifyType + '\'' +
                        ", months='" + months + '\'' +
                        ", newCard='" + newCard + '\'' +
                        ", nominalAmount='" + nominalAmount + '\'' +
                        ", oldCard='" + oldCard + '\'' +
                        ", opencardSerialId='" + opencardSerialId + '\'' +
                        ", orderStatus='" + orderStatus + '\'' +
                        ", tableType='" + tableType + '\'' +
                        ", tarmemberName='" + tarmemberName + '\'' +
                        ", times='" + times + '\'' +
                        ", validityTime='" + validityTime + '\'' +
                        '}';
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
