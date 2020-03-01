package com.spring.free.common.domain;/**
 * Created by hengpu on 2019/3/4.
 */

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ClassName TelecomCard
 * @Description //TODO
 * @Author hengpu
 * @Date 2019/3/4 15:25
 * @Version 1.0
 **/
@Setter
@Getter
@ToString
public class TelecomCard {
    /**
     *账号
     */
    private String account;
    /**
     * 店铺类型
     */
    private String shopType;
    /**
     *店铺订单号
     */
    private String shopOrder;
    /**
     * 开卡号码
     */
    private String mobile;
    /**
     * 套餐产品编号
     */
    private String productNo;
    /**
     * 开卡姓名
     */
    private String userName;
    /**
     *身份证号
     */
    private String cardNo;
    /**
     * 收货人
     */
    private String receiveUser;
    /**
     *收货人手机号
     */
    private String receiveMobile;
    /**
     *收货地址
     */
    private String receiveAddress;
    /**
     *正面
     */
    private String frontStr;
    /**
     *反面
     */
    private String backStr;
    /**
     *手持
     */
    private String handStr;
    /**
     *签名
     */
    private String sign;
    /**
     * 用户来源 1­微信;2QQ;3天猫;4京东;5拼多多;20其他;
     */
    private String userType;
    /**
     * 数量
     */
    private String amount;
    /**
     *单价
     */
    private String price;
    /**
     *快照信息
     */
    private String snapshot;
    /**
     *买家备注
     */
    private String buyerRemark;
    /**
     * 商家备注 url编码
     */
    private String remark;
    /**
     * 付款金额
     */
    private String payMoney;

    /**
     * 付款金额
     */
    private String memberId;
    /**
     * 付款金额
     */
    private String name;


}
