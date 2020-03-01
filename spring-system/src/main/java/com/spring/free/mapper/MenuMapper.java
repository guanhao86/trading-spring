package com.spring.free.mapper;

import com.spring.free.base.BaseMapper;
import com.spring.free.domain.MenuInfo;

import java.util.List;
import java.util.Map;

/**
 * 菜单管理DAO
 * @author Memory
 * @version 1.0
 */
public interface MenuMapper extends BaseMapper<MenuInfo> {

    List<MenuInfo> findByParentIdsLike(MenuInfo menu);

    int updateParentIds(MenuInfo menu);

    List<MenuInfo> selectMenuByGrade(Map map);

    /**
     * 超级管理员菜单查询
     * @param map
     * @return
     */
    List<MenuInfo> selectMenuAdminByGrade(Map map);

    /**
     * 根据权限获取菜单信息，用来验证权限标识是否存在
     * @param menu
     * @return
     */
    List<MenuInfo> getMenuByPermission(MenuInfo menu);

    /**
     * 根据URL获取菜单信息
     * @param menu
     * @return
     */
    MenuInfo getUrl(MenuInfo menu);

    String findSortMenu(MenuInfo menu);

}
