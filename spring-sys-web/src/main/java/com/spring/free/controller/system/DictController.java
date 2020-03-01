package com.spring.free.controller.system;

import com.google.common.collect.Maps;
import com.spring.free.domain.DictInfo;
import com.spring.free.system.DictService;
import com.spring.free.util.PageResult;
import com.spring.free.util.constraints.Global;
import com.spring.free.util.constraints.PageDefaultConstraints;
import com.spring.free.util.constraints.PromptInfoConstraints;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by spink on 2017/3/29.
 */
@Controller
@RequestMapping(Global.ADMIN_PATH + "/dict/")
public class DictController {

    @Autowired
    private DictService dictService;

    @RequiresPermissions("system:dict:view")
    @RequestMapping({"", "dictList"})
    public ModelAndView dictManage(ModelAndView mav, HttpSession session, DictInfo dict, HttpServletRequest request,
                                   @RequestParam(value = "page",required = false, defaultValue = PageDefaultConstraints.PAGE) int page,
                                   @RequestParam(value = "rows", required = false, defaultValue = PageDefaultConstraints.PAGE_SIZE) int pageSize) {
        String dictType = request.getParameter("dictType");
        if (dictType != null && !"".equals(dictType)){
            dict.setType(dictType);
        }
        //获取词典列表信息
        mav.addObject("page", dictService.dictList(dict,page, pageSize));
        //返回页面header标题
        PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_DICT_LIST);
        //返回操作提示信息
        PageResult.getPrompt(mav, request, dict.getParamMsg());
        mav.setViewName("system/dict/dict_list");
        return mav;
    }

    @RequiresPermissions("system:dict:edit")
    @RequestMapping("form")
    public ModelAndView dictManageInsertPre(ModelAndView mav, HttpServletRequest request, DictInfo dict, String buttonType) {
        if (dict.getId() != null && !"".equals(dict.getId())){
            //验证是添加还是修改，如果是修改返回相应的数据信息
            setDict(mav, dict, buttonType);
        } else {
            String dictId = request.getParameter("dictId");
            if (dictId != null && !"".equals(dictId)){
                dict.setId(Long.parseLong(dictId));
                //判断操作类型，根据操作类型返回相应的区域信息
                setDict(mav, dict, request.getParameter("buttonType"));
            } else {
                //返回页面header标题
                PageResult.setPageTitle(mav,PromptInfoConstraints.FUN_TITLE_MENU_ADD);
            }
        }
        //返回操作提示信息
        PageResult.getPrompt(mav, request, dict.getParamMsg());
        mav.setViewName("system/dict/dict_form");
        return mav;
    }

    @RequiresPermissions("system:dict:edit")
    @RequestMapping("save")
    public ModelAndView dictManageInsert(ModelAndView mav, HttpServletRequest request, DictInfo dict, HttpSession session, String buttonType){
        Map map = Maps.newHashMap();
        map.put("dictId", dict.getId());
        map.put("buttonType", buttonType);
        map.put("dictType", dict.getType());
        map.put(Global.URL, Global.ADMIN_PATH + "/dict/form");
        dictService.save(dict, map);
        //返回操作提示信息
        PageResult.setPrompt(map, PromptInfoConstraints.getSaveSuccess("字典信息"), PromptInfoConstraints.SUCCESS_STATUS);
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH + "/dict/dictList"), map);
    }

    @RequiresPermissions("system:dict:edit")
    @RequestMapping("delete")
    public ModelAndView dictManageDelete(ModelAndView mav, HttpServletRequest request, DictInfo dict, HttpSession session) {
        Map map = Maps.newHashMap();
        map.put(Global.URL, Global.ADMIN_PATH + "/dict/dictList");
        //删除词典信息
        dictService.deleteDict(dict, map);
        //返回操作提示信息
        PageResult.setPrompt(map, PromptInfoConstraints.getDeleteSuccess("字典信息"), PromptInfoConstraints.SUCCESS_STATUS);
        return new ModelAndView(new RedirectView(Global.ADMIN_PATH + "/dict/dictList"), map);
    }

/*==私有方法区========================================================================================================*/
    /**
     * 字典编辑或添加键值
     * @param mav ModelAndView对象
     * @param dict 字典对象
     * @param buttonType 操作类型 addSub：添加下级区域信息；edit：编辑区域信息
     * @return 返回ModelAndView对象
     */
    private ModelAndView setDict(ModelAndView mav, DictInfo dict, String buttonType){
        DictInfo dict1 = dictService.getDict(dict.getId());
        if (dict.getId() != null && !"".equals(dict.getId())){
            if (Global.ADD_VAL.equals(buttonType)){
                mav.addObject("dictInfo", getDict(dict1));
                //返回页面header标题
                PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_DICT_ADD);
            } else if (Global.EDIT.equals(buttonType)){
                mav.addObject("dictInfo", dict1);
                //返回页面header标题
                PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_DICT_UPD);
            }
            mav.addObject("buttonType", buttonType);
        } else {
            //返回页面header标题
            PageResult.setPageTitle(mav, PromptInfoConstraints.FUN_TITLE_DICT_ADD);
        }
        return mav;
    }

    /**
     * 获取type, desription, sort, remarks 属性值
     * @param dict1 字典对象
     * @return 返回字典对象
     */
    private DictInfo getDict(DictInfo dict1){
        DictInfo dict2 = new DictInfo();
        dict2.setType(dict1.getType());
        dict2.setDescription(dict1.getDescription());
        dict2.setSort(dict1.getSort());
        return dict2;
    }
}
