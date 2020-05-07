package com.spring.free.controller.front;/**
 * Created by hengpu on 2019/2/25.
 */

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.spring.fee.constants.InvestConstants;
import com.spring.fee.model.TableGoods;
import com.spring.fee.service.ITableGoodsBusiSV;
import com.spring.fee.service.ITableMemberBusiSV;
import com.spring.free.config.ImageUtils;
import com.spring.free.domain.QueryVO;
import com.spring.free.util.PageResult;
import com.spring.free.util.constraints.Global;
import com.spring.free.util.constraints.PageDefaultConstraints;
import com.spring.free.util.constraints.PromptInfoConstraints;
import com.spring.free.util.exception.ExceptionCodeEnum;
import com.spring.free.util.exception.ServiceException;
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
 * 前端/商品
 **/
@Controller
@RequestMapping(Global.ADMIN_PATH + "/front/goods/")
public class FrontGoodsController {

    @Autowired
    ITableGoodsBusiSV iTableGoodsBusiSV;

    @Autowired
    ITableMemberBusiSV iTableMemberBusiSV;

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

        if (StringUtils.isNotEmpty(queryVO.getMemberId())) {
            Map map = Maps.newHashMap();
            map.put(Global.URL, Global.ADMIN_PATH +"/front/member/beMemberIndex");
            if (null == this.iTableMemberBusiSV.selectByMemberId(queryVO.getMemberId())) {
                throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "会员不存在，请确认会员ID输入是否正确", map.get(Global.URL).toString(), map);
            }
        }

        tableGoods.setState("1");
        PageInfo<TableGoods> pageInfo = this.iTableGoodsBusiSV.queryListPage(tableGoods, page, pageSize, null);

        //获取热门话题列表信息
        mav.addObject("page", pageInfo);
        mav.addObject("queryVO",queryVO);
        //返回页面header标题
        PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_DICT_LIST);
        //返回操作提示信息
        PageResult.getPrompt(mav, request, queryVO.getParamMsg());

        mav.setViewName("front/goods/list");
        return mav;
    }

    @RequiresPermissions("system:member:view")
    @RequestMapping(value = "view")
    public ModelAndView view(ModelAndView view, HttpServletRequest request, QueryVO queryVO) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "商品信息");
        PageResult.getPrompt(view, request, "");
        TableGoods tableGoods = new TableGoods();
        tableGoods.setId(Integer.parseInt(queryVO.getId()+""));
        TableGoods tableGoods1=this.iTableGoodsBusiSV.select(tableGoods);

        TableGoods jjGoods = new TableGoods();
        if (StringUtils.isNotEmpty(tableGoods1.getExtentGoodsId())) {
            jjGoods.setId(Integer.parseInt(tableGoods1.getExtentGoodsId()));
            jjGoods = this.iTableGoodsBusiSV.select(jjGoods);
        }

        //获取金鸡商品列表
        TableGoods jjTableGoods = new TableGoods();
        jjTableGoods.setGoodsClass(Integer.parseInt(InvestConstants.GoodsClass.BONUS_TYPE_3));
        jjTableGoods.setState(InvestConstants.GoodsState.effect);
        PageInfo<TableGoods> pageInfo = this.iTableGoodsBusiSV.queryListPage(jjTableGoods, 1, 100, null);
        view.addObject("jjGoodsList",pageInfo.getList());

        view.addObject("jjGoods",jjGoods);
        view.addObject("goods",tableGoods1);
        view.addObject("queryVO",queryVO);
        view.setViewName("front/goods/view");
        return view;
    }

}
