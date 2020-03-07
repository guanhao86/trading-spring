package com.spring.free.config;

import com.spring.free.domain.QueryVO;
import com.spring.free.util.DateUtils;
import org.apache.commons.lang.StringUtils;

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
}
