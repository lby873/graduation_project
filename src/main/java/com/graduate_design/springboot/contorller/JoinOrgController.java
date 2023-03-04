package com.graduate_design.springboot.contorller;

import com.graduate_design.springboot.contorller.dto.UserDTO;
import com.graduate_design.springboot.entity.JoinOrg;
import com.graduate_design.springboot.service.JoinOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/joinOrg")
public class JoinOrgController {
    @Autowired
    private JoinOrgService joinOrgService;

    @GetMapping
    public Map<String, Object> findApplyPerson(@RequestParam String username,
                                @RequestParam String organization){
        return joinOrgService.findApplyPerson(username,organization);
    }

    @PostMapping("/applyToJoin")
    public Boolean applyToJoin(@RequestBody UserDTO userDTO){
        return joinOrgService.applyToJoin(userDTO);
    }

    @DeleteMapping("/{userID}")
    public Boolean cancelApply(@PathVariable Integer userID){
        return joinOrgService.cancelApply(userID);
    }
}
