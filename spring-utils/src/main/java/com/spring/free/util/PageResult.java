package com.spring.free.util;

import com.alibaba.druid.util.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Controller 公共返回数据类
 *
 * @author Memory
 * @version 1.0
 */
public class PageResult {

    static Logger logger = Logger.getLogger(PageResult.class.getName());

    /**
     * 创建开始结束时间
     *
     * @param modelAndView
     * @param day
     * @return
     */
    public static ModelAndView publicDate(ModelAndView modelAndView, int day) {
        modelAndView.addObject("startDate", DateUtils.afterNDay(day));
        modelAndView.addObject("endDate", DateUtils.getDate());
        return modelAndView;
    }

    /**
     * 显示页面功能标题
     *
     * @param modelAndView
     * @param funTitle
     * @return
     */
    public static ModelAndView setPageTitle(ModelAndView modelAndView, String funTitle) {
        modelAndView.addObject("funTitle", funTitle);
        return modelAndView;
    }

    /**
     * 提示信息
     *
     * @param promptInfo 提示信息显示的内容
     * @param status     提示信息的状态，如成功或失败等
     * @return
     */
    public static Map setPrompt(Map map, String promptInfo, String status) {
        map.put("msg", promptInfo);
        map.put("successAndFail", status);
        return map;
    }

    /**
     * 返回提示信息，没有提示信息返回空字符串
     *
     * @param mav
     * @param request
     * @return
     */
    public static ModelAndView getPrompt(ModelAndView mav, HttpServletRequest request, String paramMsg) {
        if (StringUtils.isEmpty(paramMsg)) {
            mav.addObject("msg", request.getParameter("msg"));
            mav.addObject("sts", request.getParameter("successAndFail"));
        }
        return mav;
    }

    /**
     * 显示页面标题
     *
     * @param modelAndView
     * @param title
     * @return
     */
    public static ModelAndView publicModelTitle(ModelAndView modelAndView, String title, String systemType, String systemUrl) {
        modelAndView.addObject("title", title);
        modelAndView.addObject("sysType", systemType);
        modelAndView.addObject("sysUrl", systemUrl);
        return modelAndView;
    }

    /**
     * 发生错误时，返回错误页面
     *
     * @param e
     * @param exceptionInfo
     * @return
     */
    public static void publicModelError(Exception e, String exceptionInfo) {
        e.printStackTrace();
        logger.error("异常信息：" + e.toString() + " Author：Memory; 错误提示：" + exceptionInfo);
    }

    public static void publicModelError(Exception e) {
        e.printStackTrace();
        logger.error("异常信息：" + e.toString() + " Author：Memory; 错误提示：" + e.getMessage());
    }

}
