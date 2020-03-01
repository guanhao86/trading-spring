package com.spring.free.domain;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * Created by Memory on 2014/11/12.
 */
public class UsernamePasswordCaptchaToken extends UsernamePasswordToken {


    private String captcha;
    private String url;
    private String mark;
    private String systemType;
    private boolean mobileLogin;

    public String getUrl() {
        return url;
    }


    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getSystemType() {
        return systemType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    public UsernamePasswordCaptchaToken() {
        super();

    }

    public UsernamePasswordCaptchaToken(String username, char[] password,
                                        boolean rememberMe, String host, String captcha, String url, String mark, String systemType) {
        super(username, password, rememberMe, host);
        this.captcha = captcha;
        this.mark = mark;
        this.url = url;
        this.systemType = systemType;
    }

    public boolean isMobileLogin() {
        return mobileLogin;
    }

}
