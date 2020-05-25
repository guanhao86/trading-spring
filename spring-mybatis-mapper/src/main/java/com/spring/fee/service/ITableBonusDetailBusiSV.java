package com.spring.fee.service;

import com.github.pagehelper.PageInfo;
import com.spring.fee.model.TableBonusDetail;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 奖金流水管理表服务
 */
public interface ITableBonusDetailBusiSV {

    TableBonusDetail insert(TableBonusDetail bo);

    TableBonusDetail update(TableBonusDetail bo);

    TableBonusDetail delete(TableBonusDetail bo);

    TableBonusDetail select(TableBonusDetail bo);

    /**
     * 根据BrondId统计奖金
     * @param start
     * @param end
     * @return
     */
    List<TableBonusDetail> selectByGroupBonusId( String memberId, Date start, Date end);

    /**
     * 统计奖金（日新增业绩）
     *
     * @param start
     * @param end
     * @return
     */
    TableBonusDetail selectByGroup(Date start, Date end, List<String> bonusIdIn);

    PageInfo<TableBonusDetail> queryListPage(TableBonusDetail bo, Integer pageNum, Integer pageSize, Map<String, Object> map);
}
