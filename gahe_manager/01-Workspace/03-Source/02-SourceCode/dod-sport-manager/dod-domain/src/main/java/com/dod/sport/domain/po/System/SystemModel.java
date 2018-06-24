package com.dod.sport.domain.po.System;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/14.
 */
public class SystemModel implements Serializable{

    private static final long serialVersionUID = 971927787726097957L;

    private String id;
    private String modelSerialId;
    private String modelName;
    private String platform;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}