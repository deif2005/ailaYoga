package com.dod.sport.common;

import com.dod.sport.domain.common.BusiException;
import com.dod.sport.util.CommonEnum;
import com.dod.sport.util.Message;
import com.dod.sport.util.ReturnResult;
import net.sf.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class CustomMappingExceptionResolver extends SimpleMappingExceptionResolver {
    org.slf4j.Logger logger = LoggerFactory.getLogger(CustomMappingExceptionResolver.class);

//    @Override
//    protected ModelAndView doResolveException(HttpServletRequest request,HttpServletResponse response, Object handler,
//                                              Exception exception) {
//
//        HandlerMethod mathod = (HandlerMethod) handler;
//        String strCase = "";
//        ModelAndView mv = new ModelAndView();
//        Message message = new Message();
//        ReturnResult returnResult = new ReturnResult();
//        ResponseBody body = mathod.getMethodAnnotation(ResponseBody.class);
//        // 判断是否用了@responsebody
//        if (body == null) {
//            if (exception instanceof BusiException) {
//
//            }
////            Map<String, Object> map = new HashMap<String, Object>();
////            map.put("success", false);
////            if (exception instanceof BusiException) {
////                map.put("errorMsg", exception.getMessage());
////            } else {
////                map.put("errorMsg", "系统异常！");
////            }
//            //这里需要手动将异常打印出来，由于没有配置log，实际生产环境应该打印到log里面
////            exception.printStackTrace();
//            //对于非ajax请求，我们都统一跳转到error.jsp页面
//            //不做页面跳转 defi 2017.08.18
//            //return new ModelAndView("/error", map);
//        } else {
//            // 如果是ajax请求，JSON格式返回
//            try {
//                /*  使用response返回    */
//                response.setStatus(HttpStatus.OK.value()); //设置状态码
//                response.setContentType(MediaType.APPLICATION_JSON_VALUE); //设置ContentType
//                response.setCharacterEncoding("UTF-8"); //避免乱码
//                response.setHeader("Cache-Control", "no-cache, must-revalidate");
//                response.setContentType("application/json;charset=UTF-8");
//                PrintWriter writer = response.getWriter();
////                Map<String, Object> map = new HashMap<String, Object>();
//                //如果是自定义异常则做业务处理
//                if (exception instanceof BusiException) {
//                    strCase = ((BusiException) exception).getRtnCode();
//                }else {
//                    logger.error("系统异常",exception);
//                    strCase = "4003";
//                }
//                getCodeReturnResult(strCase);
//                //捕捉未定义异常
////                String strCase = exception.getMessage();
////                if (strCase == null){
////                    logger.error("未知异常",exception);
////                    strCase = "4003";
////                }
//                //捕捉已定义异常
//                getCodeReturnResult(strCase);
//                message.setResult(returnResult);
//                // 为安全起见，只有业务异常我们对前端可见，否则统一归为系统异常
////                if (exception instanceof BusiException) {
////                    map.put("rtnCode",((BusiException) exception).getRtnCode());
////                    map.put("msg",((BusiException) exception).getMsg());
////                } else {
////                    map.put("errorMsg", "系统异常！");
////                }
//
//                logger.error(exception.getMessage(),exception);
////                writer.write(JSONUtils.toJSONString(message));
//                writer.write(JSONObject.fromObject(message).toString());
//                writer.flush();
//                writer.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return mv;
//    }

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request,HttpServletResponse response, Object handler,
                                              Exception exception) {

        String strCase = "";
        ModelAndView mv = new ModelAndView();
        Map<String,Object> map = new HashMap<>();
        ReturnResult returnResult = new ReturnResult();
        // 如果是ajax请求，JSON格式返回
        try {
            /*  使用response返回    */
            response.setStatus(HttpStatus.OK.value()); //设置状态码
            response.setContentType(MediaType.APPLICATION_JSON_VALUE); //设置ContentType
            response.setCharacterEncoding("UTF-8"); //避免乱码
            response.setHeader("Cache-Control", "no-cache, must-revalidate");
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            //如果是自定义异常则做业务处理
            if (exception instanceof BusiException) {
                strCase = ((BusiException) exception).getRtnCode();
            }else {
                logger.error("系统异常",exception);
                strCase = CommonEnum.ReturnCode.SystemCode.sys_err_exception.getValue();
            }
            //捕捉已定义异常
            returnResult = getCodeReturnResult(strCase);
            map.put("result",returnResult);
//            message.setResult(returnResult);
//            logger.error(exception.getMessage(),exception);
//            writer.write(JSONObject.fromObject(message).toString());
            writer.write(JSONObject.fromObject(map).toString());

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mv;
    }

    /**
     * 根据code返回result
     * @param code
     * @return
     */
    private ReturnResult getCodeReturnResult(String code){
        ReturnResult returnResult = new ReturnResult();
        returnResult.setCode(code);
        //捕捉已定义异常
        switch (code){
            case "4001":
                returnResult.setMsg(CommonEnum.ReturnMessage.SystemMessage.sys_err_noauth.getValue());
                break;
            case "4002":
                returnResult.setMsg(CommonEnum.ReturnMessage.SystemMessage.sys_err_paramerror.getValue());
                break;
            case "4003":
                returnResult.setMsg(CommonEnum.ReturnMessage.SystemMessage.sys_err_exception.getValue());
                break;
            case "5001":
                returnResult.setMsg(CommonEnum.ReturnMessage.BusiMessage.busi_err_identifyingcode.getValue());
                break;
            case "5002":
                returnResult.setMsg(CommonEnum.ReturnMessage.BusiMessage.busi_err_nodata.getValue());
                break;
            case "5003":
                returnResult.setMsg(CommonEnum.ReturnMessage.BusiMessage.busi_err_passwordnotmatch.getValue());
                break;
            case "5004":
                returnResult.setMsg(CommonEnum.ReturnMessage.BusiMessage.busi_err_passworderror.getValue());
                break;
            case "5005":
                returnResult.setMsg(CommonEnum.ReturnMessage.BusiMessage.busi_err_employeeexists.getValue());
                break;
            case "5006":
                returnResult.setMsg(CommonEnum.ReturnMessage.BusiMessage.busi_err_phonenumerror.getValue());
                break;
            case "5007":
                returnResult.setMsg(CommonEnum.ReturnMessage.BusiMessage.busi_err_businessiderror.getValue());
                break;
            case "5008":
                returnResult.setMsg(CommonEnum.ReturnMessage.BusiMessage.busi_err_employeenotexists.getValue());
            case "5009":
                returnResult.setMsg(CommonEnum.ReturnMessage.BusiMessage.busi_err_registered.getValue());
                break;
            case "5010":
                returnResult.setMsg(CommonEnum.ReturnMessage.BusiMessage.busi_err_getdataerror.getValue());
                break;
            case "5011":
                returnResult.setMsg(CommonEnum.ReturnMessage.BusiMessage.busi_err_nouploadfile.getValue());
                break;
            case "5012":
                returnResult.setMsg(CommonEnum.ReturnMessage.BusiMessage.busi_err_uploadfileerror.getValue());
                break;
            case "5013":
                returnResult.setMsg(CommonEnum.ReturnMessage.BusiMessage.busi_err_notinsignscope.getValue());
                break;
            case "5014":
                returnResult.setMsg(CommonEnum.ReturnMessage.BusiMessage.busi_err_notimetosign.getValue());
                break;
            case "5015":
                returnResult.setMsg(CommonEnum.ReturnMessage.BusiMessage.busi_err_classroombusy.getValue());
                break;
            case "5016":
                returnResult.setMsg(CommonEnum.ReturnMessage.BusiMessage.busi_err_employeebusy.getValue());
                break;
            case "5017":
                returnResult.setMsg(CommonEnum.ReturnMessage.BusiMessage.busi_err_noremainorder.getValue());
                break;
            case "5018":
                returnResult.setMsg(CommonEnum.ReturnMessage.BusiMessage.busi_err_cannotorder.getValue());
                break;
            case "5019":
                returnResult.setMsg(CommonEnum.ReturnMessage.BusiMessage.busi_err_cardisover.getValue());
                break;
            case "5020":
                returnResult.setMsg(CommonEnum.ReturnMessage.BusiMessage.busi_err_cardisnotvalid.getValue());
                break;
            case "5021":
                returnResult.setMsg(CommonEnum.ReturnMessage.BusiMessage.busi_err_courseexistsorder.getValue());
                break;
            case "5022":
                returnResult.setMsg(CommonEnum.ReturnMessage.BusiMessage.busi_err_membercardnotexists.getValue());
                break;
            case "5023":
                returnResult.setMsg(CommonEnum.ReturnMessage.BusiMessage.busi_err_datefomaterror.getValue());
                break;
            case "5024":
                returnResult.setMsg(CommonEnum.ReturnMessage.BusiMessage.busi_err_memberexists.getValue());
                break;
            case "5025":
                returnResult.setMsg(CommonEnum.ReturnMessage.BusiMessage.busi_err_rechargegradexists.getValue());
                break;
            case "5026":
                returnResult.setMsg(CommonEnum.ReturnMessage.BusiMessage.busi_err_storewifinotexists.getValue());
                break;
            case "5027":
                returnResult.setMsg(CommonEnum.ReturnMessage.BusiMessage.busi_err_notmanagerauth.getValue());
                break;
            case "5028":
                returnResult.setMsg(CommonEnum.ReturnMessage.BusiMessage.busi_err_teachernotmatch.getValue());
                break;
            case "5029":
                returnResult.setMsg(CommonEnum.ReturnMessage.BusiMessage.busi_err_signcompleted.getValue());
                break;
            case "5030":
                returnResult.setMsg(CommonEnum.ReturnMessage.BusiMessage.busi_err_existsprivatecourseplan.getValue());
                break;
            case "5031":
                returnResult.setMsg(CommonEnum.ReturnMessage.BusiMessage.busi_err_coursecanceled.getValue());
                break;
            case "5032":
                returnResult.setMsg(CommonEnum.ReturnMessage.BusiMessage.busi_err_coursenotenable.getValue());
                break;
            case "5033":
                returnResult.setMsg(CommonEnum.ReturnMessage.BusiMessage.busi_err_cardtimesisinvalid.getValue());
                break;
            case "5034":
                returnResult.setMsg(CommonEnum.ReturnMessage.BusiMessage.busi_err_cardstatusisstop.getValue());
                break;
            case "5035":
                returnResult.setMsg(CommonEnum.ReturnMessage.BusiMessage.busi_err_cannotorderexperiencecourse.getValue());
                break;
            default:
                returnResult.setMsg(CommonEnum.ReturnMessage.SystemMessage.sys_err_exception.getValue());
                break;
        }
        return returnResult;
    }
}