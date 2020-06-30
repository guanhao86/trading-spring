package com.spring.fee.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.fee.dao.mapper.TableMemberRelationSettingMapper;
import com.spring.fee.model.TableMemberRelationSetting;
import com.spring.fee.model.TableMemberRelationSettingDZ;
import com.spring.fee.model.TableMemberRelationSettingExample;
import com.spring.fee.service.ITableMemberBusiSV;
import com.spring.fee.service.ITableMemberRelationSettingBusiSV;
import com.spring.free.util.DateUtils;
import com.spring.free.util.exception.ExceptionCodeEnum;
import com.spring.free.util.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 网络开放设置
 */
@Slf4j
@Service
@Transactional
public class TableMemberRelationSettingBusiSVImpl implements ITableMemberRelationSettingBusiSV {

    @Autowired
    TableMemberRelationSettingMapper iTableMemberRelationSettingMapper;

    @Autowired
    ITableMemberBusiSV iTableMemberBusiSV;

    /**
     * 创建记录
     * @param bo
     * @return
     */
    @Override
    public TableMemberRelationSetting insert(TableMemberRelationSetting bo) {
        log.info("创建网络开放设置参数bo：{}", JSON.toJSON(bo));
        Date sysdate = DateUtils.getSysDate();
        bo.setCreateTime(sysdate);
        bo.setValidTime(sysdate);
        if ("1".equals(bo.getType())) {
            bo.setExpireTime(DateUtils.parseDate("2099-12-31"));
        } else if ("2".equals(bo.getType())) {
            bo.setExpireTime(DateUtils.getDateZero(DateUtils.getNextDate(DateUtils.getSysDate())));
        }
        iTableMemberRelationSettingMapper.insert(bo);
        return bo;
    }

    @Override
    public TableMemberRelationSetting update(TableMemberRelationSetting bo) {
        Date sysdate = DateUtils.getSysDate();
        bo.setCreateTime(sysdate);
        if ("1".equals(bo.getType())) {
            bo.setExpireTime(DateUtils.parseDate("2099-12-31"));
        } else if ("2".equals(bo.getType())) {
            bo.setExpireTime(DateUtils.getDateZero(DateUtils.getNextDate(DateUtils.getSysDate())));
        }
        if (this.iTableMemberRelationSettingMapper.updateByPrimaryKeySelective(bo) == 1) {
            return bo;
        }
        return null;
    }

    @Override
    public TableMemberRelationSetting set(TableMemberRelationSetting bo) {

        if(null == iTableMemberBusiSV.selectByMemberId(bo.getMemberId())) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "会员不存在！", "", null);
        }

        if (bo.getId() != null) {
            TableMemberRelationSetting orig = this.select(bo);
            if (!orig.getRelationType().equals(bo.getRelationType())) {
                delete2(bo.getMemberId(), orig.getRelationType());
            }
        }

        delete2(bo.getMemberId(), bo.getRelationType());
        this.insert(bo);
        return bo;
    }

    @Override
    public TableMemberRelationSetting delete(TableMemberRelationSetting bo) {
        if (this.iTableMemberRelationSettingMapper.deleteByPrimaryKey(bo.getId()) == 1) {
            return bo;
        }
        return null;
    }

    public TableMemberRelationSetting delete2(String memberId, String relationType) {
        TableMemberRelationSettingExample example = new TableMemberRelationSettingExample();
        TableMemberRelationSettingExample.Criteria criteria = example.createCriteria();
        criteria.andMemberIdEqualTo(memberId);
        criteria.andRelationTypeEqualTo(relationType);
        this.iTableMemberRelationSettingMapper.deleteByExample(example);
        return null;
    }

    @Override
    public TableMemberRelationSetting select(TableMemberRelationSetting bo) {
        return this.iTableMemberRelationSettingMapper.selectByPrimaryKey(bo.getId());
    }

    @Override
    public TableMemberRelationSettingDZ check(String memberId) {

        TableMemberRelationSettingDZ dz = new TableMemberRelationSettingDZ();

        TableMemberRelationSettingExample example = new TableMemberRelationSettingExample();
        TableMemberRelationSettingExample.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotEmpty(memberId)) {
            criteria.andMemberIdEqualTo(memberId);
        }

        Date now = DateUtils.getSysDate();
        criteria.andValidTimeLessThan(now);
        criteria.andExpireTimeGreaterThan(now);

        List<TableMemberRelationSetting> list = this.iTableMemberRelationSettingMapper.selectByExample(example);

        if (!CollectionUtils.isEmpty(list)) {
            for (TableMemberRelationSetting setting : list) {
                //推荐
                if ("1".equals(setting.getRelationType())) {
                    dz.setReference(true);
                } else if ("2".equals(setting.getRelationType())) {
                    dz.setArrange(true);
                }
            }
        }

        return dz;
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
    public PageInfo<TableMemberRelationSetting> queryListPage(TableMemberRelationSetting bo, Integer pageNum, Integer pageSize, Map<String ,Object> map) {
        log.info("获取网络开放设置参数bo：{}", JSON.toJSON(bo));
        log.info("获取网络开放设置参数pageNum：{}", pageNum);
        log.info("获取网络开放设置参数pageSize：{}", pageSize);
        log.info("获取网络开放设置参数map：{}", JSON.toJSON(map));
        
        TableMemberRelationSettingExample example = new TableMemberRelationSettingExample();
        TableMemberRelationSettingExample.Criteria criteria = example.createCriteria();

        if (null != bo.getId()) {
            criteria.andIdEqualTo(bo.getId());
        }
        if (StringUtils.isNotEmpty(bo.getMemberId())) {
            criteria.andMemberIdEqualTo(bo.getMemberId());
        }

        Date now = DateUtils.getSysDate();
        if (StringUtils.isNotEmpty(bo.getType())) {
            if ("0".equals(bo.getType())) {
                criteria.andExpireTimeLessThan(now);
            } else {
                criteria.andTypeEqualTo(bo.getType());
                criteria.andExpireTimeGreaterThan(now);
            }
        }

        example.setOrderByClause("create_time desc");

        PageInfo<TableMemberRelationSetting> pageInfo = PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(() -> this.iTableMemberRelationSettingMapper.selectByExample(example));
        log.info("获取网络开放设置结果：{}", JSON.toJSON(pageInfo));
        return pageInfo;
    }
}
