package cn.pompip.browser.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
	private static SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
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
	public static String getCurrentDateStr()
	{
		return simpleDateFormat.format(new Date());
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
	public static String getYesterdayStr()
	{
		Date date=new Date();//取时间
		 Calendar calendar = new GregorianCalendar();
		 calendar.setTime(date);
		 calendar.add(calendar.DATE,-1);//把日期往后增加一天.整数往后推,负数往前移动
		 date=calendar.getTime(); //这个时间就是日期往后推一天的结果 
		 String dateString = simpleDateFormat.format(date);
		 return dateString;
	}
}
