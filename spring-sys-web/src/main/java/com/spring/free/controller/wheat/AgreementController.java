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
import com.spring.free.websocket.WebSocket;
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
@RequestMapping(Global.ADMIN_PATH + "/agreement/")
public class AgreementController {

    @Autowired
    private NoticeService service;
    @Value("${upload.fileSize}")
    private Long fileSize;//允许文件上传最大限制

    @RequiresPermissions("system:agreement:view")
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
        mav.setViewName("wheat/notice/noticeList");
        if(notice.getType().intValue()==7){
            mav.setViewName("wheat/agreement/entrustAgreementList");
        }else {
            mav.setViewName("wheat/agreement/financingAgreementList");
        }

        return mav;
    }

    @RequiresPermissions("system:agreement:edit")
    @RequestMapping(value = "edit")
    public ModelAndView views(ModelAndView view, HttpServletRequest request, Notice notice) {
        Map map = Maps.newHashMap();
        if(notice.getType().intValue()==7){
            PageResult.setPageTitle(view, "委托交易协议修改");
        }else{
            PageResult.setPageTitle(view, "理财协议修改");
        }
        PageResult.getPrompt(view, request, notice.getParamMsg());
        notice=service.get(notice.getId());
        Integer type=notice.getType();
        view.addObject("notice",notice);
        view.setViewName("wheat/agreement/agreementEdit");

        return view;
    }


    @RequiresPermissions("system:agreement:edit")
    @RequestMapping(value = "save")
    public ModelAndView edit( Notice notice) {
        Map map = Maps.newHashMap();
//        map.put(Global.URL, Global.ADMIN_PATH + "/content/edit?id="+content.getId());
        map.put(Global.URL, Global.ADMIN_PATH +"/agreement/edit?type="+notice.getType()+"&id="+notice.getId());
        Notice noticecheck=service.get(notice.getId());
        if(noticecheck!=null){

            service.update(notice);

        }else{

        }
        PageResult.setPrompt(map,"操作成功", "success");
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/agreement/list?type="+notice.getType()), map);
    }


}
