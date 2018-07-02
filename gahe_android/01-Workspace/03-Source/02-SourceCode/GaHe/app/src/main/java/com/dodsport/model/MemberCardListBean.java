package com.dodsport.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/9/14.
 *
 * 会员卡数据列表
 */

public class MemberCardListBean implements Serializable {


    /**
     * datas : {"memberCardList":[{"businessId":"","createTime":"2017-09-11 09:53:07.0","creator":"bdc13c70-de9b-47e0-b59d-ea90e8926ee9","id":"77c970ec-1283-4e87-8ce1-618ee4a5d6bd","membcardName":"VIP私教课尊享卡","membcardSerialId":"022","membcardTypeId":"189404b7-1e89-4480-af80-337639db326e","membcardTypeName":"次卡","remark":"","status":"1"},{"businessId":"","createTime":"2017-09-11 14:34:02.0","creator":"bdc13c70-de9b-47e0-b59d-ea90e8926ee9","id":"88825dcc-3584-4064-a6fe-4b482097f31c","membcardName":"至尊股东会员卡","membcardSerialId":"024","membcardTypeId":"522c39ac-f25d-4810-9591-b556f041014b","membcardTypeName":"期限卡","remark":"","status":"1"},{"businessId":"","createTime":"2017-09-11 09:52:16.0","creator":"bdc13c70-de9b-47e0-b59d-ea90e8926ee9","id":"c47658bb-4b81-42fe-a156-4ef04d706476","membcardName":"VIP特色课程尊享卡","membcardSerialId":"021","membcardTypeId":"189404b7-1e89-4480-af80-337639db326e","membcardTypeName":"次卡","remark":"","status":"1"},{"businessId":"","createTime":"2017-09-14 11:46:36.0","creator":"ddcbc213-3145-4b5d-a068-144c0b06706f","id":"f80a3d10-900b-455f-97c2-8d0c61b87c33","membcardName":"ceshi","membcardSerialId":"049","membcardTypeId":"189404b7-1e89-4480-af80-337639db326e","membcardTypeName":"次卡","remark":"","status":"1"},{"businessId":"","createTime":"2017-09-11 14:33:40.0","creator":"bdc13c70-de9b-47e0-b59d-ea90e8926ee9","id":"fa51e1d4-da4c-4c44-908d-0874fc3c8d78","membcardName":"VIP钻石会员卡","membcardSerialId":"023","membcardTypeId":"522c39ac-f25d-4810-9591-b556f041014b","membcardTypeName":"期限卡","remark":"","status":"1"}]}
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

    public MemberCardListBean() {
    }

    @Override
    public String toString() {
        return "MemberCardListBean{" +
                "datas=" + datas +
                ", result=" + result +
                '}';
    }

    public static class DatasBean implements  Serializable{
        private List<MemberCardList> memberCardList;

        public List<MemberCardList> getMemberCardList() {
            return memberCardList;
        }

        public void setMemberCardList(List<MemberCardList> memberCardList) {
            this.memberCardList = memberCardList;
        }

        public DatasBean() {
        }

        @Override
        public String toString() {
            return "DatasBean{" +
                    "memberCardList=" + memberCardList +
                    '}';
        }

        public static class MemberCardList implements Serializable{
            /**
             * businessId :
             * createTime : 2017-09-11 09:53:07.0
             * creator : bdc13c70-de9b-47e0-b59d-ea90e8926ee9
             * id : 77c970ec-1283-4e87-8ce1-618ee4a5d6bd
             * membcardName : VIP私教课尊享卡
             * membcardSerialId : 022
             * membcardTypeId : 189404b7-1e89-4480-af80-337639db326e
             * membcardTypeName : 次卡
             * remark :
             * status : 1
             */

            private String businessId;
            private String createTime;
            private String creator;
            private String id;
            private String membcardName;
            private String membcardSerialId;
            private String membcardTypeId;
            private String membcardTypeName;
            private String remark;
            private String status;
            private String sumcard;
            private String membcardType;

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

            public String getMembcardName() {
                return membcardName;
            }

            public void setMembcardName(String membcardName) {
                this.membcardName = membcardName;
            }

            public String getMembcardSerialId() {
                return membcardSerialId;
            }

            public void setMembcardSerialId(String membcardSerialId) {
                this.membcardSerialId = membcardSerialId;
            }

            public String getMembcardTypeId() {
                return membcardTypeId;
            }

            public void setMembcardTypeId(String membcardTypeId) {
                this.membcardTypeId = membcardTypeId;
            }

            public String getMembcardTypeName() {
                return membcardTypeName;
            }

            public void setMembcardTypeName(String membcardTypeName) {
                this.membcardTypeName = membcardTypeName;
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

            public String getSumcard() {
                return sumcard;
            }

            public void setSumcard(String sumcard) {
                this.sumcard = sumcard;
            }

            public String getMembcardType() {
                return membcardType;
            }

            public void setMembcardType(String membcardType) {
                this.membcardType = membcardType;
            }

            public MemberCardList() {
            }

            @Override
            public String toString() {
                return "MemberCardList{" +
                        "businessId='" + businessId + '\'' +
                        ", createTime='" + createTime + '\'' +
                        ", creator='" + creator + '\'' +
                        ", id='" + id + '\'' +
                        ", membcardName='" + membcardName + '\'' +
                        ", membcardSerialId='" + membcardSerialId + '\'' +
                        ", membcardTypeId='" + membcardTypeId + '\'' +
                        ", membcardTypeName='" + membcardTypeName + '\'' +
                        ", remark='" + remark + '\'' +
                        ", status='" + status + '\'' +
                        ", sumcard='" + sumcard + '\'' +
                        ", membcardType='" + membcardType + '\'' +
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
