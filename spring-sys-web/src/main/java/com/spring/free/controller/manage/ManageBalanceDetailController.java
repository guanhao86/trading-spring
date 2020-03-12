package com.spring.free.controller.manage;/**
 * Created by hengpu on 2019/2/25.
 */

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.spring.fee.model.TableBalanceDetail;
import com.spring.fee.model.TableBalanceDetailDZ;
import com.spring.fee.service.ITableBalanceDetailBusiSV;
import com.spring.free.domain.QueryVO;
import com.spring.free.util.DateUtils;
import com.spring.free.util.PageResult;
import com.spring.free.util.constraints.Global;
import com.spring.free.util.constraints.PageDefaultConstraints;
import com.spring.free.util.constraints.PromptInfoConstraints;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 后台管理/结算
 **/
@Controller
@RequestMapping(Global.ADMIN_PATH + "/manage/balance/")
public class ManageBalanceDetailController {

    @Autowired
    ITableBalanceDetailBusiSV iTableBalanceDetailBusiSV;

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

        TableBalanceDetailDZ tableBalanceDetail = new TableBalanceDetailDZ();
        BeanUtils.copyProperties(queryVO, tableBalanceDetail);
        if (StringUtils.isNotEmpty(queryVO.getStart()))
            tableBalanceDetail.setLastTimeStart(DateUtils.parseDate(queryVO.getStart()+" 00:00:00"));
        if (StringUtils.isNotEmpty(queryVO.getEnd()))
            tableBalanceDetail.setLastTimeEnd(DateUtils.parseDate(queryVO.getEnd()+" 23:59:59"));

        PageInfo<TableBalanceDetail> pageInfo = this.iTableBalanceDetailBusiSV.queryListPage(tableBalanceDetail, page, pageSize, null);

        //累计
        List<TableBalanceDetail> listAll = this.iTableBalanceDetailBusiSV.selectByGroup(null, null, null);
        //上月
        Date start = DateUtils.getFirstDayOfMonth(DateUtils.getSysDate());
        Date end = DateUtils.getFirstDayOfNextMonth(DateUtils.getSysDate());
        List<TableBalanceDetail> listLastMonth = this.iTableBalanceDetailBusiSV.selectByGroup(null, start, end);

        //前日
        start = DateUtils.getYesterday(DateUtils.getSysDate());
        end = DateUtils.getNextDate(DateUtils.getSysDate());
        List<TableBalanceDetail> listYesterday = this.iTableBalanceDetailBusiSV.selectByGroup(null, start, end);


        //获取热门话题列表信息
        mav.addObject("page", pageInfo);
        mav.addObject("queryVO",queryVO);
        mav.addObject("all", listAll);
        mav.addObject("thisMonth", listLastMonth);
        mav.addObject("yesterday", listYesterday);
        //返回页面header标题
        PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_DICT_LIST);
        //返回操作提示信息
        PageResult.getPrompt(mav, request, queryVO.getParamMsg());

        mav.setViewName("manage/balance/list");
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
    public ModelAndView views(ModelAndView view, HttpServletRequest request, TableBalanceDetail tableBalanceDetail, String buttonType) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "结算信息");
        PageResult.getPrompt(view, request, "");
        TableBalanceDetail tableMember=this.iTableBalanceDetailBusiSV.select(tableBalanceDetail);
        view.addObject("balance",tableMember);
        view.setViewName("manage/balance/edit");
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
    public ModelAndView edit(ModelAndView mav, HttpServletRequest request, TableBalanceDetail tableBalanceDetail) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH +"/manage/balance/list");
        this.iTableBalanceDetailBusiSV.update(tableBalanceDetail);

        PageResult.setPrompt(map,"操作成功", "success");
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/manage/balance/list"), map);
    }

    @RequiresPermissions("system:member:edit")
    @RequestMapping(value = "view")
    public ModelAndView view(ModelAndView view, HttpServletRequest request, TableBalanceDetail tableBalanceDetail) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "结算信息");
        PageResult.getPrompt(view, request, "");
        TableBalanceDetail tableBalanceDetail1=this.iTableBalanceDetailBusiSV.select(tableBalanceDetail);
//        if(tableMember!=null) {
//            member.setTotal(account.getTotal().doubleValue() / 1000);
//            member.setAvailable(account.getAvailable().doubleValue() / 1000);
//            member.setFreeze(account.getFreeze().doubleValue() / 1000);
//            member.setMoneyFreeze(account.getMoneyFreeze().doubleValue() / 1000);
//            member.setGranaryFreeze(account.getGranaryFreeze().doubleValue() / 1000);
//        }
        view.addObject("balance",tableBalanceDetail1);
        view.setViewName("manage/balance/view");
        return view;
    }
}
