package com.spring.fee.service;

import com.github.pagehelper.PageInfo;
import com.spring.fee.model.TableMember;
import com.spring.fee.model.TableMemberRankChangeDetail;

import java.util.Map;

/**
 * 会员头衔变更明细表服务
 */
public interface ITableMemberRankChangeDetailBusiSV {

    TableMemberRankChangeDetail insert(TableMemberRankChangeDetail bo);

    TableMemberRankChangeDetail update(TableMemberRankChangeDetail bo);

    TableMemberRankChangeDetail delete(TableMemberRankChangeDetail bo);

    TableMemberRankChangeDetail select(TableMemberRankChangeDetail bo);

    /**
     * 改变头衔
     * @return
     */
    TableMemberRankChangeDetail changeRank(String memberId, Integer value, String remark);

    PageInfo<TableMemberRankChangeDetail> queryListPage(TableMemberRankChangeDetail bo, Integer pageNum, Integer pageSize, Map<String, Object> map);
}
