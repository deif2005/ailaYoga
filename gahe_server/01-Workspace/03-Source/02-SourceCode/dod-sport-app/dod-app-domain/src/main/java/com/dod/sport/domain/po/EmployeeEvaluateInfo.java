package com.dod.sport.domain.po;

import com.dod.sport.domain.po.Member.MemberEvaluate;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/10/23.
 */
public class EmployeeEvaluateInfo  extends  ResponseEmployee implements Serializable{

    private static final long serialVersionUID = 4123248053611482752L;
    private String avgTeachLevel;                //第几周教学水平平均数
    private String avgServiceLevel;              //第几服务态度平均数
    private String avgEnvironmentlevel;          //第几场馆环境评价平均数
    private String sumAvgTeachLevel;             //总教学水平平均数
    private String sumAvgServiceLevel;            //总服务态度平均数
    private String sumAvgEnvironmentlevel;        //总场馆环境评价平均数
    private List<MemberEvaluate> memberEvaluateList;//会员评价列表

    public String getAvgTeachLevel() {
        return avgTeachLevel;
    }

    public void setAvgTeachLevel(String avgTeachLevel) {
        this.avgTeachLevel = avgTeachLevel;
    }

    public String getAvgServiceLevel() {
        return avgServiceLevel;
    }

    public void setAvgServiceLevel(String avgServiceLevel) {
        this.avgServiceLevel = avgServiceLevel;
    }

    public String getAvgEnvironmentlevel() {
        return avgEnvironmentlevel;
    }

    public void setAvgEnvironmentlevel(String avgEnvironmentlevel) {
        this.avgEnvironmentlevel = avgEnvironmentlevel;
    }

    public String getSumAvgTeachLevel() {
        return sumAvgTeachLevel;
    }

    public void setSumAvgTeachLevel(String sumAvgTeachLevel) {
        this.sumAvgTeachLevel = sumAvgTeachLevel;
    }

    public String getSumAvgServiceLevel() {
        return sumAvgServiceLevel;
    }

    public void setSumAvgServiceLevel(String sumAvgServiceLevel) {
        this.sumAvgServiceLevel = sumAvgServiceLevel;
    }

    public String getSumAvgEnvironmentlevel() {
        return sumAvgEnvironmentlevel;
    }

    public void setSumAvgEnvironmentlevel(String sumAvgEnvironmentlevel) {
        this.sumAvgEnvironmentlevel = sumAvgEnvironmentlevel;
    }

    public List<MemberEvaluate> getMemberEvaluateList() {
        return memberEvaluateList;
    }

    public void setMemberEvaluateList(List<MemberEvaluate> memberEvaluateList) {
        this.memberEvaluateList = memberEvaluateList;
    }
}
