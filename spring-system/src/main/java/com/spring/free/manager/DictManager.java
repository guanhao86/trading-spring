package com.spring.free.manager;

import com.spring.free.base.BaseManager;
import com.spring.free.domain.DictInfo;
import com.spring.free.mapper.DictMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 字典管理DAO
 * @author Memory
 * @version 1.0
 */
@Repository
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class DictManager extends BaseManager<DictMapper, DictInfo> {

}
