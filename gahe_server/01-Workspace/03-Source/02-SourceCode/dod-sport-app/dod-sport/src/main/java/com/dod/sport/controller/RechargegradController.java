package com.dod.sport.controller;

import com.dod.sport.dao.IRechargegradDao;
import com.dod.sport.domain.common.BusiException;
import com.dod.sport.domain.po.Base.BaseRechargegrad;
import com.dod.sport.service.IRechargegradService;
import com.dod.sport.util.CommonEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员Controller
 * Created by Administrator on 2017/8/25.
 */
@Controller
@RequestMapping("api/rechargegrad/v1")
public class RechargegradController {

    @Autowired
    private IRechargegradService rechargegradService;
    @Autowired
    private IRechargegradDao rechargegradDao;
    /**
     * 根据会员卡id获取充值梯度列表
     * @param membcardId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getRechargegradList", method = RequestMethod.POST)
    public Map<String,Object> getRechargegradList(@RequestParam("membcardId") final String membcardId){
        Map<String,Object>map =  new HashMap<>();
        List<BaseRechargegrad> list = rechargegradService.queryRechargegradListByType(membcardId);
        if (list.size()<0){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_nodata.getValue());
        }
        map.put("list",list);
        return map;
    }

    /**
     * 新增充值梯度
     * @param membcardId 会员卡id
     * @param times 次数(次卡充值次数)
     * @param months 月数(期限卡充值多少个月)
     * @param nominalAmount 面值
     * @param creator
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addRechargegrad", method = RequestMethod.POST)
    public  Map<String,Object>  addRechargegrad(@RequestParam(value = "membcardId") final String membcardId,
                                   @RequestParam(value = "times", required = false ) final String times,
                                   @RequestParam(value = "months", required = false) final String months,
                                   @RequestParam(value = "nominalAmount") final String nominalAmount,
                                   @RequestParam(value = "creator") final String creator){
        BaseRechargegrad baseRechargegrad = new BaseRechargegrad();
        baseRechargegrad.setMembcardId(membcardId);
        if (times!=null&&!"".equals(times)){
            baseRechargegrad.setTimes(times);
        }
        if (months!=null && !"".equals(months)){
            baseRechargegrad.setMonths(months);
        }
        BaseRechargegrad querygrad = rechargegradService.queryRechargegradInfo(baseRechargegrad);
        if (querygrad !=null ){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_rechargegradexists.getValue());
        }
        baseRechargegrad.setNominalAmount(nominalAmount);
        baseRechargegrad.setCreator(creator);
        BaseRechargegrad returnRechargegrad = rechargegradService.addAndGetRechargegrad(baseRechargegrad);
        Map<String,Object>map =  new HashMap<>();
        map.put("baseRechargegrad",returnRechargegrad);
        return map;
    }

    /**
     * 删除充值梯度
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delRechargegradById", method = RequestMethod.POST)
    public String  delRechargegradById(@RequestParam(value = "id") final String id){
        rechargegradDao.delRechargegradById(id);
        return "";
    }

}
