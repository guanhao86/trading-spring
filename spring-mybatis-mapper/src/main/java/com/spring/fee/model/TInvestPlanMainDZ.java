package com.spring.fee.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TInvestPlanMainDZ extends TInvestPlanMain implements Serializable {

    String notEqualSettleYYYYMMDD;

    String sort;

    Date runTime;

    String canAppend; //1允许复投

    long bonusTmpId;

    String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getBonusTmpId() {
        return bonusTmpId;
    }

    public void setBonusTmpId(long bonusTmpId) {
        this.bonusTmpId = bonusTmpId;
    }

    public String getCanAppend() {
        return canAppend;
    }

    public void setCanAppend(String canAppend) {
        this.canAppend = canAppend;
    }

    List<TInvestPlanDetail> tInvestPlanDetailList;

    public List<TInvestPlanDetail> gettInvestPlanDetailList() {
        return tInvestPlanDetailList;
    }

    public void settInvestPlanDetailList(List<TInvestPlanDetail> tInvestPlanDetailList) {
        this.tInvestPlanDetailList = tInvestPlanDetailList;
    }

    public Date getRunTime() {
        return runTime;
    }

    public void setRunTime(Date runTime) {
        this.runTime = runTime;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getNotEqualSettleYYYYMMDD() {
        return notEqualSettleYYYYMMDD;
    }

    public void setNotEqualSettleYYYYMMDD(String notEqualSettleYYYYMMDD) {
        this.notEqualSettleYYYYMMDD = notEqualSettleYYYYMMDD;
    }
}