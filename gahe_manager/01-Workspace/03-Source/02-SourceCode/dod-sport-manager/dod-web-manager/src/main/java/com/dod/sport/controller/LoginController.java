package com.dod.sport.controller;

import com.dod.sport.constant.SysConfigConstants;
import com.dod.sport.constant.WebConstants;
import com.dod.sport.dao.ISystemAuthDao;
import com.dod.sport.dao.IUserDao;
import com.dod.sport.domain.common.BusiException;
import com.dod.sport.domain.po.Base.ManagerUser;
import com.dod.sport.domain.po.System.PlatformMenus;
import com.dod.sport.domain.po.System.UserRole;
import com.dod.sport.service.IAuthorityService;
import com.dod.sport.util.CommonEnum;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by defi on 2017/7/21.
 */
@Controller
public class LoginController extends BaseController {

    @Autowired
    IUserDao userDao;

    @Autowired
    ISystemAuthDao systemAuthDao;

    @Autowired
    IAuthorityService authorityService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView loginView() {return new ModelAndView("index");}

    /**
     * 用户登录，返回用户菜单项
     * @param userId
     * @param password
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/userLogin", method = RequestMethod.POST)
    public HashMap<String,Object> userLogin(@RequestParam("userId") String userId,
                                            @RequestParam("password") String password){
        HashMap<String,Object> map = new HashMap<>();
        ManagerUser user = null;
        List<PlatformMenus> platformMenusList = null;
        //验证请求参数
        if(StringUtils.isBlank(userId) || StringUtils.isBlank(password)){
            throw new BusiException(CommonEnum.ReturnCode.ManagerCode.manager_err_idorpwdisblank.getValue());
        }else{
            //查询用户是否存在
            if ("admin".equals(userId)){
                user = userDao.getUserInfo(null, password);
            }else {
                user = userDao.getUserInfo(userId, password);
            }

            if (user == null){
                throw new BusiException(CommonEnum.ReturnCode.ManagerCode.manager_err_passwordnotmatch.getValue());
            }
            //账号不存在/密码错误或账户已删除
            if("2".equals(user.getStatus()) || "3".equals(user.getStatus())){
                throw new BusiException(CommonEnum.ReturnCode.ManagerCode.manager_err_employeenotexists.getValue());
            }else{
                //缓存用户菜单信息至session中
                platformMenusList = authorityService.getUserMenus(user.getRoleId());
                //缓存用户信息至session中
                request.getSession().setAttribute(SysConfigConstants.USER_INFO, user);
            }
        }
        map.put("managerUser",user);
        map.put("platformMenusList",platformMenusList);
        return map;
    }

    /**
     * 退出
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(){
        request.getSession().removeAttribute(SysConfigConstants.USER_INFO);
        return "";
    }
}
