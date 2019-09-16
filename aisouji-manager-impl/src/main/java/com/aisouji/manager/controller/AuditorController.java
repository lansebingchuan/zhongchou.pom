package com.aisouji.manager.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.h2.util.New;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aisouji.bean.Member;
import com.aisouji.listener.LoseListener;
import com.aisouji.listener.PassListener;
import com.aisouji.listener.ProjectLoseListener;
import com.aisouji.listener.ProjectPassListener;
import com.aisouji.service.ProjectService;
import com.aisouji.service.TicketService;
import com.aisouji.util.BaseController;
import com.aisouji.util.Page;


@Controller
@RequestMapping("/auditor")
public class AuditorController extends BaseController{//流程任务审核
	
	
	
	@Autowired
	RuntimeService runtimeService;//运行时
	
	@Autowired
	TaskService taskService;//任务
	
	@Autowired
	RepositoryService repositoryService;
	
	@Autowired
	TicketService ticketService;//操作步骤
	
	@Autowired
	ProjectService projectService ;//操作步骤
	
	@RequestMapping("/toAuditMember")
	public String toAuditMember() {
		return "user/auditMember/auditMember";
	}
	
	@ResponseBody
	@RequestMapping("/initAuditMember")//取出一个用户需要审核的实名认证数据数据
	public Object initAuditMember(Integer memberid) {
		start();
		List<Map<String, Object>> auditMemberList = ticketService.getAuditMemberByMemberid(memberid);//取出需要审核的数据
		put("auditMemberList", auditMemberList);
		flage(true);
		message("请管理员审核！");
		return end();
	}
	
	

	
	@ResponseBody
	@RequestMapping("/initAutoCert")//取出所有需要审核的实名认证数据数据
	public Object initAutoCert(@RequestParam(value="pagePoint" ,defaultValue="1" ,required=false) Integer pagePoint ,
			@RequestParam(value="pageSize" ,defaultValue="5" ,required=false) Integer pageSize) {
		start();
		try {
			Page page = new Page(pagePoint, pageSize);
			List<Task> listPage = taskService.createTaskQuery().taskCandidateGroup("manager").orderByProcessInstanceId().desc()
					.listPage(page.getStartIndex(), pageSize);//根据组查询所有的任务，分页
			int count = (int) taskService.createTaskQuery().taskCandidateGroup("manager").count();//总的任务数
			List<Map<String, Object>> autoCertList = new ArrayList<Map<String, Object>>();
			for (Task task : listPage) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", task.getId());//任务id
				//查询流程实例
				ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
						.processDefinitionId(task.getProcessDefinitionId()).singleResult();
				map.put("definitionName", processDefinition.getName());//流程名称
				map.put("status", processDefinition.getVersion());//版本号
				map.put("taskname", task.getName());//任务名称
				Member member = ticketService.getMemberByInstanceId(task.getProcessInstanceId());//获取这个任务的实例id所对应的会员
				map.put("processInstanceId", task.getProcessInstanceId());//任务实例id
				map.put("member", member);//会员真实姓名
				autoCertList.add(map);
			}
			page.setLists(autoCertList);
			page.setTotalSize(count);
			put("page", page);
			flage(true);
			message("请审核！");
		} catch (Exception e) {
			flage(false);
			message("审核数据查询失败！");
			e.printStackTrace();
		}
		return end();
	}//
	
	
	@ResponseBody
	@RequestMapping("/initAutoProject")//查询需要审核的全部项目数据
	public Object initAutoProject(@RequestParam(value="pagePoint" ,defaultValue="1" ,required=false) Integer pagePoint ,
			@RequestParam(value="pageSize" ,defaultValue="5" ,required=false) Integer pageSize) {
		start();
		try {
			Page page = new Page(pagePoint, pageSize);
			List<Task> listPage = taskService.createTaskQuery().taskCandidateGroup("project").orderByProcessInstanceId().desc()
					.listPage(page.getStartIndex(), pageSize);//根据组查询所有的任务，分页
			int count = (int) taskService.createTaskQuery().taskCandidateGroup("project").count();//总的任务数
			List<Map<String, Object>> autoCertList = new ArrayList<Map<String, Object>>();
			for (Task task : listPage) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("taskId", task.getId());//任务id
				//查询项目展示map
				Map<String, Object> projectMap = ticketService.getProjectByInstanceId(task.getProcessInstanceId());//获取这个任务的实例id所对应的会员
				if (projectMap != null) {
					map.putAll(projectMap);
					autoCertList.add(map);
				}
			}
			page.setLists(autoCertList);
			page.setTotalSize(count);
			put("page", page);
			flage(true);
			message("请进行项目审核！");
		} catch (Exception e) {
			flage(false);
			message("审核数据查询失败！");
			e.printStackTrace();
		}
		return end();
	}
	
	@ResponseBody
	@RequestMapping("/successAuto")//"memberid": memberid , "processInstanceId":processInstanceId
	public Object successAuto(Integer memberid , String processInstanceId ,String status) {//2通过 ， 3不通过审核失败
		start();
			try {
				Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("passListener", new PassListener());
				map.put("loseListener", new LoseListener());
				Integer i = ticketService.updaeMemberStatusByMemberId(memberid, status);
				if (status.equals("2")) {//通过
					map.put("flag", "true");
				} else {//不通过
					map.put("flag", "false");
				}
				taskService.complete(task.getId(), map);
				flage(true);
				message("审核成功！");
			} catch (Exception e) {
				e.printStackTrace();
				flage(false);
				message("系统错误！");
			}
		return end();
	}
	
	@ResponseBody
	@RequestMapping("/auditProject")//{"status":status , "memberid":memberid ,"taskId":taskid} 
	public Object auditProject(Integer projectid,Integer memberid , String taskid ,String status) {//status -1通过 ， 0不通过审核失败
		start();
			try {
				Map<String, Object> variables = new HashMap<String, Object>();
				variables.put("flage", "true");
				variables.put("projectPass", new ProjectPassListener());
				variables.put("projectLose", new ProjectLoseListener());//完成任务监听器
				Integer i = projectService.updaeProjectStatusByMemberIdAndProjectId(projectid ,memberid, status);
				if (i >= 1) {//通过
					taskService.complete(taskid, variables );//提交任务
					flage(true);
					message("审核成功！");
				} else {//不通过
					flage(false);
					message("审核项目失败！");
				}
			} catch (Exception e) {
				e.printStackTrace();
				flage(false);
				message("系统错误！");
			}
		return end();
	}
}
