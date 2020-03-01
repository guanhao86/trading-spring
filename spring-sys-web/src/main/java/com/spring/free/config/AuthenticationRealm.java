package com.spring.free.config;

import com.google.common.collect.Maps;
;
import com.spring.free.domain.*;
import com.spring.free.domain.*;
import com.spring.free.system.MenuService;
import com.spring.free.system.UserService;
import com.spring.free.util.constraints.Global;
import com.spring.free.util.exception.UserException;
import com.spring.free.utils.principal.BaseGetPrincipal;
import com.spring.free.utils.shirosession.SessionDAO;
import com.spring.free.util.md5.Md5Util;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * Shiro 登录和权限认证
 * @author Memory
 * @date 2016/10/20
 * @version 1.0
 * @Inc 恒谱科技
 */
public class AuthenticationRealm extends AuthorizingRealm {
    private static Logger logger = Logger.getLogger(AuthenticationRealm.class);

    @Resource
    private UserService userService;
    @Resource
    private MenuService menuService;
    @Autowired
    private SessionDAO sessionDAO;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("==============================");
        Principal principal = (Principal) getAvailablePrincipal(principalCollection);
        if (Global.TRUE.equals(Global.TRUE)) {
            Collection<Session> sessions = sessionDAO.getActiveSessions(true, principal, principal.getSession());
            if (sessions.size() > 0) {
                // 来的，则踢出已在线用户
                if (BaseGetPrincipal.getSubject().isAuthenticated()) {
                    for (Session session : sessions) {
                        UserInfo info = (UserInfo) session.getAttribute("userInfo");
                        if (info != null) {
                            if (principal.getUsername().equals(info.getUsername())) {
                                session.setTimeout(0);
                                break;
                            }
                        }
                    }
                }
                // 记住我进来的，并且当前用户已登录，则退出当前用户提示信息。
                else {
                    BaseGetPrincipal.getSubject().logout();
                    throw new AuthenticationException("msg:账号已在其它地方登录，请重新登录。");
                }
            }
        }

        UserInfo user = userService.getUserByUsernameLogin(principal.getUsername());
        if (user != null) {
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            MenuInfo menu2 = new MenuInfo();
            menu2.setMap(Maps.newHashMap());
            if (Global.ADMIN.equals(user.getUsername())) {
                menu2.setType("list");
            } else {
                menu2.setType("user");
            }

            List<MenuInfo> list = menuService.queryMenuList(menu2);
            for (MenuInfo menu : list) {
                if (org.apache.commons.lang3.StringUtils.isNotBlank(menu.getPermission())) {
                    // 添加基于Permission的权限信息
                    for (String permission : org.apache.commons.lang3.StringUtils.split(menu.getPermission(), Global.SEPARATOR_COMMA)) {
                        info.addStringPermission(permission);
                    }
                }
            }
            // 添加用户权限
            info.addStringPermission("user");
            // 添加用户角色信息
            for (RoleInfo role : user.getRoleList()) {
                info.addRole(role.getName());
            }
            return info;
        } else {
            return null;
        }

    }

    @Override
    protected void checkPermission(Permission permission, AuthorizationInfo info) {
        super.checkPermission(permission, info);
    }

    @Override
    protected boolean[] isPermitted(List<Permission> permissions, AuthorizationInfo info) {
        return super.isPermitted(permissions, info);
    }

    @Override
    public boolean isPermitted(PrincipalCollection principals, Permission permission) {
        return super.isPermitted(principals, permission);
    }

    @Override
    protected boolean isPermittedAll(Collection<Permission> permissions, AuthorizationInfo info) {
        return super.isPermittedAll(permissions, info);
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {

        logger.info("-------------Apache shiro 认证------------------");

        UserInfo user = null;
        UsernamePasswordCaptchaToken token = null;
        token = (UsernamePasswordCaptchaToken) authenticationToken;

        String username = token.getUsername();
        char[] pwd = token.getPassword();
        String password = String.valueOf(pwd);
        System.out.println(Md5Util.md5Hex(password).toString());
        token.setPassword(Md5Util.md5Hex(password).toCharArray());

        if (StringUtils.hasText(username)) {
            user = this.userService.getUserByUsernameLogin(username);
        }

        if (user != null) {
            return new SimpleAuthenticationInfo(new Principal(user, token.isMobileLogin()), user.getPassword(), getName());
        } else {
            throw new UserException(username);
        }

    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public MenuService getMenuService() {
        return menuService;
    }

    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }
}
