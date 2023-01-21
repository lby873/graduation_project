package com.graduate_design.springboot.contorller.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Integer userID;
    private String username;
    private String password;
    private String nickname;
    private String phone;
    private String identity;
    private String organization;
}
