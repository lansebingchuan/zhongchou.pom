package com.activiti;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aisouji.listener.LoseListener;
import com.aisouji.listener.PassListener;

public class TestAuditor {

	private static ApplicationContext context =new  ClassPathXmlApplicationContext("spring/spring-*.xml");//加载spring和Activiti的配置文件
	private static RepositoryService repositoryService = (RepositoryService) context.getBean("repositoryService");//操作数据库的
	private static RuntimeService runtimeService = (RuntimeService) context.getBean("runtimeService");//启动流程的
	private static TaskService taskService = (TaskService) context.getBean("taskService");//启动一个任务的
	private static HistoryService historyService = (HistoryService) context.getBean("historyService");//获取历史的
	
	@Test
	public void name() {
		Task task = taskService.createTaskQuery().processInstanceId("110").singleResult();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("passListener", new PassListener());
		map.put("loseListener", new LoseListener());
		taskService.complete(task.getId(), map);
	}
}
