package com.yjx.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class ShoppingCartDto implements Serializable {

    @NotBlank(message = "key不能为空，以shopping_cart表的id做为key")
    @ApiModelProperty(value = "表格展示需要一个唯一的key")
    private String key;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "用户的账号")
    private String username;

    @NotBlank(message = "药名不能为空")
    @ApiModelProperty(value = "加入购物车的药品名称")
    private String drugName;

    @NotBlank(message = "数量不能为空")
    @ApiModelProperty(value = "数量")
    private Integer amount;

    @NotBlank(message = "价格不能为空")
    @ApiModelProperty(value = "药品价格")
    private BigDecimal price;

    @NotBlank(message = "图片不能为空")
    @ApiModelProperty(value = "图片")
    private String picture;

    @NotBlank(message = "规格不能为空")
    @ApiModelProperty(value = "规格（例如：8丸*15袋（浓缩丸））")
    private String standard;
}
