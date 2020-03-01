package com.spring.fee.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.fee.constants.InvestConstants;
import com.spring.fee.dao.mapper.TInvestPlanMainMapper;
import com.spring.fee.dao.mapper.TInvestPlanMainMapperDZ;
import com.spring.fee.model.*;
import com.spring.fee.service.*;
import com.spring.free.util.DateUtils;
import com.spring.free.util.exception.ExceptionConstants;
import com.spring.free.util.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.NullLiteral;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.crypto.spec.IvParameterSpec;
import java.util.*;

/**
 * 粮仓计划实例服务
 */
@Slf4j
@Service
@Transactional
public class TInvestPlanMainBusiSVImpl implements ITInvestPlanMainBusiSV {

    @Autowired
    TInvestPlanMainMapper investPlanMainMapper;

    @Autowired
    ITInvestPlanMainBusiSV itInvestPlanMainBusiSV;

    @Autowired
    TInvestPlanMainMapperDZ investPlanMainMapperDZ;

    @Autowired
    ITInvestPlanConfigBusiSV itInvestPlanConfigBusiSV;

    //计划分仓服务
    @Autowired
    ITInvestPlanDetailBusiSV itInvestPlanDetailBusiSV;

    @Autowired
    ITInvestQueueBusiSV itInvestQueueBusiSV;

    @Autowired
    ITWheatAccountBusiSV itWheatAccountBusiSV;

    @Autowired
    ITInvestPlanRunDetailBusiSV itInvestPlanRunDetailBusiSV;

    @Autowired
    ITInvestPlanMainLogSV itInvestPlanMainLogSV;

    @Autowired
    ITInvestMemberConfigBusiSV itInvestMemberConfigBusiSV;

    @Autowired
    ITInvestSettleRecordBusiSV itInvestSettleRecordBusiSV;

    @Autowired
    ITInvestPlanBonusBusiSV itInvestPlanBonusBusiSV;

    @Override
    public TInvestPlanMain insert(TInvestPlanMain investPlanMain) {
        investPlanMain.setCreateTime(DateUtils.getSysDate());
        if (1 == this.investPlanMainMapper.insert(investPlanMain))
            return investPlanMain;
        else
            return null;
    }

    @Override
    public TInvestPlanMain update(TInvestPlanMain investPlanMain) {
        //investPlanMain.setCreateTime(DateUtils.getSysDate());
        if (1 == this.investPlanMainMapper.updateByPrimaryKeySelective(investPlanMain))
            return investPlanMain;
        else
            return null;
    }

    @Override
    public TInvestPlanMain delete(TInvestPlanMain investPlanMain) {
        return null;
    }

    @Override
    public TInvestPlanMain select(TInvestPlanMain investPlanMain) {
        return this.investPlanMainMapper.selectByPrimaryKey(investPlanMain.getId());
    }

    @Override
    public PageInfo<TInvestPlanMain> queryList(TInvestPlanMain investPlanMain, Integer pageNum, Integer pageSize, Map<String ,Object> map) {
        log.info("获取粮仓计划实例列表参数");
        log.info("investPlanMain：{}", JSON.toJSON(investPlanMain));
        log.info("pageNum：{}", pageNum);
        log.info("pageSize：{}", pageSize);
        log.info("map：{}", JSON.toJSON(map));
        TInvestPlanMainExample example = new TInvestPlanMainExample();
        TInvestPlanMainExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(investPlanMain.getPlanId())) {
            criteria.andPlanIdEqualTo(investPlanMain.getPlanId());
        }
        if (StringUtils.isNotEmpty(investPlanMain.getMemberId())) {
            criteria.andMemberIdEqualTo(investPlanMain.getMemberId());
        }
        if (StringUtils.isNotEmpty(investPlanMain.getOrderId())) {
            criteria.andOrderIdEqualTo(investPlanMain.getOrderId());
        }
        if (StringUtils.isNotEmpty(investPlanMain.getStatus())) {
            criteria.andStatusEqualTo(investPlanMain.getStatus());
        }
        if (null != investPlanMain.getId()) {
            criteria.andIdEqualTo(investPlanMain.getId());
        }
        if (null != map) {
            if ("1".equals(map.get("IS_RUNNING"))) {
                Date date = DateUtils.getSysDate();
                criteria.andStartTimeLessThanOrEqualTo(date);
                criteria.andEndTimeGreaterThanOrEqualTo(date);
            }
        }
        example.setOrderByClause(" create_time");
        PageInfo<TInvestPlanMain> pageInfo = PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(() -> this.investPlanMainMapper.selectByExample(example));
        log.info("获取粮仓执行计划主表列表结果：{}", JSON.toJSON(pageInfo));
        return pageInfo;
    }


    /**
     * 获取已购买计划&明细
     * @param investPlanMain
     * @param pageNum
     * @param pageSize
     * @param map
     * @return
     */
    @Override
    public PageInfo<TInvestPlanMainDZ> queryMainAndDetailList(TInvestPlanMain investPlanMain, Integer pageNum, Integer pageSize, Map<String, Object> map) {

        PageInfo<TInvestPlanMainDZ> planMainDZPageInfo = new PageInfo<>();
        List<TInvestPlanMainDZ> list = new ArrayList<>();
        PageInfo<TInvestPlanMain> mainPageInfo = queryList(investPlanMain, pageNum, pageSize, map);
        if (mainPageInfo != null && !CollectionUtils.isEmpty(mainPageInfo.getList())) {
            BeanUtils.copyProperties(mainPageInfo, planMainDZPageInfo);
            for (TInvestPlanMain tInvestPlanMain : mainPageInfo.getList()) {
                TInvestPlanMainDZ tInvestPlanMainDZ = new TInvestPlanMainDZ();
                BeanUtils.copyProperties(tInvestPlanMain, tInvestPlanMainDZ);
                TInvestPlanDetail tInvestPlanDetail = new TInvestPlanDetail();
                tInvestPlanDetail.setPlanMainId(String.valueOf(tInvestPlanMainDZ.getId()));
                List<TInvestPlanDetail> details = this.itInvestPlanDetailBusiSV.queryList(tInvestPlanDetail);
                String appendFlag = "0";
                for (TInvestPlanDetail tInvestPlanDetail1 : details) {
                    if (InvestConstants.Plan.PLAN_TYPE_MAIN.equals(tInvestPlanDetail1.getType()) && InvestConstants.Plan.DETAIL_STATUS_FINISH.equals(tInvestPlanDetail1.getStatus())) {
                        //主粮仓已完成
                        appendFlag = "1";
                    }
                }
                tInvestPlanMainDZ.setCanAppend(appendFlag);
                tInvestPlanMainDZ.settInvestPlanDetailList(details);
                list.add(tInvestPlanMainDZ);
            }
            planMainDZPageInfo.setList(list);
        }
        return planMainDZPageInfo;
    }

    @Override
    public List<TInvestPlanMain> queryList(TInvestPlanMainDZ investPlanMain) {
        log.info("获取粮仓计划实例列表参数");
        log.info("investPlanMain：{}", JSON.toJSON(investPlanMain));
        TInvestPlanMainExample example = new TInvestPlanMainExample();
        TInvestPlanMainExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(investPlanMain.getPlanId())) {
            criteria.andPlanIdEqualTo(investPlanMain.getPlanId());
        }
        if (StringUtils.isNotEmpty(investPlanMain.getMemberId())) {
            criteria.andMemberIdEqualTo(investPlanMain.getMemberId());
        }
        if (StringUtils.isNotEmpty(investPlanMain.getOrderId())) {
            criteria.andOrderIdEqualTo(investPlanMain.getOrderId());
        }
        if (StringUtils.isNotEmpty(investPlanMain.getStatus())) {
            criteria.andStatusEqualTo(investPlanMain.getStatus());
        }
        if (StringUtils.isNotEmpty(investPlanMain.getNotEqualSettleYYYYMMDD())) {
            criteria.andSettleYyyymmddNotEqualTo(investPlanMain.getNotEqualSettleYYYYMMDD());
        }
        if (null != investPlanMain.getId()) {
            criteria.andIdEqualTo(investPlanMain.getId());
        }

        if (null != investPlanMain.getRunTime()) {
            criteria.andStartTimeLessThanOrEqualTo(investPlanMain.getRunTime());
            criteria.andEndTimeGreaterThan(investPlanMain.getRunTime());
        }

        if (StringUtils.isNotEmpty(investPlanMain.getSort())) {
            example.setOrderByClause(investPlanMain.getSort());
        }


        List<TInvestPlanMain> pageInfo = this.investPlanMainMapper.selectByExample(example);
        log.info("获取粮仓执行计划主表列表结果：{}", JSON.toJSON(pageInfo));
        return pageInfo;
    }

    /**
     * 获取执行开始时间
     * @param memberId
     * @param planId
     * @return
     */
    private Date getPlanStartTime(String memberId, String planId){
        TInvestPlanMainDZ investPlanMain = new TInvestPlanMainDZ();
        investPlanMain.setMemberId(memberId);
        investPlanMain.setStatus(InvestConstants.Plan.MAIN_STATUS_ING);
        investPlanMain.setSort(" start_yyyymmdd desc");
        List<TInvestPlanMain> investPlanMainList = this.queryList(investPlanMain);
        if (CollectionUtils.isEmpty(investPlanMainList)){
            return  DateUtils.getDateZero(DateUtils.addDays(DateUtils.getSysDate(), 2));
        }else{
            //取开始时间最大值
            TInvestPlanMain tInvestPlanMain= investPlanMainList.get(0);
            //(存在未执行的计划，新计划自动顺延一天)最新计划开始时间YYYYMMDD>=sysdate.yyyymmdd-> 最新计划开始时间YYYYMMDD+1
            if (Long.parseLong(tInvestPlanMain.getStartYyyymmdd()) >= Long.parseLong(DateUtils.getYYYYMMDD(DateUtils.getSysDate()))) {
                return DateUtils.getDateZero(DateUtils.getNextDate(tInvestPlanMain.getStartTime()));
            }else{
                return DateUtils.getSysDate();
            }
        }
    }

    /**
     * 创建主计划（新建）
     * 数据来源（配置表）
     * @param investPlanMain
     * @return
     */
    @Override
    public TInvestPlanMainDZ createPlanMainNew(TInvestPlanMain investPlanMain) {
        log.info("创建主计划（新建）");
        log.info("数据来源（配置表）");
        if (investPlanMain == null) {
            log.info("创建计划入参不能为空");
            throw new ServiceException(ExceptionConstants.Param.NULL, "创建计划入参不能为空");
        }
        if (StringUtils.isEmpty(investPlanMain.getPlanId())) {
            log.info("创建计划入参[计划配置ID]不能为空");
            throw new ServiceException(ExceptionConstants.Param.FEILD_NULL, "创建计划入参[计划配置ID]不能为空");
        }
        //根据计划配置ID获取计划配置信息
        TInvestPlanConfig tInvestPlanConfig = new TInvestPlanConfig();
        tInvestPlanConfig.setPlanId(investPlanMain.getPlanId());
        tInvestPlanConfig = itInvestPlanConfigBusiSV.select(tInvestPlanConfig);
        if (tInvestPlanConfig == null) {
            log.info("计划配置ID获取不到计划配置信息");
            throw new ServiceException(ExceptionConstants.Param.RESULT_NULL, "计划配置ID获取不到计划配置信息");
        }
        Date sysDate = DateUtils.getSysDate();
        investPlanMain.setPlanName(tInvestPlanConfig.getPlanName());//计划名称
        investPlanMain.setStatus(InvestConstants.Plan.MAIN_STATUS_ING);//计划执行中
        investPlanMain.setStatusTime(sysDate);
        investPlanMain.setPlanAmount(tInvestPlanConfig.getPlanAmount());//计划总金额
        investPlanMain.setPlanDays(tInvestPlanConfig.getPlanDays());//计划总天数
        investPlanMain.setAppendFlay(InvestConstants.Plan.MAIN_APPEND_N);//未续仓
        investPlanMain.setSettleYyyymmdd("0");
        //取计划开始时间
        Date startTime = getPlanStartTime(investPlanMain.getMemberId(), investPlanMain.getPlanId());
        investPlanMain.setStartTime(startTime);
        investPlanMain.setStartYyyymmdd(DateUtils.getYYYYMMDD(startTime));
        investPlanMain.setEndTime(DateUtils.addDays(startTime, tInvestPlanConfig.getPlanDays()));
        investPlanMain.setInterestReturnPer(tInvestPlanConfig.getInterestReturnPer());
        /*
         * 1、未续仓流程
         *      实际金额和天数=计划配置表中数据
         */
        investPlanMain.setTotalAmount(investPlanMain.getPlanAmount());
        investPlanMain.setTotalDays(investPlanMain.getPlanDays());
        investPlanMain = this.insert(investPlanMain);
        log.info("插入计划分表");
        //插入计划分表
        long id = this.itInvestPlanDetailBusiSV.insertByMainNew(investPlanMain);

        //修改执行中冻结金额
        this.itWheatAccountBusiSV.modifyAcctRunInvest(
                investPlanMain.getMemberId(),
                tInvestPlanConfig.getPlanAmount());

        TInvestPlanMainDZ tInvestPlanMainDZ = new TInvestPlanMainDZ();
        BeanUtils.copyProperties(investPlanMain, tInvestPlanMainDZ);
        tInvestPlanMainDZ.setBonusTmpId(id);
        return tInvestPlanMainDZ;
    }

    /**
     * 创建主计划（续仓）
     * 数据来源：当前执行计划主表数据
     * @param investPlanMain
     * @return
     */
    @Override
    public TInvestPlanMainDZ createPlanMainSecond(TInvestPlanMain investPlanMain) {
        /*
         * 1、未续仓流程
         *      实际金额和天数=计划配置表中数据
         * 2、续仓流程
         *      实际金额和天数=计划金额（天数）- 副仓已返还金额（天数）
         */
        //续仓状态,0:未续仓，1:续仓   null未续仓
        log.info("创建主计划（续仓）");
        log.info("数据来源（当前执行计划主表数据）");
        if (investPlanMain == null) {
            log.info("创建计划入参不能为空");
            throw new ServiceException(ExceptionConstants.Param.NULL, "创建计划入参不能为空");
        }
        if (null == investPlanMain.getId()) {
            log.info("创建计划入参[ID]不能为空");
            throw new ServiceException(ExceptionConstants.Param.FEILD_NULL, "创建计划入参[ID]不能为空");
        }
        log.info("判断当前计划主表是否已完结，如果已完结，不允许续仓");
        TInvestPlanMain investPlanMainParam = new TInvestPlanMain();
        Long mainId = investPlanMain.getId();
        investPlanMainParam.setId(mainId);
        TInvestPlanMain investPlanMainOrig = this.select(investPlanMainParam);
        if (investPlanMainOrig == null) {
            log.info("未查询到当前正在执行的计划，计划ID["+mainId+"]");
            throw new ServiceException(ExceptionConstants.Param.FEILD_NULL, "未查询到当前正在执行的计划，计划ID["+mainId+"]");
        }
        if (!InvestConstants.Plan.DETAIL_STATUS_ING.equals(investPlanMainOrig.getStatus())) {
            log.info("续仓当前计划状态不是执行中，不允许续仓，计划ID["+mainId+"]");
            throw new ServiceException(ExceptionConstants.Param.FEILD_NULL, "续仓当前计划状态不是执行中，不允许续仓，计划ID["+mainId+"]");
        }

        //要复投计划是否为执行中最高计划
        //查询执行中最大计划
        TInvestPlanDetailDZ maxDz = this.getMaxRuningPlan(investPlanMainOrig.getMemberId());
        if (null != maxDz && Integer.parseInt(maxDz.getPlanId()) > Integer.parseInt(investPlanMainOrig.getPlanId())) {
            log.info("存在更高级别的计划，不允许复投当前计划");
            throw new ServiceException(ExceptionConstants.Param.FEILD_NULL, "存在更高级别的计划，不允许复投当前计划");
        }

        //校验是否可以续仓并返回副仓结果
        TInvestPlanDetail tInvestPlanDetailTmp = checkCanSecondAndReturnDetail(investPlanMain);
        int days = 0;
        long amount = 0;
        //实际金额(天数)=计划金额（天数） + 计划金额（天数）- 副仓已返还金额（天数）
        days = tInvestPlanDetailTmp.getPlanDays() + tInvestPlanDetailTmp.getPlanDays() - tInvestPlanDetailTmp.getReturnDays();
        amount = tInvestPlanDetailTmp.getPlanAmount() + tInvestPlanDetailTmp.getPlanAmount() - tInvestPlanDetailTmp.getRetrunAmount();


        log.info("插入新计划主表");
        TInvestPlanMain investPlanMainNew = new TInvestPlanMain();
        BeanUtils.copyProperties(investPlanMainOrig, investPlanMainNew);
        investPlanMainNew.setId(null);
        investPlanMainNew.setTotalDays(days);//实际天数
        investPlanMainNew.setTotalAmount(amount);//实际金额
        investPlanMainNew.setSettleYyyymmdd("0");
        //取计划开始时间
        Date startTime = getPlanStartTime(investPlanMainNew.getMemberId(), investPlanMainNew.getPlanId());
        investPlanMainNew.setStartTime(startTime);
        investPlanMainNew.setStartYyyymmdd(DateUtils.getYYYYMMDD(startTime));
        investPlanMainNew.setEndTime(DateUtils.addDays(startTime, investPlanMainNew.getPlanDays()));
        investPlanMainNew.setAppendFlay(InvestConstants.Plan.MAIN_APPEND_Y);//复投
        this.insert(investPlanMainNew);

        log.info("修改原计划主表状态等信息");
        Date sysDate = DateUtils.getSysDate();
        investPlanMainOrig.setStatus(InvestConstants.Plan.MAIN_STATUS_FINISH);//原计划已完结（续仓后，原计划完结）
        investPlanMainOrig.setStatusTime(sysDate);
        investPlanMainOrig.setBeAppendFlay(InvestConstants.Plan.MAIN_APPEND_Y);//原计划已续仓
        this.update(investPlanMainOrig);

        log.info("开始处理分计划");
        long id = this.itInvestPlanDetailBusiSV.insertByMainSecond(investPlanMainOrig, investPlanMainNew);

        //修改执行中冻结金额
        this.itWheatAccountBusiSV.modifyAcctRunInvest(
                investPlanMain.getMemberId(),
                tInvestPlanDetailTmp.getPlanAmount());

        TInvestPlanMainDZ tInvestPlanMainDZ = new TInvestPlanMainDZ();
        BeanUtils.copyProperties(investPlanMain, tInvestPlanMainDZ);
        tInvestPlanMainDZ.setBonusTmpId(id);

        return tInvestPlanMainDZ;
    }

    /**
     * 校验是否可以续仓并返回副仓结果
     *
     * @param investPlanMain
     * @return
     */
    @Override
    public TInvestPlanDetail checkCanSecondAndReturnDetail(TInvestPlanMain investPlanMain) {
        log.info("校验是否可以续仓并返回副仓结果,入参{}", JSON.toJSON(investPlanMain).toString());
        Long mainId = investPlanMain.getId();

        log.info("查询当前计划的执行中分仓");
        //查询当前计划的执行中分副仓
        TInvestPlanDetail investPlanDetailParam = new TInvestPlanDetail();
        investPlanDetailParam.setPlanMainId(investPlanMain.getId()+"");
        PageInfo<TInvestPlanDetail> planDetailPageInfo = this.itInvestPlanDetailBusiSV.queryList(investPlanDetailParam, 1, 100, null);
        if (planDetailPageInfo == null || planDetailPageInfo.getList() == null || planDetailPageInfo.getList().isEmpty()) {
            log.info("未查询到分仓结果，计划ID["+mainId+"]");
            throw new ServiceException(ExceptionConstants.Param.NULL, "未查询到分仓结果，计划ID["+mainId+"]");
        }

        //分别判断主仓和副仓状态
        for(TInvestPlanDetail tInvestPlanDetailTmp : planDetailPageInfo.getList()) {
            //主仓
            if (InvestConstants.Plan.PLAN_TYPE_MAIN.equals(tInvestPlanDetailTmp.getType())) {
                if (InvestConstants.Plan.MAIN_STATUS_ING.equals(tInvestPlanDetailTmp.getStatus())){
                    //主仓正在执行中，不允许续仓
                    log.info("主仓正在执行中，不允许续仓");
                    throw new ServiceException(ExceptionConstants.Result.FEILD_NOT_MACTH, "主仓正在执行中，不允许续仓");
                }
                continue;
            }
            //副仓
            if (InvestConstants.Plan.PLAN_TYPE_SECOND.equals(tInvestPlanDetailTmp.getType())) {
                if (!InvestConstants.Plan.DETAIL_STATUS_ING.equals(tInvestPlanDetailTmp.getStatus())){
                    //副仓不在执行中，不允许续仓
                    log.info("副仓不在执行中，不允许续仓");
                    throw new ServiceException(ExceptionConstants.Result.FEILD_NOT_MACTH, "副仓不在执行中，不允许续仓");
                }
                return tInvestPlanDetailTmp;
            }
        }
        return null;
    }

    /**
     * 获取执行中计划列表，逐条调用结算方法
     *
     * @return
     */
    @Override
    public void runSettle() {
//        TInvestPlanMainDZ investPlanMain = new TInvestPlanMainDZ();
//        investPlanMain.setStatus(InvestConstants.Plan.MAIN_STATUS_ING);
//        if ("1".equals(InvestConstants.SysParam.oneTimeOneDay)) {
//            investPlanMain.setNotEqualSettleYYYYMMDD(DateUtils.getYYYYMMDD(DateUtils.getSysDate()));
//        }
//        List<TInvestPlanMain> tInvestPlanMainList = this.queryList(investPlanMain);
//        if (tInvestPlanMainList != null){
//            long planCount = 0L;
//            for (TInvestPlanMain tInvestPlanMain : tInvestPlanMainList) {
//                settleExc(tInvestPlanMain);
//                planCount++;
//            }
//            //记录结算计划
//            this.itInvestSettleRecordBusiSV.insert2(planCount);
//        }
    }

    /**
     * 获取执行中计划列表，逐条调用结算方法V2
     *
     * @return
     */
    @Override
    public TInvestSettleRecord runSettleV2() {
        log.info("==================================================开始结算===========================================");
        //查询今天是否有正在执行的结算进程，如果存在，则不允许重复执行
        TInvestSettleRecordDZ tInvestSettleRecord = new TInvestSettleRecordDZ();
        tInvestSettleRecord.setSettleday(DateUtils.getYYYYMMDD(DateUtils.getSysDate()));
        List<TInvestSettleRecord> tInvestSettleRecordList = this.itInvestSettleRecordBusiSV.queryList(tInvestSettleRecord);
        if (!CollectionUtils.isEmpty(tInvestSettleRecordList)) {
            for (TInvestSettleRecord tmp : tInvestSettleRecordList) {
                if (tmp.getPlanCount() == null) {
                    throw new ServiceException(ExceptionConstants.Business.CHECK_ERR, "当前存在正在执行中的结算进程，不允许重复操作");
                }
            }
        }
        //记录结算计划
        TInvestSettleRecord tInvestSettleRecord1 = this.itInvestSettleRecordBusiSV.insert2(null);
        TInvestPlanMainDZ investPlanMain = new TInvestPlanMainDZ();
        investPlanMain.setStatus(InvestConstants.Plan.MAIN_STATUS_ING);
        investPlanMain.setRunTime(DateUtils.getSysDate());
        if ("1".equals(InvestConstants.SysParam.oneTimeOneDay)) {
            investPlanMain.setNotEqualSettleYYYYMMDD(DateUtils.getYYYYMMDD(DateUtils.getSysDate()));
        }
        long planCount = 0L;
        try {
            List<TInvestPlanMain> tInvestPlanMainList = this.queryList(investPlanMain);
            if (tInvestPlanMainList != null) {
                for (TInvestPlanMain tInvestPlanMain : tInvestPlanMainList) {
                    settleExc(tInvestPlanMain,tInvestSettleRecord1.getId());
                    planCount++;
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            //记录结算计划
            tInvestSettleRecord1.setPlanCount(planCount);
            this.itInvestSettleRecordBusiSV.update(tInvestSettleRecord1);
        }
        return tInvestSettleRecord1;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void settleExc(TInvestPlanMain tInvestPlanMain, Long settleRecordId){
        try {
            TInvestPlanMainDZ tInvestPlanMainDZ = itInvestPlanMainBusiSV.settle(tInvestPlanMain);
            //自动复投
            if (tInvestPlanMainDZ != null) {
                this.itInvestPlanMainBusiSV.autoBuy(tInvestPlanMainDZ);
            }
            TInvestPlanMainLog tInvestPlanMainLog = new TInvestPlanMainLog();
            tInvestPlanMainLog.setPlanMainId(String.valueOf(tInvestPlanMain.getId()));
            tInvestPlanMainLog.setRemark("执行成功");
            tInvestPlanMainLog.setStatus("1");
            tInvestPlanMainLog.setYyyymmdd(DateUtils.getYYYYMMDD(DateUtils.getSysDate()));
            tInvestPlanMainLog.setType(InvestConstants.Plan.PLAN_LOG_TYPE_SETTLE);//结算日志
            tInvestPlanMainLog.setSettleRecordId(settleRecordId);
            itInvestPlanMainLogSV.insert(tInvestPlanMainLog);
        }catch (Exception e) {
            e.printStackTrace();
            TInvestPlanMainLog tInvestPlanMainLog = new TInvestPlanMainLog();
            tInvestPlanMainLog.setPlanMainId(String.valueOf(tInvestPlanMain.getId()));
            tInvestPlanMainLog.setRemark(e.getMessage());
            tInvestPlanMainLog.setStatus("2");
            tInvestPlanMainLog.setYyyymmdd(DateUtils.getYYYYMMDD(DateUtils.getSysDate()));
            tInvestPlanMainLog.setType(InvestConstants.Plan.PLAN_LOG_TYPE_SETTLE);//结算日志
            tInvestPlanMainLog.setSettleRecordId(settleRecordId);
            itInvestPlanMainLogSV.insert(tInvestPlanMainLog);
        }
    }


    /**
     * 结算方法
     *
     * @param tInvestPlanMain
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public TInvestPlanMainDZ settle(TInvestPlanMain tInvestPlanMain) {
        /*
            插入【粮仓计划实例明细表】：主要字段：主键，粮仓实例表主键，粮仓实例ID，主副仓标识，返还金额，支付利息，返还时间
            插入【奖金计算临时表】插入记录：主键，用户ID，金额，类型（结算利息），日期，插入时间
            解冻【账户表】中相应钱数：主要字段：冻结金额，总金额
            插入【账户变更记录】：变更时间，变更类型（冻结，存入，提取等），涉及金额
            修改【粮仓计划实例主表】: 修改内容：状态，状态变更时间，已返还金额，已返还期数，已支付利息
            修改【粮仓计划实例分表】: 修改内容：状态，状态变更时间，已返还金额，已返还期数，已支付利息
        */
        //1、查询粮仓子计划实例(执行中)
        log.info("查询粮仓子计划实例(执行中),计划主表ID={}",tInvestPlanMain.getId());
        TInvestPlanDetail investPlanDetailParam = new TInvestPlanDetail();
        investPlanDetailParam.setPlanMainId(String.valueOf(tInvestPlanMain.getId()));
        investPlanDetailParam.setStatus(InvestConstants.Plan.DETAIL_STATUS_ING);
        PageInfo<TInvestPlanDetail> tInvestPlanDetailPageInfo = this.itInvestPlanDetailBusiSV.queryList(investPlanDetailParam,1, 10, null);
        if (tInvestPlanDetailPageInfo == null || tInvestPlanDetailPageInfo.getList() == null || tInvestPlanDetailPageInfo.getList().isEmpty()) {
            log.info("不存在执行中粮仓子计划实例");
            throw new ServiceException(ExceptionConstants.Business.CHECK_ERR, "不存在执行中粮仓子计划实例");
        }

        TInvestPlanDetail tInvestPlanDetail = null;

        if (tInvestPlanDetailPageInfo.getList().size() > 1) {
            log.info("执行中的子计划数量大于1条, 取主仓计划");
            for (TInvestPlanDetail tInvestPlanDetailTmp : tInvestPlanDetailPageInfo.getList()) {
                if (InvestConstants.Plan.PLAN_TYPE_MAIN.equals(tInvestPlanDetailTmp.getType())) {
                    tInvestPlanDetail = tInvestPlanDetailTmp;
                }
            }
        } else {
            tInvestPlanDetail = tInvestPlanDetailPageInfo.getList().get(0);
        }

        if (tInvestPlanDetail == null) {
            log.info("获取执行中子计划失败，失败原因可能时多个执行中子计划，但无主仓计划");
            throw new ServiceException(ExceptionConstants.Business.CHECK_ERR, "获取执行中子计划失败，失败原因可能时多个执行中子计划，但无主仓计划");
        }

        //每天返还金额 = 计划金额 / 计划天数
        Long returnAmount = tInvestPlanDetail.getPlanAmount() / tInvestPlanDetail.getPlanDays();
        /*
         * 主仓计算利息 = 计划金额 * 0.01
         */
        Long interest = 0L;
        if (InvestConstants.Plan.PLAN_TYPE_MAIN.equals(tInvestPlanDetail.getType())) {
            interest = (long)(tInvestPlanDetail.getPlanAmount() * tInvestPlanDetail.getInterestReturnPer() / 100);
        }

        log.info("插入【粮仓计划实例明细表】子计划ID={}",tInvestPlanDetail.getId());
        TInvestPlanRunDetail tInvestPlanRunDetail = new TInvestPlanRunDetail();
        tInvestPlanRunDetail.setPlanDetailId(String.valueOf(tInvestPlanDetail.getId()));
        tInvestPlanRunDetail.setRetrunAmount(returnAmount);
        tInvestPlanRunDetail.setType(tInvestPlanDetail.getType());//子仓库类型
        tInvestPlanRunDetail.setRetrunInterest(interest);//利息
        itInvestPlanRunDetailBusiSV.insert(tInvestPlanRunDetail);

        /*
            解冻【账户表】中相应钱数：主要字段：冻结金额，总金额
            插入【账户变更记录】：变更时间，变更类型（冻结，存入，提取等），涉及金额
        */
        log.info("修改账户信息");
        log.info("解冻粮仓金额");
        this.itWheatAccountBusiSV.modifyAcct(
                tInvestPlanMain.getMemberId(),
                returnAmount,
                InvestConstants.Account.DETAIL_OPERTYPE_UNFREEZE,
                InvestConstants.Account.DETAIL_TYPE_GRANARY,
                InvestConstants.Account.DETAIL_SUBTYPE_BUY,
                "执行粮仓计划，返还本金");
        if (interest > 0) {
            log.info("结算利息");
            this.itWheatAccountBusiSV.modifyAcct(
                    tInvestPlanMain.getMemberId(),
                    interest,
                    InvestConstants.Account.DETAIL_OPERTYPE_INTEREST,
                    InvestConstants.Account.DETAIL_TYPE_GRANARY,
                    InvestConstants.Account.DETAIL_SUBTYPE_BUY,
                    "执行粮仓计划，利息");
            //插入奖金记录表
            TInvestPlanBonus tInvestPlanBonus = new TInvestPlanBonus();
            tInvestPlanBonus.setMemberId(tInvestPlanMain.getMemberId());
            tInvestPlanBonus.setFromMemberId(tInvestPlanMain.getMemberId());
            tInvestPlanBonus.setMemberLevel("0");
            tInvestPlanBonus.setAmount(interest);
            tInvestPlanBonus.setType(InvestConstants.Bonus.TYPE_INTEREST);//利息
            tInvestPlanBonus.setPlanMainId(tInvestPlanMain.getId()+"");
            tInvestPlanBonus.setOrderId(tInvestPlanMain.getOrderId());
            tInvestPlanBonus.setSettleFlag(InvestConstants.Bonus.BONUS_SETTLE_FLAG_FINISH);
            tInvestPlanBonus.setSettleTime(DateUtils.getSysDate());
            this.itInvestPlanBonusBusiSV.insert(tInvestPlanBonus);
        }

        //修改【粮仓计划实例分表】: 修改内容：状态，状态变更时间，已返还金额，已返还期数，已支付利息
        log.info("修改【粮仓计划实例分表】");
        boolean mainFinishFlag = false;
        boolean continueFlag = false;
        tInvestPlanDetail.setRetrunInterest(tInvestPlanDetail.getRetrunInterest() + interest);//返回利息
        tInvestPlanDetail.setReturnDays(tInvestPlanDetail.getReturnDays()+1);//返还天数
        tInvestPlanDetail.setRetrunAmount(tInvestPlanDetail.getRetrunAmount()+returnAmount);//返还金额
        if (tInvestPlanDetail.getTotalDays() == tInvestPlanDetail.getReturnDays()) {
            tInvestPlanDetail.setStatus(InvestConstants.Plan.DETAIL_STATUS_FINISH);//完结
            tInvestPlanDetail.setStatusTime(DateUtils.getSysDate());
            this.itInvestPlanDetailBusiSV.update(tInvestPlanDetail);
            //副仓计划已完结，说明计划全部执行完成
            if (InvestConstants.Plan.PLAN_TYPE_SECOND.equals(tInvestPlanDetail.getType())) {
                mainFinishFlag = true;
            }
        }else{
            this.itInvestPlanDetailBusiSV.update(tInvestPlanDetail);
        }


        //修改【粮仓计划实例主表】: 修改内容：状态，状态变更时间
        log.info("修改【粮仓计划实例主表】");
        TInvestPlanMain tInvestPlanMainModify = new TInvestPlanMain();
        BeanUtils.copyProperties(tInvestPlanMain, tInvestPlanMainModify);
        tInvestPlanMainModify.setStatus(mainFinishFlag ? InvestConstants.Plan.MAIN_STATUS_FINISH : tInvestPlanMainModify.getStatus());
        tInvestPlanMainModify.setStatusTime(mainFinishFlag ? DateUtils.getSysDate() : null);
        tInvestPlanMainModify.setSettleYyyymmdd(DateUtils.getYYYYMMDD(DateUtils.getSysDate()));
        this.update(tInvestPlanMainModify);

        TInvestPlanMainDZ tInvestPlanMainDZ = new TInvestPlanMainDZ();
        BeanUtils.copyProperties(tInvestPlanMain, tInvestPlanMainDZ);
        List<TInvestPlanDetail> tInvestPlanDetails = new ArrayList<>();
        tInvestPlanDetails.add(tInvestPlanDetail);
        tInvestPlanMainDZ.settInvestPlanDetailList(tInvestPlanDetails);

        return tInvestPlanMainDZ;
    }

    /**
     * 自动复投
     * @param tInvestPlanMainDZ
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void autoBuy(TInvestPlanMainDZ tInvestPlanMainDZ){

        TInvestPlanDetail tInvestPlanDetail = tInvestPlanMainDZ.gettInvestPlanDetailList().get(0);

        //主仓计划已完结，判断是否需要自动续仓
        if (InvestConstants.Plan.PLAN_TYPE_MAIN.equals(tInvestPlanDetail.getType())) {
            String autoFlag = itInvestMemberConfigBusiSV.selectByMemberId(tInvestPlanMainDZ.getMemberId(), InvestConstants.MemberConfig.CONFIG_CODE_AUTO_SECOND);
            if("1".equals(autoFlag)){
                try {
                    log.info("自动复投");
                    //自动副仓
                    TInvestQueue tInvestQueue = new TInvestQueue();
                    tInvestQueue.setOrigPlanMainId(String.valueOf(tInvestPlanMainDZ.getId()));
                    tInvestQueue.setType(InvestConstants.Queue.TYPE_APPEND);//续仓
                    TInvestQueueDZ tInvestQueueResp = this.itInvestQueueBusiSV.createQueueV2AndRun(tInvestQueue);
                    //异步计算奖金
                    if (tInvestQueueResp != null && tInvestQueueResp.getBonusTmpId() > 0) {
                        new Thread(() -> this.itInvestPlanBonusBusiSV.calculateStartV2(tInvestQueueResp.getBonusTmpId())).start();
                    }
                    TInvestPlanMainLog tInvestPlanMainLog = new TInvestPlanMainLog();
                    tInvestPlanMainLog.setPlanMainId(String.valueOf(tInvestPlanMainDZ.getId()));
                    tInvestPlanMainLog.setRemark("自动续仓排队成功");
                    tInvestPlanMainLog.setStatus("1");
                    tInvestPlanMainLog.setYyyymmdd(DateUtils.getYYYYMMDD(DateUtils.getSysDate()));
                    tInvestPlanMainLog.setType(InvestConstants.Plan.PLAN_LOG_TYPE_AUTO);//自动续仓日志
                    tInvestPlanMainLog.setSettleRecordId(0L);
                    itInvestPlanMainLogSV.insert(tInvestPlanMainLog);

                }catch (ServiceException e) {
                    e.printStackTrace();
//                    if (ExceptionConstants.Business.CHECK_ERR.equals(e.getCode())) {
//                        TInvestPlanMainLog tInvestPlanMainLog = new TInvestPlanMainLog();
//                        tInvestPlanMainLog.setPlanMainId(String.valueOf(tInvestPlanMainDZ.getId()));
//                        tInvestPlanMainLog.setRemark("自动续仓排队失败：" + e.getMessage());
//                        tInvestPlanMainLog.setStatus("2");
//                        tInvestPlanMainLog.setYyyymmdd(DateUtils.getYYYYMMDD(DateUtils.getSysDate()));
//                        tInvestPlanMainLog.setType(InvestConstants.Plan.PLAN_LOG_TYPE_AUTO);//自动续仓日志
//                        tInvestPlanMainLog.setSettleRecordId(0L);
//                        itInvestPlanMainLogSV.insert(tInvestPlanMainLog);
//                    }
                }catch (Exception e) {
                    e.printStackTrace();
//                    TInvestPlanMainLog tInvestPlanMainLog = new TInvestPlanMainLog();
////                    tInvestPlanMainLog.setPlanMainId(String.valueOf(tInvestPlanMainDZ.getId()));
////                    tInvestPlanMainLog.setRemark("自动续仓排队失败：" + e.getMessage());
////                    tInvestPlanMainLog.setStatus("2");
////                    tInvestPlanMainLog.setYyyymmdd(DateUtils.getYYYYMMDD(DateUtils.getSysDate()));
////                    tInvestPlanMainLog.setType(InvestConstants.Plan.PLAN_LOG_TYPE_AUTO);//自动续仓日志
////                    tInvestPlanMainLog.setSettleRecordId(0L);
////                    itInvestPlanMainLogSV.insert(tInvestPlanMainLog);
                }

            }
        }
    }

    /**
     * 获取该会员可以购买计划的列表
     *
     * @param tWheatMember
     * @return
     */
//    @Override
//    public List<TInvestPlanConfig> getCanBuyPlanConfigList(TWheatMember tWheatMember) {
//
//        //查询该会员正在执行主仓列表
//        TInvestPlanDetail tInvestPlanDetail = new TInvestPlanDetail();
//        tInvestPlanDetail.setMemberId(tWheatMember.getMemberId());
//        tInvestPlanDetail.setStatus(InvestConstants.Plan.DETAIL_STATUS_ING);
//        tInvestPlanDetail.setType(InvestConstants.Plan.PLAN_TYPE_MAIN);
//        List<TInvestPlanDetail> tInvestPlanDetailList = this.itInvestPlanDetailBusiSV.queryList(tInvestPlanDetail);
//
//        List<TInvestPlanConfig> tInvestPlanConfigList = new ArrayList<>();
//
//        if (tInvestPlanDetailList == null || tInvestPlanDetailList.isEmpty()) {
//            //当前没有执行计划，可以购买任意计划
//            //获取计划列表
//            tInvestPlanConfigList = this.itInvestPlanConfigBusiSV.queryList(new TInvestPlanConfig());
//            return tInvestPlanConfigList;
//        } else {
//            String planId = "";
//            int count = 0;
//            for (TInvestPlanDetail tInvestPlanDetailTmp : tInvestPlanDetailList) {
//                count++;
//                if (StringUtils.isEmpty(planId)) {
//                    planId = tInvestPlanDetailTmp.getPlanId();
//                }else if (!planId.equals(tInvestPlanDetailTmp.getPlanId())) {
//                    log.info("执行中主仓存在多个计划["+planId+","+tInvestPlanDetailTmp.getPlanId()+"]");
//                    throw new ServiceException(ExceptionConstants.Business.CHECK_ERR, "执行中主仓存在多个计划，等待计划完成");
//                }
//            }
//            TInvestPlanConfig tInvestPlanConfig = this.itInvestPlanConfigBusiSV.selectById(planId);
//
//            if (InvestConstants.SysParam.PLAN_MAX_ING_TYPE.equals("1")) {
//                //校验计划主表执行中计划
//                log.info("校验计划主表执行中计划");
//                TInvestPlanMainDZ tInvestPlanMainDZ = new TInvestPlanMainDZ();
//                tInvestPlanMainDZ.setStatus(InvestConstants.Plan.MAIN_STATUS_ING);//执行中
//                tInvestPlanMainDZ.setPlanId(planId);
//                tInvestPlanMainDZ.setMemberId(tWheatMember.getMemberId());
//                List<TInvestPlanMain> tInvestPlanMainList = this.queryList(tInvestPlanMainDZ);
//                if (tInvestPlanMainList == null || tInvestPlanMainList.isEmpty()) {
//                    count = 0;
//                }else{
//                    count = tInvestPlanMainList.size();
//                }
//                log.info("计划主表执行中计划数量：{}",count);
//            }else{
//                log.info("执行中主仓数量:{}",count);
//            }
//            if (count >= tInvestPlanConfig.getMaxCount()) {
//                log.info("执行中主仓数量已达上限");
//                throw new ServiceException(ExceptionConstants.Business.CHECK_ERR, "执行中主仓数量已达上限，不允许购买");
//            }
//            tInvestPlanConfigList.add(tInvestPlanConfig);
//        }
//        return tInvestPlanConfigList;
//    }

    /**
     * 获取该会员可以购买计划的列表
     * 购买了高级别计划，不允许买低级别计划
     *
     * @param tWheatMember
     * @return
     */
    @Override
    public List<TInvestPlanConfig> getCanBuyPlanConfigListV2(TWheatMember tWheatMember) {
        //查询执行中最大计划
        TInvestPlanDetailDZ maxDz = this.getMaxRuningPlan(tWheatMember.getMemberId());

        List<TInvestPlanConfig> tInvestPlanConfigList = new ArrayList<>();

        if (maxDz == null) {
            //当前没有执行计划，可以购买任意计划
            //获取计划列表
            tInvestPlanConfigList = this.itInvestPlanConfigBusiSV.queryList(new TInvestPlanConfig());
            return tInvestPlanConfigList;
        } else {
            //判断最大计划是否到达上限
            TInvestPlanConfig tInvestPlanConfig = this.itInvestPlanConfigBusiSV.selectById(maxDz.getPlanId());
            if (maxDz.getCount() < tInvestPlanConfig.getMaxCount()) {
                tInvestPlanConfigList.add(tInvestPlanConfig);
            }

            List<TInvestPlanConfig> largeList = this.itInvestPlanConfigBusiSV.queryList(new TInvestPlanConfig(), maxDz.getPlanId());
            if (!CollectionUtils.isEmpty(largeList)) {
                tInvestPlanConfigList.addAll(largeList);
            }
        }
        return tInvestPlanConfigList;
    }

    /**
     * 校验该会员是否可以购买该计划
     *
     * @param memberId
     * @param planId
     * @return
     */
    @Override
    public TInvestPlanConfig checkCanBuyPlan(String memberId, String planId) {
        TWheatMember tWheatMember = new TWheatMember();
        tWheatMember.setMemberId(memberId);
        List<TInvestPlanConfig> tInvestPlanConfigList = getCanBuyPlanConfigListV2(tWheatMember);
        if (tInvestPlanConfigList == null || tInvestPlanConfigList.isEmpty()) {
            log.info("校验该会员是否可以购买该计划:{}","不允许购买");
            //不允许购买
            return null;
        }
        for (TInvestPlanConfig tInvestPlanConfig : tInvestPlanConfigList) {
            if (planId.equals(tInvestPlanConfig.getPlanId())){
                //允许购买
                log.info("校验该会员是否可以购买该计划:{}","允许购买");
                return tInvestPlanConfig;
            }
        }
        return null;
    }

    /**
     * 执行中最大计划
     * @param memberId
     * @return
     */
    @Override
    public TInvestPlanDetailDZ getMaxRuningPlan(String memberId) {
        TInvestPlanDetailDZ tInvestPlanDetail = new TInvestPlanDetailDZ();
        tInvestPlanDetail.setMemberId(memberId);
        List<TInvestPlanDetailDZ> tInvestPlanDetailList = this.itInvestPlanDetailBusiSV.getMainRunningPlanCount(tInvestPlanDetail);
        if (!CollectionUtils.isEmpty(tInvestPlanDetailList)) {
            //取最大计划
            Collections.sort(tInvestPlanDetailList, new Comparator<TInvestPlanDetailDZ>(){
                public int compare(TInvestPlanDetailDZ o1, TInvestPlanDetailDZ o2) {
                    return (o2.getPlanId().compareTo(o1.getPlanId()));
                }
            });

            return tInvestPlanDetailList.get(0);
        }
        return null;
    }

    /**
     * 校验是否存在其他计划的执行中主仓数据
     * 续仓使用
     * @param memberId
     * @param planId
     * @return
     */
    @Override
    public boolean checkOtherPlanIng(String memberId, String planId) {
        //查询该会员正在执行主仓列表
        log.info("校验是否存在其他计划的执行中主仓数据:{}","查询该会员正在执行主仓列表");
        TInvestPlanDetail tInvestPlanDetail = new TInvestPlanDetail();
        tInvestPlanDetail.setMemberId(memberId);
        tInvestPlanDetail.setStatus(InvestConstants.Plan.DETAIL_STATUS_ING);
        tInvestPlanDetail.setType(InvestConstants.Plan.PLAN_TYPE_MAIN);
        List<TInvestPlanDetail> tInvestPlanDetailList = this.itInvestPlanDetailBusiSV.queryList(tInvestPlanDetail);

        if (tInvestPlanDetailList == null || tInvestPlanDetailList.isEmpty()) {
            //当前没有执行计划，可以续仓
            log.info("校验是否存在其他计划的执行中主仓数据:{}","当前没有执行计划，可以续仓");
            return true;
        } else {
            for (TInvestPlanDetail tInvestPlanDetailTmp : tInvestPlanDetailList) {
                if (!planId.equals(tInvestPlanDetailTmp.getPlanId())) {
                    log.info("存在执行中其他主仓");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 统计会员购买粮仓情况 执行中，所有，投入中
     *
     * @param memberId
     * @return
     */
    @Override
    public TInvestPlanMainStatistic statisticPlanCount(String memberId) {
        //执行中
        TInvestPlanMain tInvestPlanMain = new TInvestPlanMain();
        tInvestPlanMain.setMemberId(memberId);
        tInvestPlanMain.setStatus(InvestConstants.Plan.MAIN_STATUS_ING);
        Map<String, Object> map = new HashMap<>();
        map.put("IS_RUNNING", "1");
        PageInfo<TInvestPlanMain> pageInfo = this.queryList(tInvestPlanMain, 1, 1, map);
        long runningCount = (pageInfo != null? pageInfo.getTotal() : 0);
        pageInfo = this.queryList(tInvestPlanMain, 1, 1, null);
        long buyCount =  (pageInfo != null? pageInfo.getTotal() : 0);
        tInvestPlanMain.setStatus(null);
        pageInfo = this.queryList(tInvestPlanMain, 1, 1, null);
        long allCount =  (pageInfo != null? pageInfo.getTotal() : 0);
        TInvestPlanMainStatistic tInvestPlanMainStatistic = new TInvestPlanMainStatistic();
        tInvestPlanMainStatistic.setAllCount(allCount);
        tInvestPlanMainStatistic.setBuyCount(buyCount);
        tInvestPlanMainStatistic.setRunningCount(runningCount);
        return tInvestPlanMainStatistic;
    }

    /**
     * 统计粮仓数量和复投数量
     *
     * @param tInvestPlanMainStatistic
     * @return
     */
    @Override
    public TInvestPlanMainStatistic statisticPlanCount2(TInvestPlanMainStatistic tInvestPlanMainStatistic) {
        if (tInvestPlanMainStatistic == null || CollectionUtils.isEmpty(tInvestPlanMainStatistic.getMemberList())){
            TInvestPlanMainStatistic result = new TInvestPlanMainStatistic();
            result.setAllCount(0l);
            result.setAppendCount(0);
            return result;
        }
        return this.investPlanMainMapperDZ.statisticPlanCount(tInvestPlanMainStatistic);
    }

    /**
     * 统计会员的所有粮仓和当前粮仓数量
     *
     * @param tInvestPlanMainStatistic
     * @return
     */
    @Override
    public List<TInvestPlanMainStatistic> statisticPlanCountGroupByMemeber(TInvestPlanMainStatistic tInvestPlanMainStatistic) {
        return this.investPlanMainMapperDZ.statisticPlanCountGroupByMemeber(tInvestPlanMainStatistic);
    }

    /**
     * 获取队列并生成计划
     *
     * @return
     */
    @Override
    public TInvestPlanMain createPlan(int size) {

        log.info("获取队列并生成计划");

        List<TInvestQueue> queueList = itInvestQueueBusiSV.queryQueueToPlanList(size);
        if (queueList != null) {
            for (TInvestQueue tInvestQueue : queueList) {
                createPlan(tInvestQueue);
             }
        } else {
            log.info("粮仓队列为空");
        }

        return null;
    }

    /**
     * 获取队列并生成计划
     *
     * @return
     */
    @Override
    public TInvestPlanMainDZ createPlan(TInvestQueue tInvestQueue) {
        log.info("指定队列生成计划{}", tInvestQueue);
        String runResult = "0"; //成功
        String runMsg = ""; //成功
        TInvestPlanMainDZ tInvestPlanMainDZ = new TInvestPlanMainDZ();
        try {
            TInvestPlanMain investPlanMain = new TInvestPlanMain();
            investPlanMain.setPlanId(tInvestQueue.getPlanId());//计划配置ID
            investPlanMain.setMemberId(tInvestQueue.getMemberId());//会员ID
            if (InvestConstants.Queue.TYPE_NOAPPEND.equals(tInvestQueue.getType())) {
                //不续仓
                investPlanMain.setOrderId(String.valueOf(tInvestQueue.getOrderId()));//计划实例ID
                investPlanMain.setAppendFlay(InvestConstants.Plan.MAIN_APPEND_N);
                tInvestPlanMainDZ = this.createPlanMainNew(investPlanMain);
            }
            if (InvestConstants.Queue.TYPE_APPEND.equals(tInvestQueue.getType())) {
                //续仓
                investPlanMain.setId(Long.parseLong(tInvestQueue.getOrigPlanMainId()));//原计划实例ID
                investPlanMain.setAppendFlay(InvestConstants.Plan.MAIN_APPEND_Y);
                tInvestPlanMainDZ = this.createPlanMainSecond(investPlanMain);
            }
            //执行成功，修改队列状态和时间
            tInvestQueue.setStatus(InvestConstants.Queue.QUEUE_STATUS_BUY);
            tInvestQueue.setOrderTime(DateUtils.getSysDate());
            tInvestQueue.setRemark("计划购买成功");



        } catch (Exception e) {
            e.printStackTrace();
            //创建计划失败，解冻账户
            runResult = "1"; //失败
            runMsg = e.getMessage();

        } finally {
            if (!"0".equals(runResult)) {
                //执行失败，修改队列状态和时间
                tInvestQueue.setStatus(InvestConstants.Queue.QUEUE_STATUS_ERR);
                tInvestQueue.setOrderTime(DateUtils.getSysDate());
                tInvestQueue.setRemark(runMsg);
                this.itWheatAccountBusiSV.modifyAcct(tInvestQueue.getMemberId()
                        , tInvestQueue.getAmount() //冻结金额
                        , InvestConstants.Account.DETAIL_OPERTYPE_UNFREEZE //解冻
                        , InvestConstants.Account.DETAIL_TYPE_GRANARY
                        , InvestConstants.Account.DETAIL_SUBTYPE_QUEUE
                        , "粮仓队列执行失败，退回冻结资金"); //粮仓
            }
            this.itInvestQueueBusiSV.update(tInvestQueue);
        }

        return tInvestPlanMainDZ;
    }
}
