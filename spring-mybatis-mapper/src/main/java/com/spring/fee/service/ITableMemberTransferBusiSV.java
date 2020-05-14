package com.spring.fee.service;

import com.github.pagehelper.PageInfo;
import com.spring.fee.model.TableMemberTransfer;

import java.util.Map;

/**
 * 转账记录服务
 */
public interface ITableMemberTransferBusiSV {

    TableMemberTransfer insert(TableMemberTransfer bo);

    TableMemberTransfer update(TableMemberTransfer bo);

    TableMemberTransfer delete(TableMemberTransfer bo);

    TableMemberTransfer select(TableMemberTransfer bo);

    PageInfo<TableMemberTransfer> queryListPage(TableMemberTransfer bo, Integer pageNum, Integer pageSize, Map<String, Object> map);
}
