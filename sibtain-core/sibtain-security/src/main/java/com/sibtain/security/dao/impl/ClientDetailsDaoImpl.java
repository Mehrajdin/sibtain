package com.sibtain.security.dao.impl;

import com.sibtain.security.dao.ClientDetailsDao;
import com.sibtain.security.domain.ClientDetailsEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository("clientDetailsDao")
public class ClientDetailsDaoImpl implements ClientDetailsDao {
    private static final Logger logger = LoggerFactory.getLogger(ClientDetailsDaoImpl.class);
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ClientDetailsEntity loadClientByClientId(String clientId) {
        logger.info("Loading Client details for : {}", clientId);
        return (ClientDetailsEntity) entityManager.createQuery(
                "from ClientDetailsEntity where clientId = :clientId")
                .setParameter("clientId", clientId)
                .getSingleResult();
    }

    @Override
    public ClientDetailsEntity store(ClientDetailsEntity clientDetailsEntity) {
        logger.info("Creating new Client {}", clientDetailsEntity.getClientId());
        entityManager.persist(clientDetailsEntity);
        return clientDetailsEntity;
    }
}
