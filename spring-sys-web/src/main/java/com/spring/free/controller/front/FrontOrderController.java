package com.spring.free.controller.front;/**
 * Created by hengpu on 2019/2/25.
 */

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.spring.fee.model.TableGoods;
import com.spring.fee.model.TableMember;
import com.spring.fee.model.TableOrder;
import com.spring.fee.service.ITableGoodsBusiSV;
import com.spring.fee.service.ITableMemberBusiSV;
import com.spring.fee.service.ITableOrderBusiSV;
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
 * 前端/订单
 **/
@Controller
@RequestMapping(Global.ADMIN_PATH + "/front/order/")
public class FrontOrderController {

    @Autowired
    ITableOrderBusiSV iTableOrderBusiSV;

    @Autowired
    ITableMemberBusiSV iTableMemberBusiSV;

    @Autowired
    ITableGoodsBusiSV iTableGoodsBusiSV;

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

        TableOrder tableOrder = new TableOrder();
        BeanUtils.copyProperties(queryVO, tableOrder);
        tableOrder.setId(queryVO.getId()==null?null:queryVO.getId().intValue());

        UserInfo user = BaseGetPrincipal.getUser();
        tableOrder.setMemberId(user.getUsername());

        PageInfo<TableOrder> pageInfo = this.iTableOrderBusiSV.queryListPage(tableOrder, page, pageSize, null);

        //获取热门话题列表信息
        mav.addObject("page", pageInfo);
        mav.addObject("queryVO",queryVO);
        //返回页面header标题
        PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_DICT_LIST);
        //返回操作提示信息
        PageResult.getPrompt(mav, request, queryVO.getParamMsg());

        mav.setViewName("front/order/list");
        return mav;
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
    public ModelAndView edit(ModelAndView mav, HttpServletRequest request, TableOrder tableOrder) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH +"/front/order/list");

        UserInfo user = BaseGetPrincipal.getUser();
        tableOrder.setMemberId(user.getUsername());

        this.iTableOrderBusiSV.update(tableOrder);

        PageResult.setPrompt(map,"操作成功", "success");
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/front/order/list"), map);
    }



    @RequiresPermissions("system:member:view")
    @RequestMapping(value = "view")
    public ModelAndView view(ModelAndView view, HttpServletRequest request, TableOrder tableOrder) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "订单表信息");
        PageResult.getPrompt(view, request, "");
        TableOrder tableOrder1=this.iTableOrderBusiSV.select(tableOrder);
//        if(tableMember!=null) {
//            member.setTotal(account.getTotal().doubleValue() / 1000);
//            member.setAvailable(account.getAvailable().doubleValue() / 1000);
//            member.setFreeze(account.getFreeze().doubleValue() / 1000);
//            member.setMoneyFreeze(account.getMoneyFreeze().doubleValue() / 1000);
//            member.setGranaryFreeze(account.getGranaryFreeze().doubleValue() / 1000);
//        }
        view.addObject("order",tableOrder1);
        view.setViewName("front/order/view");
        return view;
    }

    @RequiresPermissions("system:member:view")
    @RequestMapping(value = "buyIndex")
    public ModelAndView buyIndex(ModelAndView view, HttpServletRequest request, TableOrder tableOrder) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "填写购买信息");
        PageResult.getPrompt(view, request, "");

        UserInfo user = BaseGetPrincipal.getUser();

        TableMember member = this.iTableMemberBusiSV.selectByMemberId(user.getUsername());

        //计算总金额
        TableGoods goods = new TableGoods();
        goods.setId(tableOrder.getGoodsId());
        goods = this.iTableGoodsBusiSV.select(goods);

        tableOrder.setPrice(tableOrder.getAmount() * goods.getPrice());

        //查询会员信息，返回地址
        view.addObject("order", tableOrder);
        view.addObject("member", member);
        view.setViewName("front/order/buyGoods");
        return view;
    }

    @RequiresPermissions("system:member:view")
    @RequestMapping(value = "buy")
    public ModelAndView buy(ModelAndView view, HttpServletRequest request, TableOrder tableOrder) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "订单信息");
        PageResult.getPrompt(view, request, "");

        UserInfo user = BaseGetPrincipal.getUser();
        tableOrder.setMemberId(user.getUsername());
        try {
            this.iTableOrderBusiSV.buy(tableOrder);
        }catch (Exception e) {
            map.put(Global.URL, Global.ADMIN_PATH +"/front/order/buyResult");
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), e.getMessage(), map.get(Global.URL).toString(), map);
        }

        //查询会员信息，返回地址
        view.addObject("result", "购买成功");
        view.setViewName("front/order/buyResult");
        return view;
    }

    /*
     * @Author gh
     * @Description //TODO 配置列表
     * @Param [mav, session, post, request, page, pageSize]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequiresPermissions("system:member:view")
    @RequestMapping({"", "buyResult"})
    public ModelAndView buyResult(ModelAndView mav, HttpSession session, QueryVO queryVO, HttpServletRequest request) {

        //返回操作提示信息
        PageResult.getPrompt(mav, request, null);

        mav.setViewName("front/order/buyResult");
        return mav;
    }
}
