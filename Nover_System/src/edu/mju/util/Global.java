package edu.mju.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Global {
	public static final double PI = 3.1415;

	public static String getSysDate() {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		return dateFormat.format(date);
	}

	public static String getSysTimeStamp() {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmssS");

		return dateFormat.format(date);
	}

	public static String NullToStr(String str) {
		if (str == null || str.equalsIgnoreCase("null")) {
			str = "";
		}
		return str;
	}
}
