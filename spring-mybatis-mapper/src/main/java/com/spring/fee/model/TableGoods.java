package com.spring.fee.model;

import java.io.Serializable;

public class TableGoods implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_goods.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_goods.goods_class
     *
     * @mbggenerated
     */
    private Integer goodsClass;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_goods.goods_name
     *
     * @mbggenerated
     */
    private String goodsName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_goods.thunmbanil_img_src
     *
     * @mbggenerated
     */
    private String thunmbanilImgSrc;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_goods.detail_img_src
     *
     * @mbggenerated
     */
    private String detailImgSrc;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_goods.price
     *
     * @mbggenerated
     */
    private Float price;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_goods.income_vip_level
     *
     * @mbggenerated
     */
    private Integer incomeVipLevel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_goods.income_dj_point
     *
     * @mbggenerated
     */
    private Float incomeDjPoint;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_goods.income_jys_point
     *
     * @mbggenerated
     */
    private Float incomeJysPoint;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_goods.pe_price
     *
     * @mbggenerated
     */
    private Float pePrice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_goods.state
     *
     * @mbggenerated
     */
    private String state;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table table_goods
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_goods.id
     *
     * @return the value of table_goods.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_goods.id
     *
     * @param id the value for table_goods.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_goods.goods_class
     *
     * @return the value of table_goods.goods_class
     *
     * @mbggenerated
     */
    public Integer getGoodsClass() {
        return goodsClass;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_goods.goods_class
     *
     * @param goodsClass the value for table_goods.goods_class
     *
     * @mbggenerated
     */
    public void setGoodsClass(Integer goodsClass) {
        this.goodsClass = goodsClass;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_goods.goods_name
     *
     * @return the value of table_goods.goods_name
     *
     * @mbggenerated
     */
    public String getGoodsName() {
        return goodsName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_goods.goods_name
     *
     * @param goodsName the value for table_goods.goods_name
     *
     * @mbggenerated
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_goods.thunmbanil_img_src
     *
     * @return the value of table_goods.thunmbanil_img_src
     *
     * @mbggenerated
     */
    public String getThunmbanilImgSrc() {
        return thunmbanilImgSrc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_goods.thunmbanil_img_src
     *
     * @param thunmbanilImgSrc the value for table_goods.thunmbanil_img_src
     *
     * @mbggenerated
     */
    public void setThunmbanilImgSrc(String thunmbanilImgSrc) {
        this.thunmbanilImgSrc = thunmbanilImgSrc == null ? null : thunmbanilImgSrc.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_goods.detail_img_src
     *
     * @return the value of table_goods.detail_img_src
     *
     * @mbggenerated
     */
    public String getDetailImgSrc() {
        return detailImgSrc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_goods.detail_img_src
     *
     * @param detailImgSrc the value for table_goods.detail_img_src
     *
     * @mbggenerated
     */
    public void setDetailImgSrc(String detailImgSrc) {
        this.detailImgSrc = detailImgSrc == null ? null : detailImgSrc.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_goods.price
     *
     * @return the value of table_goods.price
     *
     * @mbggenerated
     */
    public Float getPrice() {
        return price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_goods.price
     *
     * @param price the value for table_goods.price
     *
     * @mbggenerated
     */
    public void setPrice(Float price) {
        this.price = price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_goods.income_vip_level
     *
     * @return the value of table_goods.income_vip_level
     *
     * @mbggenerated
     */
    public Integer getIncomeVipLevel() {
        return incomeVipLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_goods.income_vip_level
     *
     * @param incomeVipLevel the value for table_goods.income_vip_level
     *
     * @mbggenerated
     */
    public void setIncomeVipLevel(Integer incomeVipLevel) {
        this.incomeVipLevel = incomeVipLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_goods.income_dj_point
     *
     * @return the value of table_goods.income_dj_point
     *
     * @mbggenerated
     */
    public Float getIncomeDjPoint() {
        return incomeDjPoint;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_goods.income_dj_point
     *
     * @param incomeDjPoint the value for table_goods.income_dj_point
     *
     * @mbggenerated
     */
    public void setIncomeDjPoint(Float incomeDjPoint) {
        this.incomeDjPoint = incomeDjPoint;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_goods.income_jys_point
     *
     * @return the value of table_goods.income_jys_point
     *
     * @mbggenerated
     */
    public Float getIncomeJysPoint() {
        return incomeJysPoint;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_goods.income_jys_point
     *
     * @param incomeJysPoint the value for table_goods.income_jys_point
     *
     * @mbggenerated
     */
    public void setIncomeJysPoint(Float incomeJysPoint) {
        this.incomeJysPoint = incomeJysPoint;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_goods.pe_price
     *
     * @return the value of table_goods.pe_price
     *
     * @mbggenerated
     */
    public Float getPePrice() {
        return pePrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_goods.pe_price
     *
     * @param pePrice the value for table_goods.pe_price
     *
     * @mbggenerated
     */
    public void setPePrice(Float pePrice) {
        this.pePrice = pePrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_goods.state
     *
     * @return the value of table_goods.state
     *
     * @mbggenerated
     */
    public String getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_goods.state
     *
     * @param state the value for table_goods.state
     *
     * @mbggenerated
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }
}