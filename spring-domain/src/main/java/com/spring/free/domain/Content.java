package com.spring.free.domain;/**
 * Created by hengpu on 2019/2/25.
 */

import com.spring.free.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Table;

/**
 * @ClassName Content
 * @Description //内容管理
 * @Author hengpu
 * @Date 2019/2/25 14:09
 * @Version 1.0
 **/
@Getter
@Setter
@ToString
@Table(name = "t_nat_content")
public class Content extends BaseEntity<Content> {
    /**
     *分类
     */
    private Integer type;
    /**
     *广告类型
     */
    private Integer advertType;
    /**
     *平台名称
     */
    private String platName;
    /**
     *地址类型
     */
    private Integer addType;
    /**
     *图片路径
     */
    private String image;
    /**
     *选择跳转链接
     */
    private Integer urlType;
    /**
     *跳转链接
     */
    private String jumpUrl;
    /**
     *是否禁用
     */
    private Integer isUse;



}
