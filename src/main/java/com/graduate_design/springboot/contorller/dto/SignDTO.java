package com.graduate_design.springboot.contorller.dto;

import com.graduate_design.springboot.entity.Activity;
import lombok.Data;

@Data
public class SignDTO extends Activity {
    private Integer signID;
    private Integer userID;
    private String userNickname;
    private String code;    // 表示取消报名、取消参赛资格、恢复参赛资格

}
