package com.spring.fee.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TInvestPlanMainLogExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_invest_plan_main_log
     *
     * @mbggenerated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_invest_plan_main_log
     *
     * @mbggenerated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_invest_plan_main_log
     *
     * @mbggenerated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_invest_plan_main_log
     *
     * @mbggenerated
     */
    public TInvestPlanMainLogExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_invest_plan_main_log
     *
     * @mbggenerated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_invest_plan_main_log
     *
     * @mbggenerated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_invest_plan_main_log
     *
     * @mbggenerated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_invest_plan_main_log
     *
     * @mbggenerated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_invest_plan_main_log
     *
     * @mbggenerated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_invest_plan_main_log
     *
     * @mbggenerated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_invest_plan_main_log
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
     * This method corresponds to the database table t_invest_plan_main_log
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
     * This method corresponds to the database table t_invest_plan_main_log
     *
     * @mbggenerated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_invest_plan_main_log
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
     * This class corresponds to the database table t_invest_plan_main_log
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andSettleRecordIdIsNull() {
            addCriterion("settle_record_id is null");
            return (Criteria) this;
        }

        public Criteria andSettleRecordIdIsNotNull() {
            addCriterion("settle_record_id is not null");
            return (Criteria) this;
        }

        public Criteria andSettleRecordIdEqualTo(Long value) {
            addCriterion("settle_record_id =", value, "settleRecordId");
            return (Criteria) this;
        }

        public Criteria andSettleRecordIdNotEqualTo(Long value) {
            addCriterion("settle_record_id <>", value, "settleRecordId");
            return (Criteria) this;
        }

        public Criteria andSettleRecordIdGreaterThan(Long value) {
            addCriterion("settle_record_id >", value, "settleRecordId");
            return (Criteria) this;
        }

        public Criteria andSettleRecordIdGreaterThanOrEqualTo(Long value) {
            addCriterion("settle_record_id >=", value, "settleRecordId");
            return (Criteria) this;
        }

        public Criteria andSettleRecordIdLessThan(Long value) {
            addCriterion("settle_record_id <", value, "settleRecordId");
            return (Criteria) this;
        }

        public Criteria andSettleRecordIdLessThanOrEqualTo(Long value) {
            addCriterion("settle_record_id <=", value, "settleRecordId");
            return (Criteria) this;
        }

        public Criteria andSettleRecordIdIn(List<Long> values) {
            addCriterion("settle_record_id in", values, "settleRecordId");
            return (Criteria) this;
        }

        public Criteria andSettleRecordIdNotIn(List<Long> values) {
            addCriterion("settle_record_id not in", values, "settleRecordId");
            return (Criteria) this;
        }

        public Criteria andSettleRecordIdBetween(Long value1, Long value2) {
            addCriterion("settle_record_id between", value1, value2, "settleRecordId");
            return (Criteria) this;
        }

        public Criteria andSettleRecordIdNotBetween(Long value1, Long value2) {
            addCriterion("settle_record_id not between", value1, value2, "settleRecordId");
            return (Criteria) this;
        }

        public Criteria andPlanMainIdIsNull() {
            addCriterion("plan_main_id is null");
            return (Criteria) this;
        }

        public Criteria andPlanMainIdIsNotNull() {
            addCriterion("plan_main_id is not null");
            return (Criteria) this;
        }

        public Criteria andPlanMainIdEqualTo(String value) {
            addCriterion("plan_main_id =", value, "planMainId");
            return (Criteria) this;
        }

        public Criteria andPlanMainIdNotEqualTo(String value) {
            addCriterion("plan_main_id <>", value, "planMainId");
            return (Criteria) this;
        }

        public Criteria andPlanMainIdGreaterThan(String value) {
            addCriterion("plan_main_id >", value, "planMainId");
            return (Criteria) this;
        }

        public Criteria andPlanMainIdGreaterThanOrEqualTo(String value) {
            addCriterion("plan_main_id >=", value, "planMainId");
            return (Criteria) this;
        }

        public Criteria andPlanMainIdLessThan(String value) {
            addCriterion("plan_main_id <", value, "planMainId");
            return (Criteria) this;
        }

        public Criteria andPlanMainIdLessThanOrEqualTo(String value) {
            addCriterion("plan_main_id <=", value, "planMainId");
            return (Criteria) this;
        }

        public Criteria andPlanMainIdLike(String value) {
            addCriterion("plan_main_id like", value, "planMainId");
            return (Criteria) this;
        }

        public Criteria andPlanMainIdNotLike(String value) {
            addCriterion("plan_main_id not like", value, "planMainId");
            return (Criteria) this;
        }

        public Criteria andPlanMainIdIn(List<String> values) {
            addCriterion("plan_main_id in", values, "planMainId");
            return (Criteria) this;
        }

        public Criteria andPlanMainIdNotIn(List<String> values) {
            addCriterion("plan_main_id not in", values, "planMainId");
            return (Criteria) this;
        }

        public Criteria andPlanMainIdBetween(String value1, String value2) {
            addCriterion("plan_main_id between", value1, value2, "planMainId");
            return (Criteria) this;
        }

        public Criteria andPlanMainIdNotBetween(String value1, String value2) {
            addCriterion("plan_main_id not between", value1, value2, "planMainId");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("type like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("type not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("status not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andYyyymmddIsNull() {
            addCriterion("yyyymmdd is null");
            return (Criteria) this;
        }

        public Criteria andYyyymmddIsNotNull() {
            addCriterion("yyyymmdd is not null");
            return (Criteria) this;
        }

        public Criteria andYyyymmddEqualTo(String value) {
            addCriterion("yyyymmdd =", value, "yyyymmdd");
            return (Criteria) this;
        }

        public Criteria andYyyymmddNotEqualTo(String value) {
            addCriterion("yyyymmdd <>", value, "yyyymmdd");
            return (Criteria) this;
        }

        public Criteria andYyyymmddGreaterThan(String value) {
            addCriterion("yyyymmdd >", value, "yyyymmdd");
            return (Criteria) this;
        }

        public Criteria andYyyymmddGreaterThanOrEqualTo(String value) {
            addCriterion("yyyymmdd >=", value, "yyyymmdd");
            return (Criteria) this;
        }

        public Criteria andYyyymmddLessThan(String value) {
            addCriterion("yyyymmdd <", value, "yyyymmdd");
            return (Criteria) this;
        }

        public Criteria andYyyymmddLessThanOrEqualTo(String value) {
            addCriterion("yyyymmdd <=", value, "yyyymmdd");
            return (Criteria) this;
        }

        public Criteria andYyyymmddLike(String value) {
            addCriterion("yyyymmdd like", value, "yyyymmdd");
            return (Criteria) this;
        }

        public Criteria andYyyymmddNotLike(String value) {
            addCriterion("yyyymmdd not like", value, "yyyymmdd");
            return (Criteria) this;
        }

        public Criteria andYyyymmddIn(List<String> values) {
            addCriterion("yyyymmdd in", values, "yyyymmdd");
            return (Criteria) this;
        }

        public Criteria andYyyymmddNotIn(List<String> values) {
            addCriterion("yyyymmdd not in", values, "yyyymmdd");
            return (Criteria) this;
        }

        public Criteria andYyyymmddBetween(String value1, String value2) {
            addCriterion("yyyymmdd between", value1, value2, "yyyymmdd");
            return (Criteria) this;
        }

        public Criteria andYyyymmddNotBetween(String value1, String value2) {
            addCriterion("yyyymmdd not between", value1, value2, "yyyymmdd");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_invest_plan_main_log
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
     * This class corresponds to the database table t_invest_plan_main_log
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