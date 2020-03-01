package com.spring.fee.dao.mapper;

import com.spring.fee.model.TableRepurchaseBonusSetting;
import com.spring.fee.model.TableRepurchaseBonusSettingExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TableRepurchaseBonusSettingMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_repurchase_bonus_setting
     *
     * @mbggenerated
     */
    int countByExample(TableRepurchaseBonusSettingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_repurchase_bonus_setting
     *
     * @mbggenerated
     */
    int deleteByExample(TableRepurchaseBonusSettingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_repurchase_bonus_setting
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_repurchase_bonus_setting
     *
     * @mbggenerated
     */
    int insert(TableRepurchaseBonusSetting record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_repurchase_bonus_setting
     *
     * @mbggenerated
     */
    int insertSelective(TableRepurchaseBonusSetting record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_repurchase_bonus_setting
     *
     * @mbggenerated
     */
    List<TableRepurchaseBonusSetting> selectByExample(TableRepurchaseBonusSettingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_repurchase_bonus_setting
     *
     * @mbggenerated
     */
    TableRepurchaseBonusSetting selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_repurchase_bonus_setting
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TableRepurchaseBonusSetting record, @Param("example") TableRepurchaseBonusSettingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_repurchase_bonus_setting
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TableRepurchaseBonusSetting record, @Param("example") TableRepurchaseBonusSettingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_repurchase_bonus_setting
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TableRepurchaseBonusSetting record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_repurchase_bonus_setting
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TableRepurchaseBonusSetting record);
}