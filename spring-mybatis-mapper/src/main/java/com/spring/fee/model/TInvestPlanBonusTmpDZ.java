package com.spring.fee.model;

public class TInvestPlanBonusTmpDZ extends TInvestPlanBonusTmp{

    Double rate;

    Integer maxMemberLevel; //最大会员级别

    Long preAmount; //上级奖金金额（用于计算平级顶死计算）

    public Long getPreAmount() {
        return preAmount;
    }

    public void setPreAmount(Long preAmount) {
        this.preAmount = preAmount;
    }

    public Integer getMaxMemberLevel() {
        return maxMemberLevel;
    }

    public void setMaxMemberLevel(Integer maxMemberLevel) {
        this.maxMemberLevel = maxMemberLevel;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}