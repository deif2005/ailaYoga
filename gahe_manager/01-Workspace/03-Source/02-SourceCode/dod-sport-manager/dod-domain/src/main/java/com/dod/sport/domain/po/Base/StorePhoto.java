package com.dod.sport.domain.po.Base;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2017/10/9
 * \* Description:
 * \
 */
public class StorePhoto {

    private String id;
    private String doorPhoto;           //'门头照'
    private String classroomPhotos;  //教室照片
    private String receptionPhotos;  //'前台照'
    private String businessLicense; //'营业执照'
    private String teacherPhoto;   //'老师照'
    private String classPhotos;    //'上课照片'

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoorPhoto() {
        return doorPhoto;
    }

    public void setDoorPhoto(String doorPhoto) {
        this.doorPhoto = doorPhoto;
    }

    public String getClassroomPhotos() {
        return classroomPhotos;
    }

    public void setClassroomPhotos(String classroomPhotos) {
        this.classroomPhotos = classroomPhotos;
    }

    public String getReceptionPhotos() {
        return receptionPhotos;
    }

    public void setReceptionPhotos(String receptionPhotos) {
        this.receptionPhotos = receptionPhotos;
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    public String getTeacherPhoto() {
        return teacherPhoto;
    }

    public void setTeacherPhoto(String teacherPhoto) {
        this.teacherPhoto = teacherPhoto;
    }

    public String getClassPhotos() {
        return classPhotos;
    }

    public void setClassPhotos(String classPhotos) {
        this.classPhotos = classPhotos;
    }
}