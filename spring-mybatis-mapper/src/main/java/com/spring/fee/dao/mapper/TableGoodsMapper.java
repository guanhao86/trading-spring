package com.spring.fee.dao.mapper;

import com.spring.fee.model.TableGoods;
import com.spring.fee.model.TableGoodsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TableGoodsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_goods
     *
     * @mbggenerated
     */
    int countByExample(TableGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_goods
     *
     * @mbggenerated
     */
    int deleteByExample(TableGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_goods
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_goods
     *
     * @mbggenerated
     */
    int insert(TableGoods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_goods
     *
     * @mbggenerated
     */
    int insertSelective(TableGoods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_goods
     *
     * @mbggenerated
     */
    List<TableGoods> selectByExample(TableGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_goods
     *
     * @mbggenerated
     */
    TableGoods selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_goods
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TableGoods record, @Param("example") TableGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_goods
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TableGoods record, @Param("example") TableGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_goods
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TableGoods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_goods
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TableGoods record);
}