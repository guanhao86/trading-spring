package com.spring.fee.model;

public class TInvestPlanMainLogDZ extends TInvestPlanMainLog {
    private String memberId;
    private String planName;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }
}