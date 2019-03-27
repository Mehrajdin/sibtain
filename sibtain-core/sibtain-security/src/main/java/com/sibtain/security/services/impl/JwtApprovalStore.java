package com.sibtain.security.services.impl;

import com.sibtain.security.dao.JwtApprovalDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.approval.Approval;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;


@Component("approvalStore")
public class JwtApprovalStore implements ApprovalStore {
    private static  final Logger logger = LoggerFactory.getLogger(JwtApprovalStore.class);
    @Autowired
    private JwtApprovalDao jwtApprovalDao;

    public JwtApprovalStore() {
        logger.info("Initializing Approval store");
    }

    public boolean addApprovals(Collection<Approval> approvals) {
        logger.info("Persisting Approvals");
        jwtApprovalDao.storeAll(approvals);
        return true;
    }

    public boolean revokeApprovals(Collection<Approval> approvals) {
        logger.info("Revoking Approvals");
        boolean success = true;
        jwtApprovalDao.remove(approvals);
        return success;
    }


    public Collection<Approval> getApprovals(String userId, String clientId) {
        String refString = UUID.nameUUIDFromBytes((userId + "DELIM" + clientId).getBytes()).toString();
        logger.info("Get Applrovals for userId: {} Client: {} reference: {}",userId,clientId,refString);
        return Collections.unmodifiableCollection(jwtApprovalDao.getApprovals(refString));
    }


}
