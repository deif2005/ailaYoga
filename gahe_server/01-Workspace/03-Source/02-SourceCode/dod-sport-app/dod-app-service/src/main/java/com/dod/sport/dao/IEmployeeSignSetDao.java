package com.dod.sport.dao;

import com.dod.sport.domain.po.*;
import com.dod.sport.domain.po.Base.StoreWifi;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */
@Repository
public interface IEmployeeSignSetDao {

    /**
     * 添加员工签到记录
     * @param emeployeeSign
     */
    public void addEmployeeSignTime(EmployeeSign emeployeeSign);

    /**
     * 获取员工签到记录
     * @param employeeId
     * @return
     */
    public List<EmployeeSign> queryEmployeeSignListByEmployeeId(@Param("employeeId")String employeeId ,
                                                                @Param("storeId")String storeId);

    /**
     * 查询排班详情
     * @param storeId
     * @param type 1;早班;2晚班
     * @return
     */
    public SignTime querySignTimeByType(@Param("storeId") String storeId,
                                        @Param("type") Integer type);

    /**
     * 删除排班
     * @param id
     */
    public void delSignTimeById(String id);

    /**
     * 设置签到规则
     * @param signTime
     */
    public void signTimeSet(SignTime signTime);

    /**
     * 新增WiFi热点
     * @param wifi
     */
    public void addStoreWifi(StoreWifi wifi);
    /**
     * 设置签到范围
     * @param signScope
     */
    public void addSignScope(SignScope signScope);

    /**
     * 删除打卡范围
     * @param id
     */
    public void delSignScope(String id);

    /**
     * 获取门店所有WiFi热点
     * @param storeId
     * @return
     */
    public StoreWifi queryAllWifiByStoreId(String storeId);

    /**
     * 删除门店中的WiFi
     * @param storeId
     */
    public void delStoreWifi(String storeId);

    public StoreWifi querywifiInfo(@Param("storeId")String storeId,
                                   @Param("wifiId")String wifiId);
    /**
     * 获取热点
     * @param wifiId
     * @return
     */
    public StoreWifi queryWifiById(String wifiId );
    /**
     * 获取商家打卡范围半径
     * @param storeId
     * @return
     */
    public SignScope querySignScopeByStoreId(String storeId);

    /**
     * 获取商家打卡规则
     * @param storeId
     * @return
     */
    public List<SignTime> querySignTimeByStoreId(String storeId);

    /**
     * 查询该门店所有的考勤记录
     * @param baseResultSign
     * @return
     */
    public List<BaseResultSign> querySignListByStoreId(BaseResultSign baseResultSign);

    /**
     * 查询该门店的上班规则
     * @return
     */
    public List<WorkWeek> getWorkWeekById(String storeId);

    /**
     * 查询员工请假单据
     * @return
     */
    public String getEmpolyeeBaseBillById(BaseResultSign baseResultSign);

    /**
     * 查询员工考勤信息
     * @return
     */
    public List<BaseResultSign> queryEmpolyeeSignListById(BaseResultSign baseResultSign);

    /**
     * 获取签到规则列表
     * @return
     */
    public SignScope  querySignTimeList(String storeId);


    public EmployeeSign getempSignByTime(@Param("employeeId")String employeeId,
                                         @Param("nowDate") String nowDate,
                                         @Param("signType") Integer signType);

}
