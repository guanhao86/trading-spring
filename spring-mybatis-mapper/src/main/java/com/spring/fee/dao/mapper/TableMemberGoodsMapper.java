package com.spring.fee.dao.mapper;

import com.spring.fee.model.TableMemberGoods;
import com.spring.fee.model.TableMemberGoodsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TableMemberGoodsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_member_goods
     *
     * @mbggenerated
     */
    int countByExample(TableMemberGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_member_goods
     *
     * @mbggenerated
     */
    int deleteByExample(TableMemberGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_member_goods
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_member_goods
     *
     * @mbggenerated
     */
    int insert(TableMemberGoods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_member_goods
     *
     * @mbggenerated
     */
    int insertSelective(TableMemberGoods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_member_goods
     *
     * @mbggenerated
     */
    List<TableMemberGoods> selectByExample(TableMemberGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_member_goods
     *
     * @mbggenerated
     */
    TableMemberGoods selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_member_goods
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TableMemberGoods record, @Param("example") TableMemberGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_member_goods
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TableMemberGoods record, @Param("example") TableMemberGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_member_goods
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TableMemberGoods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_member_goods
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TableMemberGoods record);
}