package com.graduate_design.springboot.mapper;

import com.graduate_design.springboot.contorller.dto.UserDTO;
import com.graduate_design.springboot.entity.JoinOrg;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface JoinOrgMapper {

    @Insert("INSERT INTO joinorg(userID,username,userNickname,userPhone,userIdentity,joinOrganization) " +
            "VALUES (#{userID},#{username},#{nickname},#{phone},#{identity},#{organization})")
    Boolean applyToJoin(UserDTO userDTO);

    @Delete("DELETE FROM joinorg WHERE userID = #{userID}")
    Boolean cancelApply(Integer userID);

    @Select("SELECT * FROM joinorg WHERE username LIKE concat('%', #{username}, '%') " +
            "AND joinOrganization LIKE concat('%', #{organization}, '%')")
    List<JoinOrg> findApplyPerson(String username, String organization);
}
