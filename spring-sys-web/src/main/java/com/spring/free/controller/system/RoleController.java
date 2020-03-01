package com.spring.free.controller.system;

import com.google.common.collect.Maps;
import com.spring.free.domain.MenuInfo;
import com.spring.free.domain.RoleInfo;
import com.spring.free.domain.UserInfo;
import com.spring.free.domain.UserRole;
import com.spring.free.system.MenuService;
import com.spring.free.system.RoleService;
import com.spring.free.system.UserService;
import com.spring.free.util.PageResult;
import com.spring.free.util.constraints.Global;
import com.spring.free.util.constraints.PageDefaultConstraints;
import com.spring.free.util.constraints.PromptInfoConstraints;
import com.spring.free.util.exception.ExceptionCodeEnum;
import com.spring.free.util.exception.ServiceException;
import com.spring.free.utils.principal.BaseGetPrincipal;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by spink on 2017/3/29.
 */
@Controller
@RequestMapping(Global.ADMIN_PATH + "/role/")
public class RoleController {
    //引用角色Service
    @Autowired
    private RoleService roleService;
    //引用菜单Service
    @Autowired
    private MenuService menuService;
    //引用用户Service
    @Autowired
    private UserService userService;

    @RequiresPermissions("system:role:view")
    @RequestMapping({"", "roleList"})
    public ModelAndView roleManage(ModelAndView mav, HttpSession session, RoleInfo role, HttpServletRequest request,
                                   @RequestParam(value = "page",required = false, defaultValue = PageDefaultConstraints.PAGE) int page,
                                   @RequestParam(value = "rows", required = false, defaultValue = PageDefaultConstraints.PAGE_SIZE) int pageSize) {
        mav.addObject("page", roleService.getRoleList(role, page, pageSize));
        //返回页面header标题
        PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_ROLE_LIST);
        //返回操作提示信息
        PageResult.getPrompt(mav, request, role.getParamMsg());
        mav.setViewName("system/role/role_list");
        return mav;
    }

    @RequiresPermissions("system:role:edit")
    @RequestMapping("form")
    public ModelAndView roleManageInsertPre(ModelAndView mav, HttpServletRequest request, RoleInfo role, String buttonType) {
        if (role.getId() != null && !"".equals(role.getId())){
            //根据角色ID，获取角色详情信息
            mav.addObject("role", roleService.getRole(role.getId()));
            //获取操作类型
            mav.addObject("buttonType", buttonType);
            //返回页面header标题
            PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_ROLE_UPD);
        } else {
            //返回页面header标题
            PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_ROLE_ADD);
        }
        //获取页面初始化数据信息
        initRole(mav);
        //返回操作提示信息
        PageResult.getPrompt(mav, request, role.getParamMsg());
        mav.setViewName("system/role/role_form");
        return mav;
    }

    @RequiresPermissions("system:role:edit")
    @RequestMapping("save")
    public ModelAndView roleManageInsert(ModelAndView mav, HttpServletRequest request, RoleInfo role, HttpSession session) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH + "/role/form");
        //验证角色名称是否重复
        if (!Global.TRUE.equals(checkName(role.getOldName(), role.getName()))) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "保存角色'" + role.getName() + "'失败, 角色名已存在", map.get(Global.URL).toString(), map);
        }
        //保存角色信息
        roleService.save(role, map);
        //返回操作提示信息
        PageResult.setPrompt(map, PromptInfoConstraints.getSaveSuccess("角色信息"), PromptInfoConstraints.SUCCESS_STATUS);
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH + "/role/roleList"), map);
    }

    @RequiresPermissions("system:role:edit")
    @RequestMapping("delete")
    public ModelAndView roleManageDelete(ModelAndView mav, HttpServletRequest request, RoleInfo role, HttpSession session) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH + "/role/roleList");
        //删除角色信息
        roleService.deleteRole(role, map);
        //返回操作提示信息
        PageResult.setPrompt(map, PromptInfoConstraints.getDeleteSuccess("角色信息"), PromptInfoConstraints.SUCCESS_STATUS);
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH + "/role/roleList"), map);
    }

    @RequiresPermissions("system:role:edit")
    @RequestMapping("dist")
    public ModelAndView roleManageDistPre(ModelAndView mav, HttpServletRequest request, RoleInfo role, String buttonType) {
        if (role != null && role.getId() != null && !"".equals(role.getId())){
            mav.addObject("role", roleService.getRole(role.getId()));
        } else {
            String roleId = request.getParameter("roleId");
            role.setId(Long.parseLong(roleId));
            mav.addObject("role", roleService.getRole(Long.parseLong(roleId)));
        }

        UserInfo user = new UserInfo();
        user.setRole(role);
        List<UserInfo> userList = userService.findList(user);
        mav.addObject("list", userList);
        mav.addObject("userList", removeChecked(role));
        //返回页面header标题
        PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_ROLE_DIST);
        //返回操作提示信息
        PageResult.getPrompt(mav, request, role.getParamMsg());
        mav.setViewName("system/role/role_dist");
        return mav;
    }

    @RequiresPermissions("system:role:edit")
    @RequestMapping("saveDist")
    public ModelAndView userRoleInsert(ModelAndView mav, HttpServletRequest request, Long roleId, String userIds, HttpSession session) {
        Map map = Maps.newHashMap();
        map.put("roleId", roleId);
        map.put(Global.URL, Global.ADMIN_PATH + "/role/dist");
        UserInfo user = null;
        RoleInfo role = new RoleInfo();
        List<UserInfo> userList = new ArrayList<UserInfo>();
        String[] userId = userIds.split(",");
        for (String s : userId) {
            user = new UserInfo();
            user.setId(Long.parseLong(s));
            userList.add(user);
        }
        role.setUserList(userList);
        role.setId(roleId);
        roleService.assignUserToRole(role, map);
        //返回操作提示信息
        PageResult.setPrompt(map, "角色分配用户成功！", PromptInfoConstraints.SUCCESS_STATUS);
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH + "/role/dist"), map);
    }

    @RequiresPermissions("system:role:edit")
    @RequestMapping("userRoleOut")
    public ModelAndView userRoleOut(ModelAndView mav, HttpServletRequest request, Long roleId, Long userId, HttpSession session) {
        Map map = Maps.newHashMap();
        map.put("roleId", roleId);
        map.put(Global.URL, Global.ADMIN_PATH + "/role/dist");
        UserInfo u = BaseGetPrincipal.getUser();
        RoleInfo role = roleService.getRole(roleId);
        UserInfo user = userService.getUser(userId);
        if(u.getId().equals(userId)){
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "无法从角色【" + role.getName() + "】中移除用户【" + user.getName() + "】自己！", map.get(Global.URL).toString(), map);
        } else {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            boolean flag = userService.outUserInRole(userRole);
            if (!flag){
                throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "用户【" + user.getName() + "】从角色【" + role.getName() + "】中移除失败！", map.get(Global.URL).toString(), map);
            } else {
                //返回操作提示信息
                PageResult.setPrompt(map, "用户【" + user.getName() + "】从角色【" + role.getName() + "】中移除成功！" , PromptInfoConstraints.SUCCESS_STATUS);
            }
        }
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH + "/role/dist"), map);
    }

    /**
     * 验证角色名称是否存在
     * @param oldName
     * @param name
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "checkName")
    public String checkName(String oldName, String name) {
        if (name != null && name.equals(oldName)) {
            return "true";
        } else if (name != null && roleService.getRoleByName(name) == null) {
            return "true";
        }
        return "false";
    }

/*==私有方法区========================================================================================================*/
    /**
     * 初始化角色信息
     * @param mav
     * @return
     */
    private ModelAndView initRole(ModelAndView mav) {
        MenuInfo menu = new MenuInfo();
        menu.setGrade(0);
        menu.setType("list");
        menu.setMap(Maps.newHashMap());
        mav.addObject("menuList", menuService.queryMenuList(menu));
        return mav;
    }

    /**
     * 移除选中
     * @param role
     * @return
     */
    private List<UserInfo> removeChecked(RoleInfo role){
        UserInfo user = new UserInfo();
        user.setRole(role);
        List<UserInfo> userList = userService.findList(user);
        List<UserInfo> userList1 = userService.findList(new UserInfo());
        List<UserInfo> userList2 = new ArrayList<UserInfo>();
        userList2.addAll(userList1);
        for (UserInfo user1 : userList1) {
            for (UserInfo user2 : userList) {
                if (user1.getId().equals(user2.getId())){
                    userList2.remove(user1);
                }
            }
        }
        return userList2;
    }

}
