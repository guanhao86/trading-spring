package com.spring.free.controller.wheat;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.spring.free.domain.Notice;
import com.spring.free.service.NoticeService;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping(Global.ADMIN_PATH + "/customer/")
public class CustomerController {

    @Autowired
    private NoticeService service;
    @Value("${upload.fileSize}")
    private Long fileSize;//允许文件上传最大限制

    @RequiresPermissions("system:customer:view")
    @RequestMapping({"", "list"})
    public ModelAndView topicList(ModelAndView mav, Notice notice, HttpServletRequest request,
                                  @RequestParam(value = "page",required = false, defaultValue = PageDefaultConstraints.PAGE) int page,
                                  @RequestParam(value = "rows", required = false, defaultValue = PageDefaultConstraints.PAGE_SIZE) int pageSize) {

        PageInfo<Notice> list = service.pageList(notice,page, pageSize);

        mav.addObject("page", list);
        mav.addObject("notice",notice);
        //返回页面header标题
        PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_DICT_LIST);
        //返回操作提示信息
        PageResult.getPrompt(mav, request, notice.getParamMsg());
        mav.setViewName("wheat/customer/customerList");

        return mav;
    }

    @RequiresPermissions("system:customer:edit")
    @RequestMapping(value = "edit")
    public ModelAndView views(ModelAndView view, HttpServletRequest request, Notice notice) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "联系方式修改");
        PageResult.getPrompt(view, request, notice.getParamMsg());
        notice=service.get(notice.getId());
        Integer type=notice.getType();
        view.addObject("notice",notice);
        view.setViewName("wheat/customer/customerEdit");

        return view;
    }

    @RequiresPermissions("system:customer:edit")
    @RequestMapping(value = "addPre")
    public ModelAndView addPre(ModelAndView view, HttpServletRequest request, Notice notice) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "联系方式新增");
        PageResult.getPrompt(view, request, notice.getParamMsg());
        Integer type=notice.getType();
        view.addObject("notice",notice);
        view.setViewName("wheat/customer/customerForm");

        return view;
    }

    @RequiresPermissions("system:customer:edit")
    @RequestMapping(value = "save")
    public ModelAndView edit(ModelAndView mav, HttpServletRequest request, Notice notice, MultipartFile ghPic1) {
        Map map = Maps.newHashMap();
//        map.put(Global.URL, Global.ADMIN_PATH + "/content/edit?id="+content.getId());
        map.put(Global.URL, Global.ADMIN_PATH +"/customer/addPre");
        Notice noticecheck=service.get(notice.getId());
        if(noticecheck!=null){

            service.update(notice);

        }else{

            notice.setType(3);
            service.insert(notice);
        }

        PageResult.setPrompt(map,"操作成功", "success");
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/customer/list?type=3"), map);
    }

    @RequiresPermissions("system:notice:edit")
    @RequestMapping("delete")
    public ModelAndView userManageDelete(ModelAndView mav, HttpServletRequest request, Notice notice, HttpSession session) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH + "/customer/list?type=3");
        service.delete(notice);
        PageResult.setPrompt(map, PromptInfoConstraints.getDeleteSuccess("联系方式信息"), PromptInfoConstraints.SUCCESS_STATUS);
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/customer/list?type=3"), map);
    }

    @ResponseBody
    @RequestMapping("upload")
    public void upload(HttpServletRequest request, HttpServletResponse response, MultipartFile file) {
        String imgUrl=null;
        try {
            imgUrl = service.upload(file);
            response.getWriter().println(imgUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
