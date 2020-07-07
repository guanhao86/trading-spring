package com.spring.free.scheduled;


import com.spring.fee.service.ITableMemberGoodsBusiSV;
import com.spring.free.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

/**
 * 金鸡下蛋定时任务
 */
@Slf4j
@Component
public class ScoreScheduledService {
    @Autowired
    private ITableMemberGoodsBusiSV iTableMemberGoodsBusiSV;

    //每天1点执行
    @Scheduled(cron = "0 0 1 * * ?")
    public void scheduled(){
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        log.info("========金鸡下蛋定时任务========时间:"+System.currentTimeMillis()+"START");
        Date time = DateUtils.getSysDate();
        iTableMemberGoodsBusiSV.calcScore(time);
        log.info("========金鸡下蛋定时任务========时间:"+System.currentTimeMillis()+"END");
    }
}
