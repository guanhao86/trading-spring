package com.spring.free.common.domain;

import com.alibaba.fastjson.JSON;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by hengpu on 2019/1/7.
 */
@Getter
@Setter
@Builder
public class AccessResponse<T> {

    private T data;
    private boolean success ;
    private int rspcode = 200 ;
    private String message = "";

    public String toJSON(){
        return JSON.toJSONString(this);
    }

}