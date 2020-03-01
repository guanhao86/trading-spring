package com.spring.free.domain;


import com.spring.free.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Table;

@Getter
@Setter
@ToString
@Table(name = "t_wheat_notice")
public class Notice extends BaseEntity<Notice> {
    private String title;
    private String body;
    /*
    1:公告2：常见问题3：客服联系方式4:首页顶部广告5:首页广告轮播6:理财页广告图片
     */
    private Integer type;
    /*
    1 理财公告 2 粮仓公告 3 APP公告（当type为公告）
     */
    private Integer noticeType;
    private String image;
    private String expand1;
    private String expand2;
    private String expand3;
    private String expand4;
    private String expand5;

}
