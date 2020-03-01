package com.spring.fee.dao.mapper;

import com.spring.fee.model.TInvestSettleRecord;
import com.spring.fee.model.TInvestSettleRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TInvestSettleRecordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_invest_settle_record
     *
     * @mbggenerated
     */
    int countByExample(TInvestSettleRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_invest_settle_record
     *
     * @mbggenerated
     */
    int deleteByExample(TInvestSettleRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_invest_settle_record
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_invest_settle_record
     *
     * @mbggenerated
     */
    int insert(TInvestSettleRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_invest_settle_record
     *
     * @mbggenerated
     */
    int insertSelective(TInvestSettleRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_invest_settle_record
     *
     * @mbggenerated
     */
    List<TInvestSettleRecord> selectByExample(TInvestSettleRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_invest_settle_record
     *
     * @mbggenerated
     */
    TInvestSettleRecord selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_invest_settle_record
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TInvestSettleRecord record, @Param("example") TInvestSettleRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_invest_settle_record
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TInvestSettleRecord record, @Param("example") TInvestSettleRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_invest_settle_record
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TInvestSettleRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_invest_settle_record
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TInvestSettleRecord record);
}