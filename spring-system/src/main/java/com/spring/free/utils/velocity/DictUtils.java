package com.spring.free.utils.velocity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.spring.free.domain.DictInfo;
import com.spring.free.mapper.DictMapper;
import com.spring.free.util.SpringContextHolder;
import com.spring.free.util.constraints.Global;
import com.spring.free.util.json.JsonMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.config.DefaultKey;
import org.apache.velocity.tools.config.ValidScope;

import java.util.List;
import java.util.Map;

/**
 * Velocity 模板引擎，字典工具类
 *
 * @author Memory
 * @version 1.0
 */
@DefaultKey("dict")
@ValidScope(Scope.REQUEST)
public class DictUtils {

    private static DictMapper dictDao = SpringContextHolder.getBean(DictMapper.class);
    public static List<DictInfo> DICT_INFOS = Lists.newArrayList();

    public static final String CACHE_DICT_MAP = "dictMap";

    /**
     * 获取词典中对应的value值
     *
     * @param value        键值
     * @param type         类型
     * @param defaultValue 默认值
     * @return 返回一个标签值
     */
    public static String getDictLabel(String value, String type, String defaultValue) {
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)) {
            for (DictInfo dict : getDictList(type)) {
                if (type.equals(dict.getType()) && value.equals(dict.getValue())) {
                    return dict.getLabel();
                }
            }
        }
        return defaultValue;
    }

    /**
     * 获取词典中对应的标签值
     *
     * @param values       用逗号分割的键值
     * @param type         类型
     * @param defaultValue 默认值
     * @return 返回多个标签值，格式为：逗号分割的字符串
     */
    public static String getDictLabels(String values, String type, String defaultValue) {
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(values)) {
            List<String> valueList = Lists.newArrayList();
            for (String value : StringUtils.split(values, Global.SEPARATOR_COMMA)) {
                valueList.add(getDictLabel(value, type, defaultValue));
            }
            return StringUtils.join(valueList, ",");
        }
        return defaultValue;
    }

    /**
     * 获取词典中键值
     *
     * @param label        标签
     * @param type         类型
     * @param defaultLabel 默认值
     * @return 返回一个键值
     */
    public static String getDictValue(String label, String type, String defaultLabel) {
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(label)) {
            for (DictInfo dict : getDictList(type)) {
                if (type.equals(dict.getType()) && label.equals(dict.getLabel())) {
                    return dict.getValue();
                }
            }
        }
        return defaultLabel;
    }

    /**
     * 根据标签类型获取该标签的所有键值
     *
     * @param type 类型
     * @return
     */
    public static List<DictInfo> getDictList(String type) {

        Map<String, List<DictInfo>> dictMap = Maps.newHashMap();
        if (DICT_INFOS.isEmpty()){
            DICT_INFOS = dictDao.findAllList(new DictInfo());
        }

        for (DictInfo dict : DICT_INFOS) {
            List<DictInfo> dictList = dictMap.get(dict.getType());
            if (dictList != null) {
                dictList.add(dict);
            } else {
                dictMap.put(dict.getType(), Lists.newArrayList(dict));
            }
        }

        List<DictInfo> dictList = dictMap.get(type);
        if (dictList == null) {
            dictList = Lists.newArrayList();
        }
        return dictList;
    }

    /**
     * 返回字典列表（JSON）
     *
     * @param type 类型
     * @return
     */
    public static String getDictListJson(String type) {
        return JsonMapper.toJsonString(getDictList(type));
    }

    /**
     * 单项选择词典数据
     *
     * @param defaultCheck 默认选中
     * @param dictType     词典类型
     * @param htmlName     用于提交的name属性值
     * @return
     */
    public static String getDictRadio(String defaultCheck, String dictType, String htmlName) {
        List<DictInfo> dictList = getDictList(dictType);
        String html = "";
        for (DictInfo dict : dictList) {
            if (dict.getValue().equals(defaultCheck)) {
                html += "&nbsp;&nbsp;<label class=\"radio-inline\" >";
                html += "<input type=\"radio\" name=\"" + htmlName + "\" checked=\"checked\" value=\"" + dict.getValue() + "\"> ";
                html += dict.getLabel() + "</label>";
            } else {
                html += "&nbsp;&nbsp;<label class=\"radio-inline\" >";
                html += "<input type=\"radio\" name=\"" + htmlName + "\" value=\"" + dict.getValue() + "\"> ";
                html += dict.getLabel() + "</label>";
            }
        }
        return html;
    }

    public String getDictCheckbox(String defaultCheck, String dictType, String htmlName) {
        List<DictInfo> dictList = getDictList(dictType);
        String html = "";
        for (DictInfo dict : dictList) {
            if (!"".equals(defaultCheck.trim()) && defaultCheck != null) {
                if (defaultCheck.indexOf(dict.getValue()) != -1) {
                    html += "&nbsp;&nbsp;<label class=\"checkbox-inline\" >";
                    html += "<input type=\"checkbox\" name=\"" + htmlName + "\" checked=\"checked\" style='vertical-align:middle' value=\"" + dict.getValue() + "\"> ";
                    html += dict.getLabel() + "</label>";
                } else {
                    html += "&nbsp;&nbsp;<label class=\"checkbox-inline\" >";
                    html += "<input type=\"checkbox\" name=\"" + htmlName + "\" style='vertical-align:middle' value=\"" + dict.getValue() + "\"> ";
                    html += dict.getLabel() + "</label>";
                }
            } else {
                html += "&nbsp;&nbsp;<label class=\"checkbox-inline\" >";
                html += "<input type=\"checkbox\" name=\"" + htmlName + "\" style='vertical-align:middle' value=\"" + dict.getValue() + "\"> ";
                html += dict.getLabel() + "</label>";
            }
        }
        return html;
    }

    public String getDictSelect(String defaultCheck, String dictType, String htmlName) {
        List<DictInfo> dictList = getDictList(dictType);
        String html = "<select data-placeholder=\"请选择...\" class=\"chosen-select toPageParameters\" style=\"width:100%;\" tabindex=\"2\" name=\"" + htmlName + "\">";
//        html += "<option value=\"all\">请选择</option>";
        for (DictInfo dict : dictList) {
            if (dict.getValue().equals(defaultCheck)) {
                html += "<option value=\"" + dict.getValue() + "\" selected=\"selected\">" + dict.getLabel() + "</option>";
            } else {
                html += "<option value=\"" + dict.getValue() + "\">" + dict.getLabel() + "</option>";
            }
        }
        html += "</select>";
        return html;
    }
    public String getDictSelectm(String defaultCheck, String dictType, String htmlName) {
        List<DictInfo> dictList = getDictList(dictType);
        String html = "<select data-placeholder=\"请选择...\" class=\"chosen-select toPageParameters\" style=\"width:100%;\" tabindex=\"2\" name=\"" + htmlName + "\">";
        html += "<option value=\"\">请选择</option>";
        for (DictInfo dict : dictList) {
            if (dict.getValue().equals(defaultCheck)) {
                html += "<option value=\"" + dict.getValue() + "\" selected=\"selected\">" + dict.getLabel() + "</option>";
            } else {
                html += "<option value=\"" + dict.getValue() + "\">" + dict.getLabel() + "</option>";
            }
        }
        html += "</select>";
        return html;
    }

}


