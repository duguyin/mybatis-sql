package com.duguyin.mybatissql.mapper;

import com.duguyin.mybatissql.UserTable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@Mapper
public interface UserTableMapper {


    @Select("select count(id) from user_table")
    int countAll();

    @Results(id="liuyindog", value = {
            @Result(property = "beforeResult", column = "before_result" ,javaType = String.class, jdbcType = JdbcType.VARCHAR)
    })
    @Select("select name,user_action,before_result from user_table limit 1")
    UserTable selectOne();

}
