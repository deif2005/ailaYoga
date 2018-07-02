package com.dodsport.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/9/30.
 *
 * 商家角色列表
 */

public class UserRoleListBean implements Serializable{


    /**
     * datas : {"userRoleList":[{"detailList":[],"detailSerialIdstr":"","functionList":[],"functionSerialIdstr":"0003,0009,0010","id":"58d2985a-c230-4f44-9ce5-195d4ea5f2c6","modelList":[],"modelSerialIdstr":"001,007","platform":"2","roleCode":"ROLE_BOSS","roleName":"老板"},{"detailList":[],"detailSerialIdstr":"","functionList":[],"functionSerialIdstr":"0003,0009,0010","id":"3846d44b-1cc2-4af2-b712-398b21866994","modelList":[],"modelSerialIdstr":"001,007","platform":"2","roleCode":"ROLE_MANAGER","roleName":"店长"},{"detailList":[],"detailSerialIdstr":"","functionList":[],"functionSerialIdstr":"0003,0009,0010","id":"afed24d9-1283-4e85-b49b-8de42e5d49a3","modelList":[],"modelSerialIdstr":"001,007","platform":"2","roleCode":"ROLE_TEACHER","roleName":"老师"},{"detailList":[],"detailSerialIdstr":"","functionList":[],"functionSerialIdstr":"0001,0004,0005,0006,0007","id":"dbcff34c-82bd-46c2-a27f-7c0d42b1cf4f","modelList":[],"modelSerialIdstr":"001,002","platform":"2","roleCode":"ROLE_EMPLOYEE","roleName":"员工"}]}
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

    public UserRoleListBean() {
    }

    public static class DatasBean implements Serializable{
        private List<UserRoleList> userRoleList;

        public List<UserRoleList> getUserRoleList() {
            return userRoleList;
        }

        public void setUserRoleList(List<UserRoleList> userRoleList) {
            this.userRoleList = userRoleList;
        }

        public DatasBean() {
        }

        public static class UserRoleList implements Serializable{
            /**
             * detailList : []
             * detailSerialIdstr :
             * functionList : []
             * functionSerialIdstr : 0003,0009,0010
             * id : 58d2985a-c230-4f44-9ce5-195d4ea5f2c6
             * modelList : []
             * modelSerialIdstr : 001,007
             * platform : 2
             * roleCode : ROLE_BOSS
             * roleName : 老板
             */

            private String detailSerialIdstr;
            private String functionSerialIdstr;
            private String id;
            private String modelSerialIdstr;
            private String platform;
            private String roleCode;
            private String roleName;
            private List<?> detailList;
            private List<?> functionList;
            private List<?> modelList;

            public String getDetailSerialIdstr() {
                return detailSerialIdstr;
            }

            public void setDetailSerialIdstr(String detailSerialIdstr) {
                this.detailSerialIdstr = detailSerialIdstr;
            }

            public String getFunctionSerialIdstr() {
                return functionSerialIdstr;
            }

            public void setFunctionSerialIdstr(String functionSerialIdstr) {
                this.functionSerialIdstr = functionSerialIdstr;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getModelSerialIdstr() {
                return modelSerialIdstr;
            }

            public void setModelSerialIdstr(String modelSerialIdstr) {
                this.modelSerialIdstr = modelSerialIdstr;
            }

            public String getPlatform() {
                return platform;
            }

            public void setPlatform(String platform) {
                this.platform = platform;
            }

            public String getRoleCode() {
                return roleCode;
            }

            public void setRoleCode(String roleCode) {
                this.roleCode = roleCode;
            }

            public String getRoleName() {
                return roleName;
            }

            public void setRoleName(String roleName) {
                this.roleName = roleName;
            }

            public List<?> getDetailList() {
                return detailList;
            }

            public void setDetailList(List<?> detailList) {
                this.detailList = detailList;
            }

            public List<?> getFunctionList() {
                return functionList;
            }

            public void setFunctionList(List<?> functionList) {
                this.functionList = functionList;
            }

            public List<?> getModelList() {
                return modelList;
            }

            public void setModelList(List<?> modelList) {
                this.modelList = modelList;
            }

            public UserRoleList() {
            }

            @Override
            public String toString() {
                return "UserRoleList{" +
                        "detailSerialIdstr='" + detailSerialIdstr + '\'' +
                        ", functionSerialIdstr='" + functionSerialIdstr + '\'' +
                        ", id='" + id + '\'' +
                        ", modelSerialIdstr='" + modelSerialIdstr + '\'' +
                        ", platform='" + platform + '\'' +
                        ", roleCode='" + roleCode + '\'' +
                        ", roleName='" + roleName + '\'' +
                        ", detailList=" + detailList +
                        ", functionList=" + functionList +
                        ", modelList=" + modelList +
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
