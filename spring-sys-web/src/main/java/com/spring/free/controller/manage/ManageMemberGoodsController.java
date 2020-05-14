package com.spring.free.controller.manage;/**
 * Created by hengpu on 2019/2/25.
 */

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.spring.fee.model.TableBonusDetail;
import com.spring.fee.model.TableMemberGoods;
import com.spring.fee.model.TableMemberGoodsDZ;
import com.spring.fee.model.TableOrderDZ;
import com.spring.fee.service.ITableBonusDetailBusiSV;
import com.spring.fee.service.ITableMemberGoodsBusiSV;
import com.spring.fee.service.ITableOrderBusiSV;
import com.spring.free.domain.QueryVO;
import com.spring.free.util.DateUtils;
import com.spring.free.util.PageResult;
import com.spring.free.util.constraints.Global;
import com.spring.free.util.constraints.PageDefaultConstraints;
import com.spring.free.util.constraints.PromptInfoConstraints;
import com.spring.free.vo.ScoreVO;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 后台管理/会员持有金鸡管理
 **/
@Controller
@RequestMapping(Global.ADMIN_PATH + "/manage/memberGoods/")
public class ManageMemberGoodsController {

    @Autowired
    ITableMemberGoodsBusiSV iTableMemberGoodsBusiSV;

    @Autowired
    ITableBonusDetailBusiSV iTableBonusDetailBusiSV;

    @Autowired
    ITableOrderBusiSV iTableOrderBusiSV;

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

        TableMemberGoods tableMemberGoods = new TableMemberGoods();
        BeanUtils.copyProperties(queryVO, tableMemberGoods);

        PageInfo<TableMemberGoods> pageInfo = this.iTableMemberGoodsBusiSV.queryListPage(tableMemberGoods, page, pageSize, null);

        //获取热门话题列表信息
        mav.addObject("page", pageInfo);
        mav.addObject("queryVO",queryVO);
        //返回页面header标题
        PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_DICT_LIST);
        //返回操作提示信息
        PageResult.getPrompt(mav, request, queryVO.getParamMsg());

        mav.setViewName("manage/memberGoods/list");
        return mav;
    }

    /*
     * @Author gh
     * @Description //TODO 配置列表
     * @Param [mav, session, post, request, page, pageSize]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequiresPermissions("system:member:view")
    @RequestMapping({"", "listDZ"})
    public ModelAndView listDZ(ModelAndView mav, HttpSession session, QueryVO queryVO, HttpServletRequest request,
                             @RequestParam(value = "page",required = false, defaultValue = PageDefaultConstraints.PAGE) int page,
                             @RequestParam(value = "rows", required = false, defaultValue = PageDefaultConstraints.PAGE_SIZE) int pageSize) {
        // String postType = request.getParameter("postType");

        TableMemberGoodsDZ tableMemberGoods = new TableMemberGoodsDZ();
        BeanUtils.copyProperties(queryVO, tableMemberGoods);

        Date start = null;
        Date end = null;
        if (StringUtils.isNotEmpty(queryVO.getStart()))
            start = DateUtils.parseDate(queryVO.getStart());
        if (StringUtils.isNotEmpty(queryVO.getEnd()))
            end = DateUtils.parseDate(queryVO.getEnd());

        tableMemberGoods.setCreateTimeStart(start);
        tableMemberGoods.setCreateTimeEnd(end);

        PageInfo<TableMemberGoodsDZ> pageInfo = this.iTableMemberGoodsBusiSV.queryListPageDZ(tableMemberGoods, page, pageSize, null);



        //累计金蛋产出数量；当日金蛋产出数量；累计金蛋兑换数量；当日金蛋兑换数量
        List<TableBonusDetail> tableBonusDetailList = iTableBonusDetailBusiSV.selectByGroupBonusId(queryVO.getMemberId(), start, end);
        Date today = DateUtils.getDateZero(DateUtils.getSysDate());
        Date yestoday = DateUtils.getNextDate(DateUtils.getDateZero(DateUtils.getSysDate()));
        List<TableBonusDetail> tableBonusDetailList2 = iTableBonusDetailBusiSV.selectByGroupBonusId(queryVO.getMemberId(), today, yestoday);

        TableOrderDZ tableOrderDZ = new TableOrderDZ();
        tableOrderDZ.setStart(start);
        tableOrderDZ.setEnd(end);
        tableOrderDZ.setMemberId(queryVO.getMemberId());
        TableOrderDZ tableOrderDZ1 = iTableOrderBusiSV.selectByGroup(tableOrderDZ, 1, 1000, null);
        tableOrderDZ.setStart(today);
        tableOrderDZ.setEnd(yestoday);
        TableOrderDZ tableOrderDZ2 = iTableOrderBusiSV.selectByGroup(tableOrderDZ, 1, 1000, null);

        ScoreVO scoreVO = new ScoreVO();
        if (tableBonusDetailList != null) {
            for (TableBonusDetail tableBonusDetail : tableBonusDetailList) {
                if (7==tableBonusDetail.getBonusId()) {
                    scoreVO.setAddAll(tableBonusDetail.getBonus().intValue());
                    break;
                }
            }
        }
        if (tableBonusDetailList != null) {
            for (TableBonusDetail tableBonusDetail : tableBonusDetailList2) {
                if (7==tableBonusDetail.getBonusId()) {
                    scoreVO.setAddToday(Integer.parseInt(tableBonusDetail.getBonus()+""));
                    break;
                }
            }
        }
        if (null != tableOrderDZ1) {
            scoreVO.setUseAll(tableOrderDZ1.getScorePrice());
        }
        if (null != tableOrderDZ2) {
            scoreVO.setUseToday(tableOrderDZ2.getScorePrice());
        }

        List<ScoreVO> scoreVOList = new ArrayList<>();
        scoreVOList.add(scoreVO);

        //获取热门话题列表信息
        mav.addObject("page", pageInfo);
        mav.addObject("queryVO",queryVO);
        mav.addObject("scoreVOList", scoreVOList);
        //返回页面header标题
        PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_DICT_LIST);
        //返回操作提示信息
        PageResult.getPrompt(mav, request, queryVO.getParamMsg());

        mav.setViewName("manage/memberGoods/listDZ");
        return mav;
    }
}
