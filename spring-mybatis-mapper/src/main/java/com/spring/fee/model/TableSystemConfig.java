package com.spring.fee.model;

import java.io.Serializable;

public class TableSystemConfig implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_system_config.sys_admin_cost
     *
     * @mbggenerated
     */
    private Float sysAdminCost;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_system_config.sys_cash_out_cost
     *
     * @mbggenerated
     */
    private Float sysCashOutCost;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_system_config.sys_bank_number
     *
     * @mbggenerated
     */
    private String sysBankNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_system_config.sys_bank_name
     *
     * @mbggenerated
     */
    private String sysBankName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column table_system_config.sys_bank_open_are
     *
     * @mbggenerated
     */
    private String sysBankOpenAre;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table table_system_config
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_system_config.sys_admin_cost
     *
     * @return the value of table_system_config.sys_admin_cost
     *
     * @mbggenerated
     */
    public Float getSysAdminCost() {
        return sysAdminCost;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_system_config.sys_admin_cost
     *
     * @param sysAdminCost the value for table_system_config.sys_admin_cost
     *
     * @mbggenerated
     */
    public void setSysAdminCost(Float sysAdminCost) {
        this.sysAdminCost = sysAdminCost;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_system_config.sys_cash_out_cost
     *
     * @return the value of table_system_config.sys_cash_out_cost
     *
     * @mbggenerated
     */
    public Float getSysCashOutCost() {
        return sysCashOutCost;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_system_config.sys_cash_out_cost
     *
     * @param sysCashOutCost the value for table_system_config.sys_cash_out_cost
     *
     * @mbggenerated
     */
    public void setSysCashOutCost(Float sysCashOutCost) {
        this.sysCashOutCost = sysCashOutCost;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_system_config.sys_bank_number
     *
     * @return the value of table_system_config.sys_bank_number
     *
     * @mbggenerated
     */
    public String getSysBankNumber() {
        return sysBankNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_system_config.sys_bank_number
     *
     * @param sysBankNumber the value for table_system_config.sys_bank_number
     *
     * @mbggenerated
     */
    public void setSysBankNumber(String sysBankNumber) {
        this.sysBankNumber = sysBankNumber == null ? null : sysBankNumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_system_config.sys_bank_name
     *
     * @return the value of table_system_config.sys_bank_name
     *
     * @mbggenerated
     */
    public String getSysBankName() {
        return sysBankName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_system_config.sys_bank_name
     *
     * @param sysBankName the value for table_system_config.sys_bank_name
     *
     * @mbggenerated
     */
    public void setSysBankName(String sysBankName) {
        this.sysBankName = sysBankName == null ? null : sysBankName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column table_system_config.sys_bank_open_are
     *
     * @return the value of table_system_config.sys_bank_open_are
     *
     * @mbggenerated
     */
    public String getSysBankOpenAre() {
        return sysBankOpenAre;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column table_system_config.sys_bank_open_are
     *
     * @param sysBankOpenAre the value for table_system_config.sys_bank_open_are
     *
     * @mbggenerated
     */
    public void setSysBankOpenAre(String sysBankOpenAre) {
        this.sysBankOpenAre = sysBankOpenAre == null ? null : sysBankOpenAre.trim();
    }
}