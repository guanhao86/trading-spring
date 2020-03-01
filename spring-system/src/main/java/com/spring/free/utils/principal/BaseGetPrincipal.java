/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.spring.free.utils.principal;

import com.alibaba.druid.util.StringUtils;
import com.spring.free.domain.Principal;
import com.spring.free.domain.UserInfo;
import com.spring.free.mapper.UserMapper;
import com.spring.free.util.SpringContextHolder;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * 获取当前登录用户
 *
 * @author Memory
 * @version 1.0
 */
@Transactional(readOnly = true, rollbackFor = Exception.class)
public abstract class BaseGetPrincipal {
    private static UserMapper userDao = SpringContextHolder.getBean(UserMapper.class);
    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 获取当前登录者对象
     */
    public static Principal getPrincipal() {
        try {
            Subject subject = SecurityUtils.getSubject();
            Principal principal = (Principal) subject.getPrincipal();
            if (principal != null) {
                return principal;
            }
        } catch (UnavailableSecurityManagerException e) {

        } catch (InvalidSessionException e) {

        }
        return null;
    }

    /**
     * 获取当前用户
     *
     * @return 取不到返回 new User()
     */
    public static UserInfo getUser() {
        Principal principal = getPrincipal();
        if (principal != null) {
            UserInfo user = userDao.get(principal.getId());
            if (user != null) {
                return user;
            }
            return new UserInfo();
        }
        // 如果没有登录，则返回实例化空的User对象。
        return new UserInfo();
    }

    public static UserInfo getUser(String username) {
        if (!StringUtils.isEmpty(username)){
            UserInfo info = new UserInfo();
            info.setUsername(username);
            UserInfo user = userDao.getByLoginName(info);
            if (user != null) {
                return user;
            }
            return new UserInfo();
        }
        return new UserInfo();
    }

}
