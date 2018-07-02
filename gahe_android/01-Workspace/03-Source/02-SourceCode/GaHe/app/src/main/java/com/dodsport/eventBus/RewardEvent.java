package com.dodsport.eventBus;

import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 打赏的EventBus
 */

public class RewardEvent {

    private String id;
    private int money;
    private String sendUserId;
    private LinearLayout mLinearLayout;
    private TextView mTextView;
    private String identification;

    public RewardEvent() {
    }

    public RewardEvent(String identification){
        this.identification = identification;
    }

    public RewardEvent(String id,String sendUserId,int money,LinearLayout mLinearLayout,TextView mTextView,String identification) {
        this.id = id;
        this.sendUserId = sendUserId;
        this.money = money;
        this.mLinearLayout = mLinearLayout;
        this.mTextView = mTextView;
        this.identification =identification;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(String sendUserId) {
        this.sendUserId = sendUserId;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public LinearLayout getLinearLayout() {
        return mLinearLayout;
    }

    public void setLinearLayout(LinearLayout linearLayout) {
        mLinearLayout = linearLayout;
    }

    public TextView getTextView() {
        return mTextView;
    }

    public void setTextView(TextView textView) {
        mTextView = textView;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    @Override
    public String toString() {
        return "RewardEvent{" +
                "id='" + id + '\'' +
                ", money=" + money +
                ", sendUserId='" + sendUserId + '\'' +
                ", identification='" + identification + '\'' +
                '}';
    }
}
