package com.sibtain.security.services;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

public interface UserFacingOAuthService {
    void logout() throws OAuth2Exception;

}
