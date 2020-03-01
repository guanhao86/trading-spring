package com.spring.fee.model;

import java.util.Date;
import java.util.List;

public class TWheatMemberDZ extends TWheatMember {

    private Date createTimeStart;

    private Date createTimeEnd;

    private Long maxFreeing;

    private List<String> memberList;

    private String levelDesc;

    public String getLevelDesc() {
        return levelDesc;
    }

    public void setLevelDesc(String levelDesc) {
        this.levelDesc = levelDesc;
    }

    public Long getMaxFreeing() {
        return maxFreeing;
    }

    public void setMaxFreeing(Long maxFreeing) {
        this.maxFreeing = maxFreeing;
    }

    public List<String> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<String> memberList) {
        this.memberList = memberList;
    }

    public Date getCreateTimeStart() {
        return createTimeStart;
    }

    public void setCreateTimeStart(Date createTimeStart) {
        this.createTimeStart = createTimeStart;
    }

    public Date getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(Date createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }
}