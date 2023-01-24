package com.graduate_design.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor      //无参构造
@AllArgsConstructor     //有参构造
public class Organization {
    private Integer orgID;
    private String orgName;
    private String orgSummary;
    private String orgCreatedDate;
    private Integer orgAdminID;
    private String orgAdminName;
}
