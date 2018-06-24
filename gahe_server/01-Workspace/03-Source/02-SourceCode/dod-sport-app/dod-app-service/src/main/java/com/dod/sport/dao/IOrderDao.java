package com.dod.sport.dao;

import com.dod.sport.domain.po.Order;
import com.dod.sport.domain.po.ResponseOrder;
import com.dod.sport.domain.po.SaleDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

/**
 * Created by Administrator on 2017/11/15.
 */
@Repository
public interface IOrderDao {
    /**
     * 获取最大的订单编号
     * @param dateTimeStr
     * @return
     */
    public String getOrderSerialIdMax(@Param("dateTimeStr")String dateTimeStr);

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
    public void updateOrder(SaleDetail saleDetail);

    /**
     * 根据订单号获取订单详情信息
     * @param orderSerialId
     * @return
     */
    public List<ResponseOrder>getOrderInfoByOrderSerialId(String orderSerialId );

    /**
     * 获取会员卡开卡和充值订单
     * @param membercardRelationId
     * @param startPage
     * @param endPage
     * @return
     */
    public List<ResponseOrder> listRechargeOrOpenOrderRecord(@Param("membercardRelationId")String membercardRelationId,
                                                             @Param("startPage")Integer startPage,
                                                             @Param("endPage")Integer endPage);

    /**
     *获取要取消的的订单号
     * @return
     */
    public List<String>listToCancelOrder();
}
