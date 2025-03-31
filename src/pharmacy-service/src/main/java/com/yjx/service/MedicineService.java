package com.yjx.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yjx.entity.Medicine;

import java.util.List;

public interface MedicineService extends IService<Medicine> {

    /**
     * 分页查询药品
     * @param currentPage 当前页
     * @param size 单页数量
     * @param nameOrIdOrSort 搜索框中的内容
     * @return IPage
     */
    IPage<Medicine> searchMedicine(Integer currentPage, Integer size,String nameOrIdOrSort);

    /**
     * 分页查询药品
     * @param currentPage 当前页
     * @param size 单页数量
     * @param nameOrIdOrSort 搜索框中的内容
     * @return IPage
     */
    IPage<Medicine> searchMedicine2(Integer currentPage, Integer size,String nameOrIdOrSort);


    /**
     * 获取折扣药品
     *
     * @return Medicine
     */
    List<Medicine> getDiscountDrugs();

    /**
     * 查询药品的总数量
     * @return 数量
     */
    Integer getDrugNums();

}
