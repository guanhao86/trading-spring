package com.spring.free.controller.manage;/**
 * Created by hengpu on 2019/2/25.
 */

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.spring.fee.model.TableMember;
import com.spring.fee.model.TableMemberRankChangeDetail;
import com.spring.fee.service.ITableMemberBusiSV;
import com.spring.fee.service.ITableMemberRankChangeDetailBusiSV;
import com.spring.free.domain.QueryVO;
import com.spring.free.util.PageResult;
import com.spring.free.util.constraints.Global;
import com.spring.free.util.constraints.PageDefaultConstraints;
import com.spring.free.util.constraints.PromptInfoConstraints;
import com.spring.free.util.exception.ExceptionCodeEnum;
import com.spring.free.util.exception.ServiceException;
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
 * 后台管理/调整会员头衔接口
 **/
@Controller
@RequestMapping(Global.ADMIN_PATH + "/manage/rankChange/")
public class MemberRankChangeController {

    @Autowired
    ITableMemberRankChangeDetailBusiSV iTableMemberRankChangeDetailBusiSV;

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

        TableMemberRankChangeDetail tableMemberRankChangeDetail = new TableMemberRankChangeDetail();
        BeanUtils.copyProperties(queryVO, tableMemberRankChangeDetail);

        PageInfo<TableMemberRankChangeDetail> pageInfo = this.iTableMemberRankChangeDetailBusiSV.queryListPage(tableMemberRankChangeDetail, page, pageSize, null);

        //获取热门话题列表信息
        mav.addObject("page", pageInfo);
        mav.addObject("queryVO",queryVO);
        //返回页面header标题
        PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_DICT_LIST);
        //返回操作提示信息
        PageResult.getPrompt(mav, request, queryVO.getParamMsg());

        mav.setViewName("manage/rankChange/list");
        return mav;
    }


    /*
     * @Author jzc
     * @Date 11:06 2019/2/28
     * @Param [view, request, post, buttonType]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequiresPermissions("system:member:edit")
    @RequestMapping(value = "changeIndex")
    public ModelAndView changeIndex(ModelAndView view, HttpServletRequest request, String buttonType, QueryVO queryVO) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "会员头衔变更");
        PageResult.getPrompt(view, request, "");
        TableMember tableMember = iTableMemberBusiSV.selectByMemberId(queryVO.getMemberId());
        if (tableMember == null) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "会员不存在！", "", null);
        }
        queryVO.setRank(String.valueOf(tableMember.getmRank()));
        view.addObject("member", queryVO);
        view.setViewName("manage/rankChange/change");
        return view;
    }

    //
    /*
     * 管理员充值
     * @Author bianyx
     * @Description //TODO 编辑新增保存
     * @Date 11:07 2019/1/18
     * @Param [mav, request, topItem, post, buttonType, ghPic1]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequiresPermissions("system:member:edit")
    @RequestMapping(value = "change")
    public ModelAndView change(ModelAndView mav, HttpServletRequest request, QueryVO queryVO) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH +"/manage/member/list");
        
        this.iTableMemberRankChangeDetailBusiSV.changeRank(
                queryVO.getMemberId(), Integer.parseInt(queryVO.getRank()), queryVO.getRemark());

        PageResult.setPrompt(map,"操作成功", "success");
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/manage/member/list"), map);
    }

    @RequiresPermissions("system:member:edit")
    @RequestMapping(value = "view")
    public ModelAndView view(ModelAndView view, HttpServletRequest request, TableMemberRankChangeDetail tableMemberRankChangeDetail) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "账户变更记录信息");
        PageResult.getPrompt(view, request, "");
        TableMemberRankChangeDetail tableMemberRankChangeDetail1=this.iTableMemberRankChangeDetailBusiSV.select(tableMemberRankChangeDetail);
//        if(tableMember!=null) {
//            member.setTotal(rankChange.getTotal().doubleValue() / 1000);
//            member.setAvailable(rankChange.getAvailable().doubleValue() / 1000);
//            member.setFreeze(rankChange.getFreeze().doubleValue() / 1000);
//            member.setMoneyFreeze(rankChange.getMoneyFreeze().doubleValue() / 1000);
//            member.setGranaryFreeze(rankChange.getGranaryFreeze().doubleValue() / 1000);
//        }
        view.addObject("rankChange",tableMemberRankChangeDetail1);
        view.setViewName("manage/rankChange/view");
        return view;
    }


}
