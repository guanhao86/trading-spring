package com.spring.free.manager;

import com.spring.free.domain.IconInfo;
import com.spring.free.mapper.IconMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by spink on 2017/3/28.
 */
@Repository
public class IconManager {

    @Autowired
    private IconMapper mapper;

    public List<IconInfo> selectIcon(){
        return mapper.selectIcon(new IconInfo());
    }
}
