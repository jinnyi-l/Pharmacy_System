package com.yjx.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjx.common.lang.Result;
import com.yjx.common.lang.ResultInfo;
import com.yjx.dto.AdminInfoDto;
import com.yjx.entity.Admin;
import com.yjx.entity.User;

import com.yjx.service.AdminService;
import com.yjx.service.UserService;
import com.yjx.util.MyPictureUploadUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@SuppressWarnings("all")
public class AdminController {

    @Autowired
    AdminService adminService;


    @Autowired
    UserService userService;

    /**
     * 查找admin表的相关信息展示
     *
     * @param currentPage  currentPage
     * @param pageSize     pageSize
     * @param inputContent 搜索的内容
     * @return Result
     */
    @RequiresAuthentication
    @GetMapping(value = {"/admin/list/{currentPage}/{pageSize}",
            "/admin/list/{currentPage}/{pageSize}/{inputContent}"})
    public Result queryAdminList(@PathVariable Integer currentPage,
                                 @PathVariable Integer pageSize,
                                 @PathVariable(required = false) String inputContent) {

        IPage<AdminInfoDto> pageData = adminService.selectAdminListInfo(currentPage, pageSize, inputContent);
        List<AdminInfoDto> records = pageData.getRecords();
        System.out.println("records = " + records);
        for (AdminInfoDto adminInfo : records) {
            if (adminInfo.getStatus() == 1) {
                adminInfo.setPageStatus("使用中...");
            } else if (adminInfo.getStatus() == 0) {
                adminInfo.setPageStatus("已禁用");
            } else {
                return null;
            }
        }
        return Result.success().codeAndMessage(ResultInfo.SUCCESS).data("data", pageData);
    }

    /**
     * 后台根据登录的管理员的id去获取他的有关信息
     *
     * @param id 管理员的id
     * @return 有关信息
     */
//    @RequiresAuthentication
    @GetMapping("/admin/info/{id}")
    public Result getAdminInfoById(@PathVariable Long id) {
        Admin admininfo = adminService.getById(id);
        return Result.success().codeAndMessage(ResultInfo.SUCCESS).data("data", admininfo);
    }

    /**
     * 改变账号状态
     *
     * @param id id
     * @return Result
     */
    @PostMapping("/admin/change/status")
    public Result changeAdminStatus(Long id) {

        Admin admin = adminService.getById(id);
        UpdateWrapper<Admin> updateWrapper = new UpdateWrapper<>();
        if (admin.getStatus() == 0) {
            updateWrapper.eq("id", id).set("status", 1);
        } else {
            updateWrapper.eq("id", id).set("status", 0);
        }
        adminService.update(new Admin(), updateWrapper);
        return Result.success();
    }

    /**
     * 添加管理员账号
     *
     * @param form 携带了信息的表单
     * @return Result
     */
    @PostMapping("/admin/add")
    public Result addAdmin(@RequestBody Admin form) {

        Admin one = adminService.getOne(new QueryWrapper<Admin>().eq("username", form.getUsername()));
        if (one != null) {
            return Result.error().message("该账号已经有人在用了！");
        }
        System.out.println("form = " + form);



        // 添加代码
        User user = new User();
        user.setUsername(form.getUsername());
        user.setPassword(DigestUtils.md5DigestAsHex(form.getPassword().getBytes()));
        boolean b = this.userService.saveOrUpdate(user);


        Admin admin = new Admin();

        admin.setId(user.getId());
        // 设置用户名
        admin.setUsername(form.getUsername());
        // 密码进行md5加密
        admin.setPassword(DigestUtils.md5DigestAsHex(form.getPassword().getBytes()));
        // 设置真实姓名
        admin.setRealName(form.getRealName());
        // 设置为普通管理员
        admin.setAuth(2);
        // 设置性别，默认为男
        admin.setGender("男");
        // 设置默认头像
        admin.setAvatar("img/avatar/defaultAvatar.jpg");
        // 设置邮箱
        admin.setEmail(form.getEmail());
        adminService.saveOrUpdate(admin);







        return Result.success().message("添加成功！");
    }


    /**
     * 修改管理员信息
     *
     * @param admin 携带了信息的表单
     * @return Result
     */
    @PostMapping("/admin/update")
    public Result updateAdmin(@RequestBody Admin admin) {

        // 查出要更改信息的账号
        Admin oldAdmin = adminService.selectSomeAdminInfo(admin.getId());
        if (oldAdmin.equals(admin)) {
            return Result.error().message("没有修改信息！");
        }
        System.out.println("admin = " + admin);
        UpdateWrapper<Admin> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", admin.getId())
                .set("avatar", admin.getAvatar())
                .set("gender", admin.getGender())
                .set("birthday", admin.getBirthday())
                .set("phone", admin.getPhone())
                .set("address", admin.getAddress())
                .set("signature", admin.getSignature());
        adminService.update(new Admin(), updateWrapper);
        return Result.success().message("修改成功！");
    }




}

