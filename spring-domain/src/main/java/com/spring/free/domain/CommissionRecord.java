package com.spring.free.domain;

import com.spring.free.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Table;

@Getter
@Setter
@ToString
@Table(name = "t_wheat_commission_record")
public class CommissionRecord extends BaseEntity<CommissionRecord> {
    /**
     *会员id
     */
    private Integer memId;

    /**
     *会员编号
     */
    private String mmeberId;

    /**
     *委托金额
     */
    private Integer amount;

    /**
     *QC币
     */
    private Integer qcCoin;

    /**
     *手续费
     */
    private Integer fee;

    /**
     *类型 1充值 2提现
     */
    private Integer type;

    /**
     *充值凭证截图
     */
    private String image;

    /**
     *审核状态 0待审核 1审核通过 2审核拒绝
     */
    private Integer state;

    /**
     *预留字段
     */
    private String expand1;
    private String expand2;
    private String expand3;
    private String expand4;
    private String expand5;

    int pageSize;

    int pageNum;
}
