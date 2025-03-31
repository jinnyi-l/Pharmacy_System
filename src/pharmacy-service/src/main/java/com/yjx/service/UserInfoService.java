package com.yjx.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yjx.dto.NormalUserInfoDto;
import com.yjx.dto.UserInfoDto;
import com.yjx.entity.UserInfo;

import java.util.List;

@SuppressWarnings("all")
public interface UserInfoService extends IService<UserInfo> {

    /**
     * 查询用户信息
     *
     * @param currentPage 当前页
     * @param pageSize    size
     * @param inputContent 搜索的内容
     * @return data
     */
    IPage<NormalUserInfoDto> selectUserListInfo(Integer currentPage, Integer pageSize,String inputContent);

    /**
     * 批量禁用普通用户
     *
     * @param userIds 用户id的集合
     * @return boolean值
     */
    Boolean batchDisableUser(List<Long> userIds);

    /**
     * 查询会员信息
     *
     * @param currentPage 当前页
     * @param pageSize    size
     * @return IPage
     */
    IPage<NormalUserInfoDto> selectVipListInfo(Integer currentPage, Integer pageSize,String inputContent);

    UserInfoDto getUserInfoByUsername(String username);

}
