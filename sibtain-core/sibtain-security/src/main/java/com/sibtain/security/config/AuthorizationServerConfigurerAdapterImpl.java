package com.sibtain.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.stereotype.Component;


@Component
public class AuthorizationServerConfigurerAdapterImpl extends AuthorizationServerConfigurerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(AuthorizationServerConfigurerAdapterImpl.class);
    AuthenticationManager authenticationManager;
    @Autowired
    DefaultTokenServices tokenServices;

    @Autowired
    @Qualifier("customClientDetailsService")
    private ClientDetailsService clientDetailsService;

    public AuthorizationServerConfigurerAdapterImpl(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        this.authenticationManager =
                authenticationConfiguration.getAuthenticationManager();
        logger.info("Get Authentication Manager ");

    }

    @Override
    public void configure(
            ClientDetailsServiceConfigurer clients
    ) throws Exception {
        clients.withClientDetails(clientDetailsService);
        logger.info("Configure Client details service in ClientDetailsServiceConfigurer");
    }

    @Override
    public void configure(
            AuthorizationServerEndpointsConfigurer endpoints
    ) throws Exception {
        endpoints.tokenServices(tokenServices);
        endpoints.authenticationManager(authenticationManager);
        logger.info("Set TokenService and authenticationManager to AuthorizationServerEndpointsConfigurer");

    }

}
