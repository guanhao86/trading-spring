package com.spring.fee.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.fee.constants.InvestConstants;
import com.spring.fee.dao.mapper.TWheatFinancialMapper;
import com.spring.fee.dao.mapper.TWheatFinancialOrderMapper;
import com.spring.fee.model.*;
import com.spring.fee.service.*;
import com.spring.free.util.DateUtils;
import com.spring.free.util.OSSClientUtil;
import com.spring.free.util.constraints.Global;
import com.spring.free.util.exception.ExceptionCodeEnum;
import com.spring.free.util.exception.RestException;
import com.spring.free.util.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 会员服务
 */
@Slf4j
@Service
@Transactional
public class TWheatFinancialBusiSVImpl implements ITWheatFinancialBusiSV {

    @Autowired
    TWheatFinancialMapper tWheatFinancialMapper;
    @Autowired
    TWheatFinancialOrderMapper tWheatFinancialOrderMapper;
    @Autowired
    private ITWheatMemberBusiSV twheatMemberBusiSV;
    @Autowired
    ITWheatAccountBusiSV itWheatAccountBusiSV;
    @Autowired
    private ITWheatFinancialOrderBusiSV tTWheatFinancialOrderBusiSV;
    @Autowired
    ITWheatAccountDetailBusiSV itWheatAccountDetailBusiSV;
    @Autowired
    ITWheatFinancialStreamBusiSV iTWheatFinancialStreamBusiSV;



    @Override
    public TWheatFinancial insert(TWheatFinancial tWheatFinancial) {
        tWheatFinancial.setCreateTime(DateUtils.getSysDate());
        if (1 == this.tWheatFinancialMapper.insert(tWheatFinancial)) {
            return tWheatFinancial;
        }
        return null;
    }

    @Override
    public TWheatFinancial insert(TWheatFinancial tWheatFinancial, MultipartFile postPic, Map map) {
        String imge = upload(postPic);
        System.out.print(imge);
        if(imge==null){
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "更新图片信息失败", map.get(Global.URL).toString(), map);
        }
        tWheatFinancial.setImg(imge);
        tWheatFinancial.setCreateTime(DateUtils.getSysDate());
        if (1 == this.tWheatFinancialMapper.insert(tWheatFinancial)) {
            return tWheatFinancial;
        }
        return null;
    }

    @Override
    public TWheatFinancial update(TWheatFinancial tWheatFinancial) {
        if (1 == tWheatFinancialMapper.updateByPrimaryKeySelective(tWheatFinancial)) {
            return tWheatFinancial;
        }
        return null;
    }

    @Override
    public TWheatFinancial update(TWheatFinancial tWheatFinancial, MultipartFile postPic, Map map) {
        String imge = upload(postPic);
        System.out.print(imge);
        if(imge==null){
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "更新图片信息失败", map.get(Global.URL).toString(), map);
        }
        tWheatFinancial.setImg(imge);
        if (1 == tWheatFinancialMapper.updateByPrimaryKeySelective(tWheatFinancial)) {
            return tWheatFinancial;
        }
        return null;
    }

    @Override
    public TWheatFinancial delete(TWheatFinancial tWheatFinancial) {
        if (1 == tWheatFinancialMapper.deleteByPrimaryKey(tWheatFinancial.getId())) {
            return tWheatFinancial;
        }
        return null;
    }

    @Override
    public TWheatFinancial select(TWheatFinancial tWheatFinancial) {
        return tWheatFinancialMapper.selectByPrimaryKey(tWheatFinancial.getId());
    }

    @Override
    public List<TWheatFinancial> queryList(TWheatFinancial tWheatFinancial, String[] orderBy) {
        TWheatFinancialExample example = new TWheatFinancialExample();
        TWheatFinancialExample.Criteria criteria = example.createCriteria();

        if(tWheatFinancial.getDueTime()!=null){
            criteria.andDueTimeGreaterThan(new Date());
        }
        criteria.andDelFlagEqualTo(0);
        if (null != tWheatFinancial.getId())
            criteria.andIdEqualTo(tWheatFinancial.getId());

        if (null != tWheatFinancial.getState())
            criteria.andStateEqualTo(tWheatFinancial.getState());

        if (StringUtils.isNotEmpty(tWheatFinancial.getProductName()))
            criteria.andProductNameLike("%"+tWheatFinancial.getProductName()+"%");

        if (orderBy != null && orderBy.length > 0) {
            String strOrderBy = "";
            for (String orderByTemp : orderBy) {
                strOrderBy = strOrderBy + "," + orderByTemp;
            }
            strOrderBy.substring(1);
            example.setOrderByClause(" "+strOrderBy);
        }

        return tWheatFinancialMapper.selectByExample(example);
    }
    public String upload( MultipartFile pic){
        OSSClient ossClient = new OSSClient(OSSClientUtil.Endpoint, OSSClientUtil.AccessKeyId, OSSClientUtil.AccessKeySecret);
        // 上传文件流。
        String fileExt = getExtensionName(pic.getOriginalFilename());
        String imageName = System.nanoTime()+"";
        String fileName = imageName + "." + fileExt;
        try{
            ossClient.putObject(OSSClientUtil.BucketName, fileName, pic.getInputStream());
        }catch(Exception e){

            System.out.println("上传失败，写入文件失败，"+e.getMessage());
            e.printStackTrace();
            return null;

        }

        String realpath = OSSClientUtil.bucketCDN+"/"+fileName;

        // 关闭OSSClient。
        ossClient.shutdown();

        return realpath;
    }
    /**
     * Java文件操作 获取文件扩展名
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    @Override
    public PageInfo<TWheatFinancial> pageList(TWheatFinancial entity, Integer page, Integer pageSize) {
        if(page != null && pageSize != null){
            PageHelper.startPage(page, pageSize);
        }
        List<TWheatFinancial> findList = this.queryList(entity,null);
        return new PageInfo<TWheatFinancial>(findList);
    }

    public JSONObject createOrder (String memberId,Long id,  Integer num){
        //返回体
        JSONObject jsonObj=new JSONObject();
        TWheatFinancial tWheatFinancial = new TWheatFinancial();
        tWheatFinancial.setId(id);
        TWheatFinancial bean = select(tWheatFinancial);

        if(bean==null){
            jsonObj.put("result","01");
            jsonObj.put("desc","理财产品不存在！");
            return jsonObj;
        }
        if(bean.getAllNumber()-bean.getShelledNumber()<num){
            jsonObj.put("result","01");
            jsonObj.put("desc","理财份额不足！");
            return jsonObj;
        }

        if(bean.getDueTime().before(new Date())){
            jsonObj.put("result","01");
            jsonObj.put("desc","理财购买时间已截止！");
            return jsonObj;
        }



        TWheatMember member = twheatMemberBusiSV.selectByMemberId(memberId);
        if(member==null){
            jsonObj.put("result","01");
            jsonObj.put("desc","用户不存在！");
            return jsonObj;
        }

        TWheatAccount tWheatAccount = this.itWheatAccountBusiSV.selectByMember(memberId);
        if(tWheatAccount==null){
            jsonObj.put("result","01");
            jsonObj.put("desc","账户不存在！");
            return jsonObj;
        }
        if(tWheatAccount.getAvailable()==null||tWheatAccount.getAvailable()<bean.getOncePrice()*num){
            jsonObj.put("result","01");
            jsonObj.put("desc","账户可用资金不足！");
            return jsonObj;
        }


        TWheatFinancialOrder order = new TWheatFinancialOrder();
        order.setMemId(member.getId());
        order.setMemberId(memberId);
        order.setPhone(member.getPhone());
        Long orderCode=Long.valueOf(new Date().getTime()+ Long.parseLong(memberId));
        order.setOrderCode(orderCode+"");
        order.setFinancialId(id);
        order.setProductName(bean.getProductName());
        order.setCycle(bean.getCycle());
        order.setYearProfit(bean.getYearProfit());
        order.setMonthProfit(bean.getMonthProfit());
//        order.setBonusDay(11);
        order.setDueTime(plusDay(360));
        order.setBuyNumber(num);
        order.setOncePrice(bean.getOncePrice());
        order.setTotalPrice(bean.getOncePrice()*num);
        order.setInterest(0l);
        order.setCreateTime(new Date());
        order.setModifyTime(new Date());
        order.setDelFlag(0);
        int flag = tWheatFinancialOrderMapper.insert(order);
        if(flag==1){

            //修改销售数量
            bean.setShelledNumber(bean.getShelledNumber()+num);
            update(bean);

            //理财冻结
            itWheatAccountBusiSV.modifyAcct(order.getMemberId(),bean.getOncePrice()*num,
                    InvestConstants.Account.DETAIL_OPERTYPE_FREEZE,InvestConstants.Account.DETAIL_TYPE_MONEY,null,null);


//            tWheatAccount.setAvailable(available-bean.getOncePrice()*num);
//            tWheatAccount.setFreeze(freeze+bean.getOncePrice()*num);
//            tWheatAccount.setMoneyFreeze(moneyFreeze+bean.getOncePrice()*num);
//            TWheatAccount  account = itWheatAccountBusiSV.update(tWheatAccount);
//            if(account==null){
//                throw new RestException(ExceptionCodeEnum.RESTMEM_ERROR_CODE.getCode(), ExceptionCodeEnum.RESTMEM_ERROR_CODE.getMsg()+":账户扣减失败！");
//            }
//            //插入账户明细
//            TWheatAccountDetail tWheatAccountDetail = new TWheatAccountDetail();
//            tWheatAccountDetail.setAcctId(tWheatAccount.getId());
//            tWheatAccountDetail.setAmount(bean.getOncePrice()*num);
//            tWheatAccountDetail.setMemberId(tWheatAccount.getMemberId());
//            tWheatAccountDetail.setChangeType("2");//1、解冻  2、冻结  3、提现  4、充值  5利息　6奖金
//            tWheatAccountDetail.setType("1");//0、其他 1、理财，2、粮仓
//            tWheatAccountDetail.setRemark("理财");//备注
//            tWheatAccountDetail.setBeforeTotal(tWheatAccount.getTotal());
//            tWheatAccountDetail.setAfterTotal(tWheatAccount.getTotal());
//            tWheatAccountDetail.setBeforeGranaryIngFreeze(freeze);
//            tWheatAccountDetail.setAfterGranaryIngFreeze(moneyFreeze+bean.getOncePrice()*num);
//            this.itWheatAccountDetailBusiSV.insert(tWheatAccountDetail);
        }else{
            jsonObj.put("result","01");
            jsonObj.put("desc","订单创建失败！");
            return jsonObj;
        }

        jsonObj.put("result","00");
        jsonObj.put("desc","操作成功！");
        return jsonObj;
    }

    /**
     * 当前日期加上天数后的日期
     * @param num 为增加的天数
     * @return
     */
     public static Date plusDay(int num){
         Date d = new Date();
         SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         String currdate = format.format(d);
         System.out.println("现在的日期是：" + currdate);

         Calendar ca = Calendar.getInstance();
         ca.add(Calendar.DATE, num);// num为增加的天数，可以改变的
         d = ca.getTime();
         String enddate = format.format(d);
         System.out.println("增加天数以后的日期：" + enddate);
         try {
             return format.parse(enddate);
         }catch (Exception e){
             return null;
         }

     }
    public JSONObject financialMoney (String memberId){
        //返回体
        JSONObject jsonObj=new JSONObject();

        TWheatFinancialOrderExample example = new TWheatFinancialOrderExample();
        TWheatFinancialOrderExample.Criteria criteria = example.createCriteria();

        criteria.andDelFlagEqualTo(0);
            criteria.andMemberIdEqualTo(memberId);



        List<TWheatFinancialOrder> list = tWheatFinancialOrderMapper.selectByExample(example);

        Long total = 0l;
        for(TWheatFinancialOrder tWheatFinancialOrder :list){
            total = total + tWheatFinancialOrder.getInterest();
        }

        jsonObj.put("result","00");
        jsonObj.put("total",total);
        jsonObj.put("desc","操作成功！");
        return jsonObj;

    }

    @Override
    public PageInfo<TWheatFinancialOrder> orderPageList(String memberId ,Integer page, Integer pageSize) {

        TWheatFinancialOrder order = new TWheatFinancialOrder();
        order.setMemberId(memberId);

        PageInfo<TWheatFinancialOrder>  pageInfo = tTWheatFinancialOrderBusiSV.pageList(order,page,pageSize);

        return pageInfo;
    }

    @Override
    public List<TWheatFinancialStream> streamList(String memberId) {

        TWheatFinancialStream stream = new TWheatFinancialStream();
        stream.setMemberId(memberId);

        List<TWheatFinancialStream>  streamList = iTWheatFinancialStreamBusiSV.queryList(stream,null);

        return streamList;
    }

    public void dealTWheatFinancial (int day){
        TWheatFinancialExample example = new TWheatFinancialExample();
        TWheatFinancialExample.Criteria criteria = example.createCriteria();

        List<TWheatFinancial> list = tWheatFinancialMapper.selectByExample(example);
        if(list!=null&&list.size()>0){
            for(TWheatFinancial tWheatFinancial :list){
                log.info("========理财产品========::"+tWheatFinancial.getId()+"--"+tWheatFinancial.getProductName()+"_____START");

                if(tWheatFinancial.getBonusDay().intValue()==day){
                    log.info("========理财产品处理日期========:"+tWheatFinancial.getBonusDay()+"--当前日期:"+day);
                    TWheatFinancialOrder tWheatFinancialOrder = new TWheatFinancialOrder();
                    tWheatFinancialOrder.setFinancialId(tWheatFinancial.getId());
                    tWheatFinancialOrder.setDueTime(new Date());
                    List<TWheatFinancialOrder> orderList = tTWheatFinancialOrderBusiSV.queryList(tWheatFinancialOrder,null);
                    if(orderList!=null&&orderList.size()>0){
                        for(TWheatFinancialOrder order :orderList){
                            TWheatMember member = twheatMemberBusiSV.selectByMemberId(order.getMemberId());
                            Double amount = order.getMonthProfit()*order.getTotalPrice()/100;
                            //奖金
                            itWheatAccountBusiSV.modifyAcct(order.getMemberId(),amount.longValue(),
                                    InvestConstants.Account.DETAIL_OPERTYPE_BONUS,InvestConstants.Account.DETAIL_TYPE_MONEY,null,"理财奖金");
                            //变更订单利息
                            order.setInterest(order.getInterest()==null?amount.longValue():order.getInterest()+amount.longValue());
                            tTWheatFinancialOrderBusiSV.update(order);

                            //理财流水
                            TWheatFinancialStream stream = new TWheatFinancialStream();
                            stream.setMemId(member.getId());
                            stream.setMemberId(order.getMemberId());
                            stream.setPhone(order.getPhone());
                            stream.setOrderNo(order.getOrderCode());
                            stream.setFinancialId(order.getFinancialId());
                            stream.setProductName(order.getProductName());
                            stream.setCycle(order.getCycle());
                            stream.setYearProfit(order.getYearProfit());
                            stream.setMonthProfit(order.getMonthProfit());
                            stream.setBuyNumber(order.getBuyNumber().longValue());
                            stream.setBuyTime(order.getCreateTime());
                            stream.setInterest(amount.longValue());
                            stream.setCreateTime(new Date());
                            stream.setModifyTime(new Date());
                            stream.setDelFlag(0);
                            iTWheatFinancialStreamBusiSV.insert(stream);
                        }

                    }else{
                        log.info("========理财产品无订单========");
                    }

                }else {
                    log.info("========理财产品非处理日期========:"+tWheatFinancial.getBonusDay()+"--当前日期"+day);
                }
                log.info("========理财产品========:"+tWheatFinancial.getId()+"--"+tWheatFinancial.getProductName()+"_____END");
            }
        }else{
            log.info("========无理财产品========_____END");
        }


    }
}
