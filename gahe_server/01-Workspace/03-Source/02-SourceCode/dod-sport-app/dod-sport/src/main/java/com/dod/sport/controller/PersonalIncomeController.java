package com.dod.sport.controller;

import com.dod.sport.domain.po.PersonalIncome;
import com.dod.sport.service.IPersonalIncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by defi on 2017-09-15.
 * 个人收入接口
 */

@Controller
@RequestMapping(value = "api/PersonalIncome/v1")
public class PersonalIncomeController {

    @Autowired
    IPersonalIncomeService personalIncomeService;


    /**
     * 获取个人收入信息
     * @param storeId
     * @param employeeId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPersonalIncomeList", method = RequestMethod.POST)
    public List<PersonalIncome>  getPersonalIncomeList(@RequestParam(value = "storeId") final String storeId,
                                                       @RequestParam(value = "employeeId") final String employeeId,
                                                       @RequestParam(value = "queryTime") final String queryTime) {

        List<PersonalIncome>  personalIncome = personalIncomeService.getPersonalIncomeList(storeId, employeeId ,queryTime);
        return personalIncome;
    }


    /**
     * 获取个人收入比例
     * @param storeId
     * @param employeeId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getIncomeProportionById", method = RequestMethod.POST)
    public List<PersonalIncome>  getIncomeProportionById(@RequestParam(value = "storeId") final String storeId,
                                                       @RequestParam(value = "employeeId") final String employeeId,
                                                       @RequestParam(value = "queryTime") final String queryTime) {

        List<PersonalIncome>  personalIncome = personalIncomeService.getIncomeProportionById(storeId, employeeId ,queryTime);
        return personalIncome;
    }

}