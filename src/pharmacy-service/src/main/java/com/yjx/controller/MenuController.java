package com.yjx.controller;


import com.yjx.common.lang.Result;
import com.yjx.common.lang.ResultInfo;
import com.yjx.entity.MenuFirst;
import com.yjx.entity.MenuSecond;
import com.yjx.service.MenuFirstService;
import com.yjx.service.MenuSecondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MenuController {

    @Autowired
    MenuFirstService menuFirstService;

    @Autowired
    MenuSecondService menuSecondService;

    @GetMapping("/menu/list/{auth}")
    public Result queryMenuList(@PathVariable Integer auth) {

        List<MenuFirst> firstMenuList = menuFirstService.getFirstMenuList(auth);

        for (MenuFirst menuFirst : firstMenuList) {
            List<MenuSecond> secondMenuList = menuSecondService.getSecondMenuList(menuFirst.getMenuId(),auth);
            menuFirst.setChildren(secondMenuList);
        }

        return Result.success().codeAndMessage(ResultInfo.SUCCESS).data("data", firstMenuList);
    }

}

