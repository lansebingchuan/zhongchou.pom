package com.timer;

import org.junit.Test;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TimerTest {

	private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/spring-*.xml");
	
	public static void main(String[] args) {
		
	}
}
