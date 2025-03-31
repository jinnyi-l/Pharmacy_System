package com.yjx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjx.entity.ShoppingCart;
import com.yjx.mapper.ShoppingCartMapper;
import com.yjx.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {

    @Autowired
    ShoppingCartMapper shoppingCartMapper;

    @Override
    public Integer getCartInfo(String drugName, String username) {
        return shoppingCartMapper.getCartInfo(drugName,username);
    }

    @Override
    public Integer getDrugAmount(String drugName, String username) {
        return shoppingCartMapper.getDrugAmount(drugName, username);
    }

    @Override
    public void removeByName(String drugName, String username) {

        // 获取这个记录
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<ShoppingCart>()
                .eq(ShoppingCart::getDrugName, drugName)
                .eq(ShoppingCart::getUsername, username);


        ShoppingCart item = this.getOne(wrapper);

        if (item == null) return;


        // 删除记录
        this.removeById(item.getId());



    }

}
