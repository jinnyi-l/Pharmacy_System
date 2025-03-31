package com.yjx.mapper;

import com.yjx.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper extends BaseMapper<User> {

    /**
     * 查找近一周新增用户
     * @return 用户数
     */
    Integer getNewUserOnWeek();

}
