package com.spring.fee.dao.mapper;

import com.spring.fee.model.MemberAccountDetail;
import com.spring.fee.model.MemberAccountDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MemberAccountDetailMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_account_detail
     *
     * @mbggenerated
     */
    int countByExample(MemberAccountDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_account_detail
     *
     * @mbggenerated
     */
    int deleteByExample(MemberAccountDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_account_detail
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_account_detail
     *
     * @mbggenerated
     */
    int insert(MemberAccountDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_account_detail
     *
     * @mbggenerated
     */
    int insertSelective(MemberAccountDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_account_detail
     *
     * @mbggenerated
     */
    List<MemberAccountDetail> selectByExample(MemberAccountDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_account_detail
     *
     * @mbggenerated
     */
    MemberAccountDetail selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_account_detail
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") MemberAccountDetail record, @Param("example") MemberAccountDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_account_detail
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") MemberAccountDetail record, @Param("example") MemberAccountDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_account_detail
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(MemberAccountDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member_account_detail
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(MemberAccountDetail record);
}