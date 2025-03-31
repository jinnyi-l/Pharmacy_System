package com.yjx.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjx.common.lang.Result;
import com.yjx.entity.Medicine;
import com.yjx.entity.MedicineSortSecond;
import com.yjx.service.MedicineService;
import com.yjx.service.MedicineSortSecondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class MedicineSortSecondController {

    @Autowired
    MedicineSortSecondService medicineSortSecondService;

    @Autowired
    private MedicineService medicineService;

    @PostMapping("/category/second/all")
    public Result getSecondCategory() {

        List<MedicineSortSecond> list = medicineSortSecondService.list(new QueryWrapper<MedicineSortSecond>().orderByAsc("first_sort_id"));
        return Result.success().data("responseBody", list);
    }

    /**
     * 根据一级分类的id获取二级分类，并返回
     *
     * @param firstSortId firstSortId
     * @param current     当前页
     * @return res
     */
    @PostMapping("/category/second")
    public Result getSecondCategoryByFirstSortId(Long firstSortId, Integer current) {

        Page<MedicineSortSecond> sortSecondPage = new Page<>(current, 10);
        Page<MedicineSortSecond> res = medicineSortSecondService.page(sortSecondPage,
                new QueryWrapper<MedicineSortSecond>().eq("first_sort_id", firstSortId).orderByDesc("gmt_create"));
        List<MedicineSortSecond> records = res.getRecords();
        for (MedicineSortSecond record : records) {
            int count = medicineService.count(new QueryWrapper<Medicine>().eq("second_sort", record.getId()));
            record.setAmount(count);
        }
        return Result.success().data("responseBody", res);
    }

    /**
     * 控制二级分类是否显示
     *
     * @param id     自增id，唯一
     * @param status 状态 1： 显示 0： 不显示
     * @return Result
     */
    @PostMapping("/category/second/change/status")
    public Result changeStatus(Long id, Integer status) {

        UpdateWrapper<MedicineSortSecond> updateWrapper = new UpdateWrapper<>();
        // 这个status是组件按钮的状态，1是要显示，所以分类的status要设置为1
        if (status == 1) {
            updateWrapper.eq("id", id).set("status", 1);
        } else {
            updateWrapper.eq("id", id).set("status", 0);
        }
        medicineSortSecondService.update(new MedicineSortSecond(), updateWrapper);
        return Result.success();
    }

    /**
     * 删除二级分类
     *
     * @param id id
     * @return Result
     */
    @PostMapping("/category/second/delete")
    public Result deleteCategory(Long id) {

        medicineSortSecondService.removeById(id);
        return Result.success();
    }

    /**
     * 添加二级分类
     *
     * @param categoryForm 含有二级分类信息的表单：
     *                     let categoryForm = {
     *                     firstSortId: this.firstSortId,
     *                     secondSort: this.form.category,
     *                     };
     * @return Result
     */
    @PostMapping("/category/second/add")
    public Result addCategory(@RequestBody MedicineSortSecond categoryForm) {

        medicineSortSecondService.save(categoryForm);
        return Result.success();
    }

    /**
     * 修改二级分类
     *
     * @param newCategoryForm let newCategoryForm = {
     *                        id: this.form.id,
     *                        secondSort: this.form.category,
     *                        };
     * @return Result
     */
    @PostMapping("/category/second/update")
    public Result updateCategory(@RequestBody MedicineSortSecond newCategoryForm) {

        UpdateWrapper<MedicineSortSecond> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", newCategoryForm.getId())
                .set("second_sort", newCategoryForm.getSecondSort());
        medicineSortSecondService.update(new MedicineSortSecond(), updateWrapper);
        return Result.success();
    }


//    @PostMapping("/category/second/number")
//    public Result getSecondNumberById(Long firstSortId) {
//
//        int number = medicineSortSecondService.count(
//                new QueryWrapper<MedicineSortSecond>().eq("first_sort_id", firstSortId));
//        return Result.success().data("responseBody", number);
//    }


    @PostMapping("/admin/drug/category/nums")
    public Result getDrugCategoryNum() {

        Integer countAll = medicineSortSecondService.getDrugCategoryNum();
        return Result.success().codeAndMessage("200", "操作成功").data("responseBody", countAll);
    }


}

