package com.spring.fee.service;

import com.github.pagehelper.PageInfo;
import com.spring.fee.model.TWheatAccountDetail;
import com.spring.fee.model.TWheatAccountDetailDZ;

import java.util.List;
import java.util.Map;

/**
 * 会员账户明细表
 */
public interface ITWheatAccountDetailBusiSV {

    TWheatAccountDetail insert(TWheatAccountDetail tWheatAccountDetail);

    TWheatAccountDetail update(TWheatAccountDetail tWheatAccountDetail);

    TWheatAccountDetail delete(TWheatAccountDetail tWheatAccountDetail);

    TWheatAccountDetail select(TWheatAccountDetail tWheatAccountDetail);

    List<TWheatAccountDetail> queryList(TWheatAccountDetail tWheatAccountDetail);

    PageInfo<TWheatAccountDetail> queryPage(TWheatAccountDetailDZ tWheatAccountDetail, Integer pageNum, Integer pageSize, Map<String ,Object> map);
}
