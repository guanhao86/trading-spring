package com.spring.free.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 结算记录
 */
@Data
public class SettleRecordReqVO implements Serializable {



    String start;
    String end;

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
