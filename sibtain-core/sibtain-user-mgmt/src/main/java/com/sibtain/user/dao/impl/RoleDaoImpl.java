package com.sibtain.user.dao.impl;

import com.sibtain.user.dao.RoleDao;
import com.sibtain.user.domain.UserRole;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("roleDao")
public class RoleDaoImpl implements RoleDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UserRole> listAll() {
        return entityManager.createQuery(
                "from UserRole").getResultList();

    }

    @Override
    public List<UserRole> getAllByName(List<String> roleNames) {
        return entityManager.createQuery(
                "from UserRole where roleName in :roleNames")
                .setParameter("roleNames", roleNames)
                .getResultList();
    }

    @Override
    public UserRole getById(String roleId) {
        return entityManager.find(UserRole.class, roleId);
    }

    @Override
    public UserRole getByName(String roleName) {
        return (UserRole) entityManager.createQuery(
                "from UserRole where roleName = :roleName")
                .setParameter("roleName", roleName)
                .getSingleResult();

    }

    @Override
    public UserRole createNew(String roleName) {
        UserRole existingRole = getByName(roleName);
        if (existingRole != null) {
            return existingRole;
        }
        UserRole freshRole = new UserRole();
        freshRole.setRoleName(roleName.toUpperCase());
        entityManager.persist(freshRole);
        return freshRole;
    }

    @Override
    public List<UserRole> listByIds(List<String> roles) {
        return entityManager.createQuery(
                "from UserRole where id in :roles")
                .setParameter("roles", roles)
                .getResultList();
    }
}
