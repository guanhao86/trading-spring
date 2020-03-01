package com.spring.fee.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.spring.fee.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 会员表服务
 */
public interface ITWheatFinancialBusiSV {

    TWheatFinancial insert(TWheatFinancial tWheatFinancial);

    TWheatFinancial  insert(TWheatFinancial tWheatFinancial,MultipartFile postPic,Map map);

    TWheatFinancial update(TWheatFinancial tWheatFinancial);

    TWheatFinancial update(TWheatFinancial tWheatFinancial, MultipartFile postPic, Map map);

    TWheatFinancial delete(TWheatFinancial tWheatFinancial);

    TWheatFinancial select(TWheatFinancial tWheatFinancial);

    List<TWheatFinancial> queryList(TWheatFinancial tWheatFinancial, String[] orderBy);

    PageInfo<TWheatFinancial> pageList(TWheatFinancial entity, Integer page, Integer pageSize);

    JSONObject createOrder (String memberId, Long id,  Integer num);
    JSONObject financialMoney (String memberId);
    PageInfo<TWheatFinancialOrder> orderPageList(String memberId ,Integer page, Integer pageSize);
    List<TWheatFinancialStream> streamList(String memberId);
    void dealTWheatFinancial (int day);
}
