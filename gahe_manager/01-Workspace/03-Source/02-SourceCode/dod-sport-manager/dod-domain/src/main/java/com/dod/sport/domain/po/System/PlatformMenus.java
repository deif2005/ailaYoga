package com.dod.sport.domain.po.System;

import java.io.Serializable;

/**
 * Created by defi on 2017-10-30.
 * 平台菜单
 */
public class PlatformMenus implements Serializable{

    private static final long serialVersionUID = 707228585876411382L;

    private String modelSerialId;  //一级菜单模块
    private String modelName;      //模块名称
    private String functionSerialId; //二级功能模块
    private String functionName;   //功能名称
    private String detailSerialId;  //功能细节编号
    private String detailName;      //功能细节名称
    private String detailUri;      //接口地址

    public String getModelSerialId() {
        return modelSerialId;
    }

    public void setModelSerialId(String modelSerialId) {
        this.modelSerialId = modelSerialId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getFunctionSerialId() {
        return functionSerialId;
    }

    public void setFunctionSerialId(String functionSerialId) {
        this.functionSerialId = functionSerialId;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getDetailSerialId() {
        return detailSerialId;
    }

    public void setDetailSerialId(String detailSerialId) {
        this.detailSerialId = detailSerialId;
    }

    public String getDetailName() {
        return detailName;
    }

    public void setDetailName(String detailName) {
        this.detailName = detailName;
    }

    public String getDetailUri() {
        return detailUri;
    }

    public void setDetailUri(String detailUri) {
        this.detailUri = detailUri;
    }
}
