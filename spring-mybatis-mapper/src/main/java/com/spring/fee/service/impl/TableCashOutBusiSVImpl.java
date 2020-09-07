package com.spring.fee.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.fee.dao.mapper.TableCashOutMapper;
import com.spring.fee.model.*;
import com.spring.fee.service.IMemberAccountDetailBusiSV;
import com.spring.fee.service.ITableCashOutBusiSV;
import com.spring.fee.service.ITableMemberBusiSV;
import com.spring.fee.service.ITableSystemConfigBusiSV;
import com.spring.free.common.domain.AccessResponse;
import com.spring.free.common.util.ExcelUtils;
import com.spring.free.util.DateUtils;
import com.spring.free.util.exception.ExceptionCodeEnum;
import com.spring.free.util.exception.ServiceException;
import com.spring.free.utils.velocity.DictUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 提现服务
 */
@Slf4j
@Service
@Transactional
public class TableCashOutBusiSVImpl implements ITableCashOutBusiSV {

    @Autowired
    TableCashOutMapper iTableCashOutMapper;

    @Autowired
    ITableMemberBusiSV iTableMemberBusiSV;

    @Autowired
    IMemberAccountDetailBusiSV iMemberAccountDetailBusiSV;

    @Autowired
    ITableSystemConfigBusiSV iTableSystemConfigBusiSV;


    /**
     * 创建提现记录
     * @param bo
     * @return
     */
    @Override
    public TableCashOut insert(TableCashOut bo) {
        log.info("创建提现参数bo：{}", JSON.toJSON(bo));
        bo.setCreateTime(DateUtils.getSysDate());
        bo.setAuditState("1");
        iTableCashOutMapper.insert(bo);
        return bo;
    }

    @Override
    public TableCashOut update(TableCashOut bo) {
        bo.setAuditTime(DateUtils.getSysDate());
        if (this.iTableCashOutMapper.updateByPrimaryKeySelective(bo) == 1) {
            return bo;
        }
        return null;
    }

    @Override
    public TableCashOut delete(TableCashOut bo) {
        if (this.iTableCashOutMapper.deleteByPrimaryKey(bo.getId()) == 1) {
            return bo;
        }
        return null;
    }

    @Override
    public TableCashOut select(TableCashOut bo) {
        return this.iTableCashOutMapper.selectByPrimaryKey(bo.getId());
    }

    /**
     * 提现申请
     *
     * @param tableCashOut
     * @return
     */
    @Override
    public TableCashOut apply(TableCashOut tableCashOut) {

        TableMember tableMember = this.iTableMemberBusiSV.selectByMemberId(tableCashOut.getMemberId());

        if (tableMember == null ){
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "会员不存在！", "", null);
        }

        if (tableCashOut.getAmount() < 100) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "提现金额最低100元！", "", null);
        }

        if (tableCashOut.getAmount() % 100 != 0) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "提现金额必须是100的整数倍！", "", null);
        }

        //当前存在提现申请的，不允许再次申请
        TableCashOut tableCashOutQuery = new TableCashOut();
        tableCashOutQuery.setMemberId(tableMember.getMemberId());
        tableCashOutQuery.setAuditState("1");
        PageInfo<TableCashOut> pageInfo = this.queryListPage(tableCashOutQuery, 1, 1, null);

        if (pageInfo.getTotal() > 0 ){
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "存在审核中的记录，等审核通过后再申请！", "", null);
        }

        if (tableCashOut.getAmount().floatValue() > tableMember.getAccountMoney().floatValue()) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "余额不足！", "", null);
        }

//        if (org.springframework.util.StringUtils.isEmpty(tableMember.getBankCardId())
//                || org.springframework.util.StringUtils.isEmpty(tableMember.getBankOpenAre())
//                || org.springframework.util.StringUtils.isEmpty(tableMember.getBankName())
//        ){
//            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "银行卡信息不正确！", "", null);
//        }

        if (org.springframework.util.StringUtils.isEmpty(tableMember.getReallyName())){
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "会员姓名不能为空！", "", null);
        }

        if (StringUtils.isEmpty(tableCashOut.getBankCardId())) {
            if (StringUtils.isEmpty(tableMember.getBankCardId())) {
                throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "银行卡号不能为空！", "", null);
            }
            tableCashOut.setBankCardId(tableMember.getBankCardId());
        }
//        if (StringUtils.isEmpty(tableCashOut.getBankOpenAre())) {
//            if (StringUtils.isEmpty(tableMember.getBankOpenAre())) {
//                throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "开户行地址不能为空！", "", null);
//            }
//            tableCashOut.setBankOpenAre(tableMember.getBankOpenAre());
//        }
        if (StringUtils.isEmpty(tableCashOut.getBankName())) {
            if (StringUtils.isEmpty(tableMember.getBankName())) {
                throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "开户行不能为空！", "", null);
            }
            tableCashOut.setBankName(tableMember.getBankName());
        }

        tableCashOut.setMemberName(tableMember.getReallyName());

        //直接扣款
        //提现手续费配置
        PageInfo<TableSystemConfig> systemConfigPageInfo = iTableSystemConfigBusiSV.queryListPage(new TableSystemConfig(), 1, 1, null);
        Float cashOutCostPre = systemConfigPageInfo.getList().get(0).getSysCashOutCost();
        DecimalFormat df = new DecimalFormat("#.00");
        Float cashOutCost = cashOutCostPre * tableCashOut.getAmount().floatValue() / 100;
        cashOutCost = Float.parseFloat(df.format(cashOutCost));

        this.iMemberAccountDetailBusiSV.changeMoney(tableMember.getMemberId(), "2", tableCashOut.getAmount() - cashOutCost, "提现", null);
        this.iMemberAccountDetailBusiSV.changeMoney(tableMember.getMemberId(), "2", cashOutCost, "提现手续费", null);

        //设置手续费
        tableCashOut.setCommission(cashOutCost);
        TableCashOut tableCashOut1 = this.insert(tableCashOut);

        return tableCashOut1;
    }

    @Override
    public TableCashOut audit(TableCashOut bo) {

        if (null == bo.getId() || bo.getId() == 0) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "主键不能为空！", "", null);
        }

        //查询待审核记录
        TableCashOut origTableCashOut = this.select(bo);
        if (origTableCashOut == null){
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "原申请记录不存在！", "", null);
        }
        if (!"1".equals(origTableCashOut.getAuditState())){
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "非待审核状态，不允许撤销！", "", null);
        }

        //审核通过修改状态
        if ("2".equals(bo.getAuditState())) {
            if (StringUtils.isEmpty(bo.getAuditImage())) {
                throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "打款凭证不能为空！", "", null);
            }
            this.update(bo);
        }
        //撤销，需要把原钱数返回到账户里（金额+手续费）
        if ("3".equals(bo.getAuditState())) {
            //返回钱数
            this.iMemberAccountDetailBusiSV.changeMoney(origTableCashOut.getMemberId(), "1", origTableCashOut.getAmount() - origTableCashOut.getCommission(), "提现【撤销回退】", null);
            this.iMemberAccountDetailBusiSV.changeMoney(origTableCashOut.getMemberId(), "1", origTableCashOut.getCommission(), "提现手续费【撤销回退】", null);
            if (StringUtils.isEmpty(bo.getAuditRemark())){
                bo.setAuditRemark("已撤销");
            }
            this.update(bo);
        }

        return bo;
    }

    /**
     * 数据列表分页
     * @param bo
     * @param pageNum
     * @param pageSize
     * @param map
     * @return
     */
    @Override
    public PageInfo<TableCashOut> queryListPage(TableCashOut bo, Integer pageNum, Integer pageSize, Map<String ,Object> map) {
        log.info("获取提现参数bo：{}", JSON.toJSON(bo));
        log.info("获取提现参数pageNum：{}", pageNum);
        log.info("获取提现参数pageSize：{}", pageSize);
        log.info("获取提现参数map：{}", JSON.toJSON(map));
        
        TableCashOutExample example = new TableCashOutExample();
        TableCashOutExample.Criteria criteria = example.createCriteria();

        if (null != bo.getId()) {
            criteria.andIdEqualTo(bo.getId());
        }
        if (StringUtils.isNotEmpty(bo.getMemberId())) {
            criteria.andMemberIdEqualTo(bo.getMemberId());
        }
        if (null != bo.getAmount()) {
            criteria.andAmountEqualTo(bo.getAmount());
        }
        if (StringUtils.isNotEmpty(bo.getMemberName())) {
            criteria.andMemberNameEqualTo(bo.getMemberName());
        }
        if (StringUtils.isNotEmpty(bo.getBankCardId())) {
            criteria.andBankCardIdEqualTo(bo.getBankCardId());
        }
        if (StringUtils.isNotEmpty(bo.getBankName())) {
            criteria.andBankNameEqualTo(bo.getBankName());
        }
        if (StringUtils.isNotEmpty(bo.getBankOpenAre())) {
            criteria.andBankOpenAreEqualTo(bo.getBankOpenAre());
        }
        if (null != bo.getCreateTime()) {
            criteria.andCreateTimeEqualTo(bo.getCreateTime());
        }
        if (StringUtils.isNotEmpty(bo.getAuditMemberId())) {
            criteria.andAuditMemberIdEqualTo(bo.getAuditMemberId());
        }
        if (null != bo.getAuditTime()) {
            criteria.andAuditTimeEqualTo(bo.getAuditTime());
        }
        if (StringUtils.isNotEmpty(bo.getAuditState())) {
            criteria.andAuditStateEqualTo(bo.getAuditState());
        }
        if (StringUtils.isNotEmpty(bo.getAuditRemark())) {
            criteria.andAuditRemarkEqualTo(bo.getAuditRemark());
        }
        if (StringUtils.isNotEmpty(bo.getAuditImage())) {
            criteria.andAuditImageEqualTo(bo.getAuditImage());
        }


        example.setOrderByClause("create_time desc");

        PageInfo<TableCashOut> pageInfo = PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(() -> this.iTableCashOutMapper.selectByExample(example));
        log.info("获取提现结果：{}", JSON.toJSON(pageInfo));
        return pageInfo;
    }

    /**
     * 导出文件
     *
     * @param bo
     * @param pageNum
     * @param pageSize
     * @param map
     * @return
     */
    @Override
    public HSSFWorkbook exportFile(TableCashOut bo, Integer pageNum, Integer pageSize, Map<String, Object> map) {
        PageInfo<TableCashOut> pageInfo = this.queryListPage(bo, 1, 10000000, map);

        List<TableCashOut> list = new ArrayList<>();
        if (pageInfo != null && !CollectionUtils.isEmpty(pageInfo.getList())) {
            list = pageInfo.getList();
        } else {
            System.out.println("没有数据");
        }

        String sheetName = "提现申请";
        String[] title = {"id", "申请人", "金额", "姓名", "银行卡号", "银行名", "开户行", "申请时间", "审核时间", "审核备注", "状态"};
        String[][] values = new String[list.size()+1][title.length];

        int i = 0;
        for (TableCashOut tmp : list) {
            values[i][0] = String.valueOf(tmp.getId());
            values[i][1] = tmp.getMemberId();
            values[i][2] = tmp.getAmount()+"";
            values[i][3] = tmp.getMemberName();
            values[i][4] = tmp.getBankCardId();
            values[i][5] = tmp.getBankName();
            values[i][6] = tmp.getBankOpenAre();
            values[i][7] = DateUtils.formatDateTime(tmp.getCreateTime());
            values[i][8] = DateUtils.formatDateTime(tmp.getAuditTime());
            values[i][9] = tmp.getAuditRemark();
            values[i][10] = DictUtils.getDictLabel(String.valueOf(tmp.getAuditState()),"cashoutState","");

            i++;
        }

        HSSFWorkbook wb = ExcelUtils.getHSSFWorkbook(sheetName, title, values, null);
        return wb;
    }
}
