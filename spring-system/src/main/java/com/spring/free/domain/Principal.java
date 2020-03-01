package com.spring.free.domain;

import lombok.ToString;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import java.io.Serializable;

/**
 * @author Memory
 * @version 1.0
 */
@ToString
public class Principal implements Serializable {
    private static final long serialVersionUID = 4228442260218510927L;

    /**
     * 编号
     */
    private Long id;
    /**
     * 登录名
     */
    private String username;
    /**
     * 姓名
     */
    private String name;
    /**
     * 是否手机登录
     */
    private boolean mobileLogin;

    public Principal(UserInfo user, boolean mobileLogin) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.name = user.getName();
        this.mobileLogin = mobileLogin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMobileLogin() {
        return mobileLogin;
    }

    public void setMobileLogin(boolean mobileLogin) {
        this.mobileLogin = mobileLogin;
    }

    /**
     * 获取SESSIONID
     */
    public String getSessionid() {
        try {
            return (String) getSession().getId();
        } catch (Exception e) {
            return "";
        }
    }


    public Session getSession() {
        try {
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession(false);
            if (session == null) {
                session = subject.getSession();
            }
            if (session != null) {
                return session;
            }
        } catch (InvalidSessionException e) {

        }
        return null;
    }

}
