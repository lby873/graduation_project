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
        if(activity.getActivityID() == null){       //表示插入，新增数据
            return activityMapper.insert(activity);
        } else {                                    //否则表示更新部分数据
            return activityMapper.update(activity);
        }
    }

    public List<Activity> findAllActivity() {
        return activityMapper.findAllActivity();
    }

    public Boolean deleteById(Integer activityID) {
        if (activityMapper.findSignActivity(activityID) != 0){
            return activityMapper.deleteSignActivity(activityID) && activityMapper.deleteById(activityID);
        }else {
            return activityMapper.deleteById(activityID);
        }

    }

    public Map<String, Object> findPage(String name, String organizer, String address) {
        List<Activity> data = activityMapper.selectPage(name, organizer, address);
        Map<String, Object>  res = new HashMap<>();
        res.put("data", data);
        return res;
    }

//    public Map<String, Object> findOrgPage(Integer pageNum, Integer pageSize, String organizer) {
//        pageNum = (pageNum - 1) * pageSize;
//        List<Activity> data = activityMapper.selectOrgPage(pageNum, pageSize,organizer);
//        Integer total = activityMapper.selectOrgTotal(organizer);
//        Map<String, Object>  res = new HashMap<>();
//        res.put("data", data);
//        res.put("total", total);
//        return res;
//    }

    public Boolean ending(Integer activityID) {
        return activityMapper.ending(activityID);
    }

    public Map<String, Object> findActivityEndPage(String activityName, String organizer, String address, String endStatus) {
        List<Activity> data = activityMapper.findEndActivity(activityName, organizer, address,endStatus);
        Integer total = activityMapper.totalEndActivity(activityName, organizer, address,endStatus);
        Map<String, Object> res = new HashMap<>();
        res.put("data", data);
        res.put("total", total);
        return res;
    }
}
