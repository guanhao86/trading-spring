package com.spring.free.controller.api;


import com.github.pagehelper.PageInfo;
import com.spring.free.common.domain.AccessResponse;
import com.spring.free.common.domain.PageParamDTO;
import com.spring.free.domain.Notice;
import com.spring.free.service.NoticeService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/notice")
@Controller
public class RestNoticeController {

    @Autowired
    private NoticeService noticeService;


    /*
        顶部消息区
     */
    @RequestMapping(value = "/topNotice")
    public @ResponseBody
    AccessResponse topNotice(Notice notice,HttpServletRequest request, HttpServletResponse response){

        notice.setType(1);
        notice.setNoticeType(3);
        PageInfo<Notice> pageInfo = noticeService.pageList(notice,1,3);
        return AccessResponse.builder().data(pageInfo.getList()).success(true).rspcode(200).message("服务端处理请求成功。").build();
    }

    /*
        app消息
     */
    @RequestMapping(value = "/appNotice/{page}")
    public @ResponseBody
    AccessResponse appNotice(Notice notice, @PathVariable Integer page, PageParamDTO pageParamDTO , HttpServletRequest request, HttpServletResponse response){
        Integer pageSize = pageParamDTO.getPageSize() == null ? 20 : pageParamDTO.getPageSize();
        notice.setType(1);
        notice.setNoticeType(3);
        PageInfo<Notice> pageInfo = noticeService.pageList(notice,page,pageSize);
        return AccessResponse.builder().data(pageInfo).success(true).rspcode(200).message("服务端处理请求成功。").build();
    }
    /*
        粮仓公告
     */
    @RequestMapping(value = "/granaryNotice/{page}")
    public @ResponseBody
    AccessResponse granaryNotice(Notice notice, @PathVariable Integer page, PageParamDTO pageParamDTO , HttpServletRequest request, HttpServletResponse response){
        Integer pageSize = pageParamDTO.getPageSize() == null ? 20 : pageParamDTO.getPageSize();
        notice.setType(1);
        notice.setNoticeType(2);
        PageInfo<Notice> pageInfo = noticeService.pageList(notice,page,pageSize);
        return AccessResponse.builder().data(pageInfo.getList()).success(true).rspcode(200).message("服务端处理请求成功。").build();
    }

    /*
        理财公告
     */
    @RequestMapping(value = "/financingNotice/{page}")
    public @ResponseBody
    AccessResponse financingNotice(Notice notice, @PathVariable Integer page, PageParamDTO pageParamDTO , HttpServletRequest request, HttpServletResponse response){
        Integer pageSize = pageParamDTO.getPageSize() == null ? 20 : pageParamDTO.getPageSize();
        notice.setType(1);
        notice.setNoticeType(1);
        PageInfo<Notice> pageInfo = noticeService.pageList(notice,page,pageSize);
        return AccessResponse.builder().data(pageInfo).success(true).rspcode(200).message("服务端处理请求成功。").build();
    }

    /*
        首页顶部广告轮播图
     */
    @RequestMapping(value = "/topBanner/{page}")
    public @ResponseBody
    AccessResponse topBanner(Notice notice,@PathVariable Integer page, PageParamDTO pageParamDTO ,HttpServletRequest request, HttpServletResponse response){
        Integer pageSize = pageParamDTO.getPageSize() == null ? 20 : pageParamDTO.getPageSize();
        notice.setType(4);
        PageInfo<Notice> pageInfo = noticeService.pageList(notice,page,pageSize);
        return AccessResponse.builder().data(pageInfo.getList()).success(true).rspcode(200).message("服务端处理请求成功。").build();
    }
    /*
        广告轮播图
     */
    @RequestMapping(value = "/banner/{page}")
    public @ResponseBody
    AccessResponse banner(Notice notice,@PathVariable Integer page, PageParamDTO pageParamDTO ,HttpServletRequest request, HttpServletResponse response){
        Integer pageSize = pageParamDTO.getPageSize() == null ? 6 : pageParamDTO.getPageSize();
        notice.setType(5);
        PageInfo<Notice> pageInfo = noticeService.pageList(notice,page,pageSize);
        return AccessResponse.builder().data(pageInfo.getList()).success(true).rspcode(200).message("服务端处理请求成功。").build();
    }

    /*
        理财广告区轮播图
     */
    @RequestMapping(value = "/financingBanner/{page}")
    public @ResponseBody
    AccessResponse financingBanner(Notice notice,@PathVariable Integer page, PageParamDTO pageParamDTO ,HttpServletRequest request, HttpServletResponse response){
        Integer pageSize = pageParamDTO.getPageSize() == null ? 1 : pageParamDTO.getPageSize();
        notice.setType(6);
        PageInfo<Notice> pageInfo = noticeService.pageList(notice,page,pageSize);
        return AccessResponse.builder().data(pageInfo.getList()).success(true).rspcode(200).message("服务端处理请求成功。").build();
    }

    /*
        热点问题
     */
    @RequestMapping(value = "/hotProblem")
    public @ResponseBody
    AccessResponse hotProblem(Notice notice,  HttpServletRequest request, HttpServletResponse response){
        notice.setType(2);
        notice.setExpand1("1");
        PageInfo<Notice> pageInfo = noticeService.pageList(notice,1,5);
        return AccessResponse.builder().data(pageInfo).success(true).rspcode(200).message("服务端处理请求成功。").build();
    }
    /*
        全部问题
     */
    @RequestMapping(value = "/problem/{page}")
    public @ResponseBody
    AccessResponse problem(Notice notice, @PathVariable Integer page, PageParamDTO pageParamDTO , HttpServletRequest request, HttpServletResponse response){
        Integer pageSize = pageParamDTO.getPageSize() == null ? 20 : pageParamDTO.getPageSize();
        notice.setType(2);
        PageInfo<Notice> pageInfo = noticeService.pageList(notice,page,pageSize);
        return AccessResponse.builder().data(pageInfo).success(true).rspcode(200).message("服务端处理请求成功。").build();
    }


    /*
        联系方式
     */
    @RequestMapping(value = "/touch")
    public @ResponseBody
    AccessResponse touch(Notice notice,HttpServletRequest request, HttpServletResponse response){

        notice = noticeService.get(23l);
        return AccessResponse.builder().data(notice).success(true).rspcode(200).message("服务端处理请求成功。").build();
    }

    /*
        委托协议
     */
    @RequestMapping(value = "/trust")
    public @ResponseBody
    AccessResponse trust(Notice notice,HttpServletRequest request, HttpServletResponse response){

        notice = noticeService.get(21l);
        return AccessResponse.builder().data(notice).success(true).rspcode(200).message("服务端处理请求成功。").build();
    }

    /*
        理财协议
     */
    @RequestMapping(value = "/financing")
    public @ResponseBody
    AccessResponse financing(Notice notice,HttpServletRequest request, HttpServletResponse response){

        notice = noticeService.get(22l);
        return AccessResponse.builder().data(notice).success(true).rspcode(200).message("服务端处理请求成功。").build();
    }

    /*
        详细信息
     */
    @RequestMapping(value = "/noticeInfo/{id}")
    public @ResponseBody
    AccessResponse noticeInfo(@PathVariable Long id,HttpServletRequest request, HttpServletResponse response){

        Notice notice = noticeService.get(id);
        return AccessResponse.builder().data(notice).success(true).rspcode(200).message("服务端处理请求成功。").build();
    }
}
