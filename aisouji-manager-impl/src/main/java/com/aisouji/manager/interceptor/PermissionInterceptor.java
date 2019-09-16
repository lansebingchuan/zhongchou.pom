package com.aisouji.manager.interceptor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.aisouji.util.Constent;
@SuppressWarnings("unchecked")
public class PermissionInterceptor extends HandlerInterceptorAdapter{

	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		/*通过session加载 
		 * HttpSession session = request.getSession();
		Set<String> allPermission = (Set<String>) session.getAttribute(Content.PERMISSIONAllURL);
		if (allPermission == null) {//第一次登录，没有添加放行
			return true;
		}*/
		Object user = session.getAttribute(Constent.USER);
		if (user != null) {
			Set<String> allPermission = (Set<String>)request.getSession().getServletContext().getAttribute(Constent.PERMISSIONAllURL);//通过ServletContext启动的时候加载
			String servletPath = request.getServletPath();
			if (allPermission.contains(servletPath)) {//如果在地址范围内
				Set<String> userPermission =(Set<String>) session.getAttribute(Constent.USERPERSSIOURL);
				if (userPermission.contains(request.getServletPath())) {//如果是本用户的许可路径，那么通过
					return true;
				}else {
					response.sendRedirect(request.getContextPath()+"/main.htm");
					return false;
				}
			}else {
				return true;
			}
		}else {
			return true;
		}
		
	}
}
