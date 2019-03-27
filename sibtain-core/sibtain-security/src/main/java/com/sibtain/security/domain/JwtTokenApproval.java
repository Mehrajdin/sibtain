package com.sibtain.security.domain;


import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.oauth2.provider.approval.Approval;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "jwt_token_approval")
public class JwtTokenApproval extends Approval {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Access(AccessType.PROPERTY)
    private String id;

    @Column(name = "approval_ref")
    private String approvalRef;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "client_id")
    private String clientId;
    @Column(name = "scope")
    private String scope;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Approval.ApprovalStatus status;
    @Column(name = "expires_at")
    private Date expiresAt;
    @Column(name = "last_updated_at")
    private Date lastUpdatedAt;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApprovalRef() {
        return approvalRef;
    }

    public void setApprovalRef(String approvalRef) {
        this.approvalRef = approvalRef;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public String getScope() {
        return scope;
    }

    @Override
    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public ApprovalStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(ApprovalStatus status) {
        this.status = status;
    }

    @Override
    public Date getExpiresAt() {
        return expiresAt;
    }

    @Override
    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    @Override
    public Date getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    @Override
    public void setLastUpdatedAt(Date lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }
}
