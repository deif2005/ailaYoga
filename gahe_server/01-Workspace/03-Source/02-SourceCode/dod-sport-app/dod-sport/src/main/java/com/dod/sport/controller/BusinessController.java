package com.dod.sport.controller;

import com.dod.sport.config.Configuration;
import com.dod.sport.domain.common.BusiException;
import com.dod.sport.domain.common.UploadFileResponse;
import com.dod.sport.domain.po.*;
import com.dod.sport.domain.po.Base.Album;
import com.dod.sport.domain.po.Base.BusinessInfo;
import com.dod.sport.domain.po.Base.Department;
import com.dod.sport.domain.po.Base.StoreInfo;
import com.dod.sport.service.IBusinessService;
import com.dod.sport.service.IEmployeeService;
import com.dod.sport.util.CommonEnum;
import com.dod.sport.util.RedisCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * Created by defi on 2017-08-14.
 * 商家信息相关功能controller
 */
@Controller
@RequestMapping(value = "api/Business/v1")
public class BusinessController extends BaseController{

    @Autowired
    IBusinessService businessService;
    @Autowired
    IEmployeeService employeeService;

    /**
     * 新增商家信息
     * @param businessName
     * @param address
     * @param phoneNum
     * @param creator
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addBusinessInfo", method = RequestMethod.POST)
    public String addBusinessInfo(HttpServletRequest request,
                                  @RequestParam(value = "businessName") final String businessName,
                                  @RequestParam(value = "address") final String address,
                                  @RequestParam(value = "phoneNum") final String phoneNum,
                                  @RequestParam(value = "email") final String email,
                                  @RequestParam(value = "logo") final String logo,
                                  @RequestParam(value = "phoneNum2", required = false) final String phoneNum2,
                                  @RequestParam(value = "businessType") final Integer businessType,
                                  @RequestParam(value = "creator") final String creator){
        BusinessInfo businessInfo = new BusinessInfo();
        businessInfo.setBusinessName(businessName);
        businessInfo.setAddress(address);
        businessInfo.setPhoneNum(phoneNum);
        businessInfo.setPhoneNum2(phoneNum2);
        businessInfo.setEmail(email);
        businessInfo.setLogo(logo);
        businessInfo.setBusinessType(String.valueOf(businessType));
        businessInfo.setCreator(creator);
        String maxId = RedisCommon.getMaxBusinessId(businessInfo.getBusinessType());
        businessInfo.setBusinessSerialId(maxId);
        try {
            //文件上传相对目录
            String relaPath = Configuration.getUploadBusinessPicturePath(businessInfo.getId());
            //上传文件
            UploadFileResponse uploadFileResponse = super.uploadFile(request, relaPath);
            String uploadPath = uploadFileResponse.getFileList().toString();
            StringBuffer stringBuffer = new StringBuffer();
            if (!"".equals(uploadPath)){
                for (String path : uploadFileResponse.getFileList()){
                    stringBuffer.append(";").append(path);
                }
                businessInfo.setLogo(stringBuffer.toString().substring(1));
            }
        }catch (BusiException e){
            //没传图片继续执行
        }catch (IOException e){
            logger.error("BusinessController-addPhotoInfo:", e);
            throw new BusiException(CommonEnum.ReturnCode.SystemCode.BusinessCode.busi_err_uploadfileerror.getValue());
        }
        businessService.addBusinessInfo(businessInfo);
        return "";
    }

    /**
     * 获取商家列表
     * @param businessType
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listBusinessInfo", method = RequestMethod.POST)
    public Map listBusinessInfo(@RequestParam(value = "businessType") final Integer businessType){
        Map<String,Object> map = new HashMap<>();
        List<BusinessInfo> businessInfoList = businessService.listBusinessInfo(businessType);
        map.put("businessInfos",businessInfoList);
        return map;
    }

    /**
     * 获取门店列表
     * @param businessId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listStoreByBusinessId", method = RequestMethod.POST)
    public Map listStoreByBusinessId(@RequestParam(value = "businessId") final String businessId){
        Map<String,Object>map = new HashMap<>();
        List<StoreInfo> storeInfos = businessService.listStoreByBusinessId(businessId);
        if (storeInfos==null){
            throw new BusiException(CommonEnum.ReturnCode.SystemCode.BusinessCode.busi_err_nodata.getValue());
        }
        map.put("storeInfos",storeInfos);
        return map;
    }
    /**
     * 获取部门列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listDepartmentInfo", method = RequestMethod.POST)
    public Map listBusinessInfo(){
        Map<String,Object> map = new HashMap<>();
        List<Department> departmentList = businessService.listDepartment();
        map.put("departments",departmentList);
        return map;
    }

    /**
     * 新增部门信息
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addDepartmentInfo", method = RequestMethod.POST)
    public String addDepartmentInfo(@RequestParam(value = "departmentName") final String departmentName,
                                    @RequestParam(value = "creator") final String creator){
        Department department = new Department();
        department.setDepName(departmentName);
        department.setCreator(creator);
        businessService.addDepartmentInfo(department);
        return "";
    }

    /**
     * 新增门店信息
     * @param businessId
     * @param storeName
     * @param address
     * @param creator
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addStoreInfo", method = RequestMethod.POST)
    public String addStoreInfo(HttpServletRequest request,
                               @RequestParam(value = "businessId") final String businessId,
                                @RequestParam(value = "storeName") final String storeName,
                                @RequestParam(value = "phoneNum") final String phoneNum,
                                @RequestParam(value = "email" ,required = false) final String email,
                                @RequestParam(value = "address") final String address,
                                @RequestParam(value = "creator") final String creator,
                                @RequestParam(value = "bossName") final String bossName,
                                @RequestParam(value = "bossPhone") final String bossPhone,
                                @RequestParam(value = "bossEmail") final String bossEmail,
                                @RequestParam(value = "otherWays") final String otherWays,
                                @RequestParam(value = "productService") final String productService,
                                @RequestParam(value = "doorPhotoId") final String doorPhotoId,
                                @RequestParam(value = "classroomPhotosId") final String classroomPhotosId,
                                @RequestParam(value = "receptionPhotosId") final String receptionPhotosId,
                                @RequestParam(value = "businessLicenseId") final String businessLicenseId,
                                @RequestParam(value = "businessEndTime") final String businessEndTime,
                                @RequestParam(value = "businessStartTime") final String businessStartTime ){

        StoreInfo storeInfo = new StoreInfo();
        String id = UUID.randomUUID().toString();
        storeInfo.setId(id);
        //文件上传相对目录
        String relaPath =Configuration.getUploadStoreInfoPicturePath(id);
        List<String> showPathList = new ArrayList<>();
        try {
            //文件展示地址
            String baseShowPath = Configuration.getStaticShowPath();
            //上传文件
            UploadFileResponse uploadFileResponse = super.uploadFile(request,relaPath);
            String uploadPath = uploadFileResponse.getFileList().toString();
            StringBuffer stringBuffer = new StringBuffer();
            if (!"".equals(uploadPath)){
                for (String path : uploadFileResponse.getFileList()){
                    stringBuffer.append(";").append(path);
                    showPathList.add(baseShowPath + path);
                }
                storeInfo.setStorePicture(stringBuffer.toString().substring(1));
            }
        }catch (BusiException e){
            //没传图片继续执行
        }catch (IOException e){
            logger.error("BusinessController-addStoreInfo:", e);
            throw new BusiException(CommonEnum.ReturnCode.SystemCode.BusinessCode.busi_err_uploadfileerror.getValue());
        }
        storeInfo.setBusinessId(businessId);
        storeInfo.setAddress(address);
        storeInfo.setCreator(creator);
        storeInfo.setStoreName(storeName);
        storeInfo.setBossName(bossName);
        storeInfo.setBossPhone(bossPhone);
        storeInfo.setBossEmail(bossEmail);
        storeInfo.setOtherWays(otherWays);
        storeInfo.setProductService(productService);
        storeInfo.setDoorPhotoId(doorPhotoId);
        storeInfo.setClassroomPhotosId(classroomPhotosId);
        storeInfo.setReceptionPhotosId(receptionPhotosId);
        storeInfo.setBusinessLicenseId(businessLicenseId);
        storeInfo.setBusinessStartTime(businessStartTime);
        storeInfo.setBusinessEndTime(businessEndTime);
        storeInfo.setPhoneNum(phoneNum);
        storeInfo.setPhoneNum(phoneNum);
        storeInfo.setEmail(email);
        businessService.addStoreInfo(storeInfo);

        return "";
    }

    /**
     * 新门店照片信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addStorePhotoInfo", method = RequestMethod.POST)
    public String addStorePhotoInfo(HttpServletRequest request,
                                    @RequestParam(value = "businessId") final String businessId,
                                    @RequestParam(value = "photoName") final String photoName){
        StorePhoto storePhoto = new StorePhoto();
        try {
            //文件上传相对目录
            String relaPath = Configuration.getUploadBusinessPicturePath(businessId);
            //上传文件
            UploadFileResponse uploadFileResponse = super.uploadFile(request, relaPath);
            String uploadPath = uploadFileResponse.getFileList().toString();
            StringBuffer stringBuffer = new StringBuffer();
            if (!"".equals(uploadPath)){
                for (String path : uploadFileResponse.getFileList()){
                    stringBuffer.append(";").append(path);
                }
                if("classPhotos".equals(photoName)){
                    storePhoto.setClassPhotos(stringBuffer.toString().substring(1));
                }else if("businessLicense".equals(photoName)){
                    storePhoto.setBusinessLicense(stringBuffer.toString().substring(1));
                } else if("classroomPhotos".equals(photoName)){
                    storePhoto.setClassroomPhotos(stringBuffer.toString().substring(1));
                }else if("doorPhoto".equals(photoName)){
                    storePhoto.setDoorPhoto(stringBuffer.toString().substring(1));
                } else if("receptionPhotos".equals(photoName)){
                    storePhoto.setReceptionPhotos(stringBuffer.toString().substring(1));
                } else if("teacherPhoto".equals(photoName)){
                    storePhoto.setTeacherPhoto(stringBuffer.toString().substring(1));
                }
            }
        }catch (BusiException e){
            //没传图片继续执行
        }catch (IOException e){
            logger.error("BusinessController-addPhotoInfo:", e);
            throw new BusiException(CommonEnum.ReturnCode.SystemCode.BusinessCode.busi_err_uploadfileerror.getValue());
        }
        return businessService.addStorePhotoInfo(storePhoto);
    }

    /**
     * 获取商家部门列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listBusiDepartmentInfo", method = RequestMethod.POST)
    public Map listBusiBusinessInfo(@RequestParam(value = "businessId") final String businessId){
        Map<String,Object> map = new HashMap<>();
        List<Department> departmentList = businessService.listBusiDepartment(businessId);
        map.put("departments",departmentList);
        return map;
    }

    /**
     * 新增商家部门信息
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addBusiDepartmentInfo", method = RequestMethod.POST)
    public String addBusiDepartmentInfo(@RequestParam(value = "businessId") final String businessId,
                                        @RequestParam(value = "departmentName") final String departmentName,
                                        @RequestParam(value = "creator") final String creator){
        Department department = new Department();
        department.setBusinessId(businessId);
        department.setDepName(departmentName);
        department.setCreator(creator);
        businessService.addBusiDepartmentInfo(department);
        return "";
    }

    /**
     * 新增相册信息
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addAlbumInfo", method = RequestMethod.POST)
    public List<Album> addAlbumInfo(@RequestParam(value = "storeId") final String storeId,
                                        @RequestParam(value = "albumName") final String albumName,
                                        @RequestParam(value = "creator") final String creator,
                                        @RequestParam(value = "describes") final String describes){
        Album album  = new Album();
        album.setAlbumName(albumName);
        album.setDescribes(describes);
        album.setStoreId(storeId);
        album.setCreator(creator);
        return  businessService.addAlbumInfo(album);
    }

    /**
     * 获取相册信息
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAlbumList", method = RequestMethod.POST)
    public List<Album> getAlbumList(@RequestParam(value = "storeId") final String storeId,
                                @RequestParam(value = "id", required = false) final String id){

        Album album  = new Album();
        album.setStoreId(storeId);
        List<Album> albumList = businessService.getAlbumList(album);
        return albumList;
    }

    /**
     * 修改相册信息
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateAlbumById", method = RequestMethod.POST)
    public String updateAlbumById(@RequestParam(value = "id") final String id,
                                  @RequestParam(value = "storeId") final String storeId,
                                  @RequestParam(value = "albumName") final String albumName,
                                  @RequestParam(value = "creator") final String creator,
                                  @RequestParam(value = "describes") final String describes){

        Album album  = new Album();
        album.setId(id);
        album.setAlbumName(albumName);
        album.setDescribes(describes);
        album.setStoreId(storeId);
        album.setCreator(creator);
        businessService.updateAlbumById(album);
        return "";
    }

    /**
     * 删除相册信息
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteAlbumById", method = RequestMethod.POST)
    public String deleteAlbumById(@RequestParam(value = "id") final String id){
        Album album  = new Album();
        album.setId(id);
        businessService.deleteAlbumById(album);
        return "";
    }

    /**
     * 新增照片信息
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addPhotoInfo", method = RequestMethod.POST)
    public Map addPhotoInfo (HttpServletRequest request,
                                @RequestParam(value = "albumId") final String albumId,
                                @RequestParam(value = "storeId") final String storeId,
                                @RequestParam(value = "creator") final String creator){

        Photo photo  = new Photo();
        Map map = new HashMap<>();
        List showPathList = new ArrayList<>();
        photo.setAlbumId(albumId);
        photo.setCreator(creator);
        StringBuffer stringBuffer = new StringBuffer();
        try {
            //文件上传相对目录
            String relaPath = Configuration.getUploadAlbumPhotoPicturePath(storeId);
            //文件展示地址
            String baseShowPath = Configuration.getStaticShowPath();
            //上传文件
            UploadFileResponse uploadFileResponse = super.uploadFile(request, relaPath);
            String uploadPath = uploadFileResponse.getFileList().toString();

            if (!"".equals(uploadPath)){
                for (String path : uploadFileResponse.getFileList()) {
                    stringBuffer.append(";").append(path);
                }
//                photo.setUrl(stringBuffer.toString().substring(1));
            }
        }catch (BusiException e){
            //没传图片继续执行
        }catch (IOException e){
            logger.error("BusinessController-addPhotoInfo:", e);
            throw new BusiException(CommonEnum.ReturnCode.SystemCode.BusinessCode.busi_err_uploadfileerror.getValue());
        };
//        businessService.addPhotoInfo(photo);
        String[] str = stringBuffer.toString().split(";");
        if(str!=null){
            for (int i=1;i<str.length;i++){
                photo.setUrl(str[i]);
                Photo photoInfo = businessService.addPhotoInfo(photo);
                showPathList.add(photoInfo);

            }
        }else {
            businessService.addPhotoInfo(photo);
        }
        map.put("showPathList",showPathList);
        return map;

    }

    /**
     * 获取照片信息
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPhotoList", method = RequestMethod.POST)
    public List<Album> getPhotoList (@RequestParam(value = "albumId") final String albumId){
        List<Album>  Album  = businessService.getPhotoList(albumId);
        return Album;

    }


    /**
     * 修改相册信息
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updatePhotoById", method = RequestMethod.POST)
    public String updatePhotoById(@RequestParam(value = "albumId") final String albumId,
                                  @RequestParam(value = "photoName") final String photoName,
                                  @RequestParam(value = "describes") final String describes,
                                  @RequestParam(value = "url") final String url,
                                  @RequestParam(value = "creator") final String creator){

        Photo photo  = new Photo();
        photo.setAlbumId(albumId);
        photo.setCreator(creator);
        photo.setUrl(url);
        businessService.updatePhotoById(photo);
        return "";
    }

    /**
     * 删除相册信息
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deletePhotoById", method = RequestMethod.POST)
    public String deletePhotoById(@RequestParam(value = "id") final String id){

        businessService.deletePhotoById(id);
        return "";
    }





}
