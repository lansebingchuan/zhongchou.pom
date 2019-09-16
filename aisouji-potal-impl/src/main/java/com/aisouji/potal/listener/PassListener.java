package com.aisouji.potal.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

public class PassListener implements ExecutionListener{

	public void notify(DelegateExecution execution) throws Exception {
		System.out.println("审核通过");
		
	}

}
