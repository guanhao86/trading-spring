package com.spring.free.controller.api;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.spring.fee.model.*;
import com.spring.fee.service.ITableBonusDetailBusiSV;
import com.spring.fee.service.ITableMemberBusiSV;
import com.spring.fee.service.ITableMemberGoodsBusiSV;
import com.spring.fee.service.ITableOrderBusiSV;
import com.spring.free.common.domain.AccessResponse;
import com.spring.free.config.PassToken;
import com.spring.free.domain.QueryVO;
import com.spring.free.util.DateUtils;
import com.spring.free.utils.velocity.DictUtils;
import com.spring.free.vo.TreeVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequestMapping("/api/test")
@Controller
public class RestTest {

    @Autowired
    ITableMemberGoodsBusiSV iTableMemberGoodsBusiSV;

    @Autowired
    ITableBonusDetailBusiSV iTableBonusDetailBusiSV;

    @Autowired
    ITableOrderBusiSV iTableOrderBusiSV;
    @PassToken
    @RequestMapping(value = "/test")
    public @ResponseBody
    AccessResponse test(@RequestBody QueryVO queryVO, String image, HttpServletRequest request, HttpServletResponse response){

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

        PageInfo<TableMemberGoodsDZ> pageInfo = this.iTableMemberGoodsBusiSV.queryListPageDZ(tableMemberGoods, 1, 1000, null);



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

        return null;

    }


}
