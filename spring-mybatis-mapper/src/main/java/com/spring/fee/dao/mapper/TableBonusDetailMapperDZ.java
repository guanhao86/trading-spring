package com.spring.fee.dao.mapper;

import com.spring.fee.model.TableBonusDetail;
import com.spring.fee.model.TableBonusDetailExample;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface TableBonusDetailMapperDZ {

    List<TableBonusDetail> selectByGroupBonusId(@Param("memberId") String memberId, @Param("start") Date start,@Param("end") Date end);

    TableBonusDetail selectByGroup(@Param("start") Date start,@Param("end") Date end, @Param("bonusIdIn") List<String> bonusIdIn);

}