package com.yjx.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
public class NormalUserInfoDto implements Serializable {

    private Long id;

    private String username;

    private String authority;

    private String nickName;

    private String realName;

    private String avatar;

    private String gender;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime birthday;

    private String phone;

    private String email;

    private String address;

    // 数据库中的status
    private Integer status;

    // 前端展示的status
    private String pageStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtCreate;

}
