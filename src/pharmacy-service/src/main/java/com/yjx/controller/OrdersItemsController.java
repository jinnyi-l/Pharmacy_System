package com.yjx.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yjx.common.lang.Result;
import com.yjx.entity.Orders;
import com.yjx.entity.OrdersItems;
import com.yjx.service.OrdersItemsService;
import com.yjx.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@SuppressWarnings("all")
@Slf4j
public class OrdersItemsController {

    @Autowired
    OrdersItemsService ordersItemsService;

    @Autowired
    OrdersService ordersService;

    /**
     * 根据订单编号，分别在orders，ordersItems查询订单相关信息
     *
     * @param orderId 订单编号
     * @return 订单清单信息
     */
    @RequiresAuthentication
    @PostMapping("/order/details")
    public Result getOrderDetailsById(String orderId) {

        System.out.println("orderId = " + orderId);
        // 查询订单信息
        Orders orderInfo = ordersService.getOne(new QueryWrapper<Orders>().eq("order_id", orderId));
        // 判断该订单购买的药有多少种
        // 这是只有一种的情况
        if (orderInfo.getItemAmount() == 1) {
            // 查询订单清单信息
            // 统一返回数据类型，只有一个也用list返回
            List<OrdersItems> ordersItemsList = new ArrayList<>();
            OrdersItems orderItemDetails = ordersItemsService.getOne(new QueryWrapper<OrdersItems>().eq("order_id", orderId));
            ordersItemsList.add(orderItemDetails);
            System.out.println("orderDetails = " + orderItemDetails);
            return Result.success().data("responseBody", ordersItemsList);
        }
        // 多种的情况
        else {
            List<OrdersItems> ordersItemsList = ordersItemsService.list(new QueryWrapper<OrdersItems>().eq("order_id", orderId));
            return Result.success().data("responseBody", ordersItemsList);
        }
    }

}

