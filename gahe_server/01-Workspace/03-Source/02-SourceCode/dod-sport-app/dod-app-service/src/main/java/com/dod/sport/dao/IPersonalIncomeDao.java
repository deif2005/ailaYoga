package com.dod.sport.dao;

import com.dod.sport.domain.po.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * \* Date: 2017/9/15
 * \* Description:个人收入接口
 * \
 */
@Repository
public interface IPersonalIncomeDao {

    /**
     * 获取个人收入信息
     * @param storeId
     * @param employeeId
     * @return
     */
    public SalarySetting getPersonalIncomeList(@Param("storeId") String storeId,@Param("employeeId") String employeeId );

    /**
     * 获取团课次数
     * @param storeId
     * @param employeeId
     * @return
     */
    public String getCourseplanCount(@Param("storeId") String storeId,@Param("employeeId") String employeeId ,@Param("signTime") String signTime);

    /**
     * 获取私课次数
     * @param storeId
     * @param employeeId
     * @return
     */
    public String getPrivateCourseCount(@Param("storeId") String storeId,@Param("employeeId") String employeeId ,@Param("classDatetime") String classDatetime);

    /**
     * 获取t员工职位信息
     * @param storeId
     * @param employeeId
     * @return
     */
    public ResponseEmployee getEmployeePositioninfo(@Param("storeId") String storeId,@Param("employeeId") String employeeId);

    /**
     * 获取t员工职位信息
     * @param storeId
     * @param employeeId
     * @return
     */
    public String getEmployeeAchievementById(@Param("storeId") String storeId,@Param("employeeId") String employeeId,@Param("queryTime") String queryTime);

    /**
     * 获取员工职位信息
     * @param storeId
     * @return
     */
    public List<Achievement> getAchievementCommission(@Param("storeId") String storeId);

    /**
     * 获取收入比例
     * @param storeId
     * @param employeeId
     * @return
     */
    public List<PersonalIncome> getIncomeProportionById(@Param("storeId") String storeId ,@Param("employeeId") String employeeId,@Param("queryTime") String queryTime);
}