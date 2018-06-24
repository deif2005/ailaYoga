package com.dod.sport.service.impl;

import com.dod.sport.dao.IRechargegradDao;
import com.dod.sport.domain.po.Base.BaseRechargegrad;
import com.dod.sport.service.IRechargegradService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * 充值梯度业务成实现类
 * Created by Administrator on 2017/8/31.
 */
@Service
public class RechargegradServiceImpl implements IRechargegradService {
    @Autowired
    private IRechargegradDao rechargegradDao;

    /**
     * 根据会员卡类型id获取相应的充值梯度
     * @param cardtypeId
     * @return
     */
    @Override
    public List<BaseRechargegrad> queryRechargegradListByType(String cardtypeId) {
        return rechargegradDao.queryRechargegradListByType(cardtypeId);
    }

    /**
     * 根据梯度id查询充值梯度
     * @param rechargegradId
     * @return
     */
    @Override
    public BaseRechargegrad queryRechargegradById(String rechargegradId) {
        return rechargegradDao.queryRechargegradById(rechargegradId);
    }

    /**
     * 查询充值梯度详情
     * @param baseRechargegrad
     * @return
     */
    @Override
    public BaseRechargegrad queryRechargegradInfo(BaseRechargegrad baseRechargegrad) {
        return rechargegradDao.queryRechargegradInfo(baseRechargegrad);
    }

    /**
     * 新增充值梯度
     * @param baseRechargegrad
     */
    @Override
    public void addRechargegrad(BaseRechargegrad baseRechargegrad) {
        if (baseRechargegrad.getId()==null ||"".equals(baseRechargegrad.getId())){
            String id = UUID.randomUUID().toString();
            baseRechargegrad.setId(id);
        }
        rechargegradDao.addRechargegrad(baseRechargegrad);
    }

    @Override
    @Transactional
    public BaseRechargegrad addAndGetRechargegrad(BaseRechargegrad baseRechargegrad) {
        String id = UUID.randomUUID().toString();
        baseRechargegrad.setId(id);
        this.addRechargegrad(baseRechargegrad);
        return baseRechargegrad;
    }
}
