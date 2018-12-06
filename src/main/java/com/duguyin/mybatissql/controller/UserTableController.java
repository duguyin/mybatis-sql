package com.duguyin.mybatissql.controller;


import com.duguyin.mybatissql.UserAction;
import com.duguyin.mybatissql.obj.MybatisDomainSqlPool;
import com.duguyin.mybatissql.obj.MybatisMapping;
import com.duguyin.mybatissql.service.UserTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tt")
public class UserTableController {

    @Value("${ttt.ok}")
    private String tttOK;

    @Autowired
    private UserTableService service;

    @GetMapping("/tt")
    public String get(){
        return service.countAll()+"";
    }

    @GetMapping("/user")
    public String user(){
        final MybatisMapping<UserAction> mapping = MybatisMapping.from(UserAction.class);
        final MybatisDomainSqlPool mybatisDomainSqlPool = new MybatisDomainSqlPool<>(mapping);
        System.out.println(mybatisDomainSqlPool.sql());
        return mybatisDomainSqlPool.sql();


    }

}
