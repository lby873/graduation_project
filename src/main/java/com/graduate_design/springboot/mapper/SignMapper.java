package com.graduate_design.springboot.mapper;

import com.graduate_design.springboot.contorller.dto.SignDTO;
import com.graduate_design.springboot.entity.Activity;
import com.graduate_design.springboot.entity.Sign;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SignMapper {
    // 还未报名的活动列表
    @Select("SELECT * FROM activity WHERE activityID NOT IN (SELECT activityID FROM sign WHERE userID = #{userID}) " +
            "AND name LIKE concat('%', #{activityName}, '%') AND address LIKE concat('%', #{address}, '%') " +
            "AND organizer LIKE concat('%', #{organizer}, '%') AND endStatus=#{endStatus}")
    List<Activity> findActivity(Integer userID, String activityName, String organizer, String address, String endStatus);
    @Select("SELECT count(*) FROM activity WHERE activityID NOT IN (SELECT activityID FROM sign WHERE userID = #{userID}) " +
            "AND name LIKE concat('%', #{activityName}, '%') AND address LIKE concat('%', #{address}, '%') " +
            "AND organizer LIKE concat('%', #{organizer}, '%')  AND endStatus = #{endStatus}")
    Integer selectTotalActivity(Integer userID, String activityName, String organizer, String address, String endStatus);

    // 已经报名的活动列表
    @Select("SELECT * FROM sign WHERE userID = #{userID} AND status = '已报名' AND activityName LIKE " +
            "concat('%', #{activityName}, '%') AND address LIKE concat('%', #{address}, '%') AND organizer LIKE " +
            "concat('%', #{organizer}, '%')")
    List<Sign> findSign(Integer userID, String activityName, String organizer, String address);
    @Select("SELECT count(*) FROM sign WHERE userID = #{userID} AND status = '已报名' AND activityName LIKE " +
            "concat('%', #{activityName}, '%') AND address LIKE concat('%', #{address}, '%') AND organizer " +
            "LIKE concat('%', #{organizer}, '%') ")
    Integer selectTotalSign(Integer userID, String activityName, String organizer, String address);

    // 已参加活动列表
    @Select("SELECT * FROM sign WHERE status != '已报名' AND userID = #{userID} AND activityName LIKE " +
            "concat('%', #{activityName}, '%') AND address LIKE concat('%', #{address}, '%') AND organizer LIKE " +
            "concat('%', #{organizer}, '%')")
    List<Sign> findPart(Integer userID, String activityName, String organizer, String address);
    @Select("SELECT count(*) FROM sign WHERE status != '已报名' AND userID = #{userID} AND activityName " +
            "LIKE concat('%', #{activityName}, '%') AND address LIKE concat('%', #{address}, '%') " +
            "AND organizer LIKE concat('%', #{organizer}, '%')")
    Integer selectTotalPart(Integer userID, String activityName, String organizer, String address);

    // 所有活动报名参与记录列表
    @Select("SELECT s.signID,s.activityID,s.activityTime,s.activityName,s.organizer,s.address,s.detail,u.userID,u.nickname,s.status FROM sign s,user u " +
            "WHERE s.userID=u.userID AND activityName LIKE concat('%', #{activityName}, '%') AND address LIKE " +
            "concat('%', #{address}, '%') AND organizer LIKE concat('%', #{organizer}, '%') limit #{pageNum}, #{pageSize}")
    List<SignDTO> findAll(Integer pageNum, Integer pageSize, String activityName, String organizer, String address);
    @Select("SELECT count(*) FROM sign WHERE activityName LIKE concat('%', #{activityName}, '%') AND address LIKE " +
            "concat('%', #{address}, '%') AND organizer LIKE concat('%', #{organizer}, '%')")
    Integer selectTotal(String activityName, String organizer, String address);

    @Delete("DELETE FROM sign WHERE userID = #{userID} AND activityID = #{activityID}")
    Boolean cancelSign(Integer userID, Integer activityID);       // 取消报名

    @Update("UPDATE sign SET status='取消参赛资格' WHERE signID = #{signID}")
    Boolean cancelQualification(Integer signID);        // 取消参赛资格

    @Update("UPDATE sign SET status='已参加' WHERE signID = #{signID}")
    Boolean resetQualification(Integer signID);         // 恢复参赛资格

    @Insert("INSERT INTO sign(activityID,activityTime,activityName,organizer,address,detail,userID,status) " +
            "VALUES (#{activityID},#{activityTime},#{activityName},#{organizer},#{address},#{detail},#{userID},'已报名')")
    Boolean insert(SignDTO signDTO);            // 报名

}
