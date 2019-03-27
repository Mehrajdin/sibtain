package com.sibtain.security.services.impl;

import com.sibtain.security.mapper.Oauth2UserMapper;
import com.sibtain.user.dao.UserDao;
import com.sibtain.user.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    private static  final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    @Autowired
    private UserDao userDao;
    @Autowired
    private Oauth2UserMapper oauth2UserMapper;


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        logger.info("Load user by user name : {}",s);
        User user = userDao.getByUserName(s);
        if (user == null) {
            logger.info("User not found in repository");
            throw new UsernameNotFoundException("User not found in repository");
        }
        return oauth2UserMapper.toUserDetails(user);

    }

}
