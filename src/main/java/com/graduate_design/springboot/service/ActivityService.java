package com.graduate_design.springboot.service;

import com.graduate_design.springboot.entity.Activity;
import com.graduate_design.springboot.mapper.ActivityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ActivityService {
    @Autowired
    private ActivityMapper activityMapper;

    public Boolean save(Activity activity){
        if(activity.getActivityID() == null){               //表示插入，新增数据
            return activityMapper.insert(activity);
        } else {                                    //否则表示更新部分数据
            return activityMapper.update(activity);
        }
    }

    public List<Activity> findAllActivity() {
        return activityMapper.findAllActivity();
    }

    public Boolean deleteById(Integer id) {
        return activityMapper.deleteById(id);
    }

    public Map<String, Object> findPage(Integer pageNum, Integer pageSize, String name, String organizer, String address) {
        pageNum = (pageNum - 1) * pageSize;
        List<Activity> data = activityMapper.selectPage(pageNum, pageSize, name, organizer, address);
        Integer total = activityMapper.selectTotal(name,organizer,address);
        Map<String, Object>  res = new HashMap<>();
        res.put("data", data);
        res.put("total", total);
        return res;
    }
}
