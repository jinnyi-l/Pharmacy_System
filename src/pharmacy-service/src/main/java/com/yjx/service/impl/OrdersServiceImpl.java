package com.yjx.service.impl;

import com.alipay.api.domain.Goods;
import com.alipay.api.domain.OrderItem;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yjx.common.lang.Result;
import com.yjx.common.lang.ResultInfo;
import com.yjx.dto.OrdersDto;
import com.yjx.entity.Medicine;
import com.yjx.entity.Orders;
import com.yjx.entity.OrdersItems;
import com.yjx.mapper.OrdersMapper;
import com.yjx.service.MedicineService;
import com.yjx.service.OrdersItemsService;
import com.yjx.service.OrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

    @Autowired
    private OrdersMapper ordersMapper;


    @Autowired
    private OrdersItemsService ordersItemsService;

    @Autowired
    private MedicineService medicineService;

    @Override
    public Result orderComplaint(String orderId, String reason) {

        try {
            UpdateWrapper<Orders> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("complaint", 1).set("reason", reason).eq("order_id", orderId);
            ordersMapper.update(new Orders(),updateWrapper);
            return Result.success().codeAndMessage("200", "投诉成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error().message("操作失败");
        }
    }

    @Override
    public Result confirmReceipt(String orderId) {

        try {
            UpdateWrapper<Orders> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("status", 3).eq("order_id", orderId);
            ordersMapper.update(new Orders(),updateWrapper);
            return Result.success().codeAndMessage("200", "确认收货成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error().message("操作失败");
        }
    }

    @Override
    public Result getNewComplaintNumsOrderOnMonth() {

        Integer nums = ordersMapper.getNewComplaintNumsOrderOnMonth();
        return Result.success().codeAndMessage(ResultInfo.SUCCESS).data("responseBody", nums);
    }

    @Override
    public void subGoodCount(String orderId) {

        // 获取订单详情
        Orders order = this.getById(orderId);

        // 获取订单所有商品
        List<OrdersItems> list = this.ordersItemsService.lambdaQuery()
                .eq(OrdersItems::getOrderId, orderId)
                .list();

        // 更新商品库存
        for (OrdersItems items : list) {

            Medicine medicine = this.medicineService.getOne(new LambdaQueryWrapper<Medicine>()
                    .eq(Medicine::getName, items.getDrugName())
            );

            UpdateWrapper<Medicine> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", medicine.getId());
            updateWrapper.set("name", medicine.getName())
                    .set("picture", medicine.getPicture())
                    .set("upload_user", medicine.getUploadUser())
                    .set("sort", medicine.getSort())
                    .set("second_sort", medicine.getSecondSort())
                    .set("price", medicine.getPrice())
                    .set("stock", medicine.getStock() - items.getAmount())
                    .set("description", medicine.getDescription());

            boolean update = medicineService.update(new Medicine(), updateWrapper);

        }


    }

    @Override
    public boolean checkGoodStock(OrdersDto ordersInfo) {
        // 检查库存
        List<OrdersItems> drugs = ordersInfo.getDrugs();

        for (OrdersItems drug : drugs) {

            // 获取库存量
            Integer amount = drug.getAmount();

            // 获取药品信息
            Medicine one = this.medicineService.getOne(new LambdaQueryWrapper<Medicine>()
                    .eq(Medicine::getName, drug.getDrugName()));

            if (one == null) {
                return false;
            }

            // 库存不足
            if (one.getStock() < amount) {
                return false;
            }
        }

        return true;
    }
}
