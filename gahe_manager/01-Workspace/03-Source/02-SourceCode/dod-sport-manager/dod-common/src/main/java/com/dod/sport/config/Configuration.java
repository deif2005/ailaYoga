package com.dod.sport.config;

import com.dod.sport.util.PropertiesUtil;

/**
 * Configuration
 * 读取系统配置类
 * @author yuhao
 * @date 2016/10/19 20:26
 */
public class Configuration {

    private static final String propertiesPath =
            Configuration.class.getClassLoader().getResource("config/configuration.properties").getFile();
    private  static PropertiesUtil configuration = null;

    /**
     * 系统启动初始化加载配置文件
     */
    static{
        try {
            configuration = new PropertiesUtil();
            configuration.loadFile(propertiesPath);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("加载properties文件出错，退出系统!");
            System.exit(-1);
        }
    }

    /**
     * 获取极光推送appkey
     * @return
     */
    public static String getJgPushAppkey(){
        return configuration.getValue("appKey");
    }

    /**
     * 获取极光masterSecret
     * @return
     */
    public static String getJgPushMasterSecret(){
        return configuration.getValue("masterSecret");
    }

    /**
     * 获取报销单据图片上传路径
     * @return
     */
    public static String getUploadExpenseAccountPicturePath(String employeeId){
        return configuration.getValue("/upload/picture/expenseAccount/path").replaceAll("#employeeId",employeeId);
    }

    /**
     * 获取课程封面图片上传路径
     * @return
     */
    public static String getUploadCoursePicturePath(String businessId){
        return configuration.getValue("/upload/picture/course/path").replaceAll("#businessId",businessId);
    }

    /**
     * 员工照片上传路径
     * @param employeeId
     * @return
     */
    public static String getUploadEmployeePicturePath(String employeeId){
        return configuration.getValue("/upload/empPicture/path").replaceAll("#employeeId",employeeId);
    }
    /**

     * 获取静态资源目录路径
     * @return
     */
    public static String getStaticResourcePath(){
        return configuration.getValue("/system/workPath");
    }

    /**
     * 获取静态资源展示地址
     * @return 返回资源展示地址
     * @return 例：http://192.168.10.42:8085/ices-static/upload
     */
    public static String getStaticShowPath(){
        return configuration.getValue("/static/showPath");
    }

    /**
     * 获取照片上传路径
     * @return
     */
    public static String getUploadBusinessPicturePath(String businessId){
        return configuration.getValue("/upload/picture/business/path").replaceAll("#businessId",businessId);
    }

    /**
     * 获取门店照片上传路径
     * @param storeId
     * @return
     */
    public static String getUploadStoreInfoPicturePath(String storeId){
        return configuration.getValue("/upload/picture/storePicture/path").replaceAll("#storeId",storeId);
    }

}
