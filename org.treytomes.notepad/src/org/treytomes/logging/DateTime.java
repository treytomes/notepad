package org.treytomes.logging;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTime {
	
	private static final String FORMAT_DATETIMESTAMP = "yyyyMMddHHmmss";

	public static Date getNow() {
		return new Date();
	}
	
	public static String getDateTimeStamp(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DATETIMESTAMP);
		return dateFormat.format(date);
	}
	
	public static String getDateTimeStamp(long milliseconds) {
		return getDateTimeStamp(new Date(milliseconds));
	}
}
