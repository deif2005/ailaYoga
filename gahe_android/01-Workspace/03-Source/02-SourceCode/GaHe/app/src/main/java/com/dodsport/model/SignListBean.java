package com.dodsport.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/10/25.
 *
 * 考勤记录
 */

public class SignListBean implements Serializable {


    /**
     * datas : {"signList":[{"depName":"万店瑜伽部门2","empId":"1a5e4743-448c-4d2c-9902-e7b866123136","employeeName":"","employeeSerialId":"","failurePunch":"31","id":"858520b7-4f10-432b-8bdc-d09f517c5c83","lasttimes":"0","leavetimes":"0","positionName":"万店瑜伽中级老师","storeId":"5565f926-eb45-47c5-a297-ad2101533b19"},{"depName":"万店瑜伽部门2","empId":"ea0d91de-10c7-4a2f-a1b1-0a0a16938a0c","employeeName":"","employeeSerialId":"","failurePunch":"31","id":"858520b7-4f10-432b-8bdc-d09f517c5c83","lasttimes":"0","leavetimes":"0","positionName":"万店瑜伽中级老师","storeId":"5565f926-eb45-47c5-a297-ad2101533b19"},{"depName":"万店瑜伽部门2","empId":"4540082b-7f12-4bf3-ae24-bc40e5c6742d","employeeName":"","employeeSerialId":"","failurePunch":"31","id":"858520b7-4f10-432b-8bdc-d09f517c5c83","lasttimes":"0","leavetimes":"0","positionName":"万店瑜伽中级老师","storeId":"5565f926-eb45-47c5-a297-ad2101533b19"},{"depName":"万店瑜伽部门1","empId":"21c07f85-f6c6-4290-b79c-d6531be38fd9","employeeName":"","employeeSerialId":"","failurePunch":"31","id":"0c3858e8-8155-4056-a1b3-c2ab683dc0f2","lasttimes":"0","leavetimes":"0","positionName":"万店瑜伽老板","storeId":"5565f926-eb45-47c5-a297-ad2101533b19"},{"depName":"万店瑜伽部门1","empId":"5085eead-2627-4b58-9510-06e08ebda440","employeeName":"","employeeSerialId":"","failurePunch":"31","id":"0c3858e8-8155-4056-a1b3-c2ab683dc0f2","lasttimes":"0","leavetimes":"0","positionName":"万店瑜伽老板","storeId":"5565f926-eb45-47c5-a297-ad2101533b19"},{"depName":"万店瑜伽部门1","empId":"040a955c-6fff-4beb-b3fe-04634962fbb2","employeeName":"","employeeSerialId":"","failurePunch":"31","id":"0c3858e8-8155-4056-a1b3-c2ab683dc0f2","lasttimes":"0","leavetimes":"0","positionName":"万店瑜伽中级老师","storeId":"5565f926-eb45-47c5-a297-ad2101533b19"},{"depName":"万店瑜伽部门1","empId":"1ed8bf4d-c488-45d9-875b-e1f4c1277604","employeeName":"","employeeSerialId":"","failurePunch":"31","id":"0c3858e8-8155-4056-a1b3-c2ab683dc0f2","lasttimes":"0","leavetimes":"0","positionName":"万店瑜伽中级老师","storeId":"5565f926-eb45-47c5-a297-ad2101533b19"},{"depName":"万店瑜伽部门2","empId":"72f9ff14-df88-45fb-9bd4-ce3bf985e9c2","employeeName":"","employeeSerialId":"","failurePunch":"31","id":"858520b7-4f10-432b-8bdc-d09f517c5c83","lasttimes":"0","leavetimes":"0","positionName":"万店瑜伽店长","storeId":"5565f926-eb45-47c5-a297-ad2101533b19"},{"depName":"万店瑜伽部门3","empId":"731b7df7-bb0e-4c35-af12-80e267c2bb6d","employeeName":"","employeeSerialId":"","failurePunch":"31","id":"020f141e-b611-43da-a3f3-14522f80abd0","lasttimes":"0","leavetimes":"0","positionName":"万店瑜伽店长","storeId":"5565f926-eb45-47c5-a297-ad2101533b19"},{"depName":"万店瑜伽部门2","empId":"6c3932b4-4be8-4805-8eb1-45694dbbc0aa","employeeName":"","employeeSerialId":"","failurePunch":"31","id":"858520b7-4f10-432b-8bdc-d09f517c5c83","lasttimes":"0","leavetimes":"0","positionName":"万店瑜伽中级老师","storeId":"5565f926-eb45-47c5-a297-ad2101533b19"},{"depName":"万店瑜伽部门2","empId":"ad900feb-d402-448d-95fb-96fee4580a9e","employeeName":"","employeeSerialId":"","failurePunch":"31","id":"858520b7-4f10-432b-8bdc-d09f517c5c83","lasttimes":"0","leavetimes":"0","positionName":"万店瑜伽中级老师","storeId":"5565f926-eb45-47c5-a297-ad2101533b19"},{"depName":"万店瑜伽部门1","empId":"ba07c8d6-23d6-476c-a406-c65d3dff4769","employeeName":"","employeeSerialId":"","failurePunch":"31","id":"0c3858e8-8155-4056-a1b3-c2ab683dc0f2","lasttimes":"0","leavetimes":"0","positionName":"万店瑜伽中级老师","storeId":"5565f926-eb45-47c5-a297-ad2101533b19"},{"depName":"万店瑜伽部门1","empId":"44431f2e-fcb6-4699-9787-1a5029ff75cc","employeeName":"","employeeSerialId":"","failurePunch":"31","id":"0c3858e8-8155-4056-a1b3-c2ab683dc0f2","lasttimes":"0","leavetimes":"0","positionName":"万店瑜伽高级教练","storeId":"5565f926-eb45-47c5-a297-ad2101533b19"},{"depName":"万店瑜伽部门1","empId":"d91afb49-088a-4bc9-b4ad-79178675cb2b","employeeName":"","employeeSerialId":"","failurePunch":"31","id":"0c3858e8-8155-4056-a1b3-c2ab683dc0f2","lasttimes":"0","leavetimes":"0","positionName":"万店瑜伽中级老师","storeId":"5565f926-eb45-47c5-a297-ad2101533b19"},{"depName":"万店瑜伽部门2","empId":"dc3719b2-6f6f-4b38-9736-02e2d4e8936a","employeeName":"","employeeSerialId":"","failurePunch":"31","id":"858520b7-4f10-432b-8bdc-d09f517c5c83","lasttimes":"2","leavetimes":"0","positionName":"万店瑜伽中级老师","storeId":"5565f926-eb45-47c5-a297-ad2101533b19"},{"depName":"万店瑜伽部门2","empId":"85a4967a-a481-4798-a169-5e415df47b30","employeeName":"","employeeSerialId":"","failurePunch":"31","id":"858520b7-4f10-432b-8bdc-d09f517c5c83","lasttimes":"0","leavetimes":"0","positionName":"万店瑜伽中级老师","storeId":"5565f926-eb45-47c5-a297-ad2101533b19"}]}
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

    public static class DatasBean implements Serializable {
        private List<SignList> signList;

        public List<SignList> getSignList() {
            return signList;
        }


        public void setSignList(List<SignList> signList) {
            this.signList = signList;
        }

        public DatasBean() {
        }

        public static class SignList implements Serializable {


            /**
             * depName : 万店瑜伽部门2
             * empHead : http://192.168.1.186:8088/dod-staticnull
             * empId : 85a4967a-a481-4798-a169-5e415df47b30
             * employeeName : 理老师
             * employeeSerialId : 00002
             * failurePunch : 31
             * id : eb6a8ed7-5629-46e8-af43-9581939daa83
             * lasttimes : 0
             * leavetimes : 0
             * positionName : 万店瑜伽中级老师
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
            private String storeId;

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

            public String getStoreId() {
                return storeId;
            }

            public void setStoreId(String storeId) {
                this.storeId = storeId;
            }

            public SignList() {
            }

            @Override
            public String toString() {
                return "SignList{" +
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
                        ", storeId='" + storeId + '\'' +
                        '}';
            }
        }
    }
        public static class ResultBean implements Serializable {
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
