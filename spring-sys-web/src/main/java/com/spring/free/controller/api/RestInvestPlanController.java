package com.spring.free.controller.api;

import com.github.pagehelper.PageInfo;
import com.spring.fee.constants.InvestConstants;
import com.spring.fee.model.*;
import com.spring.fee.service.*;
import com.spring.free.common.domain.AccessResponse;
import com.spring.free.util.DateUtils;
import com.spring.free.vo.PlanReqVO;
import net.bytebuddy.asm.Advice;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 粮仓计划
 */
@RequestMapping("/api/invest/plan")
@Controller
public class RestInvestPlanController {

    @Autowired
    ITInvestPlanConfigBusiSV itInvestPlanConfigBusiSV;
    @Autowired
    ITInvestQueueBusiSV itInvestQueueBusiSV;
    @Autowired
    ITInvestPlanMainBusiSV itInvestPlanMainBusiSV;
    @Autowired
    ITWheatAccountDetailBusiSV itWheatAccountDetailBusiSV;
    @Autowired
    ITWheatAccountBusiSV itWheatAccountBusiSV;
    @Autowired
    ITInvestMemberConfigBusiSV itInvestMemberConfigBusiSV;
    @Autowired
    ITInvestPlanBonusBusiSV itInvestPlanBonusBusiSV;
    @Autowired
    ITWheatMemberBusiSV itWheatMemberBusiSV;
    @Autowired
    ITInvestPlanDetailBusiSV itInvestPlanDetailBusiSV;
    @Autowired
    ITInvestMemberUpConfigBusiSV itInvestMemberUpConfigBusiSV;

    /**
     * @Author guanhao
     * 获取所有粮仓配置项
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getPlanConfigList")
    public @ResponseBody
    AccessResponse getPlanConfigList(HttpServletRequest request, HttpServletResponse response){
        try {
            System.out.println(2222);
            List<TInvestPlanConfig> tInvestPlanConfigList = this.itInvestPlanConfigBusiSV.queryList(new TInvestPlanConfig());
            return AccessResponse.builder().data(tInvestPlanConfigList).success(true).rspcode(200).message("服务端处理请求成功。").build();
        }catch (Exception e) {
            return AccessResponse.builder().data(getFail(e.getMessage())).success(false).rspcode(200).message(e.getMessage()).build();
        }

    }

    /**
     * @Author guanhao
     * 购买粮仓 / 复投
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/buyInvest")
    public @ResponseBody
    AccessResponse buyInvest(@RequestBody TInvestQueue tInvestQueue, HttpServletRequest request, HttpServletResponse response){
        try {
            TInvestQueueDZ tInvestQueueResp = this.itInvestQueueBusiSV.createQueueV2AndRun(tInvestQueue);
            //先进行升级
            if (!StringUtils.isEmpty(tInvestQueue.getMemberId())) {
                //购买升级
                Map<String, TInvestMemberUpConfig> upMap = this.itInvestMemberUpConfigBusiSV.queryMap();
                TWheatMemberTree tWheatMemberTree = new TWheatMemberTree();
                TWheatMember tWheatMember = this.itWheatMemberBusiSV.selectByMemberId(tInvestQueue.getMemberId());
                if (tWheatMember != null) {
                    BeanUtils.copyProperties(tWheatMember, tWheatMemberTree);
                    int upLevel = Integer.parseInt(tWheatMemberTree.getLevel())+1;
                    TInvestMemberUpConfig tInvestMemberUpConfig = upMap.get(String.valueOf(upLevel));
                    if (InvestConstants.MemberConfig.UP_LEVEL_TYPE_BUY.equals(tInvestMemberUpConfig.getUpType())) {
                        //购买升级
                        this.itWheatMemberBusiSV.upLevelFromLow2HighV2(tInvestQueue.getMemberId());
                    }
                }



            }

            //异步计算奖金
            if (tInvestQueueResp != null && tInvestQueueResp.getBonusTmpId() > 0) {
                new Thread(() -> this.itInvestPlanBonusBusiSV.calculateStartV2(tInvestQueueResp.getBonusTmpId())).start();
            }
            return AccessResponse.builder().data(tInvestQueueResp).success(true).rspcode(200).message("服务端处理请求成功。").build();
        }catch (Exception e) {
            return AccessResponse.builder().data(getFail(e.getMessage())).success(false).rspcode(200).message(e.getMessage()).build();
        }

    }

    /**
     * @Author guanhao
     * 会员可购买粮仓计划列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getPlanList4Member/{memberId}")
    public @ResponseBody
    AccessResponse getPlanList4Member(@PathVariable String memberId, HttpServletRequest request, HttpServletResponse response){
        try {
            TWheatMember tWheatMember = new TWheatMember();
            tWheatMember.setMemberId(memberId);
            List<TInvestPlanConfig> tInvestPlanConfigList = itInvestPlanMainBusiSV.getCanBuyPlanConfigListV2(tWheatMember);
            return AccessResponse.builder().data(tInvestPlanConfigList).success(true).rspcode(200).message("服务端处理请求成功。").build();
        }catch (Exception e) {
            return AccessResponse.builder().data(getFail(e.getMessage())).success(false).rspcode(200).message(e.getMessage()).build();
        }

    }

    /**
     * @Author guanhao
     * 获取粮仓计划列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getPlanAndDetailList")
    public @ResponseBody
    AccessResponse getPlanAndDetailList(@RequestBody PlanReqVO planReqVO, HttpServletRequest request, HttpServletResponse response){
        try {
            TInvestPlanMain param = new TInvestPlanMain();
            param.setMemberId(planReqVO.getMemberId());
            param.setStatus(param.getStatus());
            PageInfo<TInvestPlanMainDZ>  page = this.itInvestPlanMainBusiSV.queryMainAndDetailList(param, planReqVO.getPageNum(), planReqVO.getPageSize(), null);
            return AccessResponse.builder().data(page).success(true).rspcode(200).message("服务端处理请求成功。").build();
        }catch (Exception e) {
            return AccessResponse.builder().data(getFail(e.getMessage())).success(false).rspcode(200).message(e.getMessage()).build();
        }
    }

    /**
     * @Author guanhao
     * 获取粮仓计划列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getPlanList")
    public @ResponseBody
    AccessResponse getPlanList(@RequestBody PlanReqVO planReqVO, HttpServletRequest request, HttpServletResponse response){
        try {
            TInvestPlanMain param = new TInvestPlanMain();
            param.setMemberId(planReqVO.getMemberId());
            param.setStatus(param.getStatus());
            PageInfo<TInvestPlanMain>  page = this.itInvestPlanMainBusiSV.queryList(param, planReqVO.getPageNum(), planReqVO.getPageSize(), null);
            return AccessResponse.builder().data(page).success(true).rspcode(200).message("服务端处理请求成功。").build();
        }catch (Exception e) {
            return AccessResponse.builder().data(getFail(e.getMessage())).success(false).rspcode(200).message(e.getMessage()).build();
        }
    }

    /**
     * @Author guanhao
     * 获取是否自动续仓标志位
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getAutoBuyFlag/{memberId}")
    public @ResponseBody
    AccessResponse getAutoBuyFlag(@PathVariable String memberId, HttpServletRequest request, HttpServletResponse response){
        try {
            String autoFlag = itInvestMemberConfigBusiSV.selectByMemberId(memberId, InvestConstants.MemberConfig.CONFIG_CODE_AUTO_SECOND);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("autoFlag", autoFlag == null ? "0" : autoFlag);
            return AccessResponse.builder().data(jsonObject).success(true).rspcode(200).message("服务端处理请求成功。").build();
        }catch (Exception e) {
            return AccessResponse.builder().data(getFail(e.getMessage())).success(false).rspcode(200).message(e.getMessage()).build();
        }

    }

    /**
     * @Author guanhao
     * 获取是否自动续仓标志位
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/modifyAutoBuyFlag/{memberId}")
    public @ResponseBody
    AccessResponse modifyAutoBuyFlag(@PathVariable String memberId, @RequestParam String autoFlag, HttpServletRequest request, HttpServletResponse response){
        try {
            TInvestMemberConfig tInvestMemberConfig = new TInvestMemberConfig();
            tInvestMemberConfig.setMemberId(memberId);
            tInvestMemberConfig.setConfigCode( InvestConstants.MemberConfig.CONFIG_CODE_AUTO_SECOND);
            tInvestMemberConfig.setConfigValue(autoFlag);
            if (null != itInvestMemberConfigBusiSV.modify(tInvestMemberConfig)){
                return AccessResponse.builder().data(null).success(true).rspcode(200).message("服务端处理请求成功。").build();
            }else{
                return AccessResponse.builder().data(null).success(false).rspcode(200).message("更新失败。").build();
            }


        }catch (Exception e) {
            return AccessResponse.builder().data(getFail(e.getMessage())).success(false).rspcode(200).message(e.getMessage()).build();
        }

    }

    /**
     * @Author guanhao
     * 获取粮仓收益明细
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getBonusList")
    public @ResponseBody
    AccessResponse getBonusList(@RequestBody PlanReqVO planReqVO, HttpServletRequest request, HttpServletResponse response){
        try {
            TInvestPlanBonus param = new TInvestPlanBonus();
            param.setMemberId(planReqVO.getMemberId());
            PageInfo<TInvestPlanBonus>  page = this.itInvestPlanBonusBusiSV.queryPage(param, planReqVO.getPageNum(), planReqVO.getPageSize(), null);
            return AccessResponse.builder().data(page).success(true).rspcode(200).message("服务端处理请求成功。").build();
        }catch (Exception e) {
            return AccessResponse.builder().data(getFail(e.getMessage())).success(false).rspcode(200).message(e.getMessage()).build();
        }

    }

    /**
     * @Author guanhao
     * 粮仓统计：已购买数量，执行中数量，所有粮仓数量
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getMemberPlanCount/{memberId}")
    public @ResponseBody
    AccessResponse getMemberPlanCount(@PathVariable String memberId,  HttpServletRequest request, HttpServletResponse response){
        try {
            TInvestPlanMainStatistic tInvestPlanMainStatistic = this.itInvestPlanMainBusiSV.statisticPlanCount(memberId);
            return AccessResponse.builder().data(tInvestPlanMainStatistic).success(true).rspcode(200).message("服务端处理请求成功。").build();
        }catch (Exception e) {
            return AccessResponse.builder().data(getFail(e.getMessage())).success(false).rspcode(200).message(e.getMessage()).build();
        }

    }

    /**
     * @Author guanhao
     * 统计收益：支持全部收益，历史收益  会员ID必填
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getMemberEarnings/{memberId}")
    public @ResponseBody
    AccessResponse getMemberEarnings(@PathVariable String memberId, HttpServletRequest request, HttpServletResponse response){
        try {
            JSONObject jsonObject= new JSONObject();
            List<String> memberList = new ArrayList<>();
            memberList.add(memberId);
            TInvestPlanBonusDZ tInvestPlanBonusDZ = new TInvestPlanBonusDZ();
            tInvestPlanBonusDZ.setMemberIdList(memberList);
            List<TInvestPlanBonusDZ> tInvestPlanBonusDZS = this.itInvestPlanBonusBusiSV.statisticEarnings(tInvestPlanBonusDZ);
            JSONObject jsonObject1= new JSONObject();
            for (TInvestPlanBonusDZ a : tInvestPlanBonusDZS) {
                if("1".equals(a.getType())){
                    jsonObject1.put("bonus", a.getAmount());
                }
                if("2".equals(a.getType())){
                    jsonObject1.put("interest", a.getAmount());
                }
                JSONArray array = new JSONArray();
                array.add(jsonObject1);
            }
            jsonObject.put("all_earnings", jsonObject1);
            tInvestPlanBonusDZ.setPaymentday(DateUtils.getYYYYMMDD(DateUtils.getLastDate()));
            List<TInvestPlanBonusDZ> tInvestPlanBonusDZS1 = this.itInvestPlanBonusBusiSV.statisticEarnings(tInvestPlanBonusDZ);
            JSONObject jsonObject2= new JSONObject();
            for (TInvestPlanBonusDZ a : tInvestPlanBonusDZS1) {
                if("1".equals(a.getType())){
                    jsonObject2.put("bonus", a.getAmount());
                }
                if("2".equals(a.getType())){
                    jsonObject2.put("interest", a.getAmount());
                }
                JSONArray array = new JSONArray();
                array.add(jsonObject2);
            }
            jsonObject.put("yesterday_earnings", jsonObject2);
            return AccessResponse.builder().data(jsonObject).success(true).rspcode(200).message("服务端处理请求成功。").build();
        }catch (Exception e) {
            return AccessResponse.builder().data(getFail(e.getMessage())).success(false).rspcode(200).message(e.getMessage()).build();
        }

    }

    /**
     * @Author guanhao
     * 统计团队业绩：今日业绩，累计业绩，自动复投人数，复投率
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/statisticTeamInvest/{memberId}")
    public @ResponseBody
    AccessResponse statisticTeamInvest(@PathVariable String memberId, HttpServletRequest request, HttpServletResponse response){
        try {
            JSONObject jsonObject= new JSONObject();
            List<String> tWheatMemberList = this.itWheatMemberBusiSV.queryAllChildMemberIdList(memberId);
            //tWheatMemberList.add(memberId);

            //今日业绩
            long todayAmount = this.itInvestPlanDetailBusiSV.statisticPlanAmount(tWheatMemberList, "1");

            //累计业绩
            long totalAmount =  this.itInvestPlanDetailBusiSV.statisticPlanAmount(tWheatMemberList, "0");

            //自动复投人数
            int autoCount = this.itInvestMemberConfigBusiSV.selectByMemberIdList(tWheatMemberList, InvestConstants.MemberConfig.CONFIG_CODE_AUTO_SECOND);

            //复投率
            TInvestPlanMainStatistic tInvestPlanMainStatistic = new TInvestPlanMainStatistic();
            tInvestPlanMainStatistic.setMemberList(tWheatMemberList);
            tInvestPlanMainStatistic = this.itInvestPlanMainBusiSV.statisticPlanCount2(tInvestPlanMainStatistic);
            DecimalFormat df = new DecimalFormat("#.00");
            String per = "0%";
            if (tInvestPlanMainStatistic.getAllCount() != 0 && tInvestPlanMainStatistic.getAppendCount()!=0) {
                double tmp = (double)tInvestPlanMainStatistic.getAppendCount() / (double)tInvestPlanMainStatistic.getAllCount() * 100;
                per = df.format(tmp) + "%";
            }

            List<String> memberList = new ArrayList<>();
            memberList.add(memberId);
            TInvestPlanBonusDZ tInvestPlanBonusDZ = new TInvestPlanBonusDZ();
            tInvestPlanBonusDZ.setMemberIdList(memberList);
            List<TInvestPlanBonusDZ> tInvestPlanBonusDZS = this.itInvestPlanBonusBusiSV.statisticEarnings(tInvestPlanBonusDZ);

            //会员总收益
            long totalBonus = 0l;
            if (tInvestPlanBonusDZS != null) {
                for (TInvestPlanBonusDZ tInvestPlanBonusDZ1 : tInvestPlanBonusDZS) {
                    totalBonus += tInvestPlanBonusDZ1.getAmount();
                }
            }


            jsonObject.put("todayAmount", todayAmount);
            jsonObject.put("totalAmount", totalAmount);
            jsonObject.put("autoCount", autoCount);
            jsonObject.put("per", per);
            jsonObject.put("totalBonus", totalBonus);

            return AccessResponse.builder().data(jsonObject).success(true).rspcode(200).message("服务端处理请求成功。").build();
        }catch (Exception e) {
            return AccessResponse.builder().data(getFail(e.getMessage())).success(false).rspcode(200).message(e.getMessage()).build();
        }

    }

    /**
     * @Author guanhao
     * 排行榜：富豪榜，大户榜，江湖榜
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/rankingList")
    public @ResponseBody
    AccessResponse statisticTeamInvest(HttpServletRequest request, HttpServletResponse response){
        try {
            JSONObject jsonObject= new JSONObject();

            //大户榜（购买粮仓最多）
            List<TInvestPlanMainStatistic> buyMaxList = this.itInvestPlanMainBusiSV.statisticPlanCountGroupByMemeber(null);
            //富豪榜（粮仓奖金最多）
            List<TInvestPlanBonusDZ> bonusMaxList = this.itInvestPlanBonusBusiSV.statisticGroupByMemberId();
            //江湖榜（团队人数最多）
            TWheatMemberGroupDZ teamMax = this.itWheatMemberBusiSV.hasMaxChild();

            TInvestPlanMainStatistic buyMax = new TInvestPlanMainStatistic();
            if (!CollectionUtils.isEmpty(buyMaxList)) {
                buyMax = buyMaxList.get(0);
            }

            TInvestPlanBonusDZ bonusMax = new TInvestPlanBonusDZ();
            if (!CollectionUtils.isEmpty(bonusMaxList)) {
                bonusMax = bonusMaxList.get(0);
            }

            JSONObject jsonBuyMax = new JSONObject();
            jsonBuyMax.put("memberId", buyMax.getMemberId());
            jsonBuyMax.put("allCount", buyMax.getAllCount());
            jsonBuyMax.put("phone", buyMax.getPhone());
            jsonBuyMax.put("runningCount", buyMax.getRunningCount());

            JSONObject jsonbonusMax = new JSONObject();
            jsonbonusMax.put("memberId", bonusMax.getMemberId());
            jsonbonusMax.put("phone", bonusMax.getPhone());
            jsonbonusMax.put("amount", bonusMax.getAmount());

            JSONObject jsonTeamMax = new JSONObject();
            jsonTeamMax.put("memberId", teamMax.getMemberId());
            jsonTeamMax.put("phone", teamMax.getPhone());
            jsonTeamMax.put("allCount", teamMax.getCount());
            jsonTeamMax.put("firstChildCount", teamMax.getFistChildCount());

            jsonObject.put("buyMax", jsonBuyMax);
            jsonObject.put("bonusMax", jsonbonusMax);
            jsonObject.put("teamMax", jsonTeamMax);

            return AccessResponse.builder().data(jsonObject).success(true).rspcode(200).message("服务端处理请求成功。").build();
        }catch (Exception e) {
            return AccessResponse.builder().data(getFail(e.getMessage())).success(false).rspcode(200).message(e.getMessage()).build();
        }

    }


    public JSONObject getFail(String desc){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("desc", desc);
        jsonObject.put("result", "01");
        return jsonObject;
    }

}
