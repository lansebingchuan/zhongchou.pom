package com.aisouji.potal.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aisouji.bean.Member;
import com.aisouji.service.ProjectService;
import com.aisouji.util.BaseController;
import com.aisouji.util.Constent;

@Controller
@RequestMapping("/member")
public class MemberController extends BaseController{
	
	@Autowired
	ProjectService projectService;
	
	@RequestMapping("/member_top")
	public String member_top() {//返回top页面
		return "member_common/member_top";
	}
	@RequestMapping("/accttype")
	public String accttype() {
		return "member/accttype";
	}
	@RequestMapping("/minecrowdfunding")
	public String minecrowdfunding() {
		return "member/minecrowdfunding";
	}
	@ResponseBody
	@RequestMapping("/getMemberName")
	public Object getMemberName(HttpSession session) {
		start();
		Member member = (Member)session.getAttribute(Constent.MEMBER);
		if (member != null) {
			put("memberName", member.getUsername());
			put("memberLoginName", member.getLoginacct());
			put("email", member.getEmail());
			flage(true);
		}else {
			flage(false);
		}
		return end();
	}
	
	@ResponseBody
	@RequestMapping("/getMemberSupportFocusonInitiate")//获取会员的支持的项目，收藏的项目，发布的项目
	public Object getMemberSupportFocusonInitiate(HttpSession session) {
		start();
		Member member = (Member)session.getAttribute(Constent.MEMBER);
		List<Map<String, Object>> membersupport =  projectService.getMemberSupportProjectBymemberId(member.getId());
		List<Map<String, Object>> memberinitiate =  projectService.getMemberReleaseProjectByMemberId(member.getId());
		put("membersupport", membersupport);
		put("memberinitiate", memberinitiate);
		return end();
	}
}
