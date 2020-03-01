package com.spring.free.controller.wheat;

import com.github.pagehelper.PageInfo;
import com.spring.fee.model.*;
import com.spring.fee.service.*;
import com.spring.free.util.DateUtils;
import com.spring.free.util.PageResult;
import com.spring.free.util.constraints.Global;
import com.spring.free.util.constraints.PageDefaultConstraints;
import com.spring.free.util.constraints.PromptInfoConstraints;
import com.spring.free.utils.velocity.DictUtils;
import com.spring.free.vo.AcctDetailReqVO;
import com.spring.free.vo.MemberReqVO;
import com.spring.free.vo.SettleRecordReqVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 账户controller
 */
@Slf4j
@Controller
@RequestMapping(Global.ADMIN_PATH + "/wheat/acct/")
public class AcctController {

    @Autowired
    ITWheatAccountDetailBusiSV itWheatAccountDetailBusiSV;

    /*
     * @Author bianyx
     * @Description //TODO 账户变更记录明细
     * @Date 11:02 2019/1/18
     * @Param [mav, session, post, request, page, pageSize]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequiresPermissions("system:acct:view")
    @RequestMapping({"", "acctDetailListIndex"})
    public ModelAndView acctDetailListIndex(ModelAndView mav, HttpSession session, AcctDetailReqVO memberReqVO, HttpServletRequest request,
                                            @RequestParam(value = "page",required = false, defaultValue = PageDefaultConstraints.PAGE) int page,
                                            @RequestParam(value = "rows", required = false, defaultValue = PageDefaultConstraints.PAGE_SIZE) int pageSize) {
        mav.setViewName("wheat/acct/acctDetailList");
        return mav;
    }

    /*
     * @Author bianyx
     * @Description //TODO 账户变更记录明细
     * @Date 11:02 2019/1/18
     * @Param [mav, session, post, request, page, pageSize]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequiresPermissions("system:acct:view")
    @RequestMapping({"", "acctDetailList"})
    public ModelAndView acctDetailList(ModelAndView mav, HttpSession session, AcctDetailReqVO memberReqVO, HttpServletRequest request,
                                       @RequestParam(value = "page",required = false, defaultValue = PageDefaultConstraints.PAGE) int page,
                                       @RequestParam(value = "rows", required = false, defaultValue = PageDefaultConstraints.PAGE_SIZE) int pageSize) {
        // String postType = request.getParameter("postType");
        TWheatAccountDetailDZ tWheatAccountDetail = new TWheatAccountDetailDZ();
        if (!StringUtils.isEmpty(memberReqVO.getTimeStart())) {
            tWheatAccountDetail.setCreateTimeStart(DateUtils.parseDate(memberReqVO.getTimeStart()));
        }
        if (!StringUtils.isEmpty(memberReqVO.getTimeEnd())) {
            tWheatAccountDetail.setCreateTimeEnd(DateUtils.addDays(DateUtils.parseDate(memberReqVO.getTimeEnd()), 1));
        }
        tWheatAccountDetail.setMemberId(memberReqVO.getMemberId());
        PageInfo<TWheatAccountDetail> pageInfo = this.itWheatAccountDetailBusiSV.queryPage(tWheatAccountDetail, page, pageSize, null);

        mav.addObject("param",memberReqVO);
        mav.addObject("page", pageInfo);

        mav.setViewName("wheat/acct/acctDetailList");
        return mav;
    }
}
