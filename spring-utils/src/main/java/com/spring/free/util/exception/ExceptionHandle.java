package com.spring.free.util.exception;

import com.alibaba.fastjson.JSONObject;
import com.spring.free.common.domain.AccessResponse;
import com.spring.free.util.constraints.PromptInfoConstraints;
import com.spring.free.util.PageResult;
import com.mysql.jdbc.exceptions.jdbc4.MySQLTransactionRollbackException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author Memory
 * @date 2018/1/8
 * @Inc 恒谱科技
 */
@Slf4j
@ControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(value = ServiceException.class)
    public ModelAndView serviceErrorHandler(ServiceException ex) {
        ex.getMap().put("code", ex.getCode());
        ex.getMap().put("msg", ex.getMessage());
        ex.getMap().put("successAndFail", PromptInfoConstraints.FAIL_STATUS);
        PageResult.publicModelError(ex);
        return new ModelAndView(new RedirectView(ex.getUrl()), ex.getMap());
    }

    @ExceptionHandler(value = Exception.class)
    public ModelAndView systemErrorHandler(Exception ex){
        ModelAndView modelAndView = new ModelAndView();
        if (ex instanceof UnauthorizedException) {
            log.error("[权限异常] {}", ex);
            modelAndView.addObject("code", ExceptionCodeEnum.AUTH_ERROR_CODE.getCode());
            modelAndView.addObject("msg", "您没有权限，请联系管理员");
        } else if (ex instanceof MySQLTransactionRollbackException) {
            log.error("[并发异常] {}", ex);
            modelAndView.addObject("code", ExceptionCodeEnum.AUTH_ERROR_CODE.getCode());
            modelAndView.addObject("msg", "该流水号已存在，不能添加");
        } else {
            log.error("[系统异常] {}", ex);
            modelAndView.addObject("code", ExceptionCodeEnum.SYSTEM_ERROR_CODE.getCode());
            StringBuilder failureMsg = new StringBuilder();
            for (StackTraceElement element : ex.getStackTrace()) {
                failureMsg.append("<p style=\"color: red;text-indent:2em;\"> at " + element.toString() + "</p>");
            }
            failureMsg.insert(0, "<p style=\"color: red;\">" + ex.toString() + "</p>");

            modelAndView.addObject("msg",  "<h3>系统发生异常，请联系管理员</h3>" + failureMsg.toString());
            PageResult.publicModelError(ex, "系统异常");
        }
        modelAndView.setViewName("error");
        return modelAndView;
    }

    @ExceptionHandler({ RestException.class })
    @ResponseBody
    public AccessResponse restException(RestException ex) {
        log.error(ex.getMessage(), ex);

        JSONObject jsonObj=new JSONObject();
        jsonObj.put("result",ex.getCode());
        jsonObj.put("desc",ex.getMessage());
        return AccessResponse.builder().data(jsonObj).success(true).rspcode(200).message("服务端处理请求成功。").build();

    }


}
