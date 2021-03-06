package com.spring.fee.model;

import java.io.Serializable;

public class TInvestPlanConfig implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_invest_plan_config.plan_id
     *
     * @mbggenerated
     */
    private String planId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_invest_plan_config.plan_name
     *
     * @mbggenerated
     */
    private String planName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_invest_plan_config.plan_amount
     *
     * @mbggenerated
     */
    private Long planAmount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_invest_plan_config.plan_days
     *
     * @mbggenerated
     */
    private Integer planDays;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_invest_plan_config.main_amount
     *
     * @mbggenerated
     */
    private Long mainAmount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_invest_plan_config.main_days
     *
     * @mbggenerated
     */
    private Integer mainDays;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_invest_plan_config.second_amount
     *
     * @mbggenerated
     */
    private Long secondAmount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_invest_plan_config.second_days
     *
     * @mbggenerated
     */
    private Integer secondDays;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_invest_plan_config.max_count
     *
     * @mbggenerated
     */
    private Integer maxCount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_invest_plan_config.interest_return_per
     *
     * @mbggenerated
     */
    private Double interestReturnPer;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_invest_plan_config
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_invest_plan_config.plan_id
     *
     * @return the value of t_invest_plan_config.plan_id
     *
     * @mbggenerated
     */
    public String getPlanId() {
        return planId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_invest_plan_config.plan_id
     *
     * @param planId the value for t_invest_plan_config.plan_id
     *
     * @mbggenerated
     */
    public void setPlanId(String planId) {
        this.planId = planId == null ? null : planId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_invest_plan_config.plan_name
     *
     * @return the value of t_invest_plan_config.plan_name
     *
     * @mbggenerated
     */
    public String getPlanName() {
        return planName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_invest_plan_config.plan_name
     *
     * @param planName the value for t_invest_plan_config.plan_name
     *
     * @mbggenerated
     */
    public void setPlanName(String planName) {
        this.planName = planName == null ? null : planName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_invest_plan_config.plan_amount
     *
     * @return the value of t_invest_plan_config.plan_amount
     *
     * @mbggenerated
     */
    public Long getPlanAmount() {
        return planAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_invest_plan_config.plan_amount
     *
     * @param planAmount the value for t_invest_plan_config.plan_amount
     *
     * @mbggenerated
     */
    public void setPlanAmount(Long planAmount) {
        this.planAmount = planAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_invest_plan_config.plan_days
     *
     * @return the value of t_invest_plan_config.plan_days
     *
     * @mbggenerated
     */
    public Integer getPlanDays() {
        return planDays;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_invest_plan_config.plan_days
     *
     * @param planDays the value for t_invest_plan_config.plan_days
     *
     * @mbggenerated
     */
    public void setPlanDays(Integer planDays) {
        this.planDays = planDays;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_invest_plan_config.main_amount
     *
     * @return the value of t_invest_plan_config.main_amount
     *
     * @mbggenerated
     */
    public Long getMainAmount() {
        return mainAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_invest_plan_config.main_amount
     *
     * @param mainAmount the value for t_invest_plan_config.main_amount
     *
     * @mbggenerated
     */
    public void setMainAmount(Long mainAmount) {
        this.mainAmount = mainAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_invest_plan_config.main_days
     *
     * @return the value of t_invest_plan_config.main_days
     *
     * @mbggenerated
     */
    public Integer getMainDays() {
        return mainDays;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_invest_plan_config.main_days
     *
     * @param mainDays the value for t_invest_plan_config.main_days
     *
     * @mbggenerated
     */
    public void setMainDays(Integer mainDays) {
        this.mainDays = mainDays;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_invest_plan_config.second_amount
     *
     * @return the value of t_invest_plan_config.second_amount
     *
     * @mbggenerated
     */
    public Long getSecondAmount() {
        return secondAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_invest_plan_config.second_amount
     *
     * @param secondAmount the value for t_invest_plan_config.second_amount
     *
     * @mbggenerated
     */
    public void setSecondAmount(Long secondAmount) {
        this.secondAmount = secondAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_invest_plan_config.second_days
     *
     * @return the value of t_invest_plan_config.second_days
     *
     * @mbggenerated
     */
    public Integer getSecondDays() {
        return secondDays;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_invest_plan_config.second_days
     *
     * @param secondDays the value for t_invest_plan_config.second_days
     *
     * @mbggenerated
     */
    public void setSecondDays(Integer secondDays) {
        this.secondDays = secondDays;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_invest_plan_config.max_count
     *
     * @return the value of t_invest_plan_config.max_count
     *
     * @mbggenerated
     */
    public Integer getMaxCount() {
        return maxCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_invest_plan_config.max_count
     *
     * @param maxCount the value for t_invest_plan_config.max_count
     *
     * @mbggenerated
     */
    public void setMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_invest_plan_config.interest_return_per
     *
     * @return the value of t_invest_plan_config.interest_return_per
     *
     * @mbggenerated
     */
    public Double getInterestReturnPer() {
        return interestReturnPer;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_invest_plan_config.interest_return_per
     *
     * @param interestReturnPer the value for t_invest_plan_config.interest_return_per
     *
     * @mbggenerated
     */
    public void setInterestReturnPer(Double interestReturnPer) {
        this.interestReturnPer = interestReturnPer;
    }
}