package com.spring.fee.model;

import java.util.ArrayList;
import java.util.List;

public class TWheatAccountExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_wheat_account
     *
     * @mbggenerated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_wheat_account
     *
     * @mbggenerated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_wheat_account
     *
     * @mbggenerated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_wheat_account
     *
     * @mbggenerated
     */
    public TWheatAccountExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_wheat_account
     *
     * @mbggenerated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_wheat_account
     *
     * @mbggenerated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_wheat_account
     *
     * @mbggenerated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_wheat_account
     *
     * @mbggenerated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_wheat_account
     *
     * @mbggenerated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_wheat_account
     *
     * @mbggenerated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_wheat_account
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
     * This method corresponds to the database table t_wheat_account
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
     * This method corresponds to the database table t_wheat_account
     *
     * @mbggenerated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_wheat_account
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
     * This class corresponds to the database table t_wheat_account
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

        public Criteria andMemberIdIsNull() {
            addCriterion("member_id is null");
            return (Criteria) this;
        }

        public Criteria andMemberIdIsNotNull() {
            addCriterion("member_id is not null");
            return (Criteria) this;
        }

        public Criteria andMemberIdEqualTo(String value) {
            addCriterion("member_id =", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotEqualTo(String value) {
            addCriterion("member_id <>", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdGreaterThan(String value) {
            addCriterion("member_id >", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdGreaterThanOrEqualTo(String value) {
            addCriterion("member_id >=", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdLessThan(String value) {
            addCriterion("member_id <", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdLessThanOrEqualTo(String value) {
            addCriterion("member_id <=", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdLike(String value) {
            addCriterion("member_id like", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotLike(String value) {
            addCriterion("member_id not like", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdIn(List<String> values) {
            addCriterion("member_id in", values, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotIn(List<String> values) {
            addCriterion("member_id not in", values, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdBetween(String value1, String value2) {
            addCriterion("member_id between", value1, value2, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotBetween(String value1, String value2) {
            addCriterion("member_id not between", value1, value2, "memberId");
            return (Criteria) this;
        }

        public Criteria andTotalIsNull() {
            addCriterion("total is null");
            return (Criteria) this;
        }

        public Criteria andTotalIsNotNull() {
            addCriterion("total is not null");
            return (Criteria) this;
        }

        public Criteria andTotalEqualTo(Long value) {
            addCriterion("total =", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalNotEqualTo(Long value) {
            addCriterion("total <>", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalGreaterThan(Long value) {
            addCriterion("total >", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalGreaterThanOrEqualTo(Long value) {
            addCriterion("total >=", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalLessThan(Long value) {
            addCriterion("total <", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalLessThanOrEqualTo(Long value) {
            addCriterion("total <=", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalIn(List<Long> values) {
            addCriterion("total in", values, "total");
            return (Criteria) this;
        }

        public Criteria andTotalNotIn(List<Long> values) {
            addCriterion("total not in", values, "total");
            return (Criteria) this;
        }

        public Criteria andTotalBetween(Long value1, Long value2) {
            addCriterion("total between", value1, value2, "total");
            return (Criteria) this;
        }

        public Criteria andTotalNotBetween(Long value1, Long value2) {
            addCriterion("total not between", value1, value2, "total");
            return (Criteria) this;
        }

        public Criteria andAvailableIsNull() {
            addCriterion("available is null");
            return (Criteria) this;
        }

        public Criteria andAvailableIsNotNull() {
            addCriterion("available is not null");
            return (Criteria) this;
        }

        public Criteria andAvailableEqualTo(Long value) {
            addCriterion("available =", value, "available");
            return (Criteria) this;
        }

        public Criteria andAvailableNotEqualTo(Long value) {
            addCriterion("available <>", value, "available");
            return (Criteria) this;
        }

        public Criteria andAvailableGreaterThan(Long value) {
            addCriterion("available >", value, "available");
            return (Criteria) this;
        }

        public Criteria andAvailableGreaterThanOrEqualTo(Long value) {
            addCriterion("available >=", value, "available");
            return (Criteria) this;
        }

        public Criteria andAvailableLessThan(Long value) {
            addCriterion("available <", value, "available");
            return (Criteria) this;
        }

        public Criteria andAvailableLessThanOrEqualTo(Long value) {
            addCriterion("available <=", value, "available");
            return (Criteria) this;
        }

        public Criteria andAvailableIn(List<Long> values) {
            addCriterion("available in", values, "available");
            return (Criteria) this;
        }

        public Criteria andAvailableNotIn(List<Long> values) {
            addCriterion("available not in", values, "available");
            return (Criteria) this;
        }

        public Criteria andAvailableBetween(Long value1, Long value2) {
            addCriterion("available between", value1, value2, "available");
            return (Criteria) this;
        }

        public Criteria andAvailableNotBetween(Long value1, Long value2) {
            addCriterion("available not between", value1, value2, "available");
            return (Criteria) this;
        }

        public Criteria andFreezeIsNull() {
            addCriterion("freeze is null");
            return (Criteria) this;
        }

        public Criteria andFreezeIsNotNull() {
            addCriterion("freeze is not null");
            return (Criteria) this;
        }

        public Criteria andFreezeEqualTo(Long value) {
            addCriterion("freeze =", value, "freeze");
            return (Criteria) this;
        }

        public Criteria andFreezeNotEqualTo(Long value) {
            addCriterion("freeze <>", value, "freeze");
            return (Criteria) this;
        }

        public Criteria andFreezeGreaterThan(Long value) {
            addCriterion("freeze >", value, "freeze");
            return (Criteria) this;
        }

        public Criteria andFreezeGreaterThanOrEqualTo(Long value) {
            addCriterion("freeze >=", value, "freeze");
            return (Criteria) this;
        }

        public Criteria andFreezeLessThan(Long value) {
            addCriterion("freeze <", value, "freeze");
            return (Criteria) this;
        }

        public Criteria andFreezeLessThanOrEqualTo(Long value) {
            addCriterion("freeze <=", value, "freeze");
            return (Criteria) this;
        }

        public Criteria andFreezeIn(List<Long> values) {
            addCriterion("freeze in", values, "freeze");
            return (Criteria) this;
        }

        public Criteria andFreezeNotIn(List<Long> values) {
            addCriterion("freeze not in", values, "freeze");
            return (Criteria) this;
        }

        public Criteria andFreezeBetween(Long value1, Long value2) {
            addCriterion("freeze between", value1, value2, "freeze");
            return (Criteria) this;
        }

        public Criteria andFreezeNotBetween(Long value1, Long value2) {
            addCriterion("freeze not between", value1, value2, "freeze");
            return (Criteria) this;
        }

        public Criteria andMoneyFreezeIsNull() {
            addCriterion("money_freeze is null");
            return (Criteria) this;
        }

        public Criteria andMoneyFreezeIsNotNull() {
            addCriterion("money_freeze is not null");
            return (Criteria) this;
        }

        public Criteria andMoneyFreezeEqualTo(Long value) {
            addCriterion("money_freeze =", value, "moneyFreeze");
            return (Criteria) this;
        }

        public Criteria andMoneyFreezeNotEqualTo(Long value) {
            addCriterion("money_freeze <>", value, "moneyFreeze");
            return (Criteria) this;
        }

        public Criteria andMoneyFreezeGreaterThan(Long value) {
            addCriterion("money_freeze >", value, "moneyFreeze");
            return (Criteria) this;
        }

        public Criteria andMoneyFreezeGreaterThanOrEqualTo(Long value) {
            addCriterion("money_freeze >=", value, "moneyFreeze");
            return (Criteria) this;
        }

        public Criteria andMoneyFreezeLessThan(Long value) {
            addCriterion("money_freeze <", value, "moneyFreeze");
            return (Criteria) this;
        }

        public Criteria andMoneyFreezeLessThanOrEqualTo(Long value) {
            addCriterion("money_freeze <=", value, "moneyFreeze");
            return (Criteria) this;
        }

        public Criteria andMoneyFreezeIn(List<Long> values) {
            addCriterion("money_freeze in", values, "moneyFreeze");
            return (Criteria) this;
        }

        public Criteria andMoneyFreezeNotIn(List<Long> values) {
            addCriterion("money_freeze not in", values, "moneyFreeze");
            return (Criteria) this;
        }

        public Criteria andMoneyFreezeBetween(Long value1, Long value2) {
            addCriterion("money_freeze between", value1, value2, "moneyFreeze");
            return (Criteria) this;
        }

        public Criteria andMoneyFreezeNotBetween(Long value1, Long value2) {
            addCriterion("money_freeze not between", value1, value2, "moneyFreeze");
            return (Criteria) this;
        }

        public Criteria andGranaryFreezeIsNull() {
            addCriterion("granary_freeze is null");
            return (Criteria) this;
        }

        public Criteria andGranaryFreezeIsNotNull() {
            addCriterion("granary_freeze is not null");
            return (Criteria) this;
        }

        public Criteria andGranaryFreezeEqualTo(Long value) {
            addCriterion("granary_freeze =", value, "granaryFreeze");
            return (Criteria) this;
        }

        public Criteria andGranaryFreezeNotEqualTo(Long value) {
            addCriterion("granary_freeze <>", value, "granaryFreeze");
            return (Criteria) this;
        }

        public Criteria andGranaryFreezeGreaterThan(Long value) {
            addCriterion("granary_freeze >", value, "granaryFreeze");
            return (Criteria) this;
        }

        public Criteria andGranaryFreezeGreaterThanOrEqualTo(Long value) {
            addCriterion("granary_freeze >=", value, "granaryFreeze");
            return (Criteria) this;
        }

        public Criteria andGranaryFreezeLessThan(Long value) {
            addCriterion("granary_freeze <", value, "granaryFreeze");
            return (Criteria) this;
        }

        public Criteria andGranaryFreezeLessThanOrEqualTo(Long value) {
            addCriterion("granary_freeze <=", value, "granaryFreeze");
            return (Criteria) this;
        }

        public Criteria andGranaryFreezeIn(List<Long> values) {
            addCriterion("granary_freeze in", values, "granaryFreeze");
            return (Criteria) this;
        }

        public Criteria andGranaryFreezeNotIn(List<Long> values) {
            addCriterion("granary_freeze not in", values, "granaryFreeze");
            return (Criteria) this;
        }

        public Criteria andGranaryFreezeBetween(Long value1, Long value2) {
            addCriterion("granary_freeze between", value1, value2, "granaryFreeze");
            return (Criteria) this;
        }

        public Criteria andGranaryFreezeNotBetween(Long value1, Long value2) {
            addCriterion("granary_freeze not between", value1, value2, "granaryFreeze");
            return (Criteria) this;
        }

        public Criteria andGranaryIngFreezeIsNull() {
            addCriterion("granary_ing_freeze is null");
            return (Criteria) this;
        }

        public Criteria andGranaryIngFreezeIsNotNull() {
            addCriterion("granary_ing_freeze is not null");
            return (Criteria) this;
        }

        public Criteria andGranaryIngFreezeEqualTo(Long value) {
            addCriterion("granary_ing_freeze =", value, "granaryIngFreeze");
            return (Criteria) this;
        }

        public Criteria andGranaryIngFreezeNotEqualTo(Long value) {
            addCriterion("granary_ing_freeze <>", value, "granaryIngFreeze");
            return (Criteria) this;
        }

        public Criteria andGranaryIngFreezeGreaterThan(Long value) {
            addCriterion("granary_ing_freeze >", value, "granaryIngFreeze");
            return (Criteria) this;
        }

        public Criteria andGranaryIngFreezeGreaterThanOrEqualTo(Long value) {
            addCriterion("granary_ing_freeze >=", value, "granaryIngFreeze");
            return (Criteria) this;
        }

        public Criteria andGranaryIngFreezeLessThan(Long value) {
            addCriterion("granary_ing_freeze <", value, "granaryIngFreeze");
            return (Criteria) this;
        }

        public Criteria andGranaryIngFreezeLessThanOrEqualTo(Long value) {
            addCriterion("granary_ing_freeze <=", value, "granaryIngFreeze");
            return (Criteria) this;
        }

        public Criteria andGranaryIngFreezeIn(List<Long> values) {
            addCriterion("granary_ing_freeze in", values, "granaryIngFreeze");
            return (Criteria) this;
        }

        public Criteria andGranaryIngFreezeNotIn(List<Long> values) {
            addCriterion("granary_ing_freeze not in", values, "granaryIngFreeze");
            return (Criteria) this;
        }

        public Criteria andGranaryIngFreezeBetween(Long value1, Long value2) {
            addCriterion("granary_ing_freeze between", value1, value2, "granaryIngFreeze");
            return (Criteria) this;
        }

        public Criteria andGranaryIngFreezeNotBetween(Long value1, Long value2) {
            addCriterion("granary_ing_freeze not between", value1, value2, "granaryIngFreeze");
            return (Criteria) this;
        }

        public Criteria andGranaryIngMaxFreezeIsNull() {
            addCriterion("granary_ing_max_freeze is null");
            return (Criteria) this;
        }

        public Criteria andGranaryIngMaxFreezeIsNotNull() {
            addCriterion("granary_ing_max_freeze is not null");
            return (Criteria) this;
        }

        public Criteria andGranaryIngMaxFreezeEqualTo(Long value) {
            addCriterion("granary_ing_max_freeze =", value, "granaryIngMaxFreeze");
            return (Criteria) this;
        }

        public Criteria andGranaryIngMaxFreezeNotEqualTo(Long value) {
            addCriterion("granary_ing_max_freeze <>", value, "granaryIngMaxFreeze");
            return (Criteria) this;
        }

        public Criteria andGranaryIngMaxFreezeGreaterThan(Long value) {
            addCriterion("granary_ing_max_freeze >", value, "granaryIngMaxFreeze");
            return (Criteria) this;
        }

        public Criteria andGranaryIngMaxFreezeGreaterThanOrEqualTo(Long value) {
            addCriterion("granary_ing_max_freeze >=", value, "granaryIngMaxFreeze");
            return (Criteria) this;
        }

        public Criteria andGranaryIngMaxFreezeLessThan(Long value) {
            addCriterion("granary_ing_max_freeze <", value, "granaryIngMaxFreeze");
            return (Criteria) this;
        }

        public Criteria andGranaryIngMaxFreezeLessThanOrEqualTo(Long value) {
            addCriterion("granary_ing_max_freeze <=", value, "granaryIngMaxFreeze");
            return (Criteria) this;
        }

        public Criteria andGranaryIngMaxFreezeIn(List<Long> values) {
            addCriterion("granary_ing_max_freeze in", values, "granaryIngMaxFreeze");
            return (Criteria) this;
        }

        public Criteria andGranaryIngMaxFreezeNotIn(List<Long> values) {
            addCriterion("granary_ing_max_freeze not in", values, "granaryIngMaxFreeze");
            return (Criteria) this;
        }

        public Criteria andGranaryIngMaxFreezeBetween(Long value1, Long value2) {
            addCriterion("granary_ing_max_freeze between", value1, value2, "granaryIngMaxFreeze");
            return (Criteria) this;
        }

        public Criteria andGranaryIngMaxFreezeNotBetween(Long value1, Long value2) {
            addCriterion("granary_ing_max_freeze not between", value1, value2, "granaryIngMaxFreeze");
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

        public Criteria andGranaryMaxFreezeChildrenIsNull() {
            addCriterion("granary_max_freeze_children is null");
            return (Criteria) this;
        }

        public Criteria andGranaryMaxFreezeChildrenIsNotNull() {
            addCriterion("granary_max_freeze_children is not null");
            return (Criteria) this;
        }

        public Criteria andGranaryMaxFreezeChildrenEqualTo(Long value) {
            addCriterion("granary_max_freeze_children =", value, "granaryMaxFreezeChildren");
            return (Criteria) this;
        }

        public Criteria andGranaryMaxFreezeChildrenNotEqualTo(Long value) {
            addCriterion("granary_max_freeze_children <>", value, "granaryMaxFreezeChildren");
            return (Criteria) this;
        }

        public Criteria andGranaryMaxFreezeChildrenGreaterThan(Long value) {
            addCriterion("granary_max_freeze_children >", value, "granaryMaxFreezeChildren");
            return (Criteria) this;
        }

        public Criteria andGranaryMaxFreezeChildrenGreaterThanOrEqualTo(Long value) {
            addCriterion("granary_max_freeze_children >=", value, "granaryMaxFreezeChildren");
            return (Criteria) this;
        }

        public Criteria andGranaryMaxFreezeChildrenLessThan(Long value) {
            addCriterion("granary_max_freeze_children <", value, "granaryMaxFreezeChildren");
            return (Criteria) this;
        }

        public Criteria andGranaryMaxFreezeChildrenLessThanOrEqualTo(Long value) {
            addCriterion("granary_max_freeze_children <=", value, "granaryMaxFreezeChildren");
            return (Criteria) this;
        }

        public Criteria andGranaryMaxFreezeChildrenIn(List<Long> values) {
            addCriterion("granary_max_freeze_children in", values, "granaryMaxFreezeChildren");
            return (Criteria) this;
        }

        public Criteria andGranaryMaxFreezeChildrenNotIn(List<Long> values) {
            addCriterion("granary_max_freeze_children not in", values, "granaryMaxFreezeChildren");
            return (Criteria) this;
        }

        public Criteria andGranaryMaxFreezeChildrenBetween(Long value1, Long value2) {
            addCriterion("granary_max_freeze_children between", value1, value2, "granaryMaxFreezeChildren");
            return (Criteria) this;
        }

        public Criteria andGranaryMaxFreezeChildrenNotBetween(Long value1, Long value2) {
            addCriterion("granary_max_freeze_children not between", value1, value2, "granaryMaxFreezeChildren");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_wheat_account
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
     * This class corresponds to the database table t_wheat_account
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