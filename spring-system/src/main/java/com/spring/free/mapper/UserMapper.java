package com.spring.free.mapper;

import com.spring.free.base.BaseMapper;
import com.spring.free.domain.UserInfo;
import com.spring.free.domain.UserRole;

import java.util.List;

/**
 * 用户管理DAO
 * @author Memory
 * @version 1.0
 */
public interface UserMapper extends BaseMapper<UserInfo> {

    /**
     * 查询用户
     * @param user
     * @return
     */
    List<UserInfo> getUserAll(UserInfo user);

    /**
     * 根据登录名获取用户
     * @param user
     * @return
     */
    UserInfo getByLoginName(UserInfo user);

    /**
     * 根据手机号码获取用户
     * @param user
     * @return
     */
    UserInfo getByPhone(UserInfo user);

    /**
     * 删除用户角色关联数据
     * @param user
     * @return
     */
    int deleteUserRole(UserInfo user);

    /**
     * 移除角色用户
     * @param userRole
     * @return
     */
    int outUserInRole(UserRole userRole);

    /**
     * 插入用户角色关联数据
     * @param user
     * @return
     */
    int insertUserRole(UserInfo user);

    /**
     * 根据UserID查询用户角色关联数据
     * @param userRole
     * @return
     */
    List<UserRole> findUserRole(UserRole userRole);

    /**
     * 根据RoleID查询用户角色关联数据
     * @param userRole
     * @return
     */
    List<UserRole> findRoleUser(UserRole userRole);

    /**
     * 获取最大工号
     * @return
     */
    List<UserInfo> jobNumber();

    int insertBatch(List<UserInfo> list);

}
