package com.yjx.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjx.common.lang.Result;
import com.yjx.common.lang.ResultInfo;
import com.yjx.entity.Favorites;
import com.yjx.entity.ShoppingCart;
import com.yjx.service.FavoritesService;
import com.yjx.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RestController
@Slf4j
@SuppressWarnings("all")
public class FavoritesController {

    @Autowired
    FavoritesService favoritesService;

    @Autowired
    ShoppingCartService shoppingCartService;

    /**
     * 进入药品的详情页时，查找药品的收藏状态，是否被该用户收藏
     *
     * @param username 用户账号
     * @param drugName 药品名
     * @return status
     */
    @GetMapping("/get/collect/status/{username}/{drugName}")
    public Result collectDrug(@PathVariable String username, @PathVariable String drugName) {

        try {
            Favorites one = favoritesService.getOne(new QueryWrapper<Favorites>()
                    .eq("username", username)
                    .eq("drug_name", drugName));
            return Result.success().data("status", one.getStatus());
        } catch (Exception e) {
            // 捕获无登录状态的无参数的异常
            log.info("获取状态");
            return Result.error().message("无参数");
        }
    }

    /**
     * 收藏或取消药品，收藏过了就取消收藏，没有就添加
     *
     * @param favorites 要收藏的药品信息
     * @return Result
     */
    @PostMapping("/drug/collect")
    public Result collectDrug(@RequestBody Favorites favorites) {

        Favorites one = favoritesService.getOne(new QueryWrapper<Favorites>()
                .eq("username", favorites.getUsername())
                .eq("drug_name", favorites.getDrugName()));
        if (one != null) {
            if (one.getStatus() == 0) {
                UpdateWrapper<Favorites> statusUpdate = new UpdateWrapper<Favorites>();
                statusUpdate.set("status", 1)
                        .eq("username", favorites.getUsername())
                        .eq("drug_name", favorites.getDrugName())
                        .eq("deleted", 0);
                favoritesService.update(new Favorites(), statusUpdate);
                return Result.success().message("收藏成功！");
            } else {
                UpdateWrapper<Favorites> statusUpdate = new UpdateWrapper<Favorites>();
                statusUpdate.set("status", 0)
                        .eq("username", favorites.getUsername())
                        .eq("drug_name", favorites.getDrugName())
                        .eq("deleted", 0);
                favoritesService.update(new Favorites(), statusUpdate);
                return Result.success().message("已取消收藏！");
            }
        } else {
            favoritesService.save(favorites);
            return Result.success();
        }
    }

    /**
     * 获取该用户收藏的信息并返回
     *
     * @param username 用户账号
     * @return list
     */
    @RequiresAuthentication
    @GetMapping(value = {"/get/collect/info/{username}/{current}/{pageSize}",
            "/get/collect/info/{username}/{current}/{pageSize}/{time}"})
    public Result getCollectDrug(@PathVariable String username,
                                 @PathVariable Integer current,
                                 @PathVariable Integer pageSize,
                                 @PathVariable(required = false) Integer time) {
        if (time == null || "".equals(time)) {
            Page<Favorites> favoritesPage = new Page<>(current, pageSize);
            Page<Favorites> pageInfo = favoritesService.page(favoritesPage,
                    new QueryWrapper<Favorites>().eq("username", username).eq("status", 1).orderByDesc("gmt_create"));
            return Result.success().codeAndMessage(ResultInfo.SUCCESS).data("data", pageInfo);
        } else {
            Page<Favorites> pageWithTime = favoritesService.getFavoritesPageWithTime(username, current, pageSize, time);
            return Result.success().codeAndMessage(ResultInfo.SUCCESS).data("data", pageWithTime);
        }

    }


    /**
     * 查找该账号收藏的所有药的名字，返回名字集合
     *
     * @param username 用户账号
     * @return nameList
     */
    @GetMapping("/get/collect/name/{username}")
    public Result getCollectDrugNameList(@PathVariable String username) {

        List<Favorites> list = favoritesService.list(
                new QueryWrapper<Favorites>().eq("username", username).eq("status", 1));
        ArrayList<String> nameList = new ArrayList<>();
        for (Favorites item : list) {
            nameList.add(item.getDrugName());
        }
        return Result.success().codeAndMessage(ResultInfo.SUCCESS).data("data", nameList);
    }

    /**
     * 删除单个
     *
     * @param id 收藏的行id
     * @return Result
     */
    @RequiresAuthentication
    @GetMapping("/delete/favorite/{id}")
    public Result deleteOneFavorite(@PathVariable Long id) {

        favoritesService.removeById(id);
        return Result.success();
    }

    /**
     * 批量删除
     *
     * @param username 用户账号
     * @param ids      要删除的id数组
     * @return Result
     */
    @RequiresAuthentication
    @PostMapping("/favorite/batch/delete/{username}")
    public Result batchDelete(@PathVariable String username, @RequestBody Long[] ids) {

        System.out.println("ids = " + ids);
        favoritesService.removeByIds(Arrays.asList(ids));
        return Result.success();
    }

    /**
     * 清空收藏夹
     *
     * @param username 用户账号
     * @return Result
     */
    @RequiresAuthentication
    @PostMapping("/favorite/all/delete")
    public Result deleteAll(String username) {

        favoritesService.remove(new QueryWrapper<Favorites>().eq("username", username));
        return Result.success();
    }

    /**
     * 把收藏夹的全部添加至购物车
     *
     * @return Result
     */
    @RequiresAuthentication
    @PostMapping("/add/to/shoppingCart")
    public Result addToShoppingCart(String username) {

        List<Favorites> favoriteListlist = favoritesService.list(new QueryWrapper<Favorites>().eq("username", username));
        for (Favorites favorite : favoriteListlist) {
            // 如果该用户的购物车已经有这种药的，因为设置的药名唯一，所以有的话就只需要加数量，还得判断有没有到数量上限200
            if (shoppingCartService.getCartInfo(favorite.getDrugName(), favorite.getUsername()) == 0) {
                // 把查到的药名存到对应的用户下，数量为：1
                shoppingCartService.save(new ShoppingCart()
                        .setDrugName(favorite.getDrugName()).setAmount(1).setUsername(favorite.getUsername()));
            } else {
                Integer drugAmount = shoppingCartService.getDrugAmount(favorite.getDrugName(), favorite.getUsername());
                int maxAmount = 200;
                if (drugAmount + 1 > maxAmount) {
                    // 数量到达上限，跳过当前循环
                    continue;
                } else {
                    UpdateWrapper<ShoppingCart> updateWrapper = new UpdateWrapper<>();
                    updateWrapper.set("amount", drugAmount + 1)
                            .eq("drug_name", favorite.getDrugName()).eq("username", favorite.getUsername());
                    shoppingCartService.update(new ShoppingCart(), updateWrapper);
                }
            }

        }
        // 无论上面的条件怎么样，最终用户的购物车里肯定是会有收藏夹里的全部内容，肯定是要移除收藏夹里该用户的全部收藏的
        favoritesService.remove(new QueryWrapper<Favorites>().eq("username", username));
        return Result.success();
    }
}
