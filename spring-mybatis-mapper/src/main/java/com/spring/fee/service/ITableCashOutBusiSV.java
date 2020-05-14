package com.spring.fee.service;

import com.github.pagehelper.PageInfo;
import com.spring.fee.model.TableCashOut;
import com.spring.fee.model.TableInvest;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.Map;

/**
 * 提现服务
 */
public interface ITableCashOutBusiSV {

    TableCashOut insert(TableCashOut bo);

    TableCashOut update(TableCashOut bo);

    TableCashOut delete(TableCashOut bo);

    TableCashOut select(TableCashOut bo);

    TableCashOut audit(TableCashOut bo);

    PageInfo<TableCashOut> queryListPage(TableCashOut bo, Integer pageNum, Integer pageSize, Map<String, Object> map);

    /**
     * 导出文件
     * @param bo
     * @param pageNum
     * @param pageSize
     * @param map
     * @return
     */
    HSSFWorkbook exportFile(TableCashOut bo, Integer pageNum, Integer pageSize, Map<String, Object> map);
}
