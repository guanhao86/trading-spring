package com.spring.free.config;

import com.spring.free.domain.Principal;
import com.spring.free.domain.UserInfo;
import com.spring.free.domain.UsernamePasswordCaptchaToken;
import com.spring.free.system.UserService;
import com.spring.free.util.constraints.GlobalConstraints;
import com.spring.free.util.constraints.PromptInfoConstraints;
import com.spring.free.util.exception.CaptchaException;
import com.spring.free.util.exception.EnabledException;
import com.spring.free.util.exception.UserException;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * Shiro 认证
 * @author Memory
 * @date 2016/10/20
 * @version 1.0
 * @Inc 恒谱科技
 */
public class AuthenticationFilter extends FormAuthenticationFilter {
    private Logger logger = Logger.getLogger(AuthenticationFilter.class);
    @Resource
    private UserService userService;

    public static final String DEFAULT_CAPTCHA_PARAM = "captcha";

    private String captchaParam = DEFAULT_CAPTCHA_PARAM;

    public String getCaptchaParam() {

        return captchaParam;

    }

    protected String getCaptcha(ServletRequest request) {

        return WebUtils.getCleanParam(request, getCaptchaParam());

    }

    protected String getUrl(ServletRequest request) {

        return WebUtils.getCleanParam(request, "url");

    }

    protected String getMark(ServletRequest request) {
        return WebUtils.getCleanParam(request, "mark");
    }

    protected String getSystemType(ServletRequest request){
        return WebUtils.getCleanParam(request, "systemType");
    }


    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {

        String username = getUsername(request);

        String password = getPassword(request);

        String captcha = getCaptcha(request);

        String url = getUrl(request);

        String mark = getMark(request);

        String sysType = getSystemType(request);

        boolean rememberMe = false;

        String host = getHost(request);

        return new UsernamePasswordCaptchaToken(username, password.toCharArray(), rememberMe, host, captcha, url, mark, sysType);

    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        UsernamePasswordCaptchaToken authenticationToken = null;
        authenticationToken = (UsernamePasswordCaptchaToken) token;
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        session.setAttribute("mark", "true");
        request.setAttribute("username", authenticationToken.getUsername());
        if (e instanceof IncorrectCredentialsException) {
            request.setAttribute("e", PromptInfoConstraints.SYS_USER_PWD_FAIL);
        } else if (e instanceof CaptchaException) {
            request.setAttribute("e", PromptInfoConstraints.SYS_IDENTIFYING_CODE);
        } else if (e instanceof EnabledException) {
            request.setAttribute("e", PromptInfoConstraints.SYS_USER_ENABLED);
        } else if (e instanceof UserException) {
            request.setAttribute("e", e.getMessage());
        } else {
            logger.error("====================================查看用户登录失败原因查找日志原因  之后删除==================================");
            e.printStackTrace();
            request.setAttribute("e", PromptInfoConstraints.SYS_USER_AUTH_FAIL);
        }

        return true;
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        UsernamePasswordCaptchaToken authenticationToken = null;
        try {
            authenticationToken = (UsernamePasswordCaptchaToken) token;
            Session session = subject.getSession();
            session.setAttribute("mark", "false");
            String usernameSessionKey = "org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY";
            String username = "";

            SimplePrincipalCollection simplePrincipalCollection = null;
            simplePrincipalCollection = (SimplePrincipalCollection) session.getAttribute(usernameSessionKey);
            Object pricipp = simplePrincipalCollection.getPrimaryPrincipal();
            Principal u = (Principal) pricipp;

            username = u.getUsername();

            insertUserLoginIP(username, request.getRemoteAddr());
            UserInfo userInfo = userService.getUserByUsernameOrPhoneLogin(username);
            session.setAttribute(GlobalConstraints.USER_INFO, userInfo);
            WebUtils.getAndClearSavedRequest(request);
            session.setAttribute("systemType", userInfo.getUserType().split(",")[0]);
            this.setSuccessUrl("/");

        } catch (Exception e) {

            e.printStackTrace();
            logger.error(e.toString());
        }

        return super.onLoginSuccess(token, subject, request, response);
    }

    private void insertUserLoginIP(String username, String ip) {
        UserInfo user = new UserInfo();
        user.setUsername(username);
        user.setLoginDate(new Date());
        user.setLoginIp(ip);
        userService.setLoginIP(user);
    }

}
