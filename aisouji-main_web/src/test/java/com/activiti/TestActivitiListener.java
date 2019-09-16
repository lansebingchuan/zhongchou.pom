package com.activiti;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.h2.util.New;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.activiti.listener.NoListener;
import com.activiti.listener.YesListener;

public class TestActivitiListener {
	 
		private static ApplicationContext context =new  ClassPathXmlApplicationContext("spring/spring-*.xml");//加载spring和Activiti的配置文件
		private static RepositoryService repositoryService = (RepositoryService) context.getBean("repositoryService");//操作数据库的
		private static RuntimeService runtimeService = (RuntimeService) context.getBean("runtimeService");//启动流程的
		private static TaskService taskService = (TaskService) context.getBean("taskService");//启动一个任务的
		private static HistoryService historyService = (HistoryService) context.getBean("historyService");//获取历史的
		
		@Test
		public void testListener() {
			ProcessDefinition pDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey("myProcess8").latestVersion().singleResult();
			TaskQuery taskQuery = taskService.createTaskQuery().processDefinitionId(pDefinition.getId());
			List<Task> list = taskQuery.taskAssignee("zhangsan").list();
			for (Task task : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("nolistener", new NoListener());
				map.put("yeslistener", new YesListener());
				map.put("flage", "true");
				taskService.complete(task.getId() , map);
			}
		}
		
		
		
		@Test//测试运行一个流程文件，开始->第一个运行节点
		public void testRunService_Email_myProcess7() {
			//获取key 为 myProcess 的数据库流程对象processDefinition ,获取最后的一个版本，的一个对象
			ProcessInstance startProcessInstanceByKey = runtimeService.startProcessInstanceByKey("myProcess8");
			System.out.println("开始一个流程成功：startProcessInstanceById"+startProcessInstanceByKey);
		}
		
		//根据activiti文件，把流程文件加入数据库
		@Test
		public void testCreateProcess() {
		       Deployment deploy = repositoryService.createDeployment().addClasspathResource("MyProcess8.bpmn").deploy();//根据文件保存到数据库
		       System.out.println("装载文件成功"+deploy);
		}
}
