package com.yjx.dto;


import lombok.Data;

@Data
public class DrugSaleDto {

    private String productName;

    private int salesQuantity;

    private int totalSales;

    public DrugSaleDto() {
    }

    public DrugSaleDto(String productName, int salesQuantity, int totalSales) {
        this.productName = productName;
        this.salesQuantity = salesQuantity;
        this.totalSales = totalSales;
    }

    public void addTotalSales (int num) {
        this.totalSales += num;
    }

    public void addSalesQuantity (int num) {
        this.salesQuantity += num;
    }

}
