package com.yjx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjx.entity.ShoppingCart;
import org.springframework.stereotype.Component;

@Component
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {

    /**
     * 根据药名和用户名查找该用户是否添加该药
     *
     * @param drugName 药品名称
     * @param username 用户名
     * @return 数量：用来判断有没有添加这种药
     */
    Integer getCartInfo(String drugName, String username);

    /**
     * 根据用户名和药名查找该药添加的数量
     *
     * @param drugName 药品名称
     * @param username 用户名
     * @return 药的数量amount
     */
    Integer getDrugAmount(String drugName, String username);
}
