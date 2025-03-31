package com.yjx.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@Accessors(chain = true)
public class CommentsDto implements Serializable {

    private String drugName;

    private String username;

    private String avatar;

    private String orderId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtCreate;

    private String message = "";

    private Integer rate = 5;
}
