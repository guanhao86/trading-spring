package com.spring.free.scheduled;/**
 * Created by hengpu on 2019/3/8.
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;

/**
 * @ClassName ScheduledService
 * @Description //TODO
 * @Author hengpu
 * @Date 2019/3/8 10:27
 * @Version 1.0
 **/
@Slf4j
//@Component
public class ScheduledService {
//    @Autowired
//    private MemberPrivilegeManager memberPrivilegeManager;
//    @Autowired
//    private CardManager cardManager;
//
//    //每隔5分钟执行一次
//    @Scheduled(cron = "0 0/5 * * * ?")
//    @Scheduled(cron = "0 1 0 * * ?")
//    public void scheduled(){
//        MemberPrivilege memberPrivilege = new MemberPrivilege();
//        memberPrivilege.setState(2);
//        List<MemberPrivilege> list = memberPrivilegeManager.findList(memberPrivilege);
//        for(MemberPrivilege bean :list){
//            if(bean.getEffectTime().before(new Date())){
//                bean.setState(1);
//                memberPrivilegeManager.updateBs(bean);
//
//                Card card = new Card();
//                card.setMemberId(bean.getMemberId());
//                List<Card> cardList = cardManager.findList(card);
//                for(Card cardBean :cardList){
//                    cardBean.setIsGrounding(1);
//                    cardManager.updateBs(cardBean);
//                }
//            }
//        }
//
//
//
//        log.info("=====>>>>>使用cron  {}",System.currentTimeMillis());
//    }


}
