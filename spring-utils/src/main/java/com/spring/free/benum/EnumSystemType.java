package com.spring.free.benum;

/**
 * @author Memory
 * @date 2018/3/6
 * @Inc 恒谱科技
 */
public enum EnumSystemType {

    /**
     * Wallet 服务管理系统服务系统
     */
    TRADING_SYSTEM(1, "艾斯派客管理系统"),
    /**
     * 配置系统后台管理
     */
    CONFIGURE_SYSTEM(3, "配置系统");

    private Integer key;
    private String value;

    EnumSystemType(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
