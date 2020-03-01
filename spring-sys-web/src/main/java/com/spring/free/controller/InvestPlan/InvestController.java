package com.spring.free.controller.InvestPlan;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.spring.fee.constants.InvestConstants;
import com.spring.fee.model.*;
import com.spring.fee.service.*;
import com.spring.free.domain.Fee;
import com.spring.free.util.DateUtils;
import com.spring.free.util.PageResult;
import com.spring.free.util.constraints.Global;
import com.spring.free.util.constraints.PageDefaultConstraints;
import com.spring.free.util.constraints.PromptInfoConstraints;
import com.spring.free.utils.velocity.DictUtils;
import com.spring.free.vo.MemberReqVO;
import com.spring.free.vo.SettleRecordReqVO;
import com.spring.free.vo.StartInvestPlanVO;
import com.sun.javafx.runtime.async.BackgroundExecutor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
 * 粮仓计划controller
 */
@Slf4j
@Controller
@RequestMapping(Global.ADMIN_PATH + "/invest/plan/")
public class InvestController {

    @Autowired
    ITInvestQueueBusiSV itInvestQueueBusiSV;

    @Autowired
    ITInvestPlanMainBusiSV itInvestPlanMainBusiSV;

    @Autowired
    ITInvestPlanDetailBusiSV itInvestPlanDetailBusiSV;

    @Autowired
    ITInvestPlanConfigBusiSV itInvestPlanConfigBusiSV;

    @Autowired
    ITWheatMemberBusiSV itWheatMemberBusiSV;

    @Autowired
    ITInvestPlanMainLogSV itInvestPlanMainLogSV;

    @Autowired
    ITInvestPlanBonusBusiSV itInvestPlanBonusBusiSV;

    @Autowired
    ITInvestSettleRecordBusiSV itInvestSettleRecordBusiSV;

    @Autowired
    ITWheatAccountBusiSV itWheatAccountBusiSV;

    /*
     * @Author bianyx
     * @Description //TODO 已购买列表
     * @Date 11:02 2019/1/18
     * @Param [mav, session, post, request, page, pageSize]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequiresPermissions("system:invest:view")
    @RequestMapping({"", "detaillist"})
    public ModelAndView planConfigList(ModelAndView mav, HttpSession session, TInvestPlanMainDZ tInvestPlanMainDZ, HttpServletRequest request,
                                       @RequestParam(value = "page",required = false, defaultValue = PageDefaultConstraints.PAGE) int page,
                                       @RequestParam(value = "rows", required = false, defaultValue = PageDefaultConstraints.PAGE_SIZE) int pageSize) {
       // String postType = request.getParameter("postType");

        TInvestPlanDetail investPlanDetail = new TInvestPlanDetail();
        investPlanDetail.setMemberId(tInvestPlanMainDZ.getMemberId());
        PageInfo<TInvestPlanDetail> list = this.itInvestPlanDetailBusiSV.queryList(investPlanDetail, page, pageSize, null);

        //获取热门话题列表信息
        mav.addObject("page", list);
        mav.addObject("param",tInvestPlanMainDZ);
        //返回页面header标题
        PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_DICT_LIST);
//        //返回操作提示信息
//        PageResult.getPrompt(mav, request, tInvestPlanMainDZ.getParamMsg());

        mav.setViewName("wheat/invest/detailList");
        return mav;
    }


    /*
     * @Author bianyx
     * @Description //TODO 当日结算列表
     * @Date 11:02 2019/1/18
     * @Param [mav, session, post, request, page, pageSize]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequiresPermissions("system:invest:view")
    @RequestMapping({"", "list4Settle"})
    public ModelAndView list4Settle(ModelAndView mav, HttpSession session, TInvestPlanMainDZ tInvestPlanMainDZ, HttpServletRequest request,
                                       @RequestParam(value = "page",required = false, defaultValue = PageDefaultConstraints.PAGE) int page,
                                       @RequestParam(value = "rows", required = false, defaultValue = PageDefaultConstraints.PAGE_SIZE) int pageSize) {
        TInvestSettleRecordDZ tInvestSettleRecordDZ = new TInvestSettleRecordDZ();
        tInvestSettleRecordDZ.setOrderBy("id desc");
        PageInfo<TInvestSettleRecord> arr = this.itInvestSettleRecordBusiSV.queryListPage(tInvestSettleRecordDZ, 1,1,null);
        if (arr != null && !CollectionUtils.isEmpty(arr.getList())) {
            mav.addObject("lastTime", arr.getList().get(0).getCreateTime());
        }
        mav.addObject("sysTime", DateUtils.getSysDate());
        mav.addObject("flag", "1");
        //返回页面header标题
        PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_DICT_LIST);
//        //返回操作提示信息
//        PageResult.getPrompt(mav, request, tInvestPlanMainDZ.getParamMsg());

        mav.setViewName("wheat/invest/detailList4Settle");
        return mav;
    }

    /*
     * @Author bianyx
     * @Description //TODO 开始结算
     * @Date 11:02 2019/1/18
     * @Param [mav, session, post, request, page, pageSize]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequiresPermissions("system:invest:view")
    @RequestMapping({"", "runSettle"})
    public ModelAndView runSettle(ModelAndView mav, HttpSession session, TInvestPlanMainDZ tInvestPlanMainDZ, HttpServletRequest request,
                                    @RequestParam(value = "page",required = false, defaultValue = PageDefaultConstraints.PAGE) int page,
                                    @RequestParam(value = "rows", required = false, defaultValue = PageDefaultConstraints.PAGE_SIZE) int pageSize) {
        // String postType = request.getParameter("postType");
        try {
            TInvestSettleRecord tInvestSettleRecord = this.itInvestPlanMainBusiSV.runSettleV2();
            TInvestPlanMainLog tInvestPlanMainLog = new TInvestPlanMainLog();
            tInvestPlanMainLog.setSettleRecordId(tInvestSettleRecord.getId());
            tInvestPlanMainLog.setStatus("2");
            List<TInvestPlanMainLog> tInvestPlanMainLogList = this.itInvestPlanMainLogSV.queryList(tInvestPlanMainLog);
            List<TInvestPlanMainDZ> arr = new ArrayList<>();
            int failCount = 0;
            if(!CollectionUtils.isEmpty(tInvestPlanMainLogList)) {
                for (TInvestPlanMainLog log: tInvestPlanMainLogList) {
                    TInvestPlanMain tInvestPlanMain = new TInvestPlanMain();
                    tInvestPlanMain.setId(Long.parseLong(log.getPlanMainId()));
                    tInvestPlanMain = this.itInvestPlanMainBusiSV.select(tInvestPlanMain);
                    TInvestPlanMainDZ tInvestPlanMainDZ1 = new TInvestPlanMainDZ();
                    BeanUtils.copyProperties(tInvestPlanMain, tInvestPlanMainDZ1);
                    tInvestPlanMainDZ1.setRemark(log.getRemark());
                    arr.add(tInvestPlanMainDZ1);
                    failCount++;
                }
            }


            // 会员升级
            this.itWheatMemberBusiSV.upLevelV2ALL();
            mav.addObject("result", "结算涉及粮仓计划数量："+tInvestSettleRecord.getPlanCount()+",其中失败数量："+failCount+".");
            mav.addObject("page", arr);
        }catch (Exception e) {
            e.printStackTrace();
            mav.addObject("result", e.getMessage());
        }
        mav.setViewName("wheat/invest/detailList4Settle");
        return mav;
    }

    /*
     * @Author bianyx
     * @Description //TODO 粮仓收益列表
     * @Date 11:02 2019/1/18
     * @Param [mav, session, post, request, page, pageSize]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequiresPermissions("system:invest:view")
    @RequestMapping({"", "bonuslist"})
    public ModelAndView bonuslist(ModelAndView mav, HttpSession session, MemberReqVO memberReqVO, HttpServletRequest request,
                                  @RequestParam(value = "page",required = false, defaultValue = PageDefaultConstraints.PAGE) int page,
                                  @RequestParam(value = "rows", required = false, defaultValue = PageDefaultConstraints.PAGE_SIZE) int pageSize) {
        // String postType = request.getParameter("postType");

        TInvestPlanBonus investPlanBonus = new TInvestPlanBonus();
        investPlanBonus.setMemberId(memberReqVO.getMemberId());
        PageInfo<TInvestPlanBonus> list = this.itInvestPlanBonusBusiSV.queryPage(investPlanBonus, page, pageSize, null);


        //获取热门话题列表信息
        mav.addObject("page", list);
        mav.addObject("param",memberReqVO);
        //返回页面header标题
        PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_DICT_LIST);
//        //返回操作提示信息
//        PageResult.getPrompt(mav, request, tInvestPlanMainDZ.getParamMsg());

        mav.setViewName("wheat/invest/bonusList");
        return mav;
    }

    /*
     * @Author bianyx
     * @Description //TODO 结算记录列表
     * @Date 11:02 2019/1/18
     * @Param [mav, session, post, request, page, pageSize]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequiresPermissions("system:invest:view")
    @RequestMapping({"", "settleloglist"})
    public ModelAndView settleloglist(ModelAndView mav, HttpSession session, SettleRecordReqVO settleRecordReqVO, HttpServletRequest request,
                                      @RequestParam(value = "page",required = false, defaultValue = PageDefaultConstraints.PAGE) int page,
                                      @RequestParam(value = "rows", required = false, defaultValue = PageDefaultConstraints.PAGE_SIZE) int pageSize) {
        // String postType = request.getParameter("postType");

        TInvestSettleRecordDZ tInvestSettleRecordDZ = new TInvestSettleRecordDZ();
        if (!StringUtils.isEmpty(settleRecordReqVO.getStart())) {
            tInvestSettleRecordDZ.setCreateTimeStart(DateUtils.parseDate(settleRecordReqVO.getStart()));
        }
        if (!StringUtils.isEmpty(settleRecordReqVO.getEnd())) {
            tInvestSettleRecordDZ.setCreateTimeEnd(DateUtils.addDays(DateUtils.parseDate(settleRecordReqVO.getEnd()), 1));
        }
        tInvestSettleRecordDZ.setOrderBy(" id desc");
       PageInfo<TInvestSettleRecord> pageInfo = this.itInvestSettleRecordBusiSV.queryListPage(tInvestSettleRecordDZ, page, pageSize, null);


        //获取热门话题列表信息
        mav.addObject("page", pageInfo);
        mav.addObject("param",settleRecordReqVO);
        //返回页面header标题
        PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_DICT_LIST);
//        //返回操作提示信息
       // PageResult.getPrompt(mav, request, TInvestPlanMainDZ.getParamMsg());

        mav.setViewName("wheat/invest/settleloglist");
        return mav;
    }

    /*
     * @Author bianyx
     * @Description //TODO 计划结算记录明细
     * @Date 11:02 2019/1/18
     * @Param [mav, session, post, request, page, pageSize]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequiresPermissions("system:invest:view")
    @RequestMapping({"", "planmainloglist/{settleRecordId}"})
    public ModelAndView planmainloglist(ModelAndView mav, HttpSession session, @PathVariable String settleRecordId, HttpServletRequest request,
                                        @RequestParam(value = "page",required = false, defaultValue = PageDefaultConstraints.PAGE) int page,
                                        @RequestParam(value = "rows", required = false, defaultValue = PageDefaultConstraints.PAGE_SIZE) int pageSize) {
        // String postType = request.getParameter("postType");
        try {
            TInvestPlanMainLogDZ tInvestPlanMainLog = new TInvestPlanMainLogDZ();
            tInvestPlanMainLog.setSettleRecordId(Long.parseLong(settleRecordId));
            List<TInvestPlanMainLogDZ> tInvestPlanMainLogList = this.itInvestPlanMainLogSV.queryList2(tInvestPlanMainLog);
            mav.addObject("page", tInvestPlanMainLogList);
        }catch (Exception e) {
            e.printStackTrace();
            mav.addObject("result", e.getMessage());
        }
        mav.setViewName("wheat/invest/planmainloglist");
        return mav;
    }

    /*
     * @Author bianyx
     * @Description //TODO 伞下业绩及明细
     * @Date 11:02 2019/1/18
     * @Param [mav, session, post, request, page, pageSize]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequiresPermissions("system:invest:view")
    @RequestMapping({"", "teamFreeingListIndex"})
    public ModelAndView teamFreeingListIndex(ModelAndView mav, HttpSession session, MemberReqVO memberReqVO, HttpServletRequest request,
                                        @RequestParam(value = "page",required = false, defaultValue = PageDefaultConstraints.PAGE) int page,
                                        @RequestParam(value = "rows", required = false, defaultValue = PageDefaultConstraints.PAGE_SIZE) int pageSize) {
        mav.setViewName("wheat/invest/teamFreeingList");
        return mav;
    }

    /*
     * @Author bianyx
     * @Description //TODO 伞下业绩及明细
     * @Date 11:02 2019/1/18
     * @Param [mav, session, post, request, page, pageSize]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequiresPermissions("system:invest:view")
    @RequestMapping({"", "teamFreeingList"})
    public ModelAndView teamFreeingList(ModelAndView mav, HttpSession session, MemberReqVO memberReqVO, HttpServletRequest request,
                                       @RequestParam(value = "page",required = false, defaultValue = PageDefaultConstraints.PAGE) int page,
                                       @RequestParam(value = "rows", required = false, defaultValue = PageDefaultConstraints.PAGE_SIZE) int pageSize) {
        // String postType = request.getParameter("postType");

        List<TWheatMemberDZ> arr = new ArrayList<>();
        long totalMaxFreeing = 0l;
        //查询伞下会员列表
        List<TWheatMember> tWheatMembers = this.itWheatMemberBusiSV.queryAllChildList(memberReqVO.getMemberId());
        if (!CollectionUtils.isEmpty(tWheatMembers)) {
            for (TWheatMember tWheatMember : tWheatMembers) {
                TWheatMemberDZ dz = new TWheatMemberDZ();
                BeanUtils.copyProperties(tWheatMember, dz);
                TWheatAccount account = itWheatAccountBusiSV.selectByMember(tWheatMember.getMemberId());
                if (account != null) {
                    dz.setMaxFreeing(account.getGranaryIngMaxFreeze());
                    totalMaxFreeing += account.getGranaryIngMaxFreeze();
                } else {
                    dz.setMaxFreeing(0l);
                }
                dz.setLevelDesc(DictUtils.getDictLabel(dz.getLevel(), "level", ""));
                arr.add(dz);
            }
        }

        mav.addObject("param",memberReqVO);
        mav.addObject("totalMaxFreeing", totalMaxFreeing / 1000);
        mav.addObject("page", arr);

        mav.setViewName("wheat/invest/teamFreeingList");
        return mav;
    }
}
