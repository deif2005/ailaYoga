package com.dod.sport.service;

import com.dod.sport.domain.po.*;
import com.dod.sport.domain.po.Base.Album;
import com.dod.sport.domain.po.Base.BusinessInfo;
import com.dod.sport.domain.po.Base.Department;
import com.dod.sport.domain.po.Base.StoreInfo;

import java.util.List;

/**
 * Created by defi on 2017-08-14.
 */
public interface IBusinessService {

    /**
     * 新增商家信息
     * @param businessInfo
     */
    public void addBusinessInfo(BusinessInfo businessInfo);

    /**
     * 获取商家信息列表
     * @return
     */
    public List<BusinessInfo> listBusinessInfo(Integer businessType);

    /**
     * 获取商家信息
     * @param id
     * @return
     */
    public BusinessInfo getBusinessInfoById(String id);

    /**
     * 获取门店列表
     * @param businessId
     * @return
     */
    public List<StoreInfo> listStoreByBusinessId(String businessId);


    /**
     * 新增部门信息
     * @param department
     */
    public void addDepartmentInfo(Department department);

    /**
     * 获取部门信息列表
     * @return
     */
    public List<Department> listDepartment();


    /**
     * 新增商家部门信息
     * @param department
     */
    public void addBusiDepartmentInfo(Department department);

    /**
     * 获取商家部门信息列表
     * @return
     */
    public List<Department> listBusiDepartment(String businessId);

    /**
     * 获取商家部门信息
     * @param id
     * @return
     */
    public Department getBusiDepartmentById(String id);

    /**
     * 获取部门信息
     * @param id
     * @return
     */
    public Department getDepartmentById(String id);

    /**
     * 新增商家信息
     * @param storeInfo
     */
    public void addStoreInfo(StoreInfo storeInfo);

    /**
     * 新增相册信息
     * @param album
     */
    public  List<Album> addAlbumInfo(Album album);

    /**
    **
     * 获取相册信息
    * @param album
    */
    public List<Album> getAlbumList(Album album);

    /**
     **
     * 修改相册信息
     * @param album
     */
    public void updateAlbumById(Album album);

    /**
     **
     * 删除相册信息
     * @param album
     */
    public void deleteAlbumById(Album album);

    /**
     **
     * 新增照片信息
     * @param photo
     */
    public Photo addPhotoInfo(Photo photo);

    /**
     **
     * 获取照片信息
     * @param albumId
     */
    public List<Album> getPhotoList(String albumId );


    /**
     **
     * 删除照片信息
     * @param id
     */
    public void deletePhotoById( String id);

    /**
            **
            * 删除照片信息
    * @param
    * @param
    */
    public void  updatePhotoById(Photo photoId);

    /**
     **
     * 新增门店照片
     * @param
     * @param
     */
    public String  addStorePhotoInfo(StorePhoto storePhoto);



}
