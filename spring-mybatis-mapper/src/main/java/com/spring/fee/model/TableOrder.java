package com.spring.fee.model;

import java.io.Serializable;
import java.util.Date;

public class TableOrder implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_order.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_order.order_id
     *
     * @mbggenerated
     */
    private String orderId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_order.member_id
     *
     * @mbggenerated
     */
    private String memberId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_order.goods_id
     *
     * @mbggenerated
     */
    private Integer goodsId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_order.goods_class
     *
     * @mbggenerated
     */
    private Integer goodsClass;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_order.amount
     *
     * @mbggenerated
     */
    private Integer amount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_order.price
     *
     * @mbggenerated
     */
    private Float price;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_order.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_order.state
     *
     * @mbggenerated
     */
    private Integer state;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_order.express_number
     *
     * @mbggenerated
     */
    private String expressNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_order.receiver_name
     *
     * @mbggenerated
     */
    private String receiverName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_order.receiver_addr
     *
     * @mbggenerated
     */
    private String receiverAddr;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_order.receiver_phone
     *
     * @mbggenerated
     */
    private String receiverPhone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_order.oper_member_id
     *
     * @mbggenerated
     */
    private String operMemberId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_order.order_type
     *
     * @mbggenerated
     */
    private String orderType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table table_order
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_order.id
     *
     * @return the value of table_order.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_order.id
     *
     * @param id the value for table_order.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_order.order_id
     *
     * @return the value of table_order.order_id
     *
     * @mbggenerated
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_order.order_id
     *
     * @param orderId the value for table_order.order_id
     *
     * @mbggenerated
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_order.member_id
     *
     * @return the value of table_order.member_id
     *
     * @mbggenerated
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_order.member_id
     *
     * @param memberId the value for table_order.member_id
     *
     * @mbggenerated
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId == null ? null : memberId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_order.goods_id
     *
     * @return the value of table_order.goods_id
     *
     * @mbggenerated
     */
    public Integer getGoodsId() {
        return goodsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_order.goods_id
     *
     * @param goodsId the value for table_order.goods_id
     *
     * @mbggenerated
     */
    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_order.goods_class
     *
     * @return the value of table_order.goods_class
     *
     * @mbggenerated
     */
    public Integer getGoodsClass() {
        return goodsClass;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_order.goods_class
     *
     * @param goodsClass the value for table_order.goods_class
     *
     * @mbggenerated
     */
    public void setGoodsClass(Integer goodsClass) {
        this.goodsClass = goodsClass;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_order.amount
     *
     * @return the value of table_order.amount
     *
     * @mbggenerated
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_order.amount
     *
     * @param amount the value for table_order.amount
     *
     * @mbggenerated
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_order.price
     *
     * @return the value of table_order.price
     *
     * @mbggenerated
     */
    public Float getPrice() {
        return price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_order.price
     *
     * @param price the value for table_order.price
     *
     * @mbggenerated
     */
    public void setPrice(Float price) {
        this.price = price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_order.create_time
     *
     * @return the value of table_order.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_order.create_time
     *
     * @param createTime the value for table_order.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_order.state
     *
     * @return the value of table_order.state
     *
     * @mbggenerated
     */
    public Integer getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_order.state
     *
     * @param state the value for table_order.state
     *
     * @mbggenerated
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_order.express_number
     *
     * @return the value of table_order.express_number
     *
     * @mbggenerated
     */
    public String getExpressNumber() {
        return expressNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_order.express_number
     *
     * @param expressNumber the value for table_order.express_number
     *
     * @mbggenerated
     */
    public void setExpressNumber(String expressNumber) {
        this.expressNumber = expressNumber == null ? null : expressNumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_order.receiver_name
     *
     * @return the value of table_order.receiver_name
     *
     * @mbggenerated
     */
    public String getReceiverName() {
        return receiverName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_order.receiver_name
     *
     * @param receiverName the value for table_order.receiver_name
     *
     * @mbggenerated
     */
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName == null ? null : receiverName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_order.receiver_addr
     *
     * @return the value of table_order.receiver_addr
     *
     * @mbggenerated
     */
    public String getReceiverAddr() {
        return receiverAddr;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_order.receiver_addr
     *
     * @param receiverAddr the value for table_order.receiver_addr
     *
     * @mbggenerated
     */
    public void setReceiverAddr(String receiverAddr) {
        this.receiverAddr = receiverAddr == null ? null : receiverAddr.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_order.receiver_phone
     *
     * @return the value of table_order.receiver_phone
     *
     * @mbggenerated
     */
    public String getReceiverPhone() {
        return receiverPhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_order.receiver_phone
     *
     * @param receiverPhone the value for table_order.receiver_phone
     *
     * @mbggenerated
     */
    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone == null ? null : receiverPhone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_order.oper_member_id
     *
     * @return the value of table_order.oper_member_id
     *
     * @mbggenerated
     */
    public String getOperMemberId() {
        return operMemberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_order.oper_member_id
     *
     * @param operMemberId the value for table_order.oper_member_id
     *
     * @mbggenerated
     */
    public void setOperMemberId(String operMemberId) {
        this.operMemberId = operMemberId == null ? null : operMemberId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_order.order_type
     *
     * @return the value of table_order.order_type
     *
     * @mbggenerated
     */
    public String getOrderType() {
        return orderType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_order.order_type
     *
     * @param orderType the value for table_order.order_type
     *
     * @mbggenerated
     */
    public void setOrderType(String orderType) {
        this.orderType = orderType == null ? null : orderType.trim();
    }
}