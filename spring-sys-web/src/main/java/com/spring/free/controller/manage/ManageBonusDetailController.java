package com.spring.free.controller.manage;/**
 * Created by hengpu on 2019/2/25.
 */

import com.github.pagehelper.PageInfo;
import com.spring.fee.model.TableBonusDetail;
import com.spring.fee.model.TableOrder;
import com.spring.fee.model.TableOrderDZ;
import com.spring.fee.service.ITableBonusDetailBusiSV;
import com.spring.fee.service.ITableOrderBusiSV;
import com.spring.free.config.CommonUtils;
import com.spring.free.domain.QueryVO;
import com.spring.free.util.DateUtils;
import com.spring.free.util.PageResult;
import com.spring.free.util.constraints.Global;
import com.spring.free.util.constraints.PageDefaultConstraints;
import com.spring.free.util.constraints.PromptInfoConstraints;
import com.spring.free.vo.BonusDetailPerVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 后台管理/奖金接口
 **/
@Controller
@RequestMapping(Global.ADMIN_PATH + "/manage/bonus/")
public class ManageBonusDetailController {

    @Autowired
    ITableBonusDetailBusiSV iTableBonusDetailBusiSV;
    @Autowired
    ITableOrderBusiSV iTableOrderBusiSV;



    /*
     * @Author haha
     * @Description //TODO 配置列表
     * @Param [mav, session, post, request, page, pageSize]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequiresPermissions("system:member:view")
    @RequestMapping({"", "listIndex"})
    public ModelAndView listIndex(ModelAndView mav, HttpSession session, QueryVO queryVO, HttpServletRequest request,
                             @RequestParam(value = "page",required = false, defaultValue = PageDefaultConstraints.PAGE) int page,
                             @RequestParam(value = "rows", required = false, defaultValue = PageDefaultConstraints.PAGE_SIZE) int pageSize) {
        // String postType = request.getParameter("postType");

        TableBonusDetail memberAccountDetail = new TableBonusDetail();
        BeanUtils.copyProperties(queryVO, memberAccountDetail);

        //获取热门话题列表信息
        mav.addObject("queryVO",queryVO);
        //返回页面header标题
        PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_DICT_LIST);
        //返回操作提示信息
        PageResult.getPrompt(mav, request, queryVO.getParamMsg());

        mav.setViewName("manage/bonus/listIndex");
        return mav;
    }

    /*
     * @Author haha
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

        TableBonusDetail memberAccountDetail = new TableBonusDetail();
        BeanUtils.copyProperties(queryVO, memberAccountDetail);

        PageInfo<TableBonusDetail> pageInfo = this.iTableBonusDetailBusiSV.queryListPage(memberAccountDetail, page, pageSize, CommonUtils.getStartEnd(queryVO));

        //获取热门话题列表信息
        mav.addObject("page", pageInfo);
        mav.addObject("queryVO",queryVO);
        //返回页面header标题
        PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_DICT_LIST);
        //返回操作提示信息
        PageResult.getPrompt(mav, request, queryVO.getParamMsg());

        mav.setViewName("manage/bonus/list");
        return mav;
    }

    /*
     * 增加一个奖金总账菜单
     * @Author haha
     * @Description //TODO 配置列表
     * @Param [mav, session, post, request, page, pageSize]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequiresPermissions("system:member:view")
    @RequestMapping({"", "listPer"})
    public ModelAndView listPer(ModelAndView mav, HttpSession session, QueryVO queryVO, HttpServletRequest request,
                             @RequestParam(value = "page",required = false, defaultValue = PageDefaultConstraints.PAGE) int page,
                             @RequestParam(value = "rows", required = false, defaultValue = PageDefaultConstraints.PAGE_SIZE) int pageSize) {

        Date yesterday0 = DateUtils.getDateZero(DateUtils.getYesterday(new Date()));
        Date today0 = DateUtils.getDateZero(new Date());

        List list = new ArrayList(){{add("1"); add("2"); add("3"); add("4"); add("5"); add("6");}};
        //日新增业绩 = 》昨天日期的bonus值，并且close_state = 1 求和
        TableBonusDetail dayData = iTableBonusDetailBusiSV.selectByGroup(yesterday0, today0, list);
        //总奖金
        TableBonusDetail allData = iTableBonusDetailBusiSV.selectByGroup(null, null, list);

        //总业绩=》所有订单表（报单商品、复消商品、金鸡商品）提货商品的不算，被删除的（退货订单）不算，所有这些符合条件的订单，时间也是截至到昨日的订单金额求和
        TableOrderDZ tableOrderDZ = iTableOrderBusiSV.selectByGroup2(null, null, today0);

        BonusDetailPerVO bonusDetailPerVO = new BonusDetailPerVO();
        bonusDetailPerVO.setYesterdayAddBonus(dayData==null?0f:dayData.getBonus());
        bonusDetailPerVO.setAllAddBonus(allData==null?0f:allData.getBonus());
        bonusDetailPerVO.setAllOrderPrice(tableOrderDZ==null?0f:tableOrderDZ.getPrice());
        String allPer = Math.floor(
                bonusDetailPerVO.getAllAddBonus() / (bonusDetailPerVO.getAllOrderPrice() == 0 ? 1 : bonusDetailPerVO.getAllOrderPrice()) * 100) / 100 + "%";
        bonusDetailPerVO.setAllPer(allPer);

        //获取热门话题列表信息
        mav.addObject("bonusDetailPerVO",bonusDetailPerVO);
        //返回页面header标题
        PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_DICT_LIST);
        //返回操作提示信息
        PageResult.getPrompt(mav, request, queryVO.getParamMsg());

        mav.setViewName("manage/bonus/per");
        return mav;
    }

}
