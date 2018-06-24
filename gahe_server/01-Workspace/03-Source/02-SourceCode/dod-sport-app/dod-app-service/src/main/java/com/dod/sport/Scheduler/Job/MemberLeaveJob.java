package com.dod.sport.Scheduler.Job;

import com.dod.sport.dao.IMemberDao;
import com.dod.sport.domain.po.Member.MemberLeave;
import com.dod.sport.service.impl.MessageServiceImpl;
import com.dod.sport.util.CommonEnum;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Administrator
 * \* Date: 2017/11/3
 * \* Description:会员请假定时任务
 * \
 */
public class MemberLeaveJob  implements Job {

    Logger logger = LoggerFactory.getLogger(MemberLeaveJob.class);

    @Autowired
    private IMemberDao memberDao;

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        //发送消息至每个预约的会员
        try {
            Date date=new Date();
            DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time=format.format(date);
            MemberLeave memberLeave = new MemberLeave();
            memberLeave.setApproveStatus("2");
            memberLeave.setEndTime(time);
            memberDao.deleteMemberApprove(memberLeave);
        } catch (Exception e) {
            logger = LoggerFactory.getLogger(MemberLeaveJob.class);
            logger.error("任务调度异常", e);
        }
    }
}