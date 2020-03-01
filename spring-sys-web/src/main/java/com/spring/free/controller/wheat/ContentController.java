package com.spring.free.controller.wheat;/**
 * Created by hengpu on 2019/2/25.
 */

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.spring.free.domain.Content;
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
@RequestMapping(Global.ADMIN_PATH + "/content/")
public class ContentController {

    @Autowired
    private ContentService service;

    @Value("${upload.fileSize}")
    private Long fileSize;//允许文件上传最大限制

    /*
     * @Author bianyx
     * @Description //TODO 热门话题
     * @Date 11:02 2019/1/18
     * @Param [mav, session, post, request, page, pageSize]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequiresPermissions("system:content:view")
    @RequestMapping({"", "list"})
    public ModelAndView topicList(ModelAndView mav, HttpSession session, Content content, HttpServletRequest request,
                                  @RequestParam(value = "page",required = false, defaultValue = PageDefaultConstraints.PAGE) int page,
                                  @RequestParam(value = "rows", required = false, defaultValue = PageDefaultConstraints.PAGE_SIZE) int pageSize) {
        String postType = request.getParameter("postType");

        PageInfo<Content> list = service.pageList(content,page, pageSize);


        //获取热门话题列表信息
        mav.addObject("page", list);
        mav.addObject("content",content);
        //返回页面header标题
        PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_DICT_LIST);
        //返回操作提示信息
        PageResult.getPrompt(mav, request, content.getParamMsg());
        int type = content.getType();
        if(type==1){
            mav.setViewName("wheat/content/advertList");
        }else if(type==2){
            mav.setViewName("wheat/content/videoList");
        }else{
            mav.setViewName("wheat/content/addressList");
        }



        return mav;
    }


    /*
* @Author jzc
* @Description //TODO 广告编辑跳转
* @Date 11:06 2019/2/28
* @Param [view, request, post, buttonType]
* @return org.springframework.web.servlet.ModelAndView
**/
    @RequiresPermissions("system:content:edit")
    @RequestMapping(value = "edit")
    public ModelAndView views(ModelAndView view, HttpServletRequest request, Content content, String buttonType) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "付费套餐配置修改");
        PageResult.getPrompt(view, request, content.getParamMsg());
        content=service.get(content.getId());
        Integer type=content.getType();
        view.addObject("content",content);
        if(type==1){
            view.setViewName("wheat/content/advertEdit");
        }else if(type==2){
            view.setViewName("wheat/content/videoEdit");
        }else{
            view.setViewName("wheat/content/addressEdit");
        }
        return view;
    }

    /*
* @Author jzc
* @Description //TODO 广告新增跳转
* @Date 11:06 2019/2/28
* @Param [view, request, post, buttonType]
* @return org.springframework.web.servlet.ModelAndView
**/
    @RequiresPermissions("system:content:edit")
    @RequestMapping(value = "addPre")
    public ModelAndView addPre(ModelAndView view, HttpServletRequest request, Content content, String buttonType) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "付费套餐配置修改");
        PageResult.getPrompt(view, request, content.getParamMsg());
        Integer type=content.getType();
        view.addObject("content",content);
        if(type==1){
            view.setViewName("wheat/content/advertForm");
        }else if(type==2){
            view.setViewName("wheat/content/videoForm");
        }else{
            view.setViewName("wheat/content/addressForm");
        }
        return view;
    }

    /*
  * @Author bianyx
  * @Description //TODO 广告编辑新增保存
  * @Date 11:07 2019/1/18
  * @Param [mav, request, topItem, post, buttonType, ghPic1]
  * @return org.springframework.web.servlet.ModelAndView
  **/
    @RequiresPermissions("system:content:edit")
    @RequestMapping(value = "save")
    public ModelAndView edit(ModelAndView mav, HttpServletRequest request, Content content, String advertType, String image,
                             String urlType, String jumpUrl, String isUse, String type, MultipartFile ghPic1,String buttonType) {
        Map map = Maps.newHashMap();
//        map.put(Global.URL, Global.ADMIN_PATH + "/content/edit?id="+content.getId());
        map.put(Global.URL, Global.ADMIN_PATH +"/content/addPre?type="+type);
        map.put("buttonType", buttonType);
        content=service.get(content.getId());
       if(content!=null){
           content.setAdvertType(Integer.valueOf(advertType));
//           content.setImage(image);
           content.setType(Integer.valueOf(type));
           content.setIsUse(Integer.valueOf(isUse));
           if(ghPic1!=null&&ghPic1.getOriginalFilename()!=null&& StringUtils.isNotEmpty(ghPic1.getOriginalFilename())){
               if(ghPic1.getSize()>fileSize){

                   System.out.println("fileSize:"+ghPic1.getSize());
                   throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "图片过大，请上传小于5M的图片", map.get(Global.URL).toString(), map);

               }
               service.update(content,ghPic1,map);
           }else{
               service.update(content);
           }
       }else{
           if(ghPic1.getSize()>fileSize){

               System.out.println("fileSize:"+ghPic1.getSize());
               throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "图片过大，请上传小于5M的图片", map.get(Global.URL).toString(), map);

           }
           Content contents=new Content();
           contents.setAdvertType(Integer.valueOf(advertType));
           contents.setImage(image);
//           contents.setUrlType(Integer.valueOf(urlType));
           contents.setJumpUrl(jumpUrl);
           contents.setType(Integer.valueOf(type));
           contents.setIsUse(Integer.valueOf(isUse));
           service.insert(contents,ghPic1,map);
       }

        PageResult.setPrompt(map,"操作成功", "success");
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/content/list?type="+type), map);
    }
    /*
  * @Author jzc
  * @Description //TODO 广告视频地址删除
  * @Date 11:07 2019/1/18
  * @Param [mav, request, topItem, post, buttonType, ghPic1]
  * @return org.springframework.web.servlet.ModelAndView
  **/
    @RequiresPermissions("system:content:edit")
    @RequestMapping("delete")
    public ModelAndView userManageDelete(ModelAndView mav, HttpServletRequest request,String type, Content content, HttpSession session) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH + "/content/list");
        service.delete(content);
        PageResult.setPrompt(map, PromptInfoConstraints.getDeleteSuccess("用户信息"), PromptInfoConstraints.SUCCESS_STATUS);
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/content/list?type="+type), map);
    }


    /*
* @Author bianyx
* @Description //TODO 视频编辑新增保存
* @Date 11:07 2019/1/18
* @Param [mav, request, topItem, post, buttonType, ghPic1]
* @return org.springframework.web.servlet.ModelAndView
**/
    @RequiresPermissions("system:content:edit")
    @RequestMapping(value = "videoSave")
    public ModelAndView videoEdit(ModelAndView mav, HttpServletRequest request, Content content,String advertType,String image,
                             String urlType,String jumpUrl,String isUse,String type, String platName,MultipartFile ghPic1,String buttonType) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH +"/content/addPre?type="+type);

        map.put("buttonType", buttonType);
        content=service.get(content.getId());
        if(content!=null){
//            content.setImage(image);
            content.setJumpUrl(jumpUrl);
            content.setType(Integer.valueOf(type));
            content.setIsUse(Integer.valueOf(isUse));
            content.setPlatName(platName);
            if(ghPic1!=null&&ghPic1.getOriginalFilename()!=null&& StringUtils.isNotEmpty(ghPic1.getOriginalFilename())){
                if(ghPic1.getSize()>fileSize){

                    System.out.println("fileSize:"+ghPic1.getSize());
                    throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "图片过大，请上传小于5M的图片", map.get(Global.URL).toString(), map);

                }
                service.update(content,ghPic1,map);
            }else{
                service.update(content);
            }
        }else{
            if(ghPic1.getSize()>fileSize){

                System.out.println("fileSize:"+ghPic1.getSize());
                throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "图片过大，请上传小于5M的图片", map.get(Global.URL).toString(), map);

            }
            Content contents=new Content();
            contents.setImage(image);
            contents.setJumpUrl(jumpUrl);
            contents.setType(Integer.valueOf(type));
            contents.setIsUse(Integer.valueOf(isUse));
            contents.setPlatName(platName);
            service.insert(contents,ghPic1,map);
        }
        PageResult.setPrompt(map,"操作成功", "success");
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/content/list?type="+type), map);
    }


    /*
  * @Author bianyx
  * @Description //TODO 地址编辑新增保存
  * @Date 11:07 2019/1/18
  * @Param [mav, request, topItem, post, buttonType, ghPic1]
  * @return org.springframework.web.servlet.ModelAndView
  **/
    @RequiresPermissions("system:content:edit")
    @RequestMapping(value = "addressSave")
    public ModelAndView addressEdit(ModelAndView mav, HttpServletRequest request, Content content,String type,String jumpUrl, String plateName,String addType,
                                  String buttonType) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH +"/content/addPre?type="+type);

        map.put("buttonType", buttonType);
        content=service.get(content.getId());
        if(content!=null){
            content.setType(Integer.valueOf(type));
            content.setAddType(Integer.valueOf(addType));
            content.setJumpUrl(jumpUrl);
            service.update(content);
        }else{
            Content contents=new Content();
            contents.setType(Integer.valueOf(type));
            contents.setAddType(Integer.valueOf(addType));
            contents.setJumpUrl(jumpUrl);
            service.insert(contents);
        }
        PageResult.setPrompt(map,"操作成功", "success");
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/content/list?type="+type), map);
    }
}
