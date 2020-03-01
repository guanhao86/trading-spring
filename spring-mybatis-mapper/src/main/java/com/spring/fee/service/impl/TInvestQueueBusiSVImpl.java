package com.spring.fee.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.fee.constants.InvestConstants;
import com.spring.fee.dao.mapper.TInvestQueueMapper;
import com.spring.fee.model.*;
import com.spring.fee.service.ITInvestPlanConfigBusiSV;
import com.spring.fee.service.ITInvestPlanMainBusiSV;
import com.spring.fee.service.ITInvestQueueBusiSV;
import com.spring.fee.service.ITWheatAccountBusiSV;
import com.spring.free.util.DateUtils;
import com.spring.free.util.exception.ExceptionConstants;
import com.spring.free.util.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 队列服务
 */
@Slf4j
@Service
@Transactional
public class TInvestQueueBusiSVImpl implements ITInvestQueueBusiSV {

    @Autowired
    TInvestQueueMapper investQueueMapper;

    @Autowired
    ITInvestPlanMainBusiSV itInvestPlanMainBusiSV;

    @Autowired
    ITInvestPlanConfigBusiSV itInvestPlanConfigBusiSV;

    @Autowired
    ITWheatAccountBusiSV itWheatAccountBusiSV;

    /**
     * 创建队列
     * @param tInvestQueue
     * @return
     */
    @Override
    public TInvestQueue insert(TInvestQueue tInvestQueue) {
        log.info("创建队列参数tInvestQueue：{}", JSON.toJSON(tInvestQueue));
        tInvestQueue.setInsertTime(DateUtils.getSysDate());
        tInvestQueue.setStatus("1");
        tInvestQueue.setQueueYyyymmdd(DateUtils.getYYYYMMDD(DateUtils.getSysDate()));
        investQueueMapper.insert(tInvestQueue);
        return tInvestQueue;
    }

    @Override
    public TInvestQueue update(TInvestQueue tInvestQueue) {
        if (1 == investQueueMapper.updateByPrimaryKeySelective(tInvestQueue)){
            return tInvestQueue;
        }
        return null;
    }

    @Override
    public TInvestQueue delete(TInvestQueue tInvestQueue) {
        return null;
    }

    @Override
    public TInvestQueue select(TInvestQueue tInvestQueue) {
        return null;
    }

    @Override
    public List<TInvestQueue> queryList(TInvestQueue tInvestQueue) {
        TInvestQueueExample example = new TInvestQueueExample();
        TInvestQueueExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(tInvestQueue.getStatus())) {
            criteria.andStatusEqualTo(tInvestQueue.getStatus());
        }
        if (StringUtils.isNotEmpty(tInvestQueue.getMemberId())) {
            criteria.andStatusEqualTo(tInvestQueue.getMemberId());
        }
        if (StringUtils.isNotEmpty(tInvestQueue.getOrigPlanMainId())) {
            criteria.andOrigPlanMainIdEqualTo(tInvestQueue.getOrigPlanMainId());
        }
        return investQueueMapper.selectByExample(example);
    }

    /**
     * 数据列表分页
     * @param tInvestQueue
     * @param pageNum
     * @param pageSize
     * @param map
     * @return
     */
    @Override
    public PageInfo<TInvestQueue> queryListPage(TInvestQueue tInvestQueue, Integer pageNum, Integer pageSize, Map<String ,Object> map) {
        log.info("获取队列列表参数tInvestQueue：{}", JSON.toJSON(tInvestQueue));
        log.info("获取队列列表参数pageNum：{}", pageNum);
        log.info("获取队列列表参数pageSize：{}", pageSize);
        log.info("获取队列列表参数map：{}", JSON.toJSON(map));
        TInvestQueueExample example = new TInvestQueueExample();
        TInvestQueueExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(tInvestQueue.getStatus())) {
            criteria.andStatusEqualTo(tInvestQueue.getStatus());
        }

        if (map != null) {
            if (map.get("SORT") != null) {
                String sort = (String) map.get("SORT");
                example.setOrderByClause(" " + sort);
            }
        }
        PageInfo<TInvestQueue> pageInfo = PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(() -> this.investQueueMapper.selectByExample(example));
        log.info("获取队列列表结果：{}", JSON.toJSON(pageInfo));
        return pageInfo;
    }

    /**
     * 申请排队
     *
     * @param tInvestQueue
     * @return
     */
    @Override
    public TInvestQueue createQueue(TInvestQueue tInvestQueue) {
        log.info("申请排队开始");
        if (tInvestQueue == null) {
            throw new ServiceException(ExceptionConstants.Param.NULL, "申请排队，入参为空");
        }

        if (StringUtils.isEmpty(tInvestQueue.getType())) {
            throw new ServiceException(ExceptionConstants.Param.NULL, "申请队列，续仓类型不能为空");
        }

        //需要冻结的金额
        long amount = 0 ;
        //会员ID
        String memberId="";

        //不续仓
        if (InvestConstants.Queue.TYPE_NOAPPEND.equals(tInvestQueue.getType())) {
            if (StringUtils.isEmpty(tInvestQueue.getMemberId())) {
                throw new ServiceException(ExceptionConstants.Param.NULL, "申请队列，会员ID不能为空");
            }
            if (StringUtils.isEmpty(tInvestQueue.getPlanId())) {
                throw new ServiceException(ExceptionConstants.Param.NULL, "申请队列，计划配置ID不能为空");
            }

            //是否一天只允许申请一个计划(队列中存在当天申请的记录，状态为排队中或购买成功的状态)
            //特殊情况：1号申请一个计划，单位排队比较靠后，没执行成功。2号又申请一个计划，和1号一起开始执行，那么执行开始时间，他们两个相同
            if ("1".equals(InvestConstants.SysParam.onePlanOneDay)) {
                //判断当天是否创建过队列
                TInvestQueue TInvestQueueCheck = new TInvestQueue();
                TInvestQueueCheck.setQueueYyyymmdd(DateUtils.getYYYYMMDD(DateUtils.getSysDate()));
                List<TInvestQueue> tInvestQueueList = this.queryList(TInvestQueueCheck);
                if (tInvestQueueList != null && !tInvestQueueList.isEmpty()) {
                    for (TInvestQueue tmp : tInvestQueueList) {
                        if (InvestConstants.Queue.QUEUE_STATUS_ING.equals(tmp.getStatus()) || InvestConstants.Queue.QUEUE_STATUS_BUY.equals(tmp.getStatus())) {
                            throw new ServiceException(ExceptionConstants.Param.NULL, "当天已申请购买粮仓计划，不允许多次购买！");
                        }
                    }
                }
            }

            //获取计划配置
            TInvestPlanConfig tInvestPlanConfig = this.itInvestPlanConfigBusiSV.selectById(tInvestQueue.getPlanId());
            amount = tInvestPlanConfig.getPlanAmount();
            memberId = tInvestQueue.getMemberId();

        } else if (InvestConstants.Queue.TYPE_APPEND.equals(tInvestQueue.getType())) {
            if (StringUtils.isEmpty(tInvestQueue.getOrigPlanMainId())) {
                throw new ServiceException(ExceptionConstants.Param.NULL, "申请续仓队列，原执行中计划ID不能为空");
            }

            //判断原计划是否已经进入续仓队列
            TInvestQueue TInvestQueueCheck = new TInvestQueue();
            TInvestQueueCheck.setOrigPlanMainId(tInvestQueue.getOrigPlanMainId());
            List<TInvestQueue> tInvestQueueList = this.queryList(TInvestQueueCheck);
            if (tInvestQueueList != null && !tInvestQueueList.isEmpty()) {
                for (TInvestQueue tmp : tInvestQueueList) {
                    if (InvestConstants.Queue.QUEUE_STATUS_ING.equals(tmp.getStatus()) || InvestConstants.Queue.QUEUE_STATUS_BUY.equals(tmp.getStatus())) {
                        throw new ServiceException(ExceptionConstants.Param.NULL, "该计划已经申请过续仓，不允许重复申请！");
                    }
                }
            }

            //续仓判断当前计划状态是否可以续仓
            //判断计划主表
            TInvestPlanMain investPlanMainParam = new TInvestPlanMain();
            Long mainId = Long.parseLong(tInvestQueue.getOrigPlanMainId());
            investPlanMainParam.setId(mainId);
            TInvestPlanMain investPlanMainOrig = this.itInvestPlanMainBusiSV.select(investPlanMainParam);
            if (investPlanMainOrig == null) {
                throw new ServiceException(ExceptionConstants.Param.FEILD_NULL, "未查询到当前正在执行的计划，计划ID["+mainId+"]");
            }
            if (!InvestConstants.Plan.DETAIL_STATUS_ING.equals(investPlanMainOrig.getStatus())) {
                throw new ServiceException(ExceptionConstants.Param.FEILD_NULL, "续仓当前计划状态不是执行中，不允许续仓，计划ID["+mainId+"]");
            }
            //校验副仓
            TInvestPlanDetail tInvestPlanDetail = this.itInvestPlanMainBusiSV.checkCanSecondAndReturnDetail(investPlanMainParam);
            if (tInvestPlanDetail != null){
                amount = tInvestPlanDetail.getPlanAmount();
            } else {
                throw new ServiceException(ExceptionConstants.Param.FEILD_NULL, "副仓状态不正确，不允许续仓，计划ID["+mainId+"]");
            }
            memberId = investPlanMainOrig.getMemberId();
        }

        //校验数量
        log.info("申请排队校验数量");
        TInvestQueue tInvestQueueCheck = new TInvestQueue();
        tInvestQueueCheck.setMemberId(memberId);
        tInvestQueueCheck.setStatus(InvestConstants.Queue.QUEUE_STATUS_ING);
        List<TInvestQueue> tInvestQueueList = this.queryList(tInvestQueueCheck);
        //获取排队中数量
        int queueNum = tInvestQueueList == null ? 0 : tInvestQueueList.size();
        log.info("排队中数量：{}", queueNum);
        //获取计划中的数据
        TInvestPlanMainDZ investPlanMainCheck = new TInvestPlanMainDZ();
        investPlanMainCheck.setMemberId(memberId);
        investPlanMainCheck.setStatus(InvestConstants.Plan.MAIN_STATUS_ING);
        List<TInvestPlanMain> tInvestPlanMainList = this.itInvestPlanMainBusiSV.queryList(investPlanMainCheck);
        int planingNum = tInvestPlanMainList == null ? 0 : tInvestPlanMainList.size();
        log.info("计划中数量：{}", planingNum);

        //续仓不校验数量
        if (queueNum + planingNum >= Integer.parseInt(InvestConstants.SysParam.PLAN_MAX_ING) && !InvestConstants.Queue.TYPE_APPEND.equals(tInvestQueue)) {
            log.info("申请排队校验数量不通过，当前不允许申请");
            throw new ServiceException(ExceptionConstants.Business.CHECK_ERR, "申请排队校验数量不通过，当前不允许申请");
        }

        //校验账本金额是否够用
        //amount
        log.info("排队需要金额{}",amount);
        //查询账户信息
        TWheatAccount tWheatAccount = this.itWheatAccountBusiSV.selectByMember(memberId);
        if (tWheatAccount.getAvailable() < amount) {
            log.info("排队需要金额为："+amount/1000+"，账户可用余额："+tWheatAccount.getAvailable()/1000+"，余额不足，当前不允许申请");
            throw new ServiceException(ExceptionConstants.Business.CHECK_ERR, "排队需要金额为："+amount/1000+"，账户可用余额："+tWheatAccount.getAvailable()/1000+"，余额不足，当前不允许申请");
        }

        tInvestQueue.setAmount(amount);
        //插入队列
        this.insert(tInvestQueue);
        //冻结账户（1、修改账户冻结金额，2、增加账户变更记录）
        this.itWheatAccountBusiSV.modifyAcct(memberId
                , amount //冻结金额
                , InvestConstants.Account.DETAIL_OPERTYPE_FREEZE //冻结
                , InvestConstants.Account.DETAIL_TYPE_GRANARY
                , InvestConstants.Account.DETAIL_SUBTYPE_QUEUE //排队
                ,"粮仓计划购买排队"); //粮仓
        return tInvestQueue;
    }

    /**
     * 申请排队
     *
     * @param tInvestQueue
     * @return
     */
    @Override
    public TInvestQueue createQueueV2(TInvestQueue tInvestQueue) {
        log.info("申请排队开始V2");
        if (tInvestQueue == null) {
            throw new ServiceException(ExceptionConstants.Param.NULL, "申请排队，入参为空");
        }

        if (StringUtils.isEmpty(tInvestQueue.getType())) {
            throw new ServiceException(ExceptionConstants.Param.NULL, "申请队列，续仓类型不能为空");
        }

        //需要冻结的金额
        long amount = 0 ;
        //会员ID
        String memberId="";

        //不续仓
        if (InvestConstants.Queue.TYPE_NOAPPEND.equals(tInvestQueue.getType())) {
            if (StringUtils.isEmpty(tInvestQueue.getMemberId())) {
                throw new ServiceException(ExceptionConstants.Param.NULL, "申请队列，会员ID不能为空");
            }
            if (StringUtils.isEmpty(tInvestQueue.getPlanId())) {
                throw new ServiceException(ExceptionConstants.Param.NULL, "申请队列，计划配置ID不能为空");
            }

            memberId = tInvestQueue.getMemberId();

            //校验V2版本允许执行计划的服务
            TInvestPlanConfig tInvestPlanConfig = this.itInvestPlanMainBusiSV.checkCanBuyPlan(memberId, tInvestQueue.getPlanId());

            if (tInvestPlanConfig == null) {
                throw new ServiceException(ExceptionConstants.Param.NULL, "不允许购买粮仓计划");
            }

            //获取计划配置
            amount = tInvestPlanConfig.getPlanAmount();


        } else if (InvestConstants.Queue.TYPE_APPEND.equals(tInvestQueue.getType())) {
            if (StringUtils.isEmpty(tInvestQueue.getOrigPlanMainId())) {
                throw new ServiceException(ExceptionConstants.Param.NULL, "申请续仓队列，原执行中计划ID不能为空");
            }

            //判断原计划是否已经进入续仓队列
            TInvestQueue TInvestQueueCheck = new TInvestQueue();
            TInvestQueueCheck.setOrigPlanMainId(tInvestQueue.getOrigPlanMainId());
            List<TInvestQueue> tInvestQueueList = this.queryList(TInvestQueueCheck);
            if (tInvestQueueList != null && !tInvestQueueList.isEmpty()) {
                for (TInvestQueue tmp : tInvestQueueList) {
                    if (InvestConstants.Queue.QUEUE_STATUS_ING.equals(tmp.getStatus()) || InvestConstants.Queue.QUEUE_STATUS_BUY.equals(tmp.getStatus())) {
                        throw new ServiceException(ExceptionConstants.Param.NULL, "该计划已经申请过续仓，不允许重复申请！");
                    }
                }
            }

            //续仓判断当前计划状态是否可以续仓
            //判断计划主表
            TInvestPlanMain investPlanMainParam = new TInvestPlanMain();
            Long mainId = Long.parseLong(tInvestQueue.getOrigPlanMainId());
            investPlanMainParam.setId(mainId);
            TInvestPlanMain investPlanMainOrig = this.itInvestPlanMainBusiSV.select(investPlanMainParam);
            memberId = investPlanMainOrig.getMemberId();
            //已经存在其他类型计划正在执行中，不允许续仓(modify by guanhao  由只能购买一种计划，修改为可以购买多种计划)
//            if (!this.itInvestPlanMainBusiSV.checkOtherPlanIng(investPlanMainOrig.getMemberId(), investPlanMainOrig.getPlanId())) {
//                log.info("已经存在其他类型计划正在执行中，不允许续仓");
//                throw new ServiceException(ExceptionConstants.Param.NULL, "已经存在其他类型计划正在执行中，不允许续仓");
//            }

            //校验主仓中是否存在其他执行中计划主仓
//            if(!this.itInvestPlanMainBusiSV.checkOtherPlanIng(memberId, investPlanMainOrig.getPlanId())){
//                log.info("不允许续仓");
//                throw new ServiceException(ExceptionConstants.Param.NULL, "不允许续仓");
//            }

            //要复投计划是否为执行中最高计划
            //查询执行中最大计划
            TInvestPlanDetailDZ maxDz = this.itInvestPlanMainBusiSV.getMaxRuningPlan(investPlanMainOrig.getMemberId());
            if (null != maxDz && Integer.parseInt(maxDz.getPlanId()) > Integer.parseInt(investPlanMainOrig.getPlanId())) {
                log.info("存在更高级别的计划，不允许复投当前计划");
                throw new ServiceException(ExceptionConstants.Param.FEILD_NULL, "存在更高级别的计划，不允许复投当前计划");
            }

            if (investPlanMainOrig == null) {
                log.info("未查询到当前正在执行的计划，计划ID["+mainId+"]");
                throw new ServiceException(ExceptionConstants.Param.FEILD_NULL, "未查询到当前正在执行的计划，计划ID["+mainId+"]");
            }
            if (!InvestConstants.Plan.DETAIL_STATUS_ING.equals(investPlanMainOrig.getStatus())) {
                log.info("续仓当前计划状态不是执行中，不允许续仓，计划ID["+mainId+"]");
                throw new ServiceException(ExceptionConstants.Param.FEILD_NULL, "续仓当前计划状态不是执行中，不允许续仓，计划ID["+mainId+"]");
            }
            //校验副仓
            TInvestPlanDetail tInvestPlanDetail = this.itInvestPlanMainBusiSV.checkCanSecondAndReturnDetail(investPlanMainParam);
            if (tInvestPlanDetail != null){
                amount = tInvestPlanDetail.getPlanAmount();
            } else {
                log.info("副仓状态不正确，不允许续仓，计划ID["+mainId+"]");
                throw new ServiceException(ExceptionConstants.Param.FEILD_NULL, "副仓状态不正确，不允许续仓，计划ID["+mainId+"]");
            }

        }



        //校验账本金额是否够用
        //amount
        log.info("排队需要金额{}",amount);
        //查询账户信息
        TWheatAccount tWheatAccount = this.itWheatAccountBusiSV.selectByMember(memberId);
        if (tWheatAccount.getAvailable() < amount) {
            log.info("排队需要金额为："+amount/1000+"，账户可用余额："+tWheatAccount.getAvailable()/1000+"，余额不足，当前不允许申请");
            throw new ServiceException(ExceptionConstants.Business.CHECK_ERR, "排队需要金额为："+amount/1000+"，账户可用余额："+tWheatAccount.getAvailable()/1000+"，余额不足，当前不允许申请");
        }

        tInvestQueue.setAmount(amount);
        //插入队列
        log.info("插入队列");
        tInvestQueue = this.insert(tInvestQueue);
        //冻结账户（1、修改账户冻结金额，2、增加账户变更记录）
        log.info("冻结账户");
        this.itWheatAccountBusiSV.modifyAcct(memberId
                , amount //冻结金额
                , InvestConstants.Account.DETAIL_OPERTYPE_FREEZE //冻结
                , InvestConstants.Account.DETAIL_TYPE_GRANARY
                , InvestConstants.Account.DETAIL_SUBTYPE_QUEUE //排队
                ,"粮仓计划购买排队"); //粮仓
        return tInvestQueue;
    }

    /**
     * 获取待执行队列，指定数量
     *
     * @param size
     * @return
     */
    @Override
    public List<TInvestQueue> queryQueueToPlanList(int size) {
        //获取指定数量的队列数据，通过分页方式获取
        log.info("获取指定数量的队列数据，通过分页方式获取，获取条数{}",size);
        TInvestQueue tInvestQueue = new TInvestQueue();
        tInvestQueue.setStatus(InvestConstants.Queue.QUEUE_STATUS_ING);//排队中
        Map<String ,Object> map = new HashMap<>();
        map.put("SORT", "order_id");//按插入顺序获取列表
        PageInfo<TInvestQueue> queuePageInfo = this.queryListPage(tInvestQueue, 1, size, map);
        List<TInvestQueue> tInvestQueueList = null;
        if (queuePageInfo != null) {
            tInvestQueueList = queuePageInfo.getList();
        }
//        if (tInvestQueueList != null) {
//            //开始遍历队列
//            log.info("开始遍历队列{}", JSON.toJSON(tInvestQueueList));
//            for (TInvestQueue tmpQueue : tInvestQueueList) {
//                log.info("队列数据:{}", JSON.toJSON(tmpQueue));
//
//            }
//        } else {
//            log.info("队列为空{}", size);
//        }
        return tInvestQueueList;
    }

    /**
     * 申请排队并开始执行计划
     *
     * @param tInvestQueue
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public TInvestQueueDZ createQueueV2AndRun(TInvestQueue tInvestQueue) {
        //创建队列
        tInvestQueue = this.createQueueV2(tInvestQueue);
        //立即执行计划
        TInvestPlanMainDZ tInvestPlanMainDZ = this.itInvestPlanMainBusiSV.createPlan(tInvestQueue);

        TInvestQueueDZ result = new TInvestQueueDZ();
        result.setBonusTmpId(tInvestPlanMainDZ.getBonusTmpId());
        return result;
    }
}
