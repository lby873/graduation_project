package com.graduate_design.springboot.contorller;

import com.graduate_design.springboot.contorller.dto.UserDTO;
import com.graduate_design.springboot.entity.User;
import com.graduate_design.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController             // @Controller + @ResponseBody
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")      // 登录
    public Map<String, Object> login(@RequestBody UserDTO userDTO) {   // 新增或者更新，requestBody将json转为User类型
        return userService.login(userDTO);
    }

    @PostMapping("/register")      // 注册
    public Boolean register(@RequestBody UserDTO userDTO){  return userService.register(userDTO); }

    @PostMapping("/save")       // 新增和修改用户信息
    public Boolean save(@RequestBody UserDTO userDTO) {   // 新增或者更新，requestBody将json转为UserDTO类型
        return userService.save(userDTO);
    }

    // 删除用户
    @DeleteMapping("/{userID}")     //删除
    public Boolean delete(@PathVariable Integer userID) {
        return userService.deleteById(userID);
    }

    //查询
    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    // 查询组织成员
    @GetMapping("/findMember")
    public Map<String, Object> findMember(@RequestParam String organization,@RequestParam Integer orgMemberCode) {
        return userService.findMember(organization,orgMemberCode);
    }

    // 查询组织列表
    @GetMapping("/org")
    public List<User> findOrg(){
        return userService.findOrg();
    }

    // 查询身份列表
    @GetMapping("/identity")
    public List<User> findIdentity(){
        return userService.findIdentity();
    }

    // 修改社团成员身份为普通用户
    @PostMapping("/alterMemberIdentity/{userID}")
    public Boolean alterMemberToUser(@PathVariable Integer userID){
        return userService.alterMemberToUser(userID);
    }

    // 修改普通用户身份为社团成员
    @PostMapping("/alterUserToMember/{userID}")
    public Boolean alterUserToMember(@PathVariable Integer userID,@RequestBody String organization){
        return userService.alterUserToMember(userID,organization);
    }

    // 修改社团管理员
    @GetMapping("/alterAdmin")
    public Boolean alterAdmin(@RequestParam Integer oldOrgAdminID,
                              @RequestParam String newOrgAdminID,
                              @RequestParam String orgName){
        return userService.alterAdmin(oldOrgAdminID, Integer.valueOf(newOrgAdminID),orgName);
    }

    // 分页查询
    // 接口路径：/user/page?pageNum=1&pageSize=10
    // @RequestParam接受
    // limit第一个参数 = (pageNum - 1) * pageSize
    // pageSize
    @GetMapping("/page")
    public Map<String, Object> findPage(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize,
                                        @RequestParam String username,
                                        @RequestParam String nickname,
                                        @RequestParam String phone,
                                        @RequestParam String identity,
                                        @RequestParam String organization) {
        return userService.findPage(pageNum,pageSize,username,nickname,phone,identity,organization);
    }
}
