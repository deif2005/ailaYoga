package com.dodsport.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/11/21.
 *
 * 员工的个人考勤异常记录
 */

public class AbnormalAttendanceBean implements Serializable{


    /**
     * datas : {"abnormalAttendance":{"depName":"万店瑜伽部门2","earlyTime":[""],"empHead":"","empId":"1d3c6081-b4a8-4752-856a-267da26c6350","employeeName":"理老师","employeeSerialId":"000003","failurePunch":"30","id":"","lasttimes":"","lateTime":[""],"leavetimes":"","notSign":["2017-11-1 (星期三)  未签到","2017-11-1 (星期三)  未签退","2017-11-2 (星期四)  未签到","2017-11-2 (星期四)  未签退","2017-11-3 (星期五)  未签到","2017-11-3 (星期五)  未签退","2017-11-4 (星期六)  未签到","2017-11-4 (星期六)  未签退","2017-11-5 (星期天)  未签到","2017-11-5 (星期天)  未签退","2017-11-6 (星期一)  未签到","2017-11-6 (星期一)  未签退","2017-11-7 (星期二)  未签到","2017-11-7 (星期二)  未签退","2017-11-8 (星期三)  未签到","2017-11-8 (星期三)  未签退","2017-11-9 (星期四)  未签到","2017-11-9 (星期四)  未签退","2017-11-10 (星期五)  未签到","2017-11-10 (星期五)  未签退","2017-11-11 (星期六)  未签到","2017-11-11 (星期六)  未签退","2017-11-12 (星期天)  未签到","2017-11-12 (星期天)  未签退","2017-11-13 (星期一)  未签到","2017-11-13 (星期一)  未签退","2017-11-14 (星期二)  未签到","2017-11-14 (星期二)  未签退","2017-11-15 (星期三)  未签到","2017-11-15 (星期三)  未签退","2017-11-16 (星期四)  未签到","2017-11-16 (星期四)  未签退","2017-11-17 (星期五)  未签到","2017-11-17 (星期五)  未签退","2017-11-18 (星期六)  未签到","2017-11-18 (星期六)  未签退","2017-11-19 (星期天)  未签到","2017-11-19 (星期天)  未签退","2017-11-20 (星期一)  未签到","2017-11-20 (星期一)  未签退","2017-11-21 (星期二)  未签到","2017-11-21 (星期二)  未签退","2017-11-22 (星期三)  未签到","2017-11-22 (星期三)  未签退","2017-11-23 (星期四)  未签到","2017-11-23 (星期四)  未签退","2017-11-24 (星期五)  未签到","2017-11-24 (星期五)  未签退","2017-11-25 (星期六)  未签到","2017-11-25 (星期六)  未签退","2017-11-26 (星期天)  未签到","2017-11-26 (星期天)  未签退","2017-11-27 (星期一)  未签到","2017-11-27 (星期一)  未签退","2017-11-28 (星期二)  未签到","2017-11-28 (星期二)  未签退","2017-11-29 (星期三)  未签到","2017-11-29 (星期三)  未签退","2017-11-30 (星期四)  未签到","2017-11-30 (星期四)  未签退"],"positionName":"万店瑜伽中级老师","signType":"","storeId":"5565f926-eb45-47c5-a297-ad2101533b19"}}
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

    public static class DatasBean implements Serializable{
        /**
         * abnormalAttendance : {"depName":"万店瑜伽部门2","earlyTime":[""],"empHead":"","empId":"1d3c6081-b4a8-4752-856a-267da26c6350","employeeName":"理老师","employeeSerialId":"000003","failurePunch":"30","id":"","lasttimes":"","lateTime":[""],"leavetimes":"","notSign":["2017-11-1 (星期三)  未签到","2017-11-1 (星期三)  未签退","2017-11-2 (星期四)  未签到","2017-11-2 (星期四)  未签退","2017-11-3 (星期五)  未签到","2017-11-3 (星期五)  未签退","2017-11-4 (星期六)  未签到","2017-11-4 (星期六)  未签退","2017-11-5 (星期天)  未签到","2017-11-5 (星期天)  未签退","2017-11-6 (星期一)  未签到","2017-11-6 (星期一)  未签退","2017-11-7 (星期二)  未签到","2017-11-7 (星期二)  未签退","2017-11-8 (星期三)  未签到","2017-11-8 (星期三)  未签退","2017-11-9 (星期四)  未签到","2017-11-9 (星期四)  未签退","2017-11-10 (星期五)  未签到","2017-11-10 (星期五)  未签退","2017-11-11 (星期六)  未签到","2017-11-11 (星期六)  未签退","2017-11-12 (星期天)  未签到","2017-11-12 (星期天)  未签退","2017-11-13 (星期一)  未签到","2017-11-13 (星期一)  未签退","2017-11-14 (星期二)  未签到","2017-11-14 (星期二)  未签退","2017-11-15 (星期三)  未签到","2017-11-15 (星期三)  未签退","2017-11-16 (星期四)  未签到","2017-11-16 (星期四)  未签退","2017-11-17 (星期五)  未签到","2017-11-17 (星期五)  未签退","2017-11-18 (星期六)  未签到","2017-11-18 (星期六)  未签退","2017-11-19 (星期天)  未签到","2017-11-19 (星期天)  未签退","2017-11-20 (星期一)  未签到","2017-11-20 (星期一)  未签退","2017-11-21 (星期二)  未签到","2017-11-21 (星期二)  未签退","2017-11-22 (星期三)  未签到","2017-11-22 (星期三)  未签退","2017-11-23 (星期四)  未签到","2017-11-23 (星期四)  未签退","2017-11-24 (星期五)  未签到","2017-11-24 (星期五)  未签退","2017-11-25 (星期六)  未签到","2017-11-25 (星期六)  未签退","2017-11-26 (星期天)  未签到","2017-11-26 (星期天)  未签退","2017-11-27 (星期一)  未签到","2017-11-27 (星期一)  未签退","2017-11-28 (星期二)  未签到","2017-11-28 (星期二)  未签退","2017-11-29 (星期三)  未签到","2017-11-29 (星期三)  未签退","2017-11-30 (星期四)  未签到","2017-11-30 (星期四)  未签退"],"positionName":"万店瑜伽中级老师","signType":"","storeId":"5565f926-eb45-47c5-a297-ad2101533b19"}
         */

        private AbnormalAttendance abnormalAttendance;

        public AbnormalAttendance getAbnormalAttendance() {
            return abnormalAttendance;
        }

        public void setAbnormalAttendance(AbnormalAttendance abnormalAttendance) {
            this.abnormalAttendance = abnormalAttendance;
        }

        public DatasBean() {
        }

        public static class AbnormalAttendance implements Serializable{
            /**
             * depName : 万店瑜伽部门2
             * earlyTime : [""]
             * empHead :
             * empId : 1d3c6081-b4a8-4752-856a-267da26c6350
             * employeeName : 理老师
             * employeeSerialId : 000003
             * failurePunch : 30
             * id :
             * lasttimes :
             * lateTime : [""]
             * leavetimes :
             * notSign : ["2017-11-1 (星期三)  未签到","2017-11-1 (星期三)  未签退","2017-11-2 (星期四)  未签到","2017-11-2 (星期四)  未签退","2017-11-3 (星期五)  未签到","2017-11-3 (星期五)  未签退","2017-11-4 (星期六)  未签到","2017-11-4 (星期六)  未签退","2017-11-5 (星期天)  未签到","2017-11-5 (星期天)  未签退","2017-11-6 (星期一)  未签到","2017-11-6 (星期一)  未签退","2017-11-7 (星期二)  未签到","2017-11-7 (星期二)  未签退","2017-11-8 (星期三)  未签到","2017-11-8 (星期三)  未签退","2017-11-9 (星期四)  未签到","2017-11-9 (星期四)  未签退","2017-11-10 (星期五)  未签到","2017-11-10 (星期五)  未签退","2017-11-11 (星期六)  未签到","2017-11-11 (星期六)  未签退","2017-11-12 (星期天)  未签到","2017-11-12 (星期天)  未签退","2017-11-13 (星期一)  未签到","2017-11-13 (星期一)  未签退","2017-11-14 (星期二)  未签到","2017-11-14 (星期二)  未签退","2017-11-15 (星期三)  未签到","2017-11-15 (星期三)  未签退","2017-11-16 (星期四)  未签到","2017-11-16 (星期四)  未签退","2017-11-17 (星期五)  未签到","2017-11-17 (星期五)  未签退","2017-11-18 (星期六)  未签到","2017-11-18 (星期六)  未签退","2017-11-19 (星期天)  未签到","2017-11-19 (星期天)  未签退","2017-11-20 (星期一)  未签到","2017-11-20 (星期一)  未签退","2017-11-21 (星期二)  未签到","2017-11-21 (星期二)  未签退","2017-11-22 (星期三)  未签到","2017-11-22 (星期三)  未签退","2017-11-23 (星期四)  未签到","2017-11-23 (星期四)  未签退","2017-11-24 (星期五)  未签到","2017-11-24 (星期五)  未签退","2017-11-25 (星期六)  未签到","2017-11-25 (星期六)  未签退","2017-11-26 (星期天)  未签到","2017-11-26 (星期天)  未签退","2017-11-27 (星期一)  未签到","2017-11-27 (星期一)  未签退","2017-11-28 (星期二)  未签到","2017-11-28 (星期二)  未签退","2017-11-29 (星期三)  未签到","2017-11-29 (星期三)  未签退","2017-11-30 (星期四)  未签到","2017-11-30 (星期四)  未签退"]
             * positionName : 万店瑜伽中级老师
             * signType :
             * storeId : 5565f926-eb45-47c5-a297-ad2101533b19
             */

            private String depName;
            private String empHead;
            private String empId;
            private String employeeName;
            private String employeeSerialId;
            private String failurePunch;
            private String id;
            private String lasttimes;
            private String leavetimes;
            private String positionName;
            private String signType;
            private String storeId;
            private List<String> earlyTime;
            private List<String> lateTime;
            private List<String> notSign;

            public String getDepName() {
                return depName;
            }

            public void setDepName(String depName) {
                this.depName = depName;
            }

            public String getEmpHead() {
                return empHead;
            }

            public void setEmpHead(String empHead) {
                this.empHead = empHead;
            }

            public String getEmpId() {
                return empId;
            }

            public void setEmpId(String empId) {
                this.empId = empId;
            }

            public String getEmployeeName() {
                return employeeName;
            }

            public void setEmployeeName(String employeeName) {
                this.employeeName = employeeName;
            }

            public String getEmployeeSerialId() {
                return employeeSerialId;
            }

            public void setEmployeeSerialId(String employeeSerialId) {
                this.employeeSerialId = employeeSerialId;
            }

            public String getFailurePunch() {
                return failurePunch;
            }

            public void setFailurePunch(String failurePunch) {
                this.failurePunch = failurePunch;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getLasttimes() {
                return lasttimes;
            }

            public void setLasttimes(String lasttimes) {
                this.lasttimes = lasttimes;
            }

            public String getLeavetimes() {
                return leavetimes;
            }

            public void setLeavetimes(String leavetimes) {
                this.leavetimes = leavetimes;
            }

            public String getPositionName() {
                return positionName;
            }

            public void setPositionName(String positionName) {
                this.positionName = positionName;
            }

            public String getSignType() {
                return signType;
            }

            public void setSignType(String signType) {
                this.signType = signType;
            }

            public String getStoreId() {
                return storeId;
            }

            public void setStoreId(String storeId) {
                this.storeId = storeId;
            }

            public List<String> getEarlyTime() {
                return earlyTime;
            }

            public void setEarlyTime(List<String> earlyTime) {
                this.earlyTime = earlyTime;
            }

            public List<String> getLateTime() {
                return lateTime;
            }

            public void setLateTime(List<String> lateTime) {
                this.lateTime = lateTime;
            }

            public List<String> getNotSign() {
                return notSign;
            }

            public void setNotSign(List<String> notSign) {
                this.notSign = notSign;
            }

            public AbnormalAttendance() {
            }

            @Override
            public String toString() {
                return "AbnormalAttendance{" +
                        "depName='" + depName + '\'' +
                        ", empHead='" + empHead + '\'' +
                        ", empId='" + empId + '\'' +
                        ", employeeName='" + employeeName + '\'' +
                        ", employeeSerialId='" + employeeSerialId + '\'' +
                        ", failurePunch='" + failurePunch + '\'' +
                        ", id='" + id + '\'' +
                        ", lasttimes='" + lasttimes + '\'' +
                        ", leavetimes='" + leavetimes + '\'' +
                        ", positionName='" + positionName + '\'' +
                        ", signType='" + signType + '\'' +
                        ", storeId='" + storeId + '\'' +
                        ", earlyTime=" + earlyTime +
                        ", lateTime=" + lateTime +
                        ", notSign=" + notSign +
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
