package com.aisouji.manager.interceptor;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.aisouji.util.Constent;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	private  static Set<String> interceptor = new HashSet<String>();//设置白名单
	static {
		interceptor.add("/indexmain.htm");
		interceptor.add("/login.htm");
		interceptor.add("/reg.htm");
		interceptor.add("/logout.do");
		interceptor.add("/dologin.do");
		interceptor.add("/index.htm");//根路径放行
		interceptor.add("/project/getTypeProjects.do");//首页加载数据
		interceptor.add("/isUser_Member_flage.do");//判断首页是否登录//  
		interceptor.add("/pay/notify_url.do");//支付宝异步通知通过
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String servletPath = request.getServletPath();
		if (interceptor.contains(servletPath)) {
			return true;
		}
		HttpSession session = request.getSession();
		Object user = session.getAttribute(Constent.USER);
		Object member = session.getAttribute(Constent.MEMBER);
		if (user != null | member != null) {
			return true;
		}else {
			response.sendRedirect(request.getContextPath()+"/login.htm");
			return false;
		}

	}
}
