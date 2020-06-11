package com.spring.free.vo;

import lombok.Data;

@Data
public class GoodsVO extends PageVO {

    //商品类型
    private String goodsClass;

    private String sort;

}
