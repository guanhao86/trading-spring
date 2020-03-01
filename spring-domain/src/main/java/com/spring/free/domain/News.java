package com.spring.free.domain;

import com.spring.free.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Table;

@Getter
@Setter
@ToString
@Table(name = "t_wheat_news")
public class News extends BaseEntity<News> {

    private String title;
    private String body;

    private Integer type;

    private String bigImage;
    private String image1;
    private String image2;
    private String image3;
    private Integer readNum;
    private Integer goodNum;
    private String expand1;
    private String expand2;
    private String expand3;
    private String expand4;
    private String expand5;
}
