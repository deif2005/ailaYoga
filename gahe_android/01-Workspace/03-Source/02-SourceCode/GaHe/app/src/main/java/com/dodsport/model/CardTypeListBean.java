package com.dodsport.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/9/14.
 *
 * 会员卡类型 实体类
 */

public class CardTypeListBean implements Serializable {


    /**
     * datas : {"memberCardTypeList":[{"createTime":"2017-09-11 09:49:35.0","creator":"bdc13c70-de9b-47e0-b59d-ea90e8926ee9","id":"189404b7-1e89-4480-af80-337639db326e","membcardNumber":0,"membcardTypeName":"次卡","membcardtypeSerialId":"000024","status":"1"},{"createTime":"2017-09-11 09:49:20.0","creator":"bdc13c70-de9b-47e0-b59d-ea90e8926ee9","id":"522c39ac-f25d-4810-9591-b556f041014b","membcardNumber":0,"membcardTypeName":"期限卡","membcardtypeSerialId":"000023","status":"1"}]}
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

    public CardTypeListBean() {
    }

    public static class DatasBean implements Serializable{
        private List<MemberCardTypeListBean> memberCardTypeList;

        public List<MemberCardTypeListBean> getMemberCardTypeList() {
            return memberCardTypeList;
        }

        public void setMemberCardTypeList(List<MemberCardTypeListBean> memberCardTypeList) {
            this.memberCardTypeList = memberCardTypeList;
        }

        public DatasBean() {
        }

        @Override
        public String toString() {
            return "DatasBean{" +
                    "memberCardTypeList=" + memberCardTypeList +
                    '}';
        }

        public static class MemberCardTypeListBean implements Serializable{
            /**
             * createTime : 2017-09-11 09:49:35.0
             * creator : bdc13c70-de9b-47e0-b59d-ea90e8926ee9
             * id : 189404b7-1e89-4480-af80-337639db326e
             * membcardNumber : 0
             * membcardTypeName : 次卡
             * membcardtypeSerialId : 000024
             * status : 1
             */

            private String createTime;
            private String creator;
            private String id;
            private int membcardNumber;
            private String membcardTypeName;
            private String membcardtypeSerialId;
            private String status;

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

            public int getMembcardNumber() {
                return membcardNumber;
            }

            public void setMembcardNumber(int membcardNumber) {
                this.membcardNumber = membcardNumber;
            }

            public String getMembcardTypeName() {
                return membcardTypeName;
            }

            public void setMembcardTypeName(String membcardTypeName) {
                this.membcardTypeName = membcardTypeName;
            }

            public String getMembcardtypeSerialId() {
                return membcardtypeSerialId;
            }

            public void setMembcardtypeSerialId(String membcardtypeSerialId) {
                this.membcardtypeSerialId = membcardtypeSerialId;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public MemberCardTypeListBean() {
            }

            @Override
            public String toString() {
                return "MemberCardTypeListBean{" +
                        "createTime='" + createTime + '\'' +
                        ", creator='" + creator + '\'' +
                        ", id='" + id + '\'' +
                        ", membcardNumber=" + membcardNumber +
                        ", membcardTypeName='" + membcardTypeName + '\'' +
                        ", membcardtypeSerialId='" + membcardtypeSerialId + '\'' +
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
