package com.dod.sport.Scheduler.Job;

import com.dod.sport.dao.ICourseDao;
import com.dod.sport.domain.common.BusiException;
import com.dod.sport.service.impl.MessageServiceImpl;
import com.dod.sport.util.CommonEnum;
import org.quartz.*;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by defi on 2017-10-17.
 * 课程结束推送评价消息
 */

public class SendCourseCommentMessageJob implements Job{

    Logger logger = LoggerFactory.getLogger(SendCourseCommentMessageJob.class);

    @Autowired
    ICourseDao courseDao;

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException{
        //发送消息至每个预约的会员
        try {
            JobDataMap dataMap = arg0.getJobDetail().getJobDataMap();
            List<String> courseOrderMemberIdList = courseDao.listCourseOrderMember("",
                    CommonEnum.Course.OrderStatus.signed.getIntegerVal());
            ArrayList<String> memberIdList = (ArrayList)dataMap.get("memberIdList");
            String[] memberIdArray = memberIdList.toArray(new String[memberIdList.size()]);
            MessageServiceImpl messageService = new MessageServiceImpl();
            String messageContent = messageService.getCourseCommentMessageContent();
            HashMap<String,String> extrasMap = new HashMap<>();
            extrasMap.put("url","courseComment");
            messageService.sendMessageToAlias("课程评论", messageContent, messageContent, extrasMap, memberIdArray);
            System.out.println(courseOrderMemberIdList);
        }catch (Exception e){
            logger = LoggerFactory.getLogger(SendCourseCommentMessageJob.class);
            logger.error("任务调度异常",e);
//            throw new BusiException(CommonEnum.ReturnCode.SystemCode.sys_err_exception.getValue());
        }
    }
}
