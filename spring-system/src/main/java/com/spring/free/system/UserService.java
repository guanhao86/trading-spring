package com.spring.free.system;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.spring.free.domain.DictInfo;
import com.spring.free.domain.RoleInfo;
import com.spring.free.domain.UserInfo;
import com.spring.free.domain.UserRole;
import com.spring.free.manager.RoleManager;
import com.spring.free.manager.UserManager;
import com.spring.free.util.constraints.Global;
import com.spring.free.util.constraints.PromptInfoConstraints;
import com.spring.free.util.exception.EnabledException;
import com.spring.free.util.exception.ExceptionCodeEnum;
import com.spring.free.util.exception.ServiceException;
import com.spring.free.util.exception.UserException;
import com.spring.free.util.md5.Md5Util;
import com.spring.free.utils.shirosession.SessionDAO;
import com.spring.free.utils.velocity.DictUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by spink on 2017/3/30.
 */
@Slf4j
@Service
public class UserService {

    @Autowired
    private UserManager userManager;

    @Autowired
    private RoleManager roleManager;
    @Autowired
    private SessionDAO sessionDAO;


    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    public UserInfo save(UserInfo user, Map map) throws ServiceException {
        log.info("保存系统用户信息");
        if (user.getRoleList() == null || user.getRoleList().isEmpty()) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "没有设置权限信息，请先设置相关权限后，再进行添加用户", map.get(Global.URL).toString(), map);
        }
        return userManager.save(user, map);
    }

    public int insertBatch(List<UserInfo> list) {
        List<UserInfo> infoList = new ArrayList<UserInfo>();
        for (UserInfo info : list) {
            info.setInsertDefault();
            info.setPassword(Md5Util.md5Hex("123456".getBytes()));
            infoList.add(info);
        }
        return userManager.insertBatch(infoList);
    }

    public void update(UserInfo user, Map map) throws ServiceException {
        log.info("编辑系统用户个人信息");
        boolean b = userManager.updateBs(user) > 0 ? true : false;
        if (!b) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "保存个人信息失败", map.get(Global.URL).toString(), map);
        }
    }


    /**
     * 更新用户登录IP
     *
     * @param user
     */
    public void setLoginIP(UserInfo user) {
        userManager.updateBs(user);
    }

    /**
     * 删除用户信息
     *
     * @param user
     */
    public void deleteUserInfo(UserInfo user, Map map) {
        log.info("删除系统用户信息");
        boolean boo = userManager.deleteBs(user) > 0 ? true : false;
        if (!boo) {
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "删除用户信息失败", map.get(Global.URL).toString(), map);
        }
    }

    /**
     * 修改用户密码
     *
     * @param username
     * @param password
     */
    public void updateUserPassword(String username, String password) {
        UserInfo user = new UserInfo();
        user.setUsername(username);
        user.setPassword(password);
        userManager.updateBs(user);
    }

    /**
     * 用户登录查询认证
     *
     * @param username
     * @return
     */
    public UserInfo getUserByUsernameOrPhoneLogin(String username) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username.trim());
        try {
            UserInfo user = userManager.getByLoginName(userInfo);
            if (user == null) {
                userInfo.setPhone(username.trim());
                userInfo.setUsername(null);
                user = userManager.getByPhone(userInfo);
                if (user == null) {
                    throw new UserException(PromptInfoConstraints.SYS_USER_NOT_EXISTENT);
                }
            }
            List<RoleInfo> roleList = roleManager.getUserRoleInfo(user.getId());
            if (roleList.isEmpty()){
                setSystemTypes(user);
            }
            if (StringUtils.isEmpty(user.getUserType())){
                throw new UserException(PromptInfoConstraints.SYS_USER_ROLE_FAIL);
            }
            user.setRoleList(roleList);

            if (Global.STR_NUMBER_ZERO.equals(user.getLoginFlag().trim())) {
                throw new EnabledException(PromptInfoConstraints.SYS_USER_ENABLED);
            }
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            if (PromptInfoConstraints.SYS_USER_ENABLED.equals(e.getMessage())) {
                throw new EnabledException(PromptInfoConstraints.SYS_USER_ENABLED);
            }
            throw new UserException(e.getMessage());
        }
    }

    /**
     * 用户登录查询认证
     *
     * @param username
     * @return
     */
    public UserInfo getUserByUsernameLogin(String username) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username.trim());
        try {
            UserInfo user = userManager.getByLoginName(userInfo);
            if (user == null) {
                throw new UserException(PromptInfoConstraints.SYS_USER_NOT_EXISTENT);
            }
            List<RoleInfo> roleList = roleManager.getUserRoleInfo(user.getId());
            if (roleList.isEmpty()){
                setSystemTypes(user);
            }
            if (StringUtils.isEmpty(user.getUserType())){
                throw new UserException(PromptInfoConstraints.SYS_USER_ROLE_FAIL);
            }
            user.setRoleList(roleList);

            if (Global.STR_NUMBER_ZERO.equals(user.getLoginFlag().trim())) {
                throw new EnabledException(PromptInfoConstraints.SYS_USER_ENABLED);
            }
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            if (PromptInfoConstraints.SYS_USER_ENABLED.equals(e.getMessage())) {
                throw new EnabledException(PromptInfoConstraints.SYS_USER_ENABLED);
            }
            throw new UserException(e.getMessage());
        }
    }

    /**
     * 查询所有用户
     *
     * @param user
     * @return
     */
    public List<UserInfo> findList(UserInfo user) {
        return userManager.findList(user);
    }

    public PageInfo<UserInfo> findList(UserInfo user, Integer page, Integer pageSize) {
        return userManager.findList(user, page, pageSize);
    }

    /**
     * 获取用户信息
     *
     * @param id
     * @return
     */
    public UserInfo getUser(Long id) {
        UserInfo info = new UserInfo();
        info.setId(id);
        return userManager.get(info);
    }

    /**
     * 获取用户信息
     *
     * @param id
     * @return
     */
    public UserInfo getUserByUserName(String userName) {
        UserInfo info = new UserInfo();
        info.setUsername(userName);
        return userManager.getByLoginName(info);
    }

    public String getJobNumber() {
        String no = "00001";
        List<UserInfo> userList = userManager.jobNumber();
        if (userList != null && userList.size() > 0) {
            no = userList.get(0).getNo();
        }
        return String.format("%05d", (Long.parseLong(no) + 1));
    }

    /**
     * 获取用户角色关联数据
     *
     * @param userId
     * @return
     */
    public List<UserRole> findUserRole(Long userId) {
        UserRole user = new UserRole();
        user.setUserId(userId);
        return userManager.findUserRole(user);
    }

    /**
     * 验证角色下是否存在用户关联
     *
     * @param roleId
     * @return 返回false 代表该角色下有用户关联，true代表该角色下没有用户关联
     */
    public boolean isRoleUser(Long roleId) {
        UserRole user = new UserRole();
        user.setRoleId(roleId);
        List<UserRole> userRoles = userManager.findRoleUser(user);
        if (userRoles != null && !userRoles.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * 保存用户和角色关联
     *
     * @param user
     */
    public void saveUserRole(UserInfo user) {
        userManager.insertUserRole(user);
    }

    /**
     * 验证用户名是否存在，存在返回false，不存在返回true
     *
     * @param username
     * @return
     */
    public boolean getUserByLoginName(String username) {
        UserInfo user = new UserInfo();
        user.setUsername(username);
        return validata(user);
    }

    /**
     * 验证手机号是否存在，存在返回false，不存在返回true
     *
     * @param mobile
     * @return
     */
    public boolean getUserByMobile(String mobile) {
        UserInfo user = new UserInfo();
        user.setMobile(mobile);
        return validata(user);
    }

    /**
     * 验证邮箱是否存在，存在返回false，不存在返回true
     *
     * @param email
     * @return
     */
    public boolean getUserByEmail(String email) {
        UserInfo user = new UserInfo();
        user.setEmail(email);
        return validata(user);
    }

    /**
     * 通过用户名和密码，确认密码是否正确
     *
     * @param username
     * @param password
     * @return
     */
    public boolean getUserByPassword(String username, String password) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setPassword(Md5Util.md5Hex(password));
        return !validata(userInfo);
    }

    /**
     * 验证User是否存在
     *
     * @param user
     * @return
     */
    public boolean validata(UserInfo user) {
        List<UserInfo> userList = userManager.getUserAll(user);
        if (userList != null && !userList.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * 移除用户和角色关联
     *
     * @param userRole
     */
    public boolean outUserInRole(UserRole userRole) {
        int ur = userManager.outUserInRole(userRole);
        if (ur > 0) {
            return true;
        }
        return false;
    }

    public List<UserInfo> getOnlineUser() {
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        List<UserInfo> list = Lists.newArrayList();
        for (Session session : sessions) {
            System.out.println(session.toString());
            UserInfo principal = (UserInfo) session.getAttribute("userInfo");
            list.add(principal);
        }
        return list;
    }

    public List<UserInfo> getOnlineStsQuery(UserInfo info) {
        List<UserInfo> findUserInfo = findList(info);
        List<UserInfo> onlineQuery = getOnlineUser();
        for (UserInfo userInfo : findUserInfo) {
            for (UserInfo online : onlineQuery) {
                if (online != null) {
                    if (userInfo.getUsername().equals(online.getUsername())) {
                        userInfo.setLoginSts(1);
                        break;
                    }
                }
            }
        }
        return findUserInfo;
    }

    public UserInfo setSystemTypes(UserInfo user){
        String sysTypes = "";
        List<DictInfo> dictList = DictUtils.getDictList("EnumSystemType");
        for (DictInfo info : dictList) {
            if (StringUtils.isEmpty(sysTypes)){
                sysTypes = info.getValue();
            } else {
                sysTypes +=  "," + info.getValue();
            }
        }
        user.setUserType(sysTypes);
        return user;
    }
}
