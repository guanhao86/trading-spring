package com.spring.fee.model;

import java.io.Serializable;
import java.util.Date;

public class TableBonusComposeDetail implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_bonus_compose_detail.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_bonus_compose_detail.bonus_detail_id
     *
     * @mbggenerated
     */
    private Integer bonusDetailId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_bonus_compose_detail.member_id
     *
     * @mbggenerated
     */
    private String memberId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_bonus_compose_detail.order_price
     *
     * @mbggenerated
     */
    private Float orderPrice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_bonus_compose_detail.order_time
     *
     * @mbggenerated
     */
    private Date orderTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table table_bonus_compose_detail
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_bonus_compose_detail.id
     *
     * @return the value of table_bonus_compose_detail.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_bonus_compose_detail.id
     *
     * @param id the value for table_bonus_compose_detail.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_bonus_compose_detail.bonus_detail_id
     *
     * @return the value of table_bonus_compose_detail.bonus_detail_id
     *
     * @mbggenerated
     */
    public Integer getBonusDetailId() {
        return bonusDetailId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_bonus_compose_detail.bonus_detail_id
     *
     * @param bonusDetailId the value for table_bonus_compose_detail.bonus_detail_id
     *
     * @mbggenerated
     */
    public void setBonusDetailId(Integer bonusDetailId) {
        this.bonusDetailId = bonusDetailId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_bonus_compose_detail.member_id
     *
     * @return the value of table_bonus_compose_detail.member_id
     *
     * @mbggenerated
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_bonus_compose_detail.member_id
     *
     * @param memberId the value for table_bonus_compose_detail.member_id
     *
     * @mbggenerated
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId == null ? null : memberId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_bonus_compose_detail.order_price
     *
     * @return the value of table_bonus_compose_detail.order_price
     *
     * @mbggenerated
     */
    public Float getOrderPrice() {
        return orderPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_bonus_compose_detail.order_price
     *
     * @param orderPrice the value for table_bonus_compose_detail.order_price
     *
     * @mbggenerated
     */
    public void setOrderPrice(Float orderPrice) {
        this.orderPrice = orderPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_bonus_compose_detail.order_time
     *
     * @return the value of table_bonus_compose_detail.order_time
     *
     * @mbggenerated
     */
    public Date getOrderTime() {
        return orderTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_bonus_compose_detail.order_time
     *
     * @param orderTime the value for table_bonus_compose_detail.order_time
     *
     * @mbggenerated
     */
    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }
}