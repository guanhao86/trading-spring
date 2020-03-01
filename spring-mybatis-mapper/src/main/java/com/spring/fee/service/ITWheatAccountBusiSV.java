package com.spring.fee.service;

import com.spring.fee.model.TWheatAccount;
import com.spring.fee.model.TWheatMemberTree;

import java.util.List;
import java.util.Map;

/**
 * 会员账户表
 */
public interface ITWheatAccountBusiSV {

    TWheatAccount insert(TWheatAccount tWheatAccount);

    TWheatAccount update(TWheatAccount tWheatAccount);

    TWheatAccount delete(TWheatAccount tWheatAccount);

    TWheatAccount select(TWheatAccount tWheatAccount);

    List<TWheatAccount> queryList(TWheatAccount tWheatAccount, List<String> memberIdInList, Map<String, Object> map);

    TWheatAccount selectByMember(String memberId);

    /**
     * 修改账户信息
     * @param memberId
     * @param amount（金额， 单位元）
     * @param operType 类型：1、解冻  2、冻结  3、提现  4、充值  5、奖金（利息）
     * @param operType 类型：0、其他 1、理财，2、粮仓
     * @param subType 子类型：type=粮仓: 1、购买，2、排队
     * @return
     */
    TWheatAccount modifyAcct(String memberId, Long amount, String operType, String type, String subType, String remark);

    /**
     * 修改账户信息(开始执行粮仓)
     *
     * @param memberId
     * @param amount   （金额）
     * @return
     */
    TWheatAccount modifyAcctRunInvest(String memberId, Long amount);

    /**
     * 获取所有帐户信息到map
     * @return
     */
    Map<String, TWheatAccount> getAllAccount2Map();

    /**
     * 获取会员下所有子节点的最大冻结金额之和
     * @return
     */
    Long getAccountAllChildMaxFreeingSum(TWheatMemberTree tWheatMemberTree, Map<String, TWheatAccount> accountMap);

    /**
     * 获取会员下所有子节点的最大冻结金额之和
     * @return
     */
    Long getAccountAllChildMaxFreeingSum(String memberId, TWheatMemberTree tWheatMemberTree, Map<String, TWheatAccount> accountMap);
}
