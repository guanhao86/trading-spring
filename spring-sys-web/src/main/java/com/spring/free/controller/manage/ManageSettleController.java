package com.spring.free.controller.manage;/**
 * Created by hengpu on 2019/2/25.
 */

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.spring.fee.model.TableBonusDetail;
import com.spring.fee.service.ITableBonusDetailBusiSV;
import com.spring.free.common.util.PythonUtil;
import com.spring.free.common.util.PythonUtil2;
import com.spring.free.common.util.PythonUtil3;
import com.spring.free.config.CommonUtils;
import com.spring.free.domain.QueryVO;
import com.spring.free.domain.UserInfo;
import com.spring.free.util.PageResult;
import com.spring.free.util.constraints.Global;
import com.spring.free.util.constraints.PageDefaultConstraints;
import com.spring.free.util.constraints.PromptInfoConstraints;
import com.spring.free.util.exception.ExceptionCodeEnum;
import com.spring.free.util.exception.ServiceException;
import com.spring.free.utils.principal.BaseGetPrincipal;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 后台管理/结算接口
 **/
@Controller
@RequestMapping(Global.ADMIN_PATH + "/manage/settle/")
public class ManageSettleController {

    @Autowired
    ITableBonusDetailBusiSV iTableBonusDetailBusiSV;

    @Value("${python.exePath}")
    public String python_exepath;

    @Value("${python.path}")
    public String python_path;

    /*
     * @Author gh
     * @Description //TODO 配置列表
     * @Param [mav, session, post, request, page, pageSize]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequiresPermissions("system:member:view")
    @RequestMapping({"", "settleIndex"})
    public ModelAndView listIndex(ModelAndView mav, HttpSession session, QueryVO queryVO, HttpServletRequest request,
                             @RequestParam(value = "page",required = false, defaultValue = PageDefaultConstraints.PAGE) int page,
                             @RequestParam(value = "rows", required = false, defaultValue = PageDefaultConstraints.PAGE_SIZE) int pageSize) {
        // String postType = request.getParameter("postType");

        TableBonusDetail memberAccountDetail = new TableBonusDetail();
        BeanUtils.copyProperties(queryVO, memberAccountDetail);

        //获取热门话题列表信息
        mav.addObject("queryVO",queryVO);
        //返回页面header标题
        PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_DICT_LIST);
        //返回操作提示信息
        PageResult.getPrompt(mav, request, null);

        mav.setViewName("manage/settle/settle");
        return mav;
    }

    /*
     * @Author gh
     * @Description //TODO 配置列表
     * @Param [mav, session, post, request, page, pageSize]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequiresPermissions("system:member:view")
    @RequestMapping({"", "settle"})
    public ModelAndView list(ModelAndView mav, HttpSession session, QueryVO queryVO, HttpServletRequest request,
                             @RequestParam(value = "page",required = false, defaultValue = PageDefaultConstraints.PAGE) int page,
                             @RequestParam(value = "rows", required = false, defaultValue = PageDefaultConstraints.PAGE_SIZE) int pageSize) {

        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH +"/manage/balance/list");
        UserInfo user = BaseGetPrincipal.getUser();

        String result = "";
        try {
            //日结
            if ("1".equals(queryVO.getSettleType())) {
                result = PythonUtil3.runPy(python_exepath, python_path, "balance_gogogo.py", user.getUsername(), "");
                //PythonUtil.runPy("E:\\工作\\01 需求\\20191014 麦子科技\\东家嗨团\\结算代码\\结算代码\\dj_balance_accounts.py", "balance_gogogo", user.getUsername());
            }

            if ("2".equals(queryVO.getSettleType())) {
                result = PythonUtil3.runPy(python_exepath, python_path, "send_bonus.py", user.getUsername(), "");
            }

            if ("3".equals(queryVO.getSettleType())) {
                result = PythonUtil3.runPy(python_exepath, python_path, "balance_go2.py", user.getUsername(), "");
            }

        }catch (Exception e) {
            map.put(Global.URL, Global.ADMIN_PATH +"/manage/balance/list");
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), e.getMessage(), map.get(Global.URL).toString(), map);
        }

        if ("0".equals(result)) {
            PageResult.setPrompt(map, "操作成功", "success");
        }else {
            PageResult.setPrompt(map, "结算失败："+result, "success");
        }

        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/manage/balance/list"), map);
    }

}
