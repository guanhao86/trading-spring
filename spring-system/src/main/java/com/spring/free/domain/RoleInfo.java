package com.spring.free.domain;

import com.google.common.collect.Lists;
import com.spring.free.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色Entity
 *
 * @author Memory
 * @version 1.0
 */
@Getter
@Setter
@ToString
@Table(name = "t_sys_role_info")
public class RoleInfo extends BaseEntity<RoleInfo> {

    /**
     * 角色名称
     */
    private String name;
    /**
     * 英文名称，无用
     */
    private String enname;
    /**
     * 权限类型，无用
     */
    private String roleType;
    /**
     * 数据范围，无用
     */
    private String dataScope;
    /**
     * 原角色名称
     */
    private String oldName;
    /**
     * 原英文名称，无用
     */
    private String oldEnname;
    /**
     * 是否是系统数据
     */
    private String sysData;
    /**
     * 是否是可用
     */
    private String useable;
    /**
     * 是否选中
     */
    private String checked;
    /**
     * 根据用户ID查询角色列表
     */
    @Transient
    private UserInfo user;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 拥有菜单列表
     */
    @Transient
    private List<MenuInfo> menuList = new ArrayList<MenuInfo>();
    /**
     * 拥有用户列表
     */
    @Transient
    private List<UserInfo> userList = new ArrayList<UserInfo>();

    public List<Long> getMenuIdList() {
        List<Long> menuIdList = new ArrayList<Long>();
        for (MenuInfo menu : menuList) {
            menuIdList.add(menu.getId());
        }
        return menuIdList;
    }

    public void setMenuIdList(List<Long> menuIdList) {
        menuList = new ArrayList<MenuInfo>();
        for (Long menuId : menuIdList) {
            MenuInfo menu = new MenuInfo();
            menu.setId(menuId);
            menuList.add(menu);
        }
    }

    public String getMenuIds() {
        return StringUtils.join(getMenuIdList(), ",");
    }

    public void setMenuIds(String menuIds) {
        menuList = new ArrayList<MenuInfo>();
        if (menuIds != null) {
            String[] ids = StringUtils.split(menuIds, ",");

            setMenuIdList(Lists.newArrayList(getLongArr(ids)));
        }
    }

    /**
     * 获取权限字符串列表
     */
    public List<String> getPermissions() {
        List<String> permissions = Lists.newArrayList();
        for (MenuInfo menu : menuList) {
            if (menu.getPermission() != null && !"".equals(menu.getPermission())) {
                permissions.add(menu.getPermission());
            }
        }
        return permissions;
    }

    private Long[] getLongArr(String[] ids) {
        Long[] longs = new Long[ids.length];
        for (int i = 0; i < longs.length; i++) {
            longs[i] = Long.parseLong(ids[i]);
        }
        return longs;
    }

}
