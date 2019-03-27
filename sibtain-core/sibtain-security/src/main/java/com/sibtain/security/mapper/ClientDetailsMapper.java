package com.sibtain.security.mapper;

import com.sibtain.security.domain.ClientDetailsEntity;
import com.sibtain.security.vo.ClientDetailsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component("clientDetailsMapper")
public class ClientDetailsMapper {
    private static final Logger logger = LoggerFactory.getLogger(ClientDetailsMapper.class);
    @Autowired
    private PasswordEncoder passwordEncoder;

    public ClientDetailsVO toVo(ClientDetailsEntity clientDetails) {
        logger.info("Mapping ClientDetails entity to clientDetailsVo");
        if (clientDetails == null) {
            return null;
        }
        ClientDetailsVO clientDetailsVO = new ClientDetailsVO();
        clientDetailsVO.setId(clientDetails.getId());
        clientDetailsVO.setClientName(clientDetails.getClientName());
        clientDetailsVO.setAccessTokenValiditySeconds(clientDetails.getAccessTokenValiditySeconds());
        clientDetailsVO.setRefreshTokenValiditySeconds(clientDetails.getRefreshTokenValiditySeconds());
        clientDetailsVO.setScoped(clientDetails.isScoped());
        clientDetailsVO.setSecretRequired(clientDetails.isSecretRequired());
        clientDetailsVO.setClientId(clientDetails.getClientId());
        clientDetailsVO.setClientSecret(clientDetails.getClientSecret());

        String authoritiesStr = clientDetails.getAuthorities();
        if (StringUtils.hasText(authoritiesStr)) {
            Set<String> authorities = StringUtils.commaDelimitedListToSet(authoritiesStr);
            Set<SimpleGrantedAuthority> authoritySet = new HashSet<>();
            for (String authority : authorities) {
                authoritySet.add(new SimpleGrantedAuthority(authority));
            }

            clientDetailsVO.setAuthorities(authoritySet);
        }
        if (StringUtils.hasText(clientDetails.getAuthorizedGrantTypes())) {
            clientDetailsVO.setAuthorizedGrantTypes(StringUtils.commaDelimitedListToSet(clientDetails.getAuthorizedGrantTypes()));
        }


        if (StringUtils.hasText(clientDetails.getResourceIds())) {
            clientDetailsVO.setResourceIds(StringUtils.commaDelimitedListToSet(clientDetails.getResourceIds()));
        }
        if (StringUtils.hasText(clientDetails.getScope())) {
            clientDetailsVO.setScope(StringUtils.commaDelimitedListToSet(clientDetails.getScope()));
        }
        if (StringUtils.hasText(clientDetails.getRegisteredRedirectUri())) {
            clientDetailsVO.setRegisteredRedirectUri(StringUtils.commaDelimitedListToSet(clientDetails.getRegisteredRedirectUri()));
        }

        return clientDetailsVO;

    }

    public ClientDetailsEntity toEntity(ClientDetailsVO clientDetails) {
        logger.info("Mapping ClientDetailsVo entity to clientDetails");
        if (clientDetails == null) {
            return null;
        }
        ClientDetailsEntity clientDetailsEntity = new ClientDetailsEntity();
        clientDetailsEntity.setAccessTokenValiditySeconds(clientDetails.getAccessTokenValiditySeconds());
        clientDetailsEntity.setRefreshTokenValiditySeconds(clientDetails.getRefreshTokenValiditySeconds());
        clientDetailsEntity.setScoped(clientDetails.isScoped());
        clientDetailsEntity.setSecretRequired(clientDetails.isSecretRequired());
        clientDetailsEntity.setClientId(clientDetails.getClientId());
        clientDetailsEntity.setClientName(clientDetails.getClientName());
        clientDetailsEntity.setClientSecret(
                passwordEncoder.encode(clientDetails.getClientSecret()));

        if (clientDetails instanceof ClientDetailsVO) {
            List<String> autoApproveScopes = ((ClientDetailsVO) clientDetails).getAutoApproveScopes();
            if (autoApproveScopes != null) {
                clientDetailsEntity.setAutoApproveScopes(StringUtils.collectionToCommaDelimitedString(autoApproveScopes));
            }
        }
        Collection<GrantedAuthority> authoritySet = clientDetails.getAuthorities();
        if (authoritySet != null) {
            clientDetailsEntity.setAuthorities(StringUtils.collectionToCommaDelimitedString(authoritySet));
        }
        Set<String> grantedAuths = clientDetails.getAuthorizedGrantTypes();
        if (grantedAuths != null) {
            clientDetailsEntity.
                    setAuthorizedGrantTypes(StringUtils.collectionToCommaDelimitedString(grantedAuths));
        }


        Set<String> resourceIds = clientDetails.getResourceIds();
        if (resourceIds != null) {
            clientDetailsEntity.setResourceIds(StringUtils.collectionToCommaDelimitedString(resourceIds));
        }

        Set<String> scopes = clientDetails.getScope();
        if (scopes != null) {
            clientDetailsEntity.setScope(StringUtils.collectionToCommaDelimitedString(scopes));
        }
        Set<String> redirectUrls = clientDetails.getRegisteredRedirectUri();
        if (redirectUrls != null) {
            clientDetailsEntity.setRegisteredRedirectUri(StringUtils.collectionToCommaDelimitedString(redirectUrls));
        }
        return clientDetailsEntity;
    }

}
