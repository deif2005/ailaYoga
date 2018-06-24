package com.dod.sport.Scheduler.Job;

import com.dod.sport.domain.po.Member.MembercardRelation;
import com.dod.sport.service.IMembercardRelationService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/11/21.
 */
public class ActivateMembercardRelationJob implements Job {
    Logger logger = LoggerFactory.getLogger(ActivateMembercardRelationJob.class);
    @Autowired
    private IMembercardRelationService membercardRelationService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
           // SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //获取所有要激活的会员卡
           List< MembercardRelation> membercardrelations = membercardRelationService.getMembercardRelationActive();
            if (membercardrelations !=null&& membercardrelations.size()>0) {
                for (MembercardRelation membercardRelation :membercardrelations){
                    if (membercardRelation.getActTime()==null ||membercardRelation.getActTime()==""){
                        membercardRelationService.activateMembercardRelation(membercardRelation.getId());
                    }
                }
               /* Calendar c1 = Calendar.getInstance();
                Calendar c2 = Calendar.getInstance();
                Date currentDate = new Date();
                String specacTime = membercardrelation.getSpecactTime();
                Date specatTime = dfs.parse(specacTime);
                c1.setTime(currentDate);
                c2.setTime(specatTime);
                int result = c1.compareTo(c2);
                if (result == 0) {*/
                //}
            }
        } catch (Exception e) {
            logger = LoggerFactory.getLogger(ActivateMembercardRelationJob.class);
            logger.error("任务调度异常", e);
        }
    }
}
