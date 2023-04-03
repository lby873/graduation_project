package com.graduate_design.springboot.mapper;
import com.graduate_design.springboot.entity.Activity;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface ActivityMapper {
    @Select("SELECT * FROM activity")           //查询所有数据
    List<Activity> findAllActivity();

    @Insert("INSERT into activity(time,name,organizer,address,detail) " +
            "VALUES (#{time},#{name},#{organizer},#{address},#{detail})")
    Boolean insert(Activity activity);
    @Update("UPDATE activity SET time=#{time},name=#{name},organizer=#{organizer},address=#{address}," +
            "detail=#{detail} WHERE activityID=#{activityID}")
    Boolean update(Activity activity);

    @Delete("DELETE FROM activity WHERE activityID = #{activityID}")
    Boolean deleteById(Integer activityID);
    @Delete("DELETE FROM sign WHERE activityID = #{activityID}")
    Boolean deleteSignActivity(Integer activityID);             // 若活动被删除，则报名的相关活动要被删除
    @Select("SELECT count(*) FROM sign WHERE activityID = #{activityID}")
    Integer findSignActivity(Integer activityID);               // 查询是否有报名活动记录

    @Select("SELECT * FROM activity WHERE name LIKE concat('%', #{name}, '%') AND address LIKE concat('%',#{address},'%')  " +
            "AND organizer LIKE concat('%', #{organizer}, '%')")
    List<Activity> selectPage(String name, String organizer, String address);

//    @Select("SELECT * FROM activity WHERE organizer LIKE #{organizer} limit #{pageNum}, #{pageSize}")
//    List<Activity> selectOrgPage(Integer pageNum, Integer pageSize, String organizer);
//    @Select("SELECT count(*) FROM activity WHERE organizer LIKE #{organizer}")
//    Integer selectOrgTotal(String organizer);

    @Update("UPDATE activity,sign SET activity.endStatus = '活动已结束',sign.STATUS = '已参加' " +
            "WHERE activity.activityID = #{activityID} AND sign.activityID =#{activityID}")
    Boolean ending(Integer activityID);

    @Select("SELECT * FROM activity WHERE name LIKE concat('%', #{activityName}, '%') AND address LIKE concat('%', #{address}, '%') " +
            "AND organizer LIKE concat('%', #{organizer}, '%') AND endStatus=#{endStatus}")
    List<Activity> findEndActivity(String activityName, String organizer, String address, String endStatus);
    @Select("SELECT count(*) FROM activity WHERE name LIKE concat('%', #{activityName}, '%') AND address LIKE concat('%', #{address}, '%') " +
            "AND organizer LIKE concat('%', #{organizer}, '%')  AND endStatus = #{endStatus}")
    Integer totalEndActivity(String activityName, String organizer, String address, String endStatus);

}
