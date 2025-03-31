package com.yjx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yjx.dto.CommentsDto;
import com.yjx.entity.Comments;

import java.util.List;

public interface CommentsService extends IService<Comments> {

    /**
     * 获取订单下的药品项
     *
     * @param orderId 订单编号
     * @return OrdersItems
     */
    List<CommentsDto> getDrugsItemByOrderId(String orderId);

    /**
     * 提交评论
     *
     * @param comments 要提交的评论
     */
    void submitComments(CommentsDto[] comments);

    /**
     * 根据药品名称获取展示的评论
     *
     * @param drugName 药品名称
     * @return 评论
     */
    List<CommentsDto> getCommentsByDrugName(String drugName);
}
