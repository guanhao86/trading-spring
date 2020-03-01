package com.spring.free.domain;

import com.google.common.collect.Lists;
import com.spring.free.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * 字典Entity
 *
 * @author Memory
 * @version 1.0
 */
@Getter
@Setter
@ToString
@Table(name = "t_sys_dict_info")
public class DictInfo extends BaseEntity<DictInfo> {
    /**
     * 数据值
     */
    private String value;
    /**
     * 标签名
     */
    private String label;
    /**
     * 类型
     */
    private String type;
    /**
     * 描述
     */
    private String description;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 父Id,无用
     */
    private String parentId;

    @Transient
    public static List<DictInfo> DICT_INFOS = Lists.newArrayList();

}