package com.spring.free.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.spring.free.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户Entity
 *
 * @author Memory
 * @version 1.0
 */
@Getter
@Setter
@ToString
@Table(name = "t_sys_user_info")
public class UserInfo extends BaseEntity<UserInfo> {
    /**
     * 登录名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 工号
     */
    private String no;
    /**
     * 姓名
     */
    private String name;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 电话
     */
    private String phone;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 用户类型
     */
    private String userType;
    /**
     * 最后登陆IP
     */
    private String loginIp;
    /**
     * 最后登陆日期
     */
    private Date loginDate;
    /**
     * 是否允许登陆
     */
    private String loginFlag;
    /**
     * 头像
     */
    private String photo;
    /**
     * 登录状态
     */
    @Transient
    private int loginSts;
    /**
     * 原登录名
     */
    @Transient
    private String oldLoginName;
    /**
     * 新密码
     */
    @Transient
    private String newPassword;
    /**
     * 上次登陆IP
     */
    @Transient
    private String oldLoginIp;
    /**
     * 上次登陆日期
     */
    @Transient
    private Date oldLoginDate;
    /**
     * 根据角色查询用户条件
     */
    @Transient
    private RoleInfo role;
    /**
     * 允许登录的系统类型 多个 用逗号分隔
     */
    @Transient
    private String systemTypes;

    private String remarks;
    /**
     * 拥有角色列表
     */
    @Transient
    private List<RoleInfo> roleList = new ArrayList<RoleInfo>();

    public boolean isAdmin() {
        return isAdmin(this.id);
    }

    public static boolean isAdmin(Long id) {
        return id != null && id == 1L;
    }

    @JsonIgnore
    public List<Long> getRoleIdList() {
        List<Long> roleIdList = Lists.newArrayList();
        for (RoleInfo role : roleList) {
            roleIdList.add(role.getId());
        }
        return roleIdList;
    }

    public void setRoleIdList(List<Long> roleIdList) {
        roleList = Lists.newArrayList();
        for (Long roleId : roleIdList) {
            RoleInfo role = new RoleInfo();
            role.setId(roleId);
            roleList.add(role);
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public String getLoginFlag() {
        return loginFlag;
    }

    public void setLoginFlag(String loginFlag) {
        this.loginFlag = loginFlag;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getOldLoginName() {
        return oldLoginName;
    }

    public void setOldLoginName(String oldLoginName) {
        this.oldLoginName = oldLoginName;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldLoginIp() {
        return oldLoginIp;
    }

    public void setOldLoginIp(String oldLoginIp) {
        this.oldLoginIp = oldLoginIp;
    }

    public Date getOldLoginDate() {
        return oldLoginDate;
    }

    public void setOldLoginDate(Date oldLoginDate) {
        this.oldLoginDate = oldLoginDate;
    }

    public RoleInfo getRole() {
        return role;
    }

    public void setRole(RoleInfo role) {
        this.role = role;
    }

    @JsonIgnore
    public List<RoleInfo> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<RoleInfo> roleList) {
        this.roleList = roleList;
    }

}