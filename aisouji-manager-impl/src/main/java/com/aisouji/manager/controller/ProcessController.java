package com.aisouji.manager.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.aspectj.weaver.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.aisouji.util.BaseController;
import com.aisouji.util.Page;

@Controller
public class ProcessController extends BaseController{//流程任务的部署

	@Autowired
	RepositoryService repositoryService;

	@ResponseBody
	@RequestMapping("/initProcessData")//初始化流程定义，显示
	public Object initProcessData(@RequestParam(value="pagePoint" ,defaultValue="1" ,required=false) Integer pagePoint ,
			@RequestParam(value="pageSize" ,defaultValue="1" ,required=false) Integer pageSize) {
		start();
		Page page = new Page(pagePoint, pageSize);
		try {
			List<ProcessDefinition> listPage = repositoryService.createProcessDefinitionQuery().listPage(page.getStartIndex(), pageSize);
			Integer totalSize =(int) repositoryService.createProcessDefinitionQuery().count();
			page.setTotalSize(totalSize);
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			for (ProcessDefinition pd : listPage) {
				Map<String , Object> listProcessDefinition = new HashMap<String , Object>();
				listProcessDefinition.put("id", pd.getId());
				listProcessDefinition.put("name", pd.getName());
				listProcessDefinition.put("version", pd.getVersion());
				listProcessDefinition.put("taskname", pd.getKey());
				list.add(listProcessDefinition);
			}
			page.setLists(list);
			put("page" ,page);
			message("流程数据请求完成！");
			flage(true);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			message("流程数据请求失败！");
			flage(false);
		}
		return end();
	}

	@ResponseBody
	@RequestMapping("/upProcessFile")
	public Object upProcessFile(HttpServletRequest servletRequest) {
		start();
		MultipartHttpServletRequest rMultipartHttpServletRequest = (MultipartHttpServletRequest)servletRequest;
		MultipartFile file = rMultipartHttpServletRequest.getFile("file");
		try {
			Deployment deploy = repositoryService.createDeployment().addInputStream(file.getOriginalFilename(), file.getInputStream()).deploy();
			if (deploy != null) {
				flage(true);
				message("保存流程文件成功！");
			}else {
				flage(false);
				message("保存流程文件失败！");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flage(false);
			message("保存流程文件失败！");
		}
		return end();
	}


	@ResponseBody
	@RequestMapping("/delProcess")
	public Object delProcess(String processId) {
		start();
		try {
			ProcessDefinition pDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processId).singleResult();
			repositoryService.deleteDeployment(pDefinition.getDeploymentId());
			flage(true);
			message("删除流程文件成功！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flage(false);
			message("删除流程文件失败！");
		}
		return end();
	}
	@RequestMapping("/toShowProcess")
	public String toShowProcess() {		
		return "operation/process/showProcess";
	}
	@RequestMapping("/showProcess")
	public void showProcess(HttpServletResponse response ,String processId) throws IOException {		
		ProcessDefinition pDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processId).singleResult();
		ServletOutputStream outputStream = response.getOutputStream();
		InputStream inputStream = repositoryService.getResourceAsStream(pDefinition.getDeploymentId(), pDefinition.getDiagramResourceName());
		byte[] bs = new byte[1024];
		int i = -1;
		while (( i = inputStream.read(bs, 0, bs.length)) != -1) {
			outputStream.write(bs);
		}
	}
}
