package org.wustrive.java.common.util;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.google.common.collect.Lists;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class DateTimeUtils {
    protected static final Log log = LogFactory.get(DateTimeUtils.class);

    /**
     * 时间格式，如：2009-08-02 13:43
     */
    public static final String pattern_yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";
    /**
     * 时间格式，如：2009-08-02 13:43
     */
    public static final String pattern_yyyy_MMdd_HH_mm = "yyyy-MM-ddHH:mm";
    /**
     * 时间格式，如：2009-08-02 13:43:13
     */
    public static final String pattern_yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    /**
     * 时间格式，如：2009-08-02
     */
    public static final String pattern_yyyy_MM_dd = "yyyy-MM-dd";

    /**
     * 时间格式，如：20090802
     */
    public static final String pattern_yyyyMMdd = "yyyyMMdd";
    /**
     * 时间格式，如：13:43
     */
    public static final String pattern_HH_mm = "HH:mm";
    /**
     * 日期时间格式化字符串
     */
    public static final String DATE_TIME_FORMAT_STR_1 = "yyyyMM";

    /**
     * 时间格式，如：20170104104945777
     */
    public static final String pattern_yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";


    /**
     * 时间格式，如：201701041049
     */
    public static final String pattern_yyyyMMddHHmm = "yyyyMMddHHmm";

    static final long MILLISECONDS_PER_DAY = 24L * 3600 * 1000;

    public static Date parseStringToDate(String pattern, String dateString) {
        DateFormat df1 = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = df1.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    /**
     * 格式化Unix时间戳
     */
    public static String formatUnixTimeForDefaultFormat(Long unixTime) {
        return formatUnixTime(DateTimeUtils.pattern_yyyy_MM_dd, unixTime);
    }

    /**
     * 格式化Unix时间戳
     */
    public static String formatUnixTime(String format, Long unixTime) {
        String dateStr = DateTimeUtils.parseDateToString(format, new Date((long) (unixTime * 1000)));
        return dateStr;
    }

    /**
     * 日期字符串转为UnixTime时间戳
     */
    public static Long parseDateToUnixTime(String format, String source) {
        DateFormat df = new SimpleDateFormat(format);
        Date date = new Date();
        try {
            date = df.parse(source);
        } catch (ParseException e) {
            log.error("日期时间格式错误");
            e.printStackTrace();
        }
        return date.getTime() / 1000;
    }

    /**
     * 根据规则转换成指定的时间字符串
     *
     * @param pattern
     * @param date
     * @return
     */
    public static String parseDateToString(String pattern, Date date) {
        DateFormat df1 = new SimpleDateFormat(pattern);
        return df1.format(date);
    }

    /**
     * 获取days天后的日期
     *
     * @param days
     * @return
     */
    public static Date getEndDateOfTodayByDays(int days) {
        long now = System.currentTimeMillis();

        return new Date(now + days * 24 * 60 * 60 * 1000);
    }

    /**
     * 计算指定天数的毫秒数
     *
     * @param days
     * @return
     */
    static long convertDaysToMilliseconds(int days) {
        return days * MILLISECONDS_PER_DAY;
    }

    /**
     * 更新时间的计算
     *
     * @param reflshDate
     * @return
     */
    public static String Datetochange(Date reflshDate) {
        if (reflshDate == null)
            return "";

        Calendar c = Calendar.getInstance();

        c.setTime(reflshDate);

        c.set(c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH),
                c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE),
                c.get(Calendar.SECOND));

        Long time = (new Date().getTime()) - (c.getTimeInMillis());

        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = time / dd;
        long hour = (time - day * dd) / hh;
        long minute = (time - day * dd - hour * hh) / mi;

        String strDay = "" + day;
        String strHour = "" + hour;
        String strMinute = "" + minute;
        StringBuffer leavetime = new StringBuffer();
        if (!strDay.equals("0")) {
            leavetime.append(strDay + "天");
        }
        if (!strHour.equals("0")) {
            leavetime.append(strHour + "小时");
        }
        leavetime.append(strMinute + "分钟前");
        return leavetime.toString();
    }

    public static String DateLoginChange(Date loginDate) {
        if (loginDate == null)
            return "";

        Calendar c = Calendar.getInstance();

        c.setTime(loginDate);

        c.set(c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH),
                c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE),
                c.get(Calendar.SECOND));

        Long time = (new Date().getTime()) - (c.getTimeInMillis());

        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = time / dd;
        long hour = (time - day * dd) / hh;
        long minute = (time - day * dd - hour * hh) / mi;

        StringBuffer leavetime = new StringBuffer();
        if (day == 0 && hour == 0 && minute < 10) {
            leavetime.append("刚刚来过");
        } else {
            String strDay = "" + day;
            String strHour = "" + hour;
            String strMinute = "" + minute;

            if (!strDay.equals("0")) {
                leavetime.append(strDay + "天");
            }
            if (!strHour.equals("0")) {
                leavetime.append(strHour + "小时");
            }
            leavetime.append(strMinute + "分钟前");
        }
        return leavetime.toString();
    }

    /**
     * 获取今天的开始时间
     *
     * @return
     */
    public static Date getBeginDateTimeOfToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }


    /**
     * 获取n天前的开始时间
     *
     * @param date
     * @return
     */
    public static Date getBeginDaysByInt(int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -n);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }


    /**
     * 获取n天前的结束时间
     *
     * @param n
     * @return
     */
    public static Date getEndDaysByInt(int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -n);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 获取n小时前的开始时间
     *
     * @param n
     * @return
     */
    public static Date getBeginHourByInt(int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -n);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * getBeforeDate(当前时间提前几个小时)
     * (这里描述这个方法适用条件 – 可选)
     *
     * @param hours
     * @return Date
     * @throws
     * @since 1.0.0
     */
    public static Date getBeforeDate(int hours) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar.add(Calendar.HOUR, -hours);
        return calendar.getTime();
    }

    /**
     * 获取今天的结束时间
     *
     * @return
     */
    public static Date getEndDateTimeOfToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date getHalfOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 15);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date getOneOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date getOneHalfOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 45);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date getTowOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 60);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    @SuppressWarnings("deprecation")
    public static String getYearNo(Date date) {
        int year = date.getYear();
        String yearNo = (year + "").substring(1);
        return yearNo;
    }

    @SuppressWarnings("deprecation")
    public static String getMonthNo(Date date) {
        int month = date.getMonth();
        String monthNo = month + 1 + "";
        if (month < 10) {
            monthNo = "0" + monthNo;
        }
        return monthNo;
    }

    public static String getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return String.valueOf(calendar.get(Calendar.YEAR));
    }

    public static String getMonthDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return String.valueOf(calendar.get(Calendar.MONTH) + 1)
                + String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
    }

    public static String getFormatMonthDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return String.valueOf(calendar.get(Calendar.MONTH) + 1)
                + "/"
                + String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 通过字符串创建一个Date对象
     *
     * @param time 格式如"2010-06-11"
     * @return date
     * @throws ParseException
     */
    public static Date formatStringToDate(String time) throws ParseException {
        DateFormat sdf = DateFormat.getDateInstance();

        return sdf.parse(time);
    }

    public static String formatDateToString(String pattern, Date date) {

        if (null == date) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        return sdf.format(date);
    }

    public static Date formatStringToDate(String pattern, String time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        return sdf.parse(time);
    }

    /**
     * 获取某天的结束时间
     *
     * @return
     */
    public static Date getEndDateTimeOfDate(Date date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    // 获得当前日期与本周日相差的天数
    private static int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
        if (dayOfWeek == 1) {
            return 0;
        } else {
            return 1 - dayOfWeek;
        }
    }

    /**
     * 获取本周的开始时间
     *
     * @return
     */
    public static Date getBeginWeekTimeOfToday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -(-getMondayPlus()));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 根据日期获取3天前的开始时间
     *
     * @param date
     * @return
     */
    public static Date getBeginThreeDays(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -2);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 根据日期获取两月前的开始时间
     *
     * @param date
     * @return
     */
    public static Date getBeginOfTwoMounth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, 1);
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTime();
    }

    /**
     * 根据日期获得一季度的开始时间
     *
     * @param date
     * @return
     */
    public static Date getBeginOfFirstQuarter(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTime();
    }

    /**
     * 根据日期获取当月的开始时间
     *
     * @param date
     * @return
     */
    public static Date getBeginOfMounth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        return calendar.getTime();
    }

    /**
     * 根据所给日期获取当前月的最后时间
     *
     * @param date
     * @return
     */
    public static Date getEndOfMounth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    public static String getStringCallLong(long callLong) {
        if (0 != callLong) {
            if (callLong < 60) {
                return "00:" + callLong;
            } else if (callLong > 60 && callLong < 3600) {
                return callLong / 60 + ":" + callLong % 60;
            } else {
                return (8000 / 60) / 60 + ":" + (8000 / 60) % 60 + ":" + 8000 % 60;
            }
        }
        return null;
    }

    /**
     * 根据日期获取1天前的20点
     *
     * @param date
     * @return
     */
    public static Date getTwentyClickOfYesterday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 20);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 根据日期获取90分钟(5400秒)前的时间
     *
     * @param date
     * @return
     */
    public static Date getNinetyMinutesLater(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.SECOND, -5400);
        return calendar.getTime();
    }

    /**
     * 获取今天的开始时间
     *
     * @return
     */
    public static String getBeginTimeOfToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return formatDateToString(pattern_yyyy_MM_dd_HH_mm_ss, calendar.getTime());
    }

    /**
     * 获取今天的结束时间
     *
     * @return
     */
    public static String getEndTimeOfToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DATE, 1);
        calendar.add(Calendar.SECOND, -1);
        return formatDateToString(pattern_yyyy_MM_dd_HH_mm_ss, calendar.getTime());
    }


    /**
     * 判断时间是否在今天
     *
     * @param time
     * @return
     */
    public static boolean isToday(Date time) {
        Date begin = getBeginDateTimeOfToday();
        Date end = getEndDateTimeOfToday();
        if (begin.getTime() <= time.getTime() && end.getTime() >= time.getTime()) {
            return true;
        }
        return false;
    }


    /**
     * 判断时间是否在昨天
     *
     * @param time
     * @return
     */
    public static boolean isYestoday(Date time) {
        Date begin = getBeginDaysByInt(1);
        Date end = getEndDaysByInt(1);
        if (begin.getTime() <= time.getTime() && end.getTime() >= time.getTime()) {
            return true;
        }
        return false;
    }

    /**
     * 是否在昨天之前
     *
     * @param time
     * @return
     */
    public static boolean isBeforeYestoday(Date time) {
        Date begin = getBeginDaysByInt(2);
        if (begin.getTime() <= time.getTime()) {
            return true;
        }
        return false;
    }

    /**
     * 获取几年之后的时间  返回 UNIX时间戳
     *
     * @param years
     * @return
     */
    public static Long getTimeOfYearsLater(Integer years) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, years);
        Date y = c.getTime();
        return y.getTime() / 1000;
    }

    /**
     * 获取几年之后的时间  返回 UNIX时间戳
     *
     * @param years
     * @return
     */
    public static Long getTimeOfYearsLater(Long unixTime, Integer years) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(unixTime * 1000));
        c.add(Calendar.YEAR, years);
        Date y = c.getTime();
        return y.getTime() / 1000;
    }

    /**
     * 获取明天日期
     *
     * @return
     */
    public static String getTomorrowDate() {
        Date date = new Date();//取时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime(); //这个时间就是日期往后推一天的结果
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
        return dateString;
    }

    // 时间 格式:YYYYMMDDhhmmss
    public static String getCurrentTime() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    /**
     * 返回两个时间之间相差的分钟数
     *
     * @param time1
     * @param time2
     * @return
     */
    public static long getDifferMinter(long time1, long time2) {
        return (time1 - time2) / 1000 / 60;
    }


    /**
     * 获取一段连续的时间段(天)
     *
     * @param dBegin
     * @param dEnd
     * @return
     */
    public static List<Date> findContinueDates(Date dBegin, Date dEnd) {
        List lDate = new ArrayList();
        lDate.add(dBegin);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(calBegin.getTime());
        }
        return lDate;
    }

    public static List<String> getContinueDateOfString(String beginDate, String endDate) {
        try {
            Date bDate = new SimpleDateFormat(DateTimeUtils.pattern_yyyy_MM_dd).parse(beginDate);
            Date eDate = (new SimpleDateFormat(DateTimeUtils.pattern_yyyy_MM_dd)).parse(endDate);
            List<Date> dates = findContinueDates(bDate, eDate);
            List<String> res = Lists.newArrayList();
            dates.stream().forEach(date -> res.add(new SimpleDateFormat(DateTimeUtils.pattern_yyyy_MM_dd).format(date)));
            return res;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据结束日期和天数向前取连续的日期
     *
     * @param endDate
     * @param days
     * @return
     */
    public static List<String> getContinueDateOfNums(String endDate, Integer days) {
        Date dEnd = parseStringToDate(DateTimeUtils.pattern_yyyy_MM_dd, endDate);
        List<String> res = Lists.newArrayList();
        res.add(endDate);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        for (int i = 0; i < days; i++) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calEnd.add(Calendar.DAY_OF_MONTH, -1);
            res.add(parseDateToString(DateTimeUtils.pattern_yyyy_MM_dd, calEnd.getTime()));
        }
        //列表顺序翻转
        Collections.sort(res);
        return res;

    }

    public static void main(String[] args) {
        //System.out.println(parseDateToUnixTime("yyy-dd-MM", "2017-02-01"));
        System.out.println(getDifferMinter(new Date().getTime(), parseStringToDate(DateTimeUtils.pattern_yyyy_MM_dd_HH_mm_ss, "2017-07-08 17:20:00").getTime()));
    }

}
