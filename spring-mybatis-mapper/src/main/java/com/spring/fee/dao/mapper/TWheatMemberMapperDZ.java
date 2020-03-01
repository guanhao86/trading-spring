package com.spring.fee.dao.mapper;

import com.spring.fee.model.TWheatMemberDZ;
import com.spring.fee.model.TWheatMemberGroupDZ;

import java.util.List;

public interface TWheatMemberMapperDZ {

    /**
     * 获取直推会员数量，级别分组
     * @param record
     * @return
     */
    List<TWheatMemberGroupDZ> getLevelGroupCountAll(TWheatMemberDZ record);
}