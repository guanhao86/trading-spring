package com.spring.free.vo;

import com.spring.fee.model.TableGoods;
import com.spring.fee.model.TableMember;
import com.spring.fee.model.TableOrder;
import lombok.Data;

/**
 * 商品结果
 */
@Data
public class MemberRspVO extends TableMember {

    //奖衔描述
    String mRankDesc;

    //级别描述
    String levelDesc;

    //金鸡数量
    int jjCount;

}
