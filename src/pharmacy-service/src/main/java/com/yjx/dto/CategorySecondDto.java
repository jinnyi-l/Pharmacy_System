package com.yjx.dto;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
@SuppressWarnings("all")
public class CategorySecondDto {

    // 二级分类id
    private String value;

    // 二级分类名称
    private String label;
}
