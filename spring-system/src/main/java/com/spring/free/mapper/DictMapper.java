package com.spring.free.mapper;

import com.spring.free.base.BaseMapper;
import com.spring.free.domain.DictInfo;

import java.util.List;

/**
 * 字典管理DAO
 * @author Memory
 * @version 1.0
 */
public interface DictMapper extends BaseMapper<DictInfo> {

	List<String> findTypeList(DictInfo dict);
	
}
