package com.graduate_design.springboot.service;

import com.graduate_design.springboot.entity.Organization;
import com.graduate_design.springboot.entity.User;
import com.graduate_design.springboot.mapper.OrganizationMapper;
import com.graduate_design.springboot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrganizationService {
    @Autowired
    private OrganizationMapper organizationMapper;
    @Autowired
    private UserMapper userMapper;

    public Map<String, Object> findPage() {
        List<Organization> data = organizationMapper.selectPage();
        Map<String, Object> res = new HashMap<>();
        res.put("data", data);
        return res;
    }

    public String save(Organization organization) {
        String code = null;         // 0：插入、更新数据成功。 1：社团名称重复。 2：社团管理员ID重复。 3：该用户不存在
        String orgName = organization.getOrgName();
        Integer orgAdminID = Integer.valueOf(organization.getOrgAdminID());
        List<Organization> org1 = organizationMapper.findOrgMsg(orgName);           // 按名称查询
        Organization flag1 = organizationMapper.findOrgMsgByAdminID(orgAdminID);    // 按管理员ID查询
        User flag2 = userMapper.checkUserID(orgAdminID);                         // user表是否存在该ID
        if(organization.getOrgID() == null){        // 表示插入，新增数据
            if (org1.size() > 0){                  // 社团名称相同
                code = "1";
            } else if (flag1 != null && flag1.getOrgID() != organization.getOrgID()){ // 社团ID不同，管理员ID相同
                code = "2";
            } else if (flag2 == null){
                code = "3";
            } else if (organizationMapper.insert(organization)) {
                code = "0";
            }
        } else {                                // 更新社团信息
            if (flag1 != null && flag1.getOrgID() != organization.getOrgID()){         // 社团ID不同，管理员ID相同
                code = "2";
            }else if (flag2 == null){           // 用户不存在
                code = "3";
            }else if (organizationMapper.update(organization)) {
                code = "0";
            }
        }
        return code;
    }

    public Boolean delete(Integer orgAdminID) {
        // 删除社团，要同时修改社团管理员为普通用户
        return organizationMapper.delete(orgAdminID) && userMapper.alterAdminToUser(orgAdminID);
    }

    public Map<String, Object> findOrgMsg(String orgName) {
        List<Organization> data = organizationMapper.findOrgMsg(orgName);
        Map<String, Object> res = new HashMap<>();
        res.put("data",data);
        return res;
    }
}
