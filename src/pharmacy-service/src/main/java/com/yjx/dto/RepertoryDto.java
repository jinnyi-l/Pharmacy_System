package com.yjx.dto;


import lombok.Data;

@Data
public class RepertoryDto {

    private String itemName;

    private long quantity;

    public RepertoryDto() {
    }

    public RepertoryDto(String itemName, long quantity) {
        this.itemName = itemName;
        this.quantity = quantity;
    }
}
