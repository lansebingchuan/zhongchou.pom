package com.aisouji.potal.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.aisouji.bean.Cert;
import com.aisouji.bean.Member;
import com.aisouji.bean.Membercert;
import com.aisouji.bean.Ticket;
import com.aisouji.potal.service.MemberService;
import com.aisouji.potal.service.MembercertService;
import com.aisouji.redis.utils.PasswordUtil;
import com.aisouji.redis.utils.RedisUtils;
import com.aisouji.service.CertService;
import com.aisouji.service.TicketService;
import com.aisouji.util.BaseController;
import com.aisouji.util.Constent;


@Controller
@RequestMapping("/auto")
public class AutoController extends BaseController{
	
	@Autowired
	RuntimeService runtimeService;//运行时
	
	@Autowired
	MemberService memberService;//会员
	
	@Autowired
	CertService certService;//资质
	
	@Autowired
	MembercertService membercertService;//会员-资质
	
	@Autowired
	TaskService taskService;//任务
	
	@Autowired
	TicketService ticketService;//操作步骤
	
	@Autowired
	JavaMailSenderImpl javaMailSenderImpl;//发送邮件
	
	@RequestMapping("/toapply")
	public String toapply() {//取申请页面0
		return "member/auto/apply";
	}
	@RequestMapping("/toapply1")
	public String toapply1() {//取申请页面1
		return "member/auto/apply-1";
	}
	
	@RequestMapping("/toapply2")
	public String toapply2() {//取申请页面2
		return "member/auto/apply-2";
	}
	
	@RequestMapping("/toapply3")
	public String toapply3() {//取申请页面3
		return "member/auto/apply-3";
	}
	
	@ResponseBody
	@RequestMapping("/apply")
	public Object apply(HttpSession session , String accttype) {//保存申请需要的数据
		session.setAttribute("accttype", accttype);
		Member member = (Member)session.getAttribute(Constent.MEMBER);
		ticketService.updateTicketOnPstep(member.getId() ,"/auto/toapply.htm");
		return true;
	}
	
	
	@ResponseBody
	@RequestMapping("/accounttypecertByMember")//加载用户对于的账户类型，需要申请的
	public Object accounttypecertByMember(HttpSession session) {//保存申请需要的数据
		start();
		try {
			Member member = (Member)session.getAttribute(Constent.MEMBER);
			List<Integer> certIds = memberService.getAccounttypeList(member.getAccttype());
			List<Cert> certLis = certService.getInitCertList();
			List<Cert> memberCertLis = new ArrayList<Cert>();
			for (Cert cert : certLis) {
				if (certIds.contains(cert.getId())) {//如果是需要的账户类型审核文件，那么
					memberCertLis.add(cert);//添加取审核资质
				}
			}
			flage(true);
			put("memberCertLis", memberCertLis);
		}catch (NullPointerException e) {
			e.printStackTrace();
			flage(false);
		} catch (Exception e) {
			e.printStackTrace();
			flage(false);
		}
		return end();
	}
	
	@ResponseBody
	@RequestMapping("/judgeAcctType")
	public Object judgeAcctType(HttpSession session) {//保存申请需要的数据
		start();
		try {
			Member member = (Member)session.getAttribute(Constent.MEMBER);
			Ticket dataTicketMember = ticketService.getTicketByMemberId(member.getId());
			if (dataTicketMember == null) {
				dataTicketMember = new Ticket(member.getId(), member.getAuthstatus(), null, null, "/member/accttype.htm");
				ticketService.saveTicket(dataTicketMember);
			}
			flage(true);
			if (dataTicketMember.getPstep().equals("/member.do")) {
				message("管理员正在审核中，请等待！");
				put("autoUrl", dataTicketMember.getPstep());
			}else {
				message("正在加载数据！");
				put("autoUrl", dataTicketMember.getPstep());
			}

		}catch (NullPointerException e) {
			e.printStackTrace();
			flage(false);
		} catch (Exception e) {
			e.printStackTrace();
			flage(false);
		}
		return end();
	}
	
	
	@ResponseBody
	@RequestMapping("/addApply")//保存申请第一步
	public Object addApply(HttpSession session , Member member) {//保存申请需要的数据/auto/toapply1.do
		start();
		try {
			Member member2 = (Member)session.getAttribute(Constent.MEMBER);
			String accttype =(String) session.getAttribute("accttype");
			member.setId(member2.getId());
			member.setAccttype(accttype);
			Integer i = memberService.updateByPrimaryKeySelective(member);//更新用户数据
			if (i > 0) {
				flage(true);
				message("操作成功！");
			}
			ticketService.updateTicketOnPstep(member.getId() ,"/auto/toapply1.do");
		}catch (NullPointerException e) {
			// TODO: handle exception
			flage(false);
			message("请重新登录！");
		} catch (Exception e) {
			// TODO: handle exception
			flage(false);
			message("操作成功！");
		}
		return end();
	}
	@ResponseBody
	@RequestMapping("/upAtuoPicks")//保存申请第一步
	public boolean upAtuoPicks(HttpSession session ,HttpServletRequest request , Integer[] certId) {//保存申请需要的数据
		start();
		try {
			Member member= (Member)session.getAttribute(Constent.MEMBER);			
			MultipartHttpServletRequest servletRequest = (MultipartHttpServletRequest)request;
			MultipartFile[] multipartFiles = new  MultipartFile[certId.length];
			List<Membercert> membercertList = new ArrayList<Membercert>();
			for (int i = 0; i < certId.length; i++) {
				MultipartFile multipartFile = servletRequest.getFile(String.valueOf(certId[i]));
				if (multipartFile == null) {
					return false;
				}
				System.out.println("文件名："+multipartFile.getOriginalFilename());
				multipartFiles[i] = multipartFile;
			}
			String realPath = session.getServletContext().getRealPath("/tipc/auto/");//保存的路径
			for (int i = 0; i < multipartFiles.length; i++) {
				MultipartFile multipartFile =multipartFiles[i];
				String originalFilename = multipartFile.getOriginalFilename();//真实的名字
				String z = originalFilename.substring(originalFilename.lastIndexOf("."));//后缀
				String fileName = UUID.randomUUID().toString()+z;//随机文件名
				String fileNewPath = realPath + fileName;//保存的路径
				File file = new File(fileNewPath);
				multipartFile.transferTo(file);//保存文件
				Membercert membercert = new Membercert(member.getId(), certId[i], "/tipc/auto/"+fileName);
				membercertList.add( membercert);
			}
			Integer i = membercertService.insertMembercert(membercertList);
			Integer y = ticketService.updateTicketOnPstep(member.getId() ,"/auto/toapply2.htm");
			if (i == 1 && y == 1) {
				return true;
			}///auto/toapply2.htm
		}catch (NullPointerException e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@ResponseBody
	@RequestMapping("/addApply3")
	public Object addApply3(String email , HttpSession session) {
		start();
		try {
			Member member = (Member)session.getAttribute(Constent.MEMBER);
			if (!member.getEmail().equals(email)) {
				member.setEmail(email);
				memberService.updateByPrimaryKeySelective(member);
			}
			Map<String, Object> variables = new HashMap<String, Object>();
			String random_4 = PasswordUtil.getRandom_6();//生成随机密码
			variables.put("loginacct", member.getLoginacct());
			variables.put("toEmail", email);
			variables.put("autoCode", random_4);
			ProcessInstance startProcessInstanceById = runtimeService.startProcessInstanceById("auto:1:4",variables);
			System.out.println("开始一个邮件服务：" + startProcessInstanceById);
			//RedisUtils.tryGetDistributedLock(String.valueOf(member.getId()), random_4, 120);//单位秒
			String ok = RedisUtils.setObject(String.valueOf(member.getId()), random_4, 120);
			if (ok.equals("OK")) {
				flage(true);
				message("已经发送到_"+email+"_的邮箱，两分钟后过期！请尽快处理！");
			}else {
				message("邮件发送失败！");
			}
			Ticket ticket = new Ticket(member.getId(), null, startProcessInstanceById.getId(), random_4, "/auto/toapply3.do");
			ticketService.updateTicketByMemberId(ticket);
		}catch (NullPointerException e) {
			e.printStackTrace();
			flage(false);
			message("请重新登录，在尝试！");
		} catch (Exception e) {
			e.printStackTrace();
			flage(false);
			message("邮件发送失败！");
		}
		return end();
	}
	
	@ResponseBody
	@RequestMapping("/addApplyEmail")//审核邮件是否正确
	public Object addApplyEmail(String emailnum , HttpSession session) {
		start();
		try {
			Member member = (Member)session.getAttribute(Constent.MEMBER);
			String email1 = (String) RedisUtils.getObject(String.valueOf(member.getId()));//内存验证码
			if (email1  != null) {
				if (emailnum.equals(email1)) {//如果和内存验证码一样
//					TaskQuery taskQuery = taskService.createTaskQuery().processDefinitionId("auto:1:3204");
//					Task task = taskQuery.taskAssignee(member.getLoginacct()).orderByProcessInstanceId().desc().singleResult();
//					task.
//					  

					Ticket ticket2 = ticketService.getTicketByMemberId(member.getId());//也可以从这个表里面判断验证码
					//   邮件发送任务          //inst-id            //审核代理人
					Task task = taskService.createTaskQuery().processInstanceId(ticket2.getPinstid()).taskAssignee(member.getLoginacct()).singleResult();
					taskService.complete(task.getId());//完成邮件审核步骤
					//消除redis里面的数据-验证码
					RedisUtils.delkeyObject(String.valueOf(member.getId()));
					flage(true);
					message("验证通过！");
					Ticket ticket = new Ticket(member.getId(), "1", null, null, "/member.do");
					Member member2 = new Member();
					member2.setAuthstatus("1");//设置为审核状态
					member2.setId(member.getId());
					memberService.updateByPrimaryKeySelective(member2);
					ticketService.updateTicketByMemberId(ticket);
				}else {
					flage(false);
					message("邮件验证失败！");
				}
			}else {
				flage(false);
				message("邮件验证码失效！");
			}
		}catch (NullPointerException e) {
			e.printStackTrace();
			flage(false);
		} catch (Exception e) {
			e.printStackTrace();
			flage(false);
			message("邮件验证失败！");
		}
		return end();
	}
	@ResponseBody
	@RequestMapping("/reEmail")//重新发送验证码
	public Object reEmail( HttpSession session) {
		start();
		try {
			Member member = (Member) session.getAttribute(Constent.MEMBER);
			String random_6 = PasswordUtil.getRandom_6();//生成随机密码
			System.out.println("开始一个邮件服务：");
			
			javaMailSenderImpl.setDefaultEncoding("UTF-8");
			MimeMessage message = javaMailSenderImpl.createMimeMessage();//一封信
			MimeMessageHelper messageHelper = new MimeMessageHelper(message);
			StringBuilder stringBuilder = new StringBuilder();
			messageHelper.setSubject("实名认证验证码");
			stringBuilder.append("实名认证验证码: "+random_6+"   请在两分钟内完成验证注册！");
			messageHelper.setFrom("1669638693@qq.com");
			messageHelper.setTo(member.getEmail());
			messageHelper.setText(stringBuilder.toString() , true);
			javaMailSenderImpl.send(message);
			
			//RedisUtils.tryGetDistributedLock(String.valueOf(member.getId()), random_4, 120);//单位秒
			String ok = RedisUtils.setObject(String.valueOf(member.getId()), random_6, 120);
			if (ok.equals("OK")) {
				flage(true);
				message("已经发送到_"+member.getEmail()+"_的邮箱，两分钟后过期！请尽快处理！");
			}else {
				message("邮件发送失败！");
			}
			Ticket ticket = new Ticket(member.getId(), null, null, random_6, "/auto/toapply3.do");
			ticketService.updateTicketByMemberId(ticket);
		} catch (NullPointerException e) {
		}catch (Exception e) {
		}
		return end();
	}
	
	@RequestMapping("/initAutomember")
	@ResponseBody
	public Object initAutomember(HttpSession session) {//初始化，获取用户的实时数据
		start();
		try {
			Member member = (Member) session.getAttribute(Constent.MEMBER);
			Member membernow = memberService.getMemberByid(member.getId());
			flage(true);
			put("authstatus", membernow.getAuthstatus());
		} catch (Exception e) {
			flage(false);
		}
		return end();
	}
	
	@ResponseBody
	@RequestMapping("/getMember")
	public Object getMember(HttpSession session) {//保存申请需要的数据，，从session里面获取
		start();
		try {
			Member member = (Member)session.getAttribute(Constent.MEMBER);
			flage(true);
			put("member",member);
		}catch (NullPointerException e) {
			e.printStackTrace();
			flage(false);
		} catch (Exception e) {
			e.printStackTrace();
			flage(false);
		}
		return end();
	}
}
