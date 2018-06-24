package com.dod.sport.domain.po.System;

import com.dod.sport.domain.common.StringGZIPUtils;

import java.io.Serializable;

/**
 * Created by defi on 2017-09-19.
 */
public class FunctionDetail implements Serializable {

    private static final long serialVersionUID = 124206667425932233L;
    private String id;
    private String ModelSerialId;
    private String functionSerialId;
    private String detailSerialId;
    private String detailName;
    private String detailUri;
    private String platform;
    private String isPublic;
    private String modelName;
    private String functionName;

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

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(String isPublic) {
        this.isPublic = isPublic;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }
}
