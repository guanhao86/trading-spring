package com.spring.free.system;

import com.github.pagehelper.PageInfo;
import com.spring.free.domain.DictInfo;
import com.spring.free.manager.DictManager;
import com.spring.free.util.constraints.Global;
import com.spring.free.util.exception.ExceptionCodeEnum;
import com.spring.free.util.exception.ServiceException;
import com.spring.free.utils.velocity.DictUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 字典Service
 * @author Memory
 * @version 1.0
 */
@Service
public class DictService {

    @Autowired
    private DictManager dictManager;

    public PageInfo<DictInfo> dictList(DictInfo dict, Integer page, Integer pageSize){
        return dictManager.findList(dict,page, pageSize);
    }

    public void save(DictInfo dict, Map map){
        if (!DictUtils.DICT_INFOS.isEmpty()){
            DictUtils.DICT_INFOS.removeAll(DictUtils.DICT_INFOS);
        }
        boolean boo = false;
        String msg = "没有任何操作";
        if (dict.getId() != null && !"".equals(dict.getId())){
            boo = dictManager.updateBs(dict) > 0 ? true : false;
            msg = "编辑词典信息，发生错误！";
        } else {
            boo = dictManager.insert(dict) > 0 ? true : false;
            msg = "新增词典信息，发生错误！";
        }
        if (!boo){
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), msg, map.get(Global.URL).toString(), map);
        }
    }

    public void deleteDict(DictInfo dict, Map map){
        boolean boo = dictManager.deleteBs(dict) > 0 ? true : false;
        if (!boo){
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "删除词典信息，发生错误！", map.get(Global.URL).toString(), map);
        }
    }

    public DictInfo getDict(Long id){
        DictInfo info = new DictInfo();
        info.setId(id);
        return dictManager.get(info);
    }

}
