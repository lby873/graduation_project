package com.graduate_design.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "activity")
@Data
public class Activity {
    private Integer activityID;
    private String time;
    private String name;
    private String organizer;
    private String address;
    private String detail;
    private String endStatus;
}
