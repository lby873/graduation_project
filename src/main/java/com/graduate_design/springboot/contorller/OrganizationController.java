package com.graduate_design.springboot.contorller;

import com.graduate_design.springboot.entity.Organization;
import com.graduate_design.springboot.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/org")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @GetMapping("/page")
    public Map<String, Object> findPage(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize) {
        return organizationService.findPage(pageNum,pageSize);
    }

    @GetMapping("/findOrgMsg")
    public Map<String, Object> findOrgMsg(@RequestParam String orgName) {
        return organizationService.findOrgMsg(orgName);
    }

    @PostMapping("/save")
    public Boolean save(@RequestBody Organization organization){
        return organizationService.save(organization);
    }

    @DeleteMapping("/{orgID}")
    public Boolean delete(@PathVariable Integer orgID){
        return organizationService.delete(orgID);
    }
}
