package com.sibtain.security.dao;

import com.sibtain.security.domain.ClientDetailsEntity;

public interface ClientDetailsDao {
    ClientDetailsEntity loadClientByClientId(String s);

    ClientDetailsEntity store(ClientDetailsEntity clientDetailsEntity);

}
