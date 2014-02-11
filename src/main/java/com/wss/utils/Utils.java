package com.wss.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
	public static String implodeArray(Object[] array, String separator) {
		int len = array.length - 1;
		StringBuilder str = new StringBuilder();
		separator = separator + "";
		for (int i = 0; i <= len; i++) {
			str.append(array[i] + "");
			if (i < len) str.append(separator);
		}
		return str.toString();
	}

	public static boolean isNumeric(String str) {
		return str.matches("\\d*");
	}
	public static String getDateTime(String format){
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(new Date());
	}
}
