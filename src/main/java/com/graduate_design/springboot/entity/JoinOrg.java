package com.graduate_design.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "joinorg")
public class JoinOrg {
    private Integer joinOrgID;
    private Integer userID;
    private String username;
    private String userNickname;
    private String userPhone;
    private String userIdentity;
    private String joinOrganization;

}
