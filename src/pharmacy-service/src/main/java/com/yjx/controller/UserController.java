package com.yjx.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yjx.common.lang.Result;
import com.yjx.common.lang.ResultInfo;
import com.yjx.entity.User;
import com.yjx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取最近一周新增用户数量
     * @return Result
     */
    @PostMapping("/admin/get/newUsers")
    public Result getNewUserOnWeek() {

        return userService.getNewUserOnWeek();
    }

    /**
     * 获取vip的数量
     *
     * @return Result
     */
    @PostMapping("/admin/get/vipNums")
    public Result getVipUserNums() {

        int count = userService.count(new QueryWrapper<User>().eq("auth", 3));
        return Result.success().codeAndMessage(ResultInfo.SUCCESS).data("responseBody", count);
    }


    @PostMapping("/username")
    public String getUsername(@RequestParam String username) {

        return "早上好啊，" + username;
    }

}

