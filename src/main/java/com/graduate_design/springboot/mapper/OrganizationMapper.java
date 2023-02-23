package com.graduate_design.springboot.mapper;

import com.graduate_design.springboot.entity.Organization;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrganizationMapper {
    @Select("SELECT * FROM organization limit #{pageNum}, #{pageSize}")
    List<Organization> selectPage(Integer pageNum, Integer pageSize);
    @Select("SELECT count(*) FROM organization ")
    Integer selectTotal();

    // 新增，修改社团信息
    @Insert("INSERT into organization(orgName,orgSummary,orgCreatedDate,orgAdminID) " +
            "VALUES (#{orgName},#{orgSummary},#{orgCreatedDate},#{orgAdminID})")
    Boolean insert(Organization organization);
    @Update("UPDATE organization SET orgName=#{orgName},orgSummary=#{orgSummary},orgCreatedDate=#{orgCreatedDate}," +
            "orgAdminID=#{orgAdminID} WHERE orgID=#{orgID}")
    Boolean update(Organization organization);

    // 删除社团信息
    @Delete("DELETE FROM organization WHERE orgID = #{orgID}")
    Boolean delete(Integer orgID);

    // 查某社团信息
    @Select("SELECT * FROM organization WHERE orgName = #{orgName}")
    List<Organization> findOrgMsg(String orgName);


}
