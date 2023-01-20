package com.graduate_design.springboot.contorller;

import com.graduate_design.springboot.contorller.dto.UserDTO;
import com.graduate_design.springboot.entity.User;
import com.graduate_design.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")      // 登录
    public User login(@RequestBody UserDTO userDTO) {   // 新增或者更新，requestBody将json转为User类型
        return userService.login(userDTO);
    }

    @PostMapping("/register")      // 注册
    public Boolean register(@RequestBody UserDTO userDTO){  return userService.register(userDTO); }

    @PostMapping        // 新增和修改
    public Boolean save(@RequestBody UserDTO userDTO) {   // 新增或者更新，requestBody将json转为UserDTO类型
        return userService.save(userDTO);
    }

    //删除
    @DeleteMapping("/{id}")     //删除
    public Boolean delete(@PathVariable Integer id) {
        return userService.deleteById(id);
    }

    //查询所有
    @GetMapping
    public List<User> index() {
        return userService.findAll();
    }
    @GetMapping("/org")
    public List<User> findOrg(){
        return userService.findOrg();
    }
    @GetMapping("/identity")
    public List<User> findIdentity(){
        return userService.findIdentity();
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
