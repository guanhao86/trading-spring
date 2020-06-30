package com.spring.fee.service;

import com.github.pagehelper.PageInfo;
import com.spring.fee.model.TableMemberRelationSetting;
import com.spring.fee.model.TableMemberRelationSettingDZ;

import java.util.List;
import java.util.Map;

/**
 * 网络开放设置
 */
public interface ITableMemberRelationSettingBusiSV {

    TableMemberRelationSetting insert(TableMemberRelationSetting bo);

    TableMemberRelationSetting update(TableMemberRelationSetting bo);

    TableMemberRelationSetting set(TableMemberRelationSetting bo);

    TableMemberRelationSetting delete(TableMemberRelationSetting bo);

    TableMemberRelationSetting select(TableMemberRelationSetting bo);

    TableMemberRelationSettingDZ check(String memberId);

    PageInfo<TableMemberRelationSetting> queryListPage(TableMemberRelationSetting bo, Integer pageNum, Integer pageSize, Map<String, Object> map);
}
