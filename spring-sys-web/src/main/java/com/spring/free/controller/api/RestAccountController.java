package com.spring.free.controller.api;

import com.github.pagehelper.PageInfo;
import com.spring.fee.model.*;
import com.spring.fee.service.*;
import com.spring.free.common.domain.AccessResponse;
import com.spring.free.vo.AcctDetailReqVO;
import com.spring.free.vo.PlanReqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 帐户服务
 */
@RequestMapping("/api/invest/account")
@Controller
public class RestAccountController {

    @Autowired
    ITWheatAccountDetailBusiSV itWheatAccountDetailBusiSV;
    @Autowired
    ITWheatAccountBusiSV itWheatAccountBusiSV;

    /**
     * @Author guanhao
     * 会员账户变更明细列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getAcctDetailList")
    public @ResponseBody
    AccessResponse getAcctDetailList(@RequestBody AcctDetailReqVO acctDetailReqVO, HttpServletRequest request, HttpServletResponse response){
        try {
            TWheatAccountDetailDZ tWheatAccountDetail = new TWheatAccountDetailDZ();
            tWheatAccountDetail.setMemberId(acctDetailReqVO.getMemberId());
            PageInfo<TWheatAccountDetail> tWheatAccountDetailPageInfo = this.itWheatAccountDetailBusiSV.queryPage(tWheatAccountDetail, acctDetailReqVO.getPageNum(), acctDetailReqVO.getPageSize(), null);
            return AccessResponse.builder().data(tWheatAccountDetailPageInfo).success(true).rspcode(200).message("服务端处理请求成功。").build();
        }catch (Exception e) {
            return AccessResponse.builder().data(null).success(false).rspcode(200).message(e.getMessage()).build();
        }

    }

    /**
     * @Author guanhao
     * 会员账户信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getAcctInfo/{memberId}")
    public @ResponseBody
    AccessResponse getAcctInfo(@PathVariable String memberId, HttpServletRequest request, HttpServletResponse response){
        try {
            TWheatAccount tWheatAccount = this.itWheatAccountBusiSV.selectByMember(memberId);
            if (tWheatAccount == null) {
                return AccessResponse.builder().data(null).success(false).rspcode(200).message("未查询到帐户信息").build();
            }
            return AccessResponse.builder().data(tWheatAccount).success(true).rspcode(200).message("服务端处理请求成功。").build();
        }catch (Exception e) {
            return AccessResponse.builder().data(null).success(false).rspcode(200).message(e.getMessage()).build();
        }

    }

}
