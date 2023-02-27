package com.graduate_design.springboot.service;

import com.graduate_design.springboot.contorller.dto.UserDTO;
import com.graduate_design.springboot.entity.User;
import com.graduate_design.springboot.mapper.UserMapper;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)   // 所有方法都会用到事务
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StringEncryptor stringEncryptor;

    public Boolean save(UserDTO userDTO) {        // 新增和修改
        Integer userID = userDTO.getUserID();     // 前端传来的用户数据
        String name = userDTO.getUsername();
        User user = userMapper.checkUsername(name); // 查询数据库的用户数据
        if (userID == null) {                       // user没有id，则表示是新增，否则为更新
            String encryptPw = stringEncryptor.encrypt(userDTO.getPassword());
            userDTO.setPassword(encryptPw);         // 设置为加密密码存入数据库
            return userMapper.insert(userDTO);
        } else if(user == null || user.getUserID() == userID){    // 修改后的用户名不重复，或没有修改
            if(userDTO.getPassword()!=null){                      // 用户设置了新密码
                String encryptPw = stringEncryptor.encrypt(userDTO.getPassword());
                userDTO.setPassword(encryptPw);         // 设置为加密密码
            }
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
        return userMapper.findOrg();
    }
    public List<User> findIdentity() {
        return userMapper.findIdentity();
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
        String encPassword = userMapper.getEncPassword(username);       // 得到加密密码字符串
        if(stringEncryptor.decrypt(encPassword).equals(password)){      // 解密后进行比较
            return userMapper.loginData(username,identity);
        }else {
            return null;
        }
    }

    public Boolean register(UserDTO userDTO) {
        String name = userDTO.getUsername();
        User user = userMapper.checkUsername(name);
        if (user == null){      // 数据库没有相同用户名的用户
            String encryptPw = stringEncryptor.encrypt(userDTO.getPassword());
            userDTO.setPassword(encryptPw);         // 设置为加密密码
            return userMapper.insert(userDTO);
        } else {                // 存在相同用户名
            return false;
        }
    }

    public Map<String, Object> findMember(String organization) {
        List<User> memberList = userMapper.findMember(organization);
        Map<String, Object> res = new HashMap<>();
        res.put("data", memberList);
        return res;
    }

    public Boolean alterIdentity(Integer userID) {
        return userMapper.alterIdentity(userID);
    }

    public Boolean alterAdmin(Integer oldOrgAdminID, Integer newOrgAdminID,String orgName) {
        return userMapper.alterToAdmin(newOrgAdminID,orgName) && userMapper.alterToUser(oldOrgAdminID);
    }

}
