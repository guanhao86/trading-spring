package com.spring.free.controller.manage;/**
 * Created by hengpu on 2019/2/25.
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.spring.fee.model.TableMember;
import com.spring.fee.service.ITableMemberBusiSV;
import com.spring.free.domain.QueryVO;
import com.spring.free.domain.UserInfo;
import com.spring.free.util.PageResult;
import com.spring.free.util.constraints.Global;
import com.spring.free.util.constraints.PageDefaultConstraints;
import com.spring.free.util.constraints.PromptInfoConstraints;
import com.spring.free.util.exception.ExceptionCodeEnum;
import com.spring.free.util.exception.ServiceException;
import com.spring.free.util.md5.Md5Util;
import com.spring.free.utils.principal.BaseGetPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 后台管理/会员
 **/
@Slf4j
@Controller
@RequestMapping(Global.ADMIN_PATH + "/manage/member/")
public class ManageMemberController {

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

        TableMember tableMember = new TableMember();
        BeanUtils.copyProperties(queryVO, tableMember);

        PageInfo<TableMember> pageInfo = this.iTableMemberBusiSV.queryListPage(tableMember, page, pageSize, null);


        //获取热门话题列表信息
        mav.addObject("page", pageInfo);
        mav.addObject("queryVO",queryVO);
        //返回页面header标题
        PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_DICT_LIST);
        //返回操作提示信息
        PageResult.getPrompt(mav, request, queryVO.getParamMsg());

        mav.setViewName("manage/member/list");
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
    public ModelAndView views(ModelAndView view, HttpServletRequest request, TableMember member, String buttonType) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "会员信息");
        PageResult.getPrompt(view, request, "");
        TableMember tableMember=this.iTableMemberBusiSV.select(member);
//        if(tableMember!=null) {
//            member.setTotal(account.getTotal().doubleValue() / 1000);
//            member.setAvailable(account.getAvailable().doubleValue() / 1000);
//            member.setFreeze(account.getFreeze().doubleValue() / 1000);
//            member.setMoneyFreeze(account.getMoneyFreeze().doubleValue() / 1000);
//            member.setGranaryFreeze(account.getGranaryFreeze().doubleValue() / 1000);
//        }
        view.addObject("member",tableMember);
        view.setViewName("manage/member/edit");
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
    public ModelAndView edit(ModelAndView mav, HttpServletRequest request, TableMember member) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH +"/manage/member/list");
        this.iTableMemberBusiSV.update(member);

        PageResult.setPrompt(map,"操作成功", "success");
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/manage/member/list"), map);
    }

    @RequiresPermissions("system:member:edit")
    @RequestMapping(value = "view")
    public ModelAndView view(ModelAndView view, HttpServletRequest request, TableMember member) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "会员信息");
        PageResult.getPrompt(view, request, "");

        UserInfo user = BaseGetPrincipal.getUser();


        TableMember tableMember=this.iTableMemberBusiSV.select(member);
//        if(tableMember!=null) {
//            member.setTotal(account.getTotal().doubleValue() / 1000);
//            member.setAvailable(account.getAvailable().doubleValue() / 1000);
//            member.setFreeze(account.getFreeze().doubleValue() / 1000);
//            member.setMoneyFreeze(account.getMoneyFreeze().doubleValue() / 1000);
//            member.setGranaryFreeze(account.getGranaryFreeze().doubleValue() / 1000);
//        }
        view.addObject("member",tableMember);
        view.setViewName("manage/member/view");
        return view;
    }

    @RequiresPermissions("system:member:view")
    @RequestMapping({"", "treeIndex"})
    public ModelAndView treeIndex(ModelAndView mav, HttpSession session, TableMember member, HttpServletRequest request,
                                  @RequestParam(value = "page",required = false, defaultValue = PageDefaultConstraints.PAGE) int page,
                                  @RequestParam(value = "rows", required = false, defaultValue = PageDefaultConstraints.PAGE_SIZE) int pageSize) {

        mav.setViewName("manage/member/tree");
        return mav;
    }

    @RequiresPermissions("system:member:edit")
    @RequestMapping(value = "add")
    public ModelAndView add(ModelAndView view, HttpServletRequest request) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "会员信息注册");
        view.setViewName("manage/member/form");
        return view;
    }

    @RequiresPermissions("system:member:edit")
    @RequestMapping(value = "register")
    public ModelAndView register(ModelAndView view, HttpServletRequest request, TableMember m) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH +"/manage/member/register");
        TableMember member = null;
        try {
            member = this.iTableMemberBusiSV.regist(m,2);
        }catch (ServiceException e) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), e.getMessage(), map.get(Global.URL).toString(), map);
        }catch (Exception e1) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), e1.getMessage(), map.get(Global.URL).toString(), map);
        }

        String msg = "恭喜您注册成功，您的会员编号为："+member.getMemberId()+"，默认登录密码为注册手机号码后6位，请登录后自行修改密码";

        PageResult.setPrompt(map,"操作成功", "success");
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/manage/member/list"), map);
    }

    /**
     * 跳转到注册页面
     * @param mav
     * @param session
     * @param member
     * @param request
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping("registSimpleIndex")
    public ModelAndView registSimpleIndex(ModelAndView mav, HttpSession session, TableMember member, HttpServletRequest request,
                                  @RequestParam(value = "page",required = false, defaultValue = PageDefaultConstraints.PAGE) int page,
                                  @RequestParam(value = "rows", required = false, defaultValue = PageDefaultConstraints.PAGE_SIZE) int pageSize) {

        mav.setViewName("manage/member/registSimple");
        return mav;
    }

    /**
     * 会员自己注册页面
     * @param view
     * @param request
     * @param m
     * @return
     */
    @RequestMapping(value = "registSimple")
    @ResponseBody
    public JSONObject registSimple(HttpServletRequest request,@RequestBody TableMember m) {

        log.info(JSON.toJSONString(m));

        JSONObject object = new JSONObject();
        object.put("code","0");
        TableMember member;
        try {
            member = this.iTableMemberBusiSV.regist(m, 1);
        }catch (ServiceException e) {
            object.put("code", "1");
            object.put("msg", e.getMessage());
        }catch (Exception e1) {
            object.put("code", "1");
            object.put("msg", e1.getMessage());
        }
        return object;
    }

    /**
     * 重置密码
     * @param mav
     * @param request
     * @param member
     * @return
     */
    @RequiresPermissions("system:member:edit")
    @RequestMapping(value = "resetPassword")
    public ModelAndView resetPassword(ModelAndView mav, HttpServletRequest request, TableMember member) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH +"/manage/member/list");
        TableMember m= this.iTableMemberBusiSV.select(member);
        m.setPassword(Md5Util.md5Hex(m.getPhone().substring(m.getPhone().length()-6)));
        this.iTableMemberBusiSV.update(m);
        PageResult.setPrompt(map,"操作成功", "success");
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/manage/member/list"), map);
    }
}
