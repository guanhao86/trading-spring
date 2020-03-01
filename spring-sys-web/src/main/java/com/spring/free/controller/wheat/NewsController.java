package com.spring.free.controller.wheat;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.spring.free.domain.News;
import com.spring.free.service.NewsService;
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
@RequestMapping(Global.ADMIN_PATH + "/news/")
public class NewsController {

    @Autowired
    private NewsService service;
    @Value("${upload.fileSize}")
    private Long fileSize;//允许文件上传最大限制

    @RequiresPermissions("system:news:view")
    @RequestMapping({"", "list"})
    public ModelAndView topicList(ModelAndView mav, News news, HttpServletRequest request,
                                  @RequestParam(value = "page",required = false, defaultValue = PageDefaultConstraints.PAGE) int page,
                                  @RequestParam(value = "rows", required = false, defaultValue = PageDefaultConstraints.PAGE_SIZE) int pageSize) {

        PageInfo<News> list = service.pageList(news,page, pageSize);

        mav.addObject("page", list);
        mav.addObject("news",news);
        //返回页面header标题
        PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_DICT_LIST);
        //返回操作提示信息
        PageResult.getPrompt(mav, request, news.getParamMsg());
        mav.setViewName("wheat/news/newsList");

        return mav;
    }

    @RequiresPermissions("system:news:edit")
    @RequestMapping(value = "edit")
    public ModelAndView views(ModelAndView view, HttpServletRequest request, News news) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "新闻修改");
        PageResult.getPrompt(view, request, news.getParamMsg());
        news=service.get(news.getId());
        view.addObject("news",news);
        view.setViewName("wheat/news/newsEdit");

        return view;
    }

    @RequiresPermissions("system:news:edit")
    @RequestMapping(value = "addPre")
    public ModelAndView addPre(ModelAndView view, HttpServletRequest request, News news) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "新闻新增");
        PageResult.getPrompt(view, request, news.getParamMsg());
        view.addObject("news",news);
        view.setViewName("wheat/news/newsForm");

        return view;
    }

    @RequiresPermissions("system:news:edit")
    @RequestMapping(value = "save")
    public ModelAndView edit(ModelAndView mav, HttpServletRequest request, News news, MultipartFile bigGhPic1,
                             MultipartFile ghPic1,MultipartFile ghPic2,MultipartFile ghPic3) {
        Map map = Maps.newHashMap();
//        map.put(Global.URL, Global.ADMIN_PATH + "/content/edit?id="+content.getId());
        map.put(Global.URL, Global.ADMIN_PATH +"/news/addPre");
        News newscheck=service.get(news.getId());
        if(newscheck!=null){

            if(bigGhPic1!=null&&bigGhPic1.getOriginalFilename()!=null&& StringUtils.isNotEmpty(bigGhPic1.getOriginalFilename())){
                if(bigGhPic1.getSize()>fileSize){
                    System.out.println("fileSize:"+bigGhPic1.getSize());
                    throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "图片过大，请上传小于5M的图片", map.get(Global.URL).toString(), map);
                }
            }else{
                bigGhPic1=null;
            }
            if(ghPic1!=null&&ghPic1.getOriginalFilename()!=null&& StringUtils.isNotEmpty(ghPic1.getOriginalFilename())){
                if(ghPic1.getSize()>fileSize){
                    System.out.println("fileSize:"+ghPic1.getSize());
                    throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "图片过大，请上传小于5M的图片", map.get(Global.URL).toString(), map);
                }
            }else{
                ghPic1=null;
            }
            if(ghPic2!=null&&ghPic2.getOriginalFilename()!=null&& StringUtils.isNotEmpty(ghPic2.getOriginalFilename())){
                if(ghPic2.getSize()>fileSize){
                    System.out.println("fileSize:"+ghPic2.getSize());
                    throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "图片过大，请上传小于5M的图片", map.get(Global.URL).toString(), map);
                }
            }else{
                ghPic2=null;
            }
            if(ghPic3!=null&&ghPic3.getOriginalFilename()!=null&& StringUtils.isNotEmpty(ghPic3.getOriginalFilename())){
                if(ghPic3.getSize()>fileSize){
                    System.out.println("fileSize:"+ghPic3.getSize());
                    throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "图片过大，请上传小于5M的图片", map.get(Global.URL).toString(), map);
                }
            }else{
                ghPic3=null;
            }
            service.update(news,bigGhPic1,ghPic1,ghPic2,ghPic3,map);
        }else{
            if(bigGhPic1.getSize()>fileSize){

                System.out.println("fileSize:"+bigGhPic1.getSize());
                throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "图片过大，请上传小于5M的图片", map.get(Global.URL).toString(), map);

            }
            if(ghPic1.getSize()>fileSize){

                System.out.println("fileSize:"+ghPic1.getSize());
                throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "图片过大，请上传小于5M的图片", map.get(Global.URL).toString(), map);

            }
            if(ghPic2.getSize()>fileSize){

                System.out.println("fileSize:"+ghPic2.getSize());
                throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "图片过大，请上传小于5M的图片", map.get(Global.URL).toString(), map);

            }
            if(ghPic3.getSize()>fileSize){

                System.out.println("fileSize:"+ghPic3.getSize());
                throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "图片过大，请上传小于5M的图片", map.get(Global.URL).toString(), map);

            }
            news.setType(1);
            service.insert(news,bigGhPic1,ghPic1,ghPic2,ghPic3,map);
        }

        PageResult.setPrompt(map,"操作成功", "success");
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/news/list"), map);
    }

    @RequiresPermissions("system:news:edit")
    @RequestMapping("delete")
    public ModelAndView userManageDelete(ModelAndView mav, HttpServletRequest request, News news, HttpSession session) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH + "/news/list");
        service.delete(news);
        PageResult.setPrompt(map, PromptInfoConstraints.getDeleteSuccess("新闻信息"), PromptInfoConstraints.SUCCESS_STATUS);
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/news/list"), map);
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
