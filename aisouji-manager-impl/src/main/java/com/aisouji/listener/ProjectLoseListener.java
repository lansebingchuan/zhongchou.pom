package com.aisouji.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.context.ApplicationContext;

import com.aisouji.spring.ApplicationContextUtil;

public class ProjectLoseListener implements ExecutionListener{
	private static final long serialVersionUID = 1L;

	public void notify(DelegateExecution execution) throws Exception {
		System.out.println("项目审核失败！");
		ApplicationContext applicationContext = ApplicationContextUtil.applicationContext;
	}

}
