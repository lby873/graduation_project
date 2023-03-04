package com.graduate_design.springboot.mapper;

import com.graduate_design.springboot.contorller.dto.UserDTO;
import com.graduate_design.springboot.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user")       //查询所有数据
    List<User> findAll();
    @Select("SELECT DISTINCT organization FROM user WHERE organization NOT LIKE '无'")
    List<User> findOrg();
    @Select("SELECT DISTINCT identity FROM user")
    List<User> findIdentity();
    @Select("SELECT * FROM user WHERE username LIKE #{username}")
    User checkUsername(String username);        // 判断用户名是否重复

    @Insert("INSERT INTO user(username,password,nickname,phone,identity,organization) " +
            "VALUES (#{username},#{password},#{nickname},#{phone},#{identity},#{organization})")
    Boolean insert(UserDTO userDTO);      //插入数据

    Boolean update(UserDTO userDTO);      //@update内容在User.xml具体实现，动态更新数据

    @Delete("DELETE FROM user WHERE userID = #{id}")
    Boolean deleteById(Integer id);

    @Delete("DELETE FROM sign WHERE userID = #{id}")
    Boolean deleteUserActivity(Integer id);         // 若用户被删除，则相关活动要被删除

    @Select("SELECT * FROM user WHERE (username LIKE concat('%', #{username}, '%') AND nickname LIKE " +
            "concat('%', #{nickname}, '%') AND phone LIKE concat('%', #{phone}, '%') AND identity LIKE " +
            "concat('%', #{identity}, '%')  AND organization LIKE concat('%', #{organization}, '%')) limit #{pageNum}, #{pageSize}")
    List<User> selectPage(Integer pageNum, Integer pageSize, String username, String nickname, String phone, String identity, String organization);

    @Select("SELECT count(*) FROM user WHERE (username LIKE concat('%', #{username}, '%') " +
            "AND nickname LIKE concat('%', #{nickname}, '%') AND phone LIKE concat('%', #{phone}, '%') " +
            "AND identity LIKE concat('%', #{identity}, '%')  AND organization LIKE concat('%', #{organization}, '%'))")
    Integer selectTotal(String username, String nickname, String phone, String identity, String organization);

    @Select("SELECT password FROM user WHERE username LIKE #{username}")
    String getEncPassword(String username);     // 得到用户的加密密码
    @Select("SELECT * FROM user WHERE (username LIKE #{username} AND identity LIKE concat('%', #{identity}, '%'))")
    User loginData(String username, String identity);  // 检查登录信息

    @Select("SELECT * FROM user WHERE organization LIKE #{organization}")
    List<User> findAllMember(String organization);      // 查找所有社团成员，包括管理员
    @Select("SELECT * FROM user WHERE organization LIKE #{organization} AND identity NOT LIKE '社团管理员'")
    List<User> findMember(String organization);         // 不包括管理员

    @Update("UPDATE user SET identity='普通用户', organization='无' WHERE userID=#{userID}")
    Boolean alterMemberToUser(Integer userID);    // 社团成员修改为普通用户

    @Update("update user set identity='普通用户', organization='无' where userID=#{oldOrgAdminID}")
    Boolean alterAdminToUser(Integer oldOrgAdminID);     // 管理员修改为普通用户

    @Update("update user set identity='社团管理员', organization=#{orgName} where userID=#{newOrgAdminID}")
    Boolean alterToAdmin( Integer newOrgAdminID, String orgName);   // 修改为新管理员

    @Update("UPDATE user SET identity='社团成员', organization=#{organization} WHERE userID=#{userID}")
    Boolean alterUserToMember(Integer userID,String organization);
}
