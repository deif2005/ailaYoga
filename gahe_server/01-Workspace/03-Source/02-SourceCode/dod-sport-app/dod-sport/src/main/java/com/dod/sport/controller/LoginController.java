package com.dod.sport.controller;

import com.dod.sport.service.IEmployeeService;
import com.dod.sport.service.IUserService;
import com.dod.sport.util.CommonEnum;
import com.dod.sport.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by defi on 2017/7/21.
 */
@Controller
@RequestMapping(value = "api/userManager/v1")
public class LoginController {

    @Autowired
    IEmployeeService employeeService;

   /* @RequestMapping(value = "/index", method = RequestMethod.GET)*/
   @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginView() {return new ModelAndView("login");}

  /*  *//**
     * 获取用户信息
     * @param user
     * @return
     *//*
    @ResponseBody
    @RequestMapping(value = "/getUser", method = RequestMethod.POST)
    public User getUserInfoById(User user){
        Message message = new Message();
        User us = null;

        try {
            us = userService.getUserInfo(user);
        }catch (Exception e){
            new User();
            //logger.error("UserManagerController-getUser",e);
            //message.setResult(CommonEnum.SystemCode.ExceptionCode.exception.getValue());
            throw new RuntimeException(CommonEnum.SystemCode.ExceptionCode.exception.getValue(),e);

        }
        return us;
    }*/


}
