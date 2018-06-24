package com.dod.sport.controller;

import com.dod.sport.domain.common.BusiException;
import com.dod.sport.domain.po.*;
import com.dod.sport.domain.po.Base.StoreWifi;
import com.dod.sport.service.IEmployeeSignSetService;
import com.dod.sport.util.CommonEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2017/8/31.
 */
@Controller
@RequestMapping("api/employeeSignSet/v1")
public class EmployeeSignSetController  extends BaseController {

    @Autowired
    IEmployeeSignSetService employeeSignSetService;

    /**
     * 获取员工签到记录
     * @param employeeId
     * @return
     */
    @RequestMapping(value = "/queryEmployeeSignListByEmployeeId" ,method = RequestMethod.POST)
    @ResponseBody
    public Map<String ,Object> queryEmployeeSignListByEmployeeId( @RequestParam(value = "employeeId") final String employeeId,
                                                                  @RequestParam(value = "storeId") final String storeId){
        Map<String ,Object> map = new HashMap<>();
        List<EmployeeSign> signList =  employeeSignSetService.queryEmployeeSignListByEmployeeId(employeeId,storeId);
        map.put("signList",signList);
        return map;
    }

    /**
     * 签到设置
     * @param radius 签到范围半径;单位米
     * @param storeId 分店id
     * @param morningShift 早班时间
     * @param nightShift 晚班时间
     * @param creatorId 创建人id
     * @param vacationDays 可休假天数
     * @param reissueTimes 可补卡次数
     *                     signAdd 签到地址 lng  经度 lat 纬度
     * @return
     */
    @RequestMapping(value = "/signSet" ,method = RequestMethod.POST)
    @ResponseBody
    public String  signSet( @RequestParam(value = "radius") final String radius,
                            @RequestParam(value = "storeId") final String storeId,
                            @RequestParam(value = "morningShift" ,required = false) final String morningShift,
                            @RequestParam(value = "nightShift",required = false) final String nightShift,
                            @RequestParam(value = "wifiStr",required = false) final String wifiStr,
                            @RequestParam(value = "creatorId") final String creatorId,
                            @RequestParam(value = "vacationDays") final String vacationDays,
                            @RequestParam(value = "reissueTimes") final String reissueTimes,
                            @RequestParam(value = "signAdd") final String signAdd,
                            @RequestParam(value = "lng") final String lng,
                            @RequestParam(value = "lat") final String lat){
        SignTime signTime = new SignTime();
        signTime.setStoreId(storeId);
        signTime.setCreator(creatorId);
        if(morningShift !=null&&morningShift !=""){ //早班
            String[]  signtime = morningShift.split(",");
            signTime.setId(UUID.randomUUID().toString());

            if (signtime.length==2){
                signTime.setFirstTime(signtime[0]);//设置签到时间
                signTime.setLastTime(signtime[1]);//设置签退时间
            }
            signTime.setSchedulingType(CommonEnum.EmployeeSignSet.schedulingType.morning_shift.getIntegerVal());
            //判断该门店是否已经设置早班时间,如果有设置的话删除
            SignTime querysignTime = employeeSignSetService.querySignTimeByType(storeId, CommonEnum.EmployeeSignSet.schedulingType.morning_shift.getIntegerVal());
            if (querysignTime !=null){
                employeeSignSetService.delSignTimeById(querysignTime.getId());
            }
            employeeSignSetService.signTimeSet(signTime);
        }
        if(nightShift !=null&&nightShift !=""){ //晚班
            String[]  signtime = nightShift.split(",");
            signTime.setId(UUID.randomUUID().toString());
            if (signtime.length==2){
                signTime.setFirstTime(signtime[0]);//设置签到时间
                signTime.setLastTime(signtime[1]);//设置签退时间
            }
            signTime.setSchedulingType(CommonEnum.EmployeeSignSet.schedulingType.night_shift.getIntegerVal());
            //判断该门店是否已经设置早班时间,如果有设置的话删除
            SignTime querysignTime = employeeSignSetService.querySignTimeByType(storeId, CommonEnum.EmployeeSignSet.schedulingType.night_shift.getIntegerVal());
            if (querysignTime !=null){
                employeeSignSetService.delSignTimeById(querysignTime.getId());
            }
            employeeSignSetService.signTimeSet(signTime);
        }
        if (wifiStr!=null &&wifiStr!=""){//设置WiFi
            employeeSignSetService.addStoreWifi(storeId, wifiStr);
        }
        SignScope querySignScope =  employeeSignSetService .querySignScopeByStoreId(storeId);
        if (querySignScope!=null){
            employeeSignSetService.delSignScope(querySignScope.getId());
        }
        SignScope signScope = new SignScope();
        signScope.setId(UUID.randomUUID().toString());
        signScope.setStoreId(storeId);
        signScope.setRadius(radius);
        signScope.setCreator(creatorId);
        signScope.setReissueTimes(reissueTimes);
        signScope.setVacationDays(vacationDays);
        signScope.setSignAdd(signAdd);
        signScope.setLat(lat);
        signScope.setLng(lng    );
        employeeSignSetService.addSignScope(signScope);
        return "";
    }

    /**
     * 获取该门店签到记录
     * @param queryTime
     * @param storeId
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/querySignListByStoreId" ,method = RequestMethod.POST)
    @ResponseBody
    public Map<String ,Object> querySignListByStoreId( @RequestParam(value = "queryTime") final String queryTime,
                                                       @RequestParam(value = "storeId") final String storeId,
                                                       @RequestParam(value = "emplpyeeName",required = false) final String emplpyeeName,
                                                       @RequestParam(value = "page") final Integer page,
                                                       @RequestParam(value = "pageSize",required = false) final Integer pageSize
    ){
        BaseResultSign baseResultSign = new BaseResultSign();
        baseResultSign.setStoreId(storeId);
        baseResultSign.setQueryTime(queryTime);
        baseResultSign.setEmployeeName(emplpyeeName);
        List<AttendanceRecord> signList = employeeSignSetService.querySignListByStoreId(baseResultSign,page,pageSize);
        Map<String ,Object> map = new HashMap<>();
        map.put("signList",signList);
        return map;
    }

    /**
     * 获取该员工签到记录详情
     * @param queryTime
     * @param storeId
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/getEmployeeSignById" ,method = RequestMethod.POST)
    @ResponseBody
    public Map<String ,Object> getEmployeeSignById(@RequestParam(value = "queryTime") final String queryTime,
                                                    @RequestParam(value = "storeId") final String storeId,
                                                    @RequestParam(value = "emplpyeeId") final String emplpyeeId){
        super.response.setContentType("utf-8");
        super.response.setCharacterEncoding("utf-8");
        BaseResultSign baseResultSign = new BaseResultSign();
        baseResultSign.setQueryTime(queryTime);
        baseResultSign.setStoreId(storeId);
        baseResultSign.setEmployeeId(emplpyeeId);
        Map<String ,Object> map = new HashMap<>();
        AbnormalAttendance sign = employeeSignSetService.getEmployeeSignById(baseResultSign);
        map.put("abnormalAttendance",sign);
        return map;
    }


    /**
     * 获取门店签到规则
     * @param storeId
     * @return
     */
    @RequestMapping(value = "/querySignTimeByStoreId" ,method = RequestMethod.POST)
    @ResponseBody
    public  Map<String ,Object> querySignTimeByStoreId( @RequestParam(value = "storeId") final String storeId){
        SignScope signScope = employeeSignSetService.querySignScopeByStoreId(storeId);
        StoreWifi StoreWifi = employeeSignSetService.queryAllWifiByStoreId(storeId);
        List<SignTime> signTimeList =  employeeSignSetService.querySignTimeByStoreId(storeId);
        Map<String ,Object> map = new HashMap<>();
        map.put("signScope",signScope);
        map.put("StoreWifi",StoreWifi);
        map.put("signTimeList",signTimeList);
        return map;
    }


    /**
     * 未签卡补签卡
     * @param storeId
     * @return
     */
    @RequestMapping(value = "/repairSignEmployeeById" ,method = RequestMethod.POST)
    @ResponseBody
    public  Map<String,String> repairSignEmployeeById( @RequestParam(value = "storeId") final String storeId,
                                                        @RequestParam(value = "signType") final String signType,
                                                        @RequestParam(value = "employeeId") final String employeeId,
                                                        @RequestParam(value = "depId") final String depId,
                                                        @RequestParam(value = "signTime") final String signTime,
                                                        @RequestParam(value = "signStatus") final String signStatus){

        Map<String,String> map  = new HashMap<String,String>();
        EmployeeSign emeployeeSign = new EmployeeSign();
        emeployeeSign.setStoreId(storeId);
        emeployeeSign.setEmployeeId(employeeId);
        emeployeeSign.setSignType(Integer.parseInt(signType));
        emeployeeSign.setDepId(depId);
        emeployeeSign.setSignStatus(Integer.parseInt(signStatus));
        emeployeeSign.setSignTime(signTime);
        employeeSignSetService.repairSignEmployeeById(emeployeeSign);

        return map;
    }

}
