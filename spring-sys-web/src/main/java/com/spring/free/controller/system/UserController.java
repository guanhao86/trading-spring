package com.spring.free.controller.system;

import com.alibaba.druid.util.StringUtils;
import com.google.common.collect.Maps;
import com.spring.free.domain.RoleInfo;
import com.spring.free.domain.UserInfo;
import com.spring.free.domain.UserRole;
import com.spring.free.system.RoleService;
import com.spring.free.system.UserService;
import com.spring.free.util.PageResult;
import com.spring.free.util.constraints.Global;
import com.spring.free.util.constraints.PageDefaultConstraints;
import com.spring.free.util.constraints.PromptInfoConstraints;
import com.spring.free.util.md5.Md5Util;
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
 * Created by spink on 2017/3/30.
 * 用户管理Controller
 */
@Controller
@RequestMapping(Global.ADMIN_PATH + "/user/")
public class UserController {

    public static final String USERNAME = "username";
    public static final String MOBILE = "mobile";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    //用户Service
    @Autowired
    private UserService userService;
    //角色Service
    @Autowired
    private RoleService roleService;

    /**
     * 用户查询
     *
     * @param mav
     * @param user     用户信息对象
     * @param request
     * @param page     当前页数
     * @param pageSize 每页显示数量
     * @return
     */
    @RequiresPermissions("system:user:view")
    @RequestMapping({"", "userList"})
    public ModelAndView userManage(ModelAndView mav, UserInfo user, HttpServletRequest request,
                                   @RequestParam(value = "page", required = false, defaultValue = PageDefaultConstraints.PAGE) int page,
                                   @RequestParam(value = "rows", required = false, defaultValue = PageDefaultConstraints.PAGE_SIZE) int pageSize) {
        mav.addObject("page", userService.findList(user, page, pageSize));
        mav.addObject("user", user);
        PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_USER_LIST);
        PageResult.getPrompt(mav, request, user.getParamMsg());
        mav.setViewName("system/user/user_list");
        return mav;
    }

    @RequiresPermissions("system:user:online")
    @RequestMapping("online")
    public ModelAndView onlineSts(ModelAndView mav, UserInfo user, HttpServletRequest request) {
        mav.addObject("list", userService.getOnlineStsQuery(user));
        mav.addObject("user", user);
        PageResult.setPageTitle(mav, "管理员登录状态");
        PageResult.getPrompt(mav, request, user.getParamMsg());
        mav.setViewName("system/user/online_list");
        return mav;
    }

    @RequiresPermissions("system:user:edit")
    @RequestMapping("form")
    public ModelAndView userManageInsertPre(ModelAndView mav, HttpServletRequest request, UserInfo user, String buttonType) {
        UserInfo user1 = new UserInfo();
        String userId = request.getParameter("userId");
        if (StringUtils.isEmpty(buttonType)) {
            buttonType = request.getParameter("buttonType");
        }
        if (!StringUtils.isEmpty(userId)) {
            user.setId(Long.parseLong(userId));
        }
        if (user.getId() != null && !"".equals(user.getId())) {
            user1 = userService.getUser(user.getId());
            mav.addObject("userRole", userService.findUserRole(user.getId()));
            PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_USER_UPD);
        } else {
            user1.setNo(userService.getJobNumber());
            PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_USER_ADD);
        }
        mav.addObject("user", user1);
        initUser(mav, user);
        mav.addObject("buttonType", buttonType);
        PageResult.getPrompt(mav, request, user.getParamMsg());
        mav.setViewName("system/user/user_form");
        return mav;
    }

    @RequiresPermissions("user")
    @RequestMapping("info")
    public ModelAndView userInfo(ModelAndView mav, HttpServletRequest request, UserInfo user, String buttonType) {
        mav.addObject("user", userService.getUser(BaseGetPrincipal.getUser().getId()));
        RoleInfo info = new RoleInfo();
        info.setUserId(BaseGetPrincipal.getUser().getId());
        mav.addObject("userRole", roleService.getUserRoleName(info));
        mav.addObject("buttonType", buttonType);
        PageResult.setPageTitle(mav, "个人资料");
        initUser(mav, user);
        PageResult.getPrompt(mav, request, null);
        mav.setViewName("system/user/user_profile");
        return mav;
    }

    @RequiresPermissions("user")
    @RequestMapping("infoEdit")
    public ModelAndView userInfoEdit(ModelAndView mav, HttpServletRequest request, UserInfo user, String buttonType) {
        mav.addObject("user", userService.getUser(BaseGetPrincipal.getUser().getId()));
        PageResult.setPageTitle(mav, "个人信息编辑");
        initUser(mav, user);
        PageResult.getPrompt(mav, request, null);
        mav.setViewName("system/user/user_setting");
        return mav;
    }

    @RequiresPermissions("user")
    @RequestMapping("userInfoSave")
    public ModelAndView userInfoIns(ModelAndView mav, UserInfo user) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH + "/user/infoEdit");
        userService.update(user, map);
        PageResult.setPrompt(map, PromptInfoConstraints.getSaveSuccess("个人信息"), PromptInfoConstraints.SUCCESS_STATUS);
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH + "/user/infoEdit"), map);
    }

    /**
     * 修改密码
     * @param mav
     * @param user
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping("userUpdPwd")
    public ModelAndView userUpdPwd(ModelAndView mav, UserInfo user) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH + "/user/infoEdit");
        user.setPassword(Md5Util.md5Hex(user.getPassword()));
        userService.update(user, map);
        PageResult.setPrompt(map, "修改密码成功", PromptInfoConstraints.SUCCESS_STATUS);
        return new ModelAndView(new RedirectView("/login"));
    }

    @RequiresPermissions("system:user:edit")
    @RequestMapping("save")
    public ModelAndView userManageInsert(ModelAndView mav, HttpServletRequest request, UserInfo user, HttpSession session, String buttonType) {
        Map map = Maps.newHashMap();
        map.put("buttonType", buttonType);
        map.put("userId", user.getId());
        map.put(Global.URL, Global.ADMIN_PATH + "/user/form");
        userService.save(user, map);
        PageResult.setPrompt(map, PromptInfoConstraints.getSaveSuccess("用户信息"), PromptInfoConstraints.SUCCESS_STATUS);
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH + "/user/userList"), map);
    }

    /**
     * 重置密码
     * @param mav
     * @param user
     * @return
     */
    @RequiresPermissions("system:user:pwd")
    @RequestMapping("updPwd")
    public ModelAndView updPwd(ModelAndView mav, UserInfo user) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH + "/user/userList");
        user.setPassword(Md5Util.md5Hex("123456"));
        userService.update(user, map);
        map = PageResult.setPrompt(map, "重置密码成功", PromptInfoConstraints.SUCCESS_STATUS);
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH + "/user/userList"), map);

    }

    @RequiresPermissions("system:user:edit")
    @RequestMapping("delete")
    public ModelAndView userManageDelete(ModelAndView mav, HttpServletRequest request, UserInfo user, HttpSession session) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH + "/user/userList");
        userService.deleteUserInfo(user, map);
        PageResult.setPrompt(map, PromptInfoConstraints.getDeleteSuccess("用户信息"), PromptInfoConstraints.SUCCESS_STATUS);
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH + "/user/userList"), map);
    }

    @ResponseBody
    @RequestMapping(value = "checkRepeat")
    public String checkRepeat(String oldName, String name, String type) {
        if (name != null && name.equals(oldName)) {
            return "true";
        } else if (name != null && userService.getUserByLoginName(name) && USERNAME.equals(type)) {
            return "true";
        } else if (name != null && userService.getUserByMobile(name) && MOBILE.equals(type)) {
            return "true";
        } else if (name != null && userService.getUserByEmail(name) && EMAIL.equals(type)) {
            return "true";
        } else if (name != null && oldName != null && userService.getUserByPassword(oldName, name) && PASSWORD.equals(type)) {
            return "true";
        }
        return "false";
    }

/**==私有方法区========================================================================================================*/

    /**
     * 初始化角色信息
     *
     * @param mav
     * @return
     */
    private ModelAndView initUser(ModelAndView mav, UserInfo user) {
        mav.addObject("roleList", getRole(user.getId()));
        return mav;
    }

    /**
     * 获取角色信息，如果有选中的角色，将其赋值为选中状态
     *
     * @param userId
     * @return
     */
    private List<RoleInfo> getRole(Long userId) {
        List<RoleInfo> roles = new ArrayList<RoleInfo>();
        List<RoleInfo> roleList = roleService.getRoleList();
        List<UserRole> userRoleList = userService.findUserRole(userId);
        for (RoleInfo role : roleList) {
            for (UserRole userRole : userRoleList) {
                if (role.getId().equals(userRole.getRoleId())) {
                    role.setChecked("checked");
                }
            }
            roles.add(role);
        }
        return roles;
    }
}
