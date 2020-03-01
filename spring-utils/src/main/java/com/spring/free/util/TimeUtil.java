package com.spring.free.util;/**
 * Created by hengpu on 2019/3/7.
 */

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @ClassName TimeUtil
 * @Description //TODO
 * @Author hengpu
 * @Date 2019/3/7 16:51
 * @Version 1.0
 **/
public class TimeUtil {
    public static Timestamp plusDay(Date d ,int num){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currdate = format.format(d);

        Calendar ca = Calendar.getInstance();
        ca.setTime(d);
        ca.add(Calendar.DATE, num);// num为增加的天数，可以改变的
        d = ca.getTime();
        String enddate = format.format(d);
        enddate = enddate.substring(0, 10)+" 23:59:59";
        Timestamp ts = null;
        try {
            ts = new Timestamp(format.parse(enddate).getTime());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ts;
    }
    public static Date timestampToDate(Timestamp ts){
        Date date = new Date();
        try {
            date = ts;
            System.out.println(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }
}
