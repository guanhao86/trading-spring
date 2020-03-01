package com.spring.fee.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.fee.constants.InvestConstants;
import com.spring.fee.dao.mapper.TInvestPlanDetailMapper;
import com.spring.fee.dao.mapper.TInvestPlanDetailMapperDZ;
import com.spring.fee.model.*;
import com.spring.fee.service.ITInvestPlanBonusBusiSV;
import com.spring.fee.service.ITInvestPlanBonusTmpBusiSV;
import com.spring.fee.service.ITInvestPlanConfigBusiSV;
import com.spring.fee.service.ITInvestPlanDetailBusiSV;
import com.spring.free.util.DateUtils;
import com.spring.free.util.exception.ExceptionConstants;
import com.spring.free.util.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.crypto.spec.IvParameterSpec;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 粮仓计划实例：分仓服务
 */
@Slf4j
@Service
@Transactional
public class TInvestPlanDetailBusiSVImpl implements ITInvestPlanDetailBusiSV {

    @Autowired
    TInvestPlanDetailMapper investPlanDetailMapper;

    @Autowired
    TInvestPlanDetailMapperDZ investPlanDetailMapperDZ;

    @Autowired
    ITInvestPlanConfigBusiSV itInvestPlanConfigBusiSV;

    @Autowired
    ITInvestPlanBonusTmpBusiSV itInvestPlanBonusTmpBusiSV;

    @Autowired
    ITInvestPlanBonusBusiSV itInvestPlanBonusBusiSV;

    @Override
    public TInvestPlanDetail insert(TInvestPlanDetail investPlanDetail) {
        Date sysDate = DateUtils.getSysDate();
        investPlanDetail.setCreateTime(sysDate);
        investPlanDetail.setStatus(InvestConstants.Plan.DETAIL_STATUS_ING);//执行中
        investPlanDetail.setStatusTime(sysDate);
        if (1 == this.investPlanDetailMapper.insert(investPlanDetail))
            return investPlanDetail;
        else
            return null;
    }

    @Override
    public TInvestPlanDetail update(TInvestPlanDetail investPlanDetail) {
        if (1 == this.investPlanDetailMapper.updateByPrimaryKeySelective(investPlanDetail))
            return investPlanDetail;
        else
            return null;
    }

    @Override
    public TInvestPlanDetail delete(TInvestPlanDetail investPlanDetail) {
        return null;
    }

    @Override
    public TInvestPlanDetail select(TInvestPlanDetail investPlanDetail) {
        return this.investPlanDetailMapper.selectByPrimaryKey(investPlanDetail.getId());
    }

    @Override
    public PageInfo<TInvestPlanDetail> queryList(TInvestPlanDetail investPlanDetail, Integer pageNum, Integer pageSize, Map<String ,Object> map) {
        log.info("获取粮仓执行计划分仓列表参数");
        log.info("investPlanDetail：{}", JSON.toJSON(investPlanDetail));
        log.info("pageNum：{}", pageNum);
        log.info("pageSize：{}", pageSize);
        log.info("map：{}", JSON.toJSON(map));
        TInvestPlanDetailExample example = new TInvestPlanDetailExample();
        TInvestPlanDetailExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(investPlanDetail.getPlanMainId())) {
            criteria.andPlanMainIdEqualTo(investPlanDetail.getPlanMainId());
        }
        if (StringUtils.isNotEmpty(investPlanDetail.getMemberId())) {
            criteria.andMemberIdEqualTo(investPlanDetail.getMemberId());
        }
        if (StringUtils.isNotEmpty(investPlanDetail.getOrderId())) {
            criteria.andOrderIdEqualTo(investPlanDetail.getOrderId());
        }
        if (StringUtils.isNotEmpty(investPlanDetail.getStatus())) {
            criteria.andStatusEqualTo(investPlanDetail.getStatus());
        }
        if (StringUtils.isNotEmpty(investPlanDetail.getType())) {
            criteria.andTypeEqualTo(investPlanDetail.getType());
        }
        if (null != investPlanDetail.getId()) {
            criteria.andIdEqualTo(investPlanDetail.getId());
        }
        PageInfo<TInvestPlanDetail> pageInfo = PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(() -> this.investPlanDetailMapper.selectByExample(example));
        log.info("获取粮仓执行计划分仓列表结果：{}", JSON.toJSON(pageInfo));
        return pageInfo;
    }

    @Override
    public List<TInvestPlanDetail> queryList(TInvestPlanDetail investPlanDetail) {
        log.info("获取粮仓执行计划分仓列表参数");
        log.info("investPlanDetail：{}", JSON.toJSON(investPlanDetail));
        TInvestPlanDetailExample example = new TInvestPlanDetailExample();
        TInvestPlanDetailExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(investPlanDetail.getPlanMainId())) {
            criteria.andPlanMainIdEqualTo(investPlanDetail.getPlanMainId());
        }
        if (StringUtils.isNotEmpty(investPlanDetail.getMemberId())) {
            criteria.andMemberIdEqualTo(investPlanDetail.getMemberId());
        }
        if (StringUtils.isNotEmpty(investPlanDetail.getOrderId())) {
            criteria.andOrderIdEqualTo(investPlanDetail.getOrderId());
        }
        if (StringUtils.isNotEmpty(investPlanDetail.getStatus())) {
            criteria.andStatusEqualTo(investPlanDetail.getStatus());
        }
        if (StringUtils.isNotEmpty(investPlanDetail.getType())) {
            criteria.andTypeEqualTo(investPlanDetail.getType());
        }
        if (null != investPlanDetail.getId()) {
            criteria.andIdEqualTo(investPlanDetail.getId());
        }
        List<TInvestPlanDetail> arr = this.investPlanDetailMapper.selectByExample(example);
        log.info("获取粮仓执行计划分仓列表结果：{}", JSON.toJSON(arr));
        return arr;
    }

    /**
     * 根据计划主表插入计划分表
     *
     * @param investPlanMain
     * @return
     */
    @Override
    public long insertByMainNew(TInvestPlanMain investPlanMain) {
        TInvestPlanConfig investPlanConfigParam = new TInvestPlanConfig();
        investPlanConfigParam.setPlanId(investPlanMain.getPlanId());
        TInvestPlanConfig investPlanConfig = this.itInvestPlanConfigBusiSV.select(investPlanConfigParam);
        Date date = DateUtils.getSysDate();
        if (investPlanMain.getStartTime() != null)
            date = investPlanMain.getStartTime();

        //插入主仓
        TInvestPlanDetail tInvestPlanDetailMain = this.getDetailVOfromMain(investPlanMain, investPlanConfig, "1");
        tInvestPlanDetailMain.setStartTime(date);//主仓开始时间
        tInvestPlanDetailMain.setEndTime(DateUtils.addDays(date, investPlanConfig.getMainDays()));
        this.insert(tInvestPlanDetailMain);
        //插入副仓
        //副仓开始时间=主仓结束时间
        TInvestPlanDetail tInvestPlanDetailSecond = this.getDetailVOfromMain(investPlanMain, investPlanConfig, "2");
        tInvestPlanDetailSecond.setStartTime(tInvestPlanDetailMain.getEndTime());//副仓开始时间
        tInvestPlanDetailSecond.setEndTime(DateUtils.addDays(tInvestPlanDetailMain.getEndTime(), investPlanConfig.getSecondDays()));
        this.insert(tInvestPlanDetailSecond);

        //计划执行时插入（粮仓奖金计算临时表，执行计划时，将计划金额和执行时间插入到该表）
        log.info("计划执行时插入（粮仓奖金计算临时表，执行计划时，将计划金额和执行时间插入到该表）");
        TInvestPlanBonusTmp tInvestPlanBonusTmp = new TInvestPlanBonusTmp();
        tInvestPlanBonusTmp.setAmount(tInvestPlanDetailMain.getPlanAmount()+tInvestPlanDetailSecond.getPlanAmount());//奖金按照投入金额计算
        tInvestPlanBonusTmp.setMemberId(investPlanMain.getMemberId());
        tInvestPlanBonusTmp.setOrderId(investPlanMain.getOrderId());
        tInvestPlanBonusTmp.setPlanMainId(investPlanMain.getId()+"");
        tInvestPlanBonusTmp.setType(InvestConstants.Bonus.BONUS_TYPE_MAIN_MONEY);//主仓投入金额类型
        tInvestPlanBonusTmp.setPaymentday(DateUtils.getYYYYMMDD(investPlanMain.getStartTime()));
        tInvestPlanBonusTmp = itInvestPlanBonusTmpBusiSV.insert(tInvestPlanBonusTmp);
        Long id= tInvestPlanBonusTmp.getId();

        return id;
    }

    /**
     * 根据计划主表插入计划分表（续仓）
     *
     * @param investPlanMainOrig
     * @param investPlanMainNew
     * @return
     */
    @Override
    public long insertByMainSecond(TInvestPlanMain investPlanMainOrig, TInvestPlanMain investPlanMainNew) {
        //查询原分仓列表
        TInvestPlanDetail investPlanDetailParam = new TInvestPlanDetail();
        investPlanDetailParam.setPlanMainId(String.valueOf(investPlanMainOrig.getId()));
        PageInfo<TInvestPlanDetail> planDetailPageInfo = this.queryList(investPlanDetailParam, 1, 20, null);
        if (planDetailPageInfo == null || planDetailPageInfo.getList() == null || planDetailPageInfo.getList().isEmpty()) {
            throw new ServiceException(ExceptionConstants.Result.FEILD_NOT_MACTH, "未查询到原分仓记录");
        }
        long id = 0l;
        for (TInvestPlanDetail tInvestPlanDetail : planDetailPageInfo.getList()) {
            //副仓
            if (InvestConstants.Plan.PLAN_TYPE_SECOND.equals(tInvestPlanDetail.getType())) {
                //如果副仓记录不是运行中，则不允许续仓
                if (!InvestConstants.Plan.DETAIL_STATUS_ING.equals(tInvestPlanDetail.getStatus())){
                    throw new ServiceException(ExceptionConstants.Result.FEILD_NOT_MACTH, "当前计划副仓已办结，不允许续仓");
                }
                //待插入副仓
                TInvestPlanDetail tInvestPlanDetailSecond = new TInvestPlanDetail();
                //待插入主仓
                TInvestPlanDetail tInvestPlanDetailMain = new TInvestPlanDetail();
                //复制原副仓到新的副仓
                BeanUtils.copyProperties(tInvestPlanDetail, tInvestPlanDetailSecond);
                //复制原副仓到新的主仓
                BeanUtils.copyProperties(tInvestPlanDetail, tInvestPlanDetailMain);

                log.info("续仓流程：（从原仓复制计划）");
                log.info("续仓流程：插入新的主仓");
                //获取开始时间

                Date date = DateUtils.getSysDate();
                if (investPlanMainNew.getStartTime() != null)
                    date = investPlanMainNew.getStartTime();

                //修改新主仓外键为新计划主键
                tInvestPlanDetailMain.setId(null);
                tInvestPlanDetailMain.setPlanMainId(String.valueOf(investPlanMainNew.getId()));
                tInvestPlanDetailMain.setType(InvestConstants.Plan.PLAN_TYPE_MAIN);//修改为主仓
                tInvestPlanDetailMain.setStartTime(date);
                Date mainEndDate = DateUtils.addDays(date, tInvestPlanDetailMain.getPlanDays() - tInvestPlanDetailMain.getReturnDays());//新主仓结束时间=开始时间+（原副仓的计划天数-已返还天数）
                tInvestPlanDetailMain.setEndTime(mainEndDate);
                tInvestPlanDetailMain.setRetrunAmount(0L);
                tInvestPlanDetailMain.setTotalDays(tInvestPlanDetailMain.getPlanDays() - tInvestPlanDetailMain.getReturnDays());
                tInvestPlanDetailMain.setReturnDays(0);
                tInvestPlanDetailMain.setRetrunInterest(0L);//返还利息
                tInvestPlanDetailMain.setAppendFlay(InvestConstants.Plan.DETAIL_APPEND_Y); //分表未续仓
                tInvestPlanDetailMain.setInterestReturnPer(investPlanMainOrig.getInterestReturnPer());
                this.insert(tInvestPlanDetailMain);

                log.info("续仓流程：插入新的副仓");
                //修改新副仓外键为新计划主键
                tInvestPlanDetailSecond.setId(null);
                tInvestPlanDetailSecond.setPlanMainId(String.valueOf(investPlanMainNew.getId()));
                tInvestPlanDetailSecond.setRetrunAmount(0L);
                tInvestPlanDetailSecond.setReturnDays(0);
                tInvestPlanDetailSecond.setTotalDays(tInvestPlanDetailSecond.getPlanDays());
                tInvestPlanDetailSecond.setRetrunInterest(0L);//返还利息
                tInvestPlanDetailSecond.setStartTime(mainEndDate);
                tInvestPlanDetailSecond.setEndTime(DateUtils.addDays(mainEndDate, tInvestPlanDetailSecond.getPlanDays()));
                tInvestPlanDetailSecond.setAppendFlay(InvestConstants.Plan.DETAIL_APPEND_N);//分表未续仓
                this.insert(tInvestPlanDetailSecond);

                log.info("续仓流程：更新原副仓状态");
                tInvestPlanDetail.setStatus(InvestConstants.Plan.DETAIL_STATUS_FINISH); //完结
                tInvestPlanDetail.setBeAppendFlay(InvestConstants.Plan.DETAIL_APPEND_Y); //续仓
                tInvestPlanDetail.setStatusTime(date);
                tInvestPlanDetail.setAppendTime(date);
                this.update(tInvestPlanDetail);

                //计划执行时插入（粮仓奖金计算临时表，执行计划时，将计划金额和执行时间插入到该表）
                log.info("计划执行时插入（粮仓奖金计算临时表，执行计划时，将计划金额和执行时间插入到该表）");
                TInvestPlanBonusTmp tInvestPlanBonusTmp = new TInvestPlanBonusTmp();
                tInvestPlanBonusTmp.setAmount(tInvestPlanDetailMain.getPlanAmount());
                tInvestPlanBonusTmp.setMemberId(investPlanMainOrig.getMemberId());
                tInvestPlanBonusTmp.setOrderId(tInvestPlanDetailMain.getOrderId());
                tInvestPlanBonusTmp.setPlanMainId(investPlanMainNew.getId()+"");
                tInvestPlanBonusTmp.setType(InvestConstants.Bonus.BONUS_TYPE_MAIN_MONEY);//主仓投入金额类型
                tInvestPlanBonusTmp.setPaymentday(DateUtils.getYYYYMMDD(investPlanMainNew.getStartTime()));
                tInvestPlanBonusTmp = itInvestPlanBonusTmpBusiSV.insert(tInvestPlanBonusTmp);

                id = tInvestPlanBonusTmp.getId();

            }

        }
        return id;
    }

    /**
     * 获取正在执行主计划列表（计划ID，会员级别分组）
     *
     * @param tInvestPlanDetailDZ
     * @return
     */
    @Override
    public List<TInvestPlanDetailDZ> getMainRunningCount(TInvestPlanDetailDZ tInvestPlanDetailDZ) {
        return this.investPlanDetailMapperDZ.getMainRunningCount(tInvestPlanDetailDZ);
    }

    /**
     * 获取正在执行主计划列表（会员级别分组）
     *
     * @param tInvestPlanDetailDZ
     * @return
     */
    @Override
    public Map<String, TInvestPlanDetailDZ> getMainRunningCount2(TInvestPlanDetailDZ tInvestPlanDetailDZ) {

        Map<String, TInvestPlanDetailDZ> map = new HashMap<>();

        List<TInvestPlanDetailDZ> tInvestPlanDetailDZS = this.investPlanDetailMapperDZ.getMainRunningCount2(tInvestPlanDetailDZ);
        if (!CollectionUtils.isEmpty(tInvestPlanDetailDZS)) {
            for (TInvestPlanDetailDZ dz : tInvestPlanDetailDZS) {
                map.put(dz.getMemberId(), dz);
            }
        }
        return map;
    }

    /**
     * 统计业绩
     *
     * @param memberIdList
     * @param todayFlag    = 1今日业绩  其他 累计业绩
     * @return
     */
    @Override
    public long statisticPlanAmount(List<String> memberIdList, String todayFlag) {

        if (CollectionUtils.isEmpty(memberIdList)) {
            return 0l;
        }

        TInvestPlanDetailDZ tInvestPlanDetailDZ = new TInvestPlanDetailDZ();
        if ("1".equals(todayFlag)) {
            tInvestPlanDetailDZ.setCreateTimeStart(DateUtils.getDateZero(DateUtils.getSysDate()));
            tInvestPlanDetailDZ.setCreateTimeEnd(DateUtils.getDateZero(DateUtils.getNextDate(DateUtils.getSysDate())));
        }
        tInvestPlanDetailDZ.setMemberList(memberIdList);
        List<TInvestPlanDetailDZ> arr = this.investPlanDetailMapperDZ.statisticPlanAmount(tInvestPlanDetailDZ);
        if (CollectionUtils.isEmpty(arr) || null == arr.get(0)) {
            return 0;
        }
        return arr.get(0).getAmount();
    }

    /**
     * 获取主计划执行中数量统计，用于可购买计划校验
     *
     * @param tInvestPlanDetailDZ
     * @return
     */
    @Override
    public List<TInvestPlanDetailDZ> getMainRunningPlanCount(TInvestPlanDetailDZ tInvestPlanDetailDZ) {
        return this.investPlanDetailMapperDZ.getMainRunningPlanCount(tInvestPlanDetailDZ);
    }

    /**
     * 从主仓中复制分仓
     * @param investPlanMain
     * @param investPlanConfig 计划配置
     * @param isMain 1主仓  2副仓
     * @return
     */
    private TInvestPlanDetail getDetailVOfromMain(TInvestPlanMain investPlanMain, TInvestPlanConfig investPlanConfig, String isMain){
        if (investPlanConfig == null) {
            TInvestPlanConfig investPlanConfigParam = new TInvestPlanConfig();
            investPlanConfigParam.setPlanId(investPlanMain.getPlanId());
            investPlanConfig = this.itInvestPlanConfigBusiSV.select(investPlanConfigParam);
        }
        TInvestPlanDetail tInvestPlanDetail = new TInvestPlanDetail();
        tInvestPlanDetail.setPlanMainId(String.valueOf(investPlanMain.getId()));//主仓实例ID
        tInvestPlanDetail.setOrderId(investPlanMain.getOrderId());//计划ID（一个计划ID可对应多个计划实例）
        tInvestPlanDetail.setStatus(InvestConstants.Plan.DETAIL_STATUS_ING);//运行中
        if ("1".equals(isMain)){
            tInvestPlanDetail.setType(InvestConstants.Plan.PLAN_TYPE_MAIN);//主仓
            tInvestPlanDetail.setPlanAmount(investPlanConfig.getMainAmount());//主仓金额
            tInvestPlanDetail.setPlanDays(investPlanConfig.getMainDays());//主仓天数
            tInvestPlanDetail.setTotalDays(investPlanConfig.getMainDays());//实际天数
        }else if ("2".equals(isMain)){
            tInvestPlanDetail.setType(InvestConstants.Plan.PLAN_TYPE_SECOND);//副仓
            tInvestPlanDetail.setPlanAmount(investPlanConfig.getSecondAmount());//主仓金额
            tInvestPlanDetail.setPlanDays(investPlanConfig.getSecondDays());//主仓天数
            tInvestPlanDetail.setTotalDays(investPlanConfig.getSecondDays());//实际天数
        }
        tInvestPlanDetail.setPlanId(investPlanMain.getPlanId());
        tInvestPlanDetail.setMemberId(investPlanMain.getMemberId());
        tInvestPlanDetail.setRetrunAmount(0L);
        tInvestPlanDetail.setReturnDays(0);
        tInvestPlanDetail.setRetrunInterest(0L);//返还利息
        tInvestPlanDetail.setAppendFlay(InvestConstants.Plan.DETAIL_APPEND_N);//未续仓
        tInvestPlanDetail.setInterestReturnPer(investPlanMain.getInterestReturnPer());
        return tInvestPlanDetail;
    }
}
