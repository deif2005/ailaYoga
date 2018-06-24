package com.dod.sport.domain.po.Course;

import java.io.Serializable;

/**
 * Created by defi on 2017-09-05.
 * 课程-卡关系pojo
 */
public class CourseAndCard implements Serializable {

    private static final long serialVersionUID = 1240823469904260635L;
    private String id;
    private String courseId;    //课程id
    private String membcardId;  //会员卡id
    private String membcardName; //会员卡名称
    private Integer membcardType;  //1.次卡;2.期限卡
    private String enable;       //是否启用1未启用，2启用

    public Integer getMembcardType() {
        return membcardType;
    }

    public void setMembcardType(Integer membcardType) {
        this.membcardType = membcardType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getMembcardId() {
        return membcardId;
    }

    public void setMembcardId(String membcardId) {
        this.membcardId = membcardId;
    }

    public String getMembcardName() {
        return membcardName;
    }

    public void setMembcardName(String membcardName) {
        this.membcardName = membcardName;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }
}
