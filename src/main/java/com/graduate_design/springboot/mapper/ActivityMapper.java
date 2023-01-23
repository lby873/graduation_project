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

    Boolean update(Activity activity);      //在Activity.xml具体实现

    @Delete("DELETE FROM activity WHERE activityID = #{activityID}")
    Boolean deleteById(Integer activityID);

    @Select("SELECT * FROM activity WHERE name LIKE concat('%', #{name}, '%') AND address LIKE concat('%',#{address},'%')  " +
            "AND organizer LIKE concat('%', #{organizer}, '%') limit #{pageNum}, #{pageSize}")
    List<Activity> selectPage(Integer pageNum, Integer pageSize, String name, String organizer, String address);

    @Select("SELECT count(*) FROM activity WHERE name LIKE concat('%', #{name}, '%') " +
            "AND address LIKE concat('%', #{address}, '%') AND organizer LIKE concat('%', #{organizer}, '%')")
    Integer selectTotal(String name, String organizer, String address);

    @Select("SELECT * FROM activity WHERE organizer LIKE #{organizer} limit #{pageNum}, #{pageSize}")
    List<Activity> selectOrgPage(Integer pageNum, Integer pageSize, String organizer);

    @Select("SELECT count(*) FROM activity WHERE organizer LIKE #{organizer}")
    Integer selectOrgTotal(String organizer);
}
