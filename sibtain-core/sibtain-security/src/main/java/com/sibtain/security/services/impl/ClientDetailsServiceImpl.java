package com.sibtain.security.services.impl;

import com.sibtain.security.dao.ClientDetailsDao;
import com.sibtain.security.domain.ClientDetailsEntity;
import com.sibtain.security.mapper.ClientDetailsMapper;
import com.sibtain.security.vo.ClientDetailsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("customClientDetailsService")
public class ClientDetailsServiceImpl implements ClientDetailsService, ClientRegistrationService {
    private static final Logger logger = LoggerFactory.getLogger(ClientDetailsServiceImpl.class);
    @Autowired
    private ClientDetailsDao clientDetailsDao;
    @Autowired
    private ClientDetailsMapper clientDetailsMapper;

    @Override
    @Transactional(readOnly = true)
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        logger.info("Load client details by clientId : {}", clientId);
        ClientDetailsEntity clientDetailsEntity = clientDetailsDao.loadClientByClientId(clientId);
        if (clientDetailsEntity == null) {
            logger.info("Client details not found");
            throw new ClientRegistrationException("Client " + clientId + " was not registered");
        }
        return clientDetailsMapper.toVo(clientDetailsEntity);
    }


    @Override
    @Transactional
    public void addClientDetails(ClientDetails clientDetails) throws ClientAlreadyExistsException {
        logger.info("Storing new client details " + clientDetails.getClientId());
        ClientDetailsVO clientDetailsVO = (ClientDetailsVO) clientDetails;
        ClientDetailsEntity entity = clientDetailsMapper.toEntity(clientDetailsVO);
        clientDetailsDao.store(entity);
    }

    @Override
    public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {

    }

    @Override
    public void updateClientSecret(String s, String s1) throws NoSuchClientException {

    }

    @Override
    public void removeClientDetails(String s) throws NoSuchClientException {

    }

    @Override
    public List<ClientDetails> listClientDetails() {
        return null;
    }



}
