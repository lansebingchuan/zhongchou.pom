package com.aisouji.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

public class DateUtil {
	
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	public static LocalDateTime getLocalDate(String dateString) {	
		return LocalDateTime.parse(dateString , formatter);
	}
	
	public static String getLocalDateString(LocalDateTime localDateTime) {
		return formatter.toFormat().format(localDateTime);
	}
	
	public static LocalDateTime getLocalDateNow() {
		return LocalDateTime.now();
	}
	
	public static String getBetweenDateDay(LocalDateTime startDateInclusive ,LocalDateTime endDateExclusive) {
		Period between = Period.between(startDateInclusive.toLocalDate(), endDateExclusive.toLocalDate());
		int years = between.getYears();
		int months = between.getMonths();
		int days = between.getDays();
		StringBuilder stringBuilder = new StringBuilder();
		if (years >0) {
			stringBuilder.append(years+"年");
		}
		if (months > 0) {
			stringBuilder.append(months+"月");
		}
		if (days > 0) {
			stringBuilder.append(days+"天");
		}
		return stringBuilder.toString();
	}
	
	@Test
	public void dataTostring() {
		System.out.println(getLocalDateString(LocalDateTime.now()));  //2019-07-26 18:39:51
		LocalDateTime localDate = getLocalDate("2019-07-08 18:39:51");
		LocalDateTime endDateExclusive = localDate.plusDays(20);
		String betweenDateDay = getBetweenDateDay(getLocalDateNow(), endDateExclusive);
		System.out.println(betweenDateDay);
	}
	
	@Test
	public static String dateStringToOrderId(String string) {//替换掉时间中的    “空格 , - , ：”
		//System.out.println(string);
		string = string.replace(" ", "");
		//System.out.println(string);
		string =string.replaceAll("-", "");
		//System.out.println(string);
		string =string.replaceAll(":", "");
		//System.out.println(string);
		return string;
	}
}
