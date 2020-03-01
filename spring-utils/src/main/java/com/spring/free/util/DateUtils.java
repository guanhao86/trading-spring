package com.spring.free.util;

import com.google.common.collect.Lists;
import com.spring.free.util.constraints.Global;
import org.apache.commons.lang3.time.DateFormatUtils;

import javax.sql.rowset.serial.SerialException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by spink on 2017/4/8.
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    public static final String FORMAT = "yyyy-MM-dd";

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd）
     */
    public static String getDate() {
        return getDate("yyyy-MM-dd");
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    public static Date getSysDate() {
        return new Date();
    }

    /**
     * 获取当天（今日）零点零分零秒
     * @param date
     * @return
     */
    public static Date getDateZero(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date getLastDate() {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) - 1);
        Date endDate = null;
        try {
            endDate = dft.parse(dft.format(date.getTime()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return endDate;
    }

    /**
     * 获取月份的第一天
     * @return
     */
    public static Date getFirstDayOfMonth() {
        // 获取前月的第一天
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获取月份的最后一天
     * @return
     */
    public static Date getLastDayOfMonth(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        return calendar.getTime();
    }

    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String formatDate(Date date, Object... pattern) {
        String formatDate = null;
        if (pattern != null && pattern.length > 0) {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }


    /**
     * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String formatDateTime(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前时间字符串 格式（HH:mm:ss）
     */
    public static String getTime() {
        return formatDate(new Date(), "HH:mm:ss");
    }

    /**
     * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String getDateTime() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前年份字符串 格式（yyyy）
     */
    public static String getYear() {
        return formatDate(new Date(), "yyyy");
    }

    /**
     * 得到当前月份字符串 格式（MM）
     */
    public static String getMonth() {
        return formatDate(new Date(), "MM");
    }

    /**
     * 得到当天字符串 格式（dd）
     */
    public static String getDay() {
        return formatDate(new Date(), "dd");
    }

    /**
     * 得到当前星期字符串 格式（E）星期几
     */
    public static String getWeek() {
        return formatDate(new Date(), "E");
    }

    /**
     * 日期型字符串转化为日期 格式
     * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
     * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
     * "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取过去的天数
     *
     * @param date
     * @return
     */
    public static long pastDays(Date date) {
        long t = System.currentTimeMillis() - date.getTime();
        return t / (24 * 60 * 60 * 1000);
    }

    /**
     * 获取过去的小时
     *
     * @param date
     * @return
     */
    public static long pastHour(Date date) {
        long t = System.currentTimeMillis() - date.getTime();
        return t / (60 * 60 * 1000);
    }

    /**
     * 获取过去的分钟
     *
     * @param date
     * @return
     */
    public static long pastMinutes(Date date) {
        long t = System.currentTimeMillis() - date.getTime();
        return t / (60 * 1000);
    }

    public static long pastMinutes(Date date, String currDate, String format) {
        long t = strToDate(currDate, format).getTime() - date.getTime();
        return t / (60 * 1000);
    }

    /**
     * 转换为时间（天,时:分:秒.毫秒）
     *
     * @param timeMillis
     * @return
     */
    public static String formatDateTime(long timeMillis) {
        long day = timeMillis / (24 * 60 * 60 * 1000);
        long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
        long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
        return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
    }

    /**
     * 获取两个日期之间的天数
     *
     * @param before
     * @param after
     * @return
     */
    public static double getDistanceOfTwoDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
    }

    public static boolean isWeekend(Calendar cal) {
        int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        //0代表周日，6代表周六
        if (week == Global.NUMBER_SIX  || week == Global.NUMBER_ZERO) {
            return true;
        }
        return false;
    }


    public static Date strToDate(String str, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date getNextDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        return cal.getTime();
    }

    public static String getWeek(Date date) {
        String[] weeks = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int weekIndex = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (weekIndex < 0) {
            weekIndex = 0;
        }
        return weeks[weekIndex];
    }

    /**
     * 获得周一的日期
     *
     * @param date
     * @return
     */
    public static String getMonday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT);
        return dateFormat.format(calendar.getTime());
    }

    /**
     * 获得周二的日期
     *
     * @param date
     * @return
     */
    public static String getTuesday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT);
        return dateFormat.format(calendar.getTime());
    }

    /**
     * 获得周三的日期
     *
     * @param date
     * @return
     */
    public static String getWednesday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT);
        return dateFormat.format(calendar.getTime());
    }

    /**
     * 获得周四的日期
     *
     * @param date
     * @return
     */
    public static String getThursday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT);
        return dateFormat.format(calendar.getTime());
    }

    /**
     * 获得周五的日期
     *
     * @param date
     * @return
     */
    public static String getFriday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT);
        return dateFormat.format(calendar.getTime());
    }

    /**
     * 获得周六的日期
     *
     * @param date
     * @return
     */
    public static String getSaturday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT);
        return dateFormat.format(calendar.getTime());
    }

    /**
     * 获得周日的日期
     *
     * @param date
     * @return
     */
    public static String getSunday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT);
        return dateFormat.format(calendar.getTime());
    }

    /**
     * 当前日期前几天或者后几天的日期
     *
     * @param n
     * @return
     */
    public static String afterNDay(int n) {

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(new Date());

        calendar.add(Calendar.DATE, n);

        Date date = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT);
        String s = dateFormat.format(date);

        return s;

    }

    /**
     * 判断两个日期是否在同一周
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameWeekDates(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
        if (Global.NUMBER_ZERO == subYear) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2
                    .get(Calendar.WEEK_OF_YEAR)) {
                return true;
            }
        } else if (Global.NUMBER_ONE == subYear && Global.NUMBER_ELEVEN == cal2.get(Calendar.MONTH)) {
            // 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2
                    .get(Calendar.WEEK_OF_YEAR)) {
                return true;
            }
        } else if (-1 == subYear && Global.NUMBER_ELEVEN == cal1.get(Calendar.MONTH)) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2
                    .get(Calendar.WEEK_OF_YEAR)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据开始时间和结束时间返回时间段内的时间集合
     *
     * @param beginDate
     * @param endDate
     * @return List
     */
    public static List<Date> getDatesBetweenTwoDate(Date beginDate, Date endDate) {
        List<Date> lDate = Lists.newArrayList();
        // 把开始时间加入集合
        lDate.add(beginDate);
        Calendar cal = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        cal.setTime(beginDate);
        boolean bContinue = true;
        while (bContinue) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cal.add(Calendar.DAY_OF_MONTH, 1);
            // 测试此日期是否在指定日期之后
            if (endDate.after(cal.getTime())) {
                lDate.add(cal.getTime());
            } else {
                break;
            }
        }
        // 把结束时间加入集合
        lDate.add(endDate);
        return lDate;
    }

    public static List<String> setTimes(String startTime, String endTime) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dBegin = sdf.parse(startTime);
        Date dEnd = sdf.parse(endTime);
        List<Date> listDate = getDatesBetweenTwoDate(dBegin, dEnd);
        if (listDate == null || listDate.size() > Global.NUMBER_SEVEN){
            throw new SerialException("只能选择七天的数据");
        }
        List<String> times = Lists.newArrayList();
        for (Date date : listDate) {
            String strDate = formatDate(date, "yyyyMMdd");
            times.add(strDate);
        }
        return times;
    }

    public static boolean compareDate(String date1, String date2, String format) {
        DateFormat df = new SimpleDateFormat(format);
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                System.out.println("dt1 在dt2前");
                return false;
            } else if (dt1.getTime() < dt2.getTime()) {
                System.out.println("dt1在dt2后");
                return true;
            } else {
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public static boolean compareDateEqual(String date1, String date2, String format) {
        DateFormat df = new SimpleDateFormat(format);
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() == dt2.getTime()) {
                return true;
            }
            return false;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }
    public static boolean compareDateSmall(String date1, String date2, String format) {
        DateFormat df = new SimpleDateFormat(format);
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() <= dt2.getTime()) {
                return true;
            }
            return false;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }
    public static String getYYYYMMDD(Date date){
        String y = (date.getYear()+1900)+"";
        String m = String.valueOf((date.getMonth()+1)<10?"0"+(date.getMonth()+1):(date.getMonth()+1));
        String d = String.valueOf((date.getDate()<10?"0"+date.getDate():date.getDate()));

        return y+m+d;
    }
}
