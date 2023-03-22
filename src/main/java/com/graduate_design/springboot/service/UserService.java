package com.graduate_design.springboot.service;

import com.graduate_design.springboot.contorller.dto.UserDTO;
import com.graduate_design.springboot.entity.Sign;
import com.graduate_design.springboot.entity.User;
import com.graduate_design.springboot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private BCryptPasswordEncoder encoder;

    // 新增和修改
    public Boolean save(UserDTO userDTO) {
        Integer userID = userDTO.getUserID();
        String username = userDTO.getUsername();
        String identity = userDTO.getIdentity();
        String organization = userDTO.getOrganization();
        User user = userMapper.checkUsername(username); // 查询数据库的用户数据

        // 如果是增、修为社团新管理员，一要增、改user表中对应社团的管理员，二要修改organization表中对应的社团为最新管理员
        if (identity.equals("社团管理员")){
            Boolean flag1 = userMapper.alterToUser(identity,organization);
            Boolean flag2 = userMapper.alterAdminID(userID,organization);
        }

        if (userID == null) {                       // user没有id，则表示是新增
            String encodePw = encoder.encode(userDTO.getPassword());
            userDTO.setPassword(encodePw);         // 设置为加密密码存入数据库
            return userMapper.insert(userDTO);
        } else {                                    // 更新数据
            if(user == null || user.getUserID() == userID){    // 修改后的用户名不重复，或没有修改
                if(userDTO.getPassword() != null){       // 用户设置了新密码
                    String encodePw = encoder.encode(userDTO.getPassword());
                    userDTO.setPassword(encodePw);         // 设置为加密密码存入数据库
                }
                return userMapper.update(userDTO);
            }else {
                return false;
            }
        }
    }

    public Boolean deleteById(Integer userID) {     // 删除某个用户id记录
        List<Sign> signList = userMapper.findActivityByUserID(userID);
        if (userMapper.checkUserID(userID).getIdentity().equals("社团管理员")){   // 如果是管理员，则不能直接删除
            return false;
        } else if (signList.size()>0){              // 该用户有活动报名参与记录
            return userMapper.deleteUserActivity(userID) && userMapper.deleteById(userID);
        } else {
            return userMapper.deleteById(userID);
        }
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
    public Map<String, Object> login(UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        String identity = userDTO.getIdentity();

        Map<String, Object> res = new HashMap<>();
        User user = userMapper.loginData(username);
        String code = null;    // 0：正常登录； 1：用户名错误； 2：密码错误； 3：身份错误

        if (user == null){      // 数据库中不存在该用户
            code = "1";
        }else if (!user.getIdentity().equals(identity)){        // 用户存在，身份不相等
            code = "3";
        }else if(encoder.matches(password, user.getPassword())) {  // 用户存在，身份相等，解密后核对密码
            code = "0";
        }else {
            code = "2";
        }
        res.put("data", user);
        res.put("code", code);
        return res;
    }

    public Boolean register(UserDTO userDTO) {
        String name = userDTO.getUsername();
        User user = userMapper.checkUsername(name);
        if (user == null){      // 数据库没有相同用户名的用户
            String encodePw = encoder.encode(userDTO.getPassword());
            userDTO.setPassword(encodePw);         // 设置为加密密码存入数据库
            return userMapper.insert(userDTO);
        } else {                // 存在相同用户名
            return false;
        }
    }

    public Map<String, Object> findMember(String organization, Integer orgMemberCode) {
        List<User> memberList;
        if (orgMemberCode == 1){
            memberList = userMapper.findAllMember(organization);
        } else {
            memberList = userMapper.findMember(organization);
        }
        Map<String, Object> res = new HashMap<>();
        res.put("data", memberList);
        return res;
    }

    public Boolean alterMemberToUser(Integer userID) {
        return userMapper.alterMemberToUser(userID);
    }

    public Boolean alterAdmin(Integer oldOrgAdminID, Integer newOrgAdminID,String orgName) {
        if (oldOrgAdminID == 9999){     // 新增社团，没有旧管理员，则无需修改
            return userMapper.alterToAdmin(newOrgAdminID,orgName);
        } else {
            return userMapper.alterToAdmin(newOrgAdminID,orgName) && userMapper.alterAdminToUser(oldOrgAdminID);
        }
    }

    public Boolean alterUserToMember(Integer userID,String organization) {
        organization = organization.replace("\"","");        // 转成不带双引号的字符串
        return userMapper.alterUserToMember(userID,organization);
    }
}
