package com.yjx.mapper;

import com.yjx.entity.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

@Component
public interface OrdersMapper extends BaseMapper<Orders> {

    /**
     * 最近一个月内的投诉订单数
     *
     * @return 最近一个月内的投诉订单数
     */
    Integer getNewComplaintNumsOrderOnMonth();
}
