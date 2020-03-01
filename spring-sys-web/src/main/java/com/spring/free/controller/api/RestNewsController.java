package com.spring.free.controller.api;

import com.github.pagehelper.PageInfo;
import com.spring.free.common.domain.AccessResponse;
import com.spring.free.common.domain.PageParamDTO;
import com.spring.free.domain.News;
import com.spring.free.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping("/api/news")
@Controller
public class RestNewsController {
    @Autowired
    private NewsService newsService;


    /*
        新闻列表
     */
    @RequestMapping(value = "/news/{page}")
    public @ResponseBody
    AccessResponse news(News news, @PathVariable Integer page, PageParamDTO pageParamDTO , HttpServletRequest request, HttpServletResponse response){
        Integer pageSize = pageParamDTO.getPageSize() == null ? 20 : pageParamDTO.getPageSize();
        PageInfo<News> pageInfo = newsService.pageList(news,page,pageSize);
        return AccessResponse.builder().data(pageInfo).success(true).rspcode(200).message("服务端处理请求成功。").build();
    }

    /*
        新闻详情
     */
    @RequestMapping(value = "/newsInfo/{id}")
    public @ResponseBody
    AccessResponse noticeInfo(@PathVariable Long id,HttpServletRequest request, HttpServletResponse response){

        News news = newsService.get(id);
        return AccessResponse.builder().data(news).success(true).rspcode(200).message("服务端处理请求成功。").build();
    }
}
