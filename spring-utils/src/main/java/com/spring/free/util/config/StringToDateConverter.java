package com.spring.free.util.config;

import com.spring.free.util.constraints.Global;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 字符串转日期的转换器
 * @author byshome
 * @version $Id: StringToDateConverter.java, v 0.1 2015年9月24日 下午7:19:41 byshome Exp $
 */
public class StringToDateConverter implements Converter<String, Date> {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String SHORT_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * @see Converter#convert(Object)
     */
//    @Override
    @Override
    public Date convert(String source) {
        String regex = "^\\d+$";
        if (StringUtils.isBlank(source)) {
            return null;
        }
        source = source.trim();
        try {
            if (source.contains(Global.SEPARATOR_BAR)) {
                SimpleDateFormat formatter;
                if (source.contains(Global.SEPARATOR_COLON)) {
                    formatter = new SimpleDateFormat(DATE_FORMAT);
                } else {
                    formatter = new SimpleDateFormat(SHORT_DATE_FORMAT);
                }
                Date dtDate = formatter.parse(source);
                return dtDate;
            } else if (source.matches(regex)) {
                Long lDate = new Long(source);
                return new Date(lDate);
            }
        } catch (Exception e) {
            throw new RuntimeException(String.format("parser %s to Date fail", source));
        }
        throw new RuntimeException(String.format("parser %s to Date fail", source));
    }

}