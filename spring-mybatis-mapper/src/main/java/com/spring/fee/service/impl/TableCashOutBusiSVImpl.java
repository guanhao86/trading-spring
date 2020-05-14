package com.spring.fee.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.fee.dao.mapper.TableCashOutMapper;
import com.spring.fee.model.TableCashOut;
import com.spring.fee.model.TableCashOutExample;
import com.spring.fee.model.TableInvest;
import com.spring.fee.model.TableMember;
import com.spring.fee.service.IMemberAccountDetailBusiSV;
import com.spring.fee.service.ITableCashOutBusiSV;
import com.spring.fee.service.ITableMemberBusiSV;
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

    @Override
    public TableCashOut audit(TableCashOut bo) {

        if ("2".equals(bo.getAuditState())) {

            //查询待审核记录
            TableCashOut origTableCashOut = this.select(bo);
            if ("1".equals(origTableCashOut.getAuditState())){
                //查询会员
                TableMember tableMember = this.iTableMemberBusiSV.selectByMemberId(bo.getMemberId());

                if (tableMember.getAccountMoney() < origTableCashOut.getAmount()) {
                    throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "余额不足！", "", null);
                }

                this.iMemberAccountDetailBusiSV.changeMoney(tableMember.getMemberId(), "2", bo.getAmount(), "提现", null);

                this.update(bo);
            }
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
