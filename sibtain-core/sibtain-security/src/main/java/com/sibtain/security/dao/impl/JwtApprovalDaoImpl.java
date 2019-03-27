package com.sibtain.security.dao.impl;

import com.sibtain.security.dao.JwtApprovalDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.provider.approval.Approval;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

@Repository("jwtApprovalDao")
public class JwtApprovalDaoImpl implements JwtApprovalDao {
    private static final Logger logger = LoggerFactory.getLogger(JwtApprovalDaoImpl.class);
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Collection<Approval> getApprovals(String approvalRef) {
        logger.info("Getting Stored Approvals for reference : {}", approvalRef);
        return entityManager.createQuery(
                "from JwtTokenApproval where approvalRef = :approvalRef")
                .setParameter("approvalRef", approvalRef)
                .getResultList();
    }

    @Override
    public Collection<Approval> storeAll(Collection<Approval> tokenApprovals) {
        logger.info("Storing Token Approvals");
        for (Approval approval : tokenApprovals) {
            entityManager.persist(approval);
            logger.info("Stored approval {}::{}::{}"
                    , approval.getScope()
                    , approval.getClientId(),
                    approval.getUserId());
        }
        return tokenApprovals;

    }

    @Override
    public void remove(Collection<Approval> tokenApprovals) {
        logger.info("Revoking Token Approvals");
        for (Approval approval : tokenApprovals) {
            entityManager.remove(approval);
            logger.info("Removed approval {}::{}::{}"
                    , approval.getScope()
                    , approval.getClientId(),
                    approval.getUserId());
        }
    }
}
