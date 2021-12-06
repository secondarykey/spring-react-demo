package com.example.demo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {
	
	private static final String ClientFmt = "yyyy-MM-dd HH:mm:ss";
	private static final SimpleDateFormat sdf = new SimpleDateFormat(ClientFmt);	
	private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern(ClientFmt);

	/**
	 * クライアント時刻変換
	 * @param v 時刻文字列
	 * @return Date型
	 */
	public static Date parse(String v) {
		try {
			return sdf.parse(v);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * ローカルタイム変換
	 * @param v 時刻
	 * @return ローカルタイム変換
	 */
	private static LocalDateTime local(Date v) {
		String buf = sdf.format(v);
		return LocalDateTime.parse(buf,dtf);
	}

	/**
	 * 時刻をそのままゾーンを付与
	 * 注：without time zone にinsertすると、JSTで保存される為、使用してはいけない。
	 * @param v 対象時刻
	 * @param tz タイムゾーンの略(UTC,JSTなど)
	 * @return 指定時刻にタイムゾーンを付与した時刻
	 */
	public static OffsetDateTime zone(Date v,String tz) {
		Instant intant = v.toInstant();
	    ZoneId id = ZoneId.of(tz,ZoneId.SHORT_IDS);
	    ZoneOffset offset = id.getRules().getOffset(intant);
	    LocalDateTime local = local(v);
	    return OffsetDateTime.of(local, offset);
	}
}
