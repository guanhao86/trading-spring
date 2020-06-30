package com.spring.free.vo;

import lombok.Data;

@Data
public class QueryReqVO extends PageVO {

    private Integer goodsClass;

    private Integer state;

    //金额
    private float amount;

    //奖金明细类型：
    /**
     * 1	直推奖金
     * 2	组织奖
     * 3	伯乐奖
     * 4	复消奖
     * 5	奖衔奖
     * 6	管理费
     * 7    金蛋
     */
    private Integer bonusId;

    /**
     * 1	现金积分
     * 2	奖金积分（可用）
     * 3	奖金积分（冻结）
     * 4	购物积分
     * 5	保值积分
     * 6	金蛋
     * 10	福利积分
     */
    private Integer bonusType;

    private String oldPassword;

    private String newPassword;
    /**
     * 1:已发放
     * 0：待发放
     */
    private String closeState;

    private String memberId;

}
