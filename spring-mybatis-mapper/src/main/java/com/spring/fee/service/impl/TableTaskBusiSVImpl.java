package com.spring.fee.service.impl;

import com.alibaba.fastjson.JSON;
import com.spring.fee.dao.mapper.TableTaskMapper;
import com.spring.fee.model.TableTask;
import com.spring.fee.service.ITableTaskBusiSV;
import com.spring.free.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 定时任务，避免重复执行
 */
@Slf4j
@Service
@Transactional
public class TableTaskBusiSVImpl implements ITableTaskBusiSV {

    @Autowired
    TableTaskMapper iTableTaskMapper;

    /**
     * 定时任务，避免重复执行
     * @param bo
     * @return
     */
    @Override
    public boolean insert(TableTask bo) {
        log.info("定时任务，避免重复执行：{}", JSON.toJSON(bo));
        bo.setRunTime(DateUtils.getSysDate());
        bo.setTaskTime(DateUtils.getYYYYMMDD(DateUtils.getSysDate()));
        try {
            if (1 == iTableTaskMapper.insert(bo)) {
                return true;
            }
        }catch (Exception e) {
            log.info("定时任务执行冲突", e);
            return false;
        }
        return false;
    }
}
