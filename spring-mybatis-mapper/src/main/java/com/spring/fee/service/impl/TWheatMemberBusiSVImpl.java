package com.spring.fee.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.fee.constants.InvestConstants;
import com.spring.fee.dao.mapper.TWheatMemberMapper;
import com.spring.fee.dao.mapper.TWheatMemberMapperDZ;
import com.spring.fee.model.*;
import com.spring.fee.service.ITInvestMemberUpConfigBusiSV;
import com.spring.fee.service.ITInvestPlanMainBusiSV;
import com.spring.fee.service.ITWheatAccountBusiSV;
import com.spring.fee.service.ITWheatMemberBusiSV;
import com.spring.free.util.DateUtils;
import com.spring.free.util.exception.ExceptionConstants;
import com.spring.free.util.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 会员服务
 */
@Slf4j
@Service
@Transactional
public class TWheatMemberBusiSVImpl implements ITWheatMemberBusiSV {

    @Autowired
    TWheatMemberMapper iWheatMemberMapper;

    @Autowired
    TWheatMemberMapperDZ iWheatMemberMapperDZ;

    @Autowired
    ITWheatAccountBusiSV itWheatAccountBusiSV;

    @Autowired
    ITInvestMemberUpConfigBusiSV itInvestMemberUpConfigBusiSV;

    @Autowired
    ITInvestPlanMainBusiSV itInvestPlanMainBusiSV;

    @Autowired
    ITWheatMemberBusiSV itWheatMemberBusiSV;

    class MemberAcct{
        String memberId;
        long amount;

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public long getAmount() {
            return amount;
        }

        public void setAmount(long amount) {
            this.amount = amount;
        }
    }

    @Override
    public TWheatMember insert(TWheatMember tWheatMember) {
        tWheatMember.setCreateTime(DateUtils.getSysDate());
        if (1 == this.iWheatMemberMapper.insert(tWheatMember)) {
            return tWheatMember;
        }
        return null;
    }

    @Override
    public TWheatMember update(TWheatMember tWheatMember) {
        if (1 == iWheatMemberMapper.updateByPrimaryKeySelective(tWheatMember)) {
            return tWheatMember;
        }
        return null;
    }

    @Override
    public TWheatMember updatebyMemberId(TWheatMember tWheatMember) {
        TWheatMemberExample example = new TWheatMemberExample();
        TWheatMemberExample.Criteria criteria = example.createCriteria();

        criteria.andMemberIdEqualTo(tWheatMember.getMemberId());
        if (1 == iWheatMemberMapper.updateByExampleSelective(tWheatMember, example)) {
            return tWheatMember;
        }
        return null;
    }

    @Override
    public TWheatMember delete(TWheatMember tWheatMember) {
        if (1 == iWheatMemberMapper.deleteByPrimaryKey(tWheatMember.getId())) {
            return tWheatMember;
        }
        return null;
    }

    /**
     * 获取会员信息详情
     *
     * @param tWheatMember
     * @return
     */
    @Override
    public TWheatMember select(TWheatMember tWheatMember) {
        return iWheatMemberMapper.selectByPrimaryKey(tWheatMember.getId());
    }

    /**
     * 获取会员信息详情
     *
     * @param memberId
     * @return
     */
    @Override
    public TWheatMember selectByMemberId(String memberId) {
        TWheatMemberExample example = new TWheatMemberExample();
        TWheatMemberExample.Criteria criteria = example.createCriteria();

        criteria.andMemberIdEqualTo(memberId);

        List<TWheatMember> tWheatMemberList = this.iWheatMemberMapper.selectByExample(example);
        if (tWheatMemberList != null && tWheatMemberList.size() == 1) {
            return tWheatMemberList.get(0);
        }
        return null;
    }

    /**
     * 获取父节点
     *
     * @param memberId
     * @return
     */
    @Override
    public TWheatMember selectParentMember(String memberId) {

        TWheatMember tWheatMember = this.selectByMemberId(memberId);
        if (tWheatMember == null) {
            return null;
        }
        return this.selectByMemberId(tWheatMember.getReferenceId());
    }

    /**
     * 获取会员信息详情
     *
     * @param phone
     * @return
     */
    @Override
    public TWheatMember selectByPhone(String phone) {
        TWheatMemberExample example = new TWheatMemberExample();
        TWheatMemberExample.Criteria criteria = example.createCriteria();

        criteria.andPhoneEqualTo(phone);

        List<TWheatMember> tWheatMemberList = this.iWheatMemberMapper.selectByExample(example);
        if (tWheatMemberList != null && tWheatMemberList.size() == 1) {
            return tWheatMemberList.get(0);
        }
        return null;
    }

    public TWheatMember selectByReferenceId(String referenceId){
        TWheatMemberExample example = new TWheatMemberExample();
        TWheatMemberExample.Criteria criteria = example.createCriteria();

        criteria.andReferenceIdEqualTo(referenceId);

        List<TWheatMember> tWheatMemberList = this.iWheatMemberMapper.selectByExample(example);
        if (tWheatMemberList != null && tWheatMemberList.size() == 1) {
            return tWheatMemberList.get(0);
        }
        return null;
    }

    /**
     * 获取一级子节点列表
     * @param memberId
     * @return
     */
    @Override
    public List<TWheatMember> queryChildList(String memberId) {
        TWheatMemberDZ tWheatMember = new TWheatMemberDZ();
        tWheatMember.setReferenceId(memberId);
        return this.queryList(tWheatMember, null);
    }

    /**
     * 获取所有子节点会员
     * 递归
     * 不包括会员本身
     * @param memberId
     * @return
     */
    @Override
    public List<TWheatMember> queryAllChildList(String memberId) {
        List<TWheatMember> list = new ArrayList<>();

        List<TWheatMember> childList = this.queryChildList(memberId);

        for (TWheatMember tWheatMember : childList) {
            list.add(tWheatMember);
            list.addAll(this.queryAllChildList(tWheatMember.getMemberId()));
        }

        return list;
    }

    /**
     * 获取所有子节点会员(TREE)
     * 递归
     * 不包括会员本身
     * @param tWheatMemberTree
     * @return
     */
    @Override
    public TWheatMemberTree queryAllChildTree(TWheatMemberTree tWheatMemberTree) {

        //log.info("获取所有子节点会员(TREE)");

        List<TWheatMemberTree> list = new ArrayList<>();

        List<TWheatMember> childList = this.queryChildList(tWheatMemberTree.getMemberId());

        for (TWheatMember tWheatMember : childList) {
            TWheatMemberTree tWheatMemberTree1 = new TWheatMemberTree();
            BeanUtils.copyProperties(tWheatMember, tWheatMemberTree1);
            list.add(tWheatMemberTree1);
            tWheatMemberTree.setChildList(list);
            this.queryAllChildTree(tWheatMemberTree1);
        }

        return tWheatMemberTree;
    }

    /**
     * 查询指定tree
     *
     * @param memberId
     * @param tWheatMemberTree
     * @return
     */
    @Override
    public TWheatMemberTree getChildTree(String memberId, TWheatMemberTree tWheatMemberTree) {
        if (tWheatMemberTree != null) {
            if (memberId.equals(tWheatMemberTree.getMemberId())) {
                return tWheatMemberTree;
            } else {
                if (tWheatMemberTree.getChildList() != null) {
                    for (TWheatMemberTree tmp : tWheatMemberTree.getChildList()) {
                        TWheatMemberTree result = this.getChildTree(memberId, tmp);
                        if (result != null)
                            return result;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 获取所有子节点会员ID列表
     * 递归
     * 不包括会员本身
     *
     * @param memberId
     * @return
     */
    @Override
    public List<String> queryAllChildMemberIdList(String memberId) {
        List<String> memberIdList = new ArrayList<>();
        List<TWheatMember> list = this.queryAllChildList(memberId);
        for (TWheatMember tWheatMember : list) {
            memberIdList.add(String.valueOf(tWheatMember.getMemberId()));
        }
        return memberIdList;
    }

    /**
     * 升级全部会员（根节点为0，循环调用upLevelV2方法）
     */
    @Override
    public void upLevelV2ALL(){
        log.info("==================================================开始会员升级===========================================");
        //获取所有会员
        List<TWheatMember> allList = this.queryChildList("0");
        log.info("根目录会员列表：{}", JSON.toJSON(allList));
        //获取帐户信息
        Map<String, TWheatAccount> accountMap = itWheatAccountBusiSV.getAllAccount2Map();
        //获取升级配置
        Map<String, TInvestMemberUpConfig> upMap = itInvestMemberUpConfigBusiSV.queryMap();
        for (TWheatMember tWheatMember : allList) {
            //获取所有会员TREE
            TWheatMemberTree tWheatMemberTree = new TWheatMemberTree();
            BeanUtils.copyProperties(tWheatMember, tWheatMemberTree);
            this.queryAllChildTree(tWheatMemberTree);
            upLevelV2(tWheatMemberTree, accountMap, upMap);
        }

    }
    /**
     * 递归升级树下所有会员（具体升级方案，调用upLevelV2Exe）
     * @param tWheatMemberTree
     * @param accountMap
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void upLevelV2(TWheatMemberTree tWheatMemberTree, Map<String, TWheatAccount> accountMap, Map<String, TInvestMemberUpConfig> upMap){
        try {
            //获取所有会员TREE
            this.upLevelV2Exe(tWheatMemberTree, accountMap, upMap);
        }catch (Exception e) {
            e.printStackTrace();
        }
        if (null != tWheatMemberTree.getChildList()) {
            for (TWheatMemberTree child : tWheatMemberTree.getChildList()) {
                try {
                    this.upLevelV2(child, accountMap, upMap);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 指定会员，从下向上升级逐个升级，对于不涉及业绩的升级
     *
     * @param memberId
     */
    @Override
    public void upLevelFromLow2HighV2(String memberId) {
        //购买升级
        Map<String, TInvestMemberUpConfig> upMap = this.itInvestMemberUpConfigBusiSV.queryMap();
        TWheatMemberTree tWheatMemberTree = new TWheatMemberTree();
        TWheatMember tWheatMember = this.itWheatMemberBusiSV.selectByMemberId(memberId);
        if (tWheatMember != null) {
            BeanUtils.copyProperties(tWheatMember, tWheatMemberTree);
            this.itWheatMemberBusiSV.upLevelV2Exe(tWheatMemberTree, null, upMap);
            //继续升级推荐人
            this.upLevelFromLow2HighV2(tWheatMember.getReferenceId());
        }
    }

    /**
     * 升级方案。具体实现
     * @param tWheatMemberTree
     * @param accountMap
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean upLevelV2Exe(TWheatMemberTree tWheatMemberTree, Map<String, TWheatAccount> accountMap, Map<String, TInvestMemberUpConfig> upMap){

        log.info("会员ID：{}", tWheatMemberTree.getMemberId());
        boolean upFlag = false;

        if (upMap == null) {
            upMap = this.itInvestMemberUpConfigBusiSV.queryMap();
        }

        int upLevel = Integer.parseInt(tWheatMemberTree.getLevel()) + 1;

        //根据不同级别进行升级
        log.info("要升级到的级别：{}", upLevel);

        TInvestMemberUpConfig tInvestMemberUpConfig = upMap.get(String.valueOf(upLevel));
        if (null == tInvestMemberUpConfig) {
            log.info("要升级到的配置为空，不能继续升级，跳出递归");
            return upFlag;
        }

        log.info("要升级到的级别配置：{}", JSON.toJSONString(tInvestMemberUpConfig));

        try {


            if (InvestConstants.MemberConfig.UP_LEVEL_TYPE_REG.equals(tInvestMemberUpConfig.getUpType())) {
                log.info("注册升级，暂时不使用");
            } else if (InvestConstants.MemberConfig.UP_LEVEL_TYPE_BUY.equals(tInvestMemberUpConfig.getUpType())) {
                log.info("购买粮仓计划升级");
                //查询用户是否购买了粮仓
                TInvestPlanMainDZ tInvestPlanMain = new TInvestPlanMainDZ();
                tInvestPlanMain.setMemberId(tWheatMemberTree.getMemberId());
                if (!CollectionUtils.isEmpty(this.itInvestPlanMainBusiSV.queryList(tInvestPlanMain))) {
                    upFlag = true;
                }
            } else if (InvestConstants.MemberConfig.UP_LEVEL_TYPE_FORWARD.equals(tInvestMemberUpConfig.getUpType())) {
                log.info("推荐升级");
                //查询用户是否推荐
                /**
                 * 规则配置：  推荐级别|推荐个数
                 */
                List<TWheatMember> childList = this.queryChildList(tWheatMemberTree.getMemberId());
                if (!CollectionUtils.isEmpty(childList)) {
                    String config[] = tInvestMemberUpConfig.getUpCondition().split("\\|");
                    int count = 0;
                    for (TWheatMember tmp : childList) {
                        if (config[0].equals(tmp.getLevel())) {
                            count++;
                        }
                    }

                    if (count >= Integer.parseInt(config[1])) {
                        upFlag = true;
                    } else {
                        log.info("推荐数量不达标：[推荐数量：" + count + ", 要求数量：" + config[1] + "]");
                    }

                }
            } else if (InvestConstants.MemberConfig.UP_LEVEL_TYPE_MONEY.equals(tInvestMemberUpConfig.getUpType()) && tInvestMemberUpConfig.getUpCondition() != null && accountMap != null) {
                log.info("绩效达额升级");
                //遍历一级子节点的业绩
                List<MemberAcct> maxList = new ArrayList<>();
                if (!CollectionUtils.isEmpty(tWheatMemberTree.getChildList())) {
                    for (TWheatMemberTree child : tWheatMemberTree.getChildList()) {
                        long maxFreezing = itWheatAccountBusiSV.getAccountAllChildMaxFreeingSum(child, accountMap);
                        MemberAcct memberAcct = new MemberAcct();
                        memberAcct.setMemberId(child.getMemberId());
                        memberAcct.setAmount(maxFreezing);
                        maxList.add(memberAcct);
                    }
                    //根据业绩排序
                    /*
                     * 排序
                     */
                    log.info("一级子会员业绩列表：{}", JSONObject.toJSON(maxList));
                    Collections.sort(maxList, new Comparator<MemberAcct>() {
                        @Override
                        public int compare(MemberAcct o1, MemberAcct o2) {
                            //逆序
                            return (int) (o2.amount - o1.amount);
                        }
                    });
                    log.info("一级子会员业绩列表(排序后)：{}", JSONObject.toJSON(maxList));

                    //配置达额要求  例如 90000｜900000｜900000  表示 前两个业绩达到9万，最后一个数字其他用户达到9万
                    String moneyList[] = tInvestMemberUpConfig.getUpCondition().split("\\|");
                    if (maxList.size() < moneyList.length) {
                        log.info("直推会员数量小于配置所需");
                    } else if (moneyList == null) {
                        log.info("升级配置错误");
                    } else {
                        for (int i = 0; i < moneyList.length - 1; i++) {
                            MemberAcct memberAcct = maxList.get(i);
                            if (memberAcct.getAmount() < Long.parseLong(moneyList[i])) {
                                log.info("第" + i + "个直推会员业绩未达标，[要求：" + moneyList[i] + "，实际：" + memberAcct.getAmount() + "]");
                                break;
                            }
                        }
                        long otherMoneySum = 0;
                        for (int j = moneyList.length - 1; j < maxList.size(); j++) {
                            otherMoneySum += maxList.get(j).getAmount();
                        }
                        if (otherMoneySum >= Long.parseLong(moneyList[moneyList.length - 1])) {
                            upFlag = true;
                        }
                    }
                }
            }
            log.info("是否允许升级：{}", upFlag);
            //true:允许升级
            if (upFlag) {
                TWheatMember tWheatMember = new TWheatMember();
                tWheatMember.setMemberId(tWheatMemberTree.getMemberId());
                tWheatMember.setLevel(String.valueOf(upLevel));
                this.updatebyMemberId(tWheatMember);
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            //升级完当前级别，继续向上升级判断
            tWheatMemberTree.setLevel(String.valueOf(upLevel));
            this.upLevelV2Exe(tWheatMemberTree, accountMap, upMap);
            return upFlag;
        }
    }



    /**
     * 升级该会员（返回升级后会员信息，不修改数据库）
     *
     * @param tWheatMember
     * @param tInvestMemberUpConfig 会员升级配置，作为入参传进来，避免多次查询数据库(要升级到的级别配置)
     *                              如：当前会员级别VIP1  则配置信息查询VIP2的配置数据
     * @return 返回升级后的会员对象
     */
    @Override
    public TWheatMember upLevelByMemberId(TWheatMember tWheatMember, TInvestMemberUpConfig tInvestMemberUpConfig) {
        //子节点中，比当前用户低一级别的会员数量
        int lowLevelCount = 0;
        //所有子节点中的冻结金额
        int allClildAmount = 0;

        //查询会员信息(待升级会员信息)
        log.info("查询会员信息(待升级会员信息)");
        TWheatMember upMember = this.select(tWheatMember);

        int conditionY = tInvestMemberUpConfig.getConditionY();//获取晋升需要的会员数量
        log.info("会员晋升需要直推前一级别会员数量：{}", conditionY);
        if (conditionY > 0) {
            //获取一级子会员列表
            log.info("获取一级子会员列表");
            List<TWheatMember> childList = this.queryChildList(upMember.getMemberId());
            if (childList != null && !childList.isEmpty()) {
                for (TWheatMember child : childList) {
                    //子会员级别>=要升级到的级别的前一级。可以解决用户跳级升级
                    if (Integer.parseInt(child.getLevel()) >= tInvestMemberUpConfig.getLevelId()) {
                        lowLevelCount++;
                    }
                }
            } else {
                //没有子会员，不升级
                log.info("获取一级子会员列表，没有子会员，不升级");
                throw new ServiceException(ExceptionConstants.Param.RESULT_NULL, "获取一级子会员列表，没有子会员，不升级");
            }

            log.info("会员直推前一级别会员数量：{}", lowLevelCount);
            if (lowLevelCount < conditionY) {
                log.info("直推前一级别会员数量不达标，不升级");
                throw new ServiceException(ExceptionConstants.Business.CHECK_ERR, "直推前一级别会员数量不达标，不升级");
            }
        }else{
            log.info("不校验直推前一级别会员数量");
        }

        long conditionX = tInvestMemberUpConfig.getConditionX();//获取晋升需要的条件X（伞下业绩）

        if (conditionX > 0) {
            //获取所有子节点会员ID列表
            List<String> allChildMemberIdList = this.queryAllChildMemberIdList(upMember.getMemberId());
            if (allChildMemberIdList == null || allChildMemberIdList.isEmpty()) {
                //没有子会员，不升级
                log.info("获取所有子节点会员ID列表，没有子会员，不升级");
                throw new ServiceException(ExceptionConstants.Param.RESULT_NULL, "获取所有子节点会员ID列表，没有子会员，不升级");
            }

            //所有子节点中的历史最大粮仓执行中冻结金额
            List<TWheatAccount> childAllAccountList = this.itWheatAccountBusiSV.queryList(null, allChildMemberIdList, null);

            if (childAllAccountList == null || childAllAccountList.isEmpty()) {
                //没有子会员账户，不升级
                log.info("所有子节点中的历史最大粮仓执行中冻结金额，没有子会员账户，不升级");
                throw new ServiceException(ExceptionConstants.Param.RESULT_NULL, "所有子节点中的历史最大粮仓执行中冻结金额，没有子会员账户，不升级");
            }

            for (TWheatAccount account : childAllAccountList) {
                if (account.getGranaryIngMaxFreeze() != null) {
                    allClildAmount += account.getGranaryIngMaxFreeze();
                }
            }

            log.info("所有子节点中的历史最大粮仓执行中冻结金额总和-伞下业绩(单位厘)为{}", allClildAmount);


            if (allClildAmount < conditionX) {
                log.info("伞下业绩不达标，不升级");
                throw new ServiceException(ExceptionConstants.Business.CHECK_ERR, "伞下业绩不达标，不升级");
            }
        } else {
            log.info("不校验伞下业绩");
        }

        upMember.setLevel(upMember.getLevel() + 1);

        return upMember;
    }

    @Override
    public PageInfo<TWheatMember> pageList(TWheatMemberDZ entity, Integer page, Integer pageSize) {
        if(page != null && pageSize != null){
            PageHelper.startPage(page, pageSize);
        }
        List<TWheatMember> findList = iWheatMemberMapper.list(entity);
        return new PageInfo<TWheatMember>(findList);
    }

    @Override
    public List<TWheatMemberGroupDZ> getLevelGroupCountAll(TWheatMemberDZ record) {
        return iWheatMemberMapperDZ.getLevelGroupCountAll(record);
    }

    /**
     * 获取直推人数(级别分组)
     *
     * @param memberId
     * @return
     */
    @Override
    public List<TWheatMemberGroupDZ> getFirstChildCountByLevel(String memberId) {
        //返回体
        TWheatMemberDZ tWheatMemberDZ = new TWheatMemberDZ();
        tWheatMemberDZ.setMemberId(memberId);
        return this.getLevelGroupCountAll(tWheatMemberDZ);
    }

    /**
     * 获取直推人数(级别分组).,当天
     *
     * @param memberId
     * @return
     */
    @Override
    public List<TWheatMemberGroupDZ> getFirstChildCountByLevelToday(String memberId) {
        //返回体
        TWheatMemberDZ tWheatMemberDZ = new TWheatMemberDZ();
        tWheatMemberDZ.setMemberId(memberId);
        tWheatMemberDZ.setCreateTimeStart(DateUtils.getDateZero(DateUtils.getSysDate()));
        tWheatMemberDZ.setCreateTimeEnd(DateUtils.getDateZero(DateUtils.getNextDate(DateUtils.getSysDate())));
        return this.getLevelGroupCountAll(tWheatMemberDZ);
    }

    /**
     * 获取团队人数(级别分组)
     *
     * @param tWheatMemberList
     * @return
     */
    @Override
    public List<TWheatMemberGroupDZ> getAllChildCountByLevel(List<String> tWheatMemberList) {
        //返回体
        TWheatMemberDZ tWheatMemberDZ = new TWheatMemberDZ();
        if (!CollectionUtils.isEmpty(tWheatMemberList)) {
            tWheatMemberDZ.setMemberList(tWheatMemberList);
        }else{
            tWheatMemberDZ.setMemberList(null);
        }
        return this.getLevelGroupCountAll(tWheatMemberDZ);
    }

    /**
     * 获取团队人数(级别分组)当天
     *
     * @param tWheatMemberList
     * @return
     */
    @Override
    public List<TWheatMemberGroupDZ> getAllChildCountByLevelToday( List<String> tWheatMemberList) {
        TWheatMemberDZ tWheatMemberDZ = new TWheatMemberDZ();
        if (!CollectionUtils.isEmpty(tWheatMemberList)) {
            tWheatMemberDZ.setMemberList(tWheatMemberList);
        }else{
            tWheatMemberDZ.setMemberList(null);
        }
        tWheatMemberDZ.setCreateTimeStart(DateUtils.getDateZero(DateUtils.getSysDate()));
        tWheatMemberDZ.setCreateTimeEnd(DateUtils.getDateZero(DateUtils.getNextDate(DateUtils.getSysDate())));
        return this.getLevelGroupCountAll(tWheatMemberDZ);
    }

    /**
     * 获取团队人数最多的会员
     *
     * @return
     */
    @Override
    public TWheatMemberGroupDZ hasMaxChild() {

        TWheatMemberGroupDZ result = new TWheatMemberGroupDZ();
        result.setCount(0);
        //获取根节点的直接子会员（团队人数最多的人，肯定会在这里产生）:避免存在多个根节点
        //先获取所有根节点
        List<TWheatMember> member0s = this.queryChildList("0");
        for (TWheatMember tWheatMember0 : member0s) {
            List<TWheatMember> members = this.queryChildList(tWheatMember0.getMemberId());
            for (TWheatMember tWheatMember : members) {
                //获取所有子节点
                List<String> arr = this.queryAllChildMemberIdList(tWheatMember.getMemberId());
                if (arr != null && arr.size() > result.getCount()) {
                    BeanUtils.copyProperties(tWheatMember, result);
                    result.setCount(arr.size());
                }
            }
        }

        //取直推人数
        List<TWheatMember> childList = this.queryChildList(result.getMemberId());
        if (childList != null) {
            result.setFistChildCount(childList.size());
        }

        return result;
    }

    @Override
    public PageInfo<TWheatMember> queryPage(TWheatMemberDZ entity, Integer page, Integer pageSize) {

        PageInfo<TWheatMember> pageInfo = PageHelper.startPage(page, pageSize)
                .doSelectPageInfo(() -> this.queryList(entity, null));
        log.info("获取队列列表结果：{}", JSON.toJSON(pageInfo));

        return pageInfo;
    }

    /**
     * 获取会员信息列表
     *
     * @param tWheatMember
     * @return
     */
    @Override
    public List<TWheatMember> queryList(TWheatMemberDZ tWheatMember, String[] orderBy) {
        TWheatMemberExample example = new TWheatMemberExample();
        TWheatMemberExample.Criteria criteria = example.createCriteria();

        if (null != tWheatMember.getId())
            criteria.andIdEqualTo(tWheatMember.getId());

        if (StringUtils.isNotEmpty(tWheatMember.getMemberId()))
            criteria.andMemberIdEqualTo(tWheatMember.getMemberId());

        //推荐人
        if (StringUtils.isNotEmpty(tWheatMember.getReferenceId()))
            criteria.andReferenceIdEqualTo(tWheatMember.getReferenceId());

        if (StringUtils.isNotEmpty(tWheatMember.getLevel()))
            criteria.andLevelEqualTo(tWheatMember.getLevel());

        if (StringUtils.isNotEmpty(tWheatMember.getName()))
            criteria.andNameLike("%"+tWheatMember.getName()+"%");

        if (StringUtils.isNotEmpty(tWheatMember.getPhone()))
            criteria.andPhoneEqualTo(tWheatMember.getPhone());

        if (StringUtils.isNotEmpty(tWheatMember.getRealName()))
            criteria.andRealNameLike("%"+tWheatMember.getRealName()+"%");

        if (StringUtils.isNotEmpty(tWheatMember.getIsAut()))
            criteria.andIsAutEqualTo(tWheatMember.getIsAut());

        if (null != tWheatMember.getCreateTimeStart()) {
            criteria.andCreateTimeGreaterThanOrEqualTo(tWheatMember.getCreateTimeStart());
        }

        if (null != tWheatMember.getCreateTimeEnd()) {
            criteria.andCreateTimeLessThanOrEqualTo(tWheatMember.getCreateTimeEnd());
        }

        if (null != tWheatMember.getMemberList()) {
            if (tWheatMember.getMemberList().size() == 0) {
                return new ArrayList<>();
            }
            criteria.andMemberIdIn(tWheatMember.getMemberList());
        }

        if (orderBy != null && orderBy.length > 0) {
            String strOrderBy = "";
            for (String orderByTemp : orderBy) {
                strOrderBy = strOrderBy + "," + orderByTemp;
            }
            strOrderBy.substring(1);
            example.setOrderByClause(" "+strOrderBy);
        }

        return iWheatMemberMapper.selectByExample(example);
    }

}
