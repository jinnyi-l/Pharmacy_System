package com.yjx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yjx.entity.MenuSecond;

import java.util.List;

public interface MenuSecondService extends IService<MenuSecond> {

    /**
     * 查询二级菜单
     * @param fatherId  父菜单id
     * @param auth  权限等级
     * @return List
     */
    List<MenuSecond> getSecondMenuList(String fatherId,Integer auth);
}
