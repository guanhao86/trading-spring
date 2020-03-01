package com.spring.free.controller.test;

import com.alibaba.fastjson.JSON;
import com.spring.fee.dao.mapper.JcMemberMapper;
import com.spring.fee.dao.mapper.TInvestPlanBonusMapperDZ;
import com.spring.fee.model.TInvestPlanBonusDZ;
import com.spring.fee.model.TInvestQueue;
import com.spring.fee.model.TWheatMember;
import com.spring.fee.model.TWheatMemberTree;
import com.spring.fee.service.*;
import com.spring.free.util.constraints.Global;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(Global.ADMIN_PATH + "/test/")
public class TestController {

    @Autowired
    ITWheatMemberBusiSV itWheatMemberBusiSVr;

    @Autowired
    ITWheatAccountBusiSV itWheatAccountBusiSV;

    @Autowired
    TInvestPlanBonusMapperDZ tInvestPlanBonusMapper;

    @Autowired
    ITInvestQueueBusiSV itInvestQueueBusiSV;

    @Autowired
    ITInvestPlanMainBusiSV itInvestPlanMainBusiSV;

    @Autowired
    ITInvestPlanBonusBusiSV itInvestPlanBonusBusiSV;

    @RequestMapping("getU")
    public Object getU(@RequestParam String id){
        log.info("system-env: {}", "获取用户");
        //return JSONObject.toJSON(jcMemberMapper.selectByPrimaryKey(Integer.parseInt(id)));
        //PageInfo<TInvestQueue> arr = itInvestQueueBusiSV.queryListPage(new TInvestQueue(), 1, 1, null);
//        List<TInvestPlanBonusDZ> arr = tInvestPlanBonusMapper.selectGroupByMemberId();
//        TWheatMemberTree tWheatMemberTree = new TWheatMemberTree();
//               BeanUtils.copyProperties(this.itWheatMemberBusiSVr.selectByMemberId("1"), tWheatMemberTree);
//        itWheatMemberBusiSVr.queryAllChildTree(tWheatMemberTree);
//        //TWheatMemberTree tWheatMemberTree1 = itWheatMemberBusiSVr.getChildTree("9", tWheatMemberTree);
//
//        long aaa = itWheatAccountBusiSV.getAccountAllChildMaxFreeingSum(id, tWheatMemberTree, itWheatAccountBusiSV.getAllAccount2Map());

        //this.itWheatMemberBusiSVr.upLevelV2ALL();

        //Map<String, Double> map = this.itInvestPlanBonusBusiSV.getMemberBonusEarningRate();
        //this.itInvestPlanBonusBusiSV.calculateStartV2();
        log.info("================================1");
        new Thread(()->doReplace()).start();
        log.info("================================2");
        return "hello";
    }

    public void doReplace(){
        this.itInvestPlanMainBusiSV.runSettleV2();
        //异步处理的业务
    }

    @RequestMapping("insert")
    public Object insert(@RequestParam String id){
        log.info("insert: {}", "插入");
        TInvestQueue tInvestQueue = new TInvestQueue();
        tInvestQueue.setMemberId("1");
        tInvestQueue.setAmount(4200000L);
        tInvestQueue.setType("1");
        tInvestQueue.setPlanId("1");
        return itInvestQueueBusiSV.createQueue(tInvestQueue);
//        itInvestPlanMainBusiSV.createPlan();
//        return null;
    }


}
