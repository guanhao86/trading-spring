package com.spring.fee.model;

public class TWheatMemberGroupDZ extends TWheatMember {

    /**
     * 会员数量
     */
    int count;

    /**
     * 直推人数
     */
    int fistChildCount;

    public int getFistChildCount() {
        return fistChildCount;
    }

    public void setFistChildCount(int fistChildCount) {
        this.fistChildCount = fistChildCount;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}