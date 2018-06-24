package com.dod.sport.Scheduler.Job;

import com.dod.sport.service.IOrderService;
import com.dod.sport.util.CommonEnum;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.List;

/**
 * Created by Administrator on 2017/11/30.
 */
public class CancelOrderJob implements Job {
    @Autowired
    private IOrderService orderService;
    Logger logger = LoggerFactory.getLogger(ActivateMembercardRelationJob.class);
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        try {
            //1:检索数据库中当前时间超过了订单提交时间之后12小时的支付时间的未完成订单
            List<String>list = orderService.listToCancelOrder();
            String orderStatus = CommonEnum.Order.orderStatus.orderStatus_cancel.getValue();//订单设置成取消状态
            if (list!=null && list.size()>0){
                for (String orderSerialId:list){
                    //2:对这些订单进行取消处理
                    orderService.completeOrderByOrderSerialId(orderSerialId, orderStatus);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }catch (Exception e) {
            logger = LoggerFactory.getLogger(CancelOrderJob.class);
            logger.error("任务调度异常", e);
        }

    }
}
