package com.activiti.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.omg.IOP.ExceptionDetailMessage;

public class YesListener implements ExecutionListener, ExceptionDetailMessage {

	public void notify(DelegateExecution execution) throws Exception {
		System.out.println("审批通过");
	}

}
