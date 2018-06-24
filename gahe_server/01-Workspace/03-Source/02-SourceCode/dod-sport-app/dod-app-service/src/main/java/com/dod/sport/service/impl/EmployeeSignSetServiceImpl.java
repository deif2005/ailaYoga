package com.dod.sport.service.impl;

import com.dod.sport.config.Configuration;
import com.dod.sport.dao.IEmployeeDao;
import com.dod.sport.dao.IEmployeeSignSetDao;
import com.dod.sport.domain.common.BatchDataPage;
import com.dod.sport.domain.common.BusiException;
import com.dod.sport.domain.common.StringGZIPUtils;
import com.dod.sport.domain.po.*;
import com.dod.sport.domain.po.Base.StoreWifi;
import com.dod.sport.service.IEmployeeSignSetService;
import com.dod.sport.util.BusiUtils;
import com.dod.sport.util.CommonEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Null;
import java.text.ParseException;
import java.util.*;

/**
 * Created by Administrator on 2017/8/31.
 */
@Service
public class EmployeeSignSetServiceImpl implements IEmployeeSignSetService {

    protected static Logger logger = LoggerFactory.getLogger(EmployeeSignSetServiceImpl.class);

    @Autowired
    IEmployeeSignSetDao employeeSignSetDao;
    @Autowired
    IEmployeeDao employeeDao;

    /**
     * 添加签到记录
     * @param emeployeeSign
     */
    @Override
    public void addEmployeeSignTime(EmployeeSign emeployeeSign) {
        employeeSignSetDao.addEmployeeSignTime(emeployeeSign);
    }

    @Override
    public List<EmployeeSign> queryEmployeeSignListByEmployeeId(String employeeId ,String storeId) {
        return employeeSignSetDao.queryEmployeeSignListByEmployeeId(employeeId ,storeId);
    }

    /**
     * 设置签到规则
     * @param signTime
     */
    @Override
    public void signTimeSet(SignTime signTime) {
        employeeSignSetDao.signTimeSet(signTime);
    }

    /**
     * 添加WiFi热点
     * @param storeId
     * @param wifiId
     */
    @Override
    @Transactional
    public void addStoreWifi(String storeId, String wifiId) {
        StoreWifi querywifi = employeeSignSetDao.queryAllWifiByStoreId(storeId);
        if (querywifi!=null){
            StoreWifi wifi = employeeSignSetDao.querywifiInfo(storeId, wifiId);
            if (wifi !=null){
                return;
            }else {
                employeeSignSetDao.delStoreWifi(storeId);
            }
        }
        StoreWifi wifi = new StoreWifi();
        wifi.setStoreId(storeId);
        wifi.setWifiId(wifiId);
        String id = UUID.randomUUID().toString();
        wifi.setId(id);
        employeeSignSetDao.addStoreWifi(wifi);
    }

    /**
     *设置签到范围半径
     * @param signScope
     */
    @Override
    public void addSignScope(SignScope signScope) {
        employeeSignSetDao.addSignScope(signScope);
    }

    /**
     * 获取打卡半径
     * @param storeId
     * @return
     */
    @Override
    public SignScope querySignScopeByStoreId(String storeId) {
        return employeeSignSetDao.querySignScopeByStoreId(storeId);
    }

    @Override
    public List<SignTime> querySignTimeByStoreId(String storeId) {
        return employeeSignSetDao.querySignTimeByStoreId(storeId);
    }

    /**
     * 查询排班详情
     * @param storeId
     * @param type 1;早班;2晚班
     * @return
     */
    @Override
    public SignTime querySignTimeByType(String storeId, Integer type) {
        return employeeSignSetDao.querySignTimeByType( storeId, type);
    }
    /**
     * 删除签到删除排班
     * @param id
     */
    @Override
    public void delSignTimeById(String id) {
        employeeSignSetDao.delSignTimeById(id);
    }

    /**
     * 删除打卡范围
     * @param id
     */
    @Override
    public void delSignScope(String id) {
        employeeSignSetDao.delSignScope(id);
    }

    /**
     * 获取门店所有wifi热点
     * @param storeId
     * @return
     */
    @Override
    public StoreWifi queryAllWifiByStoreId(String storeId) {
        return employeeSignSetDao.queryAllWifiByStoreId(storeId);
    }

    /**
     * 查询门店所有员工的考勤
     * @param baseResultSign
     * @return
     */
    @Override
    public  List<AttendanceRecord> querySignListByStoreId(BaseResultSign baseResultSign,Integer page, Integer pageSize) {

        BatchDataPage batchDataPage = new BatchDataPage();
        batchDataPage.setPage(page);
        if (pageSize !=null && pageSize !=0){
            batchDataPage.setPagesize(pageSize);
        }

        List<ResponseEmployee> empList = employeeDao.getEmpBusinessRelationByStoreId(baseResultSign.getStoreId(),baseResultSign.getEmployeeName(),baseResultSign.getEmployeeId(), batchDataPage.getOffset(),
                batchDataPage.getPagesize());
        String queryTime = baseResultSign.getQueryTime();
        String[] arrStr = queryTime.split("-");
        int year = Integer.parseInt(arrStr[0]);
        int mouth =Integer.parseInt(arrStr[1]);
        int days = BusiUtils.getDaysByYearMonth(year,mouth);
        if (empList!=null||empList.size()>0){
            for (ResponseEmployee busiEmployee:empList){
                baseResultSign.setEmployeeId(busiEmployee.getId());
                Map<String, Object> map = new HashMap<>();

                ResponseEmployee  returnEmp = new ResponseEmployee();
                //查询迟到记录
                baseResultSign.setSignStatus("2");
                List<BaseResultSign>lastSignList = employeeSignSetDao.querySignListByStoreId(baseResultSign);
                busiEmployee.setLasttimes(String.valueOf(lastSignList.size()));
                //查询早退记录
                baseResultSign.setSignStatus("3");
                List<BaseResultSign>leaveSignList = employeeSignSetDao.querySignListByStoreId(baseResultSign);
                busiEmployee.setLeavetimes(String.valueOf(leaveSignList.size()));

            }
        }

        BaseResultSign  bases  = new BaseResultSign();
        for (ResponseEmployee baseEmployee:empList) {
            bases.setEmployeeId(baseEmployee.getId());
            bases.setQueryTime(queryTime);
            bases.setStoreId(baseResultSign.getStoreId());
            int failurePunch = 0;
            List<BaseResultSign> empSignList = employeeSignSetDao.queryEmpolyeeSignListById(bases);
            for (int i = 1; i <= days; i++) {
                String dateTime = queryTime + "-" + i;
                int index = 0;
                for (BaseResultSign base : empSignList) {
                    String signTime = base.getSignTime();
                    String[] array = signTime.split("-");
                    String date = array[0] + "-" + array[1] + "-" + array[2];
                    if (dateTime.equals(date)) {
                        index++;
                    }
                }
                if (index < 2) {
                    baseResultSign.setQueryTime(dateTime);
                    String empId = employeeSignSetDao.getEmpolyeeBaseBillById(baseResultSign);
                    if (empId == null) {
                        failurePunch++;
                    }
                }
            }
            baseEmployee.setFailurePunch(String.valueOf(failurePunch));
        }
        List<AttendanceRecord> attendanceRecordList = new ArrayList<AttendanceRecord>();
        for (ResponseEmployee busiEmployee:empList) {
            AttendanceRecord  attendanceRecord  = new AttendanceRecord();
            attendanceRecord.setEmpId(busiEmployee.getId());
            attendanceRecord.setDepId(busiEmployee.getDepId());
            attendanceRecord.setEmployeeSerialId(busiEmployee.getEmployeeSerialId());
            attendanceRecord.setEmployeeName(busiEmployee.getEmployeeName());
            attendanceRecord.setDepName(busiEmployee.getDepName());
            attendanceRecord.setLeavetimes(busiEmployee.getLeavetimes());
            attendanceRecord.setLasttimes(busiEmployee.getLasttimes());
            attendanceRecord.setStoreId(busiEmployee.getStoreId());
            attendanceRecord.setFailurePunch(busiEmployee.getFailurePunch());
            attendanceRecord.setPositionName(busiEmployee.getPositionName());
            attendanceRecord.setEmpHead(Configuration.getStaticShowPath()+busiEmployee.getEmpHead());
            attendanceRecordList.add(attendanceRecord);

        }
        return attendanceRecordList;
    }

    /**
     * 查询门店所有员工的考勤
     * @param baseResultSign
     * @return
     */
    @Override
    public  AbnormalAttendance getEmployeeSignById(BaseResultSign baseResultSign){

        String queryTime = baseResultSign.getQueryTime();
        String[] arrStr = queryTime.split("-");
        int year = Integer.parseInt(arrStr[0]);
        int mouth =Integer.parseInt(arrStr[1]);
        int days = BusiUtils.getDaysByYearMonth(year,mouth);

        ResponseEmployee responseEmployee = employeeDao.getEmpBusinessRelationById(baseResultSign.getEmployeeId());
        AbnormalAttendance abnormalAttendance = new  AbnormalAttendance();

        baseResultSign.setSignStatus("2");
        List<BaseResultSign>lastSignList = employeeSignSetDao.querySignListByStoreId(baseResultSign);
        abnormalAttendance.setLeavetimes(String.valueOf(lastSignList.size()));
        StringBuffer lateTime = new StringBuffer();
        if(lastSignList != null ){
            int  index = 0;
            for (BaseResultSign base: lastSignList){
                String[]  arr = base.getSignTime().split(" ");
                lateTime.append(arr[0] +" (星期"+ getWeekNumber(arr[0]) + ") " + arr[1] + " 签到").append(";");
            }
            abnormalAttendance.setLateTime(lateTime.toString().split(";"));
        }

        //查询早退记录
        baseResultSign.setSignStatus("3");
        List<BaseResultSign>leaveSignList = employeeSignSetDao.querySignListByStoreId(baseResultSign);
        StringBuffer earlyTime = new StringBuffer();
        if(lastSignList != null ){

            int  index = 0;
            for (BaseResultSign base: leaveSignList){
                String[]  arr = base.getSignTime().split(" ");
                earlyTime.append(arr[0] +" (星期"+ getWeekNumber(arr[0]) + ") " + arr[1] + " 签退").append(";");
            }
            abnormalAttendance.setEarlyTime(earlyTime.toString().split(";"));
        }
        abnormalAttendance.setLeavetimes(String.valueOf(leaveSignList.size()));

        int failurePunch = 0;
        List<BaseResultSign> empSignList = employeeSignSetDao.queryEmpolyeeSignListById(baseResultSign);
        StringBuffer notSign = new StringBuffer();
        for (int i = 1; i <= days; i++) {
            String dayNumber = "-" + i;
            if (i < 10){
               dayNumber = "-0" + i;
            }
            String dateTime = queryTime + dayNumber;
            int index = 0;
            for (BaseResultSign base : empSignList) {
                String signTime = base.getSignTime();
                String[] array = signTime.split("-");
                String date = array[0] + "-" + array[1] + "-" + array[2];
                if (dateTime.equals(date)) {
                    index++;
                }
            }
            if (index < 2) {
                baseResultSign.setQueryTime(dateTime);
                String empId = employeeSignSetDao.getEmpolyeeBaseBillById(baseResultSign);
                if (empId == null) {
                    failurePunch++;
                    if(index ==  1 ){
                        for (BaseResultSign base : empSignList) {
                            String signTime = base.getSignTime();
                            String[] array = signTime.split(" ");
                            if (dateTime.equals(array[0])) {
                                if( !base.getSignStatus().equals("2" )){
                                    notSign.append(dateTime +" (星期"+ getWeekNumber(dateTime) + ") " + " 未签到").append(";");
                                }else{
                                    notSign.append(dateTime +" (星期"+ getWeekNumber(dateTime) + ") "+ " 未签退").append(";");
                                }

                            }
                        }
                    }
                    if(index ==  0 ){

                        notSign.append(dateTime +" (星期"+ getWeekNumber(dateTime) + ") "  + " 未签到").append(";");
                        notSign.append(dateTime +" (星期"+ getWeekNumber(dateTime) + ") "  + " 未签退").append(";");
                    }
                }
            }
        }
        abnormalAttendance.setEmpId(responseEmployee.getId());
        abnormalAttendance.setDepId(responseEmployee.getDepId());
        abnormalAttendance.setEmployeeSerialId(responseEmployee.getEmployeeSerialId());
        abnormalAttendance.setEmployeeName(responseEmployee.getEmployeeName());
        abnormalAttendance.setDepName(responseEmployee.getDepName());
        abnormalAttendance.setLeavetimes(responseEmployee.getLeavetimes());
        abnormalAttendance.setLasttimes(responseEmployee.getLasttimes());
        abnormalAttendance.setStoreId(responseEmployee.getStoreId());
        abnormalAttendance.setFailurePunch(responseEmployee.getFailurePunch());
        abnormalAttendance.setPositionName(responseEmployee.getPositionName());
        abnormalAttendance.setNotSign(notSign.toString().split(";"));
        abnormalAttendance.setFailurePunch(String.valueOf(failurePunch));

        return  abnormalAttendance;
    }

    /**
     * 获取星期几
     * @param time
     * @return
     */
    public static String getWeekNumber (String time){
        String Week = null;
        try {

            int  date = BusiUtils.getWeek(time);
            switch (date) {
                case 1:
                    Week =  "天";
                    break;
                case 2:
                    Week =  "一";
                    break;
                case 3:
                    Week =  "二";
                    break;
                case 4:
                    Week =  "三";
                    break;
                case 5:
                    Week =  "四";
                    break;
                case 6:
                    Week =  "五";
                    break;
                case 7:
                    Week =  "六";
                    break;
                default:

                    break;
            }
        }catch (Exception e){
            logger.error("EmployeeSignSetServiceImpl-getWeekNumber:", e);
            throw new BusiException(CommonEnum.ReturnCode.SystemCode.BusinessCode.busi_err_nodata.getValue());
        }
        return Week;
    }

    /**
     * 员工补签到记录
     * @param emeployeeSign
     * @return
     */
    public void  repairSignEmployeeById(EmployeeSign emeployeeSign){
        emeployeeSign.setId(UUID.randomUUID().toString());
        employeeSignSetDao.addEmployeeSignTime(emeployeeSign);
    }
}
