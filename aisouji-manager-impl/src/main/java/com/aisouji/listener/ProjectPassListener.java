package com.aisouji.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

public class ProjectPassListener implements ExecutionListener{
	private static final long serialVersionUID = 1L;

	public void notify(DelegateExecution execution) throws Exception {
		System.out.println("项目审核通过");
	}

}
