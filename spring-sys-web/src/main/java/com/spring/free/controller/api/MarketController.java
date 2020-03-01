package com.spring.free.controller.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.spring.free.domain.Market;
import com.spring.free.websocket.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MarketController {


    //15秒执行一次
    @Scheduled(cron = "0/15 * * * * ? ")
    public void run(){

        if(WebSocket.getOnlineCount()>0){
            System.out.println("当前websocket连接数："+WebSocket.getOnlineCount()+"。开始推送行情");
            List<Market> list=new ArrayList<>();
            //获取人民币美元汇率;
            String url = "https://kline.zbgpro.net/api/data/v1/ticker?marketName=USDT_QC";
            RestTemplate restTemplate = new RestTemplate();
            JSONObject jsonObject1 = restTemplate.getForEntity(url, JSONObject.class).getBody();
            String dao= JSON.parseArray(jsonObject1.getString("datas")).getString(1);

//            https://kline.zbgpro.net/api/data/v1/ticker?marketName=OW_QC
//            https://kline.zbgpro.net/api/data/v1/ticker?marketName=ETH_USDT
//            https://kline.zbgpro.net/api/data/v1/ticker?marketName=BTC_USDT

            url = "https://kline.zbgpro.net/api/data/v1/ticker?marketName=OW_QC";
             restTemplate = new RestTemplate();
            JSONObject jsonObject2 = restTemplate.getForEntity(url, JSONObject.class).getBody();
            JSONArray jsonArray2=JSON.parseArray(jsonObject2.getString("datas"));
            Market market2=new Market();
            market2.setMarketName("OW_QC");
            market2.setAmount(jsonArray2.getString(4));
            market2.setPrice(jsonArray2.getString(1));
            market2.setCnyPrice(jsonArray2.getString(1));
//            market2.setCnyPrice(String.valueOf(Double.parseDouble(dao)*Double.parseDouble(jsonArray2.getString(1))));
            market2.setChg(jsonArray2.getString(5));
            list.add(market2);

            url = "https://kline.zbgpro.net/api/data/v1/ticker?marketName=ETH_USDT";
            restTemplate = new RestTemplate();
            JSONObject jsonObject3 = restTemplate.getForEntity(url, JSONObject.class).getBody();
            JSONArray jsonArray3=JSON.parseArray(jsonObject3.getString("datas"));
            Market market3=new Market();
            market3.setMarketName("ETH_USDT");
            market3.setAmount(jsonArray3.getString(4));
            market3.setPrice(jsonArray3.getString(1));
            market3.setCnyPrice(String.valueOf(Double.parseDouble(dao)*Double.parseDouble(jsonArray3.getString(1))));
            market3.setChg(jsonArray3.getString(5));
            list.add(market3);


            url = "https://kline.zbgpro.net/api/data/v1/ticker?marketName=BTC_USDT";
            restTemplate = new RestTemplate();
            JSONObject jsonObject4 = restTemplate.getForEntity(url, JSONObject.class).getBody();
            JSONArray jsonArray4=JSON.parseArray(jsonObject4.getString("datas"));
            Market market4=new Market();
            market4.setMarketName("BTC_USDT");
            market4.setAmount(jsonArray4.getString(4));
            market4.setPrice(jsonArray4.getString(1));
            market4.setCnyPrice(String.valueOf(Double.parseDouble(dao)*Double.parseDouble(jsonArray4.getString(1))));
            market4.setChg(jsonArray4.getString(5));
            list.add(market4);

            WebSocket.setMarketList(list);
            WebSocket.GroupSending(JSON.toJSONString(list));
            System.out.println("当前websocket连接数："+WebSocket.getOnlineCount()+"。结束推送行情");
        }
    }
}
