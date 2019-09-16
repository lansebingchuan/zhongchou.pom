package com.email;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.aisouji.util.DesUtil;

public class SpringEmail {
	
	private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/spring-*.xml");
	
	@Test
	public void sendEmail() {
		
		JavaMailSenderImpl  javaMailSender = (JavaMailSenderImpl) applicationContext.getBean("javaMailSenderImpl");
		javaMailSender.setDefaultEncoding("UTF-8");
		MimeMessage message = javaMailSender.createMimeMessage();//一封信
		MimeMessageHelper messageHelper = new MimeMessageHelper(message);
		StringBuilder stringBuilder = new StringBuilder();
		
		String value = "ILY";
		try {
			value = DesUtil.encrypt(value, "sdfdafds");
			messageHelper.setSubject("来自SpringEmail");
			stringBuilder.append("<a href='http://www.baidu.com?id="+value+"'>去百度</a>");
			messageHelper.setFrom("1669638693@qq.com");
			messageHelper.setTo("2827273055@qq.com");
			messageHelper.setText(stringBuilder.toString() , true);
			javaMailSender.send(message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
