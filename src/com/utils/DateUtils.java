package com.utils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * 定义日期类型转换基类
 */
public class DateUtils {

	// 日期转换类型>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	private static final String DATA_YYY_MM_DD = "yyyy-MM-dd";
	public static final String DATA_YYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	private static SimpleDateFormat formatter = null;

	/**
	 * 方法用法: 将日期类型转按指定的格式换为字符串 默认为: yyyy-mm-dd || yyyy-mm-dd hh:mm:ss
	 * 参数说明:
	 * 
	 * @param aDate
	 *            要转换的日期
	 * 
	 * @return 返回转换后的字符串
	 */
	public static final String convertDateToString(Date aDate) {
		return getStringTime(DATA_YYY_MM_DD, aDate);
	}

	public static final String convertDateTimeToString(Date aDate) {
		return getStringTime(DATA_YYY_MM_DD_HH_MM_SS, aDate);
	}

	/**
	 * 方法用法: 将字符串类型转按指定的格式换为日期 默认为: yyyy-mm-dd || yyyy-mm-dd hh:mm:ss
	 * 参数说明:
	 * 
	 * @param strDate
	 *            要转换的字符串
	 * 
	 * @return 返回转换后的日期
	 */
	public static final Date convertStringToDate(String strDate) {
		return getDateTime(DATA_YYY_MM_DD, strDate);
	}

	public static final Date convertStringToDateTime(String strDate) {
		return getDateTime(DATA_YYY_MM_DD_HH_MM_SS, strDate);
	}

	/**
	 * 
	 * 
	 * 参数作用：aMask 这个参数 是一个时间格式,默认为"MM/dd/yyyy" aDate 这个参数 是用户自己给定的。是一个日期.
	 * 
	 * 方法用法：传第一个日期时间格式，各第二个具体时间，对这个时间格式化成第一个参数的样子 作用:格式化时间.
	 * 
	 * @param aMask
	 *            the date pattern the String is in
	 * 
	 * @param aDate
	 *            a date Object
	 * 
	 * @return a formatted String representation of the date
	 * 
	 * @see java.text.SimpleDateFormat
	 */
	public static final String getStringTime(String aMask, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate == null) {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(new Date());
		} else {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * 
	 * 参数作用：aMask 这个参数 是一个时间格式,默认为"MM/dd/yyyy" aDate 这个参数 是用户自己给定的。是一个字符串.
	 * 
	 * 方法用法：传第一个日期时间格式，各第二个具体字符串，对这个时间格式化成第一个参数的样子 作用:字符串.
	 * 
	 * @param aMask
	 *            the date pattern the String is in
	 * 
	 * @param strDate
	 *            a String Object
	 * 
	 * @return a formatted String representation of the date
	 * 
	 * @see java.text.SimpleDateFormat
	 */
	public static Date getDateTime(String aMask, String strDate) {
		if (strDate != null || strDate != "") {
			// json 字符串
			if(strDate.startsWith("{")){
				String[] msgs = strDate.split(",");
				long time = -1;
				for(String item : msgs){
					String[] keyValue = item.split("=");
					if(keyValue[0].toLowerCase().trim().equals("time")){
						time = Long.parseLong(keyValue[1]);
						break;
					}
				}
				if(time!=-1){
					return new Date(time);
				}
			}else{
				try {
					formatter = new SimpleDateFormat(aMask);
					Date dt = formatter.parse(strDate);
					return dt;
				} catch (ParseException e) {
					e.printStackTrace();
					throw new RuntimeException(e.getMessage());
				}
			}
		}
		return new Date();
	}

	public static Date getDateFormat(String aMask, Date date) {
		try {
			if (date != null && date.toString().length() > 0) {
				formatter = new SimpleDateFormat(aMask);
				System.out
						.println(">:" + formatter.parse(String.valueOf(date)));
				return formatter.parse(String.valueOf(date));
			}
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return new Date();
	}

	/**
	 * This method returns the current date in the format: MM/dd/yyyy
	 * 
	 * @return the current date
	 * @throws ParseException
	 */
	public static Calendar getToday() throws ParseException {
		Date today = new Date();
		SimpleDateFormat df = new SimpleDateFormat(DATA_YYY_MM_DD);
		String todayAsString = df.format(today);
		Calendar cal = new GregorianCalendar();
		cal.setTime(convertStringToDate(todayAsString));
		return cal;
	}

	public static Date getBeforeDay(int days) throws ParseException {
		Calendar cal = new GregorianCalendar();
		cal.add(Calendar.DATE, -days);
		return cal.getTime();
	}

	public static Date getAfterDay(int days) throws ParseException {
		Calendar cal = new GregorianCalendar();
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}
	
	private StringBuffer buffer = new StringBuffer();
	private static String ZERO = "0";
	private static DateUtils date;
	public static SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
	public static SimpleDateFormat format1 = new SimpleDateFormat(
			"yyyyMMdd HH:mm:ss");

	public String getNowString() {
		Calendar calendar = getCalendar();
		buffer.delete(0, buffer.capacity());
		buffer.append(getYear(calendar));

		if (getMonth(calendar) < 10) {
			buffer.append(ZERO);
		}
		buffer.append(getMonth(calendar));

		if (getDate(calendar) < 10) {
			buffer.append(ZERO);
		}
		buffer.append(getDate(calendar));
		if (getHour(calendar) < 10) {
			buffer.append(ZERO);
		}
		buffer.append(getHour(calendar));
		if (getMinute(calendar) < 10) {
			buffer.append(ZERO);
		}
		buffer.append(getMinute(calendar));
		if (getSecond(calendar) < 10) {
			buffer.append(ZERO);
		}
		buffer.append(getSecond(calendar));
		return buffer.toString();
	}

	private static int getDateField(Date date,int field) {
		Calendar c = getCalendar();
		c.setTime(date);
		return c.get(field);
	}
	public static int getYearsBetweenDate(Date begin,Date end){
		int bYear=getDateField(begin,Calendar.YEAR);
		int eYear=getDateField(end,Calendar.YEAR);
		return eYear-bYear;
	}
	
	public static int getMonthsBetweenDate(Date begin,Date end){
		int bMonth=getDateField(begin,Calendar.MONTH);
		int eMonth=getDateField(end,Calendar.MONTH);
		return eMonth-bMonth;
	}
	public static int getWeeksBetweenDate(Date begin,Date end){
		int bWeek=getDateField(begin,Calendar.WEEK_OF_YEAR);
		int eWeek=getDateField(end,Calendar.WEEK_OF_YEAR);
		return eWeek-bWeek;
	}
	public static int getDaysBetweenDate(Date begin,Date end){
		int bDay=getDateField(begin,Calendar.DAY_OF_YEAR);
		int eDay=getDateField(end,Calendar.DAY_OF_YEAR);
		return eDay-bDay;
	}
	
	
	public static void main(String args[]){
		System.out.println(getSpecficDateStart(new Date(), 288));
	}

	/**
	 * 获取date年后的amount年的第一天的开始时间
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficYearStart(Date date,int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, amount);
		cal.set(Calendar.DAY_OF_YEAR, 1);
		return getStartDate(cal.getTime());
	}

	/**
	 * 获取date年后的amount年的最后一天的终止时间
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficYearEnd(Date date,int amount) {
		Date temp = getStartDate(getSpecficYearStart(date,amount + 1));
		Calendar cal = Calendar.getInstance();
		cal.setTime(temp);
		cal.add(Calendar.DAY_OF_YEAR, -1);
		return getFinallyDate(cal.getTime());
	}

	/**
	 * 获取date月后的amount月的第一天的开始时间
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficMonthStart(Date date,int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, amount);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return getStartDate(cal.getTime());
	}

	/**
	 * 获取当前自然月后的amount月的最后一天的终止时间
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficMonthEnd(Date date,int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getSpecficMonthStart(date,amount + 1));
		cal.add(Calendar.DAY_OF_YEAR, -1);
		return getFinallyDate(cal.getTime());
	}

	/**
	 * 获取date周后的第amount周的开始时间（这里星期一为一周的开始）
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficWeekStart(Date date,int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.setFirstDayOfWeek(Calendar.MONDAY); /* 设置一周的第一天为星期一 */
		cal.add(Calendar.WEEK_OF_MONTH, amount);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return getStartDate(cal.getTime());
	}

	/**
	 * 获取date周后的第amount周的最后时间（这里星期日为一周的最后一天）
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficWeekEnd(Date date,int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY); /* 设置一周的第一天为星期一 */
		cal.add(Calendar.WEEK_OF_MONTH, amount);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return getFinallyDate(cal.getTime());
	}
	
	public static Date getSpecficDateStart(Date date,int amount){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, amount);
		return getStartDate(cal.getTime());
	}

	/**
	 * 得到指定日期的一天的的最后时刻23:59:59
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFinallyDate(Date date) {
		String temp = format.format(date);
		temp += " 23:59:59";

		try {
			return format1.parse(temp);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 得到指定日期的一天的开始时刻00:00:00
	 * 
	 * @param date
	 * @return
	 */
	public static Date getStartDate(Date date) {
		String temp = format.format(date);
		temp += " 00:00:00";

		try {
			return format1.parse(temp);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 
	 * @param date 指定比较日期
	 * @param compareDate 
	 * @return
	 */
	public static boolean isInDate(Date date,Date compareDate){
		if(compareDate.after(getStartDate(date))&&compareDate.before(getFinallyDate(date))){
			return true;
		}else{
			return false;
		}
	}

	private int getYear(Calendar calendar) {
		return calendar.get(Calendar.YEAR);
	}

	private int getMonth(Calendar calendar) {
		return calendar.get(Calendar.MONDAY) + 1;
	}

	private int getDate(Calendar calendar) {
		return calendar.get(Calendar.DATE);
	}

	private int getHour(Calendar calendar) {
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	private int getMinute(Calendar calendar) {
		return calendar.get(Calendar.MINUTE);
	}

	private int getSecond(Calendar calendar) {
		return calendar.get(Calendar.SECOND);
	}

	private static Calendar getCalendar() {
		return Calendar.getInstance();
	}

	public static DateUtils getDateInstance() {
		if (date == null) {
			date = new DateUtils();
		}
		return date;
	}
}
