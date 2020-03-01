package com.spring.free.controller.wheat;/**
 * Created by hengpu on 2019/2/25.
 */

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.spring.free.domain.Content;
import com.spring.free.domain.Fee;
import com.spring.free.service.ContentService;
import com.spring.free.service.FeeService;
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
 * @ClassName FeeController
 * @Description //TODO
 * @Author hengpu
 * @Date 2019/2/25 16:30
 * @Version 1.0
 **/
@Controller
@RequestMapping(Global.ADMIN_PATH + "/fee/")
public class FeeController {

    @Autowired
    private FeeService service;

    @Value("${upload.fileSize}")
    private Long fileSize;//允许文件上传最大限制

    /*
     * @Author bianyx
     * @Description //TODO 热门话题
     * @Date 11:02 2019/1/18
     * @Param [mav, session, post, request, page, pageSize]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequiresPermissions("system:fee:view")
    @RequestMapping({"", "list"})
    public ModelAndView topicList(ModelAndView mav, HttpSession session, Fee fee, HttpServletRequest request,
                                  @RequestParam(value = "page",required = false, defaultValue = PageDefaultConstraints.PAGE) int page,
                                  @RequestParam(value = "rows", required = false, defaultValue = PageDefaultConstraints.PAGE_SIZE) int pageSize) {
        String postType = request.getParameter("postType");

        PageInfo<Fee> list = service.pageList(fee,page, pageSize);


        //获取热门话题列表信息
        mav.addObject("page", list);
        mav.addObject("fee",fee);
        //返回页面header标题
        PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_DICT_LIST);
        //返回操作提示信息
        PageResult.getPrompt(mav, request, fee.getParamMsg());

        mav.setViewName("wheat/fee/feeList");
        return mav;
    }


    /*
* @Author jzc
* @Description //TODO 广告编辑跳转
* @Date 11:06 2019/2/28
* @Param [view, request, post, buttonType]
* @return org.springframework.web.servlet.ModelAndView
**/
    @RequiresPermissions("system:fee:edit")
    @RequestMapping(value = "edit")
    public ModelAndView views(ModelAndView view, HttpServletRequest request, Fee fee, String buttonType) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "手续费配置修改");
        PageResult.getPrompt(view, request, fee.getParamMsg());
        fee=service.get(fee.getId());
        view.addObject("fee",fee);
        view.setViewName("wheat/fee/feeEdit");
        return view;
    }

   /*
  * @Author bianyx
  * @Description //TODO 广告编辑新增保存
  * @Date 11:07 2019/1/18
  * @Param [mav, request, topItem, post, buttonType, ghPic1]
  * @return org.springframework.web.servlet.ModelAndView
  **/
    @RequiresPermissions("system:fee:edit")
    @RequestMapping(value = "save")
    public ModelAndView edit(ModelAndView mav, HttpServletRequest request, Fee fee) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH + "/fee/edit?id="+fee.getId());
        Fee f=service.get(fee.getId());
        f.setFee(fee.getFee());
        service.update(f);
        PageResult.setPrompt(map,"操作成功", "success");
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/fee/list"), map);
    }
}
