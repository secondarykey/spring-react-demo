package com.example.demo.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {
	
	public static String sql(Date v,String tz) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
		return df.format(v);
	}

	public static LocalDateTime local(Date v,String tzBuf) {
		String format = "yyyy-MM-dd'T'HH:mm:ss.SSSSS";
		SimpleDateFormat df = new SimpleDateFormat(format);
		String buf = df.format(v) + "+01:00";
	    LocalDateTime parsedDate = LocalDateTime.parse(buf, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	    
	    DateUtil.debug("local()", parsedDate);

		return parsedDate;
	}
	
	public static void debug(String prefix,LocalDateTime v)  {
		System.out.println(prefix + ":" + v.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));		
		System.out.println(v.hashCode());
	}

}
