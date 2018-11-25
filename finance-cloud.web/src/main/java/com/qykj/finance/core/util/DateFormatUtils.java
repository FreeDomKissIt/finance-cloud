package com.qykj.finance.core.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.apache.commons.lang3.StringUtils;


/**
 * <p>
 * 日期格式化工具类，提供日期的各种格式化静态方法；
 * </p>
 * @author wenjing 
 * @version V1.0 
 */
public class DateFormatUtils{
	
	/**
	 * 时间格式：<tt>yyyy-MM-dd HH:mm:ss</tt>.
	 */
	public static final String FORMAT_COMMON_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 *时间格式： <tt>yyyy-MM-dd'T'HH:mm:ss.SSS'Z'</tt>.
	 */
	public static final String DATE_FORMAT_LONG = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	
	/**
	 *时间格式： <tt>yyyy-MM-dd'T'HH:mm:ss'Z'</tt>.
	 */
	public static final String DATE_FORMAT_SHORT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	
	/**
	 *时间格式： <tt>yyyyMMddHHmmssSSS</tt>.
	 */
	public static final String DATE_FORMAT_NUM_LONG = "yyyyMMddHHmmssSSS";
	
	/**
	 *时间格式： <tt>yyyyMMddHHmmss</tt>.
	 */
	public static final String DATE_FORMAT_NUM_SHORT = "yyyyMMddHHmmss";
	
	/**
	 *时间格式： <tt>yyyy-MM-dd</tt>.
	 */
	public static final String DATE_FORMAT_MIN = "yyyy-MM-dd";
	
	private static final String DATE_FORMAT_DEFAULT = DATE_FORMAT_LONG;
	
	static final long MILLISECONDS_PER_MINUTE = 60L * 1000;
	
	/**
	 * ISO8601标准不带时区的格式，.时间格式： <tt>yyyy-MM-dd'T'HH:mm:ss</tt>.
	 */
	public static final FastDateFormat ISO_DATETIME_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd'T'HH:mm:ss");
	
	/**
	 * ISO8601标准带时区的格式.时间格式： <tt>yyyy-MM-dd'T'HH:mm:ssZZ</tt>.
	 */
	public static final FastDateFormat ISO_DATETIME_TIME_ZONE_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd'T'HH:mm:ssZZ");
	
	/**
	 * ISO8601标准不带时区的格式时间格式： <tt>yyyy-MM-dd</tt>.
	 */
	public static final FastDateFormat ISO_DATE_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd");
	
	/**
	 * 标准带时区的格式.时间格式： <tt>yyyy-MM-ddZZ</tt>
	 */
	public static final FastDateFormat ISO_DATE_TIME_ZONE_FORMAT = FastDateFormat.getInstance("yyyy-MM-ddZZ");
	
	/**
	 * ISO8601 formatter for time without time zone.时间格式： <tt>'T'HH:mm:ss</tt>.
	 */
	public static final FastDateFormat ISO_TIME_FORMAT = FastDateFormat.getInstance("'T'HH:mm:ss");
	
	/**
	 * ISO8601 formatter for time with time zone.时间格式： <tt>'T'HH:mm:ssZZ</tt>.
	 */
	public static final FastDateFormat ISO_TIME_TIME_ZONE_FORMAT = FastDateFormat.getInstance("'T'HH:mm:ssZZ");
	
	/**
	 * ISO8601-like formatter for time without time zone.时间格式： <tt>HH:mm:ss</tt>. This pattern does not comply with the formal ISO8601
	 * specification as the standard requires the 'T' prefix for times.
	 */
	public static final FastDateFormat ISO_TIME_NO_T_FORMAT = FastDateFormat.getInstance("HH:mm:ss");
	
	/**
	 * ISO8601-like formatter for time with time zone.时间格式： <tt>HH:mm:ssZZ</tt>. This pattern does not comply with the formal ISO8601
	 * specification as the standard requires the 'T' prefix for times.
	 */
	public static final FastDateFormat ISO_TIME_NO_T_TIME_ZONE_FORMAT = FastDateFormat.getInstance("HH:mm:ssZZ");
	
	/**
	 * SMTP (and probably other) date headers.时间格式： <tt>EEE, dd MMM yyyy HH:mm:ss Z</tt> in US locale.
	 */
	public static final FastDateFormat SMTP_DATETIME_FORMAT = FastDateFormat.getInstance("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
	
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * DateFormatUtils instances should NOT be constructed in standard programming.
	 * </p>
	 * 
	 * <p>
	 * This constructor is public to permit tools that require a JavaBean instance to operate.
	 * </p>
	 */
	public DateFormatUtils() {
		super();
	}
	
	/**
	 * <p>
	 * Formats a date/time into a specific pattern using the UTC time zone.
	 * </p>
	 * 
	 * @param millis the date to format expressed in milliseconds
	 * @param pattern the pattern to use to format the date
	 * @return the formatted date
	 */
	public static String formatUTC(long millis, String pattern) {
		return format(new Date(millis), pattern, DateUtils.UTC_TIME_ZONE, null);
	}
	
	/**
	 * <p>
	 * Formats a date/time into a specific pattern using the UTC time zone.
	 * </p>
	 * 
	 * @param date the date to format
	 * @param pattern the pattern to use to format the date
	 * @return the formatted date
	 */
	public static String formatUTC(Date date, String pattern) {
		return format(date, pattern, DateUtils.UTC_TIME_ZONE, null);
	}
	
	/**
	 * <p>
	 * Formats a date/time into a specific pattern using the UTC time zone.
	 * </p>
	 * 
	 * @param millis the date to format expressed in milliseconds
	 * @param pattern the pattern to use to format the date
	 * @param locale the locale to use, may be <code>null</code>
	 * @return the formatted date
	 */
	public static String formatUTC(long millis, String pattern, Locale locale) {
		return format(new Date(millis), pattern, DateUtils.UTC_TIME_ZONE, locale);
	}
	
	/**
	 * <p>
	 * Formats a date/time into a specific pattern using the UTC time zone.
	 * </p>
	 * 
	 * @param date the date to format
	 * @param pattern the pattern to use to format the date
	 * @param locale the locale to use, may be <code>null</code>
	 * @return the formatted date
	 */
	public static String formatUTC(Date date, String pattern, Locale locale) {
		return format(date, pattern, DateUtils.UTC_TIME_ZONE, locale);
	}
	
	/**
	 * <p>
	 * Formats a date/time into a specific pattern.
	 * </p>
	 * 
	 * @param millis the date to format expressed in milliseconds
	 * @param pattern the pattern to use to format the date
	 * @return the formatted date
	 */
	public static String format(long millis, String pattern) {
		return format(new Date(millis), pattern, null, null);
	}
	
	/**
	 * <p>
	 * Formats a date/time into a specific pattern.
	 * </p>
	 * 
	 * @param date the date to format
	 * @param pattern the pattern to use to format the date
	 * @return the formatted date
	 */
	public static String format(Date date, String pattern) {
		return format(date, pattern, null, null);
	}
	
	/**
	 * <p>
	 * Formats a calendar into a specific pattern.
	 * </p>
	 * 
	 * @param calendar the calendar to format
	 * @param pattern the pattern to use to format the calendar
	 * @return the formatted calendar
	 * @see FastDateFormat#format(Calendar)
	 * @since 2.4
	 */
	public static String format(Calendar calendar, String pattern) {
		return format(calendar, pattern, null, null);
	}
	
	/**
	 * <p>
	 * Formats a date/time into a specific pattern in a time zone.
	 * </p>
	 * 
	 * @param millis the time expressed in milliseconds
	 * @param pattern the pattern to use to format the date
	 * @param timeZone the time zone to use, may be <code>null</code>
	 * @return the formatted date
	 */
	public static String format(long millis, String pattern, TimeZone timeZone) {
		return format(new Date(millis), pattern, timeZone, null);
	}
	
	/**
	 * <p>
	 * Formats a date/time into a specific pattern in a time zone.
	 * </p>
	 * 
	 * @param date the date to format
	 * @param pattern the pattern to use to format the date
	 * @param timeZone the time zone to use, may be <code>null</code>
	 * @return the formatted date
	 */
	public static String format(Date date, String pattern, TimeZone timeZone) {
		return format(date, pattern, timeZone, null);
	}
	
	/**
	 * <p>
	 * Formats a calendar into a specific pattern in a time zone.
	 * </p>
	 * 
	 * @param calendar the calendar to format
	 * @param pattern the pattern to use to format the calendar
	 * @param timeZone the time zone to use, may be <code>null</code>
	 * @return the formatted calendar
	 * @see FastDateFormat#format(Calendar)
	 * @since 2.4
	 */
	public static String format(Calendar calendar, String pattern, TimeZone timeZone) {
		return format(calendar, pattern, timeZone, null);
	}
	
	/**
	 * <p>
	 * Formats a date/time into a specific pattern in a locale.
	 * </p>
	 * 
	 * @param millis the date to format expressed in milliseconds
	 * @param pattern the pattern to use to format the date
	 * @param locale the locale to use, may be <code>null</code>
	 * @return the formatted date
	 */
	public static String format(long millis, String pattern, Locale locale) {
		return format(new Date(millis), pattern, null, locale);
	}
	
	/**
	 * <p>
	 * Formats a date/time into a specific pattern in a locale.
	 * </p>
	 * 
	 * @param date the date to format
	 * @param pattern the pattern to use to format the date
	 * @param locale the locale to use, may be <code>null</code>
	 * @return the formatted date
	 */
	public static String format(Date date, String pattern, Locale locale) {
		return format(date, pattern, null, locale);
	}
	
	/**
	 * <p>
	 * Formats a calendar into a specific pattern in a locale.
	 * </p>
	 * 
	 * @param calendar the calendar to format
	 * @param pattern the pattern to use to format the calendar
	 * @param locale the locale to use, may be <code>null</code>
	 * @return the formatted calendar
	 * @see FastDateFormat#format(Calendar)
	 * @since 2.4
	 */
	public static String format(Calendar calendar, String pattern, Locale locale) {
		return format(calendar, pattern, null, locale);
	}
	
	/**
	 * <p>
	 * Formats a date/time into a specific pattern in a time zone and locale.
	 * </p>
	 * 
	 * @param millis the date to format expressed in milliseconds
	 * @param pattern the pattern to use to format the date
	 * @param timeZone the time zone to use, may be <code>null</code>
	 * @param locale the locale to use, may be <code>null</code>
	 * @return the formatted date
	 */
	public static String format(long millis, String pattern, TimeZone timeZone, Locale locale) {
		return format(new Date(millis), pattern, timeZone, locale);
	}
	
	/**
	 * <p>
	 * Formats a date/time into a specific pattern in a time zone and locale.
	 * </p>
	 * 
	 * @param date the date to format
	 * @param pattern the pattern to use to format the date
	 * @param timeZone the time zone to use, may be <code>null</code>
	 * @param locale the locale to use, may be <code>null</code>
	 * @return the formatted date
	 */
	public static String format(Date date, String pattern, TimeZone timeZone, Locale locale) {
		FastDateFormat df = FastDateFormat.getInstance(pattern, timeZone, locale);
		return df.format(date);
	}
	
	/**
	 * <p>
	 * Formats a calendar into a specific pattern in a time zone and locale.
	 * </p>
	 * 
	 * @param calendar the calendar to format
	 * @param pattern the pattern to use to format the calendar
	 * @param timeZone the time zone to use, may be <code>null</code>
	 * @param locale the locale to use, may be <code>null</code>
	 * @return the formatted calendar
	 * @see FastDateFormat#format(Calendar)
	 * @since 2.4
	 */
	public static String format(Calendar calendar, String pattern, TimeZone timeZone, Locale locale) {
		FastDateFormat df = FastDateFormat.getInstance(pattern, timeZone, locale);
		return df.format(calendar);
	}
	
	/**
	 * 获取默认格式的当前时间字符串
	 * @author zhanhongkui 2012-3-3 上午10:34:52
	 * @return 时间字符串 yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
	 */
	public static String getDefaultCurrentDate() {
		return getCurrentDateFormat(DATE_FORMAT_DEFAULT);
	}
	
	/**
	 * 获取指定格式的当前时间字符串
	 * @author zhanhongkui 2012-3-3 上午10:35:18
	 * @param format 时间格式
	 * @return 时间字符串
	 */
	public static String getCurrentDateFormat(String format) {
		return format(Calendar.getInstance(), format);
	}
	
	/**
	 * 转换日期对象为时间字符串
	 * @author zhanhongkui 2012-3-3 下午03:34:15
	 * @param date
	 * @return 时间字符串 yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
	 */
	public static String defaultFormat(Date date) {
		return format(date, DATE_FORMAT_DEFAULT);
	}
	
	/**
	 * 转换日历对象为时间字符串，格式："yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
	 * @author zhanhongkui 2012-3-3 上午10:36:34
	 * @param calendar
	 * @return 时间字符串
	 */
	public static String defaultFormat(Calendar calendar) {
		return format(calendar, DATE_FORMAT_DEFAULT);
	}
	
	/**
	 * 转换long型日期为字符串
	 * @author zhanhongkui 2012-3-3 下午03:50:07
	 * @param timeInMillis
	 * @return
	 */
	public static String defaultFormat(long timeInMillis) {
		return format(timeInMillis, DATE_FORMAT_DEFAULT);
	}
	
	/**
	 * 转换long型的日期为日历对象
	 * @author zhanhongkui 2012-3-3 上午10:50:19
	 * @param timeInMillis
	 * @return
	 */
	public static Calendar toCalendar(long timeInMillis) {
		// 判断参数是否合法
		if (timeInMillis < 0)
			return null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeInMillis);
		return calendar;
	}
	
	/**
	 * 转换年、月、日、时、分、秒为日历对象
	 * @author zhanhongkui 2012-3-3 上午11:00:00
	 * @param year
	 * @param month
	 * @param date
	 * @param hourOfDay
	 * @param minute
	 * @param second
	 * @return
	 */
	public static Calendar toCalendar(int year, int month, int date, int hourOfDay, int minute, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, date, hourOfDay, minute, second);
		return calendar;
	}
	
	/**
	 * 获取当前整点时间
	 * @author zhanhongkui 2012-3-5 上午11:14:01
	 * @return
	 */
	public static Calendar getWholeHour() {
		return getWholeHour(Calendar.getInstance(), -1);
	}
	
	/**
	 * 获取当日指定小时的整点时间
	 * @author zhanhongkui 2012-3-5 上午11:14:28
	 * @param hour
	 * @return
	 */
	public static Calendar getWholeHour(int hour) {
		return getWholeHour(Calendar.getInstance(), hour);
	}
	
	/**
	 * 获取指定日期指定小时的整点时间
	 * @author zhanhongkui 2012-3-5 上午11:15:01
	 * @param calendar
	 * @param hour
	 * @return
	 */
	public static Calendar getWholeHour(Calendar calendar, int hour) {
		// 判断参数是否合法
		if (calendar == null) {
			return null;
		}
		calendar.clear(Calendar.SECOND);
		calendar.clear(Calendar.MINUTE);
		calendar.clear(Calendar.MILLISECOND);
		calendar.clear(Calendar.HOUR_OF_DAY);
		if (hour >= 0)
			calendar.set(Calendar.HOUR_OF_DAY, hour);
		return calendar;
	}
	
	/**
	 * 获取指定天数后的日历对象
	 * @author zhanhongkui 2012-3-5 上午11:41:45
	 * @param days
	 * @return
	 */
	public static Calendar afterDays(long days) {
		return afterMilliseconds(Calendar.getInstance(), days * 24 * 3600 * 1000);
	}
	
	/**
	 * 获取指定天数后的日历对象
	 * @author zhanhongkui 2012-3-5 上午11:41:45
	 * @param calendar
	 * @param days
	 * @return
	 */
	public static Calendar afterDays(Calendar calendar, long days) {
		return afterMilliseconds(calendar, days * 24 * 3600 * 1000);
	}
	
	/**
	 * 获取指定小时后的日历对象
	 * @author zhanhongkui 2012-3-5 上午11:41:54
	 * @param hours
	 * @return
	 */
	public static Calendar afterHours(long hours) {
		return afterMilliseconds(Calendar.getInstance(), hours * 3600 * 1000);
	}
	
	/**
	 * 获取指定小时后的日历对象
	 * @author zhanhongkui 2012-3-5 上午11:41:54
	 * @param calendar
	 * @param hours
	 * @return
	 */
	public static Calendar afterHours(Calendar calendar, long hours) {
		return afterMilliseconds(calendar, hours * 3600 * 1000);
	}
	
	/**
	 * 获取指定分钟后的日历对象
	 * @author zhanhongkui 2012-3-5 上午11:41:58
	 * @param minutes
	 * @return
	 */
	public static Calendar afterMinutes(long minutes) {
		return afterMilliseconds(Calendar.getInstance(), minutes * 60 * 1000);
	}
	
	/**
	 * 获取指定分钟后的日历对象
	 * @author zhanhongkui 2012-3-5 上午11:41:58
	 * @param calendar
	 * @param minutes
	 * @return
	 */
	public static Calendar afterMinutes(Calendar calendar, long minutes) {
		return afterMilliseconds(calendar, minutes * 60 * 1000);
	}
	
	/**
	 * 获取指定秒钟后的日历对象
	 * @author zhanhongkui 2012-3-5 上午11:42:02
	 * @param seconds
	 * @return
	 */
	public static Calendar afterSeconds(long seconds) {
		return afterMilliseconds(Calendar.getInstance(), seconds * 1000);
	}
	
	/**
	 * 获取指定秒钟后的日历对象
	 * @author zhanhongkui 2012-3-5 上午11:42:02
	 * @param calendar
	 * @param seconds
	 * @return
	 */
	public static Calendar afterSeconds(Calendar calendar, long seconds) {
		return afterMilliseconds(calendar, seconds * 1000);
	}
	
	/**
	 * 获取指定毫秒后的日历对象
	 * @author zhanhongkui 2012-3-5 上午11:42:07
	 * @param milliseconds
	 * @return
	 */
	public static Calendar afterMilliseconds(long milliseconds) {
		return afterMilliseconds(Calendar.getInstance(), milliseconds);
	}
	
	/**
	 * 获取指定毫秒后的日历对象
	 * @author zhanhongkui 2012-3-5 上午11:42:07
	 * @param calendar
	 * @param milliseconds
	 * @return
	 */
	public static Calendar afterMilliseconds(Calendar calendar, long milliseconds) {
		// 判断参数是否为空
		if (calendar == null) {
			return null;
		}
		calendar.setTimeInMillis(calendar.getTimeInMillis() + milliseconds);
		return calendar;
	}
	
	/**
	 * 获取指定年后的日历对象
	 * @author zhanhongkui 2012-3-5 下午12:00:40
	 * @param years
	 * @return
	 */
	public static Calendar afterYears(int years) {
		return afterYears(Calendar.getInstance(), years);
	}
	
	/**
	 * 获取指定年后的日历对象
	 * @author zhanhongkui 2012-3-5 下午12:00:45
	 * @param calendar
	 * @param years
	 * @return
	 */
	public static Calendar afterYears(Calendar calendar, int years) {
		// 判断参数是否为空
		if (calendar == null) {
			return null;
		}
		calendar.add(Calendar.YEAR, years);
		return calendar;
	}
	
	/**
	 * 获取指定月后的日历对象
	 * @author zhanhongkui 2012-3-5 下午12:00:50
	 * @param months
	 * @return
	 */
	public static Calendar afterMonths(int months) {
		return afterMonths(Calendar.getInstance(), months);
	}
	
	/**
	 * 获取指定月后的日历对象
	 * @author zhanhongkui 2012-3-5 下午12:00:54
	 * @param calendar
	 * @param months
	 * @return
	 */
	public static Calendar afterMonths(Calendar calendar, int months) {
		// 判断参数是否为空
		if (calendar == null) {
			return null;
		}
		calendar.add(Calendar.MONTH, months);
		return calendar;
	}
	
	/**
	 * 获取当日的零时的日历对象
	 * @author zhanhongkui 2011-12-8 上午09:22:23
	 * @return
	 */
	public static Calendar getZeroTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}
	
	/**
	 * 获取当日的零时的毫秒值
	 * @author zhanhongkui 2011-12-8 上午09:22:58
	 * @return
	 */
	public static long getZeroTimeInMillis() {
		return getZeroTime().getTimeInMillis();
	}
	
	/**
	 * 转换字符串为日期对象
	 * @author zhanhongkui 2012-3-3 下午02:28:34
	 * @param dateStr
	 * @return
	 * @throws Exception
	 */
	public static Date toDate(String dateStr) throws Exception {
		return toDate(dateStr, DATE_FORMAT_DEFAULT);
	}
	
	/**
	 * 转换字符串为日期对象
	 * @author zhanhongkui 2012-3-3 下午02:29:04
	 * @param dateStr
	 * @param format
	 * @return
	 * @throws Exception
	 */
	public static Date toDate(String dateStr, String format) throws Exception {
		if (StringUtils.isEmpty(dateStr) || StringUtils.isEmpty(format)) {
			return null;
		}
		Date date = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat(format);
			date = dateFormat.parse(dateStr);
		} catch (Exception e) {
			throw new Exception("date string [" + dateStr + "] is a wrong format for [" + format + "]", e);
		}
		return date;
	}
	
	/**
	 * 转换日期Long值（毫秒为单位）为日期对象，输入必须是非负树，否则返回空
	 * @author zhanhongkui 2012-3-3 下午02:54:44
	 * @param timeInMillis 用毫秒数表示的绝对时间
	 * @return 如果输入值小于0，则返回null
	 */
	public static Date toDate(long timeInMillis) {
		if (timeInMillis < 1)
			return null;
		return new Date(timeInMillis);
	}
}
