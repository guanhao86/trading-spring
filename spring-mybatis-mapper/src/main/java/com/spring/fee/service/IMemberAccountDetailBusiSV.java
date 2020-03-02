package com.spring.fee.service;

import com.github.pagehelper.PageInfo;
import com.spring.fee.model.MemberAccountDetail;

import java.util.Map;

/**
 * 账户资金情况变更表服务
 */
public interface IMemberAccountDetailBusiSV {

    MemberAccountDetail insert(MemberAccountDetail bo);

    MemberAccountDetail update(MemberAccountDetail bo);

    MemberAccountDetail delete(MemberAccountDetail bo);

    MemberAccountDetail select(MemberAccountDetail bo);

    PageInfo<MemberAccountDetail> queryListPage(MemberAccountDetail bo, Integer pageNum, Integer pageSize, Map<String, Object> map);
}
