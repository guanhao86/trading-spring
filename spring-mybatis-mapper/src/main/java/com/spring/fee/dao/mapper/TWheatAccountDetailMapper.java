package com.spring.fee.dao.mapper;

import com.spring.fee.model.TWheatAccountDetail;
import com.spring.fee.model.TWheatAccountDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TWheatAccountDetailMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_wheat_account_detail
     *
     * @mbggenerated
     */
    int countByExample(TWheatAccountDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_wheat_account_detail
     *
     * @mbggenerated
     */
    int deleteByExample(TWheatAccountDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_wheat_account_detail
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_wheat_account_detail
     *
     * @mbggenerated
     */
    int insert(TWheatAccountDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_wheat_account_detail
     *
     * @mbggenerated
     */
    int insertSelective(TWheatAccountDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_wheat_account_detail
     *
     * @mbggenerated
     */
    List<TWheatAccountDetail> selectByExample(TWheatAccountDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_wheat_account_detail
     *
     * @mbggenerated
     */
    TWheatAccountDetail selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_wheat_account_detail
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TWheatAccountDetail record, @Param("example") TWheatAccountDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_wheat_account_detail
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TWheatAccountDetail record, @Param("example") TWheatAccountDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_wheat_account_detail
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TWheatAccountDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_wheat_account_detail
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TWheatAccountDetail record);
}