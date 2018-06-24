package com.dod.sport.service.impl;

import com.dod.sport.config.Configuration;
import com.dod.sport.dao.IBusinessDao;
import com.dod.sport.domain.po.*;
import com.dod.sport.domain.po.Base.Album;
import com.dod.sport.domain.po.Base.BusinessInfo;
import com.dod.sport.domain.po.Base.Department;
import com.dod.sport.domain.po.Base.StoreInfo;
import com.dod.sport.service.IBusinessService;
import com.dod.sport.util.RedisCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.List;
import java.util.UUID;

/**
 * Created by defi on 2017-08-14.
 * 商家信息相关业务
 */
@Service
public class BusinessServiceImpl implements IBusinessService {

    @Autowired
    IBusinessDao businessDao;

    /**
     * 新增商家信息
     * @param businessInfo
     */
    @Override
    public void addBusinessInfo(BusinessInfo businessInfo){
        String maxId = RedisCommon.getMaxBusinessId(businessInfo.getBusinessType());
        if ("".equals(maxId)){
            maxId = RedisCommon.setAndReturnMaxBusinessId(businessInfo.getBusinessType(),
                    businessDao.getMaxBusinessId());
        }
        businessInfo.setId(UUID.randomUUID().toString());
        businessInfo.setBusinessSerialId(maxId);
        businessDao.addBusinessInfo(businessInfo);
    }

    /**
     * 获取商家信息列表
     * @return
     */
    @Override
    public List<BusinessInfo> listBusinessInfo(Integer businessType){
        List<BusinessInfo> businessInfoList = businessDao.listBusinessInfo(businessType);
        return businessInfoList;
    }

    /**
     * 获取商家信息
     * @param id
     * @return
     */
    @Override
    public BusinessInfo getBusinessInfoById(String id){
        BusinessInfo businessInfo = businessDao.getBusinessInfoById(id);
        return businessInfo;
    }

    /**
     * 获取门店列表
     * @param businessId
     * @return
     */
    @Override
    public List<StoreInfo> listStoreByBusinessId(String businessId) {
        return businessDao.listStoreByBusinessId(businessId);
    }

    /**
     * 新增部门信息
     * @param department
     */
    @Override
    public void addDepartmentInfo(Department department){
        String maxId = RedisCommon.getMaxDepartmentId();
        if ("".equals(maxId)){
            maxId = RedisCommon.setAndReturnMaxDepartmentId(businessDao.getMaxDepartmentId());
        }
        department.setId(UUID.randomUUID().toString());
        department.setDepSerialId(maxId);
        businessDao.addDepartmentInfo(department);
    }

    /**
     * 获取部门信息列表
     * @return
     */
    @Override
    public List<Department> listDepartment(){
        List<Department> departmentList = businessDao.listDepartmentInfo();
        return departmentList;
    }

    /**
     * 获取部门信息
     * @param id
     * @return
     */
    @Override
    public Department getDepartmentById(String id){
        Department department = businessDao.getDepartmentInfoById(id);
        return department;
    }
    /**
     * 新增门店信息
     * @param storeInfo
     */
    public void addStoreInfo(StoreInfo storeInfo){
        String maxId = RedisCommon.getMaxStoreId(storeInfo.getBusinessId());
        if ("".equals(maxId)){
            maxId = RedisCommon.setAndReturnMaxStoreId(storeInfo.getBusinessId(),businessDao.getMaxStoreId(storeInfo.getBusinessId()));
        }
        storeInfo.setStoreSerialId(maxId);
        businessDao.addStoreInfo(storeInfo);
    }

    /**
     * 新增商家部门信息
     * @param department
     */
    @Override
    public void addBusiDepartmentInfo(Department department){
        String maxId = RedisCommon.getMaxDepartmentId();
        if ("".equals(maxId)){
            maxId = RedisCommon.setAndReturnMaxDepartmentId(businessDao.getMaxBusiDepartmentId(department.getBusinessId()));
        }
        department.setId(UUID.randomUUID().toString());
        department.setDepSerialId(maxId);
        businessDao.addBusiDepartmentInfo(department);
    }

    /**
     * 获取商家部门信息列表
     * @return
     */
    @Override
    public List<Department> listBusiDepartment(String businessId){
        List<Department> departmentList = businessDao.listBusiDepartmentInfo(businessId);
        return departmentList;
    }

    /**
     * 获取商家部门信息
     * @param id
     * @return
     */
    @Override
    public Department getBusiDepartmentById(String id){
        Department department = businessDao.getBusiDepartmentInfoById(id);
        return department;
    }

    /**
     * 新增相册信息
     * @param album
     */
    @Override
    public  List<Album> addAlbumInfo(Album album){
        album.setId(UUID.randomUUID().toString());
        businessDao.addAlbumInfo(album);
        Album all =new Album();
        all.setId(album.getId());

        return businessDao.getAlbumList(album);
    }

    /**
     * 获取相册信息
     * @param album
     */
    @Override
    public  List<Album> getAlbumList(Album album){
        List<Album>  list =businessDao.getAlbumList(album);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setUrl(Configuration.getStaticShowPath()+list.get(i).getUrl());
        }
        return  list;
    }

    /**
     **
     * 修改相册信息
     * @param album
     */
    @Override
    public void updateAlbumById(Album album){

        businessDao.updateAlbumById(album);
    }

    /**
     **
     * 删除相册信息
     * @param album
     */
    @Override
    public void deleteAlbumById(Album album){

        businessDao.deleteAlbumById(album);
    }

    /**
     **
     * 新增照片信息
     * @param photo
     */
    @Override
    public Photo addPhotoInfo(Photo photo){
        photo.setId(UUID.randomUUID().toString());
        businessDao.addPhotoInfo(photo);
        Photo photoinfo = businessDao.getPhotoListById(photo.getId());
        photoinfo.setUrl(Configuration.getStaticShowPath()+photoinfo.getUrl());
        return photoinfo;
    }

    /**
     **
     * 获取照片信息
     * @param albumId
     */
    @Override
    public List<Album>getPhotoList(String albumId){
        Photo photo = new Photo();
        photo.setAlbumId(albumId);
        List<Album> list = businessDao.getPhotoList(photo);
        for (int i = 0; i < list.size(); i++) {
            List<Photo> photoList = list.get(i).getPhotoList();
            for (int j = 0; j < photoList.size(); j++) {
                photoList.get(j).setUrl(Configuration.getStaticShowPath()+photoList.get(j).getUrl());
            }
            list.get(i).setPhotoList(photoList);
        }
        return  list;
    }

    /**
     **
     * 修改照片信息
     * @param photo
     */
    @Override
    public void updatePhotoById(Photo photo){
        businessDao.updatePhotoById(photo);
    }

    /**
     **
     * 删除照片信息
     */
    @Override
    public void deletePhotoById(String  id ){
        Photo photo = new Photo();
        String[] arr = id.split(",");
        for (int i = 0; i <arr.length ; i++) {
            photo.setId(arr[i]);
            businessDao.deletePhotoById(photo);
        }
    }

    /**
     **
     * 新增门店照片
     * @param
     * @param
     */
    @Override
    public String addStorePhotoInfo(StorePhoto storePhoto){
        storePhoto.setId(UUID.randomUUID().toString());
        businessDao.addStorePhotoInfo(storePhoto);
        return storePhoto.getId();
    }

}
