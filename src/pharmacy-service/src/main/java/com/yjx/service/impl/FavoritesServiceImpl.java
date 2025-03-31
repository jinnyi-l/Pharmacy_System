package com.yjx.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjx.entity.Favorites;
import com.yjx.mapper.FavoritesMapper;
import com.yjx.service.FavoritesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoritesServiceImpl extends ServiceImpl<FavoritesMapper, Favorites> implements FavoritesService {

    @Autowired
    FavoritesMapper favoritesMapper;

    @Override
    public Page<Favorites> getFavoritesPageWithTime(String username, Integer current, Integer pageSize, Integer time) {

        Page<Favorites> favoritesPage = new Page<>(current, pageSize);
        return favoritesMapper.getFavoritesWithTime(username, favoritesPage, time);
    }
}
