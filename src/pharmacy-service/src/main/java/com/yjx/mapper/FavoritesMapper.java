package com.yjx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjx.entity.Favorites;
import org.springframework.stereotype.Component;

@Component
public interface FavoritesMapper extends BaseMapper<Favorites> {

    /**
     * 查找最近一个星期的收藏
     *
     * @param page 分页
     * @param username 用户名
     * @param time     时间
     * @return 收藏集合
     */
    Page<Favorites> getFavoritesWithTime(String username,
                                         Page<Favorites> page,
                                         Integer time);
}
