package com.yjx.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yjx.dto.CategoryDto;
import com.yjx.entity.MedicineSort;

import java.util.List;

public interface MedicineSortService extends IService<MedicineSort> {

    /**
     * 分页查询分类信息
     * @param currentPage 当前页
     * @param pageSize 每页的数量
     * @param idOrCategoryOrUploadUser 搜索的内容
     * @return IPage
     */
    IPage<MedicineSort> selectSortPage(Integer currentPage,Integer pageSize, String idOrCategoryOrUploadUser);

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
