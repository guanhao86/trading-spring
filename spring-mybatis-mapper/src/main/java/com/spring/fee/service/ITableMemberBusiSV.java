package com.spring.fee.service;

import com.github.pagehelper.PageInfo;
import com.spring.fee.model.*;
import jnr.ffi.annotations.In;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Map;

/**
 * 会员管理表服务
 */
public interface ITableMemberBusiSV {

    TableMember insert(TableMember bo);

    TableMember update(TableMember bo, boolean updateArrange);

    TableMember updateSimple(TableMember bo);

    TableMember delete(TableMember bo);

    TableMember renew(TableMember bo);

    TableMember select(TableMember bo);

    TableMember selectByMemberId(String memberId);

    /**
     * 购买订单时使用，不校验会员状态
     * @param memberId
     * @return
     */
    TableMember selectByMemberId4BuyOrder(String memberId);
    /**
     * 获取会员下一级子会员
     * @param memberId
     * @return
     */
    List<TableMember> queryChildList(String memberId);

    /**
     * 获取所有子节点会员
     * 递归
     * @param memberId
     * @return
     */
    List<TableMember> queryAllChildList(String memberId);

    /**
     * 获取所有子节点会员 （会员加载到Map）
     * @param memberId
     * @param map
     * @return
     */
    List<TableMember> queryAllChildList(String memberId, Map<String, List<TableMember>> map);

    /**
     * 获取所有子节点会员
     * 递归
     * @param memberId
     * @return
     */
    PageInfo<TableMember> queryAllChildPage(String memberId, Integer pageNum, Integer pageSize, Map<String, Object> map);

    /**
     * 获取所有子节点会员(TREE)
     * 递归
     * 不包括会员本身
     * @param tableMemberTree
     * @return
     */
    TableMemberTree queryAllChildTree(TableMemberTree tableMemberTree);

    /**
     * 获取所有子节点会员(TREE)（会员加载到Map）
     * 递归
     * 不包括会员本身
     * @param tableMemberTree
     * @return
     */
    TableMemberTree queryAllChildTree(TableMemberTree tableMemberTree, Map<String, List<TableMember>> map);

    /**
     * 查询指定tree
     * @param memberId
     * @return
     */
    TableMemberTree getChildTree(String memberId, TableMemberTree tableMemberTree);

    /**
     * 获取所有子节点会员ID列表
     * 递归
     * 不包括会员本身
     * @param memberId
     * @return
     */
    List<String> queryAllChildMemberIdList(String memberId);

    TableMember selectByPhone(String phone);

    List<TableMember> queryList(TableMember bo);

    List<TableMember> queryList(TableMember bo, Map<String, Object> map);

    Map<String, TableMember> queryListMap(TableMember bo);

    Map<String, List<TableMember>> queryArrangeListMap(TableMember bo);

    /**
     * parent 是否是bo的父节点，可跨级别
     * @param bo
     * @param parentMemberId
     * @return
     */
    boolean checkParent(TableMember bo, String parentMemberId);

    PageInfo<TableMember> queryListPage(TableMember bo, Integer pageNum, Integer pageSize, Map<String, Object> map);

    /**
     * 注册
     * @param bo
     * @return
     */
    public TableMember regist(TableMember bo, int registerFrom);

    /**
     * 修改密码（密码为空则为密码重置）
     * @param bo
     * @return
     */
    public TableMember changePwd(TableMember bo, String oldPassword);

    /**
     * 实名认证审核
     * @return
     */
    TableMember audit(Integer id, Integer autFlag);

    /**
     * 导出会员文件
     * @param bo
     * @param pageNum
     * @param pageSize
     * @param map
     * @return
     */
    HSSFWorkbook exportFile(TableMember bo, Integer pageNum, Integer pageSize, Map<String, Object> map);

    /**
     * 统计金额
     */
    List<TableMember> statisticMoney();

    /**
     * 更新是否允许登陆状态 1允许 0不允许
     * @param memberId
     * @param state
     * @return
     */
    void updateLoginState(String memberId, String state);
}
