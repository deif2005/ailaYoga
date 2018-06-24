package com.dod.sport.service.impl;

import com.dod.sport.constant.SysConfigConstants;
import com.dod.sport.dao.IOrderDao;
import com.dod.sport.domain.common.BatchDataPage;
import com.dod.sport.domain.po.Member.MembercardModify;
import com.dod.sport.domain.po.Order;
import com.dod.sport.domain.po.ResponseOrder;
import com.dod.sport.domain.po.SaleDetail;
import com.dod.sport.service.IMembecardmodifyService;
import com.dod.sport.service.IMembercardRelationService;
import com.dod.sport.service.IOrderService;
import com.dod.sport.util.CommonEnum;
import com.dod.sport.util.RedisCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2017/11/15.
 */
@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private IOrderDao orderDao;
    @Autowired
    private IMembercardRelationService membercardRelationService;
    @Autowired
    private IMembecardmodifyService membecardmodifyService;

    /**
     * 获取订单的订单号 格式:201711091806000000001
     * @param date
     * @return
     */
    @Override
    public String getOrderSerialId(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddkkmmss");
        String dateStr = sdf.format(date);
        String orderSerialId = RedisCommon.getMaxOrderSerialId(dateStr);
        if (orderSerialId==null ||orderSerialId==""){
            String orderSerialIdMax = orderDao.getOrderSerialIdMax(dateStr);
            if (orderSerialIdMax.equals("0")){
                orderSerialIdMax = dateStr+"000000000";
            }
            orderSerialId = RedisCommon.setAndReturnMaxOrderSerialId(dateStr, orderSerialIdMax);
        }
        return orderSerialId;
    }

    /**
     * 新增订单
     * @param order
     */
    @Override
    public void addOrder(Order order) {
        orderDao.addOrder(order);
    }

    /**
     * 根据id获取订单信息
     * @param id
     * @return
     */
    @Override
    public Order getOrderById(String id) {
        return orderDao.getOrderById(id);
    }

    /**
     * 编辑订单信息
     * @param order
     */
    @Override
    public void updateOrder(Order order) {
        orderDao.updateOrder(order);
    }

    /**
     * 新增销售详情
     * @param saleDetail
     */
    @Override
    public void addOrderDetail(SaleDetail saleDetail) {
        saleDetail.setId(UUID.randomUUID().toString());
        orderDao.addOrderDetail(saleDetail);
    }

    /**
     * 获取销售详情
     * @param id
     * @return
     */
    @Override
    public SaleDetail getOrderDetailById(String id) {
        return orderDao.getOrderDetailById(id);
    }

    /**
     * 更新销售详情
     * @param saleDetail
     */
    @Override
    public void updateOrderDetail(SaleDetail saleDetail) {
        orderDao.updateOrder(saleDetail);
    }

    /**
     * 根据订单号获取销售信息
     * @param orderSerialId
     * @return
     */
    @Override
    public List<ResponseOrder>getOrderInfoByOrderSerialId(String orderSerialId ){
        return orderDao.getOrderInfoByOrderSerialId(orderSerialId);
    }

    /**
     * 完成订单
     * @param orderSerialId
     */
    @Override
    @Transactional
    public void completeOrderByOrderSerialId(String orderSerialId , String orderStatus) throws ParseException {
        //1:根据订单好获取销售信息列表
        List<ResponseOrder> responseOrderList = this.getOrderInfoByOrderSerialId(orderSerialId);
        //判断该订单是已支付,还是取消订单
        //2:订单具体操作
        for (ResponseOrder responseOrder:responseOrderList) {
            responseOrder.setOrderStatus(orderStatus);
            String saleType = responseOrder.getSaleType();
            if (orderStatus.equals(CommonEnum.Order.orderStatus.orderStatus_prepaid.getValue())) {//已支付
                //销售类型：1:商品;2:店内服务;3:增值服务,9:其它
                if (saleType.equals(CommonEnum.SaleDetail.saleType.sale_product.getValue())) {
                    return;//暂未开发
                } else if (saleType.equals(CommonEnum.SaleDetail.saleType.sale_store_service.getValue())) {
                    this.updateProduct(responseOrder);
                } else if (saleType.equals(CommonEnum.SaleDetail.saleType.sale_added_service.getValue())) {
                    return;//暂未开发
                } else if (saleType.equals(CommonEnum.SaleDetail.saleType.sale_other.getValue())) {
                    return;//暂未开发
                }
            }else if (orderStatus.equals(CommonEnum.Order.orderStatus.orderStatus_cancel.getValue())){//取消订单
                //销售类型：1:商品;2:店内服务;3:增值服务,9:其它
                if (saleType.equals(CommonEnum.SaleDetail.saleType.sale_product.getValue())) {
                    return;//暂未开发
                } else if (saleType.equals(CommonEnum.SaleDetail.saleType.sale_store_service.getValue())) {
                    this.updateProduct(responseOrder);
                } else if (saleType.equals(CommonEnum.SaleDetail.saleType.sale_added_service.getValue())) {
                    return;//暂未开发
                } else if (saleType.equals(CommonEnum.SaleDetail.saleType.sale_other.getValue())) {
                    return;//暂未开发
                }
            }
        }
        //3:更新订单信息
        Order order = new Order();
        order.setOrderSerialId(orderSerialId);
        order.setOrderStatus(orderStatus);
        this.updateOrder(order);
    }

    /**
     * 更新商品信息信息
     * @param responseOrder
     */
    @Override
    public void updateProduct(ResponseOrder responseOrder) throws ParseException {
        String modifyType = responseOrder.getModifyType();
        if (responseOrder.getOrderStatus().equals(CommonEnum.Order.orderStatus.orderStatus_prepaid.getValue())){//已支付
            //变更类型:;3换卡;7开卡;9:请假;10:充值
            if (modifyType.equals(CommonEnum.Membercardmodify.modifyType.modify_change.getValue())){
                membercardRelationService.memberCardChange(responseOrder);
            } else if (modifyType.equals(CommonEnum.Membercardmodify.modifyType.modify_open.getValue())){
                membercardRelationService.addMemberCardRelation(responseOrder);
            } else if (modifyType.equals(CommonEnum.Membercardmodify.modifyType.modify_leave.getValue())){
               //更新请假操作,调用请假接口
            }else if (modifyType.equals(CommonEnum.Membercardmodify.modifyType.modify_recharge.getValue())){
                membercardRelationService.memberCardRecharge(responseOrder);
            }
        }else if (responseOrder.getOrderStatus().equals(CommonEnum.Order.orderStatus.orderStatus_prepaid.getValue())){//未支付
            MembercardModify modify = new MembercardModify();
            modify.setId(responseOrder.getId());
            modify.setStatus(CommonEnum.Membercardmodify.status.status_del.getValue());//将单据设置成删除状态
            membecardmodifyService.updateMembercardModify(modify);
        }
        SimpleDateFormat sdf = new SimpleDateFormat(SysConfigConstants.DATE_FORMAT_FORDATETIME);
        //更新变更记录状态
        MembercardModify membercardModify = new MembercardModify();
        membercardModify.setId(responseOrder.getId());
        membercardModify.setStatus(responseOrder.getOrderStatus());
        membercardModify.setModifyTime(sdf.format(new Date()));//设置完成换卡时间时间
        membecardmodifyService.updateMembercardModify(membercardModify);
    }

    /**
     * 获取会员卡开卡和充值订单
     * @param membercardRelationId
     * @param page
     * @return
     */
    @Override
    public List<ResponseOrder> listRechargeOrOpenOrderRecord(String membercardRelationId, Integer page) {
        BatchDataPage batchDataPage = new BatchDataPage();
        batchDataPage.setPage(page);
        return orderDao.listRechargeOrOpenOrderRecord(membercardRelationId,batchDataPage.getOffset(), batchDataPage.getPagesize());
    }

    /**
     *获取要取消的的订单号
     * @return
     */
    @Override
    public List<String> listToCancelOrder( ) {
        return orderDao.listToCancelOrder();
    }


}
