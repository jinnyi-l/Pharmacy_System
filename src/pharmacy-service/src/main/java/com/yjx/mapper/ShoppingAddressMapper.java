package com.yjx.mapper;

import com.yjx.entity.ShoppingAddress;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface ShoppingAddressMapper extends BaseMapper<ShoppingAddress> {

    /**
     * 查询是否已经有一样的记录
     *
     * @param shoppingAddress 收货地址信息
     * @param username 用户名
     * @return 数量
     */
    Integer queryHasCommon(@Param("shoppingAddress") ShoppingAddress shoppingAddress, @Param("username") String username);

    /**
     * 查询该用户设置为默认的数量
     *
     * @param username 用户名
     * @return 数量
     */
    Integer queryDefaultNum(@Param("username") String username);

}
