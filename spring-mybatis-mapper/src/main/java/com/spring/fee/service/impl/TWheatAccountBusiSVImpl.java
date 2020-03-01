package com.spring.fee.service.impl;

import com.alibaba.fastjson.JSON;
import com.spring.fee.constants.InvestConstants;
import com.spring.fee.dao.mapper.TWheatAccountMapper;
import com.spring.fee.model.TWheatAccount;
import com.spring.fee.model.TWheatAccountDetail;
import com.spring.fee.model.TWheatAccountExample;
import com.spring.fee.model.TWheatMemberTree;
import com.spring.fee.service.ITWheatAccountBusiSV;
import com.spring.fee.service.ITWheatAccountDetailBusiSV;
import com.spring.fee.service.ITWheatMemberBusiSV;
import com.spring.free.util.exception.ExceptionConstants;
import com.spring.free.util.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 账户服务
 */
@Slf4j
@Service
@Transactional
public class TWheatAccountBusiSVImpl implements ITWheatAccountBusiSV {

    @Autowired
    TWheatAccountMapper tWheatAccountMapper;

    @Autowired
    ITWheatAccountDetailBusiSV itWheatAccountDetailBusiSV;

    @Autowired
    ITWheatMemberBusiSV itWheatMemberBusiSV;

    @Override
    public TWheatAccount insert(TWheatAccount tWheatAccount) {
        log.info("创建账户信息参数tWheatAccount：{}", JSON.toJSON(tWheatAccount));
        if (1 == tWheatAccountMapper.insert(tWheatAccount)){
            return tWheatAccount;
        }
        return null;
    }

    @Override
    public TWheatAccount update(TWheatAccount tWheatAccount) {
        log.info("修改账户信息参数tWheatAccount：{}", JSON.toJSON(tWheatAccount));
        if (1 == tWheatAccountMapper.updateByPrimaryKeySelective(tWheatAccount)){
            return tWheatAccount;
        }
        return null;
    }

    @Override
    public TWheatAccount delete(TWheatAccount tWheatAccount) {
        return null;
    }

    @Override
    public TWheatAccount select(TWheatAccount tWheatAccount) {
        return this.tWheatAccountMapper.selectByPrimaryKey(tWheatAccount.getId());
    }

    @Override
    public TWheatAccount selectByMember(String memberId) {
        TWheatAccount tWheatAccount = new TWheatAccount();
        tWheatAccount.setMemberId(memberId);
        List<TWheatAccount> arr = queryList(tWheatAccount, null, null);
        if (arr == null || arr.isEmpty()) {
            return null;
        }
        return arr.get(0);
    }

    /**
     * 修改账户信息
     *
     * @param memberId
     * @param amount   （金额）
     * @param operType 类型：1、解冻  2、冻结  3、提现  4、充值  5、利息　6、奖金
     * @param type 类型：0、其他 1、理财，2、粮仓
     * @param subType 子类型：type=粮仓: 1、购买，2、排队
     * @return
     */
    @Override
    public TWheatAccount modifyAcct(String memberId, Long amount, String operType, String type, String subType, String remark) {
        log.info("修改账户信息参数memberId：{}", memberId);
        log.info("修改账户信息参数amount：{}", amount);
        log.info("修改账户信息参数类型：1、解冻  2、冻结  3、提现  4、充值  5、利息 6、奖金operType：{}", operType);
        log.info("修改账户信息参数类型：0、其他 1、理财，2、粮仓type：{}", type);
        log.info("修改账户信息参数子类型：type=粮仓: 1、购买，2、排队subType：{}", subType);
        //查询账户
        TWheatAccount tWheatAccount = this.selectByMember(memberId);
        if (tWheatAccount == null) {
            throw new ServiceException(ExceptionConstants.Param.RESULT_NULL, "当前会员ID查询不到账户信息");
        }

        //变更前资产总额
        Long beforeTotal = tWheatAccount.getTotal();
        //变更后资产总额
        Long afterTotal = tWheatAccount.getTotal();

        //变更前_执行中粮仓冻结金额
        Long beforeGranaryIngFreeze = tWheatAccount.getGranaryIngFreeze();
        //变更后_执行中粮仓冻结金额
        Long afterGranaryIngFreeze = tWheatAccount.getGranaryIngFreeze();

        Long amountDetail = 0L;

        //解冻
        if (InvestConstants.Account.DETAIL_OPERTYPE_UNFREEZE.equals(operType)) {
            amountDetail = amount;
            if (tWheatAccount.getFreeze() < amount) {
                throw new ServiceException(ExceptionConstants.Business.CHECK_ERR, "当前冻结余额不足，不能解冻");
            }
            // 冻结金额
            tWheatAccount.setFreeze(tWheatAccount.getFreeze() - amount);
            // 可用金额
            tWheatAccount.setAvailable(tWheatAccount.getAvailable() + amount);
            if (InvestConstants.Account.DETAIL_TYPE_MONEY.equals(type)) {
                if (tWheatAccount.getMoneyFreeze() < amount) {
                    throw new ServiceException(ExceptionConstants.Business.CHECK_ERR, "理财冻结当前余额不足");
                }
                //理财，解冻
                tWheatAccount.setMoneyFreeze(tWheatAccount.getMoneyFreeze() - amount);
            }
            if (InvestConstants.Account.DETAIL_TYPE_GRANARY.equals(type)) {
                if (tWheatAccount.getGranaryFreeze() < amount) {
                    throw new ServiceException(ExceptionConstants.Business.CHECK_ERR, "粮仓冻结当前余额不足");
                }
                //粮仓，解冻   当前冻结金额-变更金额
                tWheatAccount.setGranaryFreeze(tWheatAccount.getGranaryFreeze() - amount);

                if (InvestConstants.Account.DETAIL_SUBTYPE_BUY.equals(subType)) {
                    //已购买粮仓解冻：执行中冻结金额-变更金额
                    tWheatAccount.setGranaryIngFreeze(tWheatAccount.getGranaryIngFreeze()-amount);
                    afterGranaryIngFreeze = tWheatAccount.getGranaryIngFreeze();
                }
            }
        }
        //冻结
        if (InvestConstants.Account.DETAIL_OPERTYPE_FREEZE.equals(operType)) {
            amountDetail = -1 * amount;
            if (tWheatAccount.getAvailable() < amount) {
                throw new ServiceException(ExceptionConstants.Business.CHECK_ERR, "当前可用余额不足，不能冻结");
            }
            // 冻结金额
            tWheatAccount.setFreeze(tWheatAccount.getFreeze() + amount);
            // 可用金额
            tWheatAccount.setAvailable(tWheatAccount.getAvailable() - amount);
            if (InvestConstants.Account.DETAIL_TYPE_MONEY.equals(type)) {
                //理财，冻结
                tWheatAccount.setMoneyFreeze(tWheatAccount.getMoneyFreeze() + amount);
            } else if (InvestConstants.Account.DETAIL_TYPE_GRANARY.equals(type)) {
                //粮仓，冻结
                if (subType == null || InvestConstants.Account.DETAIL_SUBTYPE_QUEUE.equals(subType)) {
                    tWheatAccount.setGranaryFreeze(tWheatAccount.getGranaryFreeze() + amount);
                    afterGranaryIngFreeze = beforeGranaryIngFreeze;
                } else if (InvestConstants.Account.DETAIL_SUBTYPE_BUY.equals(subType)) {
                    //已购买粮仓冻结：执行中冻结金额+变更金额
                    tWheatAccount.setGranaryIngFreeze(tWheatAccount.getGranaryIngFreeze()+amount);
                    afterGranaryIngFreeze = tWheatAccount.getGranaryIngFreeze();
                }
            }
        }
        //提现
        if (InvestConstants.Account.DETAIL_OPERTYPE_WITHDRAW.equals(operType)) {
            amountDetail = -1 * amount;
            if (tWheatAccount.getAvailable() < amount) {
                throw new ServiceException(ExceptionConstants.Business.CHECK_ERR, "可用余额不足");
            }
            // 总金额
            tWheatAccount.setTotal(tWheatAccount.getTotal() - amount);
            // 可用金额
            tWheatAccount.setAvailable(tWheatAccount.getAvailable() - amount);
            afterTotal = tWheatAccount.getTotal();
        }
        //充值
        if (InvestConstants.Account.DETAIL_OPERTYPE_ADD.equals(operType)) {
            amountDetail = amount;
            // 总金额
            tWheatAccount.setTotal(tWheatAccount.getTotal() + amount);
            // 可用金额
            tWheatAccount.setAvailable(tWheatAccount.getAvailable() + amount);
            afterTotal = tWheatAccount.getTotal();
        }
        //利息 & 奖金
        if (InvestConstants.Account.DETAIL_OPERTYPE_INTEREST.equals(operType) || InvestConstants.Account.DETAIL_OPERTYPE_BONUS.equals(operType)) {
            amountDetail = amount;
            // 总金额
            tWheatAccount.setTotal(tWheatAccount.getTotal() + amount);
            // 可用金额
            tWheatAccount.setAvailable(tWheatAccount.getAvailable() + amount);
            afterTotal = tWheatAccount.getTotal();
        }


        //校验执行中粮仓冻结金额 与 最大执行中粮仓冻结金额比较
        if (tWheatAccount.getGranaryIngFreeze() > tWheatAccount.getGranaryIngMaxFreeze()) {
            //修改最大执行中粮仓冻结金额为当前执行中粮仓冻结金额
            tWheatAccount.setGranaryIngMaxFreeze(tWheatAccount.getGranaryIngFreeze());
        }

        //更新账户表
        this.update(tWheatAccount);

        //插入账户明细
        TWheatAccountDetail tWheatAccountDetail = new TWheatAccountDetail();
        tWheatAccountDetail.setAcctId(tWheatAccount.getId());
        tWheatAccountDetail.setAmount(amountDetail);
        tWheatAccountDetail.setMemberId(tWheatAccount.getMemberId());
        tWheatAccountDetail.setChangeType(operType);//1、解冻  2、冻结  3、提现  4、充值  5利息　6奖金
        tWheatAccountDetail.setType(type);//0、其他 1、理财，2、粮仓
        tWheatAccountDetail.setRemark(remark);//备注
        tWheatAccountDetail.setBeforeTotal(beforeTotal);
        tWheatAccountDetail.setAfterTotal(afterTotal);
        tWheatAccountDetail.setBeforeGranaryIngFreeze(beforeGranaryIngFreeze);
        tWheatAccountDetail.setAfterGranaryIngFreeze(afterGranaryIngFreeze);
        this.itWheatAccountDetailBusiSV.insert(tWheatAccountDetail);

        return tWheatAccount;
    }

    /**
     * 修改账户信息(开始执行粮仓)
     *
     * @param memberId
     * @param amount   （金额）
     * @return
     */
    @Override
    public TWheatAccount modifyAcctRunInvest(String memberId, Long amount) {
        log.info("修改账户信息参数memberId：{}", memberId);
        log.info("修改账户信息参数amount：{}", amount);
        //查询账户
        TWheatAccount tWheatAccount = this.selectByMember(memberId);
        if (tWheatAccount == null) {
            throw new ServiceException(ExceptionConstants.Param.RESULT_NULL, "当前会员ID查询不到账户信息");
        }

        //变更前资产总额
        Long beforeTotal = tWheatAccount.getTotal();
        //变更后资产总额
        Long afterTotal = tWheatAccount.getTotal();

        //变更前_执行中粮仓冻结金额
        Long beforeGranaryIngFreeze = tWheatAccount.getGranaryIngFreeze();
        //变更后_执行中粮仓冻结金额
        Long afterGranaryIngFreeze = tWheatAccount.getGranaryIngFreeze();

        Long amountDetail = 0L;

        tWheatAccount.setGranaryIngFreeze(tWheatAccount.getGranaryIngFreeze()+amount);
        afterGranaryIngFreeze = tWheatAccount.getGranaryIngFreeze();

        //校验执行中粮仓冻结金额 与 最大执行中粮仓冻结金额比较
        if (tWheatAccount.getGranaryIngFreeze() > tWheatAccount.getGranaryIngMaxFreeze()) {
            //修改最大执行中粮仓冻结金额为当前执行中粮仓冻结金额
            tWheatAccount.setGranaryIngMaxFreeze(tWheatAccount.getGranaryIngFreeze());
        }

        //更新账户表
        this.update(tWheatAccount);

        //插入账户明细
        TWheatAccountDetail tWheatAccountDetail = new TWheatAccountDetail();
        tWheatAccountDetail.setAcctId(tWheatAccount.getId());
        tWheatAccountDetail.setAmount(amountDetail);
        tWheatAccountDetail.setMemberId(tWheatAccount.getMemberId());
        tWheatAccountDetail.setChangeType(InvestConstants.Account.DETAIL_OPERTYPE_FREEZE);//1、解冻  2、冻结  3、提现  4、充值  5利息
        tWheatAccountDetail.setType(InvestConstants.Account.DETAIL_TYPE_GRANARY);//0、其他 1、理财，2、粮仓
        tWheatAccountDetail.setRemark("开始执行计划，修改执行中计划冻结金额");//备注
        tWheatAccountDetail.setBeforeTotal(beforeTotal);
        tWheatAccountDetail.setAfterTotal(afterTotal);
        tWheatAccountDetail.setBeforeGranaryIngFreeze(beforeGranaryIngFreeze);
        tWheatAccountDetail.setAfterGranaryIngFreeze(afterGranaryIngFreeze);
        this.itWheatAccountDetailBusiSV.insert(tWheatAccountDetail);

        return tWheatAccount;
    }

    /**
     * 获取所有帐户信息到map
     * @return
     */
    @Override
    public Map<String, TWheatAccount> getAllAccount2Map() {
        Map<String, TWheatAccount> map = new HashMap<>();
        List<TWheatAccount> accounts = this.queryList(new TWheatAccount(), null, null);
        for (TWheatAccount tmp : accounts) {
            map.put(tmp.getMemberId(), tmp);
        }
        return map;
    }

    /**
     * 获取会员下所有子节点的最大冻结金额之和
     *
     * @param tWheatMemberTree
     * @param accountMap
     * @return
     */
    @Override
    public Long getAccountAllChildMaxFreeingSum(TWheatMemberTree tWheatMemberTree, Map<String, TWheatAccount> accountMap) {
        if (tWheatMemberTree != null && tWheatMemberTree.getChildList() != null) {
            Long sum = 0L;

            //遍历会员表，获取所有子节点最大帐户冻结金额
            for (TWheatMemberTree tmp : tWheatMemberTree.getChildList()) {
                if (accountMap.get(tmp.getMemberId()) != null) {
                    sum += accountMap.get(tmp.getMemberId()).getGranaryIngMaxFreeze();
                    sum += this.getAccountAllChildMaxFreeingSum(tmp, accountMap);
                }
            }
            return sum;
        }
        return 0L;
    }

    /**
     * 获取会员下所有子节点的最大冻结金额之和
     *
     * @param tWheatMemberTree
     * @param accountMap
     * @return
     */
    @Override
    public Long getAccountAllChildMaxFreeingSum(String memberId, TWheatMemberTree tWheatMemberTree, Map<String, TWheatAccount> accountMap) {
        //获取到会员信息
        TWheatMemberTree tWheatMemberTreeSub = this.itWheatMemberBusiSV.getChildTree(memberId, tWheatMemberTree);
        return this.getAccountAllChildMaxFreeingSum(tWheatMemberTreeSub, accountMap);
    }


    @Override
    public List<TWheatAccount> queryList(TWheatAccount tWheatAccount, List<String> memberIdInList, Map<String, Object> map) {
        TWheatAccountExample example = new TWheatAccountExample();
        TWheatAccountExample.Criteria criteria = example.createCriteria();

        if (tWheatAccount != null) {
            if (StringUtils.isNotEmpty(tWheatAccount.getMemberId())) {
                criteria.andMemberIdEqualTo(tWheatAccount.getMemberId());
            }
            if (null != tWheatAccount.getId()) {
                criteria.andIdEqualTo(tWheatAccount.getId());
            }
        }

        if (memberIdInList != null && !memberIdInList.isEmpty()) {
            criteria.andMemberIdIn(memberIdInList);
        }

        return this.tWheatAccountMapper.selectByExample(example);
    }
}
