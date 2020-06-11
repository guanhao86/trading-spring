package com.spring.fee.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TableMemberAccountDetail implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_member_account_detail.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_member_account_detail.member_id
     *
     * @mbggenerated
     */
    private String memberId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_member_account_detail.account_type
     *
     * @mbggenerated
     */
    private Integer accountType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_member_account_detail.before_value
     *
     * @mbggenerated
     */
    private BigDecimal beforeValue;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_member_account_detail.after_value
     *
     * @mbggenerated
     */
    private BigDecimal afterValue;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_member_account_detail.modify_time
     *
     * @mbggenerated
     */
    private Date modifyTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_member_account_detail.remark
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table table_member_account_detail
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_member_account_detail.id
     *
     * @return the value of table_member_account_detail.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_member_account_detail.id
     *
     * @param id the value for table_member_account_detail.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_member_account_detail.member_id
     *
     * @return the value of table_member_account_detail.member_id
     *
     * @mbggenerated
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_member_account_detail.member_id
     *
     * @param memberId the value for table_member_account_detail.member_id
     *
     * @mbggenerated
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId == null ? null : memberId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_member_account_detail.account_type
     *
     * @return the value of table_member_account_detail.account_type
     *
     * @mbggenerated
     */
    public Integer getAccountType() {
        return accountType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_member_account_detail.account_type
     *
     * @param accountType the value for table_member_account_detail.account_type
     *
     * @mbggenerated
     */
    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_member_account_detail.before_value
     *
     * @return the value of table_member_account_detail.before_value
     *
     * @mbggenerated
     */
    public BigDecimal getBeforeValue() {
        return beforeValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_member_account_detail.before_value
     *
     * @param beforeValue the value for table_member_account_detail.before_value
     *
     * @mbggenerated
     */
    public void setBeforeValue(BigDecimal beforeValue) {
        this.beforeValue = beforeValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_member_account_detail.after_value
     *
     * @return the value of table_member_account_detail.after_value
     *
     * @mbggenerated
     */
    public BigDecimal getAfterValue() {
        return afterValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_member_account_detail.after_value
     *
     * @param afterValue the value for table_member_account_detail.after_value
     *
     * @mbggenerated
     */
    public void setAfterValue(BigDecimal afterValue) {
        this.afterValue = afterValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_member_account_detail.modify_time
     *
     * @return the value of table_member_account_detail.modify_time
     *
     * @mbggenerated
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_member_account_detail.modify_time
     *
     * @param modifyTime the value for table_member_account_detail.modify_time
     *
     * @mbggenerated
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_member_account_detail.remark
     *
     * @return the value of table_member_account_detail.remark
     *
     * @mbggenerated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_member_account_detail.remark
     *
     * @param remark the value for table_member_account_detail.remark
     *
     * @mbggenerated
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}