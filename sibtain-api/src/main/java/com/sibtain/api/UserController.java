package com.sibtain.api;

import com.sibtain.security.services.UserFacingOAuthService;
import com.sibtain.user.services.UserRoleService;
import com.sibtain.user.services.UserService;
import com.sibtain.user.vo.UserRoleVo;
import com.sibtain.user.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(path = "/users")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserFacingOAuthService userFacingOAuthService;


    @RequestMapping(path = "/add", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    //@Secured("ROLE_ADMIN")
    public UserVo addUser(@RequestBody UserVo userVo) {
        logger.info("Adding user {}", userVo.getFirstName());
        return userService.create(userVo);
    }

    @RequestMapping(path = "/roles", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserRoleVo> getRoles() {
        return userRoleService.listAll();
    }


    @RequestMapping(path = "/get/{userId}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UserVo getUser(@PathVariable("userId") String userId) {
        return userService.getById(userId);
    }


    @RequestMapping(path = "/me/logout", method = RequestMethod.POST)
    public void logoutUser(@RequestHeader(name = "Authorization") String authHeader) {
        userFacingOAuthService.logout();
    }

}
