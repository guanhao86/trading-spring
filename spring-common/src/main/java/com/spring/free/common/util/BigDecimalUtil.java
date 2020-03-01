package com.spring.free.common.util;/**
 * Created by hengpu on 2019/5/20.
 */

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @ClassName BigDecimalUtil
 * @Description //TODO
 * @Author hengpu
 * @Date 2019/5/20 16:01
 * @Version 1.0
 **/
public class BigDecimalUtil {

    public static double multiply(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        BigDecimal b3 = b1.multiply(b2);//此处举例使用乘法（注意：必须使用一个新的BigDecimal来获得运算后的值）
        Double b4 = b3.doubleValue();
        return b4;//可直接使用这个值，set、输出都可以（部分步骤可简化）
    }

    public static String multiplyToStr(double v1, double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        BigDecimal b3 = b1.multiply(b2);//此处举例使用乘法（注意：必须使用一个新的BigDecimal来获得运算后的值）
        Double b4 = b3.doubleValue();
        DecimalFormat df = new DecimalFormat("0.00");
        String str = df.format(b4);
        return str;//可直接使用这个值，set、输出都可以（部分步骤可简化）
    }

    public static String multiplyToStrDown(double v1, double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        BigDecimal b3 = b1.multiply(b2);//此处举例使用乘法（注意：必须使用一个新的BigDecimal来获得运算后的值）
        BigDecimal setScale = b3.setScale(2,BigDecimal.ROUND_DOWN);

        return setScale.toString();//可直接使用这个值，set、输出都可以（部分步骤可简化）
    }

}
