package com.android.housingonitoringagent.homeworryagent.utils;

import com.android.housingonitoringagent.homeworryagent.App;
import com.android.housingonitoringagent.homeworryagent.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateUtil {
    public interface Unit {
        int MILLI_SECOND = 1;
        int SECOND = 1000;
    }

    public static String DATE_TEMPLATE = App.getInstance().getString(R.string.date_format);
    public static String DATE_SIMPLE = App.getInstance().getString(R.string.date_simple);
    public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_TEMPLATE, Locale.CHINA);
    public static SimpleDateFormat DATE_FORMAT_SIMPLE = new SimpleDateFormat(DATE_SIMPLE, Locale.CHINA);

    public static String parseTimeStamp(Long timeStamp, int unit) {
        if (timeStamp == null) {
            return "";
        }

        return parseTimeMillis(timeStamp * unit);
    }

    public static String parseTimeMillis(Long timeMillis) {
        if (timeMillis == null) {
            return "";
        }

        return DATE_FORMAT.format(new Date(timeMillis));
    }


    /**
     根据给定的格式与时间(Date类型的)，返回时间字符串。最为通用。<br>
     *
     * @param date
     *            指定的日期
     * @param format
     *            日期格式字符串
     * <br>常用格式字符串：
     * <br> y year
     * <br> M month in year
     * <br> d day in month
     * <br> H hour in day (0-23)
     * <br> s second in minute
     * <pre> e.g. format="MM月dd日"  返回 11月03日</pre>
     *
     * @return String 指定格式的日期字符串.
     */
    public static String getFormatDateString(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /***
     * 根据传入的整型年月日，得到yyyy年MM月dd日 格式的字符串
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static String getFormatDateString(int year,int month,int day){
        StringBuffer sb=new StringBuffer();
        sb.append(year+"年");
        sb.append(month>9?month:"0"+month);
        sb.append("月");
        sb.append(day>9?day:"0"+day);
        sb.append("日");
        return  sb.toString();
    }

    /**
     * 将传入的Date对象参数 转化成 格式为 “yyyy年MM月dd日 星期x” 的字符串
     * @param date
     * @return  “yyyy年MM月dd日 星期x” 的字符串
     */
    public static String getFormatDateString(Date date){
        return getFormatDateString(date,DATE_TEMPLATE)+" "+ DateUtil.getDayStrOfWeek(date);
    }

    /**
     * 传入指定格式的 字符串 转成Date对象
     * @param s
     * @param format
     * @return
     */
    public static Date stringToDate(String s,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 根据给定的格式的时间字符串 “yyyy年MM月dd日” 返回Date对象<br>
     * @return Date对象
     */
    public static Date stringToDate(String d) {
        return stringToDate(d,DATE_TEMPLATE);
    }


    /***完成 整型 → Date对象的转换
     * <br/>根据传入的整型年月日，getFormatDateString(year,month,day)得到yyyy年MM月dd日 格式的字符串
     * <br/> 再根据 “yyyy年MM月dd日” 格式返回Date对象
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static Date intToDate(int year,int month,int day){
        return stringToDate(getFormatDateString(year, month, day));

    }

    /**
     * 根据指定的年、月、日返回当前是星期几。1表示星期天、2表示星期一、7表示星期六。
     *
     * @param year
     * @param month
     *            month是从1开始的12结束
     * @param day
     * @return 返回一个代表当期日期是星期几的数字。1表示星期天、2表示星期一、7表示星期六。
     */
    public static int getDayOfWeek(String year, String month, String day) {
        Date date=new Date();
        date.setYear(Integer.valueOf(year));
        date.setMonth(Integer.valueOf(month));
        date.setDate(Integer.valueOf(day));
        return date.getDay();
    }

    /**
     *   根据指定的年、月、日返回当前是星期几字符串。
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static String getDayStrOfWeek(String year, String month, String day) {
        int i=getDayOfWeek(year,month,day);
        return getDayStrOfWeek(i);//i=0为星期日
    }

    /**
     *   根据指定的年、月、日返回当前是星期几字符串。
     * @param date
     * @return String
     */
    public static String getDayStrOfWeek(Date date) {
        return  getDayStrOfWeek(date.getDay());
    }

    /**
     * 根据 数字1~7返回字符串 星期一~星期日
     * @param day
     * @return
     */
    public static String getDayStrOfWeek(int day) {
        String s="";
//		int i=getDayOfWeek(year,month,day);
        switch (day){
            case  1:
                s="星期一";
                break;
            case  2:
                s="星期二";
                break;
            case  3:
                s="星期三";
                break;
            case  4:
                s="星期四";
                break;
            case  5:
                s="星期五";
                break;
            case  6:
                s="星期六";
                break;
            case  0:
                s="星期日";
                break;
            default:
                if (day>7)
                    return getDayStrOfWeek(day-7);
                if (day<=0)
                    return getDayStrOfWeek(day+7);
                break;
        }
        return s;
    }

    /**
     *  取得给定日期加上一定天数后的日期对象
     * @param date
     *            给定的日期对象
     * @param amount
     *            需要添加的天数，如果是向前的天数，使用负数就可以.
     * @return     Date对象
     */
    public static Date getFormatDateAdd(Date date, int amount) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(GregorianCalendar.DATE, amount);
        return cal.getTime();
    }

    /**
     *  取得给定日期加上一定天数后的日期对象.
     *
     * @param date
     *            给定的日期对象
     * @param amount
     *            需要添加的天数，如果是向前的天数，使用负数就可以.
     * @param format
     *            输出格式.
     */
    public static String getFormatDateAdd(Date date, int amount, String format) {
        return getFormatDateString(getFormatDateAdd(date, amount), format);
    }


    /**
     * 获取 date所在的一周日期
     * @param date  返回Date[7]
     * @return
     */
    public static Date[] getWholeWeekDate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DAY_OF_WEEK, -1);
        }

        Date[] dates = new Date[7];
        for (int i = 0; i < 7; i++) {
            dates[i] = calendar.getTime();
            calendar.add(Calendar.DATE, 1);
        }
        return dates;
    }


    /**
     * 获取本星期 星期一 到星期日 的Date对象
     * @return Date[]
     */
    public static Date[] getWholeWeekDate() {
        return getWholeWeekDate(getTodayDate());
    }


    /**
     * 获取 今天的Date对象
     * @return	Date 对象
     */
    public static Date getTodayDate(){
        Calendar calendar=Calendar.getInstance();
        calendar.get(Calendar.HOUR_OF_DAY);//设置为24小时制
        return calendar.getTime();
    }

    /**
     * 返回字符串 MM月DD日
     * @return yyyy年MM月DD日 格式的今天日期
     */
    public static String getToday(){
        Date date= getTodayDate();
        return getFormatDateString(date, DATE_TEMPLATE);
    }

    /***
     *
     * @return   yyyy年MM月DD日 星期X  格式的今天日期 String
     */
    public static String getTodayDateString(){
        return DateUtil.getToday()+" "+ DateUtil.getDayStrOfWeek(DateUtil.getTodayDate());
    }

    public static String getStrTime(Long cc_time) {
        if (cc_time == null) {
            return "";
        }
        return DATE_FORMAT_SIMPLE.format(new Date(cc_time));
    }
}
