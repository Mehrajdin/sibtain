package com.sibtain.security.services.impl;

import com.sibtain.security.services.UserFacingOAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userFacingOAuthService")
public class UserFacingOAuthServiceImpl implements UserFacingOAuthService {
    private static  final Logger logger = LoggerFactory.getLogger(UserFacingOAuthServiceImpl.class);
    @Autowired
    private DefaultTokenServices tokenServices;

    @Override
    @Transactional
    public void logout() throws OAuth2Exception {
        OAuth2AuthenticationDetails details =
                (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        String tokenValue =
                details.getTokenValue();
        logger.info("Logout user token : {} "+tokenValue);
        tokenServices.revokeToken(tokenValue);
    }
}
