package com.spring.fee.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.fee.dao.mapper.MemberAccountDetailMapper;
import com.spring.fee.model.MemberAccountDetail;
import com.spring.fee.model.MemberAccountDetailExample;
import com.spring.fee.service.IMemberAccountDetailBusiSV;
import com.spring.free.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * 账户资金情况变更表服务
 */
@Slf4j
@Service
@Transactional
public class MemberAccountDetailBusiSVImpl implements IMemberAccountDetailBusiSV {

    @Autowired
    MemberAccountDetailMapper iMemberAccountDetailMapper;

    /**
     * 创建记录
     * @param bo
     * @return
     */
    @Override
    public MemberAccountDetail insert(MemberAccountDetail bo) {
        log.info("创建账户资金情况变更表参数bo：{}", JSON.toJSON(bo));
        Date sysdate = DateUtils.getSysDate();
        bo.setModifyTime(sysdate);
        iMemberAccountDetailMapper.insert(bo);
        return bo;
    }

    @Override
    public MemberAccountDetail update(MemberAccountDetail bo) {
        Date sysdate = DateUtils.getSysDate();
        bo.setModifyTime(sysdate);
        if (this.iMemberAccountDetailMapper.updateByPrimaryKeySelective(bo) == 1) {
            return bo;
        }
        return null;
    }

    @Override
    public MemberAccountDetail delete(MemberAccountDetail bo) {
        if (this.iMemberAccountDetailMapper.deleteByPrimaryKey(bo.getId()) == 1) {
            return bo;
        }
        return null;
    }

    @Override
    public MemberAccountDetail select(MemberAccountDetail bo) {
        return this.iMemberAccountDetailMapper.selectByPrimaryKey(bo.getId());
    }

    /**
     * 数据列表分页
     * @param bo
     * @param pageNum
     * @param pageSize
     * @param map
     * @return
     */
    @Override
    public PageInfo<MemberAccountDetail> queryListPage(MemberAccountDetail bo, Integer pageNum, Integer pageSize, Map<String ,Object> map) {
        log.info("获取账户资金情况变更表参数bo：{}", JSON.toJSON(bo));
        log.info("获取账户资金情况变更表参数pageNum：{}", pageNum);
        log.info("获取账户资金情况变更表参数pageSize：{}", pageSize);
        log.info("获取账户资金情况变更表参数map：{}", JSON.toJSON(map));
        
        MemberAccountDetailExample example = new MemberAccountDetailExample();
        MemberAccountDetailExample.Criteria criteria = example.createCriteria();

        if (null != bo.getId()) {
            criteria.andIdEqualTo(bo.getId());
        }
        if (StringUtils.isNotEmpty(bo.getMemberId())) {
            criteria.andMemberIdEqualTo(bo.getMemberId());
        }
        if (null != bo.getAccountType()) {
            criteria.andAccountTypeEqualTo(bo.getAccountType());
        }
        if (null != bo.getBeforeValue()) {
            criteria.andBeforeValueEqualTo(bo.getBeforeValue());
        }
        if (null != bo.getAfterValue()) {
            criteria.andAfterValueEqualTo(bo.getAfterValue());
        }
        if (null != bo.getModifyTime()) {
            criteria.andModifyTimeEqualTo(bo.getModifyTime());
        }
        if (StringUtils.isNotEmpty(bo.getRemark())) {
            criteria.andRemarkEqualTo(bo.getRemark());
        }

        PageInfo<MemberAccountDetail> pageInfo = PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(() -> this.iMemberAccountDetailMapper.selectByExample(example));
        log.info("获取账户资金情况变更表结果：{}", JSON.toJSON(pageInfo));
        return pageInfo;
    }
}
