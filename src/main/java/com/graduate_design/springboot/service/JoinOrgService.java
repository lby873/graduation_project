package com.graduate_design.springboot.service;

import com.graduate_design.springboot.contorller.dto.UserDTO;
import com.graduate_design.springboot.entity.JoinOrg;
import com.graduate_design.springboot.mapper.JoinOrgMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JoinOrgService {
    @Autowired
    private JoinOrgMapper joinOrgMapper;

    public Boolean applyToJoin(UserDTO userDTO) {
        return joinOrgMapper.applyToJoin(userDTO);
    }

    public Boolean cancelApply(Integer userID) {
        return joinOrgMapper.cancelApply(userID);
    }

    public Map<String, Object> findApplyPerson(String username, String organization) {
        List<JoinOrg> list = joinOrgMapper.findApplyPerson(username,organization);
        Map<String, Object> res = new HashMap<>();
        res.put("data", list);
        return res;
    }
}
