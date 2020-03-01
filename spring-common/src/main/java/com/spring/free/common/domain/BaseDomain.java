package com.spring.free.common.domain;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by sunyl on 2017/2/4.
 */

@Setter
@Getter
public class BaseDomain  implements Serializable{

    private Date modifyTime;
    private Date createTime;
    /**
     * 数据库乐观锁
     */
    private long version=200;

    public String toJson(){
        return JSON.toJSONString(this);
    }

}
