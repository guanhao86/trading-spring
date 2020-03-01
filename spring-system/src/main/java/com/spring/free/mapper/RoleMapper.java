package com.spring.free.mapper;


import com.spring.free.base.BaseMapper;
import com.spring.free.domain.RoleInfo;

import java.util.List;

/**
 * 角色管理DAO
 * @author Memory
 * @version 1.0
 */
public interface RoleMapper extends BaseMapper<RoleInfo> {

	/**
	 * 获取角色名称
	 * @param role
	 * @return
	 */
	RoleInfo getByName(RoleInfo role);

	/**
	 * 维护角色与菜单权限关系
	 * 删除角色与菜单关系
	 * @param role
	 * @return
	 */
	int deleteRoleMenu(RoleInfo role);

	/**
	 * 维护角色与菜单权限关系
	 * 插入角色与菜单关系
	 * @param role
	 * @return
	 */
	int insertRoleMenu(RoleInfo role);

	/**
	 * 角色分配用户
	 * @param role
	 * @return
	 */
	int assignUserToRole(RoleInfo role);

	/**
	 * 根据用户ID获取角色信息
	 * @param userId
	 * @return
	 */
	List<RoleInfo> getUserRoleInfo(Long userId);

	/**
	 * 获取用户拥有的角色名称
	 * @param role
	 * @return
	 */
	String getUserRoleName(RoleInfo role);

}
