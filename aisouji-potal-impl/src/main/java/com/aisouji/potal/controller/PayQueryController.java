package com.aisouji.potal.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aisouji.pay.AlipayConfig;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;

@Controller
@RequestMapping("/orderquery")
public class PayQueryController {

	private String out_trade_no;//商户订单编号
	private String trade_no;////支付宝交易号
	private String tradeStatus = null;//交易状态
	private String code = null;//接口请求状态

	@RequestMapping("/query")
	public void query(HttpServletRequest request ,HttpServletResponse response) throws IOException {
		//获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

		//设置请求参数
		AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();

		//商户订单号，商户网站订单系统中唯一订单号
		String out_trade_no = getOut_trade_no();
		if (out_trade_no == null) {
			if (request != null) {
				out_trade_no = new String(request.getParameter("WIDTQout_trade_no").getBytes("ISO-8859-1"),"UTF-8");
			}
		}
		String trade_no = getTrade_no();
		if (trade_no == null) {
			//支付宝交易号
			if (request != null) {
				trade_no = new String(request.getParameter("WIDTQtrade_no").getBytes("ISO-8859-1"),"UTF-8");
			}			
		}	
		//请二选一设置

		alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","+"\"trade_no\":\""+ trade_no +"\"}");		
		//请求		
		try {
			AlipayTradeQueryResponse execute = alipayClient.execute(alipayRequest);//发送请求，得到结果
			this.code =execute.getCode();//获取状态码
			if (code.equals("10000")) {//请求成功
				this.tradeStatus = execute.getTradeStatus();//获取字符状态
			}else {
				System.out.println("支付宝异常错误，请等待");
			}			
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		if (request == null & response == null) {//如果是异步请求查询，查询已经完毕，返回
			return;
		}//后面的为主动查询代码块
		response.setCharacterEncoding("UTF-8");
		//这里开始不是异步查询，执行相应的事情
		if (code.equals("10000")) {//请求成功
			System.out.println("交易查询结果："+tradeStatus);
			//输出
			response.getWriter().println("支付成功，状态为："+tradeStatus);
		}else {
			response.getWriter().println("支付失败");
		}			
	}

	public String getOut_trade_no() {
		if (this.out_trade_no == null) {
			return null;
		}else {
			return out_trade_no;
		}
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getTrade_no() {
		if (this.trade_no == null) {
			return null;
		}else {
			return trade_no;
		}
	}

	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}

	public String getTradeStatus() {
		return tradeStatus;
	}

	public String getCode() {
		return code;
	}
	public void query() throws ServletException, IOException {
		this.query(null, null);
	}
	
	
}
