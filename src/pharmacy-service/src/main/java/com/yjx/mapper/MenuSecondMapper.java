package com.yjx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjx.entity.MenuSecond;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MenuSecondMapper extends BaseMapper<MenuSecond> {

    /**
     * 查询二级菜单
     * @param fatherId 父菜单的id
     * @param auth 需要的权限等级
     * @return List
     */
    List<MenuSecond> getSecondMenuList(String fatherId,Integer auth);

}
