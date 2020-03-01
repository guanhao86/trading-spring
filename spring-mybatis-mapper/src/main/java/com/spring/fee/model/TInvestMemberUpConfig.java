package com.spring.fee.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class TInvestMemberUpConfig implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_invest_member_up_config.level_id
     *
     * @mbggenerated
     */
    private Integer levelId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_invest_member_up_config.level_name
     *
     * @mbggenerated
     */
    private String levelName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_invest_member_up_config.up_type
     *
     * @mbggenerated
     */
    private String upType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_invest_member_up_config.up_condition
     *
     * @mbggenerated
     */
    private String upCondition;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_invest_member_up_config.condition_x
     *
     * @mbggenerated
     */
    private Long conditionX;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_invest_member_up_config.condition_y
     *
     * @mbggenerated
     */
    private Integer conditionY;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_invest_member_up_config.performance_persent
     *
     * @mbggenerated
     */
    private BigDecimal performancePersent;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_invest_member_up_config.commission_persent
     *
     * @mbggenerated
     */
    private BigDecimal commissionPersent;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_invest_member_up_config.sharing_persent
     *
     * @mbggenerated
     */
    private BigDecimal sharingPersent;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_invest_member_up_config.remark
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_invest_member_up_config
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_invest_member_up_config.level_id
     *
     * @return the value of t_invest_member_up_config.level_id
     *
     * @mbggenerated
     */
    public Integer getLevelId() {
        return levelId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_invest_member_up_config.level_id
     *
     * @param levelId the value for t_invest_member_up_config.level_id
     *
     * @mbggenerated
     */
    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_invest_member_up_config.level_name
     *
     * @return the value of t_invest_member_up_config.level_name
     *
     * @mbggenerated
     */
    public String getLevelName() {
        return levelName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_invest_member_up_config.level_name
     *
     * @param levelName the value for t_invest_member_up_config.level_name
     *
     * @mbggenerated
     */
    public void setLevelName(String levelName) {
        this.levelName = levelName == null ? null : levelName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_invest_member_up_config.up_type
     *
     * @return the value of t_invest_member_up_config.up_type
     *
     * @mbggenerated
     */
    public String getUpType() {
        return upType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_invest_member_up_config.up_type
     *
     * @param upType the value for t_invest_member_up_config.up_type
     *
     * @mbggenerated
     */
    public void setUpType(String upType) {
        this.upType = upType == null ? null : upType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_invest_member_up_config.up_condition
     *
     * @return the value of t_invest_member_up_config.up_condition
     *
     * @mbggenerated
     */
    public String getUpCondition() {
        return upCondition;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_invest_member_up_config.up_condition
     *
     * @param upCondition the value for t_invest_member_up_config.up_condition
     *
     * @mbggenerated
     */
    public void setUpCondition(String upCondition) {
        this.upCondition = upCondition == null ? null : upCondition.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_invest_member_up_config.condition_x
     *
     * @return the value of t_invest_member_up_config.condition_x
     *
     * @mbggenerated
     */
    public Long getConditionX() {
        return conditionX;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_invest_member_up_config.condition_x
     *
     * @param conditionX the value for t_invest_member_up_config.condition_x
     *
     * @mbggenerated
     */
    public void setConditionX(Long conditionX) {
        this.conditionX = conditionX;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_invest_member_up_config.condition_y
     *
     * @return the value of t_invest_member_up_config.condition_y
     *
     * @mbggenerated
     */
    public Integer getConditionY() {
        return conditionY;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_invest_member_up_config.condition_y
     *
     * @param conditionY the value for t_invest_member_up_config.condition_y
     *
     * @mbggenerated
     */
    public void setConditionY(Integer conditionY) {
        this.conditionY = conditionY;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_invest_member_up_config.performance_persent
     *
     * @return the value of t_invest_member_up_config.performance_persent
     *
     * @mbggenerated
     */
    public BigDecimal getPerformancePersent() {
        return performancePersent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_invest_member_up_config.performance_persent
     *
     * @param performancePersent the value for t_invest_member_up_config.performance_persent
     *
     * @mbggenerated
     */
    public void setPerformancePersent(BigDecimal performancePersent) {
        this.performancePersent = performancePersent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_invest_member_up_config.commission_persent
     *
     * @return the value of t_invest_member_up_config.commission_persent
     *
     * @mbggenerated
     */
    public BigDecimal getCommissionPersent() {
        return commissionPersent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_invest_member_up_config.commission_persent
     *
     * @param commissionPersent the value for t_invest_member_up_config.commission_persent
     *
     * @mbggenerated
     */
    public void setCommissionPersent(BigDecimal commissionPersent) {
        this.commissionPersent = commissionPersent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_invest_member_up_config.sharing_persent
     *
     * @return the value of t_invest_member_up_config.sharing_persent
     *
     * @mbggenerated
     */
    public BigDecimal getSharingPersent() {
        return sharingPersent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_invest_member_up_config.sharing_persent
     *
     * @param sharingPersent the value for t_invest_member_up_config.sharing_persent
     *
     * @mbggenerated
     */
    public void setSharingPersent(BigDecimal sharingPersent) {
        this.sharingPersent = sharingPersent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_invest_member_up_config.remark
     *
     * @return the value of t_invest_member_up_config.remark
     *
     * @mbggenerated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_invest_member_up_config.remark
     *
     * @param remark the value for t_invest_member_up_config.remark
     *
     * @mbggenerated
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}