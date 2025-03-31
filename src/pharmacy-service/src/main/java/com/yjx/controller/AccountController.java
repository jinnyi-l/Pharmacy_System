package com.yjx.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yjx.dto.LoginDto;
import com.yjx.dto.RegisterDto;
import com.yjx.common.lang.Result;
import com.yjx.common.lang.ResultInfo;
import com.yjx.dto.UserInfoDto;
import com.yjx.entity.Admin;
import com.yjx.entity.User;
import com.yjx.entity.UserInfo;
import com.yjx.service.AdminService;
import com.yjx.service.UserInfoService;
import com.yjx.service.UserService;
import com.yjx.smtp.SendMailService;
import com.yjx.util.JwtUtils;
import com.yjx.util.VerificationUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Slf4j
@SuppressWarnings("all")
@RestController
public class AccountController {

    @Autowired
    AdminService adminService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    VerificationUtils verificationUtils;

    @Autowired
    SendMailService sendMailService;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    UserService userService;

    @Autowired
    UserInfoService userInfoService;

    /**
     * 没有放进缓存前的邮箱生成的验证码
     */
    private String code;

    /**
     * 数学算术的验证码结果
     */
    private String res;

    /**
     * redis里查找是否有登录的用户信息，用来跟前端配合，判断用户的登录状态
     *
     * @param username 用户名
     * @return 用户信息
     */
    @CrossOrigin
    @GetMapping("/has/user/{username}")
    public Result hasUser(@PathVariable String username) {

        Boolean status = redisTemplate.hasKey(username);
        return Result.success().codeAndMessage(ResultInfo.SUCCESS).data("isLogin", status);
    }


    /**
     * 生成验证码图片
     *
     * @param response 响应
     * @return 结果
     */
    @GetMapping("/verification")
    public String verification(HttpServletResponse response) {
        try {
            // 生成png的验证码，res是结果
            res = verificationUtils.setSpecCaptcha(response, 130, 48, 5);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 后台登录--账号登录
     *
     * @param loginDto loginDto
     * @param response response
     * @return Result
     */
    @CrossOrigin
    @PostMapping(value = "/admin/login")
    public Result accountLogin(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response) {
        // 根据用户名获取用户
        Admin admin = adminService.getOne(new QueryWrapper<Admin>().eq("username", loginDto.getUsername()));
        if (res == null) {
            log.info("刷新验证码！！！");
            return Result.error().message("验证码超时，请刷新验证码！");
        }
        // 1.判断验证码是否匹配
        else if (!res.toLowerCase().equals(loginDto.getVerificationCode().toLowerCase())) {
            log.info("验证码错误！！！");
            return Result.error().message("验证码错误！");
        }
        // 2.如果用户名为空，就返回用户不存在
        else if (admin == null) {
            log.info("用户 " + loginDto.getUsername() + " 不存在！");
            return Result.error().message("用户不存在！");
        }
        // 3.判断密码是否正确
        else if (!admin.getPassword().equals(DigestUtils.md5DigestAsHex(loginDto.getPassword().getBytes()))) {
            log.info("密码错误！！！");
            return Result.error().message("密码错误！");
        }
        // 还得判断账号是否可用
        else if (admin.getStatus() == 0) {
            log.info("账号被禁用！");
            return Result.error().message("账号被禁用！");
        }
        // 用户名，密码，验证码都正确且账号可用就开始获取用户的信息并封装返回
        String jwt = jwtUtils.generateToken(admin.getId());

        response.setHeader("Authorization", jwt);
        response.setHeader("Access-control-Expose-Headers", "Authorization");

        Map<String, Object> adminInfo = new HashMap<>(6);
        adminInfo.put("id", admin.getId());
        adminInfo.put("username", admin.getUsername());

        return Result.success().message("登录成功！").data("adminInfo", adminInfo);
    }

    /**
     * 获取邮箱验证码
     *
     * @param email 接收邮箱的地址
     * @return
     */
    @CrossOrigin
    @GetMapping("/get/emailCode/{email}")
    public Result sendHtml(@PathVariable String email) {
        try {
            // 没有放进缓存前的邮箱生成的验证码
            code = sendMailService.sendAdminLoginEmailCode(email);
            // 将生成的验证码放进缓存，设置时间为5分钟
            redisTemplate.opsForValue().set("emailCode" + email, code, 60 * 5, TimeUnit.SECONDS);
            log.info("发送成功-->收件人：" + email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.success().codeAndMessage(ResultInfo.SUCCESS).data("msg", "验证码获取成功，请在5分钟内使用！");
    }

    /**
     * 后台登录--邮箱登录
     *
     * @param loginDto loginDto
     * @param response response
     * @return Result
     */
    @CrossOrigin
    @PostMapping(value = "/admin/email/login")
    public Result emailLogin(@RequestBody LoginDto loginDto, HttpServletResponse response) {
        String emailCode = (String) redisTemplate.opsForValue().get("emailCode" + loginDto.getEmail());

        Admin admin = adminService.getOne(new QueryWrapper<Admin>().eq("email", loginDto.getEmail()));
        // 先根据邮箱判断用户是否存在
        if (admin == null) {
            log.info("邮箱地址 " + loginDto.getEmail() + " 不存在！");
            return Result.error().message("邮箱地址不存在！");
        } else if (code == null) {
            // 存在，判断验证码是否生成，不然缓存里必然没有验证码
            log.info("请先获取验证码！");
            return Result.error().message("请先获取验证码！");
        } else if (emailCode == null) {
            // 到这里验证码已经生成且放入缓存，那么就判断缓存是否过期
            log.info("请重新获取验证码！");
            return Result.error().message("验证码超时，请重新获取验证码！");
        } else if (emailCode != null) {
            // 没有过期，判断用户输入的验证码跟生成的是不是不一样
            if (!loginDto.getEmailCode().toLowerCase().equals(emailCode.toLowerCase())) {
                log.info("验证码错误！！！");
                return Result.error().message("验证码错误！");
            } else {
                // 还得判断账号是否可用
                if (admin.getStatus() == 0) {
                    log.info("账号被禁用！");
                    return Result.error().message("账号被禁用！");
                } else {
                    // 到这里说明用户已经可以用验证码登录，那就把缓存里的验证码删除
                    redisTemplate.delete("emailCode" + loginDto.getEmail());

                    String jwt = jwtUtils.generateToken(admin.getId());
                    response.setHeader("Authorization", jwt);
                    response.setHeader("Access-control-Expose-Headers", "Authorization");

                    Map<String, Object> adminInfo = new HashMap<>(12);
                    adminInfo.put("id", admin.getId());
                    adminInfo.put("username", admin.getUsername());

                    return Result.success().message("登录成功！").data("adminInfo", adminInfo);
                }
            }
        }
        return null;
    }


    /**
     * 退出账号
     *
     * @return Result
     */
    @RequiresAuthentication
    @GetMapping("/logout/{username}")
    public Result logout(@PathVariable String username) {
        SecurityUtils.getSubject().logout();
        redisTemplate.delete(username);
        return Result.success();
    }


    // ----------------------------------------------------------------------------------------
    // 前台账户相关的操作
    // ----------------------------------------------------------------------------------------


    /**
     * 前台登录--账号登录
     *
     * @param loginDto loginDto
     * @param response response
     * @return Result
     */
    @CrossOrigin
    @PostMapping(value = "/user/login")
    public Result reontAccountLogin(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response) {
        // 根据用户名获取用户
        User user = userService.getOne(new QueryWrapper<User>().eq("username", loginDto.getUsername()));
        System.out.println("loginDto.getUsername() = " + loginDto.getUsername());
        // 没有验证码的情况也可能出现
        if (res == null) {
            log.info("请刷新验证码");
            return Result.error().message("验证码超时，请刷新验证码");
        }
        // 1.先判断验证码是否匹配
        else if (!res.toLowerCase().equals(loginDto.getVerificationCode().toLowerCase())) {
            log.info("验证码不匹配");
            return Result.error().message("验证码错误");
        }
        // 2.如果用户名为空，就返回用户不存在
        else if (user == null) {
            log.info("用户 " + loginDto.getUsername() + " 不存在！");
            return Result.error().message("用户不存在");
        }
        // 3.用户存在，判断密码是否正确
        else if (!user.getPassword().equals(DigestUtils.md5DigestAsHex(loginDto.getPassword().getBytes()))) {
            log.info("密码错误！！！");
            return Result.error().message("密码错误");
        }
        // 4.还得判断账号是否可用
        else if (user.getStatus() == 0) {
            log.info("账号被禁用！");
            return Result.error().message("账号被禁用！");
        }
        // 5.用户名，密码，都正确就开始获取用户的信息并封装返回
        String jwt = jwtUtils.generateToken(user.getId());

        response.setHeader("Authorization", jwt);
        response.setHeader("Access-control-Expose-Headers", "Authorization");

        // 从userInfo表获取的信息
        UserInfo userInfo = userInfoService.getOne(new LambdaQueryWrapper<UserInfo>()
                .eq(UserInfo::getUserId, user.getId()));

        Map<String, Object> userDetailsInfo = new HashMap<>(15);
        userDetailsInfo.put("id", user.getId());
        userDetailsInfo.put("username", user.getUsername());
        userDetailsInfo.put("auth", user.getAuth());
        userDetailsInfo.put("avatar", userInfo.getAvatar());

        redisTemplate.opsForValue().set(user.getUsername(), userDetailsInfo, 30, TimeUnit.DAYS);
        return Result.success().message("登录成功").data("userInfo", redisTemplate.opsForValue().get(user.getUsername()));
    }


    /**
     * 前台注册一个账户
     *
     * @return 注册结果
     */
    @CrossOrigin
    @PostMapping(value = "/user/register")
    public Result registerAccount(@RequestBody RegisterDto registerDto) {

                // 拿到注册信息，添加到uer表中
                User user = new User();
                user.setUsername(registerDto.getUsername());
                // 密码进行md5加密，不可逆
                user.setPassword(DigestUtils.md5DigestAsHex(registerDto.getPassword().getBytes()));
                // 设置为权限为普通用户
                user.setAuth(4);
                boolean resUser = userService.saveOrUpdate(user);
                log.info("创建user账户==>" + resUser);
                // 根据唯一的用户名查找到这个添加的新用户
                User freshUser = userService.getOne(new QueryWrapper<User>().eq("username", registerDto.getUsername()));
                // 给这个新用户在user_info表中与userId关联，用来保存个人信息
                UserInfo userInfo = new UserInfo();
                userInfo.setUserId(freshUser.getId());
                userInfo.setEmail(registerDto.getEmail());
                userInfo.setAvatar("img/avatar/defaultAvatar.png");
                boolean resUserInfo = userInfoService.saveOrUpdate(userInfo);
                log.info("初始化user信息==>" + resUserInfo);
                System.out.println("registerDto = " + registerDto);
                log.info("注册成功！");
                redisTemplate.delete("emailCode" + registerDto.getEmail());
                HashMap<String, Object> resBody = new HashMap<>(2);
                resBody.put("username", registerDto.getUsername());
                resBody.put("password", registerDto.getPassword());
                return Result.success().message("注册成功").data("responseBody", resBody);


    }

    @PostMapping(value = "/user/password/update")
    public Result updateUserPassword(@RequestBody RegisterDto updateDto) {


            Result result = userService.updateUserPassword(updateDto);
            return result;


    }


}
