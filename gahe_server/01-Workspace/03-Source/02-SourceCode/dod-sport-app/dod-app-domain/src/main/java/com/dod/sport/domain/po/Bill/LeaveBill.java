package com.dod.sport.domain.po.Bill;

import com.dod.sport.domain.po.Bill.BaseBill;

/**
 * Created by defi on 2017-08-16.
 * 离职单pojo
 */
public class LeaveBill extends BaseBill {

//    private String startTime;      //入职日期
//    private String endTime;        //离职日期
    private String handler;        //交接人
    private String handlerName;    //交接人姓名
    private String leaveReason;    //离职原因
    private String handleItem;     //所需交接事项
    private String postionName;    //职位名称

//    public String getStartTime() {
//        return startTime;
//    }
//
//    public void setStartTime(String startTime) {
//        this.startTime = startTime;
//    }
//
//    public String getEndTime() {
//        return endTime;
//    }
//
//    public void setEndTime(String endTime) {
//        this.endTime = endTime;
//    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public String getLeaveReason() {
        return leaveReason;
    }

    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
    }

    public String getHandleItem() {
        return handleItem;
    }

    public void setHandleItem(String handleItem) {
        this.handleItem = handleItem;
    }

    public String getHandlerName() {
        return handlerName;
    }

    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName;
    }

    public String getPostionName() {
        return postionName;
    }

    public void setPostionName(String postionName) {
        this.postionName = postionName;
    }
}
