package com.duguyin.mybatissql.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserTableMapper {


    @Select("select count(id) from user_table")
    int countAll();

    @ResultMap("_UserAction_")
    @Select("select name,user_action,before_result from user_table limit 1")
    UserTable selectOne();


}
