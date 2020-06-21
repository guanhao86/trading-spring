package com.spring.fee.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class TableBonusDetailDZ implements Serializable {

    private Date recodeTime;

    private BigDecimal bonus1;

    private BigDecimal bonus2;

    private BigDecimal bonus3;

    private BigDecimal bonus4;

    private BigDecimal bonus5;

    private BigDecimal bonus6;

    private BigDecimal bonus7;

    //福利积分
    private BigDecimal bonus10;

    private TableMember tableMember;
}