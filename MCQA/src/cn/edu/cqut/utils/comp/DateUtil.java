package cn.edu.cqut.utils.comp;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间处理器
 *
 * @author 谌毅
 *
 */
@SuppressLint("SimpleDateFormat")
public class DateUtil {

	public static final DateFormat STANDARD_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final DateFormat File_NAME_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
	private static final DateFormat SIMPLE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 标准格式化时间
	 *
	 * @param date
	 * @return
	 */
	public static String formatStandard(Date date) {
		return STANDARD_FORMAT.format(date);
	}

	/**
	 * 获取当前时间的标准化时间
	 * @return
	 */
	public static String curDateFormatStandard(){
		return STANDARD_FORMAT.format(getCurDate());
	}
	/**
	 * 获取当前时间的文件名时间
	 * @return
	 */
	public static String curDateFormatFileName(){
		return File_NAME_FORMAT.format(getCurDate());
	}

	/**
	 * 获取当前时间
	 * @return
	 */
	public static Date getCurDate(){
		return getCalendar().getTime();
	}

	/**
	 * 获取本月第一天
	 * @return
	 */
	public static Date getFirstDateInThisMonth(){
		Calendar calendar = getCalendar();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}
	/**
	 * 获取本月最后一天
	 * @return
	 */
	public static Date getLastDateInThisMonth(){
		Calendar calendar = getCalendar();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	/**
	 * 获取7天前的时间
	 * @return
	 */
	public static Date get7DayAgao(){
		Calendar calendar = getCalendar();
		calendar.add(Calendar.DAY_OF_YEAR, -7);
		return calendar.getTime();
	}

	/**
	 * 获取7天后的时间
	 * @return
	 */
	public static Date get7DayAfter(){
		Calendar calendar = getCalendar();
		calendar.add(Calendar.DAY_OF_YEAR, 7);
		return calendar.getTime();
	}

	/**
	 * 获取当前时间
	 * @return Calendar
	 */
	public static Calendar getCalendar(){
		return Calendar.getInstance();
	}

	/**
	 * 格式化字符串为标准时间
	 */
    public static Date parseDateTime(String dt) {
        try {
			return STANDARD_FORMAT.parse(dt);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
    }
    /**
	 * 格式化字符串为时间
	 */
    public static Date parseDate(String dt) {
        try {
			return SIMPLE_FORMAT.parse(dt);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
    }
	/**
	 * 标准格式化时间
	 *
	 * @param date
	 * @return
	 */
	public static String formatSimple(Date date) {
		return SIMPLE_FORMAT.format(date);
	}
	/**
	 * 标准格式化时间
	 *
	 * @param date
	 * @return
	 */
	public static String formatSimple(long milliseconds) {
		Calendar calendar = getCalendar();
		calendar.setTimeInMillis(milliseconds);
		return SIMPLE_FORMAT.format(calendar.getTime());
	}

	/**
	 * 获取指定时间的一周开始时间和结束时间
	 * @param calendar
	 * @return 0为本周第一天，1为本周最后一天
	 */
	public static Date[] getWeekDays(Calendar calendar){
		Date[] weekDays = new Date[2];
		int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 2;
		calendar.add(Calendar.DATE, -day_of_week);
		weekDays[0] = calendar.getTime();

		calendar.add(Calendar.DATE, 6);
		weekDays[1] = calendar.getTime();

		return weekDays;
	}

	/**
	 * 获取指定时间的一周开始时间和结束时间
	 * @return 0为本周第一天，1为本周最后一天
	 */
	public static Date[] getWeekDays(){
		return getWeekDays(Calendar.getInstance());
	}

	/**
	 * 获取指定时间的上一周开始时间和结束时间
	 * @param calendar
	 * @return 0为上1周第一天，1为上1周最后一天
	 */
	public static Date[] getLastWeekDays(Calendar calendar){
		calendar.add(Calendar.DAY_OF_WEEK, -1);//设置前一周的而时间
		return getWeekDays(calendar);
	}
	/**
	 * 获取当前时间的上一周开始时间和结束时间
	 * @param calendar
	 * @return 0为上1周第一天，1为上1周最后一天
	 */
	public static Date[] getLastWeekDays(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, -7);//设置前一周的而时间
		return getWeekDays(calendar);
	}
}
