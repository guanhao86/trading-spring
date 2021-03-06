package com.spring.free.controller.manage;/**
 * Created by hengpu on 2019/2/25.
 */

import com.aliyun.oss.OSSClient;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.spring.fee.model.TableInvest;
import com.spring.fee.service.ITableInvestBusiSV;
import com.spring.free.config.ImageUtils;
import com.spring.free.domain.QueryVO;
import com.spring.free.service.ImageService;
import com.spring.free.util.OSSClientUtil;
import com.spring.free.util.PageResult;
import com.spring.free.util.constraints.Global;
import com.spring.free.util.constraints.PageDefaultConstraints;
import com.spring.free.util.constraints.PromptInfoConstraints;
import com.spring.free.util.exception.ExceptionCodeEnum;
import com.spring.free.util.exception.ServiceException;
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
import java.util.Map;

/**
 * 后台管理/充值申请&审核管理表
 **/
@Controller
@RequestMapping(Global.ADMIN_PATH + "/manage/invest/")
public class ManageInvestController {

    @Autowired
    ITableInvestBusiSV iTableInvestBusiSV;

    @Autowired
    ImageService imageService;

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

        TableInvest tableInvest = new TableInvest();
        BeanUtils.copyProperties(queryVO, tableInvest);

        PageInfo<TableInvest> pageInfo = this.iTableInvestBusiSV.queryListPage(tableInvest, page, pageSize, null);

        //获取热门话题列表信息
        mav.addObject("page", pageInfo);
        mav.addObject("queryVO",queryVO);
        //返回页面header标题
        PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_DICT_LIST);
        //返回操作提示信息
        PageResult.getPrompt(mav, request, queryVO.getParamMsg());

        mav.setViewName("manage/invest/list");
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
    public ModelAndView views(ModelAndView view, HttpServletRequest request, TableInvest tableInvest, String buttonType) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "充值申请&审核管理表信息");
        PageResult.getPrompt(view, request, "");
        TableInvest tableMember=this.iTableInvestBusiSV.select(tableInvest);
        view.addObject("invest",tableMember);
        view.setViewName("manage/invest/edit");
        return view;
    }

    /*
     * 审核index
     * @Author jzc
     * @Date 11:06 2019/2/28
     * @Param [view, request, post, buttonType]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequiresPermissions("system:member:edit")
    @RequestMapping(value = "auditIndex")
    public ModelAndView auditIndex(ModelAndView view, HttpServletRequest request, TableInvest tableInvest, String buttonType) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "充值申请&审核管理表信息");
        PageResult.getPrompt(view, request, "");
        TableInvest tableMember = this.iTableInvestBusiSV.select(tableInvest);
        view.addObject("invest",tableMember);
        view.setViewName("manage/invest/audit");
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
    public ModelAndView edit(ModelAndView mav, HttpServletRequest request, TableInvest tableInvest, MultipartFile file) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH +"/manage/invest/list");
        if (file != null) {
            //上传图片
            String imgPath = ImageUtils.upload(file);
            tableInvest.setCertificateImgSrc(imgPath);
        }

        try {
            if (null != tableInvest.getId())
                this.iTableInvestBusiSV.update(tableInvest);
            else
                this.iTableInvestBusiSV.insert(tableInvest);
        }catch (Exception e) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), e.getMessage(), map.get(Global.URL).toString(), map);
        }

        PageResult.setPrompt(map,"操作成功", "success");
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/manage/invest/list"), map);
    }

    @RequiresPermissions("system:member:edit")
    @RequestMapping(value = "view")
    public ModelAndView view(ModelAndView view, HttpServletRequest request, TableInvest tableInvest) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "充值申请&审核管理表信息");
        PageResult.getPrompt(view, request, "");
        TableInvest tableInvest1=this.iTableInvestBusiSV.select(tableInvest);
//        if(tableMember!=null) {
//            member.setTotal(account.getTotal().doubleValue() / 1000);
//            member.setAvailable(account.getAvailable().doubleValue() / 1000);
//            member.setFreeze(account.getFreeze().doubleValue() / 1000);
//            member.setMoneyFreeze(account.getMoneyFreeze().doubleValue() / 1000);
//            member.setGranaryFreeze(account.getGranaryFreeze().doubleValue() / 1000);
//        }
        view.addObject("invest",tableInvest1);
        view.setViewName("manage/invest/view");
        return view;
    }

    //
    /*
     * @Author bianyx
     * @Description //TODO 审核
     * @Date 11:07 2019/1/18
     * @Param [mav, request, topItem, post, buttonType, ghPic1]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequiresPermissions("system:member:edit")
    @RequestMapping(value = "audit")
    public ModelAndView audit(ModelAndView mav, HttpServletRequest request, TableInvest tableInvest, MultipartFile file) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH +"/manage/invest/list");
        ModelAndView modelAndView =  new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/manage/invest/list"), map);
        try {
            this.iTableInvestBusiSV.audit(tableInvest);
        }catch (Exception e) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), e.getMessage(), map.get(Global.URL).toString(), map);

        }

        PageResult.setPrompt(map,"操作成功", "success");
        return modelAndView;
    }


}
