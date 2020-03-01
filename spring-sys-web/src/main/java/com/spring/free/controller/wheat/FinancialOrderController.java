package com.spring.free.controller.wheat;/**
 * Created by hengpu on 2019/2/25.
 */

import com.github.pagehelper.PageInfo;
import com.spring.fee.model.TWheatFinancialOrder;
import com.spring.fee.service.ITWheatFinancialOrderBusiSV;
import com.spring.free.util.PageResult;
import com.spring.free.util.constraints.Global;
import com.spring.free.util.constraints.PageDefaultConstraints;
import com.spring.free.util.constraints.PromptInfoConstraints;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @ClassName FinancialController
 * @Description //TODO
 * @Author hengpu
 * @Date 2019/2/25 16:30
 * @Version 1.0
 **/
@Controller
@RequestMapping(Global.ADMIN_PATH + "/financialOrder/")
public class FinancialOrderController {

    @Autowired
    private ITWheatFinancialOrderBusiSV service;

    @Value("${upload.fileSize}")
    private Long fileSize;//允许文件上传最大限制

    /*
     * @Author bianyx
     * @Description //TODO 热门话题
     * @Date 11:02 2019/1/18
     * @Param [mav, session, post, request, page, pageSize]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequiresPermissions("system:financialOrder:view")
    @RequestMapping({"", "list"})
    public ModelAndView topicList(ModelAndView mav, HttpSession session, TWheatFinancialOrder order, HttpServletRequest request,
                                  @RequestParam(value = "page",required = false, defaultValue = PageDefaultConstraints.PAGE) int page,
                                  @RequestParam(value = "rows", required = false, defaultValue = PageDefaultConstraints.PAGE_SIZE) int pageSize) {

        PageInfo<TWheatFinancialOrder> list = service.pageList(order,page, pageSize);


        //获取热门话题列表信息
        mav.addObject("page", list);
        mav.addObject("order",order);
        //返回页面header标题
        PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_DICT_LIST);
        //返回操作提示信息
        PageResult.getPrompt(mav, request, order.getParamMsg());

        mav.setViewName("wheat/financialOrder/list");
        return mav;
    }



}
