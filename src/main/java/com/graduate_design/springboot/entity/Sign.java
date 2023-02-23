package com.graduate_design.springboot.entity;

import lombok.Data;

@Data
public class Sign {
    private Integer signID;
    private Integer activityID;
    private String activityTime;
    private String activityName;
    private String organizer;
    private String address;
    private String detail;
    private Integer userID;
    private String status;

}
