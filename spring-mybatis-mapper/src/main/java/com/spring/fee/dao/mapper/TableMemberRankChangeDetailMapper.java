package com.spring.fee.dao.mapper;

import com.spring.fee.model.TableMemberRankChangeDetail;
import com.spring.fee.model.TableMemberRankChangeDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TableMemberRankChangeDetailMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_member_rank_change_detail
     *
     * @mbggenerated
     */
    int countByExample(TableMemberRankChangeDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_member_rank_change_detail
     *
     * @mbggenerated
     */
    int deleteByExample(TableMemberRankChangeDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_member_rank_change_detail
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_member_rank_change_detail
     *
     * @mbggenerated
     */
    int insert(TableMemberRankChangeDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_member_rank_change_detail
     *
     * @mbggenerated
     */
    int insertSelective(TableMemberRankChangeDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_member_rank_change_detail
     *
     * @mbggenerated
     */
    List<TableMemberRankChangeDetail> selectByExample(TableMemberRankChangeDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_member_rank_change_detail
     *
     * @mbggenerated
     */
    TableMemberRankChangeDetail selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_member_rank_change_detail
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TableMemberRankChangeDetail record, @Param("example") TableMemberRankChangeDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_member_rank_change_detail
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TableMemberRankChangeDetail record, @Param("example") TableMemberRankChangeDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_member_rank_change_detail
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TableMemberRankChangeDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_member_rank_change_detail
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TableMemberRankChangeDetail record);
}