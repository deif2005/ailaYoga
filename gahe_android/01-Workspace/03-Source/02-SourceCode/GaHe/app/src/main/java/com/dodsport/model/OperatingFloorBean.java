package com.dodsport.model;

import java.util.List;

/**
 * Created by Administrator on 2017/7/21.
 */

public class OperatingFloorBean {

    private String grouping;
    private String key1;
    private String key2;
    private String key3;
    private String key4;
    private List<String> data;

    public String getGrouping() {
        return grouping;
    }

    public void setGrouping(String grouping) {
        this.grouping = grouping;
    }

    public String getKey1() {
        return key1;
    }

    public void setKey1(String key1) {
        this.key1 = key1;
    }

    public String getKey2() {
        return key2;
    }

    public void setKey2(String key2) {
        this.key2 = key2;
    }

    public String getKey3() {
        return key3;
    }

    public void setKey3(String key3) {
        this.key3 = key3;
    }

    public String getKey4() {
        return key4;
    }

    public void setKey4(String key4) {
        this.key4 = key4;
    }
    public List<String> getData() {
        return data;
    }
    public void setData(List<String> data) {
        this.data = data;
    }

    public OperatingFloorBean() {
    }



    @Override
    public String toString() {
        return "OperatingFloorBean{" +
                "grouping='" + grouping + '\'' +
                ", key1='" + key1 + '\'' +
                ", key2='" + key2 + '\'' +
                ", key3='" + key3 + '\'' +
                ", key4='" + key4 + '\'' +
                ", data=" + data +
                '}';
    }
}
