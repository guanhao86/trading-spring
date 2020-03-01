package com.spring.fee.model;

import java.io.Serializable;
import java.util.Date;

public class TWheatAccountDetailDZ extends TWheatAccountDetail {

    Date createTimeStart;

    Date createTimeEnd;

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