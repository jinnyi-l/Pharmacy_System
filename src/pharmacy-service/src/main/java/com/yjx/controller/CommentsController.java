package com.yjx.controller;


import com.yjx.common.lang.Result;
import com.yjx.common.lang.ResultInfo;
import com.yjx.dto.CommentsDto;
import com.yjx.service.CommentsService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/comments")
public class CommentsController {

    /**
     * 使用构造器注入的好处：
     *
     * 1. 保证依赖不可变（final关键字）
     * 2. 保证依赖不为空（省去了我们对其检查）
     * 3. 保证返回客户端（调用）的代码的时候是完全初始化的状态
     * 4. 避免了循环依赖
     * 5. 提升了代码的可复用性
     *
     * 参考博客：https://www.cnblogs.com/Donnnnnn/p/10405863.html
     */
    private final CommentsService commentsService;

    @Autowired
    public CommentsController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    /**
     * 查询订单包含的药品信息
     *
     * @param orderId 订单编号
     * @return Result
     */
    @RequiresAuthentication
    @PostMapping("/user/order/drugs")
    public Result getOrderDrugs(String orderId) {

        List<CommentsDto> drugsList = commentsService.getDrugsItemByOrderId(orderId);
        return Result.success().codeAndMessage("200", "操作成功！").data("responseBody", drugsList);
    }


    /**
     * 提交评论
     *
     * @param drugs 可能一个订单的药品不止有一种，接收一个数组
     * @return Result
     */
    @RequiresAuthentication
    @PostMapping("/submit")
    public Result submitComments(@RequestBody CommentsDto[] drugs) {

        try {
            commentsService.submitComments(drugs);
            return Result.success().codeAndMessage("200", "操作成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error().message("提交失败！");
        }
    }

    /**
     * 获取药品对应拥有的评价
     *
     * @param drugName 药品名称
     * @return 评价
     */
    @PostMapping("/drug/has")
    public Result getCommentsByDrugName(String drugName) {

        List<CommentsDto> list = commentsService.getCommentsByDrugName(drugName);
        return Result.success().codeAndMessage(ResultInfo.SUCCESS).data("responseBody", list);
    }

}

