package com.spring.fee.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TInvestPlanBonusDZ extends TInvestPlanBonus implements Serializable {

    private List<String> memberIdList;

    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<String> getMemberIdList() {
        return memberIdList;
    }

    public void setMemberIdList(List<String> memberIdList) {
        this.memberIdList = memberIdList;
    }
}