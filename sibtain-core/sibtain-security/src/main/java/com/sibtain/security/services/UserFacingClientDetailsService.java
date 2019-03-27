package com.sibtain.security.services;

import com.sibtain.security.vo.ClientDetailsVO;
import com.sibtain.security.vo.ClientRegistrationSuccessResponse;

public interface UserFacingClientDetailsService {
    ClientRegistrationSuccessResponse createClientDetails(ClientDetailsVO clientDetails);
}
