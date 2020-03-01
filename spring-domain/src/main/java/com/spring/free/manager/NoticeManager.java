package com.spring.free.manager;

import com.spring.free.base.BaseManager;
import com.spring.free.domain.Notice;
import com.spring.free.mapper.NoticeMapper;
import org.springframework.stereotype.Repository;

@Repository
public class NoticeManager extends BaseManager<NoticeMapper, Notice> {
}
