package com.spring.fee.model;

import java.util.Date;

public class TableOrderDZ extends TableOrder {
    Date start;

    Date end;

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}