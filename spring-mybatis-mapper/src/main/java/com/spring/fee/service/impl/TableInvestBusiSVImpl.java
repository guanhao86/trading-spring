package com.spring.fee.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.fee.dao.mapper.TableInvestMapper;
import com.spring.fee.model.TableInvest;
import com.spring.fee.model.TableInvestExample;
import com.spring.fee.model.TableMember;
import com.spring.fee.service.IMemberAccountDetailBusiSV;
import com.spring.fee.service.ITableInvestBusiSV;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 充值申请&审核管理表服务
 */
@Slf4j
@Service
@Transactional
public class TableInvestBusiSVImpl implements ITableInvestBusiSV {

    @Autowired
    TableInvestMapper iTableInvestMapper;

    @Autowired
    IMemberAccountDetailBusiSV iMemberAccountDetailBusiSV;

    /**
     * 创建记录
     * @param bo
     * @return
     */
    @Override
    public TableInvest insert(TableInvest bo) {
        log.info("创建充值申请&审核管理表参数bo：{}", JSON.toJSON(bo));
        Date sysdate = DateUtils.getSysDate();
        bo.setInvestTime(sysdate);
        bo.setState(1); //待审核
        iTableInvestMapper.insert(bo);
        return bo;
    }

    @Override
    public TableInvest update(TableInvest bo) {
        bo.setApprovalTime(DateUtils.getSysDate());
        if (this.iTableInvestMapper.updateByPrimaryKeySelective(bo) == 1) {
            return bo;
        }
        return null;
    }

    @Override
    public TableInvest delete(TableInvest bo) {
        if (this.iTableInvestMapper.deleteByPrimaryKey(bo.getId()) == 1) {
            return bo;
        }
        return null;
    }

    @Override
    public TableInvest select(TableInvest bo) {
        return this.iTableInvestMapper.selectByPrimaryKey(bo.getId());
    }

    /**
     * 审核
     *
     * @param bo
     * @return
     */
    @Override
    public TableInvest audit(TableInvest bo) {

        TableInvest orig = this.select(bo);

        if (orig == null || !"1".equals(orig.getState()+"")) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "不是待审核状态！", "", null);
        }

        /*
         * 申请状态
         * 1：申请提交，待审核
         * 2：审核通过
         * 3：审核拒绝
         */
        if("2".equals(bo.getState()+"")) {
            this.iMemberAccountDetailBusiSV.changeMoney(
                    bo.getMemberId(),
                    "1",
                    bo.getAccountMoney(),
                    "充值申请，审核通过",
                    null);
        }
        orig.setState(bo.getState());
        orig.setApprovalResult(bo.getApprovalResult());
        this.update(orig);
        return orig;
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
    public PageInfo<TableInvest> queryListPage(TableInvest bo, Integer pageNum, Integer pageSize, Map<String ,Object> map) {
        log.info("获取充值申请&审核管理表参数bo：{}", JSON.toJSON(bo));
        log.info("获取充值申请&审核管理表参数pageNum：{}", pageNum);
        log.info("获取充值申请&审核管理表参数pageSize：{}", pageSize);
        log.info("获取充值申请&审核管理表参数map：{}", JSON.toJSON(map));
        
        TableInvestExample example = new TableInvestExample();
        TableInvestExample.Criteria criteria = example.createCriteria();

        if (null != bo.getId()) {
            criteria.andIdEqualTo(bo.getId());
        }
        if (StringUtils.isNotEmpty(bo.getMemberId())) {
            criteria.andMemberIdEqualTo(bo.getMemberId());
        }
        if (null != bo.getAccountMoney()) {
            criteria.andAccountMoneyEqualTo(bo.getAccountMoney());
        }
        if (StringUtils.isNotEmpty(bo.getCertificateImgSrc())) {
            criteria.andCertificateImgSrcEqualTo(bo.getCertificateImgSrc());
        }
        if (null != bo.getInvestTime()) {
            criteria.andInvestTimeEqualTo(bo.getInvestTime());
        }
        if (null != bo.getState()) {
            criteria.andStateEqualTo(bo.getState());
        }
        if (StringUtils.isNotEmpty(bo.getApprovalResult())) {
            criteria.andApprovalResultEqualTo(bo.getApprovalResult());
        }
        if (null != bo.getApprovalTime()) {
            criteria.andApprovalTimeEqualTo(bo.getApprovalTime());
        }

        if (null != map) {
            if (null != map.get("start")) {
                criteria.andInvestTimeGreaterThanOrEqualTo((Date)map.get("start"));
            }
            if (null != map.get("end")) {
                criteria.andInvestTimeLessThanOrEqualTo((Date)map.get("end"));
            }
        }

        example.setOrderByClause("approval_time desc");

        PageInfo<TableInvest> pageInfo = PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(() -> this.iTableInvestMapper.selectByExample(example));
        log.info("获取充值申请&审核管理表结果：{}", JSON.toJSON(pageInfo));
        return pageInfo;
    }

    /**
     * 导出会员文件
     *
     * @param bo
     * @param pageNum
     * @param pageSize
     * @param map
     * @return
     */
    @Override
    public HSSFWorkbook exportFile(TableInvest bo, Integer pageNum, Integer pageSize, Map<String, Object> map) {
        PageInfo<TableInvest> pageInfo = this.queryListPage(bo, 1, 10000000, map);

        List<TableInvest> list = new ArrayList<>();
        if (pageInfo != null && !CollectionUtils.isEmpty(pageInfo.getList())) {
            list = pageInfo.getList();
        } else {
            System.out.println("没有数据");
        }

        String sheetName = "充值申请";
        String[] title = {"id", "会员ID", "充值金额(元)", "申请时间", "申请状态"};
        String[][] values = new String[list.size()+1][title.length];

        int i = 0;
        for (TableInvest tmp : list) {
            values[i][0] = String.valueOf(tmp.getId());
            values[i][1] = tmp.getMemberId();
            values[i][2] = tmp.getAccountMoney()+"";
            values[i][3] = DateUtils.formatDateTime(tmp.getInvestTime());
            values[i][4] = DictUtils.getDictLabel(String.valueOf(tmp.getState()),"investState","");

            i++;
        }

        HSSFWorkbook wb = ExcelUtils.getHSSFWorkbook(sheetName, title, values, null);
        return wb;
    }
}
