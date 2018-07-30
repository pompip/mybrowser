package cn.pompip.browser.util.date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
    static Logger logger = LoggerFactory.getLogger(DateTimeUtil.class);
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");

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
            return simpleDateFormat1.parse(dateStr);
        } catch (Exception e) {
            logger.info("格式化时间错误", e);
            return new Date();
        }
    }
}
