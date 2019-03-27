package com.sibtain.user.services.impl;

import com.sibtain.user.dao.RoleDao;
import com.sibtain.user.domain.UserRole;
import com.sibtain.user.mapper.RoleMapper;
import com.sibtain.user.services.UserRoleService;
import com.sibtain.user.vo.UserRoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userRoleService")
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private RoleMapper roleMapper;
    @Override
    @Transactional(readOnly = true)
    public List<UserRoleVo> listAll() {
        List<UserRole> userRoles = roleDao.listAll();
        return roleMapper.mapListToVos(userRoles);

    }
}
