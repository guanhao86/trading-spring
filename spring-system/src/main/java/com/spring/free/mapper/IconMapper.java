package com.spring.free.mapper;


import com.spring.free.domain.IconInfo;

import java.util.List;

/**
 * 图标管理DAO
 * @author Memory
 * @version 1.0
 */
public interface IconMapper {

    /**
     * 查询图标
     * @param lcon
     * @return
     */
    List<IconInfo> selectIcon(IconInfo lcon);
}
