package com.dod.sport.controller;

import com.dod.sport.domain.po.ResponseOrder;
import com.dod.sport.service.IOrderService;
import com.dod.sport.util.CommonEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/16.
 */
@Controller
@RequestMapping("api/OrderController/v1")
public class OrderController {

    @Autowired
    IOrderService orderService;

    /**
     * 付款后的操作
     * @param orderSerialId
     */
    @RequestMapping(value = "/completeOrderByOrderSerialId" ,method = RequestMethod.POST)
    @ResponseBody
    public String completeOrderByOrderSerialId(@RequestParam(value = "orderSerialId") final String orderSerialId)   {
        try {
            orderService.completeOrderByOrderSerialId(orderSerialId, CommonEnum.Order.orderStatus.orderStatus_prepaid.getValue());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 取消订单
     * @param orderSerialId
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/cancelOrderByOrderSerialId" ,method = RequestMethod.POST)
    @ResponseBody
    public String cancelOrderByOrderSerialId(@RequestParam(value = "orderSerialId") final String orderSerialId)   {
        try {
            orderService.completeOrderByOrderSerialId(orderSerialId,CommonEnum.Order.orderStatus.orderStatus_cancel.getValue());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取会员卡开卡和充值订单
     * @param membercardRelationId
     * @param page
     * @return
     */
    @RequestMapping(value = "/listRechargeOrOpenOrderRecord" ,method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> listRechargeOrOpenOrderRecord(@RequestParam(value = "membercardRelationId") final String membercardRelationId,
                                                            @RequestParam(value = "page") final Integer page ){
        List<ResponseOrder> responseOrders = orderService.listRechargeOrOpenOrderRecord(membercardRelationId,  page);
        Map<String,Object> map = new HashMap<>();
        map.put("responseOrders",responseOrders);
        return map;
    }
}
