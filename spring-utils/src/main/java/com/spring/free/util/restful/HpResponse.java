package com.spring.free.util.restful;

import com.alibaba.fastjson.JSON;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by sea on 2017/9/14.
 */
@Getter
@Setter
@Builder
public class HpResponse<T> {
    private boolean success;
    private String msg = "";
    private T data;

    public String toJSON() {
        return JSON.toJSONString(this);
    }
}