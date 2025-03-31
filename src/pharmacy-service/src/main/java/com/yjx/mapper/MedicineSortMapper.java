package com.yjx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjx.dto.CategoryDto;
import com.yjx.entity.MedicineSort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MedicineSortMapper extends BaseMapper<MedicineSort> {

    /**
     * 分页查询分类信息
     *
     * @param page                     page分页插件
     * @param idOrCategoryOrUploadUser 搜索的内容
     * @return IPage
     */
    IPage<MedicineSort> selectSortPage(Page<?> page, String idOrCategoryOrUploadUser);

    /**
     * 查询全部的一级分类
     *
     * @return 一级分类集合
     */
    List<CategoryDto> getAllFirstCategory();

    /**
     * delete删除分类
     *
     * @param sortId 分类id
     * @return Boolean
     */
    Boolean deleteBySortId(Integer sortId);
}
