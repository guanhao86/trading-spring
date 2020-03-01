package com.spring.free.manager;

import com.spring.free.base.BaseManager;
import com.spring.free.domain.News;
import com.spring.free.mapper.NewsMapper;
import org.springframework.stereotype.Repository;

@Repository
public class NewsManager extends BaseManager<NewsMapper, News> {
}
