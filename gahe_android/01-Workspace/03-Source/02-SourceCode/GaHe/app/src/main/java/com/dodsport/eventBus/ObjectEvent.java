package com.dodsport.eventBus;

import com.dodsport.model.MemberMyCardListBean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/22.
 */

public class ObjectEvent implements Serializable {

    private String Type;
    private MemberMyCardListBean.DatasBean.MembercardrelationListBean mMemberCardBean;

    public ObjectEvent() {
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public MemberMyCardListBean.DatasBean.MembercardrelationListBean getMemberCardBean() {
        return mMemberCardBean;
    }

    public void setMemberCardBean(MemberMyCardListBean.DatasBean.MembercardrelationListBean memberCardBean) {
        mMemberCardBean = memberCardBean;
    }

    @Override
    public String toString() {
        return "ObjectEvent{" +
                "Type='" + Type + '\'' +
                ", mMemberCardBean=" + mMemberCardBean +
                '}';
    }
}
