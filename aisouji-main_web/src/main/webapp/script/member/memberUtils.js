function initMemberName() {
	$.post("/member/getMemberName.do" , function(memberMap) {
		 if (!memberMap.flage) {
			 document.getElementById('memberName').innerText="请修改基本信息!";
		}else {
			document.getElementById('memberName').innerText=memberMap.memberName;
			if (document.getElementById('memberLoginName')) {
				document.getElementById('memberLoginName').innerText=memberMap.memberLoginName;
			}
		}
	});
}