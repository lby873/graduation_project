package com.graduate_design.springboot.mapper;

import com.graduate_design.springboot.contorller.dto.UserDTO;
import com.graduate_design.springboot.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user")       //查询所有数据
    List<User> findAll();
    @Select("SELECT DISTINCT organization FROM user WHERE organization NOT LIKE '无'")
    List<User> findOrg();
    @Select("SELECT DISTINCT identity FROM user WHERE identity NOT LIKE '超级管理员'")
    List<User> findIdentity();
    @Select("SELECT * FROM user WHERE username LIKE #{username}")
    User checkUsername(String username);

    @Insert("INSERT INTO user(username,password,nickname,phone,identity,organization) " +
            "VALUES (#{username},#{password},#{nickname},#{phone},#{identity},#{organization})")
    Boolean insert(UserDTO userDTO);      //插入数据

    Boolean update(UserDTO userDTO);      //@update内容在User.xml具体实现，动态更新数据

    @Delete("DELETE FROM user WHERE userID = #{id}")
    Boolean deleteById(Integer id);

    @Select("SELECT * FROM user WHERE (username LIKE concat('%', #{username}, '%') AND nickname LIKE " +
            "concat('%', #{nickname}, '%') AND phone LIKE concat('%', #{phone}, '%') AND identity LIKE " +
            "concat('%', #{identity}, '%')  AND organization LIKE concat('%', #{organization}, '%')) limit #{pageNum}, #{pageSize}")
    List<User> selectPage(Integer pageNum, Integer pageSize, String username, String nickname, String phone, String identity, String organization);

    @Select("SELECT count(*) FROM user WHERE (username LIKE concat('%', #{username}, '%') " +
            "AND nickname LIKE concat('%', #{nickname}, '%') AND phone LIKE concat('%', #{phone}, '%') " +
            "AND identity LIKE concat('%', #{identity}, '%')  AND organization LIKE concat('%', #{organization}, '%'))")
    Integer selectTotal(String username, String nickname, String phone, String identity, String organization);

    @Select("SELECT * FROM user WHERE (username LIKE #{username} AND password LIKE #{password} AND" +
            " identity LIKE concat('%', #{identity}, '%'))")
    User loginData(String username, String password, String identity);  // 检查登录信息

    @Select("SELECT * FROM user WHERE organization LIKE #{organization}")
    List<User> findMember(String organization);
}
