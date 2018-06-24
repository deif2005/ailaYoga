package com.dod.sport.domain.po.Base;

import com.dod.sport.domain.po.Photo;

import java.io.Serializable;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Administrator
 * \* Date: 2017/9/8
 * \* Time: 10:00
 * \* To change this template use File | Settings | File Templates.
 * \* Description:相册表
 * \
 */
public class Album implements Serializable{
    private static final long serialVersionUID = -201604831622080982L;

    private  String id ;
    private  String albumName; //相册名称
    private  String storeId;   //门店ID
    private  String describes;  //描述
    private  String creator;    //创建人
    private  String url;    //照片UrL
    private List<Photo> photoList;  //照片

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Photo> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<Photo> photoList) {
        this.photoList = photoList;
    }

    public String getCreator() {return creator;}

    public void setCreator(String creator) {this.creator = creator;}

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getAlbumName() {return albumName;}

    public void setAlbumName(String albumName) {this.albumName = albumName;}

    public String getDescribes() {return describes;}

    public void setDescribes(String describes) {this.describes = describes;}

    public String getStoreId() {return storeId;}

    public void setStoreId(String storeId) {this.storeId = storeId;}
}