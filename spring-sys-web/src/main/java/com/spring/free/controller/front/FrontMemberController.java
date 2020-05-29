package com.spring.free.controller.front;/**
 * Created by hengpu on 2019/2/25.
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.spring.fee.model.TableMember;
import com.spring.fee.service.ITableMemberBusiSV;
import com.spring.free.common.util.PythonUtil3;
import com.spring.free.config.ImageUtils;
import com.spring.free.domain.QueryVO;
import com.spring.free.domain.UserInfo;
import com.spring.free.util.PageResult;
import com.spring.free.util.constraints.Global;
import com.spring.free.util.constraints.PageDefaultConstraints;
import com.spring.free.util.constraints.PromptInfoConstraints;
import com.spring.free.util.exception.ExceptionCodeEnum;
import com.spring.free.util.exception.ServiceException;
import com.spring.free.util.md5.Md5Util;
import com.spring.free.utils.principal.BaseGetPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 前端/会员
 **/
@Slf4j
@Controller
@RequestMapping(Global.ADMIN_PATH + "/front/member/")
public class FrontMemberController {

    @Value("${python.path}")
    public String python_path;

    @Autowired
    ITableMemberBusiSV iTableMemberBusiSV;

    /*
     * @Author haha
     * @Description //TODO 配置列表
     * @Param [mav, session, post, request, page, pageSize]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequiresPermissions("system:member:view")
    @RequestMapping({"", "myChildlist"})
    public ModelAndView list(ModelAndView mav, HttpSession session, QueryVO queryVO, HttpServletRequest request,
                             @RequestParam(value = "page",required = false, defaultValue = PageDefaultConstraints.PAGE) int page,
                             @RequestParam(value = "rows", required = false, defaultValue = PageDefaultConstraints.PAGE_SIZE) int pageSize) {
        // String postType = request.getParameter("postType");

        TableMember tableMember = new TableMember();
        UserInfo user = BaseGetPrincipal.getUser();
        tableMember.setArrangeId(user.getUsername());

        PageInfo<TableMember> pageInfo = this.iTableMemberBusiSV.queryAllChildPage(user.getUsername(), page, pageSize, null);

        //调用python获取在客户端我的粉丝这个界面，最上面加两行，左区业绩： 右区业绩
        //调用我这个函数获得数据就可以了，传参数是字符串类型的，member_id
        //String result = "(1,2)";
        String result = PythonUtil3.runPy(python_path, "get_child_achievement.py", user.getUsername(), "");

        if (StringUtils.isNotEmpty(result) && result.indexOf("(") >= 0 && result.indexOf(")")>=0) {
            result = result.substring(1, result.length()-1);
        }


        System.out.println("左区业绩： 右区业绩："+result);

        String left = "0";
        String right = "0";
        String[] results = result.split(",");

        if (results != null && results.length == 2) {
            left = results[0];
            right = results[1];
        }

        //获取热门话题列表信息
        mav.addObject("left", left);
        mav.addObject("right", right);
        mav.addObject("page", pageInfo);
        mav.addObject("queryVO",queryVO);
        //返回页面header标题
        PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_DICT_LIST);
        //返回操作提示信息
        PageResult.getPrompt(mav, request, queryVO.getParamMsg());

        mav.setViewName("front/member/list");
        return mav;
    }


    /*
     * @Author jzc
     * @Date 11:06 2019/2/28
     * @Param [view, request, post, buttonType]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequiresPermissions("system:member:view")
    @RequestMapping(value = "edit")
    public ModelAndView views(ModelAndView view, HttpServletRequest request, TableMember member, String buttonType) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "会员信息");
        PageResult.getPrompt(view, request, "");
        UserInfo user = BaseGetPrincipal.getUser();
        TableMember tableMember=this.iTableMemberBusiSV.selectByMemberId(user.getUsername());
        view.addObject("member",tableMember);
        view.setViewName("front/member/edit");
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
    @RequiresPermissions("system:member:view")
    @RequestMapping(value = "save")
    public ModelAndView edit(ModelAndView mav, HttpServletRequest request, TableMember member, MultipartFile fcartImg1
            , MultipartFile fcartImg2, MultipartFile fcartImg3, MultipartFile fbankImg1, MultipartFile fbankImg2) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH +"/front/member/edit");

        try {
            if (fcartImg1 != null && StringUtils.isNotEmpty(fcartImg1.getOriginalFilename())) {
                //身份证正面
                String imgPath = ImageUtils.upload(fcartImg1);
                member.setCartImg1(imgPath);
            }

            if (fcartImg2 != null && StringUtils.isNotEmpty(fcartImg2.getOriginalFilename())) {
                //身份证反面
                String imgPath = ImageUtils.upload(fcartImg2);
                member.setCartImg2(imgPath);
            }

            if (fcartImg3 != null && StringUtils.isNotEmpty(fcartImg3.getOriginalFilename())) {
                //身份证手持
                String imgPath = ImageUtils.upload(fcartImg3);
                member.setCartImg3(imgPath);
            }

            if (fbankImg1 != null && StringUtils.isNotEmpty(fbankImg1.getOriginalFilename())) {
                //银行卡正面
                String imgPath = ImageUtils.upload(fbankImg1);
                member.setBankImg1(imgPath);
            }

            if (fbankImg2 != null && StringUtils.isNotEmpty(fbankImg2.getOriginalFilename())) {
                //银行卡反面
                String imgPath = ImageUtils.upload(fbankImg2);
                member.setBankImg2(imgPath);
            }

            member.setAutFlag(2); //修改信息后为待审核
            this.iTableMemberBusiSV.update(member, false);
        }catch (Exception e) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), e.getMessage(), map.get(Global.URL).toString(), map);
        }

        PageResult.setPrompt(map,"操作成功", "success");
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/front/member/view"), map);
    }

    @RequiresPermissions("system:member:view")
    @RequestMapping(value = "view")
    public ModelAndView view(ModelAndView view, HttpServletRequest request, TableMember member) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "会员信息");
        PageResult.getPrompt(view, request, "");

        UserInfo user = BaseGetPrincipal.getUser();
        member.setMemberId(user.getUsername());
        TableMember tableMember=this.iTableMemberBusiSV.selectByMemberId(user.getUsername());
//        if(tableMember!=null) {
//            member.setTotal(account.getTotal().doubleValue() / 1000);
//            member.setAvailable(account.getAvailable().doubleValue() / 1000);
//            member.setFreeze(account.getFreeze().doubleValue() / 1000);
//            member.setMoneyFreeze(account.getMoneyFreeze().doubleValue() / 1000);
//            member.setGranaryFreeze(account.getGranaryFreeze().doubleValue() / 1000);
//        }
        view.addObject("member",tableMember);
        view.setViewName("front/member/view");
        return view;
    }

    @RequiresPermissions("system:member:view")
    @RequestMapping({"", "treeIndex"})
    public ModelAndView treeIndex(ModelAndView view, HttpSession session, TableMember member, HttpServletRequest request,
                                  @RequestParam(value = "page",required = false, defaultValue = PageDefaultConstraints.PAGE) int page,
                                  @RequestParam(value = "rows", required = false, defaultValue = PageDefaultConstraints.PAGE_SIZE) int pageSize) {

        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "会员信息");
        PageResult.getPrompt(view, request, "");

        UserInfo user = BaseGetPrincipal.getUser();
        member.setMemberId(user.getUsername());
        view.addObject("member",member);
        view.setViewName("front/member/tree");
        return view;
    }

    /**
     * 会员自己注册页面
     * @param view
     * @param request
     * @param m
     * @return
     */
    @RequestMapping(value = "registSimple")
    @ResponseBody
    public JSONObject registSimple(HttpServletRequest request,@RequestBody TableMember m) {

        log.info(JSON.toJSONString(m));

        JSONObject object = new JSONObject();
        object.put("code","0");

        TableMember member;
        try {
            member = this.iTableMemberBusiSV.regist(m, 1);
            object.put("msg",member.getMemberId());
        }catch (ServiceException e) {
            object.put("code", "1");
            object.put("msg", e.getMessage());
        }catch (Exception e1) {
            object.put("code", "1");
            object.put("msg", e1.getMessage());
        }
        return object;
    }

    /**
     * 修改密码
     * @param mav
     * @param request
     * @param member
     * @return
     */
    @RequiresPermissions("system:member:view")
    @RequestMapping(value = "changePasswordIndex")
    public ModelAndView changePasswordIndex(ModelAndView mav, HttpServletRequest request, TableMember member) {
        Map map = Maps.newHashMap();


        PageResult.getPrompt(mav, request, "");
        mav.setViewName("front/member/changePassword");

        return mav;
    }

    /**
     * 修改密码
     * @param mav
     * @param request
     * @param queryVO
     * @return
     */
    @RequiresPermissions("system:member:view")
    @RequestMapping(value = "changePassword")
    public ModelAndView changePassword(ModelAndView view, HttpServletRequest request, QueryVO queryVO) {

        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "订单信息");
        PageResult.getPrompt(view, request, "");

        UserInfo user = BaseGetPrincipal.getUser();
        TableMember tableMember=new TableMember();
        try {
            tableMember.setMemberId(user.getUsername());
            tableMember.setPassword(queryVO.getPassword());
            this.iTableMemberBusiSV.changePwd(tableMember, queryVO.getOldPassword());
        }catch (Exception e) {
            map.put(Global.URL, Global.ADMIN_PATH +"/front/member/changePasswordIndex");
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), e.getMessage(), map.get(Global.URL).toString(), map);
        }

        //查询会员信息，返回地址
        PageResult.setPrompt(map,"操作成功", "success");
       return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/front/member/changePasswordIndex"), map);
    }

    /**
     * 替别人注册
     * @param view
     * @param request
     * @return
     */
    @RequiresPermissions("system:member:view")
    @RequestMapping(value = "registIndex")
    public ModelAndView registIndex(ModelAndView view, HttpServletRequest request) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "会员信息注册");

        String registMemberId = request.getParameter("registMemberId");
        String referenceId = request.getParameter("referenceId");
        String arrangeId = request.getParameter("arrangeId");

        UserInfo user = BaseGetPrincipal.getUser();
        TableMember tableMember=this.iTableMemberBusiSV.selectByMemberId(user.getUsername());
        PageResult.getPrompt(view, request, "");
        if (registMemberId != null) {
            TableMember tableMember1 = new TableMember();
            tableMember1.setMemberId(registMemberId);
            view.addObject("registMember", tableMember1);
        }
        view.addObject("member", tableMember);
        view.addObject("arrangeId", StringUtils.isEmpty(arrangeId)?tableMember.getMemberId():arrangeId);
        view.addObject("referenceId", StringUtils.isEmpty(referenceId)?tableMember.getMemberId():referenceId);
        view.setViewName("front/member/regist");
        return view;
    }
    /**
     * 替别人注册
     * @param view
     * @param request
     * @return
     */
    @RequiresPermissions("system:member:view")
    @RequestMapping(value = "register")
    public ModelAndView register(ModelAndView view, HttpServletRequest request, TableMember m) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH +"/front/member/registIndex?referenceId="+m.getArrangeId()+"&arrangeId="+m.getArrangeId());
        TableMember member = null;
        try {
            member = this.iTableMemberBusiSV.regist(m,1);
        }catch (ServiceException e) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), e.getMessage(), map.get(Global.URL).toString(), map);
        }catch (Exception e1) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), e1.getMessage(), map.get(Global.URL).toString(), map);
        }

        String msg = "恭喜您注册成功，您注册的会员编号为："+member.getMemberId()+"，默认登录密码为注册手机号码后6位，请登录后自行修改密码";
        view.addObject("registMember", member);
        PageResult.setPrompt(map,msg, "success");
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/front/member/registIndex?registMemberId="+member.getMemberId()), map);
    }

    /**
     * 去报单
     * @param mav
     * @param request
     * @param member
     * @return
     */
    @RequiresPermissions("system:member:view")
    @RequestMapping(value = "beMemberIndex")
    public ModelAndView beMemberIndex(ModelAndView mav, HttpServletRequest request, TableMember member) {
        Map map = Maps.newHashMap();

        mav.addObject("member", member);

        PageResult.getPrompt(mav, request, "");
        mav.setViewName("front/member/beMember");

        return mav;
    }
}
