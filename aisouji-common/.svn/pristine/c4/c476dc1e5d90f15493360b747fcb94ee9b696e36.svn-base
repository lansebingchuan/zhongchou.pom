package com.aisouji.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component//容器启动的时候，调用接口设置applicationContext域
public class ApplicationContextUtil implements ApplicationContextAware{
	
	public static ApplicationContext applicationContext;
	//启动的时候，注入applicationContext，然后可以其他地方获取
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ApplicationContextUtil.applicationContext = applicationContext;		
	}

}
