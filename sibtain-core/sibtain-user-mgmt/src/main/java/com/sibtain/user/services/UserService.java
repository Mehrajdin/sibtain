package com.sibtain.user.services;

import com.sibtain.user.vo.UserVo;

public interface UserService {
    UserVo getById(String userId);

    UserVo create(UserVo userVo);

}
