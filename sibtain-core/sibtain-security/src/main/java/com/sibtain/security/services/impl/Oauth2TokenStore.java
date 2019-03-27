package com.sibtain.security.services.impl;

import com.sibtain.security.domain.JwtTokenApproval;
import com.sibtain.security.vo.OauthUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.ExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.approval.Approval;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.KeyPair;
import java.util.*;

@Component("tokenStore")
public class Oauth2TokenStore extends JwtAccessTokenConverter implements TokenStore {
    private static final String TOKEN_SIGNING_ALGO = "SHA256withRSA";
    private static final Logger logger = LoggerFactory.getLogger(Oauth2TokenStore.class);

    @Autowired
    private ApprovalStore approvalStore;
    @Autowired
    private KeyPair jwtSigningKeyPair;

    public Oauth2TokenStore() {
        logger.info("Instantiating Oauth2TokenStore");
    }

    @PostConstruct
    public void init() {
        setSigningKey("Demo-signing_key");
        logger.info("Initializing Oauth2TokenStore setting up Token Signing");
        /*RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) jwtSigningKeyPair.getPrivate();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) jwtSigningKeyPair.getPublic();
        Signer signer = new RsaSigner(rsaPrivateKey, TOKEN_SIGNING_ALGO);
        setSigner(signer);
        setVerifier(new RsaVerifier(rsaPublicKey, TOKEN_SIGNING_ALGO));*/
    }

    public void setApprovalStore(ApprovalStore approvalStore) {
        logger.info("Setting Approval store in TokenStore");
        this.approvalStore = approvalStore;
    }

    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        return this.readAuthentication(token.getValue());
    }

    public OAuth2Authentication readAuthentication(String token) {
        return extractAuthentication(decode(token));
    }

    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        logger.info("Persist Token info for validation latter");
        OAuth2Request request = authentication.getOAuth2Request();
        String clientId = request.getClientId();
        OauthUser user = (OauthUser) authentication.getPrincipal();
        String refString = UUID.nameUUIDFromBytes((user.getUsername() + "DELIM" + clientId).getBytes()).toString();
        Set<String> scopes = request.getScope();
        List<Approval> approvals = new ArrayList<>();
        if (scopes != null) {
            for (String scope : scopes) {
                JwtTokenApproval approval = new JwtTokenApproval();
                approval.setApprovalRef(refString);
                approval.setClientId(clientId);
                approval.setExpiresAt(token.getExpiration());
                approval.setScope(scope);
                approval.setStatus(Approval.ApprovalStatus.APPROVED);
                approval.setLastUpdatedAt(new Date());
                approval.setUserId(user.getUsername());
                approvals.add(approval);
            }
            approvalStore.addApprovals(approvals);
        }

    }

    public OAuth2AccessToken readAccessToken(String tokenValue) {
        logger.info("Reading accessToken");
        Authentication authentication = readAuthentication(tokenValue);
        if (authentication == null) {
            logger.error("Access Token has no authorization");
            throw new OAuth2AccessDeniedException("Access Token has no authorization");
        }
        OAuth2Request request = ((OAuth2Authentication) authentication).getOAuth2Request();
        String clientId = request.getClientId();
        String user = (String) authentication.getPrincipal();
        Collection<Approval> approvals = approvalStore.getApprovals(user, clientId);
        if (approvals == null || approvals.size() < 1) {
            logger.error("Access has already been revoked for token " + tokenValue);
            throw new OAuth2AccessDeniedException("Access was revoked");
        }
        return extractAccessToken(tokenValue, decode(tokenValue));
    }

    public void removeAccessToken(OAuth2AccessToken token) {
        logger.info("Remove Access token");
        if (this.approvalStore != null) {
            OAuth2Authentication auth = this.readAuthentication(token);
            String clientId = auth.getOAuth2Request().getClientId();
            Authentication user = auth.getUserAuthentication();
            if (user != null) {
                Collection<Approval> approvals = this.approvalStore.getApprovals(user.getName(), clientId);
                this.approvalStore.revokeApprovals(approvals);
            }
        }

    }

    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
        logger.info("RECEIVED : Store RefreshToken | ACTION : No Token Stored");
    }

    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        logger.info("Read Refresh Token");
        OAuth2AccessToken encodedRefreshToken = this.readAccessToken(tokenValue);
        ExpiringOAuth2RefreshToken refreshToken = new DefaultExpiringOAuth2RefreshToken(encodedRefreshToken.getValue(), encodedRefreshToken.getExpiration());
        if (this.approvalStore != null) {
            OAuth2Authentication authentication = this.readAuthentication(tokenValue);
            if (authentication.getUserAuthentication() != null) {
                String userId = authentication.getUserAuthentication().getName();
                String clientId = authentication.getOAuth2Request().getClientId();
                Collection<Approval> approvals = this.approvalStore.getApprovals(userId, clientId);
                Collection<String> approvedScopes = new HashSet();
                Iterator var10 = approvals.iterator();

                while (var10.hasNext()) {
                    Approval approval = (Approval) var10.next();
                    if (approval.isApproved()) {
                        approvedScopes.add(approval.getScope());
                    }
                }

                if (!approvedScopes.containsAll(authentication.getOAuth2Request().getScope())) {
                    return null;
                }
            }
        }

        return refreshToken;
    }

    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
        return this.readAuthentication(token.getValue());
    }

    public void removeRefreshToken(OAuth2RefreshToken token) {
        this.remove(token.getValue());
    }

    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
        this.removeRefreshToken(refreshToken);
    }

    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        return null;
    }

    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
        return Collections.emptySet();
    }

    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        return Collections.emptySet();
    }


    private void remove(String token) {
        logger.info("Remove Token");
        if (this.approvalStore != null) {
            OAuth2Authentication auth = this.readAuthentication(token);
            String clientId = auth.getOAuth2Request().getClientId();
            Authentication user = auth.getUserAuthentication();
            if (user != null) {
                Collection<Approval> approvals = new ArrayList();
                Iterator var7 = auth.getOAuth2Request().getScope().iterator();

                while (var7.hasNext()) {
                    String scope = (String) var7.next();
                    approvals.add(new Approval(user.getName(), clientId, scope, new Date(), Approval.ApprovalStatus.APPROVED));
                }

                this.approvalStore.revokeApprovals(approvals);
            }
        }

    }


}
