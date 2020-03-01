package com.spring.free.manager;

import com.spring.free.base.BaseManager;
import com.spring.free.domain.MenuInfo;
import com.spring.free.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by spink on 2017/3/28.
 */
@Repository
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class MenuManager extends BaseManager<MenuMapper, MenuInfo> {

    @Autowired
    private MenuMapper mapper;

    public List<MenuInfo> findByParentIdsLike(MenuInfo menu){
        return mapper.findByParentIdsLike(menu);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int updateParentIds(MenuInfo menu){
        return mapper.updateParentIds(menu);
    }

    public List<MenuInfo> selectMenuByGrade(Map map){
        return mapper.selectMenuByGrade(map);
    }

    /**
     * 超级管理员菜单查询
     * @param map
     * @return
     */
    public List<MenuInfo> selectMenuAdminByGrade(Map map){
        return mapper.selectMenuAdminByGrade(map);
    }

    /**
     * 根据权限获取菜单信息，用来验证权限标识是否存在
     * @param menu
     * @return
     */
    public List<MenuInfo> getMenuByPermission(MenuInfo menu){
        return mapper.getMenuByPermission(menu);
    }

    public MenuInfo getUrl(String href){
        MenuInfo info = new MenuInfo();
        info.setHref(href);
        return mapper.getUrl(info);
    }

    public String findSortMenu(Integer grade, Long parentID){
        MenuInfo info = new MenuInfo();
        info.setGrade(grade);
        info.setParentID(parentID);
        return mapper.findSortMenu(info);
    }
}
