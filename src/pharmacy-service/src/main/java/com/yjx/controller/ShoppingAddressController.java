package com.yjx.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yjx.common.lang.Result;
import com.yjx.common.lang.ResultInfo;
import com.yjx.dto.ShoppingAddressDto;
import com.yjx.entity.ShoppingAddress;
import com.yjx.service.ShoppingAddressService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@Slf4j
public class ShoppingAddressController {

    @Autowired
    ShoppingAddressService shoppingAddressService;


    /**
     * 订单页面，查出该用户账号的收货信息
     *
     * @param username 用户账号
     * @return 有就返回信息集合，没有就返回error
     */
    @GetMapping("/query/shoppingAddressInfo/{username}")
    public Result getShoppingAddressInfo(@PathVariable String username) {

        List<ShoppingAddress> addressInfoList = shoppingAddressService.list(
                new QueryWrapper<ShoppingAddress>()
                        .eq("username", username).orderByDesc("is_default"));
        if (addressInfoList.isEmpty()) {
            return Result.error().message("该用户还没有添加收货地址！");
        }
        return Result.success().codeAndMessage(ResultInfo.SUCCESS).data("data", addressInfoList);
    }

    /**
     * 收货设置管理页面
     *
     * @param username 用户账号
     * @return 有就返回信息集合，没有就返回error
     */
    @RequiresAuthentication
    @GetMapping("/query/receiptInfo/{username}")
    public Result getReceiptInfo(@PathVariable String username) {

        List<ShoppingAddress> addressInfoList = shoppingAddressService.list(
                new QueryWrapper<ShoppingAddress>()
                        .eq("username", username).orderByDesc("gmt_create"));
        if (addressInfoList.isEmpty()) {
            return Result.error().message("该用户还没有添加收货地址！");
        } else {
            List<ShoppingAddressDto> receiptInfoList = new ArrayList<>();
            for (ShoppingAddress shoppingAddress : addressInfoList) {
                ShoppingAddressDto shoppingAddressDto = new ShoppingAddressDto();
                shoppingAddressDto.setKey(shoppingAddress.getId().toString())
                        .setId(shoppingAddress.getId())
                        .setReceiptName(shoppingAddress.getReceiptName())
                        .setReceiptAddress(shoppingAddress.getReceiptAddress())
                        .setDetailsAddress(shoppingAddress.getDetailsAddress())
                        .setPhone(shoppingAddress.getPhone())
                        .setIsDefault(shoppingAddress.getIsDefault());
                receiptInfoList.add(shoppingAddressDto);
            }
            return Result.success().codeAndMessage(ResultInfo.SUCCESS).data("data", receiptInfoList);
        }
    }

    /**
     * 添加收货地址信息
     *
     * @param username 用户账号
     * @param info     收货地址信息
     * @return 对应的回答
     */
    @PostMapping("/add/shoppingAddressInfo/{username}")
    public Result addShoppingAddressInfo(@PathVariable String username, @RequestBody ShoppingAddress info) {

        int count = shoppingAddressService.count(new QueryWrapper<ShoppingAddress>().eq("username", username));
        int maxCount = 8;
        if (count >= maxCount) {
            return Result.error().message("最多添加8条收货地址信息！您可以修改或删除不常用的地址信息。");
        }
        // 存的时候判断有没有存过一样的数据，防止用户重复存
        else if (!shoppingAddressService.queryHasCommon(info, username)) {
            log.info("有完全相同的收货地址了！");
            return Result.error().message("有完全相同的收货地址了！");
        }
        // 如果用户存的信息要设置为默认
        else if (info.getIsDefault() == 1) {
            // 就查一下有没有存过已经为默认的，有的话就查出来改成非默认状态
            if (shoppingAddressService.hasDefault(username)) {
                UpdateWrapper<ShoppingAddress> updateWrapper = new UpdateWrapper<>();
                updateWrapper.set("is_default", 0).eq("username", username).eq("is_default", 1);
                shoppingAddressService.update(null, updateWrapper);
                // 再把要设置为默认的存进去
                ShoppingAddress shoppingAddress = new ShoppingAddress();
                shoppingAddress.setUsername(username)
                        .setReceiptName(info.getReceiptName())
                        .setDetailsAddress(info.getDetailsAddress())
                        .setReceiptAddress(info.getReceiptAddress())
                        .setPhone(info.getPhone())
                        .setIsDefault(info.getIsDefault());
                boolean result = shoppingAddressService.save(shoppingAddress);
                return Result.success().message("添加成功！").data("result", result);
            } else {
                // 没有查到默认的，直接存进去
                ShoppingAddress shoppingAddress = new ShoppingAddress();
                shoppingAddress.setUsername(username)
                        .setReceiptName(info.getReceiptName())
                        .setDetailsAddress(info.getDetailsAddress())
                        .setReceiptAddress(info.getReceiptAddress())
                        .setPhone(info.getPhone())
                        .setIsDefault(info.getIsDefault());
                boolean result = shoppingAddressService.save(shoppingAddress);
                return Result.success().message("添加成功！").data("result", result);
            }
        } else {
            // 不要设置成默认，也是直接存进去
            ShoppingAddress shoppingAddress = new ShoppingAddress();
            shoppingAddress.setUsername(username)
                    .setReceiptName(info.getReceiptName())
                    .setDetailsAddress(info.getDetailsAddress())
                    .setReceiptAddress(info.getReceiptAddress())
                    .setPhone(info.getPhone())
                    .setIsDefault(info.getIsDefault());
            boolean result = shoppingAddressService.save(shoppingAddress);
            return Result.success().message("添加成功！").data("result", result);
        }
    }


    /**
     * 更改默认的收货地址
     *
     * @param username 用户账号
     * @param id       id
     * @return 操作结果
     */
    @RequiresAuthentication
    @GetMapping("/set/default/{username}/{id}")
    public Result setToDefault(@PathVariable String username, @PathVariable Long id) {

        // 已经有默认的
        if (shoppingAddressService.hasDefault(username)) {
            // 查出默认的这一条
            ShoppingAddress one = shoppingAddressService.getOne(new QueryWrapper<ShoppingAddress>().eq("username", username).eq("is_default", 1));
            // 改掉，改成非默认0
            UpdateWrapper<ShoppingAddress> updateWrapper = new UpdateWrapper<ShoppingAddress>().eq("id", one.getId()).set("is_default", 0);
            shoppingAddressService.update(new ShoppingAddress(), updateWrapper);
            // 把要改成默认的设置成默认
            UpdateWrapper<ShoppingAddress> updateWrapper2 = new UpdateWrapper<ShoppingAddress>().eq("id", id).set("is_default", 1);
            shoppingAddressService.update(new ShoppingAddress(), updateWrapper2);
        }
        // 没有默认的,直接根据id设置为默认的
        else {
            UpdateWrapper<ShoppingAddress> updateWrapper = new UpdateWrapper<ShoppingAddress>().eq("id", id).set("is_default", 1);
            shoppingAddressService.update(new ShoppingAddress(), updateWrapper);
        }
        return Result.success().message("设置成功！");
    }

    /**
     * 修改
     *
     * @param shoppingAddress 地址信息
     * @return Result
     */
    @CrossOrigin
    @PostMapping("/update/receiptInfo")
    public Result updateReceiptInfo(@RequestBody ShoppingAddress shoppingAddress) {

        // 如果有相同的
        if (!shoppingAddressService.queryHasCommon(shoppingAddress, shoppingAddress.getUsername())) {
            return Result.error().message("您还没有更改任何一条必要信息！");
        }
        // 没有就更新
        else {
            // 如果要设置默认
            if (shoppingAddress.getIsDefault() == 1) {
                // 已经有默认的
                if (shoppingAddressService.hasDefault(shoppingAddress.getUsername())) {
                    // 查出默认的这一条
                    ShoppingAddress one = shoppingAddressService.getOne(new QueryWrapper<ShoppingAddress>().eq("username", shoppingAddress.getUsername()).eq("is_default", 1));
                    // 改掉，改成非默认0
                    UpdateWrapper<ShoppingAddress> updateWrapper = new UpdateWrapper<ShoppingAddress>().eq("id", one.getId()).set("is_default", 0);
                    shoppingAddressService.update(new ShoppingAddress(), updateWrapper);
                    // 把要改成默认的设置成默认
                    UpdateWrapper<ShoppingAddress> updateWrapper2 = new UpdateWrapper<ShoppingAddress>().eq("id", shoppingAddress.getId()).set("is_default", 1);
                    shoppingAddressService.update(new ShoppingAddress(), updateWrapper2);
                }
                // 没有默认的,直接根据id设置为默认的
                else {
                    UpdateWrapper<ShoppingAddress> updateWrapper = new UpdateWrapper<ShoppingAddress>().eq("id", shoppingAddress.getId()).set("is_default", 1);
                    shoppingAddressService.update(new ShoppingAddress(), updateWrapper);
                }
                UpdateWrapper<ShoppingAddress> updateWrapper = new UpdateWrapper<ShoppingAddress>().eq("id", shoppingAddress.getId())
                        .set("receipt_name", shoppingAddress.getReceiptName())
                        .set("receipt_address", shoppingAddress.getReceiptAddress())
                        .set("details_address", shoppingAddress.getDetailsAddress())
                        .set("phone", shoppingAddress.getPhone());
                shoppingAddressService.update(new ShoppingAddress(), updateWrapper);
            } else {
                UpdateWrapper<ShoppingAddress> updateWrapper = new UpdateWrapper<ShoppingAddress>().eq("id", shoppingAddress.getId())
                        .set("receipt_name", shoppingAddress.getReceiptName())
                        .set("receipt_address", shoppingAddress.getReceiptAddress())
                        .set("details_address", shoppingAddress.getDetailsAddress())
                        .set("phone", shoppingAddress.getPhone());
                shoppingAddressService.update(new ShoppingAddress(), updateWrapper);
            }
            return Result.success().message("更新成功！请刷新页面查看最新数据！");
        }
    }

    /**
     * 删除
     *
     * @param id id
     * @return Result
     */
    @RequiresAuthentication
    @PostMapping("/receiptInfo/delete")
    public Result deleteReceiptInfoById(Long id) {

        shoppingAddressService.removeById(id);
        return Result.success().message("删除成功！");
    }


}

