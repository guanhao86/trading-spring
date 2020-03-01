package com.spring.free.vo;

import java.util.List;

public class TreeVO {

    String name;

    List<TreeVO> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TreeVO> getChildren() {
        return children;
    }

    public void setChildren(List<TreeVO> children) {
        this.children = children;
    }
}
