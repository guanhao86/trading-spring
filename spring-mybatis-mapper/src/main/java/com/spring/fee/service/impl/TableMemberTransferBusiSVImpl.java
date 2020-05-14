package com.spring.fee.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.fee.dao.mapper.TableMemberTransferMapper;
import com.spring.fee.model.TableMemberTransfer;
import com.spring.fee.model.TableMemberTransferExample;
import com.spring.fee.service.IMemberAccountDetailBusiSV;
import com.spring.fee.service.ITableBonusDetailBusiSV;
import com.spring.fee.service.ITableMemberTransferBusiSV;
import com.spring.fee.service.ITableTaskBusiSV;
import com.spring.free.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 转账记录服务
 */
@Slf4j
@Service
@Transactional
public class TableMemberTransferBusiSVImpl implements ITableMemberTransferBusiSV {

    @Autowired
    TableMemberTransferMapper iTableMemberTransferMapper;

    @Autowired
    ITableTaskBusiSV iTableTaskBusiSV;

    @Autowired
    ITableBonusDetailBusiSV iTableBonusDetailBusiSV;

    @Autowired
    IMemberAccountDetailBusiSV iMemberAccountDetailBusiSV;

    /**
     * 创建转账记录记录
     * @param bo
     * @return
     */
    @Override
    public TableMemberTransfer insert(TableMemberTransfer bo) {
        log.info("转账记录参数bo：{}", JSON.toJSON(bo));
        bo.setCreateTime(DateUtils.getSysDate());
        iTableMemberTransferMapper.insert(bo);
        return bo;
    }

    @Override
    public TableMemberTransfer update(TableMemberTransfer bo) {
        if (this.iTableMemberTransferMapper.updateByPrimaryKeySelective(bo) == 1) {
            return bo;
        }
        return null;
    }

    @Override
    public TableMemberTransfer delete(TableMemberTransfer bo) {
        if (this.iTableMemberTransferMapper.deleteByPrimaryKey(bo.getId()) == 1) {
            return bo;
        }
        return null;
    }

    @Override
    public TableMemberTransfer select(TableMemberTransfer bo) {
        return this.iTableMemberTransferMapper.selectByPrimaryKey(bo.getId());
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
    public PageInfo<TableMemberTransfer> queryListPage(TableMemberTransfer bo, Integer pageNum, Integer pageSize, Map<String ,Object> map) {
        log.info("获取转账记录参数bo：{}", JSON.toJSON(bo));
        log.info("获取转账记录参数pageNum：{}", pageNum);
        log.info("获取转账记录参数pageSize：{}", pageSize);
        log.info("获取转账记录参数map：{}", JSON.toJSON(map));
        
        TableMemberTransferExample example = new TableMemberTransferExample();
        TableMemberTransferExample.Criteria criteria = example.createCriteria();

        if (null != bo.getId()) {
            criteria.andIdEqualTo(bo.getId());
        }
        if (StringUtils.isNotEmpty(bo.getFromMemberId())) {
            criteria.andFromMemberIdEqualTo(bo.getFromMemberId());
        }
        if (null != bo.getToMemberId()) {
            criteria.andToMemberIdEqualTo(bo.getToMemberId());
        }
        if (null != bo.getAmount()) {
            criteria.andAmountEqualTo(bo.getAmount());
        }
        if (StringUtils.isNotEmpty(bo.getType())) {
            criteria.andTypeEqualTo(bo.getType());
        }
        if (null != bo.getCreateTime()) {
            criteria.andCreateTimeEqualTo(bo.getCreateTime());
        }
        if (StringUtils.isNotEmpty(bo.getRemark())) {
            criteria.andRemarkEqualTo(bo.getRemark());
        }

        example.setOrderByClause("create_time desc");

        PageInfo<TableMemberTransfer> pageInfo = PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(() -> this.iTableMemberTransferMapper.selectByExample(example));
        log.info("获取转账记录结果：{}", JSON.toJSON(pageInfo));
        return pageInfo;
    }
}
