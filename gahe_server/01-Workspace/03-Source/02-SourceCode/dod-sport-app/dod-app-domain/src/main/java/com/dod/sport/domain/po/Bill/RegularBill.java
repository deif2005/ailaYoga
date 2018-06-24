package com.dod.sport.domain.po.Bill;

import com.dod.sport.domain.po.Bill.BaseBill;

/**
 * Created by defi on 2017-08-16.
 * 员工转正单pojo
 */
public class RegularBill extends BaseBill {

    private String positionDesc;  //试用期岗位理解
    private String workDesc;      //试用期工作总结
    private String suggestion;    //意见或建议
    private String approver;      //审批人

    public String getPositionDesc() {
        return positionDesc;
    }

    public void setPositionDesc(String positionDesc) {
        this.positionDesc = positionDesc;
    }

    public String getWorkDesc() {
        return workDesc;
    }

    public void setWorkDesc(String workDesc) {
        this.workDesc = workDesc;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    @Override
    public String getApprover() {
        return approver;
    }

    @Override
    public void setApprover(String approver) {
        this.approver = approver;
    }
}
