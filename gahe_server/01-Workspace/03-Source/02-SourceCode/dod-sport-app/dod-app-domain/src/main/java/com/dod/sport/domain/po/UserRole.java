package com.dod.sport.domain.po;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/9/14.
 */
public class UserRole implements Serializable {

    private static final long serialVersionUID = 3604992242373079991L;

    private String id;
    private String roleCode;
    private String roleName;
    private String modelSerialIdstr;
    private String functionSerialIdstr;
    private String detailSerialIdstr;
    private List<String> modelList;
    private List<String> functionList;
    private List<String> detailList;
    private String platform;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getModelSerialIdstr() {
        return modelSerialIdstr;
    }

    public void setModelSerialIdstr(String modelSerialIdstr) {
        this.modelSerialIdstr = modelSerialIdstr;
    }

    public String getFunctionSerialIdstr() {
        return functionSerialIdstr;
    }

    public void setFunctionSerialIdstr(String functionSerialIdstr) {
        this.functionSerialIdstr = functionSerialIdstr;
    }

    public String getDetailSerialIdstr() {
        return detailSerialIdstr;
    }

    public void setDetailSerialIdstr(String detailSerialIdstr) {
        this.detailSerialIdstr = detailSerialIdstr;
    }

    public List<String> getModelList() {
        return modelList;
    }

    public void setModelList(List<String> modelList) {
        this.modelList = modelList;
    }

    public List<String> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<String> functionList) {
        this.functionList = functionList;
    }

    public List<String> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<String> detailList) {
        this.detailList = detailList;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
