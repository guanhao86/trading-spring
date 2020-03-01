package com.spring.free.domain;

import com.spring.free.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 菜单Entity
 *
 * @author Memory
 * @version 1.0
 */
@Getter
@Setter
@ToString
@Table(name = "t_sys_menu_info")
public class MenuInfo extends BaseEntity<MenuInfo> {

    /**
     * 父级对象
     */
    @Transient
    private MenuInfo parent;
    /**
     * 所有父级编号
     */
    private String parentIds;
    /**
     * 名称
     */
    private String name;
    /**
     * 链接
     */
    private String href;
    /**
     * 目标（ mainFrame、_blank、_self、_parent、_top），无用
     */
    private String target;
    /**
     * 图标
     */
    private String icon;
    /**
     * 是否在菜单中显示（1：显示；0：不显示）
     */
    private Integer isShow;
    /**
     * 权限标识
     */
    private String permission;
    /**
     * 父级ID
     */
    @Column(name = "parent_id")
    private Long parentID;
    /**
     * 用户ID
     */
    @Transient
    private Long userId;
    /**
     * 菜单级别
     */
    private Integer grade;
    /**
     *
     */
    @Transient
    private String parentIdGrade;
    /**
     * 排序
     */
    protected String sort;
    /**
     *
     */
    @Transient
    private List<MenuInfo> children = new ArrayList<MenuInfo>();
    /**
     * 用来过滤菜单信息
     */
    @Transient
    private Map map;
    /**
     * 验证登录用户的user表示普通用户或管理员，list表示超级管理员
     */
    @Transient
    private String type;
    /**
     * 系统类型
     */
    private Integer systemType;

}