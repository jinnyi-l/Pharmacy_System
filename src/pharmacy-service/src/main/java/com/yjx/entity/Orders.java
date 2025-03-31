package com.yjx.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Orders对象", description = "")
@Accessors(chain = true)
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户账号")
    private String username;

    @ApiModelProperty(value = "订单编号，以Y开头，根据年月日时分秒生成，再加上四位随机生成的")
    private String orderId;

    @ApiModelProperty(value = "清单药品数量")
    private Integer itemAmount;

    @ApiModelProperty(value = "应付金额")
    private BigDecimal payment;

    @ApiModelProperty(value = "订单备注")
    private String message;

    @ApiModelProperty(value = "收货人")
    private String receiptName;

    @ApiModelProperty(value = "收货地区")
    private String receiptAddress;

    @ApiModelProperty(value = "详细地址")
    private String detailsAddress;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "投诉理由")
    private String reason;

    @ApiModelProperty(value = "投诉状态：默认为0（不投诉）")
    private Integer complaint;

    @ApiModelProperty(value = "退货理由")
    private String backReason;

    @ApiModelProperty(value = "订单状态（1：等待发货；2：等待收货；3：已收到货，可评价；0：取消交易；-1：被投诉）")
    private Integer status;

    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    private Integer deleted;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "交易订单生成时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;


}
