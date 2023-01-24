package com.graduate_design.springboot.contorller;

import com.graduate_design.springboot.contorller.dto.SignDTO;
import com.graduate_design.springboot.service.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/sign")
public class SignController {
    @Autowired
    private SignService signService;

    @PostMapping("/save")
    public Boolean save(@RequestBody SignDTO signDTO){
        return signService.save(signDTO);
    }

    @GetMapping("/cancelQualification")        // 取消参赛资格
    public Boolean cancelQualification(@RequestParam Integer signID){
        return signService.cancelQualification(signID);
    }
    @GetMapping("/resetQualification")        // 恢复参赛资格
    public Boolean resetQualification(@RequestParam Integer signID){
        return signService.resetQualification(signID);
    }

    @GetMapping("/activityPage")    // 活动列表
    public Map<String, Object> findActivityPage(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize,
                                        @RequestParam Integer userID,
                                        @RequestParam String activityName,
                                        @RequestParam String organizer,
                                        @RequestParam String address,
                                        @RequestParam String endStatus) {
        return signService.findActivityPage(pageNum,pageSize,userID,activityName,organizer,address,endStatus);
    }

    @GetMapping("/page")        // 已报名、已参与列表
    public Map<String, Object> findPage(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize,
                                        @RequestParam Integer userID,
                                        @RequestParam String activityName,
                                        @RequestParam String organizer,
                                        @RequestParam String address,
                                        @RequestParam Integer pageShow) {
        return signService.findPage(pageNum,pageSize,userID,activityName,organizer,address,pageShow);
    }

    @GetMapping("/allPage")        // 所有活动报名参与记录列表
    public Map<String, Object> findAllPage(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize,
                                        @RequestParam String activityName,
                                        @RequestParam String organizer,
                                        @RequestParam String address) {
        return signService.findAllPage(pageNum,pageSize,activityName,organizer,address);
    }

}
