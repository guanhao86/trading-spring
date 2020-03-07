package com.spring.fee.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.fee.dao.mapper.TableMemberLevelChangeDetailMapper;
import com.spring.fee.model.TableMember;
import com.spring.fee.model.TableMemberLevelChangeDetail;
import com.spring.fee.model.TableMemberLevelChangeDetailExample;
import com.spring.fee.service.ITableMemberBusiSV;
import com.spring.fee.service.ITableMemberLevelChangeDetailBusiSV;
import com.spring.free.util.DateUtils;
import com.spring.free.util.exception.ExceptionCodeEnum;
import com.spring.free.util.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * 会员级别变更明细表服务
 */
@Slf4j
@Service
@Transactional
public class TableMemberLevelChangeDetailBusiSVImpl implements ITableMemberLevelChangeDetailBusiSV {

    @Autowired
    TableMemberLevelChangeDetailMapper iTableMemberLevelChangeDetailMapper;

    @Autowired
    ITableMemberBusiSV iTableMemberBusiSV;

    /**
     * 创建记录
     * @param bo
     * @return
     */
    @Override
    public TableMemberLevelChangeDetail insert(TableMemberLevelChangeDetail bo) {
        log.info("创建会员级别变更明细表参数bo：{}", JSON.toJSON(bo));
        Date sysdate = DateUtils.getSysDate();
        bo.setModifyTime(sysdate);
        iTableMemberLevelChangeDetailMapper.insert(bo);
        return bo;
    }

    @Override
    public TableMemberLevelChangeDetail update(TableMemberLevelChangeDetail bo) {
        Date sysdate = DateUtils.getSysDate();
        bo.setModifyTime(sysdate);
        if (this.iTableMemberLevelChangeDetailMapper.updateByPrimaryKeySelective(bo) == 1) {
            return bo;
        }
        return null;
    }

    @Override
    public TableMemberLevelChangeDetail delete(TableMemberLevelChangeDetail bo) {
        if (this.iTableMemberLevelChangeDetailMapper.deleteByPrimaryKey(bo.getId()) == 1) {
            return bo;
        }
        return null;
    }

    @Override
    public TableMemberLevelChangeDetail select(TableMemberLevelChangeDetail bo) {
        return this.iTableMemberLevelChangeDetailMapper.selectByPrimaryKey(bo.getId());
    }

    /**
     * 改变level
     *
     * @return
     */
    @Override
    public TableMemberLevelChangeDetail changeLevel(String memberId, Integer value, String remark) {
        //查询原纪录
        TableMember orig = this.iTableMemberBusiSV.selectByMemberId(memberId);
        if (orig == null)
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "会员不存在！", "", null);

        TableMemberLevelChangeDetail tableMemberLevelChangeDetail = new TableMemberLevelChangeDetail();
        tableMemberLevelChangeDetail.setMemberId(memberId);
        tableMemberLevelChangeDetail.setRemark(remark);
        tableMemberLevelChangeDetail.setBeforeLevel(orig.getLevel());
        tableMemberLevelChangeDetail.setAfterLevel(value);
        this.insert(tableMemberLevelChangeDetail);

        orig.setLevel(value);
        this.iTableMemberBusiSV.update(orig);

        return tableMemberLevelChangeDetail;
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
    public PageInfo<TableMemberLevelChangeDetail> queryListPage(TableMemberLevelChangeDetail bo, Integer pageNum, Integer pageSize, Map<String ,Object> map) {
        log.info("获取会员级别变更明细表参数bo：{}", JSON.toJSON(bo));
        log.info("获取会员级别变更明细表参数pageNum：{}", pageNum);
        log.info("获取会员级别变更明细表参数pageSize：{}", pageSize);
        log.info("获取会员级别变更明细表参数map：{}", JSON.toJSON(map));
        
        TableMemberLevelChangeDetailExample example = new TableMemberLevelChangeDetailExample();
        TableMemberLevelChangeDetailExample.Criteria criteria = example.createCriteria();

        if (null != bo.getId()) {
            criteria.andIdEqualTo(bo.getId());
        }
        if (StringUtils.isNotEmpty(bo.getMemberId())) {
            criteria.andMemberIdEqualTo(bo.getMemberId());
        }
        if (null != bo.getBeforeLevel()) {
            criteria.andBeforeLevelEqualTo(bo.getBeforeLevel());
        }
        if (null != bo.getAfterLevel()) {
            criteria.andAfterLevelEqualTo(bo.getAfterLevel());
        }
        if (null != bo.getModifyTime()) {
            criteria.andModifyTimeEqualTo(bo.getModifyTime());
        }
        if (StringUtils.isNotEmpty(bo.getRemark())) {
            criteria.andRemarkEqualTo(bo.getRemark());
        }

        example.setOrderByClause("modify_time desc");

        PageInfo<TableMemberLevelChangeDetail> pageInfo = PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(() -> this.iTableMemberLevelChangeDetailMapper.selectByExample(example));
        log.info("获取会员级别变更明细表结果：{}", JSON.toJSON(pageInfo));
        return pageInfo;
    }
}
