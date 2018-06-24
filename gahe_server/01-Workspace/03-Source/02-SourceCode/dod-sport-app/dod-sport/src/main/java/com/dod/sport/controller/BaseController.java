package com.dod.sport.controller;

import com.dod.sport.config.Configuration;
import com.dod.sport.constant.WebConstants;
import com.dod.sport.domain.common.BusiException;
import com.dod.sport.domain.common.UploadFileResponse;
import com.dod.sport.domain.po.UserRole;
import com.dod.sport.util.CommonEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * BaseController
 * 基础controller
 * @author yuhao
 * @date 2016/6/30
 */
public class BaseController {
    protected static Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected HttpServletResponse response;


    /**
     * 上传文件方法，支持多文件同时上传
     * @param request
     * @param updateDir 上传文件的相对路径，如（/paper/）
     * @return (返回代码说明：0=上传成功；1=没有文件上传；2=上传失败；3=上传异常；4=上传文件类型错误)
     */
    public UploadFileResponse uploadFile(HttpServletRequest request, String updateDir) throws IOException {
        UploadFileResponse resp = new UploadFileResponse();
        List<String> fileList = new ArrayList<>();
        List<String> fileNameList = new ArrayList<>();
        try {
            String basePath = Configuration.getStaticResourcePath();
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            multipartResolver.setDefaultEncoding("utf-8");
            //判断 request 是否有文件上传,即多部分请求
            if (multipartResolver.isMultipart(request)) {
                //转换成多部分request
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                //取得request中的所有文件名
                Iterator<String> iter = multiRequest.getFileNames();
                int count = 0;
                while (iter.hasNext()) {
                    count++;
                    //取得上传文件
                    MultipartFile file = multiRequest.getFile(iter.next());
                    if (file != null) {
                        //取得当前上传文件的文件名称
                        String myFileName = file.getOriginalFilename();
                        //如果名称不为"",说明该文件存在，否则说明该文件不存在
                        if (!"".equals(myFileName.trim())) {
                            //获取文件后缀
                            String suffix = myFileName.substring(myFileName.lastIndexOf("."));
                            //上传文件名使用uuid
                            myFileName = UUID.randomUUID().toString() + suffix;//+ "-" + file.getOriginalFilename()
                            //获取完整服务器上传文件路径(上传目录根路径+相对路径+文件名称)
                            String uploadServerPath = basePath + updateDir +  myFileName;
                            File localFile = new File(uploadServerPath);
                            if (!localFile.exists()) {
                                localFile.mkdirs();
                            }
                            if (localFile.isFile()) {
                                localFile.delete();
                            }
                            try {
                                file.transferTo(localFile);
                            } catch (IOException e) {
                                throw e;
                            }
                            //增加至返回文件列表中
                            fileList.add(updateDir + myFileName);
                            //增加至返回文件名称列表中
                            fileNameList.add(file.getOriginalFilename());
                        }
                    }
                }
                //没有文件上传
                if (count == 0) {
                    throw new BusiException(CommonEnum.ReturnCode.SystemCode.BusinessCode.busi_err_nouploadfile.getValue());
                }
                logger.debug("上传文件成功---------------[" + fileList + "]");
            }
            //不存在文件流
            else {
                throw new BusiException(CommonEnum.ReturnCode.SystemCode.BusinessCode.busi_err_nouploadfile.getValue());
            }
        } catch (Exception e) {
            throw e;
        }
        resp.setFileList(fileList);
        resp.setFileNameList(fileNameList);
        return resp;
    }

    public UserRole getUserRole(){
        return (UserRole)request.getSession().getAttribute(WebConstants.USER_ROLE);
    }
}
