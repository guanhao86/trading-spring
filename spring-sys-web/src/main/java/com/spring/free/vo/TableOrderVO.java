package com.spring.free.vo;

import com.spring.fee.model.TableGoods;
import com.spring.fee.model.TableOrder;
import lombok.Data;

@Data
public class TableOrderVO extends TableOrder {
    TableGoods tableGoods;
}

