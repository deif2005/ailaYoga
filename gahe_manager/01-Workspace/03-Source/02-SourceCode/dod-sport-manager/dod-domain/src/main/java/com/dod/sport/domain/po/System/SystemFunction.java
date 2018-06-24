package com.dod.sport.domain.po.System;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/14.
 */
public class SystemFunction implements Serializable{

    private static final long serialVersionUID = 5846573233969617676L;

    private String id;
    private String ModelSerialId;
    private String functionSerialId;
    private String functionName;
    private String platform;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModelSerialId() {
        return ModelSerialId;
    }

    public void setModelSerialId(String modelSerialId) {
        ModelSerialId = modelSerialId;
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

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
