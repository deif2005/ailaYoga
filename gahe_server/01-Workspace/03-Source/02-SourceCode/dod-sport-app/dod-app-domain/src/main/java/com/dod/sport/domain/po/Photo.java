package com.dod.sport.domain.po;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Administrator
 * \* Date: 2017/9/8
 * \* Time: 11:14
 * \* To change this template use File | Settings | File Templates.
 * \* Description:照片
 * \
 */
public class Photo {

    private static final long serialVersionUID = -201604831622080982L;

    private  String id ;        //id
    private  String albumId;    //相册ID
    private  String creator;    //创建
    private  String url  ;      //  照片url
    private  String storeId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}