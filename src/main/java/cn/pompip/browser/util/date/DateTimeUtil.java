package cn.pompip.browser.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
	private static SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static String getCurrentDateTimeStr()
	{
		return formatDate(new Date());
	}
	
	public static Date getCurrentDate()
	{
		String toDay=DateUtil.getCurrentDateStr()+" 00:00:00";
		return string2Date(toDay);
	}
	
	public static String formatDate(Date date)
	{
		if(date!=null)
		{
			return simpleDateFormat.format(date);
		}
		else
		{
			return "";
		}
	}
	public static Date string2Date(String dateStr)
	{
		if(dateStr!=null&&!"".equals(dateStr))
		{
			try {
				return simpleDateFormat.parse(dateStr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		else
		{
			return null;
		}
	}
}
