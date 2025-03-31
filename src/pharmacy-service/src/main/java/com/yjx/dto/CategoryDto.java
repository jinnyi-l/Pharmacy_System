package com.yjx.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
@SuppressWarnings("all")
public class CategoryDto implements Serializable {

    // 一级分类id
    private String value;

    // 一级分类名称
    private String label;

    // 子分类
    private List<CategorySecondDto> children;
}
