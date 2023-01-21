package com.graduate_design.springboot.service;

import com.graduate_design.springboot.contorller.dto.SignDTO;
import com.graduate_design.springboot.entity.Activity;
import com.graduate_design.springboot.entity.Sign;
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
        String code = signDTO.getCode();
        if (code.equals("0")){       // 报名
            return signMapper.insert(signDTO);
        }  else {           // 取消报名
            return  signMapper.cancelSign(signDTO.getUserID(),signDTO.getActivityID());
        }
    }

    // 活动列表
    public Map<String, Object> findActivityPage(Integer pageNum, Integer pageSize, Integer userID, String activityName,
                                        String organizer,String address) {
        pageNum = (pageNum - 1) * pageSize;
        List<Activity> data = signMapper.findActivity(pageNum, pageSize, userID, activityName, organizer, address);
        Integer total = signMapper.selectTotalActivity(userID, activityName, organizer, address);
        Map<String, Object> res = new HashMap<>();
        res.put("data", data);
        res.put("total", total);
        return res;

    }

    // 已报名、已参加列表
    public Map<String, Object> findPage(Integer pageNum, Integer pageSize, Integer userID, String activityName,
                                                String organizer,String address,Integer pageShow) {
        pageNum = (pageNum - 1) * pageSize;
        List<Sign> data = null;
        Integer total = null;
        if (pageShow == 0) {       // 0已报名列表，1已参加列表
            data = signMapper.findSign(pageNum, pageSize, userID, activityName, organizer, address);
            total = signMapper.selectTotalSign(userID, activityName, organizer, address);
        } else {
            data = signMapper.findPart(pageNum, pageSize, userID, activityName, organizer, address);
            total = signMapper.selectTotalPart(userID, activityName, organizer, address);
        }
        Map<String, Object> res = new HashMap<>();
        res.put("data", data);
        res.put("total", total);
        return res;

    }
}
