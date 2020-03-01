package com.spring.free.domain;

import com.spring.free.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Table;

@Getter
@Setter
@ToString
public class Market  {
    /**
     *交易对
     */
    private String marketName;
    /**
     *成交量
     */
    private String  amount;
    /**
     *价格
     */
    private String  price;
    /**
     *人民币价格
     */
    private String  cnyPrice;
    /**
     *涨跌幅
     */
    private String  chg;
}
