package com.spring.free.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * 用户角色关联Entity
 * @author Memory
 * @version 1.0
 */
@Getter
@Setter
@ToString
@Table(name = "t_sys_user_role")
public class UserRole implements Serializable {

    private static final long serialVersionUID = -6024714867731733142L;

    private Long userId;

    private Long roleId;

}
