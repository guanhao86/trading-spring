package com.spring.fee.service;

import com.github.pagehelper.PageInfo;
import com.spring.fee.model.TWheatFinancialStream;

import java.util.List;

/**
 * 会员表服务
 */
public interface ITWheatFinancialStreamBusiSV {

    TWheatFinancialStream insert(TWheatFinancialStream tWheatFinancialStream);

    TWheatFinancialStream update(TWheatFinancialStream tWheatFinancialStream);

    TWheatFinancialStream delete(TWheatFinancialStream tWheatFinancialStream);

    TWheatFinancialStream select(TWheatFinancialStream tWheatFinancialStream);

    List<TWheatFinancialStream> queryList(TWheatFinancialStream tWheatFinancialStream, String[] orderBy);

    PageInfo<TWheatFinancialStream> pageList(TWheatFinancialStream entity, Integer page, Integer pageSize);
}
