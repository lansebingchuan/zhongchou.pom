var typeProjects = new Vue({
	el : '#typeProjects',
	data : {
		typeProjectsData: '',
	},
	methods: {
		typeProjectsDatame : function () {//二种传参，get请求
			axios.get('/project/getTypeProjects.do' ,
			 {
				params: {//参数对象
					
				}, 
				responseType: "json",//设置返回类型 stream-图片
			 }
			).then(function(response) {
				typeProjects.typeProjectsData = response.data.projecttypesMony;
			})//response包含了头信息，与地址信息，response.data是服务器返回的数据
			.catch(function (error) { // 请求失败处理
				alert('出错了')
				console.log(error);
			});
		},
		mouseover: function($event) {
			 var e = $($event.currentTarget);
			 e.addClass("projectActive");
		},
		 // 移出
	    mouseLeave: function($event) {
	    	var e = $($event.currentTarget);
	    	e.removeClass("projectActive");
	    },
		xiugaiWidth:function(width) {//修改宽度
			if (!width) {
				return 0;
			}else{
				return width;
			}
		},
	    GetPercent: function (num, total) {
	        /// <summary>
	        /// 求百分比
	        /// </summary>
	        /// <param name="num">当前数</param>
	        /// <param name="total">总数</param>
	        num = parseFloat(num);
	        total = parseFloat(total);
	        if (isNaN(num) || isNaN(total)) {
	            return "-";
	        }
	        return total <= 0 ? "0%" : (Math.round(num / total * 10000) / 100.00)+"%";
	    }
	
	},
	filters: {
		datefilter : function(params) {//截取日期位置
			return params.slice(0,11);
		},
	},
	computed: {
		getTotalMoney : function(params) {
			if (!params) {
				return 0;
			}else{
				return params;
			}
		}
	}
});
typeProjects.typeProjectsDatame();//获取首页类型，和项目的数据

