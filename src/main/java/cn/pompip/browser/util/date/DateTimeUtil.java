package cn.pompip.browser.util.date;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String getCurrentDateTimeStr() {
        return formatDate(new Date());
    }


    public static String formatDate(Date date) {
        return simpleDateFormat.format(date);
    }
    public static String formatDate(long date) {
        return simpleDateFormat.format(new Date(date));
    }

    public static Date string2Date(String dateStr) {
        try {
            return simpleDateFormat.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
            return new Date();
        }
    }
}
