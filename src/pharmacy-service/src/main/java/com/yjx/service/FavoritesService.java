package com.yjx.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yjx.entity.Favorites;

public interface FavoritesService extends IService<Favorites> {

    /**
     * 查找最近一个星期的收藏
     *
     * @param username 用户名
     * @param current 当前页
     * @param pageSize 每一页的数量
     * @param time 时间
     * @return 收藏集合
     */
    Page<Favorites> getFavoritesPageWithTime(String username,
                              Integer current,
                              Integer pageSize,
                              Integer time);

}
