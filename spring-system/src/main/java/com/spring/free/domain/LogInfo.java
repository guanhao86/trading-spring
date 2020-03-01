package com.spring.free.domain;

import com.spring.free.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * Created by spink on 2017/6/19.
 */
@Getter
@Setter
@ToString
@Table(name = "t_sys_log_info")
public class LogInfo extends BaseEntity<LogInfo> {

    private String username;

    private String name;

    private String ip;

    private String handleUrl;

    private String handleName;

    private Date handleTime;

    private String handleRemarks;
    @Transient
    private String startTime;
    @Transient
    private String endTime;

}
