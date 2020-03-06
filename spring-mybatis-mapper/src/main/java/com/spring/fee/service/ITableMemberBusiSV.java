package com.spring.fee.service;

import com.github.pagehelper.PageInfo;
import com.spring.fee.model.TWheatMember;
import com.spring.fee.model.TWheatMemberTree;
import com.spring.fee.model.TableMember;
import com.spring.fee.model.TableMemberTree;

import java.util.List;
import java.util.Map;

/**
 * 会员管理表服务
 */
public interface ITableMemberBusiSV {

    TableMember insert(TableMember bo);

    TableMember update(TableMember bo);

    TableMember delete(TableMember bo);

    TableMember select(TableMember bo);

    TableMember selectByMemberId(String memberId);

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
     * 获取所有子节点会员(TREE)
     * 递归
     * 不包括会员本身
     * @param tableMemberTree
     * @return
     */
    TableMemberTree queryAllChildTree(TableMemberTree tableMemberTree);

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

    PageInfo<TableMember> queryListPage(TableMember bo, Integer pageNum, Integer pageSize, Map<String, Object> map);

    /**
     * 注册
     * @param bo
     * @return
     */
    public TableMember regist(TableMember bo, int registerFrom);
}
