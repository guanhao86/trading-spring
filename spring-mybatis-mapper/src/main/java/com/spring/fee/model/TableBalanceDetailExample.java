package com.spring.fee.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TableBalanceDetailExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table table_balance_detail
     *
     * @mbggenerated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table table_balance_detail
     *
     * @mbggenerated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table table_balance_detail
     *
     * @mbggenerated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_balance_detail
     *
     * @mbggenerated
     */
    public TableBalanceDetailExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_balance_detail
     *
     * @mbggenerated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_balance_detail
     *
     * @mbggenerated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_balance_detail
     *
     * @mbggenerated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_balance_detail
     *
     * @mbggenerated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_balance_detail
     *
     * @mbggenerated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_balance_detail
     *
     * @mbggenerated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_balance_detail
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
     * This method corresponds to the database table table_balance_detail
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
     * This method corresponds to the database table table_balance_detail
     *
     * @mbggenerated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table table_balance_detail
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
     * This class corresponds to the database table table_balance_detail
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

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andAdminIdIsNull() {
            addCriterion("admin_id is null");
            return (Criteria) this;
        }

        public Criteria andAdminIdIsNotNull() {
            addCriterion("admin_id is not null");
            return (Criteria) this;
        }

        public Criteria andAdminIdEqualTo(String value) {
            addCriterion("admin_id =", value, "adminId");
            return (Criteria) this;
        }

        public Criteria andAdminIdNotEqualTo(String value) {
            addCriterion("admin_id <>", value, "adminId");
            return (Criteria) this;
        }

        public Criteria andAdminIdGreaterThan(String value) {
            addCriterion("admin_id >", value, "adminId");
            return (Criteria) this;
        }

        public Criteria andAdminIdGreaterThanOrEqualTo(String value) {
            addCriterion("admin_id >=", value, "adminId");
            return (Criteria) this;
        }

        public Criteria andAdminIdLessThan(String value) {
            addCriterion("admin_id <", value, "adminId");
            return (Criteria) this;
        }

        public Criteria andAdminIdLessThanOrEqualTo(String value) {
            addCriterion("admin_id <=", value, "adminId");
            return (Criteria) this;
        }

        public Criteria andAdminIdLike(String value) {
            addCriterion("admin_id like", value, "adminId");
            return (Criteria) this;
        }

        public Criteria andAdminIdNotLike(String value) {
            addCriterion("admin_id not like", value, "adminId");
            return (Criteria) this;
        }

        public Criteria andAdminIdIn(List<String> values) {
            addCriterion("admin_id in", values, "adminId");
            return (Criteria) this;
        }

        public Criteria andAdminIdNotIn(List<String> values) {
            addCriterion("admin_id not in", values, "adminId");
            return (Criteria) this;
        }

        public Criteria andAdminIdBetween(String value1, String value2) {
            addCriterion("admin_id between", value1, value2, "adminId");
            return (Criteria) this;
        }

        public Criteria andAdminIdNotBetween(String value1, String value2) {
            addCriterion("admin_id not between", value1, value2, "adminId");
            return (Criteria) this;
        }

        public Criteria andLastTimeIsNull() {
            addCriterion("last_time is null");
            return (Criteria) this;
        }

        public Criteria andLastTimeIsNotNull() {
            addCriterion("last_time is not null");
            return (Criteria) this;
        }

        public Criteria andLastTimeEqualTo(Date value) {
            addCriterion("last_time =", value, "lastTime");
            return (Criteria) this;
        }

        public Criteria andLastTimeNotEqualTo(Date value) {
            addCriterion("last_time <>", value, "lastTime");
            return (Criteria) this;
        }

        public Criteria andLastTimeGreaterThan(Date value) {
            addCriterion("last_time >", value, "lastTime");
            return (Criteria) this;
        }

        public Criteria andLastTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("last_time >=", value, "lastTime");
            return (Criteria) this;
        }

        public Criteria andLastTimeLessThan(Date value) {
            addCriterion("last_time <", value, "lastTime");
            return (Criteria) this;
        }

        public Criteria andLastTimeLessThanOrEqualTo(Date value) {
            addCriterion("last_time <=", value, "lastTime");
            return (Criteria) this;
        }

        public Criteria andLastTimeIn(List<Date> values) {
            addCriterion("last_time in", values, "lastTime");
            return (Criteria) this;
        }

        public Criteria andLastTimeNotIn(List<Date> values) {
            addCriterion("last_time not in", values, "lastTime");
            return (Criteria) this;
        }

        public Criteria andLastTimeBetween(Date value1, Date value2) {
            addCriterion("last_time between", value1, value2, "lastTime");
            return (Criteria) this;
        }

        public Criteria andLastTimeNotBetween(Date value1, Date value2) {
            addCriterion("last_time not between", value1, value2, "lastTime");
            return (Criteria) this;
        }

        public Criteria andBonusReferenceIsNull() {
            addCriterion("bonus_reference is null");
            return (Criteria) this;
        }

        public Criteria andBonusReferenceIsNotNull() {
            addCriterion("bonus_reference is not null");
            return (Criteria) this;
        }

        public Criteria andBonusReferenceEqualTo(Float value) {
            addCriterion("bonus_reference =", value, "bonusReference");
            return (Criteria) this;
        }

        public Criteria andBonusReferenceNotEqualTo(Float value) {
            addCriterion("bonus_reference <>", value, "bonusReference");
            return (Criteria) this;
        }

        public Criteria andBonusReferenceGreaterThan(Float value) {
            addCriterion("bonus_reference >", value, "bonusReference");
            return (Criteria) this;
        }

        public Criteria andBonusReferenceGreaterThanOrEqualTo(Float value) {
            addCriterion("bonus_reference >=", value, "bonusReference");
            return (Criteria) this;
        }

        public Criteria andBonusReferenceLessThan(Float value) {
            addCriterion("bonus_reference <", value, "bonusReference");
            return (Criteria) this;
        }

        public Criteria andBonusReferenceLessThanOrEqualTo(Float value) {
            addCriterion("bonus_reference <=", value, "bonusReference");
            return (Criteria) this;
        }

        public Criteria andBonusReferenceIn(List<Float> values) {
            addCriterion("bonus_reference in", values, "bonusReference");
            return (Criteria) this;
        }

        public Criteria andBonusReferenceNotIn(List<Float> values) {
            addCriterion("bonus_reference not in", values, "bonusReference");
            return (Criteria) this;
        }

        public Criteria andBonusReferenceBetween(Float value1, Float value2) {
            addCriterion("bonus_reference between", value1, value2, "bonusReference");
            return (Criteria) this;
        }

        public Criteria andBonusReferenceNotBetween(Float value1, Float value2) {
            addCriterion("bonus_reference not between", value1, value2, "bonusReference");
            return (Criteria) this;
        }

        public Criteria andBonusGroupIsNull() {
            addCriterion("bonus_group is null");
            return (Criteria) this;
        }

        public Criteria andBonusGroupIsNotNull() {
            addCriterion("bonus_group is not null");
            return (Criteria) this;
        }

        public Criteria andBonusGroupEqualTo(Float value) {
            addCriterion("bonus_group =", value, "bonusGroup");
            return (Criteria) this;
        }

        public Criteria andBonusGroupNotEqualTo(Float value) {
            addCriterion("bonus_group <>", value, "bonusGroup");
            return (Criteria) this;
        }

        public Criteria andBonusGroupGreaterThan(Float value) {
            addCriterion("bonus_group >", value, "bonusGroup");
            return (Criteria) this;
        }

        public Criteria andBonusGroupGreaterThanOrEqualTo(Float value) {
            addCriterion("bonus_group >=", value, "bonusGroup");
            return (Criteria) this;
        }

        public Criteria andBonusGroupLessThan(Float value) {
            addCriterion("bonus_group <", value, "bonusGroup");
            return (Criteria) this;
        }

        public Criteria andBonusGroupLessThanOrEqualTo(Float value) {
            addCriterion("bonus_group <=", value, "bonusGroup");
            return (Criteria) this;
        }

        public Criteria andBonusGroupIn(List<Float> values) {
            addCriterion("bonus_group in", values, "bonusGroup");
            return (Criteria) this;
        }

        public Criteria andBonusGroupNotIn(List<Float> values) {
            addCriterion("bonus_group not in", values, "bonusGroup");
            return (Criteria) this;
        }

        public Criteria andBonusGroupBetween(Float value1, Float value2) {
            addCriterion("bonus_group between", value1, value2, "bonusGroup");
            return (Criteria) this;
        }

        public Criteria andBonusGroupNotBetween(Float value1, Float value2) {
            addCriterion("bonus_group not between", value1, value2, "bonusGroup");
            return (Criteria) this;
        }

        public Criteria andBonusBoleIsNull() {
            addCriterion("bonus_bole is null");
            return (Criteria) this;
        }

        public Criteria andBonusBoleIsNotNull() {
            addCriterion("bonus_bole is not null");
            return (Criteria) this;
        }

        public Criteria andBonusBoleEqualTo(Float value) {
            addCriterion("bonus_bole =", value, "bonusBole");
            return (Criteria) this;
        }

        public Criteria andBonusBoleNotEqualTo(Float value) {
            addCriterion("bonus_bole <>", value, "bonusBole");
            return (Criteria) this;
        }

        public Criteria andBonusBoleGreaterThan(Float value) {
            addCriterion("bonus_bole >", value, "bonusBole");
            return (Criteria) this;
        }

        public Criteria andBonusBoleGreaterThanOrEqualTo(Float value) {
            addCriterion("bonus_bole >=", value, "bonusBole");
            return (Criteria) this;
        }

        public Criteria andBonusBoleLessThan(Float value) {
            addCriterion("bonus_bole <", value, "bonusBole");
            return (Criteria) this;
        }

        public Criteria andBonusBoleLessThanOrEqualTo(Float value) {
            addCriterion("bonus_bole <=", value, "bonusBole");
            return (Criteria) this;
        }

        public Criteria andBonusBoleIn(List<Float> values) {
            addCriterion("bonus_bole in", values, "bonusBole");
            return (Criteria) this;
        }

        public Criteria andBonusBoleNotIn(List<Float> values) {
            addCriterion("bonus_bole not in", values, "bonusBole");
            return (Criteria) this;
        }

        public Criteria andBonusBoleBetween(Float value1, Float value2) {
            addCriterion("bonus_bole between", value1, value2, "bonusBole");
            return (Criteria) this;
        }

        public Criteria andBonusBoleNotBetween(Float value1, Float value2) {
            addCriterion("bonus_bole not between", value1, value2, "bonusBole");
            return (Criteria) this;
        }

        public Criteria andBonusRepurchaseIsNull() {
            addCriterion("bonus_repurchase is null");
            return (Criteria) this;
        }

        public Criteria andBonusRepurchaseIsNotNull() {
            addCriterion("bonus_repurchase is not null");
            return (Criteria) this;
        }

        public Criteria andBonusRepurchaseEqualTo(Float value) {
            addCriterion("bonus_repurchase =", value, "bonusRepurchase");
            return (Criteria) this;
        }

        public Criteria andBonusRepurchaseNotEqualTo(Float value) {
            addCriterion("bonus_repurchase <>", value, "bonusRepurchase");
            return (Criteria) this;
        }

        public Criteria andBonusRepurchaseGreaterThan(Float value) {
            addCriterion("bonus_repurchase >", value, "bonusRepurchase");
            return (Criteria) this;
        }

        public Criteria andBonusRepurchaseGreaterThanOrEqualTo(Float value) {
            addCriterion("bonus_repurchase >=", value, "bonusRepurchase");
            return (Criteria) this;
        }

        public Criteria andBonusRepurchaseLessThan(Float value) {
            addCriterion("bonus_repurchase <", value, "bonusRepurchase");
            return (Criteria) this;
        }

        public Criteria andBonusRepurchaseLessThanOrEqualTo(Float value) {
            addCriterion("bonus_repurchase <=", value, "bonusRepurchase");
            return (Criteria) this;
        }

        public Criteria andBonusRepurchaseIn(List<Float> values) {
            addCriterion("bonus_repurchase in", values, "bonusRepurchase");
            return (Criteria) this;
        }

        public Criteria andBonusRepurchaseNotIn(List<Float> values) {
            addCriterion("bonus_repurchase not in", values, "bonusRepurchase");
            return (Criteria) this;
        }

        public Criteria andBonusRepurchaseBetween(Float value1, Float value2) {
            addCriterion("bonus_repurchase between", value1, value2, "bonusRepurchase");
            return (Criteria) this;
        }

        public Criteria andBonusRepurchaseNotBetween(Float value1, Float value2) {
            addCriterion("bonus_repurchase not between", value1, value2, "bonusRepurchase");
            return (Criteria) this;
        }

        public Criteria andBonusRankIsNull() {
            addCriterion("bonus_rank is null");
            return (Criteria) this;
        }

        public Criteria andBonusRankIsNotNull() {
            addCriterion("bonus_rank is not null");
            return (Criteria) this;
        }

        public Criteria andBonusRankEqualTo(Float value) {
            addCriterion("bonus_rank =", value, "bonusRank");
            return (Criteria) this;
        }

        public Criteria andBonusRankNotEqualTo(Float value) {
            addCriterion("bonus_rank <>", value, "bonusRank");
            return (Criteria) this;
        }

        public Criteria andBonusRankGreaterThan(Float value) {
            addCriterion("bonus_rank >", value, "bonusRank");
            return (Criteria) this;
        }

        public Criteria andBonusRankGreaterThanOrEqualTo(Float value) {
            addCriterion("bonus_rank >=", value, "bonusRank");
            return (Criteria) this;
        }

        public Criteria andBonusRankLessThan(Float value) {
            addCriterion("bonus_rank <", value, "bonusRank");
            return (Criteria) this;
        }

        public Criteria andBonusRankLessThanOrEqualTo(Float value) {
            addCriterion("bonus_rank <=", value, "bonusRank");
            return (Criteria) this;
        }

        public Criteria andBonusRankIn(List<Float> values) {
            addCriterion("bonus_rank in", values, "bonusRank");
            return (Criteria) this;
        }

        public Criteria andBonusRankNotIn(List<Float> values) {
            addCriterion("bonus_rank not in", values, "bonusRank");
            return (Criteria) this;
        }

        public Criteria andBonusRankBetween(Float value1, Float value2) {
            addCriterion("bonus_rank between", value1, value2, "bonusRank");
            return (Criteria) this;
        }

        public Criteria andBonusRankNotBetween(Float value1, Float value2) {
            addCriterion("bonus_rank not between", value1, value2, "bonusRank");
            return (Criteria) this;
        }

        public Criteria andOperateTimeIsNull() {
            addCriterion("operate_time is null");
            return (Criteria) this;
        }

        public Criteria andOperateTimeIsNotNull() {
            addCriterion("operate_time is not null");
            return (Criteria) this;
        }

        public Criteria andOperateTimeEqualTo(Date value) {
            addCriterion("operate_time =", value, "operateTime");
            return (Criteria) this;
        }

        public Criteria andOperateTimeNotEqualTo(Date value) {
            addCriterion("operate_time <>", value, "operateTime");
            return (Criteria) this;
        }

        public Criteria andOperateTimeGreaterThan(Date value) {
            addCriterion("operate_time >", value, "operateTime");
            return (Criteria) this;
        }

        public Criteria andOperateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("operate_time >=", value, "operateTime");
            return (Criteria) this;
        }

        public Criteria andOperateTimeLessThan(Date value) {
            addCriterion("operate_time <", value, "operateTime");
            return (Criteria) this;
        }

        public Criteria andOperateTimeLessThanOrEqualTo(Date value) {
            addCriterion("operate_time <=", value, "operateTime");
            return (Criteria) this;
        }

        public Criteria andOperateTimeIn(List<Date> values) {
            addCriterion("operate_time in", values, "operateTime");
            return (Criteria) this;
        }

        public Criteria andOperateTimeNotIn(List<Date> values) {
            addCriterion("operate_time not in", values, "operateTime");
            return (Criteria) this;
        }

        public Criteria andOperateTimeBetween(Date value1, Date value2) {
            addCriterion("operate_time between", value1, value2, "operateTime");
            return (Criteria) this;
        }

        public Criteria andOperateTimeNotBetween(Date value1, Date value2) {
            addCriterion("operate_time not between", value1, value2, "operateTime");
            return (Criteria) this;
        }

        public Criteria andBalanceTypeIsNull() {
            addCriterion("balance_type is null");
            return (Criteria) this;
        }

        public Criteria andBalanceTypeIsNotNull() {
            addCriterion("balance_type is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceTypeEqualTo(Integer value) {
            addCriterion("balance_type =", value, "balanceType");
            return (Criteria) this;
        }

        public Criteria andBalanceTypeNotEqualTo(Integer value) {
            addCriterion("balance_type <>", value, "balanceType");
            return (Criteria) this;
        }

        public Criteria andBalanceTypeGreaterThan(Integer value) {
            addCriterion("balance_type >", value, "balanceType");
            return (Criteria) this;
        }

        public Criteria andBalanceTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("balance_type >=", value, "balanceType");
            return (Criteria) this;
        }

        public Criteria andBalanceTypeLessThan(Integer value) {
            addCriterion("balance_type <", value, "balanceType");
            return (Criteria) this;
        }

        public Criteria andBalanceTypeLessThanOrEqualTo(Integer value) {
            addCriterion("balance_type <=", value, "balanceType");
            return (Criteria) this;
        }

        public Criteria andBalanceTypeIn(List<Integer> values) {
            addCriterion("balance_type in", values, "balanceType");
            return (Criteria) this;
        }

        public Criteria andBalanceTypeNotIn(List<Integer> values) {
            addCriterion("balance_type not in", values, "balanceType");
            return (Criteria) this;
        }

        public Criteria andBalanceTypeBetween(Integer value1, Integer value2) {
            addCriterion("balance_type between", value1, value2, "balanceType");
            return (Criteria) this;
        }

        public Criteria andBalanceTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("balance_type not between", value1, value2, "balanceType");
            return (Criteria) this;
        }

        public Criteria andBonnusAdminCostIsNull() {
            addCriterion("bonnus_admin_cost is null");
            return (Criteria) this;
        }

        public Criteria andBonnusAdminCostIsNotNull() {
            addCriterion("bonnus_admin_cost is not null");
            return (Criteria) this;
        }

        public Criteria andBonnusAdminCostEqualTo(Float value) {
            addCriterion("bonnus_admin_cost =", value, "bonnusAdminCost");
            return (Criteria) this;
        }

        public Criteria andBonnusAdminCostNotEqualTo(Float value) {
            addCriterion("bonnus_admin_cost <>", value, "bonnusAdminCost");
            return (Criteria) this;
        }

        public Criteria andBonnusAdminCostGreaterThan(Float value) {
            addCriterion("bonnus_admin_cost >", value, "bonnusAdminCost");
            return (Criteria) this;
        }

        public Criteria andBonnusAdminCostGreaterThanOrEqualTo(Float value) {
            addCriterion("bonnus_admin_cost >=", value, "bonnusAdminCost");
            return (Criteria) this;
        }

        public Criteria andBonnusAdminCostLessThan(Float value) {
            addCriterion("bonnus_admin_cost <", value, "bonnusAdminCost");
            return (Criteria) this;
        }

        public Criteria andBonnusAdminCostLessThanOrEqualTo(Float value) {
            addCriterion("bonnus_admin_cost <=", value, "bonnusAdminCost");
            return (Criteria) this;
        }

        public Criteria andBonnusAdminCostIn(List<Float> values) {
            addCriterion("bonnus_admin_cost in", values, "bonnusAdminCost");
            return (Criteria) this;
        }

        public Criteria andBonnusAdminCostNotIn(List<Float> values) {
            addCriterion("bonnus_admin_cost not in", values, "bonnusAdminCost");
            return (Criteria) this;
        }

        public Criteria andBonnusAdminCostBetween(Float value1, Float value2) {
            addCriterion("bonnus_admin_cost between", value1, value2, "bonnusAdminCost");
            return (Criteria) this;
        }

        public Criteria andBonnusAdminCostNotBetween(Float value1, Float value2) {
            addCriterion("bonnus_admin_cost not between", value1, value2, "bonnusAdminCost");
            return (Criteria) this;
        }

        public Criteria andBonusCarIsNull() {
            addCriterion("bonus_car is null");
            return (Criteria) this;
        }

        public Criteria andBonusCarIsNotNull() {
            addCriterion("bonus_car is not null");
            return (Criteria) this;
        }

        public Criteria andBonusCarEqualTo(Float value) {
            addCriterion("bonus_car =", value, "bonusCar");
            return (Criteria) this;
        }

        public Criteria andBonusCarNotEqualTo(Float value) {
            addCriterion("bonus_car <>", value, "bonusCar");
            return (Criteria) this;
        }

        public Criteria andBonusCarGreaterThan(Float value) {
            addCriterion("bonus_car >", value, "bonusCar");
            return (Criteria) this;
        }

        public Criteria andBonusCarGreaterThanOrEqualTo(Float value) {
            addCriterion("bonus_car >=", value, "bonusCar");
            return (Criteria) this;
        }

        public Criteria andBonusCarLessThan(Float value) {
            addCriterion("bonus_car <", value, "bonusCar");
            return (Criteria) this;
        }

        public Criteria andBonusCarLessThanOrEqualTo(Float value) {
            addCriterion("bonus_car <=", value, "bonusCar");
            return (Criteria) this;
        }

        public Criteria andBonusCarIn(List<Float> values) {
            addCriterion("bonus_car in", values, "bonusCar");
            return (Criteria) this;
        }

        public Criteria andBonusCarNotIn(List<Float> values) {
            addCriterion("bonus_car not in", values, "bonusCar");
            return (Criteria) this;
        }

        public Criteria andBonusCarBetween(Float value1, Float value2) {
            addCriterion("bonus_car between", value1, value2, "bonusCar");
            return (Criteria) this;
        }

        public Criteria andBonusCarNotBetween(Float value1, Float value2) {
            addCriterion("bonus_car not between", value1, value2, "bonusCar");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table table_balance_detail
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
     * This class corresponds to the database table table_balance_detail
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