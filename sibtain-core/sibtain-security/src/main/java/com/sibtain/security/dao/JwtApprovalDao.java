package com.sibtain.security.dao;

import org.springframework.security.oauth2.provider.approval.Approval;

import java.util.Collection;

public interface JwtApprovalDao {
    Collection<Approval> getApprovals(String approvalRef);

    Collection<Approval> storeAll(Collection<Approval> tokenApprovals);

    void remove(Collection<Approval> tokenApprovals);

}
