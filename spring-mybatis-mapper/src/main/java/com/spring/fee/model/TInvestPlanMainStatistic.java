package com.spring.fee.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 粮仓计划统计
 */
public class TInvestPlanMainStatistic implements Serializable {

    /**
     * 执行中计划数
     */
    long runningCount;
    /**
     * 正在投入计划数
     */
    long buyCount;
    /**
     * 所有计划数
     */
    long allCount;

    /**
     * 复投总数
     */
    int appendCount;

    String memberId;

    String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    private List<String> memberList;

    public List<String> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<String> memberList) {
        this.memberList = memberList;
    }

    public int getAppendCount() {
        return appendCount;
    }

    public void setAppendCount(int appendCount) {
        this.appendCount = appendCount;
    }

    public long getRunningCount() {
        return runningCount;
    }

    public void setRunningCount(long runningCount) {
        this.runningCount = runningCount;
    }

    public long getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(long buyCount) {
        this.buyCount = buyCount;
    }

    public long getAllCount() {
        return allCount;
    }

    public void setAllCount(long allCount) {
        this.allCount = allCount;
    }
}