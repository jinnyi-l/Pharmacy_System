package com.yjx.dto;

import lombok.Data;

import java.io.Serializable;


@Data
public class LoginDto implements Serializable {

    private String username;

    private String password;

    private String verificationCode;

    private String email;

    private String emailCode;

    private boolean remember;
}
