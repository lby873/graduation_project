package com.graduate_design.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data                   // get和set方法
@NoArgsConstructor      // 无参构造
@AllArgsConstructor     // 有参构造
@TableName(value = "user")
public class User {
    @TableId(type = IdType.AUTO)        // 加上比较符合规范
    private Integer userID;
    private String username;
    @JsonIgnore                         // 查询时忽略密码
    private String password;
    private String nickname;
    private String phone;
    private String identity;
    private String organization;

}
