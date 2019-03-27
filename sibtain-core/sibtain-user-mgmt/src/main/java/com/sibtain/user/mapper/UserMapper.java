package com.sibtain.user.mapper;

import com.sibtain.user.domain.User;
import com.sibtain.user.domain.UserRoleAssoc;
import com.sibtain.user.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("userMapper")
public class UserMapper {
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserVo toVo(User user) {
        if (user == null) {
            return null;
        }
        UserVo userVo = new UserVo();
        userVo.setId(user.getId());
        userVo.setFirstName(user.getFirstName());
        userVo.setLastName(user.getFirstName());
        userVo.setUserName(user.getUserName());
        userVo.setEnabled(user.getEnabled());
        userVo.setPassword(user.getPassword());
        if (user.getRoleAssocs() != null) {
            List<String> roles = new ArrayList<>();
            for (UserRoleAssoc userRoleAssoc : user.getRoleAssocs()) {
                roles.add(userRoleAssoc.getUserRole().getRoleName());
            }
            userVo.setRoles(roles);
        }
        return userVo;
    }

    public User toEntity(UserVo userVo) {
        if (userVo == null) {
            return null;
        }
        User user = new User();
        user.setId(user.getId());
        user.setFirstName(userVo.getFirstName());
        user.setLastName(userVo.getFirstName());
        user.setUserName(userVo.getUserName());
        user.setEnabled(userVo.getEnabled());
        user.setPassword(passwordEncoder.encode(userVo.getPassword()));
        List<String> roles = userVo.getRoles();
        if(roles != null){
            for(String role :roles){

            }
        }



        return user;
    }
}
