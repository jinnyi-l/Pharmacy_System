package com.yjx.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjx.common.lang.Result;
import com.yjx.common.lang.ResultInfo;
import com.yjx.entity.HealthTips;
import com.yjx.entity.MedicineSort;
import com.yjx.service.HealthTipsService;
import com.yjx.service.MedicineSortService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class HealthTipsController {

    @Autowired
    HealthTipsService healthTipsService;

    @Autowired
    MedicineSortService medicineSortService;

    /**
     * 添加健康小贴士文章
     *
     * @param article 文章
     * @return Result
     */
    @RequiresAuthentication
    @PostMapping("/health/tips/add")
    public Result addHealthTips(@RequestBody HealthTips article) {

        try {
            HealthTips healthTips = new HealthTips();
            healthTips.setTitle(article.getTitle())
                    .setUsername(article.getUsername())
                    .setAuthor(article.getAuthor())
                    .setOrigin(article.getOrigin())
                    .setReprintLink(article.getReprintLink())
                    .setCategory(article.getCategory())
                    .setContents(article.getContents());
            healthTipsService.save(healthTips);
            return Result.success();
        } catch (Exception e) {
            if (e.getCause().toString().contains("SQLIntegrityConstraintViolationException")) {
                System.out.println("已经存在一样的标题和作者！");
                return Result.error().message("您已经上传过该标题的文章！");
            } else {
                e.printStackTrace();
                return Result.error();
            }
        }
    }

    /**
     * 修改文章
     * 应用场景：后台修改文章
     *
     * @param article HealthTips文章实体类
     * @return Result
     */
    @RequiresAuthentication
    @PostMapping("/health/tips/update")
    public Result updateHealthTips(@RequestBody HealthTips article) {

        // 根据要修改的文章id查出文章
        HealthTips oldArticle = healthTipsService.getById(article.getId());
        if (oldArticle.getTitle().equals(article.getTitle())
                && oldArticle.getContents().equals(article.getContents())
                && oldArticle.getOrigin().equals(article.getOrigin())
                && oldArticle.getCategory().equals(article.getCategory())) {
            return Result.error().message("没有进行修改");
        }
        UpdateWrapper<HealthTips> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", article.getId())
                .set("title", article.getTitle())
                .set("origin", article.getOrigin())
                .set("category", article.getCategory())
                .set("reprint_link", article.getReprintLink())
                .set("contents", article.getContents());
        healthTipsService.update(new HealthTips(), updateWrapper);
        return Result.success();
    }

    /**
     * 根据id删除文章
     *
     * @param id 文章id
     * @return Result
     */
    @RequiresAuthentication
    @PostMapping("/health/tips/delete")
    public Result updateHealthTips(Long id) {

        healthTipsService.removeById(id);
        return Result.success();
    }

    /**
     * 拿到一级分类
     *
     * @return Result
     */
    @GetMapping("/health/tips/get/firstSort")
    public Result getFirstSort() {

        List<MedicineSort> list = medicineSortService.list(new QueryWrapper<MedicineSort>().eq("status", 1));
        return Result.success().codeAndMessage(ResultInfo.SUCCESS).data("data", list);
    }


    /**
     * 前端主页展示的健康小提示，status为1的就是展示
     *
     * @return Result
     */
    @GetMapping("/get/home/health/tips")
    public Result getHealthTipsToHome() {
        List<HealthTips> list = healthTipsService.list(new LambdaQueryWrapper<>(HealthTips.class)
                .eq(HealthTips::getStatus, 1)
                .orderByDesc(HealthTips::getGmtCreate));

        return Result.success().codeAndMessage(ResultInfo.SUCCESS).data("data", list);
    }

    /**
     * 分页拿到全部的健康资讯
     *
     * @param current 当前页
     * @return page
     */
    @GetMapping("/all/health/tips/{current}")
    public Result getAllHealthTips(@PathVariable Integer current) {

        Page<HealthTips> healthTipsPage = new Page<>(current, 10);
        QueryWrapper<HealthTips> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");
        Page<HealthTips> page = healthTipsService.page(healthTipsPage, wrapper);

        return Result.success().codeAndMessage(ResultInfo.SUCCESS).data("data", page);
    }


    /**
     * 根据分类拿到健康资讯，一页10篇
     *
     * @param category 分类
     * @param current  当前页
     * @return page
     */
    @GetMapping("/health/tips/{category}/{current}")
    public Result getHealthTipsByCategory(@PathVariable String category, @PathVariable Integer current) {

        Page<HealthTips> healthTipsPage = new Page<>(current, 10);
        Page<HealthTips> page = healthTipsService.page(healthTipsPage,
                new QueryWrapper<HealthTips>().eq("category", category));
        return Result.success().codeAndMessage(ResultInfo.SUCCESS).data("data", page);
    }

    /**
     * 根据标题和username拿到文章
     *
     * @param username username
     * @param title    标题
     * @return 文章详情
     */
    @GetMapping("/health/tips/get/details/{username}/{title}")
    public Result getDetailsByUsernameAndTitle(@PathVariable String username, @PathVariable String title) {

        HealthTips one = healthTipsService.getOne(new QueryWrapper<HealthTips>().eq("username", username).eq("title", title));
        return Result.success().codeAndMessage(ResultInfo.SUCCESS).data("data", one);
    }

    /**
     * 根据文章id获取文章
     * 应用场景：后台查看文章详情
     *
     * @param articleId 文章id
     * @return 文章
     */
    @PostMapping("/health/tips/get/details")
    public Result getDetailsById(Long articleId) {

        HealthTips article = healthTipsService.getById(articleId);
        return Result.success().data("responseBody", article);
    }

    /**
     * 阅览次数 + 1
     *
     * @param id id
     * @return 结果
     */
    @PostMapping("/health/tips/readNumber/add")
    public Result addReadNumber(Long id) {

        HealthTips one = healthTipsService.getById(id);
        Long readNumber = one.getReadNumber();
        Long newReadNumber = readNumber + 1;
        one.setReadNumber(newReadNumber);
        healthTipsService.updateById(one);
        return Result.success();
    }

    /**
     * 前台获取浏览次数最多的6篇文章
     *
     * @return data
     */
    @PostMapping("/health/tips/hot")
    public Result getHotHealthTips() {

        Page<HealthTips> healthTipsPage = new Page<>(1, 6);
        Page<HealthTips> data = healthTipsService.page(healthTipsPage,
                new QueryWrapper<HealthTips>().orderByDesc("read_number"));
        return Result.success().codeAndMessage(ResultInfo.SUCCESS).data("responseBody", data);
    }

    /**
     * 前台首页推荐的8篇文章
     *
     * @return list
     */
    @PostMapping("/health/tips/status")
    public Result getHotHealthTipsByStatus() {

        List<HealthTips> list = healthTipsService.list(new QueryWrapper<HealthTips>().eq("status", 1));
        return Result.success().codeAndMessage(ResultInfo.SUCCESS).data("responseBody", list);
    }

    /**
     * 更换前台首页推荐的8篇文章
     *
     * @param oldArticleId 已经在前台展示的
     * @param newArticleId 没展示，要替换的
     * @return res
     */
    @RequiresAuthentication
    @PostMapping("/health/tips/status/change")
    public Result changeHotHealthTips(Long oldArticleId, Long newArticleId) {

        HealthTips newArticle = healthTipsService.getById(newArticleId);
        if (newArticle.getStatus() == 1) {
            return Result.error().message("该文章已经在推荐列表中，无需替换！");
        } else {
            HealthTips oldArticle = healthTipsService.getById(oldArticleId);
            UpdateWrapper<HealthTips> updateWrapper1 = new UpdateWrapper<>();
            // 把要在首页展示的文章的status设置为1
            updateWrapper1.eq("id", newArticleId).set("status", 1);
            healthTipsService.update(new HealthTips(), updateWrapper1);
            UpdateWrapper<HealthTips> updateWrapper2 = new UpdateWrapper<>();
            // 把要被替换的文章的status设置为0
            updateWrapper2.eq("id", oldArticle.getId()).set("status", 0);
            healthTipsService.update(new HealthTips(), updateWrapper2);
            return Result.success().message("更换成功！");
        }
    }


    /**
     * 分页获取全部的资讯
     * 使用场景：后台资讯列表
     *
     * @param current  当前页
     * @param pageSize 数量
     * @return data
     */
    @PostMapping("/health/tips/all")
    public Result getAllHotHealthTips(Integer current, Integer pageSize, String sortBy) {

        Page<HealthTips> healthTipsPage = new Page<>(current, pageSize);
        Page<HealthTips> data = healthTipsService.page(healthTipsPage,
                new QueryWrapper<HealthTips>().orderByDesc(sortBy));
        return Result.success().codeAndMessage(ResultInfo.SUCCESS).data("responseBody", data);
    }

}

