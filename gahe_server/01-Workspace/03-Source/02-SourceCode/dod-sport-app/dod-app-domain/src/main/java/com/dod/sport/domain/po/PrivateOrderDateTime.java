package com.dod.sport.domain.po;

import java.io.Serializable;
import java.util.List;

/**
 * 接收前端传递的私教预约时间
 * Created by Administrator on 2017/10/13.
 */
public class PrivateOrderDateTime implements Serializable {

    private static final long serialVersionUID = -5285741326419314901L;
    private String id ;            //私教计划id
    private String classDate;      //约课日期
    private String classDatetime;  //约课时间段,以逗号分割
    private List<ResponsePrivateDateTime> responsePrivateDateTimeList;  //时间list
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassDate() {
        return classDate;
    }

    public void setClassDate(String classDate) {
        this.classDate = classDate;
    }

    public String getClassDatetime() {
        return classDatetime;
    }

    public void setClassDatetime(String classDatetime) {
        this.classDatetime = classDatetime;
    }

    public List<ResponsePrivateDateTime> getResponsePrivateDateTimeList() {
        return responsePrivateDateTimeList;
    }

    public void setResponsePrivateDateTimeList(List<ResponsePrivateDateTime> responsePrivateDateTimeList) {
        this.responsePrivateDateTimeList = responsePrivateDateTimeList;
    }
}
