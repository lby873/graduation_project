package com.graduate_design.springboot.contorller;

import com.graduate_design.springboot.entity.Activity;
import com.graduate_design.springboot.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping
    public List<Activity> index() {
        return activityService.findAllActivity();
    }

    @PostMapping("/save")
    public Boolean save(@RequestBody Activity activity){
        return activityService.save(activity);
    }

    @PostMapping("/ending/{activityID}")
    public Boolean ending(@PathVariable Integer activityID){
        return activityService.ending(activityID);
    }

    @DeleteMapping("/{activityID}")
    public Boolean delete(@PathVariable Integer activityID){
        return activityService.deleteById(activityID);
    }

    @GetMapping("/page")
    public Map<String, Object> findPage(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize,
                                        @RequestParam String name,
                                        @RequestParam String organizer,
                                        @RequestParam String address) {
        return activityService.findPage(pageNum,pageSize,name,organizer,address);
    }

//    @GetMapping("/orgPage")
//    public Map<String, Object> findOrgPage( @RequestParam Integer pageNum,
//                                            @RequestParam Integer pageSize,
//                                            @RequestParam String organizer) {
//        return activityService.findOrgPage(pageNum,pageSize,organizer);
//    }

    @GetMapping("/activityEndPage")    // 活动列表
    public Map<String, Object> findActivityEndPage(@RequestParam Integer pageNum,
                                                @RequestParam Integer pageSize,
                                                @RequestParam String activityName,
                                                @RequestParam String organizer,
                                                @RequestParam String address,
                                                @RequestParam String endStatus) {
        return activityService.findActivityEndPage(pageNum,pageSize,activityName,organizer,address,endStatus);
    }

}
