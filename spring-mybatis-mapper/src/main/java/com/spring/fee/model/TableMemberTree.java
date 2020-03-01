package com.spring.fee.model;

import java.util.List;

public class TableMemberTree extends TableMember {

    List<TableMemberTree> childList;

    public List<TableMemberTree> getChildList() {
        return childList;
    }

    public void setChildList(List<TableMemberTree> childList) {
        this.childList = childList;
    }
}