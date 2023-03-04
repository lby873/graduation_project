package com.graduate_design.springboot.contorller.dto;

import com.graduate_design.springboot.entity.Activity;
import lombok.Data;

@Data
public class SignDTO{
    private Integer signID;
    private Integer activityID;
    private String activityTime;
    private String activityName;
    private String organizer;
    private String address;
    private String detail;
    private Integer userID;
    private String nickname;
    private String status;
    private String code;    // 0报名、1取消报名

}
