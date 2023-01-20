package com.graduate_design.springboot.service;

import com.graduate_design.springboot.contorller.dto.SignDTO;
import com.graduate_design.springboot.entity.Activity;
import com.graduate_design.springboot.mapper.SignMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SignService {
    @Autowired
    private SignMapper signMapper;

    public Boolean save(SignDTO signDTO) {
        Integer signID = signDTO.getSignID();
        if (signDTO.getSignID() == null){       // 表示新增
            return signMapper.insert(signDTO);
        }  else {           // 表示更新状态
            if (signDTO.getCode() == "0"){      // 取消报名
                return signMapper.cancelSign(signID);
            } else if(signDTO.getCode() == "1"){    // 取消参赛资格
                return signMapper.cancelQualification(signID);
            } else {                            // 恢复参赛资格
                return signMapper.resetQualification(signID);
            }

        }
    }

    public Map<String, Object> findPage(Integer pageNum, Integer pageSize, Integer userID,
                                        String activityName, String organizer,String address) {
        pageNum = (pageNum - 1) * pageSize;
        List<Activity> data = signMapper.findActivity(pageNum,pageSize,userID,activityName,organizer,address);
        Integer total = signMapper.selectTotal(userID,activityName,organizer,address);
        Map<String, Object> res = new HashMap<>();
        res.put("data", data);
        res.put("total", total);
        return res;

    }
}
