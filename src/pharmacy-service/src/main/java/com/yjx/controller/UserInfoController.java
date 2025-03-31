package com.yjx.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjx.dto.NormalUserInfoDto;
import com.yjx.dto.UserInfoDto;
import com.yjx.common.lang.Result;
import com.yjx.common.lang.ResultInfo;
import com.yjx.entity.User;
import com.yjx.entity.UserInfo;

import com.yjx.service.UserInfoService;
import com.yjx.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@Slf4j
public class UserInfoController {

    @Autowired
    UserService userService;

    @Autowired
    UserInfoService userInfoService;



    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 查询普通用户
     *
     * @param currentPage  当前页
     * @param pageSize     size
     * @param inputContent 搜索内容
     * @return data
     */
    @CrossOrigin
    @RequiresAuthentication
    @GetMapping(value = {"/user/list/{currentPage}/{pageSize}",
            "/user/list/{currentPage}/{pageSize}/{inputContent}"})
    public Result selectUserList(@PathVariable Integer currentPage,
                                 @PathVariable Integer pageSize,
                                 @PathVariable(required = false) String inputContent) {
        IPage<NormalUserInfoDto> pageData = userInfoService.selectUserListInfo(currentPage, pageSize, inputContent);

        List<NormalUserInfoDto> records = pageData.getRecords();
        // 因为前端需要把数据库中的status从1和0变成 "使用中" 和 "禁用" 两种状态，这样更方便一些
        for (NormalUserInfoDto userInfo : records) {
            if (userInfo.getStatus() == 1) {
                userInfo.setPageStatus("使用中...");
            } else if (userInfo.getStatus() == 0) {
                userInfo.setPageStatus("已禁用");
            } else {
                return null;
            }
        }

        if ("".equals(inputContent) || inputContent == null) {
            // 查询普通用户的数量
            int total = userService.count(new QueryWrapper<User>().eq("auth", 4));
            pageData.setTotal(total);
            return Result.success().codeAndMessage(ResultInfo.SUCCESS).data("data", pageData);
        } else {
            return Result.success().codeAndMessage(ResultInfo.SUCCESS).data("data", pageData);
        }

    }

    /**
     * 根据username查找用户相关信息，username唯一
     *
     * @param username 用户名
     * @return 用户信息
     */
    @CrossOrigin
    @RequiresAuthentication
    @GetMapping("/front/user/info/{username}")
    public Result getUserInfoByUsername(@PathVariable String username) {
        UserInfoDto userInfoDto = userInfoService.getUserInfoByUsername(username);
        return Result.success().codeAndMessage(ResultInfo.SUCCESS).data("data", userInfoDto);
    }


    /**
     * 选择是否禁用该用户
     *
     * @param userId 用户Id
     * @return Result
     */
    @GetMapping("/userInfo/disabled/{userId}")
    public Result changeUserStatus(@PathVariable(name = "userId") Long userId) {
        User user = userService.getById(userId);
        int status = user.getStatus();

        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", userId);
        if (status == 1) {
            updateWrapper.set("status", 0);
        } else {
            updateWrapper.set("status", 1);
        }
        userService.update(new User(), updateWrapper);
        return Result.success().codeAndMessage(ResultInfo.SUCCESS);
    }

    /**
     * 批量禁用用户
     *
     * @param userIds 用户Id数组
     * @return Result
     */
    @PostMapping("/user/batch/disable")
    public Result batchDisableUser(Long[] userIds) {

        // 将前端传过来的数组转换成集合
        List<Long> list = Arrays.asList(userIds);
        System.out.println(list);
        // 批量禁用用户
        userInfoService.batchDisableUser(list);
        return Result.success().codeAndMessage(ResultInfo.SUCCESS);
    }

    /**
     * 提升普通用户的权限至VIP
     *
     * @param userId 普通用户id
     * @return Result
     */
    @PostMapping("/user/upgrade/vip/{userId}")
    public Result upUserToVip(@PathVariable Long userId) {

        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", userId);
        updateWrapper.set("auth", 3);

        userService.update(new User(), updateWrapper);

        return Result.success().codeAndMessage(ResultInfo.SUCCESS);
    }


    // ---------------------------------------------------------------------------------------------------


    /**
     * 查询VIP用户
     *
     * @param currentPage 当前页
     * @param pageSize    size
     * @return data
     */
    @RequiresAuthentication
    @GetMapping(value = {"/vip/list/{currentPage}/{pageSize}",
            "/vip/list/{currentPage}/{pageSize}/{inputContent}"})
    public Result selectVipList(@PathVariable Integer currentPage,
                                @PathVariable Integer pageSize,
                                @PathVariable(required = false) String inputContent) {
        IPage<NormalUserInfoDto> pageData = userInfoService.selectVipListInfo(currentPage, pageSize, inputContent);

        List<NormalUserInfoDto> records = pageData.getRecords();
        // 因为我前端需要把数据库中的status从1和0变成 "使用中" 和 "禁用" 两种状态，这样更方便一些
        for (NormalUserInfoDto userInfo : records) {
            if (userInfo.getStatus() == 1) {
                userInfo.setPageStatus("使用中...");
            } else if (userInfo.getStatus() == 0) {
                userInfo.setPageStatus("已禁用");
            } else {
                return null;
            }
        }

        if ("".equals(inputContent) || inputContent == null) {
            // 查询VIP用户的数量
            int total = userService.count(new QueryWrapper<User>().eq("auth", 3));

            pageData.setTotal(total);
            return Result.success().codeAndMessage(ResultInfo.SUCCESS).data("data", pageData);
        } else {
            return Result.success().codeAndMessage(ResultInfo.SUCCESS).data("data", pageData);
        }

    }

    /**
     * 将VIP的权限降为普通用户
     *
     * @param vipId vipId
     * @return Result
     */
    @PostMapping("/vip/downgrade/user/{vipId}")
    public Result downVipToUser(@PathVariable Long vipId) {

        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", vipId);
        updateWrapper.set("auth", 4);

        userService.update(new User(), updateWrapper);

        return Result.success().codeAndMessage(ResultInfo.SUCCESS);
    }

    /**
     * 前台用户更改信息
     *
     * @param userInfo 用户传过来的要更改的信息
     * @return Result
     */
    @PostMapping("/user/update/info")
    public Result updateUserInfo(@RequestBody UserInfoDto userInfo) {

        System.out.println("userInfo = " + userInfo);
        User user = userService.getOne(new QueryWrapper<User>().eq("username", userInfo.getUsername()));
        UpdateWrapper<UserInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("nick_name", userInfo.getNickName())
                .set("real_name", userInfo.getRealName())
                .set("gender", userInfo.getGender())
                .set("birthday", userInfo.getBirthday())
                .set("phone", userInfo.getPhone())
                .set("address", userInfo.getAddress()).eq("user_id", user.getId());
        boolean userInfoUpdateRes = userInfoService.update(new UserInfo(), updateWrapper);
        log.info(userInfo.getUsername() + "更新信息===>" + userInfoUpdateRes);
        return Result.success().codeAndMessage(ResultInfo.SUCCESS);
    }



}

