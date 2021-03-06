package com.spring.fee.model;

import java.io.Serializable;

public class TableRankSetting implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_rank_setting.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_rank_setting.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_rank_setting.case_performance
     *
     * @mbggenerated
     */
    private Float casePerformance;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_rank_setting.case_down_rank_count
     *
     * @mbggenerated
     */
    private Integer caseDownRankCount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_rank_setting.repurchase_bonus
     *
     * @mbggenerated
     */
    private Float repurchaseBonus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table table_rank_setting
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_rank_setting.id
     *
     * @return the value of table_rank_setting.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_rank_setting.id
     *
     * @param id the value for table_rank_setting.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_rank_setting.name
     *
     * @return the value of table_rank_setting.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_rank_setting.name
     *
     * @param name the value for table_rank_setting.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_rank_setting.case_performance
     *
     * @return the value of table_rank_setting.case_performance
     *
     * @mbggenerated
     */
    public Float getCasePerformance() {
        return casePerformance;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_rank_setting.case_performance
     *
     * @param casePerformance the value for table_rank_setting.case_performance
     *
     * @mbggenerated
     */
    public void setCasePerformance(Float casePerformance) {
        this.casePerformance = casePerformance;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_rank_setting.case_down_rank_count
     *
     * @return the value of table_rank_setting.case_down_rank_count
     *
     * @mbggenerated
     */
    public Integer getCaseDownRankCount() {
        return caseDownRankCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_rank_setting.case_down_rank_count
     *
     * @param caseDownRankCount the value for table_rank_setting.case_down_rank_count
     *
     * @mbggenerated
     */
    public void setCaseDownRankCount(Integer caseDownRankCount) {
        this.caseDownRankCount = caseDownRankCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_rank_setting.repurchase_bonus
     *
     * @return the value of table_rank_setting.repurchase_bonus
     *
     * @mbggenerated
     */
    public Float getRepurchaseBonus() {
        return repurchaseBonus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_rank_setting.repurchase_bonus
     *
     * @param repurchaseBonus the value for table_rank_setting.repurchase_bonus
     *
     * @mbggenerated
     */
    public void setRepurchaseBonus(Float repurchaseBonus) {
        this.repurchaseBonus = repurchaseBonus;
    }
}