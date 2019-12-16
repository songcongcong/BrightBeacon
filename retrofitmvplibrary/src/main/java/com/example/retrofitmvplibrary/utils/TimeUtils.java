package com.example.retrofitmvplibrary.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 作者：宋聪聪 on 2019/6/19.
 */

public class TimeUtils {
    public static final  int VIEW_THROTTLE_TIME = 50;

    private static final long ONE_DAY = 86400000;
    private static final long ONE_HOUR = 3600000;
    private static final long ONE_MINUTE = 60000;

    /**
     * 秒数转化为日期
     */
    public static String getDateFromSeconds(String seconds) {
        if (seconds == null)
            return " ";
        else {
            Date date = new Date();
            try {
                date.setTime(Long.parseLong(seconds));
            } catch (NumberFormatException nfe) {

            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(date);
        }
    }

    public static Date secondToDate(long seconds) {
        Date date = new Date();
        date.setTime(Long.parseLong(String.valueOf(seconds)));
        return date;
    }

    /**
     * 日期转换成秒数
     */
    public static long getSecondsFromDate(String expireDate) {
        if (expireDate == null || expireDate.trim().equals(""))
            return 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(expireDate);
            return (long) (date.getTime() / 1000);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 获取目标时间和当前时间之间的差距
     *
     * @param seconds
     * @return
     */
    public static String getTimestampString(long seconds) {
        Date date = secondToDate(seconds);
        Date curDate = new Date();
        long splitTime = curDate.getTime() - date.getTime();
//        Tools.Log("当前时间:" + curDate.getTime() + "发布时间:" + date.getTime() + "时间差:" + splitTime);
        if (splitTime < (ONE_DAY)) {
            if (splitTime < ONE_MINUTE) {
                return "刚刚";
            }
            if (splitTime < ONE_HOUR) {
                return String.format("%d分钟前", splitTime / ONE_MINUTE);
            }

            return String.format("%d小时前", splitTime / ONE_HOUR);
        }
        String result;
        result = "yyyy-MM-dd HH:mm";
        return (new SimpleDateFormat(result, Locale.CHINA)).format(date);
    }
}
