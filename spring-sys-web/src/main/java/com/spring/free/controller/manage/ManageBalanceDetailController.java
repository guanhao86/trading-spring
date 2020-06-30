package com.spring.free.controller.manage;/**
 * Created by hengpu on 2019/2/25.
 */

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.spring.fee.model.TableBalanceDetail;
import com.spring.fee.model.TableBalanceDetailDZ;
import com.spring.fee.model.TableMember;
import com.spring.fee.service.ITableBalanceDetailBusiSV;
import com.spring.free.common.util.PythonUtil3;
import com.spring.free.domain.QueryVO;
import com.spring.free.domain.UserInfo;
import com.spring.free.util.DateUtils;
import com.spring.free.util.PageResult;
import com.spring.free.util.constraints.Global;
import com.spring.free.util.constraints.PageDefaultConstraints;
import com.spring.free.util.constraints.PromptInfoConstraints;
import com.spring.free.util.exception.ExceptionCodeEnum;
import com.spring.free.util.exception.ServiceException;
import com.spring.free.utils.principal.BaseGetPrincipal;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 后台管理/结算
 **/
@Controller
@RequestMapping(Global.ADMIN_PATH + "/manage/balance/")
public class ManageBalanceDetailController {

    @Value("${python.path}")
    public String python_path;

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
        if (StringUtils.isNotEmpty(queryVO.getBalanceType()))
            tableBalanceDetail.setBalanceType(Integer.parseInt(queryVO.getBalanceType()));

        PageInfo<TableBalanceDetail> pageInfo = this.iTableBalanceDetailBusiSV.queryListPage(tableBalanceDetail, page, pageSize, null);

        PageInfo<TableBalanceDetailDZ> pageInfoDZ = new PageInfo<>();
        List<TableBalanceDetailDZ> list = new ArrayList<>();
        BeanUtils.copyProperties(pageInfo, pageInfoDZ);

        for (TableBalanceDetail a : pageInfo.getList()) {
            TableBalanceDetailDZ dz = new TableBalanceDetailDZ();
            BeanUtils.copyProperties(a, dz);
            dz.setSettleDate(DateUtils.formatDate(DateUtils.getYesterday(a.getLastTime())));
            list.add(dz);
        }
        pageInfoDZ.setList(list);

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
        mav.addObject("page", pageInfoDZ);
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

    /**
     * 奖金发放
     * @param mav
     * @param request
     * @return
     */
    @RequiresPermissions("system:member:view")
    @RequestMapping(value = "close")
    public ModelAndView close(ModelAndView mav, HttpServletRequest request, TableBalanceDetail tableBalanceDetail) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH +"/manage/balance/list");
        try {
            tableBalanceDetail = this.iTableBalanceDetailBusiSV.select(tableBalanceDetail);
            if (tableBalanceDetail == null || 0 != tableBalanceDetail.getCloseFlag()) {
                throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "结算失败", map.get(Global.URL).toString(), map);
            }
            tableBalanceDetail.setCloseFlag(1);
            this.iTableBalanceDetailBusiSV.update(tableBalanceDetail);

            UserInfo user = BaseGetPrincipal.getUser();
            //结算
            String result = PythonUtil3.runPy(python_path, "send_bonus2.py", user.getUsername(), DateUtils.formatDateTime(tableBalanceDetail.getLastTime()));

        }catch (Exception e) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), e.getMessage(), map.get(Global.URL).toString(), map);
        }
        PageResult.setPrompt(map,"操作成功", "success");
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/manage/balance/list"), map);
    }
}
