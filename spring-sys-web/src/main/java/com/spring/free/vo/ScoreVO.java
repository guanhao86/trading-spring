package com.spring.free.vo;

import lombok.Data;

@Data
public class ScoreVO {

    //累计金蛋产出数量
    int addAll = 0;
    //当日金蛋产出数量；
    int addToday = 0;
    //累计金蛋兑换数量；
    int useAll = 0;
    //当日金蛋兑换数量
    int useToday = 0;
    //会员当前余额
    int score;

}
