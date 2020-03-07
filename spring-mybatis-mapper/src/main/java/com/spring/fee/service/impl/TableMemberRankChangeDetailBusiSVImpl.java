package com.spring.fee.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.fee.dao.mapper.TableMemberRankChangeDetailMapper;
import com.spring.fee.model.TableMember;
import com.spring.fee.model.TableMemberLevelChangeDetail;
import com.spring.fee.model.TableMemberRankChangeDetail;
import com.spring.fee.model.TableMemberRankChangeDetailExample;
import com.spring.fee.service.ITableMemberBusiSV;
import com.spring.fee.service.ITableMemberRankChangeDetailBusiSV;
import com.spring.free.util.DateUtils;
import com.spring.free.util.exception.ExceptionCodeEnum;
import com.spring.free.util.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * 会员头衔变更明细表服务
 */
@Slf4j
@Service
@Transactional
public class TableMemberRankChangeDetailBusiSVImpl implements ITableMemberRankChangeDetailBusiSV {

    @Autowired
    TableMemberRankChangeDetailMapper iTableMemberRankChangeDetailMapper;

    @Autowired
    ITableMemberBusiSV iTableMemberBusiSV;

    /**
     * 创建记录
     * @param bo
     * @return
     */
    @Override
    public TableMemberRankChangeDetail insert(TableMemberRankChangeDetail bo) {
        log.info("创建会员头衔变更明细表参数bo：{}", JSON.toJSON(bo));
        Date sysdate = DateUtils.getSysDate();
        bo.setModifyTime(sysdate);
        iTableMemberRankChangeDetailMapper.insert(bo);
        return bo;
    }

    @Override
    public TableMemberRankChangeDetail update(TableMemberRankChangeDetail bo) {
        Date sysdate = DateUtils.getSysDate();
        bo.setModifyTime(sysdate);
        if (this.iTableMemberRankChangeDetailMapper.updateByPrimaryKeySelective(bo) == 1) {
            return bo;
        }
        return null;
    }

    @Override
    public TableMemberRankChangeDetail delete(TableMemberRankChangeDetail bo) {
        if (this.iTableMemberRankChangeDetailMapper.deleteByPrimaryKey(bo.getId()) == 1) {
            return bo;
        }
        return null;
    }

    @Override
    public TableMemberRankChangeDetail select(TableMemberRankChangeDetail bo) {
        return this.iTableMemberRankChangeDetailMapper.selectByPrimaryKey(bo.getId());
    }

    /**
     * 改变头衔
     *
     * @param memberId
     * @param value
     * @param remark
     * @return
     */
    @Override
    public TableMemberRankChangeDetail changeRank(String memberId, Integer value, String remark) {
        //查询原纪录
        TableMember orig = this.iTableMemberBusiSV.selectByMemberId(memberId);
        if (orig == null)
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "会员不存在！", "", null);

        TableMemberRankChangeDetail tableMemberRankChangeDetail = new TableMemberRankChangeDetail();
        tableMemberRankChangeDetail.setMemberId(memberId);
        tableMemberRankChangeDetail.setRemark(remark);
        tableMemberRankChangeDetail.setBeforeRank(orig.getmRank());
        tableMemberRankChangeDetail.setAfterRank(value);
        this.insert(tableMemberRankChangeDetail);

        orig.setmRank(value);
        this.iTableMemberBusiSV.update(orig);

        return tableMemberRankChangeDetail;
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
    public PageInfo<TableMemberRankChangeDetail> queryListPage(TableMemberRankChangeDetail bo, Integer pageNum, Integer pageSize, Map<String ,Object> map) {
        log.info("获取会员头衔变更明细表参数bo：{}", JSON.toJSON(bo));
        log.info("获取会员头衔变更明细表参数pageNum：{}", pageNum);
        log.info("获取会员头衔变更明细表参数pageSize：{}", pageSize);
        log.info("获取会员头衔变更明细表参数map：{}", JSON.toJSON(map));
        
        TableMemberRankChangeDetailExample example = new TableMemberRankChangeDetailExample();
        TableMemberRankChangeDetailExample.Criteria criteria = example.createCriteria();

        if (null != bo.getId()) {
            criteria.andIdEqualTo(bo.getId());
        }
        if (StringUtils.isNotEmpty(bo.getMemberId())) {
            criteria.andMemberIdEqualTo(bo.getMemberId());
        }
        if (null != bo.getBeforeRank()) {
            criteria.andBeforeRankEqualTo(bo.getBeforeRank());
        }
        if (null != bo.getAfterRank()) {
            criteria.andAfterRankEqualTo(bo.getAfterRank());
        }
        if (null != bo.getModifyTime()) {
            criteria.andModifyTimeEqualTo(bo.getModifyTime());
        }
        if (StringUtils.isNotEmpty(bo.getRemark())) {
            criteria.andRemarkEqualTo(bo.getRemark());
        }

        example.setOrderByClause("modify_time desc");

        PageInfo<TableMemberRankChangeDetail> pageInfo = PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(() -> this.iTableMemberRankChangeDetailMapper.selectByExample(example));
        log.info("获取会员头衔变更明细表结果：{}", JSON.toJSON(pageInfo));
        return pageInfo;
    }
}
