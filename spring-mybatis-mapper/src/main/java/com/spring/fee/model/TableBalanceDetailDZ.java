package com.spring.fee.model;

import java.util.Date;

public class TableBalanceDetailDZ extends TableBalanceDetail {

    Date lastTimeStart;

    Date lastTimeEnd;

    public Date getLastTimeStart() {
        return lastTimeStart;
    }

    public void setLastTimeStart(Date lastTimeStart) {
        this.lastTimeStart = lastTimeStart;
    }

    public Date getLastTimeEnd() {
        return lastTimeEnd;
    }

    public void setLastTimeEnd(Date lastTimeEnd) {
        this.lastTimeEnd = lastTimeEnd;
    }
}