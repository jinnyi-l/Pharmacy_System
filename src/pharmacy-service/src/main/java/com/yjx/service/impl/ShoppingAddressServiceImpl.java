package com.yjx.service.impl;

import com.yjx.entity.ShoppingAddress;
import com.yjx.mapper.ShoppingAddressMapper;
import com.yjx.service.ShoppingAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingAddressServiceImpl extends ServiceImpl<ShoppingAddressMapper, ShoppingAddress> implements ShoppingAddressService {

    @Autowired
    ShoppingAddressMapper shoppingAddressMapper;

    @Override
    public Boolean queryHasCommon(ShoppingAddress shoppingAddress, String username) {
        Integer integer = shoppingAddressMapper.queryHasCommon(shoppingAddress, username);
        return integer == 0;
    }

    @Override
    public Boolean hasDefault(String username) {
        Integer integer = shoppingAddressMapper.queryDefaultNum(username);
        return integer == 1;
    }
}
