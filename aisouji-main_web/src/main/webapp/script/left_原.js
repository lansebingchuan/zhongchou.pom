/*引入Header部分公共代码*/
$(function(){
	$.ajax({
		type:"get",
		url:"/menu.htm",
		async:true,
		success:function(data){
			$("#tree").html(data);//加载树
		}
	});
});

function initPermission() {//初始化权限信息
   

}