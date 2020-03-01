package com.spring.fee.model;

import java.io.Serializable;
import java.util.Date;

public class TInvestPlanBonus implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_invest_plan_bonus.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_invest_plan_bonus.member_id
     *
     * @mbggenerated
     */
    private String memberId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_invest_plan_bonus.member_level
     *
     * @mbggenerated
     */
    private String memberLevel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_invest_plan_bonus.from_member_id
     *
     * @mbggenerated
     */
    private String fromMemberId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_invest_plan_bonus.amount
     *
     * @mbggenerated
     */
    private Long amount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_invest_plan_bonus.order_id
     *
     * @mbggenerated
     */
    private String orderId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_invest_plan_bonus.plan_main_id
     *
     * @mbggenerated
     */
    private String planMainId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_invest_plan_bonus.type
     *
     * @mbggenerated
     */
    private String type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_invest_plan_bonus.paymentDay
     *
     * @mbggenerated
     */
    private String paymentday;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_invest_plan_bonus.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_invest_plan_bonus.settle_flag
     *
     * @mbggenerated
     */
    private String settleFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_invest_plan_bonus.settle_time
     *
     * @mbggenerated
     */
    private Date settleTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_invest_plan_bonus
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_invest_plan_bonus.id
     *
     * @return the value of t_invest_plan_bonus.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_invest_plan_bonus.id
     *
     * @param id the value for t_invest_plan_bonus.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_invest_plan_bonus.member_id
     *
     * @return the value of t_invest_plan_bonus.member_id
     *
     * @mbggenerated
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_invest_plan_bonus.member_id
     *
     * @param memberId the value for t_invest_plan_bonus.member_id
     *
     * @mbggenerated
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId == null ? null : memberId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_invest_plan_bonus.member_level
     *
     * @return the value of t_invest_plan_bonus.member_level
     *
     * @mbggenerated
     */
    public String getMemberLevel() {
        return memberLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_invest_plan_bonus.member_level
     *
     * @param memberLevel the value for t_invest_plan_bonus.member_level
     *
     * @mbggenerated
     */
    public void setMemberLevel(String memberLevel) {
        this.memberLevel = memberLevel == null ? null : memberLevel.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_invest_plan_bonus.from_member_id
     *
     * @return the value of t_invest_plan_bonus.from_member_id
     *
     * @mbggenerated
     */
    public String getFromMemberId() {
        return fromMemberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_invest_plan_bonus.from_member_id
     *
     * @param fromMemberId the value for t_invest_plan_bonus.from_member_id
     *
     * @mbggenerated
     */
    public void setFromMemberId(String fromMemberId) {
        this.fromMemberId = fromMemberId == null ? null : fromMemberId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_invest_plan_bonus.amount
     *
     * @return the value of t_invest_plan_bonus.amount
     *
     * @mbggenerated
     */
    public Long getAmount() {
        return amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_invest_plan_bonus.amount
     *
     * @param amount the value for t_invest_plan_bonus.amount
     *
     * @mbggenerated
     */
    public void setAmount(Long amount) {
        this.amount = amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_invest_plan_bonus.order_id
     *
     * @return the value of t_invest_plan_bonus.order_id
     *
     * @mbggenerated
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_invest_plan_bonus.order_id
     *
     * @param orderId the value for t_invest_plan_bonus.order_id
     *
     * @mbggenerated
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_invest_plan_bonus.plan_main_id
     *
     * @return the value of t_invest_plan_bonus.plan_main_id
     *
     * @mbggenerated
     */
    public String getPlanMainId() {
        return planMainId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_invest_plan_bonus.plan_main_id
     *
     * @param planMainId the value for t_invest_plan_bonus.plan_main_id
     *
     * @mbggenerated
     */
    public void setPlanMainId(String planMainId) {
        this.planMainId = planMainId == null ? null : planMainId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_invest_plan_bonus.type
     *
     * @return the value of t_invest_plan_bonus.type
     *
     * @mbggenerated
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_invest_plan_bonus.type
     *
     * @param type the value for t_invest_plan_bonus.type
     *
     * @mbggenerated
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_invest_plan_bonus.paymentDay
     *
     * @return the value of t_invest_plan_bonus.paymentDay
     *
     * @mbggenerated
     */
    public String getPaymentday() {
        return paymentday;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_invest_plan_bonus.paymentDay
     *
     * @param paymentday the value for t_invest_plan_bonus.paymentDay
     *
     * @mbggenerated
     */
    public void setPaymentday(String paymentday) {
        this.paymentday = paymentday == null ? null : paymentday.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_invest_plan_bonus.create_time
     *
     * @return the value of t_invest_plan_bonus.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_invest_plan_bonus.create_time
     *
     * @param createTime the value for t_invest_plan_bonus.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_invest_plan_bonus.settle_flag
     *
     * @return the value of t_invest_plan_bonus.settle_flag
     *
     * @mbggenerated
     */
    public String getSettleFlag() {
        return settleFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_invest_plan_bonus.settle_flag
     *
     * @param settleFlag the value for t_invest_plan_bonus.settle_flag
     *
     * @mbggenerated
     */
    public void setSettleFlag(String settleFlag) {
        this.settleFlag = settleFlag == null ? null : settleFlag.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_invest_plan_bonus.settle_time
     *
     * @return the value of t_invest_plan_bonus.settle_time
     *
     * @mbggenerated
     */
    public Date getSettleTime() {
        return settleTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_invest_plan_bonus.settle_time
     *
     * @param settleTime the value for t_invest_plan_bonus.settle_time
     *
     * @mbggenerated
     */
    public void setSettleTime(Date settleTime) {
        this.settleTime = settleTime;
    }
}