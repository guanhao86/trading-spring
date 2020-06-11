package com.spring.free.controller.api;


import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.spring.fee.model.TableMessage;
import com.spring.fee.service.ITableMessageBusiSV;
import com.spring.free.common.domain.AccessResponse;
import com.spring.free.config.TokenUtil;
import com.spring.free.vo.QueryReqVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 留言接口
 */
@RequestMapping("/api/message")
@Controller
@Slf4j
public class RestMessageController {

    @Autowired
    ITableMessageBusiSV iTableMessageBusiSV;

    /**
     * 留言列表
     */
    @RequestMapping(value = "/getList")
    public @ResponseBody
    AccessResponse getList(@RequestBody QueryReqVO queryReqVO, HttpServletRequest request, HttpServletResponse response){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        log.info("留言列表:{}", JSON.toJSONString(queryReqVO));
        //返回体
        PageInfo<TableMessage> pageInfo ;
        try {
            String memberId = TokenUtil.getUserId(request);
            TableMessage tableMessage = new TableMessage();
            tableMessage.setMemberId(memberId);
            pageInfo = this.iTableMessageBusiSV.queryListPage(tableMessage, queryReqVO.getPageNum(), queryReqVO.getPageSize(), null);

        }catch (Exception e) {
            return AccessResponse.builder().data(null).success(true).rspcode(ResponseConstants.ResponseCode.FAIL).message(e.getMessage()).build();
        }
        stopWatch.stop();
        System.out.println("耗时：" + stopWatch.getTotalTimeSeconds());
        return AccessResponse.builder().data(pageInfo).success(true).rspcode(ResponseConstants.ResponseCode.SUCCESS).message("服务端处理请求成功。").build();
    }

    /**
     * 留言创建
     */
    @RequestMapping(value = "/create")
    public @ResponseBody
    AccessResponse create(@RequestBody TableMessage tableMessage, HttpServletRequest request, HttpServletResponse response){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        log.info("留言创建:{}", JSON.toJSONString(tableMessage));
        //返回体
        try {
            String memberId = TokenUtil.getUserId(request);
            tableMessage.setMemberId(memberId);
            this.iTableMessageBusiSV.insert(tableMessage);

        }catch (Exception e) {
            return AccessResponse.builder().data(null).success(true).rspcode(ResponseConstants.ResponseCode.FAIL).message(e.getMessage()).build();
        }
        stopWatch.stop();
        System.out.println("耗时：" + stopWatch.getTotalTimeSeconds());
        return AccessResponse.builder().data(tableMessage).success(true).rspcode(ResponseConstants.ResponseCode.SUCCESS).message("服务端处理请求成功。").build();
    }

}
