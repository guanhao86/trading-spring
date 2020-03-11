package com.spring.fee.service;

import com.github.pagehelper.PageInfo;
import com.spring.fee.model.TableBalanceDetail;
import com.spring.fee.model.TableBalanceDetailDZ;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 结算管理表服务
 */
public interface ITableBalanceDetailBusiSV {

    TableBalanceDetail insert(TableBalanceDetail bo);

    TableBalanceDetail update(TableBalanceDetail bo);

    TableBalanceDetail delete(TableBalanceDetail bo);

    TableBalanceDetail select(TableBalanceDetail bo);

    /**
     * 统计奖金发放总和
     * @param memberId
     * @param start
     * @param end
     * @return
     */
    List<TableBalanceDetail> selectByGroup(String memberId, Date start, Date end);

    PageInfo<TableBalanceDetail> queryListPage(TableBalanceDetailDZ bo, Integer pageNum, Integer pageSize, Map<String, Object> map);
}
