package ipower.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期工具类。
 * @author yangyong.
 * @since 2014-03-19.
 * */
public final class DateUtil {
	/**
	 * 取得指定日期所在周的第一天。
	 * @param date
	 * 	指定日期。
	 * @return
	 * 	所在周的第一天。
	 * */
	public static Date firstDayOfWeek(Date date){
		Calendar calendar = new GregorianCalendar();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());//monday
		return calendar.getTime();
	}
	/**
	 * 取得指定日期所在周的最后一天。
	 * @param date
	 * 	指定日期。
	 * @return
	 * 	所在周的最后一天。
	 * */
	public static Date lastDayOfWeek(Date date){
		Calendar calendar = new GregorianCalendar();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() + 6);//sunday
		return calendar.getTime();
	}
}