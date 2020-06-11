package com.spring.free.controller.api;


import com.github.pagehelper.PageInfo;
import com.spring.fee.model.TableBonusDetail;
import com.spring.fee.model.TableGoods;
import com.spring.fee.model.TableMember;
import com.spring.fee.service.ITableBonusDetailBusiSV;
import com.spring.fee.service.ITableGoodsBusiSV;
import com.spring.fee.service.ITableMemberBusiSV;
import com.spring.free.common.domain.AccessResponse;
import com.spring.free.config.CommonUtils;
import com.spring.free.config.PassToken;
import com.spring.free.config.TokenUtil;
import com.spring.free.domain.UserInfo;
import com.spring.free.utils.principal.BaseGetPrincipal;
import com.spring.free.vo.GoodsRspVO;
import com.spring.free.vo.GoodsVO;
import com.spring.free.vo.QueryReqVO;
import lombok.extern.slf4j.Slf4j;
import org.redisson.misc.Hash;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 奖金明细接口
 */
@RequestMapping("/api/bonus")
@Controller
@Slf4j
public class RestBonusDetailController {

    @Autowired
    ITableMemberBusiSV iTableMemberBusiSV;

    @Autowired
    ITableBonusDetailBusiSV iTableBonusDetailBusiSV;

    /**
     * 奖金列表
     */
    @RequestMapping(value = "/getList")
    public @ResponseBody
    AccessResponse getList(@RequestBody QueryReqVO queryReqVO, HttpServletRequest request, HttpServletResponse response){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        log.info("奖金列表:{}");
        //返回体
        PageInfo<TableBonusDetail> pageInfo = new PageInfo<>();
        try {
            TableBonusDetail memberAccountDetail = new TableBonusDetail();
            BeanUtils.copyProperties(queryReqVO, memberAccountDetail);
            String memberId = TokenUtil.getUserId(request);

            memberAccountDetail.setMemberId(memberId);
            if (!StringUtils.isEmpty(queryReqVO.getCloseState())) {
                memberAccountDetail.setCloseState(Integer.parseInt(queryReqVO.getCloseState()));
            }
            Map<String, Object> map = new HashMap();
            map.put("bonusIdNotIn", Arrays.asList(6,7));
            pageInfo = this.iTableBonusDetailBusiSV.queryListPage(memberAccountDetail, queryReqVO.getPageNum(), queryReqVO.getPageSize(), map);

        }catch (Exception e) {
            return AccessResponse.builder().data(null).success(true).rspcode(ResponseConstants.ResponseCode.FAIL).message(e.getMessage()).build();
        }
        stopWatch.stop();
        System.out.println("耗时：" + stopWatch.getTotalTimeSeconds());
        return AccessResponse.builder().data(pageInfo).success(true).rspcode(ResponseConstants.ResponseCode.SUCCESS).message("服务端处理请求成功。").build();
    }

}
