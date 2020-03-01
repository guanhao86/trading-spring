package com.spring.fee.model;

import java.util.ArrayList;
import java.util.List;

public class TableSystemConfigExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table table_system_config
     *
     * @mbggenerated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table table_system_config
     *
     * @mbggenerated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table table_system_config
     *
     * @mbggenerated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_system_config
     *
     * @mbggenerated
     */
    public TableSystemConfigExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_system_config
     *
     * @mbggenerated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_system_config
     *
     * @mbggenerated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_system_config
     *
     * @mbggenerated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_system_config
     *
     * @mbggenerated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_system_config
     *
     * @mbggenerated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_system_config
     *
     * @mbggenerated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_system_config
     *
     * @mbggenerated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_system_config
     *
     * @mbggenerated
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_system_config
     *
     * @mbggenerated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_system_config
     *
     * @mbggenerated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table table_system_config
     *
     * @mbggenerated
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andSysAdminCostIsNull() {
            addCriterion("sys_admin_cost is null");
            return (Criteria) this;
        }

        public Criteria andSysAdminCostIsNotNull() {
            addCriterion("sys_admin_cost is not null");
            return (Criteria) this;
        }

        public Criteria andSysAdminCostEqualTo(Float value) {
            addCriterion("sys_admin_cost =", value, "sysAdminCost");
            return (Criteria) this;
        }

        public Criteria andSysAdminCostNotEqualTo(Float value) {
            addCriterion("sys_admin_cost <>", value, "sysAdminCost");
            return (Criteria) this;
        }

        public Criteria andSysAdminCostGreaterThan(Float value) {
            addCriterion("sys_admin_cost >", value, "sysAdminCost");
            return (Criteria) this;
        }

        public Criteria andSysAdminCostGreaterThanOrEqualTo(Float value) {
            addCriterion("sys_admin_cost >=", value, "sysAdminCost");
            return (Criteria) this;
        }

        public Criteria andSysAdminCostLessThan(Float value) {
            addCriterion("sys_admin_cost <", value, "sysAdminCost");
            return (Criteria) this;
        }

        public Criteria andSysAdminCostLessThanOrEqualTo(Float value) {
            addCriterion("sys_admin_cost <=", value, "sysAdminCost");
            return (Criteria) this;
        }

        public Criteria andSysAdminCostIn(List<Float> values) {
            addCriterion("sys_admin_cost in", values, "sysAdminCost");
            return (Criteria) this;
        }

        public Criteria andSysAdminCostNotIn(List<Float> values) {
            addCriterion("sys_admin_cost not in", values, "sysAdminCost");
            return (Criteria) this;
        }

        public Criteria andSysAdminCostBetween(Float value1, Float value2) {
            addCriterion("sys_admin_cost between", value1, value2, "sysAdminCost");
            return (Criteria) this;
        }

        public Criteria andSysAdminCostNotBetween(Float value1, Float value2) {
            addCriterion("sys_admin_cost not between", value1, value2, "sysAdminCost");
            return (Criteria) this;
        }

        public Criteria andSysCashOutCostIsNull() {
            addCriterion("sys_cash_out_cost is null");
            return (Criteria) this;
        }

        public Criteria andSysCashOutCostIsNotNull() {
            addCriterion("sys_cash_out_cost is not null");
            return (Criteria) this;
        }

        public Criteria andSysCashOutCostEqualTo(Float value) {
            addCriterion("sys_cash_out_cost =", value, "sysCashOutCost");
            return (Criteria) this;
        }

        public Criteria andSysCashOutCostNotEqualTo(Float value) {
            addCriterion("sys_cash_out_cost <>", value, "sysCashOutCost");
            return (Criteria) this;
        }

        public Criteria andSysCashOutCostGreaterThan(Float value) {
            addCriterion("sys_cash_out_cost >", value, "sysCashOutCost");
            return (Criteria) this;
        }

        public Criteria andSysCashOutCostGreaterThanOrEqualTo(Float value) {
            addCriterion("sys_cash_out_cost >=", value, "sysCashOutCost");
            return (Criteria) this;
        }

        public Criteria andSysCashOutCostLessThan(Float value) {
            addCriterion("sys_cash_out_cost <", value, "sysCashOutCost");
            return (Criteria) this;
        }

        public Criteria andSysCashOutCostLessThanOrEqualTo(Float value) {
            addCriterion("sys_cash_out_cost <=", value, "sysCashOutCost");
            return (Criteria) this;
        }

        public Criteria andSysCashOutCostIn(List<Float> values) {
            addCriterion("sys_cash_out_cost in", values, "sysCashOutCost");
            return (Criteria) this;
        }

        public Criteria andSysCashOutCostNotIn(List<Float> values) {
            addCriterion("sys_cash_out_cost not in", values, "sysCashOutCost");
            return (Criteria) this;
        }

        public Criteria andSysCashOutCostBetween(Float value1, Float value2) {
            addCriterion("sys_cash_out_cost between", value1, value2, "sysCashOutCost");
            return (Criteria) this;
        }

        public Criteria andSysCashOutCostNotBetween(Float value1, Float value2) {
            addCriterion("sys_cash_out_cost not between", value1, value2, "sysCashOutCost");
            return (Criteria) this;
        }

        public Criteria andSysBankNumberIsNull() {
            addCriterion("sys_bank_number is null");
            return (Criteria) this;
        }

        public Criteria andSysBankNumberIsNotNull() {
            addCriterion("sys_bank_number is not null");
            return (Criteria) this;
        }

        public Criteria andSysBankNumberEqualTo(String value) {
            addCriterion("sys_bank_number =", value, "sysBankNumber");
            return (Criteria) this;
        }

        public Criteria andSysBankNumberNotEqualTo(String value) {
            addCriterion("sys_bank_number <>", value, "sysBankNumber");
            return (Criteria) this;
        }

        public Criteria andSysBankNumberGreaterThan(String value) {
            addCriterion("sys_bank_number >", value, "sysBankNumber");
            return (Criteria) this;
        }

        public Criteria andSysBankNumberGreaterThanOrEqualTo(String value) {
            addCriterion("sys_bank_number >=", value, "sysBankNumber");
            return (Criteria) this;
        }

        public Criteria andSysBankNumberLessThan(String value) {
            addCriterion("sys_bank_number <", value, "sysBankNumber");
            return (Criteria) this;
        }

        public Criteria andSysBankNumberLessThanOrEqualTo(String value) {
            addCriterion("sys_bank_number <=", value, "sysBankNumber");
            return (Criteria) this;
        }

        public Criteria andSysBankNumberLike(String value) {
            addCriterion("sys_bank_number like", value, "sysBankNumber");
            return (Criteria) this;
        }

        public Criteria andSysBankNumberNotLike(String value) {
            addCriterion("sys_bank_number not like", value, "sysBankNumber");
            return (Criteria) this;
        }

        public Criteria andSysBankNumberIn(List<String> values) {
            addCriterion("sys_bank_number in", values, "sysBankNumber");
            return (Criteria) this;
        }

        public Criteria andSysBankNumberNotIn(List<String> values) {
            addCriterion("sys_bank_number not in", values, "sysBankNumber");
            return (Criteria) this;
        }

        public Criteria andSysBankNumberBetween(String value1, String value2) {
            addCriterion("sys_bank_number between", value1, value2, "sysBankNumber");
            return (Criteria) this;
        }

        public Criteria andSysBankNumberNotBetween(String value1, String value2) {
            addCriterion("sys_bank_number not between", value1, value2, "sysBankNumber");
            return (Criteria) this;
        }

        public Criteria andSysBankNameIsNull() {
            addCriterion("sys_bank_name is null");
            return (Criteria) this;
        }

        public Criteria andSysBankNameIsNotNull() {
            addCriterion("sys_bank_name is not null");
            return (Criteria) this;
        }

        public Criteria andSysBankNameEqualTo(String value) {
            addCriterion("sys_bank_name =", value, "sysBankName");
            return (Criteria) this;
        }

        public Criteria andSysBankNameNotEqualTo(String value) {
            addCriterion("sys_bank_name <>", value, "sysBankName");
            return (Criteria) this;
        }

        public Criteria andSysBankNameGreaterThan(String value) {
            addCriterion("sys_bank_name >", value, "sysBankName");
            return (Criteria) this;
        }

        public Criteria andSysBankNameGreaterThanOrEqualTo(String value) {
            addCriterion("sys_bank_name >=", value, "sysBankName");
            return (Criteria) this;
        }

        public Criteria andSysBankNameLessThan(String value) {
            addCriterion("sys_bank_name <", value, "sysBankName");
            return (Criteria) this;
        }

        public Criteria andSysBankNameLessThanOrEqualTo(String value) {
            addCriterion("sys_bank_name <=", value, "sysBankName");
            return (Criteria) this;
        }

        public Criteria andSysBankNameLike(String value) {
            addCriterion("sys_bank_name like", value, "sysBankName");
            return (Criteria) this;
        }

        public Criteria andSysBankNameNotLike(String value) {
            addCriterion("sys_bank_name not like", value, "sysBankName");
            return (Criteria) this;
        }

        public Criteria andSysBankNameIn(List<String> values) {
            addCriterion("sys_bank_name in", values, "sysBankName");
            return (Criteria) this;
        }

        public Criteria andSysBankNameNotIn(List<String> values) {
            addCriterion("sys_bank_name not in", values, "sysBankName");
            return (Criteria) this;
        }

        public Criteria andSysBankNameBetween(String value1, String value2) {
            addCriterion("sys_bank_name between", value1, value2, "sysBankName");
            return (Criteria) this;
        }

        public Criteria andSysBankNameNotBetween(String value1, String value2) {
            addCriterion("sys_bank_name not between", value1, value2, "sysBankName");
            return (Criteria) this;
        }

        public Criteria andSysBankOpenAreIsNull() {
            addCriterion("sys_bank_open_are is null");
            return (Criteria) this;
        }

        public Criteria andSysBankOpenAreIsNotNull() {
            addCriterion("sys_bank_open_are is not null");
            return (Criteria) this;
        }

        public Criteria andSysBankOpenAreEqualTo(String value) {
            addCriterion("sys_bank_open_are =", value, "sysBankOpenAre");
            return (Criteria) this;
        }

        public Criteria andSysBankOpenAreNotEqualTo(String value) {
            addCriterion("sys_bank_open_are <>", value, "sysBankOpenAre");
            return (Criteria) this;
        }

        public Criteria andSysBankOpenAreGreaterThan(String value) {
            addCriterion("sys_bank_open_are >", value, "sysBankOpenAre");
            return (Criteria) this;
        }

        public Criteria andSysBankOpenAreGreaterThanOrEqualTo(String value) {
            addCriterion("sys_bank_open_are >=", value, "sysBankOpenAre");
            return (Criteria) this;
        }

        public Criteria andSysBankOpenAreLessThan(String value) {
            addCriterion("sys_bank_open_are <", value, "sysBankOpenAre");
            return (Criteria) this;
        }

        public Criteria andSysBankOpenAreLessThanOrEqualTo(String value) {
            addCriterion("sys_bank_open_are <=", value, "sysBankOpenAre");
            return (Criteria) this;
        }

        public Criteria andSysBankOpenAreLike(String value) {
            addCriterion("sys_bank_open_are like", value, "sysBankOpenAre");
            return (Criteria) this;
        }

        public Criteria andSysBankOpenAreNotLike(String value) {
            addCriterion("sys_bank_open_are not like", value, "sysBankOpenAre");
            return (Criteria) this;
        }

        public Criteria andSysBankOpenAreIn(List<String> values) {
            addCriterion("sys_bank_open_are in", values, "sysBankOpenAre");
            return (Criteria) this;
        }

        public Criteria andSysBankOpenAreNotIn(List<String> values) {
            addCriterion("sys_bank_open_are not in", values, "sysBankOpenAre");
            return (Criteria) this;
        }

        public Criteria andSysBankOpenAreBetween(String value1, String value2) {
            addCriterion("sys_bank_open_are between", value1, value2, "sysBankOpenAre");
            return (Criteria) this;
        }

        public Criteria andSysBankOpenAreNotBetween(String value1, String value2) {
            addCriterion("sys_bank_open_are not between", value1, value2, "sysBankOpenAre");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table table_system_config
     *
     * @mbggenerated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table table_system_config
     *
     * @mbggenerated
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}