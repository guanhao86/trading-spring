package com.spring.fee.service;

import com.github.pagehelper.PageInfo;
import com.spring.fee.model.TableMember;
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

    /**
     * 改变level
     * @return
     */
    public TableMemberLevelChangeDetail changeLevel(String memberId, Integer value, String remark);

    PageInfo<TableMemberLevelChangeDetail> queryListPage(TableMemberLevelChangeDetail bo, Integer pageNum, Integer pageSize, Map<String, Object> map);
}
