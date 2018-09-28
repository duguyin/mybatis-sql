package com.duguyin.mybatissql.service.impl;

import com.duguyin.mybatissql.UserTable;
import com.duguyin.mybatissql.mapper.UserTableMapper;
import com.duguyin.mybatissql.service.UserTableService;
import com.duguyin.mybatissql.tool.MybatisSqlBuilder;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class UserTableServiceImpl implements UserTableService {

    @Autowired
    private UserTableMapper userTableMapper;

    @PostConstruct
    public void init(){
        final Configuration configuration = MybatisSqlBuilder.getConfiguration();
        System.out.println(configuration.getResultMaps());

    }

    @Override
    public int countAll() {
        UserTable userTable = userTableMapper.selectOne();
        System.out.println(userTable.toString());
        return userTableMapper.countAll();
    }
}
