package com.spring.fee.model;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

public class TWheatMember implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_wheat_member.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_wheat_member.member_id
     *
     * @mbggenerated
     */
    private String memberId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_wheat_member.phone
     *
     * @mbggenerated
     */
    private String phone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_wheat_member.password
     *
     * @mbggenerated
     */
    private String password;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_wheat_member.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_wheat_member.level
     *
     * @mbggenerated
     */
    private String level;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_wheat_member.reference_id
     *
     * @mbggenerated
     */
    private String referenceId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_wheat_member.is_aut
     *
     * @mbggenerated
     */
    private String isAut;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_wheat_member.bank_name
     *
     * @mbggenerated
     */
    private String bankName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_wheat_member.bank_card
     *
     * @mbggenerated
     */
    private String bankCard;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_wheat_member.id_card
     *
     * @mbggenerated
     */
    private String idCard;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_wheat_member.img_front
     *
     * @mbggenerated
     */
    private String imgFront;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_wheat_member.img_back
     *
     * @mbggenerated
     */
    private String imgBack;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_wheat_member.real_name
     *
     * @mbggenerated
     */
    private String realName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_wheat_member.total
     *
     * @mbggenerated
     */
    private Double total;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_wheat_member.available
     *
     * @mbggenerated
     */
    private Double available;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_wheat_member.freeze
     *
     * @mbggenerated
     */
    private Double freeze;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_wheat_member.money_freeze
     *
     * @mbggenerated
     */
    private Double moneyFreeze;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_wheat_member.granary_freeze
     *
     * @mbggenerated
     */
    private Double granaryFreeze;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_wheat_member.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_wheat_member.modify_time
     *
     * @mbggenerated
     */
    private Date modifyTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_wheat_member.del_flag
     *
     * @mbggenerated
     */
    private Integer delFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_wheat_member.version
     *
     * @mbggenerated
     */
    private Long version;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_wheat_member.expand_1
     *
     * @mbggenerated
     */
    private String expand1;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_wheat_member.expand_2
     *
     * @mbggenerated
     */
    private String expand2;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_wheat_member.expand_3
     *
     * @mbggenerated
     */
    private String expand3;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_wheat_member.expand_4
     *
     * @mbggenerated
     */
    private String expand4;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_wheat_member.expand_5
     *
     * @mbggenerated
     */
    private String expand5;
    @Transient
    protected String paramMsg;

    private TWheatAccount tWheatAccount;

    public TWheatAccount gettWheatAccount() {
        return tWheatAccount;
    }

    public void settWheatAccount(TWheatAccount tWheatAccount) {
        this.tWheatAccount = tWheatAccount;
    }

    public String getParamMsg() {
        return paramMsg;
    }

    public void setParamMsg(String paramMsg) {
        this.paramMsg = paramMsg;
    }

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_wheat_member
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_wheat_member.id
     *
     * @return the value of t_wheat_member.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_wheat_member.id
     *
     * @param id the value for t_wheat_member.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_wheat_member.member_id
     *
     * @return the value of t_wheat_member.member_id
     *
     * @mbggenerated
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_wheat_member.member_id
     *
     * @param memberId the value for t_wheat_member.member_id
     *
     * @mbggenerated
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId == null ? null : memberId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_wheat_member.phone
     *
     * @return the value of t_wheat_member.phone
     *
     * @mbggenerated
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_wheat_member.phone
     *
     * @param phone the value for t_wheat_member.phone
     *
     * @mbggenerated
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_wheat_member.password
     *
     * @return the value of t_wheat_member.password
     *
     * @mbggenerated
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_wheat_member.password
     *
     * @param password the value for t_wheat_member.password
     *
     * @mbggenerated
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_wheat_member.name
     *
     * @return the value of t_wheat_member.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_wheat_member.name
     *
     * @param name the value for t_wheat_member.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_wheat_member.level
     *
     * @return the value of t_wheat_member.level
     *
     * @mbggenerated
     */
    public String getLevel() {
        return level;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_wheat_member.level
     *
     * @param level the value for t_wheat_member.level
     *
     * @mbggenerated
     */
    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_wheat_member.reference_id
     *
     * @return the value of t_wheat_member.reference_id
     *
     * @mbggenerated
     */
    public String getReferenceId() {
        return referenceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_wheat_member.reference_id
     *
     * @param referenceId the value for t_wheat_member.reference_id
     *
     * @mbggenerated
     */
    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId == null ? null : referenceId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_wheat_member.is_aut
     *
     * @return the value of t_wheat_member.is_aut
     *
     * @mbggenerated
     */
    public String getIsAut() {
        return isAut;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_wheat_member.is_aut
     *
     * @param isAut the value for t_wheat_member.is_aut
     *
     * @mbggenerated
     */
    public void setIsAut(String isAut) {
        this.isAut = isAut == null ? null : isAut.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_wheat_member.bank_name
     *
     * @return the value of t_wheat_member.bank_name
     *
     * @mbggenerated
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_wheat_member.bank_name
     *
     * @param bankName the value for t_wheat_member.bank_name
     *
     * @mbggenerated
     */
    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_wheat_member.bank_card
     *
     * @return the value of t_wheat_member.bank_card
     *
     * @mbggenerated
     */
    public String getBankCard() {
        return bankCard;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_wheat_member.bank_card
     *
     * @param bankCard the value for t_wheat_member.bank_card
     *
     * @mbggenerated
     */
    public void setBankCard(String bankCard) {
        this.bankCard = bankCard == null ? null : bankCard.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_wheat_member.id_card
     *
     * @return the value of t_wheat_member.id_card
     *
     * @mbggenerated
     */
    public String getIdCard() {
        return idCard;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_wheat_member.id_card
     *
     * @param idCard the value for t_wheat_member.id_card
     *
     * @mbggenerated
     */
    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_wheat_member.img_front
     *
     * @return the value of t_wheat_member.img_front
     *
     * @mbggenerated
     */
    public String getImgFront() {
        return imgFront;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_wheat_member.img_front
     *
     * @param imgFront the value for t_wheat_member.img_front
     *
     * @mbggenerated
     */
    public void setImgFront(String imgFront) {
        this.imgFront = imgFront == null ? null : imgFront.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_wheat_member.img_back
     *
     * @return the value of t_wheat_member.img_back
     *
     * @mbggenerated
     */
    public String getImgBack() {
        return imgBack;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_wheat_member.img_back
     *
     * @param imgBack the value for t_wheat_member.img_back
     *
     * @mbggenerated
     */
    public void setImgBack(String imgBack) {
        this.imgBack = imgBack == null ? null : imgBack.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_wheat_member.real_name
     *
     * @return the value of t_wheat_member.real_name
     *
     * @mbggenerated
     */
    public String getRealName() {
        return realName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_wheat_member.real_name
     *
     * @param realName the value for t_wheat_member.real_name
     *
     * @mbggenerated
     */
    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_wheat_member.total
     *
     * @return the value of t_wheat_member.total
     *
     * @mbggenerated
     */
    public Double getTotal() {
        return total;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_wheat_member.total
     *
     * @param total the value for t_wheat_member.total
     *
     * @mbggenerated
     */
    public void setTotal(Double total) {
        this.total = total;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_wheat_member.available
     *
     * @return the value of t_wheat_member.available
     *
     * @mbggenerated
     */
    public Double getAvailable() {
        return available;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_wheat_member.available
     *
     * @param available the value for t_wheat_member.available
     *
     * @mbggenerated
     */
    public void setAvailable(Double available) {
        this.available = available;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_wheat_member.freeze
     *
     * @return the value of t_wheat_member.freeze
     *
     * @mbggenerated
     */
    public Double getFreeze() {
        return freeze;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_wheat_member.freeze
     *
     * @param freeze the value for t_wheat_member.freeze
     *
     * @mbggenerated
     */
    public void setFreeze(Double freeze) {
        this.freeze = freeze;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_wheat_member.money_freeze
     *
     * @return the value of t_wheat_member.money_freeze
     *
     * @mbggenerated
     */
    public Double getMoneyFreeze() {
        return moneyFreeze;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_wheat_member.money_freeze
     *
     * @param moneyFreeze the value for t_wheat_member.money_freeze
     *
     * @mbggenerated
     */
    public void setMoneyFreeze(Double moneyFreeze) {
        this.moneyFreeze = moneyFreeze;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_wheat_member.granary_freeze
     *
     * @return the value of t_wheat_member.granary_freeze
     *
     * @mbggenerated
     */
    public Double getGranaryFreeze() {
        return granaryFreeze;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_wheat_member.granary_freeze
     *
     * @param granaryFreeze the value for t_wheat_member.granary_freeze
     *
     * @mbggenerated
     */
    public void setGranaryFreeze(Double granaryFreeze) {
        this.granaryFreeze = granaryFreeze;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_wheat_member.create_time
     *
     * @return the value of t_wheat_member.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_wheat_member.create_time
     *
     * @param createTime the value for t_wheat_member.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_wheat_member.modify_time
     *
     * @return the value of t_wheat_member.modify_time
     *
     * @mbggenerated
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_wheat_member.modify_time
     *
     * @param modifyTime the value for t_wheat_member.modify_time
     *
     * @mbggenerated
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_wheat_member.del_flag
     *
     * @return the value of t_wheat_member.del_flag
     *
     * @mbggenerated
     */
    public Integer getDelFlag() {
        return delFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_wheat_member.del_flag
     *
     * @param delFlag the value for t_wheat_member.del_flag
     *
     * @mbggenerated
     */
    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_wheat_member.version
     *
     * @return the value of t_wheat_member.version
     *
     * @mbggenerated
     */
    public Long getVersion() {
        return version;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_wheat_member.version
     *
     * @param version the value for t_wheat_member.version
     *
     * @mbggenerated
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_wheat_member.expand_1
     *
     * @return the value of t_wheat_member.expand_1
     *
     * @mbggenerated
     */
    public String getExpand1() {
        return expand1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_wheat_member.expand_1
     *
     * @param expand1 the value for t_wheat_member.expand_1
     *
     * @mbggenerated
     */
    public void setExpand1(String expand1) {
        this.expand1 = expand1 == null ? null : expand1.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_wheat_member.expand_2
     *
     * @return the value of t_wheat_member.expand_2
     *
     * @mbggenerated
     */
    public String getExpand2() {
        return expand2;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_wheat_member.expand_2
     *
     * @param expand2 the value for t_wheat_member.expand_2
     *
     * @mbggenerated
     */
    public void setExpand2(String expand2) {
        this.expand2 = expand2 == null ? null : expand2.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_wheat_member.expand_3
     *
     * @return the value of t_wheat_member.expand_3
     *
     * @mbggenerated
     */
    public String getExpand3() {
        return expand3;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_wheat_member.expand_3
     *
     * @param expand3 the value for t_wheat_member.expand_3
     *
     * @mbggenerated
     */
    public void setExpand3(String expand3) {
        this.expand3 = expand3 == null ? null : expand3.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_wheat_member.expand_4
     *
     * @return the value of t_wheat_member.expand_4
     *
     * @mbggenerated
     */
    public String getExpand4() {
        return expand4;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_wheat_member.expand_4
     *
     * @param expand4 the value for t_wheat_member.expand_4
     *
     * @mbggenerated
     */
    public void setExpand4(String expand4) {
        this.expand4 = expand4 == null ? null : expand4.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_wheat_member.expand_5
     *
     * @return the value of t_wheat_member.expand_5
     *
     * @mbggenerated
     */
    public String getExpand5() {
        return expand5;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_wheat_member.expand_5
     *
     * @param expand5 the value for t_wheat_member.expand_5
     *
     * @mbggenerated
     */
    public void setExpand5(String expand5) {
        this.expand5 = expand5 == null ? null : expand5.trim();
    }
}