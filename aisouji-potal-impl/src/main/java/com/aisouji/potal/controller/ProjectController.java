package com.aisouji.potal.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aisouji.bean.Member;
import com.aisouji.bean.Memberaddress;
import com.aisouji.bean.Memberprojectfollow;
import com.aisouji.bean.Project;
import com.aisouji.bean.ProjectOrder;
import com.aisouji.bean.Return;
import com.aisouji.bean.Type;
import com.aisouji.pay.AlipayConfig;
import com.aisouji.potal.service.MemberService;
import com.aisouji.potal.service.MemberaddressService;
import com.aisouji.service.MemberprojectfollowService;
import com.aisouji.service.ProjectOrderService;
import com.aisouji.service.ProjectService;
import com.aisouji.service.ProjecttypeService;
import com.aisouji.service.ReturnService;
import com.aisouji.util.BaseController;
import com.aisouji.util.Constent;
import com.aisouji.util.DateUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;

@Controller
@RequestMapping("/project")
public class ProjectController extends BaseController{

	@Autowired
	MemberService memberService;//会员服务

	@Autowired
	MemberaddressService memberaddressService;

	@Autowired
	ReturnService returnService;

	@Autowired
	ProjectService projectService;

	@Autowired
	MemberprojectfollowService memberprojectfollowService;

	@Autowired
	ProjectOrderService projectOrderService;
	
	@Autowired
	ProjecttypeService projecttypeService;//项目与类型对应表

	@RequestMapping("/project")
	public String project() {//一个项目的购买页面
		return "member/project/project";
	}
	@RequestMapping("/projects")
	public String projects() {//一个项目的购买页面
		return "member/project/projects";
	}
	@RequestMapping("/paystep1")
	public String paystep1() {//一个项目的购买页面
		return "member/project/pay-step-1";
	}
	@RequestMapping("/paystep2")
	public String paystep2() {//一个项目的确认支付页面
		return "member/project/pay-step-2";
	}

	@ResponseBody
	@RequestMapping("/getTypeProjects")//获取项目的所有类型，和每一个类型的项目
	public Object getTypeProjects() {
		start();
		List<Type> allType = memberService.getAllType();//获取所有的类型

		List<Map<String, Object>>  projecttypesMony = projecttypeService.getProjecttypesMony();//类型对应的项目

		Map<Integer, List<Object>> map = new HashMap<Integer, List<Object>>();
		List<Object> list;
		for (Type type : allType) {
			list = new ArrayList<Object>();
			map.put(type.getId(), list);
		}
		for (Map<String, Object> map2 : projecttypesMony) {
			List<Object> list1 = map.get((Integer)map2.get("typeid"));
			list1.add(map2);
			map.put((Integer)map2.get("typeid"), list1);
		}

		List<Object> roots = new ArrayList<Object>();

		for (Type type : allType) {
			list = new ArrayList<Object>();
			list.add(type);
			list.add(map.get(type.getId()));
			roots.add(list);
		}
		put("projecttypesMony", roots);
		return end();
	}

	@ResponseBody
	@RequestMapping("/getProjectResults")//获取项目的所有类型，和每一个类型的项目
	public Object getProjectResults(Integer projectid) {
		start();
		//获取所有的类型
		Map<String, Object> projectMoney = projectService.getProjectMoneyByProjectid(projectid);//获取一个项目的总钱

		String dateString = (String) projectMoney.get("deploydate");
		LocalDateTime localDate = DateUtil.getLocalDate(dateString);//转换为日期对象
		LocalDateTime endDateExclusive = localDate.plusDays((Integer)projectMoney.get("day"));//增加项目天数后的日期
		String remainingDate = DateUtil.getBetweenDateDay(DateUtil.getLocalDateNow(), endDateExclusive);//获取日期差，tostring
		projectMoney.put("remainingDate", remainingDate);//项目结束还剩多少天
		List<Return> returns = returnService.getProjectResultByProjectid(projectid);//获取一个项目所有的回报设置

		List<Map<String, Object>> lMaps = projectService.getProjrctSupporterMemberByProjectid(projectid);//获取所有的项目支持

		Map<Long,List<Object>> monySupporterMember = new HashMap<Long, List<Object>>();//每一种支持类型对应的支持人数集合

		for (Map<String, Object> mony : lMaps) {
			Long supportmoney = (Long)mony.get("supportmoney");
			List<Object> list = monySupporterMember.get(supportmoney);
			if (list == null) {
				list = new ArrayList<Object>();
				list.add(mony);
			}else {
				list.add(mony);
			}
			monySupporterMember.put(supportmoney, list);
		}
		List<Object> return_monySupporter = new ArrayList<Object>();
		for (Return return1 : returns) {//根据支持金额，对每一个金额回报分类
			long supportmoney = return1.getSupportmoney();
			@SuppressWarnings("unlikely-arg-type")
			List<Object> list = monySupporterMember.get(supportmoney);
			List<Object> returnmonySupporter = new ArrayList<Object>();
			returnmonySupporter.add(return1);
			returnmonySupporter.add(list);
			return_monySupporter.add(returnmonySupporter);
		}
		put("return_monySupporter", return_monySupporter);
		put("projectMoney", projectMoney);		
		return end();
	}

	@ResponseBody
	@RequestMapping("/getProjectResult")									//为1：获取邮寄地址，
	public Object getProjectResult(HttpSession session ,Integer projectid, Integer resultid , @RequestParam(defaultValue="0" , required=false) Integer flage) {//获取一个项目的一个回报设置，然后点击购买pay-step-1.html
		start();
		Map<String, Object> projectReturnMap = projectService.getProjectReturnByResultid(projectid ,resultid);
		put("projectReturnMap", projectReturnMap);
		if (flage == 1) {//需要邮寄地址
			Member member = (Member)session.getAttribute(Constent.MEMBER);
			Map<String, Object> memberaddress =  memberaddressService.getAddressByMemberId(member.getId());
			put("memberaddress", memberaddress);
		}
		return end();
	}

	@ResponseBody
	@RequestMapping("/alertMemberAdddress")
	public Object alertMemberAdddress(String memberaddress , HttpSession session) {//获取一个项目的一个回报设置，然后点击购买pay-step-1.html
		start();
		Member member = (Member)session.getAttribute(Constent.MEMBER);
		Memberaddress memberaddress1 = new Memberaddress();
		memberaddress1.setMemberid(member.getId());
		memberaddress1.setAddress(memberaddress);
		Integer i = memberaddressService.alertMemberAddressByMemberid(memberaddress1);
		if (i > 0) {
			flage(true);
		}else {
			flage(false);
		}
		return end();
	}
	@ResponseBody
	@RequestMapping("/toPay")
	public String toPay(HttpSession session ,Integer projectid , Integer supporter, Long supportmoney , String projectname, String projectremark) {
		LocalDateTime localDatestart = DateUtil.getLocalDateNow();
		String dateStart = DateUtil.getLocalDateString(localDatestart);
		Member member = (Member)session.getAttribute(Constent.MEMBER);
		Memberprojectfollow memberprojectfollow = new Memberprojectfollow();
		memberprojectfollow.setMemberid(member.getId());
		memberprojectfollow.setProjectid(projectid);//支持项目的id
		//添加这个用户支持的项目
		Integer follow = memberprojectfollowService.insertMemberprojectfollow(memberprojectfollow);//返回主键projectfollow-id
		if (follow > 0 ) {
			Project oldProject = new Project();
			oldProject.setSupporter(supporter);
			oldProject.setSupportmoney(supportmoney);
			oldProject.setCreatedate(dateStart);
			oldProject.setMemberid(member.getId());
			oldProject.setCompletion(0);
			oldProject.setName(projectname);
			oldProject.setRemark(projectremark);
			oldProject.setFollower(memberprojectfollow.getId());
			Integer oldi = projectService.insertMemberProject(oldProject);//返回主键project-id
			if (oldi > 0) {//支持的项目添加完成
				ProjectOrder projectOrder = new ProjectOrder();
				Integer projectOrderid = oldProject.getId();
				projectOrder.setProjectid(projectOrderid);//支持项目id
				projectOrder.setMemberid(member.getId());//支持会员id
				LocalDateTime dateend = localDatestart.plusMinutes(30);//支付三十分钟结束
				projectOrder.setCreatdate(dateStart);
				projectOrder.setEnddate(DateUtil.getLocalDateString(dateend));		
				
				String out_trade_no = DateUtil.dateStringToOrderId(dateStart)+projectid;//当时日期，加上购买的项目id
				
				projectOrder.setOrdernumber(out_trade_no);//订单编号
				
				Integer totalmoney = supportmoney.intValue() * supporter.intValue();
				
				projectOrder.setTotalmoney(totalmoney);//总的金额
				
				Integer projectOrdera = projectOrderService.addProjectOrder(projectOrder);
				
				if (projectOrdera <=0) {//如果订单插入失败
					return null;
				}
				
				String total_amount =  String.valueOf(totalmoney);
				
				//获得初始化的AlipayClient
				AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
				
				//设置请求参数
				AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
				alipayRequest.setReturnUrl(AlipayConfig.return_url);
				alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
				
				
				alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," //订单编号
						+ "\"total_amount\":\""+ total_amount +"\"," //订单金额
						+ "\"subject\":\""+ projectname +"\"," //订单名称
						+ "\"body\":\""+ projectremark +"\"," //描述
						+ "\"timeout_express\":\""+30+"m\","  //超时时间,这里是15分钟
						+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
				//请求支付宝
				String result = null;
				try {
					result = alipayClient.pageExecute(alipayRequest).getBody();
					
					return result;//返回支付宝提交页面
				} catch (AlipayApiException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;

	}
}
