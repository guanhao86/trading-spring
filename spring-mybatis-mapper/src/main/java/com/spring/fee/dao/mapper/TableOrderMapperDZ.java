package com.spring.fee.dao.mapper;

import com.spring.fee.model.TableOrderDZ;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface TableOrderMapperDZ {

    TableOrderDZ selectByGroup(@Param("memberId") String memberId, @Param("start") Date start, @Param("end") Date end);

    TableOrderDZ selectByGroup2(@Param("memberId") String memberId, @Param("start") Date start, @Param("end") Date end);

}