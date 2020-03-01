package com.spring.free.system;

import com.spring.free.domain.IconInfo;
import com.spring.free.manager.IconManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by spink on 2017/3/29.
 */
@Service
public class IconService {

    @Autowired
    private IconManager iconManager;

    public List<IconInfo> selectIcon(){
        return iconManager.selectIcon();
    }

}
