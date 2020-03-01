package com.spring.free.common.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by hengpu on 2019/1/7.
 */
@Setter
@Getter
@ToString
public class PageParamDTO {
    private Integer page;
    private Integer pageSize;
    private String startTime;
    private String endTime;
    private String type;
}
