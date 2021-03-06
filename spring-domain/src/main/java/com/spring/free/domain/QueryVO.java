package com.spring.free.domain;

import com.spring.free.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class QueryVO extends BaseEntity<QueryVO> {

    private String queryMemberId;

    private String memberId;

    private String referenceId;

    private String arrangeId;

    private String reallyName;

    private String phone;

    private String password;

    private String oldPassword;

    private Integer autFlag;


    //公告
    private String title;

    //商品名
    private String goodsName;

    //充值钱数（单位元）
    private String amount;

    //级别
    private String level;
    //头衔
    private String rank;
    private String remark;

    private String start;
    private String end;

    private Integer state;

    private Integer goodsClass;

    private String orderId;

    private Integer bonusId;

    //账户类型
    private Integer accountType;

    /**
     * 结算类型
     * 1：日结  2：奖金发放  3：月结
     */
    private String settleType;

    private String auditState;

    private String respState;
}
