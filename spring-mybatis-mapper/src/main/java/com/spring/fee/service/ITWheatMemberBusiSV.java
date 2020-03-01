package com.spring.fee.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.fee.model.*;
import com.spring.free.common.domain.AccessResponse;
import com.spring.free.util.DateUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 会员表服务
 */
public interface ITWheatMemberBusiSV {

    TWheatMember insert(TWheatMember tWheatMember);

    TWheatMember update(TWheatMember tWheatMember);

    TWheatMember updatebyMemberId(TWheatMember tWheatMember);

    TWheatMember delete(TWheatMember tWheatMember);

    TWheatMember select(TWheatMember tWheatMember);

    List<TWheatMember> queryList(TWheatMemberDZ tWheatMember, String[] orderBy);

    TWheatMember selectByMemberId(String memberId);

    /**
     * 获取父节点
     * @param memberId
     * @return
     */
    TWheatMember selectParentMember(String memberId);
    TWheatMember selectByPhone(String phone);

    TWheatMember selectByReferenceId(String referenceId);

    /**
     * 获取会员下一级子会员
     * @param memberId
     * @return
     */
    List<TWheatMember> queryChildList(String memberId);

    /**
     * 获取所有子节点会员
     * 递归
     * @param memberId
     * @return
     */
    List<TWheatMember> queryAllChildList(String memberId);

    /**
     * 获取所有子节点会员(TREE)
     * 递归
     * 不包括会员本身
     * @param tWheatMemberTree
     * @return
     */
    TWheatMemberTree queryAllChildTree(TWheatMemberTree tWheatMemberTree);

    /**
     * 查询指定tree
     * @param memberId
     * @return
     */
    TWheatMemberTree getChildTree(String memberId, TWheatMemberTree tWheatMemberTree);

    /**
     * 获取所有子节点会员ID列表
     * 递归
     * 不包括会员本身
     * @param memberId
     * @return
     */
    List<String> queryAllChildMemberIdList(String memberId);

    /**
     * 升级该会员
     * @param tWheatMember
     * @param tInvestMemberUpConfig 会员升级配置，作为入参传进来，避免多次查询数据库
     * @return 返回升级后的会员对象
     */
    TWheatMember upLevelByMemberId(TWheatMember tWheatMember, TInvestMemberUpConfig tInvestMemberUpConfig);

    /**
     * 升级全部会员（根节点为0，循环调用upLevelV2方法）
     */
    void upLevelV2ALL();

    /**
     * 递归升级树下所有会员（具体升级方案，调用upLevelV2Exe）
     * @param tWheatMemberTree
     * @param accountMap
     */
    void upLevelV2(TWheatMemberTree tWheatMemberTree, Map<String, TWheatAccount> accountMap, Map<String, TInvestMemberUpConfig> upMap);

    /**
     * 指定会员，从下向上升级逐个升级，对于不涉及业绩的升级
     * @param memberId
     */
    void upLevelFromLow2HighV2(String memberId);

    /**
     * 升级方案
     * @param tWheatMemberTree
     * @param accountMap
     */
    boolean upLevelV2Exe(TWheatMemberTree tWheatMemberTree, Map<String, TWheatAccount> accountMap, Map<String, TInvestMemberUpConfig> upMap);

    PageInfo<TWheatMember> pageList(TWheatMemberDZ entity, Integer page, Integer pageSize);

    List<TWheatMemberGroupDZ> getLevelGroupCountAll(TWheatMemberDZ record);

    /**
     * 获取直推人数(级别分组)
     * @param memberId
     * @return
     */
    List<TWheatMemberGroupDZ> getFirstChildCountByLevel(String memberId);
    /**
     * 获取直推人数(级别分组).,当天
     * @param memberId
     * @return
     */
    List<TWheatMemberGroupDZ> getFirstChildCountByLevelToday(String memberId);

    /**
     * 获取团队人数(级别分组)
     * @param tWheatMemberList
     * @return
     */
    List<TWheatMemberGroupDZ> getAllChildCountByLevel(List<String> tWheatMemberList);

    /**
     * 获取团队人数(级别分组)当天
     * @param tWheatMemberList
     * @return
     */
    List<TWheatMemberGroupDZ> getAllChildCountByLevelToday(List<String> tWheatMemberList);

    /**
     * 获取团队人数最多的会员
     * @return
     */
    TWheatMemberGroupDZ hasMaxChild();

    PageInfo<TWheatMember> queryPage(TWheatMemberDZ entity, Integer page, Integer pageSize);

//    /**
//     * 获取伞下业绩
//     * 递归
//     * 不包括会员本身
//     * @param tWheatMemberTree
//     * @return
//     */
//    TWheatMemberTree queryTeamFreeingTree(TWheatMemberTree tWheatMemberTree);
}
