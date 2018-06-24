package com.dod.sport.config;

import com.dod.sport.util.DateUtil;
import com.dod.sport.util.PropertiesUtil;

import java.util.UUID;

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
     * 获取客户端appkey
     * @return
     */
    public static String getClientJgPushAppkey(){
        return configuration.getValue("clientAppKey");
    }

    /**
     * 获取极光客户端 masterSecret
      * @return
     */
    public static String getClientJgPushMasterSecret(){
        return configuration.getValue("clientMasterSecret");
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
     * 员工头像上传路径
     * @param employeeId
     * @return
     */
    public static String getUploadEmployeeHeadPath(String employeeId){
        return configuration.getValue("/upload/empHead/path").replaceAll("#employeeId",employeeId);
    }

    /**
     * 员工教学照片上传路径
     * @param empRelationId
     * @return
     */
    public static String getUploadEmployeePicturePath(String empRelationId){
        return configuration.getValue("/upload/empPicture/path").replaceAll("#empRelationId",empRelationId);
    }

    /**
     * 获取门店照片上传路径
     * @param storeId
     * @return
     */
    public static String getUploadStoreInfoPicturePath(String storeId){
        return configuration.getValue("/upload/picture/storePicture/path").replaceAll("#storeId",storeId);
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
     * 获取静态资源上传地址
     * @return 例：d:/tomcat/ices-static/webpps/icse-static/
     */
    public static String getSystemWorkDir(){
        //return ConfigUtil.getConfigString("/static/uploadPath");
        return  configuration.getValue("/system/workPath");
    }

    /**
     * 获取试题内容xml保存地址
     * @return 根路径+试题内容xml目录
     * @desc 跟据试卷id分文件夹保存，不与制卷路径保持一致
     */
    public static String getQuestionXmlSavePath(String paperId){
        //return ConfigUtil.getConfigString("/upload/question/xmlPath").replaceAll("#paperId", paperId);
        return  configuration.getValue("/upload/question/xmlPath").replaceAll("#paperId", paperId);
    }

    /**
     * 获取考务包下载地址
     * @return string
     */
    public static String getKaoWuPackagePath(){
       // return ConfigUtil.getConfigString("/download/kaowu/path");
        return  configuration.getValue("/download/kaowu/path");
    }

    /**
     * 获取试卷包上传地址
     * @return
     */
    public static String getUploadPaperPkgPath(){
      //  return ConfigUtil.getConfigString("/upload/paperPkg/path").replaceAll("#date", DateUtil.getDate());
        return  configuration.getValue("/upload/paperPkg/path").replaceAll("#date", DateUtil.getDate());
    }
    /**
     * 获取试卷包上传的主地址
     * @return
     */
    public static String getUploadPaperPkgMainPath(){
       // return ConfigUtil.getConfigString("/upload/paperPkg/path").replaceAll("#date/", "");
        return  configuration.getValue("/upload/paperPkg/path").replaceAll("#date/", "");
    }
    /**
     * 获取试卷上传文件规则
     * @return
     */
    public static String getUploadPaperPkgRule(){
        //return ConfigUtil.getConfigString("/upload/paperPkg/rule");
        return configuration.getValue("/upload/paperPkg/rule");
    }

    /**
     * 获取考务包上传地址
     * @return
     */
    public static String getUploadExamPkgPath(){
       // return ConfigUtil.getConfigString("/upload/examPkg/path").replaceAll("#date", UUID.randomUUID().toString());
        return configuration.getValue("/upload/examPkg/path").replaceAll("#date", UUID.randomUUID().toString());
    }

    /**
     * 获取考务包上传主地址
     * @return
     */
    public static String getUploadExamPkgMainPath(){
       // return ConfigUtil.getConfigString("/upload/examPkg/path").replaceAll("#date/", "");
        return configuration.getValue("/upload/examPkg/path").replaceAll("#date/", "");
    }

    /**
     * 获取考务包文件规则
     * @return
     */
    public static String getUploadExamPkgRule(){
       // return ConfigUtil.getConfigString("/upload/examPkg/rule");
        return configuration.getValue("/upload/examPkg/rule");
    }

    /**
     * 获取场次答案包导出临时地址
     */
    public static String getExamTimePkgTempPath(String examTimesId){
        //return ConfigUtil.getConfigString("/download/examTimesPkg/path").replaceAll("#examTimesId", examTimesId);
        return configuration.getValue("/download/examTimesPkg/path").replaceAll("#examTimesId", examTimesId);
    }

    /**
     * 2017-03-05 defi
     * 获取考生答案文件地址
     */
    public static String getUploadStudentAnswerPath(String examTimesId, String studentId, String questionId){
        //return ConfigUtil.getConfigString("/upload/student/path").replaceAll("#examTimesId",examTimesId);
        return configuration.getValue("/upload/student/path").replaceAll("#examTimesId",examTimesId).
                replaceAll("#studentId",studentId).replaceAll("#questionId",questionId);
    }

    /**
     * 获取场次包上传服务器地址
     */
    public static String getUploadExamTimePkgPath(){
       // return ConfigUtil.getConfigString("/upload/examTimesPkg/path");
        return configuration.getValue("/upload/examTimesPkg/path");
    }

    /**
     * 获取打包导出路径
     */
    public static String getPackagePath(){
        //return ConfigUtil.getConfigString("/download/examTimesPkg/");
        return configuration.getValue("/download/examTimesPkg/");
    }

    /**
     * 获取考生照片上传地址
     * @param studentNo 考号
     * @return
     */
    public static String getUploadStudentPhotoPath(String studentNo){
       // return ConfigUtil.getConfigString("/upload/student/photo/path").replaceAll("#studentNo",studentNo);
        return configuration.getValue("/upload/student/photo/path").replaceAll("#studentNo",studentNo);
    }

    /**
     * 获取考务包协议文件上传地址
     * @return
     */
    public static String getAgreementPath(){
        //return ConfigUtil.getConfigString("/upload/examPkg/agreement/path");
        return configuration.getValue("/upload/examPkg/agreement/path");
    }

    /**
     * 获取导入试室excel模板下载地址
     * @return string
     */
    public static String getExcelTemplateClassRoomPath(){
       // return ConfigUtil.getConfigString("/download/excelTemplate/classRoom/path");
        return configuration.getValue("/download/excelTemplate/classRoom/path");
    }

    /**
     * 获取导入监考账号excel模板下载地址
     * @return string
     */
    public static String getExcelTemplateMonitorPath(){
       // return  ConfigUtil.getConfigString("/download/excelTemplate/monitor/path");
        return configuration.getValue("/download/excelTemplate/monitor/path");
    }

    /**
     * 获取导入座位excel模板下载地址
     * @return string
     */
    public static String getExcelTemplateSeatPath(){
        //return ConfigUtil.getConfigString("/download/excelTemplate/seat/path");
        return configuration.getValue("/download/excelTemplate/seat/path");
    }
    /**客户端读取内容**********************************
     /**
     * 获取考生答案包路径
     * @return
     */
    public static String getStudentAnswerPkgPath(String timesId, String studentId){
       // return ConfigUtil.getConfigString("/download/student/answerPkg/path").replaceAll("#timesId", timesId).replaceAll("#studentId",studentId);
        return configuration.getValue("/download/student/answerPkg/path").replaceAll("#timesId", timesId).replaceAll("#studentId",studentId);
    }

    /***************************************************/


    /***********从监考端合并过来开始****************************************/
    /**
     * 获取考生答案包路径
     * @return
     */
    public static String getStudentAnswerPkgPath(String timesId){
        //return ConfigUtil.getConfigString("/download/student/answerPkg/path").replaceAll("#timesId",timesId);
        return configuration.getValue("/download/student/answerPkg/path").replaceAll("#timesId",timesId);
    }

     /**
     * 获取考场http路径
     * @return
     */
    /*public static String getKcHttpPath(){
       // return ConfigUtil.getConfigString("/static/kcHttpPath");
        return configuration.getValue("/static/kcHttpPath");

    }*/

    /**
     * 获取客户端http路径
     * @return
     */
    /*public static String getKhdHttpPath(){
       // return ConfigUtil.getConfigString("/static/khdHttpPath");
        return configuration.getValue("/static/khdHttpPath");
    }*/

    /**
     * 获取监考路径
     * @return
     */
    /*public static String getJkSocketPath(){
        // return ConfigUtil.getConfigString("/static/jkSocketPath");
        return configuration.getValue("/static/jkSocketPath");
    }*/
    /***********从监考端合并过来结束****************************************/
    /**
     * 获取考生成绩统计存放目录
     * @return
     */

    public static String getExamTimesScorePath(String examTimeId) {
        return configuration.getValue("/download/examTimesScore/path").replaceAll("#examTimeId", examTimeId);
    }

    /**
     * 获取考生答案上传路径（主要针对主观题答案文件）
     */
    public static String getStudentAnswerUploadPath(String examTimeId){
        return configuration.getValue("/upload/student/answer/path").replaceAll("#examTimeId", examTimeId);
    }

    /**
     * 获取照片上传路径
     * @return
     */
    public static String getUploadAlbumPhotoPicturePath(String storeId){
        return configuration.getValue("/upload/picture/albumPhoto/path").replaceAll("#storeId",storeId);
    }

    /**
     * 获取照片上传路径
     * @return
     */
    public static String getUploadBusinessPicturePath(String businessId){
        return configuration.getValue("/upload/picture/Business/path").replaceAll("#businessId",businessId);
    }

    /**
     * 员工头像上传路径
     * @param userId
     * @return
     */
    public static String getUploadUserHeadPath(String userId){
        return configuration.getValue("/upload/userHead/path").replaceAll("#userId",userId);
    }

    /**
     * 获取微信支付appId
     * @return
     */
    public static String getWeiXinAppId(){
        return configuration.getValue("appId");
    }

    /**
     * 获取微信支付mchId
     * @return
     */
    public static String getWeiXinMchId(){
        return configuration.getValue("mchId");
    }

    /**
     * 获取微信支付apiSecret
     * @return
     */
    public static String getWeiXinApiSecret(){
        return configuration.getValue("apiSecret");
    }


    /**
     * 获取极光短信appkey
     * @return
     */
    public static String getSmsAppkey(){
        return configuration.getValue("smsAppKey");
    }

    /**
     * 获取极光短信masterSecret
     * @return
     */
    public static String getSmsMasterSecret(){
        return configuration.getValue("smsMasterSecret");
    }





}
