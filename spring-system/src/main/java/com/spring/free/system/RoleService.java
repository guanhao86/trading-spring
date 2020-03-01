package com.spring.free.system;

import com.github.pagehelper.PageInfo;
import com.spring.free.domain.MenuInfo;
import com.spring.free.domain.RoleInfo;
import com.spring.free.domain.UserInfo;
import com.spring.free.manager.RoleManager;
import com.spring.free.util.constraints.Global;
import com.spring.free.util.exception.ExceptionCodeEnum;
import com.spring.free.util.exception.ServiceException;
import com.spring.free.utils.principal.BaseGetPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by spink on 2017/3/29.
 */
@Service
public class RoleService {

    @Autowired
    private RoleManager roleManager;
    @Autowired
    private UserService userService;

    /**
     * 获取当前用户角色列表
     * @return
     */
    public List<RoleInfo> getRoleList(){
        List<RoleInfo> roleList = null;
        UserInfo user = BaseGetPrincipal.getUser();
        if (user.isAdmin()){
            roleList = roleManager.findAllList(new RoleInfo());
        }else{
            roleList = roleManager.findList(new RoleInfo());
        }
        return roleList;
    }

    public PageInfo<RoleInfo> getRoleList(RoleInfo info, Integer page, Integer pageSize){
        return roleManager.findAllList(info, page, pageSize);
    }
    /**
     * 保存角色信息
     * @param role
     */
    public void save(RoleInfo role, Map map) {
        roleManager.save(role, map);
    }

    /**
     * 删除角色
     * @param role
     */
    public void deleteRole(RoleInfo role, Map map){
        if (!userService.isRoleUser(role.getId())){
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "该角色名下挂有用户，不可删除", map.get(Global.URL).toString(), map);
        }
        boolean boo = roleManager.deleteBs(role) > 0 ? true : false;
        if (!boo){
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "删除角色信息，发生错误！", map.get(Global.URL).toString(), map);
        }
    }

    /**
     * 角色分配用户
     * @param role
     */
    public void assignUserToRole(RoleInfo role, Map map){
        boolean boo = roleManager.assignUserToRole(role) > 0 ? true : false;
        if (!boo){
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "角色分配用户，发生错误！", map.get(Global.URL).toString(), map);
        }
    }

    /**
     * 根据ID获取角色信息
     * @param id
     * @return
     */
    public RoleInfo getRole(Long id) {
        RoleInfo role = roleManager.get(id);
        String menus = getMenus(role.getMenuList());
        role.setMenuIds(menus);
        return role;
    }

    /**
     * 验证角色名称是否存在
     * @param name
     * @return
     */
    public RoleInfo getRoleByName(String name) {
        RoleInfo r = new RoleInfo();
        r.setName(name);
        return roleManager.getByName(r);
    }

    /**
     * 获取用户拥有的角色名称
     * @param role
     * @return
     */
    public String getUserRoleName(RoleInfo role){
        return roleManager.getUserRoleName(role);
    }


    private String getMenus(List<MenuInfo> menuList){
        String menus = "";
        for (MenuInfo menu : menuList) {
            if ("".equals(menus)){
                menus += menu.getId();
            } else {
                menus += "," + menu.getId();
            }
        }
        return menus;
    }
}
