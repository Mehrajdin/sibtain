package com.sibtain.user.services.impl;

import com.sibtain.user.dao.RoleDao;
import com.sibtain.user.dao.UserDao;
import com.sibtain.user.domain.User;
import com.sibtain.user.domain.UserRole;
import com.sibtain.user.domain.UserRoleAssoc;
import com.sibtain.user.mapper.UserMapper;
import com.sibtain.user.services.UserService;
import com.sibtain.user.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleDao roleDao;

    @Override
    @Transactional(readOnly = true)
    public UserVo getById(String userId) {
        User user = userDao.getById(userId);
        return userMapper.toVo(user);
    }

    @Override
    @Transactional(readOnly = false)
    public UserVo create(UserVo userVo) {
        User user = userMapper.toEntity(userVo);
        List<UserRole> roles = roleDao.listByIds(userVo.getRoles());
        List<UserRoleAssoc> roleAssocs = new ArrayList<>();
        if (roles != null) {
            for (UserRole userRole : roles) {
                UserRoleAssoc roleAssoc = new UserRoleAssoc();
                roleAssoc.setUserRole(userRole);
                roleAssocs.add(roleAssoc);
                roleAssoc.setUser(user);
            }
            user.setRoleAssocs(roleAssocs);
        }
        return userMapper.toVo(userDao.create(user));
    }
}
