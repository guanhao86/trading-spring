package com.spring.fee.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.fee.dao.mapper.TableSystemConfigMapper;
import com.spring.fee.model.TableSystemConfig;
import com.spring.fee.model.TableSystemConfigExample;
import com.spring.fee.service.ITableSystemConfigBusiSV;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 平台参数配置服务
 * 只有一条记录
 */
@Slf4j
@Service
@Transactional
public class TableSystemConfigBusiSVImpl implements ITableSystemConfigBusiSV {

    @Autowired
    TableSystemConfigMapper iTableSystemConfigMapper;

    /**
     * 创建结算记录
     * @param bo
     * @return
     */
    @Override
    public TableSystemConfig insert(TableSystemConfig bo) {
        log.info("创建平台参数配置参数bo：{}", JSON.toJSON(bo));
        iTableSystemConfigMapper.insert(bo);
        return bo;
    }

    @Override
    public TableSystemConfig update(TableSystemConfig bo) {
        TableSystemConfigExample example = new TableSystemConfigExample();
        TableSystemConfigExample.Criteria criteria = example.createCriteria();

        if(this.iTableSystemConfigMapper.updateByExample(bo, example) > 0)
            return bo;
        return null;
    }

    @Override
    public TableSystemConfig delete(TableSystemConfig bo) {
        TableSystemConfigExample example = new TableSystemConfigExample();
        if (this.iTableSystemConfigMapper.deleteByExample(example)>0) {
            return bo;
        }
        return null;
    }

    @Override
    public TableSystemConfig select(TableSystemConfig bo) {
        TableSystemConfigExample example = new TableSystemConfigExample();
        List<TableSystemConfig> list = this.iTableSystemConfigMapper.selectByExample(example);
        return list.get(0);
    }

    @Override
    public PageInfo<TableSystemConfig> queryListPage(TableSystemConfig bo, Integer pageNum, Integer pageSize, Map<String, Object> map) {

        TableSystemConfigExample example = new TableSystemConfigExample();

        PageInfo<TableSystemConfig> pageInfo = PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(() -> this.iTableSystemConfigMapper.selectByExample(example));
        log.info("平台参数配置结果：{}", JSON.toJSON(pageInfo));

        return pageInfo;
    }
}
