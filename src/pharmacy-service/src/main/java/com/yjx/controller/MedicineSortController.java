package com.yjx.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjx.common.lang.Result;
import com.yjx.common.lang.ResultInfo;
import com.yjx.dto.CategoryDto;
import com.yjx.dto.CategorySecondDto;
import com.yjx.dto.FrontCategoryDto;
import com.yjx.entity.*;
import com.yjx.service.MedicineService;
import com.yjx.service.MedicineSortSecondService;
import com.yjx.service.MedicineSortService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



@RestController
public class MedicineSortController {

    @Autowired
    MedicineSortService medicineSortService;

    @Autowired
    MedicineSortSecondService medicineSortSecondService;

    @Autowired
    MedicineService medicineService;

    /**
     * 查询全部的分类并分页，然后列表展示
     *
     * @return Result
     */
    @GetMapping(value = {"/sortList/{currentPage}/{pageSize}",
            "/sortList/{currentPage}/{pageSize}/{idOrCategoryOrUploadUser}"})
    public Result querySortPage(@PathVariable Integer currentPage,
                                @PathVariable Integer pageSize,
                                @PathVariable(required = false) String idOrCategoryOrUploadUser) {
        IPage<MedicineSort> pageData = medicineSortService.selectSortPage(currentPage, pageSize, idOrCategoryOrUploadUser);
        return Result.success().codeAndMessage(ResultInfo.SUCCESS).data("sortData", pageData);
    }

    /**
     * 查询所有分类----用于药品添加和修改时，分类的select标签展示
     *
     * @return Result
     */
    @GetMapping("/medicine/sortList")
    public Result querySortList() {
//        Page<MedicineSort> page = new Page<>();
//        Page<MedicineSort> pageData = medicineSortService.page(page, new QueryWrapper<>());
        List<MedicineSort> list = medicineSortService.list();
        return Result.success().codeAndMessage(ResultInfo.SUCCESS).data("sortData", list);
    }

    /**
     * 选择分类是否显示
     *
     * @param sortId 分类编号
     * @return Result
     */
    @RequiresAuthentication
    @GetMapping("/sort/isShow/{sortId}")
    public Result changeStatus(@PathVariable(name = "sortId") Integer sortId) {

        MedicineSort one = medicineSortService.getById(sortId);
        UpdateWrapper<MedicineSort> updateWrapper = new UpdateWrapper<>();
        if (one.getStatus() == 1) {
            updateWrapper.eq("sort_id", sortId).set("status", 0);
        } else {
            updateWrapper.eq("sort_id", sortId).set("status", 1);
        }
        medicineSortService.update(new MedicineSort(), updateWrapper);
        return Result.success().codeAndMessage(ResultInfo.SUCCESS);
    }

    /**
     * 单个删除分类-----逻辑删除
     *
     * @param sortId 分类编号
     * @return Result
     */
    @RequiresAuthentication
    @GetMapping("/sort/delete/{sortId}")
    public Result deleteSort(@PathVariable(name = "sortId") Integer sortId) {
        try {
            medicineSortService.deleteBySortId(sortId);
            return Result.success().codeAndMessage(ResultInfo.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error().message("操作失败");
        }
    }

    /**
     * 批量删除分类-----逻辑删除
     *
     * @return Result
     */
    @RequiresAuthentication
    @PostMapping("/category/batch/delete")
    public Result batchDeleteMedicine(Integer[] sortIds) {
        try {
            // 将前端传过来的数组转换成集合
            List<Integer> list = Arrays.asList(sortIds);
            // 通过分类的id的集合进行批量删除
            list.forEach(sortId -> medicineSortService.deleteBySortId(sortId));
            return Result.success().codeAndMessage(ResultInfo.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error().message("操作失败");
        }
    }


    /**
     * 添加分类
     *
     * @param medicineSort 分类相关信息
     * @return Result
     */
    @RequiresAuthentication
    @PostMapping("/category/add")
    public Result addCategory(@RequestBody MedicineSort medicineSort) {
        medicineSortService.saveOrUpdate(medicineSort);
        return Result.success().codeAndMessage(ResultInfo.SUCCESS);
    }

    /**
     * 修改分类
     *
     * @param sortInfo 前端传过来的分类的相关信息
     * @return Result
     */
    @RequiresAuthentication
    @PostMapping("/category/update")
    public Result updateCategory(@RequestBody MedicineSort sortInfo) {
        UpdateWrapper<MedicineSort> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("sort_id", sortInfo.getSortId());

        updateWrapper.set("category", sortInfo.getCategory());
        updateWrapper.set("upload_user", sortInfo.getUploadUser());

        boolean update = medicineSortService.update(new MedicineSort(), updateWrapper);
        System.out.println("修改结果 ==>" + update);
        return Result.success().codeAndMessage(ResultInfo.SUCCESS);
    }


    /**
     * 前台获取分类信息，一级分类、二级分类，然后展示在全部分类页面
     *
     * @return frontCategories
     */
    @GetMapping("/front/get/category")
    public Result frontGetCategory() {

        List<MedicineSort> firstCategoryList = medicineSortService.list(
                new QueryWrapper<MedicineSort>().eq("status", 1));
        List<FrontCategoryDto> frontCategories = new ArrayList<>();
        for (MedicineSort firstCategory : firstCategoryList) {
            FrontCategoryDto frontCategoryDto = new FrontCategoryDto();
            // 拿到第一分类的名称，顺便把它的id作为每一项的key
            frontCategoryDto.setFirstCategory(firstCategory.getCategory());
            frontCategoryDto.setKey(firstCategory.getSortId().toString());
            List<MedicineSortSecond> secondCategoryList = medicineSortSecondService.list(
                    new QueryWrapper<MedicineSortSecond>().eq("first_sort_id", firstCategory.getSortId()).eq("status", 1));
            frontCategoryDto.setSecondCategoryList(secondCategoryList);
            frontCategories.add(frontCategoryDto);
        }
        return Result.success().codeAndMessage(ResultInfo.SUCCESS).data("frontCategories", frontCategories);
    }

    /**
     * 根据分类名称获取该类药品
     *
     * @param categoryName 分类名称
     * @return 该类药品
     */
    @PostMapping("/get/category/drugs")
    public Result frontGetCategoryByName(String categoryName, Integer current) {

        // 先根据分类名称去一级分类里查找有没有这个分类
        MedicineSort firstCategory = medicineSortService.getOne(new QueryWrapper<MedicineSort>().eq("category", categoryName).eq("status", 1));
        if (firstCategory != null) {
            // 如果一级分类有这个分类, 就把这个总分类的药全部查出来
            Page<Medicine> page = new Page<>(current, 20);
            Page<Medicine> medicineList = medicineService.page(page, new QueryWrapper<Medicine>().eq("sort", firstCategory.getSortId()).eq("status", 1));
            return Result.success().codeAndMessage(ResultInfo.SUCCESS).data("data", medicineList);
        } else {
            // 一级没有，就是二级的分类了
            MedicineSortSecond secondCategory = medicineSortSecondService.getOne(new QueryWrapper<MedicineSortSecond>().eq("second_sort", categoryName).eq("status", 1));
            Page<Medicine> page = new Page<>(current, 20);
            if (secondCategory == null) {
                return Result.success().codeAndMessage(ResultInfo.SUCCESS).data("data", null);
            }
            Page<Medicine> medicineList = medicineService.page(page, new QueryWrapper<Medicine>().eq("second_sort", secondCategory.getId()).eq("status", 1));
            return Result.success().codeAndMessage(ResultInfo.SUCCESS).data("data", medicineList);
        }
    }


    /**
     * 获取全部的一级分类，status=1，能显示的
     *
     * @return 一级分类的集合
     */
    @GetMapping("/get/all/first/category")
    public Result getAllFirstCategory() {

        List<MedicineSort> sortList = medicineSortService.list(new QueryWrapper<MedicineSort>().eq("status", 1));
        return Result.success().data("data", sortList);
    }


    /**
     * 把一级分类和二级分类封装成options返回前端进行级联选择
     *
     * @return firstCategoryList
     */
    @PostMapping("/admin/category/arr")
    public Result queryMenuList() {

        List<CategoryDto> firstCategoryList = medicineSortService.getAllFirstCategory();

        for (CategoryDto firstCategory : firstCategoryList) {
            List<CategorySecondDto> secondCategoryList = medicineSortSecondService.getAllSecondCategory(Integer.valueOf(firstCategory.getValue()));
            firstCategory.setChildren(secondCategoryList);
        }

        return Result.success().codeAndMessage(ResultInfo.SUCCESS).data("data", firstCategoryList);
    }
}

