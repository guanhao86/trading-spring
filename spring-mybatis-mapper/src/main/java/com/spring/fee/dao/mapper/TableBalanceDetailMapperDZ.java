package com.spring.fee.dao.mapper;

import com.spring.fee.model.TableBalanceDetail;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface TableBalanceDetailMapperDZ {
    List<TableBalanceDetail> selectByGroup(@Param("memberId") String memberId, @Param("start") Date start, @Param("end") Date end);
}