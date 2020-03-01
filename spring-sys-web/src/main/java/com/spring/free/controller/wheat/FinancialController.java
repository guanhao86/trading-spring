package com.spring.free.controller.wheat;/**
 * Created by hengpu on 2019/2/25.
 */

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.spring.fee.model.TWheatFinancial;
import com.spring.fee.service.ITWheatFinancialBusiSV;
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
 * @ClassName FinancialController
 * @Description //TODO
 * @Author hengpu
 * @Date 2019/2/25 16:30
 * @Version 1.0
 **/
@Controller
@RequestMapping(Global.ADMIN_PATH + "/financial/")
public class FinancialController {

    @Autowired
    private ITWheatFinancialBusiSV service;

    @Value("${upload.fileSize}")
    private Long fileSize;//允许文件上传最大限制

    /*
     * @Author bianyx
     * @Description //TODO 热门话题
     * @Date 11:02 2019/1/18
     * @Param [mav, session, post, request, page, pageSize]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequiresPermissions("system:financial:view")
    @RequestMapping({"", "list"})
    public ModelAndView topicList(ModelAndView mav, HttpSession session, TWheatFinancial financial, HttpServletRequest request,
                                  @RequestParam(value = "page",required = false, defaultValue = PageDefaultConstraints.PAGE) int page,
                                  @RequestParam(value = "rows", required = false, defaultValue = PageDefaultConstraints.PAGE_SIZE) int pageSize) { ;

        PageInfo<TWheatFinancial> list = service.pageList(financial,page, pageSize);


        //获取热门话题列表信息
        mav.addObject("page", list);
        mav.addObject("financial",financial);
        //返回页面header标题
        PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_DICT_LIST);
        //返回操作提示信息
        PageResult.getPrompt(mav, request, financial.getParamMsg());

        mav.setViewName("wheat/financial/list");
        return mav;
    }
    @RequiresPermissions("system:financial:edit")
    @RequestMapping(value = "add")
    public ModelAndView add(ModelAndView view, HttpServletRequest request, TWheatFinancial financial) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "理财产品新增");
        PageResult.getPrompt(view, request, financial.getParamMsg());
        view.addObject("financial",financial);
        view.setViewName("wheat/financial/form");

        return view;
    }

    @RequiresPermissions("system:financial:edit")
    @RequestMapping(value = "save")
    public ModelAndView edit(ModelAndView mav, HttpServletRequest request, TWheatFinancial financial, MultipartFile ghPic1) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH +"/financial/add");
        TWheatFinancial noticecheck=service.select(financial);
        financial.setOncePrice(financial.getOncePrice()*1000);
        if(noticecheck!=null){
            if(ghPic1!=null&&ghPic1.getOriginalFilename()!=null&& StringUtils.isNotEmpty(ghPic1.getOriginalFilename())){
                if(ghPic1.getSize()>fileSize){

                    System.out.println("fileSize:"+ghPic1.getSize());
                    throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "图片过大，请上传小于5M的图片", map.get(Global.URL).toString(), map);

                }
                service.update(financial,ghPic1,map);
            }else{
                service.update(financial);
            }
        }else{
            if(ghPic1.getSize()>fileSize){

                System.out.println("fileSize:"+ghPic1.getSize());
                throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "图片过大，请上传小于5M的图片", map.get(Global.URL).toString(), map);

            }
//            financial.setAllNumber(0);
            financial.setShelledNumber(0);
            financial.setDelFlag(0);
            service.insert(financial,ghPic1,map);
        }

        PageResult.setPrompt(map,"操作成功", "success");
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/financial/list"), map);
    }

    /*
    * @Author jzc
    * @Description //TODO 广告编辑跳转
    * @Date 11:06 2019/2/28
    * @Param [view, request, post, buttonType]
    * @return org.springframework.web.servlet.ModelAndView
    **/
    @RequiresPermissions("system:financial:edit")
    @RequestMapping(value = "edit")
    public ModelAndView views(ModelAndView view, HttpServletRequest request, TWheatFinancial financial) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "理财产品修改");
        PageResult.getPrompt(view, request, financial.getParamMsg());
        financial=service.select(financial);
        financial.setOncePrice(financial.getOncePrice()/1000);
        view.addObject("financial",financial);
        view.setViewName("wheat/financial/edit");
        return view;
    }


}
