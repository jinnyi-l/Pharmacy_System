package com.yjx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjx.dto.CategorySecondDto;
import com.yjx.entity.MedicineSortSecond;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MedicineSortSecondMapper extends BaseMapper<MedicineSortSecond> {


    /**
     * 根据一级分类的编号获取二级分类
     *
     * @param firstCategoryId 一级分类的id
     * @return 集合
     */
    List<CategorySecondDto> getAllSecondCategory(Integer firstCategoryId);


}
