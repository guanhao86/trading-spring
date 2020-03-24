package com.spring.fee.model;

import java.io.Serializable;
import java.util.Date;

public class TableBroadcastInfo implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_broadcast_info.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_broadcast_info.title
     *
     * @mbggenerated
     */
    private String title;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_broadcast_info.content
     *
     * @mbggenerated
     */
    private String content;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_broadcast_info.time
     *
     * @mbggenerated
     */
    private Date time;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_broadcast_info.is_delete
     *
     * @mbggenerated
     */
    private Integer isDelete;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_broadcast_info.member_level
     *
     * @mbggenerated
     */
    private Integer memberLevel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_broadcast_info.mRank_level
     *
     * @mbggenerated
     */
    private Integer mrankLevel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table table_broadcast_info
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_broadcast_info.id
     *
     * @return the value of table_broadcast_info.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_broadcast_info.id
     *
     * @param id the value for table_broadcast_info.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_broadcast_info.title
     *
     * @return the value of table_broadcast_info.title
     *
     * @mbggenerated
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_broadcast_info.title
     *
     * @param title the value for table_broadcast_info.title
     *
     * @mbggenerated
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_broadcast_info.content
     *
     * @return the value of table_broadcast_info.content
     *
     * @mbggenerated
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_broadcast_info.content
     *
     * @param content the value for table_broadcast_info.content
     *
     * @mbggenerated
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_broadcast_info.time
     *
     * @return the value of table_broadcast_info.time
     *
     * @mbggenerated
     */
    public Date getTime() {
        return time;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_broadcast_info.time
     *
     * @param time the value for table_broadcast_info.time
     *
     * @mbggenerated
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_broadcast_info.is_delete
     *
     * @return the value of table_broadcast_info.is_delete
     *
     * @mbggenerated
     */
    public Integer getIsDelete() {
        return isDelete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_broadcast_info.is_delete
     *
     * @param isDelete the value for table_broadcast_info.is_delete
     *
     * @mbggenerated
     */
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_broadcast_info.member_level
     *
     * @return the value of table_broadcast_info.member_level
     *
     * @mbggenerated
     */
    public Integer getMemberLevel() {
        return memberLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_broadcast_info.member_level
     *
     * @param memberLevel the value for table_broadcast_info.member_level
     *
     * @mbggenerated
     */
    public void setMemberLevel(Integer memberLevel) {
        this.memberLevel = memberLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_broadcast_info.mRank_level
     *
     * @return the value of table_broadcast_info.mRank_level
     *
     * @mbggenerated
     */
    public Integer getMrankLevel() {
        return mrankLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_broadcast_info.mRank_level
     *
     * @param mrankLevel the value for table_broadcast_info.mRank_level
     *
     * @mbggenerated
     */
    public void setMrankLevel(Integer mrankLevel) {
        this.mrankLevel = mrankLevel;
    }
}