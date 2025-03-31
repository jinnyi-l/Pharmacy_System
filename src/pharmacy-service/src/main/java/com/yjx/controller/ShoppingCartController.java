package com.yjx.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yjx.dto.ShoppingCartDto;
import com.yjx.common.lang.Result;
import com.yjx.common.lang.ResultInfo;
import com.yjx.entity.Medicine;
import com.yjx.entity.ShoppingCart;
import com.yjx.service.MedicineService;
import com.yjx.service.ShoppingCartService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RestController
public class ShoppingCartController {

    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    MedicineService medicineService;

    /**
     * 添加药品到购物车，每种药最多200盒
     *
     * @param drugName 药名
     * @param username 用户账号
     * @param amount   数量
     * @return 操作结果
     */
    @PostMapping("/shopping/add/{drugName}/{username}/{amount}")
    public Result addToShoppingCart(@PathVariable String drugName,
                                    @PathVariable String username,
                                    @PathVariable Integer amount) {
        // 看看购物车里有没有这种药
        Integer haveOrN0 = shoppingCartService.getCartInfo(drugName, username);
        // 有，就在已有的基础上添加数量
        if (haveOrN0 == 1) {
            // 查出这一条信息
            ShoppingCart cartInfo = shoppingCartService.getOne(new QueryWrapper<ShoppingCart>()
                    .eq("drug_name", drugName).eq("username", username));
            int maxAmount = 200;
            // 如果已经添加的和打算添加的总和超过200
            if (cartInfo.getAmount() + amount > maxAmount || cartInfo.getAmount() == maxAmount) {
                // 返回 提示："最多只能添加200盒！" 和 还能添加的数量 maxAmount - cartInfo.getAmount()
                return Result.error().message("最多只能添加200盒！").data("data", maxAmount - cartInfo.getAmount());
            }
            // 没有超过就更新数量
            else {
                UpdateWrapper<ShoppingCart> updateWrapper = new UpdateWrapper<>();
                updateWrapper.set("amount", cartInfo.getAmount() + amount).eq("drug_name", drugName);
                shoppingCartService.update(new ShoppingCart(), updateWrapper);
                return Result.success().message("添加成功！");
            }
        }
        // 没有，创建保存
        else {
            shoppingCartService.save(new ShoppingCart()
                    .setUsername(username)
                    .setDrugName(drugName)
                    .setAmount(amount));
            return Result.success().message("添加成功！");
        }
    }

    /**
     * 根据用户账号获取对应的购物车信息
     *
     * @param username 用户账号
     * @return 对应的购物车信息
     */
    @RequiresAuthentication
    @GetMapping("/shopping/info/{username}")
    public Result getShoppingCartInfo(@PathVariable String username) {
        // 根据用户账号获取属于他的购物车信息
        List<ShoppingCart> cartInfoList = shoppingCartService.list(new QueryWrapper<ShoppingCart>().eq("username", username));
        List<ShoppingCartDto> shoppingCartDtoList = new ArrayList<>();
        // 遍历集合，获取每一条信息，封装成shoppingCartDto返回给前端
        for (ShoppingCart cartInfo : cartInfoList) {
            ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
            Medicine medicine = medicineService.getOne(new QueryWrapper<Medicine>().eq("name", cartInfo.getDrugName()));
            shoppingCartDto.setKey(cartInfo.getId().toString())
                    .setPicture(medicine.getPicture())
                    .setDrugName(cartInfo.getDrugName())
                    .setStandard(medicine.getStandard())
                    .setPrice(medicine.getPrice())
                    .setAmount(cartInfo.getAmount());
            shoppingCartDtoList.add(shoppingCartDto);
        }
        return Result.success().codeAndMessage(ResultInfo.SUCCESS).data("data", shoppingCartDtoList);
    }

    /**
     * 在购物车页面实时改变数量
     *
     * @param key    前端展示每一条记录的key，唯一，我让key对应的是数据库的id，主键唯一
     * @param amount 数量
     * @return 操作成功
     */
    @PostMapping("/shopping/update/amount/{key}/{amount}")
    public Result updateAmount(@PathVariable Integer key, @PathVariable Integer amount) {

        // 允许添加到购物车的最大数量
        int maxAmount = 200;
        // 虽然前台输入框已经设置了校验规则，不能超过200，
        // 但是却是监听到输入框数字变化就会发起请求，如果鼠标不离开输入框，还是会造成可以发送超过200的请求，
        // 所以后台也得进行一次判断，加一层保险
        if (amount > maxAmount) {
            return null;
        } else {
            shoppingCartService.update(new ShoppingCart(),
                    new UpdateWrapper<ShoppingCart>().eq("id", key).set("amount", amount));
            return Result.success().codeAndMessage(ResultInfo.SUCCESS);
        }
    }


    /**
     * 拿到要结算的id数组，再查出对应的数量和单价，需要用BigDecimal计算，最后返回算好的金额
     *
     * @param shoppingCartIds 要结算的id数组
     * @return 算好的金额
     */
    @PostMapping("/shopping/account")
    public Result settleAccounts(String[] shoppingCartIds) {

        // 用来接收每一次两两计算的结果，得到最后的金额
        BigDecimal result = new BigDecimal(0);
        // 遍历要结算的id数组
        for (String id : shoppingCartIds) {
            // 根据id查询出购物车记录
            ShoppingCart oneInfo = shoppingCartService.getOne(new QueryWrapper<ShoppingCart>().eq("id", id));
            // 获取数量
            Integer amount = oneInfo.getAmount();
            // 根据药名获取药品信息，从里面拿到单价
            Medicine drug = medicineService.getOne(new QueryWrapper<Medicine>().eq("name", oneInfo.getDrugName()));
            BigDecimal price = drug.getPrice();
            // amount是Integer类型，price是BigDecimal类型，不能计算，要借助BigDecimal类提供的方法
            BigDecimal bigDecimalAmount = new BigDecimal(amount);
            BigDecimal res = price.multiply(bigDecimalAmount);
            result = result.add(res);
        }
        return Result.success().codeAndMessage(ResultInfo.SUCCESS).data("totalMoney", result);
    }


    /**
     * 删除单条购物车信息
     *
     * @param id id
     * @return Result
     */
    @GetMapping("/shopping/delete/one/{id}")
    public Result deleteOneById(@PathVariable Long id) {

        shoppingCartService.removeById(id);
        return Result.success();
    }

    @PostMapping("/shopping/batchDelete")
    public Result batchDelete(Long[] ids) {

        shoppingCartService.removeByIds(Arrays.asList(ids));
        return Result.success();
    }
}
