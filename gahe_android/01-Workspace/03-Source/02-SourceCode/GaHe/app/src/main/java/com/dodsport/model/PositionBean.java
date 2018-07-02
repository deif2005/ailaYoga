package com.dodsport.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/9/7.
 *
 * 岗位
 */
public class PositionBean implements Serializable {


    /**
     * datas : {"positionList":[{"businessId":"80a2aa90-2205-4453-8760-b395fb48746a","createTime":"2017-09-08 15:23:06.0","creator":"bdc13c70-de9b-47e0-b59d-ea90e8926ee9","id":"32e133ef-4383-4421-afb9-f044f7e5e22e","positionName":"万店瑜伽店长","positionSerialId":"1038","status":"1"},{"businessId":"80a2aa90-2205-4453-8760-b395fb48746a","createTime":"2017-10-24 17:34:00.0","creator":"bdc13c70-de9b-47e0-b59d-ea90e8926ee9","id":"53263d8a-4f5b-4a2b-8d47-eaddf48be212","positionName":"销售顾问","positionSerialId":"1045","status":"1"},{"businessId":"80a2aa90-2205-4453-8760-b395fb48746a","createTime":"2017-10-24 17:34:00.0","creator":"bdc13c70-de9b-47e0-b59d-ea90e8926ee9","id":"b2e5f704-c430-43bd-93b1-963d36ce437d","positionName":"前台","positionSerialId":"1046","status":"1"},{"businessId":"80a2aa90-2205-4453-8760-b395fb48746a","createTime":"2017-09-08 15:22:36.0","creator":"bdc13c70-de9b-47e0-b59d-ea90e8926ee9","id":"bdc6b17d-c62f-4bf8-8dae-fb7ff8efed12","positionName":"万店瑜伽高级教练","positionSerialId":"1036","status":"1"},{"businessId":"80a2aa90-2205-4453-8760-b395fb48746a","createTime":"2017-10-24 17:29:25.0","creator":"bdc13c70-de9b-47e0-b59d-ea90e8926ee9","id":"cb7f190b-c9ef-4574-b5a4-1de018fc0b04","positionName":"运动职位1","positionSerialId":"1044","status":"1"},{"businessId":"80a2aa90-2205-4453-8760-b395fb48746a","createTime":"2017-10-13 17:23:59.0","creator":"bdc13c70-de9b-47e0-b59d-ea90e8926ee9","id":"d78dc1ef-7db5-48b1-acbb-b79fbe5a5d0b","positionName":"商家执行官","positionSerialId":"1041","status":"1"},{"businessId":"80a2aa90-2205-4453-8760-b395fb48746a","createTime":"2017-09-08 15:22:47.0","creator":"bdc13c70-de9b-47e0-b59d-ea90e8926ee9","id":"e9f99e8e-7b7e-4a1c-9b85-aed441471501","positionName":"万店瑜伽中级老师","positionSerialId":"1037","status":"1"}]}
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

    public PositionBean() {
    }

    public static class DatasBean implements Serializable{
        private List<PositionListBean> positionList;

        public List<PositionListBean> getPositionList() {
            return positionList;
        }

        public void setPositionList(List<PositionListBean> positionList) {
            this.positionList = positionList;
        }

        public DatasBean() {
        }

        public static class PositionListBean implements Serializable{
            /**
             * businessId : 80a2aa90-2205-4453-8760-b395fb48746a
             * createTime : 2017-09-08 15:23:06.0
             * creator : bdc13c70-de9b-47e0-b59d-ea90e8926ee9
             * id : 32e133ef-4383-4421-afb9-f044f7e5e22e
             * positionName : 万店瑜伽店长
             * positionSerialId : 1038
             * status : 1
             */

            private String businessId;
            private String createTime;
            private String creator;
            private String id;
            private String positionName;
            private String positionSerialId;
            private String status;

            public String getBusinessId() {
                return businessId;
            }

            public void setBusinessId(String businessId) {
                this.businessId = businessId;
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

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPositionName() {
                return positionName;
            }

            public void setPositionName(String positionName) {
                this.positionName = positionName;
            }

            public String getPositionSerialId() {
                return positionSerialId;
            }

            public void setPositionSerialId(String positionSerialId) {
                this.positionSerialId = positionSerialId;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public PositionListBean() {
            }

            @Override
            public String toString() {
                return "PositionListBean{" +
                        "businessId='" + businessId + '\'' +
                        ", createTime='" + createTime + '\'' +
                        ", creator='" + creator + '\'' +
                        ", id='" + id + '\'' +
                        ", positionName='" + positionName + '\'' +
                        ", positionSerialId='" + positionSerialId + '\'' +
                        ", status='" + status + '\'' +
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
