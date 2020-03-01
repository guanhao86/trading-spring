package com.spring.free.utils.velocity;

import com.alibaba.druid.util.StringUtils;
import com.google.common.collect.Maps;
import com.spring.free.domain.UserInfo;
import com.spring.free.util.DateUtils;
import com.spring.free.util.constraints.Global;
import com.spring.free.utils.principal.BaseGetPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.config.DefaultKey;
import org.apache.velocity.tools.config.ValidScope;
import org.springframework.cglib.beans.BeanMap;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Velocity 模板引擎，字符串工具类
 *
 * @author Memory
 * @version 1.0
 */
@Slf4j
@DefaultKey("StringTool")
@ValidScope(Scope.APPLICATION)
public class StringTool {

    private static Map<String, String> map = Maps.newHashMap();

    /**
     * 超长截取
     *
     * @param str    需要截取的字符串
     * @param length 需要截取的长度
     * @return
     */
    public String overLength(String str, int length) {
        if (str.length() > length) {
            str = str.substring(0, length) + "...";
        }
        return str;
    }

    /**
     * 局部字符串加密
     *
     * @param str      字符串
     * @param frontLen 开始截取位数
     * @param endLen   结束截取位数
     * @return
     */
    public String partEncryption(String str, int frontLen, int endLen) {
        String sy = "";
        if (!StringUtils.isEmpty(str)){
            if (str.length() > (endLen + frontLen)){
                int len = str.length() - frontLen - endLen;
                for (int i = 0; i < len; i++) {
                    sy += "*";
                }
                return str.substring(0, frontLen) + sy + str.substring(str.length() - endLen);
            } else {
                int len = str.length();
                for (int i = 0; i < len; i++) {
                    sy += "*";
                }
                return sy;
            }

        }
        return str.substring(0, 0) + sy + str.substring(0);
    }

    /**
     * 全部字符串加密
     *
     * @param str 字符串
     * @return
     */
    public String wholeEncryption(String str) {
        return str.replaceAll("[\\s\\S]*", "*");
    }

    /**
     * 获取目录
     *
     * @return
     */
    public String adminPath() {
        return Global.ADMIN_PATH;
    }

    /**
     * 获取选项卡上线数量
     * @return
     */
    public String pageTabsNumber() {
        return "10";
    }

    /**
     * 图片上传限制
     * @return
     */
    public String imageLimit(){

        return "6";
    }

    public String getCurrTime(String format) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(currentTime);
    }

    public UserInfo getUser(){
        return BaseGetPrincipal.getUser();
    }

    public String getUserName(String username) {
        return BaseGetPrincipal.getUser(username).getName();
    }

    public String format(String number) {
        if (StringUtils.isEmpty(number)){
            number = "0";
        }
        double number2 = Double.valueOf(number);
        NumberFormat format = NumberFormat.getNumberInstance();
        format.setMinimumFractionDigits(2);
        format.setMaximumFractionDigits(2);
        return format.format(number2);
    }

    public boolean afterTrading(){

        return true;
    }

    public Map<String, Object> macroTest(Object object){
        Map<String, Object> map = Maps.newHashMap();
        if (object != null) {
            BeanMap beanMap = BeanMap.create(object);
            for (Object key : beanMap.keySet()) {
                map.put(key+"", beanMap.get(key));
            }
        }
        return map;
    }

    public long formatLong(String dateStr){
        if (!StringUtils.isEmpty(dateStr)){
            return DateUtils.strToDate(dateStr, "yyyy-MM-dd HH:mm:ss").getTime();
        }
        return 0L;

    }

    public long getNumber(String number){
        if (!StringUtils.isEmpty(number)){
            return Long.parseLong(number);
        }
        return 0L;
    }

    public Map<String, String> getKeyCode(){
        if (map.isEmpty()){
            setMaps();
        }
        return map;
    }

    /**
     * 取绝对值
     * int类型
     * @param value
     * @return
     */
    public int abs(int value){
        return Math.abs(value);
    }

    /**
     * 取绝对值
     * long类型
     * @param value
     * @return
     */
    public long abs(long value){
        return Math.abs(value);
    }

    /**
     * 取绝对值
     * double类型
     * @param value
     * @return
     */
    public double abs(double value){
        return Math.abs(value);
    }

    /**
     * 取绝对值
     * float类型
     * @param value
     * @return
     */
    public float abs(float value){
        return Math.abs(value);
    }

    private void setMaps(){
        map.put("A", "CTRL + ALT + A");
        map.put("B", "CTRL + ALT + B");
        map.put("C", "CTRL + ALT + C");
        map.put("D", "CTRL + ALT + D");
        map.put("E", "CTRL + ALT + E");
        map.put("F", "CTRL + ALT + F");
        map.put("G", "CTRL + ALT + G");
        map.put("H", "CTRL + ALT + H");
        map.put("I", "CTRL + ALT + I");
        map.put("J", "CTRL + ALT + J");
        map.put("K", "CTRL + ALT + K");
        map.put("L", "CTRL + ALT + L");
        map.put("M", "CTRL + ALT + M");
        map.put("N", "CTRL + ALT + N");
        map.put("O", "CTRL + ALT + O");
        map.put("P", "CTRL + ALT + P");
        map.put("Q", "CTRL + ALT + Q");
        map.put("R", "CTRL + ALT + R");
        map.put("S", "CTRL + ALT + S");
        map.put("T", "CTRL + ALT + T");
        map.put("U", "CTRL + ALT + U");
        map.put("V", "CTRL + ALT + V");
        map.put("W", "CTRL + ALT + W");
        map.put("X", "CTRL + ALT + X");
        map.put("Y", "CTRL + ALT + Y");
        map.put("Z", "CTRL + ALT + Z");

        map.put("1", "CTRL + ALT + 1");
        map.put("2", "CTRL + ALT + 2");
        map.put("3", "CTRL + ALT + 3");
        map.put("4", "CTRL + ALT + 4");
        map.put("5", "CTRL + ALT + 5");
        map.put("6", "CTRL + ALT + 6");
        map.put("7", "CTRL + ALT + 7");
        map.put("8", "CTRL + ALT + 8");
        map.put("9", "CTRL + ALT + 9");
        map.put("0", "CTRL + ALT + 0");

    }

    public String  scientificToNumber(String  scientific){
        log.info("参数：{}", scientific);
        if (StringUtils.isEmpty(scientific)){
            return "0";
        }
        BigDecimal bd = new BigDecimal(scientific);
        if (bd == null) {
            return "0";
        }
        log.info("BigDecimal：{}", bd);
        return bd.toPlainString();
    }

    public String getRealData(String num) {
        log.info("参数：{}", num);
        BigDecimal bd = new BigDecimal(num);
        if (bd == null) {
            return "0";
        }
        String value = bd.stripTrailingZeros().toString();
        return value;
    }

}
