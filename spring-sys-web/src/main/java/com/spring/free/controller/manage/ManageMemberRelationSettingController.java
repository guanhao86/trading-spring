package com.spring.free.controller.manage;/**
 * Created by hengpu on 2019/2/25.
 */

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.spring.fee.model.TableMember;
import com.spring.fee.model.TableMemberRelationSetting;
import com.spring.fee.service.ITableMemberRelationSettingBusiSV;
import com.spring.free.domain.QueryVO;
import com.spring.free.util.DateUtils;
import com.spring.free.util.PageResult;
import com.spring.free.util.constraints.Global;
import com.spring.free.util.constraints.PageDefaultConstraints;
import com.spring.free.util.constraints.PromptInfoConstraints;
import com.spring.free.util.exception.ExceptionCodeEnum;
import com.spring.free.util.exception.ServiceException;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;

/**
 * 后台管理/网络权限配置表
 **/
@Controller
@RequestMapping(Global.ADMIN_PATH + "/manage/memberRelationSetting/")
public class ManageMemberRelationSettingController {

    @Autowired
    ITableMemberRelationSettingBusiSV iTableMemberRelationSettingBusiSV;

    /*
     * @Author haha
     * @Description //TODO 配置列表
     * @Param [mav, session, post, request, page, pageSize]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequiresPermissions("system:member:view")
    @RequestMapping({"", "list"})
    public ModelAndView list(ModelAndView mav, HttpSession session, QueryVO queryVO, HttpServletRequest request,
                             @RequestParam(value = "page",required = false, defaultValue = PageDefaultConstraints.PAGE) int page,
                             @RequestParam(value = "rows", required = false, defaultValue = PageDefaultConstraints.PAGE_SIZE) int pageSize) {

        TableMemberRelationSetting queryParam = new TableMemberRelationSetting();
        BeanUtils.copyProperties(queryVO, queryParam);

        Date now = DateUtils.getSysDate();
        PageInfo<TableMemberRelationSetting> pageInfo = this.iTableMemberRelationSettingBusiSV.queryListPage(queryParam, page, pageSize, null);
        if (pageInfo != null && !CollectionUtils.isEmpty(pageInfo.getList())) {
            for (TableMemberRelationSetting setting : pageInfo.getList()) {
                if (setting.getExpireTime().getTime() < now.getTime()) {
                    setting.setType("0");
                }
            }

        }
        //获取热门话题列表信息
        mav.addObject("page", pageInfo);
        mav.addObject("queryVO",queryVO);
        //返回页面header标题
        PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_DICT_LIST);
        //返回操作提示信息
        PageResult.getPrompt(mav, request, queryVO.getParamMsg());

        mav.setViewName("manage/memberRelationSetting/list");
        return mav;
    }


    /*
     * @Author jzc
     * @Date 11:06 2019/2/28
     * @Param [view, request, post, buttonType]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequiresPermissions("system:member:view")
    @RequestMapping(value = "edit")
    public ModelAndView views(ModelAndView view, HttpServletRequest request, TableMemberRelationSetting tableMemberRelationSetting, String buttonType) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "网络权限配置信息");
        PageResult.getPrompt(view, request, "");
        TableMemberRelationSetting relationSetting=this.iTableMemberRelationSettingBusiSV.select(tableMemberRelationSetting);

        view.addObject("relationSetting",relationSetting);
        view.setViewName("manage/memberRelationSetting/edit");
        return view;
    }
    //
    /*
     * @Author bianyx
     * @Description //TODO 编辑新增保存
     * @Date 11:07 2019/1/18
     * @Param [mav, request, topItem, post, buttonType, ghPic1]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequiresPermissions("system:member:view")
    @RequestMapping(value = "save")
    public ModelAndView edit(ModelAndView mav, HttpServletRequest request, TableMemberRelationSetting tableMemberRelationSetting) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH +"/manage/memberRelationSetting/edit");
        try {
            this.iTableMemberRelationSettingBusiSV.set(tableMemberRelationSetting);
        }catch (Exception e) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), e.getMessage(), map.get(Global.URL).toString(), map);
        }

        PageResult.setPrompt(map,"操作成功", "success");
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/manage/memberRelationSetting/list"), map);
    }

    /**
     * 删除
     * @param mav
     * @param request
     * @return
     */
    @RequiresPermissions("system:member:view")
    @RequestMapping(value = "delete")
    public ModelAndView delete(ModelAndView mav, HttpServletRequest request, TableMemberRelationSetting tableMemberRelationSetting) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH +"/manage/memberRelationSetting/list");
        try {
            this.iTableMemberRelationSettingBusiSV.delete(tableMemberRelationSetting);
        }catch (Exception e) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), e.getMessage(), map.get(Global.URL).toString(), map);
        }
        PageResult.setPrompt(map,"操作成功", "success");
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/manage/memberRelationSetting/list"), map);
    }
}
