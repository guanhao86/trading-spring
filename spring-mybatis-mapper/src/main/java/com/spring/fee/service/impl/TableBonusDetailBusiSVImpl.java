package com.spring.fee.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.fee.dao.mapper.TableBonusDetailMapper;
import com.spring.fee.dao.mapper.TableBonusDetailMapperDZ;
import com.spring.fee.model.TableBonusDetail;
import com.spring.fee.model.TableBonusDetailExample;
import com.spring.fee.service.ITableBonusDetailBusiSV;
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
 * 奖金流水管理表服务
 */
@Slf4j
@Service
@Transactional
public class TableBonusDetailBusiSVImpl implements ITableBonusDetailBusiSV {

    @Autowired
    TableBonusDetailMapper iTableBonusDetailMapper;

    @Autowired
    TableBonusDetailMapperDZ iTableBonusDetailMapperDZ;

    /**
     * 创建结算记录
     * @param bo
     * @return
     */
    @Override
    public TableBonusDetail insert(TableBonusDetail bo) {
        log.info("创建奖金流水管理表参数bo：{}", JSON.toJSON(bo));
        Date sysdate = DateUtils.getSysDate();
        bo.setRecodeTime(sysdate);
        iTableBonusDetailMapper.insert(bo);
        return bo;
    }

    @Override
    public TableBonusDetail update(TableBonusDetail bo) {
        if (this.iTableBonusDetailMapper.updateByPrimaryKeySelective(bo) == 1) {
            return bo;
        }
        return null;
    }

    @Override
    public TableBonusDetail delete(TableBonusDetail bo) {
        if (this.iTableBonusDetailMapper.deleteByPrimaryKey(bo.getId()) == 1) {
            return bo;
        }
        return null;
    }

    @Override
    public TableBonusDetail select(TableBonusDetail bo) {
        return this.iTableBonusDetailMapper.selectByPrimaryKey(bo.getId());
    }

    /**
     * 根据BrondId统计奖金
     *
     * @param start
     * @param end
     * @return
     */
    @Override
    public List<TableBonusDetail> selectByGroupBonusId(String memberId, Date start, Date end) {
        return iTableBonusDetailMapperDZ.selectByGroupBonusId( memberId, start, end);
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
    public PageInfo<TableBonusDetail> queryListPage(TableBonusDetail bo, Integer pageNum, Integer pageSize, Map<String ,Object> map) {
        log.info("获取奖金流水管理表参数bo：{}", JSON.toJSON(bo));
        log.info("获取奖金流水管理表参数pageNum：{}", pageNum);
        log.info("获取奖金流水管理表参数pageSize：{}", pageSize);
        log.info("获取奖金流水管理表参数map：{}", JSON.toJSON(map));
        
        TableBonusDetailExample example = new TableBonusDetailExample();
        TableBonusDetailExample.Criteria criteria = example.createCriteria();

        if (null != bo.getId()) {
            criteria.andIdEqualTo(bo.getId());
        }
        if (StringUtils.isNotEmpty(bo.getMemberId())) {
            criteria.andMemberIdEqualTo(bo.getMemberId());
        }
        if (null != bo.getBonusId()) {
            criteria.andBonusIdEqualTo(bo.getBonusId());
        }
        if (null != bo.getBonusType()) {
            criteria.andBonusTypeEqualTo(bo.getBonusType());
        }
        if (null != bo.getBonus()) {
            criteria.andBonusEqualTo(bo.getBonus());
        }
        if (null != bo.getRecodeTime()) {
            criteria.andRecodeTimeEqualTo(bo.getRecodeTime());
        }
        if (StringUtils.isNotEmpty(bo.getRemark())) {
            criteria.andRemarkEqualTo(bo.getRemark());
        }
        if (null != bo.getCloseState()) {
            criteria.andCloseStateEqualTo(bo.getCloseState());
        }

        if (null != map) {
            if (null != map.get("start")) {
                criteria.andRecodeTimeGreaterThanOrEqualTo((Date)map.get("start"));
            }
            if (null != map.get("end")) {
                criteria.andRecodeTimeLessThanOrEqualTo((Date)map.get("end"));
            }
        }

        example.setOrderByClause("recode_time desc");

        PageInfo<TableBonusDetail> pageInfo = PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(() -> this.iTableBonusDetailMapper.selectByExample(example));
        log.info("获取奖金流水管理表结果：{}", JSON.toJSON(pageInfo));
        return pageInfo;
    }
}
