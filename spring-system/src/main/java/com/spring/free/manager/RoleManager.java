package com.spring.free.manager;

import com.spring.free.base.BaseManager;
import com.spring.free.domain.RoleInfo;
import com.spring.free.mapper.RoleMapper;
import com.spring.free.util.constraints.Global;
import com.spring.free.util.exception.ExceptionCodeEnum;
import com.spring.free.util.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by spink on 2017/3/28.
 */
@Repository
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class RoleManager extends BaseManager<RoleMapper, RoleInfo> {

    @Autowired
    private RoleMapper mapper;

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public RoleInfo save(RoleInfo entity, Map map){
        boolean boo = false;
        if (entity.getId() != null){
            boo = mapper.updateBs(entity) > 0 ? true : false;
        } else {
            boo = mapper.insertBs(entity) > 0 ? true : false;
        }
        if (!boo){
            throw new ServiceException(ExceptionCodeEnum.SERVICE_ERROR_CODE.getCode(), "设置失败", map.get(Global.URL).toString(), map);
        }
        // 更新角色与菜单关联
        mapper.deleteRoleMenu(entity);
        if (entity.getMenuList().size() > 0){
            mapper.insertRoleMenu(entity);
        }
        return entity;
    }

    /**
     * 获取角色名称
     * @param role
     * @return
     */
    public RoleInfo getByName(RoleInfo role){
        return mapper.getByName(role);
    }

    /**
     * 维护角色与菜单权限关系
     * 删除角色与菜单关系
     * @param role
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int deleteRoleMenu(RoleInfo role){
        return mapper.deleteRoleMenu(role);
    }

    /**
     * 维护角色与菜单权限关系
     * 插入角色与菜单关系
     * @param role
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int insertRoleMenu(RoleInfo role){
        return mapper.insertRoleMenu(role);
    }

    /**
     * 角色分配用户
     * @param role
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int assignUserToRole(RoleInfo role){
        return mapper.assignUserToRole(role);
    }

    /**
     * 根据用户ID获取角色信息
     * @param userId
     * @return
     */
    public List<RoleInfo> getUserRoleInfo(Long userId){
        return mapper.getUserRoleInfo(userId);
    }

    /**
     * 获取用户拥有的角色名称
     * @param role
     * @return
     */
    public String getUserRoleName(RoleInfo role){
        return mapper.getUserRoleName(role);
    }
}
