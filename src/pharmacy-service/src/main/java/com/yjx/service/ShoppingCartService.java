package com.yjx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yjx.entity.ShoppingCart;

public interface ShoppingCartService extends IService<ShoppingCart> {

    /**
     * 根据药名查找购物车需要的药品信息
     *
     * @param drugName 药品名称
     * @param username 用户名
     * @return 购物车需要的药品信息
     */
    Integer getCartInfo(String drugName,String username);

    /**
     * 根据用户名和药名查找该药添加的数量
     *
     * @param drugName 药品名称
     * @param username 用户名
     * @return 药的数量amount
     */
    Integer getDrugAmount(String drugName, String username);


    void removeByName(String drugName, String username);
}
