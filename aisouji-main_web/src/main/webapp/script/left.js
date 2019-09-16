//左侧导航部分
var rolePermission;
$(function(){
	$.ajax({
		type:"post",
		url:"/rolePermission.do",
		async:true,
		success:function(data){
		  if (!data || data == "") {
			  layer.msg("请重新登录！", {time:1000, icon:5, shift:6});
			  return;
		  }
		  rolePermission = data;
		  initPermission();
		  action();//添加事件
		 /* $("#tree").html(data);//加载树
*/		}
	});
});
    
function initPermission() {//初始化权限信息
   var tree = $("#tree");
   var ul_1 = $('<ul style="padding-left:0px;" class="list-group"></ul>');
   var li_out1 = $('<li class="list-group-item tree-closed" ><i class="'+rolePermission.icon+'"></i> '+rolePermission.name+'：'+'</li>');
   ul_1.append(li_out1);
   var childrenout = rolePermission.children;
   $.each(childrenout ,function(kay , value) {
    var perimission = value;
    var children = perimission.children;
    if (children.length > 0) {//不是数组
    	if (true) {//perimission.open
    		 var li_zu1 = $('<li class="list-group-item"></li>');  
		}else {
	    	var li_zu1 = $('<li class="list-group-item tree-closed"></li>');  
		}

        var li_zu1_span_1 = $('<span><i class="'+perimission.icon+'"></i>'+perimission.name+'<span class="badge" style="float:right">'+children.length+'</span></span>');
        li_zu1.append(li_zu1_span_1);
        if (true) {
            var ul_zu1 = $('<ul style="margin-top:10px;"></ul>');
		}else {
			var ul_zu1 = $('<ul style="margin-top:10px;display:none;"></ul>');
		}

        $.each(children , function(key , value) {
            var childrenin = value;
            var li2 = $('<li style="height:30px; width: 190px;">'+
                        '<a id="weihu" href="'+childrenin.url+'" ><i class="'+childrenin.icon+'"></i> '+childrenin.name+'</a>'+
                      '</li>');
            ul_zu1.append(li2);
            li_zu1.append(ul_zu1);
            ul_1.append(li_zu1);
        })
    }else{
        var li_out1 = $('<li class="list-group-item tree-closed" ><a href="'+perimission.url+'"><i class="'+perimission.icon+'"></i> '+perimission.name+'</a></li>');
        ul_1.append(li_out1);
    }
   });
   tree.append(ul_1);
}

function action() {//懂作事件
	$(function () {
	  $(".list-group-item").click(function(){
	  if ( $(this).find("ul") ) {
	   $(this).toggleClass("tree-closed");
	   if ( $(this).hasClass("tree-closed") ) {
	 $("ul", this).hide("fast");
	   } else {
	     $("ul", this).show("fast");
	       }
	     }
	  });
	    });
	 $(function() {//获取当前的URL，并设置点击的样式
	  var href = window.location.href;
	  var host = window.location.host;
	   var hostindex = href.indexOf(host);
	   var hostLength = host.length;
	   var url = href.substr(hostindex+hostLength);//获取当前的URL，
	   var as = $("#tree ul li ul li a");
	   var acss =  $('#tree ul li ul li a[href="'+url+'"]')
	   acss.css("color","red");
	   
	});
	$("tbody .btn-success").click(function(){
	window.location.href = "assignRole.html";
	});
	$("tbody .btn-primary").click(function(){
	window.location.href = "edit.html";
	});
}