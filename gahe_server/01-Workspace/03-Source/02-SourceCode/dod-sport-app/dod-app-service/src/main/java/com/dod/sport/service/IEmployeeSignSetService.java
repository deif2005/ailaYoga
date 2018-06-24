package com.dod.sport.service;

import com.dod.sport.domain.po.*;
import com.dod.sport.domain.po.Base.StoreWifi;

import java.text.ParseException;
import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */
public interface IEmployeeSignSetService {
    /**
     * 添加员工签到记录
     * @param emeployeeSign
     */
    public void addEmployeeSignTime(EmployeeSign emeployeeSign);

    /**
     * 获取员工签到记录
     * @param employeeId
     * @param storeId
     * @return
     */
    public List<EmployeeSign> queryEmployeeSignListByEmployeeId(String employeeId,String storeId);

    /**
     * 设置考勤规则
     * @param signTime
     */
    public void signTimeSet(SignTime signTime);

    public void addStoreWifi(String storeId,String wifiStr);

    /**
     *设置签到范围
     * @param signScope
     */
    public void addSignScope( SignScope signScope);

    /**
     * 获取商家打卡范围半径
     * @param storeId
     * @return
     */
    public SignScope querySignScopeByStoreId(String storeId);

    /**
     * 获取门店签到时间列表
     * @param storeId
     * @return
     */
    public List<SignTime> querySignTimeByStoreId(String storeId);

    /**
     * 查询排班详情
     * @param storeId
     * @param type 1;早班;2晚班
     * @return
     */
    public SignTime querySignTimeByType(String storeId,Integer type);

    /**
     * 删除签到删除排班
     * @param id
     */
    public void  delSignTimeById(String id);

    /**
     * 删除打卡范围
     * @param id
     */
    public void delSignScope(String id);

    public StoreWifi queryAllWifiByStoreId(String storeId);

    /**
     * 查询门店所有员工的考勤
     * @param baseResultSign
     * @return
     */
    public   List<AttendanceRecord> querySignListByStoreId(BaseResultSign baseResultSign,Integer page, Integer pageSize);

    /**
     * 获取该员工签到记录详情
     * @param baseResultSign
     * @return
     */
    public   AbnormalAttendance getEmployeeSignById(BaseResultSign baseResultSign);

    /**
     * 员工补签到记录
     * @param emeployeeSign
     * @return
     */
    public void  repairSignEmployeeById(EmployeeSign emeployeeSign);
}
