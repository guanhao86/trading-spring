package com.spring.free.controller.front;/**
 * Created by hengpu on 2019/2/25.
 */

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.spring.fee.model.TableBroadcastInfo;
import com.spring.fee.service.ITableBroadcastInfoBusiSV;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 前端/公告
 **/
@Controller
@RequestMapping(Global.ADMIN_PATH + "/front/broadcast/")
public class FrontBroadcastController {

    @Autowired
    ITableBroadcastInfoBusiSV iTableBroadcastInfoBusiSV;

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

        TableBroadcastInfo tableBroadcastInfo = new TableBroadcastInfo();
        BeanUtils.copyProperties(queryVO, tableBroadcastInfo);

        PageInfo<TableBroadcastInfo> pageInfo = this.iTableBroadcastInfoBusiSV.queryListPage(tableBroadcastInfo, page, pageSize, null);

        //获取热门话题列表信息
        mav.addObject("page", pageInfo);
        mav.addObject("queryVO",queryVO);
        //返回页面header标题
        PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_DICT_LIST);
        //返回操作提示信息
        PageResult.getPrompt(mav, request, queryVO.getParamMsg());

        mav.setViewName("front/broadcast/list");
        return mav;
    }



    @RequiresPermissions("system:member:view")
    @RequestMapping(value = "view")
    public ModelAndView view(ModelAndView view, HttpServletRequest request, TableBroadcastInfo tableBroadcastInfo) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "公告信息");
        PageResult.getPrompt(view, request, "");
        TableBroadcastInfo tableBroadcastInfo1=this.iTableBroadcastInfoBusiSV.select(tableBroadcastInfo);
//        if(tableMember!=null) {
//            member.setTotal(account.getTotal().doubleValue() / 1000);
//            member.setAvailable(account.getAvailable().doubleValue() / 1000);
//            member.setFreeze(account.getFreeze().doubleValue() / 1000);
//            member.setMoneyFreeze(account.getMoneyFreeze().doubleValue() / 1000);
//            member.setGranaryFreeze(account.getGranaryFreeze().doubleValue() / 1000);
//        }
        view.addObject("broadcast",tableBroadcastInfo1);
        view.setViewName("front/broadcast/view");
        return view;
    }
}
