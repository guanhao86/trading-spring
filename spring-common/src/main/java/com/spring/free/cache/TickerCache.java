package com.spring.free.cache;

import org.redisson.api.RMap;

/**
 * Created by hengpu on 2018/9/14.
 */
public interface TickerCache {
    void textAdd(String key, CacheObject cacheObject);
    void textUpdate(String key, CacheObject cacheObject);
    void textDel(String key, CacheObject cacheObject);
    CacheObject textQuery(String key, CacheObject cacheObject);
    RMap<String, CacheObject> textQuery(CacheObject cacheObject);
}
