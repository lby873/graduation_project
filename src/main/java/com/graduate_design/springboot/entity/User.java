package com.graduate_design.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor      //无参构造
@AllArgsConstructor     //有参构造
@TableName(value = "user")
public class User {
    //    @TableField(value = "userID")  // 指定数据库的字段名称
    private Integer userID;
    private String username;
    @JsonIgnore
    private String password;
    private String nickname;
    private String phone;
    private String identity;
    private String organization;

}
