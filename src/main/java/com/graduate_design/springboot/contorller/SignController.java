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

    @GetMapping("/page")
    public Map<String, Object> findPage(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize,
                                        @RequestParam Integer userID,
                                        @RequestParam String activityName,
                                        @RequestParam String organizer,
                                        @RequestParam String address) {
        return signService.findPage(pageNum,pageSize,userID,activityName,organizer,address);
    }
}
