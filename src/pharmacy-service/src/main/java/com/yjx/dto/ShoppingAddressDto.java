package com.yjx.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
@Accessors(chain = true)
public class ShoppingAddressDto implements Serializable {

    private Long id;

    private String key;

    private String receiptName;

    private String receiptAddress;

    private String detailsAddress;

    private String phone;

    private Integer isDefault;


}
