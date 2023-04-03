package com.graduate_design.springboot.mapper;

import com.graduate_design.springboot.entity.Organization;
import com.graduate_design.springboot.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrganizationMapper {
    @Select("SELECT * FROM organization")
    List<Organization> selectPage();

    // 新增，修改社团信息
    @Insert("INSERT into organization(orgName,orgSummary,orgCreatedDate,orgAdminID) " +
            "VALUES (#{orgName},#{orgSummary},#{orgCreatedDate},#{orgAdminID})")
    Boolean insert(Organization organization);
    @Update("UPDATE organization SET orgName=#{orgName},orgSummary=#{orgSummary},orgCreatedDate=#{orgCreatedDate}," +
            "orgAdminID=#{orgAdminID} WHERE orgID=#{orgID}")
    Boolean update(Organization organization);

    // 删除社团信息
    @Delete("DELETE FROM organization WHERE orgAdminID = #{orgAdminID}")
    Boolean delete(Integer orgAdminID);

    // 根据社团名称、社团管理员ID，查社团信息
    @Select("SELECT * FROM organization WHERE orgName = #{orgName}")
    List<Organization> findOrgMsg(String orgName);
    @Select("SELECT * FROM organization WHERE orgAdminID = #{orgAdminID}")
    Organization findOrgMsgByAdminID(Integer orgAdminID);

}
