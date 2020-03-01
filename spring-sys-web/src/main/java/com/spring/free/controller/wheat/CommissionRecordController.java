package com.spring.free.controller.wheat;/**
 * Created by hengpu on 2019/2/25.
 */

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.spring.free.domain.CommissionRecord;
import com.spring.free.domain.Content;
import com.spring.free.service.CommissionRecordService;
import com.spring.free.service.ContentService;
import com.spring.free.util.PageResult;
import com.spring.free.util.constraints.Global;
import com.spring.free.util.constraints.PageDefaultConstraints;
import com.spring.free.util.constraints.PromptInfoConstraints;
import com.spring.free.util.exception.ExceptionCodeEnum;
import com.spring.free.util.exception.ServiceException;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
 * @ClassName ContentController
 * @Description //TODO
 * @Author hengpu
 * @Date 2019/2/25 16:30
 * @Version 1.0
 **/
@Controller
@RequestMapping(Global.ADMIN_PATH + "/commissionRecord/")
public class CommissionRecordController {

    @Autowired
    private CommissionRecordService service;

    @Value("${upload.fileSize}")
    private Long fileSize;//允许文件上传最大限制

    /*
     * @Author bianyx
     * @Description //TODO 热门话题
     * @Date 11:02 2019/1/18
     * @Param [mav, session, post, request, page, pageSize]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequiresPermissions("system:commissionRecord:view")
    @RequestMapping({"", "list"})
    public ModelAndView topicList(ModelAndView mav, HttpSession session, CommissionRecord commissionRecord, HttpServletRequest request,
                                  @RequestParam(value = "page",required = false, defaultValue = PageDefaultConstraints.PAGE) int page,
                                  @RequestParam(value = "rows", required = false, defaultValue = PageDefaultConstraints.PAGE_SIZE) int pageSize) {

        PageInfo<CommissionRecord> list = service.pageList(commissionRecord,page, pageSize);

        //获取热门话题列表信息
        mav.addObject("page", list);
        mav.addObject("commissionRecord",commissionRecord);
        //返回页面header标题
        PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_DICT_LIST);
        //返回操作提示信息
        PageResult.getPrompt(mav, request, commissionRecord.getParamMsg());
        mav.setViewName("wheat/commissionRecord/list");
        return mav;
    }


    /*
* @Author jzc
* @Description //TODO 广告编辑跳转
* @Date 11:06 2019/2/28
* @Param [view, request, post, buttonType]
* @return org.springframework.web.servlet.ModelAndView
**/
    @RequiresPermissions("system:commissionRecord:edit")
    @RequestMapping(value = "edit")
    public ModelAndView views(ModelAndView view, HttpServletRequest request, CommissionRecord commissionRecord ) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "委托交易审核");
        PageResult.getPrompt(view, request, commissionRecord.getParamMsg());
        commissionRecord=service.get(commissionRecord.getId());
        view.addObject("commissionRecord",commissionRecord);
        view.setViewName("wheat/commissionRecord/audit");

        return view;
    }



    /*
  * @Author bianyx
  * @Description //
  * @Date 11:07 2019/1/18
  * @Param [mav, request, topItem, post, buttonType, ghPic1]
  * @return org.springframework.web.servlet.ModelAndView
  **/
    @RequiresPermissions("system:commissionRecord:edit")
    @RequestMapping(value = "audit")
    public ModelAndView edit(ModelAndView mav, HttpServletRequest request, CommissionRecord commissionRecord) {
        Map map = Maps.newHashMap();
        CommissionRecord  c=service.get(commissionRecord.getId());
        c.setState(commissionRecord.getState());
        service.update(c);
        PageResult.setPrompt(map,"操作成功", "success");
        map.put(Global.URL, Global.ADMIN_PATH +"/commissionRecord/list?type="+c.getType());
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/commissionRecord/list?type="+c.getType()), map);
    }
    /*
  * @Author jzc
  * @Description //TODO 广告视频地址删除
  * @Date 11:07 2019/1/18
  * @Param [mav, request, topItem, post, buttonType, ghPic1]
  * @return org.springframework.web.servlet.ModelAndView
  **/
    @RequiresPermissions("system:commissionRecord:edit")
    @RequestMapping("delete")
    public ModelAndView userManageDelete(ModelAndView mav, HttpServletRequest request, CommissionRecord commissionRecord, HttpSession session) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH + "/commissionRecord/list");
        service.delete(commissionRecord);
        PageResult.setPrompt(map, PromptInfoConstraints.getDeleteSuccess("用户信息"), PromptInfoConstraints.SUCCESS_STATUS);
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/commissionRecord/list"), map);
    }

}
