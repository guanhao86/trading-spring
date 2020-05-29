package com.spring.free.vo;

import lombok.Data;

/**
 * 转账对象
 */
@Data
public class TransferVO {

    //转账金额
    private String amount;
    //转账给谁
    private String memberId;

    private String remark;
    //转账类型(1：现金    2：金蛋)
    private String transferType;
    //密码
    private String password;

}
