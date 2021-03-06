package com.spring.fee.dao.mapper;

import com.spring.fee.model.TableBonusDetail;
import com.spring.fee.model.TableBonusDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TableBonusDetailMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_bonus_detail
     *
     * @mbggenerated
     */
    int countByExample(TableBonusDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_bonus_detail
     *
     * @mbggenerated
     */
    int deleteByExample(TableBonusDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_bonus_detail
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_bonus_detail
     *
     * @mbggenerated
     */
    int insert(TableBonusDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_bonus_detail
     *
     * @mbggenerated
     */
    int insertSelective(TableBonusDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_bonus_detail
     *
     * @mbggenerated
     */
    List<TableBonusDetail> selectByExample(TableBonusDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_bonus_detail
     *
     * @mbggenerated
     */
    TableBonusDetail selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_bonus_detail
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TableBonusDetail record, @Param("example") TableBonusDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_bonus_detail
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TableBonusDetail record, @Param("example") TableBonusDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_bonus_detail
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TableBonusDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_bonus_detail
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TableBonusDetail record);
}