package com.dod.sport.domain.po;

import com.dod.sport.domain.po.Base.StoreInfo;

import java.io.Serializable;

/**
 * 门店业务返回pojo
 * Created by Administrator on 2017/10/23.
 */
public class ResponseStore extends StoreInfo implements Serializable {
    private static final long serialVersionUID = -2366367451486795819L;
    private String cid;      //用于判断该对象是否是会员卡关联门店:有值时,是关联门店,无值时非关联门店

    public String getCid() {
        return cid;
    }
    public void setCid(String cid) {
        this.cid = cid;
    }
}
