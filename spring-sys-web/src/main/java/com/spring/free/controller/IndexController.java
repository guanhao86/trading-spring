package com.spring.free.controller;

import com.alibaba.druid.util.StringUtils;
import com.google.common.collect.Maps;
import com.spring.free.benum.EnumSystemType;
import com.spring.free.domain.MenuInfo;
import com.spring.free.system.MenuService;
import com.spring.free.util.PageResult;
import com.spring.free.util.constraints.GlobalConstraints;
import com.spring.free.util.constraints.PromptInfoConstraints;
import com.spring.free.utils.principal.BaseGetPrincipal;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by spink on 2017/3/28.
 */
@Controller
public class IndexController {

    @Autowired
    private MenuService menuService;
    @RequestMapping("/indexInfo")
    public ModelAndView indexInfo(ModelAndView mav){
        mav.setViewName("index_info");
        return mav;
    }

    @RequestMapping({"/", "/index"})
    public ModelAndView index(ModelAndView mav, Integer systemType, ServletRequest request, HttpSession session) {
        MenuInfo menu = new MenuInfo();
        menu.setMap(Maps.newHashMap());
        if (session.getAttribute("systemType") != null){
            String sysType = session.getAttribute("systemType").toString();
            if (!StringUtils.isEmpty(sysType)){
                systemType = Integer.valueOf(sysType);
                session.removeAttribute("systemType");
            }
        }
        if(systemType == null){
            menu.setSystemType(EnumSystemType.TRADING_SYSTEM.getKey());
            mav.addObject("systemType", 1);
            mav.addObject("systemName", EnumSystemType.TRADING_SYSTEM.getValue());
        } else {
            if (EnumSystemType.TRADING_SYSTEM.getKey().equals(systemType)){
                menu.setSystemType(EnumSystemType.TRADING_SYSTEM.getKey());
                mav.addObject("systemName", EnumSystemType.TRADING_SYSTEM.getValue());
            } else if (EnumSystemType.CONFIGURE_SYSTEM.getKey().equals(systemType)) {
                menu.setSystemType(EnumSystemType.CONFIGURE_SYSTEM.getKey());
                mav.addObject("systemName", EnumSystemType.CONFIGURE_SYSTEM.getValue());
            }
            mav.addObject("systemType", systemType);
        }
        mav.addObject("menuList", getMenu(menu, BaseGetPrincipal.getUser().getId()));
        PageResult.publicModelTitle(mav, PromptInfoConstraints.INDEX,"","/");
        mav.setViewName("index");
        return mav;
    }

    @RequestMapping("/login")
    public ModelAndView login(ModelAndView mav, HttpSession session) {
        if (session.getAttribute(GlobalConstraints.USER_INFO) != null) {
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.logout();
        }
        PageResult.publicModelTitle(mav, PromptInfoConstraints.MEMBER_SYSTEM_LOGIN, "", "");
        mav.setViewName("login2");
        return mav;
    }

    @RequestMapping("/regist")
    public ModelAndView regist(ModelAndView mav, HttpSession session) {

        PageResult.publicModelTitle(mav, PromptInfoConstraints.MEMBER_SYSTEM_LOGIN, "", "");
        mav.setViewName("registSimple");
        return mav;
    }

    @RequestMapping("/handle600")
    public ModelAndView handle600(ModelAndView mav, HttpServletRequest request) {
        mav.setViewName("600");
        return mav;
    }

    private List<MenuInfo> getMenu(MenuInfo menu, Long userID){
        List<MenuInfo> menuList = null;
        if (userID != null && userID != 1L){
            menu.setType("user");
            menuList = menuService.menuList(menu);
        } else {
            menu.setType("list");
            menuList = menuService.menuList(menu);
        }
        return menuList;
    }

}
