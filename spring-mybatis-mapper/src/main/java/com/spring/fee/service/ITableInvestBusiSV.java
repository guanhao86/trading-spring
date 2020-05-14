package com.spring.fee.service;

import com.github.pagehelper.PageInfo;
import com.spring.fee.model.TableInvest;
import com.spring.fee.model.TableMember;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.Map;

/**
 * 充值申请&审核管理表服务
 */
public interface ITableInvestBusiSV {

    TableInvest insert(TableInvest bo);

    TableInvest update(TableInvest bo);

    TableInvest delete(TableInvest bo);

    TableInvest select(TableInvest bo);

    /**
     * 审核
     * @param bo
     * @return
     */
    TableInvest audit(TableInvest bo);

    PageInfo<TableInvest> queryListPage(TableInvest bo, Integer pageNum, Integer pageSize, Map<String, Object> map);

    /**
     * 导出文件
     * @param bo
     * @param pageNum
     * @param pageSize
     * @param map
     * @return
     */
    HSSFWorkbook exportFile(TableInvest bo, Integer pageNum, Integer pageSize, Map<String, Object> map);
}
