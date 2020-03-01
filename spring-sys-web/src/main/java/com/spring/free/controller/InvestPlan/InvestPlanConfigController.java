package com.spring.free.controller.InvestPlan;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.spring.fee.model.TInvestMemberMoneyConfig;
import com.spring.fee.model.TInvestPlanConfig;
import com.spring.fee.model.TInvestQueue;
import com.spring.fee.service.*;
import com.spring.free.domain.Fee;
import com.spring.free.util.PageResult;
import com.spring.free.util.constraints.Global;
import com.spring.free.util.constraints.PageDefaultConstraints;
import com.spring.free.util.constraints.PromptInfoConstraints;
import com.spring.free.utils.velocity.DictUtils;
import com.spring.free.vo.StartInvestPlanVO;
import com.spring.free.vo.TInvestMemberMoneyConfigVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 粮仓计划配置controller
 */
@Slf4j
@Controller
@RequestMapping(Global.ADMIN_PATH + "/invest/planConfig/")
public class InvestPlanConfigController {

    @Autowired
    ITInvestQueueBusiSV itInvestQueueBusiSV;

    @Autowired
    ITInvestPlanMainBusiSV itInvestPlanMainBusiSV;

    @Autowired
    ITInvestPlanConfigBusiSV itInvestPlanConfigBusiSV;

    @Autowired
    ITInvestMemberMoneyConfigBusiSV itInvestMemberMoneyConfigBusiSV;

    /**
     * 购买粮仓排队
     * @param tInvestQueue
     * @return
     */
    @RequestMapping("createQueueBuyInvest")
    public Object createQueueBuyInvest(@RequestBody TInvestQueue tInvestQueue){
        log.info("购买粮仓排队: {}", tInvestQueue);
        JSONObject jsonObject = new JSONObject();
        try {
            itInvestQueueBusiSV.createQueue(tInvestQueue);
        }catch (Exception e) {
            jsonObject.put("result", e.getMessage());
        }
        return jsonObject;
    }

    /**
     * 排队->开始执行粮仓计划
     * @param startInvestPlanVO
     * @return
     */
    @RequestMapping("startInvestPlan")
    public Object startInvestPlan(@RequestBody StartInvestPlanVO startInvestPlanVO){
        log.info("排队->开始执行粮仓计划: {}", startInvestPlanVO);
        JSONObject jsonObject = new JSONObject();
        try {
            itInvestPlanMainBusiSV.createPlan(Integer.parseInt(startInvestPlanVO.getQueueNum()));
        }catch (Exception e) {
            jsonObject.put("result", e.getMessage());
        }
        return jsonObject;
    }

    /**
     * 开始结算
     * @return
     */
    @RequestMapping("startSettle")
    public Object startSettle(){
        log.info("开始结算");
        JSONObject jsonObject = new JSONObject();
        try {
            itInvestPlanMainBusiSV.runSettleV2();
        }catch (Exception e) {
            jsonObject.put("result", e.getMessage());
        }

        return jsonObject;
    }

    /**
     * 购买粮仓排队并立即执行队列
     * @param tInvestQueue
     * @return
     */
    @RequestMapping("createQueueAndRun")
    public Object createQueueAndRun(@RequestBody TInvestQueue tInvestQueue){
        log.info("购买粮仓排队并立即执行队列: {}", tInvestQueue);
        JSONObject jsonObject = new JSONObject();
        try {
            itInvestQueueBusiSV.createQueueV2AndRun(tInvestQueue);
        }catch (Exception e) {
            jsonObject.put("result", e.getMessage());
        }
        return jsonObject;
    }

    /*
     * @Author bianyx
     * @Description //TODO 配置列表
     * @Date 11:02 2019/1/18
     * @Param [mav, session, post, request, page, pageSize]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequiresPermissions("system:invest:view")
    @RequestMapping({"", "configlist"})
    public ModelAndView planConfigList(ModelAndView mav, HttpSession session, Fee fee, HttpServletRequest request,
                                  @RequestParam(value = "page",required = false, defaultValue = PageDefaultConstraints.PAGE) int page,
                                  @RequestParam(value = "rows", required = false, defaultValue = PageDefaultConstraints.PAGE_SIZE) int pageSize) {
       // String postType = request.getParameter("postType");

        List<TInvestPlanConfig> investPlanConfigList = this.itInvestPlanConfigBusiSV.queryList(new TInvestPlanConfig());


        //获取热门话题列表信息
        mav.addObject("page", investPlanConfigList);
        mav.addObject("fee",fee);
        //返回页面header标题
        PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_DICT_LIST);
        //返回操作提示信息
        PageResult.getPrompt(mav, request, fee.getParamMsg());

        mav.setViewName("wheat/planConfig/planConfigList");
        return mav;
    }


    /*
     * @Author jzc
     * @Description //TODO 粮仓配置编辑跳转
     * @Date 11:06 2019/2/28
     * @Param [view, request, post, buttonType]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequiresPermissions("system:invest:edit")
    @RequestMapping(value = "edit")
    public ModelAndView views(ModelAndView view, HttpServletRequest request, TInvestPlanConfig config, String buttonType) {
        Map map = Maps.newHashMap();
//        PageResult.setPageTitle(view, "手续费配置修改");
//        PageResult.getPrompt(view, request, config.getParamMsg());
        TInvestPlanConfig tInvestPlanConfig = new TInvestPlanConfig();
        tInvestPlanConfig.setPlanId(config.getPlanId());
        config=this.itInvestPlanConfigBusiSV.select(tInvestPlanConfig);
        view.addObject("config",config);
        view.setViewName("wheat/planConfig/planConfigEdit");
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
    @RequiresPermissions("system:invest:edit")
    @RequestMapping(value = "save")
    public ModelAndView edit(ModelAndView mav, HttpServletRequest request, TInvestPlanConfig config) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH + "/invest/plan/edit?planId="+config.getPlanId());
        config.setMainAmount(config.getMainAmount()*1000);
        config.setSecondAmount(config.getSecondAmount()*1000);
        config.setPlanAmount(config.getPlanAmount()*1000);
        this.itInvestPlanConfigBusiSV.update(config);
        PageResult.setPrompt(map,"操作成功", "success");
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/invest/planConfig/configlist"), map);
    }

    /*
     * @Author bianyx
     * @Description //TODO 奖金计算配置列表
     * @Date 11:02 2019/1/18
     * @Param [mav, session, post, request, page, pageSize]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequiresPermissions("system:invest:view")
    @RequestMapping({"", "bonusConfiglist"})
    public ModelAndView bonusConfiglist(ModelAndView mav, HttpSession session, Fee fee, HttpServletRequest request,
                                       @RequestParam(value = "page",required = false, defaultValue = PageDefaultConstraints.PAGE) int page,
                                       @RequestParam(value = "rows", required = false, defaultValue = PageDefaultConstraints.PAGE_SIZE) int pageSize) {

        List<TInvestMemberMoneyConfig> tInvestMemberMoneyConfigList = this.itInvestMemberMoneyConfigBusiSV.queryList(new TInvestMemberMoneyConfig(), null);

        List<TInvestMemberMoneyConfigVO> voList = new ArrayList<>();

        if (tInvestMemberMoneyConfigList != null) {

            //获取planConfigMap
            Map<String, TInvestPlanConfig> configMap = this.itInvestPlanConfigBusiSV.queryMap();

            for (TInvestMemberMoneyConfig config : tInvestMemberMoneyConfigList) {
                TInvestMemberMoneyConfigVO vo = new TInvestMemberMoneyConfigVO();
                BeanUtils.copyProperties(config, vo);
                TInvestPlanConfig tInvestPlanConfig = configMap.get(vo.getPlanId());
                vo.setPlanName(tInvestPlanConfig!=null?tInvestPlanConfig.getPlanName():"");
                vo.setMemberLevelDesc(DictUtils.getDictLabel(String.valueOf(vo.getLevelId()),"level",""));
                voList.add(vo);
            }
        }

        //列表
        mav.addObject("page", voList);
        //返回页面header标题
        PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_DICT_LIST);
        //返回操作提示信息
        PageResult.getPrompt(mav, request, fee.getParamMsg());

        mav.setViewName("wheat/planConfig/planBonusConfigList");
        return mav;
    }


    /*
     * @Author jzc
     * @Description //TODO 粮仓配置编辑跳转
     * @Date 11:06 2019/2/28
     * @Param [view, request, post, buttonType]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequiresPermissions("system:invest:edit")
    @RequestMapping(value = "bonusConfigEdit")
    public ModelAndView bonusConfigEdit(ModelAndView view, HttpServletRequest request, TInvestMemberMoneyConfig config, String buttonType) {
        config=this.itInvestMemberMoneyConfigBusiSV.selectById(config.getId());
        TInvestMemberMoneyConfigVO vo = new TInvestMemberMoneyConfigVO();
        BeanUtils.copyProperties(config, vo);
        //获取planConfigMap
        Map<String, TInvestPlanConfig> configMap = this.itInvestPlanConfigBusiSV.queryMap();
        TInvestPlanConfig tInvestPlanConfig = configMap.get(vo.getPlanId());
        vo.setPlanName(tInvestPlanConfig!=null?tInvestPlanConfig.getPlanName():"");
        vo.setMemberLevelDesc(DictUtils.getDictLabel(String.valueOf(vo.getLevelId()),"level",""));
        view.addObject("config",vo);
        view.setViewName("wheat/planConfig/planBonusConfigEdit");
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
    @RequiresPermissions("system:invest:edit")
    @RequestMapping(value = "bonusConfigSave")
    public ModelAndView bonusConfigSave(ModelAndView mav, HttpServletRequest request, TInvestMemberMoneyConfig config) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH + "/invest/plan/edit?planId="+config.getPlanId());
        this.itInvestMemberMoneyConfigBusiSV.update(config);
        PageResult.setPrompt(map,"操作成功", "success");
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/invest/planConfig/bonusConfiglist"), map);
    }
}
