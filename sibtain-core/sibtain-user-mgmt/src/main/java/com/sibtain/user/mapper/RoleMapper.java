package com.sibtain.user.mapper;

import com.sibtain.user.domain.UserRole;
import com.sibtain.user.vo.UserRoleVo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("roleMapper")
public class RoleMapper {
    public UserRoleVo toVo(UserRole role) {
        if (role == null) {
            return null;
        }
        UserRoleVo roleVo = new UserRoleVo();
        roleVo.setId(role.getId());
        roleVo.setRoleName(role.getRoleName());
        return roleVo;
    }


    public UserRole toEntity(UserRoleVo roleVo) {
        if (roleVo == null) {
            return null;
        }
        UserRole role = new UserRole();
        role.setRoleName(roleVo.getRoleName());
        return role;
    }

    public List<UserRoleVo> mapListToVos(List<UserRole> userRoles) {
        List<UserRoleVo> voList = new ArrayList<>();
        if (userRoles == null || userRoles.size() < 1) {
            return voList;
        }
        for (UserRole role : userRoles) {
            voList.add(toVo(role));
        }
        return voList;
    }
}
