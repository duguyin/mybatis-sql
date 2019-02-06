package com.duguyin.mybatissql.mapper;

import com.duguyin.mybatissql.UserTable;
import com.duguyin.mybatissql.tool.MybatisSqlBuilder;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@Mapper
public interface UserTableMapper {


    @Select("select count(id) from user_table")
    int countAll();

    @ResultMap("_UserAction_")
    @Select("select name,user_action,before_result from user_table limit 1")
    UserTable selectOne();


}
