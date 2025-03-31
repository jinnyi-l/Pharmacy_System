package com.yjx.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yjx.entity.OrdersItems;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Data
@Accessors(chain = true)
public class OrdersDto implements Serializable {

    private String orderId;

    @ApiModelProperty(value = "用户账号")
    private String username;

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

    @ApiModelProperty(value = "投诉状态")
    private Integer complaint;

    @ApiModelProperty(value = "投诉理由")
    private String reason;

    @ApiModelProperty(value = "退货理由")
    private String backReason;

    private List<OrdersItems> drugs;

    @ApiModelProperty(value = "订单状态（1：等待发货；2：等待收货；3：已收到货，可评价；0：取消交易）")
    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtCreate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtModified;

}
