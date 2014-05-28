package com.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateManage {
	// genarate date
	public static String getNowTime() {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		return sdf.format(d);
	} 

	// genarate today
	public static String getToday() {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(d);
	}

	/**
	 * 
	 * @param num
	 *            : 时间变更数量
	 * @param unit
	 *            : 时间变更单位(YEAR; MONTH; DATE)
	 * @return
	 */
	public static String getDate(int num, String unit) {
		Calendar cal = Calendar.getInstance();
		if ("YEAR".equals(unit)) {
			cal.add(Calendar.YEAR, num);
		} else if ("MONTH".equals(unit)) {
			cal.add(Calendar.MONTH, num);
		} else if ("DATE".equals(unit)) {
			cal.add(Calendar.DATE, num);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(cal.getTime());
	}
	/**
	 * 
	 * @param num
	 *            : 时间变更数量
	 * @param unit
	 *            : 时间变更单位(YEAR; MONTH; DATE)
	 * @return
	 */
	public static String getDateTime(int num, String unit) {
		Calendar cal = Calendar.getInstance();
		if ("YEAR".equals(unit)) {
			cal.add(Calendar.YEAR, num);
		} else if ("MONTH".equals(unit)) {
			cal.add(Calendar.MONTH, num);
		} else if ("DATE".equals(unit)) {
			cal.add(Calendar.DATE, num);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		return sdf.format(cal.getTime());
	}
	// genarate date
		public static String getNowTimeWithOutSign() {
			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssS");
			return sdf.format(d);
		}
}
