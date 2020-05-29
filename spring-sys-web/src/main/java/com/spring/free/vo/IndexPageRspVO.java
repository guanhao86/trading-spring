package com.spring.free.vo;

import com.spring.fee.model.TableBroadcastInfo;
import lombok.Data;

import java.util.List;

/**
 * 首页对象
 */
@Data
public class IndexPageRspVO {

    //左区业绩
    String leftAmount;
    //右区业绩
    String rightAmount;
    //公告
    List<TableBroadcastInfo> tableBroadcastInfoList;
    //今日推荐
    String todayCount;
    //今日团队
    String todayTeam;
    //累计推荐
    String allCount;
    //累计团队
    String allTeam;

}
