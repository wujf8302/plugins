package com.plugin.api;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.StringTokenizer;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
/**
 * String Date Calendar之间的转换
 * 
1.Calendar 转化 String
Calendar calendat = Calendar.getInstance();
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
String dateStr = sdf.format(calendar.getTime());
 
2.String 转化Calendar
String str="2012-5-27";
SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
Date date =sdf.parse(str);
Calendar calendar = Calendar.getInstance();
calendar.setTime(date);
 
3.Date 转化String
SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
String dateStr=sdf.format(new Date());
 
4.String 转化Date
String str="2012-5-27";
SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
Date date= sdf.parse(str);
 
5.Date 转化Calendar
Calendar calendar = Calendar.getInstance();
calendar.setTime(new java.util.Date());
 
6.Calendar转化Date
Calendar calendar = Calendar.getInstance();
java.util.Date date =calendar.getTime();
 
7.String 转成 Timestamp
Timestamp ts = Timestamp.valueOf("2012-1-14 08:11:00");
 
8.Date 转 TimeStamp
SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
String time = df.format(new Date());
Timestamp ts = Timestamp.valueOf(time);
 * @author wujf
 *
 */
public class DateUtil {
    
    private static Logger log = Logger.getLogger(DateUtil.class);
    public static final String DEFAULT_DATE_FORMATE = "yyyy-MM-dd";
    public static final String DEFAULT_TIME_FORMATE = "HH:mm:ss";
    public static final String DEFAULT_DATETIME_FORMATE = "yyyy-MM-dd HH:mm:ss";
   
    //---
    public static final SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATETIME_FORMATE);
    //数据库yyyy-MM-dd HH24:mi:ss   to_date('" + ACC_DATE + "', 'yyyy-mm-dd hh24:mi:ss')
    
    //---
    public static final DateFormat longFormat = new SimpleDateFormat(DEFAULT_DATETIME_FORMATE);
    public static final DateFormat shortFormat = new SimpleDateFormat(DEFAULT_DATE_FORMATE);
    
    public static Date getSystemTime(){
    	return new Date();
    }
    
    /**
     * getDateForTimeZone("GMT+8:00","yyyy-MM-dd HH:mm:ss")
     * @param _timeZone GMT+8:00  中国一般使用东八区，因此timeZoneOffset就是8
     * @param format  yyyy-MM-dd HH:mm:ss
     */
	 public static String getDateForTimeZone(String _timeZone,String format){
	     TimeZone timeZone = null;
	     if(StringUtils.isEmpty(_timeZone)){
	         timeZone = TimeZone.getDefault();
	     }else{
	         timeZone = TimeZone.getTimeZone(_timeZone);
	     }
	   
	     SimpleDateFormat sdf = new SimpleDateFormat(format);
	     sdf.setTimeZone(timeZone);
	     //TimeZone.setDefault(timeZone);
	     return sdf.format(new Date());
	}
	 /**
     * getDateForTimeZone(8,"yyyy-MM-dd HH:mm:ss")
     * @param _timeZone 8  中国一般使用东八区，因此timeZoneOffset就是8
     * @param format  yyyy-MM-dd HH:mm:ss
     */
	public static String getDateForTimeZone(int timeZoneOffset,String format){
	     if (timeZoneOffset > 13 || timeZoneOffset < -12) {
	         timeZoneOffset = 0;
	     }
	     TimeZone timeZone;
	     String[] ids = TimeZone.getAvailableIDs(timeZoneOffset * 60 * 60 * 1000);
	     if (ids.length == 0) {
	         // if no ids were returned, something is wrong. use default TimeZone
	         timeZone = TimeZone.getDefault();
	     } else {
	         timeZone = new SimpleTimeZone(timeZoneOffset * 60 * 60 * 1000, ids[0]);
	     }
	  
	     SimpleDateFormat sdf = new SimpleDateFormat(format);
	     sdf.setTimeZone(timeZone);
	     return sdf.format(new Date());
	 }
    
    /**
     * 日期转化成YYYY-MM-dd HH:mm:ss格式的字符串
     * @param date
     * @return 
     */
    public static String formatNormal(Date date) {
        if(date == null) {
            return null;
        }
        return longFormat.format(date);
    }
    
    /**
     * 日期转化成YYYY-MM-dd格式的字符串
     * @param date
     * @return 
     */
    public static String formatShort(Date date) {
        if(date == null) {
            return null;
        }
        return shortFormat.format(date);
    }
    
    /**
     * YYYY-MM-dd HH:mm:ss格式的字符串转换成日期
     * @param date
     * @return 
     */
    public static Date parseNormal(String date) throws ParseException {
        if(date == null) {
            return null;
        }
        return longFormat.parse(date);
    }
    
    /**
     * YYYY-MM-dd格式的字符串转换成日期
     * @param date
     * @return 
     */
    public static Date parseShort(String date) throws ParseException {
        if(date == null) {
            return null;
        }
        return shortFormat.parse(date);
    }
    //------------------------------------------------------------
    
    public static String getTimeString() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
    
    /*
     * Calendar calendar = Calendar.getInstance();
     * calendar.setTime(TimeStampDate);
     * calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),
     * calendar.get(Calendar.DAY_OF_MONTH)+1);
     */
    public static java.sql.Date utilDate2SqlDate(String s, String pattern) {
        java.sql.Date date = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            date = new java.sql.Date(format.parse(s).getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }
    
    public static java.util.Date sqlDate2UtilDate(java.sql.Date date) {
        return date;
    }
    
    public static String formatSqlDate(java.sql.Date date, String pattern) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            return format.format(new Date(date.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
    public static String formatTimestamp(java.sql.Timestamp date, String pattern) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            return format.format(new Date(date.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
    public static void main(String[] args) {
        java.sql.Date date = utilDate2SqlDate("20101116155442",
            "yyyyMMddHHmmss");
        java.util.Date d = sqlDate2UtilDate(date);
        System.out.println(format.format(d));
    }
    
    public static java.sql.Date parse(String s) {
        java.sql.Date date = null;
        try {
            date = java.sql.Date.valueOf(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }
    
    /**
     * 计算给定时间至今的天数
     * @param date给定的时间
     * @return 给定时间至今的天数
     */
    public static long date2day(String date) {
        long day = 0;
        DateFormat df = DateFormat.getDateInstance();
        try {
            long old = df.parse(date).getTime();
            long now = new java.util.Date().getTime();
            long secs = now - old;
            day = secs / (1000 * 24 * 60 * 60);
        } catch (ParseException e) {
            log.error("",e);
        }
        return day;
    }
    
    /**
     * 格式化给定时间后day天的时间
     * @param date 需要格式化的时间
     * @param day  增加的天数
     * @return 给定时间的后day天的格式化字符串(如：2008-11-22)
     */
    public static String formatDate(java.util.Date date, Integer day) {
        String str = "";
        str = new Date(date.getTime() + day * 24 * 60 * 60).toString();
        return str;
    }
    
    /**
     * 格式化给定时间.
     * @param date 需要格式化的时间
     * @return 给定时间的格式化字符串(如：2008-11-22)
     */
    public static String formatDate(java.util.Date date) {
        return new Date(date.getTime()).toString();
    }
    
    public static String currentDate(){
        return formatDate(new Date(),DEFAULT_DATE_FORMATE);
    }
    public static String currentTime(){
        return formatDate(new Date(),DEFAULT_TIME_FORMATE);
    }
    public static String currentDateTime(){
        return formatDate(new Date(),DEFAULT_DATETIME_FORMATE);
    }
    
    public static String formatDate(long longTime){
        return formatDate(longTime,DEFAULT_DATETIME_FORMATE);
    }
    public static String formatDate(long longTime,String formatstr){
    	String date = "";
    	SimpleDateFormat format = new SimpleDateFormat(formatstr);
        try {
        	date = format.format(new Date(longTime));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return date;
    }
    public static Long date2Long(Date date){
        try {
        	return date.getTime();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }
    
    /**
     * 得到当前年
     * @return 返回当前年(YYYY)
     */
    public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }
    
    /**
     * 得到当前月
     * @return 返回当前月(1~12)
     */
    public static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }
    public static int getMonth(String dateStr,String pattern) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateUtil.parseDate(dateStr,pattern));
        return calendar.get(Calendar.MONTH) + 1;
    }
    
    /**
     * 得到当前日
     * @return 返回当前日(1~31)
     */
    public static int getDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }
    
    /**
     * 得到当前年
     * @return 返回当前年(YY)
     */
    public static String getYear2() {
        int year = getYear();
        return StringUtil.integer2String(year, "1986").substring(2, 4);
    }
    
    /**
     * 得到当前月
     * @return 返回当前月(01~12)
     */
    public static String getMonth2() {
        int month = getMonth();
        if (month < 10) {
            return "0" + StringUtil.integer2String(month, "00");
        }
        return StringUtil.integer2String(month, "00");
    }
    
    /**
     * 得到当前日
     * @return 返回当前日(01~31)
     */
    public static String getDay2() {
        int day = getDay();
        if (day < 10) {
            return "0" + StringUtil.integer2String(day, "00");
        }
        return StringUtil.integer2String(day, "00");
    }
    
    /**
     * 得到指定格式的当前时间
     * @param format
     *            格式化形式(年用YY/YYYY表示；月用M/MM表示;日用D/DD表示,之间任意任序组合),<br />
     *            如"YYYY-MM-DD"返回形如：1986-12-17<br />
     *            如"YY-MM"返回形如：86-12<br />
     *            如"YY年MM月"返回形如：86年12月……
     * @return 返回指定格式的当前时间
     */
    public static String getDate(String format) {
        format = format.replace("YYYY", getYear() + "");
        format = format.replace("YY", getYear2());
        format = format.replace("MM", getMonth2());
        format = format.replace("M", getMonth() + "");
        format = format.replace("DD", getDay2());
        format = format.replace("D", getDay() + "");
        return format;
    }
    
    /**
     * 计算详细年龄.
     * @param birthdayStr 出生日期 格式"YYYY-MM-DD"
     * @return 指定日期至今天的年龄
     */
    public static String countAge(String birthdayStr) {
        String age = "";
        if (birthdayStr != null && birthdayStr.length() == 8) {
            java.util.Date birthday = parseDate(birthdayStr, "YYYY-MM-DD");
            
            if (birthday != null) {
                Calendar calendar = Calendar.getInstance();
                int year1 = getYear();
                int month1 = StringUtil.string2Integer(getMonth2(), 0);
                int day1 = StringUtil.string2Integer(getDay2(), 00);
                
                calendar.setTime(birthday);
                int year2 = calendar.get(Calendar.YEAR);
                int month2 = calendar.get(Calendar.MONTH) + 1;
                int day2 = calendar.get(Calendar.DATE);
                
                int year = year1 - year2;
                int month = month1 - month2;
                int day = day1 - day2;
                
                if (month < 0) {
                    year = year - 1;
                    month = 12 + month1 - month2;
                }
                
                if (day < 0) {
                    month = month - 1;
                    if (month < 0) {
                        year = year - 1;
                        month = 11;
                    }
                    int lastMonthDay = 0;
                    if (month1 == 0) {
                        lastMonthDay = getDayOfMonth(12, year1 - 1);
                    } else {
                        lastMonthDay = getDayOfMonth(month1, year1);
                    }
                    day = lastMonthDay + day1 - day2;
                }
                
                if (year > 5) {
                    age = year + "岁";
                } else if (year > 0) {
                    if (month == 0) {
                        age = year + "岁";
                    } else {
                        age = year + "岁" + month + "月";
                    }
                } else {
                    if (month > 5) {
                        age = month + "月";
                    } else if (month > 0) {
                        age = month + "月" + day + "天";
                    } else {
                        age = day + "天";
                    }
                }
            }
        }
        
        return age;
    }
    
    /**
     * 得到指定年月的天数
     * @param month 指定月份
     * @param year  指定的年份
     * @return 天数
     */
    public static int getDayOfMonth(int month, int year) {
        int ret = 0;
        boolean flag = false;
        
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            flag = true;
        }
        
        switch (month) {
            case 1:
                ret = 31;
                break;
            case 2:
                if (flag) {
                    ret = 29;
                } else {
                    ret = 28;
                }
                break;
            case 3:
                ret = 31;
                break;
            case 4:
                ret = 30;
                break;
            case 5:
                ret = 31;
                break;
            case 6:
                ret = 30;
                break;
            case 7:
                ret = 31;
                break;
            case 8:
                ret = 31;
                break;
            case 9:
                ret = 30;
                break;
            case 10:
                ret = 31;
                break;
            case 11:
                ret = 30;
                break;
            case 12:
                ret = 31;
                break;
            default:
                break;
        }
        return ret;
    }
    
    /**
     * 计算某天是星期几
     * @param p_date 日期字符串
     * @return 星期
     */
    public static int whatDayIsSpecifyDate(String p_date) {
        // 2002-2-22 is friday5
        long differenceDays = 0L;
        long m = 0L;
        differenceDays = signDaysBetweenTowDate(p_date, "2002-2-22");
        
        m = (differenceDays % 7);
        m = m + 5;
        m = m > 7 ? m - 7
            : m;
        return Integer.parseInt(m + "");
    }
    
    /**
     * 计算两日期间相差天数.
     * @since 1.1
     * @param d1 日期字符串
     * @param d2 日期字符串
     * @return long 天数
     */
    public static long signDaysBetweenTowDate(String d1, String d2) {
        java.sql.Date dd1 = null;
        java.sql.Date dd2 = null;
        long result = -1L;
        try {
            dd1 = java.sql.Date.valueOf(d1);
            dd2 = java.sql.Date.valueOf(d2);
            result = signDaysBetweenTowDate(dd1, dd2);
        } catch (Exception ex) {
            result = -1;
        }
        return Math.abs(result);
    }
    
    /**
     * 计算两日期间相差天数.
     * @param d1 开始日期 日期型
     * @param d2 结束日期 日期型
     * @return long 天数
     */
    public static long signDaysBetweenTowDate(java.sql.Date d1, java.sql.Date d2) {
        return (d1.getTime() - d2.getTime()) / (3600 * 24 * 1000);
    }
    
    /**
     * 将字符串按指定格式解析成日期对象
     * @param dateStr 需要进行转换的日期字符串
     * @param pattern 日期字符串的格式
     * @return "yyyy-MM-dd HH:mm:ss"形式的日期对象
     */
    public static Date parseDate(String dateStr, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = format.parse(dateStr);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        //format.applyPattern(defDtPtn);
        
        return date;
    }
    
    /**
     * 时间转换.
     * @param dateStr  时间字符串 如20160207113634
     * @param spattern 源时间格式字符串 如yyyyMMddHHmmss
     * @param tpattern 目标时间格式字符串 如yyyyMM-dd HH:mm:ss
     */
    public static String changeDate(String dateStr, String spattern,String tpattern) {
        SimpleDateFormat format = new SimpleDateFormat(spattern);
        String dateTime = "";
        try {
        	Date date = format.parse(StringUtil.replaceBlank(dateStr,"2"));
        	format.applyPattern(tpattern);
        	dateTime = format.format(date);
        } catch (Exception e) {
            log.error("",e);
        }
        return dateTime;
    }
    
    /**
     * 获取时间对象
     */
    public static Date getCurDate() {
        return Calendar.getInstance().getTime();
    }
    
    /**
     * 根据时间字符串生成时间
     */
    public static Date parseDate(String param) {
        Calendar calendar = Calendar.getInstance();
        if (param != null && !"".equals(param)) {
            if (param.indexOf("-") != -1) {
                String[] s = param.split("-");
                Integer year = Integer.valueOf(s[0]);
                Integer month = Integer.valueOf(s[1]);
                
                if (param.length() == 13) {
                    String[] ss = s[2].split(":");
                    Integer day = Integer.valueOf(ss[0].substring(0, 2));
                    Integer hour = Integer.valueOf(ss[0].substring(3, 5));
                    calendar.set(year, month - 1, day, hour, 0, 0);
                } else if (param.length() == 16) {
                    String[] ss = s[2].split(":");
                    Integer day = Integer.valueOf(ss[0].substring(0, 2));
                    Integer hour = Integer.valueOf(ss[0].substring(4, 5));
                    Integer minute = Integer.valueOf(ss[1]);
                    calendar.set(year, month - 1, day, hour, minute, 0);
                } else if (param.length() == 19) {
                    String[] ss = s[2].split(":");
                    Integer day = Integer.valueOf(ss[0].substring(0, 2));
                    Integer hour = Integer.valueOf(ss[0].substring(3, 5));
                    Integer minute = Integer.valueOf(ss[1]);
                    Integer second = Integer.valueOf(ss[2]);
                    calendar.set(year, month - 1, day, hour, minute, second);
                } else if (param.length() == 21) {
                    String[] ss = s[2].split(":");
                    Integer day = Integer.valueOf(ss[0].substring(0, 2));
                    Integer hour = Integer.valueOf(ss[0].substring(3, 5));
                    Integer minute = Integer.valueOf(ss[1]);
                    Integer second = Integer.valueOf(ss[2].substring(0, 2));
                    calendar.set(year, month - 1, day, hour, minute, second);
                } else {
                    Integer day = Integer.valueOf(s[2]);
                    calendar.set(year, month - 1, day, 0, 0, 0);
                }
            } else {
                if (param.length() == 8) {
                    Integer year = Integer.valueOf(param.substring(0, 4));
                    Integer month = Integer.valueOf(param.substring(4, 6));
                    Integer day = Integer.valueOf(param.substring(6, 8));
                    calendar.set(year, month - 1, day, 0, 0, 0);
                } else if (param.length() == 10) {
                    Integer year = Integer.valueOf(param.substring(0, 4));
                    Integer month = Integer.valueOf(param.substring(4, 6));
                    Integer day = Integer.valueOf(param.substring(6, 8));
                    Integer hour = Integer.valueOf(param.substring(8, 10));
                    calendar.set(year, month - 1, day, hour, 0, 0);
                } else if (param.length() == 12) {
                    Integer year = Integer.valueOf(param.substring(0, 4));
                    Integer month = Integer.valueOf(param.substring(5, 6));
                    Integer day = Integer.valueOf(param.substring(6, 8));
                    Integer hour = Integer.valueOf(param.substring(9, 10));
                    Integer minute = Integer.valueOf(param.substring(11, 12));
                    calendar.set(year, month - 1, day, hour, minute, 0);
                } else if (param.length() == 14) {
                    Integer year = Integer.valueOf(param.substring(0, 4));
                    Integer month = Integer.valueOf(param.substring(4, 6));
                    Integer day = Integer.valueOf(param.substring(6, 8));
                    Integer hour = Integer.valueOf(param.substring(8, 10));
                    Integer minute = Integer.valueOf(param.substring(10, 12));
                    Integer second = Integer.valueOf(param.substring(12, 14));
                    calendar.set(year, month - 1, day, hour, minute, second);
                } else if (param.length() == 13) {
                    Date d = new Date(Long.valueOf(param));
                }
            }
        }
        return calendar.getTime();
    }
    
    /**
     * 获取当前时间 格式：yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentDate() {
        return format.format(new Date());
    }
    
    /**
     * 获取当前时间 格式：yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date(System.currentTimeMillis());
        return sdf.format(d);
    }
    
    public static String parse(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date(time);
        return sdf.format(d);
    }
    
    /**
     * 获取指定格式的当前时间
     */
    public static String getCurrentDate(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date d = new Date(System.currentTimeMillis());
        return sdf.format(d);
    }
    
    /**
     * 获取指定格式的当前时间
     */
    public static String getDateTime(String pattern) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(cal.getTime());
    }
    
    public static String getDateTime(Calendar cal, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(cal.getTime());
    }
    
    public static String dateFormat(Calendar fCal, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(fCal.getTime());
    }
    
    /**
     * 将指定时间按指定格式格式化.
     */
    public static String getDateTime(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }
    
    /**
     * 2010-04-15 返回04-15
     */
    public static String getDisplayDate(String date) {
        return date.replaceFirst("\\d{4}-", "");
    }
    
    /**
     * 2010-04-15 15:30:54 返回 15:30:54
     */
    public static String getDisplayTime(String date) {
        return date.replaceFirst("\\d{4}-\\d{2}-\\d{2}\\s", "");
    }
    
    /**
     * 校验时间 是 返回时间 否 null
     */
    public static Date getMatchedDate(String dateString) {
        String dateRegex = "\\d{4}-\\d{2}-\\d{2}";
        String timeRegex = "\\d{2}:\\d{2}:\\d{2}.*";
        String dateTimeRegex = (new StringBuilder(String.valueOf(dateRegex)))
            .append("\\s").append(timeRegex).toString();
        if (dateString.matches(dateRegex))
            return java.sql.Date.valueOf(dateString);
        if (dateString.matches(timeRegex))
            return Time.valueOf(dateString);
        if (dateString.matches(dateTimeRegex))
            return Timestamp.valueOf(dateString);
        else
            return null;
    }
    
    /**
     * getDateString(Timestamp.valueOf("2010-04-15 15:12:20")
     * getDateString(Time.valueOf("15:12:20") getDateString(new
     * Date("2010-04-15"))
     */
    public static String getDateString(Date date) {
        if (date instanceof Timestamp)
            return getDateTime(date, "yyyy-MM-dd HH:mm:ss");
        if (date instanceof Time)
            return getDateTime(date, "HH:mm:ss");
        if (date instanceof java.sql.Date)
            return getDateTime(date, "yyyy-MM-dd");
        else
            return date.toString();
    }
    
    /**
     * 2010-04-16
     */
    public static String getCurDate10() {
        Calendar cal = Calendar.getInstance();
        String year = Integer.toString(cal.get(1));
        String month = Integer.toString(cal.get(2) + 1);
        String days = Integer.toString(cal.get(5));
        if (month.length() == 1)
            month = (new StringBuilder("0")).append(month).toString();
        days = Integer.toString(cal.get(5));
        if (days.length() == 1)
            days = (new StringBuilder("0")).append(days).toString();
        return (new StringBuilder(String.valueOf(year))).append("-")
            .append(month).append("-").append(days).toString();
    }
    
    /**
     * 20100416
     */
    public static String getCurDate8() {
        Calendar cal = Calendar.getInstance();
        String year = Integer.toString(cal.get(1));
        String month = Integer.toString(cal.get(2) + 1);
        String days = Integer.toString(cal.get(5));
        if (month.length() == 1)
            month = (new StringBuilder("0")).append(month).toString();
        days = Integer.toString(cal.get(5));
        if (days.length() == 1)
            days = (new StringBuilder("0")).append(days).toString();
        return (new StringBuilder(String.valueOf(year))).append(month)
            .append(days).toString();
    }
    
    /**
     * getDateByDate8("20100416")
     */
    public static Date getDateByDate8(String date8) {
        Calendar cal = Calendar.getInstance();
        cal.set(1, Integer.parseInt(date8.substring(0, 4)));
        cal.set(2, Integer.parseInt(date8.substring(4, 6)) - 1);
        cal.set(5, Integer.parseInt(date8.substring(6, 8)));
        return cal.getTime();
    }
    
    /**
     * getNextMonthFirstDate(getCurrentDate(), 13) 返回 20110101
     */
    public static String getNextMonthFirstDate(String currdate, int i) {
        if (currdate == null)
            return "";
        if (currdate.length() < 8)
            return currdate;
        Calendar cal = Calendar.getInstance();
        cal.set(1, Integer.parseInt(currdate.substring(0, 4)));
        cal.set(2, (Integer.parseInt(currdate.substring(4, 6)) - 1) + i);
        cal.set(5, Integer.parseInt("01"));
        String year = Integer.toString(cal.get(1));
        String month = Integer.toString(cal.get(2) + 1);
        String days = Integer.toString(cal.get(5));
        if (month.length() == 1)
            month = (new StringBuilder("0")).append(month).toString();
        if (days.length() == 1)
            days = (new StringBuilder("0")).append(days).toString();
        return (new StringBuilder(String.valueOf(year))).append(month)
            .append(days).toString();
    }
    
    /**
     * 时间10位转8位
     */
    public static String date10to8(String date10) {
        if (date10 == null)
            return "";
        String date8 = "";
        int y = date10.indexOf("-");
        if (y == -1)
            return "";
        String year = date10.substring(0, y);
        if (year.length() != 4)
            return "";
        int m = date10.indexOf("-", y + 1);
        if (y == -1) {
            return "";
        } else {
            String month = date10.substring(y + 1, m);
            String days = date10.substring(m + 1);
            date8 = (new StringBuilder(String.valueOf(year))).append(month)
                .append(days).toString();
            return date8;
        }
    }
    
    /**
     * 时间8位转10位
     */
    public static String date8to10(String date8) {
        if (date8 == null)
            return "";
        if (date8.trim().length() < 8) {
            return date8;
        } else {
            String date10 = "";
            String year = date8.substring(0, 4);
            String month = date8.substring(4, 6);
            String days = date8.substring(6, 8);
            date10 = (new StringBuilder(String.valueOf(year))).append("-")
                .append(month).append("-").append(days).toString();
            return date10;
        }
    }
    
    /**
     * 当前时间加上days天 getDateTime(getAdDate(new Date(),20), "yyyy-MM-dd")
     */
    public static Date getAdDate(Date currdate, int days) {
        Date myDate = currdate;
        long myTime = myDate.getTime() / 1000L + (long) (0x15180 * days);
        myDate.setTime(myTime * 1000L);
        return myDate;
    }
    
    /**
     * 当前时间减少days天 getDateTime(getAdDate(new Date(),20), "yyyy-MM-dd")
     */
    public static Date getReduceDate(Date currdate, int days) {
        Date myDate = currdate;
        long myTime = myDate.getTime() / 1000L - (long) (0x15180 * days);
        myDate.setTime(myTime * 1000L);
        return myDate;
    }
    
    /**
     * getDate8ByDate(new Date()) 返回 20100416
     */
    public static String getDate8ByDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        String year = Integer.toString(cal.get(1));
        String month = Integer.toString(cal.get(2) + 1);
        String days = Integer.toString(cal.get(5));
        if (month.length() == 1)
            month = (new StringBuilder("0")).append(month).toString();
        if (days.length() == 1)
            days = (new StringBuilder("0")).append(days).toString();
        return (new StringBuilder(String.valueOf(year))).append(month)
            .append(days).toString();
    }
    
    /**
     * 指定的8位时间 加上adddays天
     */
    public static String getAdDate(String date8, int adddays) {
        Date date = getDateByDate8(date8);
        date = getAdDate(date, adddays);
        return getDate8ByDate(date);
    }
    
    public static Calendar getCalendar(String date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = format.parse(date, pos);
        return format.getCalendar();
    }
    
    public static String getDateTimeAd(String pattern, int days) {
        Calendar cal = Calendar.getInstance();
        cal.set(5, cal.get(5) + days);
        DateFormat format = new SimpleDateFormat(pattern);
        return format.format(cal.getTime());
    }
    
    public static String getRandom() {
        int i1 = (int) (Math.random() * 10D);
        int i2 = (int) (Math.random() * 10D);
        int i3 = (int) (Math.random() * 10D);
        int i4 = (int) (Math.random() * 10D);
        return (new StringBuilder(String.valueOf(String.valueOf(i1))))
            .append(String.valueOf(i2)).append(String.valueOf(i3))
            .append(String.valueOf(i4)).toString();
    }
    
    public static String getBookingCode() {
        String str = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int i1 = (int) (Math.random() * 35D);
        int i2 = (int) (Math.random() * 35D);
        int i3 = (int) (Math.random() * 35D);
        int i4 = (int) (Math.random() * 35D);
        int i5 = (int) (Math.random() * 35D);
        return (new StringBuilder(String.valueOf(str.substring(i1, i1 + 1))))
            .append(str.substring(i2, i2 + 1))
            .append(str.substring(i3, i3 + 1))
            .append(str.substring(i4, i4 + 1))
            .append(str.substring(i5, i5 + 1)).toString();
    }
    
    public static String getWeekNum(String date8) {
        if (date8 != null && date8.length() == 8) {
            StringBuffer sb = new StringBuffer(date8);
            Calendar cal = Calendar.getInstance(Locale.CHINA);
            cal.set(1, Integer.parseInt(sb.substring(0, 4)));
            cal.set(2, Integer.parseInt(sb.substring(4, 6)) - 1);
            cal.set(5, Integer.parseInt(sb.substring(6, 8)));
            String week = dateFormat(cal, "EEE");
            if (week.equalsIgnoreCase("\u661F\u671F\u4E00"))
                week = "1";
            else if (week.equalsIgnoreCase("\u661F\u671F\u4E8C"))
                week = "2";
            else if (week.equalsIgnoreCase("\u661F\u671F\u4E09"))
                week = "3";
            else if (week.equalsIgnoreCase("\u661F\u671F\u56DB"))
                week = "4";
            else if (week.equalsIgnoreCase("\u661F\u671F\u4E94"))
                week = "5";
            else if (week.equalsIgnoreCase("\u661F\u671F\u516D"))
                week = "6";
            else if (week.equalsIgnoreCase("\u661F\u671F\u65E5"))
                week = "7";
            return week;
        } else {
            return date8;
        }
    }
    
    public static String getNextTime(String dectime) {
        StringTokenizer datest = new StringTokenizer(dectime, ":");
        String timetmp = "";
        int hh = 0;
        int mm = 0;
        boolean first = true;
        while (datest.hasMoreElements())
            if (first) {
                hh = Integer.parseInt(datest.nextToken().trim());
                first = false;
            } else {
                mm = Integer.parseInt(datest.nextToken().trim());
            }
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date now = new Date();
        cal.setTime(now);
        cal.add(11, hh);
        cal.add(12, mm);
        return format.format(cal.getTime());
    }
    
    public static String getNextDateTime(String dectime) {
        StringTokenizer datest = new StringTokenizer(dectime, ":");
        String timetmp = "";
        int hh = 0;
        int mm = 0;
        boolean first = true;
        while (datest.hasMoreElements())
            if (first) {
                hh = Integer.parseInt(datest.nextToken().trim());
                first = false;
            } else {
                mm = Integer.parseInt(datest.nextToken().trim());
            }
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date now = new Date();
        cal.setTime(now);
        cal.add(11, hh);
        cal.add(12, mm);
        return format.format(cal.getTime());
    }
    
    public static int getTimeSpace(String time1, String time2) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = format.parse(time1);
            date2 = format.parse(time2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int count = (int) ((date2.getTime() / 1000L - date1.getTime() / 1000L) / 60L);
        return count;
    }
    
    public static String getNextDate(String dectime) {
        StringTokenizer datest = new StringTokenizer(dectime, ":");
        String timetmp = "";
        int hh = 0;
        int mm = 0;
        boolean first = true;
        while (datest.hasMoreElements())
            if (first) {
                hh = Integer.parseInt(datest.nextToken().trim());
                first = false;
            } else {
                mm = Integer.parseInt(datest.nextToken().trim());
            }
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date now = new Date();
        cal.setTime(now);
        cal.add(11, hh);
        cal.add(12, mm);
        return format.format(cal.getTime());
    }
    
    public static void sleepSec(int second) {
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
    public static int getDays(String begindate, String enddate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        ParsePosition pos = new ParsePosition(0);
        ParsePosition pos1 = new ParsePosition(0);
        Date dt1 = formatter.parse(begindate, pos);
        Date dt2 = formatter.parse(enddate, pos1);
        long l = (dt2.getTime() - dt1.getTime()) / 0x5265c00L;
        return (int) l;
    }
    
    public static String getWeeks(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int weekday = cal.get(cal.DAY_OF_WEEK);
        String rstr = "星期";
        switch (weekday) {
            case 2:
                rstr += "一";
                break;
            case 3:
                rstr += "二";
                break;
            case 4:
                rstr += "三";
                break;
            case 5:
                rstr += "四";
                break;
            case 6:
                rstr += "五";
                break;
            case 7:
                rstr += "六";
                break;
            case 1:
                rstr += "日";
                break;
        }
        return rstr;
    }
    
    /**
     * 根据传入的模式参数返回当天的日期.
     */
    public static String getToday(String pattern) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }
    
    /**
     * 比较日期.
     * @param date1
     * @param date2
     * @param pattern
     * @return date1>date2 1 ;date1<date2 -1 ; date1=date2 0 ; -2未知
     * n1 >= 0 //time<=beginTime 1 0
     */
    public static int compareTo(String date1,String date2,String pattern){
	    int result = -2;
    	try {
	    	if(StringUtil.isNotEmpty(date1) && StringUtil.isNotEmpty(date2)){
	    	    Date d1 = DateUtil.convertToCalendar(date1, pattern).getTime();
	            Date d2 = DateUtil.convertToCalendar(date2, pattern).getTime();
	            
	            if(d1 != null && d2 != null){
	    		    result = d1.compareTo(d2);
	    		}
		    }
		} catch (Exception e) {
			log.error("比较日期时异常【date1: " + date1 + " - date2: " + date2 + "】");
		}
	    /*
		if(result == 1){
			System.out.println(date1 + " 大于 " + date2 + " - " + result);
		}else if(result == 0){
			System.out.println(date1 + " 等于 " + date2 + " - " + result);
		}else if(result == -1){
			System.out.println(date1 + " 小于 " + date2 + " - " + result);
		}
		*/
	    return result;
    }
    
    /**
     * 比较两个日期大小.
     * @param date1 Date
     * @param date2 Date
     * @return boolean 若是date1比date2小则返回true
     * d1小于d2返回false，否则返回true
     */
    public static boolean compareTo(Date d1, Date d2) {
        try {
            int result = -2;
        	if(d1 != null && d2 != null){
	    		result = d1.compareTo(d2);
	    	}
        	if(result == 1){
    			System.out.println(d1 + " 大于 " + d2 + " - " + result);
    			return true;
    		}else if(result == 0){
    			System.out.println(d1 + " 等于 " + d2 + " - " + result);
    			return true;
    		}else if(result == -1){
    			System.out.println(d1 + " 小于 " + d2 + " - " + result);
    		}
        } catch (Exception ex) {
           log.error("",ex);
        }
        return false;
    }
    public static int compareTo2(Date d1, Date d2) {
    	int rs = -2;
        try {
        	if(d1 != null && d2 != null){
	    		rs = d1.compareTo(d2);
	    	}
        	if(rs == 1){
    			System.out.println(d1 + " 大于 " + d2 + " - " + rs);
    		}else if(rs == 0){
    			System.out.println(d1 + " 等于 " + d2 + " - " + rs);
    		}else if(rs == -1){
    			System.out.println(d1 + " 小于 " + d2 + " - " + rs);
    		}
        } catch (Exception ex) {
           log.error("",ex);
        }
        return rs;
    }
    public static int compareTo(Date d1, String d2,String pattern) {
    	int rs = -2;
    	try {
    		rs = compareTo2(d1,DateUtil.parseDate(d2, pattern));
		} catch (Exception ex) {
			log.error("",ex);
		}
    	return rs;
    }
    
    /**
     * 比较两个日期大小
     * @param date1   日期字符串
     * @param pattern1日期格式
     * @param date2   日期字符串
     * @param pattern2日期格式
     * @return boolean 若是date1比date2小则返回true
     */
    public static boolean compareTo(String date1, String pattern1,
        String date2, String pattern2) throws ParseException {
        Date d1 = convertToCalendar(date1, pattern1).getTime();
        Date d2 = convertToCalendar(date2, pattern2).getTime();
        return d1.before(d2);
    }
    
    /**
     * 根据传入的日期字符串以及格式，产生一个Calendar对象
     * @param date日期字符串
     * @param pattern 日期格式
     * @return Calendar
     * @throws ParseException 当格式与日期字符串不匹配时抛出该异常
     */
    public static Calendar convertToCalendar(String date, String pattern)
        throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date d = sdf.parse(date);
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(d);
        return calendar;
    }
    
    public static boolean isToday(String sdate,String sdatepattern,String tdate,String tdatepattern,String pattern) {
    	boolean b = false;
    	String sd = formatDate(sdate,sdatepattern, pattern);
    	String td = formatDate(tdate,tdatepattern, pattern);
    	b = sd.equals(td);
    	return b;
    }
    
    /**
     * 判断当前时间是不是在起始时间和结束时间范围之间.
     * true=在时间周期范围内，false=不是
     */
	public static boolean isRange(String btime,String etime,String pattern) {
		if(DateUtil.compareTo(btime,etime,pattern)> 0){//btime>etime
			Date d = new Date();
			String cdate = DateUtil.formatDate(d, DateUtil.DEFAULT_DATE_FORMATE);
			String ndate = DateUtil.formatDate(DateUtil.getAdDate(d,1), DateUtil.DEFAULT_DATE_FORMATE);
			if("HH:mm".equals(pattern) || "HH:mm:ss".equals(pattern)){
				btime  = cdate + " " + btime;
				etime  = ndate + " " + etime;
				pattern  = DateUtil.DEFAULT_DATE_FORMATE + " " + pattern;
			}
		}
		String ctime = DateUtil.formatDate(new Date(),pattern);
		//System.out.println(btime + " - " + ctime + " - " + etime);
		//b <= x && x <= e
		if(DateUtil.compareTo(btime,ctime,pattern) <= 0 && DateUtil.compareTo(ctime,etime,pattern) <= 0){
			return true;
		}
		return false;
    }
	public static boolean equals(Date date,String tdate,String pattern) {
		String cdate = formatDate(date,pattern);
		if(cdate.equals(tdate)){
			return true;
		}
		return false;
	}
    
    /**
     * 用途：以指定的格式格式化日期字符串
     * @param pattern字符串的格式
     * @param currentDate被格式化日期
     * @return String 已格式化的日期字符串
     * @throws NullPointerException如果参数为空
     */
    public static String formatDate(Calendar currentDate, String pattern) {
        Date date = currentDate.getTime();
        return formatDate(date, pattern);
    }
    
    /**
     * 用途：以指定的格式格式化日期字符串
     * @param pattern字符串的格式
     * @param currentDate被格式化日期
     * @return String 已格式化的日期字符串
     * @throws NullPointerException 如果参数为空
     */
    public static String formatDate(Date date, String pattern) {
    	if(date != null){
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.format(date);
    	}
    	return "";
    }
    
    public static String formatDate(java.sql.Date currentDate, String pattern) {
    	if(currentDate != null){
    		SimpleDateFormat format = new SimpleDateFormat(pattern);
            return format.format(new Date(currentDate.getTime()));
            //return format.format(currentDate);
    	}
    	return "";
    }
    
    /**
     * 用途：以指定的格式格式化日期字符串
     * @param currentDate 被格式化日期字符串 必须为yyyymmdd
     * @param pattern字符串的格式
     * @return String 已格式化的日期字符串
     * @throws NullPointerException 如果参数为空
     * @throws java.text.ParseException若被格式化日期字符串不是yyyymmdd形式时抛出
     */
    public static String formatDate1(String currentDate, String pattern)
        throws java.text.ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = sdf.parse(currentDate);
        sdf.applyPattern(pattern);
        return sdf.format(date);
    }

	public static String formatDate(String s,String sformate,String tformate){
		SimpleDateFormat format = new SimpleDateFormat(sformate);
        String date = "";
        try {
        	Date d = format.parse(s);
            if(d != null){
            	format.applyPattern(tformate);
                date = format.format(d);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return date;
    }
	
	public static String formatDate(String formate){
		SimpleDateFormat format = new SimpleDateFormat(formate);
        String date = "";
        try {
        	 date = format.format(new Date());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return date;
    }
    
    //return DateUtil.formatDate2("Fri Jan 22 14:59:59 +0800 2016", "yyyy-MM-dd HH:mm:ss");
    public static String formatDate2(String currentDate, String pattern){
    	/*
    	SimpleDateFormat format= new SimpleDateFormat("dow mon dd hh:mm:ss zzz yyyy"); 
		try {
			System.out.println(format.parse("Fri Jan 22 14:59:59 +0800 2016"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	 */
    	Date d = new Date(currentDate);//"Fri Jan 22 14:59:59 +0800 2016"
    	return formatDate(d, pattern);//"yyyy-MM-dd HH:mm:ss"
    }
    
    /**
     * 用途：以指定的格式格式化日期字符串
     * @param strDate  被格式化日期字符串 必须为yyyymmdd
     * @param formator 格式字符串
     * @return String  已格式化的日期字符串
     * @throws NullPointerException 如果参数为空
     * @throws java.text.ParseException 若被格式化日期字符串不是yyyymmdd形式时抛出
     */
    public static Calendar strToDate(String strDate, String formator) {
        Calendar date = Calendar.getInstance();
        date.setTime(java.sql.Date.valueOf(strDate));
        return date;
    }
    
    /**
     * 判断当前时间是否在参数时间内（当开始时间大于结束时间表示时间段的划分从begin到第二天的end时刻） 例如当前时间在12：00
     * 传入参数为（12,12,0,1）返回true 例如当前时间在12：00 传入参数为（12,12,1,0）返回true
     * @param beginHour  int 开始的小时值
     * @param endHour    int 结束的小时值
     * @param beginMinu  int 开始的分钟值
     * @param endMinuint 结束的分钟值
     */
    public static boolean isInTime(int beginHour, int endHour, int beginMinu,
        int endMinu) {
        Date date1 = new Date();
        Date date2 = new Date();
        Date nowDate = new Date();
        date1.setHours(beginHour);
        date2.setHours(endHour);
        date1.setMinutes(beginMinu);
        date2.setMinutes(endMinu);
        if (date1 == date2) {
            return false;
        }
        // yyyy-MM-dd HH:mm:ss
        if (DateUtil.compareTo(date2, date1)) {
            if (!DateUtil.compareTo(nowDate, date1)
                || DateUtil.compareTo(nowDate, date2)) {
                return true;
            }
        } else {
            if (!DateUtil.compareTo(nowDate, date1)
                && DateUtil.compareTo(nowDate, date2)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 将指定格式的时间String转为Date类型
     * @param dateStr String 待转换的时间String
     * @param pattern String 转换的类型
     */
    public static Date convertStringToDate(String dateStr, String pattern)
        throws ParseException {
        if (dateStr == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.parse(dateStr);
    }
    
    public static String convertDateToString(Date date) throws ParseException {
        if (date == null) {
            return "";
        }
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }
    
    /**
     * 计算两个date相隔的天数
     */
    public static int getIntervalDays(Date enddate, Date begindate) {
        long millisecond = enddate.getTime() - begindate.getTime();
        int day = (int) (millisecond / 24L / 60L / 60L / 1000L);
        return day;
    }
    
    /**
     * 将日期转化为字符串的形式
     */
    public static String dateToString(Date date, String strFormat) {
        if (date == null) {
            return null;
        }
        if (strFormat == null) {
            strFormat = "yyyy-MM-dd";
        }
        SimpleDateFormat format = new SimpleDateFormat(strFormat);
        return format.format(date);
    }
    
    /**
     * 获取n天后的日期
     * 2016-07-26 -> 2016-07-27
     */
    public static Date dateAfterNDate(Date date, int nDay) {
        return getDate(date, nDay, 1);
    }
    public static Date dateAfterNDate(String dateStr,String pattern,int nDay) {
    	Date date = null;
    	try {
    		date = parseDate(dateStr, pattern);
		} catch (Exception e) {
			log.error("",e);
		}
		if(date != null){
			return getDate(date, nDay, 1);
		}
        return null;
    }
    
    /**
     * 获取n天前的日期
     * 2016-07-26 -> 2016-07-25
     */
    public static Date dateBeforeNDate(Date date, int nDay) {
        return getDate(date, nDay, -1);
    }
    public static Date dateBeforeNDate(String dateStr, int nDay) {
    	Date date = parseDate(dateStr, "");
        return getDate(date, nDay, -1);
    }
    
    /**
     * 获取date前或后nDay天的日期
     * @param date：开始日期
     * @param nDay：天数
     * @param type：正为后nDay天的日期，否则为前nDay天的日期。
     */
    private static Date getDate(Date date, int nDay, int type) {
        long millisecond = date.getTime(); // 从1970年1月1日00:00:00开始算起所经过的微秒数
        long msel = nDay * 24 * 3600 * 1000l; // 获取nDay天总的毫秒数
        millisecond = millisecond + ((type > 0) ? msel
            : (-msel));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millisecond);
        return calendar.getTime();
    }
    
    /**
     * 获取指定时间+/-year后的时间
     * @param date  时间
     * @param num   数量 +/-num
     */
    public static Date operateYear(Date date, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        year = year + (num);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        calendar.set(year, month, day, hour, minute,second);
        Date newDate = calendar.getTime();
        
        return newDate;
    }
    
    /**
     * 获取指定时间+/-month后的时间
     * @param date  时间
     * @param num   数量 +/-num
     */
    public static Date operateMonth(Date date, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        month = month + (num);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        calendar.set(year, month, day, hour, minute,second);
        Date newDate = calendar.getTime();
        
        return newDate;
    }
    
    /**
     * 获取指定时间+/-day后的时间
     * @param date  时间
     * @param num   数量 +/-num
     */
    public static Date operateDay(Date date, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        day = day + (num);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        calendar.set(year, month, day, hour, minute,second);
        Date newDate = calendar.getTime();
        
        return newDate;
    }
    
    /**
     * 获取指定时间+/-hour后的时间
     * @param date  时间
     * @param num   数量 +/-num
     */
    public static Date operateHour(Date date, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        hour = hour + (num);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        calendar.set(year, month, day, hour, minute,second);
        Date newDate = calendar.getTime();
        
        return newDate;
    }
    
    /**
     * 获取指定时间+/-minute后的时间
     * @param date  时间
     * @param num   数量 +/-num
     */
	public static Date operateMinute(String dateStr,String pattern,int num) {
    	Date date = null;
    	try {
    		date = parseDate(dateStr, pattern);
		} catch (Exception e) {
			log.error("",e);
		}
		if(date != null){
			return operateMinute(date,num);
		}
		return null;
    }
    public static Date operateMinute(Date date, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        minute = minute + (num);
        calendar.set(year, month, day, hour, minute);
        Date newDate = calendar.getTime();
        
        return newDate;
    }
    public static Date operateSecond(Date date, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        second = second + (num);
        calendar.set(year, month, day, hour, minute,second);
        Date newDate = calendar.getTime();
        
        return newDate;
    }
    
    /**
     * Java比较两个时间相差多少天，多少个月，多少年
     * @param date1 需要比较的时间 不能为空(null),需要正确的日期格式 ,如：2009-09-12
     * @param date2 被比较的时间 为空(null)则为当前时间
     * @param stype 返回值类型 0为多少天，1为多少个月，2为多少年
     * @return
     *         举例：
     *         compareDate("2009-09-12", null, 0);//比较天
     *         compareDate("2009-09-12", null, 1);//比较月
     *         compareDate("2009-09-12", null, 2);//比较年
     */
    public static int compareDate(String startDay, String endDay, int stype) {
        int n = 0;
        String[] u = {"天", "月", "年" };
        String formatStyle = stype == 1 ? "yyyy-MM"
            : "yyyy-MM-dd";
        endDay = endDay == null ? getCurrentDate("yyyy-MM-dd")
            : endDay;
        DateFormat df = new SimpleDateFormat(formatStyle);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(df.parse(startDay));
            c2.setTime(df.parse(endDay));
        } catch (Exception e3) {
            System.out.println("wrong occured");
        }
        while (!c1.after(c2)) { // 循环对比，直到相等，n 就是所要的结果
            n++;
            if (stype == 1) {
                c1.add(Calendar.MONTH, 1); // 比较月份，月份+1
            } else {
                c1.add(Calendar.DATE, 1); // 比较天数，日期+1
            }
        }
        n = n - 1;
        if (stype == 2) {
            n = (int) n / 365;
        }
        System.out.println(startDay + " -- " + endDay + " 相差多少" + u[stype]
            + ":" + n);
        return n;
    }
    
    /**
     * 实现给定某日期，判断是星期几.
     */
    public static String getWeekday(String date) {// 必须yyyy-MM-dd
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdw = new SimpleDateFormat("E");
        Date d = null;
        try {
            d = sd.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdw.format(d);
    }
    
    /**
     * 判断是不是当天日.
     */
    public static boolean equals(String date1) {
		return equals(date1,DateUtil.DEFAULT_DATE_FORMATE);
	}
	public static boolean equals(String date1,String pattern) {
        boolean b = false;
		try {
			if(DateUtil.compareTo(date1,DateUtil.formatDate(new Date(), pattern),pattern) == 0){
				b = true;
			}
		} catch (Exception e) {
			log.error("",e);
		}
	
		return b;
	}
    
    /**
     * 获取起始时间的当月的所有天数.12-01 -- 12-31
     */
    public static String[] getDayOfMonthCategories(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        String[] categories = new String[days];
        String monthStr = calendar.get(Calendar.MONTH) + 1 + "-";
        for (int i = 1; i <= days; i++) {
            categories[i - 1] = monthStr + (i < 10 ? "0" + i: i);
        }
        return categories;
    }
    
    /**
     * 获取起始时间的后30天.12-19 -- 01-17
     * @param date 起始时间
     */
    public static String[] getDayCategories(Date date) {
        String[] categories = new String[30];
        DateFormat df = new SimpleDateFormat("MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        for (int i = 0; i < categories.length; i++) {
            categories[i] = df.format(c.getTime());
            c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) + 1);
        }
        return categories;
    }
    
    /*
    public static void main(String[] args) {
        String[] categories = getDayCategories(new Date());
        for (int i = 0; i < categories.length; i++) {
            System.out.println(categories[i]);
        }
    }
    */
    public static String[] getCategories(String stattype) {
        String[] categories = null;
        if ("32".equals(stattype)) { // 小时
            categories = getHourCategories();
        } else if ("16".equals(stattype)) { // 日
            categories = getDayCategories();
        } else if ("8".equals(stattype)) { // 周
            categories = getWeekCategories();
        } else if ("4".equals(stattype)) { // 月
            categories = getMonthCategories();
        } else if ("2".equals(stattype)) { // 季
            categories = getQuarterCategories();
        }
        return categories;
    }

    /**
     * 近24小时.
     */
    public static String[] getHourCategories() {
        String[] categories = new String[24];
        DateFormat df = new SimpleDateFormat("MM-dd HH");
        Calendar c = Calendar.getInstance();
        for (int i = categories.length - 1; i >= 0; i--) {
            categories[i] = df.format(c.getTime());
            c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY) - 1);
        }
        return categories;
    }
    
    /**
     * 近30天.
     */
    public static String[] getDayCategories() {
        String[] categories = new String[30];
        DateFormat df = new SimpleDateFormat("MM-dd");
        Calendar c = Calendar.getInstance();
        for (int i = categories.length - 1; i >= 0; i--) {
            categories[i] = df.format(c.getTime());
            c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) - 1);
        }
        return categories;
    }
    
    /**
     * 近12周.
     */
    public static String[] getWeekCategories() {
        String[] categories = new String[12];
        DateFormat df = new SimpleDateFormat("w");
        Calendar c = Calendar.getInstance();
        for (int i = categories.length - 1; i >= 0; i--) {
            categories[i] = df.format(c.getTime());
            c.set(Calendar.WEEK_OF_YEAR, c.get(Calendar.WEEK_OF_YEAR) - 1);
        }
        return categories;
    }
    
    /**
     * 近12月.
     */
    public static String[] getMonthCategories() {
        String[] categories = new String[12];
        DateFormat df = new SimpleDateFormat("MM");
        Calendar c = Calendar.getInstance();
        for (int i = categories.length - 1; i >= 0; i--) {
            categories[i] = df.format(c.getTime());
            c.set(Calendar.MONTH, c.get(Calendar.MONTH) - 1);
        }
        return categories;
    }
    
    /**
     * 近4季度.
     */
    public static String[] getQuarterCategories() {
        String[] categories = new String[4];
        Calendar c = Calendar.getInstance();
        for (int i = categories.length - 1; i >= 0; i--) {
            int month = c.get(Calendar.MONTH) + 1;
            categories[i] = ((month - 1) / 3 + 1) + "";
            c.set(Calendar.MONTH, c.get(Calendar.MONTH) - 3);
        }
        return categories;
    }
}
