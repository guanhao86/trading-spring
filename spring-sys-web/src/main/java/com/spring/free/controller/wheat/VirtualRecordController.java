package com.spring.free.controller.wheat;/**
 * Created by hengpu on 2019/2/25.
 */

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.spring.fee.model.TWheatMember;
import com.spring.fee.service.ITWheatAccountBusiSV;
import com.spring.fee.service.ITWheatMemberBusiSV;
import com.spring.free.domain.VirtualRecord;
import com.spring.free.service.VirtualRecordService;
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
@RequestMapping(Global.ADMIN_PATH + "/virtualRecord/")
public class VirtualRecordController {

    @Autowired
    private VirtualRecordService service;

    @Autowired
    private ITWheatMemberBusiSV memberService;

    @Value("${upload.fileSize}")
    private Long fileSize;//允许文件上传最大限制


    /*
     * @Author bianyx
     * @Description //TODO 热门话题
     * @Date 11:02 2019/1/18
     * @Param [mav, session, post, request, page, pageSize]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequiresPermissions("system:virtualRecord:view")
    @RequestMapping({"", "list"})
    public ModelAndView topicList(ModelAndView mav, HttpSession session, VirtualRecord virtualRecord, HttpServletRequest request,
                                  @RequestParam(value = "page",required = false, defaultValue = PageDefaultConstraints.PAGE) int page,
                                  @RequestParam(value = "rows", required = false, defaultValue = PageDefaultConstraints.PAGE_SIZE) int pageSize) {

        PageInfo<VirtualRecord> list = service.pageList(virtualRecord,page, pageSize);

        //获取热门话题列表信息
        mav.addObject("page", list);
        mav.addObject("virtualRecord",virtualRecord);
        //返回页面header标题
        PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_DICT_LIST);
        //返回操作提示信息
        PageResult.getPrompt(mav, request, virtualRecord.getParamMsg());
        //充值
        if(virtualRecord.getType()==1){
            mav.setViewName("wheat/virtualRecord/czlist");
        }else {
            mav.setViewName("wheat/virtualRecord/list");
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
    @RequiresPermissions("system:virtualRecord:edit")
    @RequestMapping(value = "edit")
    public ModelAndView views(ModelAndView view, HttpServletRequest request, VirtualRecord virtualRecord ) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "虚拟币交易审核");
        PageResult.getPrompt(view, request, virtualRecord.getParamMsg());
        virtualRecord=service.get(virtualRecord.getId());
        if(virtualRecord.getFee()!=null) {
            virtualRecord.setDoubleFee(virtualRecord.getFee().doubleValue() / 1000);
            virtualRecord.setDue(virtualRecord.getQcCoin().doubleValue()-virtualRecord.getFee().doubleValue() / 1000);
        }else {
            virtualRecord.setDoubleFee(0.0);
            virtualRecord.setDue(virtualRecord.getQcCoin().doubleValue());
        }
        view.addObject("virtualRecord",virtualRecord);
        view.setViewName("wheat/virtualRecord/audit");

        return view;
    }
    @RequiresPermissions("system:virtualRecord:edit")
    @RequestMapping(value = "add")
    public ModelAndView add(ModelAndView view, HttpServletRequest request, VirtualRecord virtualRecord ) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "虚拟币充值");
        PageResult.getPrompt(view, request, virtualRecord.getParamMsg());;
        view.addObject("virtualRecord",virtualRecord);
        view.setViewName("wheat/virtualRecord/form");

        return view;
    }


    /*
  * @Author bianyx
  * @Description //
  * @Date 11:07 2019/1/18
  * @Param [mav, request, topItem, post, buttonType, ghPic1]
  * @return org.springframework.web.servlet.ModelAndView
  **/
    @RequiresPermissions("system:virtualRecord:edit")
    @RequestMapping(value = "audit")
    public ModelAndView edit(ModelAndView mav, HttpServletRequest request, VirtualRecord virtualRecord) {
        Map map = Maps.newHashMap();
        VirtualRecord  c=service.get(virtualRecord.getId());
        c.setState(virtualRecord.getState());
        service.audit(c);
        PageResult.setPrompt(map,"操作成功", "success");
        map.put(Global.URL, Global.ADMIN_PATH +"/virtualRecord/list?type="+c.getType());
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/virtualRecord/list?type="+c.getType()), map);
    }
    /*
  * @Author jzc
  * @Description //TODO 广告视频地址删除
  * @Date 11:07 2019/1/18
  * @Param [mav, request, topItem, post, buttonType, ghPic1]
  * @return org.springframework.web.servlet.ModelAndView
  **/
    @RequiresPermissions("system:virtualRecord:edit")
    @RequestMapping("delete")
    public ModelAndView userManageDelete(ModelAndView mav, HttpServletRequest request, VirtualRecord virtualRecord, HttpSession session) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH + "/virtualRecord/list");
        service.delete(virtualRecord);
        PageResult.setPrompt(map, PromptInfoConstraints.getDeleteSuccess("用户信息"), PromptInfoConstraints.SUCCESS_STATUS);
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/virtualRecord/list"), map);
    }
    @RequiresPermissions("system:virtualRecord:edit")
    @RequestMapping(value = "save")
    public ModelAndView edit(ModelAndView mav, HttpServletRequest request, VirtualRecord virtualRecord, MultipartFile ghPic1) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH + "/virtualRecord/add");

        TWheatMember member=memberService.selectByMemberId(virtualRecord.getMmeberId());
        if(null==member){
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "没有这个会员，请输入正确的会员ID", map.get(Global.URL).toString(), map);
        }
        virtualRecord.setMemId(member.getId().intValue());
//        virtualRecord.setQcCoin(virtualRecord.getQcCoin()*1000);
        if(ghPic1!=null&&ghPic1.getOriginalFilename()!=null&& StringUtils.isNotEmpty(ghPic1.getOriginalFilename())){
            if(ghPic1.getSize()>fileSize){

                System.out.println("fileSize:"+ghPic1.getSize());
                throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "图片过大，请上传小于5M的图片", map.get(Global.URL).toString(), map);

            }
            service.insert(virtualRecord, ghPic1, map);
        }else{
            service.insert(virtualRecord);
        }


        PageResult.setPrompt(map, "操作成功", "success");
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH + "/virtualRecord/list?type="+virtualRecord.getType()), map);
    }

}
