package com.yjx.dto;


import lombok.Data;

@Data
public class DrugCategoryDto {
    private String categoryName;

    private int productCount;

    public DrugCategoryDto(String categoryName, int productCount) {
        this.categoryName = categoryName;
        this.productCount = productCount;


    }

    public DrugCategoryDto( ) {

    }


    public void addCount() {
        this.productCount++;
    }
}
