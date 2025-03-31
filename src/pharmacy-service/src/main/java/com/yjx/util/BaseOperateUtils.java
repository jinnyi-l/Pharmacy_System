package com.yjx.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yjx.entity.Medicine;
import com.yjx.entity.MedicineSort;
import com.yjx.service.MedicineService;
import com.yjx.service.MedicineSortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BaseOperateUtils {

    @Autowired
    MedicineService medicineService;

    @Autowired
    MedicineSortService medicineSortService;

    /**
     * 根据分类编号查询出此分类的总数量，更新
     * @param sort 分类
     */
//    public void updateSortHaveNum(String sort) {
//
//        QueryWrapper<Medicine> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("sort", sort);
//
//        List<Medicine> list = medicineService.list(queryWrapper);
//
//        UpdateWrapper<MedicineSort> updateWrapper = new UpdateWrapper<>();
//        updateWrapper.eq("sort_id", sort);
//        updateWrapper.set("sort_have_num", list.size());
//        medicineSortService.update(null, updateWrapper);
//
//    }


}
