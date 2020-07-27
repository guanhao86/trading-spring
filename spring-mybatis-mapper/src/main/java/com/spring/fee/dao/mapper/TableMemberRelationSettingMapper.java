package com.spring.fee.dao.mapper;

import com.spring.fee.model.TableMemberRelationSetting;
import com.spring.fee.model.TableMemberRelationSettingExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TableMemberRelationSettingMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_member_relation_setting
     *
     * @mbggenerated
     */
    int countByExample(TableMemberRelationSettingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_member_relation_setting
     *
     * @mbggenerated
     */
    int deleteByExample(TableMemberRelationSettingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_member_relation_setting
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_member_relation_setting
     *
     * @mbggenerated
     */
    int insert(TableMemberRelationSetting record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_member_relation_setting
     *
     * @mbggenerated
     */
    int insertSelective(TableMemberRelationSetting record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_member_relation_setting
     *
     * @mbggenerated
     */
    List<TableMemberRelationSetting> selectByExample(TableMemberRelationSettingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_member_relation_setting
     *
     * @mbggenerated
     */
    TableMemberRelationSetting selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_member_relation_setting
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TableMemberRelationSetting record, @Param("example") TableMemberRelationSettingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_member_relation_setting
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TableMemberRelationSetting record, @Param("example") TableMemberRelationSettingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_member_relation_setting
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TableMemberRelationSetting record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_member_relation_setting
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TableMemberRelationSetting record);
}