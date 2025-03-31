package com.yjx.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="MenuFirst对象", description="一级菜单表")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuFirst implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "一级菜单id")
    private String menuId;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "子菜单")
    private List<MenuSecond> children;

    @ApiModelProperty(value = "需要的权限等级")
    private Integer needLevel;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    private Integer deleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;


}
