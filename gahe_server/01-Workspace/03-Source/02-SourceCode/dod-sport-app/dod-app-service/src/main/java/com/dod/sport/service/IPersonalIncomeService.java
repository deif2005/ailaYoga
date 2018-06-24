package com.dod.sport.service;

import com.dod.sport.domain.po.PersonalIncome;

import java.util.List;

/**
 * \* Date: 2017/9/15
 * \* Description:个人收入接口
 * \
 */

public  interface  IPersonalIncomeService {

    /**
     * 获取个人收入信息
     * @param storeId
     * @param employeeId
     * @return
     */
    public List<PersonalIncome> getPersonalIncomeList(String storeId,String employeeId ,String queryTime);


    /**
     * 获取个人收入比例
     * @param storeId
     * @param employeeId
     * @return
     */
    public List<PersonalIncome> getIncomeProportionById(String storeId,String employeeId ,String queryTime);

}