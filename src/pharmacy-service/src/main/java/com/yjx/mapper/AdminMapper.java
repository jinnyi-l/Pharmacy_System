package com.yjx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjx.dto.AdminInfoDto;
import com.yjx.entity.Admin;
import org.springframework.stereotype.Component;


@Component
public interface AdminMapper extends BaseMapper<Admin> {

    /**
     * 查询管理员信息
     *
     * @param page 分页插件
     * @param inputContent 搜索的内容
     * @return IPage
     */
    IPage<AdminInfoDto> selectAdminListInfo(Page<?> page,String inputContent);

    /**
     * 根据id查询要修改的这个账号的部分信息
     * @param id id
     * @return Admin
     */
    Admin selectSomeAdminInfo(Long id);
}
