package com.spring.free.controller.front;/**
 * Created by hengpu on 2019/2/25.
 */

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.spring.fee.model.TableGoods;
import com.spring.fee.model.TableMember;
import com.spring.fee.model.TableMemberAccountDetail;
import com.spring.fee.model.TableOrder;
import com.spring.fee.service.IMemberAccountDetailBusiSV;
import com.spring.fee.service.ITableMemberBusiSV;
import com.spring.free.config.CommonUtils;
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
import org.apache.commons.lang.StringUtils;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 前端/充值,账户接口
 **/
@Controller
@RequestMapping(Global.ADMIN_PATH + "/front/account/")
public class FrontAccountController {

    @Autowired
    IMemberAccountDetailBusiSV iMemberAccountDetailBusiSV;

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

        TableMemberAccountDetail memberAccountDetail = new TableMemberAccountDetail();
        BeanUtils.copyProperties(queryVO, memberAccountDetail);

        UserInfo user = BaseGetPrincipal.getUser();

        //获取会员信息
        TableMember tabelMember = iTableMemberBusiSV.selectByMemberId(user.getUsername());
        List<TableMember> list = new ArrayList();
        list.add(tabelMember);

        memberAccountDetail.setMemberId(user.getUsername());

        PageInfo<TableMemberAccountDetail> pageInfo = this.iMemberAccountDetailBusiSV.queryListPage(memberAccountDetail, page, pageSize, CommonUtils.getStartEnd(queryVO));

        //获取热门话题列表信息
        mav.addObject("page", pageInfo);
        mav.addObject("member", list);
        mav.addObject("queryVO",queryVO);
        //返回页面header标题
        PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_DICT_LIST);
        //返回操作提示信息
        PageResult.getPrompt(mav, request, queryVO.getParamMsg());

        mav.setViewName("front/account/list");
        return mav;
    }


    @RequiresPermissions("system:member:view")
    @RequestMapping(value = "transferInnerIndex")
    public ModelAndView transferInnerIndex(ModelAndView view, HttpServletRequest request) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "账本内部互转");
        PageResult.getPrompt(view, request, "");

        UserInfo user = BaseGetPrincipal.getUser();

        TableMember member = this.iTableMemberBusiSV.selectByMemberId(user.getUsername());

        view.addObject("member", member);
        view.setViewName("front/account/transferInner");
        return view;
    }

    @RequiresPermissions("system:member:view")
    @RequestMapping(value = "transferInner")
    public ModelAndView transferInner(ModelAndView view, HttpServletRequest request, QueryVO queryVO) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "账本内部互转");
        PageResult.getPrompt(view, request, "");

        try {
            UserInfo user = BaseGetPrincipal.getUser();
            this.iMemberAccountDetailBusiSV.transferInner(user.getUsername(), queryVO.getAmount(), null);
        }catch (Exception e) {
            map.put(Global.URL, Global.ADMIN_PATH +"/front/account/transferInnerIndex");
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), e.getMessage(), map.get(Global.URL).toString(), map);
        }

        //查询会员信息，返回地址
        PageResult.setPrompt(map,"转账成功", "success");
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/front/account/transferInnerIndex"), map);
    }

    @RequiresPermissions("system:member:view")
    @RequestMapping(value = "transferIndex")
    public ModelAndView transferIndex(ModelAndView view, HttpServletRequest request) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "转账");
        PageResult.getPrompt(view, request, "");

        UserInfo user = BaseGetPrincipal.getUser();

        TableMember member = this.iTableMemberBusiSV.selectByMemberId(user.getUsername());

        view.addObject("member", member);
        view.setViewName("front/account/transfer");
        return view;
    }

    @RequiresPermissions("system:member:view")
    @RequestMapping(value = "transfer")
    public ModelAndView transfer(ModelAndView view, HttpServletRequest request, QueryVO queryVO) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "转账");
        PageResult.getPrompt(view, request, "");

        try {
            UserInfo user = BaseGetPrincipal.getUser();
            if(!user.getPassword().equals(Md5Util.md5Hex(queryVO.getPassword()))) {
                map.put(Global.URL, Global.ADMIN_PATH +"/front/account/transferIndex");
                throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "密码错误", map.get(Global.URL).toString(), map);
            }
            this.iMemberAccountDetailBusiSV.transfer(user.getUsername(), queryVO.getMemberId(), queryVO.getAmount(), queryVO.getRemark(), queryVO.getTransType());
        }catch (Exception e) {
            map.put(Global.URL, Global.ADMIN_PATH +"/front/account/transferIndex");
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), e.getMessage(), map.get(Global.URL).toString(), map);
        }

        //查询会员信息，返回地址
        PageResult.setPrompt(map,"转账成功", "success");
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/front/account/transferIndex"), map);
    }

}
