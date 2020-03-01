package com.spring.free.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 图标Entity
 * @author Memory
 * @version 1.0
 */

@Getter
@Setter
@ToString
public class IconInfo {

    private Long id;
    private String iconName;
    private String code;
    private List<IconInfo> iconList;

}
