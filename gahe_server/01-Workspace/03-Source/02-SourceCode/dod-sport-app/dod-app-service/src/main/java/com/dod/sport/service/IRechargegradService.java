package com.dod.sport.service;

import com.dod.sport.domain.po.Base.BaseRechargegrad;

import java.util.List;

/**
 * 充值梯度业务层接口
 * Created by Administrator on 2017/8/31.
 */
public interface IRechargegradService {
    /**
     * 根据会员卡类型获取相应的充值梯度
     * @param cardtypeId
     * @return
     */
    public List<BaseRechargegrad> queryRechargegradListByType(String cardtypeId);

    /**
     * 根据充值梯度id获取充值梯度信息
     * @param rechargegradId
     * @return
     */
    public  BaseRechargegrad queryRechargegradById(String rechargegradId);

    /**
     * 查询充值梯度详情
     * @param baseRechargegrad
     * @return
     */
    public BaseRechargegrad queryRechargegradInfo(BaseRechargegrad baseRechargegrad);

    /**
     * 新增充值梯度
     * @param baseRechargegrad
     */
    public void addRechargegrad(BaseRechargegrad baseRechargegrad);

    /**
     *新增并获取充值梯度
     * @param baseRechargegrad
     * @return
     */
    public BaseRechargegrad addAndGetRechargegrad(BaseRechargegrad baseRechargegrad);
}
