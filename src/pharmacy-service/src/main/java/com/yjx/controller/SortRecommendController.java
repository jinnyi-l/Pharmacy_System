package com.yjx.controller;


import com.yjx.common.lang.Result;
import com.yjx.common.lang.ResultInfo;
import com.yjx.entity.SortRecommend;
import com.yjx.service.SortRecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class SortRecommendController {

    @Autowired
    SortRecommendService sortRecommendService;

    /**
     * 分类推荐
     * @return 推荐的分类的信息
     */
    @GetMapping("/category/recommend")
    public Result getAllSortList() {
        List<SortRecommend> list = sortRecommendService.list();
        return Result.success().codeAndMessage(ResultInfo.SUCCESS).data("data", list);
    }

}

