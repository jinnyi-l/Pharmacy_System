package com.yjx.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Data
public class AdminInfoDto implements Serializable {

    private Long id;

    private String username;

    private String password;

    private Integer status;

    private String pageStatus;

    private Integer auth;

    private String authority;

    private String realName;

    private String avatar;

    private String gender;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private String phone;

    private String email;

    private String address;

    private String signature;

    private Integer version;

    private Integer deleted;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtCreate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtModified;

}
