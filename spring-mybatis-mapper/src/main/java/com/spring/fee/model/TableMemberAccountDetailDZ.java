package com.spring.fee.model;

import java.util.ArrayList;
import java.util.List;

public class TableMemberAccountDetailDZ extends TableMemberAccountDetail {

    public TableMemberAccountDetailDZ(){
        accountTypeNotInList.add(6);
    }

    private List<Integer> accountTypeNotInList = new ArrayList<>();

    public List<Integer> getAccountTypeNotInList() {
        return accountTypeNotInList;
    }

    public void setAccountTypeNotInList(List<Integer> accountTypeNotInList) {
        this.accountTypeNotInList = accountTypeNotInList;
    }
}