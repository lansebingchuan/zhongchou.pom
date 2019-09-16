$($.get("/member/member_top.htm" , function(result){
	$("#member_top").html(result);
	initMemberName();//加载会员名
}));