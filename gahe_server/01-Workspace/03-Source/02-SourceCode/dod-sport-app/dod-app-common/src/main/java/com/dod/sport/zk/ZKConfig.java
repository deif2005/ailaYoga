package com.dod.sport.zk;

import com.dod.sport.constant.FileConstants;
import com.dod.sport.constant.WebConstants;
import com.dod.sport.domain.common.BusiException;
import com.dod.sport.util.DateUtil;
import org.apache.curator.framework.CuratorFramework;

import java.net.URLDecoder;
import java.util.UUID;

/**
 * Created by BIG on 2016/4/7.
 */

public class ZKConfig {

	// ZK的根目录
	static String rootPath = "/configs/ices/ices-kc";

	/**
	 * 获取ZK上的配置路径
	 * @param zk_path ZK的配置路径
	 * @return
	 */
	public static String getUploadPath(String zk_path) {
		zk_path = rootPath + zk_path;
		// 默认上传地址
		String uploadPath = null;

		// 判断 ZK是否有配置，如果没有，使用默认
		CuratorFramework zk = ZKClient.getClient();
		try {
			if (zk.checkExists().forPath(zk_path) != null) {
				byte[] data = zk.getData().forPath(zk_path);
				uploadPath = URLDecoder.decode(new String(data),FileConstants.FILE_ENCODING);
			} else {
				throw new BusiException("","读取zk配置失败，配置不存在：" + zk_path);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uploadPath;
	}

	/**
	 * 获取静态资源展示地址
	 * @return 返回资源展示地址
	 * @return 例：http://192.168.10.42:8085/ices-static/upload
	 */
	/*public static String getStaticShowPath(){
		return getUploadPath("/static/showPath");
	}*/

	/**
	 * 获取静态资源上传地址
	 * @return 例：d:/tomcat/ices-static/webpps
	 */
	/*public static String getSystemWorkDir(){
		return getUploadPath("/system/workPath");
	}
*/
	/**
	 * 获取试题内容xml保存地址
	 * @return 根路径+试题内容xml目录
	 * @desc 跟据试卷id分文件夹保存，不与制卷路径保持一致
	 */
	public static String getQuestionXmlSavePath(String paperId){
		return getUploadPath("/upload/question/xmlPath").replaceAll("#paperId", paperId);
	}

	/**
	 * 获取考务包下载地址
	 * @return string
	 */
	public static String getKaoWuPackagePath(){
		return getUploadPath("/download/kaowu/path");
	}

	/**
	 * 获取试卷包上传地址
	 * @return
	 */
	public static String getUploadPaperPkgPath(){
		return getUploadPath("/upload/paperPkg/path").replaceAll("#date", DateUtil.getDate());
	}
	/**
	 * 获取试卷包上传的主地址
	 * @return
	 */
	public static String getUploadPaperPkgMainPath(){
		return getUploadPath("/upload/paperPkg/path").replaceAll("#date/", "");
	}
	/**
	 * 获取试卷上传文件规则
	 * @return
	 */
	public static String getUploadPaperPkgRule(){
		return getUploadPath("/upload/paperPkg/rule");
	}

	/**
	 * 获取考务包上传地址
	 * @return
	 */
	public static String getUploadExamPkgPath(){
		return getUploadPath("/upload/examPkg/path").replaceAll("#date", UUID.randomUUID().toString());
	}

	/**
	 * 获取考务包上传主地址
	 * @return
	 */
	public static String getUploadExamPkgMainPath(){
		return getUploadPath("/upload/examPkg/path").replaceAll("#date/", "");
	}

	/**
	 * 获取考务包文件规则
	 * @return
	 */
	public static String getUploadExamPkgRule(){
		return getUploadPath("/upload/examPkg/rule");
	}

	/**
	 * 获取场次答案包导出临时地址
	 */
	public static String getExamTimePkgTempPath(String examTimesId){
		return getUploadPath("/download/examTimesPkg/path").replaceAll("#examTimesId", examTimesId);
	}

	/**
	 * 获取考生答案文件地址
	 */
	public static String getUploadStudentAnswerPath(String examTimesId){
		return getUploadPath("/upload/student/path").replaceAll("#examTimesId",examTimesId);
	}

	/**
	 * 获取场次包上传服务器地址
	 */
	public static String getUploadExamTimePkgPath(){
		return getUploadPath("/upload/examTimesPkg/path");
	}

	/**
	 * 获取打包导出路径
	 */
	public static String getPackagePath(){
		return getUploadPath("/download/examTimesPkg/");
	}

	/**
	 * 获取考生照片上传地址
	 * @param studentNo 考号
	 * @return
	 */
	public static String getUploadStudentPhotoPath(String studentNo){
		return getUploadPath("/upload/student/photo/path").replaceAll("#studentNo",studentNo);
	}

	/**
	 * 获取试题资源上传地址(导入存放试卷试题资源)
	 * @param paperId
	 * @return
	 * @desc 跟据试卷分文件夹，标识一个试卷下的资源
	 */
	/*public static String getUploadQuestionResourcePath(String paperId){
		return getUploadPath("/upload/question/resource/path").replaceAll("#paperId",paperId);
	}*/

	/**
	 * 获取考务包协议文件上传地址
	 * @return
	 */
	public static String getAgreementPath(){
		return getUploadPath("/upload/examPkg/agreement/path");
	}

	/**
	 * 获取导入试室excel模板下载地址
	 * @return string
	 */
	public static String getExcelTemplateClassRoomPath(){
		return getUploadPath("/download/excelTemplate/classRoom/path");
	}

	/**
	 * 获取导入监考账号excel模板下载地址
	 * @return string
	 */
	public static String getExcelTemplateMonitorPath(){
		return getUploadPath("/download/excelTemplate/monitor/path");
	}

	/**
	 * 获取导入座位excel模板下载地址
	 * @return string
	 */
	public static String getExcelTemplateSeatPath(){
		return getUploadPath("/download/excelTemplate/seat/path");
	}
	 
	 /**
	 * 获取考生答案包路径
	 * @return
	 */
	public static String getStudentAnswerPkgPath(String timesId){
		return getUploadPath("/download/student/answerPkg/path").replaceAll("#timesId",timesId);
	}

	/**
	 * 获取考场http路径
	 * @return
	 */
	/*public static String getKcHttpPath(){
		return getUploadPath("/static/kcHttpPath");
	}*/

	/**
	 * 获取客户端http路径
	 * @return
	 */
	/*public static String getKhdHttpPath(){
		return getUploadPath("/static/khdHttpPath");
	}*/

	/**
	 * 获取考生成绩统计存放目录
	 * @return
	 */

	public static String getExamTimesScorePath(String examTimeId) {
		return getUploadPath("/download/examTimesScore/path").replaceAll("#examTimeId", examTimeId);
	}
	
}
