package com.spring.fee.service;

import com.spring.fee.model.TableTask;

/**
 * 定时任务，避免重复执行
 */
public interface ITableTaskBusiSV {

    boolean insert(TableTask bo);
}
