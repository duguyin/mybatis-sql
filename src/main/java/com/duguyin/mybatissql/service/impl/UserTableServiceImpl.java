package com.duguyin.mybatissql.service.impl;

import com.duguyin.mybatissql.UserTable;
import com.duguyin.mybatissql.mapper.UserTableMapper;
import com.duguyin.mybatissql.service.UserTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserTableServiceImpl implements UserTableService {

    @Autowired
    private UserTableMapper userTableMapper;

    @Override
    public int countAll() {
        UserTable userTable = userTableMapper.selectOne();
        System.out.println(userTable.toString());
        return userTableMapper.countAll();
    }
}
