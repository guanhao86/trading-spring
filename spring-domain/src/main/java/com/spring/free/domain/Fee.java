package com.spring.free.domain;

import com.spring.free.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Table;

@Getter
@Setter
@ToString
@Table(name = "t_wheat_fee")
public class Fee extends BaseEntity<Fee> {

    /**
     *手续费类型 1虚拟币充值 2提现手续费 3法币充值手 4提现续费
     */
    private Integer type;
    /**
     *手续费
     */
    private Double fee;

    /**
     *预留字段
     */
    private String expand1;
    private String expand2;
    private String expand3;
    private String expand4;
    private String expand5;
}
