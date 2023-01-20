package com.graduate_design.springboot.mapper;

import com.graduate_design.springboot.contorller.dto.SignDTO;
import com.graduate_design.springboot.entity.Activity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SignMapper {
    @Select("SELECT * FROM activity WHERE activityID NOT IN (SELECT activityID FROM sign WHERE userID = #{userID}) " +
            "AND name LIKE concat('%', #{activityName}, '%') AND address LIKE concat('%', #{address}, '%') " +
            "AND organizer LIKE concat('%', #{organizer}, '%') limit #{pageNum}, #{pageSize}")
    List<Activity> findActivity(Integer pageNum, Integer pageSize, Integer userID, String activityName, String organizer, String address);
    @Select("SELECT count(*) FROM activity WHERE activityID NOT IN (SELECT activityID FROM sign WHERE userID = #{userID}) " +
            "AND name LIKE concat('%', #{activityName}, '%') AND address LIKE concat('%', #{address}, '%') " +
            "AND organizer LIKE concat('%', #{organizer}, '%')")
    Integer selectTotal(Integer userID,String activityName, String organizer, String address);

    @Delete("DELETE FROM sign WHERE userID = #{userID}")
    Boolean deleteUserID(Integer userID);                     // 若用户被删除，则相关活动要被删除

    @Delete("DELETE FROM sign WHERE signID = #{signID}")
    Boolean cancelSign(Integer signID);                     // 取消报名

    @Update("UPDATE sign SET qualification = '没有参赛资格', participatingStatus='0' WHERE signID = #{signID}")
    Boolean cancelQualification(Integer signID);        // 取消参赛资格

    @Update("UPDATE sign SET qualification = '' WHERE signID = #{signID}")
    Boolean resetQualification(Integer signID);         // 恢复参赛资格

    @Insert("INSERT INTO sign(activityID,activityTime,activityName,organizer,address,userID,userNickname) " +
            "VALUES (#{activityID},#{time},#{name},#{organizer},#{address},#{userID},#{userNickname})")
    Boolean insert(SignDTO signDTO);            // 报名
}
