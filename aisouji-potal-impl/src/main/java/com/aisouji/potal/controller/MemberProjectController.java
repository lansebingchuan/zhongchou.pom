package com.aisouji.potal.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.codehaus.jackson.map.ObjectMapper;
import org.h2.util.New;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aisouji.bean.Member;
import com.aisouji.bean.MemberProjectTicket;
import com.aisouji.bean.Memberprojectfollow;
import com.aisouji.bean.Project;
import com.aisouji.bean.Projecttag;
import com.aisouji.bean.Projecttype;
import com.aisouji.bean.Return;
import com.aisouji.bean.Tag;
import com.aisouji.bean.Type;
import com.aisouji.potal.service.MemberProjectTicketService;
import com.aisouji.potal.service.MemberService;
import com.aisouji.potal.serviceImpl.MemberServiceImpl;
import com.aisouji.service.MemberprojectfollowService;
import com.aisouji.service.ProjectService;
import com.aisouji.service.ProjecttagService;
import com.aisouji.service.ProjecttypeService;
import com.aisouji.service.ReturnService;
import com.aisouji.util.BaseController;
import com.aisouji.util.Constent;
import com.aisouji.util.HttpClient;

@Controller
@RequestMapping("/memberProject")
public class MemberProjectController extends BaseController{
	
	@Autowired
	MemberService memberService;//会员
	
	@Autowired
	ReturnService returnService;
	
	@Autowired
	ProjectService projectService;//项目
	
	@Autowired
	ProjecttypeService projecttypeService;//项目类型
	
	@Autowired
	ProjecttagService projecttagService;//项目标签
	
	@Autowired
	MemberprojectfollowService memberprojectfollowService;//项目与发布者关联
	
	@Autowired
	MemberProjectTicketService memberProjectTicketService;
	
	@Autowired
	RuntimeService runtimeService;//运行时
	
	@Autowired
	RepositoryService repositoryService;//运行时
	
	@RequestMapping("/toStart")
	public String start1() {
		return "member/memberProjectStart/start";
	}
	@RequestMapping("/toStartStep1")
	public String toStartstep1() {
		return "member/memberProjectStart/start-step-1";
	}
	@RequestMapping("/toStartStep2")
	public String toStartstep2() {
		return "member/memberProjectStart/start-step-2";
	}
	@RequestMapping("/toStartStep3")
	public String toStartstep3(HttpSession session) {
		Member member = (Member)session.getAttribute(Constent.MEMBER);
		memberProjectTicketService.updateCurrenUrlByMemberId("/memberProject/toStartStep3.htm" , member.getId());
		return "member/memberProjectStart/start-step-3";
	}
	@RequestMapping("/toStartStep4")
	public String toStartstep4() {
		return "member/memberProjectStart/start-step-4";
	}
	@ResponseBody
	@RequestMapping("/initStart")
	public Object initStart(HttpSession session) {
		start();
		Member member = (Member)session.getAttribute(Constent.MEMBER);
		MemberProjectTicket memberProjectTicket= memberProjectTicketService.getProjectTickeByMemberId(member.getId());
		if (memberProjectTicket != null) {
			put("url", memberProjectTicket.getCurrenturl()+"?projectId="+memberProjectTicket.getProjectid());
		}else {
			put("url", "/memberProject/toStart");
		}
		flage(true);
		return end();
	}
	
	@ResponseBody
	@RequestMapping("/initProjectType")
	public Object initProjectType_Tag() {//初始化所有的项目类型和标签
		start();
		List<Type> pList = memberService.getAllType();//获取所有的项目类型
		put("projectTypeList", pList);
		List<Tag> rootTag = new ArrayList<Tag>();//Tag根
		Map<Integer, Tag> map = new HashMap<Integer, Tag>();
		List<Tag> tList = memberService.getAllTag();
		for (Tag tag : tList) {
			map.put(tag.getId(), tag);
		}
		for (Tag tag : tList) {
			if (tag.getPid() == null) {//如果为根
				tag.setOpen(true);
				rootTag.add(tag);
			}else {
				Tag parent = map.get(tag.getPid());//父亲
				parent.getChildren().add(tag);
			}
		}
		flage(true);
		put("projectTagList", rootTag);
		return end();
	}
	
	@ResponseBody
	@RequestMapping("/addMemberProject")//会员发起项目//"projectTypeId":projectTypeId,"projectName":projectName,"money":money,"#day":day, "remark":remark
	public Object addMemberProject(HttpSession session,Integer projectTypeId , String projectName , Long money ,Integer day ,String remark ,@RequestParam(value = "tagArray")Integer[] tagArray) {
		start();
		try {
			for (Integer integer : tagArray) {
				
			}
			Member member = (Member)session.getAttribute(Constent.MEMBER);
			Project project = new Project();
			project.setName(projectName);
			project.setMoney(money);
			project.setDay(day);
			project.setRemark(remark);
			project.setStatus("0");
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
			String deploydate = simpleDateFormat.format(new Date());
			project.setDeploydate(deploydate);
			//开启一个流程，项目处理
			ProcessInstance pInstance = runtimeService.startProcessInstanceById("memberProject:1:204");
			Integer projecti = projectService.insertMemberProject(project);//保存项目 
			Projecttype projecttype = new Projecttype();
			projecttype.setProjectid(project.getId());
			projecttype.setTypeid(projectTypeId);
			projecttypeService.insertSelective(projecttype);//保存项目与类型
			Projecttag projecttag = null;
			for (Integer tagid : tagArray) {
				projecttag = new Projecttag();//插入对应的标签
				projecttag.setProjectid(project.getId());
				projecttag.setTagid(tagid);
				projecttagService.insertSelective(projecttag);//保存项目与标签
			}
			Memberprojectfollow memberprojectfollow = new Memberprojectfollow();
			memberprojectfollow.setMemberid(member.getId());
			memberprojectfollow.setProjectid(project.getId());
			Integer memberprojectfollowid = memberprojectfollowService.insertMemberprojectfollow(memberprojectfollow);//保存项目与发布者,返回关联id
			projectService.updateFollowById(memberprojectfollowid ,project.getId());//更新发布者与项目 
			MemberProjectTicket memberProjectTicket = new MemberProjectTicket();
			memberProjectTicket.setMemberid(member.getId());
			memberProjectTicket.setStatus("0");
			memberProjectTicket.setCurrenturl("/memberProject/toStartStep2.htm");
			memberProjectTicket.setProinstid(pInstance.getId());
			memberProjectTicket.setProjectid(project.getId());
			memberProjectTicketService.insertSelective(memberProjectTicket);
			put("url", "/memberProject/toStartStep2.htm?projectId="+memberProjectTicket.getProjectid());
			flage(true);
			message("操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			flage(false);
		}
		return end();
	}
	
	@ResponseBody
	@RequestMapping("/addProjectReturn")
	public Object addProjectReturn(Return return1) {
		start();
		Integer i = returnService.insertSelective(return1);
		if (i > 0) {
			flage(true);
			Integer id = return1.getId();
			put("returnid", id);
		}else {
			flage(false);
		}
		return end();
	}
	
	@ResponseBody
	@RequestMapping("/validatecardNo")
	public Object validatecardNo(String cardNo ,HttpSession session){//验证银行卡号-银行卡号
		start();
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("cardNo", cardNo);
			map.put("cardBinCheck", "true");
			HttpClient httpClient = new HttpClient("https://ccdcapi.alipay.com/validateAndCacheCardInfo.json", 3000,
					3000);
			httpClient.send(map, "UTF-8");
			String result = httpClient.getResult();//接口查询返回值
			ObjectMapper objectMapper = new ObjectMapper();
			Map bankMap = objectMapper.readValue(result, map.getClass());
			String bank = (String) bankMap.get("bank");
			if (bank != null) {
				flage(true);
				put("bankMap", bankMap);
				Member member = (Member)session.getAttribute(Constent.MEMBER);
				memberProjectTicketService.updateCurrenUrlByMemberId("/memberProject/toStartStep4.htm" , member.getId());
			} else {
				flage(false);
			} 
		} catch (Exception e) {
			e.printStackTrace();
			flage(false);
		}
		return end();
	}
}
