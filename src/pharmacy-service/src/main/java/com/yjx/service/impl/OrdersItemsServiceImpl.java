package com.yjx.service.impl;

import com.yjx.entity.OrdersItems;
import com.yjx.mapper.OrdersItemsMapper;
import com.yjx.service.OrdersItemsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class OrdersItemsServiceImpl extends ServiceImpl<OrdersItemsMapper, OrdersItems> implements OrdersItemsService {

}
