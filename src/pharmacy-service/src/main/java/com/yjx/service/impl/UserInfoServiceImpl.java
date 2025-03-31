package com.yjx.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjx.dto.NormalUserInfoDto;
import com.yjx.dto.UserInfoDto;
import com.yjx.entity.UserInfo;
import com.yjx.mapper.UserInfoMapper;
import com.yjx.service.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Autowired
    UserInfoMapper userInfoMapper;

    @Override
    public IPage<NormalUserInfoDto> selectUserListInfo(Integer currentPage, Integer pageSize, String inputContent) {
        Page<NormalUserInfoDto> userInfoPage = new Page<>(currentPage, pageSize);
        if ("".equals(inputContent) || inputContent == null) {
            userInfoPage.setSearchCount(false);
        }
        return userInfoMapper.selectUserListInfo(userInfoPage, inputContent);
    }

    @Override
    public Boolean batchDisableUser(List<Long> userIds) {
        return userInfoMapper.batchDisableUser(userIds);
    }

    @Override
    public IPage<NormalUserInfoDto> selectVipListInfo(Integer currentPage, Integer pageSize, String inputContent) {
        Page<NormalUserInfoDto> vipInfoPage = new Page<>(currentPage, pageSize);
        if ("".equals(inputContent) || inputContent == null) {
            vipInfoPage.setSearchCount(false);
        }
        return userInfoMapper.selectVipListInfo(vipInfoPage,inputContent);
    }

    @Override
    public UserInfoDto getUserInfoByUsername(String username) {
        return  userInfoMapper.getUserInfoByUsername(username);
    }

}
