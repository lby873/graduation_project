package com.graduate_design.springboot.service;

import com.graduate_design.springboot.contorller.dto.UserDTO;
import com.graduate_design.springboot.entity.User;
import com.graduate_design.springboot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public Boolean save(UserDTO userDTO) {        // 新增和修改
        Integer userID = userDTO.getUserID();
        String name = userDTO.getUsername();
        User user = userMapper.checkUsername(name);
        if (userID == null) {     // user没有id，则表示是新增，否则为更新
            return userMapper.insert(userDTO);
        } else if(user == null || user.getUserID() == userID){    // 修改后的用户名不重复，或没有修改
            return userMapper.update(userDTO);
        } else {
            return false;
        }
    }

    public Boolean deleteById(Integer id) {     // 删除某个id记录
        return userMapper.deleteById(id);
    }

    public List<User> findAll() {        // 查询所有
        return userMapper.findAll();
    }
    public List<User> findOrg() {
        List<User> list = userMapper.findOrg();
        return list;
    }
    public List<User> findIdentity() {
        List<User> list = userMapper.findIdentity();
        return list;
    }

    // 分页查询，按搜索框内容查询
    public Map<String, Object> findPage(Integer pageNum, Integer pageSize, String username, String nickname,
                                        String phone, String identity, String organization) {
        pageNum = (pageNum - 1) * pageSize;
        List<User> data = userMapper.selectPage(pageNum, pageSize, username, nickname, phone,identity,organization);
        Integer total = userMapper.selectTotal(username, nickname, phone,identity,organization);
        Map<String, Object> res = new HashMap<>();
        res.put("data", data);
        res.put("total", total);
        return res;
    }

    // 判断登录账号是否合法
    public User login(UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        String identity = userDTO.getIdentity();
        return userMapper.loginData(username,password,identity);
    }

    public Boolean register(UserDTO userDTO) {
        String name = userDTO.getUsername();
        User user = userMapper.checkUsername(name);
        if (user == null){      // 数据库没有相同的用户名
            return userMapper.insert(userDTO);
        } else {    // 存在相同用户名
            return false;
        }
    }

    public Map<String, Object> findMember(String organization) {
        List<User> memberList = userMapper.findMember(organization);
        Map<String, Object> res = new HashMap<>();
        res.put("data", memberList);
        return res;
    }
}
