package com.spring.fee.service;

import com.github.pagehelper.PageInfo;
import com.spring.fee.model.TableInvest;

import java.util.Map;

/**
 * 充值申请&审核管理表服务
 */
public interface ITableInvestBusiSV {

    TableInvest insert(TableInvest bo);

    TableInvest update(TableInvest bo);

    TableInvest delete(TableInvest bo);

    TableInvest select(TableInvest bo);

    PageInfo<TableInvest> queryListPage(TableInvest bo, Integer pageNum, Integer pageSize, Map<String, Object> map);
}
