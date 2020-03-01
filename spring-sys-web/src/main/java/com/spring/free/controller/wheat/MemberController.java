package com.spring.free.controller.wheat;/**
 * Created by hengpu on 2019/2/25.
 */

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.spring.fee.constants.InvestConstants;
import com.spring.fee.model.TWheatAccount;
import com.spring.fee.model.TWheatMember;
import com.spring.fee.model.TWheatMemberDZ;
import com.spring.fee.service.ITWheatAccountBusiSV;
import com.spring.fee.service.ITWheatMemberBusiSV;
import com.spring.free.common.service.AsyncRestService;
import com.spring.free.common.service.SendSmsService;
import com.spring.free.manager.MemberExtendManager;
import com.spring.free.util.PageResult;
import com.spring.free.util.constraints.Global;
import com.spring.free.util.constraints.PageDefaultConstraints;
import com.spring.free.util.constraints.PromptInfoConstraints;
import com.spring.free.util.exception.ExceptionCodeEnum;
import com.spring.free.util.exception.RestException;
import com.spring.free.util.exception.ServiceException;
import com.spring.free.util.md5.Md5Util;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;

/**
 * @ClassName MemberController
 * @Description //TODO
 * @Author hengpu
 * @Date 2019/2/25 15:33
 * @Version 1.0
 **/
@Controller
@RequestMapping(Global.ADMIN_PATH + "/member/")
public class MemberController {
    @Autowired
    private ITWheatMemberBusiSV service;
    @Autowired
    private ITWheatAccountBusiSV accountService;
    @Autowired
    private SendSmsService sendSmsService;

    @Autowired
    private AsyncRestService asyncRestService;


    /*
     * @Author bianyx
     * @Description //TODO 热门话题
     * @Date 11:02 2019/1/18
     * @Param [mav, session, post, request, page, pageSize]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequiresPermissions("system:member:view")
    @RequestMapping({"", "list"})
    public ModelAndView topicList(ModelAndView mav, HttpSession session, TWheatMemberDZ member, HttpServletRequest request,
                                  @RequestParam(value = "page",required = false, defaultValue = PageDefaultConstraints.PAGE) int page,
                                  @RequestParam(value = "rows", required = false, defaultValue = PageDefaultConstraints.PAGE_SIZE) int pageSize) {
        String postType = request.getParameter("postType");

        PageInfo<TWheatMember> list = service.pageList(member,page, pageSize);


        //获取热门话题列表信息
        mav.addObject("page", list);
        mav.addObject("member",member);
        //返回页面header标题
        PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_DICT_LIST);
        //返回操作提示信息
        PageResult.getPrompt(mav, request, member.getParamMsg());
        mav.setViewName("wheat/member/list");
        return mav;
    }
    @RequiresPermissions("system:member:edit")
    @RequestMapping(value = "edit")
    public ModelAndView edit(ModelAndView view, HttpServletRequest request, TWheatMember member) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "会员信息修改");
        PageResult.getPrompt(view, request, member.getParamMsg());
        member=service.select(member);
        TWheatAccount account=accountService.selectByMember(member.getMemberId());
        if(account!=null) {
            member.setTotal(account.getTotal().doubleValue() / 1000);
            member.setAvailable(account.getAvailable().doubleValue() / 1000);
            member.setFreeze(account.getFreeze().doubleValue() / 1000);
            member.setMoneyFreeze(account.getMoneyFreeze().doubleValue() / 1000);
            member.setGranaryFreeze(account.getGranaryFreeze().doubleValue() / 1000);
        }
        member.settWheatAccount(account);
        view.addObject("member",member);
        view.setViewName("wheat/member/edit");
        return view;
    }
    @RequiresPermissions("system:member:edit")
    @RequestMapping(value = "save")
    public ModelAndView save(ModelAndView mav, HttpServletRequest request, TWheatMember member) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH +"/member/list");
        TWheatMember  m=service.select(member);
        m.setName(member.getName());
        m.setPhone(member.getPhone());
        m.setReferenceId(member.getReferenceId());
        m.setLevel(member.getLevel());
        service.update(m);
        TWheatAccount account=accountService.selectByMember(member.getMemberId());
        Double available=member.getAvailable()*1000;
        if(available.longValue()>account.getAvailable()){
            //管理员修改可用余额 增加充值
            accountService.modifyAcct(member.getMemberId(),
                    available.longValue()-account.getAvailable(),
                    InvestConstants.Account.DETAIL_OPERTYPE_ADD,
                    InvestConstants.Account.DETAIL_TYPE_OTHER,
                    null,
                    "管理员修改");
        }
        if(member.getAvailable()*1000<account.getAvailable()){
            //管理员修改可用余额 减少提现
            accountService.modifyAcct(member.getMemberId(),
                    account.getAvailable()-available.longValue(),
                    InvestConstants.Account.DETAIL_OPERTYPE_WITHDRAW,
                    InvestConstants.Account.DETAIL_TYPE_OTHER,
                    null,
                    "管理员修改");
        }

        PageResult.setPrompt(map,"操作成功", "success");
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/member/list"), map);
    }

    @RequiresPermissions("system:member:edit")
    @RequestMapping(value = "view")
    public ModelAndView view(ModelAndView view, HttpServletRequest request, TWheatMember member) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "会员信息");
        PageResult.getPrompt(view, request, member.getParamMsg());
        member=service.select(member);
        TWheatAccount account=accountService.selectByMember(member.getMemberId());
        if(account!=null) {
            member.setTotal(account.getTotal().doubleValue() / 1000);
            member.setAvailable(account.getAvailable().doubleValue() / 1000);
            member.setFreeze(account.getFreeze().doubleValue() / 1000);
            member.setMoneyFreeze(account.getMoneyFreeze().doubleValue() / 1000);
            member.setGranaryFreeze(account.getGranaryFreeze().doubleValue() / 1000);
        }
        member.settWheatAccount(account);
        view.addObject("member",member);
        view.setViewName("wheat/member/view");
        return view;
    }

    @RequiresPermissions("system:member:edit")
    @RequestMapping(value = "toAudit")
    public ModelAndView toAudit(ModelAndView view, HttpServletRequest request, TWheatMember member) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "会员信息修改");
        PageResult.getPrompt(view, request, member.getParamMsg());
        member=service.select(member);
        view.addObject("member",member);
        view.setViewName("wheat/member/audit");
        return view;
    }
    @RequiresPermissions("system:member:edit")
    @RequestMapping(value = "audit")
    public ModelAndView audit(ModelAndView mav, HttpServletRequest request, TWheatMember member) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH +"/member/list");

        TWheatMember  m=service.select(member);

        m.setIsAut(member.getIsAut());
        service.update(m);
        PageResult.setPrompt(map,"操作成功", "success");
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/member/list"), map);
    }

    @RequiresPermissions("system:member:edit")
    @RequestMapping(value = "resetPassword")
    public ModelAndView resetPassword(ModelAndView mav, HttpServletRequest request, TWheatMember member) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH +"/member/list");
        TWheatMember  m=service.select(member);
        m.setPassword(Md5Util.md5Hex("123456"));
        service.update(m);
        PageResult.setPrompt(map,"操作成功", "success");
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/member/list"), map);
    }
    @RequiresPermissions("system:member:view")
    @RequestMapping({"", "treeIndex"})
    public ModelAndView treeIndex(ModelAndView mav, HttpSession session, TWheatMemberDZ member, HttpServletRequest request,
                                  @RequestParam(value = "page",required = false, defaultValue = PageDefaultConstraints.PAGE) int page,
                                  @RequestParam(value = "rows", required = false, defaultValue = PageDefaultConstraints.PAGE_SIZE) int pageSize) {

        mav.setViewName("wheat/member/tree");
        return mav;
    }


    @RequiresPermissions("system:member:edit")
    @RequestMapping(value = "ztree")
    public ModelAndView ztree(ModelAndView view, HttpServletRequest request, TWheatMember member) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "会员信息修改");
        PageResult.getPrompt(view, request, member.getParamMsg());
        member=service.select(member);
        view.addObject("member",member);
        view.setViewName("wheat/member/audit");
        return view;
    }

    @RequiresPermissions("system:member:edit")
    @RequestMapping(value = "add")
    public ModelAndView add(ModelAndView view, HttpServletRequest request, TWheatMember member) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "会员信息注册");
        PageResult.getPrompt(view, request, member.getParamMsg());
        view.addObject("member",member);
        view.setViewName("wheat/member/form");
        return view;
    }

    @RequiresPermissions("system:member:edit")
    @RequestMapping(value = "register")
    public ModelAndView register(ModelAndView view, HttpServletRequest request, TWheatMember m) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH +"/member/list");
        String phone=m.getPhone();
        String referenceId=m.getReferenceId();
        TWheatMember  checkMember = service.selectByPhone(phone);
        if(checkMember!=null){
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "手机号已注册！", map.get(Global.URL).toString(), map);
        }
        TWheatMember  referenceIdMember = service.selectByMemberId(referenceId);

        if(referenceIdMember==null){
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "推荐人不存在！", map.get(Global.URL).toString(), map);
        }

        TWheatMember member = new TWheatMember();
        member.setPhone(phone);
        member.setPassword(Md5Util.md5Hex(phone.substring(phone.length()-6,phone.length())));
        member.setReferenceId(referenceId);
        member.setCreateTime(new Date());
        member.setModifyTime(new Date());
        member.setDelFlag(0);
        member.setIsAut("0");
        member.setLevel("1");
        member = service.insert(member);
        member.setMemberId("010"+String.format("%08d", member.getId()));
        member = service.update(member);

        TWheatAccount account = new TWheatAccount();
        account.setMemberId(member.getMemberId());
        account.setTotal(0l);
        account.setAvailable(0l);
        account.setFreeze(0l);
        account.setMoneyFreeze(0l);
        account.setGranaryFreeze(0l);
        account.setGranaryIngFreeze(0l);
        account.setGranaryIngMaxFreeze(0l);

        accountService.insert(account);

        JSONObject responseJson = asyncRestService.createAddress(member.getMemberId());
        if(!responseJson.getBoolean("success")){
            throw new RestException(ExceptionCodeEnum.RESTMEM_ERROR_CODE.getCode(), ExceptionCodeEnum.RESTMEM_ERROR_CODE.getMsg()+":生成钱包地址失败！");
        }
        String msg = "恭喜您注册成功，您的会员编号为："+member.getMemberId()+"，默认登录密码为注册手机号码后6位，请登录后自行修改密码";
        sendSmsService.sendSmd(phone,msg);

        PageResult.setPrompt(map,"操作成功", "success");
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/member/list"), map);
    }
}
