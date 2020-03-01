package com.spring.fee.service;

import com.github.pagehelper.PageInfo;
import com.spring.fee.model.TableSystemConfig;

import java.util.Map;

/**
 * 平台参数服务
 */
public interface ITableSystemConfigBusiSV {

    TableSystemConfig insert(TableSystemConfig bo);

    TableSystemConfig update(TableSystemConfig bo);

    TableSystemConfig delete(TableSystemConfig bo);

    TableSystemConfig select(TableSystemConfig bo);

    PageInfo<TableSystemConfig> queryListPage(TableSystemConfig bo, Integer pageNum, Integer pageSize, Map<String, Object> map);
}
