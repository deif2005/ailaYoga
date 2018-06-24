package com.dod.sport.domain.dto.redis;

import java.io.Serializable;

/**
 * ExamInfoRedis
 * 缓存考试相关信息dto
 * @author yuhao
 * @date 2016/8/8
 */
public class ExamInfoRedis implements Serializable{
    private static final long serialVersionUID = -2587138672432813723L;
    private String examId;
    private String examCode;
    private String examName;
    private String examSiteId;
    private String examSiteCode;
    private String examSiteName;
    private String examRoomId;
    private String examRoomCode;
    private String examRoomName;
    private String examTimeId;
    private String examTimeCode;
    private String examTimeName;
    private String subjectId;
    private String subjectCode;
    private String subjectName;

    public String getExamCode() {
        return examCode;
    }

    public void setExamCode(String examCode) {
        this.examCode = examCode;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getExamRoomCode() {
        return examRoomCode;
    }

    public void setExamRoomCode(String examRoomCode) {
        this.examRoomCode = examRoomCode;
    }

    public String getExamRoomId() {
        return examRoomId;
    }

    public void setExamRoomId(String examRoomId) {
        this.examRoomId = examRoomId;
    }

    public String getExamRoomName() {
        return examRoomName;
    }

    public void setExamRoomName(String examRoomName) {
        this.examRoomName = examRoomName;
    }

    public String getExamSiteCode() {
        return examSiteCode;
    }

    public void setExamSiteCode(String examSiteCode) {
        this.examSiteCode = examSiteCode;
    }

    public String getExamSiteId() {
        return examSiteId;
    }

    public void setExamSiteId(String examSiteId) {
        this.examSiteId = examSiteId;
    }

    public String getExamSiteName() {
        return examSiteName;
    }

    public void setExamSiteName(String examSiteName) {
        this.examSiteName = examSiteName;
    }

    public String getExamTimeCode() {
        return examTimeCode;
    }

    public void setExamTimeCode(String examTimeCode) {
        this.examTimeCode = examTimeCode;
    }

    public String getExamTimeId() {
        return examTimeId;
    }

    public void setExamTimeId(String examTimeId) {
        this.examTimeId = examTimeId;
    }

    public String getExamTimeName() {
        return examTimeName;
    }

    public void setExamTimeName(String examTimeName) {
        this.examTimeName = examTimeName;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
