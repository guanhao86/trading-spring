package com.spring.fee.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.fee.dao.mapper.TableBalanceDetailMapper;
import com.spring.fee.dao.mapper.TableBalanceDetailMapperDZ;
import com.spring.fee.model.TableBalanceDetail;
import com.spring.fee.model.TableBalanceDetailDZ;
import com.spring.fee.model.TableBalanceDetailExample;
import com.spring.fee.service.ITableBalanceDetailBusiSV;
import com.spring.free.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 结算管理服务
 */
@Slf4j
@Service
@Transactional
public class TableBalanceDetailBusiSVImpl implements ITableBalanceDetailBusiSV {

    @Autowired
    TableBalanceDetailMapper iTableBalanceDetailMapper;

    @Autowired
    TableBalanceDetailMapperDZ iTableBalanceDetailMapperDZ;

    /**
     * 创建结算记录
     * @param bo
     * @return
     */
    @Override
    public TableBalanceDetail insert(TableBalanceDetail bo) {
        log.info("创建结算管理参数bo：{}", JSON.toJSON(bo));
        Date sysdate = DateUtils.getSysDate();
        bo.setLastTime(sysdate);
        iTableBalanceDetailMapper.insert(bo);
        return bo;
    }

    @Override
    public TableBalanceDetail update(TableBalanceDetail bo) {
        if (this.iTableBalanceDetailMapper.updateByPrimaryKeySelective(bo) == 1) {
            return bo;
        }
        return null;
    }

    @Override
    public TableBalanceDetail delete(TableBalanceDetail bo) {
        if (this.iTableBalanceDetailMapper.deleteByPrimaryKey(bo.getId()) == 1) {
            return bo;
        }
        return null;
    }

    @Override
    public TableBalanceDetail select(TableBalanceDetail bo) {
        return this.iTableBalanceDetailMapper.selectByPrimaryKey(bo.getId());
    }

    /**
     * 统计奖金发放总和
     *
     * @param memberId
     * @param start
     * @param end
     * @return
     */
    @Override
    public List<TableBalanceDetail> selectByGroup(String memberId, Date start, Date end) {
        return iTableBalanceDetailMapperDZ.selectByGroup(memberId, start, end);
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
    public PageInfo<TableBalanceDetail> queryListPage(TableBalanceDetailDZ bo, Integer pageNum, Integer pageSize, Map<String ,Object> map) {
        log.info("获取结算管理参数bo：{}", JSON.toJSON(bo));
        log.info("获取结算管理参数pageNum：{}", pageNum);
        log.info("获取结算管理参数pageSize：{}", pageSize);
        log.info("获取结算管理参数map：{}", JSON.toJSON(map));
        
        TableBalanceDetailExample example = new TableBalanceDetailExample();
        TableBalanceDetailExample.Criteria criteria = example.createCriteria();

        if (bo.getLastTimeStart() != null) {
            criteria.andLastTimeGreaterThanOrEqualTo(bo.getLastTimeStart());
        }

        if (bo.getLastTimeEnd() != null) {
            criteria.andLastTimeLessThanOrEqualTo(bo.getLastTimeEnd());
        }

        if (null != bo.getId()) {
            criteria.andIdEqualTo(bo.getId());
        }
        if (StringUtils.isNotEmpty(bo.getAdminId())) {
            criteria.andAdminIdEqualTo(bo.getAdminId());
        }
        if (null != bo.getLastTime()) {
            criteria.andLastTimeEqualTo(bo.getLastTime());
        }
        if (null != bo.getBonusReference()) {
            criteria.andBonusReferenceEqualTo(bo.getBonusReference());
        }
        if (null != bo.getBonusGroup()) {
            criteria.andBonusGroupEqualTo(bo.getBonusGroup());
        }
        if (null != bo.getBonusBole()) {
            criteria.andBonusBoleEqualTo(bo.getBonusBole());
        }
        if (null != bo.getBonusRepurchase()) {
            criteria.andBonusRepurchaseEqualTo(bo.getBonusRepurchase());
        }
        if (null != bo.getBonusRank()) {
            criteria.andBonusRankEqualTo(bo.getBonusRank());
        }
        if (null != bo.getOperateTime()) {
            criteria.andOperateTimeEqualTo(bo.getOperateTime());
        }
        if (null != bo.getBalanceType()) {
            criteria.andBalanceTypeEqualTo(bo.getBalanceType());
        }

        if (null != bo.getCloseFlag()) {
            criteria.andCloseFlagEqualTo(bo.getCloseFlag());
        }

        if (null != map) {
            if (null != map.get("balanceTypeNotIn")) {
                criteria.andBalanceTypeNotIn((List)map.get("balanceTypeNotIn"));
            }
        }

        example.setOrderByClause("operate_time desc");

        PageInfo<TableBalanceDetail> pageInfo = PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(() -> this.iTableBalanceDetailMapper.selectByExample(example));
        log.info("获取结算管理结果：{}", JSON.toJSON(pageInfo));
        return pageInfo;
    }
}
