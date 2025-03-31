package com.yjx.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "HealthTips对象", description = "")
@Accessors(chain = true)
public class HealthTips implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "所属分类")
    private String category;

    @ApiModelProperty(value = "来源")
    private String origin;

    @ApiModelProperty(value = "原文链接")
    private String reprintLink;

    @ApiModelProperty(value = "管理员账号")
    private String username;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "正文内容")
    private String contents;

    @ApiModelProperty(value = "阅览次数")
    private Long readNumber;

    @ApiModelProperty(value = "是否可见。1可见，0不可见")
    private Integer status;

    @TableLogic
    private Integer deleted;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创作时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;


}
