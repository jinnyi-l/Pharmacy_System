package com.yjx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjx.entity.MenuFirst;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MenuFirstMapper extends BaseMapper<MenuFirst> {

    /**
     * 查询可用的一级菜单
     * @return List
     */
    List<MenuFirst> getFirstMenuList(Integer auth);

}
