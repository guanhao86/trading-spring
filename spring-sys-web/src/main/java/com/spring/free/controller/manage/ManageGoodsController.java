package com.spring.free.controller.manage;/**
 * Created by hengpu on 2019/2/25.
 */

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.spring.fee.constants.InvestConstants;
import com.spring.fee.model.TableGoods;
import com.spring.fee.model.TableMember;
import com.spring.fee.service.ITableGoodsBusiSV;
import com.spring.free.config.ImageUtils;
import com.spring.free.domain.QueryVO;
import com.spring.free.util.PageResult;
import com.spring.free.util.constraints.Global;
import com.spring.free.util.constraints.PageDefaultConstraints;
import com.spring.free.util.constraints.PromptInfoConstraints;
import com.spring.free.util.md5.Md5Util;
import jnr.ffi.annotations.In;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 后台管理/商品
 **/
@Controller
@RequestMapping(Global.ADMIN_PATH + "/manage/goods/")
public class ManageGoodsController {

    @Autowired
    ITableGoodsBusiSV iTableGoodsBusiSV;

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
                             @RequestParam(value = "rows", required = false, defaultValue = PageDefaultConstraints.PAGE_SIZE) int pageSize) {
        // String postType = request.getParameter("postType");

        TableGoods tableGoods = new TableGoods();
        BeanUtils.copyProperties(queryVO, tableGoods);

        PageInfo<TableGoods> pageInfo = this.iTableGoodsBusiSV.queryListPage(tableGoods, page, pageSize, null);

        //获取热门话题列表信息
        mav.addObject("page", pageInfo);
        mav.addObject("queryVO",queryVO);
        //返回页面header标题
        PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_DICT_LIST);
        //返回操作提示信息
        PageResult.getPrompt(mav, request, queryVO.getParamMsg());

        mav.setViewName("manage/goods/list");
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
    public ModelAndView views(ModelAndView view, HttpServletRequest request, TableGoods tableGoods, String buttonType) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "商品信息");
        PageResult.getPrompt(view, request, "");
        TableGoods tableMember=this.iTableGoodsBusiSV.select(tableGoods);

        //获取金鸡商品列表
        TableGoods jjTableGoods = new TableGoods();
        jjTableGoods.setGoodsClass(Integer.parseInt(InvestConstants.GoodsClass.BONUS_TYPE_3));
        jjTableGoods.setState(InvestConstants.GoodsState.effect);
        PageInfo<TableGoods> pageInfo = this.iTableGoodsBusiSV.queryListPage(jjTableGoods, 1, 100, null);
        view.addObject("jjGoodsList",pageInfo.getList());
        view.addObject("goods",tableMember);
        view.setViewName("manage/goods/edit");
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
    public ModelAndView edit(ModelAndView mav, HttpServletRequest request, TableGoods tableGoods, MultipartFile thumbnialImgSrcFile, MultipartFile detailImgSrcFile) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH +"/manage/goods/list");

        if (thumbnialImgSrcFile != null && StringUtils.isNotEmpty(thumbnialImgSrcFile.getOriginalFilename())) {
            //缩略图
            String imgPath = ImageUtils.upload(thumbnialImgSrcFile);
            tableGoods.setThunmbanilImgSrc(imgPath);
        }

        if (detailImgSrcFile != null && StringUtils.isNotEmpty(detailImgSrcFile.getOriginalFilename())) {
            //详情图
            String imgPath = ImageUtils.upload(detailImgSrcFile);
            tableGoods.setDetailImgSrc(imgPath);
        }

        if (null != tableGoods.getId()) {
            this.iTableGoodsBusiSV.update(tableGoods);
        } else {
            tableGoods.setState("1");
            this.iTableGoodsBusiSV.insert(tableGoods);
        }
        PageResult.setPrompt(map,"操作成功", "success");
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/manage/goods/list"), map);
    }

    @RequiresPermissions("system:member:edit")
    @RequestMapping(value = "view")
    public ModelAndView view(ModelAndView view, HttpServletRequest request, TableGoods tableGoods) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "商品信息");
        PageResult.getPrompt(view, request, "");
        TableGoods tableGoods1=this.iTableGoodsBusiSV.select(tableGoods);
        TableGoods jjGoods = new TableGoods();
        if (StringUtils.isNotEmpty(tableGoods1.getExtentGoodsId())) {
            jjGoods.setId(Integer.parseInt(tableGoods1.getExtentGoodsId()));
            jjGoods = this.iTableGoodsBusiSV.select(jjGoods);
        }

//        if(tableMember!=null) {
//            member.setTotal(account.getTotal().doubleValue() / 1000);
//            member.setAvailable(account.getAvailable().doubleValue() / 1000);
//            member.setFreeze(account.getFreeze().doubleValue() / 1000);
//            member.setMoneyFreeze(account.getMoneyFreeze().doubleValue() / 1000);
//            member.setGranaryFreeze(account.getGranaryFreeze().doubleValue() / 1000);
//        }
        view.addObject("jjGoods",jjGoods);
        view.addObject("goods",tableGoods1);
        view.setViewName("manage/goods/view");
        return view;
    }

    /**
     * 下架商品
     * @param mav
     * @param request
     * @param tableGoods
     * @return
     */
    @RequiresPermissions("system:member:edit")
    @RequestMapping(value = "pullOff")
    public ModelAndView pullOff(ModelAndView mav, HttpServletRequest request, TableGoods tableGoods) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH +"/manage/goods/list");

        TableGoods tableGoods1 = new TableGoods();
        tableGoods1.setId(tableGoods.getId());
        tableGoods1.setState("2");//下架
        this.iTableGoodsBusiSV.update(tableGoods1);
        PageResult.setPrompt(map,"操作成功", "success");
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/manage/goods/list"), map);
    }
}
