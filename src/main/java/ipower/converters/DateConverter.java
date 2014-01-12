package ipower.converters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.util.StrutsTypeConverter;

/**
 * 日期转换器
 * struts2默认自带日期转换器，但是如果前台传递的日期字符串是 null 的话，会出现错误，
 * 所以这里写了一个公共的日期转换器来替代默认的。
 * @author young.
 * @since 2013-08-19.
 * */
public class DateConverter extends StrutsTypeConverter {
	private static final Logger logger = Logger.getLogger(DateConverter.class);
	private static final String FROMDATE = "yyyy-MM-dd",FROMDATETIME = "yyyy-MM-dd HH:mm:ss";

	@SuppressWarnings("rawtypes")
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		if(values == null || values.length == 0) return null;
		String dateString = values[0];
		logger.info("需转换的时间:"+ dateString);
		if(dateString != null && !dateString.isEmpty() && !dateString.trim().equalsIgnoreCase("null")){
			try {
				if(dateString.length() == 10){
					logger.info("使用格式：" + FROMDATE);
					return new SimpleDateFormat(FROMDATE).parse(dateString);
				}
				logger.info("使用格式：" + FROMDATETIME);
				return new SimpleDateFormat(FROMDATETIME).parse(dateString);
			} catch (ParseException e) {
				logger.error(e);
				e.printStackTrace();
			}
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String convertToString(Map context, Object o) {
		if(o instanceof Date){
			SimpleDateFormat sdf = new SimpleDateFormat(FROMDATETIME);
			return sdf.format(o);
		}
		return "";
	}

}