package com.spring.free.controller.system;

import com.alibaba.druid.util.StringUtils;
import com.google.common.collect.Maps;
import com.spring.free.domain.MenuInfo;
import com.spring.free.system.IconService;
import com.spring.free.system.MenuService;
import com.spring.free.util.PageResult;
import com.spring.free.util.constraints.Global;
import com.spring.free.util.constraints.PromptInfoConstraints;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by spink on 2017/3/29.
 */
@Controller
@RequestMapping(Global.ADMIN_PATH + "/menu/")
public class MenuController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private IconService iconService;

    @RequiresPermissions("system:menu:view")
    @RequestMapping({"", "menuList"})
    public ModelAndView menuManage(ModelAndView mav, MenuInfo menu, HttpServletRequest request) {
        menu.setMap(Maps.newHashMap());
        menu.setType("list");
        //获取菜单集合
        mav.addObject("list", menuService.queryMenuList(menu));
        //返回操作提示信息
        PageResult.getPrompt(mav, request, menu.getParamMsg());
        //返回页面header标题
        PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_MENU_LIST);
        mav.setViewName("system/menu/menu_list");
        return mav;
    }

    @RequiresPermissions("system:menu:edit")
    @RequestMapping("form")
    public ModelAndView menuManageInsertPre(ModelAndView mav, HttpServletRequest request, MenuInfo menu, String buttonType) {
        if (menu.getId() != null) {
            //验证是添加还是修改，如果是修改返回相应的数据信息
            setMenu(mav, menu, buttonType);
        } else {
            String menuId = request.getParameter("menuId");
            if (menuId != null && !"".equals(menuId)) {
                menu.setId(Long.parseLong(menuId));
                //判断操作类型，根据操作类型返回相应的区域信息
                setMenu(mav, menu, request.getParameter("buttonType"));
            } else {
                menu.setSort(menuService.findSortMenu(1, 1L));
                mav.addObject("menu", menu);
                PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_MENU_ADD);
            }
        }
        //初始化页面信息
        initMenu(mav, menu);
        //返回操作提示信息
        PageResult.getPrompt(mav, request, menu.getParamMsg());
        mav.setViewName("system/menu/menu_form");
        return mav;
    }

    @RequiresPermissions("system:menu:edit")
    @RequestMapping("save")
    public ModelAndView menuManageInsert(ModelAndView mav, HttpServletRequest request, MenuInfo menu, HttpSession session, String buttonType) {
        Map map = Maps.newHashMap();
        map.put("menuId", menu.getId());
        map.put("buttonType", buttonType);
        map.put(Global.URL, Global.ADMIN_PATH + "/menu/form");
        //保存菜单信息
        menuService.save(menu, map);
        map = PageResult.setPrompt(map, PromptInfoConstraints.getSaveSuccess("菜单信息"), PromptInfoConstraints.SUCCESS_STATUS);
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH + "/menu/menuList"), map);
    }

    @RequiresPermissions("system:menu:edit")
    @RequestMapping("delete")
    public ModelAndView menuManageDelete(ModelAndView mav, HttpServletRequest request, MenuInfo menu, HttpSession session) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH + "/menu/menuList");
        //删除菜单信息，逻辑删除
        menuService.delete(menu, map);
        PageResult.setPrompt(map, PromptInfoConstraints.getDeleteSuccess("菜单信息"), PromptInfoConstraints.SUCCESS_STATUS);
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH + "/menu/menuList"), map);
    }

    /**
     * 权限标识重复验证
     *
     * @param oldPermission
     * @param permission
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "checkPermission")
    public String checkPermission(String oldPermission, String permission) {
        if (permission != null && permission.equals(oldPermission)) {
            return "true";
        } else if (permission != null && menuService.getMenuBypermission(permission)) {
            return "true";
        }
        return "false";
    }

    @ResponseBody
    @RequestMapping(value = "getSort")
    public Map<String, String> getSort(String sort) {
        Map<String, String> map = Maps.newHashMap();
        if(!StringUtils.isEmpty(sort)){
            String[] sorts = sort.split(Global.SEPARATOR_UNDER_BAR);
            String str = menuService.findSortMenu(Integer.valueOf(sorts[1]) + 1, Long.valueOf(sorts[0]));
            map.put("sort", str);
        }
        return map;
    }

/*==私有方法区========================================================================================================*/

    /**
     * 添加 或 编辑 菜单时初始化菜单信息
     *
     * @param mav
     * @param menu
     * @return
     */
    private ModelAndView initMenu(ModelAndView mav, MenuInfo menu) {
        menu.setMap(Maps.newHashMap());
        menu.setType("list");
        mav.addObject("list", menuService.queryMenuList(menu));
        mav.addObject("icon", iconService.selectIcon());
        return mav;
    }

    /**
     * 验证按钮是编辑操作还是添加下级菜单操作
     *
     * @param mav
     * @param menu
     * @param buttonType
     * @return
     */
    private ModelAndView setMenu(ModelAndView mav, MenuInfo menu, String buttonType) {
        //根据ID获取菜单信息
        MenuInfo menu1 = menuService.getMenu(menu.getId());
        menu1.setSort(menuService.findSortMenu(menu1.getGrade() + 1, menu1.getId()));
        /*判断按钮类型，为edit时，为编辑状态，返回完整的菜单信息*/
        /*判断按钮类型，为addSub时，为添加下级菜单状态，返回部分的菜单信息{grade,id,name}*/
        if (Global.EDIT.equals(buttonType)) {
            mav.addObject("menu", menu1);
            PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_MENU_UPD);
        } else if (Global.ADD_SUB.equals(buttonType)) {
            mav.addObject("menu", setSubMenu(menu1));
            PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_MENU_ADD);
        }
        //按钮类型
        mav.addObject("buttonType", buttonType);
        return mav;
    }

    /**
     * 添加下级菜单返回数据的对象
     *
     * @param menu1
     * @return
     */
    private MenuInfo setSubMenu(MenuInfo menu1) {
        MenuInfo menu2 = new MenuInfo();
        menu2.setGrade(menu1.getGrade());
        menu2.setId(menu1.getId());
        menu2.setName(menu1.getName());
        MenuInfo menu = new MenuInfo();
        menu.setParent(menu2);
        menu.setSort(menu1.getSort());
        menu.setSystemType(menu1.getSystemType());
        return menu;
    }

}
