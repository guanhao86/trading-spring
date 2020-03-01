package com.spring.fee.service;

import com.github.pagehelper.PageInfo;
import com.spring.fee.model.TWheatFinancial;
import com.spring.fee.model.TWheatFinancialOrder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 会员表服务
 */
public interface ITWheatFinancialOrderBusiSV {

    TWheatFinancialOrder insert(TWheatFinancialOrder tWheatFinancialOrder);

    TWheatFinancialOrder update(TWheatFinancialOrder tWheatFinancialOrder);

    TWheatFinancialOrder delete(TWheatFinancialOrder tWheatFinancialOrder);

    TWheatFinancialOrder select(TWheatFinancialOrder tWheatFinancialOrder);

    List<TWheatFinancialOrder> queryList(TWheatFinancialOrder tWheatFinancialOrder, String[] orderBy);

    PageInfo<TWheatFinancialOrder> pageList(TWheatFinancialOrder entity, Integer page, Integer pageSize);
}
