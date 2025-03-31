package com.yjx.controller;


import com.alipay.api.request.AlipayDataBillDownloadurlGetRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjx.common.lang.Result;
import com.yjx.common.lang.ResultInfo;
import com.yjx.dto.OrdersDto;
import com.yjx.entity.Orders;
import com.yjx.entity.OrdersItems;
import com.yjx.entity.ShoppingCart;
import com.yjx.service.OrdersItemsService;
import com.yjx.service.OrdersService;
import com.yjx.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;


@RestController
@Slf4j
public class OrdersController {

    @Autowired
    OrdersService ordersService;

    @Autowired
    OrdersItemsService ordersItemsService;

    @Autowired
    ShoppingCartService shoppingCartService;

    /**
     * 生成订单
     * 之前这里犯了一个找了很久的错，怎么看都看不出来，其他接口都是可以正常访问的，但是这个类的接口全部报错，
     * 只是因为类名用的是Order , 这是MySQL的关键字，导致SQL执行全部报错。
     *
     * @param ordersInfo 订单基本信息
     * @return 订单号
     */
    @PostMapping("/init/order")
    public Result buyDrugOrderInfo(@RequestBody OrdersDto ordersInfo) {

        try {
            System.out.println("ordersInfo = " + ordersInfo);
            Calendar calendar = Calendar.getInstance();
            String time = new SimpleDateFormat("yyyyMMddHHmmss").format(calendar.getTime());
            String orderId = "Y" + time + Math.round(Math.floor(Math.random() * 8999 + 1000));
            System.out.println("Math.ceil(Math.random() * 8999 + 1000) = " + Math.round(Math.floor(Math.random() * 8999 + 1000)));
            // 生成订单信息

            // 对药品库存进行检查

            boolean ok = this.ordersService.checkGoodStock(ordersInfo);
            if (!ok) {
                return Result.error().message("订单提交失败");
            }


            Orders orders = new Orders();
            BeanUtils.copyProperties(ordersInfo, orders);
            orders.setOrderId(orderId)
                    .setItemAmount(ordersInfo.getDrugs().size());
            ordersService.saveOrUpdate(orders);
            // 根据orderId生成对应的清单信息
            for (OrdersItems drug : ordersInfo.getDrugs()) {
                OrdersItems ordersItems = new OrdersItems();
                ordersItems.setOrderId(orderId)
                        .setDrugName(drug.getDrugName())
                        .setAmount(drug.getAmount())
                        .setPicture(drug.getPicture())
                        .setPrice(drug.getPrice())
                        .setStandard(drug.getStandard());
                ordersItemsService.save(ordersItems);
                //删除购物车药品信息
                shoppingCartService.removeByName(drug.getDrugName(),ordersInfo.getUsername());
//                shoppingCartService.removeById(drug.getKey());
            }
            // 返回订单编号
            return Result.success().data("orderId", orderId);
        } catch (Exception e) {
            // 打印异常信息
            log.info(e.getMessage());
            return Result.error();
        }
    }

    /**
     * 用户根据用户名查询订单，根据状态分类：1.待发货，2.待收货，3.待评价
     *
     * @param username 用户账号
     * @param current  当前页
     * @param status   订单状态
     * @return 对应的订单集合
     */
    @GetMapping("/order/{username}/{current}/{status}")
    @RequiresAuthentication
    public Result getOrdersByUsername(@PathVariable String username, @PathVariable Integer current, @PathVariable Integer status) {

        Page<Orders> ordersPage = new Page<>(current, 10);
        Page<Orders> orders = ordersService.page(ordersPage, new QueryWrapper<Orders>()
                .eq("username", username).eq("status", status).orderByDesc("gmt_create"));
        // 拿到分页的记录
        List<Orders> records = orders.getRecords();
        List<OrdersDto> backList = new ArrayList<>();
        for (Orders record : records) {
            // 获取订单编号，根据订单编号获取清单信息
            String orderId = record.getOrderId();
            List<OrdersItems> ordersItemsList = ordersItemsService.list(new QueryWrapper<OrdersItems>().eq("order_id", orderId));
            OrdersDto ordersDto = new OrdersDto();
            ordersDto.setDrugs(ordersItemsList);
            // 克隆对象
            BeanUtils.copyProperties(record, ordersDto);
            backList.add(ordersDto);
        }
        Map<String, Object> map = new HashMap<>(8);
        map.put("total", orders.getTotal());
        map.put("current", orders.getCurrent());
        map.put("orderList", backList);
        return Result.success().data("data", map);
    }

    /**
     * 查询全部订单
     *
     * @return 全部订单
     */
    @CrossOrigin
    @GetMapping("/order/all")
    public Result getAll() {

        List<Orders> list = ordersService.list();
        return Result.success().data("data", list);
    }

    /**
     * 根据订单编号，分别在orders，ordersItems查询订单相关信息
     *
     * @param orderId 订单编号
     * @return orderInfo
     */
    @RequiresAuthentication
    @PostMapping("/order/info")
    public Result getOrderDetailsById(String orderId) {

        Orders orderInfo = ordersService.getOne(new QueryWrapper<Orders>().eq("order_id", orderId));
        return Result.success().data("responseBody", orderInfo);
    }

    /**
     * 管理员查询订单，根据状态分类：1.待发货，2.待收货，3.待评价
     * 和前台用户查询自己的订单差不多，只不过这里不需要用户名
     *
     * @param current 当前页
     * @param status  订单状态
     * @return 对应的订单集合
     */
    @GetMapping("/admin/order/{current}/{size}/{status}")
    public Result adminGetOrders(@PathVariable Integer current,
                                 @PathVariable Integer size,
                                 @PathVariable Integer status) {

        Page<Orders> ordersPage = new Page<>(current, size);
        Page<Orders> orders = ordersService.page(ordersPage, new QueryWrapper<Orders>()
                .eq("status", status).orderByDesc("gmt_create"));
        // 拿到分页的记录
        List<Orders> records = orders.getRecords();
        List<OrdersDto> backList = new ArrayList<>();
        for (Orders record : records) {
            // 获取订单编号，根据订单编号获取清单信息
            String orderId = record.getOrderId();
            List<OrdersItems> ordersItemsList = ordersItemsService.list(new QueryWrapper<OrdersItems>().eq("order_id", orderId));
            OrdersDto ordersDto = new OrdersDto();
            ordersDto.setDrugs(ordersItemsList);
            // 克隆对象
            BeanUtils.copyProperties(record, ordersDto);
            backList.add(ordersDto);
        }
        Map<String, Object> map = new HashMap<>(8);
        map.put("total", orders.getTotal());
        map.put("current", orders.getCurrent());
        map.put("orderList", backList);
        return Result.success().data("responseBody", map);
    }

    /**
     * 管理员查询被投诉的订单
     *
     * @param current   当前页
     * @param complaint 订单状态
     * @return 对应的订单集合
     */
    @GetMapping("/admin/complaint/order/{current}/{size}/{complaint}")
    public Result adminGetComplaintOrders(@PathVariable Integer current,
                                          @PathVariable Integer size,
                                          @PathVariable Integer complaint) {

        Page<Orders> ordersPage = new Page<>(current, size);
        Page<Orders> orders = ordersService.page(ordersPage, new QueryWrapper<Orders>()
                .eq("complaint", complaint).orderByDesc("gmt_create"));
        // 拿到分页的记录
        List<Orders> records = orders.getRecords();
        List<OrdersDto> backList = new ArrayList<>();
        for (Orders record : records) {
            // 获取订单编号，根据订单编号获取清单信息
            String orderId = record.getOrderId();
            List<OrdersItems> ordersItemsList = ordersItemsService.list(new QueryWrapper<OrdersItems>().eq("order_id", orderId));
            OrdersDto ordersDto = new OrdersDto();
            ordersDto.setDrugs(ordersItemsList);
            // 克隆对象
            BeanUtils.copyProperties(record, ordersDto);
            backList.add(ordersDto);
        }
        Map<String, Object> map = new HashMap<>(8);
        map.put("total", orders.getTotal());
        map.put("current", orders.getCurrent());
        map.put("orderList", backList);
        return Result.success().data("responseBody", map);
    }

    /**
     * 管理员查看订单后根据订单编号发货
     * 修改订单status为2
     *
     * @param orderId 订单编号
     * @return Result
     */
    @PostMapping("/admin/order/send")
    public Result adminSendOrder(String orderId) {

        System.out.println("orderId = " + orderId);
        UpdateWrapper<Orders> updateWrapper = new UpdateWrapper<>();
        // status=2，运输中。。。
        updateWrapper.set("status", 2).eq("order_id", orderId);
        ordersService.update(new Orders(), updateWrapper);

        // 对商品库存量进行更新
        this.ordersService.subGoodCount(orderId);


        return Result.success().message("发货成功！");
    }

    /**
     * 根据地区筛选订单，分页
     *
     * @param receiptAddress 例如：江西省/南昌市/青山湖区
     * @param current        当前页
     * @param size           个数
     * @param status         状态
     * @return responseBody
     */
    @PostMapping("/admin/orderWith/address")
    public Result adminGetOrdersWithAddress(String receiptAddress,
                                            Integer current,
                                            Integer size,
                                            Integer status) {

        Page<Orders> ordersPage = new Page<>(current, size);
        Page<Orders> orders = ordersService.page(ordersPage, new QueryWrapper<Orders>()
                .eq("status", status).eq("receipt_address", receiptAddress).orderByDesc("gmt_create"));
        // 拿到分页的记录
        List<Orders> records = orders.getRecords();
        List<OrdersDto> backList = new ArrayList<>();
        for (Orders record : records) {
            // 获取订单编号，根据订单编号获取清单信息
            String orderId = record.getOrderId();
            List<OrdersItems> ordersItemsList = ordersItemsService.list(new QueryWrapper<OrdersItems>().eq("order_id", orderId));
            OrdersDto ordersDto = new OrdersDto();
            ordersDto.setDrugs(ordersItemsList);
            // 克隆对象
            BeanUtils.copyProperties(record, ordersDto);
            backList.add(ordersDto);
        }
        Map<String, Object> map = new HashMap<>(8);
        map.put("total", orders.getTotal());
        map.put("current", orders.getCurrent());
        map.put("orderList", backList);
        return Result.success().data("responseBody", map);
    }

    /**
     * 精确搜索，1.订单编号，2.收货人，3.联系方式
     *
     * @param type    1==>订单编号，2==>收货人，3==>联系方式
     * @param content 搜索的内容
     * @param current 当前页
     * @param size    每页的个数
     * @param status  状态 1==>未收货。。。
     * @return responseBody
     */
    @PostMapping("/admin/orderWith/search")
    public Result adminGetOrdersWithSearchContent(Integer type,
                                                  String content,
                                                  Integer current,
                                                  Integer size,
                                                  Integer status) {
        Page<Orders> ordersPage = new Page<>(current, size);
        Page<Orders> orders = null;
        // 类型1 = 根据订单编号搜索
        int searchForOrderId = 1;
        // 根据收货人名字搜索
        int searchForReceiptName = 2;
        // 根据收货人联系方式搜索
        int searchForPhone = 3;
        if (type == searchForOrderId) {
            orders = ordersService.page(ordersPage, new QueryWrapper<Orders>()
                    .eq("status", status).eq("order_id", content).orderByDesc("gmt_create"));
        } else if (type == searchForReceiptName) {
            orders = ordersService.page(ordersPage, new QueryWrapper<Orders>()
                    .eq("status", status).eq("receipt_name", content).orderByDesc("gmt_create"));
        } else if (type == searchForPhone) {
            orders = ordersService.page(ordersPage, new QueryWrapper<Orders>()
                    .eq("status", status).eq("phone", content).orderByDesc("gmt_create"));
        } else {
            return null;
        }
        assert orders != null;
        // 拿到分页的记录
        List<Orders> records = orders.getRecords();
        List<OrdersDto> backList = new ArrayList<>();
        for (Orders record : records) {
            // 获取订单编号，根据订单编号获取清单信息
            String orderId = record.getOrderId();
            List<OrdersItems> ordersItemsList = ordersItemsService.list(new QueryWrapper<OrdersItems>().eq("order_id", orderId));
            OrdersDto ordersDto = new OrdersDto();
            ordersDto.setDrugs(ordersItemsList);
            // 克隆对象
            BeanUtils.copyProperties(record, ordersDto);
            backList.add(ordersDto);
        }
        Map<String, Object> map = new HashMap<>(8);
        map.put("total", orders.getTotal());
        map.put("current", orders.getCurrent());
        map.put("orderList", backList);
        return Result.success().data("responseBody", map);
    }

    /**
     * 用户进行订单投诉
     *
     * @param orderId 订单编号
     * @param reason  投诉理由
     * @return Result
     */
    @RequiresAuthentication
    @PostMapping("/user/complaint")
    public Result orderComplaint(String orderId, String reason) {

        return ordersService.orderComplaint(orderId, reason);
    }

    /**
     * 用户确认订单
     *
     * @param orderId 订单Id
     * @return Result
     */
    @RequiresAuthentication
    @PostMapping("/user/order/confirm")
    public Result confirmReceipt(String orderId) {

        return ordersService.confirmReceipt(orderId);
    }

    /**
     * 获取近一个月内的投诉订单数
     *
     * @return Result
     */
    @PostMapping("/order/complaint/nums")
    public Result getComplaintOrderNums() {

        return ordersService.getNewComplaintNumsOrderOnMonth();
    }

    /**
     * 获取未发货的订单数
     *
     * @return Result
     */
    @PostMapping("/order/undone/nums")
    public Result getUndoneOrderNums() {

        int count = ordersService.count(new QueryWrapper<Orders>().eq("status", 1));
        return Result.success().codeAndMessage(ResultInfo.SUCCESS).data("responseBody", count);
    }

    @RequiresAuthentication
    @PostMapping("/user/delete/order")
    public Result deleteThisOrder(String orderId){

        boolean res = ordersService.remove(new QueryWrapper<Orders>().eq("order_id", orderId));
        return Result.success().data("responseBody", res);
    }

}

