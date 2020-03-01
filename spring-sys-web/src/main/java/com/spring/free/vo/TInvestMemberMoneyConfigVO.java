package com.spring.free.vo;

import com.spring.fee.model.TInvestMemberMoneyConfig;

public class TInvestMemberMoneyConfigVO extends TInvestMemberMoneyConfig {

    private String planName;

    private String memberLevelDesc;

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getMemberLevelDesc() {
        return memberLevelDesc;
    }

    public void setMemberLevelDesc(String memberLevelDesc) {
        this.memberLevelDesc = memberLevelDesc;
    }
}
