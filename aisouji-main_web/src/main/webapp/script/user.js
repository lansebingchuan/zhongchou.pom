function initUserName(){
	 $.post("/getUser.do", function(result){
		 if (!result) {
			 document.getElementById('userName').innerText="请修改基本信息!";
		}else {
			document.getElementById('userName').innerText=result;
			}
    });
}
var time=5;//设定跳转的时间
var url;
var message;
var num;
function skept(url1 , time1 , message1 ,num1) {
   	 url = url1;
   	 time = time1;
   	 message = message1;
   	 num = num1;
	 setInterval("refer()",1000); //启动1秒定时
	  	}
function refer(){
    if(time==0){
      layer.close();
      window.location.href=url; //#设定跳转的链接地址

      }
     // document.getElementById('show').innerHTML="注册成功！"+""+t+"秒后跳转到首页"; // 显示倒计时
      layer.msg(message,{time:1000, icon:num, shift:6});
      time--; // 计数器递减
}
var startIntext;
function start() {//开始等待 
	 index = layer.load(2, {time: 1000});
	 startIntext = index;
}
function stop_load(url){
	layer.close(startIntext);
	window.location.href=url; //#设定跳转的链接地址
}
function stop(){
	layer.close(startIntext);//取消等待
}
//为表单元素添加失去焦点事件
var chakeFormYes_No = false;//用于ajaxpost、get进行判断是否合法
//为表单元素添加失去焦点事件
$("form :input").blur(function(){
    var $parent = $(this).parent();
    $parent.find(".msg").remove(); //删除以前的提醒元素（find()：查找匹配元素集中元素的所有匹配元素）
    //验证姓名
    if($(this).is("#loginacct")){
        var nameVal = $.trim(this.value); //原生js去空格方式：this.replace(/(^\s*)|(\s*$)/g, "")
        var regName = /[~#^$@%&!*()<>:;'"{}【】  ]/;
        if(nameVal == "" || nameVal.length < 6 || regName.test(nameVal)){
            var errorMsg = " 姓名非空，长度6位以上，不包含特殊字符！";
            //class='msg onError' 中间的空格是层叠样式的格式
            $parent.append("<span class='msg onError'>" + errorMsg + "</span>");
        }
        else{
            var okMsg=" 输入正确";
            $parent.find(".high").remove();
            $parent.append("<span class='msg onSuccess'>" + okMsg + "</span>");
        }
    }
    //验证邮箱
    if($(this).is("#email")){
        var emailVal = $.trim(this.value);
        var regEmail = /.+@.+\.[a-zA-Z]{2,4}$/;
        if(emailVal== "" || (emailVal != "" && !regEmail.test(emailVal))){
            var errorMsg = " 请输入正确的E-Mail住址！";
            $parent.append("<span class='msg onError'>" + errorMsg + "</span>");
        }
        else{
            var okMsg=" 输入正确";


            $parent.append("<span class='msg onSuccess'>" + okMsg + "</span>");
        }
    }
}).keyup(function(){
    //triggerHandler 防止事件执行完后，浏览器自动为标签获得焦点
    $(this).triggerHandler("blur"); 
}).focus(function(){
    $(this).triggerHandler("blur");
});

function chakeForm() {
   	$("form :input").each(function(){
           var $parent = $(this).parent();
           $parent.find(".msg").remove(); //删除以前的提醒元素（find()：查找匹配元素集中元素的所有匹配元素）
           //验证姓名
           if($(this).is("#loginacct")){
               var nameVal = $.trim(this.value); //原生js去空格方式：this.replace(/(^\s*)|(\s*$)/g, "")
               var regName = /[~#^$@%&!*()<>:;'"{}【】  ]/;
               if(nameVal == "" || nameVal.length < 6 || regName.test(nameVal)){
                   var errorMsg = " 姓名非空，长度6位以上，不包含特殊字符！";
                   //class='msg onError' 中间的空格是层叠样式的格式
                   $parent.append("<span class='msg onError'>" + errorMsg + "</span>");
                   chakeFormYes_No = false;
                   return false;
               }
               else{
                   var okMsg=" 输入正确";
                   $parent.find(".high").remove();
                   $parent.append("<span class='msg onSuccess'>" + okMsg + "</span>");
                   chakeFormYes_No = true;
               }
           }
           //验证邮箱
           if($(this).is("#email")){
               var emailVal = $.trim(this.value);
               var regEmail = /.+@.+\.[a-zA-Z]{2,4}$/;
               if(emailVal== "" || (emailVal != "" && !regEmail.test(emailVal))){
                   var errorMsg = " 请输入正确的E-Mail住址！";
                   $parent.append("<span class='msg onError'>" + errorMsg + "</span>");
                   chakeFormYes_No = false;
                   return false;
               }
               else{
                   var okMsg=" 输入正确";
                   $parent.find(".high").remove();
                   $parent.append("<span class='msg onSuccess'>" + okMsg + "</span>");
                   chakeFormYes_No = true;
               }
           }
       }).keyup(function(){
           //triggerHandler 防止事件执行完后，浏览器自动为标签获得焦点
           $(this).triggerHandler("blur"); 
       }).focus(function(){
           $(this).triggerHandler("blur");
       });
} 