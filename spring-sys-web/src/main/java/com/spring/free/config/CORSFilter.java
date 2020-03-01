package com.spring.free.config;/**
 * Created by hengpu on 2019/3/8.
 */

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName CORSFilter
 * @Description //TODO
 * @Author hengpu
 * @Date 2019/3/8 16:15
 * @Version 1.0
 **/
@Component
public class CORSFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        System.out.println( request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Credentials","true"); //是否支持cookie跨域




//        // TODO 支持跨域访问
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        response.setHeader("Access-Control-Allow-Methods", "*");
//        response.setHeader("Access-Control-Allow-Headers", "Content-Type,Access-Token");//这里“Access-Token”是我要传到后台的内容key
//        response.setHeader("Access-Control-Expose-Headers", "*");

//        if (request.getMethod().equals("OPTIONS")) {
//            HttpUtil.setResponse(response, HttpStatus.OK.value(), null);
//            return;
//        }
        chain.doFilter(req, res);
    }

    public void init(FilterConfig filterConfig) {}

    public void destroy() {}

}
