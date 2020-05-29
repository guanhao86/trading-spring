package com.spring.free.config;


import com.alibaba.fastjson.JSON;
import com.spring.free.common.domain.AccessResponse;
import com.spring.free.controller.api.ResponseBaseVO;
import com.spring.free.controller.api.ResponseConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ========================
 * token验证拦截器
 * Created with IntelliJ IDEA.
 * User：pyy
 * Date：2019/7/18 9:46
 * Version: v1.0
 * ========================
 */
@Slf4j
public class JwtInterceptor extends HandlerInterceptorAdapter{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ResponseBaseVO responseBaseVO = new ResponseBaseVO();
        // 忽略带JwtIgnore注解的请求, 不做后续token认证校验
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            PassToken jwtIgnore = handlerMethod.getMethodAnnotation(PassToken.class);
            if (jwtIgnore != null) {
                return true;
            }
        }

        if (HttpMethod.OPTIONS.equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        // 获取请求头信息authorization信息
        final String authHeader = request.getHeader(TokenUtil.AUTH_HEADER_KEY);
        log.info("## authHeader= {}", authHeader);
        response.setCharacterEncoding("utf-8");
        if (StringUtils.isBlank(authHeader) || !authHeader.startsWith(TokenUtil.TOKEN_PREFIX)) {
            log.info("### 用户未登录，请先登录 ###");
            AccessResponse accessResponse = AccessResponse.builder().data(null).success(true).rspcode(ResponseConstants.ResponseCode.TOKEN_FAIL).message("未登录。").build();
            response.getWriter().print(JSON.toJSON(accessResponse));
            return false;
        }

        // 获取token
        final String token = authHeader.substring(5);
        try {
            //校验token是否过期
            if(TokenUtil.isExpiration2(token)){
                //如果过期,校验FreshToken
                String authHeaderRefresh = request.getHeader(TokenUtil.AUTH_HEADER_REFESH_KEY);
                String tokenRefresh = authHeaderRefresh.substring(5);
                if (!TokenUtil.isExpiration2(tokenRefresh)) {
                    //FreshToken未过期，返回新的token
                    //获取token
                    String tokenNew = TokenUtil.createJWT(TokenUtil.getUserId(tokenRefresh), TokenUtil.getUsername(tokenRefresh), "",  24 * 3600 * 1000);
                    responseBaseVO.setToken(tokenNew);

                    String tokenRefreshNew = TokenUtil.createJWT(TokenUtil.getUserId(tokenRefresh), TokenUtil.getUsername(tokenRefresh), "",  48 * 3600 * 1000);
                    responseBaseVO.setTokenRefresh(tokenRefreshNew);

                    AccessResponse accessResponse = AccessResponse.builder().data(responseBaseVO).success(true).rspcode(ResponseConstants.ResponseCode.REFRESH_TOKEN).message("刷新token").build();
                    response.getWriter().print(JSON.toJSON(accessResponse));
                    return false;
                }
            }
            // 验证token是否有效--无效已做异常抛出，由全局异常处理后返回对应信息
            TokenUtil.parseJWT(token);
        }catch (Exception e) {
            log.info("### 用户未登录，请先登录 ###");
            AccessResponse accessResponse = AccessResponse.builder().data(responseBaseVO).success(true).rspcode(ResponseConstants.ResponseCode.TOKEN_FAIL).message("未登录:"+e.getMessage()).build();
            response.getWriter().print(JSON.toJSON(accessResponse));
            return false;
        }

        return true;
    }

}