package com.spring.fee.service;

import com.github.pagehelper.PageInfo;
import com.spring.fee.model.TableMemberLevelChangeDetail;

import java.util.Map;

/**
 * 会员级别变更明细表服务
 */
public interface ITableMemberLevelChangeDetailBusiSV {

    TableMemberLevelChangeDetail insert(TableMemberLevelChangeDetail bo);

    TableMemberLevelChangeDetail update(TableMemberLevelChangeDetail bo);

    TableMemberLevelChangeDetail delete(TableMemberLevelChangeDetail bo);

    TableMemberLevelChangeDetail select(TableMemberLevelChangeDetail bo);

    PageInfo<TableMemberLevelChangeDetail> queryListPage(TableMemberLevelChangeDetail bo, Integer pageNum, Integer pageSize, Map<String, Object> map);
}
