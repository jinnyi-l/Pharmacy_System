package com.yjx.service.impl;

import com.yjx.entity.MenuFirst;
import com.yjx.mapper.MenuFirstMapper;
import com.yjx.service.MenuFirstService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuFirstServiceImpl extends ServiceImpl<MenuFirstMapper, MenuFirst> implements MenuFirstService {

    @Autowired
    MenuFirstMapper menuFirstMapper;

    @Override
    public List<MenuFirst> getFirstMenuList(Integer auth) {
        return menuFirstMapper.getFirstMenuList(auth);
    }
}
