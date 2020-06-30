package com.spring.fee.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.fee.constants.InvestConstants;
import com.spring.fee.dao.mapper.TableOrderMapper;
import com.spring.fee.dao.mapper.TableOrderMapperDZ;
import com.spring.fee.model.*;
import com.spring.fee.service.*;
import com.spring.free.common.util.ExcelUtils;
import com.spring.free.domain.UserInfo;
import com.spring.free.system.UserService;
import com.spring.free.util.DateUtils;
import com.spring.free.util.exception.ExceptionCodeEnum;
import com.spring.free.util.exception.ServiceException;
import com.spring.free.utils.principal.BaseGetPrincipal;
import com.spring.free.utils.velocity.DictUtils;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * 订单服务
 */
@Slf4j
@Service
@Transactional
public class TableOrderBusiSVImpl implements ITableOrderBusiSV {

    @Autowired
    TableOrderMapper iTableOrderMapper;

    @Autowired
    ITableMemberBusiSV iTableMemberBusiSV;

    @Autowired
    ITableMemberGoodsBusiSV iTableMemberGoodsBusiSV;

    @Autowired
    ITableGoodsBusiSV iTableGoodsBusiSV;

    @Autowired
    IMemberAccountDetailBusiSV iMemberAccountDetailBusiSV;

    @Autowired
    ITableMemberLevelChangeDetailBusiSV iTableMemberLevelChangeDetailBusiSV;

    @Autowired
    TableOrderMapperDZ iTableOrderMapperDZ;

    @Autowired
    UserService userService;

    /**
     * 创建记录
     * @param bo
     * @return
     */
    @Override
    public TableOrder insert(TableOrder bo) {
        log.info("创建订单参数bo：{}", JSON.toJSON(bo));
        Date sysdate = DateUtils.getSysDate();
        bo.setCreateTime(sysdate);
        bo.setOrderId(DateUtils.getYYYYMMDDHHMISS(sysdate)+(int)(Math.random()*10000));
        iTableOrderMapper.insert(bo);
        return bo;
    }

    @Override
    public TableOrder update(TableOrder bo) {
        if (this.iTableOrderMapper.updateByPrimaryKeySelective(bo) == 1) {
            return bo;
        }
        return null;
    }

    /**
     * 根据安置人删除会员相关信息
     */
//    private void remove(String arrangeId, String notRemoveMemberId) {
//        TableMember tableMember = new TableMember();
//        tableMember.setState(InvestConstants.MemberState.INIT);
//        tableMember.setArrangeId(arrangeId);
//        List<TableMember> tableMembers = this.iTableMemberBusiSV.queryList(tableMember);
//        for (TableMember tmp : tableMembers) {
//            if (tmp.getMemberId().equals(notRemoveMemberId)) {
//                continue;
//            }
//
//        }
//    }

    @Override
    public TableOrder delete(TableOrder bo) {
        //查订单
        bo = this.select(bo);
        if (bo == null) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "订单不存在！", "", null);
        }
        if (1 != bo.getState()) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "只允许删除待发货状态订单，订单当前状态不允许删除！", "", null);
        }
        if (InvestConstants.GoodsClass.BONUS_TYPE_0.equals(bo.getGoodsClass()+"")) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "报单商品订单不允许删除！", "", null);
        }
        if (bo.getPrice() != null && bo.getPrice().floatValue() > 0) {
            //退回金额(现金)
            this.iMemberAccountDetailBusiSV.changeMoney(bo.getMemberId(),
                    "1",
                    bo.getPrice().floatValue(),
                    "删除订单",
                    1);
        }
        if (bo.getScorePrice() != null && bo.getScorePrice() > 0) {
            //退回金蛋
            this.iMemberAccountDetailBusiSV.changeMoney(bo.getMemberId(),
                    "1",
                    (float)bo.getScorePrice(),
                    "删除订单",
                    1);
        }

        bo.setState(4);
        this.update(bo);
        return bo;
    }

    @Override
    public TableOrder select(TableOrder bo) {
        return this.iTableOrderMapper.selectByPrimaryKey(bo.getId());
    }

    @Override
    public TableOrder selectByOrderId(String orderId) {
        TableOrderExample example = new TableOrderExample();
        TableOrderExample.Criteria criteria = example.createCriteria();

        criteria.andOrderIdEqualTo(orderId);

        List<TableOrder> tableOrderLIst = this.iTableOrderMapper.selectByExample(example);

        if (tableOrderLIst.size() == 1) {
            return tableOrderLIst.get(0);
        }

        return null;
    }

    @Override
    public TableOrder buy(TableOrder bo) {

        /**
         * 如果操作员和会员不相同，则从操作员扣款，其他操作均为会员信息
         */
        //------------------------------------设置操作人员/会员开始---------------------------------------------------------
        String operId = bo.getOperMemberId();
        if (StringUtils.isEmpty(operId)) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "操作会员ID不能为空！", "", null);
        }

        TableMember operMember = this.iTableMemberBusiSV.selectByMemberId(operId);
        TableMember member;

        //判断操作员和会员是否是同一人
        if (StringUtils.isNotEmpty(bo.getMemberId()) && !operId.equals(bo.getMemberId())) {
            bo.setOrderType("2");
            member = this.iTableMemberBusiSV.selectByMemberId4BuyOrder(bo.getMemberId());
        } else {
            bo.setOrderType("1");
            member = operMember;
        }
        //------------------------------------设置操作人员/会员完成---------------------------------------------------------

        //计算总金额
        TableGoods goods = new TableGoods();
        goods.setId(bo.getGoodsId());
        goods = this.iTableGoodsBusiSV.select(goods);

        if (StringUtils.isEmpty(bo.getMemberId())){
            bo.setMemberId(member.getMemberId());
        }
        bo.setGoodsClass(goods.getGoodsClass());
        bo.setGoodsId(goods.getId());
        bo.setGoodsName(goods.getGoodsName());
        bo.setState(1);

        //设置收货人信息
        if (StringUtils.isEmpty(bo.getReceiverName())){
            bo.setReceiverName(operMember.getReceiverName());
        }
        if (StringUtils.isEmpty(bo.getReceiverPhone())){
            bo.setReceiverPhone(operMember.getReceiverPhone());
        }
        if (StringUtils.isEmpty(bo.getReceiverAddr())){
            bo.setReceiverAddr(operMember.getAddr());
        }

        if (StringUtils.isEmpty(bo.getReceiverAddr())){
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "收货地址不能为空！", "", null);
        }
        if (StringUtils.isEmpty(bo.getReceiverName())) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "收货人姓名不能为空！", "", null);
        }
        if (StringUtils.isEmpty(bo.getReceiverPhone())) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "收货人电话不能为空！", "", null);
        }

        //订单金额
        Float price = bo.getAmount() * goods.getPrice();

        //是否购买了金鸡商品
        if (StringUtils.isNotEmpty(bo.getExtentGoodsId())) {
            //计算金鸡商品价格
            TableGoods ex = new TableGoods();
            ex.setId(Integer.parseInt(bo.getExtentGoodsId()));
            ex = this.iTableGoodsBusiSV.select(ex);
            price += ex.getPrice() * bo.getExtentGoodsCount();
            bo.setExtentGoodsPrice(new BigDecimal(ex.getPrice()));
        }
        //购买商品单价
        bo.setGoodsPrice(new BigDecimal(goods.getPrice()));
        //订单金额
        bo.setPrice(new BigDecimal(price));
        //商品积分单价
        bo.setGoodsScorePrice(goods.getScorePrice()==null?0:goods.getScorePrice());
        //计算总积分
        bo.setScorePrice(bo.getAmount() * (goods.getScorePrice()==null?0:goods.getScorePrice()));

        String remark = "商品购买（"+goods.getGoodsName()+"）";

        //购买报单商品，最后一次购买的订单
        TableOrder lastOrder = null;

        //报单商品：
        if (0 == goods.getGoodsClass()) {

            //------------------升级购买操作时，额外判断下会员注册时间，注册时间大于1个月的，也不可以购买报单商品-------------------------
            int days = Integer.parseInt(DictUtils.getDictValue("允许升级天数","updateLevelDays", "30"));
            if(DateUtils.addDays(member.getRegisterTime(), days).getTime() - DateUtils.getSysDate().getTime() < 0) {
                throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "超过升级时限！", "", null);
            }
            //------------------升级购买操作时，额外判断下会员注册时间，注册时间大于1个月的，也不可以购买报单商品-------------------------

            //报单商品只能购买一个
            if (bo.getAmount() > 1) {
                throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "报单商品只允许购买一个！", "", null);
            }

            boolean firstBuy = false;

            //查询购买的报单商品
            TableOrder queryTableOrder = new TableOrder();
            queryTableOrder.setMemberId(member.getMemberId());
            queryTableOrder.setGoodsClass(Integer.parseInt(InvestConstants.GoodsClass.BONUS_TYPE_0));
            List<TableOrder> tableOrderList = this.queryList(queryTableOrder, null);

            if(tableOrderList != null && tableOrderList.size() >= 2) {
                throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "购买报单商品数量已达上限！", "", null);
            }

            //订单金额
            Float orderPrice = goods.getPrice();
            Float incomeDjPoint = goods.getIncomeDjPoint();
            Float incomeJysPoint = goods.getIncomeJysPoint();
            Integer incomeVipLevel = goods.getIncomeVipLevel();
            BigDecimal totalPrice = new BigDecimal(orderPrice);

            //首次购买报单商品
            if (CollectionUtils.isEmpty(tableOrderList)) {
                firstBuy = true;
            }else{
                //非首次购买报单商品，计算累计购买报单商品总金额
                lastOrder = tableOrderList.get(0);
                //累计历史订单+本地订单总金额

                for (TableOrder tmp : tableOrderList) {
                    totalPrice = totalPrice.add(tmp.getPrice());
                }
                TableGoods bdQuery = new TableGoods();
                bdQuery.setState(InvestConstants.GoodsState.effect);
                bdQuery.setGoodsClass(Integer.parseInt(InvestConstants.GoodsClass.BONUS_TYPE_0));
                List<TableGoods> upGoods = this.iTableGoodsBusiSV.queryList(bdQuery, " PRICE DESC");
                //计算历史总额能到达哪个报单商品金额等级
                for (TableGoods tmp : upGoods) {
                    if (totalPrice.floatValue() >= tmp.getPrice()) {
                        incomeVipLevel = tmp.getIncomeVipLevel();
                        break;
                    }
                }
                firstBuy = false;
            }

            bo.setPrice(new BigDecimal(orderPrice));
            //当时购买商品定义的值
            bo.setIncomeJysPoint(new BigDecimal(goods.getIncomeJysPoint()));
            //当时购买商品定义的值
            bo.setIncomeDjPoint(new BigDecimal(goods.getIncomeDjPoint()));
            if ("2".equals(bo.getOrderType())) {
                remark = "替会员【"+member.getMemberId()+"】购买报单商品（"+goods.getGoodsName()+"）";
            }else{
                remark = "报单商品购买（"+goods.getGoodsName()+"）";
            }

            if (member.getLevel().intValue() > incomeVipLevel) {
                throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "会员当前级别高于购买报单商品进行升级的会员级别，购买失败！", "", null);
            }

            //已经是激活状态
            if (member.getState().equals(InvestConstants.MemberState.VALID)) {
                firstBuy = false;
            }
            //修改会员信息
            member.setLevel(incomeVipLevel);
            member.setReportingAmount(bo.getGoodsPrice().add(member.getReportingAmount()));
            //当前系统时间.month + 2 , day = 1号
            member.setPerfomanceTime(DateUtils.getFirstDayOfMonth(DateUtils.addMonths(DateUtils.getSysDate(), 2)));
            //更新会员状态为激活
            member.setState(firstBuy?InvestConstants.MemberState.VALID:null);
            member.setAccountDjPoint(member.getAccountDjPoint().add(new BigDecimal(incomeDjPoint)));
            member.setAccountJsyPoint(member.getAccountJsyPoint().add(new BigDecimal(incomeJysPoint)));


            if (firstBuy) {
                //修改会员为可登录
                UserInfo userInfo = userService.getUserByUserName(member.getMemberId());
                userInfo.setLoginFlag("1");
                userService.update(userInfo, new HashMap());
                member.setLoginFlag("1");
            }

            this.iTableMemberBusiSV.update(member, firstBuy);

            //删除初始化，并且安置人与当前会员相同的会员信息
            //this.remove(member.getMemberId());

            /*
                【逻辑2】
                更新table_member_level_change_detail表，记录会员级别变更履历
                remark = “报单商品购买”
            */
            iTableMemberLevelChangeDetailBusiSV.changeLevel(member.getMemberId(), incomeVipLevel, "报单商品购买");
        } else if (3 == goods.getGoodsClass()) {
            //单独购买金鸡商品
            //设置购买金鸡商品ID=当前购买商品ID（金鸡）
            bo.setExtentGoodsId(bo.getGoodsId()+"");
            bo.setExtentGoodsCount(1);
            bo.setState(3);//金鸡商品默认发货
            bo.setExpressNumber("M0000000000000000");
            bo.setExpressCompany("金鸡虚拟商品");
            bo.setSendTime(DateUtils.getSysDate());
        }

        //扣减金额
        if (bo.getPrice().floatValue() > 0) {
            this.iMemberAccountDetailBusiSV.changeMoney(
                    operMember.getMemberId(),
                    "2",
                    bo.getPrice().floatValue(),
                    remark,
                    null);
        }
        //扣减积分
        if (bo.getScorePrice() > 0) {
            this.iMemberAccountDetailBusiSV.changeMoney(
                    operMember.getMemberId(),
                    "2",
                    (float)bo.getScorePrice(),
                    remark,
                    6);
        }

        //生成订单
        bo = this.insert(bo);

        //订单额外购买金鸡产品，把金鸡商品插入用户商品表
        if (StringUtils.isNotEmpty(bo.getExtentGoodsId())) {
            log.info("订单额外购买金鸡产品，把金鸡商品插入用户商品表");
            createMemberGoods(Integer.parseInt(bo.getExtentGoodsId()), bo.getExtentGoodsCount(), bo, InvestConstants.MemberGoodsType.type2);
        }
        //购买的商品存在金鸡产品，把金鸡商品插入用户商品表
        if (StringUtils.isNotEmpty(goods.getExtentGoodsId())) {
            //购买的商品存在金鸡产品，把金鸡商品插入用户商品表
            log.info("购买的商品存在金鸡产品，把金鸡商品插入用户商品表");
            int extentGoodsCount = 0;
            extentGoodsCount = goods.getExtentGoodsCount();
            bo.setExtentGoodsId(goods.getExtentGoodsId());
            bo.setExtentGoodsCount(extentGoodsCount);
            bo.setExtentGoodsPrice(new BigDecimal(0));//赠送的金鸡，金额为0
            createMemberGoods(Integer.parseInt(goods.getExtentGoodsId()), extentGoodsCount, bo, InvestConstants.MemberGoodsType.type1);
        }

        bo = this.update(bo);

        return bo;
    }

    /**
     * 计算报单商品订单金额
     * @param bo
     */
    public void getBDGoodsOrderPrice(TableOrder bo){

        //本次购买的商品
        TableGoods goods = new TableGoods();
        goods.setId(bo.getGoodsId());
        goods = this.iTableGoodsBusiSV.select(goods);
        //不是报单商品直接跳出
        if (goods.getGoodsClass() != 0) {
            return;
        }

        TableMember operMember = this.iTableMemberBusiSV.selectByMemberId(bo.getOperMemberId());
        TableMember member;

        //判断操作员和会员是否是同一人
        if (StringUtils.isNotEmpty(bo.getMemberId()) && !bo.getOperMemberId().equals(bo.getMemberId())) {
            member = this.iTableMemberBusiSV.selectByMemberId4BuyOrder(bo.getMemberId());
        } else {
            member = operMember;
        }

        //------------------升级购买操作时，额外判断下会员注册时间，注册时间大于1个月的，也不可以购买报单商品-------------------------
        if(DateUtils.addDays(member.getRegisterTime(), 30).getTime() - DateUtils.getSysDate().getTime() < 0) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "超过升级时限！", "", null);
        }
        //------------------升级购买操作时，额外判断下会员注册时间，注册时间大于1个月的，也不可以购买报单商品-------------------------

        //报单商品只能购买一个
        if (bo.getAmount() > 1) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "报单商品只允许购买一个！", "", null);
        }


        //查询最后一次购买的报单商品
        TableOrder queryTableOrder = new TableOrder();
        queryTableOrder.setMemberId(member.getMemberId());
        queryTableOrder.setGoodsClass(Integer.parseInt(InvestConstants.GoodsClass.BONUS_TYPE_0));
        List<TableOrder> tableOrderList = this.queryList(queryTableOrder, null);

        if(tableOrderList != null && tableOrderList.size() >= 2) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "购买报单商品数量已达上限！", "", null);
        }

        //订单金额
        Float orderPrice = goods.getPrice();

        bo.setPrice(new BigDecimal(orderPrice));
    }

    /**
     *
     * @param exGoogsId
     * @param exGoodsCount 单个商品赠送金鸡数量
     * @param order
     * @param memberGoodsType
     */
    public void createMemberGoods(int exGoogsId, int exGoodsCount, TableOrder order, String memberGoodsType){
        log.info("把金鸡商品插入用户商品表");
        //查询商品属性
        TableGoods ex = new TableGoods();
        ex.setId(exGoogsId);
        ex = this.iTableGoodsBusiSV.select(ex);

        TableMemberGoods memberGoods = new TableMemberGoods();
        memberGoods.setMemberId(order.getMemberId());
        memberGoods.setOrderId(order.getOrderId());
        memberGoods.setGoodsId(ex.getId());
        memberGoods.setGoodsClass(ex.getGoodsClass());
        memberGoods.setAddScoreByonegoods(ex.getAddScore());
        memberGoods.setAmount(exGoodsCount * order.getAmount());  //每个商品赠送金鸡数 * 商品数量
        memberGoods.setCreateTime(DateUtils.getSysDate());
        memberGoods.setType(memberGoodsType);
        memberGoods.setAddCount(0);
        this.iTableMemberGoodsBusiSV.insert(memberGoods);
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
    public PageInfo<TableOrder> queryListPage(TableOrder bo, Integer pageNum, Integer pageSize, Map<String ,Object> map) {
        log.info("获取订单参数bo：{}", JSON.toJSON(bo));
        log.info("获取订单参数pageNum：{}", pageNum);
        log.info("获取订单参数pageSize：{}", pageSize);
        log.info("获取订单参数map：{}", JSON.toJSON(map));
        
        TableOrderExample example = new TableOrderExample();
        TableOrderExample.Criteria criteria = example.createCriteria();

        if (null != bo.getId()) {
            criteria.andIdEqualTo(bo.getId());
        }
        if (StringUtils.isNotEmpty(bo.getOrderId())) {
            criteria.andOrderIdEqualTo(bo.getOrderId());
        }
        if (StringUtils.isNotEmpty(bo.getMemberId())) {
            criteria.andMemberIdEqualTo(bo.getMemberId());
        }
        if (null != bo.getGoodsId()) {
            criteria.andGoodsIdEqualTo(bo.getGoodsId());
        }
        if (StringUtils.isNotEmpty(bo.getGoodsName())) {
            criteria.andGoodsNameEqualTo(bo.getGoodsName());
        }
        if (null != bo.getGoodsClass()) {
            criteria.andGoodsClassEqualTo(bo.getGoodsClass());
        }
        if (null != bo.getCreateTime()) {
            criteria.andCreateTimeEqualTo(bo.getCreateTime());
        }
        if (null != bo.getState()) {
            criteria.andStateEqualTo(bo.getState());
        }
        if (StringUtils.isNotEmpty(bo.getExpressNumber())) {
            criteria.andExpressNumberEqualTo(bo.getExpressNumber());
        }

        if (null != map) {
            if (null != map.get("start")) {
                criteria.andCreateTimeGreaterThanOrEqualTo((Date)map.get("start"));
            }
            if (null != map.get("end")) {
                criteria.andCreateTimeLessThanOrEqualTo((Date)map.get("end"));
            }
        }

        example.setOrderByClause("create_time desc");

        PageInfo<TableOrder> pageInfo = PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(() -> this.iTableOrderMapper.selectByExample(example));
        log.info("获取订单结果：{}", JSON.toJSON(pageInfo));
        return pageInfo;
    }

    @Override
    public List<TableOrder> queryList(TableOrder bo, Map<String, Object> map) {
        log.info("获取订单参数bo：{}", JSON.toJSON(bo));
        log.info("获取订单参数map：{}", JSON.toJSON(map));

        TableOrderExample example = new TableOrderExample();
        TableOrderExample.Criteria criteria = example.createCriteria();

        if (null != bo.getId()) {
            criteria.andIdEqualTo(bo.getId());
        }
        if (StringUtils.isNotEmpty(bo.getOrderId())) {
            criteria.andOrderIdEqualTo(bo.getOrderId());
        }
        if (StringUtils.isNotEmpty(bo.getMemberId())) {
            criteria.andMemberIdEqualTo(bo.getMemberId());
        }
        if (null != bo.getGoodsId()) {
            criteria.andGoodsIdEqualTo(bo.getGoodsId());
        }
        if (null != bo.getGoodsClass()) {
            criteria.andGoodsClassEqualTo(bo.getGoodsClass());
        }
        if (null != bo.getCreateTime()) {
            criteria.andCreateTimeEqualTo(bo.getCreateTime());
        }
        if (null != bo.getState()) {
            criteria.andStateEqualTo(bo.getState());
        }
        if (StringUtils.isNotEmpty(bo.getExpressNumber())) {
            criteria.andExpressNumberEqualTo(bo.getExpressNumber());
        }

        if (null != map) {
            if (null != map.get("start")) {
                criteria.andCreateTimeGreaterThanOrEqualTo((Date)map.get("start"));
            }
            if (null != map.get("end")) {
                criteria.andCreateTimeLessThanOrEqualTo((Date)map.get("end"));
            }
        }

        example.setOrderByClause("create_time desc, id desc");
        return this.iTableOrderMapper.selectByExample(example);
    }

    @Override
    public TableOrderDZ selectByGroup(TableOrderDZ bo, Integer pageNum, Integer pageSize, Map<String, Object> map) {
        return this.iTableOrderMapperDZ.selectByGroup(bo.getMemberId(), bo.getStart(), bo.getEnd());
    }

    /**
     * 总业绩=》所有订单表（报单商品、复消商品、金鸡商品）提货商品的不算，被删除的（退货订单）不算，所有这些符合条件的订单，时间也是截至到昨日的订单金额求和
     *
     * @return
     */
    @Override
    public TableOrderDZ selectByGroup2(String memberId, Date start, Date end) {
        return this.iTableOrderMapperDZ.selectByGroup2(memberId, start, end);
    }

    /**
     * 13、结算管理菜单中加入一个子菜单“管理业绩分析”
     * 进入界面后输入会员编号和时间段（开始时间和结束时间），“分析”按钮
     * 列表为左区和右区，点击分析按钮查询该会员在设定时间段内的左区和右区的业绩
     * 业绩计算方法：根据网体结构找到会员的所有子节点，根据时间段获取该时间段内这些会员子节点的所有报单商品的订单求和，左区的所有会员订单求和后显示在左区，右区的所有订单求和后显示在右区
     *
     * @param memberList
     * @param start
     * @param end
     * @return
     */
    @Override
    public List<TableOrderDZ> selectByGroup3(List<String> memberList, Date start, Date end) {
        return this.iTableOrderMapperDZ.selectByGroup3(memberList, start, end);
    }

    /**
     * 导出订单
     *
     * @param bo
     * @param pageNum
     * @param pageSize
     * @param map
     * @return
     */
    @Override
    public HSSFWorkbook exportFile(TableOrder bo, Integer pageNum, Integer pageSize, Map<String, Object> map) {

        PageInfo<TableOrder> pageInfo = this.queryListPage(bo, 1, 10000000, map);

        List<TableOrder> list = new ArrayList<>();
        if (pageInfo != null && !CollectionUtils.isEmpty(pageInfo.getList())) {
            list = pageInfo.getList();
        } else {
            System.out.println("没有数据");
        }

        String sheetName = "传输计划";
        String[] title = {"ID", "快递单号", "物流公司", "订单编号", "会员编号", "商品ID", "商品名", "商品数量", "总金额", "订单时间", "状态", "收货人姓名", "收货人电话", "收货人地址"};
        String[][] values = new String[list.size()+1][title.length];

        int i = 0;
        for (TableOrder order : list) {
            values[i][0] = "ID"+String.valueOf(order.getId());
            values[i][1] = order.getExpressNumber();
            values[i][2] = order.getExpressCompany();
            values[i][3] = order.getOrderId();
            values[i][4] = order.getMemberId();
            values[i][5] = String.valueOf(order.getGoodsId());
            values[i][6] = String.valueOf(order.getGoodsName());
            values[i][7] = String.valueOf(order.getAmount());
            values[i][8] = String.valueOf(order.getPrice());
            values[i][9] = DateUtils.formatDateTime(order.getCreateTime());
            String state = "";
            if (1 == order.getState()) {
                state = "等待发货";
            } else if (3 == order.getState()) {
                state = "发货完成";
            }
            values[i][10] = state;
            values[i][11] = order.getReceiverName();
            values[i][12] = order.getReceiverPhone();
            values[i][13] = order.getReceiverAddr();
            i++;
        }

        HSSFWorkbook wb = ExcelUtils.getHSSFWorkbook(sheetName, title, values, null);
        return wb;
    }

    /**
     * 批量发货
     *
     * @param orderList
     * @return
     */
    @Override
    public List<TableOrder> sendOrder(List<TableOrder> orderList) {
        List<TableOrder> list = new ArrayList<>();
        for (TableOrder tableOrder : orderList) {
            if (StringUtils.isNotEmpty(tableOrder.getExpressNumber())) {
                //查询订单
                TableOrder orig = this.selectByOrderId(tableOrder.getOrderId());
                if (null == orig) {
                    throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "订单编号="+tableOrder.getOrderId()+"未查询到订单信息，请确认订单编号是否正确", "", null);
                }

                if (orig.getState() == 3) {
                    continue;
                }

                if (StringUtils.isNotEmpty(orig.getExpressNumber())) {
                    continue;
                }

                TableOrder update = new TableOrder();
                update.setId(tableOrder.getId());
                update.setState(3);
                update.setSendTime(DateUtils.getSysDate());
                update.setExpressNumber(tableOrder.getExpressNumber());
                update.setExpressCompany(tableOrder.getExpressCompany());
                this.update(update);
                list.add(update);
            }

        }

        return list;
    }
}
