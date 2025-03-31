package com.yjx.dto;

import com.yjx.entity.MenuSecond;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class MenuListDto {

    @ApiModelProperty(value = "一级菜单id")
    private String menuId;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "子菜单")
    private List<MenuSecond> children;


}
