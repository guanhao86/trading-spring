package com.spring.free.config;

import com.spring.free.domain.QueryVO;
import com.spring.free.util.DateUtils;
import org.apache.commons.lang.StringUtils;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

public class CommonUtils {

    public static Map<String, Object> getStartEnd(QueryVO queryVO){
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotEmpty(queryVO.getStart()))
            map.put("start", DateUtils.parseDate(queryVO.getStart()+" 00:00:00"));
        if (StringUtils.isNotEmpty(queryVO.getEnd()))
            map.put("end", DateUtils.parseDate(queryVO.getEnd()+" 23:59:59"));
        return map;

    }

    public static String convert(Float d) {
        NumberFormat nf = NumberFormat.getInstance();
        // 是否以逗号隔开, 默认true以逗号隔开,如[123,456,789.36]
        nf.setGroupingUsed(false);
        // 结果未做任何处理
        return nf.format(d);
    }
}
