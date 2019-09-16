package com.aisouji.listener;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.aisouji.bean.Permission;
import com.aisouji.manager.service.PermissionService;
import com.aisouji.util.Constent;

public class StartSystemListener implements ServletContextListener{
	
	ApplicationContext aContext ;
	
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		ServletContext servletContext = sce.getServletContext();
		String contextPath = servletContext.getContextPath();//获取相对路径
		servletContext.setAttribute("APP_PATH", contextPath);//设置地址，可用于在JSP中使用该地址
		System.out.println("APP_PATH"+contextPath);//加载配置路径
		
		//2.启动的时候-.加载所有的许可权限
		aContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		PermissionService permissionService = aContext.getBean(PermissionService.class);
		List<Permission> allPermissionList = permissionService.getAllPermission();
		Set<String> allPermissionSet = new HashSet<String>();
		for (Permission permission : allPermissionList) {
			allPermissionSet.add(permission.getUrl());
		}
		servletContext.setAttribute(Constent.PERMISSIONAllURL, allPermissionSet);
	}
	
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
	
	}
}
