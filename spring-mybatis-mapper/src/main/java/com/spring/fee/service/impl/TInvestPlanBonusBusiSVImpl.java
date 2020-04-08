package com.spring.fee.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.fee.constants.InvestConstants;
import com.spring.fee.dao.mapper.TInvestPlanBonusMapper;
import com.spring.fee.dao.mapper.TInvestPlanBonusMapperDZ;
import com.spring.fee.model.*;
import com.spring.fee.service.*;
import com.spring.free.util.DateUtils;
import com.spring.free.util.exception.ExceptionConstants;
import com.spring.free.util.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 奖金计算服务
 */
@Slf4j
@Service
@Transactional
public class TInvestPlanBonusBusiSVImpl implements ITInvestPlanBonusBusiSV {

    @Autowired
    TInvestPlanBonusMapper tInvestPlanBonusMapper;

    @Autowired
    TInvestPlanBonusMapperDZ tInvestPlanBonusMapperDZ;

    @Autowired
    ITInvestPlanBonusTmpBusiSV itInvestPlanBonusTmpBusiSV;

    @Autowired
    ITWheatMemberBusiSV itWheatMemberBusiSV;

    @Autowired
    ITInvestMemberUpConfigBusiSV itInvestMemberUpConfigBusiSV;

    @Autowired
    ITInvestMemberMoneyConfigBusiSV itInvestMemberMoneyConfigBusiSV;

    @Autowired
    ITWheatAccountBusiSV itWheatAccountBusiSV;

    @Autowired
    ITWheatAccountDetailBusiSV itWheatAccountDetailBusiSV;

    @Autowired
    ITInvestPlanDetailBusiSV itInvestPlanDetailBusiSV;

    @Autowired
    ITInvestPlanBonusBusiSV itInvestPlanBonusBusiSV;

    @Override
    public TInvestPlanBonus insert(TInvestPlanBonus tInvestPlanBonus) {
        log.info("创建奖金计算记录{}",JSON.toJSONString(tInvestPlanBonus));
        Date sysdate = DateUtils.getSysDate();
        tInvestPlanBonus.setCreateTime(sysdate);
        tInvestPlanBonus.setPaymentday(DateUtils.getYYYYMMDD(sysdate));//账期
        if (1 == tInvestPlanBonusMapper.insert(tInvestPlanBonus)) {
            return tInvestPlanBonus;
        }
        return null;
    }

    @Override
    public TInvestPlanBonus update(TInvestPlanBonus tInvestPlanBonus) {
        log.info("更新奖金计算记录{}",JSON.toJSONString(tInvestPlanBonus));
        if (1 == tInvestPlanBonusMapper.updateByPrimaryKeySelective(tInvestPlanBonus)) {
            return tInvestPlanBonus;
        }
        return null;
    }

    @Override
    public TInvestPlanBonus delete(TInvestPlanBonus tInvestPlanBonus) {
        return null;
    }

    @Override
    public TInvestPlanBonus select(TInvestPlanBonus tInvestPlanBonus) {
        return this.tInvestPlanBonusMapper.selectByPrimaryKey(tInvestPlanBonus.getId());
    }

    @Override
    public List<TInvestPlanBonus> queryList(TInvestPlanBonus tInvestPlanBonus) {

        TInvestPlanBonusExample example = new TInvestPlanBonusExample();
        TInvestPlanBonusExample.Criteria criteria = example.createCriteria();

        if (null != tInvestPlanBonus.getId()) {
            criteria.andIdEqualTo(tInvestPlanBonus.getId());
        }

        if (StringUtils.isNotEmpty(tInvestPlanBonus.getMemberId())) {
            criteria.andMemberIdEqualTo(tInvestPlanBonus.getMemberId());
        }

        if (StringUtils.isNotEmpty(tInvestPlanBonus.getPaymentday())) {
            criteria.andPaymentdayEqualTo(tInvestPlanBonus.getPaymentday());
        }

        return this.tInvestPlanBonusMapper.selectByExample(example);
    }

    @Override
    public PageInfo<TInvestPlanBonus> queryPage(TInvestPlanBonus tInvestPlanBonus, Integer pageNum, Integer pageSize, Map<String, Object> map) {
        TInvestPlanBonusExample example = new TInvestPlanBonusExample();
        TInvestPlanBonusExample.Criteria criteria = example.createCriteria();

        if (null != tInvestPlanBonus.getId()) {
            criteria.andIdEqualTo(tInvestPlanBonus.getId());
        }

        if (StringUtils.isNotEmpty(tInvestPlanBonus.getMemberId())) {
            criteria.andMemberIdEqualTo(tInvestPlanBonus.getMemberId());
        }

        if (StringUtils.isNotEmpty(tInvestPlanBonus.getPaymentday())) {
            criteria.andPaymentdayEqualTo(tInvestPlanBonus.getPaymentday());
        }
        example.setOrderByClause("create_time desc");
        PageInfo<TInvestPlanBonus> pageInfo = PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(() -> this.tInvestPlanBonusMapper.selectByExample(example));
        log.info("获取粮仓收益列表结果：{}", JSON.toJSON(pageInfo));
        return pageInfo;
    }

    /**
     * 计算伞下业绩
     *
     * @param tInvestPlanBonusTmp
     */
    @Override
    public boolean calculateTmp(TInvestPlanBonusTmp tInvestPlanBonusTmp, String memberId) {
        //查询会员信息
        TWheatMember tWheatMember = itWheatMemberBusiSV.selectByMemberId(memberId);
        if (tWheatMember == null) {
            throw new ServiceException(ExceptionConstants.Param.RESULT_NULL, "未查询到会员信息，会员ID="+memberId);
        }
        log.info("会员信息：{}", JSON.toJSONString(tWheatMember));

        //根据父节点ID查询上级用户信息
        TWheatMember tWheatMemberParent = itWheatMemberBusiSV.selectByMemberId(tWheatMember.getReferenceId());
        if (tWheatMemberParent == null) {
            log.info("根据父节点ID{}查询不到上级用户信息，不计算伞下业绩", tWheatMember.getReferenceId());
            return false;
        }
        log.info("父会员级别{},子会员级别{}", tWheatMemberParent.getLevel(), tWheatMember.getLevel());
        if (Integer.parseInt(tWheatMemberParent.getLevel()) > Integer.parseInt(tWheatMember.getLevel())) {
            log.info("父会员级别>子会员级别-->计算伞下业绩");
            TInvestPlanBonus tInvestPlanBonus = new TInvestPlanBonus();
            tInvestPlanBonus.setPaymentday(tInvestPlanBonusTmp.getPaymentday());
            tInvestPlanBonus.setMemberId(tWheatMemberParent.getMemberId());
            tInvestPlanBonus.setMemberLevel(tWheatMemberParent.getLevel());
            tInvestPlanBonus.setFromMemberId(tInvestPlanBonusTmp.getMemberId());
            tInvestPlanBonus.setAmount(tInvestPlanBonusTmp.getAmount());
            this.insert(tInvestPlanBonus);
            log.info("继续执行下一个用户");
            this.calculateTmp(tInvestPlanBonusTmp, tWheatMemberParent.getMemberId());
        }else{
            log.info("遮断流程：父会员级别 <= 子会员级别");
            return false;
        }

        return false;
    }

    /**
     * 结算奖金，并存入用户账户
     *
     * @param memberId
     * @param amount 伞下钱数
     */
    @Override
    public boolean calculate(String memberId, String memberLevel, Long amount, Map<String, TInvestPlanBonusDZ> map) {

        //查询奖金计算配置
        TInvestMemberUpConfig tInvestMemberUpConfig = this.itInvestMemberUpConfigBusiSV.selectById(memberLevel);

        long amountR1 = 0L;
        long amountR2 = 0L;

        //判断结算类型
        //业绩分润奖金（？%）,例如：0.01 = 1%
        if (tInvestMemberUpConfig.getPerformancePersent().doubleValue() > 0) {
            //伞下 * %
            amountR1 = (long)(amount * tInvestMemberUpConfig.getPerformancePersent().doubleValue() / 100);
        }

        //佣金分润（？%）,例如：0.01 = 1%
        if (tInvestMemberUpConfig.getCommissionPersent().doubleValue() > 0) {
            /*
            佣金分润说明：A推荐B和C，B获得100元，A获得B的百分之200，则A获得100*2 = 200；C获得50，A获得C的百分之200，
            则A获得50*2= 100，A总共奖金为200+100 = 300元；前提是B和C一定是只比A低一个等级的会员，低二个以上等级的不计算A的奖金
             */
            //查询直推，低一级别的会员列表
            TWheatMemberDZ tWheatMemberParam = new TWheatMemberDZ();
            tWheatMemberParam.setReferenceId(memberId);
            tWheatMemberParam.setLevel(String.valueOf(Integer.parseInt(memberLevel) - 1));
            List<TWheatMember> tWheatMemberList = this.itWheatMemberBusiSV.queryList(tWheatMemberParam, null);
            if (tWheatMemberList != null && !tWheatMemberList.isEmpty()) {
                long amountR2Tmp = 0l;
                //获取子节点会员钱数，并求和
                for (TWheatMember tWheatMemberTmp : tWheatMemberList) {
                    TInvestPlanBonusDZ tInvestPlanBonusDZ = map.get(tWheatMemberTmp.getMemberId());
                    if (tInvestPlanBonusDZ != null) {
                        amountR2Tmp += tInvestPlanBonusDZ.getAmount();
                    }
                }
                amountR2 = (long)(amountR2Tmp * tInvestMemberUpConfig.getCommissionPersent().doubleValue() / 100);
            }
        }

        //插入账户
        if(amountR1 > 0) {
            itWheatAccountBusiSV.modifyAcct(memberId,
                    amountR1,
                    InvestConstants.Account.DETAIL_OPERTYPE_INTEREST,
                    InvestConstants.Account.DETAIL_TYPE_GRANARY,
                    null,
                    "业绩分润奖金");
        }
        if(amountR2 > 0) {
            itWheatAccountBusiSV.modifyAcct(memberId,
                    amountR2,
                    InvestConstants.Account.DETAIL_OPERTYPE_INTEREST,
                    InvestConstants.Account.DETAIL_TYPE_GRANARY,
                    null,
                    "佣金分润奖金");
        }

        return false;
    }

    /**
     * 获取会员奖金收益率  %
     * 根据会员当前级别，正在执行主仓类型及数量 获取指定的收益百分比
     *
     * @return
     */
    @Override
    public Map<String, Double> getMemberBonusEarningRate() {
        List<TInvestPlanDetailDZ> tInvestPlanDetailDZList = this.itInvestPlanDetailBusiSV.getMainRunningCount(new TInvestPlanDetailDZ());
        Map<String, TInvestMemberMoneyConfig> configMap = this.itInvestMemberMoneyConfigBusiSV.queryMap();
        Map<String, Double> map = new HashMap<>();
        if (!CollectionUtils.isEmpty(tInvestPlanDetailDZList)) {
            for (TInvestPlanDetailDZ tInvestPlanDetailDZ : tInvestPlanDetailDZList){
                TInvestMemberMoneyConfig tInvestMemberMoneyConfig = configMap.get(tInvestPlanDetailDZ.getLevel()+"_"+tInvestPlanDetailDZ.getPlanId());
                if (tInvestMemberMoneyConfig == null) {
                    continue;
                }
                log.info("正在执行主计划数据：{}", JSON.toJSONString(tInvestPlanDetailDZ));
                log.info("获取到的收益配置数据：{}", JSON.toJSONString(tInvestMemberMoneyConfig));
                if (tInvestPlanDetailDZ.getCount() >= tInvestMemberMoneyConfig.getRunningCount()) {
                    log.info("数量达标，更新会员收益百分比为当前最大值");
                    Double hisPer = map.get(tInvestPlanDetailDZ.getMemberId());
                    if (hisPer == null || tInvestMemberMoneyConfig.getInterestReturnPer() > hisPer) {
                        map.put(tInvestPlanDetailDZ.getMemberId(), tInvestMemberMoneyConfig.getInterestReturnPer());
                    }
                }
            }
        }
        log.info("会员结算比例：{}", JSON.toJSONString(map));
        return map;
    }

    /**
     * 获取会员奖金收益率  %
     * 根据会员当前级别，正在执行主仓类型及数量 获取指定的收益百分比
     * 与计划无关：1-4,5-6,7 三个档次
     *
     * @return
     */
    @Override
    public Map<String, Double> getMemberBonusEarningRate2() {
        //会员下个计划数量
        List<TInvestPlanDetailDZ> tInvestPlanDetailDZList = this.itInvestPlanDetailBusiSV.getMainRunningCount(new TInvestPlanDetailDZ());
        //会员下计划数量
        Map<String, TInvestPlanDetailDZ> memberPlanCountMap = this.itInvestPlanDetailBusiSV.getMainRunningCount2(new TInvestPlanDetailDZ());
        //取结算配置
        Map<String, List<TInvestMemberMoneyConfig>> configMap = this.itInvestMemberMoneyConfigBusiSV.queryMap2();
        log.info("奖金计算配置：{}", JSON.toJSONString(configMap));
        Map<String, Double> map = new HashMap<>();
        if (!CollectionUtils.isEmpty(tInvestPlanDetailDZList)) {
            for (TInvestPlanDetailDZ tInvestPlanDetailDZ : tInvestPlanDetailDZList){
                List<TInvestMemberMoneyConfig> tInvestMemberMoneyConfigList = configMap.get(tInvestPlanDetailDZ.getLevel());
                if (CollectionUtils.isEmpty(tInvestMemberMoneyConfigList)) {
                    //无奖金配置，不计算奖金
                    continue;
                }

                log.info("正在执行主计划数据：{}", JSON.toJSONString(tInvestPlanDetailDZ));
                log.info("获取到的收益配置数据：{}", JSON.toJSONString(tInvestMemberMoneyConfigList));
                for (TInvestMemberMoneyConfig tInvestMemberMoneyConfig : tInvestMemberMoneyConfigList) {
                    int checkCount = 0;
                    //判断校验数量类型
                    if (InvestConstants.MoneyConfig.COUNT_TYPE_ALL.equals(tInvestMemberMoneyConfig.getCountType())) {
                        //校验计划总数是否达标
                        TInvestPlanDetailDZ dz0 = memberPlanCountMap.get(tInvestPlanDetailDZ.getMemberId());
                        checkCount = dz0 == null ? 0 : dz0.getCount();
                    }else if(InvestConstants.MoneyConfig.COUNT_TYPE_PLAN.equals(tInvestMemberMoneyConfig.getCountType())) {
                        //校验当前计划数是否达标
                        checkCount = tInvestPlanDetailDZ.getCount();
                    }
                    if (checkCount >= tInvestMemberMoneyConfig.getRunningCount()) {
                        log.info("数量达标，更新会员收益百分比为当前最大值");
                        Double hisPer = map.get(tInvestPlanDetailDZ.getMemberId());
                        if (hisPer == null || tInvestMemberMoneyConfig.getInterestReturnPer() > hisPer) {
                            map.put(tInvestPlanDetailDZ.getMemberId(), tInvestMemberMoneyConfig.getInterestReturnPer());
                        }
                    }
                }
            }
        }
        log.info("会员结算比例：{}", JSON.toJSONString(map));
        return map;
    }

    /**
     * 计算奖金V2版本（级差算法） 结算算法入口
     *
     * @return
     */
    @Override
    public void calculateStartV2(Long id) {
        log.info("结算奖金V2版本========================================开始");
        List<TInvestPlanBonusTmp> investPlanBonusTmpList = new ArrayList<>();
        TInvestPlanBonusTmp param = new TInvestPlanBonusTmp();
        if (id == null) {
            log.info("获取待结算列表");

            param.setPaymentday(DateUtils.getYYYYMMDD(DateUtils.getSysDate()));
            param.setSettleFlag(InvestConstants.Bonus.BONUS_SETTLE_FLAG);

        } else {
            log.info("传入待结算数据，插入结算列表");
            param.setId(id);
        }

        Map<String, TInvestMemberMoneyConfig> moneyConfigMap = this.itInvestMemberMoneyConfigBusiSV.queryMap();

        investPlanBonusTmpList = this.itInvestPlanBonusTmpBusiSV.queryList(param);
        if (investPlanBonusTmpList != null) {
            log.info("获取待结算列表数量：{}", investPlanBonusTmpList.size());
            //modify by gg 20191118 结算与购买计划级别无关，只取正在执行计划数量
            Map<String, Double> map = this.itInvestPlanBonusBusiSV.getMemberBonusEarningRate2();
            for (TInvestPlanBonusTmp tInvestPlanBonusTmp : investPlanBonusTmpList) {
                TInvestPlanBonusTmpDZ dz = new TInvestPlanBonusTmpDZ();
                BeanUtils.copyProperties(tInvestPlanBonusTmp, dz);
                log.info("待结算数据：{}", JSON.toJSONString(dz));
                try {
                    TWheatMember tWheatMember = this.itWheatMemberBusiSV.selectByMemberId(dz.getMemberId());
                    dz.setMaxMemberLevel(Integer.parseInt(tWheatMember.getLevel()));
                    this.calculateV2(dz, map, tInvestPlanBonusTmp.getMemberId(), moneyConfigMap);
                    log.info("结算完成，修改待结算标志为已结算");
                    tInvestPlanBonusTmp.setSettleFlag("1");
                    tInvestPlanBonusTmp.setSettleTime(DateUtils.getSysDate());
                    this.itInvestPlanBonusTmpBusiSV.update(tInvestPlanBonusTmp);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        log.info("结算奖金V2版本========================================结束");
    }

    /**
     * 统计收益：支持全部收益，历史收益  会员ID必填
     *
     * @param tInvestPlanBonusDZ
     * @return
     */
    @Override
    public List<TInvestPlanBonusDZ> statisticEarnings(TInvestPlanBonusDZ tInvestPlanBonusDZ) {
        return this.tInvestPlanBonusMapperDZ.statisticMemberSum(tInvestPlanBonusDZ);
    }

    /**
     * 根据会员分组统计收益
     *
     * @return
     */
    @Override
    public List<TInvestPlanBonusDZ> statisticGroupByMemberId() {
        return this.tInvestPlanBonusMapperDZ.statisticGroupByMemberId();
    }

    /**
     * 结算奖金，并存入用户账户
     *
     * @param tInvestPlanBonusTmpDZ
     * @param rateMap 会员奖金比例
     */
    public boolean calculateV2(TInvestPlanBonusTmpDZ tInvestPlanBonusTmpDZ, Map<String, Double> rateMap, String memberId, Map<String, TInvestMemberMoneyConfig> moneyConfigMap) {

        log.info("结算奖金，并存入用户账户V2版本");

        log.info("获取父级会员信息");
        TWheatMember parentMember = this.itWheatMemberBusiSV.selectParentMember(tInvestPlanBonusTmpDZ.getMemberId());
        if (parentMember == null) {
            log.info("无父级会员，跳出计算");
            return false;
        }
        log.info("父级会员信息：{}", JSON.toJSONString(parentMember));
        log.info("获取父级会员奖金结算比例");
        Double parantRate = rateMap.get(parentMember.getMemberId());
        parantRate = parantRate == null ? 0D : parantRate;
        Double rate = parantRate - (tInvestPlanBonusTmpDZ.getRate() == null ? 0D: tInvestPlanBonusTmpDZ.getRate());
        log.info("根据级差规则，父级会员比例-子级会员比例：{}", rate);

        long money = 0;

        if (rate > 0) {
            log.info("比例大于0修改帐户：{}", rate);
            money = (long) (tInvestPlanBonusTmpDZ.getAmount() * rate / 100);
            log.info("奖金金额：{}", money);
            if (money > 0) {
                modifyBonus(parentMember, money, memberId, tInvestPlanBonusTmpDZ, "业绩奖金");
                //设置当前最高利率
                tInvestPlanBonusTmpDZ.setRate(parantRate);
            }

        }

        log.info("判断是否进行平级顶死奖金计算：会员级别["+parentMember.getLevel()+"],遍历记录最大会员级别["+tInvestPlanBonusTmpDZ.getMaxMemberLevel()+"]");
        //父节点级别小于等于最大级别，并且奖金比例=0，则进行性 平级顶死奖计算
        if (Integer.parseInt(parentMember.getLevel()) <= tInvestPlanBonusTmpDZ.getMaxMemberLevel()) {
            //取平级顶死配置
            TInvestMemberMoneyConfig moneyConfig = moneyConfigMap.get(parentMember.getLevel());
            log.info("平级顶死规则配置：{}", JSON.toJSONString(moneyConfig));
            log.info("当前父节点团队奖金比例：{}", parantRate);
            if (moneyConfig != null && moneyConfig.getSameLevelInterestPer() != null && moneyConfig.getSameLevelInterestPer() > 0 && rate <= 0) {
                log.info("计算平级顶死奖金");
                log.info("取上一用户的奖金额作为基数计算平级顶死奖金：{}", tInvestPlanBonusTmpDZ.getPreAmount());
                if (tInvestPlanBonusTmpDZ.getPreAmount() != null && tInvestPlanBonusTmpDZ.getPreAmount() > 0) {
                    money = (long)(tInvestPlanBonusTmpDZ.getPreAmount() * moneyConfig.getSameLevelInterestPer() / 100);
                    if (money > 0) {
                        log.info("平级顶死计算奖金，并存入帐户");
                        modifyBonus(parentMember, money, memberId, tInvestPlanBonusTmpDZ, "平级超越奖金");
                    }
                }
            }
        } else {
            //（只要出现级别高的，就设置）设置当前最高会员级别
            tInvestPlanBonusTmpDZ.setMaxMemberLevel(Integer.parseInt(parentMember.getLevel()));
        }

        tInvestPlanBonusTmpDZ.setMemberId(parentMember.getMemberId());
        if (money > 0) {
            tInvestPlanBonusTmpDZ.setPreAmount(money);
        }

        this.calculateV2(tInvestPlanBonusTmpDZ, rateMap, memberId, moneyConfigMap);
        return false;
    }

    public void modifyBonus(TWheatMember parentMember, long money, String memberId, TInvestPlanBonusTmpDZ tInvestPlanBonusTmpDZ, String remark){
        if (money > 0) {
            log.info("计算奖金，并存入帐户");
            itWheatAccountBusiSV.modifyAcct(parentMember.getMemberId(),
                    money,
                    InvestConstants.Account.DETAIL_OPERTYPE_BONUS,
                    InvestConstants.Account.DETAIL_TYPE_GRANARY,
                    null,
                    remark);

            //插入奖金记录表
            TInvestPlanBonus tInvestPlanBonus = new TInvestPlanBonus();
            tInvestPlanBonus.setMemberId(parentMember.getMemberId());
            tInvestPlanBonus.setFromMemberId(memberId);
            tInvestPlanBonus.setMemberLevel(parentMember.getLevel());
            tInvestPlanBonus.setAmount(money);
            tInvestPlanBonus.setType(InvestConstants.Bonus.TYPE_BONUS);//奖金
            tInvestPlanBonus.setPlanMainId(tInvestPlanBonusTmpDZ.getPlanMainId());
            tInvestPlanBonus.setOrderId(tInvestPlanBonusTmpDZ.getOrderId());
            tInvestPlanBonus.setSettleFlag(InvestConstants.Bonus.BONUS_SETTLE_FLAG_FINISH);
            tInvestPlanBonus.setSettleTime(DateUtils.getSysDate());
            this.insert(tInvestPlanBonus);
        }
    }
}
