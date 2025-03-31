package com.yjx.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Medicine对象", description = "药品的基本信息")
public class Medicine implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "药品编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "图片")
    private String picture;

    @ApiModelProperty(value = "药品名称")
    private String name;

    @ApiModelProperty(value = "药品价格")
    private BigDecimal price;

    @ApiModelProperty(value = "药品一级分类")
    private String sort;

    @ApiModelProperty(value = "二级分类")
    private String secondSort;

    @ApiModelProperty(value = "药品的用途描述")
    private String description;

    @ApiModelProperty(value = "规格（例如：8丸*15袋（浓缩丸））")
    private String standard;

    @ApiModelProperty(value = "药品的库存数量")
    private Long stock;

    @ApiModelProperty(value = "产品编号")
    private String productNumber;

    @ApiModelProperty(value = "批准文号")
    private String approvalNumber;

    @ApiModelProperty(value = "生产厂家")
    private String manufacturer;

    @ApiModelProperty(value = "上传药品的人")
    private String uploadUser;

    @ApiModelProperty(value = "是否显示，1显示，0不显示，默认为1")
    private Integer status;

    @ApiModelProperty(value = "是否折扣")
    private Integer isDiscount;

    @ApiModelProperty(value = "版本号")
    @Version
    private Integer version;

    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    private Integer deleted;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "添加时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;


}
