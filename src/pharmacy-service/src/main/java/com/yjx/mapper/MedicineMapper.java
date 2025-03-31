package com.yjx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjx.entity.Medicine;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MedicineMapper extends BaseMapper<Medicine> {

    /**
     * 查询药品
     *
     * @param page           page分页插件
     * @param nameOrIdOrSort 查询参数
     * @return list
     */
    IPage<Medicine> searchMedicine(Page<?> page, String nameOrIdOrSort);

    /**
     * 查询药品
     *
     * @param page           page分页插件
     * @param nameOrIdOrSort 查询参数
     * @return list
     */
    IPage<Medicine> searchMedicine2(Page<?> page, String nameOrIdOrSort);


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
