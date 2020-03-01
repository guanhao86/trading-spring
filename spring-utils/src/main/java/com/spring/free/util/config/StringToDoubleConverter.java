package com.spring.free.util.config;

import com.spring.free.util.constraints.Global;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.math.BigDecimal;

/**
 * 字符串转日期的转换器
 * @author byshome
 * @version $Id: StringToDateConverter.java, v 0.1 2015年9月24日 下午7:19:41 byshome Exp $
 */
public class StringToDoubleConverter implements Converter<String, Double> {

    /**
     * @see Converter#convert(Object)
     */
    @Override
    public Double convert(String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        source = source.trim();
        if (source.contains(Global.PER)){
            String str = source.substring(2, source.length());
            return new BigDecimal(str).divide(new BigDecimal(100)).doubleValue();
        }
        return Double.parseDouble(source);
    }
}