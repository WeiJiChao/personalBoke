package com.jwBlog.utils.dp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtils {

	/**
	 * 转换当前日期为指定日期格式
	 * @param strFormat － 日期格式
	 * @return 指定日期格式日期
	 */
	public static String toString(String strFormat) {
		SimpleDateFormat SDateFmt = new SimpleDateFormat(strFormat);
		SDateFmt.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
		return SDateFmt.format(new Date());
	}

	/**
	 * 转换当前日期为标准日期时间格式
	 * @return 标准日期时间格式日期
	 */
	public static String toStandar() {
		Date nowDate = new Date();
		return DateUtils.toString("yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 当前日期
	 * @return 标准日期时间格式日期
	 */
	public static Date getNow() {
		Date nowDate = new Date();
		return nowDate;
	}
	/**
	 * 将字符串日期转换为Date
	 *
	 * @param s
	 * @return
	 */
	public static Date convertToDateByLong(Long s) {
		if (s == null || s.equals("")) {
			return null;
		}
		try {
			return new Date(s*1000);
		} catch (Exception e) {
			return null;
		}
	}
	public static Date convertToDateByLong(String s) {
		if (s == null || s.equals("")) {
			return null;
		}
		try {
			return new Date(Long.parseLong(s)*1000);
		} catch (Exception e) {
			return null;
		}
	}
	public static Date convertToDateByLong(Object s) {
		if (s == null || s.equals("")) {
			return null;
		}
		try {
			return new Date(Long.parseLong(s.toString())*1000);
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * 将字符串日期转换为Date
	 * 
	 * @param s
	 * @return
	 */
	public static Date convertToDate(String s) {
		DateFormat df;
		if (s == null || s.equals("")) {
			return null;
		}
		if (s.contains(":")) {
			try {
				df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				return df.parse(s);
			} catch (Exception e) {
				return null;
			}
		} else {
			try {
				df = new SimpleDateFormat("yyyy-MM-dd");
				return df.parse(s);
			} catch (Exception e) {
				return null;
			}
		}
	}
	public static Date convertLongToDate(String longDt) {
		Date dt=new Date();
		dt.setTime(Long.valueOf(longDt));
		return dt;
	}
	
	public static Date convertToDate(String s,String format) {
		DateFormat df;
		if (s == null) {
			return null;
		}
		
			try {
				df = new SimpleDateFormat(format);
				return df.parse(s);
			} catch (Exception e) {
				return null;
			}
		
	}
	/**
	 * 算出两个日期中所包含的月份
	 * 
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public static Set<String> getMonthBetweenTwoDate(Date fromDate, Date toDate) {
		long begin = fromDate.getTime();
		long end = toDate.getTime();
		long inter = end - begin;
		if (inter < 0) {
			inter = inter * (-1);
		}
		long dateMillSec = 86400000;
		long dateCnt = inter / dateMillSec;
		long remainder = inter % dateMillSec;
		if (remainder != 0) {
			dateCnt++;
		}
		Set<String> set = new LinkedHashSet<String>();
		Calendar cl = Calendar.getInstance();
		cl.setTime(fromDate);
		cl.set(Calendar.HOUR_OF_DAY, 0);
		cl.set(Calendar.MINUTE, 0);
		cl.set(Calendar.SECOND, 0);
		cl.set(Calendar.MILLISECOND, 0);
		set.add(getDateyyyyMM(cl.getTime()));
		for (int i = 1; i <= dateCnt; i++) {
			cl.add(Calendar.DAY_OF_YEAR, 1);
			set.add(getDateyyyyMM(cl.getTime()));
		}
		return set;
	}

	/**
	 * 算出两个日期中所包含的月份
	 * 
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public static long getDayBetweenTwoDate(Date fromDate, Date toDate) {
		long begin = fromDate.getTime();
		long end = toDate.getTime();
		long inter = end - begin;
		if (inter < 0) {
			inter = inter * (-1);
		}
		long dateMillSec = 86400000;
		long dateCnt = inter / dateMillSec;
		long remainder = inter % dateMillSec;
		if (remainder != 0) {
			dateCnt++;
		}		
		return dateCnt;
	}
	/**
	 * 算出两个日期中所包含的年份
	 * 
	 * @param fromDate
	 * @return
	 */
	public static String getYearBetweenTwoDate(Date fromDate) {
		Date systemDate = new Date();
		int date1 = Integer.parseInt(DateUtils.getDateyyyy(fromDate));
		int date2 = Integer.parseInt(DateUtils.getDateyyyy(systemDate));
		int date = date2 - date1;
		return String.valueOf(date);
	}

	/**
	 * 得到yyyyMM的年月
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateyyyyMM(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
		return df.format(date);
	}

	/**
	 * 得到yyyyMMdd的年月日
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateyyyyMMdd(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		return df.format(date);
	}
	/**
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getDateyyyyMMdd(Date date,String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}

	/**
	 * 把日期格式化为日期字符串型
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            日期格式，如"yyyy-MM-dd HH:mm:ss"格式
	 * @return
	 */
	public static String formatDate(Date date, String format) {
		try {
			if (format != null && !"".equals(format) && date != null) {
				SimpleDateFormat formatter = new SimpleDateFormat(format);
				return formatter.format(date);
			}
		} catch (Exception e) {
			return "";
		}
		return "";
	}

	/**
	 * 得到yyyy的年
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateyyyy(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy");
		return df.format(date);
	}
	/**
	 * 得到几小时之前
	 * 
	 * @param hour
	 * @return
	 */
	public static Date getBeforeHour(int hour) {
		Calendar cal = Calendar.getInstance(Locale.CHINA);	
		cal.set(Calendar.SECOND,-hour*60*60);
		return cal.getTime();
	}
	/**
	 * 得到几分钟之前
	 *
	 * @param time
	 * @return
	 */
	public static Date getBeforeTime(int time) {
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.set(Calendar.SECOND,-time*60);
		return cal.getTime();
	}
	/**
	 * 得到昨天
	 *
	 * @return
	 */
	public static Date getYesterday() {
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}
	/**
	 * 得到明天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getTomorrowday(Date date) {
		if(date==null){
			return null;
		}
		Calendar cal= Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}
	/**
	 * 得到几天之后的某天
	 * @param date
	 * @return
	 */
	public static Date getDayAfter(Date date,int dayafter ) {
		if(date==null){
			return null;
		}
		Calendar cal= Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.DAY_OF_MONTH, dayafter);
		return cal.getTime();
	}
	/**
	 * 前几天
	 * @param date
	 * @param dayafter
	 * @return
	 */
	public static Date getDayPrio(Date date,int dayafter ) {
		if(date==null){
			return null;
		}
		Calendar cal= Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.DAY_OF_MONTH, - dayafter);
		return cal.getTime();
	}
	/**
	 * 得到一个月之前的那天
	 * @param date
	 * @return
	 */
	public static Date getPrioMonthDay(Date date) {
		if(date==null){
			return null;
		}
		Calendar cal= Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.MONTH, -1);
		return cal.getTime();
	}
	/**
	 * 得到一个月之前的那天
	 * @param date
	 * @return
	 */
	public static Date getPrioMonthDay(Date date,int month) {
		if(date==null){
			return null;
		}
		Calendar cal= Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.MONTH, - month);
		return cal.getTime();
	}
	
	public static Boolean comparedate(Date date1,Date date2){
		return date1.after(date2);
	}
	
	
	/**
	 * 得到两个日期差，并转化为**年**月**日格式返回
	 * @param date1
	 * @param date2
	 * @param date2
	 * @return
	 */
	public static String getsubtimetoString(Date date1,Date date2,String strtype){
		Date d1=null,d2=null;//小   大
		if(comparedate(date1,date2)){
			d1=date2;
			d2=date1;
		}else{
			d1=date1;
			d2=date2;	
		}
		
		if(strtype=="d"){
	    	return Math.ceil((d2.getTime()-d1.getTime())/(1000*3600*24))+"天";
	    }else if(strtype=="y"){
	    	return Math.ceil((d2.getTime()-d1.getTime())/(1000*3600*24*365))+"年";
	    }else if(strtype=="h"){
	    	return Math.ceil((d2.getTime()-d1.getTime())/(1000*3600))+"小时";
	    }else if(strtype=="m"){
	    	return Math.ceil((d2.getTime()-d1.getTime())/(1000*60))+"分";
	    }else if(strtype=="all"){
	    	Integer year=DoubleUtils.round(((d2.getTime()-d1.getTime())/((long)1000*3600*24*365)),0,0).intValue();
	    	Integer day=DoubleUtils.round(((d2.getTime()-d1.getTime()-year*(long)1000*3600*24*365  )/((long)1000*3600*24)),0,0).intValue();;
	    	Integer h=DoubleUtils.round(((d2.getTime()-d1.getTime()-year*(long)1000*3600*24*365-day*(long)1000*3600*24)/((long)1000*3600)),0,0).intValue();;
	    	Integer m=DoubleUtils.round(((d2.getTime()-d1.getTime()-year*(long)1000*3600*24*365-day*(long)1000*3600*24-h*(long)1000*3600)/((long)1000*60)),0,0).intValue();;
	    	String rtn="";
	    	if(year!=0){
	    		rtn=year+"年";
	    	}
	    	if(day!=0){
	    		rtn+=day+"天";
	    	}
	    	if(h!=0){
	    		rtn+=h+"小时";
	    	}
	    	if(m!=0){
	    		rtn+=m+"分";
	    	}
	    	return rtn;
	    }
		return "";
	}	
	public static String getsubtimetoString(String date1,String date2,String strtype){
		Date date11= DateUtils.convertToDate(date1,"yyyy-MM-dd HH:mm:ss");
		Date date21= DateUtils.convertToDate(date2,"yyyy-MM-dd HH:mm:ss");
		return getsubtimetoString(date11,date21,strtype);
	}
	public static String getsubtimetoString(String date1,Date date2,String strtype){
		Date date11= DateUtils.convertToDate(date1,"yyyy-MM-dd HH:mm:ss");
		return getsubtimetoString(date11,date2,strtype);
	}
	
	//将日期字符串转换成固定格式
	public static String getDateToString(String str1,String str2){
		SimpleDateFormat df = new SimpleDateFormat(str2);
		try {
			return df.format(df.parse(str1));
		} catch (ParseException e) {
		}
		return "";
	}
	
	
	/**
	 * 计算两个日期之间相差的天数
	 * @param smdate 较小的时间
	 * @param bdate  较大的时间
	 * @return 相差天数
	 */
	public static int daysBetween(Date smdate,Date bdate)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days=(time2-time1)/(1000*3600*24);

		return Integer.parseInt(String.valueOf(between_days));
	}
}
