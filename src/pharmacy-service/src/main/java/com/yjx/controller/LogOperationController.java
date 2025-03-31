package com.yjx.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjx.common.lang.Result;
import com.yjx.entity.LogOperation;
import com.yjx.service.LogOperationService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogOperationController {

    @Autowired
    LogOperationService logOperationService;

    /**
     * 添加操作日志
     *
     * @param auth   身份
     * @param admin  管理员
     * @param doWhat 干了啥事
     * @return Result
     */
    @PostMapping("/log/add")
    public Result logAdd(String auth, String admin, String doWhat) {

        LogOperation logOperation = new LogOperation();
        logOperation.setAuth(auth).setAdmin(admin).setDoWhat(doWhat);
        logOperationService.save(logOperation);
        return Result.success();
    }

    /**
     * 查询全部日志
     *
     * @param current 当前页
     * @param pageSize 数量
     * @return responseBody
     */
    @RequiresAuthentication
    @PostMapping("/log/look")
    public Result lookAllLog(Integer current, Integer pageSize) {

        Page<LogOperation> page = new Page<>(current, pageSize);
        Page<LogOperation> data = logOperationService.page(page,
                new QueryWrapper<LogOperation>().orderByDesc("gmt_create"));
        return Result.success().data("responseBody", data);
    }

}

