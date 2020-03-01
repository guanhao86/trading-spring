package com.spring.free.domain;

import com.spring.free.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QueryVO extends BaseEntity<QueryVO> {

    private String memberId;

    private String referenceId;

    private String reallyName;

    //公告
    private String title;

}
