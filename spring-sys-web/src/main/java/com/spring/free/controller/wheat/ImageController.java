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
@RequestMapping(Global.ADMIN_PATH + "/image/")
public class ImageController {

    @Autowired
    private NoticeService service;
    @Value("${upload.fileSize}")
    private Long fileSize;//允许文件上传最大限制

    @RequiresPermissions("system:image:view")
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
        if(notice.getType().intValue()==4){
            mav.setViewName("wheat/image/topImageList");
        }else if(notice.getType().intValue()==5){
            mav.setViewName("wheat/image/noticeImageList");
        }else{
            mav.setViewName("wheat/image/financingImageList");
        }

        return mav;
    }

    @RequiresPermissions("system:image:edit")
    @RequestMapping(value = "edit")
    public ModelAndView views(ModelAndView view, HttpServletRequest request, Notice notice) {
        Map map = Maps.newHashMap();
        if(notice.getType().intValue()==4){
            PageResult.setPageTitle(view, "首页顶部广告图片修改");
        }else if(notice.getType().intValue()==5){
            PageResult.setPageTitle(view, "首页广告轮播图修改");
        }else{
            PageResult.setPageTitle(view, "理财页广告图片修改");
        }
        PageResult.getPrompt(view, request, notice.getParamMsg());
        notice=service.get(notice.getId());
        Integer type=notice.getType();
        view.addObject("notice",notice);
        view.setViewName("wheat/image/imageEdit");

        return view;
    }

    @RequiresPermissions("system:image:edit")
    @RequestMapping(value = "addPre")
    public ModelAndView addPre(ModelAndView view, HttpServletRequest request, Notice notice) {
        Map map = Maps.newHashMap();
        if(notice.getType().intValue()==4){
            PageResult.setPageTitle(view, "首页顶部广告图片新增");
        }else if(notice.getType().intValue()==5){
            PageResult.setPageTitle(view, "首页广告轮播图新增");
        }else{
            PageResult.setPageTitle(view, "理财页广告图片新增");
        }
        PageResult.getPrompt(view, request, notice.getParamMsg());
        Integer type=notice.getType();
        view.addObject("notice",notice);
        view.setViewName("wheat/image/imageForm");

        return view;
    }

    @RequiresPermissions("system:image:edit")
    @RequestMapping(value = "save")
    public ModelAndView edit(ModelAndView mav, HttpServletRequest request, Notice notice, MultipartFile ghPic1) {
        Map map = Maps.newHashMap();
//        map.put(Global.URL, Global.ADMIN_PATH + "/content/edit?id="+content.getId());
        map.put(Global.URL, Global.ADMIN_PATH +"/image/addPre?type="+notice.getType());
        Notice noticecheck=service.get(notice.getId());
        if(noticecheck!=null){
            if(ghPic1!=null&&ghPic1.getOriginalFilename()!=null&& StringUtils.isNotEmpty(ghPic1.getOriginalFilename())){
                if(ghPic1.getSize()>fileSize){

                    System.out.println("fileSize:"+ghPic1.getSize());
                    throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "图片过大，请上传小于5M的图片", map.get(Global.URL).toString(), map);

                }
                service.update(notice,ghPic1,map);
            }else{
                service.update(notice);
            }
        }else{
            if(ghPic1.getSize()>fileSize){

                System.out.println("fileSize:"+ghPic1.getSize());
                throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "图片过大，请上传小于5M的图片", map.get(Global.URL).toString(), map);

            }
            service.insert(notice,ghPic1,map);
        }

        PageResult.setPrompt(map,"操作成功", "success");
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/image/list?type="+notice.getType()), map);
    }

    @RequiresPermissions("system:image:edit")
    @RequestMapping("delete")
    public ModelAndView userManageDelete(ModelAndView mav, HttpServletRequest request, Notice notice, HttpSession session) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH + "/image/list?type="+notice.getType());

        service.delete(notice);
        if(notice.getType().intValue()==4){
            PageResult.setPrompt(map, PromptInfoConstraints.getDeleteSuccess("首页顶部广告图片信息"), PromptInfoConstraints.SUCCESS_STATUS);

        }else if(notice.getType().intValue()==5){
            PageResult.setPrompt(map, PromptInfoConstraints.getDeleteSuccess("首页广告轮播图信息"), PromptInfoConstraints.SUCCESS_STATUS);

        }else{
            PageResult.setPrompt(map, PromptInfoConstraints.getDeleteSuccess("理财页广告图片信息"), PromptInfoConstraints.SUCCESS_STATUS);

        }
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/image/list?type="+notice.getType()), map);
    }

}
