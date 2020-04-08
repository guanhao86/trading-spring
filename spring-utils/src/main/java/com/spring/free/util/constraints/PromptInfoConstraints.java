package com.spring.free.util.constraints;

/**
 * 提示信息
 *
 * @author Memory
 * @version 1.0
 */
public class PromptInfoConstraints {
    /**
     * 成功状态
     */
    public static final String SUCCESS_STATUS = "success";
    /**
     * 失败状态
     */
    public static final String FAIL_STATUS = "fail";

    public static final String PROMPT_ADD_USER_FAIL = "添加用户失败！请联系管理员";

    public static final String SYS_USER_ENABLED = "您的用户被禁用或没有被激活！";

    public static final String SYS_USER_NAME_FAIL = "用户名输入不正确";

    public static final String SYS_USER_ROLE_FAIL = "您没有权限登录管理系统！请联系管理员";

    public static final String SYS_USER_PWD_FAIL = "密码错误！";

    public static final String SYS_IDENTIFYING_CODE = "验证码错误！";

    public static final String SYS_USER_NOT_EXISTENT = "用户不存在";

    public static final String SYS_USER_AUTH_FAIL = "用户认证失败";

    public static final String SAVE_SUCCESS = "保存成功！";

    public static final String SAVA_FAIL = "保存失败！";

    public static final String DELETE_SUCCESS = "删除成功";

    public static final String DELETE_FAIL = "删除失败";

    public static String getSaveSuccess(String info) {
        return "保存" + info + "成功！";
    }

    public static String getDeleteSuccess(String info) {
        return "删除" + info + "成功！";
    }

    /**
     * ==功能标题==========================================================================================================
     */
    public static final String[] COMMON_TITLE = {" 列表", " 新增", " 编辑", " 分配"};
    public static final String FUN_TITLE_MENU_LIST = "菜单管理";
    public static final String FUN_TITLE_MENU_ADD = "菜单管理" + COMMON_TITLE[1];
    public static final String FUN_TITLE_MENU_UPD = "菜单管理" + COMMON_TITLE[2];

    public static final String FUN_TITLE_USER_LIST = "用户管理";
    public static final String FUN_TITLE_USER_ADD = "用户管理" + COMMON_TITLE[1];
    public static final String FUN_TITLE_USER_UPD = "用户管理" + COMMON_TITLE[2];

    public static final String FUN_TITLE_ROLE_LIST = "角色管理";
    public static final String FUN_TITLE_ROLE_ADD = "角色管理" + COMMON_TITLE[1];
    public static final String FUN_TITLE_ROLE_UPD = "角色管理" + COMMON_TITLE[2];
    public static final String FUN_TITLE_ROLE_DIST = "角色管理" + COMMON_TITLE[3];

    public static final String FUN_TITLE_DICT_LIST = "词典管理";
    public static final String FUN_TITLE_DICT_ADD = "词典管理" + COMMON_TITLE[1];
    public static final String FUN_TITLE_DICT_UPD = "词典管理" + COMMON_TITLE[2];

    /**
     * ==页面标题==========================================================================================================
     */
    public static final String INDEX = "首页";

    public static final String TRADE_SYSTEM = "艾斯派客管理系统";

    public static final String TRADE_SYSTEM_INDEX = TRADE_SYSTEM + "-首页";

    public static final String MEMBER_SYSTEM_LOGIN = "会员管理系统";

    public static final String SYSTEM_SETTING = "系统设置";

    public static final String SYSTEM_SETTING_INDEX = SYSTEM_SETTING + "-首页";

}
