package com.spring.free.util;


import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/26 0026.
 */
public  class CreateSerialNo {

    private static  Map<String,String> map=new HashMap<String, String>();
    private static String STATNUM="01";

    /**
     * 获取年月日
     * @return
     */
    public String getTime(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        return df.format(cal.getTime());
    }

    /**
     * 判断序号是否到了最后一个
     * @param s
     * @return
     */
    public String getLastSixNum(String s){
        int maxLength=2;
        String rs=s;
        int i=Integer.parseInt(rs);
        i+=1;
        rs=""+i;
        for (int j = rs.length(); j <maxLength; j++) {
            //rs="0"+rs;
            //直接使用StringUtils类的leftPad方法处理补零
            rs = StringUtils.leftPad(rs,j+1, "0");
        }
        return rs;
    }

    /**
     * 产生不重复的号码  加锁
     * @return
     */
    public synchronized  String getNum(){
        String yearAMon = getTime();
        String last2Num=map.get(yearAMon);
        if(last2Num==null){
            map.put(yearAMon,STATNUM);
        }else{
            map.put(yearAMon,getLastSixNum(last2Num));
        }
        return map.get(yearAMon)+yearAMon;
    }

}

