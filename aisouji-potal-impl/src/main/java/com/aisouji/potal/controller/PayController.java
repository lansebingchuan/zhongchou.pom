package com.aisouji.potal.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aisouji.bean.ProjectOrder;
import com.aisouji.pay.AlipayConfig;
import com.aisouji.service.ProjectOrderService;
import com.aisouji.service.ProjectService;
import com.aisouji.util.BaseController;
import com.aisouji.util.DesUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;

@Controller
@RequestMapping("/pay")
public class PayController extends BaseController{

	@Autowired
	ProjectOrderService projectOrderService;

	@Autowired
	ProjectService projectService;

	@RequestMapping("/returnUrl")//同步的后置通知
	public String returnUrl(HttpServletRequest request , HttpServletResponse response) throws Exception {
		//获取支付宝GET过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}

		boolean signVerified = false;
		try {
			signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type);
		} catch (AlipayApiException e) {
			e.printStackTrace();
		} //调用SDK验证签名

		//——请在这里编写您的程序（以下代码仅作参考）——
		if(signVerified) {
			//商户订单号,必须验证
			String orderNumber = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

			//支付宝交易号
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

			//交易金额-必须
			String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");

			//APPID-必须验证
			String app_id = new String(request.getParameter("app_id").getBytes("ISO-8859-1"),"UTF-8");			

			ProjectOrder projectOrder = projectOrderService.getProjectOrderByOrderNumber(orderNumber);

			if (projectOrder != null) {//此订单存在
				total_amount = total_amount.substring(0, total_amount.indexOf("."));//java.lang.NumberFormatException: For input string: "1.00",,对返回金额格式化
				if (projectOrder.getTotalmoney().equals(Integer.valueOf(total_amount)) && app_id.equals(AlipayConfig.app_id)) {
					String encryptorderNumber = DesUtil.encrypt(orderNumber, DesUtil.ORDER);//对订单编号加密get传输
					return "redirect:/pay/paysuccess.htm?orderNumber="+encryptorderNumber;
				}
			}else {
				return "redirect:/pay/paysuccess.htm?orderNumber=0";//0表示订单不存在
			}
		}else {
			response.getWriter().println("验签失败");
		}
		return "redirect:/pay/paysuccess.htm?orderNumber=1";//1 表示订单错误
	}

	@RequestMapping("/paysuccess")
	public String paysuccess() {////0表示订单不存在 ,1 表示订单错误
		return "member/payhtml/paysuccess";		
	}

	@ResponseBody
	@RequestMapping("/getProjectOrderByOrderNumber")//在订单展示页面，根据订单id，获取订单详细
	public Object getProjectOrderByOrderNumber(String orderNumber) throws IOException, Exception {////0表示订单不存在 ,1 表示订单错误
		start();
		orderNumber = DesUtil.decrypt(orderNumber, DesUtil.ORDER);
		Map<String, Object> map = null;
		if (orderNumber.equals("1")) {
			map = new HashMap<String, Object>();
			map.put("error", "订单错误");
		}else if (orderNumber.equals("0")) {
			map = new HashMap<String, Object>();
			map.put("error", "订单不存在");
		}else {
			Map<String, Object> orderMessage = projectOrderService.getProjectOrderMessageByOrderNumber(orderNumber);
			System.out.println("获取订单-orderNumber-详细信息"+orderMessage);
			put("orderMessage", orderMessage);
		}
		if (map != null) {
			put("error", map);
		}
		return end();		
	}

	@ResponseBody
	@RequestMapping("/notify_url")//支付宝需要的异步通知
	public String notify_url(HttpServletRequest request ,HttpServletResponse response) throws IOException, ServletException {
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}

		boolean signVerified = false;
		try {//调用SDK验证签名
			signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type);
		} catch (AlipayApiException e) {
			e.printStackTrace();
		} 

		//——请在这里编写您的程序（以下代码仅作参考）——

		/* 实际验证过程建议商户务必添加以下校验：
					1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
					2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
					3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
					4、验证app_id是否为该商户本身。
		 */
		if(signVerified) {//验证成功
			//商户订单号,必须验证
			String orderNumber = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

			//支付宝交易号
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

			//交易状态
			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

			//交易金额-必须
			String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");

			//APPID-必须验证
			String app_id = new String(request.getParameter("app_id").getBytes("ISO-8859-1"),"UTF-8");

			//取数据库 的数据
			total_amount = total_amount.substring(0, total_amount.indexOf("."));//对金额格式化
			ProjectOrder projectOrder = projectOrderService.getProjectOrderByOrderNumber(orderNumber);

			if (projectOrder !=null &&	AlipayConfig.app_id.equals(app_id)
					&projectOrder.getOrdernumber().equals(orderNumber)
					//&projectOrder.getTrade_no().equals(trade_no)
					//&projectOrder.getTrade_status().equals(trade_status)
					&projectOrder.getTotalmoney().equals(Integer.valueOf(total_amount))) {//如果异步信息对，那么进行异步查询，进一步确认

				PayQueryController query = new PayQueryController();//主动查询是否交易完成
				query.setTrade_no(trade_no);
				query.query();//执行请求查询				
				if (query.getCode().equals("10000")) {//请求成功，然后根据不同的状态码，更新数据库的的值
					String tradeStatus = query.getTradeStatus();
					if(tradeStatus.equals("TRADE_FINISHED")){//不支持退款
						Integer projectid = projectOrder.getProjectid();
						Integer aInteger = projectService.updateCompletionByProjectId(projectid , 1);
						ProjectOrder newprojectOrder  = new ProjectOrder();//通过订单更新订单流水号
						newprojectOrder.setOrdernumber(orderNumber);
						newprojectOrder.setSerialnumber(trade_no);
						Integer bInteger = projectOrderService.updateProjectOrderByOrderNumber(newprojectOrder);
						if (aInteger > 0 && bInteger > 0) {
							System.out.println("交易完成，不能退款");
							return "success";
						}
					}else if (tradeStatus.equals("TRADE_SUCCESS")){//支持退款
						Integer projectid = projectOrder.getProjectid();
						Integer aInteger = projectService.updateCompletionByProjectId(projectid , 2);
						ProjectOrder newprojectOrder  = new ProjectOrder();//通过订单更新订单流水号
						newprojectOrder.setOrdernumber(orderNumber);
						newprojectOrder.setSerialnumber(trade_no);
						Integer bInteger = projectOrderService.updateProjectOrderByOrderNumber(newprojectOrder);
						if (aInteger > 0 && bInteger > 0) {
							System.out.println("交易完成，能退款");
							return "success";
						}
					}else if (tradeStatus.equals("TRADE_CLOSED")) {//未付款交易超时关闭，或支付完成后全额退款
						Integer projectid = projectOrder.getProjectid();
						Integer aInteger = projectService.updateCompletionByProjectId(projectid , 3);
						ProjectOrder newprojectOrder  = new ProjectOrder();//通过订单更新订单流水号
						newprojectOrder.setOrdernumber(orderNumber);
						newprojectOrder.setSerialnumber(trade_no);
						projectOrderService.updateProjectOrderByOrderNumber(newprojectOrder);
						if (aInteger > 0) {
							System.out.println("未付款交易超时关闭，或支付完成后全额退款");
							return "success";
						}
					}else if (tradeStatus.equals("WAIT_BUYER_PAY")) {//交易创建，等待支付
						Integer projectid = projectOrder.getProjectid();
						Integer aInteger = projectService.updateCompletionByProjectId(projectid ,4);
						ProjectOrder newprojectOrder  = new ProjectOrder();//通过订单更新订单流水号
						newprojectOrder.setOrdernumber(orderNumber);
						newprojectOrder.setSerialnumber(trade_no);
						projectOrderService.updateProjectOrderByOrderNumber(newprojectOrder);
						if (aInteger > 0) {
							System.out.println("交易创建，等待支付");
							return "success";
						}
					}
				}else {//请求接口失败
					return "success";
				}			
			}					
			//resp.getWriter().println("success");//程序执行完后必须打印输出“success”（不包含引号
			System.out.println("异步验签成功");
			return "success";
		}else {//验证失败
			//resp.getWriter().println("fail");
			System.out.println("异步验签失败");
			//调试用，写文本函数记录程序运行情况是否正常
			//String sWord = AlipaySignature.getSignCheckContentV1(params);
			//AlipayConfig.logResult(sWord);
			return "success";
		}
		//——请在这里编写您的程序（以上代码仅作参考）——
	}

	@ResponseBody
	@RequestMapping("/fastpayRefund")//商户订单号                        //支付宝交易号
	public Object fastpayRefund(String orderNumber ,String serialnumber ,String totalmoney ,Integer projectid) {
		start();
		//获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

		//设置请求参数
		AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();
		//退款的原因说明
		String refund_reason = new String("不喜欢次项目！");
		//标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传,可以为订单编号
		String out_request_no = orderNumber;

		alipayRequest.setBizContent("{\"out_trade_no\":\""+ orderNumber +"\"," 
				+ "\"trade_no\":\""+ serialnumber +"\"," 
				+ "\"refund_amount\":\""+ totalmoney +"\"," 
				+ "\"refund_reason\":\""+ refund_reason +"\"," 
				+ "\"out_request_no\":\""+ out_request_no +"\"}");

		AlipayTradeRefundResponse alipayTradeRefundResponse = null;
		try {
			alipayTradeRefundResponse = alipayClient.execute(alipayRequest);
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(alipayTradeRefundResponse.isSuccess()){
			System.out.println("退款成功！");
			Integer a = projectService.updateCompletionByProjectId(projectid, 5);//修改为取消订单
			if (a > 0) {
				flage(true);
			}else {
				flage(false);
			}
		} else {
			System.out.println("退款失败！");
			flage(false);
		}
		return end();

	}

}
