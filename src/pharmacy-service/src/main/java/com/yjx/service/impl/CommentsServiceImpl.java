package com.yjx.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjx.dto.CommentsDto;
import com.yjx.entity.Comments;
import com.yjx.entity.Orders;
import com.yjx.mapper.CommentsMapper;
import com.yjx.mapper.OrdersMapper;
import com.yjx.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments> implements CommentsService {

    @Autowired
    private CommentsMapper commentsMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Override
    public List<CommentsDto> getDrugsItemByOrderId(String orderId) {

        return commentsMapper.getDrugsItemByOrderId(orderId);
    }

    @Override
    public void submitComments(CommentsDto[] comments) {
        for (CommentsDto item : comments) {
            Comments comment = new Comments();
            comment.setDrugName(item.getDrugName())
                    .setUsername(item.getUsername())
                    .setComment(item.getMessage())
                    .setRate(item.getRate())
                    .setOrderId(item.getOrderId());
            // 提交评价
            commentsMapper.insert(comment);
            // 评价了的订单更改状态为“已完成”,status = 4
            ordersMapper.update(new Orders(),
                    new UpdateWrapper<Orders>().eq("order_id", item.getOrderId()).set("status", 4));
        }
    }

    @Override
    public List<CommentsDto> getCommentsByDrugName(String drugName) {

        // 先根据药品名称拿到属于它的评论，评论包含：username，rate，gmtCreate，comments。
        List<CommentsDto> commentsList = commentsMapper.getCommentsByDrugName(drugName);
        // 再根据用户名获取对应的id，然后拿到头像
        for (CommentsDto item : commentsList) {
            Long id = commentsMapper.getIdByUsername(item.getUsername());
            String avatar = commentsMapper.getAvatar(id);
            item.setAvatar(avatar);
        }
        return commentsList;
    }
}
