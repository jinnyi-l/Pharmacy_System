package com.yjx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yjx.common.lang.Result;
import com.yjx.dto.RegisterDto;
import com.yjx.entity.User;

public interface UserService extends IService<User> {

    /**
     * 用户修改密码
     *
     * @param updateDto 用户名，密码，邮箱，邮箱验证码
     * @return Result
     */
    Result updateUserPassword(RegisterDto updateDto);

    /**
     * 查找近一周新增用户
     * @return 用户数
     */
    Result getNewUserOnWeek();

}
