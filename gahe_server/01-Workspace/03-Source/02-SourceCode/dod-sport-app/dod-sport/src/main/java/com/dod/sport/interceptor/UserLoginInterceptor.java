package com.dod.sport.interceptor;

import com.dod.sport.constant.WebConstants;
import com.dod.sport.domain.common.BusiException;
import com.dod.sport.util.CommonEnum;
import com.dod.sport.util.RedisCommon;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


/**
 * 会话/权限拦截器
 * User: yuhao
 * Date: 2016-06-29
 */
public class UserLoginInterceptor implements HandlerInterceptor {

	private List<String> excludedUrls;

	public void setExcludedUrls(List<String> excludedUrls) {
		this.excludedUrls = excludedUrls;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//初始化变量
		String code,tips;
		//忽略不过滤的url
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		requestUri = requestUri.substring(contextPath.length());
		for (String url : excludedUrls) {
			if (requestUri.endsWith(url)) {
				return true;
			}
		}
		if (request.getParameter("token") == null || !RedisCommon.verifyToken(request.getParameter("token"))){
			throw new BusiException(CommonEnum.ReturnCode.SystemCode.sys_err_noauth.getValue());
		}
		else {
			return true;
		}
//		if (!RedisCommon.verifyToken(request.getParameter("token"))){
//			throw new BusiException(CommonEnum.ReturnCode.SystemCode.sys_err_noauth.getValue());
//		}
//		if (!RedisCommon.verifyToken(request.getParameter("token"))){
//			code = CommonEnum.ReturnCode.SystemCode.sys_err_noauth.getValue();
//			tips = CommonEnum.ReturnMessage.SystemMessage.sys_err_noauth.getValue();
//		}else {
//			return true;
//		}
		//判断Session是否失效，登陆时放入session的用户信息是否为 空
//		User user = (User)request.getSession().getAttribute("USER_INFO");
//		if (user == null) {
//			code = WebConstants.SESSION_INVALID;
//			tips = "您太久没有操作了，请重新登陆！";
//		}
//		//如果session没有失效，验证权限 2016-11-10 aisq暂时屏蔽
//		else if(true){
//			code = WebConstants.AUTH_INVALID;
//			tips = "您无权限访问此链接！";
//		}
//		// 会话有效且有权限访问返回true
//		else{
//			return true;
//		}

//		//异步请求处理，返回code，公共js弹出提示跳转到登陆页面
//		if(request.getHeader("x-requested-with") != null &&
//				request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){//ajax
//			response.setCharacterEncoding("UTF-8");
//			response.setContentType("application/json;charset=UTF-8");
//			PrintWriter out = response.getWriter();
//			out.print("{\"rtnCode\":" + code + "}");
//			out.flush();
//			out.close();
//		}
//		//同步请求直接重定向到登陆页面
//		else {
//			//弹出提示并跳转到登陆页面
//			StringBuffer builder = new StringBuffer();
//			builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");
//			builder.append("alert('" + tips + "');");
//			builder.append("window.top.location.href='"+contextPath+"';");
//			builder.append("</script>");
//			html(response,builder.toString());
//		}
//		return false;
	}

	/**
	 * 执行html js 代码
	 *
	 * @param response
	 * @param html
	 * @throws IOException
	 */
	protected void html(HttpServletResponse response, String html)
			throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(html);
		out.close();
	}

	@Override
	public void postHandle(HttpServletRequest request,
						   HttpServletResponse response, Object handler,
						   ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request,
								HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
}
