package com.yjx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yjx.dto.CategorySecondDto;
import com.yjx.entity.MedicineSortSecond;

import java.util.List;

public interface MedicineSortSecondService extends IService<MedicineSortSecond> {


    Integer getDrugCategoryNum();

    /**
     * 根据一级分类的编号获取二级分类
     *
     * @param firstCategoryId 一级分类的id
     * @return 集合
     */
    List<CategorySecondDto> getAllSecondCategory(Integer firstCategoryId);

}
