package com.spring.fee.dao.mapper;

import com.spring.fee.model.TableBalanceDetail;
import com.spring.fee.model.TableBalanceDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TableBalanceDetailMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_balance_detail
     *
     * @mbggenerated
     */
    int countByExample(TableBalanceDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_balance_detail
     *
     * @mbggenerated
     */
    int deleteByExample(TableBalanceDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_balance_detail
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_balance_detail
     *
     * @mbggenerated
     */
    int insert(TableBalanceDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_balance_detail
     *
     * @mbggenerated
     */
    int insertSelective(TableBalanceDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_balance_detail
     *
     * @mbggenerated
     */
    List<TableBalanceDetail> selectByExample(TableBalanceDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_balance_detail
     *
     * @mbggenerated
     */
    TableBalanceDetail selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_balance_detail
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TableBalanceDetail record, @Param("example") TableBalanceDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_balance_detail
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TableBalanceDetail record, @Param("example") TableBalanceDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_balance_detail
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TableBalanceDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_balance_detail
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TableBalanceDetail record);
}