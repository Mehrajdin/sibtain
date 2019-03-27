package com.sibtain.user.dao;

import com.sibtain.user.domain.UserRole;

import java.util.List;

public interface RoleDao {
    List<UserRole> listAll();
    List<UserRole> getAllByName(List<String> roleNames);
    UserRole getById(String roleId);

    UserRole getByName(String roleName);

    UserRole createNew(String roleName);

    List<UserRole> listByIds(List<String> roles);
}
