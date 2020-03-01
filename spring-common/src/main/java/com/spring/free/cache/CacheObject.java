package com.spring.free.cache;

import java.io.Serializable;

/**
 * Created by sunyl on 2017/3/5.
 *
 * 缓存接口
 */
public interface CacheObject extends Serializable{


    /**
     * 业务类型
     * @return
     */
    String bizType();

    /**
     *
     * @return
     */
    String uid();




}
