package com.spring.free.controller.front;/**
 * Created by hengpu on 2019/2/25.
 */

import com.github.pagehelper.PageInfo;
import com.spring.fee.model.TableBonusDetail;
import com.spring.fee.service.ITableBonusDetailBusiSV;
import com.spring.free.config.CommonUtils;
import com.spring.free.domain.QueryVO;
import com.spring.free.domain.UserInfo;
import com.spring.free.util.PageResult;
import com.spring.free.util.constraints.Global;
import com.spring.free.util.constraints.PageDefaultConstraints;
import com.spring.free.util.constraints.PromptInfoConstraints;
import com.spring.free.utils.principal.BaseGetPrincipal;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 后台管理/奖金接口
 **/
@Controller
@RequestMapping(Global.ADMIN_PATH + "/front/bonus/")
public class FrontBonusDetailController {

    @Autowired
    ITableBonusDetailBusiSV iTableBonusDetailBusiSV;

    /*
     * @Author haha
     * @Description //TODO 配置列表
     * @Param [mav, session, post, request, page, pageSize]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequiresPermissions("system:member:view")
    @RequestMapping({"", "listIndex"})
    public ModelAndView listIndex(ModelAndView mav, HttpSession session, QueryVO queryVO, HttpServletRequest request,
                                  @RequestParam(value = "page",required = false, defaultValue = PageDefaultConstraints.PAGE) int page,
                                  @RequestParam(value = "rows", required = false, defaultValue = PageDefaultConstraints.PAGE_SIZE) int pageSize) {
        // String postType = request.getParameter("postType");

        TableBonusDetail memberAccountDetail = new TableBonusDetail();
        BeanUtils.copyProperties(queryVO, memberAccountDetail);

        //获取热门话题列表信息
        mav.addObject("queryVO",queryVO);
        //返回页面header标题
        PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_DICT_LIST);
        //返回操作提示信息
        PageResult.getPrompt(mav, request, queryVO.getParamMsg());

        mav.setViewName("front/bonus/listIndex");
        return mav;
    }

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

        TableBonusDetail memberAccountDetail = new TableBonusDetail();
        BeanUtils.copyProperties(queryVO, memberAccountDetail);

        UserInfo user = BaseGetPrincipal.getUser();
        memberAccountDetail.setMemberId(user.getUsername());

        PageInfo<TableBonusDetail> pageInfo = this.iTableBonusDetailBusiSV.queryListPage(memberAccountDetail, page, pageSize, CommonUtils.getStartEnd(queryVO));

        //获取热门话题列表信息
        mav.addObject("page", pageInfo);
        mav.addObject("queryVO",queryVO);
        //返回页面header标题
        PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_DICT_LIST);
        //返回操作提示信息
        PageResult.getPrompt(mav, request, queryVO.getParamMsg());

        mav.setViewName("front/bonus/list");
        return mav;
    }

}
