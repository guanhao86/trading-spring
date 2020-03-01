package com.spring.free.vo;

public class PlanReqVO extends PageVO {

    String memberId;

    String status; //1:执行中，2:完结

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
