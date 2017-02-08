package com.yangyang.corejava.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalcDate {

	public static void main(String[] args) throws ParseException {
		System.out.println(getDateSpace("2015-03-01", "2017-01-13"));
	}

	public static int getDateSpace(String date1, String date2) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(sdf.parse(date1));
		// 设置时间为0时
		cal1.set(Calendar.HOUR_OF_DAY, 0);
		cal1.set(Calendar.MINUTE, 0);
		cal1.set(Calendar.SECOND, 0);
		cal2.setTime(sdf.parse(date2));
		cal2.set(Calendar.HOUR_OF_DAY, 0);
		cal2.set(Calendar.MINUTE, 0);
		cal2.set(Calendar.SECOND, 0);
		// 得到两个日期相差的天数 O
		int days = ((int) (cal2.getTime().getTime() / 1000) - (int) (cal1.getTime().getTime() / 1000)) / 3600 / 24;
		return days;
	}
}
