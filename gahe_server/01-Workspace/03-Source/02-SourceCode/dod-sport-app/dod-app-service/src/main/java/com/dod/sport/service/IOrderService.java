package com.dod.sport.service;

import com.dod.sport.domain.po.Order;
import com.dod.sport.domain.po.ResponseOrder;
import com.dod.sport.domain.po.SaleDetail;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/11/15.
 */
public interface IOrderService {

    /**
     * 获取订单的订单号 格式:201711091806000000001
     * @param date
     * @return
     */

    public String getOrderSerialId(Date date);
    /**
     * 新增订单
     * @param order
     */
    public void addOrder(Order order);

    /**
     * 获取订单信息
     * @param id
     * @return
     */
    public Order getOrderById(String id);

    /**
     * 更新订单信息
     * @param order
     */
    public void updateOrder(Order order);

    /**
     * 新增订单详细表
     * @param saleDetail
     */
    public void addOrderDetail(SaleDetail saleDetail);

    /**
     * 获取订单详细信息
     * @param id
     * @return
     */
    public SaleDetail getOrderDetailById(String id);

    /**
     * 更新订单详细信息
     * @param saleDetail
     */
    public void updateOrderDetail(SaleDetail saleDetail);

    /**
     * 根据订单号获取订单详细信息
     * @param orderSerialId
     * @return
     */
    public List<ResponseOrder>getOrderInfoByOrderSerialId(String orderSerialId );

    /**
     * 完成订单
     * @param orderSerialId
     */
    public void completeOrderByOrderSerialId(String orderSerialId ,String orderStatus) throws ParseException;

    /**
     * 更新操作记录
     * @param responseOrder
     */
    public void updateProduct(ResponseOrder responseOrder) throws ParseException;

    /**
     * 获取会员卡开卡和充值订单
     * @param membercardRelationId
     * @param page
     * @return
     */
    public List<ResponseOrder> listRechargeOrOpenOrderRecord(String membercardRelationId, Integer page );

    /**
     *获取要取消的的订单号
     * @return
     */
    public List<String>listToCancelOrder( );
}
