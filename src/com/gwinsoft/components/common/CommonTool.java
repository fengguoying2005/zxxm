package com.gwinsoft.components.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import com.gwinsoft.util.GwinSoftUtil;

public class CommonTool {

	public static String getLSH() {
		try {
			String time = GwinSoftUtil.getSysFormatDate("mSSS");
			String uid = UUID.randomUUID().toString().replaceAll("-", "");
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(uid.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			String result = time + buf.toString().substring(8, 24);// 16位的加密
			if(result.length()>20) {
				result = result.substring(result.length()-20);
			}
			return result;
		} catch (NoSuchAlgorithmException e) {
			return ""+System.currentTimeMillis();
		}
	}
	public static final DateFormat df20 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final DateFormat df10 = new SimpleDateFormat("yyyy-MM-dd");
	public static String format20(Date date) {
		if(date == null) {
			return "";
		}
		return df20.format(date);
	}
	public static String format10(Date date) {
		if(date == null) {
			return "";
		}
		return df10.format(date);
	}
	public static int getMonthDays() {
		return getMonthDays(GwinSoftUtil.getSysDate());
	}
	public static int getMonthDays(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	public static int getMonthDays(String date) {
		try {
			Date day = df10.parse(date);
			return getMonthDays(day);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}
	public static void main(String[] args) {
		System.out.println(getMonthDays("2013-01-01"));
		System.out.println(getMonthDays("2012-02-01"));
		System.out.println(getMonthDays("2013-03-01"));
		System.out.println(getMonthDays("2013-04-01"));
	}
}

