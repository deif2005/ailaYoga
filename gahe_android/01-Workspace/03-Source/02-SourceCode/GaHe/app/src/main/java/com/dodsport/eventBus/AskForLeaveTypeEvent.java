package com.dodsport.eventBus;

/**
 *   发现VIP
 */

public class AskForLeaveTypeEvent {

       private int mPosition;
       private String msg;
       private String Type;

    public AskForLeaveTypeEvent() {
    }

    public AskForLeaveTypeEvent(int mPosition, String msg) {
        this.mPosition = mPosition;
        this.msg = msg;
    }


    public int getmPosition() {
        return mPosition;
    }

    public void setmPosition(int mPosition) {
        this.mPosition = mPosition;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getPosition() {
        return mPosition;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    @Override
    public String toString() {
        return "AskForLeaveTypeEvent{" +
                "mPosition=" + mPosition +
                ", msg='" + msg + '\'' +
                ", Type='" + Type + '\'' +
                '}';
    }
}
