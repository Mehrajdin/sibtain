package com.sibtain.security.mapper;

import com.sibtain.security.vo.OauthUser;
import com.sibtain.user.domain.User;
import com.sibtain.user.domain.UserRoleAssoc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("oauth2UserMapper")
public class Oauth2UserMapper {
    private static final Logger logger = LoggerFactory.getLogger(Oauth2UserMapper.class);

    public OauthUser toUserDetails(User user) {
        logger.info("Mapping User to OauthUser");
        if (null == user) {
            return null;
        }
        List<UserRoleAssoc> userRoleAssocs = user.getRoleAssocs();
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (null != userRoleAssocs) {
            for (UserRoleAssoc roleAssoc : userRoleAssocs) {
                authorities.add(new SimpleGrantedAuthority(roleAssoc.getUserRole().getRoleName()));
            }
        }
        OauthUser oauthUser =
                new OauthUser(authorities, user.getPassword(), user.getUserName(), user.getEnabled());
        return oauthUser;

    }
}
