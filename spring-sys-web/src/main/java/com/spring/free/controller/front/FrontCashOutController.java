package com.spring.free.controller.front;/**
 * Created by hengpu on 2019/2/25.
 */

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.spring.fee.model.TableCashOut;
import com.spring.fee.model.TableMember;
import com.spring.fee.service.ITableCashOutBusiSV;
import com.spring.fee.service.ITableMemberBusiSV;
import com.spring.free.domain.QueryVO;
import com.spring.free.domain.UserInfo;
import com.spring.free.util.PageResult;
import com.spring.free.util.constraints.Global;
import com.spring.free.util.constraints.PageDefaultConstraints;
import com.spring.free.util.constraints.PromptInfoConstraints;
import com.spring.free.util.exception.ExceptionCodeEnum;
import com.spring.free.util.exception.ServiceException;
import com.spring.free.utils.principal.BaseGetPrincipal;
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
import java.util.Map;

/**
 * 前端/提现
 **/
@Controller
@RequestMapping(Global.ADMIN_PATH + "/front/cashout/")
public class FrontCashOutController {

    @Autowired
    ITableCashOutBusiSV iTableCashOutBusiSV;

    @Autowired
    ITableMemberBusiSV iTableMemberBusiSV;

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

        TableCashOut tableCashOut = new TableCashOut();
        BeanUtils.copyProperties(queryVO, tableCashOut);

        UserInfo user = BaseGetPrincipal.getUser();
        tableCashOut.setMemberId(user.getUsername());

        PageInfo<TableCashOut> pageInfo = this.iTableCashOutBusiSV.queryListPage(tableCashOut, page, pageSize, null);

        //获取热门话题列表信息
        mav.addObject("page", pageInfo);
        mav.addObject("queryVO",queryVO);
        //返回页面header标题
        PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_DICT_LIST);
        //返回操作提示信息
        PageResult.getPrompt(mav, request, queryVO.getParamMsg());

        mav.setViewName("front/cashout/list");
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
    public ModelAndView views(ModelAndView view, HttpServletRequest request, TableCashOut tableCashOut, String buttonType) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "提现信息");
        PageResult.getPrompt(view, request, "");
        UserInfo user = BaseGetPrincipal.getUser();

        TableMember tableMember = this.iTableMemberBusiSV.selectByMemberId(user.getUsername());

        tableCashOut.setMemberId(user.getUsername());

        TableCashOut tableCashOut1 = this.iTableCashOutBusiSV.select(tableCashOut);

        view.addObject("member",tableMember);
        view.addObject("cashout",tableCashOut1);
        view.setViewName("front/cashout/edit");
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
    public ModelAndView edit(ModelAndView mav, HttpServletRequest request, TableCashOut tableCashOut) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH +"/front/cashout/list");

        try {
            UserInfo user = BaseGetPrincipal.getUser();

            TableMember tableMember = this.iTableMemberBusiSV.selectByMemberId(user.getUsername());

            if (tableCashOut.getAmount() > tableMember.getAccountMoney()) {
                throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "余额不足", map.get(Global.URL).toString(), map);
            }

            tableCashOut.setMemberId(user.getUsername());
            this.iTableCashOutBusiSV.insert(tableCashOut);
        }catch (Exception e) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), e.getMessage(), map.get(Global.URL).toString(), map);
        }

        PageResult.setPrompt(map,"操作成功", "success");
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/front/cashout/list"), map);
    }

    @RequiresPermissions("system:member:view")
    @RequestMapping(value = "view")
    public ModelAndView view(ModelAndView view, HttpServletRequest request, TableCashOut tableCashOut) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "提现信息");
        PageResult.getPrompt(view, request, "");

        TableCashOut tableCashOut1=this.iTableCashOutBusiSV.select(tableCashOut);

        view.addObject("cashout",tableCashOut1);
        view.setViewName("front/cashout/view");
        return view;
    }

}
