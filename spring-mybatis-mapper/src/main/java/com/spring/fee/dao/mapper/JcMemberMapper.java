package com.spring.fee.dao.mapper;

import com.spring.fee.model.JcMember;
import com.spring.fee.model.JcMemberExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface JcMemberMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jc_member
     *
     * @mbggenerated
     */
    int countByExample(JcMemberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jc_member
     *
     * @mbggenerated
     */
    int deleteByExample(JcMemberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jc_member
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jc_member
     *
     * @mbggenerated
     */
    int insert(JcMember record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jc_member
     *
     * @mbggenerated
     */
    int insertSelective(JcMember record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jc_member
     *
     * @mbggenerated
     */
    List<JcMember> selectByExample(JcMemberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jc_member
     *
     * @mbggenerated
     */
    JcMember selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jc_member
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") JcMember record, @Param("example") JcMemberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jc_member
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") JcMember record, @Param("example") JcMemberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jc_member
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(JcMember record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jc_member
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(JcMember record);
}