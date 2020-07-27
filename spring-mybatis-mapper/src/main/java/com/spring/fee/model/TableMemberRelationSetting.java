package com.spring.fee.model;

import java.io.Serializable;
import java.util.Date;

public class TableMemberRelationSetting implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_member_relation_setting.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_member_relation_setting.member_id
     *
     * @mbggenerated
     */
    private String memberId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_member_relation_setting.relation_type
     *
     * @mbggenerated
     */
    private String relationType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_member_relation_setting.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_member_relation_setting.valid_time
     *
     * @mbggenerated
     */
    private Date validTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_member_relation_setting.expire_time
     *
     * @mbggenerated
     */
    private Date expireTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_member_relation_setting.type
     *
     * @mbggenerated
     */
    private String type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table table_member_relation_setting
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_member_relation_setting.id
     *
     * @return the value of table_member_relation_setting.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_member_relation_setting.id
     *
     * @param id the value for table_member_relation_setting.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_member_relation_setting.member_id
     *
     * @return the value of table_member_relation_setting.member_id
     *
     * @mbggenerated
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_member_relation_setting.member_id
     *
     * @param memberId the value for table_member_relation_setting.member_id
     *
     * @mbggenerated
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId == null ? null : memberId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_member_relation_setting.relation_type
     *
     * @return the value of table_member_relation_setting.relation_type
     *
     * @mbggenerated
     */
    public String getRelationType() {
        return relationType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_member_relation_setting.relation_type
     *
     * @param relationType the value for table_member_relation_setting.relation_type
     *
     * @mbggenerated
     */
    public void setRelationType(String relationType) {
        this.relationType = relationType == null ? null : relationType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_member_relation_setting.create_time
     *
     * @return the value of table_member_relation_setting.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_member_relation_setting.create_time
     *
     * @param createTime the value for table_member_relation_setting.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_member_relation_setting.valid_time
     *
     * @return the value of table_member_relation_setting.valid_time
     *
     * @mbggenerated
     */
    public Date getValidTime() {
        return validTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_member_relation_setting.valid_time
     *
     * @param validTime the value for table_member_relation_setting.valid_time
     *
     * @mbggenerated
     */
    public void setValidTime(Date validTime) {
        this.validTime = validTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_member_relation_setting.expire_time
     *
     * @return the value of table_member_relation_setting.expire_time
     *
     * @mbggenerated
     */
    public Date getExpireTime() {
        return expireTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_member_relation_setting.expire_time
     *
     * @param expireTime the value for table_member_relation_setting.expire_time
     *
     * @mbggenerated
     */
    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_member_relation_setting.type
     *
     * @return the value of table_member_relation_setting.type
     *
     * @mbggenerated
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_member_relation_setting.type
     *
     * @param type the value for table_member_relation_setting.type
     *
     * @mbggenerated
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
}