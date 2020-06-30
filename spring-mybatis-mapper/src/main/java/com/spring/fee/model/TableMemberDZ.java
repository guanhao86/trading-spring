package com.spring.fee.model;

import java.util.List;

public class TableMemberDZ extends TableMember {

    boolean hasChild;

    List<TableMemberDZ> childList;

    public boolean isHasChild() {
        return hasChild;
    }

    public void setHasChild(boolean hasChild) {
        this.hasChild = hasChild;
    }

    public List<TableMemberDZ> getChildList() {
        return childList;
    }

    public void setChildList(List<TableMemberDZ> childList) {
        this.childList = childList;
    }
}