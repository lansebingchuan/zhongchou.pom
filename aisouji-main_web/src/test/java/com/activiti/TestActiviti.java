package com.activiti;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestActiviti {//创建Activiti的表的结构
    
	private static ApplicationContext context =new  ClassPathXmlApplicationContext("spring/spring-*.xml");//加载spring和Activiti的配置文件
	private static RepositoryService repositoryService = (RepositoryService) context.getBean("repositoryService");//操作数据库的
	private static RuntimeService runtimeService = (RuntimeService) context.getBean("runtimeService");//启动流程的
	private static TaskService taskService = (TaskService) context.getBean("taskService");//启动一个任务的
	private static HistoryService historyService = (HistoryService) context.getBean("historyService");//获取历史的
	
	
	
	
	
	@Test//根据运行一个流程文件，开始->第一个运行节点，然后根据任务组，分配给指定的人   在进行了一个分组，之后，查询的对象已经改变，需要重新赋值
	public void testTaskGroup() {
		TaskQuery taskQuery = taskService.createTaskQuery();
		taskQuery = taskQuery.processDefinitionKey("myProcess2");//获取指定的流程的KEY
		long count1 = taskQuery.taskAssignee("zhangsan").count();//获取一个指定流程的数量，
		System.out.println("分配给一个zhangsan所有者，之后："+count1);
		List<Task> list = taskQuery.taskCandidateGroup("zuzhang").list();//获取一个组的名字
		long count = taskQuery.taskAssignee("zhangsan").count();//获取一个所有者
		System.out.println("分配给一个zhangsan所有者，之前："+count);
		for (Task task : list) {//获取组的所有的任务
			System.out.println("组的id："+task.getId());
			System.out.println("组的名字Name："+task.getName());
			System.out.println("组的(成员/持有者)Assignee："+task.getAssignee());
			//taskService.claim(task.getId(), "zhangsan");//把组的分配给一个所有者
		}
		taskQuery = taskService.createTaskQuery();//没有指定流程KEY，表示获取全部的流程
		long count2 = taskQuery.taskAssignee("zhangsan").count();//获取一个所有者，所有数量为2
		System.out.println("分配给一个zhangsan所有者，之后："+count2);
	}
	
	@Test//根据运行一个流程文件，开始->第一个运行节点，然后根据历史流的id获取历史实例对象
	public void testHistory() {
		HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery();
		HistoricProcessInstance singleResult = historicProcessInstanceQuery.processInstanceId("701").singleResult();
		System.out.println("HistoricProcessInstance="+singleResult);
		System.out.println("流的id"+singleResult.getProcessDefinitionId());
		
	}
	
	@Test//根据运行一个流程文件，开始->第一个运行节点，然后执行第一个任务
	public void testRunTask() {
		//获取key 为 myProcess 的数据库流程对象processDefinition ,获取最后的一个版本，的一个对象
		TaskQuery taskQuery = taskService.createTaskQuery();
	    List<Task> list = taskQuery.taskAssignee("zhangsan").list();//获取代理人,的所有的任务
	    for (Task task : list) {
	    	System.out.println("代理id："+task.getId());
	    	System.out.println("代理人："+task.getAssignee());
			System.out.println("代理任务："+task.getName());
		}
		long count1 = taskQuery.taskAssignee("zhangsan").count();//获取一个所有者
		System.out.println("分配给一个zhangsan所有者，之后："+count1);
	}
	
	
	@Test//测试运行一个流程文件，开始->第一个运行节点，带初始的变量的启动一个流程
	public void testTaskProcess_Variate() {
		//获取key 为 myProcess 的数据库流程对象processDefinition ,获取最后的一个版本，的一个对象
		
		ProcessDefinition pDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey("myProcess4").latestVersion().singleResult();
		System.out.println("运行流的id:="+pDefinition.getId());
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("tl", "zuzhang");//组长
		variables.put("pm", "jingli");//经理
		ProcessInstance startProcessInstanceById = runtimeService.startProcessInstanceById(pDefinition.getId(), variables);
		System.out.println("开始一个流程成功：startProcessInstanceById"+startProcessInstanceById);
	}
	
	//-----------------------------------------------------------完整的一个任务----一个排他网关的使用----------有你没我，有我没你-----------------------------------------------
	@Test//测试运行一个流程文件，开始->第一个运行节点，带初始的变量的启动一个流程 , 带变量的参数
	public void testRunService_Variate_myProcess4() {
		//获取key 为 myProcess 的数据库流程对象processDefinition ,获取最后的一个版本，的一个对象
		
		ProcessDefinition pDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey("myProcess4").latestVersion().singleResult();
		System.out.println("运行流的id:="+pDefinition.getId());
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("days", "5");//组长
		ProcessInstance startProcessInstanceById = runtimeService.startProcessInstanceById(pDefinition.getId(), variables);
		System.out.println("开始一个流程成功：startProcessInstanceById"+startProcessInstanceById);
	}
	
	@Test//根据运行一个流程文件，开始->第一个运行节点，然后执行第一个任务
	public void testRunTask_myProcess4() {
		//获取key 为 myProcess 的数据库流程对象processDefinition ,获取最后的一个版本，的一个对象
		TaskQuery taskQuery = taskService.createTaskQuery().processDefinitionKey("myProcess4");//根据流程id查询任务
	    List<Task> list = taskQuery.taskAssignee("lisi").list();//获取代理人,的所有的任务
	    for (Task task : list) {
	    	System.out.println("执行的id："+task.getExecutionId());
	    	System.out.println("组长处理人："+task.getAssignee());
	    	System.out.println("使用流程："+task.getTaskDefinitionKey());
	    	taskService.complete(task.getId());//任务服务执行完这个任务，接着执行下一个任务
		}
	}
	//-----------------------------------------------------------------------------------------------------------------------------------
	
	
	//-----------------------------------------------------------完整的一个任务----一个并行网关的使用----------两个完成才能向后执行-----------------------------------------------
		@Test//测试运行一个流程文件，开始->第一个运行节点
		public void testRunService_Variate_myProcess5() {
			//获取key 为 myProcess 的数据库流程对象processDefinition ,获取最后的一个版本，的一个对象
			//Map<String, Object> variables = new HashMap<String, Object>();//添加运行的参数
			ProcessInstance startProcessInstanceByKey = runtimeService.startProcessInstanceByKey("myProcess5");
			System.out.println("开始一个流程成功：startProcessInstanceById"+startProcessInstanceByKey);
		}
		
		@Test//根据运行一个流程文件，开始->第一个运行节点，然后执行第一个任务
		public void testRunTask_myProcess5() {
			//获取key 为 myProcess 的数据库流程对象processDefinition ,获取最后的一个版本，的一个对象
			TaskQuery taskQuery = taskService.createTaskQuery().processDefinitionKey("myProcess5");//根据流程id查询任务
		    List<Task> list = taskQuery.taskAssignee("lisi").list();//获取代理人,的所有的任务
		    for (Task task : list) {
		    	System.out.println("执行的id："+task.getExecutionId());
		    	System.out.println("组长处理人："+task.getAssignee());
		    	System.out.println("使用流程："+task.getTaskDefinitionKey());
		    	taskService.complete(task.getId());//任务服务执行完这个任务，接着执行下一个任务
			}
		}
		//-----------------------------------------------------------------------------------------------------------------------------------
	
		
		//-----------------------------------------------------------完整的一个任务----一个包含（会签）网关的使用---（排他网关+并行网关）---满足条件的走----两个（或者一个完成）完成才能向后执行-----------------------------------------------
		@Test//测试运行一个流程文件，开始->第一个运行节点
		public void testRunService_Variate_myProcess6() {
			//获取key 为 myProcess 的数据库流程对象processDefinition ,获取最后的一个版本，的一个对象
			Map<String, Object> variables = new HashMap<String, Object>();//添加运行的参数
			variables.put("toEmail",  "2827273055@qq.com");
			variables.put("fromEmail","1669638693@qq.com");
			variables.put("word", "你好--2827273055");
			ProcessInstance startProcessInstanceByKey = runtimeService.startProcessInstanceById("qq:1:3904", variables);
			System.out.println("开始一个流程成功：startProcessInstanceById"+startProcessInstanceByKey);
		}
		
		@Test//根据运行一个流程文件，开始->第一个运行节点，然后执行第一个任务
		public void testRunTask_myProcess6() {
			//获取key 为 myProcess 的数据库流程对象processDefinition ,获取最后的一个版本，的一个对象
			TaskQuery taskQuery = taskService.createTaskQuery().processDefinitionKey("myProcess6");//根据流程id查询任务
		    List<Task> list = taskQuery.taskAssignee("lisi").list();//获取代理人,的所有的任务
		    for (Task task : list) {
		    	System.out.println("执行的id："+task.getExecutionId());
		    	System.out.println("处理人："+task.getAssignee());
		    	System.out.println("使用流程："+task.getTaskDefinitionKey());
		    	taskService.complete(task.getId());//任务服务执行完这个任务，接着执行下一个任务
			}
		}
		//-----------------------------------------------------------------------------------------------------------------------------------
		
		
		//-----------------------------------------------------------完整的一个任务----邮件发送-----------------------------------------------
		@Test//测试运行一个流程文件，开始->第一个运行节点
		public void testRunService_Email_myProcess7() {
			//获取key 为 myProcess 的数据库流程对象processDefinition ,获取最后的一个版本，的一个对象
			ProcessInstance startProcessInstanceByKey = runtimeService.startProcessInstanceByKey("myProcess7");
			System.out.println("开始一个流程成功：startProcessInstanceById"+startProcessInstanceByKey);
		}

		//-----------------------------------------------------------------------------------------------------------------------------------
		
	@Test//测试运行一个流程文件，开始->第一个运行节点，不带变量
	public void testRunProcess() {
		//获取key 为 myProcess 的数据库流程对象processDefinition ,获取最后的一个版本，的一个对象
		
		ProcessDefinition pDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey("myProcess4").latestVersion().singleResult();
		ProcessInstance startProcessInstanceById = runtimeService.startProcessInstanceById(pDefinition.getId());//根据Id开启一个运行任务
		System.out.println("开始一个流程成功：startProcessInstanceById"+startProcessInstanceById);
	}
	
	@Test
	public void testQueryProcess() {//根据activiti的数据库文件，进行查询
		ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();//创建一个查询的实例
		List<ProcessDefinition> list = processDefinitionQuery.list();//返回所有的文件对象
		for (ProcessDefinition processDefinition : list) {
			System.out.println("------------获取全部的流程---------------");
			System.out.println("ID："+processDefinition.getId());
			System.out.println("KEY："+processDefinition.getKey());
			System.out.println("Name："+processDefinition.getName());
			System.out.println("Version："+processDefinition.getVersion());
		}
		
		ProcessDefinition singleResult = processDefinitionQuery.latestVersion().singleResult();
		System.out.println("------------获取版本最高的一个---------------");
		System.out.println("ID："+singleResult.getId());
		System.out.println("KEY："+singleResult.getKey());
		System.out.println("Name："+singleResult.getName());
		System.out.println("Version："+singleResult.getVersion());
		
		List<ProcessDefinition> listPage = processDefinitionQuery.processDefinitionKey("myProcess").listPage(0, 3);//其中框架加了max参数，就是对一个流程，只返回最大的
		for (ProcessDefinition processDefinition2 : listPage) {
			System.out.println("------------获取对应KEY版本--并且分页------使用offset属性---------");
			System.out.println("ID："+processDefinition2.getId());
			System.out.println("KEY："+processDefinition2.getKey());
			System.out.println("Name："+processDefinition2.getName());
			System.out.println("Version："+processDefinition2.getVersion());
		}
		
		ProcessDefinition singleResult2 = processDefinitionQuery.orderByProcessDefinitionVersion().desc().singleResult();
		System.out.println("------------降序，返回最高的一个---------------");
		System.out.println("ID："+singleResult.getId());
		System.out.println("KEY："+singleResult.getKey());
		System.out.println("Name："+singleResult.getName());
		System.out.println("Version："+singleResult.getVersion());
	}
	
	//根据activiti文件，把流程文件加入数据库
	@Test
	public void testCreateProcess() {
	       Deployment deploy = repositoryService.createDeployment().addClasspathResource("memberProject.bpmn").deploy();//根据文件保存到数据库
	       System.out.println("装载文件成功"+deploy);
	}
	//测试Activiti的初始化，并且创建数据库表
	@Test
	public void testInit() {
		ProcessEngine processEngine = context.getBean(ProcessEngine.class);
		System.out.println(processEngine);
	}
	
}
