package com.yjx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjx.dto.CommentsDto;
import com.yjx.entity.Comments;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CommentsMapper extends BaseMapper<Comments> {

    /**
     * 获取订单下的药品项
     *
     * @param orderId 订单编号
     * @return OrdersItems
     */
    List<CommentsDto> getDrugsItemByOrderId(String orderId);

    /**
     * 根据药品名称获取展示的评论
     *
     * @param drugName 药品名称
     * @return 评论
     */
    List<CommentsDto> getCommentsByDrugName(String drugName);

    /**
     * 查找username对应的id
     *
     * @param username 用户名
     * @return id
     */
    Long getIdByUsername(String username);

    /**
     * 获取头像路径
     *
     * @param id user的id
     * @return 头像路径
     */
    String getAvatar(Long id);

}
