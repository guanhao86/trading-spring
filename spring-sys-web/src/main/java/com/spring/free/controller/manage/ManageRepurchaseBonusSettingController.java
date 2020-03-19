package com.spring.free.controller.manage;/**
 * Created by hengpu on 2019/2/25.
 */

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.spring.fee.model.TableRepurchaseBonusSetting;
import com.spring.fee.service.ITableRepurchaseBonusSettingBusiSV;
import com.spring.free.domain.QueryVO;
import com.spring.free.util.PageResult;
import com.spring.free.util.constraints.Global;
import com.spring.free.util.constraints.PageDefaultConstraints;
import com.spring.free.util.constraints.PromptInfoConstraints;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 后台管理/复消奖金配置
 **/
@Controller
@RequestMapping(Global.ADMIN_PATH + "/manage/repurchaseBonusSetting/")
public class ManageRepurchaseBonusSettingController {

    @Autowired
    ITableRepurchaseBonusSettingBusiSV iTableRepurchaseBonusSettingBusiSV;

    /*
     * @Author gh
     * @Description //TODO 配置列表
     * @Param [mav, session, post, request, page, pageSize]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequiresPermissions("system:member:view")
    @RequestMapping({"", "list"})
    public ModelAndView list(ModelAndView mav, HttpSession session, QueryVO queryVO, HttpServletRequest request,
                             @RequestParam(value = "page",required = false, defaultValue = PageDefaultConstraints.PAGE) int page,
                             @RequestParam(value = "rows", required = false, defaultValue = PageDefaultConstraints.PAGE_SIZE) int pageSize) {
        // String postType = request.getParameter("postType");

        TableRepurchaseBonusSetting tableRepurchaseBonusSetting = new TableRepurchaseBonusSetting();
        BeanUtils.copyProperties(queryVO, tableRepurchaseBonusSetting);

        PageInfo<TableRepurchaseBonusSetting> pageInfo = this.iTableRepurchaseBonusSettingBusiSV.queryListPage(tableRepurchaseBonusSetting, page, pageSize, null);

        //获取热门话题列表信息
        mav.addObject("page", pageInfo);
        mav.addObject("queryVO",queryVO);
        //返回页面header标题
        PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_DICT_LIST);
        //返回操作提示信息
        PageResult.getPrompt(mav, request, queryVO.getParamMsg());

        mav.setViewName("manage/repurchaseBonusSetting/list");
        return mav;
    }


    /*
     * @Author jzc
     * @Date 11:06 2019/2/28
     * @Param [view, request, post, buttonType]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequiresPermissions("system:member:edit")
    @RequestMapping(value = "edit")
    public ModelAndView views(ModelAndView view, HttpServletRequest request, TableRepurchaseBonusSetting tableRepurchaseBonusSetting, String buttonType) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "复消奖金配置信息");
        PageResult.getPrompt(view, request, "");
        TableRepurchaseBonusSetting tableMember=this.iTableRepurchaseBonusSettingBusiSV.select(tableRepurchaseBonusSetting);
        view.addObject("repurchaseBonusSetting",tableMember);
        view.setViewName("manage/repurchaseBonusSetting/edit");
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
    @RequiresPermissions("system:member:edit")
    @RequestMapping(value = "save")
    public ModelAndView edit(ModelAndView mav, HttpServletRequest request, TableRepurchaseBonusSetting tableRepurchaseBonusSetting, MultipartFile thumbnialImgSrcFile, MultipartFile detailImgSrcFile) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH +"/manage/repurchaseBonusSetting/list");

        this.iTableRepurchaseBonusSettingBusiSV.update(tableRepurchaseBonusSetting);
        PageResult.setPrompt(map,"操作成功", "success");
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/manage/repurchaseBonusSetting/list"), map);
    }

}
