package com.spring.free.controller.manage;/**
 * Created by hengpu on 2019/2/25.
 */

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.spring.fee.model.TableGoods;
import com.spring.fee.model.TableOrder;
import com.spring.fee.service.ITableOrderBusiSV;
import com.spring.free.common.util.ExcelUtils;
import com.spring.free.config.CommonUtils;
import com.spring.free.config.ImageUtils;
import com.spring.free.domain.QueryVO;
import com.spring.free.util.PageResult;
import com.spring.free.util.constraints.Global;
import com.spring.free.util.constraints.PageDefaultConstraints;
import com.spring.free.util.constraints.PromptInfoConstraints;
import com.spring.free.util.exception.ExceptionCodeEnum;
import com.spring.free.util.exception.ServiceException;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 后台管理/订单
 **/
@Controller
@RequestMapping(Global.ADMIN_PATH + "/manage/order/")
public class ManageOrderController {

    @Autowired
    ITableOrderBusiSV iTableOrderBusiSV;

    /*
     * @Author gh
     * @Description //TODO 配置列表
     * @Param [mav, session, post, request, page, pageSize]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequiresPermissions("system:member:view")
    @RequestMapping({"", "list"})
    public ModelAndView list(ModelAndView mav, HttpSession session, QueryVO queryVO, HttpServletRequest request,
                             @RequestParam(value = "page",required = false, defaultValue = PageDefaultConstraints.PAGE) int page,
                             @RequestParam(value = "rows", required = false, defaultValue = PageDefaultConstraints.PAGE_SIZE) int pageSize) {
        // String postType = request.getParameter("postType");

        TableOrder tableOrder = new TableOrder();
        BeanUtils.copyProperties(queryVO, tableOrder);
        tableOrder.setId(queryVO.getId()==null?null:queryVO.getId().intValue());
        PageInfo<TableOrder> pageInfo = this.iTableOrderBusiSV.queryListPage(tableOrder, page, pageSize, CommonUtils.getStartEnd(queryVO));

        //获取热门话题列表信息
        mav.addObject("page", pageInfo);
        mav.addObject("queryVO",queryVO);
        //返回页面header标题
        PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_DICT_LIST);
        //返回操作提示信息
        PageResult.getPrompt(mav, request, queryVO.getParamMsg());

        mav.setViewName("manage/order/list");
        return mav;
    }


    /*
     * @Author jzc
     * @Date 11:06 2019/2/28
     * @Param [view, request, post, buttonType]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequiresPermissions("system:member:view")
    @RequestMapping(value = "edit")
    public ModelAndView views(ModelAndView view, HttpServletRequest request, TableOrder tableOrder, String buttonType) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "订单表信息");
        PageResult.getPrompt(view, request, "");
        TableOrder tableMember=this.iTableOrderBusiSV.select(tableOrder);
        view.addObject("order",tableMember);
        view.setViewName("manage/order/edit");
        return view;
    }


    /*
     * 发货
     * @Author jzc
     * @Date 11:06 2019/2/28
     * @Param [view, request, post, buttonType]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequiresPermissions("system:member:view")
    @RequestMapping(value = "sendIndex")
    public ModelAndView send(ModelAndView view, HttpServletRequest request, TableOrder tableOrder, String buttonType) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "订单表信息");
        PageResult.getPrompt(view, request, "");
        TableOrder tableMember=this.iTableOrderBusiSV.select(tableOrder);
        view.addObject("order",tableMember);
        view.setViewName("manage/order/send");
        return view;
    }

    //
    /*
     * @Author bianyx
     * @Description //TODO 编辑新增保存
     * @Date 11:07 2019/1/18
     * @Param [mav, request, topItem, post, buttonType, ghPic1]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequiresPermissions("system:member:view")
    @RequestMapping(value = "send")
    public ModelAndView send(ModelAndView mav, HttpServletRequest request, TableOrder tableOrder) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH +"/manage/order/list");
        tableOrder.setState(3); //发货完成
        try {
            this.iTableOrderBusiSV.update(tableOrder);
        }catch (Exception e) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), e.getMessage(), map.get(Global.URL).toString(), map);
        }

        PageResult.setPrompt(map,"操作成功", "success");
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/manage/order/list"), map);
    }

    //
    /*
     * @Author bianyx
     * @Description //TODO 编辑新增保存
     * @Date 11:07 2019/1/18
     * @Param [mav, request, topItem, post, buttonType, ghPic1]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequiresPermissions("system:member:view")
    @RequestMapping(value = "save")
    public ModelAndView edit(ModelAndView mav, HttpServletRequest request, TableOrder tableOrder) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH +"/manage/order/list");

        this.iTableOrderBusiSV.update(tableOrder);

        PageResult.setPrompt(map,"操作成功", "success");
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/manage/order/list"), map);
    }

    @RequiresPermissions("system:member:view")
    @RequestMapping(value = "view")
    public ModelAndView view(ModelAndView view, HttpServletRequest request, TableOrder tableOrder) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "订单表信息");
        PageResult.getPrompt(view, request, "");
        TableOrder tableOrder1=this.iTableOrderBusiSV.select(tableOrder);
//        if(tableMember!=null) {
//            member.setTotal(account.getTotal().doubleValue() / 1000);
//            member.setAvailable(account.getAvailable().doubleValue() / 1000);
//            member.setFreeze(account.getFreeze().doubleValue() / 1000);
//            member.setMoneyFreeze(account.getMoneyFreeze().doubleValue() / 1000);
//            member.setGranaryFreeze(account.getGranaryFreeze().doubleValue() / 1000);
//        }
        view.addObject("order",tableOrder1);
        view.setViewName("manage/order/view");
        return view;
    }

    @RequestMapping("/exportOrderFile")
    public void exportTransFile(HttpServletRequest request, HttpServletResponse response) {

        QueryVO queryVO = new QueryVO();

        response.setContentType("application/octet-stream;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        ServletOutputStream outputStream = null;

        try {
            TableOrder tableOrder = new TableOrder();
            BeanUtils.copyProperties(queryVO, tableOrder);
            tableOrder.setId(queryVO.getId()==null?null:queryVO.getId().intValue());
            outputStream = response.getOutputStream();
            HSSFWorkbook hssfWorkbook = iTableOrderBusiSV.exportFile(tableOrder, 0, 0, CommonUtils.getStartEnd(queryVO));
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Disposition", "attachment;filename=order.xls");

            hssfWorkbook.write(outputStream);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if (outputStream != null) outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * 批量发货
     * @Author jzc
     * @Date 11:06 2019/2/28
     * @Param [view, request, post, buttonType]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequiresPermissions("system:member:view")
    @RequestMapping(value = "sendBatchIndex")
    public ModelAndView sendBatchIndex(ModelAndView view, HttpServletRequest request, TableOrder tableOrder, String buttonType) {
        Map map = Maps.newHashMap();
        PageResult.setPageTitle(view, "订单表信息");
        PageResult.getPrompt(view, request, "");
        view.setViewName("manage/order/sendBatch");
        return view;
    }

    @RequiresPermissions("system:member:view")
    @RequestMapping(value = "sendBatch")
    public ModelAndView sendBatch(ModelAndView mav, HttpServletRequest request, TableGoods tableGoods, MultipartFile orderFile) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH +"/manage/order/sendlist");

        List<TableOrder> okList = new ArrayList<>();

        try {
            if (orderFile != null && StringUtils.isNotEmpty(orderFile.getOriginalFilename())) {
                //导入文件
                Workbook workbook = ExcelUtils.readExcel(orderFile);

                // 获取第一个sheet
                Sheet sheet = workbook.getSheetAt(0);
                // 获取最大行数
                int rownum = sheet.getPhysicalNumberOfRows();

                List<TableOrder> tableOrderList = new ArrayList();

                for (int i = 1; i < rownum; i++) {
                    Row row = sheet.getRow(i);
                    Cell a = row.getCell(0);
                    if (null == a || StringUtils.isEmpty(a.getStringCellValue())) {
                        continue;
                    }
                    TableOrder tableOrder = new TableOrder();
                    tableOrder.setId(Integer.parseInt(row.getCell(0).getStringCellValue()));
                    tableOrder.setOrderId(row.getCell(2).getStringCellValue());
                    tableOrder.setExpressNumber(row.getCell(1).getStringCellValue());
                    tableOrderList.add(tableOrder);
                }

                okList = this.iTableOrderBusiSV.sendOrder(tableOrderList);
            }
        } catch (Exception e) {
            map.put(Global.URL, Global.ADMIN_PATH +"/manage/order/sendBatchIndex");
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), e.getMessage(), map.get(Global.URL).toString(), map);

        }

        PageResult.setPrompt(map,"操作成功", "success");
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH +"/manage/order/list"), map);
    }
}
