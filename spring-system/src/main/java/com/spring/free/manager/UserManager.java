package com.spring.free.manager;

import com.spring.free.base.BaseManager;
import com.spring.free.domain.UserInfo;
import com.spring.free.domain.UserRole;
import com.spring.free.mapper.UserMapper;
import com.spring.free.util.constraints.Global;
import com.spring.free.util.exception.ExceptionCodeEnum;
import com.spring.free.util.exception.ServiceException;
import com.spring.free.util.md5.Md5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by spink on 2017/3/28.
 */
@Slf4j
@Repository
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class UserManager extends BaseManager<UserMapper, UserInfo> {

    @Autowired
    private UserMapper mapper;

    /**
     * 系统用户保存
     * @param user
     * @param map
     * @return
     * @throws ServiceException
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public UserInfo save(UserInfo user, Map map) throws ServiceException{
        boolean b = false;
        if (user.getId() != null){
            b = updateBs(user) > 0 ? true : false;
        } else {
            user.setPassword(Md5Util.md5Hex(user.getPassword()));
            b = insertBs(user) > 0 ? true : false;
        }
        if (!b){
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "保存用户信息失败", map.get(Global.URL).toString(), map);
        }
        setUserRole(user, map);
        return user;
    }

    public UserInfo getByLoginName(UserInfo user){
        return mapper.getByLoginName(user);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int outUserInRole(UserRole userRole){
        return mapper.outUserInRole(userRole);
    }

    public List<UserRole> findUserRole(UserRole userRole){
        return mapper.findUserRole(userRole);
    }

    public List<UserRole> findRoleUser(UserRole userRole) {
        return mapper.findRoleUser(userRole);
    }

    public List<UserInfo> jobNumber(){
        return mapper.jobNumber();
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int deleteUserRole(UserInfo user){
        return mapper.deleteUserRole(user);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int insertUserRole(UserInfo user){
        return mapper.insertUserRole(user);
    }

    public List<UserInfo> getUserAll(UserInfo user){
        return mapper.getUserAll(user);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int insertBatch(List<UserInfo> list){
        return mapper.insertBatch(list);
    }

    /**
     * 更新用户与角色关联信息
     * @param user
     */
    private void setUserRole(UserInfo user, Map map) throws ServiceException {
        if (user.getId() != null){
            // 更新用户与角色关联
            deleteUserRole(user);
            if (user.getRoleList() != null && user.getRoleList().size() > 0){
                insertUserRole(user);
            }else{
                throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), user.getUsername() + "没有设置角色！", map.get(Global.URL).toString(), map);
            }
        }
    }
}
