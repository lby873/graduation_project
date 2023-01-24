package com.graduate_design.springboot.service;

import com.graduate_design.springboot.entity.Organization;
import com.graduate_design.springboot.mapper.OrganizationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrganizationService {
    @Autowired
    private OrganizationMapper organizationMapper;

    public Map<String, Object> findPage(Integer pageNum, Integer pageSize) {
        pageNum = (pageNum - 1) * pageSize;
        List<Organization> data = organizationMapper.selectPage(pageNum, pageSize);
        Integer total = organizationMapper.selectTotal();
        Map<String, Object> res = new HashMap<>();
        res.put("data", data);
        res.put("total", total);
        return res;
    }

    public Boolean save(Organization organization) {
        if(organization.getOrgID() == null){       //表示插入，新增数据
            return organizationMapper.insert(organization);
        } else {                                    //否则表示更新部分数据
            return organizationMapper.update(organization);
        }
    }

    public Boolean delete(Integer orgID) {
        return organizationMapper.delete(orgID);
    }

    public Map<String, Object> findOrgMsg(String orgName) {
        List<Organization> data = organizationMapper.findOrgMsg(orgName);
        Map<String, Object> res = new HashMap<>();
        res.put("data",data);
        return res;
    }
}
