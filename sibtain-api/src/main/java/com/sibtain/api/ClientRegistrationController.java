package com.sibtain.api;

import com.sibtain.security.services.UserFacingClientDetailsService;
import com.sibtain.security.vo.ClientDetailsVO;
import com.sibtain.security.vo.ClientRegistrationSuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/admin/oauth/clients")
public class ClientRegistrationController {

    @Autowired
    private UserFacingClientDetailsService userFacingClientDetailsService;

    @RequestMapping(path = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured({"ROLE_ADMIN", "ROLE_CONFIG_MANAGER"})
    public ClientRegistrationSuccessResponse createClient(@RequestBody ClientDetailsVO clientDetailsVO) {
        ClientRegistrationSuccessResponse details = userFacingClientDetailsService.createClientDetails(clientDetailsVO);
        return details;
    }
}
