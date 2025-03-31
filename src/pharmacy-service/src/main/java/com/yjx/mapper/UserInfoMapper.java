package com.yjx.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjx.dto.NormalUserInfoDto;
import com.yjx.dto.UserInfoDto;
import com.yjx.entity.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    /**
     * 查询用户信息
     *
     * @param page 分页插件
     * @param inputContent 搜索的内容
     * @return IPage
     */
    IPage<NormalUserInfoDto> selectUserListInfo(Page<?> page,String inputContent);

    /**
     * 批量禁用用户
     *
     * @param userIds 用户id的集合
     * @return boolean值
     */
    Boolean batchDisableUser(List<Long> userIds);

    /**
     * 查询会员信息
     *
     * @param page 分页插件
     * @param inputContent 搜索的内容
     * @return IPage
     */
    IPage<NormalUserInfoDto> selectVipListInfo(Page<?> page,String inputContent);

    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return 用户信息
     */
    UserInfoDto getUserInfoByUsername(String username);

}
