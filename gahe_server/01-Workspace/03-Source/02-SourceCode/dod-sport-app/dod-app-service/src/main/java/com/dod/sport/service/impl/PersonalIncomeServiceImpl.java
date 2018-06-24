package com.dod.sport.service.impl;

import com.dod.sport.dao.IPersonalIncomeDao;
import com.dod.sport.domain.po.*;
import com.dod.sport.service.IPersonalIncomeService;
import com.dod.sport.util.BusiUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ��ȡ����������Ϣ
 *
 */
@Service
public class PersonalIncomeServiceImpl implements IPersonalIncomeService {

    @Autowired
    IPersonalIncomeDao personalIncomeDao;

    /**
     * ��ȡ����������Ϣ
     * @param storeId
     * @param employeeId
     * @return
     */
    @Override
    public List<PersonalIncome> getPersonalIncomeList(String storeId,String employeeId ,String queryTime){
        List<PersonalIncome> personalIncomesList = new ArrayList<PersonalIncome>();
        for(int i=0 ;i <= 5;i++) {
            try
            {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date date = sdf.parse(queryTime);
                String dateTime = BusiUtils.getNextDate(date,-1,"yyyy-MM");

                SalarySetting salarySetting = personalIncomeDao.getPersonalIncomeList(storeId, employeeId);
                String courseplanCount = personalIncomeDao.getCourseplanCount(storeId, employeeId, dateTime);
                String privateCourseCount = personalIncomeDao.getPrivateCourseCount(storeId, employeeId, dateTime);
                ResponseEmployee base = personalIncomeDao.getEmployeePositioninfo(storeId, employeeId);
                String balance = personalIncomeDao.getEmployeeAchievementById(storeId, employeeId, dateTime);
                List<Achievement> achievement = personalIncomeDao.getAchievementCommission(storeId);
                PersonalIncome personalIncome = new PersonalIncome();
                //��λ����
                personalIncome.setBasePay(String.valueOf(Double.parseDouble(salarySetting.getBasePay()) + Double.parseDouble(salarySetting.getPositionPay())));
                //����
                Double allowance = Double.parseDouble(salarySetting.getHousingSubsidies()) + Double.parseDouble(salarySetting.getMealAllowance())
                        + Double.parseDouble(salarySetting.getPhoneSubsidy()) + Double.parseDouble(salarySetting.getTravelAllowance()) +
                        Double.parseDouble(salarySetting.getAttendanceReward()) + Double.parseDouble(salarySetting.getManagementAllowance() +
                        Double.parseDouble(salarySetting.getOther()));
                personalIncome.setAllowance(String.valueOf(allowance));
                Double comission = 0.0;
                if ((base.getEmpRela()).equals("1")) {
                    if (base.getJobTitle() .equals("1")) {
                        comission = comission + Double.parseDouble(salarySetting.getJuniorCoach()) * Double.parseDouble(courseplanCount);
                        comission = comission + Double.parseDouble(salarySetting.getJuniorCoach())
                                * Double.parseDouble(privateCourseCount) * Double.parseDouble(salarySetting.getConcurrentPost()) * Double.parseDouble(salarySetting.getConcurrentPost());
                    } else if (base.getJobTitle() .equals("2")) {
                        comission = comission + Double.parseDouble(salarySetting.getIntermediateCoach()) * Double.parseDouble(courseplanCount);
                        comission = comission + Double.parseDouble(salarySetting.getIntermediateCoach())
                                * Double.parseDouble(privateCourseCount) * Double.parseDouble(salarySetting.getConcurrentPost()) * Double.parseDouble(salarySetting.getConcurrentPost());
                    } else if (base.getJobTitle() .equals("3")) {
                        comission = comission + Double.parseDouble(salarySetting.getSeniorCoach()) * Double.parseDouble(courseplanCount);
                        comission = comission + Double.parseDouble(salarySetting.getSeniorCoach())
                                * Double.parseDouble(privateCourseCount) * Double.parseDouble(salarySetting.getConcurrentPost()) * Double.parseDouble(salarySetting.getConcurrentPost());
                    }
                } else {
                    if (base.getJobTitle() .equals("1")) {
                        comission = comission + Double.parseDouble(salarySetting.getJuniorCoach()) * Double.parseDouble(courseplanCount);
                        comission = comission + Double.parseDouble(salarySetting.getJuniorCoach())
                                * Double.parseDouble(privateCourseCount) * Double.parseDouble(salarySetting.getConcurrentPost()) * Double.parseDouble(salarySetting.getPrivateConcurrentPost());
                    } else if (base.getJobTitle() .equals("2")) {
                        comission = comission + Double.parseDouble(salarySetting.getIntermediateCoach()) * Double.parseDouble(courseplanCount);
                        comission = comission + Double.parseDouble(salarySetting.getIntermediateCoach())
                                * Double.parseDouble(privateCourseCount) * Double.parseDouble(salarySetting.getConcurrentPost()) * Double.parseDouble(salarySetting.getPrivateConcurrentPost());
                    } else if (base.getJobTitle() .equals("3")) {
                        comission = comission + Double.parseDouble(salarySetting.getSeniorCoach()) * Double.parseDouble(courseplanCount);
                        comission = comission + Double.parseDouble(salarySetting.getSeniorCoach())
                                * Double.parseDouble(privateCourseCount) * Double.parseDouble(salarySetting.getConcurrentPost()) * Double.parseDouble(salarySetting.getPrivateConcurrentPost());
                    }
                }

                //�γ����
                personalIncome.setCommission(String.valueOf(comission));
                Double achievementMax = 0.0;
                Double commissionMax = 0.0;
                for (int j = 0; j < achievement.size(); j++) {
                    if (Integer.parseInt(balance) >= Integer.parseInt(achievement.get(j).getAchievement())) {
                        if (achievementMax > Double.parseDouble(achievement.get(j).getAchievement())) {
                            achievementMax = Double.parseDouble(achievement.get(j).getAchievement());
                            commissionMax = Double.parseDouble(achievement.get(j).getCommission());
                        }
                    }
                }
                //ҵ�����
                personalIncome.setCourseCommission(String.valueOf(commissionMax * Double.parseDouble(balance)));
                personalIncome.setTotal(String.valueOf(Double.parseDouble(personalIncome.getBasePay())
                                + Double.parseDouble(personalIncome.getCommission())
                                + Double.parseDouble(personalIncome.getCourseCommission())
                                + Double.parseDouble(personalIncome.getAllowance())
                                + Double.parseDouble(personalIncome.getBonus())
                                + Double.parseDouble(personalIncome.getAchievements())
                                + Double.parseDouble(personalIncome.getReceiveDividents())
                                - Double.parseDouble(personalIncome.getFine())
                                - Double.parseDouble(salarySetting.getOcialSecurity())
                                - Double.parseDouble(salarySetting.getAccumulationFund())
                                - Double.parseDouble(salarySetting.getPersonalTax())
                ));
                personalIncome.setIncomeTime(dateTime);
                personalIncomesList.add(personalIncome);
            }
            catch (ParseException e) {
            }
        }

        return personalIncomesList ;
    }

    /**
     * ��ȡ����������Ϣ
     * @param storeId
     * @param employeeId
     * @return
     */
    @Override
    public List<PersonalIncome> getIncomeProportionById(String storeId,String employeeId ,String queryTime){
        return personalIncomeDao.getIncomeProportionById(storeId, employeeId,queryTime);
    }


}