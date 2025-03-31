package com.yjx.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yjx.dto.AdminInfoDto;
import com.yjx.entity.Admin;

public interface AdminService extends IService<Admin> {

    /**
     * 查询管理员列表
     *
     * @param currentPage currentPage
     * @param pageSize pageSize
     * @param inputContent inputContent
     * @return IPage
     */
    IPage<AdminInfoDto> selectAdminListInfo(Integer currentPage,Integer pageSize,String inputContent);


    /**
     * 根据id查询要修改的这个账号的部分信息
     * @param id id
     * @return Admin
     */
    Admin selectSomeAdminInfo(Long id);

}
