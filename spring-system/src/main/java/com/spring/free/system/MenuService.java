package com.spring.free.system;

import com.alibaba.druid.util.StringUtils;
import com.spring.free.domain.MenuInfo;
import com.spring.free.manager.MenuManager;
import com.spring.free.util.constraints.Global;
import com.spring.free.util.exception.ExceptionCodeEnum;
import com.spring.free.util.exception.ServiceException;
import com.spring.free.utils.principal.BaseGetPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by spink on 2017/3/29.
 */
@Service
public class MenuService {

    @Autowired
    private MenuManager menuManager;

    public MenuInfo get(String url){
        MenuInfo menuInfo = new MenuInfo();
        menuInfo.setHref(url);
        return menuManager.get(menuInfo);
    }

    public MenuInfo getUrl(String href){
        return menuManager.getUrl(href);
    }
    /**
     * 保存菜单信息
     * @param menu
     */
    public void save(MenuInfo menu, Map map) {
        menu = setMenuParent(menu);
        boolean boo = false;
        if (menu.getId() != null && !"".equals(menu.getId())){
            // 获取修改前的parentIds，用于更新子节点的parentIds
            String oldParentIds = menu.getParentIds();
            boo = menuManager.updateBs(menu) > 0 ? true : false;
            // 更新子节点 parentIds
            updateChildNode(menu, oldParentIds);
        } else {
            boo = menuManager.insertBs(menu) > 0 ? true : false;
        }
        if (!boo){
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "保存菜单失败", map.get(Global.URL).toString(), map);
        }
    }


    /**
     * 删除菜单信息
     * @param menu
     */
    public void delete(MenuInfo menu, Map map){
        boolean boo = menuManager.deleteBs(menu) > 0 ? true : false;
        if (!boo){
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "删除菜单信息发生异常", map.get(Global.URL).toString(), map);
        }
    }

    /**
     * 获取菜单详情信息
     * @param id
     * @return
     */
    public MenuInfo getMenu(Long id) {
        MenuInfo menuInfo = new MenuInfo();
        menuInfo.setId(id);
        return menuManager.get(menuInfo);
    }

    /**
     * 根据权限获取菜单信息，用来验证权限标识是否存在
     * @param permission
     * @return
     */
    public boolean getMenuBypermission(String permission){
        MenuInfo menu = new MenuInfo();
        menu.setPermission(permission);
        List<MenuInfo> menuList = menuManager.getMenuByPermission(menu);
        if (menuList != null && menuList.size() > 0){
            return false;
        }
        return true;
    }

    /**
     * 查询菜单信息
     * @param menu
     * @return
     */
    public List<MenuInfo> queryMenuList(MenuInfo menu){
        return queryMenu(menu, menu.getMap(), menu.getType());
    }

    /**
     * 查询菜单，左侧导航使用
     * @param menu
     * @return
     */
    public List<MenuInfo> menuList(MenuInfo menu){
        return getMenuInfoList(menu, menu.getMap(), menu.getType());
    }

    /**
     * 根据等级获取最后的排序序号
     * @param grade
     * @return
     */
    public String findSortMenu(Integer grade, Long parentID){
        String sortStr = menuManager.findSortMenu(grade, parentID);
        if (!StringUtils.isEmpty(sortStr)){
            return getNewSort(sortStr, grade);
        } else {
            MenuInfo info = getMenu(parentID);
            if (info != null && !StringUtils.isEmpty(info.getSort())){
                return getNewSort(info.getSort(), (info.getGrade() + 1));
            }
        }
        return "00:00:00:00";
    }

    private String getNewSort(String sortStr, int grade){
        String[] sorts = sortStr.split("\\.");
        if (grade == Global.NUMBER_ONE){
            Integer sort = (Integer.valueOf(sorts[0]) + 1);
            sorts[0] = setSort(sort);
        } else if (grade == Global.NUMBER_TWO){
            Integer sort = (Integer.valueOf(sorts[1]) + 1);
            sorts[1] = setSort(sort);
        } else if (grade == Global.NUMBER_THREE){
            Integer sort = (Integer.valueOf(sorts[2]) + 1);
            sorts[2] = setSort(sort);
        } else if (grade == Global.NUMBER_FOUR){
            Integer sort = (Integer.valueOf(sorts[3]) + 1);
            sorts[3] = setSort(sort);
        }
        return sorts[0] + Global.SEPARATOR_DOT + sorts[1] + Global.SEPARATOR_DOT + sorts[2] + Global.SEPARATOR_DOT + sorts[3];
    }
    private String setSort(Integer sort){
        String sortStr = "00";
        if (sort < Global.NUMBER_TEN){
            sortStr = "0" + sort;
        } else {
            sortStr = String.valueOf(sort);
        }
        return sortStr;
    }
    /**
     * 菜单信息归类
     * @param menu
     * @return
     */
    private List<MenuInfo> queryMenu(MenuInfo menu, Map map, String type){
        List<MenuInfo> apiMenuList = getMenuInfoList(menu, map, type);
        List<MenuInfo> tree = new ArrayList<MenuInfo>();
        for (MenuInfo a : apiMenuList) {
            buildHelpKernelTree(tree, a);
        }
        return tree;
    }

    /**
     * 递归调用，目的是进行菜单类别归类
     * @param tree
     * @param menu
     */
    private void buildHelpKernelTree(List<MenuInfo> tree, MenuInfo menu) {
        tree.add(menu);
        if (menu.getChildren() != null && menu.getChildren().size() > 0) {
            for (MenuInfo child : menu.getChildren()) {
                buildHelpKernelTree(tree, child);
            }
        }
    }

    /**
     * 获取菜单信息集合
     * @param menu
     * @param map
     * @param type
     * @return
     */
    private static final String LIST = "list";
    private List<MenuInfo> getMenuInfoList(MenuInfo menu, Map map, String type){
        Long userId = BaseGetPrincipal.getUser().getId() == null ? 1 : BaseGetPrincipal.getUser().getId();
        if (menu.getGrade() == null){
            menu.setGrade(1);
        }
        map.put("grade", menu.getGrade());
        map.put("systemType", menu.getSystemType());
        List<MenuInfo> menuInfoList = null;
        if (Global.STR_NUMBER_ONE.equals(1) || LIST.equals(type)){
            menuInfoList = menuManager.selectMenuAdminByGrade(map);
        } else {
            map.put("userId", userId);
            menuInfoList = menuManager.selectMenuByGrade(map);
        }
        return menuInfoList;
    }

    /**
     * 设置新的父节点串
     * 添加父节点
     * 添加级别
     * @param menu
     * @return
     */
    private MenuInfo setMenuParent(MenuInfo menu){
        String parentIdGrades = menu.getParentIdGrade();
        if(!"".equals(parentIdGrades) && parentIdGrades != null){
            String[] parentIdGrade = menu.getParentIdGrade().split("_");
            menu.setParentID(Long.parseLong(parentIdGrade[0]));
            menu.setGrade(Integer.parseInt(parentIdGrade[1]) + 1);
        }
        // 获取父节点实体
        menu.setParent(this.getMenu(menu.getParentID()));
        // 设置新的父节点串
        if (menu.getParent() != null) {
            menu.setParentIds(menu.getParent().getParentIds() + menu.getParent().getId() + ",");
        } else {
            menu.setParentIds(menu.getParentID() + ",");
        }
        return menu;
    }

    /**
     * 更新子节点 parentIds
     * @param menu
     * @param oldParentIds
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    private void updateChildNode(MenuInfo menu, String oldParentIds){
        MenuInfo m = new MenuInfo();
        m.setParentIds("%," + menu.getId() + ",%");
        List<MenuInfo> list = menuManager.findByParentIdsLike(m);
        for (MenuInfo e : list) {
            e.setParentIds(e.getParentIds().replace(oldParentIds, menu.getParentIds()));
            menuManager.updateParentIds(e);
        }
    }

}
