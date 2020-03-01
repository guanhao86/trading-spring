package com.spring.fee.dao.mapper;

import com.spring.fee.model.TableMember;
import com.spring.fee.model.TableMemberExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TableMemberMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_member
     *
     * @mbggenerated
     */
    int countByExample(TableMemberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_member
     *
     * @mbggenerated
     */
    int deleteByExample(TableMemberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_member
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_member
     *
     * @mbggenerated
     */
    int insert(TableMember record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_member
     *
     * @mbggenerated
     */
    int insertSelective(TableMember record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_member
     *
     * @mbggenerated
     */
    List<TableMember> selectByExample(TableMemberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_member
     *
     * @mbggenerated
     */
    TableMember selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_member
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TableMember record, @Param("example") TableMemberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_member
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TableMember record, @Param("example") TableMemberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_member
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TableMember record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_member
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TableMember record);
}