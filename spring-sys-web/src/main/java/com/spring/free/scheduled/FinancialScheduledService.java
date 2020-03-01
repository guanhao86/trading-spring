package com.spring.free.scheduled;


import com.spring.fee.service.ITWheatFinancialBusiSV;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Slf4j
@Component
public class FinancialScheduledService {
    @Autowired
    private ITWheatFinancialBusiSV iTWheatFinancialBusiSV;

    //    //每隔5分钟执行一次
//    @Scheduled(cron = "0 0/5 * * * ?")
    @Scheduled(cron = "0 0 23 * * ?")
    public void scheduled(){
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        log.info("========理财产品订单奖金释放========时间:"+System.currentTimeMillis()+"START");
        iTWheatFinancialBusiSV.dealTWheatFinancial(day);
        log.info("========理财产品订单奖金释放========时间:"+System.currentTimeMillis()+"END");
    }
}
