package com.yjx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yjx.entity.ShoppingAddress;
import org.apache.ibatis.annotations.Param;

public interface ShoppingAddressService extends IService<ShoppingAddress> {

    /**
     * 查询是否已经有一样的记录
     *
     * @param shoppingAddress 收货地址信息
     * @param username 用户名
     * @return 数量
     */
    Boolean queryHasCommon(@Param("shoppingAddress") ShoppingAddress shoppingAddress, @Param("username") String username);


    /**
     * 查询该用户有没有设置默认的地址
     *
     * @param username 用户名
     * @return 数量
     */
    Boolean hasDefault(@Param("username") String username);
}
