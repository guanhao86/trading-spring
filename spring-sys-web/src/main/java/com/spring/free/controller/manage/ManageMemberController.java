package com.spring.free.controller.manage;/**
 * Created by hengpu on 2019/2/25.
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.spring.fee.model.TableMember;
import com.spring.fee.model.TableMemberDZ;
import com.spring.fee.service.ITableMemberBusiSV;
import com.spring.free.common.util.PythonUtil3;
import com.spring.free.config.ImageUtils;
import com.spring.free.domain.QueryVO;
import com.spring.free.domain.UserInfo;
import com.spring.free.system.UserService;
import com.spring.free.util.PageResult;
import com.spring.free.util.constraints.Global;
import com.spring.free.util.constraints.PageDefaultConstraints;
import com.spring.free.util.constraints.PromptInfoConstraints;
import com.spring.free.util.exception.ExceptionCodeEnum;
import com.spring.free.util.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map;

/**
 * 后台管理/会员
 **/
@Slf4j
@Controller
@RequestMapping(Global.ADMIN_PATH + "/manage/member/")
public class ManageMemberController {

    @Value("${python.path}")
    public String python_path;

    @Autowired
    ITableMemberBusiSV iTableMemberBusiSV;

    @Autowired
    UserService userService;
    /*
     * @Author gh
     * @Description //TODO 配置列表
     * @Param [mav, session, post, request, page, pageSize]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequiresPermissions("system:member:view")
    @RequestMapping({"", "list"})
    public ModelAndView list(ModelAndView mav, HttpSession session, QueryVO queryVO, HttpServletRequest request,
                             @RequestParam(value = "page",required = false, defaultValue = PageDefaultConstraints.PAGE) int page,
                             @RequestParam(value = "rows", required = false, defaultValue = "10") int pageSize) {
        // String postType = request.getParameter("postType");

        TableMember tableMember = new TableMember();
        BeanUtils.copyProperties(queryVO, tableMember);
        if (StringUtils.isNotEmpty(queryVO.getLevel())){
            tableMember.setLevel(Integer.parseInt(queryVO.getLevel()));
        }
        if (StringUtils.isNotEmpty(queryVO.getMRank())){
            tableMember.setmRank(Integer.parseInt(queryVO.getMRank()));
        }

        HttpSession session1 = request.getSession();
        session1.setAttribute("QUERY_MEMBER_LIST", tableMember);

        PageInfo<TableMember> pageInfo = this.iTableMemberBusiSV.queryListPage(tableMember, page, pageSize, null);


        //获取热门话题列表信息
        mav.addObject("page", pageInfo);
        mav.addObject("queryVO",queryVO);
        //返回页面header标题
        PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_DICT_LIST);
        //返回操作提示信息
        PageResult.getPrompt(mav, request, queryVO.getParamMsg());

        mav.setViewName("manage/member/list");
        return mav;
    }


    /*
     * @Author jzc
     * @Date 11:06 2019/2/28
     * @Param [view, request, post, buttonType]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequiresPermissions("system:member:edit")
    @RequestMapping(value = "edit")
    public ModelAndView views(ModelAndView view, HttpServletRequest request, TableMember member, String buttonType) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "会员信息");
        PageResult.getPrompt(view, request, "");
        TableMember tableMember=this.iTableMemberBusiSV.select(member);
//        if(tableMember!=null) {
//            member.setTotal(account.getTotal().doubleValue() / 1000);
//            member.setAvailable(account.getAvailable().doubleValue() / 1000);
//            member.setFreeze(account.getFreeze().doubleValue() / 1000);
//            member.setMoneyFreeze(account.getMoneyFreeze().doubleValue() / 1000);
//            member.setGranaryFreeze(account.getGranaryFreeze().doubleValue() / 1000);
//        }
        view.addObject("userInfoA", this.userService.getUserByUserName(tableMember.getMemberId()));
        view.addObject("member",tableMember);
        view.setViewName("manage/member/edit");
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
    @RequiresPermissions("system:member:edit")
    @RequestMapping(value = "save")
    public ModelAndView edit(ModelAndView mav, HttpServletRequest request, TableMember member, MultipartFile fcartImg1
            , MultipartFile fcartImg2, MultipartFile fcartImg3, MultipartFile fbankImg1, MultipartFile fbankImg2) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH +"/manage/member/list");

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

        try {
            this.iTableMemberBusiSV.update(member, false);
        }catch (Exception e) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), e.getMessage(), map.get(Global.URL).toString(), map);
        }

        PageResult.setPrompt(map,"操作成功", "success");
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/manage/member/list"), map);
    }

    @RequiresPermissions("system:member:edit")
    @RequestMapping(value = "view")
    public ModelAndView view(ModelAndView view, HttpServletRequest request, TableMember member) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "会员信息");
        PageResult.getPrompt(view, request, "");
        String result = "(0,0)";

        TableMember tableMember=this.iTableMemberBusiSV.select(member);

//        try {
//            result = PythonUtil3.runPy(python_path, "get_child_achievement.py", tableMember.getMemberId(), "");
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        if (StringUtils.isNotEmpty(result) && result.indexOf("(") >= 0 && result.indexOf(")")>=0) {
//            result = result.substring(1, result.length()-1);
//        }
//
//
//        System.out.println("左区业绩： 右区业绩："+result);
        String left = tableMember.getLeftAmount() == null ? "0" : String.valueOf(tableMember.getLeftAmount());
        String right = tableMember.getRightAmount() == null ? "0" : String.valueOf(tableMember.getRightAmount());
//        String[] results = result.split(",");
//
//        if (results != null && results.length == 2) {
//            left = results[0];
//            right = results[1];
//        }

//        if(tableMember!=null) {
//            member.setTotal(account.getTotal().doubleValue() / 1000);
//            member.setAvailable(account.getAvailable().doubleValue() / 1000);
//            member.setFreeze(account.getFreeze().doubleValue() / 1000);
//            member.setMoneyFreeze(account.getMoneyFreeze().doubleValue() / 1000);
//            member.setGranaryFreeze(account.getGranaryFreeze().doubleValue() / 1000);
//        }
        view.addObject("left", left);
        view.addObject("right", right);
        view.addObject("member",tableMember);
        view.setViewName("manage/member/view");
        return view;
    }

    @RequiresPermissions("system:member:view")
    @RequestMapping({"", "treeIndex"})
    public ModelAndView treeIndex(ModelAndView mav, HttpSession session, TableMember member, HttpServletRequest request,
                                  @RequestParam(value = "page",required = false, defaultValue = PageDefaultConstraints.PAGE) int page,
                                  @RequestParam(value = "rows", required = false, defaultValue = PageDefaultConstraints.PAGE_SIZE) int pageSize) {

        mav.setViewName("manage/member/tree");
        return mav;
    }

    @RequiresPermissions("system:member:edit")
    @RequestMapping(value = "add")
    public ModelAndView add(ModelAndView view, HttpServletRequest request) {
        Map map = Maps.newHashMap();
        PageResult.getPrompt(view, request, "");
        PageResult.setPageTitle(view, "会员信息注册");
        view.addObject("result", "0");
        //返回操作提示信息
        view.setViewName("manage/member/form");
        return view;
    }

    @RequiresPermissions("system:member:edit")
    @RequestMapping(value = "register")
    public ModelAndView register(ModelAndView view, HttpServletRequest request, TableMember m) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH +"/manage/member/add");
        TableMember member = null;
        try {
            member = this.iTableMemberBusiSV.regist(m,2);
        }catch (ServiceException e) {
            String message = e.getMessage();
            m.setAddr(message);
            view.addObject("member", m);
            view.addObject("result", "1");
            view.addObject("desc", URLDecoder.decode(message));
            //返回操作提示信息
            view.setViewName("manage/member/form");
            return view;
            //throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), e.getMessage(), map.get(Global.URL).toString(), map);
        }catch (Exception e1) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), e1.getMessage(), map.get(Global.URL).toString(), map);
        }

        String msg = "恭喜您注册成功，您的会员编号为："+member.getMemberId()+"，默认登录密码为注册手机号码后6位，请登录后自行修改密码";

        PageResult.setPrompt(map,"操作成功", "success");
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/manage/member/list"), map);
    }

    /**
     * 跳转到注册页面
     * @param mav
     * @param session
     * @param member
     * @param request
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping("registSimpleIndex")
    public ModelAndView registSimpleIndex(ModelAndView mav, HttpSession session, TableMember member, HttpServletRequest request,
                                          @RequestParam(value = "page",required = false, defaultValue = PageDefaultConstraints.PAGE) int page,
                                          @RequestParam(value = "rows", required = false, defaultValue = PageDefaultConstraints.PAGE_SIZE) int pageSize) {

        mav.setViewName("manage/member/registSimple");
        return mav;
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
     * 重置密码
     * @param mav
     * @param request
     * @param member
     * @return
     */
    @RequiresPermissions("system:member:view")
    @RequestMapping(value = "resetPassword")
    public ModelAndView resetPassword(ModelAndView mav, HttpServletRequest request, TableMember member) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH +"/manage/member/list");
        try {
            this.iTableMemberBusiSV.changePwd(member, null);
        }catch (Exception e) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), e.getMessage(), map.get(Global.URL).toString(), map);
        }
        PageResult.setPrompt(map,"操作成功", "success");
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/manage/member/list"), map);
    }

    /**
     * 删除
     * @param mav
     * @param request
     * @param member
     * @return
     */
    @RequiresPermissions("system:member:view")
    @RequestMapping(value = "delete")
    public ModelAndView delete(ModelAndView mav, HttpServletRequest request, TableMember member) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH +"/manage/member/list");
        try {
            this.iTableMemberBusiSV.delete(member);
        }catch (Exception e) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), e.getMessage(), map.get(Global.URL).toString(), map);
        }
        PageResult.setPrompt(map,"操作成功", "success");
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/manage/member/list"), map);
    }

    /*
     * @Author bianyx
     * @Description //TODO 审核
     * @Date 11:07 2019/1/18
     * @Param [mav, request, topItem, post, buttonType, ghPic1]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequiresPermissions("system:member:edit")
    @RequestMapping(value = "auditIndex")
    public ModelAndView auditIndex(ModelAndView view, HttpServletRequest request, TableMember member) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "会员信息");
        PageResult.getPrompt(view, request, "");

        TableMember tableMember=this.iTableMemberBusiSV.select(member);
        view.addObject("member",tableMember);
        view.setViewName("manage/member/audit");
        return view;
    }

    /**
     * 审核
     * @param mav
     * @param request
     * @param member
     * @return
     */
    @RequiresPermissions("system:member:edit")
    @RequestMapping(value = "audit")
    public ModelAndView audit(ModelAndView mav, HttpServletRequest request, TableMemberDZ member) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH +"/manage/member/list");
        try {
            this.iTableMemberBusiSV.audit(member.getId(), member.getAutFlag());
        }catch (Exception e) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), e.getMessage(), map.get(Global.URL).toString(), map);
        }
        PageResult.setPrompt(map,"操作成功", "success");
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/manage/member/list"), map);
    }

    @RequestMapping("/exportMemberFile")
    public void exportMemberFile(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session1 = request.getSession();


        response.setContentType("application/octet-stream;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        ServletOutputStream outputStream = null;

        try {
            TableMember tableMember = (TableMember)session1.getAttribute("QUERY_MEMBER_LIST");
            outputStream = response.getOutputStream();
            HSSFWorkbook hssfWorkbook = this.iTableMemberBusiSV.exportFile(tableMember, 0, 0, null);
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Disposition", "attachment;filename=member.xls");

            hssfWorkbook.write(outputStream);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if (outputStream != null) outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 修改是否允许登陆状态
     * @param mav
     * @param request
     * @return
     */
    @RequiresPermissions("system:member:edit")
    @RequestMapping(value = "updateLoginState")
    public ModelAndView updateLoginState(ModelAndView mav, HttpServletRequest request, UserInfo userInfo) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH +"/manage/member/list");
        try {
            this.iTableMemberBusiSV.updateLoginState(userInfo.getUsername(), userInfo.getLoginFlag());
        }catch (Exception e) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), e.getMessage(), map.get(Global.URL).toString(), map);
        }
        PageResult.setPrompt(map,"操作成功", "success");
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/manage/member/list"), map);
    }
}
