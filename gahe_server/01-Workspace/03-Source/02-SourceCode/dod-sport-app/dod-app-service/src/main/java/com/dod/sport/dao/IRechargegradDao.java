package com.dod.sport.dao;

import com.dod.sport.domain.po.Base.BaseRechargegrad;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */
@Repository
public interface IRechargegradDao {

    /**
     * 根据会员卡类型获取相应的充值梯度
     * @param cardtypeId
     * @return
     */
    public List<BaseRechargegrad> queryRechargegradListByType(String cardtypeId);

    /**
     * 根据梯度id查询充值梯度
     * @param rechargegradId
     * @return
     */
    public  BaseRechargegrad queryRechargegradById( String rechargegradId);

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
     * 获取充值梯度最大编号
     * @return
     */
    public String queryRecjargegradIdMax();

    /**
     * 删除充值梯度
     * @param id
     */
    public void delRechargegradById(String id);

}
