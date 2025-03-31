package com.yjx.dto;

import com.yjx.entity.MedicineSortSecond;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class FrontCategoryDto implements Serializable {

    /**
     * 展示信息需要一个key来区分，可以是唯一的
     */
    private String key;

    /**
     * 一级分类
     */
    private String firstCategory;

    /**
     * 二级分类有很多个
     */
    private List<MedicineSortSecond> secondCategoryList;

}
