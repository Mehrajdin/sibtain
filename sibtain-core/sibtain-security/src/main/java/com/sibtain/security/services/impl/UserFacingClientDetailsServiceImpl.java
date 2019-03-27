package com.sibtain.security.services.impl;

import com.sibtain.security.services.UserFacingClientDetailsService;
import com.sibtain.security.vo.ClientDetailsVO;
import com.sibtain.security.vo.ClientRegistrationSuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service("userFacingClientDetailsService")
public class UserFacingClientDetailsServiceImpl implements UserFacingClientDetailsService {
    @Autowired
    private ClientRegistrationService customClientDetailsService;
    @Override
    @Transactional
    public ClientRegistrationSuccessResponse createClientDetails(ClientDetailsVO clientDetails) {
        String  clientId = generateRandomUUid();
        String  clientSecret = generateRandomUUid();
        clientDetails.setClientId(clientId);
        clientDetails.setClientSecret(clientSecret);
        customClientDetailsService.addClientDetails(clientDetails);
        ClientRegistrationSuccessResponse response = new ClientRegistrationSuccessResponse(clientDetails);
        response.setClientId(clientId);
        response.setClientSecret(clientSecret);
        return response;
    }

    private String generateRandomUUid(){
        return UUID.randomUUID().toString();
    }
}
