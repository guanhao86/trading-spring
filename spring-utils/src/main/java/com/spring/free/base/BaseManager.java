package com.spring.free.base;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by spink on 2017/3/28.
 * @author spink
 */
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class BaseManager<D extends BaseMapper<T>, T extends BaseEntity> {

    @Autowired
    private D mapper;

    /**
     * 插入数据ceshi
     * @param entity
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int insert(T entity){
        entity.setInsertDefault();
        return mapper.insert(entity);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int insertBs(T entity){
        entity.setInsertDefault();
        return mapper.insertBs(entity);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int update(T entity){
        entity.setUpdateDefault();
        return mapper.updateByPrimaryKey(entity);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int delete(T entity){
        return mapper.delete(entity);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int deleteBs(T entity){
        return mapper.deleteBs(entity);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int updateBs(T entity){
        entity.setUpdateDefault();
        return mapper.updateBs(entity);
    }

    public T get(Long id){
        return mapper.get(id);
    }

    public T get(T entity){
        return mapper.get(entity);
    }

    public T getByOne(T entity){
        return mapper.selectOne(entity);
    }

    public List<T> findList(T entity){
        return mapper.findList(entity);
    }

    public PageInfo<T> findList(T entity, Integer page, Integer pageSize){
        if(page != null && pageSize != null){
            PageHelper.startPage(page, pageSize);
        }
        List<T> findList = mapper.findList(entity);
        return new PageInfo<T>(findList);
    }

    public List<T> selectAll(){
        return mapper.selectAll();
    }

    public List<T> findAllList(T entity){
        return mapper.findAllList(entity);
    }

    public PageInfo<T> findAllList(T entity, Integer page, Integer pageSize){
        if(page != null && pageSize != null){
            PageHelper.startPage(page, pageSize);
        }
        return new PageInfo<T>(mapper.findAllList(entity));
    }
}
