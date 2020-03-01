package com.spring.fee.model;

import java.util.List;

public class TWheatMemberTree extends TWheatMember {

    Long maxFreeing;

    List<TWheatMemberTree> childList;

    public Long getMaxFreeing() {
        return maxFreeing;
    }

    public void setMaxFreeing(Long maxFreeing) {
        this.maxFreeing = maxFreeing;
    }

    public List<TWheatMemberTree> getChildList() {
        return childList;
    }

    public void setChildList(List<TWheatMemberTree> childList) {
        this.childList = childList;
    }
}