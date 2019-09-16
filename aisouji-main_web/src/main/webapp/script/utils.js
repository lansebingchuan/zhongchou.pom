 function getUrlId(id){
   var reg = new RegExp("(^|&)"+ id +"=([^&]*)(&|$)");
   var r = window.location.search.substr(1).match(reg);
   if(r!=null)return unescape(r[2]);
   return null;
}
 

function timeDifc(start,end){
	 
 let starts = new Date(start),ends = new Date(end),message = '';
 
if (starts.getTime() > ends.getTime())
	 
     return message = "现在的时间小于以前的时间!";
  
     if ((ends.getTime() - starts.getTime())/(1000*60) < 1)
         return message = "刚刚";
  
     if (ends.getFullYear() > starts.getFullYear() && ends.getMonth() >= starts.getMonth())
         message += ends.getFullYear() - starts.getFullYear() + "年";
  
     if (ends.getMonth() > starts.getMonth() && ends.getDate() >= starts.getDate())
         message += ends.getMonth() - starts.getMonth() + "个月";
  
     if (ends.getDate() > starts.getDate() && ends.getHours() >= starts.getHours())
         message += ends.getDate() - starts.getDate() + "天";
  
     if (ends.getHours() > starts.getHours() && ends.getMinutes() >= starts.getMinutes())
         message += ends.getHours() - starts.getHours() + "小时";
  
     if (ends.getMinutes() > starts.getMinutes())
         message += ends.getMinutes() - starts.getMinutes() + "分钟";
    
     return message;
};
