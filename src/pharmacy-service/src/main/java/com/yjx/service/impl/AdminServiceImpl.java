package com.yjx.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjx.dto.AdminInfoDto;
import com.yjx.entity.Admin;
import com.yjx.mapper.AdminMapper;
import com.yjx.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Autowired
    AdminMapper adminMapper;

    @Override
    public IPage<AdminInfoDto> selectAdminListInfo(Integer currentPage, Integer pageSize,String inputContent) {
        Page<AdminInfoDto> adminInfoDtoPage = new Page<>(currentPage,pageSize);
        return adminMapper.selectAdminListInfo(adminInfoDtoPage,inputContent);
    }

    @Override
    public Admin selectSomeAdminInfo(Long id) {
        return adminMapper.selectSomeAdminInfo(id);
    }
}
