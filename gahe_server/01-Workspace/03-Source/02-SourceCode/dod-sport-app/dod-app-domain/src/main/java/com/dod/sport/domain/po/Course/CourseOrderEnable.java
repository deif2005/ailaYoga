package com.dod.sport.domain.po.Course;

import java.io.Serializable;

/**
 * Created by defi on 2017-10-11.
 */
public class CourseOrderEnable implements Serializable {

    private static final long serialVersionUID = 6533882496377483377L;

    private String id;     //签到表id
    private String courseplanId; //排课id
    private String classDatetime; //开课时间
    private String classStatus;   //课程状态：1未开课，2已开课，3已取消
    private String enable;        //启用状态：1启用，2禁用
    private String permitPersons;  //可预约人数

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPermitPersons() {
        return permitPersons;
    }

    public void setPermitPersons(String permitPersons) {
        this.permitPersons = permitPersons;
    }

    public String getCourseplanId() {
        return courseplanId;
    }

    public void setCourseplanId(String courseplanId) {
        this.courseplanId = courseplanId;
    }

    public String getClassDatetime() {
        return classDatetime;
    }

    public void setClassDatetime(String classDatetime) {
        this.classDatetime = classDatetime;
    }

    public String getClassStatus() {
        return classStatus;
    }

    public void setClassStatus(String classStatus) {
        this.classStatus = classStatus;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }
}
