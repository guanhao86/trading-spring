package com.spring.free.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BonusDetailPerVO {

    //日新增业绩
    BigDecimal yesterdayAddOrderPrice;
    //总奖金
    BigDecimal allAddBonus;
    //今日奖金
    BigDecimal yesterdayAddBonus;
    //总业绩
    BigDecimal allOrderPrice;
    //总波比
    String allPer;
    //今日拨比
    String yesterdayPer;
    //现金总额
    BigDecimal accountMoney;

    private BigDecimal accountPointAvailable;

    private BigDecimal accountPointFreeze;

    private BigDecimal accountDjPoint;

    private BigDecimal accountJsyPoint;

    private BigDecimal score;

    private BigDecimal accountCarPoint;

    private BigDecimal leftAmount;

    private BigDecimal rightAmount;

    private BigDecimal leftNew;

    private BigDecimal rightNew;

}
