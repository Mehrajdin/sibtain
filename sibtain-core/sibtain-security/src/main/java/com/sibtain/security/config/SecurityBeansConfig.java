package com.sibtain.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;


@Configuration
public class SecurityBeansConfig {
    private static  final Logger logger = LoggerFactory.getLogger(SecurityBeansConfig.class);

    @Bean("passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        logger.info("Create Password encoder bean");
        return new BCryptPasswordEncoder(4);
    }

    @Bean("tokenServices")
    @Autowired
    public DefaultTokenServices tokenServices(TokenStore tokenStore, JwtAccessTokenConverter tokenConverter) {
        logger.info("Create bean tokenServices used tokenStore : {}, tokenController : {}",
                tokenStore.getClass().getPackage(),tokenConverter.getClass().getPackage());

        logger.info("Refresh Token Validity : 51840000");
        DefaultTokenServices tokenServices =
                new DefaultTokenServices();
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setRefreshTokenValiditySeconds(51840000);
        tokenServices.setReuseRefreshToken(true);
        tokenServices.setTokenEnhancer(tokenConverter);
        tokenServices.setTokenStore(tokenStore);
        return tokenServices;
    }


    @Bean("authentionKeyGenerator")
    public AuthenticationKeyGenerator authentionKeyGenerator() {
        logger.info("Create Authentication key generator bean of type DefaultAuthenticationKeyGenerator");
        return new DefaultAuthenticationKeyGenerator();
    }


    @Bean("jwtSigningKeyPair")
    public KeyPair getKeyPair() throws NoSuchAlgorithmException {
        logger.info("Creating  RSA key-pair with size 2048");
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair;

    }
}
