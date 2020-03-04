package com.spring.fee.service;

import com.github.pagehelper.PageInfo;
import com.spring.fee.model.TableMemberAccountDetail;

import java.util.Map;

/**
 * 账户资金情况变更表服务
 */
public interface IMemberAccountDetailBusiSV {

    TableMemberAccountDetail insert(TableMemberAccountDetail bo);

    TableMemberAccountDetail update(TableMemberAccountDetail bo);

    TableMemberAccountDetail delete(TableMemberAccountDetail bo);

    TableMemberAccountDetail select(TableMemberAccountDetail bo);

    /**
     * 账户变更接口
     * @param memberId
     * @param accountType
     * 1:充值  2:购物
     * @param amount
     * @param remark
     * @return
     */
    TableMemberAccountDetail changeMoney(String memberId, String accountType, Float amount, String remark);

    PageInfo<TableMemberAccountDetail> queryListPage(TableMemberAccountDetail bo, Integer pageNum, Integer pageSize, Map<String, Object> map);
}
