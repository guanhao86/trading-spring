package com.spring.free.controller.api;


import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.spring.fee.constants.InvestConstants;
import com.spring.fee.model.*;
import com.spring.fee.service.*;
import com.spring.free.common.domain.AccessResponse;
import com.spring.free.config.TokenUtil;
import com.spring.free.domain.UserInfo;
import com.spring.free.util.constraints.Global;
import com.spring.free.util.exception.ExceptionCodeEnum;
import com.spring.free.util.exception.ServiceException;
import com.spring.free.util.md5.Md5Util;
import com.spring.free.utils.principal.BaseGetPrincipal;
import com.spring.free.vo.GoodsRspVO;
import com.spring.free.vo.QueryReqVO;
import com.spring.free.vo.TransferVO;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 账户变更明细接口
 */
@RequestMapping("/api/account")
@Controller
@Slf4j
public class RestAccountDetailController {

    @Autowired
    ITableMemberBusiSV iTableMemberBusiSV;

    @Autowired
    IMemberAccountDetailBusiSV iMemberAccountDetailBusiSV;

    @Autowired
    ITableCashOutBusiSV iTableCashOutBusiSV;

    @Autowired
    ITableInvestBusiSV iTableInvestBusiSV;

    /**
     * 账户变更明细列表
     */
    @RequestMapping(value = "/getList")
    public @ResponseBody
    AccessResponse getList(@RequestBody QueryReqVO queryReqVO, HttpServletRequest request, HttpServletResponse response){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        log.info("账户变更明细列表:{}");
        //返回体
        PageInfo<TableMemberAccountDetail> pageInfo = new PageInfo<>();
        try {
            TableMemberAccountDetail query = new TableMemberAccountDetail();
            BeanUtils.copyProperties(queryReqVO, query);
            String memberId = TokenUtil.getUserId(request);

            query.setMemberId(memberId);
            if (null != queryReqVO.getBonusType())
                query.setAccountType(queryReqVO.getBonusType());
            pageInfo = this.iMemberAccountDetailBusiSV.queryListPage(query, queryReqVO.getPageNum(), queryReqVO.getPageSize(), null);

        }catch (Exception e) {
            return AccessResponse.builder().data(null).success(true).rspcode(ResponseConstants.ResponseCode.FAIL).message(e.getMessage()).build();
        }
        stopWatch.stop();
        System.out.println("耗时：" + stopWatch.getTotalTimeSeconds());
        return AccessResponse.builder().data(pageInfo).success(true).rspcode(ResponseConstants.ResponseCode.SUCCESS).message("服务端处理请求成功。").build();
    }

    /**
     * 奖金转现金
     */
    @RequestMapping(value = "/transferInner")
    public @ResponseBody
    AccessResponse transferInner(@RequestBody QueryReqVO queryReqVO, HttpServletRequest request, HttpServletResponse response){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        log.info("奖金转现金:{}");
        TableMemberAccountDetail tableMemberAccountDetail;
        //返回体
        try {
            String memberId = TokenUtil.getUserId(request);
            tableMemberAccountDetail = this.iMemberAccountDetailBusiSV.transferInner(memberId, String.valueOf(queryReqVO.getAmount()), null);
        }catch (Exception e) {
            return AccessResponse.builder().data(null).success(true).rspcode(ResponseConstants.ResponseCode.FAIL).message(e.getMessage()).build();
        }
        stopWatch.stop();
        System.out.println("耗时：" + stopWatch.getTotalTimeSeconds());
        return AccessResponse.builder().data(tableMemberAccountDetail).success(true).rspcode(ResponseConstants.ResponseCode.SUCCESS).message("服务端处理请求成功。").build();
    }

    /**
     * 提现申请
     */
    @RequestMapping(value = "/cashOut")
    public @ResponseBody
    AccessResponse cashOut(@RequestBody TableCashOut tableCashOut, HttpServletRequest request, HttpServletResponse response){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        log.info("提现申请:{}");
        //返回体
        try {
            String memberId = TokenUtil.getUserId(request);
            tableCashOut.setMemberId(memberId);
            this.iTableCashOutBusiSV.apply(tableCashOut);
        }catch (Exception e) {
            return AccessResponse.builder().data(null).success(true).rspcode(ResponseConstants.ResponseCode.FAIL).message(e.getMessage()).build();
        }
        stopWatch.stop();
        System.out.println("耗时：" + stopWatch.getTotalTimeSeconds());
        return AccessResponse.builder().data(tableCashOut).success(true).rspcode(ResponseConstants.ResponseCode.SUCCESS).message("服务端处理请求成功。").build();
    }

    /**
     * 提现申请记录
     */
    @RequestMapping(value = "/getCashOutList")
    public @ResponseBody
    AccessResponse getCashOutList(@RequestBody QueryReqVO queryReqVO, HttpServletRequest request, HttpServletResponse response){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        log.info("提现申请记录:{}");
        PageInfo<TableCashOut> pageInfo;
        //返回体
        try {
            TableCashOut tableCashOut = new TableCashOut();
            String memberId = TokenUtil.getUserId(request);
            tableCashOut.setMemberId(memberId);
            pageInfo = this.iTableCashOutBusiSV.queryListPage(tableCashOut, queryReqVO.getPageNum(), queryReqVO.getPageSize(), null);
        }catch (Exception e) {
            return AccessResponse.builder().data(null).success(true).rspcode(ResponseConstants.ResponseCode.FAIL).message(e.getMessage()).build();
        }
        stopWatch.stop();
        System.out.println("耗时：" + stopWatch.getTotalTimeSeconds());
        return AccessResponse.builder().data(pageInfo).success(true).rspcode(ResponseConstants.ResponseCode.SUCCESS).message("服务端处理请求成功。").build();
    }

    /**
     * 转账
     */
    @RequestMapping(value = "/transfer")
    public @ResponseBody
    AccessResponse transfer(@RequestBody TransferVO transferVO, HttpServletRequest request, HttpServletResponse response){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        log.info("转账:{}", transferVO);
        //返回体
        try {
            String memberId = TokenUtil.getUserId(request);
            TableMember tableMember = this.iTableMemberBusiSV.selectByMemberId(memberId);
            if(!tableMember.getPassword().equals(Md5Util.md5Hex(transferVO.getPassword()))) {
                return AccessResponse.builder().data(null).success(true).rspcode(ResponseConstants.ResponseCode.FAIL).message("密码错误").build();
            }
            this.iMemberAccountDetailBusiSV.transfer(memberId, transferVO.getMemberId(), transferVO.getAmount(), transferVO.getRemark(), transferVO.getTransferType());

        }catch (Exception e) {
            return AccessResponse.builder().data(null).success(true).rspcode(ResponseConstants.ResponseCode.FAIL).message(e.getMessage()).build();
        }
        stopWatch.stop();
        System.out.println("耗时：" + stopWatch.getTotalTimeSeconds());
        return AccessResponse.builder().data(transferVO).success(true).rspcode(ResponseConstants.ResponseCode.SUCCESS).message("服务端处理请求成功。").build();
    }

    /**
     * 充值
     */
    @RequestMapping(value = "/invest")
    public @ResponseBody
    AccessResponse invest(@RequestBody TableInvest tableInvest, HttpServletRequest request, HttpServletResponse response){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        log.info("充值:{}", JSON.toJSONString(tableInvest));
        //返回体
        try {
            String memberId = TokenUtil.getUserId(request);
            tableInvest.setMemberId(memberId);
            tableInvest.setType(InvestConstants.InvestType.MEMBER);
            this.iTableInvestBusiSV.insert(tableInvest);
        }catch (Exception e) {
            return AccessResponse.builder().data(null).success(true).rspcode(ResponseConstants.ResponseCode.FAIL).message(e.getMessage()).build();
        }
        stopWatch.stop();
        System.out.println("耗时：" + stopWatch.getTotalTimeSeconds());
        return AccessResponse.builder().data(tableInvest).success(true).rspcode(ResponseConstants.ResponseCode.SUCCESS).message("服务端处理请求成功。").build();
    }

}
