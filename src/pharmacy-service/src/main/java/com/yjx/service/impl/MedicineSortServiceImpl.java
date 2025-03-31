package com.yjx.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjx.dto.CategoryDto;
import com.yjx.entity.MedicineSort;
import com.yjx.mapper.MedicineSortMapper;
import com.yjx.service.MedicineSortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineSortServiceImpl extends ServiceImpl<MedicineSortMapper, MedicineSort> implements MedicineSortService {

    @Autowired
    MedicineSortMapper medicineSortMapper;

    @Override
    public IPage<MedicineSort> selectSortPage(Integer currentPage, Integer pageSize, String idOrCategoryOrUploadUser) {
        Page<MedicineSort> page = new Page<>(currentPage, pageSize);
        return medicineSortMapper.selectSortPage(page, idOrCategoryOrUploadUser);
    }

    @Override
    public List<CategoryDto> getAllFirstCategory() {
        return medicineSortMapper.getAllFirstCategory();
    }

    @Override
    public Boolean deleteBySortId(Integer sortId) {
        return medicineSortMapper.deleteBySortId(sortId);
    }
}
