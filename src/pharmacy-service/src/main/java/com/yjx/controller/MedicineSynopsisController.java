package com.yjx.controller;


import com.yjx.common.lang.Result;
import com.yjx.common.lang.ResultInfo;
import com.yjx.entity.MedicineSynopsis;
import com.yjx.service.MedicineSynopsisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MedicineSynopsisController {

    @Autowired
    MedicineSynopsisService medicineSynopsisService;

    /**
     * 根据药品id获取说明书信息
     * @param id id
     * @return 说明书信息
     */
    @GetMapping("/synopsis/info/{id}")
    public Result getSynopsisInfoById(@PathVariable(name = "id") Long id) {

        MedicineSynopsis synopsisInfo = medicineSynopsisService.getById(id);
        return Result.success().codeAndMessage(ResultInfo.SUCCESS).data("data",synopsisInfo);
    }

}

