package com.dod.sport.dao;

import com.dod.sport.domain.po.*;
import com.dod.sport.domain.po.Base.*;
import com.dod.sport.domain.po.Base.StoreInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by defi on 2017-08-14.
 * 商家相关信息dao
 */
@Repository
public interface IBusinessDao {

    /**
     * 获取商家信息列表
     * @return
     * 漏加@Param 报异常
     */
    public List<BusinessInfo> listBusinessInfo(@Param("businessType") Integer businessType);

    /**
     * 增加商家信息
     * @param businessInfo
     */
    public void addBusinessInfo(BusinessInfo businessInfo);

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

    public StoreInfo getStoreInfoById(String id);

    /**
     * 获取手机号对应商家列表
     * @param phoneNum
     * @return
     */
    public List<BusinessInfo> getBusinessListByPhoneNum(String phoneNum);

    /**
     * 获取部门信息列表
     * @return
     */
    public List<Department> listDepartmentInfo();

    /**
     * 增加部门信息
     * @param department
     */
    public void addDepartmentInfo(Department department);

    /**
     * 获取部门信息
     * @param id
     * @return
     */
    public Department getDepartmentInfoById(String id);

    /**
     * 获取商家部门信息列表
     * @return
     */
    public List<Department> listBusiDepartmentInfo(String businessId);

    /**
     * 增加商家部门信息
     * @param department
     */
    public void addBusiDepartmentInfo(Department department);

    /**
     * 获取商家部门信息
     * @param id
     * @return
     */
    public Department getBusiDepartmentInfoById(String id);

    /**
     * 获取商家的部门列表
     * @param businessId
     * @return
     */
    public List<Department>getBusiDepartmentListByBusinessId(String businessId);

    /**
     * 获取部门最大编号
     * @return
     */
    public String getMaxDepartmentId();

    /**
     * 获取商家部门最大编号
     * @return
     */
    public String getMaxBusiDepartmentId(String businessId);

    /**
     * 获取商家最大编号
     * @return
     */
    public String getMaxBusinessId();

    /**
     * 推荐门店信息
     * @return
     */
    public void addStoreInfo(StoreInfo storeInfo);

    /**
     * 获取商家最大门店编号
     * @param businessId
     * @return
     */
    public String getMaxStoreId(String businessId);

     /**
     * 获取商家最大门店编号
     * @param album
     * @return
     */
    public void addAlbumInfo(Album album);

     /**
     * 获取商相册
     * @param album
     * @return
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
    public void addPhotoInfo(Photo photo);

   /**
    * * 获取照片信息
    * @param photo
    */
    public List<Album> getPhotoList( Photo photo);

    /**
     * * 根据 ID获取照片信息
    * @param id
     */
    public Photo getPhotoListById( String id);

    /**
     **
     * 修改相册信息
     * @param photo
     */
    public void updatePhotoById(Photo photo);

    /**
     **
     * 删除相册信息
     * @param photo
     */
    public void deletePhotoById(Photo photo);

    /**
     **
     * 新增门店照片
     * @param
     * @param
     */
    public void addStorePhotoInfo(StorePhoto storePhoto);


}
